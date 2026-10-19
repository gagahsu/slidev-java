// Build a narrated lecture video from a Slidev deck's presenter notes.
//
//   node scripts/video/make-video.mjs <deck.md> [--slides 1-8] [--batches 4] [--skip-export] [--dry]
//
// Pipeline: slidev export (PNG per slide) -> notes to narration text -> Gemini TTS in a few batched
// requests (slides separated by long pauses) -> split each batch back into per-slide audio at the
// silences closest to the expected boundaries -> ffmpeg one clip per slide -> concat to
// out/video/<deck>/<deck>.mp4 (+ .srt, + split-report.txt to check the cuts).
// Batch audio and per-slide audio are cached by text hash, so re-runs only call TTS for changed text.
// Notes format: _shared/narration-spec.md ("【講師】" lines are dropped, [click] splits segments).
// Config from .env: GEMINI_API_KEY, GEMINI_TTS_MODEL, GEMINI_TTS_VOICE, GEMINI_TTS_STYLE (optional).
import fs from 'node:fs'
import path from 'node:path'
import crypto from 'node:crypto'
import { execFileSync, spawnSync } from 'node:child_process'
import { pathToFileURL } from 'node:url'

const root = path.resolve(import.meta.dirname, '../..')
const args = process.argv.slice(2)
const deck = args.find(a => !a.startsWith('--') && !/^\d/.test(a))
const flag = name => args.includes(`--${name}`)
const opt = name => { const i = args.indexOf(`--${name}`); return i >= 0 ? args[i + 1] : undefined }
if (!deck) { console.error('usage: node scripts/video/make-video.mjs <deck.md> [--slides 1-8] [--batches 4] [--skip-export] [--dry]'); process.exit(1) }

// ---------- config ----------
const envFile = path.join(root, '.env')
if (fs.existsSync(envFile)) {
  for (const line of fs.readFileSync(envFile, 'utf8').split(/\r?\n/)) {
    const m = line.match(/^\s*([A-Z_]+)\s*=\s*(.*?)\s*$/)
    if (m && !process.env[m[1]]) process.env[m[1]] = m[2].replace(/^["']|["']$/g, '')
  }
}
const { GEMINI_API_KEY, GEMINI_TTS_MODEL, GEMINI_TTS_VOICE = 'Kore' } = process.env
const STYLE = process.env.GEMINI_TTS_STYLE
  || '請用台灣華語，以親切、穩定、像老師上課的語氣，用適中的語速朗讀下面的課程旁白。遇到 <long pause> 時停頓約兩秒，不要唸出 <long pause>。'
const PAD = 0.6            // seconds of silence after each slide
const SECTION_MIN = 2.5    // minimum seconds a slide stays on screen
const BATCHES = Number(opt('batches') ?? 4)
const TRANSCRIBE_MODEL = process.env.GEMINI_TRANSCRIBE_MODEL || 'gemini-3.5-transcribe'

const name = path.basename(deck, '.md')
const outDir = path.join(root, 'out', 'video', name)
const pngDir = path.join(outDir, 'png')
const ttsDir = path.join(outDir, 'tts')
const clipDir = path.join(outDir, 'clips')
for (const d of [outDir, pngDir, ttsDir, clipDir]) fs.mkdirSync(d, { recursive: true })

// ---------- notes -> narration ----------
const { parseSync } = await import(pathToFileURL(path.join(root, 'node_modules/@slidev/parser/dist/index.mjs')).href)
const md = parseSync(fs.readFileSync(path.join(root, deck), 'utf8'), deck)
const lexicon = Object.entries(JSON.parse(fs.readFileSync(path.join(import.meta.dirname, 'lexicon.json'), 'utf8')))
  .sort((a, b) => b[0].length - a[0].length)

function toSpeech(text) {
  let s = text
  for (const [from, to] of lexicon) s = s.split(from).join(to)
  return s
    .replace(/`([^`]*)`/g, '$1')
    .replace(/\*\*([^*]*)\*\*/g, '$1')
    .replace(/[ \t]+/g, ' ')
    .replace(/\n{2,}/g, '\n')
    .trim()
}

const slides = md.slides.map((s, i) => {
  const note = (s.note || '').split(/\r?\n/).filter(l => !l.trim().startsWith('【講師】')).join('\n')
  const segments = note.split(/\[click(?::\d+)?\]/i).map(toSpeech)
  return { no: i + 1, text: segments.filter(Boolean).join('\n'), clicks: segments.length - 1 }
})

let range = [1, slides.length]
if (opt('slides')) { const [a, b] = opt('slides').split('-').map(Number); range = [a, b ?? a] }
const picked = slides.filter(s => s.no >= range[0] && s.no <= range[1])
const missing = picked.filter(s => !s.text).map(s => s.no)
if (missing.length) console.warn(`warning: no narration on slides ${missing.join(', ')}`)
if (picked.some(s => s.clicks)) console.warn('warning: [click] segments found; this version shows the final state of each slide for all segments')

const hash = (...parts) => crypto.createHash('sha1').update([GEMINI_TTS_MODEL, GEMINI_TTS_VOICE, STYLE, ...parts].join('\n')).digest('hex').slice(0, 16)
const slideWav = s => path.join(ttsDir, `slide-${hash(s.text)}.wav`)

// contiguous batches of uncached slides, balanced by character count
const todo = picked.filter(s => s.text && !fs.existsSync(slideWav(s)))
const batches = []
{
  const total = todo.reduce((n, s) => n + s.text.length, 0)
  const n = Math.min(BATCHES, todo.length)
  let cur = [], acc = 0
  for (const s of todo) {
    cur.push(s); acc += s.text.length
    if (batches.length < n - 1 && acc >= total * (batches.length + 1) / n) { batches.push(cur); cur = [] }
  }
  if (cur.length) batches.push(cur)
}

if (flag('dry')) {
  for (const s of picked) console.log(`--- slide ${s.no}\n${s.text}`)
  console.log(`\n${todo.length} slides need TTS -> ${batches.length} request(s):`)
  batches.forEach((b, i) => {
    const chars = b.reduce((n, s) => n + s.text.length, 0)
    console.log(`  batch ${i + 1}: slides ${b[0].no}-${b.at(-1).no}, ${chars} chars, ~${(chars / 4.5 / 60).toFixed(1)} min`)
  })
  process.exit(0)
}

// ---------- 1. export slides as PNG ----------
if (!flag('skip-export')) {
  console.log('exporting slides to PNG ...')
  fs.rmSync(pngDir, { recursive: true, force: true })
  execFileSync(process.platform === 'win32' ? 'pnpm.cmd' : 'pnpm',
    ['exec', 'slidev', 'export', deck, '--format', 'png', '--output', pngDir, '--scale', '2', '--timeout', '60000'],
    { cwd: root, stdio: 'inherit', shell: process.platform === 'win32' })
}
const pngs = fs.readdirSync(pngDir).filter(f => f.endsWith('.png'))
  .sort((a, b) => parseInt(a.match(/\d+/)) - parseInt(b.match(/\d+/)))
if (pngs.length !== slides.length) {
  console.error(`expected ${slides.length} PNGs, found ${pngs.length} in ${pngDir}`); process.exit(1)
}

// ---------- 2. TTS in batches, split back per slide ----------
function readWav(file) {
  const b = fs.readFileSync(file)
  return { rate: b.readUInt32LE(24), pcm: b.subarray(44) }
}
function writeWav(file, pcm, rate) {
  const h = Buffer.alloc(44)
  h.write('RIFF', 0); h.writeUInt32LE(36 + pcm.length, 4); h.write('WAVE', 8); h.write('fmt ', 12)
  h.writeUInt32LE(16, 16); h.writeUInt16LE(1, 20); h.writeUInt16LE(1, 22); h.writeUInt32LE(rate, 24)
  h.writeUInt32LE(rate * 2, 28); h.writeUInt16LE(2, 32); h.writeUInt16LE(16, 34); h.write('data', 36); h.writeUInt32LE(pcm.length, 40)
  fs.writeFileSync(file, Buffer.concat([h, pcm]))
}

async function ttsRequest(text, file) {
  if (fs.existsSync(file)) return
  const url = `https://generativelanguage.googleapis.com/v1beta/models/${GEMINI_TTS_MODEL}:generateContent`
  const body = {
    contents: [{ parts: [{ text: `${STYLE}\n\n${text}` }] }],
    generationConfig: {
      responseModalities: ['AUDIO'],
      speechConfig: { voiceConfig: { prebuiltVoiceConfig: { voiceName: GEMINI_TTS_VOICE } } },
    },
  }
  const res = await fetch(url, { method: 'POST', headers: { 'Content-Type': 'application/json', 'x-goog-api-key': GEMINI_API_KEY }, body: JSON.stringify(body) })
  if (!res.ok) throw new Error(`TTS ${res.status}: ${(await res.text()).slice(0, 500)}`)
  const json = await res.json()
  const parts = json.candidates?.[0]?.content?.parts?.filter(p => p.inlineData) ?? []
  if (!parts.length) throw new Error(`no audio in response: ${JSON.stringify(json).slice(0, 300)}`)
  const rate = Number(parts[0].inlineData.mimeType.match(/rate=(\d+)/)?.[1] ?? 24000)
  writeWav(file, Buffer.concat(parts.map(p => Buffer.from(p.inlineData.data, 'base64'))), rate)
  fs.writeFileSync(file.replace(/\.wav$/, '.txt'), text)
  console.log(`  finish reason: ${json.candidates[0].finishReason}`)
}

function silences(file) {
  const log = spawnSync('ffmpeg', ['-hide_banner', '-i', file, '-af', 'silencedetect=noise=-38dB:d=0.3', '-f', 'null', '-'], { encoding: 'utf8' }).stderr
  const out = []
  for (const m of log.matchAll(/silence_end: ([\d.]+) \| silence_duration: ([\d.]+)/g)) {
    const end = Number(m[1]), dur = Number(m[2])
    out.push({ start: end - dur, end, dur, mid: end - dur / 2 })
  }
  return out
}

// Recover what was said between silences: cut the batch at every silence, insert a spoken numbered
// marker ("標記 N", Windows SAPI zh-TW voice) after each chunk, transcribe once, split on the markers.
const MARKER_DIR = path.join(root, 'out', 'video', '_markers')
function ensureMarkers(n, rate) {
  fs.mkdirSync(MARKER_DIR, { recursive: true })
  const need = []
  for (let i = 1; i <= n; i++) if (!fs.existsSync(path.join(MARKER_DIR, `${i}-${rate}.wav`))) need.push(i)
  if (!need.length) return
  const ps = `Add-Type -AssemblyName System.Speech; $s = New-Object System.Speech.Synthesis.SpeechSynthesizer;
    $v = $s.GetInstalledVoices() | Where-Object { $_.VoiceInfo.Culture.Name -like 'zh-*' } | Select-Object -First 1;
    if (-not $v) { throw 'no zh voice installed' }; $s.SelectVoice($v.VoiceInfo.Name); $s.Rate = 2;
    foreach ($i in @(${need.join(',')})) { $s.SetOutputToWaveFile("${MARKER_DIR}\\raw-$i.wav"); $s.Speak("標記 $i") }; $s.Dispose()`
  execFileSync('powershell', ['-NoProfile', '-Command', ps])
  for (const i of need) {
    const raw = path.join(MARKER_DIR, `raw-${i}.wav`)
    execFileSync('ffmpeg', ['-y', '-loglevel', 'error', '-i', raw, '-ar', String(rate), '-ac', '1', '-af', 'adelay=250,apad=pad_dur=0.25', path.join(MARKER_DIR, `${i}-${rate}.wav`)])
    fs.rmSync(raw)
  }
}

function zhNumber(s) {
  if (/^\d+$/.test(s)) return Number(s)
  const d = { 零: 0, 〇: 0, 一: 1, 二: 2, 兩: 2, 两: 2, 三: 3, 四: 4, 五: 5, 六: 6, 七: 7, 八: 8, 九: 9 }
  let n = 0, cur = 0
  for (const c of s) {
    if (c in d) cur = d[c]
    else if (c === '十') { n += (cur || 1) * 10; cur = 0 }
    else if (c === '百') { n += (cur || 1) * 100; cur = 0 }
  }
  return n + cur
}

async function transcribeChunks(file, bounds) {
  const cache = file.replace(/\.wav$/, `.chunks-${TRANSCRIBE_MODEL}.json`)
  const key = bounds.map(b => b.toFixed(2)).join(',')
  if (fs.existsSync(cache)) {
    const c = JSON.parse(fs.readFileSync(cache, 'utf8'))
    if (c.key === key) return c.texts
  }
  const { rate, pcm } = readWav(file)
  const n = bounds.length - 1
  ensureMarkers(n - 1, rate)
  const parts = []
  for (let i = 0; i < n; i++) {
    parts.push(pcm.subarray(Math.round(bounds[i] * rate) * 2, Math.round(bounds[i + 1] * rate) * 2))
    if (i < n - 1) parts.push(readWav(path.join(MARKER_DIR, `${i + 1}-${rate}.wav`)).pcm)
  }
  const joined = file.replace(/\.wav$/, '.marked.wav'), mp3 = joined.replace(/\.wav$/, '.mp3')
  writeWav(joined, Buffer.concat(parts), rate)
  execFileSync('ffmpeg', ['-y', '-loglevel', 'error', '-i', joined, '-ac', '1', '-b:a', '48k', mp3])
  const url = `https://generativelanguage.googleapis.com/v1beta/models/${TRANSCRIBE_MODEL}:generateContent`
  const body = { contents: [{ parts: [{ inlineData: { mimeType: 'audio/mpeg', data: fs.readFileSync(mp3).toString('base64') } }] }] }
  const res = await fetch(url, { method: 'POST', headers: { 'Content-Type': 'application/json', 'x-goog-api-key': GEMINI_API_KEY }, body: JSON.stringify(body) })
  if (!res.ok) throw new Error(`transcribe ${res.status}: ${(await res.text()).slice(0, 300)}`)
  const json = await res.json()
  const text = (json.candidates?.[0]?.content?.parts ?? []).map(p => p.audioTranscription?.text ?? p.text ?? '').join('')
  const texts = Array(n).fill('')
  let last = 0, found = 0
  for (const m of text.matchAll(/[标標][记記]\s*([0-9]+|[零〇一二兩两三四五六七八九十百]+)[，,。.]?\s*/g)) {
    const k = zhNumber(m[1])
    if (k >= 1 && k <= n - 1) { texts[k - 1] += text.slice(last, m.index); found++ }
    last = m.index + m[0].length
  }
  texts[n - 1] += text.slice(last)
  if (found < (n - 1) * 0.7) throw new Error(`only ${found}/${n - 1} markers heard`)
  fs.writeFileSync(cache, JSON.stringify({ key, texts }))
  return texts
}

// characters that are spoken: Han characters count 1, each run of latin letters / digits counts 1
const spokenLength = s => (s.replace(/[A-Za-z0-9.]+/g, 'A').match(/[\p{Script=Han}A]/gu) ?? []).length

// assign consecutive chunks to slides so each slide's heard length matches its script length;
// returns, for each boundary between slides, the index of the chunk boundary (1..n-1)
function partition(chunkLens, slideLens, gapDur) {
  const n = chunkLens.length, k = slideLens.length
  const pre = [0]
  for (const l of chunkLens) pre.push(pre.at(-1) + l)
  const dp = Array.from({ length: k + 1 }, () => Array(n + 1).fill(Infinity))
  const from = Array.from({ length: k + 1 }, () => Array(n + 1).fill(-1))
  dp[0][0] = 0
  for (let i = 1; i <= k; i++) {
    for (let j = i; j <= n - (k - i); j++) {
      for (let p = i - 1; p < j; p++) {
        if (dp[i - 1][p] === Infinity) continue
        const c = dp[i - 1][p] + Math.abs(pre[j] - pre[p] - slideLens[i - 1]) - (j < n ? 2 * Math.min(gapDur[j - 1], 1.5) : 0)
        if (c < dp[i][j]) { dp[i][j] = c; from[i][j] = p }
      }
    }
  }
  const cuts = []
  for (let i = k, j = n; i > 1; i--) { j = from[i][j]; cuts.unshift(j) }
  return cuts
}

// choose one silence per boundary: monotonic, long, and close to the expected slide start
function pickCuts(sils, expected) {
  const k = expected.length, n = sils.length
  if (n < k) throw new Error(`only ${n} silences for ${k} boundaries`)
  const score = (j, i) => Math.min(sils[j].dur, 1.5) - 1.5 * Math.abs(sils[j].end - expected[i])
  const dp = Array.from({ length: k }, () => Array(n).fill(-Infinity))
  const from = Array.from({ length: k }, () => Array(n).fill(-1))
  for (let j = 0; j < n; j++) dp[0][j] = score(j, 0)
  for (let i = 1; i < k; i++) {
    let best = -Infinity, arg = -1
    for (let j = 0; j < n; j++) {
      if (j > 0 && dp[i - 1][j - 1] > best) { best = dp[i - 1][j - 1]; arg = j - 1 }
      if (arg >= 0) { dp[i][j] = best + score(j, i); from[i][j] = arg }
    }
  }
  let j = dp[k - 1].indexOf(Math.max(...dp[k - 1]))
  const cuts = []
  for (let i = k - 1; i >= 0; i--) { cuts.unshift(sils[j]); j = from[i][j] }
  return cuts
}

const report = []
if (batches.length) {
  if (!GEMINI_API_KEY || !GEMINI_TTS_MODEL) { console.error('set GEMINI_API_KEY and GEMINI_TTS_MODEL in .env'); process.exit(1) }
  console.log(`TTS: ${batches.length} request(s) for ${todo.length} slides`)
}
for (const [bi, batch] of batches.entries()) {
  const text = batch.map(s => s.text).join('\n\n<long pause>\n\n')
  const file = path.join(ttsDir, `batch-${hash(text)}.wav`)
  console.log(`batch ${bi + 1}/${batches.length}: slides ${batch[0].no}-${batch.at(-1).no} (${text.length} chars)`)
  await ttsRequest(text, file)

  const { rate, pcm } = readWav(file)
  const total = pcm.length / 2 / rate
  const sils = silences(file)
  const lead = sils[0]?.start < 0.05 ? sils[0].end : 0
  const tail = sils.at(-1) && Math.abs(sils.at(-1).end - total) < 0.05 ? sils.at(-1).start : total
  const chars = batch.map(s => s.text.length)
  const sum = chars.reduce((a, b) => a + b, 0)
  let acc = 0
  const expected = chars.slice(0, -1).map(c => { acc += c; return lead + (tail - lead) * acc / sum })
  const inner = sils.filter(s => s.start > lead + 0.5 && s.end < tail - 0.5)
  let cuts = [], how = 'single slide'
  const heardText = []
  if (batch.length > 1) {
    try {
      const texts = await transcribeChunks(file, [lead, ...inner.map(s => s.mid), tail])
      const idx = partition(texts.map(spokenLength), batch.map(s => spokenLength(s.text)), inner.map(s => s.dur))
      cuts = idx.map(j => inner[j - 1])
      const bounds = [0, ...idx, texts.length]
      batch.forEach((_, i) => heardText.push(texts.slice(bounds[i], bounds[i + 1]).join('')))
      how = `matched transcript (${TRANSCRIBE_MODEL})`
    } catch (e) {
      console.warn(`  transcript alignment failed (${e.message}); using estimated boundaries`)
      cuts = pickCuts(inner, expected)
      how = 'estimated from character counts -- listen to the cuts'
    }
  }
  report.push(`batch ${bi + 1}: ${total.toFixed(1)}s audio, ${sum} chars, ${(sum / (tail - lead)).toFixed(2)} chars/s, boundaries ${how}`)
  batch.forEach((s, i) => {
    const t0 = i === 0 ? lead : cuts[i - 1].end - 0.1
    const t1 = i === batch.length - 1 ? tail : cuts[i].start + 0.15
    const a = Math.max(0, Math.round(t0 * rate)) * 2, b = Math.min(pcm.length / 2, Math.round(t1 * rate)) * 2
    writeWav(slideWav(s), pcm.subarray(a, b), rate)
    const len = t1 - t0
    const cps = s.text.length / len
    const gap = i < cuts.length ? `cut@${cuts[i].mid.toFixed(1)}s (silence ${cuts[i].dur.toFixed(2)}s)` : 'end'
    report.push(`  slide ${String(s.no).padStart(2)}: ${len.toFixed(1).padStart(5)}s ${cps.toFixed(2)} chars/s ${cps < 3 || cps > 7 ? '<-- CHECK' : ''} ${gap}`)
    if (heardText[i] !== undefined) {
      const flat = s.text.replace(/\s+/g, ' ')
      report.push(`      script: ${flat.slice(0, 24)} ... ${flat.slice(-16)}`)
      report.push(`      heard : ${heardText[i].slice(0, 24)} ... ${heardText[i].slice(-16)}`)
    }
  })
}
if (report.length) {
  fs.writeFileSync(path.join(outDir, 'split-report.txt'), report.join('\n') + '\n')
  console.log(report.join('\n'))
}

// ---------- 3. one clip per slide ----------
const duration = file => Number(execFileSync('ffprobe', ['-v', 'error', '-show_entries', 'format=duration', '-of', 'csv=p=0', file]).toString())
const srt = []
let t = 0
const fmt = sec => new Date(sec * 1000).toISOString().slice(11, 23).replace('.', ',')
const clips = []
for (const s of picked) {
  const png = path.join(pngDir, pngs[s.no - 1])
  const clip = path.join(clipDir, `${String(s.no).padStart(3, '0')}.mp4`)
  const audio = s.text ? slideWav(s) : null
  const len = Math.max(SECTION_MIN, (audio ? duration(audio) : 0) + PAD)
  const vf = 'scale=1920:1080:force_original_aspect_ratio=decrease,pad=1920:1080:(ow-iw)/2:(oh-ih)/2:white,format=yuv420p'
  const input = audio ? ['-i', audio] : ['-f', 'lavfi', '-i', 'anullsrc=r=24000:cl=mono']
  execFileSync('ffmpeg', ['-y', '-loglevel', 'error', '-loop', '1', '-framerate', '30', '-i', png, ...input,
    '-af', `apad=whole_dur=${len}`, '-t', String(len), '-vf', vf,
    '-c:v', 'libx264', '-tune', 'stillimage', '-preset', 'medium', '-crf', '20', '-r', '30',
    '-c:a', 'aac', '-b:a', '128k', '-ar', '48000', '-ac', '1', clip])
  if (s.text) srt.push(`${srt.length + 1}\n${fmt(t)} --> ${fmt(t + len - PAD)}\n${s.text}\n`)
  t += len
  clips.push(clip)
}

// ---------- 4. concat ----------
const suffix = range[0] === 1 && range[1] === slides.length ? '' : `-${range[0]}-${range[1]}`
const list = path.join(outDir, 'clips.txt')
fs.writeFileSync(list, clips.map(c => `file '${c.replace(/\\/g, '/')}'`).join('\n'))
const mp4 = path.join(outDir, `${name}${suffix}.mp4`)
execFileSync('ffmpeg', ['-y', '-loglevel', 'error', '-f', 'concat', '-safe', '0', '-i', list, '-c', 'copy', '-movflags', '+faststart', mp4])
fs.writeFileSync(mp4.replace(/\.mp4$/, '.srt'), srt.join('\n'))
console.log(`\ndone: ${mp4} (${(t / 60).toFixed(1)} min)`)

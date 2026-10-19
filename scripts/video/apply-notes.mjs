// Replace every slide note in a deck from a text file (blocks start with "@@ <slideNo>").
// usage: node scripts/video/apply-notes.mjs <deck.md> <notes.txt> [--dry]
// notes.txt: blocks starting with a line "@@ <slideNo>" ; body = note text
import fs from 'node:fs'
import { pathToFileURL } from 'node:url'
import path from 'node:path'
const { parseSync } = await import(pathToFileURL(path.resolve('node_modules/@slidev/parser/dist/index.mjs')).href)
const [deck, notesFile, dry] = process.argv.slice(2)
const src = fs.readFileSync(deck, 'utf8')
const md = parseSync(src, deck)
const notes = new Map()
let cur = null
for (const line of fs.readFileSync(notesFile, 'utf8').split(/\r?\n/)) {
  const m = line.match(/^@@ (\d+)\s*$/)
  if (m) { cur = +m[1]; notes.set(cur, []); continue }
  if (cur) notes.get(cur).push(line)
}
const n = md.slides.length
const missing = [...Array(n).keys()].map(i => i + 1).filter(i => !notes.has(i))
const extra = [...notes.keys()].filter(k => k > n)
if (missing.length || extra.length) { console.error(`slides=${n} missing=${missing} extra=${extra}`); process.exit(1) }
const lines = src.split('\n')
// process from last slide to first so line indices stay valid
for (const s of [...md.slides].reverse()) {
  const body = notes.get(s.index + 1).join('\n').trim()
  if (body.includes('-->')) { console.error(`slide ${s.index + 1}: note contains -->`); process.exit(1) }
  let seg = lines.slice(s.start, s.end).join('\n')
  const stripped = seg.replace(/\n*<!--(?:(?!<!--)[\s\S])*?-->\s*$/, '')
  if (s.note && stripped === seg) { console.error(`slide ${s.index + 1}: note not at end`); process.exit(1) }
  seg = stripped.replace(/\s+$/, '') + `\n\n<!--\n${body}\n-->\n`
  lines.splice(s.start, s.end - s.start, ...seg.split('\n'))
}
const out = lines.join('\n')
const check = parseSync(out, deck)
if (check.slides.length !== n) { console.error('slide count changed!'); process.exit(1) }
if (dry) console.log(`ok dry-run: ${n} slides`)
else { fs.writeFileSync(deck, out); console.log(`wrote ${n} notes to ${deck}`) }

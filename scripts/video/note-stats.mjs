// Print spoken length per slide of a notes text file ("@@ N" blocks); flags slides over budget.
// usage: node scripts/video/note-stats.mjs <notes.txt>
import fs from 'node:fs'
const blocks = fs.readFileSync(process.argv[2], 'utf8').split(/^@@ /m).slice(1)
let total = 0
const out = []
for (const b of blocks) {
  const [n, ...rest] = b.split('\n')
  const len = rest.filter(l => !l.startsWith('【講師】')).join('').replace(/\s|`/g, '').length
  total += len
  out.push(`${n.trim().padStart(3)}:${String(len).padStart(4)}${len > 380 ? '!' : ' '}`)
}
for (let i = 0; i < out.length; i += 8) console.log(out.slice(i, i + 8).join('  '))
console.log(`total ${total} chars ≈ ${(total / 5 / 60).toFixed(1)} min`)

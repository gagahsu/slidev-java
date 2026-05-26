import { readdirSync, mkdirSync, readFileSync, writeFileSync, rmSync } from 'fs'
import { execSync } from 'child_process'
import { basename, join } from 'path'
import { fileURLToPath } from 'url'
import { PDFDocument } from 'pdf-lib'

const root = fileURLToPath(new URL('..', import.meta.url))
const outDir = join(root, 'dist')

mkdirSync(outDir, { recursive: true })

const filter = process.argv[2] // e.g. "ch01"
const chapters = readdirSync(root)
  .filter(f => /^ch\d+.*\.md$/.test(f) && (!filter || f.startsWith(filter)))
  .sort()

console.log(`找到 ${chapters.length} 個章節：${chapters.join(', ')}\n`)

for (const file of chapters) {
  const name = basename(file, '.md')
  const pngBase = join(outDir, name)
  const pdfOutput = join(outDir, `${name}.pdf`)

  console.log(`▶ 匯出 ${file} → PNG...`)
  try {
    execSync(
      `pnpm slidev export "${file}" --format png --output "${pngBase}" --timeout 60000 --wait 2000`,
      { cwd: root, stdio: 'inherit' }
    )

    // Slidev outputs PNGs into a subdirectory named after the output base
    const pngDir = pngBase
    const pngs = readdirSync(pngDir)
      .filter(f => f.endsWith('.png'))
      .sort((a, b) => {
        const n = f => parseInt(f.match(/\d+/)?.[0] ?? '0', 10)
        return n(a) - n(b)
      })

    if (pngs.length === 0) {
      console.error(`✘ 找不到 PNG，跳過：${file}\n`)
      continue
    }

    console.log(`  合併 ${pngs.length} 張 PNG → PDF...`)
    const pdf = await PDFDocument.create()

    for (const pngFile of pngs) {
      const pngData = readFileSync(join(pngDir, pngFile))
      const img = await pdf.embedPng(pngData)
      const { width, height } = img
      const p = pdf.addPage([width, height])
      p.drawImage(img, { x: 0, y: 0, width, height })
    }

    writeFileSync(pdfOutput, await pdf.save())

    // clean up intermediate PNG directory
    rmSync(pngDir, { recursive: true })

    console.log(`✔ 完成 ${name}.pdf（${pngs.length} 頁）\n`)
  } catch (err) {
    console.error(`✘ 失敗：${file}\n`, err.message)
  }
}

console.log('全部完成！PDF 存放在 dist/ 資料夾。')

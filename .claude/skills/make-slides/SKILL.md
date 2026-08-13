---
name: make-slides
description: 根據指定的教材參考檔案，套用 _template/ 的樣式與排版規則，用 Slidev 製作完整的投影片。當使用者說「根據 [檔案] 製作投影片」、「幫我把 [教材] 做成投影片」、「用 [參考資料] 做 slides」時觸發。
---

# Make Slides

根據教材參考檔案，套用固定的樣式模板，自動產生 Slidev 投影片。

## 觸發時機

使用者提供一或多個參考檔案，並要求製作投影片時使用此 skill。例如：
- 「根據 @ch14.pdf 製作投影片」
- 「幫我把這份教材做成 slides」
- 「用 @教材.md 和 @補充.pdf 做投影片」

## 執行流程

### Step 1：讀取資料

1. 讀取所有指定的參考檔案，提取文字內容與章節結構
   - **Markdown / 純文字**：直接讀取
   - **PDF（文字型）**：用 PyPDF2 提取文字
     ```bash
     python -c "import PyPDF2, sys; sys.stdout.reconfigure(encoding='utf-8'); ..."
     ```
   - **PDF（圖片型）**：若 PyPDF2 提取結果為空，改用 pdftoppm 逐頁轉圖再視覺讀取：
     1. 用 pdftoppm 將每頁轉為 PPM，再用 ffmpeg 轉成 PNG：
        ```bash
        PDFTOPPM="$LOCALAPPDATA/Microsoft/WinGet/Packages/oschwartz10612.Poppler_Microsoft.Winget.Source_8wekyb3d8bbwe/poppler-25.07.0/Library/bin/pdftoppm.exe"
        FFMPEG="$LOCALAPPDATA/Microsoft/WinGet/Packages/yt-dlp.FFmpeg_Microsoft.Winget.Source_8wekyb3d8bbwe/ffmpeg-N-122319-gf6a95c7eb7-win64-gpl/bin/ffmpeg.exe"
        "$PDFTOPPM" -r 150 "input.pdf" "C:/Users/yunch/AppData/Local/Temp/slide_page"
        # 對每個 .ppm 執行：
        "$FFMPEG" -y -update 1 -i "slide_page-N.ppm" "slide_page-N.png"
        ```
     2. 用 Read tool 依序讀取每頁 PNG，視覺提取文字與章節結構
     3. 讀完後刪除暫存圖檔
2. 讀取 `_template/slides.md` 作為格式基準

### Step 2：確認檔名與 routeAlias

- 詢問使用者：新投影片的檔名（例如 `ch14-arrays`）
- 若使用者未指定，從參考檔名推導（例如 `ch14.pdf` → `ch14-arrays`）
- 最終路徑：`<根目錄>/<檔名>.md`（例如 `ch14-arrays.md`）
- routeAlias：取章節縮寫（例如 `ch14`）

### Step 3：產生投影片檔案

在根目錄建立 `<檔名>.md`，依照下方「排版規則」填入內容。

Frontmatter 需加入 `routeAlias`（供目錄頁連結使用）：

```yaml
routeAlias: ch14
```

封面頁需在最後加入返回目錄連結：

```html
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
```

### Step 4：整合到 index.md

**4-1 加入 src: import**

在 `index.md` 最末的 `src:` 區塊後面加入：

```markdown
---
src: ./<檔名>.md
---
```

**4-2 在目錄頁加入章節卡片**

在 `index.md` 的 `.chapter-grid` div 內，仿照現有卡片格式加入：

```html
    <Link to="ch14" class="chapter-card">
      <div class="chapter-num">Ch 14</div>
      <div><中文標題></div>
      <div class="chapter-subtitle"><英文副標題></div>
    </Link>
```

### Step 5：告知使用者後續步驟

完成後提示：

```bash
pnpm dev
```

開啟 http://localhost:3030，目錄頁會出現新章節卡片。

---

## Frontmatter 固定格式

每份投影片的開頭 frontmatter 必須完整複製，只修改 `title` 與 `routeAlias`：

```yaml
---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: <投影片標題>
routeAlias: <章節縮寫，例如 ch14>
style: |
  .slidev-layout p,
  .slidev-layout li,
  .slidev-layout td,
  .slidev-layout th,
  .slidev-layout div {
    font-size: max(16px, 1em);
  }
  table {
    width: 100%;
    margin: 1rem 0;
    border-collapse: collapse;
  }
  th, td {
    padding: 8px !important;
    border: 1px solid #e2e8f0 !important;
  }
  .index-table td {
    text-align: center;
    font-family: monospace;
  }
---
```

---

## 封面頁固定格式

封面 HTML 結構固定，只替換三個文字欄位，並在結尾加上返回目錄連結：

```html
<div class="flex flex-col justify-center items-center h-full" style="background: #ffffff;">
  <p style="color: #5eada0; font-size: 1rem; font-weight: 600; letter-spacing: 0.2em; text-transform: uppercase; margin-bottom: 1.2rem;">
    <課程或單元名稱（英文，如 Java Programming Masterclass）>
  </p>
  <h1 style="color: #1a5c5c; font-size: 3.8rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">
    <主標題（中文）>
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「<一句話副標題>」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>
```

---

## 排版規則

### 頁面類型

| 類型 | 使用時機 | Frontmatter |
| --- | --- | --- |
| 封面 | 第 1 張 | 無（使用 HTML） |
| Outline | 第 2 張 | `layout: default` |
| 章節分隔 | 每個大章節開始 | `layout: section` + `class: flex flex-col justify-center items-center text-center` |
| 內容頁 | 一般內容 | 無（預設） |
| 結尾 | 最後一張 | `layout: end` |

### 方法/API 說明頁（最重要的規則）

**一律用「表格 + 程式碼區塊」，禁止用條列式（bullet points）**

```markdown
# 頁面標題

| 方法名稱 | 說明 |
| --- | --- |
| `method(param)` | 功能說明 |

```java
// 程式碼範例
System.out.println(result);
```
```

**表格列數 ≥ 5 且搭配程式碼時，強制拆成兩張頁面：**

```markdown
# 頁面標題

| 方法名稱 | 說明 |
| --- | --- |
| ... | ... |  ← 5列以上

---

# 頁面標題 — 範例

```java
// 程式碼在這裡
```
```

### 補充說明（Callout）

```html
<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>標題：</b> 說明文字
</div>
```

### 程式碼區塊

- 一律標註語言：` ```java `、` ```python `、` ```typescript ` 等
- 禁止使用 two-cols layout（會導致 Markdown 表格解析錯誤）
- **行數上限**：純程式碼頁最多 **9 行**；同一頁有表格時最多 **5 行**
  - 超出上限時，將程式碼另起一張「— 範例」頁，不在同一頁硬塞

### Outline 頁

```markdown
---
layout: default
---

# Outline

- **主題一**
- **主題二**
- **實作練習**
```

Outline 條列直接寫主題名稱，不要加「第X部分」「Part N」或小節編號前綴；
section 分隔頁同樣只放主題標題（可選第二行英文副標），不要另起一行寫「第X部分」。

### 練習題頁

拆成兩張：「任務說明」與「解題提示」

```markdown
---
layout: default
---

# 練習 X：題目名稱
### 任務說明

題目描述...

---

# 練習 X：解題提示
### 提示說明

1. 步驟一
2. 步驟二
```

### 章節練習結構

- **每個小節結尾**：安排 1 題「程式實作練習」（任務說明＋解題提示兩頁，格式同上）
- **章節最後**（最後一個 Part 結束後，Q&A／結尾頁之前）：安排 1 題「綜合練習」，整合本章節各小節概念，難度高於各小節練習

---

## 內容產生原則

1. **完整涵蓋教材** — 不跳過任何章節，確保每個主題都有對應投影片
2. **每頁一個概念** — 不把過多內容塞進同一張投影片
3. **程式碼要有範例** — 只有表格沒有程式碼的頁面，必須補上對應的範例頁
4. **中文為主** — 內容用繁體中文，方法名稱保留英文原文
5. **不憑空發明** — 程式碼範例必須來自教材或正確的語言用法，不猜測
6. **基本概念優先** — API／方法整理頁排在基本語法概念之後，不放在章節開頭；學生要先看到「怎麼用」，再看到「有哪些方法」
7. **最簡範例開場** — 每個新概念的第一個程式碼範例從最簡單的用法開始（字面值、單一符號），確認概念後再帶入完整語法或複合情境
8. **練習題結構** — 依「章節練習結構」：每個小節 1 題練習，章節結尾 1 題綜合練習

---

## 完成後輸出

告知使用者：
1. 建立了哪個檔案（`<檔名>.md`）
2. `index.md` 做了哪兩處修改（src: import + 目錄卡片）
3. 投影片共幾張、涵蓋哪些章節
4. 執行：`pnpm dev`，開啟 http://localhost:3030 即可看到新章節
5. 若圖片型 PDF 轉圖失敗（pdftoppm 不可用），提示使用者安裝 Poppler：`winget install poppler`

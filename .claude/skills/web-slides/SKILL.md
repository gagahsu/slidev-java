---
name: web-slides
description: 自動上網搜尋指定主題的內容，並參考指定 md 檔的結構與風格，用 Slidev 製作完整的投影片。當使用者說「搜尋 [主題] 做成投影片」、「上網找 [主題] 的資料並參考 @xxx.md 的風格生成 slides」、「幫我研究 [主題] 然後做 slides」時觸發。
---

# Web Slides

自動上網搜尋指定主題的資料，套用參考 md 檔的結構與排版風格，產生 Slidev 投影片。

## 觸發時機

使用者指定一個主題（不是現有檔案）並要求製作投影片時使用此 skill。例如：
- 「搜尋 Java Interface 做成投影片，參考 @ch12-char-string.md 的風格」
- 「幫我研究 Python 裝飾器並做成 slides，樣式參考 @ch13-regex.md」
- 「上網找 React Hooks 的資料，照 @ch12-char-string.md 的結構生成投影片」

## 執行流程

### Step 1：解析使用者的需求

從使用者訊息中提取：
- **搜尋主題**：要製作投影片的技術主題（例如「Java Interface」、「Python 裝飾器」）
- **參考結構檔**：使用者指定的 md 檔（例如 `@ch12-char-string.md`），用來決定投影片結構與呈現風格
- **語言/框架限制**：若有指定（例如「Java 17+」、「不要用 Ch17 字眼」）

### Step 2：讀取參考 md 檔

讀取使用者指定的參考 md 檔，分析：
1. **投影片結構**：章節分割方式（有幾個 Part？每 Part 幾張）
2. **呈現慣例**：哪些用表格、哪些用程式碼、哪些用 Callout
3. **語調與用語**：繁體中文、命名方式、動漫/主題用語等
4. **練習題的安排方式**：任務說明 + 解題提示分兩張

### Step 3：上網搜尋主題內容

使用 WebSearch 搜尋主題的核心知識，策略如下：

#### 偏好來源清單（依優先順序）

本課程以 **JDK 17** 為標準，涵蓋 Java 8 至 Java 17 的所有可用特性。
搜尋結果中若出現以下網站，**優先 WebFetch**，可確保內容品質穩定一致：

| 優先級 | 網站 | 特性 |
| --- | --- | --- |
| ★★★ | `dev.java` | Oracle 官方現代 Java 學習平台，Java 8–17 特性最權威 |
| ★★★ | `baeldung.com` | 最高品質第三方教學，深度詳盡，範例完整 |
| ★★★ | `digitalocean.com/community/tutorials` | 結構清晰，入門到進階皆宜 |
| ★★★ | `geeksforgeeks.org` | 涵蓋廣，格式一致，有比較表 |
| ★★ | `docs.oracle.com` | 官方 Javadoc，方法簽名最精確 |
| ★★ | `programiz.com` | 範例最精簡，適合確認最小語法 |
| ★ | 其他（Medium、DZone 等）| 補充用，品質不穩定，次要參考 |

**搜尋時主動鎖定偏好來源（四個都要搜）：**

```
<主題> java site:dev.java
<主題> java site:baeldung.com
<主題> java site:digitalocean.com/community
<主題> java site:geeksforgeeks.org
```

**搜尋順序（依序執行，直到蒐集到足夠內容）：**

1. **dev.java** — 搜尋 `<主題> site:dev.java`，取得官方說明與 JDK 17 語法確認
2. **Baeldung** — 搜尋 `<主題> java site:baeldung.com`，取得深度解說與完整範例
3. **DigitalOcean** — 搜尋 `<主題> java site:digitalocean.com/community`，補充結構化教學
4. **GeeksforGeeks** — 搜尋 `<主題> java site:geeksforgeeks.org`，補充方法列表或比較表
5. **其他補充** — 上述找不到足夠內容時，才開放搜尋其他來源

**使用 WebFetch 深入閱讀：**
- **至少 fetch 2 個頁面**，優先從 dev.java 與 Baeldung 選取
- 重點抓取：語法定義、程式碼範例、使用時機、注意事項、常見錯誤
- 若 fetch 後發現內容不足，繼續搜尋下一個偏好來源

**蒐集目標（至少涵蓋以下面向）：**
- 概念定義與用途
- 基本語法（最簡單的完整範例）
- 進階語法（較複雜的完整範例）
- 常見方法或修飾詞（`default`、`static`、`sealed` 等）
- 與相關概念的比較（例如 interface vs abstract class）
- 實際應用場景
- 至少 2 個練習題的靈感

### Step 4：規劃投影片大綱

根據蒐集到的內容與參考 md 檔的結構，規劃大綱：

```
封面
Outline
第一部分：<基礎概念>
  - 定義與用途
  - 基本語法
  - 最簡範例
第二部分：<進階語法>
  - 修飾詞 / 特殊關鍵字
  - 方法種類（若有）
第三部分：<比較與應用>
  - 與相關概念比較
  - 實際應用場景
練習題（至少 2 題，難度遞增）
Q & A
結尾
```

若使用者有特殊要求（例如「內容不要用到 Ch17 字眼」），在規劃時一併排除。

### Step 5：確認檔名與 routeAlias

- 從主題名稱推導檔名，例如：「Java Interface」→ `ch17-interface`（但不在投影片內容出現）
- 詢問使用者確認或讓其修改
- routeAlias：取章節縮寫（例如 `ch17`）

### Step 6：產生投影片檔案

在根目錄建立 `<檔名>.md`，依照下方「排版規則」填入內容。

Frontmatter 需加入 `routeAlias`：

```yaml
routeAlias: ch17
```

封面頁結尾加入返回目錄連結：

```html
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
```

### Step 7：整合到 index.md

**7-1 加入 src: import**

在 `index.md` 最末的 `src:` 區塊後加入：

```markdown
---
src: ./<檔名>.md
---
```

**7-2 在目錄頁加入章節卡片**

在 `index.md` 的 `.chapter-grid` div 內加入：

```html
    <Link to="ch17" class="chapter-card">
      <div class="chapter-num">Ch 17</div>
      <div><中文標題></div>
      <div class="chapter-subtitle"><英文副標題></div>
    </Link>
```

### Step 8：告知使用者後續步驟

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
routeAlias: <章節縮寫，例如 ch17>
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

```html
<div class="flex flex-col justify-center items-center h-full" style="background: #ffffff;">
  <p style="color: #5eada0; font-size: 1rem; font-weight: 600; letter-spacing: 0.2em; text-transform: uppercase; margin-bottom: 1.2rem;">
    Java Programming Masterclass
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

| 語法元素 | 說明 |
| --- | --- |
| `keyword` | 功能說明 |

```java
// 程式碼範例
```
```

**表格列數 ≥ 5 且搭配程式碼時，強制拆成兩張頁面：**

```markdown
# 頁面標題

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

- 一律標註語言：` ```java `
- 禁止使用 two-cols layout（會導致 Markdown 表格解析錯誤）
- **行數上限**：純程式碼頁最多 **9 行**；同一頁有表格時最多 **5 行**
  - 超出上限時，將程式碼另起一張「— 範例」頁

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

---

## 內容產生原則

1. **以網路資料為主要來源** — 所有程式碼與概念必須來自實際搜尋到的內容，不憑空捏造
2. **完整涵蓋主題** — 不跳過搜尋到的重要面向，確保每個核心概念都有對應投影片
3. **每頁一個概念** — 不把過多內容塞進同一張投影片
4. **程式碼要有範例** — 只有表格沒有程式碼的頁面，必須補上對應的範例頁
5. **中文為主** — 內容用繁體中文，關鍵字與方法名稱保留英文原文
6. **基本概念優先** — API／方法整理頁排在基本語法概念之後，學生先看到「怎麼用」再看到「有哪些」
7. **最簡範例開場** — 每個新概念第一個程式碼從最簡單的用法開始，確認概念後再帶入完整情境
8. **遵守使用者限制** — 若使用者有特定要求（如「不要出現 Ch17 字眼」、「只限 Java 17」），全程嚴格遵守

---

## 與 make-slides 的差異

| 面向 | make-slides | web-slides |
| --- | --- | --- |
| 內容來源 | 使用者提供的本地檔案（md、pdf）| WebSearch + WebFetch 搜尋結果 |
| 參考結構 | 從 `_template/slides.md` 讀取格式 | 從使用者指定的 md 檔讀取結構與風格 |
| 適用時機 | 已有教材，需轉成投影片 | 無現有教材，要從網路研究後直接做成投影片 |

---

## 完成後輸出

告知使用者：
1. 搜尋了哪些關鍵字、參考了哪些來源
2. 建立了哪個檔案（`<檔名>.md`）
3. `index.md` 做了哪兩處修改（src: import + 目錄卡片）
4. 投影片共幾張、涵蓋哪些章節
5. 執行：`pnpm dev`，開啟 http://localhost:3030 即可看到新章節

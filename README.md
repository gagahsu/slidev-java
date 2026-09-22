# my-clas — Java 課程投影片

用 [Slidev](https://sli.dev) 做的 Java 課程投影片集，從開發環境安裝、語法基礎、物件導向，一路教到集合框架與 Stream/Lambda，單一入口（`index.md`）統一啟動全部章節。

## 課程內容

共 27 章基礎版投影片，另有 23 章對應的「進階／自學版」（`chNNadv`）供學生延伸自學，加上一份封裝觀念的特別篇 `demo-oop`。

| 章節 | 主題 | 章節 | 主題 |
| --- | --- | --- | --- |
| Ch 1 | 基本觀念 | Ch 15 | 繼承與多形 |
| Ch 2 | 開發環境安裝 | Ch 16 | Object 類別 |
| Ch 3 | Java 程式從零開始 | Ch 17 | 抽象類別 |
| Ch 4 | Java 語言基礎 | Ch 18 | 介面與多重繼承 |
| Ch 5 | 程式基本運算 | Ch 19 | 包裝類別 |
| Ch 6 | 程式流程控制 | Ch 20 | 設計套件 |
| Ch 7 | 迴圈控制 | Ch 21 | 程式異常的處理 |
| Ch 8 | 陣列 | Ch 22 | 多執行緒（自學） |
| Ch 9 | 類別與物件 | Ch 23 | 輸入與輸出（自學） |
| Ch 10 | 物件建構與封裝 | Ch 24 | 壓縮與解壓縮（自學） |
| Ch 11 | Math 和 Random 類別 | Ch 25 | 集合框架 |
| Ch 12 | 日期與時間的類別 | Ch 26 | Stream 與 Lambda |
| Ch 13 | 字元與字串類別 | Ch 27 | 全課程總複習 |
| Ch 14 | 正規表達式 | | |

每張投影片皆使用 `penguin` 主題，內建課堂練習與解題提示頁，教材以繁體中文撰寫，並搭配英文標題。

## 需求

- Node.js
- **pnpm**（本專案指定套件管理工具，非 npm/yarn）

## 安裝

```bash
pnpm install
```

`.npmrc` 已設定 `shamefully-hoist=true`，Slidev 所需依賴會正確 hoist，安裝後無需額外設定。

## 常用指令

```bash
pnpm dev              # 啟動單一 dev server，網址 localhost:3030，含全部章節
pnpm run ch02         # 只啟動 Ch02 環境安裝
pnpm run ch13         # 只啟動 Ch13 字元與字串
pnpm run ch14         # 只啟動 Ch14 正規表達式
pnpm build            # 建置到 dist/ 供部署
pnpm run export:all   # 匯出所有章節投影片為 dist/*.pdf（可帶參數 "ch14" 或 "14-25"）
```

## 專案結構

- `index.md` — 目錄頁（Portal），透過 `src:` 匯入所有章節投影片，是 `pnpm dev` 的進入點
- `ch<NN>-<slug>.md` — 各章節「基礎版」投影片（`ch01-java-intro.md` … `ch27-course-review.md`）
- `ch<NN>-<slug>-adv.md` — 對應章節「進階／自學版」投影片，`routeAlias` 為 `chNNadv`
- `demo-oop-encapsulation.md` — 特別篇（`routeAlias` 為 `demo-oop`）
- `public/img/<topic>/` — 章節截圖，從 root path 引用，例如 `<img src="/img/env/jdk-01-download.png">`
- `global-bottom.vue` — 每張投影片下方顯示頁碼（X/Y）的 Vue 元件
- `_template/` — 新增章節時複製的樣板（含 slides.md、global-bottom.vue、package.json、.npmrc）

所有投影片皆使用 `penguin` 主題。章節編號連續且從 1 開始，檔名編號、`routeAlias`、目錄頁卡片上的 `Ch N` 標示三者必須一致；若要重新編號，需同步修改檔名、`routeAlias:`、頁面中的 `<Link to="chNN">`、內文提及的章節數字（如「Ch 8」「第 4 章」），以及 `package.json` 對應的 scripts。

## 新增章節

1. 於 root 建立 `chXX-name.md`，frontmatter 加入 `routeAlias: chXX`，封面頁加入 `<Link to="home">← 返回目錄</Link>`
2. 於 `index.md` 結尾加入 `src: ./chXX-name.md`
3. 於 `index.md` 的 `.chapter-grid` 加入對應的 `<Link to="chXX" class="chapter-card">` 卡片
4. 執行 `pnpm dev` 即可預覽，不需額外安裝

## Slidev 慣例

- 導覽一律用 `routeAlias` 搭配 Slidev 的 `<Link>` 元件，不用 `<RouterLink>` 或 `<a href>`
- 各投影片檔的 frontmatter YAML 控制主題、全域 CSS 與投影片預設值；個別投影片用 `layout:`（如 `section`、`two-cols`、`cover`）設定版型
- 漸進顯示用 `v-click` / `v-clicks`
- 自訂樣式寫在 frontmatter 的 `style:` 區塊內，沒有獨立的 CSS 檔案
- Tailwind 工具類別（`flex`、`mt-6`、`bg-blue-50` 等）可直接在投影片 Markdown 中使用

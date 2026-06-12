---
name: split-basic-advanced
description: 依照 @course-basic-advanced-plan.md 的拆分原則，將現有章節投影片拆成「基礎版」與「進階／自學版」兩個獨立檔案；plan 中列出但現有投影片缺漏的進階內容，套用 make-slides 排版規則與講稿風格補齊。當使用者說「依照拆分計畫把 chXX 拆成基礎和進階」、「ch21 整章列自學，幫我搬到進階版」、「把全部章節依拆分計畫重編」時觸發。
---

# Split Basic / Advanced

依照 `course-basic-advanced-plan.md` 的拆分原則，把現有 `chXX-xxx.md` 拆成「基礎版」（原檔，精簡）與「進階／自學版」（新檔），缺漏的進階知識點依 make-slides 規範補齊。

## 觸發時機

- 「依照 @course-basic-advanced-plan.md 把 ch12 拆成基礎版和進階版」
- 「ch21 多執行緒整章列自學，幫我搬去進階版」
- 「把 ch24 的進階內容（LinkedList/TreeSet/TreeMap 比較等）搬到自學版」
- 「依拆分計畫，把全部章節重編成基礎/進階兩版」

## 前置讀取（每次執行必讀）

1. `@course-basic-advanced-plan.md` — 拆分依據，**第三節表格是唯一判準**
2. 目標 `chXX-xxx.md` — 現有投影片全文
3. `index.md` — 目錄頁結構
4. 排版／frontmatter／封面規範：沿用 `.claude/skills/make-slides/SKILL.md` 的「Frontmatter 固定格式」「封面頁固定格式」「排版規則」「內容產生原則」，本 skill 不重複定義
5. 講稿風格：**全面改用古古 Persona** —— 不論基礎版保留頁、進階版移入頁、或新增頁，講稿一律依 `C:\Users\yunch\.claude\skills\_shared\gugu-persona.md` 的「講稿結構模板」重新撰寫，並套用本檔下方「講稿風格：古古 Persona（Java 補充）」。原有 `<!--` 講稿僅作為內容素材參考（保留其中正確的類比/重點），文字與結構全面改寫，不沿用原講稿的標頭格式

## 命名與路由規則

| 項目 | 基礎版 | 進階／自學版 |
| --- | --- | --- |
| 檔名 | 原檔不變 `chXX-xxx.md` | 新檔 `chXX-xxx-adv.md` |
| routeAlias | 不變 | 原 routeAlias + `adv`（例：`ch24` → `ch24adv`；`interface` → `interfaceadv`） |
| title | 不變 | 原 title +「（進階／自學）」 |
| 封面副標題 | 不變 | 加註「進階自學內容」 |

## 講稿風格：古古 Persona（Java 補充）

> 共用規範：`C:\Users\yunch\.claude\skills\_shared\gugu-persona.md`（身份設定、講稿結構模板、教學哲學）。以下為本 skill（Java 全章節）專屬補充。

### 生活類比清單

依主題選用貼近生活的比喻，不限於此清單：
- 封裝／存取修飾詞 → 保險箱、抽屜鎖
- 集合框架 → 收納盒、置物櫃
- 繼承／多型 → 樂高積木、模具與翻模
- 介面 → 遙控器、插座規格
- 例外處理 → 滅火器、保險機制
- 多執行緒 → 多窗口服務台
- Stream／Lambda → 自動化生產線、流水線加工

### 用詞規範

**中英夾雜原則：**
- 技術名詞保留英文：class、method、interface、exception、Stream、Lambda、Thread
- 首次出現附中文對應：interface（介面）、exception（例外）、Stream（串流）
- 方法／關鍵字保留英文並用 `` ` `` 標示：`override`、`throw`、`extends`

**常用詞彙（優先選用）：**

| 優先用詞 | 避免用詞 |
| --- | --- |
| 實作 | 實現 |
| 回傳 | 返回 |
| 覆寫 | 覆蓋 |
| 拋出 | 丟出 |
| 實例 | 實體 |
| 觸發 | 呼叫（當說「被觸發」時） |

## 執行流程

### Step 1：依 plan 第三節判斷章節類型

- **A. 整章自學**：ch21（Thread）、ch22（IO）、ch23（Zip）→ 走 Step 2A
- **B. 有進階內容清單**：ch03–ch20、ch24、ch25（含 ch13 特例）→ 走 Step 2B
- **C. plan 未列出進階內容**（ch01、ch02）→ 告知使用者此章無需拆分，結束

---

### Step 2A：整章自學（ch21 / ch22 / ch23）

1. 讀取整份 `chXX-xxx.md`
2. 建立 `chXX-xxx-adv.md`：複製封面之外的**全部內容**（投影片正文不改），frontmatter/封面依「命名與路由規則」調整；每頁講稿依「講稿風格：古古 Persona」重新撰寫
3. 將 `chXX-xxx.md` 精簡為：
   - 封面（不變）
   - 1 張「這是什麼／用在哪裡／為何列自學」說明頁（依 plan 對該章的描述撰寫，例如 ch21 標明「多執行緒對零基礎學生負擔較重，非寫出基本程式的必要條件」）
   - 一個連到 `<原routeAlias>adv` 的 `<Link>`，文案如「→ 前往自學內容」
   - `layout: end` 結尾
4. 說明頁講稿依古古 Persona 撰寫，並提示老師：「口頭帶過即可，詳細內容留給自學版」

---

### Step 2B：一般章節拆分（ch03–ch20、ch24、ch25）

1. 列出 `chXX-xxx.md` 所有投影片頁標題（含所屬 Part）
2. 對照 plan 第三節此章節的「進階／選讀內容」項目，逐項找出對應頁面
   - 同一主題的「方法整理表 + 範例頁」視為一組一起歸類
   - 練習題：若練習題依賴進階語法/API，整題（任務說明＋解題提示兩頁）移至進階版；否則留基礎版
   - **ch13 特例**：核心清單＝字元類別、量詞、分組、`matches`/`split`/`replaceAll`（依拆分原則第 3 點）；plan 表格列出的其餘項目（反向引用、具名分組、環視斷言、Pattern/Matcher 類別細節、MULTILINE/DOTALL、Predicate 整合、splitAsStream、動態取代結合 Stream）全部歸進階
3. 輸出分類結果給使用者確認：

```
@chXX-xxx.md 拆分計畫（依 course-basic-advanced-plan.md 第三節）

基礎版保留（共 N 頁）
  - 封面、Outline、...（依序列出標題）

移至進階版（共 M 頁）
  - 「頁面標題」 → 對應 plan 項目「XXX」

plan 列出但現有投影片未涵蓋（需新增，共 K 項）
  - 項目名稱 → 將新增至進階版，插入位置：<相鄰主題>

是否依此拆分？(全部 / 調整 / 取消)
```

4. **等待使用者確認**，不直接修改檔案

---

### Step 3：產生兩個檔案

**基礎版 `chXX-xxx.md`**
- 移除已分類為「移至進階版」的頁面
- Outline 頁移除對應主題列項
- 投影片正文不改寫；保留下來的每頁講稿依「講稿風格：古古 Persona」重新撰寫
- 套用 make-slides「章節練習結構」：每個小節結尾若無練習則補 1 題；章節最後（Q&A／結尾頁之前）補 1 題綜合練習，整合本檔保留下來的各小節概念

**進階版 `chXX-xxx-adv.md`**
- frontmatter／封面套用 make-slides 固定格式（title／routeAlias／封面副標題依「命名與路由規則」調整）
- 第 2 頁加 Outline（列出本檔涵蓋的進階主題）
- 依序放入：
  1. 從基礎版移入的頁面（投影片正文保留原內容，講稿依「講稿風格：古古 Persona」重新撰寫）
  2. plan 列出但缺漏的項目 → 依 make-slides「排版規則」（表格＋程式碼、列數 ≥5 拆頁、程式碼行數上限 9/5、最簡範例開場）新增投影片，講稿依「講稿風格：古古 Persona」撰寫
- 結尾 `layout: end`
- 套用 make-slides「章節練習結構」：每個小節結尾 1 題練習；章節最後補 1 題綜合練習，整合本檔（進階主題）各小節概念

---

### Step 4：更新 index.md

1. 在 `src:` 區塊加入：
   ```markdown
   ---
   src: ./chXX-xxx-adv.md
   ---
   ```
2. 若 `index.md` 尚未有進階卡片樣式，於 `<style>` 區塊新增（僅第一次執行時加入一次）：
   ```css
   .chapter-card-adv {
     border-color: #e0a96d;
   }
   .chapter-card-adv .chapter-badge {
     color: #c97b2c;
     font-size: 0.75rem;
     font-weight: 700;
     letter-spacing: 0.1em;
   }
   ```
3. 在 `.chapter-grid`（或新增一個「進階／自學內容」grid，放在主 grid 之後）加入卡片：
   ```html
   <Link to="chXXadv" class="chapter-card chapter-card-adv">
     <div class="chapter-num">Ch XX</div>
     <div class="chapter-badge">進階・自學</div>
     <div><原中文標題></div>
     <div class="chapter-subtitle"><英文副標題> (Advanced)</div>
   </Link>
   ```
4. 整章自學章節（ch21–23）：原卡片標題加註「（自學）」，連結仍指向基礎版（簡介＋導覽頁）

---

### Step 5：批量模式

使用者要求「全部章節」時，依 plan 第三節表格逐章執行：
- ch21 → ch22 → ch23（Step 2A）
- ch03 → ch20、ch24 → ch25（Step 2B，逐章先輸出 Step 2B-3 的分類結果並各自等待確認，再產生檔案）

每章完成後輸出簡要結果，全部跑完後統一回報總覽。

---

## 完成後輸出

1. 處理了哪個章節、產生/更新了哪些檔案
2. 移到進階版的頁面清單（對應 plan 項目名）
3. 新增補齊的項目清單（含插入位置）
4. `index.md` 的改動（src import + 卡片 + 樣式）
5. 執行 `pnpm dev`，開啟 http://localhost:3030 預覽基礎版與進階版

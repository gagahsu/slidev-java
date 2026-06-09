---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: 前端介紹
routeAlias: ch01
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

<div class="flex flex-col justify-center items-center h-full" style="background: #ffffff;">
  <p style="color: #5eada0; font-size: 1rem; font-weight: 600; letter-spacing: 0.2em; text-transform: uppercase; margin-bottom: 1.2rem;">
    Frontend Development Fundamentals
  </p>
  <h1 style="color: #1a5c5c; font-size: 3.8rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">
    前端介紹
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「大眾媒介 - 網頁」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
大家好，歡迎來到前端開發的第一堂課！今天我們要來聊聊什麼是「前端」。

【為什麼要學這個？】
現在每個人每天都在用手機滑網頁、點 App，但你有沒有想過，這些畫面到底是怎麼出現在你眼前的？學會前端，你就是那個「打造數位世界門面」的人。

【今天學完你會能做什麼】
今天結束後，你會對網頁的運作邏輯有個清晰的架構，並且知道 HTML、CSS 和 TypeScript 到底在扮演什麼角色，以及你該如何開始你的學習之旅。
-->

---
layout: default
---

# Outline

- **大眾媒介 - 網頁：前後端與資料庫的關係**
- **何謂前端？**
- **前端三大技術：HTML、CSS/SCSS、TypeScript**
- **各技術介紹**
- **學習路線建議**

<!--
【核心說明】
這堂課我們分五個部分。首先會先看網頁在網路世界的大圖導覽，接著定義什麼是前端。

【程式世界怎麼用】
我們會深入淺出地介紹前端的「黃金三角」：HTML、CSS 和 TypeScript。最後，我會給各位一份學習地圖，讓你們知道第一步該踩在哪裡。

💼 業界實務：
在公司裡，這五點通常也是新人訓練（Onboarding）會提到的基本認知，搞懂這些，跟工程師溝通就不會雞同鴨講。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 大眾媒介
# 網頁的世界

<!--
【開場白】
在進入程式碼之前，我們先把視野拉高。

【為什麼要學這個？】
了解網頁在「大眾媒介」中的定位，能幫你理解為什麼前端在現今的商業環境中這麼重要。
-->

---

# 大眾媒介 - 網頁 (Web)

網頁能在各種不同裝置上顯示介面，其背後是由三大核心組成的資料流程。

<div class="grid grid-cols-3 gap-4 mt-8 text-sm">
  <div class="p-3 bg-blue-50 rounded shadow-sm">
    <b>前端 Frontend</b><br>
    • 服務回傳的資料呈現<br>
    • 最直接的顯示介面<br>
    • 使用者操作的裝置
  </div>
  <div class="p-3 bg-gray-50 rounded shadow-sm">
    <b>後端 Backend</b><br>
    • 提供網頁服務<br>
    • 接收資料並處理<br>
    • 連接各資料庫
  </div>
  <div class="p-3 bg-green-50 rounded shadow-sm">
    <b>資料庫 Database</b><br>
    • 儲存使用者資訊<br>
    • 增刪查改資料<br>
    • 紀錄資訊
  </div>
</div>

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>資料流：</b> 前端接收資料並傳遞 → 後端處理完資料並回傳 → 前端顯示給使用者
</div>

<!--
【核心說明】
我們把整個網頁服務看成一間餐廳。

【生活化比喻】
前端就是「外場服務生」和「精美的菜單」，是你直接接觸到的東西。後端則是「廚房的廚師」，負責處理食材（資料）。資料庫就是「冷凍庫和倉庫」，存放所有食物和紀錄。

【程式世界怎麼用】
當你點餐（發出請求），服務生（前端）把單子傳給廚師（後端），廚師去冰箱（資料庫）拿材料，做成料理後再由服務生端到你面前（顯示資料）。

⚠️ 學生常見誤解：
初學者常以為網頁就是全部。其實你在瀏覽器看到的只是冰山一角，背後還有後端和資料庫在支撐。
-->

---

# 前端與後端的互動

- **前端 (Frontend)**：
  - 顯示使用者選用任一服務時所呈現的資料
  - 使用者操作的裝置（Phone / PC / Pad / Watch 等）
- **後端 (Backend)**：
  - 接收前端的服務請求並傳回相對應的資料
  - 與資料庫連接，進行資料的增加、刪除、查詢與修改

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>重點：</b> 多種大小的裝置皆不同，但網頁能在各種不同介面提供服務。網頁服務分為前端、後端兩部分。
</div>

<!--
【核心說明】
這裡我們強調「載體」的概念。

【生活化比喻】
就像電視節目可以出現在電視機、手機螢幕甚至是公車站的廣告看板上。內容是一樣的，但呈現的方式（前端）會根據螢幕大小調整。

【程式世界怎麼用】
前端工程師的工作，就是確保無論使用者用什麼裝置打開網頁，都能順利發送請求給後端，並且讓後端回傳的資料能漂亮地排版。

💼 業界實務：
以前前後端可能是一個人寫（全端），但現在專業分工很細，前端會花很多心思在「響應式設計（RWD）」，讓網頁在手機和電腦上都好用。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 何謂前端？
# What is Frontend?

<!--
【開場白】
接下來，我們要針對「前端」這個角色做更深入的定義。
-->

---

# 何謂前端？

前端主要在提供的介面上顯示內容針對使用者。

| 面向 | 說明 |
| --- | --- |
| **多樣載體** | Phone / PC / Pad / Watch 等 |
| **開發語法** | Swift (iOS)、JAVA (Android)、Angular (跨平台) |
| **設計概念** | 每個顯示的頁面都經過 UI / UX 設計與對應功能連結 |
| **核心目標** | 讓使用者在啟用服務時能夠有良好的體驗 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>前端的目的：</b> 不必讓使用者面對死板板的程式碼，而是提供友善的操作介面。
</div>

<!--
【核心說明】
前端的本質就是「人機互動的橋樑」。

【生活化比喻】
想像你去自動提款機領錢，你看到的是按鈕和螢幕提示（前端），而不是背後的電路板和複雜的銀行會計系統（後端）。

【程式世界怎麼用】
我們開發時會考慮 UI（長得漂不漂亮）和 UX（用起來順不順手）。雖然我們學的是網頁技術，但現在很多技術（如 Angular）可以做到跨平台，一次開發就能在不同裝置上跑。

⚠️ 學生常見誤解：
以為前端只是畫圖。其實前端也要寫很多邏輯，像是「按了這個鈕要跳出什麼警告」、「輸入框沒填資料不能送出」等等。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 前端的建築師們
# HTML、CSS/SCSS、TypeScript

<!--
【開場白】
如果要蓋一棟名為「網頁」的房子，我們需要三種不同的建材。
-->

---

# 前端三大技術概覽

| 技術 | 比喻 | 特點 |
| --- | --- | --- |
| **HTML** | 網頁的骨架 | 不算程式語言，屬樣板語言；需硬記語法，邏輯需求不高 |
| **SCSS / CSS** | 網頁的衣服 | 負責排版、顏色；HTML 結構相關知識；輸出全靠死背！ |
| **TS (TypeScript)** | 網頁的動作 | 考驗邏輯能力，比 JavaScript 嚴謹；好的邏輯架構如魚得水 |

<!--
【核心說明】
這三位就是前端開發的靈魂人物。

【生活化比喻】
想像一個人類：HTML 是骨骼，決定你有幾隻手、幾隻腳；CSS 是衣服和裝容，讓你變美變帥；TypeScript 則是肌肉和大腦，決定你怎麼走路、怎麼說話。

【程式世界怎麼用】
雖然這張投影片說 CSS 用死背的，但其實它是很有層次感的設計。而 TypeScript 則是目前業界的標配，它讓你的程式碼像有了一個嚴格的管家，幫你檢查錯誤。

💼 業界實務：
以前大家只學 JavaScript，但現在為了讓大型專案更好維護，幾乎所有公司都改用 TypeScript 了。
-->

---

# HTML - 網頁的骨架

負責內容結構，不算程式語言，屬於樣板語言。

- 透過 `<link>` 引入 `style.css`，透過 `<script>` 引入 `main.js`

```html
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <button id="greet-btn">打招呼</button>
  <p id="message"></p>
  <script src="main.js"></script>
</body>
</html>
```

<!--
【帶讀程式碼前的鋪陳】
我們來看一段最基本的 HTML 程式碼，這就是網頁的雛形。

【逐步解說】
你看這裡，`button` 標籤就像是在房子裡放了一個按鈕，`p` 標籤則是預留了一個放文字的位子。目前這間房子還沒有裝潢（CSS），也沒有通電（JavaScript）。

【類比說明】
這就像是買了預售屋，你看得到梁柱在哪，但還不能住進去。

⚠️ 學生常見誤解：
標籤的成對出現很重要！很多學生會忘記寫結束標籤（例如忘了寫 `</button>`），這會導致房子結構崩塌。
-->

---

# CSS / SCSS - 網頁的衣服

負責排版、顏色、字型與響應式設計 (RWD)。

- 針對 HTML 中的 `#greet-btn` 套上顏色、間距與圓角

```css
/* style.css */
#greet-btn {
  background-color: #5eada0;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}
```

<!--
【帶讀程式碼前的鋪陳】
現在我們幫剛才那個死板板的按鈕穿上衣服。

【逐步解說】
注意這個 `#greet-btn`，這是在跟 HTML 說：「欸，那個叫 greet-btn 的人過來，我要幫你換衣服了」。我們給它一個漂亮的底色（background-color），再加上一點圓角（border-radius），按鈕瞬間就變現代了。

【類比說明】
這就像是室內設計師進場，決定牆壁顏色、地板材質和家具擺放。

💼 業界實務：
雖然直接寫 CSS 可以，但業界更愛用 SCSS，因為它讓 CSS 可以像寫程式一樣有變數、可以巢狀撰寫，管理起來方便多了。
-->

---

# TypeScript - 網頁的動作

負責互動與邏輯，讓頁面能回應使用者的操作。

- 監聽 `#greet-btn` 的點擊事件，點擊後更新 `#message` 的文字

```javascript
// main.js
const btn = document.getElementById('greet-btn');
const msg = document.getElementById('message');

btn.addEventListener('click', () => {
  msg.textContent = '你好！歡迎來到前端的世界！';
});
```

<!--
【帶讀程式碼前的鋪陳】
最後，我們讓按鈕「活起來」。

【逐步解說】
我們在 TypeScript 裡先抓到那個按鈕和文字區塊。然後我們設定一個「監聽器」（addEventListener），就像是在按鈕旁放一個保全，只要有人點它（'click'），就執行後面的動作：把訊息內容改成「你好！」。

【類比說明】
這就像是裝上電燈開關，當你按下（動作），電燈就亮了（回應）。

⚠️ 學生常見誤解：
很多學生會搞混 ID 名稱。如果 HTML 叫 `greet-btn`，這邊寫成 `greet_btn`（底線），那這個功能就死掉了。大小寫和符號一定要完全一致。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 學習路線建議
# Learning Path

<!--
【開場白】
知道了技術是什麼，接下來要告訴大家「怎麼學」才不會迷路。
-->

---

# 學習路線建議

正確的學習順序能讓你事半功倍：

<div class="flex flex-col items-center gap-4 mt-6">
  <div class="text-xl font-bold text-teal-700">
    HTML ➔ CSS ➔ JavaScript ➔ 框架（Angular / React / Vue）
  </div>
</div>

| 階段 | 技術 | 原因 |
| --- | --- | --- |
| **第一步** | HTML | 所有框架的基礎架構 |
| **第二步** | CSS / SCSS | 讓頁面有樣式 |
| **第三步** | JavaScript / TypeScript | 加入互動與邏輯 |
| **第四步** | 框架（Angular 等） | 基於前三者延伸 |

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
💡 <b>為什麼要先學 HTML？</b> 因為市面上所有的框架都是基於 HTML 的架構去做延伸，HTML 就是三大框架的基礎。
</div>

<!--
【核心說明】
這就是前端開發者的進化之路。

【生活化比喻】
就像學煮菜，你得先認識食材（HTML），學會調味（CSS），練習火侯與刀工（JavaScript），最後才去學怎麼開一家高級連鎖餐廳（框架）。

⚠️ 學生常見誤解：
很多人看到 Angular 很紅就直接衝進去學。結果發現裡面一堆 HTML 標籤和 CSS 語法看不懂，挫折感超重。請務必先打好地基。

💼 業界實務：
在面試時，資深主管通常會問你基礎的 JavaScript 題目，而不是只問框架。因為框架會變，但基礎技術十年如一日。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 實作練習
# Lab

<!--
【開場白】
看了這麼多投影片，現在是時候親手做做看了！
-->

---
layout: default
---

# 練習：打招呼頁面
### 任務說明

建立一個由三個檔案組成的簡單網頁：`index.html`、`style.css`、`main.js`

**需求：**
1. HTML：包含一個輸入框（`<input>`）、一個按鈕（`<button>`）和一個顯示訊息的段落（`<p>`）
2. CSS：為按鈕加上背景色（`#5eada0`）、圓角、white 文字顏色與 hover 效果
3. JavaScript：點擊按鈕後，讀取輸入框的值，在段落顯示 `"你好，[名字]！歡迎來到前端世界！"`

**預期效果：**
- 輸入「小明」→ 按鈕 → 顯示「你好，小明！歡迎來到前端世界！」
- 若輸入框為空，顯示「請輸入你的名字！」

<!--
【練習導引】
這是你的第一個完整前端作品！把 HTML、CSS、JS 三個檔案放在同一個資料夾，用瀏覽器開啟 index.html 就能看到結果。

【關鍵提示】
1. 記得在 HTML 裡用 `<link>` 引入 CSS，用 `<script>` 引入 JS。
2. JS 裡用 `document.getElementById('id名稱')` 抓到元素。
3. 用 `.value` 讀取 input 的值，用 `.textContent` 設定 p 的文字。
-->

---

# 練習：解題提示

**index.html 骨架：**
```html
<input id="name-input" placeholder="請輸入你的名字">
<button id="greet-btn">打招呼</button>
<p id="message"></p>
<link rel="stylesheet" href="style.css">
<script src="main.js"></script>
```

**style.css 重點：**
```css
#greet-btn:hover { opacity: 0.85; transform: scale(1.02); }
```

**main.js 重點：**
```javascript
const btn   = document.getElementById('greet-btn');
const input = document.getElementById('name-input');
const msg   = document.getElementById('message');
btn.addEventListener('click', () => {
  const name = input.value.trim();
  msg.textContent = name
    ? `你好，${name}！歡迎來到前端世界！`
    : '請輸入你的名字！';
});
```

<!--
【解說要點】
注意 input.value.trim() 去除前後空白，這是很好的習慣。
三個反引號（template literal）讓字串拼接更清晰。
-->

---
layout: end
---

# 介紹結束
### 準備好開始你的前端之旅了嗎？

<!--
【結語】
好啦，關於前端的初步介紹就到這邊。

【互動引導】
剛才提到的 HTML、CSS、TypeScript 比喻，大家有沒有哪一個覺得還是有點抽象的？或者，你最想先看到哪一個部分的成果？

【等待與觀察】
（給學生 10 秒鐘思考，可以請一兩位分享他們對前端的第一印象）

下一堂課，我們就要動手寫下你的第一行 HTML 囉！
-->

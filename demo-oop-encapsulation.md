---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
transition: slide-left
title: 物件導向入門與封裝 — AI 協作試教
routeAlias: demo-oop
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
    Java Programming × AI Collaboration
  </p>
  <h1 style="color: #1a5c5c; font-size: 3.4rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">
    物件導向入門與封裝
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「先讓程式壞掉，再學會怎麼保護它」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
大家好，今天這 20 分鐘，我們要學 Java 裡面最重要的觀念之一：物件導向和封裝。

【為什麼要學這個？】
你之後看到的每一行 Java 程式，幾乎都活在「類別」裡面。而封裝，是讓你的程式不會被別人（或三個月後的自己）弄壞的第一道防線。

【今天學完你會能做什麼】
學完之後，你會知道怎麼把資料「包」進物件裡保護起來，而且——我們今天會請 AI 當我們的助教，你也會學到怎麼用 AI 幫自己學程式。

【時間提示】此頁 30 秒內帶過，快速進 Outline。
-->

---
layout: default
---

# Outline

- **物件是什麼？** — 資料 + 行為
- **一個沒有防護的世界** — 現場破壞一個銀行帳戶
- **封裝** — 把資料鎖起來，只留安全的門
- **AI 協作驗收** — 讓 AI 現場出題考大家

<!--
【核心說明】
今天的流程很簡單：先認識物件，然後我會故意寫一段「有漏洞」的程式，讓大家親手把它弄壞。弄壞之後，我們再請 AI 幫我們一起修好它。

【互動預告】
等一下有兩個環節需要大家出主意：一次是當駭客攻擊程式，一次是回答 AI 出的題目。

【時間提示】30 秒。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 物件是什麼？

<!--
【開場白】
我們先從一個問題開始：你的手機裡有一個「聯絡人」App，每一筆聯絡人有什麼？

【引導】
有名字、有電話、可以撥號、可以傳訊息。「名字和電話」是資料，「撥號和傳訊息」是動作。把資料和動作綁在一起，就是物件。

【時間提示】20 秒，口頭帶過就翻頁。
-->

---

# 物件 = 資料 + 行為

| 概念 | 白話說法 | 例子（手機聯絡人） |
| --- | --- | --- |
| 類別 `class` | 設計圖、模具 | 「聯絡人」的欄位格式 |
| 物件 `object` | 照設計圖做出來的實體 | 你手機裡的某一筆聯絡人 |
| 欄位 `field` | 物件記住的資料 | 姓名、電話 |
| 方法 `method` | 物件會做的動作 | 撥號、傳訊息 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>一句話記住：</b> 類別是「聯絡人的欄位格式」，物件是「手機裡真正存的那一筆聯絡人」。
</div>

<!--
【核心說明】
類別和物件的關係，是初學者第一個卡關點。類別只是一張設計圖，它本身不能動；物件才是照著設計圖做出來、真的存在記憶體裡的東西。

【生活化比喻】
聯絡人的欄位格式只有一種（姓名、電話），但手機裡可以存一百筆聯絡人。每一筆的欄位一樣（同一個類別），但姓名、電話都不同（每個物件的資料不同）；每一筆都能撥號、傳訊息（這就是方法，物件會做的動作）。

【程式世界怎麼用】
下一頁馬上會看到：「Contact 類別」定義聯絡人有什麼；你新增的每一筆聯絡人，就是一個 Contact 物件。

⚠️ 學生常見誤解：
以為寫完 class 程式就會動。要提醒：class 只是定義，要用 new 做出物件才算數。

【時間提示】1 分鐘。
-->

---

# 最簡範例：從類別做出物件

```java
public class Contact {
    String name;   // 資料：姓名
    String phone;  // 資料：電話
}

Contact c = new Contact();  // 在通訊錄裡新增一筆聯絡人
c.name = "小美";
c.phone = "0912345678";
```

<!--
【帶讀程式碼前的鋪陳】
我們來看今天第一段程式碼，只做兩件事：定義一筆聯絡人的欄位格式，然後真的新增一筆聯絡人。

【逐步解說】
你看前面四行，這是格式：聯絡人有姓名、有電話。注意這裡它還不能動。第 6 行的 new，才是真正「新增一筆聯絡人」的瞬間。新增之後，第 7、8 行我們把資料填進去。

【類比說明】
new Contact() 就是在通訊錄裡按下「新增聯絡人」。c 是我們幫這筆聯絡人取的代號，之後要找它就叫 c。

⚠️ 學生常見誤解：
c 不是物件本身，是「遙控器」（參考）。這裡先不深入，有人問再說。

【時間提示】1 分鐘。到這裡累計約 3 分鐘，準備進入重頭戲。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 一個沒有防護的世界

### 🔓 接下來，請大家當駭客

<!--
【開場白】
剛剛那段程式看起來沒什麼問題對不對？接下來我要寫一個銀行帳戶，然後——我要請大家想辦法把它弄壞。

【氣氛營造】
放心，弄壞程式不會怎麼樣，這正是工程師每天在做的事：先想「這會怎麼被弄壞」，才知道要怎麼保護。

【時間提示】15 秒，翻頁。
-->

---

# 這個銀行帳戶，哪裡有問題？

```java
public class BankAccount {
    public String owner;   // 誰都摸得到
    public int balance;    // 誰都改得動
}

BankAccount acc = new BankAccount();
acc.owner = "小明";
acc.balance = 1000;       // 目前看起來很正常...
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>關鍵字：</b> <code>public</code> 的意思是「公開」——任何程式都可以直接讀取、直接修改。
</div>

<!--
【帶讀程式碼前的鋪陳】
這是一個銀行帳戶類別，有戶名、有餘額，下面我們開了一個帳戶、存了一千塊。看起來人畜無害。

【逐步解說】
注意第 2、3 行開頭的 public。這個字的意思是：這個欄位對「所有人」開放。任何其他程式，都可以直接伸手進來讀、直接伸手進來改。

【問題引導】
現在請大家想一下：如果你是壞人，你拿到 acc 這個物件，你會對它做什麼？給大家 20 秒，講得出來的等一下就交給 AI 執行。

【等待與觀察】
常見答案：把餘額改成 0、改成負數、把戶名改掉、把餘額改成一億。都收下來，挑一兩個進 AI demo。

【時間提示】1.5 分鐘（含收集答案）。
-->

---
layout: default
---

# 🎬 AI 協作時刻 ①：執行攻擊

### 現場把大家的「攻擊點子」丟給 AI

**要用的 Prompt（貼上爛 code 之後）：**

> 這是我們課堂上的 BankAccount 類別。請寫一段 main 方法，
> 示範外部程式可以怎麼「破壞」這個帳戶的資料
> （例如：{學生的點子}），每一行加上註解說明破壞了什麼。
> 只能用目前出現過的語法，程式不超過 8 行。

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>觀察重點：</b> AI 可以即時把「學生的想法」變成可執行的程式——這是傳統教材做不到的。
</div>

<!--
【操作提示 — 這頁是現場 demo，切到 AI 視窗】
把上一頁的爛 code 貼進 AI，套用畫面上的 prompt，把 {學生的點子} 換成剛剛收集到的答案，例如「把餘額改成 -1000000」。

【解說要點】
AI 生出攻擊程式後，帶大家看關鍵一行：acc.balance = -1000000; 然後問：「銀行有擋你嗎？」——沒有。因為 public 就是不設防。

【收斂一句話】
資料公開，等於把保險箱的門拆掉。問題不是「會不會有人亂改」，是「隨時都可以被亂改」。

⚠️ 備案：
若網路或 AI 卡住，切到附錄裡準備好的「備援攻擊程式」，照樣講。

【時間提示】2.5 分鐘。到這裡累計約 8 分鐘。
-->

---
layout: default
---

# 🎬 AI 協作時刻 ②：讓 AI 引導我們找解法

### 不直接給答案，用「蘇格拉底模式」

**要用的 Prompt：**

> 你是一位只會提問、不給答案的 Java 助教。
> 針對剛剛這個 BankAccount 的問題，
> 請用一次一個問題的方式，引導初學者自己想到：
> 「欄位不該公開，修改應該經過檢查」。
> 每個問題不超過 30 個字。

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>給學生的訊息：</b> AI 不是只會給答案的抄襲工具——你可以要求它「只給提示」，陪你思考。
</div>

<!--
【操作提示 — 現場 demo】
接續同一個 AI 對話，貼上這個 prompt。AI 會開始提問，例如「如果你是銀行，你希望客戶可以直接改資料庫裡的餘額嗎？」把問題丟回給學生答，答完再讓 AI 問下一題，來回 2 輪就好，不要拖。學生回答後，把答案轉述給 AI：「學生的回答是：___，請繼續。」

【解說要點】
重點不是 AI 多聰明，是示範「學生自己在家也能這樣用」：叫 AI 不要給答案，只給提示。這招直接回應「用 AI 會不會讓學生不思考」的疑慮。

【收斂一句話】
好，大家已經自己推出結論了：資料要藏起來，改資料要經過檢查。這件事在 Java 裡有個正式名字——封裝。翻頁。

【時間提示】2 分鐘。累計約 10 分鐘，剛好過半。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 封裝 Encapsulation

### 💊 把資料鎖起來，只留安全的門

<!--
【核心比喻 — 這段用講的】
封裝的英文 Encapsulation，字根就是 capsule，膠囊。就像手機的聯絡人 App：你不能直接打開資料庫改欄位（資料被包起來），只能透過 App 的「編輯聯絡人」畫面來改（透過安全的方式使用）。

【第二個比喻備用】
或者想：你不能自己伸手到電信公司的資料庫改門號資料（private），只能透過官方 App 的「修改資料」功能（public 方法），而 App 會檢查你填的號碼格式對不對。

【時間提示】30 秒。
-->

---

# 封裝的兩個動作

| 動作 | 語法 | 白話說法 |
| --- | --- | --- |
| ① 把資料鎖起來 | `private` 欄位 | 藥粉包進膠囊，外面摸不到 |
| ② 開安全的門 | `public` 方法 | 要存錢？走「存款窗口」，有行員檢查 |

```java
public class BankAccount {
    private int balance;        // 🔒 上鎖！外部碰不到

    public int getBalance() {   // 🚪 只能「看」，不能改
        return balance;
    }
}
```

<!--
【核心說明】
封裝就兩個動作：第一，欄位從 public 改成 private，意思是「只有這個類別自己碰得到」。第二，開幾個 public 的方法當窗口，外面的人只能走窗口。

【逐步解說】
你看第 2 行，balance 前面現在是 private。剛剛那行攻擊程式 acc.balance = -1000000 現在會直接編譯錯誤——連執行都不用，寫的當下就被擋掉。第 4 行的 getBalance 是我們開的第一個窗口：你可以查餘額，但只能看。

【類比說明】
private 就是把金庫上鎖，getBalance 就是櫃檯玻璃——看得到、摸不到。

⚠️ 學生常見誤解：
以為 private 是「加密」或「隱藏看不見」。要澄清：private 是「存取權限」，是編譯器在擋，不是資料被藏起來。

【時間提示】1.5 分鐘。
-->

---

# 開一扇「會檢查」的門

```java
public void deposit(int amount) {
    if (amount <= 0) {
        System.out.println("存款金額必須大於 0！");
        return;                 // 🚫 不合理，拒絕往來
    }
    balance += amount;          // ✅ 通過檢查，才准修改
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>這就是封裝的價值：</b> 修改資料的「唯一通道」上站了一個警衛，不合理的值進不來。
</div>

<!--
【帶讀程式碼前的鋪陳】
剛剛的門只能看，現在我們開一扇可以「存錢」的門，但這扇門有警衛。

【逐步解說】
deposit 是存款。你看第 2 行，進門先檢查：金額小於等於 0？直接請你離開（return）。只有通過檢查，第 6 行才會真的把錢加上去。想把餘額改成負的？門口就被擋下了。

【類比說明】
就像夜店門口的保鑣：想進來，先檢查。以前 public 欄位的年代是根本沒有門，牆都是打通的。

💼 業界實務：
真實系統裡，幾乎所有欄位都是 private。你未來看任何 Java 專案的 code，會發現這是鐵律，不是選配。

【練習引導】
等一下 AI 出題的時候，會考大家「哪些操作會被擋下來」，先有心理準備。

【時間提示】1.5 分鐘。
-->

---
layout: default
---

# 🎬 AI 協作時刻 ③：見證攻擊失效

### 讓 AI 重新執行剛剛的攻擊

**要用的 Prompt：**

> 這是加上封裝之後的 BankAccount（貼上新版）。
> 請重新執行剛剛那段攻擊程式，
> 告訴我們每一行攻擊的下場是什麼，
> 用「攻擊 → 結果」的表格呈現，結果請標明是
> 「編譯錯誤」還是「被驗證擋下」。

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>期待看到：</b> <code>acc.balance = -1000000</code> → ❌ 編譯錯誤；<code>acc.deposit(-500)</code> → 🚫 被警衛擋下。
</div>

<!--
【操作提示 — 現場 demo】
把附錄裡「封裝後完整版」的類別貼給 AI，用畫面上的 prompt。AI 會列出對照表：直接改欄位 → 編譯錯誤；用 deposit 塞負數 → 被 if 擋下。

【解說要點】
帶大家對照剛剛的攻擊清單，一條一條看「現在攻得進去嗎」。前後對比是這堂課的高潮，語氣可以嗨一點：「十分鐘前大家還能把小明改成負債一百萬，現在呢？」

【收斂一句話】
同一批攻擊、同一個帳戶，只差兩個字：private。這就是封裝。

【時間提示】2 分鐘。累計約 15.5 分鐘。
-->

---
layout: default
---

# 🎬 AI 協作時刻 ④：AI 現場出題驗收

**要用的 Prompt：**

> 根據今天教的內容（類別與物件、public/private、
> getter、帶驗證的方法），出 3 題選擇題，難度由淺到深。
> 一次只出一題，等我回答後才公布答案，
> 答錯時不要直接講答案，先給一個提示。

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>學生帶回家的技能：</b> 這個 prompt 你可以自己用——每學完一個章節，叫 AI 考你。
</div>

<!--
【操作提示 — 現場 demo】
時間充裕做 2 題，緊張就做 1 題。請學生舉手選答案，把多數決貼給 AI。

【解說要點】
如果有人答錯，正好展示 prompt 最後一句的效果：AI 不會直接公布答案，會先給提示——再次呼應「AI 可以被設定成陪你思考」。

【時間提示】2.5 分鐘。累計約 18 分鐘。
-->

---

# 帶回家：你的 AI 學習提問模板

| 情境 | 對 AI 說 |
| --- | --- |
| 聽不懂概念 | 「用『我的興趣』的例子解釋 ___，100 字以內」 |
| 程式報錯 | 「不要給答案，一步步提問引導我找出錯在哪」 |
| 想確認學會了 | 「出 3 題考我 ___，答錯先給提示不給答案」 |
| 資訊太多 | 「只用我目前學過的語法解釋，不要提到繼承」 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>心法：</b> AI 是 24 小時待命、永遠不會不耐煩的助教——但要由「你」來當主導的人。
</div>

<!--
【核心說明】
今天所有 AI 環節用到的招式，都整理在這張表。這四句話就是初學者最需要的四個場景。

【收斂】
今天的重點兩句話：第一，封裝 = private 鎖資料 + public 方法當有警衛的門。第二，AI 用對方法，是幫你思考，不是替你思考。

【時間提示】1.5 分鐘。累計約 19.5 分鐘。
-->

---
layout: end
---

# 謝謝大家

### 有問題嗎？我們可以現場問 AI 🙋

<div class="mt-6 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left" style="max-width: 560px; margin-left: auto; margin-right: auto;">
📚 <b>想學完整版？</b> 本章概念的完整教學：<b>Ch 9 類別與物件</b>、<b>Ch 10 物件建構與封裝</b>
</div>

<!--
【結尾】
留 30 秒緩衝。若有提問且時間允許，可以把問題現場丟給 AI，再多展示一次協作——把 Q&A 本身變成 demo 的一部分。

【若沒有提問】
可以自問自答一題：「有人會問：那 setter 是什麼？其實剛剛的 deposit 就是一種進化版的 setter——這是下一堂課的預告。」
-->

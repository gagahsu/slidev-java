---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 程式流程控制
routeAlias: ch05
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
  <p style="color: #5eada0; font-size: 1rem; font-weight: 600; letter-spacing: 0.2em; text-transform: uppercase; margin-bottom: 1.2rem;">Java Programming Masterclass</p>
  <h1 style="color: #1a5c5c; font-size: 3.4rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">程式流程控制</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「讓程式學會做決定：if、switch 與條件判斷」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
嘿！各位未來的建築師們，大家好。上一章我們學會了加減乘除，但程式如果只會算術，那跟算盤沒兩樣。今天我們要教程式「思考」。這章的主題是「流程控制」，也就是讓你的程式知道，什麼時候該轉彎，什麼時候該直走，什麼時候該報警（誤）。

【為什麼要學這個？】
這就像是在玩《大富翁》。如果你走到「機會」格，你要抽一張牌；如果你走到「監獄」格，你要坐牢。程式也是一樣，我們得設定好各種規矩，它才不會像個無頭蒼蠅一樣亂撞。

【今天學完你會能做什麼】
學完這章，你就能寫出一個會自動打分數、會看生肖，甚至還能幫你決定今天要不要加班的「人生導師」程式。
-->

---
layout: default
---

# Outline

- **5-1 if 敘述**：if / if-else / if-else if-else 鏈、三元運算子 `? :`
- **5-2 switch 敘述**：傳統 switch、switch 搭配字串
- **5-3 專題實作**：BMI 計算、生肖判斷、火箭升空倒數
- **練習題**：2 題（任務說明 + 解題提示各一張）

<!--
【核心說明】
這章的重點就是兩個字：「分支」。我們先把基本的判斷工具練熟，未來想看 switch 更新潮的寫法，可以參考進階自學內容。

【生活化比喻】
想像我們站在人生的十字路口。往左是去當工程師，往右是去送外送（誤）。if 就像是那個紅綠燈，switch 就像是那個圓環，有多個出口讓你挑。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 5-1 if 敘述

<!--
【開場白】
讓我們先從最簡單、最常用，也最容易讓大家寫程式時加班的 if 開始講起。
-->

---
layout: default
---

# if 敘述的三種形式

| 形式 | 適用時機 |
| --- | --- |
| `if` | 條件為真才執行，否則跳過 |
| `if-else` | 二擇一：條件真或假各有一段邏輯 |
| `if-else if-else` 鏈 | 多條件依序判斷，第一個成立的分支執行後離開 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>執行規則：</b>if-else if-else 鏈中，一旦某個條件為 true，其餘條件全部跳過，即使後面也可能成立。
</div>

<!--
【核心說明】
if 系列就像是我們的保險條款，看條件成立與否，決定要走哪一條路。

【生活化比喻】
單純 if：如果有買保險，住院就給錢。if-else：如果及格就放暑假，不及格就去重修。if-else if-else：如果有 100 萬就買賓士，有 50 萬買豐田，都沒錢就騎腳踏車——一層一層往下篩選。

⚠️ 易錯點提醒：
在 if-else if 鏈裡，Java 是一個「懶惰的判官」。只要第一個條件成立了，後面的它連看都不看。所以順序非常重要！
-->

---

# if 與 if-else 語法

```java
// 單純 if：只有條件為真才執行
int score = 85;
if (score >= 60) {
    System.out.println("及格");
}

// if-else：二選一
if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}
```

<!--
【範例目的】
這個範例要示範 if 跟 if-else 最基本的寫法。

【帶讀關鍵行】
看 if 後面的括號，裡面一定要放「布林值」（true/false）。如果 `score >= 60` 是 true，就會印出「及格」；if-else 則是再多補一條路，讓不成立的情況也有對應的處理。

⚠️ 易錯點提醒：
大括號 {} 在只有一行時可以省略，但建議大家「永遠寫上大括號」。不然之後多加一行程式碼，卻發現它沒被包進條件裡，這種 Bug 通常會讓人抓很久。

【預期結果】
score = 85，符合 `score >= 60`，所以印出「及格」。
-->

---

# if-else if-else 鏈語法

```java
int score = 72;

if (score >= 90) {
    System.out.println("優秀");
} else if (score >= 80) {
    System.out.println("良好");
} else if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>建議：</b>條件由「最嚴格」到「最寬鬆」排列，避免邏輯被提前攔截。
</div>

<!--
【核心說明】
這叫「分級制度」：把條件由嚴到寬排好，讓每個分數都落在正確的級別。

【生活化比喻】
這就像是面試篩選。如果把門檻最低的條件放在最前面，那原本應該拿到「優秀」的人，可能一進門就被歸到「及格」然後直接結束，後面的機會就沒了。所以條件要從「最嚴格」開始寫。

💼 業界實務：
條件由嚴到寬排列，是 if-else if 鏈最常見的設計慣例，也是我們閱讀別人程式碼時，最該優先檢查的順序問題。
-->

---

# 三元運算子 `? :`

| 語法 | 說明 |
| --- | --- |
| `條件 ? 值A : 值B` | 條件為 true 回傳值A，否則回傳值B |
| 可巢狀使用 | `a ? b : (c ? d : e)`，但建議避免超過兩層 |

```java
int a = 10, b = 20;

// 等同 if-else，但可直接指派給變數
int max = (a > b) ? a : b;
System.out.println(max); // 20

// 搭配字串輸出
String result = (a > b) ? "a 較大" : "b 較大";
System.out.println(result); // b 較大
```

<!--
【核心說明】
三元運算子是 if-else 的「精簡版」，把一個判斷壓縮成一行，直接產生一個值。

【生活化比喻】
這就像是問答比賽：題目？答案 A：答案 B。對應到 `a > b ? "a 較大" : "b 較大"`，念起來就像在問「a 比 b 大嗎？是的話回答 a 較大，不是就回答 b 較大」。

💼 業界實務：
這在做簡單的賦值或字串拼接時很好用。但如果邏輯一複雜，硬塞成三元運算子只會讓程式碼變難讀，這時候還是乖乖用 if-else比較好。
-->

---

# 三元運算子 vs if-else 對比

| 比較 | 三元運算子 | if-else |
| --- | --- | --- |
| 程式碼行數 | 1 行 | 4 行以上 |
| 可賦值給變數 | 可以 | 需另設變數 |
| 適合複雜邏輯 | 不適合 | 適合 |
| 可讀性 | 簡短條件佳 | 複雜邏輯更清晰 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>使用原則：</b>邏輯簡單、單行可表達時用三元運算子；邏輯複雜或有多個步驟時用 if-else。
</div>

<!--
【核心說明】
這是一場「精簡」與「清楚」的取捨。

【生活化比喻】
三元運算子就像是「快顯視窗」，一眼看完；if-else 就像是「完整報告」，每個情況都寫得清清楚楚。邏輯簡單就用三元，邏輯複雜請務必用 if-else——程式碼是寫給人看的，不是寫來炫耀的。
-->

---
layout: default
---

# 練習 1：判斷三角形是否合法
### 任務說明

請撰寫一個 Java 程式，輸入三個正整數代表三角形的三個邊長 `a`、`b`、`c`，判斷它們是否能構成一個三角形：

**三角形成立條件：** 任意兩邊之和必須大於第三邊（`a + b > c` 且 `a + c > b` 且 `b + c > a`）

**要求：**
1. 使用 `if-else` 判斷是否成立，成立印出「可以構成三角形」，否則印出「無法構成三角形」
2. 額外使用三元運算子，計算並印出三邊中的最大值

**輸入範例：** `a = 3, b = 4, c = 5`
**輸出範例：** `可以構成三角形`、`最大邊：5`

<!--
【任務鋪陳】
這個練習把 if-else 跟三元運算子放在同一題裡。三角形判斷需要同時檢查三個條件，適合用 if-else 處理；找最大值則是「兩個值比大小」的經典情境，正好適合三元運算子。

【問題引導】
想一想：三角形的三個條件之間是「都要成立」還是「成立一個就好」？這跟我們上一章學的邏輯運算子有什麼關係？另外，要找三個數的最大值，三元運算子要怎麼巢狀使用？
-->

---
layout: default
---

# 練習 1：解題提示

### 提示說明

1. 三個條件必須**同時成立**，用 `&&` 串接：`a + b > c && a + c > b && b + c > a`
2. 用 `if-else` 包住這個條件式，分別印出對應訊息
3. 最大值用巢狀三元運算子：先比較 `a` 和 `b`，再跟 `c` 比較

```java
int a = 3, b = 4, c = 5;

if (a + b > c && a + c > b && b + c > a) {
    System.out.println("可以構成三角形");
} else {
    System.out.println("無法構成三角形");
}

int max = (a > b) ? (a > c ? a : c) : (b > c ? b : c);
System.out.println("最大邊：" + max);
```

<!--
【逐步解說】
三角形條件那一行，把上一章學到的邏輯運算子 `&&` 直接用上了——三個比較運算式必須**全部為 true**，三角形才成立，這跟上一章「三科成績都要及格才算及格」的邏輯是一樣的道理。

巢狀三元運算子的部分，可以拆開來看：先問「a 比 b 大嗎？」，如果是，再問「a 比 c 大嗎？」；如果一開始 a 不比 b 大，就改成問「b 比 c 大嗎？」。雖然寫成一行，但邏輯跟巢狀的 if-else 完全一樣。

⚠️ 易錯點提醒：
巢狀三元運算子雖然精簡，但超過兩層就會變得很難讀。像這題已經是兩層的極限了，如果還要再加更多比較，建議改回 if-else，這也呼應了上一頁「三元運算子 vs if-else 對比」的使用原則。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 5-2 switch 敘述

<!--
【開場白】
接下來講 switch。如果 if 是二選一，那 switch 就是「選單模式」。適合用在一個變數有多種可能固定值的時候。
-->

---
layout: default
---

# 傳統 switch 語法結構

| 元素 | 說明 |
| --- | --- |
| `switch(運算式)` | 運算式型別：`byte`、`short`、`int`、`char`、`String`、`enum` |
| `case 值:` | 符合值才執行，使用冒號 `:` |
| `break` | 跳出 switch，不加會發生 fall-through |
| `default:` | 所有 case 都不符合時執行，選填 |

```java
int day = 3;
switch (day) {
    case 1: System.out.println("星期一"); break;
    case 2: System.out.println("星期二"); break;
    case 3: System.out.println("星期三"); break;
    default: System.out.println("其他天");
}
```

<!--
【核心說明】
switch 就像是一個「分選機」：把運算式的值跟每個 case 比對，符合就執行那一段，全部不符合就交給 default。

【生活化比喻】
這就像是郵局的分信窗口，依信件上的地址（運算式的值）丟到對應的分類格（case）。

⚠️ 易錯點提醒：
那個 `break`！它是 switch 的靈魂。少了 break，程式會像煞車失靈的火車一樣，衝過第一站、第二站，直到撞到最後一個車站為止。這叫 fall-through（貫穿）。
-->

---

# Fall-through 效果

不加 `break` 時，程式會「貫穿」往下執行所有 case：

```java
int day = 2;
switch (day) {
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
        System.out.println("工作日"); break;
    case 6:
    case 7:
        System.out.println("假日"); break;
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>善用 fall-through：</b>多個 case 共用同一段邏輯時，可以省略中間的 break，讓程式自然貫穿。
</div>

<!--
【核心說明】
雖然忘記寫 break 通常是 Bug，但有時候我們會故意不寫，讓它發揮作用。

【生活化比喻】
這就像是我們要判斷「工作日」。不論是週一到週五，結果都一樣，所以讓它一路「貫穿」下去，最後再放一個 break 攔住它，程式碼就會簡潔很多。

💼 業界實務：
這種「多個 case 共用同一段邏輯」的寫法，在處理分組情境（例如把月份歸類成季節）時很常見，能避免重複貼上同一段程式碼。
-->

---

# switch 搭配字串

Java 7 開始，`switch` 支援 `String` 型別（比較大小寫敏感）：

```java
String season = "春";

switch (season) {
    case "春": System.out.println("Spring"); break;
    case "夏": System.out.println("Summer"); break;
    case "秋": System.out.println("Autumn"); break;
    case "冬": System.out.println("Winter"); break;
    default:   System.out.println("未知季節");
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意大小寫：</b>字串比較區分大小寫。若輸入來自使用者，建議先呼叫 <code>.toLowerCase()</code> 或 <code>.toUpperCase()</code> 統一格式。
</div>

<!--
【核心說明】
Java 7 之後，switch 終於學會看文字了，可以直接拿字串來比對。

⚠️ 易錯點提醒：
字串比較是「大小寫敏感」的。如果輸入 "SPRING" 但 case 是 "spring"，會直接跑去 default，而不是比對成功。

💼 業界實務：
為了避免這種狀況，我們通常會先呼叫 `.toLowerCase()` 統一格式，免得使用者亂打大小寫讓程式跑到 default。
-->

---
layout: default
---

# 練習 2：星期幾的中文名稱
### 任務說明

請撰寫一個 Java 程式，輸入一個整數 `1~7` 代表星期幾，使用 `switch` 輸出對應的中文名稱：

| 數字 | 中文 |
| --- | --- |
| 1~5 | 平日（顯示「平日，記得上班/上課」） |
| 6、7 | 假日（顯示「假日，好好休息」） |
| 其他 | 顯示「輸入錯誤」 |

**要求：**
1. 使用傳統 `switch`，搭配 `fall-through` 讓 1~5 共用同一段輸出
2. 6、7 共用另一段輸出
3. 其他數字交給 `default` 處理

<!--
【任務鋪陳】
這個練習要把 switch 的兩個重點兜在一起：fall-through（多個 case 共用邏輯）跟 default（兜底情況）。情境改成「平日 / 假日」分類，跟剛才月份歸季節的概念是一樣的。

【問題引導】
想一想：1 到 5 的 case 要怎麼排列，才能讓它們「貫穿」到同一段輸出？6、7 又要怎麼安排？如果輸入 0 或 8，會跑到哪一個分支？
-->

---
layout: default
---

# 練習 2：解題提示

### 提示說明

1. `case 1:` 到 `case 5:` 疊在一起，最後接「平日」的輸出與 `break`
2. `case 6:` 與 `case 7:` 疊在一起，接「假日」的輸出與 `break`
3. `default:` 印出「輸入錯誤」

```java
int day = 3;
switch (day) {
    case 1: case 2: case 3: case 4: case 5:
        System.out.println("平日，記得上班/上課"); break;
    case 6: case 7:
        System.out.println("假日，好好休息"); break;
    default:
        System.out.println("輸入錯誤");
}
```

<!--
【逐步解說】
這題的寫法跟剛剛 fall-through 那一頁的範例幾乎一樣，只是把「工作日／假日」換成「平日／假日」的問候語。重點還是同一個：`case 1:` 到 `case 4:` 後面都沒有任何程式碼，所以會一路貫穿到 `case 5:` 才真正執行 `println`。

⚠️ 易錯點提醒：
如果忘記加 `default`，遇到 0 或 8 這種不合法的輸入，switch 會什麼都不做，使用者完全看不到任何提示，這在實務上是很不友善的設計，務必養成寫 `default` 的習慣。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 5-3 專題實作

<!--
【開場白】
學了這麼多招式，如果不拿來實戰，那就只是紙上談兵。讓我們來看看剛才學的流程控制，在現實世界怎麼用。
-->

---
layout: default
---

# 專題一：BMI 計算

BMI（身體質量指數）= 體重（kg）÷ 身高²（m²）

| BMI 範圍 | 等級 |
| --- | --- |
| BMI < 18.5 | 體重過輕 |
| 18.5 ≤ BMI < 24 | 體重正常 |
| 24 ≤ BMI < 27 | 體重過重 |
| BMI ≥ 27 | 肥胖 |

<!--
【核心說明】
這是 if-else if 的經典應用。算完 BMI 後，我們要依範圍給出評價，記得身高要先換算成公尺。

【生活化比喻】
這就像體檢報告上的判讀區間：算出一個數值後，看它落在哪一段，就對應到哪一種結果。
-->

---

# 專題一：BMI 程式碼

```java
double weight = 70;  // 公斤
double height = 1.75; // 公尺

double bmi = weight / (height * height);
System.out.printf("BMI = %.2f%n", bmi);

if (bmi < 18.5) {
    System.out.println("體重過輕");
} else if (bmi < 24) {
    System.out.println("體重正常");
} else if (bmi < 27) {
    System.out.println("體重過重");
} else {
    System.out.println("肥胖");
}
```

<!--
【範例目的】
這個範例示範如何把計算結果（BMI 數值）套進 if-else if 鏈，轉成對應的文字評價。

【帶讀關鍵行】
先看 `bmi < 18.5`，再看 `bmi < 24`。因為用的是 `else if`，一旦前面的條件成立就會結束，不會繼續往下檢查。

⚠️ 易錯點提醒：
身高一定要先換算成公尺再計算，不然算出來的 BMI 會差一個數量級，整個判斷都會跟著錯。

【預期結果】
weight = 70、height = 1.75，算出 BMI 約 22.86，落在「體重正常」區間。
-->


---

# 專題二：生肖判斷

中國生肖依年份除以 12 的餘數判斷：

| 餘數 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 生肖 | 猴 | 雞 | 狗 | 豬 | 鼠 | 牛 | 虎 | 兔 | 龍 | 蛇 | 馬 | 羊 |

<!--
【核心說明】
利用除以 12 的餘數來算出對應的生肖。這裡很適合用 switch，因為情況是固定的 12 種，跟「選單模式」剛好對得上。
-->

---

# 專題二：生肖程式碼（switch expression）

```java
int year = 2024;
int r = year % 12;

String zodiac = switch (r) {
    case 0  -> "猴";
    case 1  -> "雞";
    case 2  -> "狗";
    case 3  -> "豬";
    case 4  -> "鼠";
    case 5  -> "牛";
    case 6  -> "虎";
    case 7  -> "兔";
    case 8  -> "龍";
    case 9  -> "蛇";
    case 10 -> "馬";
    default -> "羊";
};
System.out.println(year + " 年是 " + zodiac + " 年");
```

<!--
【範例目的】
這裡用了 `case 值 ->` 這種箭頭寫法，直接把結果指派給變數，不需要寫 break。

【帶讀關鍵行】
看 `String zodiac = switch (r) { ... };`，每個 case 後面接 `->` 跟對應的生肖名稱，最後用分號結尾。

⚠️ 易錯點提醒：
這種箭頭寫法跟前面教的 `case 值: ... break;` 不一樣，兩者不能混用。完整的箭頭語法規則，可以參考進階自學內容的 Switch Expression。

【預期結果】
2024 年除以 12 餘 8，對應到「龍」，印出「2024 年是 龍 年」。
-->

---

# 專題三：火箭升空倒數

結合迴圈與 if 敘述，實作倒數 + 特定秒數提示：

```java
for (int i = 10; i >= 0; i--) {
    if (i == 0) {
        System.out.println("🚀 點火！升空！");
    } else if (i <= 3) {
        System.out.println(i + "... 準備！");
    } else {
        System.out.println(i + "...");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>觀念複習：</b>for 迴圈中的 if-else if-else 鏈，每次迭代都會根據 <code>i</code> 的值選擇對應的分支執行。
</div>

<!--
【範例目的】
這個範例結合迴圈與 if-else if，示範同一段邏輯如何在不同時間點做出不同反應。

【帶讀關鍵行】
看 `i == 0`、`i <= 3` 這兩個條件：倒數到 0 秒時點火，3 秒以內顯示「準備」，其餘時間正常倒數。

⚠️ 易錯點提醒：
迴圈每次跑完都會「重新」進行一次 if 判斷，不是只判斷一次就套用到所有迴圈。

【預期結果】
從 10 開始倒數，10~4 印出「N...」，3~1 印出「N... 準備！」，最後 0 印出「🚀 點火！升空！」。
-->

---
layout: default
---

# 練習 3：成績等第轉換
### 任務說明

請撰寫一個 Java 程式，接收一個整數分數（0–100），依下列規則輸出等第：

| 分數範圍 | 等第 |
| --- | --- |
| 90 ~ 100 | A |
| 80 ~ 89 | B |
| 70 ~ 79 | C |
| 60 ~ 69 | D |
| 0 ~ 59 | F |

**輸入範例：** `score = 83`  
**輸出範例：** `等第：B`

<!--
【任務鋪陳】
剛才我們學了 if-else if-else 鏈，現在來練習一個經典應用：把分數轉成等第，就像老師批改考卷時做的事。

【問題引導】
想一想：如果條件的順序顛倒，先判斷最寬鬆的範圍，會發生什麼事？這跟我們剛才提到「最嚴格排在最前面」的原則有什麼關係？
-->

---
layout: default
---

# 練習 3：解題提示

### 提示說明

1. 使用 `if-else if-else` 鏈，從最高分段往下判斷。
2. 條件由嚴到寬：先判斷 `>= 90`，再 `>= 80`，依序往下。
3. 最後 `else` 涵蓋所有 59 分以下的情形（等第 F）。

```java
// 參考結構（填入正確條件）
int score = 83;
String grade;
if (score >= 90) {
    grade = "A";
} else if (______) {
    grade = "B";
} // ... 繼續補完
System.out.println("等第：" + grade);
```

<!--
【逐步解說】
從高分段開始寫起就對了，依序往下補完每個 `else if`。最後那個 `else` 是所有人的「保底」等第，涵蓋所有沒被前面攔到的情況。
-->

---
layout: default
---

# 練習 4 (綜合)：月份轉季節（綜合練習）
### 任務說明

請撰寫一個 Java 程式，綜合本章 if 與 switch 兩個重點：

1. 輸入一個月份（1–12），先用 `if` 檢查月份是否在 1~12 之間，超出範圍輸出 `輸入錯誤` 並結束。
2. 範圍正確的話，使用**傳統 `switch`** 搭配 **fall-through**，把月份歸類為四季：
   - 3、4、5 月 → `春季`
   - 6、7、8 月 → `夏季`
   - 9、10、11 月 → `秋季`
   - 12、1、2 月 → `冬季`

**輸入範例：** `month = 7`  
**輸出範例：** `夏季`

<!--
【任務鋪陳】
這一題把今天學到的兩個重點串起來：先用 if 把關，篩掉不合理的輸入；再用傳統 switch 搭配 fall-through，把多個月份歸到同一個季節。

【問題引導】
想一想：12、1、2 月同屬冬季，但 12 在數字上比 1、2 都大，case 的書寫順序要怎麼安排，才能讓 fall-through 正確運作？
-->

---
layout: default
---

# 練習 4：解題提示

### 提示說明

1. 先用 `if (month < 1 || month > 12)` 檢查範圍，不合法就印出 `輸入錯誤` 並結束。
2. 用 `switch (month)`，把同一季節的月份 case 連續寫在一起，最後一個才接執行內容與 `break`。
3. 12、1、2 月同屬冬季：把 `case 12:` 寫在 `case 1:` 之前，讓它貫穿到 `case 2:`。

```java
int month = 7;
if (month < 1 || month > 12) {
    System.out.println("輸入錯誤");
} else {
    switch (month) {
        case 3: case 4: case 5:
            System.out.println("春季"); break;
        case 6: case 7: case 8:
            System.out.println("夏季"); break;
        // 補完秋季、冬季的 case
    }
}
```

<!--
【逐步解說】
看 `case 3: case 4: case 5:` 這三行疊在一起，最後才接 `System.out.println("春季"); break;`，這就是 fall-through 的標準用法：多個 case 共用同一段邏輯。秋季、冬季的部分用同樣的方式補完即可。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【開場白】
今天我們學了程式怎麼「做選擇」。從簡單的 if-else，到三元運算子，再到 switch 的分選機與 fall-through。大家有什麼想問的嗎？

如果之後有興趣，switch 還有更現代化的箭頭寫法跟型別比對，這部分我們放在進階自學內容裡，有餘力的話可以再去看看。
-->

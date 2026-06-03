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
- **5-2 switch 敘述**：傳統 switch、Java 14+ switch expression、switch 搭配字串、Java 17 Pattern Matching
- **5-3 專題實作**：BMI 計算、生肖判斷、火箭升空倒數
- **練習題**：2 題（任務說明 + 解題提示各一張）

<!--
【核心說明】
這章的重點就是兩個字：「分支」。

【生活化比喻】
想像你站在人生的十字路口。往左是去當工程師，往右是去送外送（誤）。if 就像是那個紅綠燈，switch 就像是那個圓環，有多個出口讓你挑。我們還會帶到 Java 17 那些酷酷的新語法。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 5-1 if 敘述

<!--
【開場白】
讓我們先從最簡單、最常用，也最容易讓工程師加班的 if 開始講起。
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
if 系列就像是你的保險條款。

【生活化比喻】
單純 if：如果你有保險，住院就給錢。if-else：如果你及格就過暑假，不及格就去重修。if-else if-else：如果你有 100 萬就買賓士，有 50 萬買豐田，都沒錢就騎腳踏車。

⚠️ 學生常見誤解：
注意！在 if-else if 鏈裡，Java 是一個「懶惰的判官」。只要第一個條件成立了，後面的它連看都不看。所以順序很重要！
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
【逐步解說】
看這段程式碼。if 後面的括號裡一定要放「布林值」（true/false）。如果 score >= 60 是真的，它就會印出「及格」。

⚠️ 學生常見誤解：
大括號 {} 雖然在只有一行時可以省略，但我強烈建議你「永遠寫上大括號」。不然等你以後加了一行程式碼卻發現它不聽話時，你會想把電腦砸了。
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
這叫「分級制度」。

💼 業界實務：
條件要從「最嚴格」的開始寫。如果你把 score >= 60 放在最上面，那 95 分的人也會被判定為「及格」然後直接結束，他應得的「優秀」就飛了。這就像是面試，如果第一關門檻太低，後面的人才就沒機會展現了。
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
這是 if-else 的「精簡版」，適合那些喜歡把程式碼寫得像詩一樣短的工程師。

【生活化比喻】
這就像是問答比賽。題目？答案 A：答案 B。a > b ? "a 較大" : "b 較大"。

💼 業界實務：
這在做簡單的賦值或字串拼接時超好用。但別用它來寫超長、超複雜的邏輯，不然你的同事會在你背後貼紙條罵你。
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
這是一場「美學與邏輯」的對決。

【生活化比喻】
三元運算子就像是「快顯視窗」，一眼看完。if-else 就像是「長篇報告」，寫得清清楚楚。邏輯簡單就用三元，邏輯複雜請務必用 if-else。記住，程式碼是寫給人看的，不是寫來炫耀。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 5-2 switch 敘述

<!--
【開場白】
接下來講 switch。如果 if 是二選一，那 switch 就是「選單模式」。適合用在當一個變數有多種可能固定值的時候。
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
switch 就像是一個「分選機」。

⚠️ 學生常見誤解：
那個 break！它是 switch 的靈魂。少了 break，程式會像煞車失靈的火車一樣，衝過第一站、第二站，直到撞到最後一個車站為止。這叫 fall-through（貫穿）。
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
雖然忘記寫 break 通常是 Bug，但有時候我們會故意不寫。

【生活化比喻】
這就像是你要判斷「工作日」。不論是週一到週五，結果都一樣。這時候我們就讓它一路「貫穿」下去，最後再放一個 break 攔住它。這樣程式碼會簡潔很多。
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
Java 7 之後，switch 終於學會看文字了。

⚠️ 學生常見誤解：
字串比較是「大小寫敏感」的。如果你輸入 "SPRING" 但 case 是 "spring"，它會跑去 default。

💼 業界實務：
為了安全，我們通常會先用 .toLowerCase() 統一格式，免得使用者亂打大小寫讓你程式報錯。
-->

---

# Java 14+ Switch Expression 語法對比

| 特性 | 傳統 switch | Switch Expression (Java 14+) |
| --- | --- | --- |
| 語法符號 | `case 值:` + `break` | `case 值 ->` |
| Fall-through | 有（忘記 break 就貫穿） | 無（自動隔離每個 case） |
| 回傳值 | 不能直接賦值 | 可直接賦值給變數 |
| 多值 case | 需連寫多個 case | `case A, B, C ->` 逗號分隔 |
| 強制完整性 | 不強制（無 default 也行） | 必須涵蓋所有可能值 |

<!--
【核心說明】
這是 Java 的「現代化改造」。

【生活化比喻】
傳統 switch 就像是老舊的機械開關，得自己加保險絲（break）。現代 switch（用 -> 箭頭）就像是數位觸控面板，點一下就到位，不用擔心煞車失靈，還能直接把結果丟給你。
-->

---

# Switch Expression 基本用法

```java
int day = 3;

// 直接賦值，不需 break
String dayName = switch (day) {
    case 1 -> "星期一";
    case 2 -> "星期二";
    case 3 -> "星期三";
    case 4 -> "星期四";
    case 5 -> "星期五";
    default -> "假日";
};
System.out.println(dayName); // 星期三
```

<!--
【逐步解說】
看這段程式碼，多乾淨！沒有討厭的 break，也沒有一堆冒號。String dayName = switch (day) { ... };。這代表 switch 現在「有價值」了，它可以直接回傳一個字串給變數。
-->

---

# Switch Expression：多值 case 與 yield

```java
int month = 8;

int days = switch (month) {
    case 1, 3, 5, 7, 8, 10, 12 -> 31;
    case 4, 6, 9, 11 -> 30;
    case 2 -> {
        // 多行邏輯用 yield 回傳值
        boolean leap = (2024 % 4 == 0);
        yield leap ? 29 : 28;
    }
    default -> 0;
};
System.out.println(month + " 月有 " + days + " 天");
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>yield：</b>在 switch expression 的 block（大括號）中，用 <code>yield</code> 代替 <code>return</code> 回傳值。
</div>

<!--
【核心說明】
現代 switch 能讓你一行抵五行。看到 case 1, 3, 5... -> 31 了嗎？這太優雅了！

【逐步解說】
如果你的邏輯很複雜，需要在大括號裡運算，最後請用 yield 把結果「吐」出來。注意：yield 只有在這種賦值模式下才有用喔。
-->

---

# Java 17 Pattern Matching for switch（預覽特性）

Java 17 引入 switch 型別模式比對（JEP 406，預覽功能）：

| 語法 | 說明 |
| --- | --- |
| `case Integer i ->` | 比對型別並自動綁定變數 `i` |
| `case String s ->` | 比對型別並自動綁定變數 `s` |
| `case String s when s.length() > 0 ->` | 加條件守衛（guarded pattern）|
| `case null ->` | 明確處理 null 值，不再拋 NPE |

<!--
【核心說明】
這是 Java 17 的「大絕招」，它讓 switch 變成了「超能力者」。

【生活化比喻】
以前 switch 只能比數字或字串，現在它能看透物件的本質。「你是一個整數嗎？」、「你是一個空字串嗎？」。這讓程式碼看起來像是在跟電腦直接對話。
-->

---

# Pattern Matching for switch — 範例

```java
static String describe(Object o) {
    return switch (o) {
        case Integer i -> "整數：" + i;
        case String s when s.isEmpty() -> "空字串";
        case String s -> "字串：" + s;
        case null     -> "null 值";
        default       -> "其他型別";
    };
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Java 版本說明：</b>Pattern Matching for switch 在 Java 17 為預覽版，Java 21 起成為正式標準。課程以 JDK 17 為主，使用時需加上 <code>--enable-preview</code> 編譯旗標。
</div>

<!--
【逐步解說】
case Integer i ->。這行不但判斷它是整數，還順便幫它取了名字叫 i。還有那個 when 條件守衛，就像是門口的保全不但看證件，還要檢查你有沒有戴口罩。
-->

---

# Sealed Class 搭配 switch（Java 17）

`sealed` 類別限制繼承範圍，配合 switch 可達到完整型別覆蓋：

```java
sealed interface Shape permits Circle, Rectangle {}
record Circle(double radius) implements Shape {}
record Rectangle(double w, double h) implements Shape {}

static double area(Shape s) {
    return switch (s) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
    };
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>優點：</b>編譯器知道 sealed 類別的所有子型別，能在編譯期檢查是否漏掉某個 case，不需要 default。
</div>

<!--
【核心說明】
這是 Java 為了追求「安全」而設計的。

【生活化比喻】
如果你有一間只准進「圓形」和「方形」的房間，那 switch 就只需要處理這兩種可能。如果你漏寫了，編譯器會咆哮：「那個三角形怎麼辦？」。這讓你的程式碼像鋼鐵一樣堅固。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 5-3 專題實作

<!--
【開場白】
學了這麼多神功，如果不拿來實戰，那就只是在耍嘴皮子。讓我們來看看剛才學的流程控制，在現實世界怎麼用。
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
這是一個結合 if-else if 的經典應用。算完 BMI 後，我們要根據範圍給出評價。記住，身高要先換算成公尺喔！
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
【逐步解說】
看這段邏輯。我們先檢查 < 18.5，再檢查 < 24。因為我們用 else if，所以如果成立了第一個條件就會結束，不會跑到後面去。順序一定要對！
-->

---

# 專題二：生肖判斷

中國生肖依年份除以 12 的餘數判斷：

| 餘數 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 生肖 | 猴 | 雞 | 狗 | 豬 | 鼠 | 牛 | 虎 | 兔 | 龍 | 蛇 | 馬 | 羊 |

<!--
【核心說明】
利用除以 12 的餘數來算出你是哪種動物。這裡最適合用 switch 了，因為情況是固定的 12 種。
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
【逐步解說】
這裡用了 switch expression。2024 年除以 12 餘 8，對應到「龍」。不但寫起來快，讀起來也很清楚，不需要擔心忘記寫 break。
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
【核心說明】
這是一個結合迴圈與判斷的範例。倒數到 3 秒時要特別提醒，0 秒時點火。

【逐步解說】
這就是流程控制的魅力：在對的時間，做對的事情。每次迴圈都會重新跑一次 if 判斷，直到任務完成。
-->

---
layout: default
---

# 練習一：成績等第轉換
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
【出題前的鋪陳】
各位，又到了練習時間。想像你是個冷酷無情的助教，你要把分數轉成 A、B、C。

【問題引導】
記得，順序很重要！如果你先判斷 score >= 0，那全班都會拿到 A...然後你就會被教授開除。
-->

---
layout: default
---

# 練習一：解題提示

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
從高分段開始寫起就對了。試著寫完它，感受一下那個邏輯鏈的運作。最後那個 else 是所有人的「保底」等第。
-->

---
layout: default
---

# 練習二：星期幾判斷器
### 任務說明

使用 **Switch Expression（Java 14+）** 撰寫程式：

- 輸入整數 1–7，分別對應星期一到星期日
- 星期一至星期五輸出：`工作日`
- 星期六、星期日輸出：`假日`
- 其他數值輸出：`無效輸入`

**輸入範例：** `day = 6`  
**輸出範例：** `假日`

<!--
【出題前的鋪陳】
再來一個，練習最新的 switch expression。

【問題引導】
不要用舊的 case 1: ... break; 了，試著用箭頭 -> 和多值 case 1, 2, 3, 4, 5。這會讓你的程式碼看起來像是 2024 年的產物。
-->

---
layout: default
---

# 練習二：解題提示

### 提示說明

1. 使用 `switch (day)` 搭配箭頭語法（`->`）。
2. 星期一到五可用多值 case：`case 1, 2, 3, 4, 5 ->`。
3. 星期六、日：`case 6, 7 ->`。
4. 超出範圍用 `default ->`。

```java
int day = 6;
String type = switch (day) {
    case 1, 2, 3, 4, 5 -> "工作日";
    case ______         -> "假日";
    default             -> "無效輸入";
};
System.out.println(type);
```

<!--
【逐步解說】
case 1, 2, 3, 4, 5 -> "工作日";。就這一行，搞定週一到週五。這就是現代 Java 的力量！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【開場白】
今天我們教了程式怎麼「選擇」。從簡單的 if-else 到強大的 switch 模式比對。大家有什麼想問的嗎？

或者是有人想問，如果我的 if 條件是「老闆心情好」，這該怎麼宣告成布林值？（提示：那個變數通常永遠是 false...開玩笑的！）
-->

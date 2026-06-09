---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 內建 Math 和 Random 類別
routeAlias: ch10
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
  <h1 style="color: #1a5c5c; font-size: 3.2rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">內建 Math 和 Random 類別</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「用 Java 內建工具解決數學與隨機的所有問題」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
各位未來的架構師們，大家好！我是你們今天的講師。我有十年開發經驗，還有十個月的脫口秀經驗——雖然我老婆說我寫的 Bug 比笑話還好笑。今天我們要來聊聊 Java 裡的「數學家」跟「賭神」：Math 和 Random。

【為什麼要學這個？】
寫程式不是只有 CRUD（增刪改查），有時候你得算算折扣、算算距離，或者在遊戲裡抽個寶箱。這時候你不需要自己去背那些三角函數，Java 都幫你準備好了，就在 Math 類別裡。

【今天學完你會能做什麼】
學完這章，你就能寫出一個樂透號碼產生器，甚至能算出台北到東京的直線距離（雖然你還是得買機票）。
-->

---
layout: default
---

# Outline

- **10-1 數學常數：Math.PI、Math.E**
- **10-2 隨機數：Math.random()，指定範圍整數**
- **10-3 max() / min()：比較兩數大小**
- **10-4 abs()：求絕對值**
- **10-5 round()：四捨五入（回傳 long/int）**
- **10-6 rint()：最接近整數值（Banker's Rounding）**
- **10-7 ceil() / floor()：無條件進位 / 捨去**
- **10-8 一般數學運算：pow()、sqrt()、cbrt()、exp()、log()、log10()**
- **10-9 三角函數：sin()、cos()、tan()、toRadians()、toDegrees()**
- **10-10 Random 類別**
- **10-11 專題：Haversine 地球兩點距離**

<!--
【核心說明】
這章我們會先從基礎的常數 PI 開始，接著進入最常用的「隨機數」，然後學怎麼比較大小、取絕對值，還有最讓初學者崩潰的「四捨五入」家族。最後我們會挑戰一個業界等級的「地球兩點距離」計算。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# Math 類別基礎

<!--
【段落轉換】
第一部分，我們先來認識 Math 類別的基礎。它就像是你的專屬計算機，不需要 new，隨傳隨到。
-->

---
layout: default
---

# 10-1 數學常數

`java.lang.Math` 是 Java 內建的數學工具類別，無需 import，所有方法皆為 `static`。

| 常數 | 值（近似） | 說明 |
| --- | --- | --- |
| `Math.PI` | `3.141592653589793` | 圓周率 π |
| `Math.E` | `2.718281828459045` | 自然對數的底數 e（歐拉數）|

```java
System.out.println(Math.PI);   // 3.141592653589793
System.out.println(Math.E);    // 2.718281828459045

// 計算圓面積
double r = 5.0;
double area = Math.PI * r * r;
System.out.println(area);      // 78.53981633974483
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>呼叫方式：</b>Math 類別所有方法都是靜態方法，直接用 <code>Math.方法名()</code> 呼叫，不需要 <code>new</code>。
</div>

<!--
【核心說明】
Math 類別就像是你的專屬計算機，它住在 java.lang 裡面，這是 Java 的核心區，所以你不用 import 就能直接叫它。

【生活化比喻】
這就像你去便利商店買東西，不需要特別寫申請書才能進去，門開了直接進去拿（Math.PI），付錢（不用 new），走人。

【程式世界怎麼用】
最常用在需要精確常數的時候。以前老師叫我們背 3.14，但在 Math.PI 面前，3.14 只是個弟弟，它給你小數點後 15 位。

⚠️ 學生常見誤解：
很多同學會想 new Math()。別傻了，Math 的建構子是 private，它不想讓你 new 它。它是靜態的，直接叫名字就好。
-->

---
layout: default
---

# 10-2 Math.random()

`Math.random()` 回傳範圍為 **[0.0, 1.0)** 的 `double` 值（含 0，不含 1）。

| 用途 | 公式 |
| --- | --- |
| 取得 [0.0, 1.0) 的浮點數 | `Math.random()` |
| 取得 [0, n) 的整數 | `(int)(Math.random() * n)` |
| 取得 [min, max) 的整數 | `(int)(Math.random() * (max - min)) + min` |

```java
// 取得 0.0 ~ 1.0 之間的浮點數
double d = Math.random();

// 取得 0 ~ 9 的整數（共10個）
int n = (int)(Math.random() * 10);

// 取得 1 ~ 6（骰子）
int dice = (int)(Math.random() * 6) + 1;
System.out.println("骰出：" + dice);
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記憶技巧：</b>公式 <code>(int)(Math.random() * 範圍大小) + 最小值</code>，骰子範圍大小 6，最小值 1。
</div>

<!--
【核心說明】
Math.random() 是最基本的亂數產生器，它回傳一個 0 到 1 之間的數字，但不包含 1。

【生活化比喻】
這就像你去參加百貨公司抽獎，抽獎箱裡有無限多張彩票，數字從 0.000... 到 0.999...。

【逐步解說】
如果你想要 1 到 6 的骰子，公式是 (int)(Math.random() * 6) + 1。先放大 6 倍（變成 0 到 5.999），然後強制轉成整數（變成 0 到 5），最後加 1（變成 1 到 6）。

⚠️ 學生常見誤解：
忘記括號！(int)Math.random() * 10 會永遠得到 0，因為它會先強轉 0.xxxx 變成 0，再乘以 10 還是 0。這就是所謂的「一步錯步步錯」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 比較與絕對值

<!--
【段落轉換】
第二部分，我們來學比較大小和取絕對值。這在處理數據邊界時非常有用。
-->

---
layout: default
---

# 10-3 max() / min()

| 方法 | 說明 | 支援型別 |
| --- | --- | --- |
| `Math.max(a, b)` | 回傳較大的值 | int, long, float, double |
| `Math.min(a, b)` | 回傳較小的值 | int, long, float, double |

```java
System.out.println(Math.max(10, 20));     // 20
System.out.println(Math.min(10, 20));     // 10
System.out.println(Math.max(-5, -3));     // -3
System.out.println(Math.min(3.14, 2.71)); // 2.71

// 實際應用：限制分數在 0~100 之間
int score = 105;
int clamped = Math.min(Math.max(score, 0), 100);
System.out.println(clamped);             // 100
```

<!--
【核心說明】
max 和 min 就是幫你挑大的、選小的。支援各種數字型態，非常方便。

【生活化比喻】
這就像是過年去親戚家，如果你媽問你要多少紅包，你一定會用 Math.max(親戚給的, 你媽想收走的)——雖然通常結果都是你媽贏。

【程式世界怎麼用】
業界常用來做「數值限制（Clamping）」。比如音量最大 100 最小 0，你就可以用兩層 max/min 把數值鎖死在這個區間。
-->

---
layout: default
---

# 10-4 abs()：求絕對值

| 方法 | 說明 | 支援型別 |
| --- | --- | --- |
| `Math.abs(x)` | 回傳 x 的絕對值（非負數） | int, long, float, double |

```java
System.out.println(Math.abs(-5));    // 5
System.out.println(Math.abs(5));     // 5
System.out.println(Math.abs(-3.14)); // 3.14

// 計算兩數之差（絕對值）
int a = 30, b = 75;
int diff = Math.abs(a - b);
System.out.println("差距：" + diff); // 差距：45
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b><code>Math.abs(Integer.MIN_VALUE)</code> 仍回傳負數（整數溢位），這是一個已知的邊界案例。
</div>

<!--
【核心說明】
abs 代表 Absolute，就是管你正負，通通給我變正的。

【生活化比喻】
這就像是我的存款帳號，如果是負的，我也好希望它能自動執行 Math.abs()，這樣欠銀行的錢就變成我的錢了。

⚠️ 學生常見誤解：
投影片下方有個冷知識：Math.abs(Integer.MIN_VALUE) 還是負的！因為負的最極端值比正的最極端值多 1，轉正會溢位。這在面試時可以拿來臭顯擺一下。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 捨入方法詳解

<!--
【段落轉換】
第三部分是「捨入」系列。大家以為只有四捨五入嗎？Java 比你想的更周到，但也更讓人糾結。
-->

---
layout: default
---

# 捨入方法總覽

| 方法 | 回傳型別 | 行為 |
| --- | --- | --- |
| `Math.round(x)` | `int` (float) / `long` (double) | 四捨五入（0.5 向正無限大） |
| `Math.rint(x)` | `double` | 最接近的整數值；0.5 時取偶數（Banker's） |
| `Math.ceil(x)` | `double` | 無條件進位（往正方向） |
| `Math.floor(x)` | `double` | 無條件捨去（往負方向） |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>選擇建議：</b>一般四捨五入用 <code>round()</code>；財務計算避免偏差用 <code>rint()</code>；需要 double 回傳時用 <code>rint()</code>、<code>ceil()</code>、<code>floor()</code>。
</div>

<!--
【核心說明】
Java 提供了四種捨入方式，因為世界上不是只有四捨五入。

【生活化比喻】
ceil 是天花板（往上爬），floor 是地板（往下摔），round 是標準的好學生（四捨五入），rint 則是個計較的銀行家（Banker's Rounding）。
-->

---
layout: default
---

# 捨入方法對比範例

| 輸入值 | `round()` | `rint()` | `ceil()` | `floor()` |
| --- | --- | --- | --- | --- |
| `2.3` | `2` | `2.0` | `3.0` | `2.0` |
| `2.5` | `3` | `2.0` (偶數) | `3.0` | `2.0` |
| `3.5` | `4` | `4.0` (偶數) | `4.0` | `3.0` |
| `-2.5` | `-2` | `-2.0` (偶數) | `-2.0` | `-3.0` |
| `3.7` | `4` | `4.0` | `4.0` | `3.0` |

```java
System.out.println(Math.round(2.5));  // 3  (long)
System.out.println(Math.rint(2.5));   // 2.0 (Banker's → 偶數)
System.out.println(Math.ceil(2.3));   // 3.0
System.out.println(Math.floor(2.7));  // 2.0
```

<!--
【逐步解說】
大家看這張表，注意 -2.5 的 round() 結果是 -2。為什麼？因為 round 的定義是「加 0.5 後向下取整」，所以 -2.5 + 0.5 = -2.0。這跟我們直覺的「絕對值四捨五入」不太一樣，要注意。
-->

---
layout: default
---

# 10-5 round()：四捨五入

`Math.round()` 採傳統四捨五入，0.5 一律向正無限大取整。

| 方法簽名 | 回傳型別 | 說明 |
| --- | --- | --- |
| `Math.round(float a)` | `int` | 浮點數四捨五入，回傳 int |
| `Math.round(double a)` | `long` | 雙精度四捨五入，回傳 long |

```java
System.out.println(Math.round(1.4f));  // 1
System.out.println(Math.round(1.5f));  // 2
System.out.println(Math.round(2.5));   // 3  (回傳 long)
System.out.println(Math.round(-1.5));  // -1 (往正無限大)
System.out.println(Math.round(-2.5));  // -2 (往正無限大)
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b>負數的 0.5 也是往正方向，所以 -1.5 → -1，-2.5 → -2。
</div>

<!--
【核心說明】
round 的回傳型別很特別，傳 float 回 int，傳 double 回 long。

【生活化比喻】
它就是那個最保守的計算法，0.5 就大方地送給你（往正無限大走）。
-->

---
layout: default
---

# 10-6 rint()：Banker's Rounding

`Math.rint()` 回傳最接近的整數值（型別為 `double`），0.5 時取最近的**偶數**。

| 方法簽名 | 回傳型別 | 說明 |
| --- | --- | --- |
| `Math.rint(double a)` | `double` | 回傳最近整數值，0.5 取偶數 |

```java
System.out.println(Math.rint(1.5));  // 2.0 (2 是偶數)
System.out.println(Math.rint(2.5));  // 2.0 (2 是偶數)
System.out.println(Math.rint(3.5));  // 4.0 (4 是偶數)
System.out.println(Math.rint(4.5));  // 4.0 (4 是偶數)
System.out.println(Math.rint(2.3));  // 2.0
System.out.println(Math.rint(2.7));  // 3.0
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Banker's Rounding：</b>0.5 時捨入到最近的偶數，長期累加可減少系統性偏差，銀行與科學計算常用此策略。
</div>

<!--
【核心說明】
這叫「銀行家捨入法」。當剛好是 0.5 的時候，它會往「偶數」靠攏。

【生活化比喻】
銀行家很精明，如果大家都四捨五入，最後帳目會偏多。如果 0.5 的時候一半捨、一半入，帳目就會平衡。

💼 業界實務：
如果你在處理電商平台的退款或結帳，強烈建議了解一下 rint 或 BigDecimal，不然少了一塊錢，財務會追殺你到天涯海角。
-->

---
layout: default
---

# 10-7 ceil() / floor()

| 方法 | 說明 | 回傳型別 |
| --- | --- | --- |
| `Math.ceil(double a)` | 天花板：不小於 a 的最小整數 | `double` |
| `Math.floor(double a)` | 地板：不大於 a 的最大整數 | `double` |

```java
System.out.println(Math.ceil(3.1));   // 4.0
System.out.println(Math.ceil(3.9));   // 4.0
System.out.println(Math.ceil(-3.1));  // -3.0 (往正方向)
System.out.println(Math.floor(3.9));  // 3.0
System.out.println(Math.floor(3.1));  // 3.0
System.out.println(Math.floor(-3.1)); // -4.0 (往負方向)
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記憶法：</b>ceil（天花板）往上取，floor（地板）往下取。負數時方向一樣，<code>ceil(-3.1)</code> = -3.0（往上），<code>floor(-3.1)</code> = -4.0（往下）。
</div>

<!--
【生活化比喻】
ceil 就像你考試考 59.1 分，你跪求教授幫你 ceil 一下變 60。floor 就像你買東西，預算 100.9 元，老闆說不收零錢，幫你 floor 變 100。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# 數學運算方法

<!--
【段落轉換】
第四部分是核心運算：次方、開方和對數。科學計算必備。
-->

---
layout: default
---

# 10-8 一般數學運算方法

| 方法 | 說明 |
| --- | --- |
| `Math.pow(a, b)` | 計算 a 的 b 次方，回傳 double |
| `Math.sqrt(a)` | 開平方根，回傳 double |
| `Math.cbrt(a)` | 開立方根，回傳 double |
| `Math.exp(a)` | 計算 e^a，回傳 double |
| `Math.log(a)` | 自然對數 ln(a)，回傳 double |
| `Math.log10(a)` | 常用對數 log₁₀(a)，回傳 double |

<!--
【核心說明】
這邊就是我們以前數學課最討厭的部分：次方、平方根、對數。

【生活化比喻】
Math.pow(2, 10) 就是把 2 複製 10 個連乘。在電腦世界，這就是 1024 (1K)。
-->

---
layout: default
---

# 10-8 一般數學運算 — 範例

```java
System.out.println(Math.pow(2, 10));    // 1024.0
System.out.println(Math.sqrt(144));     // 12.0
System.out.println(Math.cbrt(27));      // 3.0
System.out.println(Math.exp(1));        // 2.718281828459045
System.out.println(Math.log(Math.E));   // 1.0
System.out.println(Math.log10(1000));   // 3.0
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>log 換底公式：</b>log₂(8) 在 Java 沒有直接方法，可用 <code>Math.log(8) / Math.log(2)</code> 計算，結果為 3.0。
</div>

<!--
【逐步解說】
注意 Java 沒有 log2。如果你要算 log2(8)，得用換底公式：Math.log(8) / Math.log(2)。這就像是你去國外換錢，沒有台幣換美金，得先換成美金再換成當地的錢，轉個手而已。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
# 三角函數

<!--
【段落轉換】
第五部分是三角函數。工程師常用的部分，但陷阱不少。
-->

---
layout: default
---

# 10-9 三角函數方法

| 方法 | 說明 |
| --- | --- |
| `Math.sin(radians)` | 正弦值，參數單位為**弧度** |
| `Math.cos(radians)` | 餘弦值，參數單位為**弧度** |
| `Math.tan(radians)` | 正切值，參數單位為**弧度** |
| `Math.asin(value)` | 反正弦，回傳弧度 |
| `Math.acos(value)` | 反餘弦，回傳弧度 |
| `Math.atan(value)` | 反正切，回傳弧度 |
| `Math.toRadians(degrees)` | 度數 → 弧度 |
| `Math.toDegrees(radians)` | 弧度 → 度數 |

<!--
【核心說明】
如果你要算角度，記得 Java 認的是「弧度」。

⚠️ 學生常見誤解：
如果你直接傳 90 進去 Math.sin(90)，得到的不是 1，而是 -0.89 左右。因為電腦以為你要算「90 弧度」。
-->

---
layout: default
---

# 10-9 三角函數 — 範例

```java
// toRadians 將度數轉為弧度，再傳入三角函數
System.out.println(Math.sin(Math.toRadians(90)));  // 1.0
System.out.println(Math.cos(Math.toRadians(0)));   // 1.0
System.out.println(Math.tan(Math.toRadians(45)));  // 0.9999... ≈ 1.0

// 反三角函數：回傳弧度，再轉為度數
double angle = Math.toDegrees(Math.asin(1.0));
System.out.println(angle);  // 90.0

// 直角三角形：已知對邊 3，斜邊 5，求角度
double sinValue = 3.0 / 5.0;
System.out.println(Math.toDegrees(Math.asin(sinValue))); // 36.87...
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b>Java 的三角函數參數單位是弧度（radians），不是角度（degrees）。一定要先用 <code>Math.toRadians()</code> 轉換。
</div>

<!--
【逐步解說】
所以一定要記得套 Math.toRadians()。這就像是你跟美國人講公里他們聽不懂，你要先轉成英哩（Miles）一樣，電腦跟人類的角度單位也是有代溝的。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第六部分
# Random 類別

<!--
【段落轉換】
第六部分，我們來看真正的「隨機大師」：Random 類別。
-->

---
layout: default
---

# 10-10 Random 類別

`java.util.Random` 提供更豐富的亂數功能，需要 `import java.util.Random`。

| 方法 / 建構子 | 說明 |
| --- | --- |
| `new Random()` | 以系統時間為種子（每次不同）|
| `new Random(long seed)` | 指定種子（相同種子產生相同序列）|
| `nextInt(int n)` | 回傳 [0, n) 的整數 |
| `nextDouble()` | 回傳 [0.0, 1.0) 的 double |
| `nextBoolean()` | 回傳 true 或 false（各 50%）|
| `setSeed(long seed)` | 重新設定種子 |

<!--
【核心說明】
Random 是一個物件，它比 Math.random() 強大多了，可以產生整數、布林值、甚至長整數。

【生活化比喻】
Math.random() 就像路邊的投幣抽獎機；Random 類別就像是拉斯維加斯的專業發牌員，你可以叫他發各種型態的牌。
-->

---
layout: default
---

# 10-10 Random 類別 — 範例

```java
import java.util.Random;

Random rand = new Random();

System.out.println(rand.nextInt(10));     // [0, 10) 的整數
System.out.println(rand.nextDouble());    // [0.0, 1.0) 的浮點數
System.out.println(rand.nextBoolean());   // true 或 false

// 骰子：[1, 6]
int dice = rand.nextInt(6) + 1;

// 指定種子（可重現相同序列）
Random seeded = new Random(42);
System.out.println(seeded.nextInt(100));  // 每次執行都相同
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>setSeed 用途：</b>測試或模擬時，設定固定種子可確保每次結果一致，方便重現與除錯。
</div>

<!--
【逐步解說】
這裡有個「種子（Seed）」的概念。如果你設定 new Random(42)，每次跑出來的「亂數」序列都會一模一樣！這在寫遊戲存檔或跑科學測試時非常有幫助，因為亂數變得「可以被預測」了。
-->

---
layout: default
---

# Random vs Math.random() 比較

| 面向 | `Math.random()` | `java.util.Random` |
| --- | --- | --- |
| 回傳型別 | `double` 只有一種 | int、double、boolean 等多種 |
| 種子控制 | 無法指定 | 可指定種子 |
| 使用方式 | 靜態呼叫，簡便 | 需建立物件 |
| 範圍整數 | 需手動換算 | `nextInt(n)` 直接指定範圍 |
| 適合場景 | 簡單一次性亂數 | 需多種型別或可重現的亂數 |

```java
// Math.random() — 骰子
int d1 = (int)(Math.random() * 6) + 1;

// Random — 骰子
Random r = new Random();
int d2 = r.nextInt(6) + 1;
```

<!--
【核心說明】
這張表是面試重點！初學者用 Math.random()，專業開發者常用 java.util.Random。如果你想要讓程式碼看起來高級一點，選 Random 就對了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第七部分
# 專題：地球兩點距離

<!--
【段落轉換】
最後，我們來一個實戰專題。把學到的數學工具拿來算地球兩點間的距離。
-->

---
layout: default
---

# 10-11 Haversine 公式

**問題：** 已知兩地的經緯度，如何計算地球表面的直線距離？

**Haversine 公式** 將地球視為完美球體，利用球面幾何計算大圓距離：

| 變數 | 說明 |
| --- | --- |
| `φ₁, φ₂` | 兩點的緯度（轉為弧度）|
| `λ₁, λ₂` | 兩點的經度（轉為弧度）|
| `R` | 地球半徑（6371 km）|
| `a` | haversine 中間值 |
| `c` | 圓心角 |

```java
final double R = 6371; // 地球半徑（公里）
```

<!--
【開場白】
接下來我們要來點硬核的。假設你要寫一個外送 App，要算外送員離你有幾公里。

【為什麼要學這個？】
地球是圓的（雖然有人說是平的），所以不能直接用畢氏定理。Haversine 公式就是用來處理球面的距離計算。
-->

---
layout: default
---

# 10-11 Haversine 實作 (一)

```java
static double haversine(double val) {
    return Math.pow(Math.sin(val / 2), 2);
}

static double calcDistance(double lat1, double lon1,
                           double lat2, double lon2) {
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);
    double a = haversine(dLat)
             + Math.cos(lat1) * Math.cos(lat2) * haversine(dLon);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return 6371 * c;
}
```

<!--
【逐步解說】
這段程式碼看起來很嚇人，但其實就是把剛才學的 sin、cos、atan2 串在一起。這就像是在組裝樂高，零件（方法）你都有了，只要照著說明書（公式）拼起來就好。
-->

---
layout: default
---

# 10-11 Haversine 實作 (二) — 測試

```java
// 台北 (25.033, 121.565)  →  東京 (35.689, 139.692)
double dist = calcDistance(25.033, 121.565, 35.689, 139.692);
System.out.printf("台北 → 東京：%.1f 公里%n", dist);
// 約 2097.9 公里
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>用到的 Math 方法：</b><code>Math.sin()</code>、<code>Math.cos()</code>、<code>Math.sqrt()</code>、<code>Math.atan2()</code>、<code>Math.toRadians()</code>、<code>Math.pow()</code>——一次整合本章所有方法！
</div>

<!--
【逐步解說】
算出來台北到東京大約 2097 公里。下次你買機票時，可以跟空姐說：「嘿，我算過 Haversine 了，航程應該沒錯。」（雖然她可能會叫保安，但這就是工程師的浪漫）。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 練習題

<!--
【互動引導】
現在輪到你們了。來試試兩個實用的練習題。
-->

---
layout: default
---

# 練習一：樂透號碼產生器
### 任務說明

撰寫一個程式，模擬台灣大樂透（49 選 6）：

1. 從 **1 到 49** 中隨機抽出 **6 個不重複** 的整數
2. 將號碼**由小到大**排序後印出
3. 額外抽出一個**特別號**（不可與前 6 個重複）

範例輸出：
```
大樂透號碼：03 08 15 24 35 41
特別號：17
```

<!--
【互動引導】
好了，現在該你們大展身手了。能不能退休就看這一場練習了！請寫出一個不重複的 49 選 6 程式，這可是考驗你對 Random 和集合的掌握度。
-->

---
layout: default
---

# 練習一：解題提示
### 提示說明

1. 使用 `Random` 物件的 `nextInt(49) + 1` 產生 1~49 的亂數
2. 用 `ArrayList<Integer>` 儲存號碼，加入前先檢查 `contains()` 是否重複
3. 使用 `Collections.sort()` 排序
4. 特別號同樣檢查不與前 6 個重複
5. 用 `String.format("%02d", n)` 將數字補零為兩位數

```java
import java.util.*;
Random rand = new Random();
List<Integer> nums = new ArrayList<>();
while (nums.size() < 6) {
    int n = rand.nextInt(49) + 1;
    if (!nums.contains(n)) nums.add(n);
}
Collections.sort(nums);
```

<!--
【解說要點】
重點在於「不重複」。你可以用 List 搭配 contains 來檢查。這就像是你去聯誼，要確保沒邀到重複的人，不然場面會很尷尬。
-->

---
layout: default
---

# 練習二：BMI 計算器
### 任務說明

撰寫一個程式，計算 BMI 並判斷體重狀態：

1. 輸入身高（公分）與體重（公斤）
2. 計算 BMI = 體重 / 身高²（身高換算為公尺）
3. 用 `Math.round()` 或 `Math.floor()` 取小數點一位
4. 依據以下標準輸出判斷結果：

| BMI 範圍 | 狀態 |
| --- | --- |
| < 18.5 | 體重過輕 |
| 18.5 ~ 24.9 | 正常體重 |
| 25.0 ~ 29.9 | 體重過重 |
| ≥ 30 | 肥胖 |

<!--
【互動引導】
這是一個扎心的練習。我們要算 BMI。請大家誠實面對自己的數據，反正程式只有你知道。
-->

---
layout: default
---

# 練習二：解題提示
### 提示說明

1. 身高從公分換算為公尺：`double heightM = height / 100.0;`
2. 計算 BMI：`double bmi = weight / Math.pow(heightM, 2);`
3. 取到小數一位：`double bmiRounded = Math.floor(bmi * 10) / 10.0;`
4. 用 `if-else if` 判斷 BMI 範圍

```java
double heightM = 170 / 100.0;   // 1.7
double weight  = 65.0;
double bmi     = weight / Math.pow(heightM, 2);
double rounded = Math.floor(bmi * 10) / 10.0;
System.out.printf("BMI：%.1f%n", rounded);
```

<!--
【解說要點】
記得身高要換成公尺！不要拿 170 去除，不然你的 BMI 會變成 0.000...，那你就真的成仙了。取小數點一位的小撇步是先乘以 10，取整後再除以 10。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

有任何關於 Math 或 Random 的問題嗎？

<!--
【開場白】
關於數學跟隨機，大家有沒有想問的？或者是想問我哪家樂透比較準？（雖然我只會寫程式，不會算明牌，不然我現在就在夏威夷講課了）。
-->

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
哈囉大家，歡迎來到「內建 Math 和 Random 類別」！這一章我們要來認識 Java 裡的兩個好幫手：Math 類別和 Random 類別。

為什麼要學這個？寫程式不是只有資料的增刪改查，有時候我們得算算折扣、限制數值範圍、四捨五入金額，或者在遊戲裡抽個寶箱、產生樂透號碼。這些功能其實 Java 都已經幫我們準備好了，就放在 Math 和 Random 這兩個類別裡，不用自己重新發明輪子。

學完這一章，我們會掌握數學常數、亂數產生、比較大小、絕對值、各種捨入方法、次方與開方運算，最後還能寫出一個完整的樂透號碼產生器。準備好了嗎，我們開始吧！
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
- **10-9 Random 類別**

<!--
這一章我們會先從基礎的常數 PI 開始，接著進入最常用的「隨機數」，然後學怎麼比較大小、取絕對值，還有最讓初學者容易搞混的「四捨五入」家族。最後再認識更強大的 Random 類別，並在練習中綜合運用。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# Math 類別基礎

<!--
第一部分，我們先來認識 Math 類別的基礎。它就像是我們的專屬計算機，不需要 `new`，隨傳隨到。
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
`java.lang.Math` 就像是我們的專屬計算機，它住在 `java.lang` 套件裡，這是 Java 的核心區，所以我們不用 `import` 就能直接叫它。

生活化比喻的話，這就像我們去便利商店買東西，不需要特別填申請書才能進去——門開了直接拿（`Math.PI`），結帳（不用 `new`），走人。

業界實務上，這個常數最常用在需要精確數值的時候。以前我們背 3.14，但在 `Math.PI` 面前，3.14 只是個概略值，它給我們小數點後 15 位的精度。

⚠️ 易錯點：很多人會想寫 `new Math()`。其實 Math 的建構子是 `private`，不能被 `new`。它的所有方法都是 `static`，直接用類別名稱呼叫即可。

預期結果：`System.out.println(Math.PI)` 會印出 `3.141592653589793`；圓面積範例會印出 `78.53981633974483`。
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
`Math.random()` 是最基本的亂數產生器，它回傳一個 0 到 1 之間的數字，但不包含 1。

生活化比喻的話，這就像我們去抽獎箱裡抽一張彩票，箱子裡有無限多張彩票，數字從 0.000... 到 0.999... 都有可能。

帶大家看一下骰子的例子：如果我們想要 1 到 6 的骰子，公式是 `(int)(Math.random() * 6) + 1`。先放大 6 倍（變成 0 到 5.999...），再強制轉型成整數（變成 0 到 5），最後加 1（變成 1 到 6）。

⚠️ 易錯點：忘記括號！`(int)Math.random() * 10` 會永遠得到 0，因為它會先把 `0.xxxx` 強轉成 `0`，再乘以 10 還是 0。轉型的優先順序一定要用括號控制清楚。

預期結果：骰子範例每次執行會印出 1 到 6 之間的其中一個整數。
-->

---
layout: default
---

# 練習：圓形面積與抽號機
### 任務說明

請完成以下兩個小任務：

1. 利用 `Math.PI`，計算半徑為 `7.0` 的圓面積，並印出結果
2. 利用 `Math.random()`，模擬一台「抽號機」：產生一個 **1 到 100** 之間的隨機整數（包含 1 和 100），並印出結果

<!--
【任務鋪陳】
我們剛剛學了 `Math.PI` 這個常數，也學了 `Math.random()` 的公式 `(int)(Math.random() * 範圍大小) + 最小值`。這題請大家把這兩個基礎工具各自應用一次。

【引導思考】
圓面積的公式是 `半徑 * 半徑 * Math.PI`，這個應該不難。至於 1 到 100 的整數，範圍大小是多少？最小值又是多少？把這兩個數字代入公式看看。
-->

---
layout: default
---

# 練習：圓形面積與抽號機
### 解題提示

```java
// 任務一：圓面積
double r = 7.0;
double area = r * r * Math.PI;
System.out.println(area); // 153.93804002589985

// 任務二：1~100 的隨機整數
int n = (int)(Math.random() * 100) + 1;
System.out.println(n); // 1 ~ 100 之間的某個整數
```

<!--
【帶讀解法】
第一個任務就是直接套用 `Math.PI`，把公式 `r * r * π` 寫出來即可，這跟我們剛剛看到的範例幾乎一樣，只是換了一個半徑數值。

第二個任務的重點是套公式：`(int)(Math.random() * 範圍大小) + 最小值`。這裡「範圍大小」是 100（因為 1 到 100 共有 100 個數字），「最小值」是 1。所以寫成 `(int)(Math.random() * 100) + 1`，執行結果會落在 1 到 100 之間（包含兩端）。

跟骰子的例子比較一下：骰子是 `(int)(Math.random() * 6) + 1`（1~6），這裡只是把 6 換成 100，邏輯完全一樣。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 比較與絕對值

<!--
第二部分，我們來學比較大小和取絕對值。這在處理數據邊界時非常有用，例如限制分數或音量的範圍。
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
`Math.max()` 和 `Math.min()` 就是幫我們挑大的、選小的，支援 int、long、float、double 等各種數字型態，非常方便。

生活化比喻的話，這就像我們在收納箱裡挑東西，`max` 是挑最大的那一件，`min` 是挑最小的那一件。

業界實務上，這常用來做「數值限制（Clamping）」。例如音量最大 100、最小 0，就可以用兩層 `min`/`max` 把數值鎖死在這個區間，範例裡的 `clamped` 就是把超過 100 的分數限制回 100。

預期結果：最後一行會印出 `100`，因為 105 被限制在 0~100 的範圍內。
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
`abs` 是 Absolute（絕對值）的縮寫，不管傳入的是正數還是負數，一律回傳非負數。

生活化比喻的話，這就像把一個方向相反的箭頭轉正——不管原本指向哪裡，`abs` 之後一律朝向正方向。

範例裡的 `diff` 用 `Math.abs(a - b)` 算出兩數之差的絕對值，不用擔心 `a - b` 是負數的問題。

⚠️ 易錯點：`Math.abs(Integer.MIN_VALUE)` 仍然回傳負數！這是因為 `int` 能表示的負數比正數多 1 個，轉正時會發生整數溢位（overflow），是一個常被忽略的邊界案例。

預期結果：最後一行會印出「差距：45」。
-->

---
layout: default
---

# 練習：溫度範圍限制與溫差計算
### 任務說明

某個空調系統有以下需求：

1. 使用者輸入的設定溫度 `temp` 必須限制在 **16 ~ 30 度**之間（小於 16 設為 16，大於 30 設為 30）
2. 計算「設定溫度」與「目前室溫」之間的**溫差**（不分正負，一律印出正值）

請寫一段程式碼，分別示範 `temp = 35`（超出上限）與 `temp = 10`（超出下限）這兩種情況的限制結果，並計算 `temp = 35` 與目前室溫 `25` 之間的溫差。

<!--
【任務鋪陳】
我們剛剛學了 `Math.max()`、`Math.min()` 可以做「數值限制（Clamping）」，也學了 `Math.abs()` 可以求絕對值。這題請大家把這兩個工具一起用在一個更貼近生活的情境：空調溫度設定。

【引導思考】
回想一下分數限制的寫法：`Math.min(Math.max(score, 0), 100)`。這裡的上下限換成 16 和 30，要怎麼改？溫差的部分，直接 `temp - 目前室溫` 可能會是負數，這時候該用哪個方法處理？
-->

---
layout: default
---

# 練習：溫度範圍限制與溫差計算
### 解題提示

```java
int currentRoomTemp = 25;

// 情況一：temp = 35（超出上限）
int temp1 = 35;
int clamped1 = Math.min(Math.max(temp1, 16), 30);
System.out.println(clamped1); // 30

// 情況二：temp = 10（超出下限）
int temp2 = 10;
int clamped2 = Math.min(Math.max(temp2, 16), 30);
System.out.println(clamped2); // 16

// 溫差（取絕對值）
int diff = Math.abs(clamped1 - currentRoomTemp);
System.out.println("溫差：" + diff); // 溫差：5
```

<!--
【帶讀解法】
限制範圍的寫法跟分數限制是同一個套路：`Math.min(Math.max(temp, 下限), 上限)`。先用 `Math.max(temp, 16)` 確保不會低於 16，再用 `Math.min(..., 30)` 確保不會高於 30。

- `temp1 = 35`：`Math.max(35, 16)` 是 `35`，再 `Math.min(35, 30)` 變成 `30`——超出上限被拉回 30。
- `temp2 = 10`：`Math.max(10, 16)` 是 `16`，再 `Math.min(16, 30)` 還是 `16`——超出下限被拉回 16。

溫差的部分用 `Math.abs(clamped1 - currentRoomTemp)`，也就是 `Math.abs(30 - 25)`，結果是 `5`。不管設定溫度比室溫高還是低，`abs` 都能確保溫差是正值。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 捨入方法詳解

<!--
第三部分是「捨入」系列。大家以為只有四捨五入嗎？Java 提供了不只一種捨入方式，分別適合不同的場景。
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
Java 提供了四種捨入方式，因為世界上不是只有四捨五入這一種需求。

生活化比喻的話，`ceil` 是天花板（往上取），`floor` 是地板（往下取），`round` 是我們最熟悉的四捨五入，`rint` 則像是精打細算的會計師，0.5 的時候會取最近的偶數（Banker's Rounding）。

下方的選擇建議告訴我們：一般四捨五入用 `round()`；財務計算為了避免長期偏差用 `rint()`；如果需要回傳 `double` 型別，就用 `rint()`、`ceil()` 或 `floor()`。
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
這個範例的目的是：把四種捨入方法放在同一張表裡對照，看看同樣的輸入值會得到什麼不同的結果。

帶大家看表格裡比較特別的一行：`-2.5` 的 `round()` 結果是 `-2`。為什麼？因為 `round` 的定義是「加 0.5 後向下取整」，所以 `-2.5 + 0.5 = -2.0`，再往下取整還是 `-2`。這跟我們直覺的「絕對值四捨五入」不太一樣，要特別注意。

⚠️ 易錯點：`rint()` 在 `2.5` 和 `3.5` 的結果分別是 `2.0` 和 `4.0`，因為它會取最近的偶數，不是單純的「五則進」。

預期結果：依序印出 `3`、`2.0`、`3.0`、`2.0`。
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
`Math.round()` 的回傳型別很特別：傳入 `float` 會回傳 `int`，傳入 `double` 會回傳 `long`，這是初學者很容易忽略的細節。

生活化比喻的話，`round` 就是最直覺的計算法——遇到 0.5，一律往正無限大的方向湊整數，不管正數還是負數都是同樣的規則。

⚠️ 易錯點：負數的 0.5 也是往正方向，所以 `-1.5` 會變成 `-1`，`-2.5` 會變成 `-2`，不是我們直覺的「離 0 越遠」。

預期結果：依序印出 `1`、`2`、`3`、`-1`、`-2`。
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
`Math.rint()` 又叫「銀行家捨入法」（Banker's Rounding）。當數值剛好是 0.5 的時候，它會往「最近的偶數」靠攏，而不是固定往上或往下。

生活化比喻的話，如果每次 0.5 都四捨五入，長期累加下來金額會系統性地偏多；但如果 0.5 的時候一半進、一半退（取決於偶數還是奇數），整體帳目就會比較平衡。

業界實務上，如果我們在處理電商平台的退款或結帳金額，強烈建議了解一下 `rint()` 或 `BigDecimal`，否則長期累積的捨入誤差可能會讓財務帳目對不起來。

預期結果：依序印出 `2.0`、`2.0`、`4.0`、`4.0`、`2.0`、`3.0`。
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
生活化比喻的話，`ceil`（天花板）就像我們考試考了 59.1 分，老師願意往上湊整到 60 分；`floor`（地板）則像我們買東西時，老闆說不收零錢，把 100.9 元收成 100 元。

⚠️ 易錯點：負數時方向不變，`ceil(-3.1)` 是 `-3.0`（往正方向、數值變大），`floor(-3.1)` 是 `-4.0`（往負方向、數值變小）。記憶的關鍵是「方向」而不是「數值大小」。

預期結果：依序印出 `4.0`、`4.0`、`-3.0`、`3.0`、`3.0`、`-4.0`。
-->

---
layout: default
---

# 練習：round() 與 rint() 的差異
### 認證模擬題（單選）

請看以下程式碼：

```java
System.out.println(Math.round(4.5));
System.out.println(Math.rint(4.5));
System.out.println(Math.round(3.5));
System.out.println(Math.rint(3.5));
```

執行結果依序為下列哪一個選項？

A. `5` / `4.0` / `4` / `4.0`
B. `4` / `4.0` / `4` / `4.0`
C. `5` / `5.0` / `4` / `3.0`
D. `5` / `4.0` / `4` / `3.0`

<!--
【出題動機】
這題想確認大家能不能正確區分 `round()` 跟 `rint()` 對「0.5」的不同處理方式——這是初學者最容易搞混的一組方法。

【解題引導】
回想一下：`Math.round()` 對 0.5 永遠是「向正無限大」湊整，不管前面是奇數還是偶數；`Math.rint()` 則是「Banker's Rounding」，0.5 時會取最近的**偶數**。把 `4.5` 和 `3.5` 分別代入這兩個規則看看。
-->

---
layout: default
---

# 練習：round() 與 rint() 的差異
### 解析

**正確答案：A**

- ✅ A：`Math.round(4.5)` 採「向正無限大」湊整，`4.5` → `5`；`Math.rint(4.5)` 採 Banker's Rounding，`4.5` 取最近偶數，`4` 和 `5` 中偶數是 `4`，所以是 `4.0`；`Math.round(3.5)` 同樣向正無限大，`3.5` → `4`；`Math.rint(3.5)` 取最近偶數，`3` 和 `4` 中偶數是 `4`，所以是 `4.0`。完全正確。
- ❌ B：第一個結果寫成 `4` 是錯的——`Math.round()` 沒有 Banker's Rounding 的規則，`4.5` 一律向正無限大變成 `5`，不會是 `4`。
- ❌ C：`Math.rint(4.5)` 寫成 `5.0` 是錯的——`5` 是奇數，Banker's Rounding 不會選奇數；`Math.rint(3.5)` 寫成 `3.0` 也錯，`3` 是奇數，同樣不會被選中。
- ❌ D：`Math.rint(3.5)` 寫成 `3.0` 是錯的，理由同 C——`3.5` 的最近偶數是 `4`，不是 `3`。

<!--
【帶讀解法】
這題的核心就是「兩套不同的 0.5 規則」：

- `Math.round()`：永遠向正無限大，所以 `4.5 → 5`、`3.5 → 4`，跟前面數字是奇是偶完全無關。
- `Math.rint()`：0.5 時看「哪個相鄰整數是偶數」，`4.5` 的相鄰整數是 `4` 和 `5`，偶數是 `4`，所以結果是 `4.0`；`3.5` 的相鄰整數是 `3` 和 `4`，偶數同樣是 `4`，所以結果也是 `4.0`。

這也呼應了我們前面的對照表：`2.5` 用 `rint()` 會變成 `2.0`（偶數），`3.5` 用 `rint()` 會變成 `4.0`（偶數）——規律永遠是「挑偶數」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# 數學運算方法

<!--
第四部分是核心運算：次方、開方和對數，科學計算和統計分析時經常用到。
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
這邊整理的是我們以前數學課學過的部分：次方、平方根、立方根，以及指數和對數運算，Java 都用 `static` 方法幫我們包好了。

生活化比喻的話，`Math.pow(2, 10)` 就是把 2 連續乘 10 次。在電腦世界裡，這個結果剛好就是 1024（也就是 1K），是計算容量、效能時常見的數字。
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
這個範例的目的是：把上一頁列出的六個方法一次跑過，確認每個方法的回傳結果。

帶大家看比較特別的一點：Java 沒有提供 `log2` 方法。如果我們要算 `log₂(8)`，得用「換底公式」：`Math.log(8) / Math.log(2)`。這就像我們去國外換錢，沒有台幣直接換當地貨幣的楃口，得先換成美金再換一次，轉個手而已。

⚠️ 易錯點：`Math.log()` 是「自然對數」（以 e 為底），不是常用對數；常用對數（以 10 為底）要用 `Math.log10()`。

預期結果：依序印出 `1024.0`、`12.0`、`3.0`、約 `2.718...`、`1.0`、`3.0`。
-->

---
layout: default
---

# 練習：計算正方形對角線長度
### 任務說明

已知正方形的邊長為 `side`，請計算它的對角線長度。

提示：根據畢氏定理，對角線長度 = `√(side² + side²)`

請寫一段程式碼，計算邊長為 `10` 的正方形對角線長度，並印出結果。

<!--
【任務鋪陳】
我們剛剛學了 `Math.pow()`（次方）和 `Math.sqrt()`（開平方根）。這題請大家把這兩個方法組合起來，算一個國中數學就學過的題目：正方形的對角線長度。

【引導思考】
畢氏定理告訴我們，對角線的平方等於兩個邊長平方的和。`side` 的平方可以用 `Math.pow(side, 2)`，也可以直接寫 `side * side`。算出平方和之後，再用哪個方法把它「開根號」回來？
-->

---
layout: default
---

# 練習：計算正方形對角線長度
### 解題提示

```java
double side = 10;
double diagonal = Math.sqrt(Math.pow(side, 2) + Math.pow(side, 2));
System.out.println(diagonal); // 14.142135623730951
```

<!--
【帶讀解法】
這題就是把畢氏定理直接翻譯成程式碼：

1. `Math.pow(side, 2)` 算出 `side` 的平方，也就是 `10 * 10 = 100.0`
2. 兩個 `100.0` 相加，得到 `200.0`
3. `Math.sqrt(200.0)` 開平方根，得到約 `14.142135623730951`

這也呼應了我們之前看過的 `Math.sqrt(144)` 範例——只是這次平方和不是直接給定的數字，而是用 `Math.pow()` 算出來的。次方跟開根號這兩個方法搭配使用，幾何相關的計算幾乎都離不開它們。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第六部分
# Random 類別

<!--
第六部分，我們來看更強大的亂數工具：`Random` 類別。
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
`Random` 是一個物件，它比 `Math.random()` 強大多了，可以直接產生整數、布林值，還能指定種子讓結果可重現。

生活化比喻的話，`Math.random()` 就像路邊的投幣抽獎機，每次只能轉出 0 到 1 之間的數字；`Random` 類別則像是專業發牌員，我們可以指定他發「整數牌」、「布林牌」，甚至要求他用同一套牌序重新發一次。

這張表整理了 `Random` 最常用的建構子和方法：`new Random()` 用系統時間當種子（每次都不同）；`new Random(long seed)` 可以指定種子；`nextInt(n)`、`nextDouble()`、`nextBoolean()` 則分別產生不同型態的亂數。
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
這個範例的目的是：示範 `Random` 物件的常用方法，以及「種子（seed）」的特殊用法。

帶大家看關鍵行：`new Random(42)` 設定了固定的種子，這代表每次執行程式，`seeded.nextInt(100)` 都會印出完全一樣的數字！這在寫遊戲存檔或跑科學測試時非常有幫助，因為亂數變得「可以被預測、可以重現」。

⚠️ 易錯點：`nextInt(n)` 回傳的範圍是 `[0, n)`，不包含 `n`，所以骰子範例要記得加 1 才會變成 `[1, 6]`。

預期結果：`rand.nextInt(10)` 印出 0~9 之間的整數，`seeded.nextInt(100)` 每次執行的結果都相同。
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
這張表是兩者選擇的重點整理：`Math.random()` 用起來簡單、不用建立物件，適合一次性的簡單亂數；`java.util.Random` 需要建立物件，但功能更完整，能直接產生不同型態、也能指定種子重現結果。

業界實務上，如果只是要一個簡單的浮點亂數，`Math.random()` 就夠了；但如果需要產生整數範圍的亂數，或者測試時需要可重現的結果，`Random` 會是更好的選擇。
-->

---
layout: default
---

# 練習：猜數字遊戲（產生答案）
### 任務說明

請使用 `java.util.Random` 完成以下任務：

1. 建立一個 `Random` 物件
2. 使用 `nextInt(int n)` 產生一個 **1 到 50** 之間的隨機整數，作為「答案」
3. 使用 `nextBoolean()` 產生一個布林值，決定這一局是否為「困難模式」
4. 將「答案」與「是否困難模式」都印出來

<!--
【任務鋪陳】
我們剛剛認識了 `Random` 類別的 `nextInt(n)` 和 `nextBoolean()`，這題請大家用這兩個方法，準備一個猜數字遊戲的「出題」部分。

【引導思考】
`nextInt(n)` 回傳的範圍是 `[0, n)`，如果我們要 1 到 50（共 50 個數字），`n` 應該帶多少？產生完之後，記得要做跟骰子範例一樣的加法調整。
-->

---
layout: default
---

# 練習：猜數字遊戲（產生答案）
### 解題提示

```java
import java.util.Random;

Random rand = new Random();

// 1~50 的答案
int answer = rand.nextInt(50) + 1;

// 是否為困難模式
boolean hardMode = rand.nextBoolean();

System.out.println("答案：" + answer);
System.out.println("困難模式：" + hardMode);
```

<!--
【帶讀解法】
這題的重點還是 `nextInt(n)` 的範圍公式：`nextInt(50)` 回傳 `[0, 50)`，也就是 `0` 到 `49`，剛好 50 個數字；再加 `1`，就變成 `[1, 50]`，正好符合題目要求的 1 到 50。

`nextBoolean()` 不需要任何額外調整，直接呼叫就會回傳 `true` 或 `false`，各有 50% 的機率，很適合用來決定「要不要開啟困難模式」這種二選一的情境。

這跟我們前面看過的骰子範例（`rand.nextInt(6) + 1`）是同一個套路，只是把範圍從 6 換成 50。
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

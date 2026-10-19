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
大家好，這一章我們要認識 Java 內建的 Math 和 Random 類別。

寫程式時，常常需要計算平方根、四捨五入，或是產生一個隨機的數字，例如抽籤、擲骰子。這些功能如果都要自己寫，既麻煩又容易出錯。

好消息是，Java 已經幫我們準備好了。學完這一章，我們就能用內建的工具處理各種數學計算和隨機數。
-->

---
layout: default
---

# Outline

- **數學常數：Math.PI、Math.E**
- **隨機數：Math.random()，指定範圍整數**
- **max() / min()：比較兩數大小**
- **abs()：求絕對值**
- **round()：四捨五入（回傳 long/int）**
- **rint()：最接近整數值（Banker's Rounding）**
- **ceil() / floor()：無條件進位 / 捨去**
- **一般數學運算：pow()、sqrt()、cbrt()、exp()、log()、log10()**
- **三角函數：sin()、cos()、tan()、toRadians()、toDegrees()**
- **Random 類別**
- **專題：Haversine 地球兩點距離**

<!--
這一章我們會先看 Math 類別裡的數學常數和隨機數，再看比較大小和絕對值。

接著用比較多的時間，分清楚四種捨入方法的差別，這是最容易搞混的地方。然後是次方、開根號、對數這些一般數學運算，以及三角函數。

最後認識功能更完整的 Random 類別，並用 Haversine 公式，實際計算地球上兩個城市之間的距離。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Math 類別基礎

<!--
我們先從 Math 類別最基本的部分開始：數學常數和隨機數。
-->

---
layout: default
---

# 數學常數

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
Math 類別屬於 java.lang 套件，所以不需要 import。而且它所有的方法都是 static，直接用 Math 點方法名稱就能呼叫，不需要 new。這就是上一章學過的工具類別設計。

Math 提供了兩個常用的數學常數：Math.PI 是圓周率，Math.E 是自然對數的底數，大約是 2.718。

看下面的範例，用 Math.PI 乘上半徑的平方，就能算出半徑 5 的圓面積，大約是 78.54。
-->

---
layout: default
---

# Math.random()

`Math.random()` 回傳範圍為 **[0.0, 1.0)** 的 `double` 值（含 0，不含 1）。

| 用途 | 公式 |
| --- | --- |
| 取得 [0.0, 1.0) 的浮點數 | `Math.random()` |
| 取得 [0, n) 的整數 | `(int)(Math.random() * n)` |
| 取得 [min, max) 的整數 | `(int)(Math.random() * (max - min)) + min` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記憶技巧：</b>公式 <code>(int)(Math.random() * 範圍大小) + 最小值</code>，骰子範圍大小 6，最小值 1。
</div>

<!--
Math.random 會回傳一個隨機的小數，範圍是大於等於 0、小於 1。注意 0 有可能出現，但 1 不會。

實際上我們常需要的是某個範圍內的整數，例如擲骰子要 1 到 6。表格整理了換算的公式：把 Math.random 乘上範圍的大小，轉成整數之後，再加上最小值。

以骰子為例，範圍大小是 6，最小值是 1，所以就是 Math.random 乘以 6，轉成 int，再加 1。
-->

---

# Math.random() — 範例

```java
// 取得 0.0 ~ 1.0 之間的浮點數
double d = Math.random();

// 取得 0 ~ 9 的整數（共10個）
int n = (int)(Math.random() * 10);

// 取得 1 ~ 6（骰子）
int dice = (int)(Math.random() * 6) + 1;
System.out.println("骰出：" + dice);
```

<!--
我們看實際的寫法。

第一行直接取得 0 到 1 之間的小數。第二行乘以 10 再轉成整數，會得到 0 到 9 的整數，總共 10 種可能。第三行是骰子，乘以 6 轉成整數得到 0 到 5，再加 1，就變成 1 到 6。

要注意轉型的括號位置。如果寫成 int 括號只包住 Math.random，它會先被轉成 0，結果永遠是 0。所以一定要先乘完，再轉型。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 比較與絕對值

<!--
接下來是幾個很常用的小工具：比較大小和絕對值。
-->

---
layout: default
---

# max() / min()

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
Math.max 回傳兩個數字中比較大的那個，Math.min 回傳比較小的那個，整數和小數都可以使用。

看範例，10 和 20 的最大值是 20；負 5 和負 3 比較，比較大的是負 3。

最後一個例子很實用：限制數值的範圍。分數是 105，先用 max 和 0 比較，確保不會小於 0；再用 min 和 100 比較，確保不會超過 100，結果就是 100。這種把數字限制在某個範圍內的技巧，在遊戲和表單驗證裡很常見。
-->

---
layout: default
---

# abs()：求絕對值

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
Math.abs 會回傳絕對值，也就是去掉負號之後的數字。負 5 的絕對值是 5，負 3.14 是 3.14。

它最常見的用途是計算兩個數字之間的差距。30 和 75 相減是負 45，取絕對值就是 45。這樣不管誰減誰，都能得到正確的差距。

下面也提到一個少見的特殊情況：int 能表示的最小值，取絕對值之後還是負數。因為它對應的正數，已經超出了 int 的範圍。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 捨入方法詳解

<!--
接下來是這一章最容易搞混的部分：四種不同的捨入方法。
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
💡 <b>選擇建議：</b>一般四捨五入用 <code>round()</code>；需避免累積偏差用 <code>rint()</code>（金額計算建議改用 <code>BigDecimal</code>）；需要 double 回傳時用 <code>rint()</code>、<code>ceil()</code>、<code>floor()</code>。
</div>

<!--
Math 類別有四種捨入方法，它們的差別在於遇到小數時往哪個方向取，以及回傳什麼型別。

round 是我們熟悉的四捨五入，回傳整數型別。rint 也是取最接近的整數，但剛好遇到 0.5 時，會取偶數，回傳 double。ceil 是無條件進位，往大的方向取；floor 是無條件捨去，往小的方向取，兩個都回傳 double。

一般的四捨五入用 round 就好；要處理金額時，建議改用 BigDecimal。
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
這張表把同樣的數字，丟進四種方法比較。

2.3 的情況最單純：round 和 rint 都得到 2，ceil 進位到 3，floor 捨去到 2。

差異出現在 0.5 的時候。2.5 用 round 得到 3，但 rint 得到 2，因為 2 是偶數。3.5 用 rint，則會得到偶數 4。

負數也要注意：負 2.5 用 round 得到負 2，ceil 往大的方向是負 2，floor 往小的方向是負 3。
-->

---
layout: default
---

# round()：四捨五入

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
我們仔細看 round。它遇到 0.5 時，一律往正無限大的方向取整數。

所以 1.5 變成 2，2.5 變成 3，這和我們的直覺一樣。但負數就要小心了，負 1.5 往正的方向，會變成負 1；負 2.5 則變成負 2。

另外，round 的回傳型別取決於參數。傳入 float 會回傳 int，傳入 double 會回傳 long。所以把 round 的結果存進 int 變數時，如果參數是 double，就需要強制轉型。
-->

---
layout: default
---

# rint()：Banker's Rounding

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
rint 回傳最接近的整數，型別是 double。特別的是剛好 0.5 的時候，它會取最接近的偶數。

所以 1.5 和 2.5 都變成 2.0，3.5 和 4.5 都變成 4.0。不是 0.5 的情況就正常處理，2.3 是 2.0，2.7 是 3.0。

這種捨入方式叫做銀行家捨入法。為什麼要這樣做？如果每次遇到 0.5 都進位，大量數字加總起來，結果會系統性地偏大。取偶數的話，有時進位、有時捨去，長期下來誤差會互相抵消。
-->

---
layout: default
---

# ceil() / floor()

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
ceil 和 floor 的名字很好記：ceil 是天花板，一律往上取；floor 是地板，一律往下取。

所以 3.1 和 3.9 用 ceil 都得到 4.0，用 floor 都得到 3.0。

負數時方向一樣，只是要想清楚哪邊是上面。負 3.1 往上是負 3.0，往下是負 4.0。

在實際應用中，例如計算 23 個人要分幾台每台坐 5 人的車，就要用 ceil，因為就算多一個人，也要多開一台車。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 數學運算方法

<!--
接下來是一些一般的數學運算方法，包括次方、開根號和對數。
-->

---
layout: default
---

# 一般數學運算方法

| 方法 | 說明 |
| --- | --- |
| `Math.pow(a, b)` | 計算 a 的 b 次方，回傳 double |
| `Math.sqrt(a)` | 開平方根，回傳 double |
| `Math.cbrt(a)` | 開立方根，回傳 double |
| `Math.exp(a)` | 計算 e^a，回傳 double |
| `Math.log(a)` | 自然對數 ln(a)，回傳 double |
| `Math.log10(a)` | 常用對數 log₁₀(a)，回傳 double |

<!--
這一頁列出了常用的數學運算方法。

pow 計算 a 的 b 次方；sqrt 開平方根；cbrt 開立方根；exp 計算 e 的 a 次方；log 是以 e 為底的自然對數；log10 是以 10 為底的常用對數。

要注意，這些方法的回傳型別都是 double，就算算出來剛好是整數，印出來也會帶有小數點。
-->

---
layout: default
---

# 一般數學運算 — 範例

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
我們看實際的結果。

2 的 10 次方是 1024.0；144 的平方根是 12.0；27 的立方根是 3.0；e 的 1 次方就是 e 本身；以 e 為底，e 的對數是 1；以 10 為底，1000 的對數是 3。

如果需要以 2 為底的對數，Java 沒有直接提供，但可以用換底公式：log 8 除以 log 2，結果就是 3。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 三角函數

<!--
接下來是三角函數。在遊戲開發、繪圖和地理計算裡，三角函數都很常用到。
-->

---
layout: default
---

# 三角函數方法

| 方法 | 說明 |
| --- | --- |
| `Math.sin(radians)` | 正弦值，參數單位為**弧度** |
| `Math.cos(radians)` | 餘弦值，參數單位為**弧度** |
| `Math.tan(radians)` | 正切值，參數單位為**弧度** |
| `Math.asin(v)` / `acos(v)` / `atan(v)` | 反正弦 / 反餘弦 / 反正切，回傳弧度 |
| `Math.toRadians(degrees)` | 度數 → 弧度 |
| `Math.toDegrees(radians)` | 弧度 → 度數 |

<!--
Math 類別提供了 sin、cos、tan 三個三角函數，以及它們的反函數 asin、acos、atan。

最重要的一點是：「三角函數的參數單位是弧度，不是我們熟悉的角度。」

所以 Math 也提供了兩個轉換方法：toRadians 把角度轉成弧度，toDegrees 把弧度轉回角度。
-->

---
layout: default
---

# 三角函數 — 範例

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
看範例，每個三角函數的參數都先用 toRadians 轉換。sin 90 度是 1.0，cos 0 度也是 1.0。tan 45 度理論上是 1，但因為浮點數的誤差，結果會是 0.9999 多一點。

反三角函數回傳的是弧度，所以要用 toDegrees 轉回角度。asin 1 轉回來就是 90 度。

最後一個例子是直角三角形：對邊 3、斜邊 5，sin 值就是 5 分之 3，用 asin 反推回角度，大約是 36.87 度。

如果忘了轉換，直接把 90 傳給 sin，Java 會把它當成 90 弧度計算，結果就完全不對了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Random 類別

<!--
Math.random 只能產生小數。如果需要更多樣的隨機功能，就要用 Random 類別。
-->

---
layout: default
---

# Random 類別

`java.util.Random` 提供更豐富的亂數功能，需要 `import java.util.Random`。

| 方法 / 建構子 | 說明 |
| --- | --- |
| `new Random()` | 自動產生種子（每次執行結果不同）|
| `new Random(long seed)` | 指定種子（相同種子產生相同序列）|
| `nextInt(int n)` | 回傳 [0, n) 的整數 |
| `nextInt(int min, int max)` | 回傳 [min, max) 的整數（Java 17+）|
| `nextDouble()` | 回傳 [0.0, 1.0) 的 double |
| `nextBoolean()` | 回傳 true 或 false（各 50%）|
| `setSeed(long seed)` | 重新設定種子 |

<!--
Random 類別在 java.util 套件裡，使用前要 import。它需要先建立物件才能使用。

建立時如果不給參數，每次執行都會產生不同的亂數。如果傳入一個數字當作「種子」，相同的種子，就會產生完全相同的亂數序列。

常用的方法有：nextInt 傳入 n，回傳 0 到 n 減 1 的整數；Java 17 之後，nextInt 也可以直接指定最小值和最大值。nextDouble 回傳 0 到 1 之間的小數，nextBoolean 隨機回傳 true 或 false。
-->

---
layout: default
---

# Random 類別 — 範例

```java
import java.util.Random;

Random rand = new Random();

System.out.println(rand.nextInt(10));     // [0, 10) 的整數
System.out.println(rand.nextDouble());    // [0.0, 1.0) 的浮點數
System.out.println(rand.nextBoolean());   // true 或 false

// 骰子：[1, 6]
int dice = rand.nextInt(6) + 1;
int dice2 = rand.nextInt(1, 7);           // Java 17+ 寫法

// 指定種子（可重現相同序列）
Random seeded = new Random(42);
System.out.println(seeded.nextInt(100));  // 每次執行都相同
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>setSeed 用途：</b>測試或模擬時，設定固定種子可確保每次結果一致，方便重現與除錯。
</div>

<!--
我們看實際的用法。

建立 Random 物件之後，nextInt 10 會得到 0 到 9 的整數，nextDouble 得到 0 到 1 之間的小數，nextBoolean 隨機得到 true 或 false。

骰子有兩種寫法：nextInt 6 再加 1，或是 Java 17 之後的 nextInt 1、7，代表 1 到 6，不包含 7。

最後用種子 42 建立 Random，每次執行，第一個亂數都會一樣。這在寫測試或模擬時非常有用，因為相同的輸入能得到相同的結果，出問題時才能重現和除錯。
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
這張表比較了兩種產生亂數的方式。

Math.random 只能回傳 double，不能指定種子，但使用簡單，直接呼叫就好。Random 需要先建立物件，但可以產生整數、小數、布林值，也能指定種子，指定範圍時也不需要自己換算。

下面兩種骰子寫法放在一起，就能看出差別。簡單的一次性亂數用 Math.random 就夠了；需要多種型別，或是需要重現相同結果時，就用 Random。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 專題：地球兩點距離

<!--
最後，我們用一個專題，把這一章學到的數學方法整合起來：計算地球上兩點之間的距離。
-->

---
layout: default
---

# Haversine 公式

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
已知兩個地點的經緯度，要怎麼算出它們之間的距離？

地球是球體，所以不能直接用平面的距離公式。Haversine 公式把地球視為球體，利用球面幾何計算兩點之間最短的距離。

公式需要的資料，是兩點的緯度和經度，而且要轉成弧度，再加上地球半徑，大約是 6371 公里。計算過程中會先得到一個中間值 a，再算出圓心角 c，最後乘以半徑，就是距離。
-->

---
layout: default
---

# Haversine 實作 (一)

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
這是公式的實作。

先寫一個輔助方法 haversine，計算 sin 值除以 2 的平方。在 calcDistance 方法裡，先算出兩點緯度差和經度差，轉成弧度，兩個緯度本身也要轉成弧度。

接著依照公式算出 a，再用 atan2 算出圓心角 c，最後乘以地球半徑 6371，就是兩點之間的公里數。

這裡不需要記住公式的推導，重點是看到 toRadians、sin、cos、sqrt 這些方法，怎麼組合起來解決一個實際的問題。
-->

---
layout: default
---

# Haversine 實作 (二) — 測試

```java
// 台北 (25.033, 121.565)  →  東京 (35.689, 139.692)
double dist = calcDistance(25.033, 121.565, 35.689, 139.692);
System.out.printf("台北 → 東京：%.1f 公里%n", dist);
// 台北 → 東京：2098.7 公里
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>用到的 Math 方法：</b><code>Math.sin()</code>、<code>Math.cos()</code>、<code>Math.sqrt()</code>、<code>Math.atan2()</code>、<code>Math.toRadians()</code>、<code>Math.pow()</code>——一次整合本章所有方法！
</div>

<!--
我們來測試一下，計算台北到東京的距離。

把台北和東京的經緯度傳進 calcDistance，再用 printf 印到小數點後一位，結果大約是 2100 公里，和實際的飛行距離相當接近。

這個專題一次用到了這一章的 sin、cos、sqrt、atan2、toRadians 和 pow，地圖和外送 App 計算距離，用的就是類似的方法。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 練習題

<!--
接下來，我們用兩題練習，把這一章的內容實際用一遍。
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
第一題是樂透號碼產生器，模擬台灣的大樂透，從 1 到 49 選 6 個號碼。

要求有三個：抽出 6 個不重複的號碼；由小到大排序後印出；另外再抽一個特別號，而且不能和前面 6 個重複。

我們先暫停一下，自己動手試試看。

【講師】給學生約 15 分鐘實作。ArrayList 尚未正式介紹，可直接提供提示頁的寫法。
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
我們來看提示。

用 Random 的 nextInt 49 加 1，產生 1 到 49 的亂數。號碼存進 ArrayList，加入之前先用 contains 檢查有沒有重複，重複就重抽，直到有 6 個號碼為止。ArrayList 之後的章節會詳細介紹，這裡先照著用。

接著用 Collections.sort 排序。特別號也用同樣的方式，確認不和前 6 個重複。輸出時用 `%02d`，讓個位數的號碼前面補 0。
-->

---
layout: default
---

# 練習二：BMI 計算器
### 任務說明

撰寫一個程式，計算 BMI 並判斷體重狀態：

1. 輸入身高（公分）與體重（公斤）
2. 計算 BMI = 體重 / 身高²（身高換算為公尺），用 `Math.round()` 或 `Math.floor()` 取小數點一位
3. 依據以下標準輸出判斷結果：

| BMI 範圍 | 狀態 |
| --- | --- |
| < 18.5 | 體重過輕 |
| 18.5 ~ 24.9 | 正常體重 |
| 25.0 ~ 29.9 | 體重過重 |
| ≥ 30 | 肥胖 |

<!--
第二題是 BMI 計算器。

輸入身高公分和體重公斤，計算 BMI，取到小數點後一位，再依照表格判斷是體重過輕、正常、過重還是肥胖。

這一題的重點，是練習用這一章學到的捨入方法來處理小數。一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 10 分鐘實作。
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
我們來看提示。

身高先除以 100.0 換算成公尺，再用體重除以身高的平方，平方可以用 Math.pow。

要取到小數點後一位，有一個常用的技巧：先乘以 10，用 floor 捨去小數，再除以 10.0。如果想要四捨五入，就把 floor 換成 round。最後用 if-else if 判斷 BMI 落在哪個範圍。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

有任何關於 Math 或 Random 的問題嗎？

<!--
這一章我們認識了 Java 內建的數學工具。

Math 類別的方法都是 static，可以直接呼叫。Math.random 搭配公式可以產生指定範圍的整數。四種捨入方法要分清楚：round 是四捨五入，rint 遇到 0.5 取偶數，ceil 往上，floor 往下。三角函數的參數要用弧度。需要更多樣的亂數，或是要重現相同結果時，就用 Random 類別。

下一章，我們要學日期與時間的處理。

【講師】開放 Q&A。
-->

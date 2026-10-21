---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 程式基本運算
routeAlias: ch04
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
  <h1 style="color: #1a5c5c; font-size: 3.4rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">程式基本運算</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「掌握運算子與型態轉換，讓程式精準計算」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章我們要學的是程式基本運算。

上一章我們學會把資料存進變數，這一章要讓這些資料動起來：加減乘除、比較大小、組合條件，甚至直接操作二進位的位元。

副標題裡的「精準」很重要。計算稍微不精準，可能就會讓薪水少算一個零，或是 BMI 多算好幾公斤。學完這一章，我們就能寫出會讀取使用者輸入、正確計算並輸出結果的程式。
-->

---
layout: default
---

# Outline

- **程式設計的專有名詞**（運算元、運算子、運算式、敘述）
- **指定運算子的特殊用法**（鏈式賦值）
- **基本數學運算**（+, -, *, /, %; 整數除法; Math 類別）
- **複合指定運算子**（+=, -=, *=, /=, %=）
- **布林／比較／邏輯運算子**（短路求值）
- **位元運算**（&, |, ^, ~, <<, >>, >>>）
- **運算子優先順序表**
- **資料型態轉換**（自動提升、強制轉型）
- **資料的轉換與輸入**（Scanner、Integer.parseInt）
- **import 與 java.lang 套件**
- **程式敘述的結合與分行**
- **專題：溫度轉換 / 高斯數學**

<!--
這一章的內容比較多，我們先看一下路線。

我們會先認識幾個程式設計的基本名詞，接著學指定運算子、四則運算和 Math 類別，再來是比較和邏輯運算子，以及位元運算。

中段會整理運算子的優先順序，和資料型態轉換的規則。後段學怎麼用 Scanner 讀取使用者輸入、import 的用法，最後用溫度轉換和高斯數學兩個小專題，把這一章的內容串起來。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 程式設計的專有名詞

<!--
我們先從幾個基本名詞開始，之後在看文件、讀程式時，就知道這些詞在說什麼。
-->

---
layout: default
---

# 程式設計的專有名詞

| 名詞 | 英文 | 說明 |
| --- | --- | --- |
| 運算元 | operand | 被運算的對象，可以是變數、常數或運算式 |
| 運算子 | operator | 表示運算動作的符號，如 `+`、`-`、`*` |
| 運算式 | expression | 由運算元與運算子組成，會產生一個值 |
| 敘述 | statement | 一條完整的程式指令，以分號 `;` 結尾 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>說明：</b> 運算式（expression）本身有值，敘述（statement）則是一個完整的動作指令。每個敘述都由一個或多個運算式組成。
</div>

<!--
這一頁有四個名詞。

運算元，是被運算的對象，可以是變數、常數，或是另一個運算式。運算子，是代表運算動作的符號，例如加號、減號、乘號。

運算元和運算子組合起來，就是運算式，它一定會產生一個值。最後，一條完整的程式指令，以分號結尾，就叫做敘述。

下面的說明也點出了重點：運算式本身有一個值，而敘述則是一個完整的動作。
-->

---

# 程式設計的專有名詞 — 範例

```java
int a = 5;       // a 是運算元
int b = 3;       // b 是運算元
int c = a + b;   // a + b 是運算式（expression），結果為 8
                 // int c = a + b; 是一條敘述（statement）
System.out.println(c); // 8
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記憶口訣：</b> 「運算元是數字，運算子是符號，合在一起是運算式，加上分號變敘述。」
</div>

<!--
我們用實際的程式碼來對照。

a 和 b 是運算元，加號是運算子，a 加 b 是一個運算式，結果是 8。而 int c 等於 a 加 b，加上分號，整行就是一條敘述。

可以記這句口訣：「運算元是資料，運算子是符號，合在一起是運算式，加上分號變敘述。」
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 指定運算子與數學運算

<!--
名詞搞清楚了，接下來我們看指定運算子和最常用的數學運算。
-->

---
layout: default
---

# 指定運算子的特殊用法

指定運算子 `=` 的結合方向是**由右至左**，因此可以進行鏈式賦值：

```java
int a, b, c;
a = b = c = 0;   // 鏈式賦值：先 c=0, 再 b=c, 再 a=b
System.out.println(a); // 0
System.out.println(b); // 0
System.out.println(c); // 0
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>鏈式賦值（Chain Assignment）：</b> 指定運算子由右至左執行，先將最右邊的值 <code>0</code> 指定給 <code>c</code>，再指定給 <code>b</code>，最後指定給 <code>a</code>。
</div>

<!--
在 Java 裡，等號不是數學上的「相等」，而是「指定」，意思是把右邊的值放進左邊的變數。

指定運算子有一個特性：它是由右往左執行的。所以可以寫出 a 等於 b 等於 c 等於 0 這種「鏈式賦值」。

Java 會先把 0 指定給 c，再把 c 的值指定給 b，最後指定給 a，三個變數就都變成 0。需要一次初始化好幾個變數時，這個寫法很方便。
-->

---

# 基本數學運算子

| 運算子 | 名稱 | 說明 | 範例（a=10, b=3） |
| --- | --- | --- | --- |
| `+` | 加法 | 兩數相加 | `a + b` → `13` |
| `-` | 減法 | 兩數相減 | `a - b` → `7` |
| `*` | 乘法 | 兩數相乘 | `a * b` → `30` |
| `/` | 除法 | 整數相除取商（無小數） | `a / b` → `3` |
| `%` | 取餘數 | 整數相除取餘數 | `a % b` → `1` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>整數除法：</b> 當兩個 <code>int</code> 相除時，結果自動截去小數部分。例如 <code>10 / 3 = 3</code>，不是 <code>3.33</code>。
</div>

<!--
基本數學運算子有五個：加、減、乘、除，以及取餘數。

前三個和數學課學的一樣，但除法有一個很重要的陷阱：「兩個整數相除，結果還是整數。」10 除以 3，在 Java 裡的結果是 3，不是 3.33，小數部分會直接被截掉。

取餘數用百分比符號，10 除以 3 商 3 餘 1，所以結果是 1。判斷奇偶數、計算分頁時，都很常用到它。
-->

---

# 基本數學運算 — 範例

```java
int a = 10, b = 3;
System.out.println(a + b);  // 13
System.out.println(a - b);  // 7
System.out.println(a * b);  // 30
System.out.println(a / b);  // 3（整數除法，截去小數）
System.out.println(a % b);  // 1（取餘數）

double x = 10.0, y = 3.0;
System.out.println(x / y);  // 3.3333333333333335
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>浮點數除法：</b> 若其中一個運算元為 <code>double</code>，結果也是 <code>double</code>，會保留小數。
</div>

<!--
我們看實際的執行結果。

a 是 10，b 是 3。加減乘的結果分別是 13、7、30；a 除以 b 是 3，因為是整數除法；a 取餘數 b 是 1。

再看下面，x 和 y 都是 double，相除的結果就會保留小數，是 3.333。只要其中一個運算元是 double，結果就會是 double。

所以在算平均、算 BMI 這類需要小數的情況，要記得讓其中一個數字是 double，例如寫成 10.0 除以 3，否則小數就會不知不覺被丟掉。
-->

---

# Math 類別常用方法

| 方法 | 說明 | 範例 |
| --- | --- | --- |
| `Math.abs(x)` | 取絕對值 | `Math.abs(-5)` → `5` |
| `Math.pow(x, y)` | 次方（x 的 y 次方） | `Math.pow(2, 3)` → `8.0` |
| `Math.sqrt(x)` | 平方根 | `Math.sqrt(9)` → `3.0` |
| `Math.max(x, y)` | 兩數中的最大值 | `Math.max(3, 7)` → `7` |
| `Math.min(x, y)` | 兩數中的最小值 | `Math.min(3, 7)` → `3` |
| `Math.round(x)` | 四捨五入 | `Math.round(3.6)` → `4` |
| `Math.PI` | 圓周率常數 | `3.141592653589793` |

<!--
除了基本運算，Java 還提供了 Math 類別，裡面有很多現成的數學方法。

abs 取絕對值，pow 計算次方，sqrt 開平方根，max 和 min 找出兩個數字中比較大和比較小的那個，round 則是四捨五入。另外，Math.PI 是圓周率常數。

要注意，pow 和 sqrt 的結果是 double，所以 2 的 3 次方印出來會是 8.0。
-->

---

# Math 類別 — 範例

```java
System.out.println(Math.abs(-10));      // 10
System.out.println(Math.pow(2, 8));     // 256.0
System.out.println(Math.sqrt(144));     // 12.0
System.out.println(Math.max(42, 99));   // 99
System.out.println(Math.round(3.5));    // 4
double area = Math.PI * Math.pow(5, 2); // 圓面積
System.out.println(area);              // 78.53981633974483
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>java.lang.Math：</b> Math 類別屬於 <code>java.lang</code> 套件，不需要 <code>import</code> 即可使用。
</div>

<!--
我們看一下這些方法的實際結果。

負 10 的絕對值是 10，2 的 8 次方是 256.0，144 的平方根是 12.0，42 和 99 比較大的是 99，3.5 四捨五入是 4。

倒數第二行把它們組合起來計算圓面積：圓周率乘上半徑 5 的平方，結果大約是 78.54。

Math 類別屬於 java.lang 套件，不需要 import，直接就能使用。
-->

---

# 複合指定運算子

| 運算子 | 等效寫法 | 說明 |
| --- | --- | --- |
| `a += b` | `a = a + b` | 加後賦值 |
| `a -= b` | `a = a - b` | 減後賦值 |
| `a *= b` | `a = a * b` | 乘後賦值 |
| `a /= b` | `a = a / b` | 除後賦值 |
| `a %= b` | `a = a % b` | 取餘後賦值 |

<!--
接下來是複合指定運算子，它是把運算和指定合在一起的簡寫。

例如 a 加等於 b，意思就是 a 等於 a 加 b。減、乘、除和取餘數，也都有對應的寫法。

結果完全一樣，只是寫起來更簡潔，在計數、累加的時候非常常見。
-->

---

# 複合指定運算子 — 範例

```java
int score = 100;
score += 20;    // score = 120
score -= 10;    // score = 110
score *= 2;     // score = 220
score /= 4;     // score = 55
score %= 7;     // score = 6（55 除以 7 餘 6）
System.out.println(score); // 6
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>優點：</b> 複合運算子讓程式碼更簡潔，並略為提升可讀性。<code>a += 1</code> 和 <code>a++</code> 效果相同，但 <code>+=</code> 可搭配任意數值。
</div>

<!--
我們用一個分數來看看它們的效果。

score 一開始是 100。加等於 20 變成 120，減等於 10 變成 110，乘等於 2 變成 220，除等於 4 變成 55，最後取餘數 7，55 除以 7 餘 6，所以最後印出 6。

和 a 加加比起來，加等於可以搭配任何數值，用途更廣。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 布林、比較與邏輯運算子

<!--
接下來是會產生 true 或 false 的運算子：比較運算子和邏輯運算子。它們是之後所有條件判斷的基礎。
-->

---
layout: default
---

# 比較（關係）運算子

| 運算子 | 說明 | 範例（a=5, b=3） | 結果 |
| --- | --- | --- | --- |
| `==` | 等於 | `a == b` | `false` |
| `!=` | 不等於 | `a != b` | `true` |
| `>` | 大於 | `a > b` | `true` |
| `<` | 小於 | `a < b` | `false` |
| `>=` | 大於等於 | `a >= 5` | `true` |
| `<=` | 小於等於 | `a <= 3` | `false` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>常見錯誤：</b> 勿將 <code>==</code>（比較）與 <code>=</code>（指定）混淆！<code>if (a = 5)</code> 在 Java 中會編譯錯誤，因為條件式需要布林值。
</div>

<!--
比較運算子用來比較兩個值，結果一定是 true 或 false。

兩個等號是等於，驚嘆號加等號是不等於，另外還有大於、小於、大於等於、小於等於。看表格裡的例子，a 是 5、b 是 3，a 等於 b 是 false，a 不等於 b 是 true。

下面的提醒要特別注意：「比較用兩個等號，指定用一個等號。」初學者很常把 if 括號裡的兩個等號寫成一個，在 Java 裡這樣會編譯錯誤，因為條件需要的是布林值。
-->

---

# 邏輯運算子

| 運算子 | 名稱 | 說明 | 範例 |
| --- | --- | --- | --- |
| `&&` | 邏輯 AND | 兩者皆為 true 才為 true | `(a>0) && (b>0)` |
| `\|\|` | 邏輯 OR | 其中一者為 true 即為 true | `(a>0) \|\| (b>0)` |
| `!` | 邏輯 NOT | 反轉布林值 | `!(a > 0)` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>短路求值（Short-Circuit Evaluation）：</b> <code>&&</code> 若左側為 <code>false</code>，右側不執行；<code>||</code> 若左側為 <code>true</code>，右側不執行。這可避免不必要的計算或錯誤（如除以零）。
</div>

<!--
邏輯運算子用來組合多個條件。

AND，用兩個 and 符號表示，兩個條件都是 true，結果才是 true。就像進考場，要有准考證，而且要帶文具。OR，用兩條直線表示，只要其中一個是 true，結果就是 true。NOT，用驚嘆號表示，會把 true 和 false 反過來。

下面提到一個重要特性，叫「短路求值」。用 AND 時，如果左邊已經是 false，右邊就不會執行；用 OR 時，如果左邊已經是 true，右邊也不會執行。因為結果已經確定了，不需要再看右邊。
-->

---

# 邏輯運算子 — 範例

```java
int score = 85;
boolean passed = score >= 60;
boolean excellent = score >= 90;

System.out.println(passed && excellent);   // false（85>=60 true, 85>=90 false）
System.out.println(passed || excellent);   // true（任一為 true）
System.out.println(!passed);              // false

// 短路求值示範
int x = 0;
boolean result = (x != 0) && (100 / x > 1); // 不會除以零
System.out.println(result); // false，右側因短路未執行
```

<!--
我們看範例。分數是 85，所以 passed 是 true，excellent 是 false。

passed AND excellent，因為有一個是 false，結果是 false。passed OR excellent，只要一個是 true，結果就是 true。NOT passed，則是 false。

下面示範短路求值的用處。x 是 0，如果直接計算 100 除以 x，程式會因為除以零而出錯。但左邊先判斷 x 不等於 0，結果是 false，右邊的除法根本不會執行，所以程式安全地得到 false。

把可能出錯的條件放在右邊，用左邊的條件先擋住，是很常見的寫法。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 位元運算子

<!--
接下來是位元運算子。它們不是對整個數字運算，而是對數字的每一個二進位位元做運算。
-->

---
layout: default
---

# 位元邏輯運算子

| 運算子 | 名稱 | 說明 |
| --- | --- | --- |
| `&` | 位元 AND | 兩個位元皆為 1 才得 1 |
| `\|` | 位元 OR | 任一位元為 1 即得 1 |
| `^` | 位元 XOR | 兩個位元不同才得 1 |
| `~` | 位元 NOT | 所有位元取反（~N = -(N+1)） |

```java
int a = 5;  // 二進位：0101
int b = 7;  // 二進位：0111
System.out.println(a & b);  // 5  (0101)
System.out.println(a | b);  // 7  (0111)
System.out.println(a ^ b);  // 2  (0010)
System.out.println(~a);     // -6 (位元全反)
```

<!--
位元運算子和邏輯運算子很像，但它們是針對二進位的每一個位元逐一處理。

5 的二進位是 0101，7 是 0111。做位元 AND，兩個位元都是 1 才得 1，結果是 0101，也就是 5。做位元 OR，任一位元是 1 就得 1，結果是 0111，也就是 7。XOR 是兩個位元不同才得 1，結果是 0010，也就是 2。

最後的波浪號是位元 NOT，會把每個位元反轉，5 反轉後是負 6。可以記一個規律：反轉 N，結果是負的 N 加 1。
-->

---

# 位元移位運算子

| 運算子 | 名稱 | 說明 | 等效 |
| --- | --- | --- | --- |
| `<<` | 左移 | 位元向左移，右補 0 | `n * 2^位移量` |
| `>>` | 帶符號右移 | 位元向右移，左補符號位 | `n / 2^位移量` |
| `>>>` | 無符號右移 | 位元向右移，左補 0（正負數皆補 0）| 只補 0 |

```java
int n = 4;         // 二進位：00000100
System.out.println(n << 2);  // 16（左移 2 位，等於 4 * 4）
System.out.println(n >> 1);  // 2 （右移 1 位，等於 4 / 2）
System.out.println(-1 >>> 1); // 2147483647（最大正整數）
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>用途：</b> 位移運算比乘除法更快，常用於底層效能最佳化或旗標（flag）管理。
</div>

<!--
另一類是位移運算子，把所有位元往左或往右移動。

左移是向左移，右邊補 0，每左移一位就相當於乘以 2。所以 4 左移 2 位，等於 4 乘 4，結果是 16。

右移是向右移，每右移一位相當於除以 2，4 右移 1 位結果是 2。它會用原本的正負號位元來補左邊，所以負數右移之後還是負數。

三個大於符號是無號右移，不管正負，左邊一律補 0。所以負 1 無號右移 1 位，會變成 int 能表示的最大正整數。

位元運算在底層程式和效能最佳化中比較常見，一般應用程式較少用到，但面試很常考，至少要認得。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 運算子優先順序

<!--
運算子這麼多，如果一個運算式裡同時出現好幾個，誰先算？這就要看運算子的優先順序。
-->

---
layout: default
---

# 運算子優先順序表（高 → 低）

| 優先順序 | 運算子 | 結合方向 |
| --- | --- | --- |
| 1（最高） | `()` 括號、`[]` 陣列索引、`.` 成員存取 | 左至右 |
| 2 | `++` `--`（後置）、`+` `-`（一元）、`~` `!` | 右至左 |
| 3 | `*` `/` `%` | 左至右 |
| 4 | `+` `-` | 左至右 |
| 5 | `<<` `>>` `>>>` | 左至右 |
| 6 | `<` `>` `<=` `>=` `instanceof` | 左至右 |
| 7 | `==` `!=` | 左至右 |

<!--
就像數學的先乘除後加減，Java 的運算子也有優先順序。這張表由上往下，優先順序從高到低。

最優先的是括號、陣列索引和點；接著是遞增遞減、正負號和 NOT 這類一元運算子；然後才是乘、除、取餘數，再來是加減。之後依序是位移、大小比較，以及等於和不等於。

表格最右邊的結合方向，是指同樣優先順序的運算子出現時，要從左往右，還是從右往左計算。
-->

---

# 運算子優先順序表（續）

| 優先順序 | 運算子 | 結合方向 |
| --- | --- | --- |
| 8 | `&`（位元 AND） | 左至右 |
| 9 | `^`（位元 XOR） | 左至右 |
| 10 | `\|`（位元 OR） | 左至右 |
| 11 | `&&`（邏輯 AND） | 左至右 |
| 12 | `\|\|`（邏輯 OR） | 左至右 |
| 13 | `?:`（三元運算子） | 右至左 |
| 14（最低） | `=` `+=` `-=` `*=` `/=` `%=`（指定類） | 右至左 |

<!--
接續上一頁，再往下是位元的 AND、XOR、OR，然後是邏輯 AND、邏輯 OR、三元運算子，優先順序最低的是指定類的運算子，像是等號和加等於。

注意三元運算子和指定運算子，結合方向是從右往左。例如 `a = b = 5`，會先把 5 指定給 b，再把 b 的值指定給 a。
-->

---

# 運算子優先順序 — 範例

```java
// 優先順序示範
int result = 100 + 200 / 10 - 3 * 10;
// 先算 /  和 *：200/10=20，3*10=30
// 再算 + 和 -：100+20=120，120-30=90
System.out.println(result); // 90
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>建議：</b> 遇到不確定優先順序時，用括號 <code>()</code> 明確標示，讓程式碼更易讀。
</div>

<!--
表這麼長，我們用一個範例實際算算看。100 加 200 除以 10 減 3 乘 10，會先算除法和乘法，得到 20 和 30；再從左到右算加減，100 加 20 減 30，結果是 90。

這張表不需要全部背起來。實務上的建議是：「不確定優先順序時，就加上括號。」程式不但不會出錯，讀的人也一目了然。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 資料型態轉換

<!--
接下來我們看，不同型態的資料一起運算時，Java 是怎麼處理型態轉換的。
-->

---
layout: default
---

# 自動型態提升（Numeric Promotion）

當兩個不同型態的數值進行運算時，Java 會自動提升到較大的型態：

| 自動提升規則 | 說明 |
| --- | --- |
| 有 `double` → 提升為 `double` | 最高優先 |
| 有 `float` → 提升為 `float` | 次之 |
| 有 `long` → 提升為 `long` | 再次 |
| 否則 → 提升為 `int` | 預設 |

```java
byte op1 = 4;
byte op2 = 5;
// byte + byte → 自動提升為 int，需強制轉型
byte result = (byte)(op1 + op2); // 9
int auto = op1 + op2;            // 9（直接存入 int）
```

<!--
兩個不同型態的數值一起運算時，Java 會自動把它們提升到比較大的型態。

規則是：只要有 double，就提升成 double；沒有 double 但有 float，就是 float；再來是 long；如果都沒有，就提升成 int。

最後一條很容易踩坑。看範例，兩個 byte 相加，結果其實是 int，不是 byte。所以要存回 byte，就必須強制轉型；直接存進 int 則沒有問題。
-->

---

# 強制型態轉換（Casting）

當要把大型態轉為小型態時，需要**明確強制轉型**，可能有精度損失：

```java
double pi = 3.14159;
int n = (int) pi;        // 截去小數，n = 3

long bigNum = 9999999999L;
int small = (int) bigNum; // 溢位，結果不正確！

int x = 65;
char ch = (char) x;      // ch = 'A'（ASCII 65）
System.out.println(ch);  // A
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>型態提升方向：</b> <code>byte → short → int → long → float → double</code>（由窄到寬為自動，由寬到窄需強制）
</div>

<!--
當我們要把大型態轉成小型態時，就要強制轉型，而且可能會遺失資料。

第一個例子，3.14159 轉成 int，小數直接被截掉，結果是 3。第二個例子，把超出 int 範圍的 long 轉成 int，會發生溢位，得到一個完全不正確的數字，這種錯誤很難察覺。

第三個例子比較有趣，把整數 65 轉成 char，會得到字母 A，因為 A 的字元編碼就是 65。

下面的提示整理了方向：從 byte 到 double，由窄到寬是自動的，由寬到窄就要強制轉型。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 資料的轉換與輸入

<!--
到目前為止，程式裡的資料都是我們事先寫好的。接下來我們學怎麼讓使用者自己輸入資料。
-->

---
layout: default
---

# Scanner 類別基本方法

| 方法 | 說明 |
| --- | --- |
| `nextInt()` | 讀取下一個整數 |
| `nextDouble()` | 讀取下一個浮點數 |
| `nextLine()` | 讀取一整行文字（含空格） |
| `next()` | 讀取下一個以空白分隔的字串 |
| `hasNextInt()` | 確認下一個輸入是否為整數 |
| `close()` | 關閉 Scanner，釋放資源 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b> <code>nextInt()</code> 讀完數字後不會消耗換行符號，若接著呼叫 <code>nextLine()</code> 會讀到空字串。解法是在中間多呼叫一次 <code>scanner.nextLine()</code>。
</div>

<!--
Java 讀取鍵盤輸入，最常用的是 Scanner 類別。

nextInt 讀取一個整數，nextDouble 讀取一個小數，nextLine 讀取一整行文字，包含空格；next 則只讀到空白為止。hasNextInt 可以先確認下一個輸入是不是整數，用完之後記得呼叫 close 關閉。

下面的提醒是一個很常見的陷阱：nextInt 讀完數字之後，不會把後面的換行讀走。如果接著呼叫 nextLine，它會直接讀到那個換行，得到一個空字串。解決方法是在中間多呼叫一次 nextLine，把殘留的換行清掉。
-->

---

# Scanner 基本輸入 — 範例

```java
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);
System.out.print("請輸入整數：");
int age = scanner.nextInt();

System.out.print("請輸入小數：");
double salary = scanner.nextDouble();

scanner.nextLine(); // 消耗殘留換行
System.out.print("請輸入姓名：");
String name = scanner.nextLine();

System.out.println(name + "，年齡 " + age + "，薪水 " + salary);
scanner.close();
```

<!--
我們看一個完整的例子。

第一行先 import Scanner，再用 System.in 建立 Scanner，表示從鍵盤讀取。接著依序讀入整數年齡和小數薪水。

注意中間那一行 nextLine，它就是用來清掉剛才殘留的換行，這樣下一行讀姓名時，才會讀到使用者真正輸入的內容。

最後把三筆資料組合起來印出，並關閉 Scanner。
-->

---

# 字串轉數值（parseInt / parseDouble）

| 方法 | 說明 | 範例 |
| --- | --- | --- |
| `Integer.parseInt(str)` | 將字串轉為 `int` | `Integer.parseInt("42")` → `42` |
| `Double.parseDouble(str)` | 將字串轉為 `double` | `Double.parseDouble("3.14")` → `3.14` |
| `Integer.toString(n)` | 將 `int` 轉為字串 | `Integer.toString(99)` → `"99"` |
| `String.valueOf(n)` | 任意型態轉為字串 | `String.valueOf(3.14)` → `"3.14"` |

```java
String numStr = "100";
int n = Integer.parseInt(numStr);   // 100
double d = Double.parseDouble("9.8"); // 9.8
System.out.println(n + 1);          // 101
```

<!--
有時候資料一開始是字串，例如從檔案或網頁讀進來的內容，這時候就需要把字串轉成數字。

Integer.parseInt 把字串轉成 int，Double.parseDouble 把字串轉成 double。反過來，要把數字轉成字串，可以用 Integer.toString，或是可以接受任何型態的 String.valueOf。

看範例，字串 100 經過 parseInt 之後變成整數 100，所以加 1 會得到 101。如果沒有轉換，直接用字串加 1，得到的會是字串 1001。

另外要注意，如果字串的內容不是數字，例如 abc，轉換時就會發生錯誤。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# import 與套件

<!--
剛才用 Scanner 時寫了 import。接下來我們看看，什麼時候需要 import，什麼時候不需要。
-->

---
layout: default
---

# import 與 java.lang 套件

| 套件 | 說明 | 需要 import？ |
| --- | --- | --- |
| `java.lang` | 最核心套件（String, Math, System...） | **不需要**，自動匯入 |
| `java.util` | 工具類別（Scanner, ArrayList...） | **需要** `import java.util.*` |
| `java.io` | 輸入/輸出（File, IOException...） | **需要** `import java.io.*` |

```java
// java.lang 自動匯入，不需 import
System.out.println("Hello");   // System 屬於 java.lang
String s = "Java";             // String 屬於 java.lang
double d = Math.PI;            // Math 屬於 java.lang

// java.util 需要明確 import
import java.util.Scanner;
Scanner sc = new Scanner(System.in);
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>java.lang：</b> 這個套件如此基礎，Java 編譯器會自動幫你匯入，無需手動 <code>import</code>。
</div>

<!--
Java 的類別是用套件來分類的，使用其他套件的類別時，通常要先 import。

但有一個例外：java.lang 套件。它包含 String、Math、System 這些最核心的類別，Java 會自動幫我們匯入，所以不需要寫 import。

java.util 裡有 Scanner、ArrayList 這些工具類別，java.io 則是處理輸入輸出的類別，使用時都需要 import。

看範例，System、String、Math 都直接拿來用；而 Scanner 則要先 import java.util.Scanner。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 程式敘述的結合與分行

<!--
最後一個小主題：程式敘述要怎麼結合和分行，才會比較好讀。
-->

---
layout: default
---

# 程式敘述的結合與分行

Java 的敘述以 `;` 為結束，不依賴換行。因此可以：

| 規則 | 說明 |
| --- | --- |
| 一行多敘述 | 用 `;` 分隔，允許但不推薦（降低可讀性） |
| 一敘述多行 | 在適當位置換行，增加可讀性 |
| 長運算式拆行 | 在運算子**前**或**後**換行皆可 |

```java
// 一行多敘述（合法但不推薦）
int a = 1; int b = 2; int c = 3;

// 一敘述分多行（推薦，增加可讀性）
double result = 100.0
              + 200.0
              - 50.0;
System.out.println(result); // 250.0
```

<!--
Java 是用分號判斷一條敘述的結束，而不是看換行。

所以，一行裡面可以寫好幾條敘述，只要用分號隔開就好，但這樣會降低可讀性，不建議這麼做。

反過來，一條很長的敘述，可以拆成好幾行來寫。像下面這個例子，把加法和減法分別放在不同行，結果一樣是 250.0，但更容易閱讀。實際開發時，很長的運算式通常會在運算子的位置換行。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 專題實作

<!--
最後，我們用兩個小專題，把這一章學到的運算子、型態轉換和輸入串起來。
-->

---
layout: default
---

# 專題一：溫度轉換（°C ↔ °F）

| 公式 | 說明 |
| --- | --- |
| `°F = °C × 9 / 5 + 32` | 攝氏轉華氏 |
| `°C = (°F - 32) × 5 / 9` | 華氏轉攝氏 |

```java
import java.util.Scanner;
Scanner sc = new Scanner(System.in);
System.out.print("請輸入攝氏溫度：");
double celsius = sc.nextDouble();
double fahrenheit = celsius * 9.0 / 5.0 + 32;
System.out.printf("%.1f°C = %.1f°F%n", celsius, fahrenheit);
sc.close();
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意除法：</b> 使用 <code>9.0 / 5.0</code> 而非 <code>9 / 5</code>，避免整數除法導致結果為 <code>1</code>（正確應為 <code>1.8</code>）。
</div>

<!--
第一個專題是溫度轉換。攝氏轉華氏的公式，是攝氏乘以 9，除以 5，再加 32。

程式先用 Scanner 讀入攝氏溫度，套用公式計算華氏溫度，最後用 printf 印出到小數點後一位。例如輸入 25，就會印出 25.0 度 C 等於 77.0 度 F。

注意公式裡寫的是 9.0 除以 5.0，而不是 9 除以 5。如果寫成整數，9 除以 5 會因為整數除法變成 1，而不是 1.8，整個結果就錯了。這正是我們這一章一開始提到的陷阱。
-->

---

# 專題二：高斯數學（1 + 2 + ... + n）

計算從 1 加到 n 的總和，使用**高斯公式** `sum = n × (n + 1) / 2`：

```java
import java.util.Scanner;
Scanner sc = new Scanner(System.in);
System.out.print("請輸入 n：");
int n = sc.nextInt();

// 方法一：迴圈累加
int sumLoop = 0;
for (int i = 1; i <= n; i++) sumLoop += i;

// 方法二：高斯公式（一行搞定）
int sumGauss = n * (n + 1) / 2;

System.out.println("迴圈結果：" + sumLoop);
System.out.println("高斯公式：" + sumGauss);
sc.close();
```

<!--
第二個專題是高斯數學，計算從 1 加到 n 的總和。

方法一是用迴圈，從 1 一個一個加到 n。迴圈下一章才會詳細介紹，這裡先看看它的效果。方法二是高斯公式：n 乘以 n 加 1，再除以 2，一行就算完。

兩種方法的結果一樣，例如輸入 100，都會得到 5050。但當 n 很大時，公式只需要算一次，迴圈卻要跑 n 次。同樣的問題，好的方法可以省下大量的計算。
-->

---
layout: default
---

# 練習一：BMI 計算機
### 任務說明

撰寫一個程式，讓使用者輸入**身高（cm）**和**體重（kg）**，計算並顯示 BMI 值。

**BMI 公式：** `BMI = 體重(kg) / (身高(m))²`

- 輸入身高 170，體重 65 → BMI 約為 22.49
- BMI < 18.5：體重過輕；18.5~24.9：正常；>= 25：過重

**要求：**
1. 使用 Scanner 讀取輸入
2. 注意身高需從 cm 轉換為 m（除以 100.0）
3. BMI 值以小數點後兩位輸出（使用 `printf` 或 `Math.round`）

<!--
接下來我們動手練習。

第一題是 BMI 計算機。讓使用者輸入身高公分和體重公斤，計算並顯示 BMI。公式是體重除以身高公尺的平方。例如身高 170、體重 65，BMI 大約是 22.49。

有兩個地方要注意：身高要從公分換算成公尺，而且結果要顯示到小數點後兩位。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
-->

---

# 練習一：BMI 計算機
### 解題提示

1. 使用 `Scanner.nextDouble()` 讀取身高與體重
2. 將身高 cm 轉換為 m：`double heightM = height / 100.0;`
3. 計算 BMI：`double bmi = weight / Math.pow(heightM, 2);`
4. 格式化輸出：`System.out.printf("BMI = %.2f%n", bmi);`
5. 加入 `if-else` 判斷體重範圍（進階）

```java
// 關鍵程式碼片段
double heightM = height / 100.0;
double bmi = weight / (heightM * heightM);
System.out.printf("BMI = %.2f%n", bmi);
```

<!--
我們來看提示。

用 nextDouble 讀入身高和體重。身高除以 100.0 換算成公尺，記得用 100.0，避免整數除法。接著用體重除以身高的平方，可以用 Math.pow，也可以直接乘兩次。最後用 printf 搭配 `%.2f`，印出小數點後兩位。

如果還有餘力，可以再加上判斷，依照 BMI 顯示體重過輕、正常或過重，這會用到下一章的 if-else。
-->

---
layout: default
---

# 練習二：進階位元操作
### 任務說明

給定一個整數，使用位元運算子回答以下問題：

1. 這個整數是偶數還是奇數？（提示：用 `&` 運算子）
2. 這個整數乘以 4 的結果為何？（提示：用左移運算子）
3. 這個整數除以 2 的結果為何？（提示：用右移運算子）

**要求：** 使用 Scanner 讀取整數輸入，並分別用位元運算子完成以上三個計算。

<!--
第二題練習位元運算。

讓使用者輸入一個整數，用位元運算子回答三個問題：這個數是偶數還是奇數？乘以 4 的結果是多少？除以 2 的結果是多少？

提示是：判斷奇偶用位元 AND，乘以 4 用左移，除以 2 用右移。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 10 分鐘實作。
-->

---

# 練習二：進階位元操作
### 解題提示

1. **判斷奇偶：** `(n & 1) == 0` 表示偶數，`(n & 1) == 1` 表示奇數（最低位元為 0 代表偶數）
2. **乘以 4：** `n << 2`（左移 2 位 = 乘以 2² = 乘以 4）
3. **除以 2：** `n >> 1`（右移 1 位 = 除以 2，向下取整）

```java
int n = 12;
System.out.println((n & 1) == 0 ? "偶數" : "奇數"); // 偶數
System.out.println(n << 2);   // 48（12 * 4）
System.out.println(n >> 1);   // 6 （12 / 2）
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>效能：</b> 位元運算子執行速度比算術運算更快，是底層程式與效能最佳化的利器。
</div>

<!--
我們來看提示。

判斷奇偶時，把 n 和 1 做位元 AND。二進位的最後一位是 0 就是偶數，是 1 就是奇數，所以結果等於 0 就是偶數。

乘以 4，就是左移 2 位，因為 2 的 2 次方是 4。除以 2，就是右移 1 位。

以 12 為例，它是偶數，乘以 4 是 48，除以 2 是 6。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學了很多種運算子。

四則運算要小心整數除法會截掉小數；比較用兩個等號，指定用一個等號；邏輯運算有短路求值，可以用來避免錯誤；位元運算直接處理二進位。運算子的優先順序不確定時，就加上括號。

我們也知道了自動型態提升和強制轉型的規則，學會用 Scanner 讀取輸入、用 parseInt 把字串轉成數字。下一章，我們要讓程式學會做判斷。

【講師】開放 Q&A。
-->

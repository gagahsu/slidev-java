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
【開場白】
大家好！上一章我們學會了怎麼分類資料，今天我們要來學「怎麼叫資料去工作」。沒有運算的程式就像是沒有靈魂的空殼。今天我們要學會所有的運算子，從簡單的加減乘除到讓新手崩潰的「位元運算」。準備好讓你的程式動起來了嗎？

【為什麼要學這個？】
這就像是在學格鬥遊戲。上一章我們學會了「輕拳、重拳、防禦」，這一章我們要學「連段」和「必殺技」。如果你搞不清楚運算的優先順序，你的程式就會像是在關鍵時刻放錯大招的豬隊友一樣。

【今天學完你會能做什麼】
學完這章，你能寫出一個可以跟使用者對話的程式（Scanner），還能算 BMI、轉換溫度，甚至還能玩一些位元操作的小特技。
-->

---
layout: default
---

# Outline

- **4-1 程式設計的專有名詞**（運算元、運算子、運算式、敘述）
- **4-2 指定運算子的特殊用法**（鏈式賦值）
- **4-3 基本數學運算**（+, -, *, /, %; 整數除法; Math 類別）
- **4-4 複合指定運算子**（+=, -=, *=, /=, %=）
- **4-5 布林／比較／邏輯運算子**（短路求值）
- **4-6 位元運算**（&, |, ^, ~, <<, >>, >>>）
- **4-7 運算子優先順序表**
- **4-8 資料型態轉換**（自動提升、強制轉型）
- **4-9 資料的轉換與輸入**（Scanner、Integer.parseInt）
- **4-10 import 與 java.lang 套件**
- **4-11 程式敘述的結合與分行**
- **4-12 專題：溫度轉換 / 高斯數學**

<!--
【核心說明】
這章內容有點多，就像是菜單上的全餐。

【生活化比喻】
這就像是你要開始組裝一台電腦。我們要先認識螺絲釘（專有名詞），學會怎麼鎖（指定運算），最後組裝出完整的機器（專題實作）。我們會從最簡單的 1+1 開始，一路衝到高斯數學。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 程式設計的專有名詞

<!--
【開場白】
在工程師的聚會裡，如果你說「那個加號左邊的數字」，大家會覺得你是外行。我們要學會用專業術語來聊天。
-->

---
layout: default
---

# 4-1 程式設計的專有名詞

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
【核心說明】
這四個詞是溝通的基礎。

【生活化比喻】
operand（運算元）就像是「受害者」。operator（運算子）就像是「凶器」。expression（運算式）就像是「案發現場」。statement（敘述）則是「結案報告」。

💼 業界實務：
如果你在 Code Review 時說「這個 expression 的結果怪怪的」，這聽起來就很有十年老工程師的感覺。
-->

---

# 4-1 程式設計的專有名詞 — 範例

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
【逐步解說】
看這裡，int c = a + b;。a + b 是運算式，它會算出一個「8」。然後透過 = 這個指定運算子，把 8 塞進 c 裡面。最後加上 ; 這個結尾，這就是一條完整的「敘述」。

⚠️ 學生常見誤解：
別忘了那個分號！Java 沒有分號就會像是在講電話講到一半突然斷訊一樣。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 指定運算子與數學運算

<!--
【開場白】
接下來講數學。放心，我們不用解微積分，只要會國小的加減乘除就好。
-->

---
layout: default
---

# 4-2 指定運算子的特殊用法

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
【核心說明】
= 不是「等於」，它是「塞進去」。

【生活化比喻】
a = b = c = 0; 這就像是排隊。先讓 c 變成 0，然後 b 跟著 c 變 0，最後 a 跟著 b 變 0。這叫「鏈式賦值」。

⚠️ 學生常見誤解：
記住方向是由右往左！這跟我們平常讀書的方向相反，這就是為什麼很多工程師頭髮比較少的原因之一。
-->

---

# 4-3 基本數學運算子

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
【核心說明】
注意那個除號 /，它是個「無情的小數點收割機」。

【生活化比喻】
10 / 3 在 Java 裡不是 3.33，而是 3。就像是你有 10 塊錢，一個麵包 3 塊，你只能買 3 個，老闆不會賣你 0.33 個麵包。如果你想要剩下的那一塊錢？請用 %（取餘數）。

⚠️ 學生常見誤解：
初學者最常忘了 % 的存在。% 在判斷奇偶數、閏年時超級好用！
-->

---

# 4-3 基本數學運算 — 範例

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
【逐步解說】
看這兩組。如果你用整數 int 去除，小數點會直接消失。如果你用小數 double 去除，小數點才會長出來。

💼 業界實務：
如果你想要精確的小數（例如 3.33），請確保除號兩邊至少有一個是 double。這在算平均分數時常出錯。
-->

---

# 4-3 Math 類別常用方法

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
【核心說明】
這是 Java 內建的「大腦」。

【生活化比喻】
如果你想算次方、開根號、或者是四捨五入，不要自己寫，去問 Math 就好了。它就像是你的高級計算機。

⚠️ 學生常見誤解：
Math.round 是四捨五入，但它回傳的是 long 型態。如果你想把它存回 int，別忘了轉型。
-->

---

# 4-3 Math 類別 — 範例

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
【逐步解說】
Math.abs 取絕對值，讓負數變正的。Math.pow(2, 8) 是 2 的 8 次方。注意這些方法的參數和回傳通常都是 double。

💼 業界實務：
Math.PI 是圓周率，別自己寫 3.14，那樣不專業。
-->

---

# 4-4 複合指定運算子

| 運算子 | 等效寫法 | 說明 |
| --- | --- | --- |
| `a += b` | `a = a + b` | 加後賦值 |
| `a -= b` | `a = a - b` | 減後賦值 |
| `a *= b` | `a = a * b` | 乘後賦值 |
| `a /= b` | `a = a / b` | 除後賦值 |
| `a %= b` | `a = a % b` | 取餘後賦值 |

<!--
【核心說明】
這叫「懶人加速法」。

【生活化比喻】
score += 20; 就是「我的分數加 20 分」。比 score = score + 20; 寫起來快多了。

💼 業界實務：
在業界，如果你還在寫 a = a + 1，別人會覺得你在寫舊時代的程式碼。請用 a++ 或 a += 1。
-->

---

# 4-4 複合指定運算子 — 範例

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
【逐步解說】
這些運算子就像是屬性疊加。注意那個 %=，它把最後剩下的餘數留下來。寫起來簡潔，看起來也帥。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 布林、比較與邏輯運算子

<!--
【開場白】
接下來這部分是程式的「靈魂」，因為這決定了程式會不會思考、會不會做判斷。
-->

---
layout: default
---

# 4-5 比較（關係）運算子

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
【核心說明】
比較運算子的結果只有一種：true 或 false。

⚠️ 學生常見誤解：
這是新手最常犯的錯：== 是「比較」，= 是「指定」。
如果你在 if 裡寫 a = 5，Java 會報錯。這就像是你問老婆「妳吃飽了嗎？」，結果你卻把一碗飯直接塞進她嘴裡。
-->

---

# 4-5 邏輯運算子

| 運算子 | 名稱 | 說明 | 範例 |
| --- | --- | --- | --- |
| `&&` | 邏輯 AND | 兩者皆為 true 才為 true | `(a>0) && (b>0)` |
| `\|\|` | 邏輯 OR | 其中一者為 true 即為 true | `(a>0) \|\| (b>0)` |
| `!` | 邏輯 NOT | 反轉布林值 | `!(a > 0)` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>短路求值（Short-Circuit Evaluation）：</b> <code>&&</code> 若左側為 <code>false</code>，右側不執行；<code>||</code> 若左側為 <code>true</code>，右側不執行。這可避免不必要的計算或錯誤（如除以零）。
</div>

<!--
【核心說明】
這是邏輯門，用來組合多個條件。

【生活化比喻】
&&：你要有錢「而且」有閒，才能出國。
||：你有錢「或者」有帥，就能交到朋友。
!：把黑的說成白的。

⚠️ 學生常見誤解：
短路求值（Short-Circuit）很重要！如果 && 左邊已經是 false，右邊連看都不看。這能幫你避免很多錯誤。
-->

---

# 4-5 邏輯運算子 — 範例

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
【逐步解說】
看最後一段。如果 x 是 0，(x != 0) 就是 false。因為是 &&，Java 會直接跳過後面的除法。如果你沒用 && 而直接算，你的程式就會直接「當機」（ArithmeticException）。這就是保命符！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# 位元運算子

<!--
【開場白】
接下來我們要進入「硬派」領域。位元運算是在二進位的世界裡玩遊戲。雖然這聽起來很硬，但學會了，你就是神。
-->

---
layout: default
---

# 4-6 位元邏輯運算子

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
【核心說明】
這是在位元等級操作資料。

【生活化比喻】
&（AND）就像是兩個位元要一起舉手才算 1。^（XOR）最有趣，它是「非我族類」，兩個人不一樣才給你 1。

💼 業界實務：
這在處理硬體控制或加密時非常有用。面試時這可是區分新手與高手的題目。
-->

---

# 4-6 位元移位運算子

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
【核心說明】
左移 << 每移一位就是乘以 2。右移 >> 每移一位就是除以 2。

💼 業界實務：
為什麼要用移位？因為它快！電腦算乘法很慢，但移位子超級快。在做底層驅動或影像處理時常看到。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
# 運算子優先順序

<!--
【開場白】
如果你不想背這張表，我教你一招：用括號！括號永遠是老大。
-->

---
layout: default
---

# 4-7 運算子優先順序表（高 → 低）

| 優先順序 | 運算子 | 結合方向 |
| --- | --- | --- |
| 1（最高） | `()` 括號、`[]` 陣列索引、`.` 成員存取 | 左至右 |
| 2 | `++` `--`（後置）、`+` `-`（一元）、`~` `!` | 右至左 |
| 3 | `*` `/` `%` | 左至右 |
| 4 | `+` `-` | 左至右 |
| 5 | `<<` `>>` `>>>` | 左至右 |
| 6 | `<` `>` `<=` `>=` `instanceof` | 左至右 |
| 7 | `==` `!=` | 左至右 |
| 8 | `&`（位元 AND） | 左至右 |
| 9 | `^`（位元 XOR） | 左至右 |
| 10 | `\|`（位元 OR） | 左至右 |

---

# 4-7 運算子優先順序表（續）

| 優先順序 | 運算子 | 結合方向 |
| --- | --- | --- |
| 11 | `&&`（邏輯 AND） | 左至右 |
| 12 | `\|\|`（邏輯 OR） | 左至右 |
| 13 | `?:`（三元運算子） | 右至左 |
| 14（最低） | `=` `+=` `-=` `*=` `/=` `%=`（指定類） | 右至左 |

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
【業界實務】
括號 () 是免費的，多用一點沒關係。這能讓你的程式碼像詩一樣好讀，而且不會有意外。最後一名是指定運算子 =，這很合理，算完右邊才能塞給左邊。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第六部分
# 資料型態轉換

<!--
【開場白】
這就像是上一章的複習，但這次我們要看它在運算中怎麼自動發生。
-->

---
layout: default
---

# 4-8 自動型態提升（Numeric Promotion）

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
【核心說明】
Java 很怕吵架。如果一個 int 跟一個 double 相加，Java 會把 int 也變成 double 之後再加。

⚠️ 學生常見誤解：
byte + byte 竟然會變成 int！這是 Java 為了計算速度做的設計。你得再用轉型把它縮回來。
-->

---

# 4-8 強制型態轉換（Casting）

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
【核心說明】
「我知道這會有問題，但我執意要轉。」

【生活化比喻】
當你把 double 轉成 int，小數點就會像你的連假一樣瞬間消失。把大大的 long 塞進 int 則會溢位，就像把巨漢塞進嬰兒車，結果一定很慘。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第七部分
# 資料的轉換與輸入

<!--
【開場白】
現在，我們要讓程式學會聽人說話了！我們要學會怎麼從鍵盤讀取使用者的輸入。
-->

---
layout: default
---

# 4-9 Scanner 類別基本方法

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
【核心說明】
Scanner 是你的「接聽器」。

⚠️ 學生常見誤解：
超級地獄坑：nextInt() 讀完會留下一個 Enter。如果你接著用 nextLine()，它會讀到那個 Enter 然後直接結束。解法：多叫一個 scanner.nextLine() 把垃圾清掉。
-->

---

# 4-9 Scanner 基本輸入 — 範例

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
【逐步解說】
看這段範例。讀完數字後清掉 Enter 是關鍵。記得最後要 close() 它，像講完電話要掛斷一樣。
-->

---

# 4-9 字串轉數值（parseInt / parseDouble）

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
【核心說明】
這在網頁開發超常用。

【生活化比喻】
使用者輸入的永遠是「文字」。你不能拿文字 "100" 去加 1（會變成 "1001"）。你得用 parseInt 把它翻譯成真正的數字 100 才能算。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第八部分
# import 與套件

<!--
【開場白】
Java 的函式庫就像一間巨大的圖書館。有的書就在你口袋，有的得去櫃檯借。
-->

---
layout: default
---

# 4-10 import 與 java.lang 套件

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
【核心說明】
java.lang 就像手機內建 App，直接用。java.util 就像 App Store，要先 import（下載）才能用。

💼 業界實務：
不要常用 .* 這種把整間店搬回家的寫法，明確指定你要什麼，程式才會輕量。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第九部分
# 程式敘述的結合與分行

<!--
【開場白】
這部分教你怎麼寫出「漂亮」的程式碼。Java 很隨和，但你的同事可能沒那麼隨和。
-->

---
layout: default
---

# 4-11 程式敘述的結合與分行

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
【核心說明】
Java 只認分號。

💼 業界實務：
雖然可以把所有東西擠成一行，但別這麼做。好的工程師會在大運算子前換行，讓程式碼好讀。這就是專業素養。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第十部分
# 專題實作

<!--
【開場白】
終於到了實戰環節！讓我們看看剛才學的這些運算子，在現實世界怎麼解決問題。
-->

---
layout: default
---

# 4-12 專題一：溫度轉換（°C ↔ °F）

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
【逐步解說】
注意那個 9.0 / 5.0。如果你寫 9 / 5，結果會變 1，你的轉換就會大出包。這就像是匯率被無條件捨去一樣慘。
-->

---

# 4-12 專題二：高斯數學（1 + 2 + ... + n）

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
【核心說明】
同樣的結果，不同的效率。

【逐步解說】
加到一百萬，迴圈要跑一百萬次。高斯公式只要一次乘法、一次除法。身為工程師，我們追求的是「用腦袋工作」，讓程式更聰明。
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
【出題前的鋪陳】
實作練習來了！做一個實用的 BMI 計算機。

【問題引導】
注意單位轉換，身高公分轉公尺喔。用 printf 顯示小數點兩位，這樣看起來才專業。
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
【逐步解說】
別忘了除以 100.0。如果你除以 100（整數），身高 170 就會變成 1。你的 BMI 就會爆表。
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
【逐步解說】
n & 1 為什麼能判斷奇偶？因為二進位最後一位決定了單雙號。感覺像是在直接操作電腦的神經系統一樣酷吧！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【開場白】
今天我們學會了加減乘除、跟使用者聊天、還有二進位的秘密。大家有什麼疑問嗎？或者是想知道怎麼用運算子算出明年加薪機率？（提示：通常會被轉型成 0...開玩笑的！）
-->

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
大家好！上一章我們學會了怎麼幫資料分類、取名字，今天要進一步學「怎麼讓這些資料動起來」。沒有運算子的程式，就像是準備好了食材卻不會開火，什麼菜都做不出來。

想像一下，如果你今天去買飲料，店員只會告訴你「總共要付多少錢」，但完全不會算「找你多少零錢」，這樣的店員一定會被客訴吧。寫程式也是一樣，我們需要靠運算子來做加減乘除、比較大小、判斷條件，程式才會「動腦」。

學完今天的內容，我們會能用 Java 算出 BMI、做溫度轉換，還能讓程式跟使用者「聊天」（透過 Scanner 讀取輸入），是這一章最實用的成果。
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
- **4-6 資料型態轉換**（自動提升、強制轉型）
- **4-7 資料的轉換與輸入**（Scanner、Integer.parseInt）
- **4-8 import 與 java.lang 套件**
- **4-9 程式敘述的結合與分行**
- **4-10 專題：溫度轉換 / 高斯數學**

<!--
這一章的內容算是這個階段的「全餐」，從最基礎的名詞開始，一路講到數學運算、邏輯判斷、型態轉換，最後再用兩個小專題收尾。

可以把這個過程想成組裝一台電腦：我們先認識每個零件叫什麼名字（專有名詞），再學怎麼把它們接起來（各種運算子），接著確認電壓相不相容（型態轉換），最後開機測試（專題實作）。我們會從最簡單的 1 + 1 開始，一步一步往上疊。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 程式設計的專有名詞

<!--
想像我們去參加一場工程師聚會，如果聽到別人說「這個 expression 的結果怪怪的」，自己卻一頭霧水，那場面一定很尷尬。所以開始之前，我們先把幾個最基本的專有名詞認識一遍，之後溝通起來才會順暢。
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
這四個詞是我們之後溝通的共同語言，先建立好定義，後面解釋起來才不會卡卡的。

可以把 operand（運算元）想成「被操作的東西」，operator（運算子）就是「操作的動作」，兩者合在一起變成 expression（運算式），這個運算式會算出一個「值」。如果再加上分號，變成一條完整的指令，就是 statement（敘述）。

業界實務上，如果在 Code Review 時能說出「這個 expression 的結果怪怪的」，會讓人覺得很懂行，因為這代表我們知道問題出在「算式本身」，而不是整條指令。
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
這個範例的目的，是把剛剛四個名詞「對號入座」到一行真實的程式碼上。

帶讀關鍵在第三行：a + b 這部分是運算式，它會先算出一個值「8」，再透過 = 這個指定運算子，把 8 放進變數 c 裡。整行 int c = a + b; 加上結尾的分號，就構成了一條完整的敘述。

⚠️ 易錯點：別忘了結尾的分號。在 Java 裡，少了分號程式就無法編譯，就像講電話講到一半突然斷訊一樣，對方完全不知道你想表達什麼。

預期結果：印出 8。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 指定運算子與數學運算

<!--
接下來要進入數學運算，但不用緊張，我們不會碰到微積分，只需要會國小程度的加減乘除就足夠應付這一節的內容。
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
在數學課裡，= 代表「等於」，但在程式裡，= 的意思更接近「把值塞進去」。這個觀念上的轉換很重要。

可以把 a = b = c = 0; 想成是排隊接力：最右邊的 0 先傳給 c，c 再把這個值傳給 b，b 再傳給 a，最後三個變數都變成 0。這就是所謂的「鏈式賦值」。

⚠️ 易錯點：執行方向是「由右往左」，跟我們平常閱讀文字的方向相反，第一次看到常常會搞錯順序，多看幾次、習慣這個方向就好。
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
加減乘除大家都很熟，重點要放在那個 / 跟 %。

可以想像一下：手上有 10 塊錢，一個麵包 3 塊，10 / 3 在 Java 裡的結果不是 3.33，而是 3——因為老闆不會切 0.33 個麵包賣給我們，只能整個賣。那剩下的那 1 塊錢去哪了？這就是 % 的工作，它會幫我們算出「剩下多少」。

⚠️ 易錯點：初學者很容易忽略 % 的存在，但它在判斷奇偶數、計算星期幾、或是處理迴圈分組時非常實用，務必記住它。
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
這段範例的目的，是讓我們同時看到「整數除法」跟「浮點數除法」的差別，方便對照。

帶讀關鍵在最後兩行：當 a、b 都是 int 時，a / b 的小數部分會直接被截掉；但只要其中一個運算元是 double，例如 x / y，小數點就會被保留下來。

⚠️ 易錯點：如果想要精確的小數結果（例如平均分數 3.33），一定要確保除號兩邊至少有一個是 double，否則小數部分會無聲無息地消失，這是計算平均值時很常見的出錯點。

預期結果：依序印出 13、7、30、3、1、3.3333333333333335。
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
如果要算次方、開根號、四捨五入，不用自己手刻公式，Java 內建了一個「萬用計算機」叫 Math，直接呼叫它的方法就好。

可以把 Math 想成是程式裡的「高級計算機」，遇到數學相關的需求，先想想 Math 裡有沒有現成的方法可以用。

⚠️ 易錯點：Math.round 雖然是「四捨五入」，但它回傳的型態是 long，如果想存進 int 變數，記得要做型態轉換，不然編譯會出錯。
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
這段範例的目的，是把上一頁表格裡的方法實際跑一次，看看回傳值長什麼樣子。

帶讀關鍵在 Math.abs(-10) 跟 Math.pow(2, 8)：abs 會把負數變成正數，pow(2, 8) 則是 2 的 8 次方。可以特別留意這些方法的參數和回傳值大多是 double，即使輸入的是整數也一樣。

💼 業界實務：算圓面積時用 Math.PI 取代手寫的 3.14，這是基本的專業習慣，因為 Math.PI 的精度遠高於我們手寫的近似值。

預期結果：依序印出 10、256.0、12.0、99、4、78.53981633974483。
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
如果常常需要「自己加上某個值再存回自己」，每次都寫 score = score + 20 會有點囉嗦，這時候就可以用「懶人加速法」——複合指定運算子。

可以把 score += 20; 直接讀成「我的分數加 20 分」，意思跟 score = score + 20; 完全一樣，但寫起來精簡很多。

💼 業界實務：如果在程式碼裡看到還在寫 a = a + 1，通常會被認為是比較舊式的寫法，現在大家習慣用 a++ 或 a += 1，可讀性更高。
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
這段範例的目的，是讓我們看到同一個變數 score 連續被五個複合運算子改動之後的最終結果。

帶讀關鍵在最後一行 score %= 7：前面算出來的 score 是 55，55 除以 7 商 7 餘 6，所以 score 最終變成 6。每一步都是「先算，再存回自己」。

⚠️ 易錯點：這幾個運算子是依序執行的，前一步的結果會直接影響下一步，如果中途算錯一步，後面全部都會跟著錯，建議練習時一步一步在紙上對照。

預期結果：印出 6。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 布林、比較與邏輯運算子

<!--
接下來這部分可以說是程式的「靈魂」，因為它決定了程式會不會思考、會不會做判斷——也就是後面章節的 if、while 都要靠這部分撐起來。
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
比較運算子的特色很單純：算出來的結果永遠只有兩種可能，true 或 false，沒有模糊空間。

⚠️ 易錯點：這是新手最常踩的坑——== 是「比較」，= 是「指定」。如果在 if 裡誤寫成 if (a = 5)，Java 會直接編譯錯誤。可以想像一下，這就像是問對方「你吃飽了嗎？」，結果自己卻把一碗飯硬塞進對方嘴裡，問句跟動作完全對不上。

養成習慣：看到 if 裡面的條件，先確認是不是 ==，這個小動作能省下很多除錯時間。
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
邏輯運算子的作用，是把好幾個條件「組合」成一個最終的判斷結果，這在判斷複雜情境時非常常見。

可以想像這三個運算子對應到日常生活的情境：&& 就像是「要有錢『而且』有閒，才能出國玩」；|| 則是「有錢『或者』有趣，就能交到朋友」；! 就是「把黑的說成白的，整個反過來」。

⚠️ 易錯點：「短路求值」這個概念很重要——如果 && 左邊已經是 false，右邊的條件根本不會被執行；如果 || 左邊已經是 true，右邊也會被跳過。這個特性能幫我們避免很多潛在的錯誤。
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
這段範例分成兩部分：前半段示範 &&、||、! 的基本用法，後半段示範「短路求值」如何幫我們避開一個危險的錯誤。

帶讀關鍵在最後一段：x 是 0，(x != 0) 的結果是 false。因為運算子是 &&，Java 看到左邊已經是 false，就不會再去執行右邊的 100 / x。

⚠️ 易錯點：如果沒有用 && 而是直接寫 100 / x > 1，當 x 是 0 時程式會直接拋出 ArithmeticException（除以零的例外），整個程式就會中斷。所以善用短路求值，可以說是程式的一種「保命機制」。

預期結果：依序印出 false、true、false、false。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# 資料型態轉換

<!--
這部分算是上一章「8 種基本資料型態」的延伸，但這次我們要看的是：當不同型態的資料碰在一起運算時，Java 會怎麼自動處理。
-->

---
layout: default
---

# 4-6 自動型態提升（Numeric Promotion）

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
Java 在做運算時很「怕吵架」——如果一個 int 跟一個 double 相加，Java 會先把那個 int 也變成 double，再進行加法，確保兩邊「身份相同」才開始計算。

⚠️ 易錯點：byte + byte 的結果竟然是 int！這是 Java 設計上為了計算效率而做的決定。所以如果想把結果存回 byte 變數，必須額外做強制轉型，否則編譯會出錯。
-->

---

# 4-6 強制型態轉換（Casting）

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
強制轉型可以理解成一種「我知道有風險，但我堅持要轉」的宣告，Java 會尊重我們的決定，但風險要自己承擔。

可以想像一下：把 double 轉成 int，小數點會像連假結束一樣瞬間消失；把巨大的 long 硬塞進 int，就像把一個大個子硬塞進嬰兒車，結果一定會「擠壞」，也就是溢位，算出來的數字會變得莫名其妙。

⚠️ 易錯點：int 轉 char 看似神奇（65 變成 'A'），其實是因為字元在底層也是用數字（ASCII／Unicode 編碼）儲存的，這部分在後面字元與字串的章節會更深入說明。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
# 資料的轉換與輸入

<!--
接下來要讓程式「學會聽人說話」。到目前為止，我們的程式都只能輸出固定的結果，這部分要學會怎麼從鍵盤讀取使用者輸入的內容。
-->

---
layout: default
---

# 4-7 Scanner 類別基本方法

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
Scanner 可以想成是程式的「耳朵」，負責接收使用者從鍵盤輸入的內容，再翻譯成程式可以使用的數值或文字。

⚠️ 易錯點：這是初學者最常踩的「地獄坑」——呼叫 nextInt() 讀完數字之後，使用者按下的 Enter 鍵其實還留在輸入緩衝區裡。如果接著呼叫 nextLine()，它會讀到那個殘留的 Enter，直接變成空字串，導致後面的輸入看起來「被跳過」。解法很簡單：在中間多呼叫一次 scanner.nextLine() 把這個殘留值清掉就好。
-->

---

# 4-7 Scanner 基本輸入 — 範例

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
這段範例的目的，是把「讀整數、讀小數、讀字串」三種輸入串在一起，並示範如何處理前面提到的換行殘留問題。

帶讀關鍵在 scanner.nextLine(); 那一行單獨出現的位置——它的作用就是把 nextDouble() 留下的 Enter 吃掉，這樣後面的 nextLine() 才能正確讀到使用者輸入的姓名。

⚠️ 易錯點：程式結束前呼叫 scanner.close()，就像講完電話要記得掛斷一樣，是個好習慣，能釋放系統資源。

預期結果：依照輸入內容組合成一句包含姓名、年齡、薪水的文字並印出。
-->

---

# 4-7 字串轉數值（parseInt / parseDouble）

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
這個概念在網頁開發或表單處理中非常常見，因為使用者輸入的內容，在程式眼中永遠都是「文字」，即使看起來像數字也一樣。

可以想像一下：使用者在輸入框打了「100」，這個「100」其實是文字 "100"，不是數字 100。如果直接拿這串文字去做加 1 的運算，結果會變成 "1001"（字串相加，等於把兩個文字接在一起），完全不是我們想要的結果。必須先用 parseInt 把它「翻譯」成真正的數字，才能進行數學運算。

⚠️ 易錯點：parseInt 跟 parseDouble 如果遇到無法轉換的字串（例如使用者打了中文），會拋出例外，這部分會在後面例外處理的章節詳細介紹。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第六部分
# import 與套件

<!--
Java 的函式庫可以想像成一座巨大的圖書館：有些書放在我們隨身的口袋裡，隨手就能翻；有些書則要先到櫃檯登記、借出來才能用。
-->

---
layout: default
---

# 4-8 import 與 java.lang 套件

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
💡 <b>java.lang：</b> 這個套件如此基礎，Java 編譯器會自動幫我們匯入，無需手動 <code>import</code>。
</div>

<!--
可以把 java.lang 想成手機裡「內建的 App」，像 System、String、Math，打開就能直接用，不需要額外下載；而 java.util 則像是「App Store 裡的軟體」，像 Scanner，需要先 import（下載安裝）之後才能使用。

💼 業界實務：盡量避免使用 import java.util.*; 這種「把整間店都搬回家」的寫法，明確指定需要的類別（例如 import java.util.Scanner;），可以讓程式碼更清晰、也更容易管理依賴關係。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第七部分
# 程式敘述的結合與分行

<!--
這部分要教我們怎麼寫出「漂亮」的程式碼。Java 本身對格式很隨和，但維護程式碼的同事可能就沒那麼隨和了。
-->

---
layout: default
---

# 4-9 程式敘述的結合與分行

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
Java 在判斷一條敘述是否結束時，只看「分號」，完全不在乎換行或空白，這給了我們排版上很大的彈性。

💼 業界實務：雖然技術上可以把所有程式碼擠成一行，但一個有經驗的工程師通常會選擇在較大的運算子前面換行，讓程式碼讀起來更有層次。這種排版上的講究，其實也是專業素養的一部分。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第八部分
# 專題實作

<!--
終於到了實戰環節！接下來用兩個小專題，把今天學到的運算子全部串起來，看看它們在真實情境中能解決什麼問題。
-->

---
layout: default
---

# 4-10 專題一：溫度轉換（°C ↔ °F）

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
這個專題的目的，是把「Scanner 輸入」「數學運算」「printf 格式化輸出」三件事整合在一起，做出一個真的能用的小工具。

帶讀關鍵在 celsius * 9.0 / 5.0 + 32 這一行：特別注意這裡寫的是 9.0 / 5.0，而不是 9 / 5。

⚠️ 易錯點：如果寫成 9 / 5，因為兩邊都是 int，結果會被截成 1（正確應該是 1.8），整個溫度換算就會大出包，算出來的華氏溫度會差一大截，這就像匯率被「無條件捨去」一樣，誤差非常明顯。

預期結果：輸入攝氏溫度後，印出對應的華氏溫度，並四捨五入到小數點一位。
-->

---

# 4-10 專題二：高斯數學（1 + 2 + ... + n）

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
這個專題故意用兩種方法算出同一個結果，目的是讓我們親眼比較「兩種思路的效率差異」。

帶讀關鍵在於對照 sumLoop 跟 sumGauss 這兩個變數：一個靠迴圈一步一步累加，一個用數學公式一次算出來。

如果要加到一百萬，迴圈得跑一百萬次，但高斯公式只需要一次乘法、一次除法就結束。身為工程師，我們追求的常常不是「蠻力解決」，而是「找到更聰明的算法」，這也是這個專題想傳達的精神。

預期結果：兩種方法算出來的結果應該完全相同。
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
回顧一下，我們已經學會了 Scanner 輸入、數學運算、還有型態轉換，這個練習要把這三個概念全部用上，做出一個生活中真的會用到的小工具——BMI 計算機。

引導思考一下：BMI 公式裡的身高單位是「公尺」，但我們習慣輸入的是「公分」，這中間需要做什麼轉換？另外，輸出結果時要怎麼控制小數位數，讓畫面看起來更專業？
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
這個提示的關鍵只有一個地方：千萬別忘了除以 100.0。如果寫成除以 100（整數），身高 170 經過整數除法會直接變成 1，算出來的 BMI 會嚴重失真，整個結果就「爆表」了。

把這個提示記住，再回頭看看任務說明裡的範例（身高 170、體重 65 約等於 22.49），驗證一下自己算出來的結果是否吻合。
-->

---
layout: default
---

# 練習二（綜合）：成績統計小工具
### 任務說明

撰寫一個程式，讓使用者輸入**三科成績**（國文、數學、英文，皆為整數 0–100），程式需要：

1. 計算三科**總分**與**平均分數**（平均以 `double` 計算，輸出到小數點後一位）
2. 判斷是否**及格**：三科**都**要 `>= 60` 才算及格，並印出「及格」或「不及格」
3. 額外判斷：是否為「全優」——三科**都** `>= 90`，印出「全優」或「未達全優」

**要求：**
1. 使用 Scanner 讀取三個整數
2. 平均分數務必使用浮點數運算（避免整數除法誤差）
3. 及格與全優的判斷請使用邏輯運算子 `&&`

<!--
回顧一下，這一章我們從最基本的運算元、運算子開始，學了數學運算、複合指定運算子、比較與邏輯運算子，也學會了型態轉換跟 Scanner 輸入。這個練習就是把這些概念整合在一起，做一個更貼近真實情境的小工具。

引導思考一下：計算平均分數時，三科分數相加之後是 int，但我們希望結果是有小數的——要怎麼讓除法不要變成整數除法？另外，「三科都及格」這句話，要怎麼用 && 串起三個條件來表達？
-->

---

# 練習二（綜合）：成績統計小工具
### 解題提示

1. 三個整數相加後是 `int`，計算平均時轉型為 `double`：`double avg = (chinese + math + english) / 3.0;`
2. 及格判斷：`boolean pass = chinese >= 60 && math >= 60 && english >= 60;`
3. 全優判斷：`boolean excellent = chinese >= 90 && math >= 90 && english >= 90;`
4. 平均分數輸出到小數點一位：`System.out.printf("平均：%.1f%n", avg);`

```java
int total = chinese + math + english;
double avg = total / 3.0;
boolean pass = chinese >= 60 && math >= 60 && english >= 60;
boolean excellent = chinese >= 90 && math >= 90 && english >= 90;

System.out.println("總分：" + total);
System.out.printf("平均：%.1f%n", avg);
System.out.println(pass ? "及格" : "不及格");
System.out.println(excellent ? "全優" : "未達全優");
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>關鍵提醒：</b> <code>total / 3</code>（int / int）會做整數除法，務必寫成 <code>total / 3.0</code> 才能得到正確的小數平均值。
</div>

<!--
這個提示串起了今天學的好幾個重點：total / 3.0 用到了「型態提升」的概念（只要有一邊是 double，結果就是 double）；pass 跟 excellent 的判斷則是用 && 把三個比較運算式串成一個邏輯運算式。

⚠️ 易錯點：如果寫成 total / 3 而不是 total / 3.0，會因為整數除法而失去小數部分，算出來的平均分數會不準確，這跟前面 BMI 練習裡「除以 100 vs 100.0」的坑是一樣的道理，務必養成習慣，多看一眼除號兩邊的型態。

完成這個練習，代表我們已經能把這一章學到的運算子——數學運算、複合指定、比較、邏輯——綜合運用在一個實際的小程式裡了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
今天我們從最基本的專有名詞開始，一路學了加減乘除、複合指定運算子、比較與邏輯運算子、型態轉換，還有 Scanner 輸入，最後用 BMI 計算機跟成績統計工具做了實戰演練。

大家還有什麼疑問嗎？如果對位元運算或更完整的優先順序表有興趣，這部分我們整理成了自學內容，有興趣的同學可以自行延伸閱讀。
-->

---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 正規表達式 (Regular Expression)
routeAlias: ch13
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
    Java Programming Masterclass
  </p>
  <h1 style="color: #1a5c5c; font-size: 3.8rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">
    正規表達式
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「用簡潔的模式，描述複雜的字串規則」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章的主題是正規表達式。

想像一下，要檢查使用者輸入的手機號碼格式對不對：要是 09 開頭、總共 10 碼、而且全部是數字。如果只用 charAt 一個字一個字檢查，程式會寫得又長又難讀。

正規表達式就是一種「用簡潔的規則，描述字串長什麼樣子」的語言。學完這一章，我們就能用一行規則，驗證電話、Email、身分證字號這類格式。
-->

---
layout: default
---

# Outline

- **基礎字元符號**
  - `\d`、`\w`、`\s`、`.`、`[]`、`[^]`、跳脫符號、`|`
- **量次與分組**
  - `?`、`*`、`+`、`{}`、量次總表、貪婪/懶惰、`()`、反向引用、具名分組
- **位置與進階符號**
  - `^`、`$`、`\b`、`(?i)`、環視斷言
- **String 類別常用方法**
  - `matches()`、`split()`、`replaceFirst()`、`replaceAll()`
- **正規表達式套件**
  - `java.util.regex` — `Pattern` 與 `Matcher`
- **實作練習**

<!--
這一章分成五個部分。

第一部分是基礎的字元符號，例如代表數字的反斜線 d、萬用字元點，以及中括號。第二部分是量次和分組，用來描述重複的次數。第三部分是位置和進階符號，例如開頭、結尾和環視斷言。

第四部分回到 String 類別裡和正規表達式有關的方法。第五部分是 java.util.regex 套件的 Pattern 和 Matcher。最後用三題練習，實際驗證電話、身分證字號和 Email。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 基礎字元符號

<!--
我們先從最基本的字元符號開始。
-->

---
layout: default
---

# 什麼是正規表達式？

正規表達式 (Regular Expression) 是一種**描述字串模式**的語言，主要用於：

- **模式比對** — 判斷字串是否符合指定格式
- **搜尋** — 在文字中找出符合條件的子字串
- **取代** — 將符合條件的字串取代為其他內容

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>使用時機：</b> 驗證電話號碼、Email、身份證字號等複雜格式時，正規表達式能讓程式碼大幅簡化。
</div>

<!--
正規表達式，英文是 Regular Expression，簡稱 regex，是一種描述字串模式的語言。

它主要有三種用途：第一是比對，判斷一個字串是否符合某種格式；第二是搜尋，在一大段文字裡找出符合條件的部分；第三是取代，把符合條件的內容換成別的文字。

像電話號碼、Email、身分證字號這類有固定規則的格式，用正規表達式來驗證，程式碼可以大幅簡化。
-->

---

# 第一個正規表達式

最簡單的正規表達式就是**字面字串**，只比對完全相同的字串；加入特殊符號後，一個表達式能描述一整類字串。

```java
// 字面字串：精確比對
System.out.println("java".matches("java"));  // true
System.out.println("Java".matches("java"));  // false（大小寫不同）

// \d 代表任意一個數字，比字面字串更靈活
System.out.println("3".matches("\\d"));  // true
System.out.println("7".matches("\\d"));  // true
System.out.println("a".matches("\\d"));  // false
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 在 Java 中，反斜線需跳脫：正規表達式的 <code>\d</code> 寫成字串要寫 <code>"\\d"</code>
</div>

<!--
最簡單的正規表達式，就是一般的文字，它只會比對完全相同的字串。所以 java 比對 java 是 true，但大寫開頭的 Java 就是 false，因為大小寫不同。

加入特殊符號之後，一個表達式就能描述一整類字串。反斜線 d 代表任意一個數字，所以 3 和 7 都符合，但字母 a 不符合。

要注意，在 Java 的字串裡，反斜線本身需要跳脫，所以正規表達式的反斜線 d，在程式裡要寫成兩個反斜線加 d。
-->

---

# `\d` — 數字符號

`\d` 代表一個 **0~9 的數字**，相當於 `[0-9]`

| 表達式 | 說明 |
| --- | --- |
| `\\d` | 比對一個數字（0~9） |
| `[0-9]` | 等同 `\\d` |

```java
// 手機號碼前 4 碼：4 個 \d
System.out.println("0912".matches("\\d\\d\\d\\d")); // true
System.out.println("O912".matches("\\d\\d\\d\\d")); // false（O 是字母）
```

<!--
我們仔細看反斜線 d。它代表一個 0 到 9 的數字，等同於中括號 0 減 9 的寫法。

看範例，要比對手機號碼的前 4 碼，可以寫四個反斜線 d。0912 符合；但第二個例子開頭是英文字母 O，不是數字 0，所以不符合。這種肉眼很難分辨的錯誤，正規表達式可以輕鬆抓出來。
-->

---

# `\w`、`\d`、`\s` — 常用字元類別

| 表達式 | 說明 | 等同表達式 |
| --- | --- | --- |
| `\\w` | 數字、字母或底線 | `[A-Za-z0-9_]` |
| `\\W` | 非 `\\w` 的字元 | `[^A-Za-z0-9_]` |
| `\\d` | 數字 | `[0-9]` |
| `\\D` | 非數字 | `[^0-9]` |
| `\\s` | 空白字元（空格、tab、換行） | — |
| `\\S` | 非空白字元 | — |

<!--
除了反斜線 d，還有幾個常用的字元類別。

反斜線 w 代表字母、數字或底線，也就是一般變數名稱可以用的字元。反斜線 s 代表空白字元，包括空格、Tab 和換行。

規則很好記：小寫是「是」，大寫是「不是」。大寫 W 是非單字字元，大寫 D 是非數字，大寫 S 是非空白字元。
-->

---

# `\w`、`\d`、`\s` — 範例

```java
System.out.println("Hello123".matches("\\w+"));    // true（字母數字底線）
System.out.println("Hello World".matches("\\w+")); // false（含空格）
System.out.println("12345".matches("\\d+"));       // true
System.out.println("Hello World".matches("\\w+\\s\\w+")); // true（字 空格 字）
System.out.println("!@#".matches("\\W+"));         // true（非字母數字）
```

<!--
我們看範例。

Hello123 全部都是字母和數字，所以符合反斜線 w 加號，加號的意思是一個以上，等一下會介紹。Hello World 中間有空格，所以不符合。

12345 全部是數字，符合反斜線 d 加號。第四行把它們組合起來：一串單字字元、一個空白、再一串單字字元，就能比對 Hello World。最後，驚嘆號、小老鼠和井字號都不是單字字元，所以符合大寫 W 加號。
-->

---

# `.` — 萬用字元（單一字元）

萬用字元 `.` 可比對**除了換行符號 `\n` 以外的任意單一字元**

| 表達式 | 說明 |
| --- | --- |
| `.` | 任意單一字元（換行除外） |
| `\\.` | 字面句點（需跳脫） |

```java
System.out.println("cat".matches(".at")); // true（c 是任意字元）
System.out.println("at".matches(".at"));  // false（少一個字元）
// 若要比對字面句點，需用 \\.
System.out.println("3.14".matches("\\d\\.\\d+")); // true
System.out.println("3A14".matches("\\d\\.\\d+")); // false
```

<!--
點是萬用字元，可以比對除了換行以外的任何一個字元。

看範例，點 at 可以比對 cat，因為點對應到了 c。但 at 只有兩個字，少了一個字元，所以不符合。

如果要比對的是真正的句點，就要在前面加上反斜線跳脫。看最後兩行，數字、句點、數字的規則可以比對 3.14，但 3A14 中間是字母 A，不是句點，所以不符合。
-->

---

# `[]` — 字元分類（Character Class）

中括號 `[]` 表示**比對括號內的其中一個字元**

| 表達式 | 說明 |
| --- | --- |
| `[cbm]` | c、b 或 m 其中一個 |
| `[A-Z]` | 大寫字母 A 到 Z |
| `[a-z]` | 小寫字母 a 到 z |
| `[0-9]` | 數字 0 到 9（同 `\\d`） |
| `[A-Za-z]` | 所有英文字母（大小寫） |

<!--
中括號代表「比對括號裡的其中一個字元」。

中括號 cbm，代表 c、b 或 m 其中一個。也可以用減號表示範圍：A 減 Z 是所有大寫字母，a 減 z 是所有小寫字母，0 減 9 是所有數字，和反斜線 d 一樣。多個範圍可以寫在一起，例如所有大小寫英文字母。
-->

---

# `[]` — 範例

```java
System.out.println("cat".matches("[cbm]at")); // true（c 在 [cbm] 內）
System.out.println("fat".matches("[cbm]at")); // false（f 不在 [cbm] 內）
System.out.println("A".matches("[A-Z]"));     // true
System.out.println("a".matches("[A-Za-z]"));  // true
// 注意：不可用 [A-z]，大小寫字母中間的 ASCII 含有其他符號
```

<!--
看範例，中括號 cbm 後面接 at。cat 的 c 在括號裡，所以符合；fat 的 f 不在括號裡，所以不符合。

下面的注意事項很重要：不要把所有英文字母寫成大寫 A 到小寫 z。因為在字元編碼裡，大寫 Z 和小寫 a 中間還夾著幾個符號，例如中括號和底線，這樣寫會意外地把它們也包含進去。
-->

---

# 字元類別交集與減法

可以在中括號內使用 `&&` 來縮小比對範圍

| 表達式 | 說明 |
| --- | --- |
| `[a-z&&[def]]` | 交集：同時符合 a-z 且是 d, e, f (即 d, e, f) |
| `[a-z&&[^aeiou]]` | 減法：符合 a-z 且**不是**母音 (即所有小寫輔音) |

```java
// 比對非母音的小寫字母
System.out.println("b".matches("[a-z&&[^aeiou]]")); // true
System.out.println("e".matches("[a-z&&[^aeiou]]")); // false

// 比對 1~9 之間的偶數
System.out.println("4".matches("[1-9&&[2468]]"));   // true
```

<!--
在中括號裡，還可以用兩個 and 符號表示交集，縮小比對的範圍。

第一個例子是 a 到 z 和 d、e、f 的交集，結果就是 d、e、f。第二個例子是 a 到 z，扣掉母音，也就是所有小寫的子音字母。

看範例，b 是子音，符合；e 是母音，不符合。最後一個例子，取 1 到 9 和 2、4、6、8 的交集，所以 4 符合。
-->

---

# `[^]` — 否定字元分類

在中括號**最開頭**加 `^` 表示**不包含**括號內的字元

| 表達式 | 說明 |
| --- | --- |
| `[^A-Z]` | 非大寫字母 |
| `[^0-9]` | 非數字（同 `\\D`） |
| `[^aeiou]` | 非母音字母 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>^</code> 只在中括號<b>最開頭</b>才表示否定，放其他位置只是字面的 ^ 字元
</div>

```java
System.out.println("a".matches("[^A-Z]"));       // true（非大寫）
System.out.println("A".matches("[^A-Z]"));       // false
System.out.println("5".matches("[0-9&&[^67]]")); // true（是數字且非6或7）
```

<!--
在中括號的最開頭加上一個 caret 符號，就表示「不包含」括號裡的字元。

中括號 caret A 到 Z，代表任何不是大寫字母的字元。中括號 caret 0 到 9，就是非數字，和大寫 D 一樣。

要注意，caret 只有放在中括號的最開頭才代表否定，放在其他位置，就只是一般的字元。

看範例，小寫 a 不是大寫字母，所以符合；大寫 A 則不符合。最後一個例子結合了交集，5 是數字而且不是 6 或 7，所以符合。
-->

---

# 跳脫符號 — 特殊字元表

當字串本身**包含**正規表達式的特殊符號時，需在前面加 `\\` 使其變成字面值

| 原始符號 | Java 寫法 | 說明 |
| --- | --- | --- |
| `(` | `\\(` | 字面左括號 |
| `)` | `\\)` | 字面右括號 |
| `{` | `\\{` | 字面左大括號 |
| `.` | `\\.` | 字面句點 |
| `*` | `\\*` | 字面星號 |
| `\|` | `\\\|` | 字面管道符號 |

<!--
當要比對的文字本身就包含正規表達式的特殊符號時，例如括號、大括號、句點、星號和直線，就要在前面加上反斜線，讓它變回一般的字元。

表格列出了常見的特殊符號，以及在 Java 字串裡的寫法。記得在 Java 裡，反斜線要寫兩個。
-->

---

# 跳脫符號 — 範例

```java
// 字串 (02)-26669999 含有字面括號，比對時需跳脫
String phone = "(02)-26669999";
System.out.println(phone.matches("\\(\\d{2}\\)-\\d{8}")); // true
System.out.println(phone.matches("(\\d{2})-\\d{8}"));     // false，() 被視為分組
```

<!--
看一個實際的例子。電話號碼括號 02 括號、減號、8 個數字，裡面有真正的括號。

第一個表達式把括號跳脫了，所以能正確比對，結果是 true。第二個表達式沒有跳脫，括號被當成了分組符號，於是它要求字串直接是兩個數字開頭，結果就是 false。
-->

---

# `|` — 管道（OR 運算）

管道 `|` 可以**同時比對多個模式**，相當於 Java 的 OR 運算

| 表達式 | 說明 |
| --- | --- |
| `A\|B` | 符合 A 或符合 B |
| `Mary\|Tom` | 符合其中一個名字 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 正規表達式沒有通用的 AND 運算子；<code>&&</code> 只能寫在中括號內做字元類別交集（如 <code>[a-z&&[^aeiou]]</code>）
</div>

```java
System.out.println("Mary".matches("Mary|Tom")); // true
System.out.println("Tom".matches("Mary|Tom"));  // true
System.out.println("John".matches("Mary|Tom")); // false
```

<!--
直線符號代表「或」，可以同時比對多個模式。

Mary 直線 Tom，代表符合 Mary 或 Tom 其中一個。所以 Mary 和 Tom 都是 true，John 則是 false。

另外要知道，正規表達式沒有通用的 AND 運算。剛才看到的兩個 and 符號，只能寫在中括號裡，用來做字元的交集。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 量次與分組

<!--
接下來是第二部分：量次和分組。我們要學會描述「某個東西重複幾次」。
-->

---
layout: default
---

# `{}` — 設定重複次數

大括號 `{n}` 表示前面的表達式**恰好重複 n 次**，避免重複寫相同符號

| 表達式 | 說明 |
| --- | --- |
| `\\d{4}` | 4 個連續數字 |
| `\\d{3}` | 3 個連續數字 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>{}</code> 內的數字與逗號之間不能有空格，<code>{3, 5}</code> 是錯誤寫法
</div>

```java
// \d\d\d\d 和 \d{4} 等效，後者更簡潔
System.out.println("0912-345-678".matches("\\d{4}-\\d{3}-\\d{3}")); // true
```

<!--
大括號加上一個數字，表示前面的內容剛好重複那麼多次。

反斜線 d 大括號 4，就是 4 個連續的數字，和連寫四次反斜線 d 的效果一樣，但簡潔多了。所以手機號碼可以寫成：4 個數字、減號、3 個數字、減號、3 個數字。

要注意，大括號裡的數字和逗號之間，不能有空格。
-->

---

# 量次修飾詞 — `?`、`*`、`+`

| 符號 | 說明 | 出現次數 |
| --- | --- | --- |
| `?` | 可有可無，最多一次 | 0 ~ 1 次 |
| `*` | 可無可有，不限次數 | 0 ~ 多次 |
| `+` | 至少一次，不限次數 | 1 ~ 多次 |

```java
System.out.println("Johnson".matches("John(na)?son"));     // true  (na: 0次)
System.out.println("Johnnason".matches("John(na)?son"));   // true  (na: 1次)
System.out.println("Johnnanason".matches("John(na)?son")); // false (na: 2次)
System.out.println("Johnnanason".matches("John(na)*son")); // true  (* 允許多次)
System.out.println("Johnson".matches("John(na)+son"));     // false (+ 至少1次)
```

<!--
問號、星號和加號，是三個最常用的量次符號。

問號代表可有可無，0 次或 1 次。星號代表 0 次或多次，加號則代表至少 1 次。

看範例，括號 na 加上問號：Johnson 裡 na 出現 0 次，符合；Johnnason 出現 1 次，也符合；但 Johnnanason 出現 2 次，就不符合了。改用星號就允許多次，所以符合。最後用加號，Johnson 裡一次 na 都沒有，所以不符合。
-->

---

# `{n,m}` — 設定比對次數區間

大括號除了設定固定次數，也可以設定**次數範圍**

| 表達式 | 說明 |
| --- | --- |
| `{n}` | 恰好 n 次 |
| `{n,}` | 至少 n 次 |
| `{n,m}` | n 到 m 次（含首尾） |

```java
System.out.println("sonsonson".matches("(son){3,5}"));       // true (3次)
System.out.println("sonson".matches("(son){3,5}"));          // false (2次)
System.out.println("02-12345678".matches("0\\d{1,3}-\\d{7,8}")); // true
```

<!--
大括號除了指定固定次數，也可以指定範圍。

大括號 n 逗號，代表至少 n 次；大括號 n 逗號 m，代表 n 到 m 次，頭尾都包含。

看範例，括號 son 重複 3 到 5 次。sonsonson 是 3 次，符合；sonson 只有 2 次，不符合。最後一個例子，市內電話的區碼是 0 加上 1 到 3 個數字，電話號碼是 7 到 8 個數字，可以同時比對不同長度的號碼。
-->

---

# 量次修飾詞總表

| 表達式 | 說明 | 等同表達式 |
| --- | --- | --- |
| `?` | 0 或 1 次 | `{0,1}` |
| `*` | 0 或多次 | `{0,}` |
| `+` | 1 或多次 | `{1,}` |
| `{n}` | 恰好 n 次 | — |
| `{n,}` | 至少 n 次 | — |
| `{n,m}` | n 到 m 次 | — |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記憶口訣：</b> <code>?</code> 有點猶豫（0或1）、<code>*</code> 什麼都行（0或多）、<code>+</code> 至少要有（1或多）
</div>

<!--
這張表整理了所有的量次修飾詞。

問號等於大括號 0 逗號 1，星號等於大括號 0 逗號，加號等於大括號 1 逗號。另外還有剛好 n 次、至少 n 次，以及 n 到 m 次。

可以這樣記：「問號是有點猶豫，0 或 1 次；星號是什麼都行，0 或多次；加號是至少要有，1 或多次。」
-->

---

# 貪婪 vs 懶惰 vs 佔有量詞

預設的量詞是**貪婪的**（Greedy），會盡可能比對最長的字串。

| 類型 | 範例 | 比對行為 |
| --- | --- | --- |
| 貪婪 (Greedy) | `.*` | 比對到最末尾，再往回找 |
| 懶惰 (Reluctant) | `.*?` | 比對到最少符合的字元就停止 |
| 佔有 (Possessive) | `.*+` | 比對到最長且**不回溯**（效能最高） |

```java
String html = "<div>A</div><div>B</div>";

// 貪婪：比對到最後一個 </div>
System.out.println(html.replaceAll("<div>.*</div>", "TEXT")); 
// TEXT

// 懶惰：比對到第一個 </div> 就停止
System.out.println(html.replaceAll("<div>.*?</div>", "TEXT")); 
// TEXTTEXT
```

<!--
量次符號預設是「貪婪」的，會盡可能比對最長的字串。

在量次符號後面加上問號，就變成「懶惰」模式，只要符合就盡快停下來，比對最短的字串。加上加號則是「佔有」模式，會比對最長，而且不會回頭重試，效能最好。

這個差別在處理 HTML 標籤時最明顯。用貪婪的點星號，會從第一個標籤一路比對到最後一個標籤，把中間的內容全部吃掉；改成懶惰的點星號問號，才會在第一個結束標籤就停下來。
-->

---

# `.*` — 所有字元萬用字元

`.*` 組合可比對**任意長度的任意字串**（換行除外）

| 表達式 | 說明 |
| --- | --- |
| `.*` | 0 個或多個任意字元 |
| `Java.*` | 以 Java 開頭，後面任意 |
| `.*Java` | 以 Java 結尾，前面任意 |
| `.*Java.*` | 包含 Java 的任意字串 |

```java
String s = "Hello World 123";
System.out.println(s.matches("Hello.*"));    // true（以 Hello 開頭）
System.out.println(s.matches(".*123"));      // true（以 123 結尾）
System.out.println(s.matches(".*Python.*")); // false
```

<!--
點和星號組合在一起，就代表任意長度的任意字元，是非常常用的組合。

Java 點星號，代表以 Java 開頭；點星號 Java，代表以 Java 結尾；前後都加上點星號，就代表包含 Java。

看範例，Hello World 123 以 Hello 開頭，符合；以 123 結尾，也符合；但不包含 Python，所以最後一行是 false。
-->

---

# `()` — 分組

小括號 `()` 用於**將多個字元組成一個群組**，方便對群組套用量次修飾詞

| 概念 | 說明 |
| --- | --- |
| `()` | 建立一個群組 |
| `(-\\d{3}){2}` | 群組 `-\\d{3}` 重複 2 次 |

```java
// 兩種寫法等效
System.out.println("0912-345-678".matches("\\d{4}-\\d{3}-\\d{3}"));  // true
System.out.println("0912-345-678".matches("\\d{4}(-\\d{3}){2}"));    // true
```

<!--
小括號用來分組，把多個字元組成一個整體，再對整組套用量次。

看範例，手機號碼的後半段是「減號加三個數字」重複兩次。所以可以把減號和三個數字用括號包起來，後面接大括號 2，表示整組重複兩次。

兩種寫法的結果完全一樣，第二種在重複的部分比較多時，會更簡潔。
-->

---

# `\\n` — 反向引用 (Backreferences)

反向引用允許你在表達式中**重複引用前面分組比對到的內容**

| 表達式 | 說明 |
| --- | --- |
| `\\1` | 引用第 1 個分組的比對結果 |
| `(\\w)\\1` | 比對連續兩個相同的字元（如 aa, 11） |

```java
// 比對重複的單字
String text = "hello hello world";
System.out.println(text.matches("(\\w+) \\1 .*")); // true (引用了第1個分組 hello)

// 比對 HTML 標籤是否對稱
String html = "<div>content</div>";
System.out.println(html.matches("<(\\w+)>.*</\\1>")); // true
```

<!--
用小括號分組之後，還可以在後面用反斜線加數字，引用前面那一組比對到的內容，這叫做反向引用。

反斜線 1 就是引用第 1 組。例如括號反斜線 w，再接反斜線 1，就能比對連續兩個相同的字元，像是 aa 或 11。

看範例，第一個可以找出重複的單字 hello hello。第二個可以檢查 HTML 的開始標籤和結束標籤，名稱是不是一樣。
-->

---

# 反向引用拆解：`(\\w+) \\1 .*`

以字串 `"hello hello world"` 為例：

| 片段 | 說明 | 比對到的內容 |
| --- | --- | --- |
| `(\\w+)` | **第 1 組**：一個以上的字母或數字 | `hello` |
| ` ` | 字面空格 | ` `（空格）|
| `\\1` | 反向引用：必須與第 1 組**完全相同的文字** | `hello`（重複）|
| ` ` | 字面空格 | ` `（空格）|
| `.*` | 任意字元，0 個以上 | `world` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>關鍵：</b><code>\\1</code> 不是「再比對一個單字」，而是「必須與第 1 組抓到的內容一模一樣」。<br>
所以 <code>"hello world world"</code> 不符合，因為 <code>\\1</code> 要求的是 <code>hello</code>，不是 <code>world</code>。
</div>

<!--
我們把剛才這個表達式拆開來看。

第一組，括號反斜線 w 加號，比對到 hello。接著是一個空格。然後反斜線 1，要求這裡出現的，必須和第 1 組一模一樣，也就是 hello。最後是空格，和任意的內容 world。

關鍵在於：反斜線 1 不是「再比對任何一個單字」，而是「必須和第 1 組抓到的文字完全相同」。所以 hello world world 就不符合，因為第二個位置是 world，不是 hello。
-->

---

# 具名分組 (Named Capturing Groups)

除了用編號 `\\1`，也可以為分組**取名字**，提高可讀性

| 語法 | 說明 |
| --- | --- |
| `(?<name>...)` | 定義具名分組 |
| `\\k<name>` | 在表達式中引用具名分組 |

```java
// 使用具名分組比對日期
String dateRegex = "(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})";
String input = "2024-05-20";

System.out.println(input.matches(dateRegex)); // true

// Matcher 也可以透過名字取得內容
// matcher.group("year") -> "2024"
```

<!--
除了用編號，也可以幫分組取一個名字，讓表達式更容易閱讀。

定義的寫法是括號、問號，再用角括號包住名稱。要在表達式裡引用，就用反斜線 k 加上角括號名稱。

看範例，日期的格式分成三組，分別叫 year、month 和 day。2024-05-20 符合這個格式。之後用 Matcher，就可以直接用名字取出每一組的內容。
-->

---

# 具名分組提取 — Matcher 完整範例

```java
import java.util.regex.*;
String dateRegex = "(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})";
Matcher m = Pattern.compile(dateRegex).matcher("2024-05-20");

if (m.matches()) {
    System.out.println(m.group("year"));  // 2024
    System.out.println(m.group("month")); // 05
    System.out.println(m.group("day"));   // 20
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>m.group("year")</code> 比 <code>m.group(1)</code> 更清楚，不受分組順序變動影響
</div>

<!--
這是用 Matcher 取出具名分組的完整範例。

先用 Pattern.compile 編譯表達式，再對 2024-05-20 建立 Matcher。呼叫 matches 確認整個字串符合之後，就可以用 group 加上名字，取出年份 2024、月份 05 和日期 20。

用名字取值，比用編號 1、2、3 更清楚，而且就算之後在前面多加了一組，名字也不會跑掉。
-->

---

# `(?:...)` — 非擷取分組

有時我們只需要分組的**邏輯功能**（例如套用量詞），但不需要記錄比對內容（節省效能）

| 語法 | 說明 |
| --- | --- |
| `(?:...)` | 純分組，不計入編號，不可被引用 |

```java
// 我們只想比對是否包含 .com 或 .org，但不需要擷取它
String regex = ".*\\.(?:com|org)";
System.out.println("google.com".matches(regex)); // true
// 此時沒有 group(1)，因為它是非擷取分組
```

<!--
有時候我們只需要分組的功能，例如套用量次或使用直線符號，但不需要記住比對到的內容。這時候可以用非擷取分組，寫法是括號、問號、冒號。

看範例，我們只想判斷網址是不是以 .com 或 .org 結尾，不需要把它取出來，就用非擷取分組把兩個選項包起來。

非擷取分組不會被編號，所以也不能用 group 取出內容，但可以節省一點效能。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 位置與進階符號

<!--
接下來是第三部分：位置和進階符號。這些符號比對的不是字元，而是一個位置。
-->

---
layout: default
---

# `^` 和 `$` — 比對開頭與結尾

當 `^` 和 `$` **不在**中括號內時，表示字串的**開頭**和**結尾**位置

| 表達式 | 說明 |
| --- | --- |
| `^Java` | 字串以 Java 開頭 |
| `Java$` | 字串以 Java 結尾 |
| `^Java$` | 字串恰好等於 Java |

```java
import java.util.regex.*;
String s1 = "Java is fun";
String s2 = "I love Java";
System.out.println(Pattern.compile("^Java").matcher(s1).find()); // true
System.out.println(Pattern.compile("Java$").matcher(s2).find()); // true
```

<!--
caret 和錢字號，在中括號外面時，分別代表字串的開頭和結尾。

caret Java 代表以 Java 開頭，Java 錢字號代表以 Java 結尾，兩個都加上，就代表字串剛好等於 Java。

看範例，我們用 Pattern 的 find 方法在字串中搜尋。Java is fun 以 Java 開頭，I love Java 以 Java 結尾，所以兩個都是 true。
-->

---

# `(?i)` 與 `\b` — 旗標與單字邊界

| 表達式 | 說明 |
| --- | --- |
| `(?i)` | 忽略大小寫，置於表達式開頭 |
| `\\b` | 單字邊界：匹配一個**位置**，用於比對完整單字 |
| `\\bJava\\b` | 只比對完整單字 Java（不含 JavaScript） |

<!--
這一頁介紹兩個符號。

第一個是括號、問號、i，放在表達式的開頭，可以讓整個比對忽略大小寫。

第二個是反斜線 b，代表單字邊界。它比對的是一個位置，也就是單字開始或結束的地方。所以反斜線 b Java 反斜線 b，只會比對到獨立的單字 Java，而不會比對到 JavaScript 裡面的 Java。
-->

---

# `(?i)` 與 `\b` — 範例

```java
// (?i) 忽略大小寫
System.out.println("JAVA".matches("(?i)java")); // true
System.out.println("jAvA".matches("(?i)java")); // true

// \b 單字邊界（在 matches() 中比對整個字串）
System.out.println("Java".matches("\\bJava\\b"));       // true
System.out.println("JavaScript".matches("\\bJava\\b")); // false
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>\\b</code> 在子字串搜尋時更常用，搭配第五部分的 <code>Pattern.find()</code> 效果更明顯
</div>

<!--
看範例。加上忽略大小寫的旗標之後，全大寫的 JAVA 和大小寫交錯的 jAvA，都能符合小寫的 java。

單字邊界的例子裡，Java 是一個完整的單字，所以符合；JavaScript 在 Java 後面還有其他字母，不是單字邊界，所以不符合。

反斜線 b 在一段長文字裡搜尋單字時最有用，等一下學到 Matcher 的 find，效果會更明顯。
-->

---

# 環視斷言 (Lookaround)

在目前位置「偷看」前後是否符合條件，**確認後位置不動**，後面的 pattern 仍從同一位置繼續比對

| 表達式 | 說明 |
| --- | --- |
| `(?=pattern)` | 正向先行：右邊必須符合 pattern |
| `(?!pattern)` | 負向先行：右邊必須不符合 pattern |
| `(?<=pattern)` | 正向後行：左邊必須符合 pattern |
| `(?<!pattern)` | 負向後行：左邊必須不符合 pattern |

<!--
環視斷言，是在目前的位置，偷看前面或後面的內容是否符合條件，但不會移動位置。

一共有四種。往右看的叫先行：括號問號等號，代表右邊必須符合；括號問號驚嘆號，代表右邊必須不符合。往左看的叫後行，多了一個小於符號：一樣分成必須符合和必須不符合。
-->

---

# 環視斷言 — 範例

```java
import java.util.regex.Matcher;
import java.util.regex.Pattern;
```
```java
// 密碼強度：含數字且長度 >= 8
String passRegex = "^(?=.*\\d).{8,}$";
System.out.println("password123".matches(passRegex)); // true
// 只擷取 $ 後面的數字（50 前面沒有 $，不符合）
Matcher m = Pattern.compile("(?<=\\$)\\d+").matcher("Price: $100, Cost: 50");
while (m.find()) {
    System.out.println(m.group()); // 100
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>「不消耗字元」：</b> 一般 pattern 比對到字元後，那些字元就「被用掉」，位置往後移。環視斷言只是偷看、不移動位置。<br>
以 <code>^(?=.*\\d).{8,}$</code> 為例：<code>(?=.*\\d)</code> 從開頭確認「字串含有數字」，確認完後位置還在開頭，接著 <code>.{8,}</code> 再從開頭量長度——兩個條件都從同一起點出發，互不干擾。
</div>

<!--
我們看兩個應用。

第一個是檢查密碼強度：含有數字，而且長度至少 8 個字元。開頭的正向先行，先偷看整個字串裡是否有數字；確認之後位置還在開頭，再用點大括號 8 逗號，從開頭量長度。password123 兩個條件都符合。

第二個是只擷取錢字號後面的數字。正向後行要求左邊必須是錢字號，所以 100 會被找到，而 Cost 後面的 50 前面沒有錢字號，就不會被找到。

這就是「不消耗字元」的意思：環視只是確認條件，不會把字元用掉，所以多個條件可以從同一個位置出發，互不干擾。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# String 類別常用方法

<!--
接下來，我們回到 String 類別，整理一下和正規表達式有關的方法。
-->

---
layout: default
---

# String 類別相關方法

| 方法 | 說明 |
| --- | --- |
| `matches(regex)` | 判斷**整個字串**是否符合正規表達式 |
| `split(regex)` | 用正規表達式分割字串，回傳 `String[]` |
| `replaceFirst(regex, str)` | 取代**第一個**符合正規表達式的子字串 |
| `replaceAll(regex, str)` | 取代**所有**符合正規表達式的子字串 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>matches()</code> 必須比對整個字串；若要搜尋子字串，需使用第五部分的 <code>Pattern</code>/<code>Matcher</code>
</div>

<!--
String 類別有四個方法，接受正規表達式作為參數。

matches 判斷整個字串是否符合規則。split 用正規表達式切割字串，回傳字串陣列。replaceFirst 取代第一個符合的部分，replaceAll 則取代所有符合的部分。

要注意，matches 一定要整個字串都符合。如果只是要在字串裡搜尋某一部分，就要用第五部分的 Pattern 和 Matcher。
-->

---

# `matches()` 與 `split()` — 範例

```java
// matches()：整個字串符合格式才回傳 true
String phone = "0912-345-678";
System.out.println(phone.matches("\\d{4}-\\d{3}-\\d{3}")); // true
System.out.println(phone.matches("\\d{3}-\\d{3}-\\d{3}")); // false

// split()：用正規表達式作為分隔符
String csv = "apple,orange,,banana";
String[] parts = csv.split(",+"); // 一個以上的逗號都視為分隔符
System.out.println(parts[0]); // apple
System.out.println(parts[1]); // orange
System.out.println(parts[2]); // banana
```

<!--
看範例。電話號碼 0912-345-678 符合 4 碼、3 碼、3 碼的格式，是 true；如果規則寫成 3 碼開頭，就是 false。

split 用逗號加號切割，表示一個以上連續的逗號，都當作同一個分隔符號。所以 orange 和 banana 中間雖然有兩個逗號，也不會切出空字串，得到的就是 apple、orange、banana。
-->

---

# `replaceFirst()` 與 `replaceAll()` — 範例

```java
String text = "cat bat sat";

// replaceFirst()：只取代第一個符合的
System.out.println(text.replaceFirst("[a-z]at", "***")); // *** bat sat

// replaceAll()：取代所有符合的
System.out.println(text.replaceAll("[a-z]at", "***"));   // *** *** ***

// 遮蔽電話號碼中的數字
String data = "phone: 0912-345-678";
System.out.println(data.replaceAll("\\d", "*")); // phone: ****-***-***
```

<!--
看取代的範例。中括號 a 到 z 再接 at，可以比對 cat、bat、sat。

replaceFirst 只換掉第一個，所以結果是三個星號、bat、sat。replaceAll 會全部換掉，三個單字都變成星號。

最後一個例子很實用：把字串裡所有的數字換成星號，就能把電話號碼遮蔽起來，常用在顯示個人資料的時候。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 正規表達式套件
# `java.util.regex`

<!--
最後一個部分，我們來看 java.util.regex 套件，它提供了比 String 方法更完整的正規表達式功能。
-->

---
layout: default
---

# `java.util.regex` 套件介紹

| 類別 | 說明 |
| --- | --- |
| `Pattern` | 編譯並儲存正規表達式，可重複使用 |
| `Matcher` | 對輸入字串執行比對，記錄比對結果與位置 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>與 String 方法的差異：</b> <code>String.matches()</code> 只能比對整個字串；<code>Matcher.find()</code> 可在長文字中搜尋子字串、取得位置。
</div>

```java
import java.util.regex.*;
Pattern pattern = Pattern.compile("\\d{4}");
Matcher matcher = pattern.matcher("年份2024今年");
while (matcher.find()) {
    System.out.println("找到: " + matcher.group()); // 找到: 2024
}
```

<!--
這個套件有兩個主要的類別。

Pattern 負責編譯並儲存一個正規表達式，編譯一次就可以重複使用。Matcher 則是拿這個 Pattern，對某個字串執行比對，並記錄比對到的內容和位置。

和 String 方法最大的差別是：String 的 matches 只能比對整個字串；Matcher 的 find，可以在一大段文字裡搜尋，一個一個找出符合的部分。

看範例，在年份 2024 今年這段文字裡，找到了 4 個連續的數字 2024。
-->

---

# `Pattern` 類別常用方法

| 方法 | 說明 |
| --- | --- |
| `Pattern.compile(regex)` | 編譯正規表達式，回傳 Pattern 物件 |
| `Pattern.compile(regex, flags)` | 帶旗標編譯（如 `Pattern.CASE_INSENSITIVE`） |
| `Pattern.matches(regex, input)` | 靜態方法，比對整個字串 |
| `p.matcher(input)` | 以此 Pattern 建立 Matcher 物件 |
| `p.split(input)` | 用此 Pattern 分割字串 |

<!--
Pattern 類別常用的方法有這幾個。

compile 把正規表達式編譯成 Pattern 物件，也可以多傳一個旗標，例如忽略大小寫。matches 是靜態方法，直接比對整個字串。matcher 用這個 Pattern 建立 Matcher 物件。split 則用這個 Pattern 切割字串。
-->

---

# `Pattern` 類別 — 範例

```java
import java.util.regex.*;

// 忽略大小寫旗標
Pattern p = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
System.out.println(p.matcher("JAVA").find()); // true

// 靜態方法 matches（等同 String.matches）
System.out.println(Pattern.matches("\\d+", "12345")); // true

// split 支援複雜分隔符
String[] parts = Pattern.compile(",\\s*").split("a, b,c,  d");
// parts = ["a", "b", "c", "d"]
```

<!--
看範例。用忽略大小寫的旗標編譯 java，就能在 JAVA 裡找到符合的內容。

Pattern.matches 的效果和 String 的 matches 一樣，12345 全部是數字，所以是 true。

最後用逗號加上任意空白來切割，不管逗號後面有沒有空格、有幾個空格，都能切得乾淨，得到 a、b、c、d。
-->

---

# Pattern.quote( )

| 方法名稱 | 說明 |
| --- | --- |
| `Pattern.quote(String s)` | 將字串的所有 regex 特殊字元轉為字面值，回傳可安全嵌入正規表達式的字串 |

```java
String userInput = "3.14";
System.out.println("3.14".matches(Pattern.quote(userInput)));  // true
System.out.println("3X14".matches(Pattern.quote(userInput)));  // false
// 不用 quote 時，. 是萬用字元
System.out.println("3X14".matches(userInput));                 // true
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 接受使用者輸入作為搜尋關鍵字時，用 <code>Pattern.quote()</code> 防止特殊字元破壞 regex
</div>

<!--
Pattern.quote 可以把一個字串裡所有的特殊符號，都轉成一般的字元。

看範例，使用者輸入了 3.14。用 quote 處理之後，點就只代表句點，所以 3.14 符合，3X14 不符合。如果沒有用 quote，點會被當成萬用字元，連 3X14 都會符合。

所以，當我們要把使用者輸入的關鍵字放進正規表達式時，一定要先用 quote 處理，避免特殊字元讓規則出錯。
-->

---

# Predicate 整合 (JDK 11+)

`Pattern` 可以直接轉為 `Predicate`，方便與 Stream API 結合使用

| 方法 | 說明 |
| --- | --- |
| `asPredicate()` | 判斷**子字串**是否存在（JDK 8+） |
| `asMatchPredicate()` | 判斷**整個字串**是否完全符合（JDK 11+） |

```java
List<String> list = List.of("apple", "banana", "123", "456");

// 篩選出純數字的字串
var isNumeric = Pattern.compile("\\d+").asMatchPredicate();

list.stream()
    .filter(isNumeric)
    .forEach(System.out::println); // 123, 456
```

<!--
Pattern 也可以轉換成 Predicate，也就是一個回傳 true 或 false 的判斷函式，方便和之後會學到的 Stream 一起使用。

asPredicate 判斷字串裡是否包含符合的部分；asMatchPredicate 則要求整個字串都符合。

看範例，用全部是數字的規則建立判斷函式，交給 filter 篩選清單，最後只留下 123 和 456。Stream 和 Lambda 在後面的章節會詳細介紹。
-->

---

# Pattern.splitAsStream( )

| 方法名稱 | 說明 |
| --- | --- |
| `splitAsStream(CharSequence input)` | 依此 Pattern 切割字串，回傳 `Stream<String>`（可直接串接 Stream 操作） |

```java
String csv = "apple,orange,banana";
Pattern.compile(",")
    .splitAsStream(csv)
    .map(String::toUpperCase)
    .forEach(System.out::println); // APPLE ORANGE BANANA
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>vs split()：</b> <code>split()</code> 回傳 <code>String[]</code>；<code>splitAsStream()</code> 回傳 <code>Stream</code>，可直接使用 filter / map / collect
</div>

<!--
splitAsStream 會依照 Pattern 切割字串，但回傳的是 Stream，而不是陣列。

看範例，用逗號切割三種水果，接著直接用 map 轉成大寫，再全部印出來，得到大寫的 APPLE、ORANGE、BANANA。

和 split 回傳陣列相比，splitAsStream 可以直接接著做篩選和轉換，不需要先存成陣列。
-->

---

# Pattern.MULTILINE / DOTALL 旗標

| 旗標 | 內嵌語法 | 說明 |
| --- | --- | --- |
| `MULTILINE` | `(?m)` | `^` `$` 改為比對每行開頭/結尾（而非整個輸入的開頭/結尾） |
| `DOTALL` | `(?s)` | `.` 涵蓋換行符號（預設不涵蓋） |

```java
String text = "apple\nbanana\ncherry";
long lines = Pattern.compile("^\\w+$", Pattern.MULTILINE)
    .matcher(text).results().count();
System.out.println(lines); // 3
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>DOTALL 使用時機：</b> HTML/XML 標籤跨行時，<code>(?s)&lt;div&gt;.*&lt;/div&gt;</code> 才能正確比對多行內容
</div>

<!--
Pattern 還有兩個常用的旗標。

MULTILINE 會讓 caret 和錢字號，改成比對每一行的開頭和結尾，而不只是整段文字的開頭和結尾。DOTALL 則讓點也能比對換行字元。

看範例，三種水果各佔一行。開啟 MULTILINE 之後，每一行都是一個完整的單字，所以總共找到 3 筆。

如果要比對跨越好幾行的 HTML 標籤，就要開啟 DOTALL，點星號才能跨過換行。
-->

---

# `Matcher` 類別常用方法（一）

| 方法 | 說明 |
| --- | --- |
| `find()` | 搜尋下一個符合的子字串，找到回傳 `true` |
| `find(start)` | 從 start 索引位置開始搜尋 |
| `matches()` | 比對**整個**輸入字串 |
| `group()` | 回傳目前找到的子字串 |
| `group(n)` | 回傳第 n 個 `()` 分組所比對到的子字串 |

<!--
Matcher 類別常用的方法有這幾個。

find 搜尋下一個符合的部分，找到就回傳 true，也可以指定從哪個位置開始找。matches 比對整個字串。group 回傳目前找到的內容；group 加上數字 n，則回傳第 n 組括號比對到的內容。
-->

---

# `Matcher` 類別常用方法（一）— 範例

```java
String text = "Java 11 and Java 17";
Matcher m = Pattern.compile("Java (\\d+)").matcher(text);

m.find();
System.out.println(m.group());  // "Java 11"（整個比對結果）
System.out.println(m.group(1)); // "11"（第 1 個分組）

m.find(12);                     // 從 index 12 開始搜尋
System.out.println(m.group());  // "Java 17"
```

<!--
看範例，文字是 Java 11 and Java 17，表達式是 Java 空格，再用括號包住數字。

第一次 find，找到 Java 11。group 不帶參數，回傳整個比對結果 Java 11；group 1 則只回傳括號裡的 11。

接著從索引 12 開始搜尋，就會找到 Java 17。
-->

---

# Matcher.lookingAt( ) — 三種比對方式對比

| 方法 | 說明 |
| --- | --- |
| `matches()` | 整個字串必須完整符合 |
| `lookingAt()` | 從**開頭**開始符合，但不要求整個字串都符合 |
| `find()` | 在字串的**任意位置**搜尋子字串 |

```java
String input = "123abc";
Pattern p = Pattern.compile("\\d{3}");
System.out.println(p.matcher(input).matches());    // false（abc 不符合）
System.out.println(p.matcher(input).lookingAt());  // true（開頭 123 符合）
System.out.println(p.matcher(input).find());       // true（任意位置找到）
```

<!--
Matcher 有三種比對方式，差別在比對的範圍。

matches 要求整個字串完全符合。lookingAt 只要求從開頭開始符合，後面可以有其他內容。find 則可以在任何位置找到符合的部分。

看範例，字串是 123abc，規則是三個數字。matches 是 false，因為後面還有 abc；lookingAt 是 true，因為開頭的 123 符合；find 也是 true。
-->

---

# `Matcher` 類別常用方法（二）

| 方法 | 說明 |
| --- | --- |
| `start()` | 回傳比對子字串的起始索引 |
| `end()` | 回傳比對子字串的結束索引（不含） |
| `replaceFirst(str)` | 取代第一個符合的子字串 |
| `replaceAll(str)` | 取代所有符合的子字串 |

<!--
Matcher 還可以告訴我們，找到的內容在哪個位置。

start 回傳開始的索引，end 回傳結束的索引，不包含結束位置本身。另外，Matcher 也有 replaceFirst 和 replaceAll，可以取代符合的內容。
-->

---

# `Matcher` 類別常用方法（二）— 範例

```java
String text = "Java 11 and Java 17";
Matcher m = Pattern.compile("\\d+").matcher(text);

m.find();
System.out.println(m.start()); // 5（"11" 的起始索引）
System.out.println(m.end());   // 7（"11" 的結束索引，不含）
System.out.println(m.group()); // "11"

System.out.println(m.replaceAll("X")); // "Java X and Java X"
```

<!--
看範例，在 Java 11 and Java 17 裡搜尋數字。

第一次找到的是 11，start 是 5，end 是 7。注意 end 是 7，代表 11 佔了索引 5 和 6，和 substring 的範圍規則一樣。

最後的 replaceAll，把所有數字都換成 X，得到 Java X and Java X。
-->

---

# 動態取代與 Stream 整合 (JDK 9+)

`Matcher` 在 JDK 9 之後提供了更強大的處理能力

| 方法 | 說明 |
| --- | --- |
| `replaceAll(Function)` | 使用 Lambda 動態決定取代內容 |
| `results()` | 將所有比對結果轉為 `Stream<MatchResult>` |

```java
// 動態將比對到的數字加倍
String input = "10 plus 20";
Matcher m = Pattern.compile("\\d+").matcher(input);
String result = m.replaceAll(res -> 
    String.valueOf(Integer.parseInt(res.group()) * 2));
// result: "20 plus 40"

// 使用 Stream 統計
long count = Pattern.compile("\\w+").matcher("A B C").results().count(); // 3
```

<!--
Java 9 之後，Matcher 又多了兩個強大的功能。

第一個是 replaceAll 可以傳入一個函式，依照比對到的內容，動態決定要換成什麼。看範例，把找到的每個數字都乘以 2，10 plus 20 就變成了 20 plus 40。

第二個是 results，它會把所有比對結果變成一個 Stream，方便統計和處理。例如計算 A B C 裡有幾個單字，結果是 3。
-->

---

# `Matcher` — 範例

```java
import java.util.regex.*;

String text = "Java 8, Java 11, Java 21";
Matcher m = Pattern.compile("Java (\\d+)").matcher(text);

while (m.find()) {
    System.out.printf("版本: %s，位置: %d%n",
        m.group(1),  // () 內的版本號
        m.start());  // 起始位置
}
// 版本: 8，位置: 0
// 版本: 11，位置: 8
// 版本: 21，位置: 17
```

<!--
這是用 Matcher 搜尋多筆結果最常見的寫法：用 while 迴圈搭配 find。

每次 find 找到一筆，就用 group 1 取出版本號，用 start 取出位置，再印出來。找不到時 find 回傳 false，迴圈就結束了。

所以會依序印出：版本 8 在位置 0，版本 11 在位置 8，版本 21 在位置 17。
-->

---

# 完整應用範例

```java
import java.util.regex.*;

// 從文字中擷取所有數字
String report = "銷售：12500 元，退貨：300 件，淨利：9800 元";
Matcher m = Pattern.compile("\\d+").matcher(report);
while (m.find()) System.out.print(m.group() + " ");
// 12500 300 9800

// 遮蔽電話號碼
String log = "使用者 0912-345-678 已登入";
System.out.println(log.replaceAll("\\d{4}-\\d{3}-\\d{3}", "****-***-***"));
// 使用者 ****-***-*** 已登入
```

<!--
我們看兩個實際應用。

第一個，從一段銷售報告裡，擷取出所有的數字。用反斜線 d 加號搭配 find，就能依序取出 12500、300 和 9800。

第二個，遮蔽記錄檔裡的電話號碼。用手機號碼的規則搭配 replaceAll，把電話換成星號，再寫進記錄檔，就不會洩漏使用者的個人資料。
-->

---
layout: default
---

# 練習 1：電話號碼格式驗證
### 任務說明

撰寫程式，以 `Scanner` 輸入電話號碼，判斷是否符合以下格式之一：

1. **行動電話**：`0XXX-XXX-XXX`（0 開頭，4碼-3碼-3碼）
2. **市內電話（含括號）**：`(02)12345678`（區碼 2~4 碼，電話號碼 7~8 碼）
3. **市內電話（含連字號）**：`02-12345678`（區碼 2~4 碼，電話號碼 7~8 碼）

<!--
接下來我們動手練習。

第一題，用 Scanner 輸入電話號碼，判斷它是否符合三種格式之一。第一種是行動電話，0 開頭，4 碼、3 碼、3 碼，用減號分隔。第二種是有括號的市內電話，括號裡是 2 到 4 碼的區碼，後面接 7 到 8 碼。第三種是用減號分隔的市內電話。

我們先暫停一下，自己動手試試看。

【講師】給學生約 15 分鐘實作。
-->

---
layout: default
---

# 練習 1：解題提示
### 提示說明

1. **行動電話**：`0\\d{3}-\\d{3}-\\d{3}`
2. **市內（含括號）**：`\\(0\\d{1,3}\\)\\d{7,8}`
3. **市內（連字號）**：`0\\d{1,3}-\\d{7,8}`
4. 用 `|` 組合三種格式

```java
String regex = "0\\d{3}-\\d{3}-\\d{3}"
             + "|\\(0\\d{1,3}\\)\\d{7,8}"
             + "|0\\d{1,3}-\\d{7,8}";
System.out.println(input.matches(regex));
```

<!--
我們來看提示。

行動電話是 0 加上 3 個數字、減號、3 個數字、減號、3 個數字。括號的市內電話，括號要跳脫，裡面是 0 加 1 到 3 個數字，後面接 7 到 8 個數字。減號的市內電話規則類似，只是把括號換成減號。

最後用直線符號把三種格式組合起來，交給 matches 判斷就可以了。
-->

---
layout: default
---

# 練習 2：身份證字號驗證
### 任務說明

撰寫程式，以 `Scanner` 輸入身份證字號，用正規表達式驗證格式：

- 共 **10 碼**
- 第 1 碼：英文字母（大小寫皆可）
- 第 2 碼：只能是 **1 或 2**（性別碼）
- 第 3~10 碼：**8 個數字**

**進階：** 排除 6 都的首字母（A、B、D、E、F、H，大小寫皆排除）

<!--
第二題是身分證字號驗證。

規則是：總共 10 碼；第 1 碼是英文字母，大小寫都可以；第 2 碼只能是 1 或 2；後面 8 碼都是數字。

進階挑戰是：排除六都的開頭字母，也就是 A、B、D、E、F、H。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 10 分鐘實作。
-->

---
layout: default
---

# 練習 2：解題提示
### 提示說明

1. 第 1 碼英文字母：`[A-Za-z]`
2. 第 2 碼只能 1 或 2：`[12]`
3. 後 8 碼數字：`\\d{8}`

```java
// 基本版
System.out.println(id.matches("[A-Za-z][12]\\d{8}"));

// 進階版（排除6都首字母 A B D E F H）
System.out.println(id.matches("(?i)[cgi-z][12]\\d{8}"));
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>(?i)</code> 讓整個表達式忽略大小寫，<code>[cgi-z]</code> 只列出非 6 都的字母；若寫成 <code>[^abdefh]</code>，數字和符號也會被放行
</div>

<!--
我們來看提示。

第 1 碼用中括號列出大小寫字母的範圍，第 2 碼用中括號 1 2，後面 8 碼用反斜線 d 大括號 8。

進階版用括號問號 i 忽略大小寫，再用中括號只列出不是六都的字母：c、g，以及 i 到 z。

下面也提醒了，如果寫成「不是 a、b、d、e、f、h」的否定寫法，數字和符號也會被放行，所以這裡要用列出允許字母的方式。
-->

---
layout: default
---

# 練習 3：Email 格式驗證
### 任務說明

撰寫程式，以 `Scanner` 輸入電子郵件，判斷是否符合以下格式：

- **帳號部分**：英數字、`.`、`+`、`-` 的組合（至少一個字元）
- **@ 符號**：必要字元
- **網域名稱**：英數字與 `-` 的組合（至少一個字元）
- **頂級網域**：`.` 加上至少 2 個字元，可出現多次（如 `.com.tw`）

合法範例：`user@example.com`、`hello+tag@mail.co.uk`、`a.b-c@x-y.org`

<!--
第三題是 Email 格式驗證。

規則是：帳號部分可以有英數字、句點、加號和減號，至少一個字元；接著是小老鼠；然後是網域名稱，可以有英數字和減號；最後是頂級網域，一個句點加上至少 2 個字元，而且可以出現好幾次，例如 .com.tw。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 15 分鐘實作。
-->

---
layout: default
---

# 練習 3：解題提示
### 提示說明

1. 帳號部分：`[\w.+-]+`（`\w` 是 `[A-Za-z0-9_]` 的縮寫）
2. `@` 符號：直接寫 `@`
3. 網域名稱：`[\w-]+`
4. 頂級網域（可重複）：`(\.[\w-]{2,})+`

<div class="mt-2 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>注意：</b> <code>\w</code> 是正規表達式的字元縮寫，但在 Java 字串中必須用雙反斜線 <code>\\w</code> 才能表示一個反斜線，否則 Java 編譯器會報錯
</div>

<!--
我們來看提示。

帳號部分用中括號列出反斜線 w、句點、加號和減號，後面接加號表示至少一個。小老鼠直接寫。網域名稱用中括號列出反斜線 w 和減號。頂級網域是反斜線句點，加上至少 2 個字元，用括號包起來，後面接加號，表示可以重複。

要注意，寫進 Java 字串時，每個反斜線都要寫成兩個。
-->

---
layout: default
---

# 練習 3：解題程式

```java
String emailRegex = "[\\w.+-]+@[\\w-]+(\\.([\\w-]{2,}))+";

String[] tests = {
    "user@example.com",    // ✅
    "bad.address",         // ❌ 缺少 @
    "user@.com",           // ❌ 網域開頭不合法
    "hello+tag@mail.co.uk" // ✅
};
for (String email : tests) {
    System.out.printf("%-25s → %s%n",
        email, email.matches(emailRegex) ? "✅ 合法" : "❌ 不合法");
}
```

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>動態問卷連結：</b>前台作答頁面的 Email 欄位就需要這個驗證！同一個 email 不能重複填寫同一張問卷，驗證格式是第一道防線。
</div>

<!--
這是完整的解題程式。

我們準備了四個測試資料，用迴圈逐一判斷，並用 printf 對齊輸出結果。user@example.com 和 hello+tag@mail.co.uk 是合法的；bad.address 沒有小老鼠，不合法；user@.com 網域名稱是空的，也不合法。

在實際的系統裡，像是問卷或註冊頁面的 Email 欄位，格式驗證就是第一道防線。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了用正規表達式描述字串的規則。

反斜線 d、w、s 和中括號用來描述字元；問號、星號、加號和大括號描述重複的次數；小括號用來分組，還能反向引用；caret、錢字號、反斜線 b 和環視斷言比對的是位置。簡單的驗證可以用 String 的 matches、split 和 replaceAll；需要搜尋和擷取時，就用 Pattern 和 Matcher。

下一章，我們要回到物件導向，學習繼承。

【講師】開放 Q&A。
-->

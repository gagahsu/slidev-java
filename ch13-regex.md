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
【開場白】
大家好，今天我們要進入一個非常實用的主題——正規表達式，英文叫 Regular Expression，業界通常縮寫成 Regex 或 RE。

【為什麼要學這個？】
你有沒有填過網站的表單，輸入手機號碼格式不對，頁面就說「格式不正確」？那背後很多時候就是靠正規表達式在驗證格式的。學會它，你寫的程式就可以做一樣的事。

【今天學完你會能做什麼】
學完之後你就能用一行表達式驗證電話、Email、身分證字號的格式，也能在一段文字裡搜尋、取代特定的字串，而不需要寫一堆 if 判斷。
-->
---
layout: default
---

# Outline

- **第一部分：基礎字元符號**
  - `\d`、`\w`、`\s`、`.`、`[]`、`[^]`、跳脫符號、`|`
- **第二部分：量次與分組**
  - `?`、`*`、`+`、`{}`、量次總表、貪婪/懶惰、`()`、反向引用、具名分組
- **第三部分：位置與進階符號**
  - `^`、`$`、`\b`、`(?i)`、環視斷言
- **第四部分：String 類別常用方法**
  - `matches()`、`split()`、`replaceFirst()`、`replaceAll()`
- **第五部分：正規表達式套件**
  - `java.util.regex` — `Pattern` 與 `Matcher`
- **實作練習**

<!--
【帶讀說明】
這張是今天的課程大綱。我們分五個部分，從最基本的字元符號開始，慢慢堆疊到實務應用。

【脈絡提示】
第一部分是基礎字元，第二部分加上「量次」，意思就是「幾個」，組合起來就能描述大部分的格式規則。第三到五部分是進階用法，最後有三個實作練習。

【鼓勵學生】
不用一下子全部背起來，先跟著我把基礎練熟，後面的進階符號看情況用到再查就好。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 基礎字元符號

<!--
【段落轉換】
好，我們正式進入第一部分——基礎字元符號。這部分的概念是正規表達式的地基，後面所有東西都建在上面，請大家特別專心。
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
【核心說明】
正規表達式是一種「描述字串長什麼樣子」的語言。就像你可以說「我要找一個長得像電話號碼的字串」，正規表達式就是讓你把這個「長相」精確地寫下來的工具。

【生活化比喻】
把它想像成一個「字串的模具」。你把模具放上去，符合模具形狀的字串就通過，不符合的就擋掉。

【程式世界怎麼用】
三個主要用途：一是「驗證」，確認格式是否正確；二是「搜尋」，在一大段文字裡找出符合條件的片段；三是「取代」，把符合條件的文字換成別的。

💼 業界實務：
前端後端都會用到。後端驗證使用者輸入是基本需求，前端表單驗證也少不了它。
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
【帶讀程式碼前的鋪陳】
我們先從最簡單的開始——字面字串比對。就是字串完全一樣才算符合。然後加入第一個特殊符號 \d，馬上感受到「一個符號代替一整類字元」的威力。

【逐步解說】
第一個例子："java".matches("java") 就是精確比對，大小寫不同就是 false。
第二個例子：\d 代表任何數字 0-9，所以 "3" 符合，"a" 不符合。

⚠️ 學生常見誤解：
Java 字串裡反斜線要寫兩個！正規表達式裡的 \d 在 Java 程式碼裡要寫成 "\\d"。這是初學者最容易搞混的地方，等一下我們寫程式碼的時候要特別注意。

【類比說明】
你說「我要找一個數字」，正規表達式說「\d」，Java 程式碼說「"\\d"」——三層包裝，但意思一樣。
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
【核心說明】
\d 是最常用的基礎符號，代表一個 0 到 9 的數字。記住它等同於 [0-9]。

【帶讀程式碼】
看這個手機號碼前 4 碼的範例：\d\d\d\d 代表「四個連續數字」。"0912" 符合，"O912" 不符合，因為 O 是字母不是數字。

⚠️ 學生常見誤解：
\d 只比對「一個」數字。要比對多個數字，要重複寫或用大括號 {} 設定次數——這是第二部分的內容，等一下就會教到。
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
【帶讀表格】
這張表格整理了三個最常用的字元類別，我們一起看。

【逐步解說】
\w：字母、數字、底線，注意底線也包含在裡面。大寫的 \W 就是相反，非字母數字底線的字元。
\d：純數字，大寫 \D 是非數字。
\s：空白字元，包括空格、Tab、換行，大寫 \S 是非空白。

【記憶技巧】
小寫代表「符合」，大寫代表「不符合」。word → \w；digit → \d；space → \s。

💼 業界實務：
\s+ 用來比對「一個以上的空白」非常常見，例如處理使用者輸入時去除多餘空格。
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
【帶讀程式碼】
來看範例。"Hello123" 符合 \w+，因為都是字母數字；但 "Hello World" 加了空格，空格不在 \w 範圍內所以 false。

【注意事項】
最後一行：\W+ 比對「非字母數字」，所以 "!@#" 這種特殊符號就符合了。

⚠️ 學生常見誤解：
\w 包含底線！所以 "hello_world" 符合 \w+，這跟直覺有點不同。

【互動引導】
這裡有個問題給大家想：如果我想比對「一個數字後面跟一個空格」，表達式應該怎麼寫？（答案：\d\s）
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
【核心說明】
句點 . 是萬用字元，代表「任意一個字元」，除了換行符號以外什麼都符合。

【帶讀程式碼】
"cat".matches(".at")：c 是任意字元，所以符合。"at" 不行，因為缺少一個字元，. 一定要有一個字元來對應。

⚠️ 學生常見誤解：
. 不是「零個或多個字元」，它是「恰好一個」任意字元！要比對多個要加量次符號。
另外，如果你真的想比對「句點」這個符號本身，要用 \. 跳脫，不然它會被當成萬用字元。

【類比說明】
萬用字元就像填空題的「___」，每個底線恰好填一個字，不能空著也不能填兩個。
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
【核心說明】
中括號 [] 讓你自己定義一個「字元集合」，只要比對到集合裡的其中一個字元就算符合。

【帶讀表格】
[cbm] 代表 c、b 或 m 其中一個；[A-Z] 用連字號表示範圍，從 A 到 Z 的所有大寫字母。

⚠️ 學生常見誤解：
有人會試著寫 [A-z]，意思是「從大寫 A 到小寫 z」，但 ASCII 碼裡大小寫字母中間還有其他符號，所以這樣寫是有陷阱的！要分開寫 [A-Za-z]。

💼 業界實務：
台灣身份證字號的第一碼是英文字母，就可以用 [A-Za-z] 來比對，等一下練習題就會用到。
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
【帶讀程式碼】
"cat" 符合 [cbm]at，因為 c 在集合裡；"fat" 不符合，f 不在 [cbm] 裡。

【重點提醒】
中括號 [] 一次只比對「一個」字元，但你可以設定很多選擇。如果想比對「一個字母後面跟三個數字」，要寫 [A-Za-z]\d\d\d 或 [A-Za-z]\d{3}。

【互動引導】
給大家一秒想想：[A-Za-z0-9] 和 \w 的差別是什麼？（答：\w 還包含底線 _）
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
【核心說明】
這頁比較進階，在中括號裡可以用 && 做「交集」，意思是兩個條件都要符合。

【帶讀範例】
[a-z&&[^aeiou]] 的意思是「小寫字母，而且不是母音」，也就是小寫輔音字母。

[依脈絡推斷]
這個語法在業界不太常見，通常直接寫清楚範圍更易讀。但考試或面試有時會出現，知道有這個語法就好。
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
【核心說明】
在 [] 最開頭加 ^ 就是「否定」，代表「不包含這些字元」。

【帶讀說明】
[^A-Z] 就是「不是大寫字母的任意字元」，[^0-9] 等同 \D。

⚠️ 學生常見誤解：
^ 必須在中括號「最開頭」才有否定效果。寫 [A^Z] 的話，^ 就只是字面的 ^ 字元，不是否定！

【類比說明】
就像用篩子，[A-Z] 是「只讓大寫字母通過」，[^A-Z] 是「讓所有非大寫字母通過」，把篩子翻過來。
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
| `|` | `\\|` | 字面管道符號 |

<!--
【核心說明】
正規表達式裡有些字元有特殊意義，比如 . () {} * 等。如果你真的想比對這些符號本身，要在前面加 \ 跳脫。

【帶讀表格】
這張表列了常見的需要跳脫的符號。在 Java 裡，一個反斜線要寫成 \\，所以跳脫用的 \. 在 Java 字串裡要寫 "\\."。

⚠️ 學生常見誤解：
雙重跳脫讓初學者非常困惑。記住規則：Java 字串先做一層跳脫（\\ → \），然後 regex 再做一層（\. → 字面句點）。
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
【帶讀程式碼】
電話號碼 "(02)-26669999" 含有括號，括號在 regex 裡是分組符號，所以要跳脫。
\(\d{2}\)-\d{8} 才是正確寫法，讓括號變成字面的括號。

⚠️ 學生常見誤解：
如果不跳脫，(\d{2}) 看起來像正確的表達式，但括號被解讀成「分組」而不是字面括號，比對結果就會不對。先用字面字串測試，再加特殊符號，是好的除錯習慣。
-->
---

# `|` — 管道（OR 運算）

管道 `|` 可以**同時比對多個模式**，相當於 Java 的 OR 運算

| 表達式 | 說明 |
| --- | --- |
| `A\|B` | 符合 A 或符合 B |
| `Mary\|Tom` | 符合其中一個名字 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 需要 AND 條件時用 <code>&&</code>（兩個 &），單個 <code>&</code> 無效果
</div>

```java
System.out.println("Mary".matches("Mary|Tom")); // true
System.out.println("Tom".matches("Mary|Tom"));  // true
System.out.println("John".matches("Mary|Tom")); // false
```

<!--
【核心說明】
管道符號 | 就是 OR 的意思，讓同一個表達式能比對多種模式。

【帶讀範例】
"Mary|Tom" 可以比對 "Mary" 或 "Tom"，任一個都 true。

💼 業界實務：
驗證電話號碼格式時，行動電話和市內電話格式不同，就用 | 把兩種格式串起來。等一下練習 1 就是這樣做。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 量次與分組

<!--
【段落轉換】
基礎字元符號大家都跟上了嗎？現在進入第二部分——量次與分組。有了量次，一個符號就可以代表「好幾個字元」，表達式會變得超級簡潔。
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
【核心說明】
大括號 {} 讓你設定「前面的符號要出現幾次」。

【帶讀說明】
\d{4} 就是四個數字，比手寫 \d\d\d\d 更清楚，也更好維護。

【帶讀程式碼】
電話號碼 "0912-345-678" 的格式就是 \d{4}-\d{3}-\d{3}。

⚠️ 學生常見誤解：
大括號裡面不能有空格！{3, 5} 是錯的，要寫 {3,5}。
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
【核心說明】
? * + 是三個量次修飾詞，分別代表「0或1次」、「0或多次」、「1或多次」。

【帶讀程式碼】
John(na)?son：? 讓 na 可有可無，所以 "Johnson" 和 "Johnnason" 都符合。
John(na)*son：* 允許 na 出現任意次，包括 0 次。
John(na)+son：+ 要求至少 1 次 na，所以 "Johnson" 不符合。

⚠️ 學生常見誤解：
? 只管「前面一個字元或分組」，不是整個表達式！(na)? 讓 na 這個群組可有可無，而 na? 只讓 a 可有可無。

【記憶口訣】
? 有點猶豫（0或1）、* 什麼都行（0或多）、+ 至少要有（1或多）。
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
【核心說明】
{} 除了固定次數，還可以設定範圍：{n,m} 代表 n 到 m 次，{n,} 代表至少 n 次。

【帶讀程式碼】
(son){3,5} 要求 "son" 出現 3 到 5 次，"sonsonson" 是 3 次所以符合，"sonson" 只有 2 次就不行。

💼 業界實務：
電話號碼的區碼不固定（2到4碼），用 {1,3} 搭配前面的 0，就可以涵蓋台灣所有區碼格式。
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
【帶讀表格】
這張表把所有量次符號整理在一起，對照著看很清楚。

【重點複習】
? 等同 {0,1}，* 等同 {0,}，+ 等同 {1,}。知道這個對應關係，就算忘記符號也能用 {} 寫出一樣的效果。

【互動引導】
大家猜猜：如果我想比對「剛好 6 個數字」，表達式怎麼寫？（答：\d{6}）
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
【核心說明】
這頁是進階概念，了解就好，初學時不需要背。「貪婪」是預設行為——盡量比對最長的字串。

【帶讀程式碼】
HTML 範例很經典：<div>A</div><div>B</div>
- 貪婪的 <div>.*</div> 會從第一個 <div> 一路比到最後一個 </div>，把整段都換成 TEXT。
- 懶惰的 <div>.*?</div> 只比對「最短符合」，每對 <div>...</div> 分別被替換，所以結果是 TEXTTEXT。

💼 業界實務：
解析 HTML 或 XML 時，一定要用懶惰量詞 .*?，否則會多抓到不想要的內容。
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
【核心說明】
.* 組合是「任意長度的任意字串」，非常實用。

【帶讀表格】
Java.* 表示以 Java 開頭；.*Java 以 Java 結尾；.*Java.* 中間含有 Java 都符合。

【帶讀程式碼】
"Hello World 123".matches("Hello.*") 是 true，開頭是 Hello，後面任意都行。

⚠️ 學生常見誤解：
matches() 比對的是「整個字串」，不是找子字串。如果你想找字串中間含有某段，要用 .*XXX.* 包住兩邊。
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
【核心說明】
小括號 () 用來把幾個字元「群組起來」，讓量次修飾詞可以套用在整個群組上。

【帶讀程式碼】
兩種寫法：\d{4}-\d{3}-\d{3} 和 \d{4}(-\d{3}){2}，結果一樣。後者用 () 把 -\d{3} 打包，再用 {2} 說要重複 2 次，更簡潔。

💼 業界實務：
分組除了控制量次，還有「擷取」的功能，後面的 Matcher.group() 方法就靠這個取出比對到的內容。
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
【核心說明】
反向引用是比較進階的概念：在表達式裡引用「前面某個分組實際比對到的內容」。

【帶讀程式碼】
(\w+) \1 .*：第 1 組抓到 "hello"，\1 就代表「必須也是 hello」。所以 "hello hello world" 符合，但 "hello world world" 不符合，因為 \1 要的是 hello 不是 world。

HTML 標籤範例：<(\w+)>.*</\1> 確保開標籤和閉標籤的名稱一樣。

💼 業界實務：
偵測重複單字（如 "the the"）就可以用反向引用，這在文字校稿工具裡很常見。
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
【帶讀表格】
這頁把 (\w+) \1 .* 拆開一格一格解釋，讓我們一起走一遍。

【逐步解說】
(\w+) 先抓到第一個單字，存成第 1 組。
空格比對一個空格。
\1 說「必須和第 1 組一樣」，所以只有剛才那個單字才符合。
空格再比一個空格。
.* 後面任意。

⚠️ 學生常見誤解：
\1 不是「再比一個單字」，而是「必須和第 1 組完全相同的文字」。學生常以為只要有一個字就行，但其實 \1 是複製了前面比到的結果。
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
【核心說明】
具名分組讓你給分組取個有意義的名字，比用數字 \1 更清楚，也不怕順序改變。

【帶讀程式碼】
(?<year>\d{4}) 定義了名為 year 的分組，比對四個數字。然後可以用 matcher.group("year") 取出結果，比 group(1) 更好讀。

💼 業界實務：
解析日期、電話、身分證這類有結構的字串時，具名分組讓程式碼自我說明，維護更容易。
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
【帶讀程式碼】
這是完整的 Matcher 範例。Pattern.compile() 先編譯表達式，matcher() 套用到字串，m.matches() 確認整個字串符合後，用 m.group("year") 等方法取出各個欄位。

⚠️ 學生常見誤解：
要先成功呼叫 matches() 或 find()，才能呼叫 group()。如果直接呼叫 group()，會丟出 IllegalStateException。

💼 業界實務：
Pattern.compile() 把表達式預先編譯，如果同一個表達式要用很多次，這比每次重新解析快很多。應該把 Pattern 設為常數，不要每次方法呼叫都重新 compile。
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
【核心說明】
非擷取分組 (?:...) 有分組的效果（可以套用量次），但不會記錄比對結果，所以不佔用分組編號、效能也比較好。

【帶讀程式碼】
.*\.(?:com|org) 比對以 .com 或 .org 結尾的網址，但不需要取出 com 或 org 的值，用非擷取分組節省記憶體。

💼 業界實務：
大量搜尋時，能用 (?:...) 就不要用 (...)，可以提升效能。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 位置與進階符號

<!--
【段落轉換】
量次和分組搞定了，我們進入第三部分——位置符號和進階符號。這部分的重點是「比對位置」而不是「比對字元」。
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
【核心說明】
^ 和 $ 比對的是位置，不是字元。^ 是字串開頭，$ 是字串結尾。

【帶讀表格】
^Java 表示字串以 Java 開頭；Java$ 表示以 Java 結尾；^Java$ 就是字串恰好等於 "Java"。

【帶讀程式碼】
注意這裡用的是 Pattern 和 find()，不是 matches()。find() 可以在字串中間搜尋子字串，搭配 ^ 和 $ 才能定位到行的開頭結尾。

⚠️ 學生常見誤解：
在 matches() 裡，因為本來就是比對整個字串，^ 和 $ 幾乎沒有作用。^ 和 $ 在 find() 裡才真正發揮定位的效果。
-->
---

# `(?i)` 與 `\b` — 旗標與單字邊界

| 表達式 | 說明 |
| --- | --- |
| `(?i)` | 忽略大小寫，置於表達式開頭 |
| `\\b` | 單字邊界：匹配一個**位置**，用於比對完整單字 |
| `\\bJava\\b` | 只比對完整單字 Java（不含 JavaScript） |

<!--
【帶讀表格】
(?i) 放在表達式開頭，讓後面的比對忽略大小寫，很常用。\b 是單字邊界，比對的是一個「位置」，不消耗字元。

💼 業界實務：
搜尋完整單字時一定要用 \b，不然搜尋 "Java" 也會找到 "JavaScript" 裡的 Java，這在做全文搜尋時很重要。
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
【帶讀程式碼】
(?i) 讓 "JAVA" 也能符合 "(?i)java"，不管大小寫幾種組合都 true。
\b 的效果：\bJava\b 只比對完整的 Java，JavaScript 不符合，因為 Java 後面緊接著非單字邊界的字元。

【互動引導】
大家想想：如果搜尋 "cat"，"concatenate" 裡的 cat 會被找到嗎？（用 \b 就不會，不用 \b 就會）
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
【核心說明】
環視斷言是進階技巧，概念是「偷看但不吃掉字元」。

【帶讀表格】
四種環視：正向先行 (?=...) 是「右邊必須是...」；負向先行 (?!...) 是「右邊不能是...」；後行是看左邊。

💼 業界實務：
密碼強度驗證最常用到環視：「必須含有數字」、「必須含有大寫字母」可以用多個 (?=...) 同時設定條件，非常方便。
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
【帶讀程式碼】
密碼範例 ^(?=.*\d).{8,}$：
- (?=.*\d) 從開頭確認「後面某處有數字」
- .{8,} 確認長度至少 8 個字元
- 兩個條件都從同一個起點（字串開頭）出發，互不干擾

取出金額範例：(?<=\$)\d+ 只取 $ 後面的數字，$ 本身不在結果裡。

⚠️ 學生常見誤解：
環視斷言「不消耗字元」——確認過之後，比對位置不移動。這讓初學者很困惑，要多看幾個範例才會有感覺。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# String 類別常用方法

<!--
【段落轉換】
第三部分的位置符號比較抽象，大家第一次看不完全理解沒關係，多用幾次就熟了。現在進入第四部分——String 類別的方法。這些是最常用的，一定要熟悉！
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
【帶讀表格】
Java 的 String 類別內建四個跟正規表達式相關的方法，我們一個個看。

【逐步解說】
matches()：判斷「整個字串」是否符合，只有完全匹配才回傳 true。
split()：用正規表達式做分隔符，把字串切開，很像用逗號分割 CSV。
replaceFirst()：只取代第一個符合的片段。
replaceAll()：取代所有符合的片段。

⚠️ 學生常見誤解：
matches() 比對「整個字串」——所以 "Hello World".matches("Hello") 是 false，因為後面還有 " World"。如果要搜尋子字串，要用第五部分的 Matcher.find()。
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
【帶讀程式碼】
matches() 範例很直覺，格式完全符合才是 true。

split() 的妙用：csv.split(",+") 讓一個以上的逗號都算分隔符，所以雙逗號 ",," 不會產生空字串。這比 split(",") 更實用。

💼 業界實務：
解析 CSV 檔案時，欄位之間可能有不規則的空格，用 split("\\s*,\\s*") 可以同時處理逗號和前後空格。
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
【帶讀程式碼】
replaceFirst() 只換第一個，replaceAll() 全部換。

遮蔽電話號碼這個範例很實用：把每個數字換成星號。在日誌（log）裡遮蔽個人資料是業界常見的需求。

💼 業界實務：
記錄系統日誌時，電話、身分證、信用卡號等個資都要遮蔽，replaceAll() 搭配正規表達式是標準做法。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
# 正規表達式套件
# `java.util.regex`

<!--
【段落轉換】
第四部分的 String 方法很實用，但有個限制：只能比對整個字串或做簡單取代。如果要在長文字中搜尋、或者取得比對到的位置，就需要第五部分的 Pattern 和 Matcher 套件了。
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
【核心說明】
java.util.regex 套件提供兩個核心類別：Pattern 負責「編譯」表達式，Matcher 負責對字串「執行」比對。

【生活化比喻】
Pattern 像是做好的「印章」（編譯好的表達式），Matcher 像是「蓋印」的動作（對某個字串套用）。同一個印章可以蓋在很多張紙上，這就是為什麼 Pattern 可以被重用。

【帶讀程式碼】
Pattern.compile("\\d{4}") 先建立 Pattern，matcher() 套用到字串，find() 在字串中搜尋，group() 取出結果。

⚠️ 學生常見誤解：
這和 String.matches() 最大的差別是：Matcher.find() 可以在長文字中找子字串，不需要整個字串符合。
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
【帶讀表格】
Pattern 有幾個常用方法。compile() 是最基礎的，可以加旗標（像 CASE_INSENSITIVE）來設定比對選項。

【重點】
Pattern.matches() 是靜態方法，等同 String.matches()。p.split() 的功能和 String.split() 一樣，但用法稍有不同。
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
【帶讀程式碼】
三個範例分別示範：
1. Pattern.CASE_INSENSITIVE 旗標忽略大小寫，比 (?i) 更適合在多處使用同一個 pattern 的情境。
2. Pattern.matches() 靜態方法，功能同 String.matches()。
3. Pattern.compile(",\\s*").split() 分割時同時消除逗號後的空格，比 split(",") 更強大。

💼 業界實務：
多個地方都要用相同正規表達式時，把 Pattern 定義成靜態常數，避免重複 compile，提升效能。
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
【核心說明】
Pattern.quote() 解決一個很實際的問題：當使用者輸入的字串要直接當成搜尋 pattern 時，裡面可能含有 regex 的特殊字元，會破壞表達式。

【帶讀程式碼】
"3.14" 裡的 . 如果不 quote，會被當成萬用字元，"3X14" 也會符合。用 Pattern.quote() 包起來，點就變成字面點了。

💼 業界實務：
搜尋功能接受使用者輸入的關鍵字時，一定要用 Pattern.quote() 防止 regex 注入攻擊。
-->
---

# Predicate 整合 (JDK 11+)

`Pattern` 可以直接轉為 `Predicate`，方便與 Stream API 結合使用

| 方法 | 說明 |
| --- | --- |
| `asPredicate()` | 判斷**子字串**是否存在 |
| `asMatchPredicate()` | 判斷**整個字串**是否完全符合 |

```java
List<String> list = List.of("apple", "banana", "123", "456");

// 篩選出純數字的字串
var isNumeric = Pattern.compile("\\d+").asMatchPredicate();

list.stream()
    .filter(isNumeric)
    .forEach(System.out::println); // 123, 456
```

<!--
【核心說明】
JDK 11 之後，Pattern 可以轉成 Predicate，和 Stream API 完美搭配。

【帶讀程式碼】
asMatchPredicate() 讓整個字串必須完整符合；asPredicate() 只要有子字串符合就行。
搭配 stream().filter() 可以從一串字串裡篩出符合格式的項目，一行代碼非常簡潔。

💼 業界實務：
在 Spring Boot 的資料處理或批次作業裡，這個搭配非常常見，可以取代手寫的迴圈和 if。
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
【核心說明】
splitAsStream() 是 split() 的 Stream 版本，讓切割後可以直接串接 Stream 操作。

【帶讀程式碼】
把 CSV 字串切開後，直接 map 轉大寫再 forEach 印出。

💼 業界實務：
vs split()：如果切開後需要做很多處理（過濾、轉換、收集），用 splitAsStream() 更流暢；如果只需要簡單拿到陣列，用 split() 就好。
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
【帶讀表格】
兩個實用旗標：MULTILINE 讓 ^ 和 $ 對每一行都有效；DOTALL 讓 . 也能比對換行符號。

【帶讀程式碼】
MULTILINE 範例：多行文字裡用 ^\w+$ 搜尋每行，找到 3 行。如果沒有 MULTILINE，^ 只代表整個輸入的開頭。

💼 業界實務：
解析 HTML、XML 或多行日誌時，經常需要這兩個旗標。DOTALL 配合 .*? 讓跨行的標籤都能比對到。
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
【帶讀表格】
Matcher 最常用的方法：find() 搜尋下一個符合的子字串，可以在迴圈裡不斷呼叫；matches() 比對整個字串；group() 取出比對結果。

【重點提醒】
group(n) 取出第 n 個 () 分組的內容。group() 或 group(0) 取出整個比對結果。
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
【帶讀程式碼】
"Java 11 and Java 17" 裡搜尋 Java 版本號：
- 第一次 find() 找到 "Java 11"，group(1) 取出 "11"。
- find(12) 從 index 12 開始找，跳過前面，找到 "Java 17"。

【類比說明】
Matcher 就像一根指針，每次 find() 就往右移動，找到下一個符合的地方。
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
【帶讀表格】
三種比對方式的差異，很容易混淆：
- matches()：整個字串必須完整符合
- lookingAt()：從開頭開始符合，但後面可以有多餘的字元
- find()：任意位置都行

【帶讀程式碼】
"123abc" 對 \d{3}：
- matches() false（abc 沒符合）
- lookingAt() true（開頭 123 符合）
- find() true（找到 123）
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
【帶讀表格】
Matcher 的位置方法：start() 和 end() 告訴你比對到的子字串的起始和結束索引。

【使用場景】
如果要在文字中標記找到的位置，或者做高亮（highlight）處理，就需要這些索引值。
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
【帶讀程式碼】
"Java 11 and Java 17" 裡找數字：
find() 找到 "11"，start() 是 5，end() 是 7（不包含 index 7，也就是 "11" 佔 index 5 和 6）。
replaceAll() 把所有數字換成 X。

⚠️ 學生常見誤解：
end() 是「不含」的結束索引，就像 String.substring(start, end) 的邏輯，end 那個字元不包含在結果裡。
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
【核心說明】
JDK 9 之後 Matcher 多了兩個強大方法：replaceAll(Function) 讓取代內容可以動態計算；results() 讓所有比對結果都能用 Stream 處理。

【帶讀程式碼】
把 "10 plus 20" 裡每個數字乘以 2：用 Lambda 取出比對到的數字字串，轉成 int 乘 2，再轉回 String。一行搞定！

💼 業界實務：
批次處理文字時（例如把文件裡的金額全部換算成另一種貨幣），動態 replaceAll 比手寫迴圈優雅很多。
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
// 版本: 21，位置: 16
```

<!--
【帶讀程式碼】
這是一個完整的 while(find()) 搜尋範例，從 "Java 8, Java 11, Java 21" 裡取出所有版本號和位置。

【逐步解說】
while (m.find()) 不斷搜尋下一個符合的片段，m.group(1) 取出括號裡的版本號，m.start() 取位置。三個版本號依序印出。

【互動引導】
等等我們的完整應用範例也會用到這個模式，大家先把 while(m.find()) 的節奏記起來。
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
【帶讀程式碼】
兩個實際應用範例，把這章學的東西串起來：
1. 從報表文字裡抓出所有數字，用 while(find()) 迴圈蒐集。
2. 遮蔽電話號碼，用 replaceAll() 把符合電話格式的部分替換成遮蔽字元。

💼 業界實務：
這兩個場景在真實專案裡非常常見：日誌分析要抓出數字統計，個資保護要遮蔽敏感資料。
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
【出題前的鋪陳】
好，現在來實作！這個練習把我們剛學的東西綜合運用：字元類別、量次符號、管道 |。

【問題引導】
台灣電話有兩大類：行動電話 0XXX-XXX-XXX，和市內電話（有括號版和連字號版）。這三種格式要怎麼合在一個表達式裡？

【等待與觀察】
給大家 2 分鐘想想，不用急著看提示。

【解說要點】
關鍵是用 | 把三種格式串起來，每一種分別寫好再組合。
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
【帶讀解法】
三種格式分別對應三個表達式，用 | 連接。

行動電話 0\d{3}-\d{3}-\d{3}：0 開頭，後面三個量次 \d{3}，中間用 - 分隔。
市內括號版 \(0\d{1,3}\)\d{7,8}：括號要跳脫，區碼 1-3 碼（加上 0 是 2-4 碼），電話 7-8 碼。
市內連字號版 0\d{1,3}-\d{7,8}：同上只是格式不同。

⚠️ 提醒：
括號一定要跳脫！\( 和 \)，不然會被當成分組。
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
【出題前的鋪陳】
台灣身份證字號有固定的結構：1 個英文字母 + 1 個性別碼 + 8 個數字，總共 10 碼。

【問題引導】
第 1 碼：大小寫都行，用什麼表達？第 2 碼：只能 1 或 2，用什麼表達？

【等待與觀察】
給大家思考 2 分鐘。

【解說要點】
進階版的排除 6 都字母，用 [^abdefh] 加上 (?i) 忽略大小寫是很聰明的組合。
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
System.out.println(id.matches("(?i)[^abdefh][12]\\d{8}"));
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>(?i)</code> 讓整個表達式忽略大小寫，搭配 <code>[^abdefh]</code> 排除6都字母
</div>

<!--
【帶讀解法】
基本版：[A-Za-z][12]\d{8}
- 第 1 碼：[A-Za-z] 任何英文字母
- 第 2 碼：[12] 只有 1 或 2
- 後 8 碼：\d{8}

進階版：(?i)[^abdefh][12]\d{8}
- (?i) 讓整個表達式不分大小寫
- [^abdefh] 排除 6 都字母（A B D E F H）

💡 提示：
(?i) 讓 [^abdefh] 同時排除大小寫，所以不用寫 [^ABDEFHabdefh]，更簡潔。
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
【出題前的鋪陳】
Email 格式是正規表達式的經典應用，分四個部分：帳號、@、網域、頂級網域。

【問題引導】
帳號可以有點 . 加號 + 連字號 -，怎麼表達？網域後面可以有多層 .com.tw，怎麼讓它可重複？

【等待與觀察】
給大家 3 分鐘思考，這題難度比前兩題高一點。

【解說要點】
關鍵是頂級網域用 (\.[\w-]{2,})+ 表示「一個以上的 .XXX」，可以匹配 .com 也可以 .com.tw。
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
【帶讀解法】
Email 表達式 [\w.+-]+@[\w-]+(\.[\w-]{2,})+ 拆解：
- [\w.+-]+：帳號，字母數字底線加上 . + -，至少一個字元
- @：字面 @ 符號
- [\w-]+：網域名稱
- (\.[\w-]{2,})+：頂級網域，點開頭加至少兩個字元，可重複（處理 .com.tw 這種）

⚠️ 學生常見誤解：
Email 驗證用 regex 只能確認格式，不能確認信箱真的存在。最終還是要靠發確認信才能確認。

💼 業界實務：
前後端都要做驗證，正規表達式是第一道防線，發確認信是第二道。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天學了正規表達式的完整體系，從基礎字元、量次分組、位置符號，到 String 方法和 Pattern/Matcher 套件。

【鼓勵學生】
正規表達式一開始看起來像亂碼，但用多了你會開始覺得它很優雅。業界有個玩笑說「有問題不知道怎麼解？用 regex！」雖然是誇張了一點，但正規表達式確實可以解決很多文字處理問題。

【提問引導】
現在 Q&A 時間，練習題做完有疑問也可以提出來。
-->

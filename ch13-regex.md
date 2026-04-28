---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
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

---
layout: default
---

# Outline

- **第一部分：正規表達式基礎**
  - 數字符號 `\d`、大括號 `{}`、分組 `()`、跳脫符號
  - 量次修飾詞 `?`、`*`、`+`、`{n,m}`
- **第二部分：String 類別常用方法**
  - `matches()`、`split()`、`replaceFirst()`、`replaceAll()`
- **第三部分：正規表達式的特殊字元**
  - 忽略大小寫 `(?i)`、單字邊界 `\b`
  - 字元類別 `\w`、`\d`、`\s`、`.`、`[]`、`^`、`$`
- **第四部分：正規表達式套件**
  - `java.util.regex` — `Pattern` 與 `Matcher`
- **實作練習**

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 正規表達式基礎

---

# 什麼是正規表達式？

正規表達式 (Regular Expression) 是一種**描述字串模式**的語言，主要用於：

- **模式比對** — 判斷字串是否符合指定格式
- **搜尋** — 在文字中找出符合條件的子字串
- **取代** — 將符合條件的字串取代為其他內容

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>使用時機：</b> 驗證電話號碼、Email、身份證字號等複雜格式時，正規表達式能讓程式碼大幅簡化。
</div>

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
💡 在 Java 中，反斜線需要跳脫：正規表達式的 <code>\d</code> 寫成字串要寫 <code>"\\d"</code>
</div>

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

---

# 跳脫符號 — 範例

```java
// 字串 (02)-26669999 含有字面括號，比對時需跳脫
String phone = "(02)-26669999";
System.out.println(phone.matches("\\(\\d{2}\\)-\\d{8}")); // true
System.out.println(phone.matches("(\\d{2})-\\d{8}"));     // false，() 被視為分組
```

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# String 類別常用方法

---

# String 類別相關方法

| 方法 | 說明 |
| --- | --- |
| `matches(regex)` | 判斷**整個字串**是否符合正規表達式 |
| `split(regex)` | 用正規表達式分割字串，回傳 `String[]` |
| `replaceFirst(regex, str)` | 取代**第一個**符合正規表達式的子字串 |
| `replaceAll(regex, str)` | 取代**所有**符合正規表達式的子字串 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>matches()</code> 必須比對整個字串；若要搜尋子字串，需使用第四部分的 <code>Pattern</code>/<code>Matcher</code>
</div>

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 正規表達式的特殊字元

---

# `(?i)` 與 `\b` — 旗標與單字邊界

| 表達式 | 說明 |
| --- | --- |
| `(?i)` | 忽略大小寫，置於表達式開頭 |
| `\\b` | 單字邊界：匹配一個**位置**，用於比對完整單字 |
| `\\bJava\\b` | 只比對完整單字 Java（不含 JavaScript） |

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
💡 <code>\\b</code> 在子字串搜尋時更常用，搭配第四部分的 <code>Pattern.find()</code> 效果更明顯
</div>

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

---

# `\w`、`\d`、`\s` — 範例

```java
System.out.println("Hello123".matches("\\w+"));    // true（字母數字底線）
System.out.println("Hello World".matches("\\w+")); // false（含空格）
System.out.println("12345".matches("\\d+"));       // true
System.out.println("Hello World".matches("\\w+\\s\\w+")); // true（字 空格 字）
System.out.println("!@#".matches("\\W+"));         // true（非字母數字）
```

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

---

# `[]` — 範例

```java
System.out.println("cat".matches("[cbm]at")); // true（c 在 [cbm] 內）
System.out.println("fat".matches("[cbm]at")); // false（f 不在 [cbm] 內）
System.out.println("A".matches("[A-Z]"));     // true
System.out.println("a".matches("[A-Za-z]"));  // true
// 注意：不可用 [A-z]，大小寫字母中間的 ASCII 含有其他符號
```

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# 正規表達式套件
# `java.util.regex`

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

---

# `Pattern` 類別常用方法

| 方法 | 說明 |
| --- | --- |
| `Pattern.compile(regex)` | 編譯正規表達式，回傳 Pattern 物件 |
| `Pattern.compile(regex, flags)` | 帶旗標編譯（如 `Pattern.CASE_INSENSITIVE`） |
| `Pattern.matches(regex, input)` | 靜態方法，比對整個字串 |
| `p.matcher(input)` | 以此 Pattern 建立 Matcher 物件 |
| `p.split(input)` | 用此 Pattern 分割字串 |

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

---

# `Matcher` 類別常用方法（一）

| 方法 | 說明 |
| --- | --- |
| `find()` | 搜尋下一個符合的子字串，找到回傳 `true` |
| `find(start)` | 從 start 索引位置開始搜尋 |
| `matches()` | 比對**整個**輸入字串 |
| `group()` | 回傳目前找到的子字串 |
| `group(n)` | 回傳第 n 個 `()` 分組所比對到的子字串 |

---

# `Matcher` 類別常用方法（二）

| 方法 | 說明 |
| --- | --- |
| `start()` | 回傳比對子字串的起始索引 |
| `end()` | 回傳比對子字串的結束索引（不含） |
| `replaceFirst(str)` | 取代第一個符合的子字串 |
| `replaceAll(str)` | 取代所有符合的子字串 |

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

---
layout: default
---

# 練習 1：電話號碼格式驗證
### 任務說明

撰寫程式，以 `Scanner` 輸入電話號碼，判斷是否符合以下格式之一：

1. **行動電話**：`0XXX-XXX-XXX`（0 開頭，4碼-3碼-3碼）
2. **市內電話（含括號）**：`(02)12345678`（區碼 2~4 碼，電話號碼 7~8 碼）
3. **市內電話（含連字號）**：`02-12345678`（區碼 2~4 碼，電話號碼 7~8 碼）

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

---
layout: end
---

# 課程結束
### 感謝聆聽！

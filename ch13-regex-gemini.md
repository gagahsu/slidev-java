---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: 第十三章：正規表達式完整課程
routeAlias: ch13g
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
    第十三章：正規表達式<br>(Regular Expression)
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    從硬功夫搜尋到優雅的模式匹配
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

---
layout: default
---

# Outline

- **13-1 使用 Java 硬功夫搜尋文字**
- **13-2 使用 String 類別處理正規表達式**
- **13-3 正規表達式的特殊字元 (核心語法)**
- **13-4 matches() 方法的萬用程式與功能擴充**
- **13-5 再談 String 類別有關的正規表達方法**
- **13-6 正規表達式套件 (java.util.regex)**

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 13-1
# 使用 Java 硬功夫搜尋文字

---
layout: default
---

# 為什麼需要正規表達式？
### 範例：驗證台灣手機號碼格式

我們希望驗證一個字串是否符合 `xxxx-xxx-xxx`：
- 長度必須是 12 位。
- 第 5 位與第 9 位必須是 `-`。
- 其他位置必須是數字。

---

# 沒使用正規表達式的處理方式
### 教材實例：ch13_1.java (手寫邏輯)

```java
public static boolean taiwanPhone(String str) {
    if (str.length() != 12) return false;
    for (int i = 0; i <= 3; i++) {
        if (!Character.isDigit(str.charAt(i))) return false;
    }
    if (str.charAt(4) != '-') return false;
    for (int i = 5; i <= 7; i++) {
        if (!Character.isDigit(str.charAt(i))) return false;
    }
    if (str.charAt(8) != '-') return false;
    for (int i = 9; i <= 11; i++) {
        if (!Character.isDigit(str.charAt(i))) return false;
    }
    return true;
}
```

---

# 硬功夫搜尋的缺點

- **程式碼冗長**：簡單的格式檢查需要大量的 `if` 與 `for`。
- **維護性差**：如果需求改為 `(02)-xxxxxxxx`，整段程式碼必須打掉重練。
- **可讀性低**：別人很難一眼看出這段程式在驗證什麼格式。

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 13-2
# 使用 String 類別處理正規表達式

---
layout: default
---

# 使用 String.matches()
### 正規表達式的威力

同樣的手機驗證需求，在正規表達式中只需要一行：

```java
String pattern = "\\d{4}-\\d{3}-\\d{3}";
System.out.println("0952-282-020".matches(pattern)); // true
```

| 優點 | 說明 |
| --- | --- |
| **簡潔** | 程式碼從 15 行縮減到 1 行。 |
| **強大** | 透過特定的符號（如 `\\d`）精確描述規則。 |

---

# 正規表達式基礎：`\d`

| 符號 | 說明 |
| --- | --- |
| `\d` | 代表一個數字 (0-9)。 |

**Java 範例 (ch13_3.java)：**

```java
String pattern = "\\d"; // 注意：Java 中反斜線要雙寫
System.out.println("9".matches(pattern)); // true
System.out.println("a".matches(pattern)); // false
```

---

# 重複出現處理：大括號 `{}`
### 教材實例：ch13_6.java

如果字元重複出現，不需要寫多次 `\\d\\d\\d`。

| 語法 | 說明 |
| --- | --- |
| `{n}` | 出現次數剛好為 n 次。 |

```java
// 驗證四位數字
String pattern = "\\d{4}";
System.out.println("1234".matches(pattern)); // true
System.out.println("123".matches(pattern));  // false
```

---

# 分組功能：小括號 `()`
### 教材實例：ch13_8.java

將多個字元組合在一起，視為一個單元。

```java
// 重複兩次的模式：-xxx
String pattern = "\\d{4}(-\\d{3}){2}";
System.out.println("0912-345-678".matches(pattern)); // true
```

---

# 處理文字中的特殊符號：跳脫
### 教材實例：ch13_9.java

如果要匹配字串中真的出現的括號 `(02)`，必須使用 `\\(` 與 `\\)`。

```java
// 驗證 (02)-xxxxxxxx 格式
String pattern = "\\(\\d{2}\\)-\\d{8}";
System.out.println("(02)-28350000".matches(pattern)); // true
```

---

# 管道符號：`|` (OR 運算)
### 教材實例：ch13_10.java

在多個候選模式中擇一匹配。

```java
String pattern = "Mary|Tom";
System.out.println("Mary".matches(pattern)); // true
System.out.println("Tom".matches(pattern));  // true
```

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 13-3
# 正規表達式的特殊字元 (核心語法)

---
layout: default
---

# 數量詞：`?` (0 或 1 次)
### 教材實例：ch13_11.java

代表前一個元素是「可有可無」的。

```java
// na 可有可無
String pattern = "John(na)?son";
System.out.println("Johnson".matches(pattern));   // true
System.out.println("Johnnason".matches(pattern)); // true
```

---

# 數量詞：`*` (0 到多次)
### 教材實例：ch13_13.java

代表前一個元素可以不出現，也可以出現無數次。

```java
String pattern = "John(na)*son";
System.out.println("Johnson".matches(pattern));     // true
System.out.println("Johnnanason".matches(pattern)); // true
```

---

# 數量詞：`+` (1 到多次)
### 教材實例：ch13_14.java

代表前一個元素「至少必須出現一次」。

```java
String pattern = "John(na)+son";
System.out.println("Johnson".matches(pattern));   // false
System.out.println("Johnnason".matches(pattern)); // true
```

---

# 指定次數區間：`{n,m}`
### 教材實例：ch13_15.java

| 語法 | 說明 |
| --- | --- |
| `{n,m}` | 出現次數介於 n 到 m 之間。 |

```java
// son 重複 3 到 5 次
String pattern = "(son){3,5}";
System.out.println("sonsonsonson".matches(pattern)); // true (4次)
```

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <b>注意：</b> 大括號內的逗號後方「不能有空格」，例如 <code>{3, 5}</code> 是錯誤的。
</div>

---

# 萬用字元：`.` (點號)
### 教材實例：ch13_18.java

匹配除換行符 `\n` 以外的任何「單一」字元。

```java
String pattern = ".at";
System.out.println("cat".matches(pattern)); // true
System.out.println("hat".matches(pattern)); // true
System.out.println("at".matches(pattern));  // false (少一個字元)
```

---

# 英數字類別：`\w` 與 `\W`
### 教材實例：ch13_16.java

| 符號 | 說明 |
| --- | --- |
| `\w` | 匹配字母、數字、下底線。同 `[a-zA-Z_0-9]`。 |
| `\W` | 匹配非英數字與下底線的字元。 |

```java
System.out.println("java_123".matches("\\w+")); // true
System.out.println("java-123".matches("\\w+")); // false (連字號不屬於 \\w)
```

---

# 空白字元：`\s` 與 `\S`
### 教材實例：ch13_17.java

| 符號 | 說明 |
| --- | --- |
| `\s` | 匹配空格、Tab、換行、換頁。 |
| `\S` | 匹配非空白字元。 |

```java
System.out.println(" ".matches("\\s"));  // true
System.out.println("\t".matches("\\s")); // true
```

---

# 數字類別：`\d` 與 `\D`

| 符號 | 說明 |
| --- | --- |
| `\d` | 匹配任何數字 `[0-9]`。 |
| `\D` | 匹配任何非數字字元。 |

```java
System.out.println("123".matches("\\d+")); // true
System.out.println("abc".matches("\\D+")); // true
```

---

# 字元分類：中括號 `[]`
### 教材實例：ch13_19.java

定義一個字元集合，匹配其中的任一個。

| 語法 | 說明 |
| --- | --- |
| `[aeiou]` | 匹配母音。 |
| `[a-z]` | 匹配所有小寫字母。 |
| `[A-Z]` | 匹配所有大寫字母。 |

```java
System.out.println("apple".matches("[aeiou].*")); // true (開頭是母音)
```

---

# 排除字元：中括號內的 `^`
### 教材實例：ch13_20.java

如果 `^` 出現在中括號的「第一個位置」，代表「非」的意思。

```java
// 排除數字
String pattern = "[^0-9]+";
System.out.println("Java".matches(pattern)); // true
System.out.println("Java8".matches(pattern)); // false
```

---

# 邊界錨點：`^` 與 `$`

| 符號 | 說明 |
| --- | --- |
| `^` | 匹配字串的開始。 |
| `$` | 匹配字串的結束。 |

```java
// 必須以 A 開頭且以 Z 結尾
String pattern = "^A.*Z$";
System.out.println("APPLE_TO_Z".matches(pattern)); // true
```

---

# 單字邊界：`\b`

匹配單字的起始或結束位置（如空格、標點符號）。

```java
// 搜尋完整的 Java 單字
String pattern = ".*\\bJava\\b.*";
System.out.println("I love Java programming".matches(pattern)); // true
```

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 13-5
# 再談 String 類別有關的正規表達方法

---
layout: default
---

# String.replaceFirst()
### 教材實例：ch13_23.java

僅替換「第一個」符合模式的子字串。

```java
String str = "Hello Java! I love Java.";
String pattern = "Java";
System.out.println(str.replaceFirst(pattern, "Python"));
// Result: "Hello Python! I love Java."
```

---

# String.replaceAll()
### 教材實例：ch13_25.java

替換「所有」符合模式的子字串。

```java
String str = "Hello Java! I love Java.";
String pattern = "Java";
System.out.println(str.replaceAll(pattern, "Python"));
// Result: "Hello Python! I love Python."
```

---

# 應用範例：隱藏手機資訊
### 教材實例：ch13_26.java

```java
String msg = "My phone is 0912-345-678";
// 將所有數字替換成星號
System.out.println(msg.replaceAll("\\d", "*"));
// Result: "My phone is ****-***-***"
```

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 13-6
# 正規表達式套件 (java.util.regex)

---
layout: default
---

# 進階操作：Pattern 與 Matcher

當需要更細緻的控制（如：找出所有符合的部分、取得位置）時，需使用 `java.util.regex` 套件。

| 核心類別 | 說明 |
| --- | --- |
| **Pattern** | 正規表達式的編譯物件。 |
| **Matcher** | 執行匹配操作的引擎。 |

```java
import java.util.regex.*;

Pattern p = Pattern.compile("\\d+");
Matcher m = p.matcher("I have 10 apples and 20 oranges");
```

---

# 循環搜尋：Matcher.find()
### 教材實例：ch13_30.java

`find()` 會在字串中不斷尋找下一個符合的部分。

```java
String str = "Call 0930-919-919 or 0952-001-001";
Pattern p = Pattern.compile("\\d{4}-\\d{3}-\\d{3}");
Matcher m = p.matcher(str);

while (m.find()) {
    System.out.println("找到電話: " + m.group());
    System.out.println("起始位置: " + m.start());
    System.out.println("結束位置: " + m.end());
}
```

---

# 練習題 1：台灣市話驗證
### 題目說明

請撰寫正規表達式來比對以下市話格式：
- 區碼：2 碼或 3 碼，0 開頭。
- 號碼：7 或 8 碼。
- 格式 A：`(02)12345678` (區碼有括號)
- 格式 B：`02-12345678` (區碼無括號，用連字號)

---

# 練習題 1：解題提示
### 提示說明 (ch13-15)

1. **區碼部分 (含括號或連字號)**：
   `(\(\d{2,3}\)|\d{2,3}-)`
2. **號碼部分**：
   `\d{7,8}`
3. **完整方案**：
   `^(\(\d{2,3}\)|\d{2,3}-)\d{7,8}$`

---

# 練習題 2：身分證字號驗證
### 題目說明

請驗證中華民國身分證字號：
- 共 10 碼。
- 第一碼為大寫英文字母。
- 第二碼為數字 1 (男) 或 2 (女)。
- 後續 8 碼為數字。

---

# 練習題 2：解題提示
### 提示說明

```java
// 正規表達式
String regex = "^[A-Z][12]\\d{8}$";
```

---

# 總結：正規表達式量詞表

| 符號 | 說明 | 次數 |
| --- | --- | --- |
| `?` | 出現 0 次至 1 次 | `{0,1}` |
| `*` | 出現 0 次至多次 | `{0,}` |
| `+` | 出現 1 次至多次 | `{1,}` |
| `{n}` | 出現 n 次 | `{n,n}` |
| `{n,}` | 出現至少 n 次 | `{n,}` |
| `{n,m}` | 出現 n 到 m 次 | `{n,m}` |

---
layout: end
---

# 課程結束
### 感謝您的參與！
#### 更多深入實例請參考 CH13.pdf 教科書內容。

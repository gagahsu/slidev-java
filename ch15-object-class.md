---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: Object 類別
routeAlias: ch15
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
    Object 類別
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「所有 Java 類別的共同祖先」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
今天我們要認識 Object 類別。聽起來很基礎，但這章的 equals() 和 hashCode() 是業界最常出問題的地方之一，一定要仔細學。

【為什麼要學這個？】
Object 是所有 Java 類別的祖先，你寫的每一個類別都隱含繼承它。搞清楚 Object 的方法，才能寫出正確的物件比較、集合操作。

【今天學完你會能做什麼】
學完之後你能正確覆寫 equals()、hashCode()、toString()，也能解釋為什麼「兩個內容一樣的物件 equals 卻是 false」這個經典 bug。
-->
---
layout: default
---

# Outline

- **認識 Object 類別** — `java.lang.Object` 與繼承關係
- **Objects 工具類別** — `java.util.Objects` 的 Null 安全設計
- **哈希碼與 `hashCode()`** — `Objects.hash()` 的現代實作
- **`equals()` 方法** — 搭配 Pattern Matching 的現代化寫法
- **`toString()` 方法** — 物件的字串表示
- **Records 與 Object 方法** — 自動實作 toString、equals、hashCode
- **其他 Object 方法** — `getClass()`、`clone()`、`finalize()`

<!--
【帶讀大綱】
今天的主角是 Object 類別的幾個重要方法。toString() 最簡單，hashCode() 和 equals() 是重點，最後還有一些比較少用但要知道的方法。

【學習重點預告】
equals() 和 hashCode() 有一個「合約」，違反這個合約會讓你的集合類別出現很難找的 bug。這是今天最重要的概念之一。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識 Object 類別

<!--
【段落轉換】
先了解 Object 類別是什麼，以及它和所有 Java 類別的關係。
-->
---
layout: default
---

# Object 類別是什麼？

- 位於 **`java.lang`** 套件，完整名稱 `java.lang.Object`
- **所有 Java 類別的父類別**（根類別）
- 所有物件都隱含繼承了 Object 的 `public`、`protected` 方法
- 可依需要 **Override**（重新定義）這些方法

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>提示：</b> toString()、hashCode()、equals() 等方法都來自 Object 類別，不需要 import 即可使用
</div>

<!--
【核心說明】
java.lang.Object 是 Java 所有類別的「根」。不管你寫什麼類別，都隱含繼承了 Object。

【生活化比喻】
就像所有人類都有「人類」的基本特徵——有名字、有年齡、能說話。Object 就是 Java 世界的「人類祖先」，所有物件都繼承了它的基本方法。

💼 業界實務：
toString()、equals()、hashCode() 這三個來自 Object 的方法，是 Java 開發者最常需要覆寫的方法，IDE（如 IntelliJ）都有快速生成功能。
-->
---

# 隱含繼承 Object 類別

所有類別都隱含地繼承 `Object`，以下兩種寫法完全相同：

```java
class Animal {
    String name;
}
```

```java
class Animal extends Object {
    String name;
}
```

<!--
【帶讀程式碼】
兩種寫法完全等效。你寫 class Animal 不需要 extends Object，Java 自動補上。

⚠️ 學生常見誤解：
「如果已經 extends 其他類別，還會繼承 Object 嗎？」答：會。Dog extends Animal，Animal extends Object，所以 Dog 透過繼承鏈也繼承了 Object。
-->
---

# Java 類別的繼承關係

常見類別皆以 `Object` 為父類別：

| 類別 | 父類別 | 說明 |
| --- | --- | --- |
| `String` | `Object` | 字串類別 |
| `StringBuffer` | `Object` | 可變字串類別 |
| `Scanner` | `Object` | 輸入掃描器 |
| 自訂類別（如 `Animal`） | `Object` | 所有自訂類別 |

<!--
【帶讀表格】
常見類別都繼承自 Object。String、Scanner 等你已經用過的類別，都是 Object 的子類別。

【互動引導】
現在你明白為什麼對任何物件都能呼叫 toString() 了嗎？因為 Object 就定義了 toString()，所有物件都繼承了。
-->
---

# Object 常用方法

本章介紹下列四個方法：

| 方法 | 說明 |
| --- | --- |
| `int hashCode()` | 傳回物件的雜湊碼 |
| `boolean equals(Object obj)` | 比較兩個物件是否相同 |
| `String toString()` | 傳回代表物件的字串 |
| `final Class getClass()` | 傳回物件所屬的類別 |

<!--
【帶讀表格】
今天要學四個 Object 方法：
- hashCode()：雜湊碼，用於集合的快速查找
- equals()：內容比較
- toString()：物件的文字表示
- getClass()：取得物件的類別資訊

這四個方法你將來會一直用到，要熟悉每個的用途。
-->
---

# Objects 工具類別 (JDK 7+)

**`java.util.Objects`**（注意：不是 `java.lang.Object`）是 Java 7 引入的工具類別，提供靜態方法讓開發者更安全地處理 `null`。

| 方法 | 說明 |
| --- | --- |
| `equals(a, b)` | 比較 a, b 是否相等，**自動處理 null** |
| `hash(fields...)` | 根據傳入的欄位產生 hash 值 |
| `requireNonNull(obj)` | 檢查是否為 null，為空則拋出異常 |

<!--
【核心說明】
java.util.Objects（注意 s）是 JDK 7 加入的工具類別，提供 null 安全的靜態方法。

⚠️ 學生常見誤解：
Object（沒有 s）是所有類別的父類別；Objects（有 s）是工具類別。名字長得很像但完全不同！

💼 業界實務：
處理使用者輸入或資料庫查詢結果時，null 是常見問題。用 Objects.equals() 比直接呼叫 equals() 安全，不會因為 null 拋出 NullPointerException。
-->
---

# Objects 工具類別 — 範例

```java
import java.util.Objects;

String s1 = null;
String s2 = "Java";

// 傳統寫法會拋出 NPE (NullPointerException)
// System.out.println(s1.equals(s2)); 

// 安全寫法：若 s1 為 null 則回傳 false
System.out.println(Objects.equals(s1, s2)); // false
```

<!--
【帶讀程式碼】
s1 是 null，如果直接呼叫 s1.equals(s2) 會拋出 NullPointerException——因為 null 沒有方法。

但 Objects.equals(s1, s2) 會先檢查 null，null 和任何非 null 值都不相等，所以回傳 false，不會拋出例外。

💼 業界實務：
在 equals() 方法裡比較欄位時，一律用 Objects.equals()，防止欄位值為 null 時出錯。
-->
---

# Objects.isNull() 與 nonNull()

| 方法 | 說明 |
| --- | --- |
| `Objects.isNull(obj)` | 回傳 `true` 若 `obj == null`，適合作為 Stream 的 predicate |
| `Objects.nonNull(obj)` | 回傳 `true` 若 `obj != null`，適合作為 Stream 的 predicate |

```java
List<String> list = Arrays.asList("Java", null, "Python");
list.stream().filter(Objects::nonNull)
    .forEach(System.out::println); // Java, Python
System.out.println(Objects.isNull(null)); // true
```

<!--
【帶讀說明】
isNull() 和 nonNull() 主要為了搭配 Stream 使用。filter(Objects::nonNull) 可以過濾掉 List 中的 null 元素。

【帶讀程式碼】
list.stream().filter(Objects::nonNull) 把 null 過濾掉，只留下 "Java" 和 "Python"。

💼 業界實務：
從資料庫或 API 取回的資料常有 null，在 Stream 處理前先過濾 null 是好習慣。
-->
---

# Objects.requireNonNullElse()

| 方法 | 說明 |
| --- | --- |
| `requireNonNullElse(obj, default)` | 若 `obj` 為 null 則回傳 `default` |
| `requireNonNullElseGet(obj, supplier)` | 若 `obj` 為 null 則呼叫 `supplier`（延遲求值） |

```java
String name = Objects.requireNonNullElse(input, "訪客");
// input 若為 null 則 name = "訪客"

String v = Objects.requireNonNullElseGet(
    cached, () -> loadFromDB()); // 只在 null 時才呼叫
```

<!--
【核心說明】
requireNonNullElse 提供「有就用，沒有用預設值」的功能，非常實用。

【帶讀程式碼】
input 是 null 時，name 就用 "訪客" 這個預設值。比用三元運算子 input != null ? input : "訪客" 更清楚語意。

💼 業界實務：
API 回傳結果或設定值可能為 null 時，用這個方法提供合理的預設值，比到處寫 if (x == null) 更優雅。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 哈希碼與 hashCode()

<!--
【段落轉換】
接下來進入 hashCode()，這個方法直接影響 HashMap、HashSet 的行為，非常重要。
-->
---
layout: default
---

# 什麼是哈希碼？

- **Hash（雜湊）** 源自一位數學家的名字，他發明了雜湊演算法
- 主要目的：**在集合中提升搜尋效率**
- `hashCode()` 根據演算法將物件資訊映射成一個**整數（雜湊碼）**
- 不同 JVM 的實作與記憶體位址有關，但不保證就是位址本身

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>說明：</b> 雜湊碼有時也稱「散列值」(hash code / hash value)
</div>

<!--
【核心說明】
雜湊碼是一個整數，用來快速定位物件在集合中的位置。你可以把它想像成「物件的門牌號碼」。

【生活化比喻】
圖書館書架用分類號碼排列，要找一本書先看號碼，就知道去哪個區域找。雜湊碼就是這個分類號碼，讓 HashMap 能快速找到對應的資料，而不需要一個個比對。

💼 業界實務：
HashMap 和 HashSet 的快速查找就依賴 hashCode()。如果 hashCode() 實作不好，所有物件的 hash 值都一樣，集合就退化成一個慢速的 List。
-->
---

# hashCode() 基本規則

| 情況 | 結果 |
| --- | --- |
| 相同的方法 + 相同的值 | 傳回**相同** hash 值 |
| 不同的方法 + 相同的值 | 傳回**不同** hash 值 |
| 不同物件（預設 Object） | 通常傳回**不同** hash 值 |

<!--
【帶讀表格】
hashCode() 的三個規則：
相同的演算法 + 相同的值 → 相同的 hash 值（確定性）
不同類別 → 不同 hash 值（各自有各自的算法）
不同物件（Object 預設）→ 通常不同（因為預設基於記憶體位址）
-->
---

# hashCode() — 初探

同一個類別，相同的字串值 → 相同的雜湊碼：

```java
String str1 = "Foo";
String str2 = "Foo";
System.out.println(str1.hashCode()); // 70822
System.out.println(str2.hashCode()); // 70822
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>說明：</b> str1 與 str2 都呼叫 String 的 hashCode()，值相同，所以結果一致
</div>

<!--
【帶讀程式碼】
str1 和 str2 都是 "Foo"，呼叫 String 的 hashCode() 結果都是 70822。String 的 hashCode() 是基於字串內容計算的，所以內容一樣 hash 就一樣。

⚠️ 重點：
這是 String 覆寫了 hashCode() 的結果。如果是自訂類別沒有覆寫，同樣內容但不同物件的 hash 通常不一樣。
-->
---

# hashCode() — 不同類別的比較

不同類別各自定義 `hashCode()`，相同「值」也可能得出不同結果：

```java
String str = "Foo";
Integer intObj = 10;

System.out.println(str.hashCode());    // 70822
System.out.println(intObj.hashCode()); // 10
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>說明：</b> String 與 Integer 各自 Override 了 hashCode()，使用不同的演算法
</div>

<!--
【帶讀程式碼】
String 的 hashCode() 和 Integer 的 hashCode() 算法不同。"Foo" 的 hash 是 70822，10 的 hash 就是 10（Integer 直接用值本身）。

【重點說明】
每個類別都可以定義自己的 hashCode() 算法。這就是為什麼不同類別的相同「值」可能有不同 hash。
-->
---

# 現代化的 hashCode() 實作

現代開發不再手寫複雜的雜湊演算法，而是使用 **`Objects.hash()`**。

| 方法 | 說明 |
| --- | --- |
| `Objects.hash(v1, v2...)` | 傳入多個欄位，自動計算出高品質的雜湊碼 |

```java
import java.util.Objects;

class User {
    String id;
    String email;

    @Override
    public int hashCode() {
        // 傳入所有用於判斷相等的欄位
        return Objects.hash(id, email);
    }
}
```

<!--
【核心說明】
現代 Java 不需要自己寫 hash 算法，用 Objects.hash() 傳入所有欄位就搞定了。

【帶讀程式碼】
Objects.hash(id, email) 根據 id 和 email 兩個欄位計算 hash 值。傳入哪些欄位，就是用哪些欄位決定「相等性」。

⚠️ 關鍵原則：
equals() 用哪些欄位比較，hashCode() 就要用哪些欄位計算 hash！必須保持一致。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# equals() 方法

<!--
【段落轉換】
hashCode() 講完，現在來看和它密不可分的 equals()。這兩個方法有一個「合約」，必須同時覆寫，不能只覆寫其中一個。
-->
---

# `==` 與 `equals()` 的差異

| 比較方式 | 說明 |
| --- | --- |
| `==` 運算子 | 比較**參照**是否指向同一物件 |
| `equals()` 方法 | 比較物件**內容**是否相同（可 Override） |

```java
String s1 = new String("Java");
String s2 = new String("Java");
System.out.println(s1 == s2);      // false（不同物件）
System.out.println(s1.equals(s2)); // true（內容相同）
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 字串比對請務必用 <code>equals()</code>；<code>==</code> 只判斷是否為同一個物件
</div>

<!--
【帶讀表格】
== 比的是「記憶體位址」——是不是同一個物件。
equals() 比的是「內容」——值是否相同（如果覆寫了的話）。

【帶讀程式碼】
s1 和 s2 都是 new String("Java")，但是兩個不同的物件，所以 == 是 false。但 String 的 equals() 比內容，所以 true。

⚠️ 超常見 bug：
字串用 == 比較！幾乎每個 Java 初學者都犯過這個錯。一定要用 equals()！
-->
---
layout: default
---

# Object 的 equals() — 參照比較

`Object` 原生的 `equals()` 比較的是**參照（reference）是否指向同一個物件**：

```java
Animal a1 = new Animal("Foo");
Animal a2 = new Animal("Foo");
System.out.println(a1.equals(a2)); // false
```

```java
Animal a3 = a1;
System.out.println(a1.equals(a3)); // true
```

<!--
【帶讀程式碼】
Object 預設的 equals() 比的是參照位址，所以 a1 和 a2 雖然 name 一樣，但是不同物件，結果是 false。
a3 = a1 是指向同一個物件，所以 true。

💡 這就是為什麼自訂類別通常需要覆寫 equals()——預設的行為通常不是你要的。
-->
---

# String 的 equals() — 內容比較

`String` 已 Override `equals()`，改為比較**字串內容是否相同**：

```java
String s1 = "Foo";
String s2 = "Foo";
System.out.println(s1.equals(s2)); // true
```

| 類別 | `equals()` 比較對象 | 範例結果 |
| --- | --- | --- |
| `Object`（預設） | 參照位址 | 不同物件 → `false` |
| `String`（Override） | 字串內容 | 內容相同 → `true` |

<!--
【帶讀表格說明】
String 覆寫了 equals()，改成比較字串內容。這就是為什麼 "Foo".equals("Foo") 是 true，即使是兩個不同的 String 物件。

【重點】
Object 的預設 equals() 基本上等同於 ==（比位址）。覆寫 equals() 就是把「相等的定義」從「同一個物件」改成「內容一樣的物件」。
-->
---

# 現代化的 equals() 實作 (JDK 16+)

JDK 16 引入 **Pattern Matching** 後，實作 `equals()` 變得更簡潔且易讀。

```java
@Override
public boolean equals(Object o) {
    // 1. 同一參照直接回傳 true
    if (this == o) return true;
    
    // 2. 判斷型別並同時宣告變數 (JDK 16 Pattern Matching)
    if (o instanceof User other) {
        // 3. 比較各欄位內容 (使用 Objects.equals 避免 null)
        return Objects.equals(this.id, other.id) &&
               Objects.equals(this.email, other.email);
    }
    return false;
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>重要：</b> 若 Override 了 <code>equals()</code>，<b>必須</b>同時 Override <code>hashCode()</code>！
</div>

<!--
【帶讀程式碼】
現代化寫法三步驟：
1. this == o：同一個物件直接 true，最快
2. instanceof User other：不是 User 就直接 false，同時宣告 other 變數（Pattern Matching）
3. Objects.equals() 比各欄位：null 安全的欄位比較

⚠️ 學生常見誤解：
很多人只覆寫 equals()，忘記覆寫 hashCode()！但這樣的類別放進 HashSet/HashMap 會出錯。

💼 業界實務：
IntelliJ IDEA 等 IDE 的「Generate equals() and hashCode()」功能自動生成這兩個方法，業界通常直接用工具產生，不手寫。
-->
---

# equals() 與 hashCode() 的合約

| 規則 | 說明 |
| --- | --- |
| 相等必須相同 hash | `a.equals(b)` 為 `true` → `a.hashCode() == b.hashCode()` |
| 相同 hash 不必相等 | 允許碰撞（hash 相同但 `equals()` 不一定為 true） |
| Override 連動 | 覆寫 `equals()` **必須**同時覆寫 `hashCode()` |

```java
User u1 = new User("alice"), u2 = new User("alice");
System.out.println(u1.equals(u2));    // true
Set<User> set = new HashSet<>();
set.add(u1);
System.out.println(set.contains(u2)); // false！hashCode 未 Override
```

<!--
【核心說明】
equals() 和 hashCode() 有一個嚴格的合約：如果兩個物件 equals() 是 true，它們的 hashCode() 必須相同。

【帶讀程式碼】
這個範例示範了違反合約的後果：u1.equals(u2) 是 true，但沒有覆寫 hashCode()，所以兩個物件的 hash 不同。HashSet 用 hash 先找桶，hash 不同找不到 u2，set.contains(u2) 就回傳 false——即使 equals() 說它們相等！

⚠️ 記住：
覆寫 equals() 一定要同時覆寫 hashCode()，這是 Java 的基本約定。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# toString() 方法

<!--
【段落轉換】
toString() 相對簡單，但非常實用——讓你的物件能以可讀的方式顯示出來。
-->
---
layout: default
---

# Object 的 toString() — 預設格式

`Object.toString()` 預設回傳格式：`類別名稱@hash值（十六進位）`

```java
Animal a = new Animal();
System.out.println(a.toString()); // Animal@1b6d3586
System.out.println(a);            // 也會自動呼叫 toString()
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>說明：</b> 預設格式對使用者不具可讀性，通常需要 Override 成有意義的字串
</div>

<!--
【帶讀程式碼】
Object 預設的 toString() 顯示「類別名@hash十六進位」，對人類來說沒什麼意義。

⚠️ 常見誤解：
print(a) 和 print(a.toString()) 效果一樣——Java 在需要字串的地方會自動呼叫 toString()。
-->
---

# Override toString() — 範例

```java
class Animal {
    String name = "Foo";
    int age = 1;
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}
```

```java
Animal a = new Animal();
System.out.println(a); // Name: Foo, Age: 1
```

<!--
【帶讀程式碼】
覆寫 toString() 讓印出結果有意義。Name: Foo, Age: 1 比 Animal@1b6d3586 清楚多了。

💼 業界實務：
Log 記錄時，物件直接印出到日誌裡。如果沒有覆寫 toString()，log 裡只有無意義的 hash 值，除錯很困難。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Records 與 Object 方法

<!--
【段落轉換】
Records 是個特別章節——用一行宣告，它自動幫你實作所有這三個 Object 方法。
-->
---
layout: default
---

# 紀錄類別 (Records) 與 Object 方法

JDK 16+ 的 **`record`** 會自動為你 Override 所有重要的 `Object` 方法。

| 方法 | Record 的預設行為 |
| --- | --- |
| `toString()` | 顯示類別名與所有屬性值 |
| `equals()` | 比較所有屬性的內容 (State-based) |
| `hashCode()` | 根據所有屬性產生雜湊值 |

```java
// 一行代碼搞定 toString/equals/hashCode
record Point(int x, int y) { }

Point p1 = new Point(10, 20);
System.out.println(p1); // Point[x=10, y=20]
```

<!--
【帶讀表格】
Records 自動生成 toString()、equals()（比較所有欄位內容）、hashCode()（根據所有欄位）。

【帶讀程式碼】
record Point(int x, int y) 一行，p1.toString() 自動輸出 Point[x=10, y=20]，比手寫節省大量程式碼。

💼 業界實務：
需要「純資料傳遞」的 DTO 類別，用 Record 是現代 Java 的最佳實踐，省事又正確。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 其他 Object 方法

<!--
【段落轉換】
最後快速過一些比較少用但需要知道的 Object 方法。
-->
---
layout: default
---

# getClass() — 取得物件的類別

`getClass()` 傳回物件所屬的 `Class` 物件：

```java
class MyClass { }

MyClass obj = new MyClass();
System.out.println(obj.getClass());
// 輸出：class MyClass
```

<!--
【帶讀程式碼】
getClass() 回傳一個 Class 物件，代表這個物件的類別。印出來會顯示 "class MyClass"。

💼 業界實務：
在反射（Reflection）程式設計和框架開發中，getClass() 很常用。Spring Boot 框架就大量使用反射。
-->
---

# getClass() — 常用操作

| 呼叫方式 | 說明 | 輸出 |
| --- | --- | --- |
| `obj.getClass()` | 傳回 Class 物件 | `class MyClass` |
| `obj.getClass().getName()` | 取得類別名稱字串 | `"MyClass"` |

```java
MyClass obj = new MyClass();
System.out.println(obj.getClass());
System.out.println(obj.getClass().getName());
```

<!--
【帶讀表格】
getClass().getName() 取得類別名稱的字串，這個在 log 記錄和動態載入類別時很實用。

常見用途：logger.info("{}", obj.getClass().getName()) 記錄物件的類別名稱。
-->
---

# clone() 方法與 Cloneable 介面

| 概念 | 說明 |
| --- | --- |
| `Cloneable` | 標記介面（無方法），表示允許複製 |
| 淺層複製 | 基本型態欄位複製值；物件欄位複製**參照** |
| 深層複製 | 連物件欄位也複製，兩份完全獨立 |

```java
class Point implements Cloneable {
    int x, y;
    public Point clone() throws CloneNotSupportedException {
        return (Point) super.clone();
    }
}
```

<!--
【核心說明】
clone() 讓你複製物件，但要實作 Cloneable 介面。

關鍵概念：
- 淺層複製：基本型態複製值，物件型態複製「指向」（兩個物件共用同一個子物件）
- 深層複製：完全獨立的副本，子物件也一起複製

⚠️ 警告：
clone() 的行為容易出錯，業界越來越少用，改用複製建構子（copy constructor）更安全。
-->
---

# clone() — 淺層複製的陷阱

```java
// Owner 有 pet 欄位（物件型態）
Owner o1 = new Owner(); o1.pet = new Pet("旺財");
Owner o2 = o1.clone();  // 淺層：pet 欄位仍共用
System.out.println(o1.pet == o2.pet); // true
o2.pet.name = "小白";
System.out.println(o1.pet.name); // 小白（o1 也被改了！）
// 深層：手動重建物件欄位
o2.pet = new Pet("旺財"); // 現在 o1, o2 的 pet 各自獨立
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 物件欄位較多時，建議改用<b>複製建構子</b> (<code>new Owner(other)</code>) 取代 <code>clone()</code>
</div>

<!--
【帶讀程式碼】
這個陷阱很重要：o1.clone() 產生了 o2，但 o2.pet 和 o1.pet 指向同一個 Pet 物件。改了 o2.pet.name，o1.pet.name 也跟著改了！

⚠️ 這是淺層複製最常見的 bug：以為改的是自己的副本，其實改了共用的物件。

【如何避免】
要深層複製，需要手動創建子物件的新副本，或用複製建構子 new Owner(other)。
-->
---

# finalize() 方法的廢棄 (JDK 9+)

`Object` 中還有一個 `finalize()` 方法，用於物件被 GC 回收前的清理工作。

- **現況**：自 **JDK 9** 起已被標記為 **Deprecated** (廢棄)
- **原因**：執行時機不確定、影響效能、可能導致死鎖
- **替代方案**：使用 **Try-with-resources** 與 **`AutoCloseable`** 介面

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <b>警告：</b> 在現代 Java 開發中，絕對不要 Override 或依賴 <code>finalize()</code> 方法。
</div>

<!--
【核心說明】
finalize() 是 JDK 9 之後被廢棄的方法，不要使用它。

【為什麼廢棄？】
GC 何時呼叫 finalize() 是不確定的，可能很晚才執行，導致資源沒有及時釋放。

💼 業界實務：
需要清理資源（關檔案、關資料庫連線）時，用 try-with-resources 和 AutoCloseable 介面，這是現代 Java 的標準做法。
-->
---
layout: default
---

# 練習：Employee 類別
### 任務說明

建立一個 `Employee` 類別，包含以下欄位：

```java
class Employee {
    String name;
    int age;
    String country;
}
```

1. 建立兩個屬性值相同的 `Employee` 物件，比較它們的 `hashCode()`
2. 觀察 `Object` 預設 `hashCode()` 的行為（屬性相同 ≠ 相同 hash 碼）
3. **進階**：Override `hashCode()`，使屬性相同的物件回傳相同 hash 值

<!--
【出題前的鋪陳】
來練習一下 hashCode()。這個練習讓你親眼看到：相同內容的物件，預設 hashCode() 是不同的，覆寫後就一樣了。

【問題引導】
建立兩個 name、age、country 都一樣的 Employee 物件，先印出 hashCode()，觀察預設行為。然後覆寫 hashCode() 再觀察變化。

【等待與觀察】
給大家 3 分鐘動手試試看。
-->
---
layout: default
---

# 練習：解題提示

1. **建立物件並輸出 hashCode**
   - 以 `new Employee(...)` 建立兩次，分別列印 `hashCode()`
   - Object 預設 `hashCode()` 基於記憶體位址 → 不同物件結果不同

2. **Override hashCode()**

```java
@Override
public int hashCode() {
    return name.hashCode() + age + country.hashCode();
}
```

3. **驗證結果**：Override 後，屬性相同的物件應回傳**相同** hashCode

<!--
【帶讀解法】
兩步驟：
1. 先不覆寫 hashCode()，建立兩個相同內容的 Employee，印出 hashCode()——你會看到兩個不同的數字。
2. 用 Objects.hash(name, age, country) 覆寫 hashCode()，再試一次——現在兩個相同內容的物件應該 hashCode 相同。

⚠️ 進階思考：
如果要讓 Employee 在 HashSet 中正確運作，還需要同時覆寫 equals()，讓兩個內容相同的 Employee 物件 equals() 回傳 true。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 實戰加料
# 為期中作業暖身 🔥

---
layout: default
---

# 實戰：equals() + HashSet 防止重複填寫

問卷規定「同一個 email 不能重複填寫同一張問卷」。正確覆寫 `equals()` + `hashCode()`，讓 `HashSet` 自動幫你擋重複：

```java
class Submission {
    String email;
    int quizId;
    Submission(String email, int quizId) {
        this.email = email; this.quizId = quizId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Submission other) {
            return Objects.equals(email, other.email)
                && quizId == other.quizId;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(email, quizId);
    }
}
```

```java
Set<Submission> submitted = new HashSet<>();
submitted.add(new Submission("alice@mail.com", 1));  // 加入成功
submitted.add(new Submission("alice@mail.com", 1));  // 重複！被擋下
System.out.println(submitted.size()); // 1

boolean alreadyDone = submitted.contains(new Submission("alice@mail.com", 1));
System.out.println("已填寫：" + alreadyDone); // true
```

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>動態問卷連結：</b>前台送出問卷前，就是用這個邏輯判斷 email 是否已在本問卷的填寫紀錄中。注意：只覆寫 <code>equals()</code> 但忘記覆寫 <code>hashCode()</code>，<code>HashSet.contains()</code> 就會永遠回傳 <code>false</code>！
</div>

---
layout: end
---

# 課程結束
### 下一章見！

<!--
[依脈絡推斷]
本章結束。Object 類別的 equals()、hashCode()、toString() 三個方法是 Java 開發的基礎。記住：覆寫 equals() 一定要同時覆寫 hashCode()，這是最重要的帶走重點。
-->

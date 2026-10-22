---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
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
大家好，這一章的主題是 Object 類別。

上一章我們學了繼承。其實在 Java 裡，就算沒有寫 extends，每個類別都有一個共同的祖先，就是 Object。我們一直在用的 toString、equals，都是從它繼承來的。

這一章我們會認識 Object 提供的方法，學會正確地覆寫 equals 和 hashCode。這兩個方法如果寫錯，物件放進集合裡就會出現很難發現的錯誤。
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
這一章我們會先認識 Object 類別，以及處理 null 很方便的 Objects 工具類別。

接著是這一章的重點：hashCode 和 equals，包括它們之間的約定，以及用現代 Java 的寫法實作。然後是 toString，以及 record 如何自動幫我們產生這些方法。最後介紹 getClass、clone 和已經被淘汰的 finalize。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識 Object 類別

<!--
我們先來認識 Object 類別，看看它在 Java 的類別體系裡，扮演什麼角色。
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
Object 類別位於 java.lang 套件，完整名稱是 java.lang.Object。

「它是所有 Java 類別的父類別。」不管是 JDK 內建的類別，還是我們自己寫的類別，都直接或間接繼承了 Object，所以都擁有它的 public 和 protected 方法，也可以依照需要覆寫這些方法。

因為 Object 在 java.lang 裡，所以不需要 import，就能使用 toString、hashCode、equals 這些方法。
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
所有類別都會隱含地繼承 Object。

所以左邊這個沒有寫 extends 的 Animal，和右邊寫了 extends Object 的 Animal，是完全一樣的。如果我們沒有指定父類別，編譯器會自動幫我們加上 extends Object。
-->

---

# Java 類別的繼承關係

常見類別皆直接或間接以 `Object` 為父類別：

| 類別 | 父類別 | 說明 |
| --- | --- | --- |
| `String` | `Object` | 字串類別 |
| `StringBuffer` | `AbstractStringBuilder` → `Object` | 可變字串類別（間接繼承） |
| `Scanner` | `Object` | 輸入掃描器 |
| 自訂類別（如 `Animal`） | `Object` | 所有自訂類別 |

<!--
我們常用的類別，最上層都是 Object。

String 直接繼承 Object；StringBuffer 先繼承 AbstractStringBuilder，再間接繼承 Object；Scanner 也是直接繼承 Object。我們自己寫的 Animal 類別，同樣是 Object 的子類別。

所以在 Java 裡，任何物件都可以用 Object 型別的變數來接收，這是多形最極致的應用。
-->

---

# Object 常用方法

本章介紹下列四個方法：

| 方法 | 說明 |
| --- | --- |
| `int hashCode()` | 傳回物件的雜湊碼 |
| `boolean equals(Object obj)` | 比較兩個物件是否相同 |
| `String toString()` | 傳回代表物件的字串 |
| `final Class<?> getClass()` | 傳回物件所屬的類別 |

<!--
Object 有好幾個方法，這一章會重點介紹這四個。

hashCode 回傳物件的雜湊碼，equals 比較兩個物件是否相同，toString 回傳代表這個物件的字串，getClass 則回傳物件所屬的類別。
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
在介紹這些方法之前，先認識一個很實用的工具類別：Objects，多了一個 s。

要注意它和 Object 不一樣，它位於 java.util 套件，是 Java 7 加入的工具類別，提供許多靜態方法，幫助我們安全地處理 null。

例如 Objects.equals，比較兩個物件時會自動處理 null 的情況；Objects.hash 可以根據多個欄位產生雜湊碼；requireNonNull 會檢查物件是否為 null，如果是，就拋出例外。
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
看範例，s1 是 null，s2 是 Java。

如果直接寫 s1.equals s2，因為 s1 是 null，程式會拋出 NullPointerException。改用 Objects.equals，它會先檢查 null，所以能安全地回傳 false。

比較兩個可能是 null 的物件時，用 Objects.equals 就不用自己寫一堆檢查。
-->

---

# Objects.isNull() 與 nonNull()

| 方法 | 說明 |
| --- | --- |
| `Objects.isNull(obj)` | 回傳 `true` 若 `obj == null`，適合作為 Stream 的 predicate |
| `Objects.nonNull(obj)` | 回傳 `true` 若 `obj != null`，適合作為 Stream 的 predicate |

```java
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

List<String> list = Arrays.asList("Java", null, "Python");
list.stream().filter(Objects::nonNull)
    .forEach(System.out::println); // Java, Python
System.out.println(Objects.isNull(null)); // true
```

<!--
Objects 還有 isNull 和 nonNull 兩個方法，分別判斷物件是不是 null。

直接寫兩個等號 null 也可以，但這兩個方法的好處，是可以直接當作判斷條件，交給 Stream 使用。

看範例，清單裡有一個 null。用 filter 搭配 Objects::nonNull，就能把 null 過濾掉，只印出 Java 和 Python。這種寫法在後面學 Stream 時會很常看到。
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
requireNonNullElse 可以在物件是 null 時，提供一個預設值。

看範例，如果 input 是 null，name 就會是「訪客」；否則就是 input 本身。

requireNonNullElseGet 的差別，是第二個參數傳入的是一段程式，只有在真的是 null 時才會執行。像範例裡從資料庫載入資料這種比較耗時的操作，就適合用它，避免不必要的計算。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 哈希碼與 hashCode()

<!--
接下來是雜湊碼，以及 hashCode 方法。
-->

---
layout: default
---

# 什麼是哈希碼？

- **Hash（雜湊）** 原意是「切碎、混雜」，比喻把資料打散後重新組合成一個數字
- 主要目的：**在集合中提升搜尋效率**
- `hashCode()` 根據演算法將物件資訊映射成一個**整數（雜湊碼）**
- `Object` 預設的 `hashCode()` 由 JVM 實作決定，**不保證**等於記憶體位址

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>說明：</b> 雜湊碼有時也稱「散列值」(hash code / hash value)
</div>

<!--
Hash 的原意是「切碎、混合」，就像把資料打散之後，重新組合成一個數字。

hashCode 方法會依照某種演算法，把物件的資訊轉換成一個整數，這個整數就是雜湊碼。它主要的目的，是讓集合可以更快速地找到資料。

我們可以把它想成圖書館的索書號：有了索書號，就能直接走到對應的書架，不用一本一本翻。

要注意，Object 預設的 hashCode 是由 JVM 決定的，並不保證等於物件的記憶體位址。
-->

---

# hashCode() 基本規則

| 情況 | 結果 |
| --- | --- |
| 相同的方法 + 相同的值 | 傳回**相同** hash 值 |
| 不同的方法 + 相同的值 | **不一定**傳回相同 hash 值 |
| 不同物件（預設 Object） | 通常傳回**不同** hash 值 |

<!--
hashCode 有幾個基本規則。

同一種演算法，對相同的值，一定會得到相同的雜湊碼。但不同的類別使用不同的演算法，就算看起來是相同的值，雜湊碼也不一定相同。至於 Object 預設的版本，不同的物件通常會得到不同的雜湊碼。
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
我們實際看看。

str1 和 str2 的內容都是 Foo，它們呼叫的都是 String 的 hashCode，所以得到相同的結果，70822。

String 已經覆寫了 hashCode，它是依照字串的內容計算的，所以只要內容一樣，雜湊碼就一樣。
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
再看不同類別的情況。

字串 Foo 的雜湊碼是 70822，而整數 10 的雜湊碼就是 10。String 和 Integer 各自覆寫了 hashCode，用的是不同的演算法。

所以雜湊碼只有在同一個類別裡比較才有意義。
-->

---

# 現代化的 hashCode() 實作

現代開發不再手寫複雜的雜湊演算法，而是使用 **`Objects.hash()`**。

| 方法 | 說明 |
| --- | --- |
| `Objects.hash(v1, v2...)` | 傳入多個欄位，自動計算出高品質的雜湊碼 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
⚠️ <b>關鍵原則：</b> 在 <code>equals()</code> 裡用到哪些欄位比較，<code>hashCode()</code> 就必須用相同的欄位，兩者要一致
</div>

<!--
如果我們自己寫的類別需要覆寫 hashCode，現在已經不需要自己設計複雜的演算法了，直接用 Objects.hash，把相關的欄位傳進去，它就會算出品質不錯的雜湊碼。

下面的原則非常重要：「equals 裡用哪些欄位判斷相等，hashCode 就必須用相同的欄位。」兩者一定要一致，等一下我們會看到為什麼。
-->

---

# 現代化的 hashCode() — 範例

```java
import java.util.Objects;

class User {
    String id;
    String email;

    User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public int hashCode() {
        // 傳入所有用於判斷相等的欄位
        return Objects.hash(id, email);
    }
}
```

<!--
看實際的寫法。

User 類別有 id 和 email 兩個欄位。覆寫 hashCode 時，只要一行，把 id 和 email 傳給 Objects.hash 就好了。

如果之後判斷相等的欄位改變了，記得 hashCode 也要跟著改。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# equals() 方法

<!--
接下來是 equals 方法，它決定了兩個物件怎樣才算「相等」。
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
我們先複習兩個等號和 equals 的差別。

兩個等號比較的是參照，也就是兩個變數是不是指向同一個物件。equals 比較的則是內容，而且可以依照需要覆寫。

看範例，s1 和 s2 都用 new 建立，內容都是 Java。兩個等號是 false，因為它們是不同的物件；equals 是 true，因為內容相同。
-->

---
layout: default
---

# Object 的 equals() — 參照比較

`Object` 原生的 `equals()` 比較的是**參照（reference）是否指向同一個物件**：

```java
class Animal {
    String name;
    int age;
    Animal(String name, int age) { this.name = name; this.age = age; }
}

Animal a1 = new Animal("Foo", 1);
Animal a2 = new Animal("Foo", 1);
System.out.println(a1.equals(a2)); // false
```

```java
Animal a3 = a1;
System.out.println(a1.equals(a3)); // true
```

<!--
但要注意，Object 原本的 equals，其實也是比較參照。

看範例，a1 和 a2 的名字和年齡完全一樣，但因為是兩個不同的物件，equals 的結果是 false。只有 a3 和 a1 指向同一個物件時，equals 才是 true。

所以，如果我們希望「內容相同就算相等」，就必須自己覆寫 equals。
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
String 之所以可以用 equals 比較內容，是因為它已經覆寫了 equals。

表格整理了兩者的差別：Object 預設的 equals 比較參照，不同物件就是 false；String 覆寫之後比較字串內容，內容相同就是 true。
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
我們來看現代的 equals 寫法，搭配上一章學的 instanceof 模式比對。

第一步，如果是同一個物件，直接回傳 true。第二步，用 instanceof 判斷 o 是不是 User，同時把它轉型成變數 other。第三步，用 Objects.equals 逐一比較欄位，這樣就算欄位是 null 也不會出錯。如果型別不符，就回傳 false。

最後的提醒非常重要：「覆寫了 equals，就一定要同時覆寫 hashCode。」
-->

---

# equals() 與 hashCode() 的合約

| 規則 | 說明 |
| --- | --- |
| 相等必須相同 hash | `a.equals(b)` 為 `true` → `a.hashCode() == b.hashCode()` |
| 相同 hash 不必相等 | 允許碰撞（hash 相同但 `equals()` 不一定為 true） |
| Override 連動 | 覆寫 `equals()` **必須**同時覆寫 `hashCode()` |

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <b>常見錯誤：</b> 只覆寫 <code>equals()</code>、忘記覆寫 <code>hashCode()</code>，物件在 <code>HashSet</code> / <code>HashMap</code> 中會「找不到」
</div>

<!--
equals 和 hashCode 之間，有一個必須遵守的約定。

第一，如果兩個物件 equals 相等，它們的 hashCode 就必須相同。第二，反過來不一定成立，hashCode 相同的兩個物件，equals 不一定相等，這叫做碰撞。第三，所以覆寫 equals 時，一定要同時覆寫 hashCode。

最常見的錯誤，就是只覆寫了 equals，忘了 hashCode，結果物件放進 HashSet 或 HashMap 之後，會找不到。
-->

---

# equals() 與 hashCode() 合約 — 反例（類別定義）

```java
import java.util.Objects;

// 反例：只覆寫 equals()，忘記 hashCode()
class User {
    String id;
    String email;

    User(String id, String email) { this.id = id; this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof User u)
            return Objects.equals(id, u.id) && Objects.equals(email, u.email);
        return false;
    }
    // ❌ 故意不覆寫 hashCode，模擬錯誤情境
}
```

<!--
我們用一個反例來看看會發生什麼事。

這個 User 類別正確地覆寫了 equals，用 id 和 email 判斷兩個使用者是否相同。但是，它故意沒有覆寫 hashCode。
-->

---

# equals() 與 hashCode() 合約 — 反例（驗證）

```java
import java.util.HashSet;
import java.util.Set;

User u1 = new User("u001", "alice@mail.com");
User u2 = new User("u001", "alice@mail.com");

System.out.println(u1.equals(u2));    // true（equals 正確）

Set<User> set = new HashSet<>();
set.add(u1);
System.out.println(set.contains(u2)); // false！（hashCode 未覆寫）
```

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <code>u1</code> 和 <code>u2</code> 內容相同，<code>equals()</code> 回傳 <code>true</code>，但 <code>HashSet</code> 找不到 <code>u2</code>，因為兩者的 <code>hashCode()</code> 不同
</div>

<!--
我們建立兩個內容完全相同的 User，用 equals 比較，結果是 true，看起來沒問題。

接著把 u1 放進 HashSet，再問 HashSet 有沒有包含 u2，結果卻是 false。

原因是 HashSet 會先用 hashCode 決定要去哪個位置找。u1 和 u2 的 hashCode 是 Object 預設的版本，兩者不同，所以 HashSet 根本找錯了地方，連 equals 都沒有機會執行。

就像圖書館的書，索書號寫錯了，書明明在館裡，卻永遠找不到。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# toString() 方法

<!--
接下來是 toString 方法，它決定了物件被印出來時，會顯示什麼內容。
-->

---
layout: default
---

# Object 的 toString() — 預設格式

`Object.toString()` 預設回傳格式：`類別名稱@hash值（十六進位）`

```java
Animal a = new Animal("Foo", 1);
System.out.println(a.toString()); // Animal@1b6d3586
System.out.println(a);            // 也會自動呼叫 toString()
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>說明：</b> 預設格式對使用者不具可讀性，通常需要 Override 成有意義的字串
</div>

<!--
Object 預設的 toString，回傳的格式是類別名稱、小老鼠，再加上十六進位的雜湊碼，例如 Animal 小老鼠一串數字。

另外，用 println 直接印出物件時，Java 會自動呼叫它的 toString。

但這種預設的格式，對我們來說幾乎沒有意義，看不出這隻動物的名字和年齡。所以通常會覆寫 toString。
-->

---

# Override toString() — 範例

```java
class Animal {
    String name;
    int age;
    Animal(String name, int age) { this.name = name; this.age = age; }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}
```

```java
Animal a = new Animal("Foo", 1);
System.out.println(a); // Name: Foo, Age: 1
```

<!--
我們幫 Animal 覆寫 toString，回傳名字和年齡組成的字串。

這樣直接印出 a，就會看到 Name: Foo, Age: 1，一目了然。

在除錯或記錄日誌時，清楚的 toString 能省下很多時間。實務上，大部分存放資料的類別都會覆寫它，IDE 也可以自動產生。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Records 與 Object 方法

<!--
剛才我們看到，toString、equals 和 hashCode 都需要自己覆寫，其實有一個更簡單的方法：record。
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
上一章介紹過的 record，會自動幫我們覆寫這三個重要的方法。

toString 會顯示類別名稱和所有屬性的值；equals 會比較所有屬性的內容；hashCode 也會依照所有屬性來計算，而且兩者一定一致。

看範例，Point 只寫了一行，印出 p1 就是 Point 中括號 x 等於 10、y 等於 20。對於純粹存放資料的類別，用 record 是最簡單、也最不容易出錯的做法。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 其他 Object 方法

<!--
最後，我們看看 Object 的其他幾個方法。
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
getClass 會回傳物件所屬的類別，型別是 Class 物件。

看範例，對 MyClass 的物件呼叫 getClass，印出來的就是 class MyClass。
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
getClass 回傳的 Class 物件，還可以進一步取得更多資訊。最常用的是 getName，取得類別名稱的字串。

如果類別放在套件裡，getName 會回傳包含套件的完整名稱。在記錄日誌，或是需要依照物件的實際型別做不同處理時，getClass 就很有用。
-->

---

# clone() 方法與 Cloneable 介面

| 概念 | 說明 |
| --- | --- |
| `Cloneable` | 標記介面（無方法），表示允許複製 |
| 淺層複製 | 基本型態欄位複製值；物件欄位複製**參照** |
| 深層複製 | 連物件欄位也複製，兩份完全獨立 |

```java
class Coord implements Cloneable {
    int x, y;
    public Coord clone() throws CloneNotSupportedException {
        return (Coord) super.clone();
    }
}
```

<!--
clone 方法可以複製一個物件。要使用它，類別必須實作 Cloneable 介面。這個介面裡沒有任何方法，只是一個標記，表示這個類別允許被複製。

複製分成兩種。淺層複製，基本型別的欄位會複製值，但物件型別的欄位只複製參照，兩份物件會共用同一個子物件。深層複製，則連物件欄位也一起複製，兩份完全獨立。

看範例，Coord 實作了 Cloneable，覆寫 clone 方法，呼叫 super.clone 完成複製。
-->

---

# clone() — 淺層複製的陷阱

```java
class Pet { String name; Pet(String n) { name = n; } }
class Owner implements Cloneable {
    Pet pet;
    public Owner clone() throws CloneNotSupportedException {
        return (Owner) super.clone();
    }
}

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
我們看淺層複製的陷阱。

Owner 有一個 pet 欄位。把 o1 複製成 o2 之後，比較兩者的 pet，結果是 true，代表它們共用同一隻寵物。所以把 o2 的寵物改名成小白，o1 的寵物也跟著變成了小白。

要避免這個問題，就要深層複製，自己重新建立一隻新的 Pet，讓兩者各自獨立。

clone 的規則比較複雜，所以實務上，更建議用複製建構子，也就是傳入另一個物件，在建構子裡自己複製每個欄位。
-->

---

# finalize() 方法的廢棄 (JDK 9+)

`Object` 中還有一個 `finalize()` 方法，用於物件被 GC 回收前的清理工作。

- **現況**：自 **JDK 9** 起標記為 **Deprecated**，**JDK 18** 起更標記為「預計移除」(forRemoval)
- **原因**：執行時機不確定、影響效能、可能導致死鎖
- **替代方案**：使用 **Try-with-resources** 與 **`AutoCloseable`** 介面

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <b>警告：</b> 在現代 Java 開發中，絕對不要 Override 或依賴 <code>finalize()</code> 方法。
</div>

<!--
Object 還有一個 finalize 方法，原本是用來在物件被垃圾回收之前，做一些清理工作。

但它從 Java 9 開始就被標記為不建議使用，Java 18 更標記為預計移除。原因是它的執行時間無法確定，可能影響效能，甚至造成死結。

需要釋放檔案、網路連線這類資源時，應該改用 try-with-resources 搭配 AutoCloseable 介面，之後的例外處理章節會介紹。在現代的 Java 開發中，不要覆寫或依賴 finalize。
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

    Employee(String name, int age, String country) { /* 指定三個欄位 */ }
}
```

1. 建立兩個屬性值相同的 `Employee` 物件，比較它們的 `hashCode()`
2. 觀察 `Object` 預設 `hashCode()` 的行為（屬性相同 ≠ 相同 hash 碼）
3. **進階**：Override `hashCode()`，使屬性相同的物件回傳相同 hash 值
4. **延伸**：用相同欄位 Override `equals()`，驗證兩個物件 `equals()` 為 `true`

<!--
接下來我們動手練習。

請建立一個 Employee 類別，有姓名、年齡和國家三個欄位，再加上一個建構方法，一次把三個欄位設定好。先建立兩個屬性完全相同的物件，印出它們的 hashCode，觀察 Object 預設的行為。

進階挑戰是覆寫 hashCode，讓屬性相同的物件，回傳相同的雜湊碼。延伸挑戰是用相同的欄位覆寫 equals，讓這兩個物件比較的結果是 true。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
-->

---
layout: default
---

# 練習：解題提示

1. **建立物件並輸出 hashCode**
   - 以 `new Employee(...)` 建立兩次，分別列印 `hashCode()`
   - Object 預設 `hashCode()` 依物件身分產生 → 不同物件結果通常不同

2. **Override hashCode()**

```java
import java.util.Objects;

@Override
public int hashCode() {
    return Objects.hash(name, age, country);
}
```

3. **驗證結果**：Override 後，屬性相同的物件應回傳**相同** hashCode
4. **延伸**：`equals()` 照「現代化的 equals() 實作」頁的寫法，比較 `name`、`age`、`country`

<!--
我們來看提示。

用 new Employee 建立兩個物件，分別印出 hashCode。因為 Object 預設的 hashCode 是依照物件本身產生的，兩個不同的物件，結果通常不一樣。

接著覆寫 hashCode，用 Objects.hash 傳入姓名、年齡和國家。重新執行，屬性相同的兩個物件，就會得到相同的雜湊碼。

延伸題的 equals，可以照前面「現代化的 equals 實作」那一頁的寫法，用相同的三個欄位來比較。記得，覆寫 hashCode 時，一定要用相同的欄位覆寫 equals。
-->

---
layout: end
---

# 課程結束
### 下一章見！

<!--
這一章我們認識了所有類別的共同祖先：Object。

Objects 工具類別可以安全地處理 null。hashCode 讓集合能快速找到資料，equals 決定物件是否相等，兩者一定要用相同的欄位一起覆寫，否則放進 HashSet 和 HashMap 會出問題。toString 讓物件印出有意義的內容。如果是單純存放資料的類別，用 record 就能自動產生這些方法。

我們下一章見。

【講師】開放 Q&A。
-->

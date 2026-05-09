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

---
layout: default
---

# Outline

- **認識 Object 類別** — `java.lang.Object` 與繼承關係
- **Objects 工具類別** — `java.util.Objects` 的 Null 安全設計
- **哈希碼與 `hashCode()`** — `Objects.hash()` 的現代實作
- **`equals()` 方法** — 搭配 Pattern Matching 的現代化寫法
- **`toString()` 方法** — 物件的字串表示與 Records 的自動實作
- **`getClass()` 方法** — 取得物件所屬類別與 `finalize()` 的廢棄

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識 Object 類別

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

---

# Java 類別的繼承關係

常見類別皆以 `Object` 為父類別：

| 類別 | 父類別 | 說明 |
| --- | --- | --- |
| `String` | `Object` | 字串類別 |
| `StringBuffer` | `Object` | 可變字串類別 |
| `Scanner` | `Object` | 輸入掃描器 |
| 自訂類別（如 `Animal`） | `Object` | 所有自訂類別 |

---

# Object 常用方法

本章介紹下列四個方法：

| 方法 | 說明 |
| --- | --- |
| `int hashCode()` | 傳回物件的雜湊碼 |
| `boolean equals(Object obj)` | 比較兩個物件是否相同 |
| `String toString()` | 傳回代表物件的字串 |
| `final Class getClass()` | 傳回物件所屬的類別 |

---

# Objects 工具類別 (JDK 7+)

自 Java 7 起，建議使用 **`java.util.Objects`** 來處理物件，它能更安全地處理 `null`。

| 方法 | 說明 |
| --- | --- |
| `equals(a, b)` | 比較 a, b 是否相等，**自動處理 null** |
| `hash(fields...)` | 根據傳入的欄位產生 hash 值 |
| `requireNonNull(obj)` | 檢查是否為 null，為空則拋出異常 |

```java
import java.util.Objects;

String s1 = null;
String s2 = "Java";

// 傳統寫法會拋出 NPE (NullPointerException)
// System.out.println(s1.equals(s2)); 

// 安全寫法：若 s1 為 null 則回傳 false
System.out.println(Objects.equals(s1, s2)); // false
```

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 哈希碼與 hashCode()

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

---

# hashCode() 基本規則

| 情況 | 結果 |
| --- | --- |
| 相同的方法 + 相同的值 | 傳回**相同** hash 值 |
| 不同的方法 + 相同的值 | 傳回**不同** hash 值 |
| 不同物件（預設 Object） | 通常傳回**不同** hash 值 |

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# equals() 方法

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# toString() 方法

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# getClass() 方法

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

---

# finalize() 方法的廢棄 (JDK 9+)

`Object` 中還有一個 `finalize()` 方法，用於物件被 GC 回收前的清理工作。

- **現況**：自 **JDK 9** 起已被標記為 **Deprecated** (廢棄)
- **原因**：執行時機不確定、影響效能、可能導致死鎖
- **替代方案**：使用 **Try-with-resources** 與 **`AutoCloseable`** 介面

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <b>警告：</b> 在現代 Java 開發中，絕對不要 Override 或依賴 <code>finalize()</code> 方法。
</div>

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

---
layout: end
---

# 課程結束
### 下一章見！

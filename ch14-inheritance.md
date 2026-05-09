---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: Java 繼承與多形
routeAlias: ch14
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
    繼承與多形
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「用繼承消除重複，用多形擴展彈性」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

---
layout: default
---

# Outline

- **繼承 (Inheritance)** — extends 語法、存取修飾符、繼承類型
- **IS-A 與 HAS-A 關係** — instanceof、聚合、組合
- **重新定義 (Override)** — 規則、super、@Override
- **多重定義 (Overload)** — 父類別方法的 Overload
- **多形 (Polymorphism)** — 編譯時期 vs 執行時期、型別轉型
- **靜態 / 動態綁定** — Static Binding vs Dynamic Binding
- **巢狀類別 (Nested Classes)** — 內部類別、方法類別、匿名類別

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 繼承 Inheritance

---
layout: default
---

# 為什麼需要繼承？

Animal、Dog、Bird 三個類別大量重複程式碼：

| 類別 | 共有屬性 | 共有方法 | 獨有方法 |
| --- | --- | --- | --- |
| `Animal` | `name` | `eat()`, `sleep()` | — |
| `Dog` | `name` | `eat()`, `sleep()` | `barking()` |
| `Bird` | `name` | `eat()`, `sleep()` | `flying()` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>繼承的目的：</b>讓子類別直接引用父類別的屬性與方法，消除重複程式碼
</div>

---

# 繼承的語法 — extends

使用 `extends` 關鍵字，子類別自動擁有父類別的所有屬性與方法：

```java
class Dog extends Animal {
    public void barking() {
        System.out.println("汪汪汪");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 Dog 無需再定義 <code>name</code>、<code>eat()</code>、<code>sleep()</code>，繼承後自動擁有
</div>

---

# 繼承範例 — Animal 與 Dog

```java
class Animal {
    String name;
    public Animal(String name) { this.name = name; }
    public void eat()   { System.out.println(name + " 吃東西"); }
    public void sleep() { System.out.println(name + " 睡覺"); }
}
class Dog extends Animal {
    public Dog(String name) { super(name); }
    public void barking() { System.out.println(name + " 汪汪叫"); }
}
```

---

# 父類別建構方法的啟動順序

建立子類別物件時，**父類別的建構方法會先自動被呼叫**：

```java
class Animal {
    public Animal() { System.out.println("Animal 建構"); }
}
class Dog extends Animal {
    public Dog() { System.out.println("Dog 建構"); }
}
// new Dog() 的輸出：
// Animal 建構
// Dog 建構
```

---

# 存取修飾符與繼承

| 修飾符 | 同一類別 | 同套件 | 子類別 | 其他 |
| --- | --- | --- | --- | --- |
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| （無修飾符） | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 建議父類別屬性用 <code>protected</code>：子類別可直接存取，外部類別無法直接修改
</div>

---

# protected 屬性與 super() 範例

```java
class Animal {
    protected String name;
    public Animal(String name) { this.name = name; }
}
class Dog extends Animal {
    public Dog(String name) {
        super(name); // 呼叫父類別的建構方法
    }
    public void barking() { System.out.println(name + " 汪汪"); }
}
```

---

# super 關鍵字用法

| 用途 | 語法 | 說明 |
| --- | --- | --- |
| 呼叫父類別建構方法 | `super(參數)` | 必須放在建構方法第一行 |
| 呼叫父類別方法 | `super.方法名()` | 用於 Override 後仍需呼叫父類別版本 |
| 存取父類別屬性 | `super.屬性名` | 父子類別有同名屬性時使用 |

---

# 繼承類型

| 類型 | 說明 |
| --- | --- |
| 單一繼承 (Single) | 一個子類別繼承一個父類別 |
| 分層繼承 (Hierarchical) | 多個子類別繼承同一個父類別 |
| 多層次繼承 (Multi-Level) | 子類別再被其他類別繼承（A→B→C） |
| 多重繼承 (Multiple) | Java **不支援**，可改用介面 (Interface) |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 Java 不允許同時繼承 2 個以上的父類別，但介面可以繼承多個介面
</div>

---

# 密封類別 (Sealed Classes)

JDK 17 正式功能，允許父類別**精確控制**哪些子類別可以繼承它。

| 關鍵字 | 說明 |
| --- | --- |
| `sealed` | 宣告此類別為密封類別 |
| `permits` | 指定允許繼承的子類別清單 |

```java
// 只允許 Circle 和 Square 繼承 Shape
public sealed class Shape permits Circle, Square { }

final class Circle extends Shape { }
final class Square extends Shape { }
```

---

# 密封子類別的修飾符限制

密封類別的子類別**必須**明確宣告為以下三種狀態之一：

| 修飾符 | 說明 |
| --- | --- |
| `final` | 禁止再被繼承（斷絕後代） |
| `sealed` | 繼續保持密封，並指定自己的 permits |
| `non-sealed` | 解除密封，允許任何類別繼承（回歸傳統） |

```java
public non-sealed class Circle extends Shape { } // 任何人都能繼承 Circle
```

---

# final 修飾符與繼承

| 用途 | 說明 |
| --- | --- |
| `final class` | 類別不能被繼承 |
| `final` 方法 | 子類別無法 Override 此方法 |
| 靜態綁定 | `final` 方法在編譯期決定，效能較佳 |

```java
final class MathUtil { }          // 無法被繼承
class Animal {
    public final void breathe() { }  // 子類別無法 Override
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Java 內建範例：</b><code>String</code>、<code>Integer</code> 等 Wrapper 類別都宣告為 <code>final class</code>
</div>

---

# abstract 類別與方法

| 概念 | 說明 |
| --- | --- |
| `abstract class` | 不可直接實例化，只能被繼承 |
| `abstract` 方法 | 無方法本體，子類別**必須** Override |
| 具體方法 | `abstract class` 中可混用有本體的具體方法 |

```java
abstract class Shape {
    abstract double area();  // 子類別必須實作
    void printArea() { System.out.println("面積：" + area()); }
}
```

---

# abstract 類別繼承範例

```java
class Circle extends Shape {
    double radius;
    Circle(double r) { this.radius = r; }
    @Override
    double area() { return Math.PI * radius * radius; }
}
Shape s = new Circle(5);  // 向上轉型，合法
s.printArea();            // 面積：78.53...
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>new Shape()</code> 直接報錯；必須透過子類別實例化再向上轉型使用
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# IS-A 與 HAS-A 關係

---
layout: default
---

# IS-A 關係 — 繼承

IS-A 代表「是一種」，子類別物件**也是**父類別的一種。

用 `instanceof` 驗證：

```java
class Fish extends Animal {}
class Bird extends Animal {}
class Eagle extends Bird {}

Eagle eagle = new Eagle();
System.out.println(eagle instanceof Bird);   // true
System.out.println(eagle instanceof Animal); // true
```

---

# 紀錄類別 (Records) 與繼承

JDK 16 引入的 `record` 是特殊的類別，在繼承上有嚴格限制：

| 規則 | 說明 |
| --- | --- |
| 隱含 final | Record 無法被繼承 |
| 單一父類別 | Record 隱含繼承 `java.lang.Record`，不能再繼承其他類別 |
| 實作介面 | Record **可以**實作多個介面 |

```java
// Record 是密封類別子類別的最佳拍檔（因為它天生 final）
public sealed interface Result permits Success, Failure { }

public record Success(String data) implements Result { }
public record Failure(String error) implements Result { }
```

---

# Object 類別 — 所有類別的根父類別

Java 中每個類別都**隱含繼承** `java.lang.Object`，自動擁有以下常用方法：

| 方法 | 說明 |
| --- | --- |
| `toString()` | 回傳物件的字串表示，預設為 `類別名@hashCode` |
| `equals(Object)` | 判斷兩物件是否相等，預設比較記憶體位址 |
| `hashCode()` | 回傳雜湊碼；覆寫 `equals()` 時必須一併覆寫 |
| `getClass()` | 回傳執行時期的 Class 物件，無法被 Override |

---

# Override Object 方法範例

```java
class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
    @Override
    public String toString() { return "(" + x + ", " + y + ")"; }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point p)) return false;
        return x == p.x && y == p.y;
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 覆寫 <code>equals()</code> 必須一併覆寫 <code>hashCode()</code>，否則放入 <code>HashMap</code> / <code>HashSet</code> 會出錯
</div>

---

# HAS-A 關係 — 聚合 vs 組合

| 類型 | 關鍵字 | 說明 |
| --- | --- | --- |
| 聚合 (Aggregation) | 無 `extends` | 類別 A 的屬性是類別 B 的物件（A HAS A B） |
| 組合 (Composition) | 用 `extends` | 將多個類別的共用屬性抽取到父類別再繼承 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 兩者目的相同：減少重複程式碼，提高可維護性
</div>

---

# HAS-A 聚合範例 (Aggregation)

```java
class Speed {
    protected int speed;
    public int getSpeed() { return speed; }
}
class Car {
    private Speed s = new Speed(); // Car HAS A Speed
    public int getCarSpeed() { return s.getSpeed(); }
}
```

---

# HAS-A 組合範例 (Composition)

多個類別的共用屬性抽取到 `BasinInfo`，再繼承：

```java
class BasinInfo {
    protected String id;
    protected String name;
}
class Employee extends BasinInfo {
    int salary;
}
class Customer extends BasinInfo {
    int balance;
}
```

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 重新定義 Override

---
layout: default
---

# Override 最簡範例

子類別「重新定義」父類別的方法：

```java
class Animal {
    public void move() { System.out.println("Animal 移動"); }
}
class Dog extends Animal {
    @Override
    public void move() { System.out.println("Dog 跑步"); }
}
```

---

# Override 基本規則

| 規則 | 說明 |
| --- | --- |
| 方法名稱 | 必須**相同** |
| 參數列表 | 必須**相同** |
| 回傳型態 | 必須**相同**（或子型態） |
| 存取權限 | 只能**放寬**，不能縮減 |
| 不可覆寫 | `static`、`final`、`private` 方法 |

---

# 方法隱藏 (Method Hiding) vs Override

| 比較項目 | Override | 方法隱藏 |
| --- | --- | --- |
| 適用對象 | 實例方法 | `static` 方法 |
| 決定時機 | 執行時期（動態綁定） | 編譯時期（靜態綁定） |
| 呼叫依據 | 物件的**實際型態** | 變數的**宣告型態** |

```java
class Animal { static void sound() { System.out.println("Animal"); } }
class Dog extends Animal { static void sound() { System.out.println("Dog"); } }

Animal a = new Dog();
a.sound(); // 輸出：Animal（由宣告型態 Animal 決定）
```

---

# 協變回傳型態 (Covariant Return Type)

Override 時，子類別可以回傳比父類別**更具體的子型態**：

| 父類別方法 | 子類別 Override | 合法？ |
| --- | --- | --- |
| `Animal produce()` | `Dog produce()` | ✅ Dog IS-A Animal |
| `Object clone()` | `Dog clone()` | ✅ |
| `int get()` | `double get()` | ❌ 基本型態不適用 |

```java
class Animal { Animal produce() { return new Animal(); } }
class Dog extends Animal {
    @Override
    Dog produce() { return new Dog(); }
}
```

---

# super 在 Override 中的應用

子類別呼叫父類別被覆寫的方法，用 `super.方法名()`：

```java
class Dog extends Animal {
    @Override
    public void move() {
        super.move();                    // 執行父類別的 move()
        System.out.println("Dog 跑步");
    }
}
```

---

# @Override 注解

加上 `@Override` 讓編譯器驗證方法簽名是否正確：

```java
class Dog extends Animal {
    @Override
    public void move() { System.out.println("Dog 跑步"); }

    // @Override              ← 若方法名拼錯，編譯時立即報錯
    // public void mov() { }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 強烈建議每次 Override 父類別方法時都加上 <code>@Override</code>
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 多重定義 Overload

---
layout: default
---

# Overload 父類別方法

方法名稱相同但**參數不同**，屬於編譯時期多形：

```java
class Animal {
    public void eat(String food) {
        System.out.println("吃 " + food);
    }
    public void eat(String food, int amount) {
        System.out.println("吃 " + amount + " 份 " + food);
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 Overload 靠<b>參數的個數、型態、順序</b>區別；與 Override 不同，不需繼承關係
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 多形 Polymorphism

---
layout: default
---

# 兩種多形

| 類型 | 決定時機 | 機制 |
| --- | --- | --- |
| 編譯時期多形 (Compile Time) | 編譯期間 | 方法多重定義 (Overload) |
| 執行時期多形 (Runtime) | 執行期間 | 方法重新定義 (Override) + 向上轉型 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 執行時期多形的 3 要件：① 有繼承關係 ② 子類別 Override 父類別方法 ③ 父類別變數參考子類別物件
</div>

---

# 執行時期多形 — 概念

先看沒有多形時，對不同動物分別呼叫 `move()`：

```java
Dog dog = new Dog();
Bird bird = new Bird();
dog.move();   // 需要知道每個子類別的型態
bird.move();
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 每新增一種動物，就要多寫一段呼叫 — 難以擴展
</div>

---

# 執行時期多形 — 準備工作

各子類別各自 Override `move()` 方法：

```java
class Animal { public void move() { System.out.println("Animal 移動"); } }
class Dog extends Animal {
    @Override
    public void move() { System.out.println("Dog 跑步"); }
}
class Bird extends Animal {
    @Override
    public void move() { System.out.println("Bird 飛翔"); }
}
```

---

# 執行時期多形 — 用法

以父類別型態統一接收不同子類別物件：

```java
Animal a1 = new Dog();   // Upcasting
Animal a2 = new Bird();  // Upcasting
a1.move(); // Dog 跑步
a2.move(); // Bird 飛翔
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 同一個 <code>move()</code> 呼叫，依物件實際型態執行不同行為 — 這就是多形
</div>

---

# Sealed Classes 與 Pattern Matching

密封類別最先進的用法是搭配 `switch` (JDK 17 預覽/後續正式)，編譯器會檢查**窮舉性**。

```java
// 如果 Shape 是 sealed，編譯器知道只有 Circle 和 Square
return switch (shape) {
    case Circle c -> c.radius() * c.radius() * Math.PI;
    case Square s -> s.side() * s.side();
    // 不需要 default 區塊！編譯器保證所有可能都已涵蓋
};
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 當你新增一個 permits 子類別時，編譯器會提醒你更新所有相關的 <code>switch</code> 邏輯
</div>

---

# 向上轉型 Upcasting

將子類別物件指定給**父類別**型態變數，自動轉型：

| 特性 | 說明 |
| --- | --- |
| 自動轉型 | 不需強制轉型語法 |
| 可呼叫方法 | 只有父類別定義的方法 |
| 實際執行 | 子類別 Override 後的版本 |

```java
Animal a = new Dog(); // 自動 Upcasting，a 只認識 Animal 的方法
```

---

# 向下轉型 Downcasting

將父類別型態變數轉回**子類別**，需強制轉型：

```java
Animal a = new Dog();
Dog dog = (Dog) a;    // Downcasting — 恢復存取 Dog 專屬方法
dog.barking();        // 可呼叫 Dog 的 barking()
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
⚠️ 若物件實際型態不符，執行時拋出 <code>ClassCastException</code>。建議先用 <code>instanceof</code> 判斷再轉型
</div>

---

# Pattern Matching for instanceof

JDK 16 引入了更簡潔的 **Pattern Matching**，將 `instanceof` 判斷與轉型合併。

| 方式 | 語法 |
| --- | --- |
| 傳統方式 | `if (a instanceof Dog) { Dog d = (Dog) a; ... }` |
| Pattern Matching | `if (a instanceof Dog d) { d.barking(); }` |

```java
Object obj = "Hello Java";

// 判斷的同時宣告變數 s，若符合則自動轉型
if (obj instanceof String s) {
    System.out.println(s.toLowerCase()); // 直接使用 s
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 變數 <code>s</code> 的作用域僅限於 <code>if</code> 區塊內（或邏輯符合的範圍內）
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 靜態 / 動態綁定

---
layout: default
---

# Static Binding vs Dynamic Binding

| 類型 | 時機 | 適用 |
| --- | --- | --- |
| 靜態綁定 (Static Binding) | 編譯時期 (compile time) | `static`、`final`、`private` 方法 |
| 動態綁定 (Dynamic Binding) | 執行時期 (runtime) | Override 後的一般方法 |

```java
Animal a = new Dog();
a.move(); // 動態綁定：執行時才確定呼叫 Dog.move()
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 執行時期多形的底層就是動態綁定：JVM 根據物件的實際型態決定執行哪個方法
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 巢狀類別 Nested Classes

---
layout: default
---

# 巢狀類別的種類

| 類型 | 說明 | 使用場景 |
| --- | --- | --- |
| 一般內部類別 (Inner Class) | 定義在外部類別內，可存取外部所有成員 | 資料封裝、輔助類別 |
| 方法內部類別 (Method-local) | 定義在方法內，只有該方法可使用 | 極少使用 |
| 匿名內部類別 (Anonymous) | 宣告同時建立物件，一次性使用 | Override 介面或抽象方法 |

---

# 一般內部類別 — 宣告

```java
class OuterClass {
    int x = 10;
    class InnerClass {
        void display() {
            System.out.println("x = " + x); // 直接存取外部屬性
        }
    }
}
```

---

# 一般內部類別 — 建立物件

必須先建立外部類別物件，再建立內部類別物件：

```java
OuterClass outer = new OuterClass();
OuterClass.InnerClass inner = outer.new InnerClass();
inner.display(); // x = 10
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 內部類別可宣告為 <code>private</code>，限制只有外部類別能使用
</div>

---

# 方法內部類別 (Method-local Inner Class)

類別宣告在方法內，只有該方法可使用此類別：

```java
class School {
    void showRoom() {
        class MathRoom {       // 只有 showRoom() 能使用
            int students = 40;
        }
        MathRoom m = new MathRoom();
        System.out.println("學生數：" + m.students);
    }
}
```

---

# 匿名內部類別 — 最簡範例

宣告的同時直接建立物件並 Override 方法：

```java
Animal myAnimal = new Animal() {
    @Override
    public void move() {
        System.out.println("特殊移動方式");
    }
};
myAnimal.move();
```

---

# 匿名內部類別 — 當作參數傳送

Java 允許把匿名類別物件直接作為參數傳入方法：

```java
obj.showAnimal(new Animal() {
    @Override
    public void move() {
        System.out.println("移動中...");
    }
});
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
⚠️ 雖然合法，但降低程式碼可讀性與維護性，不建議大量使用
</div>

---

# 練習
### 任務說明

1. **繼承練習**：建立父類別 `Father`，子類別 `Son` 和 `Daughter`
   - `Father`：`name(String)`、`walk()` 印出 `name is walking!!!`
   - `Son`：Override `walk()` 印 `name is walking~~~`，加上 `playBall()`
   - `Daughter`：Override `walk()` 印 `name is walking@@@`，加上 `shopping()`

2. **多形練習**：建立 `Animal`、`Dog`、`Bird`
   - 各自 Override `move()` 方法
   - 用 `Animal[] animals = { new Dog(), new Bird() }` 搭配迴圈呼叫 `move()`

---

# 練習
### 解題提示

1. **繼承結構**
   - 使用 `protected String name` 讓子類別能直接存取父類別屬性
   - 子類別建構方法用 `super(name)` 初始化父類別屬性

2. **多形陣列**
   - 先確認 `Dog` 和 `Bird` 各自有 `@Override` 的 `move()`
   - 宣告 `Animal[] animals = { new Dog("旺財"), new Bird("小翠") }`
   - 用 `for` 迴圈呼叫 `animals[i].move()` 觀察多形效果

---
layout: end
---

# 本章結束
### 繼承讓程式碼更精簡，多形讓設計更彈性

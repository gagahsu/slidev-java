---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
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

<!--
大家好，這一章的主題是繼承與多形。

想像我們要設計動物園的系統，有狗、有鳥、有魚，每一種動物都有名字，也都會吃東西、睡覺。如果每個類別都把這些重複的內容寫一遍，之後要修改，就得一個一個改。

繼承讓我們把共同的部分寫在一個地方，多形則讓我們用統一的方式，操作各種不同的物件。這兩個是物件導向最核心的觀念。
-->

---
layout: default
---

# Outline

- **繼承 (Inheritance)** — extends 語法、存取修飾符、繼承類型、final、Sealed Classes、Records
- **IS-A 與 HAS-A 關係** — instanceof、聚合、組合
- **Override 與 Overload** — Override 規則、super、@Override、Overload 對比
- **多形 (Polymorphism)** — 編譯時期 vs 執行時期、型別轉型、Pattern Matching
- **靜態 / 動態綁定** — Static Binding vs Dynamic Binding
- **巢狀類別 (Nested Classes)** — 內部類別、方法類別、匿名類別

<!--
這一章的內容比較多。

我們會先學繼承的語法，包括 extends、super、final，以及 Java 17 的密封類別和 records。接著區分「是一種」和「擁有一個」兩種類別關係。然後是 Override 和 Overload 的差別。

中段的重點是多形，以及向上轉型、向下轉型；接著了解背後的靜態綁定和動態綁定。最後介紹巢狀類別，包括內部類別和匿名類別。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 繼承 Inheritance

<!--
我們先從繼承開始，看看它解決了什麼問題。
-->

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

<!--
看這張表，Animal、Dog 和 Bird 三個類別，都有 name 屬性，也都有 eat 和 sleep 方法。唯一不同的，是 Dog 會叫、Bird 會飛。

如果三個類別各自把共同的部分寫一遍，就有大量重複的程式碼。之後要修改 eat 的邏輯，就得改三個地方，很容易漏掉。

繼承的目的，就是讓子類別直接使用父類別的屬性和方法，把共同的部分只寫一次。
-->

---

# 繼承的語法 — extends

使用 `extends` 關鍵字，子類別自動擁有父類別的屬性與方法（`private` 成員無法直接存取，建構方法不會被繼承）：

```java
class Dog extends Animal {
    public void barking() {
        System.out.println(name + " 汪汪叫");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 Dog 無需再定義 <code>name</code>、<code>eat()</code>、<code>sleep()</code>，繼承後自動擁有
</div>

<!--
繼承的語法很簡單，在類別名稱後面加上 extends 和父類別的名稱。

Dog extends Animal，意思是 Dog 繼承了 Animal。這樣 Dog 就自動擁有 Animal 的 name、eat 和 sleep，只需要寫自己獨有的 barking 方法。

要注意兩件事：父類別的 private 成員，子類別不能直接存取；而父類別的建構子，也不會被繼承。
-->

---

# 繼承範例 — Animal 與 Dog

```java
// Animal.java
class Animal {
    protected String name;
    public Animal(String name) { this.name = name; }
    public void eat()   { System.out.println(name + " 吃東西"); }
    public void sleep() { System.out.println(name + " 睡覺"); }
}
```

```java
// Dog.java
class Dog extends Animal {
    public Dog(String name) { super(name); }
    public void barking() { System.out.println(name + " 汪汪叫"); }
}
```

<!--
我們看完整的程式碼。

Animal 有一個 protected 的 name 欄位，建構子接收名字，另外有 eat 和 sleep 兩個方法。

Dog 繼承 Animal，它的建構子第一行寫了 super name，把名字交給父類別的建構子處理。接著只要定義自己的 barking 方法就好，方法裡直接使用了繼承來的 name。
-->

---

# 父類別建構方法的啟動順序

建立子類別物件時，**父類別的建構方法會先自動被呼叫**：

```java
// Animal.java
class Animal {
    protected String name;
    public Animal(String name) {
        this.name = name;
        System.out.println("Animal 建構");
    }
    public void eat()   { System.out.println(name + " 吃東西"); }
    public void sleep() { System.out.println(name + " 睡覺"); }
}
```

<!--
建立子類別物件的時候，父類別的建構子會先被呼叫。

為了觀察這個順序，我們在 Animal 的建構子裡，加上一行印出「Animal 建構」。
-->

---

# 父類別建構方法的啟動順序 — 執行結果

```java
// Dog.java
class Dog extends Animal {
    public Dog(String name) {
        super(name);
        System.out.println("Dog 建構");
    }
    public void barking() { System.out.println(name + " 汪汪叫"); }

    public static void main(String[] args) {
        new Dog("旺財");
        // 輸出：
        // Animal 建構
        // Dog 建構
    }
}
```

<!--
接著在 Dog 的建構子裡，呼叫完 super 之後，印出「Dog 建構」。

在 main 裡建立一隻旺財，輸出的順序是先 Animal 建構，再 Dog 建構。

這就像蓋房子，一定要先打好地基，也就是父類別的部分，才能往上蓋子類別的部分。如果子類別的建構子沒有寫 super，Java 會自動呼叫父類別沒有參數的建構子；如果父類別沒有這樣的建構子，就會編譯錯誤。
-->

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

<!--
在繼承裡，存取修飾詞決定了子類別可以使用哪些成員。

public 在任何地方都能存取。protected 在同一個類別、同一個套件和子類別裡可以存取。不寫修飾詞，只有同一個套件可以存取。private 則只有同一個類別。

所以父類別的屬性，通常會用 protected：子類別可以直接使用，但外部的其他類別不能隨意修改。
-->

---

# protected 屬性與 super() 範例

```java
// Animal.java
class Animal {
    protected String name;
    public Animal(String name) { this.name = name; }
    public void eat()   { System.out.println(name + " 吃東西"); }
    public void sleep() { System.out.println(name + " 睡覺"); }
}
```

```java
// Dog.java
class Dog extends Animal {
    public Dog(String name) {
        super(name); // 呼叫父類別的建構方法
    }
    public void barking() { System.out.println(name + " 汪汪叫"); }
}
```

<!--
這是 protected 搭配 super 的完整範例。

Animal 的 name 是 protected，所以 Dog 的 barking 方法裡可以直接使用 name。Dog 的建構子用 super name，呼叫父類別的建構子，完成 name 的初始化。

如果把 name 改成 private，Dog 就不能直接存取它了，必須透過父類別提供的 getter。
-->

---

# super 關鍵字用法

| 用途 | 語法 | 說明 |
| --- | --- | --- |
| 呼叫父類別建構方法 | `super(參數)` | 必須放在建構方法第一行 |
| 呼叫父類別方法 | `super.方法名()` | 用於 Override 後仍需呼叫父類別版本 |
| 存取父類別屬性 | `super.屬性名` | 父子類別有同名屬性時使用 |

<!--
super 關鍵字有三種用途。

第一，super 加括號，呼叫父類別的建構子，而且必須寫在子類別建構子的第一行。第二，super 點方法名稱，呼叫父類別的方法，常用在子類別覆寫了方法，但還需要執行父類別版本的時候。第三，super 點屬性名稱，存取父類別的屬性，用在父子類別有同名屬性的時候。
-->

---

# super 關鍵字用法 — 範例

```java
class Dog extends Animal {
    String name = "狗";           // 與父類別同名屬性
    Dog(String n) {
        super(n);                 // ① 第一行呼叫父類別建構方法
    }
    @Override
    void sound() {
        super.sound();            // ② 呼叫父類別的 sound()
        System.out.println(super.name); // ③ 存取父類別的 name
    }
}
```

<!--
這段程式碼把三種用法都放在一起。

第一，Dog 的建構子第一行用 super n，呼叫父類別的建構子。第二，覆寫的 sound 方法裡，先用 super.sound 執行父類別的版本，再加上自己的內容。第三，Dog 自己也有一個 name 欄位，和父類別同名，這時候用 super.name，就能取得父類別的那一個。

不過在實務上，父子類別使用同名的欄位很容易造成混淆，應該盡量避免。
-->

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

<!--
繼承有幾種不同的形式。

單一繼承，是一個子類別繼承一個父類別。分層繼承，是多個子類別繼承同一個父類別，例如 Dog 和 Bird 都繼承 Animal。多層次繼承，是一層接一層，例如老鷹繼承鳥，鳥又繼承動物。

但 Java 不支援多重繼承，一個類別不能同時繼承兩個以上的父類別。如果需要類似的效果，要改用介面，下一個章節會介紹。
-->

---

# final 修飾符與繼承

| 用途 | 說明 |
| --- | --- |
| `final class` | 類別不能被繼承 |
| `final` 方法 | 子類別無法 Override 此方法 |
| 靜態綁定 | `final` 方法不會被覆寫，呼叫對象在編譯期即可確定 |

```java
final class MathUtil { }          // 無法被繼承
class Animal {
    public final void breathe() { }  // 子類別無法 Override
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Java 內建範例：</b><code>String</code>、<code>Math</code>，以及 <code>Integer</code> 等 Wrapper 類別都宣告為 <code>final class</code>
</div>

<!--
final 也可以用在繼承上。

final 類別不能被繼承；final 方法則不能被子類別覆寫。因為 final 方法不會被覆寫，所以呼叫哪一個版本，在編譯時期就能確定。

Java 內建的 String、Math，以及 Integer 這類包裝類別，都是 final 類別。這樣可以確保它們的行為不會被子類別改變，例如沒有人能寫一個「假的」String 來破壞安全性。
-->

---

# 密封類別 (Sealed Classes)

JDK 17 正式功能，允許父類別**精確控制**哪些子類別可以繼承它。

| 關鍵字 | 說明 |
| --- | --- |
| `sealed` | 宣告此類別為密封類別 |
| `permits` | 指定允許繼承的子類別清單 |

```java
// 只允許 Circle 和 Square 繼承 Shape
public sealed abstract class Shape permits Circle, Square { }

final class Circle extends Shape { }
final class Square extends Shape { }
```

<!--
有時候我們希望一個類別可以被繼承，但只允許特定的幾個子類別。Java 17 正式推出的密封類別，就是為了這個需求。

在類別前面加上 sealed，再用 permits 列出允許繼承的子類別。看範例，Shape 只允許 Circle 和 Square 繼承，其他類別如果想繼承 Shape，就會編譯錯誤。
-->

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

<!--
密封類別的子類別，必須明確宣告自己的狀態，三選一。

final，表示不能再被繼承。sealed，表示繼續保持密封，並列出自己允許的子類別。non-sealed，則是解除密封，任何類別都可以繼承它，回到一般的繼承方式。

看範例，Circle 宣告成 non-sealed，所以任何類別都可以再繼承 Circle。
-->

---

# Sealed Classes 與 Pattern Matching

密封類別搭配 `switch` 模式比對（JDK 21 正式功能），編譯器會檢查**窮舉性**：

```java
// Shape 是 sealed abstract，編譯器知道只有 Circle 和 Square
// （假設 Circle 有 radius()、Square 有 side() 方法）
return switch (shape) {
    case Circle c -> c.radius() * c.radius() * Math.PI;
    case Square s -> s.side() * s.side();
    // 不需要 default 區塊！編譯器保證所有可能都已涵蓋
};
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 當你新增一個 permits 子類別時，編譯器會提醒你更新所有相關的 <code>switch</code> 邏輯
</div>

<!--
密封類別最大的好處，是搭配 switch 的模式比對。

因為 Shape 是密封的，編譯器知道它只可能是 Circle 或 Square。所以 switch 只要處理這兩種情況，不需要寫 default。

如果之後在 permits 裡新增了一種形狀，卻忘了更新 switch，編譯器就會提醒我們，避免漏掉任何情況。
-->

---

# 紀錄類別 (Records) 簡介

JDK 16 引入，只需宣告欄位，編譯器自動產生 constructor、getter、`equals`、`hashCode`、`toString`：

```java
// 傳統寫法需要數十行；record 一行搞定
record Person(String name, int age) { }

Person p = new Person("炭治郎", 15);
System.out.println(p.name()); // "炭治郎"
System.out.println(p.age());  // 15
System.out.println(p);        // Person[name=炭治郎, age=15]
```

<!--
另一個 Java 16 引入的功能，是紀錄類別 record。

如果一個類別只是用來存放資料，傳統的寫法要寫欄位、建構子、getter，還有 equals、hashCode 和 toString，常常要幾十行。用 record，只需要一行，宣告有哪些欄位，其他的編譯器都會自動產生。

看範例，Person 有 name 和 age。取得資料時呼叫 name 和 age 方法，注意沒有 get 前綴。直接印出 p，也會得到清楚易讀的內容。
-->

---

# Records 與繼承限制

| 規則 | 說明 |
| --- | --- |
| 隱含 final | Record **無法被繼承** |
| 固定父類別 | Record 隱含繼承 `java.lang.Record`，不能再 `extends` 其他類別 |
| 實作介面 | Record **可以**實作多個介面 |

```java
record Point(int x, int y) { }   // ✅ 合法
// class Sub extends Point { }    // ❌ Record 不能被繼承
// record R extends Animal { }    // ❌ Record 不能繼承其他類別
interface Drawable { }
record Circle(double r) implements Drawable { } // ✅ 可實作介面
```

<!--
record 在繼承上有一些限制。

record 隱含是 final 的，所以不能被繼承。它也已經隱含繼承了 java.lang.Record，所以不能再 extends 其他類別。不過，record 可以實作介面，而且可以實作好幾個。

看範例，Point 是合法的 record，但其他類別不能繼承它，它也不能繼承 Animal。Circle 則可以實作 Drawable 介面。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# IS-A 與 HAS-A 關係

<!--
接下來，我們來看類別之間的兩種關係：「是一種」和「擁有一個」。
-->

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
```

```java
Eagle eagle = new Eagle();
System.out.println(eagle instanceof Bird);   // true
System.out.println(eagle instanceof Animal); // true
```

<!--
IS-A 代表「是一種」，也就是繼承的關係。狗是一種動物，所以 Dog 繼承 Animal。

我們可以用 instanceof 驗證這個關係。看範例，Eagle 繼承 Bird，Bird 又繼承 Animal。所以 eagle instanceof Bird 是 true，instanceof Animal 也是 true，因為老鷹是一種鳥，也是一種動物。
-->

---

# HAS-A 關係 — 聚合 vs 組合

HAS-A 代表「擁有一個」：類別 A 的**欄位**是類別 B 的物件，不使用 `extends`。

| 類型 | 擁有程度 | 生命週期 | 範例 |
| --- | --- | --- | --- |
| 聚合 (Aggregation) | 弱擁有 | 部分可獨立存在，由外部傳入 | Car HAS-A Driver |
| 組合 (Composition) | 強擁有 | 部分隨整體建立、一起消失 | Car HAS-A Engine |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 兩者都用「欄位 + 委派」重用程式碼；物件導向設計原則：<b>組合優於繼承</b>
</div>

<!--
HAS-A 代表「擁有一個」，例如汽車有一個引擎。這不是用繼承，而是讓類別 A 的欄位，存放類別 B 的物件。

HAS-A 又分成兩種。聚合是比較弱的擁有，部分可以獨立存在，通常從外面傳進來，例如汽車和駕駛，換一個駕駛，車子還是那台車。組合是比較強的擁有，部分跟著整體一起建立、一起消失，例如汽車和它的引擎。

物件導向設計有一個重要的原則：「組合優於繼承。」能用 HAS-A 解決的，就不一定要用繼承。
-->

---

# HAS-A 聚合範例 (Aggregation)

```java
class Driver {
    String name;
    Driver(String name) { this.name = name; }
}
```

```java
class Car {
    private Driver driver;                 // Car HAS A Driver
    public void setDriver(Driver d) {      // 由外部傳入，可隨時更換
        this.driver = d;
    }
}
```

<!--
這是聚合的例子。

Car 有一個 Driver 欄位，但 Driver 不是在 Car 裡建立的，而是透過 setDriver 從外面傳進來。所以駕駛可以隨時更換，而且就算車子不在了，駕駛還是存在。
-->

---

# HAS-A 組合範例 (Composition)

```java
class Engine {
    void start() { System.out.println("引擎發動"); }
}
```

```java
class Car {
    // Car 自己建立並擁有 Engine
    private final Engine engine = new Engine();
    public void start() {
        engine.start();   // 委派給 Engine
    }
}
```

<!--
這是組合的例子。

Car 自己建立並擁有一個 Engine，用 final 確保它不會被換掉。Car 的 start 方法，把工作交給 engine 的 start 去做，這叫做「委派」。

Engine 跟著 Car 一起建立，Car 消失了，這個 Engine 也就沒有用了。這就是比較強的擁有關係。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Override 與 Overload

<!--
接下來，我們來分清楚兩個名字很像、但完全不同的觀念：Override 和 Overload。
-->

---
layout: default
---

# Override 最簡範例

子類別「重新定義」父類別的方法：

```java
class Animal {
    public void move() { System.out.println("Animal 移動"); }
}
```

```java
class Dog extends Animal {
    @Override
    public void move() { System.out.println("Dog 跑步"); }
}
```

<!--
Override 是子類別「重新定義」父類別的方法，也叫做覆寫。

看範例，Animal 的 move 印出 Animal 移動。Dog 繼承 Animal 之後，寫了一個同名、同參數的 move，改成印出 Dog 跑步。這樣呼叫 Dog 的 move 時，就會執行 Dog 自己的版本。

方法上面的 @Override，是告訴編譯器「這是覆寫」，等一下會詳細說明它的用途。
-->

---

# Override 基本規則

| 規則 | 說明 |
| --- | --- |
| 方法名稱 | 必須**相同** |
| 參數列表 | 必須**相同** |
| 回傳型態 | 必須**相同**（或子型態） |
| 存取權限 | 只能**放寬**，不能縮減 |
| 不可覆寫 | `static`、`final`、`private` 方法 |

<!--
Override 有幾條規則。

方法名稱和參數列表都必須相同。回傳型別也必須相同，或者是它的子型別。存取權限只能放寬，不能縮小，例如父類別是 public，子類別就不能改成 private。

另外，static、final 和 private 方法，都不能被覆寫。
-->

---

# 方法隱藏 (Method Hiding) vs Override

| 比較項目 | Override | 方法隱藏 |
| --- | --- | --- |
| 適用對象 | 實例方法 | `static` 方法 |
| 決定時機 | 執行時期（動態綁定） | 編譯時期（靜態綁定） |
| 呼叫依據 | 物件的**實際型態** | 變數的**宣告型態** |

```java
class Animal { static void sound() { System.out.println("Animal"); } }
```

```java
class Dog extends Animal { static void sound() { System.out.println("Dog"); } }
```

```java
Animal a = new Dog();
a.sound(); // 輸出：Animal（由宣告型態 Animal 決定）
```

<!--
如果父類別和子類別有同名的 static 方法，這不是 Override，而是「方法隱藏」。

兩者的差別在於：Override 是在執行時期，依照物件的實際型別決定呼叫哪個版本；方法隱藏則是在編譯時期，依照變數宣告的型別決定。

看範例，變數 a 宣告成 Animal，實際上是 Dog。呼叫 static 的 sound，印出的是 Animal，因為它是依照宣告的型別決定的。所以 static 方法，建議一律用類別名稱來呼叫，避免這種混淆。
-->

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
```

```java
class Dog extends Animal {
    @Override
    Dog produce() { return new Dog(); }
}
```

<!--
Override 時，回傳型別可以是父類別回傳型別的子型別，這叫做協變回傳型別。

看範例，Animal 的 produce 回傳 Animal；Dog 覆寫時，可以改成回傳更具體的 Dog，因為 Dog 也是一種 Animal。

但這只適用於物件型別。基本型別不行，例如 int 不能改成 double。
-->

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

<!--
子類別覆寫了方法之後，如果還想執行父類別原本的版本，可以用 super 點方法名稱。

看範例，Dog 的 move 先呼叫 super.move，印出 Animal 移動，再印出 Dog 跑步。

這種「在父類別的行為上，再加一點自己的東西」的寫法，在實務上非常常見。
-->

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

<!--
@Override 是一個註解，它會請編譯器幫我們檢查：這個方法真的有覆寫到父類別的方法嗎？

看範例裡註解掉的部分，如果不小心把 move 拼成 mov，沒有 @Override 的話，編譯器會以為我們新增了一個方法，程式能執行，但結果完全不對。加上 @Override，編譯時就會直接報錯。

所以強烈建議，每次覆寫方法時都加上 @Override。
-->

---

# Overload — 多重定義

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

<!--
Overload 是多重定義，也就是同一個類別裡，有好幾個名稱相同、但參數不同的方法。我們在類別與物件那一章已經學過了。

看範例，eat 有兩個版本，一個只接收食物，另一個還接收份量。Java 會依照參數的數量、型別和順序，決定呼叫哪一個。

和 Override 不同，Overload 不需要繼承關係，在同一個類別裡就能做到。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 多形 Polymorphism

<!--
接下來是這一章的重點：多形。
-->

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

<!--
多形的意思是「同一個操作，作用在不同的物件上，會產生不同的行為」。它分成兩種。

編譯時期多形，就是剛才的 Overload，在編譯時就決定好呼叫哪個方法。執行時期多形，則是透過 Override 和向上轉型，在程式執行時，才依照物件的實際型別決定。

下面整理了執行時期多形的三個條件：有繼承關係、子類別覆寫了父類別的方法，以及用父類別的變數去參考子類別的物件。
-->

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

<!--
我們先看沒有多形的情況。

要讓狗和鳥移動，就要分別宣告 Dog 和 Bird 的變數，各自呼叫 move。每多一種動物，就要多寫一段程式，而且呼叫的地方必須知道每一種子類別。

當動物的種類越來越多，這樣的程式就很難擴充。
-->

---

# 執行時期多形 — 準備工作

各子類別各自 Override `move()` 方法：

```java
class Animal { public void move() { System.out.println("Animal 移動"); } }
```

```java
class Dog extends Animal {
    @Override
    public void move() { System.out.println("Dog 跑步"); }
}
```

```java
class Bird extends Animal {
    @Override
    public void move() { System.out.println("Bird 飛翔"); }
}
```

<!--
多形的準備工作，是讓每個子類別各自覆寫 move 方法。

Animal 的 move 印出 Animal 移動，Dog 改成印出 Dog 跑步，Bird 改成印出 Bird 飛翔。
-->

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

<!--
接著，我們用父類別 Animal 的變數，來接收不同的子類別物件，這叫做向上轉型。

a1 和 a2 宣告的型別都是 Animal，但 a1 實際上是 Dog，a2 實際上是 Bird。呼叫同一個 move 方法，a1 印出 Dog 跑步，a2 印出 Bird 飛翔。

這就是多形：「呼叫的程式碼完全一樣，執行的行為卻依照物件的實際型別而不同。」之後新增任何一種動物，呼叫的程式都不需要修改。
-->

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

<!--
我們仔細看向上轉型。把子類別物件指定給父類別的變數，Java 會自動轉型，不需要特別寫轉型的語法。

要注意，透過這個變數，只能呼叫父類別有定義的方法。但實際執行的，是子類別覆寫後的版本。

所以變數 a 只認得 Animal 有的方法，不能呼叫 Dog 獨有的 barking。
-->

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

<!--
如果要把父類別的變數轉回子類別，就需要向下轉型，而且要明確寫出強制轉型。

看範例，a 實際上是 Dog，強制轉型成 Dog 之後，就可以呼叫 Dog 專屬的 barking 方法了。

但要特別小心：如果物件的實際型別不符合，例如 a 其實是 Bird，強制轉成 Dog，執行時就會拋出 ClassCastException。所以轉型之前，建議先用 instanceof 確認型別。
-->

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

<!--
Java 16 引入了 instanceof 的模式比對，把判斷型別和轉型合併成一步。

傳統的寫法，要先用 instanceof 判斷，再另外宣告變數並強制轉型。新的寫法，直接在 instanceof 後面加上變數名稱，判斷成立時，就自動轉型好了。

看範例，obj 如果是 String，就自動得到變數 s，可以直接呼叫 toLowerCase。這個變數 s 只在判斷成立的範圍內有效。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 靜態 / 動態綁定

<!--
多形背後的運作機制，就是靜態綁定和動態綁定。
-->

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

<!--
綁定，指的是決定一個方法呼叫，要執行哪一段程式碼。

靜態綁定在編譯時期就決定了，適用於 static、final 和 private 方法，因為它們不會被覆寫。動態綁定則要到執行時期才決定，適用於一般被覆寫的方法。

看範例，a 宣告成 Animal，呼叫 move 時，JVM 會在執行時檢查 a 實際上是 Dog，所以執行 Dog 的 move。這就是執行時期多形的底層原理。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 巢狀類別 Nested Classes

<!--
最後一個主題：巢狀類別，也就是定義在另一個類別裡面的類別。
-->

---
layout: default
---

# 巢狀類別的種類

| 類型 | 說明 | 使用場景 |
| --- | --- | --- |
| 一般內部類別 (Inner Class) | 定義在外部類別內，可存取外部所有成員 | 資料封裝、輔助類別 |
| 靜態巢狀類別 (Static Nested) | 以 `static` 宣告，不需外部物件即可建立 | Builder、工具類別 |
| 方法內部類別 (Method-local) | 定義在方法內，只有該方法可使用 | 極少使用 |
| 匿名內部類別 (Anonymous) | 宣告同時建立物件，一次性使用 | Override 介面或抽象方法 |

<!--
巢狀類別有四種。

一般內部類別，定義在外部類別裡，可以存取外部類別的所有成員。靜態巢狀類別，用 static 宣告，不需要外部類別的物件就能建立，常用在 Builder 模式。方法內部類別，定義在方法裡，只有那個方法能使用，實務上很少用到。匿名內部類別，在宣告的同時就建立物件，用於只用一次的情況。
-->

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

<!--
這是一般內部類別的宣告方式。

InnerClass 定義在 OuterClass 裡面。它的 display 方法，可以直接使用外部類別的欄位 x，就像使用自己的欄位一樣。

當一個類別只是用來輔助另一個類別，不需要讓外部知道時，就適合寫成內部類別。
-->

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

<!--
建立一般內部類別的物件，要先有外部類別的物件。

看範例，先建立 outer，再用 outer 點 new，建立 InnerClass 的物件。呼叫 display，就會印出 x 等於 10。

內部類別也可以宣告成 private，這樣只有外部類別自己能使用它。
-->

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

<!--
方法內部類別，是定義在方法裡面的類別。

看範例，MathRoom 寫在 showRoom 方法裡，所以只有 showRoom 能使用它。方法裡建立 MathRoom 物件，就能印出學生數 40。

這種寫法在實務上很少見，知道有這種語法就可以了。
-->

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

<!--
匿名內部類別，是在宣告的同時，直接建立物件並覆寫方法，而且不需要幫類別取名字。

看範例，new Animal 後面接著一對大括號，在裡面直接覆寫 move 方法。myAnimal 呼叫 move，就會印出特殊移動方式。

當某個子類別只會用到一次，不值得特地寫一個類別時，就可以用匿名類別。之後學到 Lambda，很多匿名類別都能寫得更簡潔。
-->

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

<!--
匿名類別也可以直接當作參數傳進方法裡。

看範例，呼叫 showAnimal 時，直接在括號裡建立一個匿名的 Animal，並覆寫 move 方法。

這樣寫雖然合法，但如果匿名類別的內容很長，程式碼就會很難閱讀和維護，所以不建議大量使用。
-->

---

# 練習
### 任務說明

1. **繼承練習**：建立父類別 `Father`，子類別 `Son` 和 `Daughter`
   - `Father`：有 `protected String name` 欄位與建構方法、`walk()` 印出「名字 + ` is walking!!!`」
   - `Son`：建構方法呼叫 `super(name)`，Override `walk()` 改印 `~~~`，加上 `playBall()`
   - `Daughter`：建構方法呼叫 `super(name)`，Override `walk()` 改印 `@@@`，加上 `shopping()`
   - `main`：建立三個物件，呼叫各自的 `walk()` 與特有方法

2. **多形練習**：建立 `Animal`、`Dog`、`Bird`
   - `Animal` 同樣有 `protected String name` 與建構方法，子類別呼叫 `super(name)`
   - 各自 Override `move()`，印出「名字 + 移動方式」
   - 用 `Animal[] animals = { new Dog("旺財"), new Bird("小翠") }` 搭配迴圈呼叫 `move()`

<!--
接下來我們動手練習，這次有兩個部分。

第一部分練習繼承。建立父類別 Father，有 name 欄位，透過建構子傳入，walk 方法印出名字加上 is walking。再建立兩個子類別 Son 和 Daughter，建構子都用 super name，各自覆寫 walk，把最後的符號換掉，並加上自己獨有的方法。最後在 main 裡建立三個物件，呼叫它們的方法。

第二部分練習多形。Animal 一樣要有 name 欄位和建構子，Dog 和 Bird 用 super name 傳給父類別，再各自覆寫 move，印出名字和移動方式。接著用一個 Animal 陣列存放旺財和小翠，用迴圈呼叫每一個的 move。

下一頁是程式執行後應該看到的結果。
-->

---

# 練習
### 預期輸出

<div class="grid grid-cols-2 gap-6">
<div>

**練習 1**

```text
Dad is walking!!!
Tom is walking~~~
Tom is playing ball!
Amy is walking@@@
Amy is shopping!
```

</div>
<div>

**練習 2**

```text
旺財 跑步
小翠 飛翔
```

</div>
</div>

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 名字由建構方法傳入；練習 2 的迴圈只寫一次 <code>move()</code>，卻印出兩種結果
</div>

<!--
這是預期的輸出。

練習一，爸爸 Dad 印出三個驚嘆號，兒子 Tom 印出三個波浪號，還會打球；女兒 Amy 印出三個小老鼠符號，還會去逛街。這些名字都是建立物件時，透過建構子傳進去的。

練習二，旺財印出跑步，小翠印出飛翔。注意迴圈裡只寫了一次 move，卻印出兩種不同的結果，這就是多形。

我們先暫停一下，自己動手試試看。

【講師】給學生約 20 分鐘實作。
-->

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

<!--
我們來看提示。

繼承的部分，父類別用 protected 宣告 name，讓子類別可以直接使用；子類別的建構子，用 super name 初始化父類別的屬性。

多形的部分，先確認 Dog 和 Bird 都用 @Override 覆寫了 move。接著宣告一個 Animal 陣列，放進一隻狗和一隻鳥，用迴圈呼叫 move。執行時會看到，同樣的呼叫，印出了不同的結果，這就是多形的效果。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學了物件導向最核心的兩個觀念。

繼承用 extends，讓子類別重用父類別的程式碼，建立物件時父類別的建構子會先執行。final 可以禁止繼承，sealed 可以限制哪些類別能繼承。IS-A 是繼承，HAS-A 是組合，而設計時組合優於繼承。

Override 是子類別重新定義父類別的方法，記得加上 @Override。多形讓我們用父類別的變數，統一操作不同的子類別物件，背後靠的是動態綁定。

【講師】開放 Q&A。
-->

---
layout: end
---

# 本章結束
### 繼承讓程式碼更精簡，多形讓設計更彈性

<!--
繼承讓程式碼更精簡，多形讓設計更有彈性。

下一章，我們要認識所有類別共同的祖先：Object 類別。我們下一章見。
-->

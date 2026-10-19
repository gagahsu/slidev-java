---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: Java 介面 (Interface)
routeAlias: interface
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
    介面 (Interface)
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「定義行為契約，讓不同類別共享相同的能力」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章的主題是介面。

想像一下，鳥會飛、飛機也會飛，但飛機不是一種鳥，鳥也不是一種飛機，它們之間沒有繼承關係。那要怎麼表達「它們都有飛的能力」？

介面就是用來定義這種行為契約的工具。學完這一章，我們就能用介面，讓彼此不相關的類別共享相同的能力，這也是 Java 框架裡最常見的設計方式。
-->

---
layout: default
---

# Outline

- **認識介面** — 概念、語法、與抽象類別的比較
- **介面的成員變數** — `public static final`
- **Java 8 新增介面內容** — Default 方法、Static 方法、Functional Interface
- **Java 9 新增介面內容** — Private 方法
- **介面的繼承** — 基本繼承、多重繼承
- **進階主題** — 名稱衝突、Diamond 問題、密封介面 (Sealed)
- **課堂練習**

<!--
這一章我們會先認識介面的概念和語法，以及介面裡的成員變數。

接著看 Java 8 為介面加入的 default 方法、static 方法和功能介面，以及 Java 9 加入的 private 方法。然後學介面的繼承，包括多重繼承。最後是進階主題：名稱衝突、鑽石問題，以及密封介面。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識介面
# Interface

<!--
我們先來認識介面，看看它和類別有什麼不同。
-->

---
layout: default
---

# 什麼是介面？

- 介面（Interface）**不是物件**，而是**行為的規範**
- 繼承描述「IS-A」關係，介面描述「**有某種能力**」
  - 鳥（Bird）**是**動物 → 繼承自 Animal（IS-A）
  - 鳥會飛、飛機也會飛 → 「飛」是**行為**，不是類別關係
  - 設計介面定義「飛」的行為，讓不同類別去實作（implements）
- 介面**無法用 `new` 直接建立物件**

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>比較飛機與鳥：</b>兩者不是同一類時，但都有「飛」的能力 → 用介面定義共同行為
</div>

<!--
介面不是物件，而是一份「行為的規範」。

繼承描述的是「是一種」的關係：鳥是一種動物，所以 Bird 繼承 Animal。介面描述的則是「有某種能力」：鳥會飛，飛機也會飛，「飛」是一種行為，而不是一種類別關係。

所以我們可以設計一個介面，定義「飛」這個行為，再讓不同的類別去實作它。

另外，和抽象類別一樣，介面也不能用 new 直接建立物件。
-->

---

# 介面的語法

```java
interface Flyable {
    void fly();   // 預設為 public abstract，可省略宣告
}
```

- 所有方法預設是 `public abstract`（可省略）
- 所有成員變數預設是 `public static final`
- Java 8 之前介面**只能有**抽象方法

```java
class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("鳥兒飛翔");
    }
}
```

<!--
宣告介面用 interface 關鍵字。Flyable 介面定義了一個 fly 方法，只寫方法的宣告，沒有內容。

介面裡的方法，預設就是 public abstract，所以可以省略不寫。成員變數則預設是 public static final。另外，Java 8 以前，介面裡只能有抽象方法。

類別要實作介面，用 implements 關鍵字。看下面，Bird implements Flyable，並覆寫 fly 方法，印出鳥兒飛翔。
-->

---

# 介面的實作規則

- 實作類別必須覆寫**所有的抽象方法**
- 覆寫時存取控制**必須宣告為 `public`**
- 建議加上 `@Override` 確認覆寫正確

```java
class Airplane implements Flyable {
    @Override
    public void fly() {
        System.out.println("飛機用引擎飛");
    }
}
public class Demo {
    public static void main(String[] args) {
        Flyable bird = new Bird();
        Flyable airplane = new Airplane();
        bird.fly();     // 鳥兒飛翔
        airplane.fly(); // 飛機用引擎飛
    }
}
```

<!--
實作介面有幾條規則：類別必須覆寫介面裡所有的抽象方法；覆寫時，存取權限一定要宣告成 public；另外建議加上 @Override。

看範例，Airplane 也實作了 Flyable，fly 印出飛機用引擎飛。

在 main 裡，bird 和 airplane 都宣告成 Flyable 型別，呼叫同一個 fly 方法，卻印出不同的結果。這和上一章學的多形一樣，介面也可以當作變數的型別。
-->

---

# 介面 vs 抽象類別

| 比較項目 | 抽象類別 | 介面 |
| --- | --- | --- |
| 父類別 | 只能繼承一個 | 可繼承多個介面 |
| 子類別 | 只能 `extends` 一個 | 可 `implements` 多個 |
| 方法 | 可包含具體方法 | Java 8 前只能抽象方法 |
| 用途 | 類別間的緊密關係 | 不同類別間的共同行為 |
| 適用 | Car → Benz、Audi（IS-A） | Bird 與 Airplane 都能飛（行為） |

<!--
我們比較一下介面和抽象類別。

一個類別只能繼承一個父類別，但可以實作好幾個介面。抽象類別可以有一般方法；介面在 Java 8 以前，只能有抽象方法。

用途上，抽象類別適合關係緊密的類別，例如 Benz 和 Audi 都是一種 Car；介面則適合定義不同類別之間共同的行為，例如鳥和飛機都會飛。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的成員變數
# Interface Member Variables

<!--
接下來，我們看看介面裡的成員變數有什麼特別的地方。
-->

---
layout: default
---

# 介面的成員變數

介面的成員變數預設是 `public`、`static`、`final`：

| 修飾詞 | 意義 |
| --- | --- |
| `public` | 所有人都可以取得 |
| `static` | 所有實作類別共享同一份 |
| `final` | 值不可更動（常數），**一定要給預設值** |

```java
interface Shape {
    double PI = 3.14159;  // 等同 public static final
    double area();        // 等同 public abstract
}
```

<!--
介面裡的成員變數，預設就是 public、static 和 final。

public 代表所有人都可以取得；static 代表所有實作類別共用同一份；final 代表它是常數，值不能改變，所以宣告時一定要給初始值。

看範例，Shape 介面裡的 PI，就等同於 public static final；area 方法則等同於 public abstract。
-->

---

# 介面成員變數 — 範例

```java
interface Shape {
    double PI = 3.14159;
    double area();
}
class Rectangle implements Shape {
    double width, height;
    Rectangle(double w, double h) { width = w; height = h; }
    public double area() { return width * height; }
}
class Circle implements Shape {
    double radius;
    Circle(double r) { radius = r; }
    public double area() { return PI * radius * radius; }
}
```

<!--
看實際的使用方式。

Rectangle 和 Circle 都實作了 Shape 介面，各自寫出計算面積的 area 方法。矩形是寬乘以高，圓形是 PI 乘以半徑的平方。

注意 Circle 的 area 裡，直接使用了介面定義的 PI 常數，因為實作類別可以直接使用介面的常數。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 8 新增介面內容
# Default & Static Methods

<!--
Java 8 為介面帶來了很大的改變，加入了 default 方法和 static 方法。
-->

---
layout: default
---

# Java 8 新增介面內容 — 概覽

| 功能 | Java 版本 | 說明 |
| --- | --- | --- |
| Constant variable | Java 1.0 起 | 常數成員變數 |
| Abstract methods | Java 1.0 起 | 抽象方法 |
| **Default methods** | **Java 8 新增** | 預設方法（有方法本體） |
| **Static methods** | **Java 8 新增** | 靜態方法 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>為什麼新增 Default 方法？</b>為維持向後相容性，舊介面可以新增方法，不破壞已有的實作類別
</div>

<!--
這張表整理了 Java 8 前後介面可以包含的內容。

從 Java 1.0 開始，介面就可以有常數和抽象方法。Java 8 新增了 default 方法和 static 方法，讓介面裡的方法可以有實際的內容。

為什麼要加入 default 方法？為了向後相容。如果一個被廣泛使用的介面要新增方法，在以前，所有實作它的類別都會因為少了這個方法而編譯失敗。有了 default 方法，就可以直接在介面裡提供預設的實作，既有的類別完全不受影響。
-->

---

# Default 方法

```java
interface Vehicle {
    String getBrand();
    default void alarmOn() {
        System.out.println("防盜啟動");
    }
    default void alarmOff() {
        System.out.println("防盜關閉");
    }
}
```

- 使用 `default` 關鍵字，介面內**直接提供預設實作**
- 實作類別可直接繼承 Default 方法，也可以 Override
- Default 方法**可以繼承**，不強制是 abstract

<!--
default 方法用 default 關鍵字宣告，它在介面裡就直接提供了預設的實作。

看範例，Vehicle 介面有一個抽象方法 getBrand，另外有兩個 default 方法：alarmOn 印出防盜啟動，alarmOff 印出防盜關閉。

實作類別可以直接繼承這些 default 方法，也可以依照需要覆寫它們。
-->

---

# Default 方法 — 範例

```java
class Car implements Vehicle {
    private String brand;
    Car(String b) { this.brand = b; }
    public String getBrand() { return brand; }
    // alarmOn / alarmOff 直接繼承自 Vehicle，不需重寫
}
public class Demo {
    public static void main(String[] args) {
        Car car = new Car("Toyota");
        car.alarmOn();   // 防盜啟動
        car.alarmOff();  // 防盜關閉
    }
}
```

<!--
Car 實作 Vehicle，只需要實作抽象方法 getBrand。alarmOn 和 alarmOff 不用自己寫，直接繼承介面的預設版本。

在 main 裡建立一台 Toyota，呼叫 alarmOn 和 alarmOff，就會印出防盜啟動和防盜關閉。
-->

---

# Static 方法

```java
interface MathUtils {
    static int add(int a, int b) {
        return a + b;
    }
    static int multiply(int a, int b) {
        return a * b;
    }
}
```

- 在介面中定義 `static` 方法
- **只能用介面名稱呼叫**，不能透過物件呼叫
- 實作類別**無法 Override** static 方法

<!--
介面也可以定義 static 方法。

看範例，MathUtils 介面定義了 add 和 multiply 兩個 static 方法。

要注意兩件事：static 方法只能用介面名稱呼叫，不能透過物件呼叫；而且實作類別不能覆寫 static 方法。
-->

---

# Static 方法 — 範例

```java
public class Demo {
    public static void main(String[] args) {
        int sum = MathUtils.add(3, 5);
        int product = MathUtils.multiply(4, 6);
        System.out.println("加法：" + sum);
        System.out.println("乘法：" + product);
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記住：</b>Static 方法只能用「<code>介面名稱.方法名稱()</code>」呼叫，無法用物件呼叫
</div>

<!--
使用時，直接用介面名稱點方法名稱。MathUtils.add 3、5 得到 8，MathUtils.multiply 4、6 得到 24。

這種寫法適合放一些和這個介面相關的工具方法。
-->

---

# 功能介面 (Functional Interface)

| 特性 | 說明 |
| --- | --- |
| 定義 | 只有**一個**抽象方法的介面（又稱 SAM - Single Abstract Method） |
| 註解 | `@FunctionalInterface`（選用，但建議加上以利檢查） |

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
    // 可以有 default 或 static 方法，但只能有一個 abstract 方法
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>主要用途：</b> 作為 Lambda 運算式的目標類型，簡化匿名內部類別的撰寫。
</div>

<!--
只有一個抽象方法的介面，叫做功能介面，英文是 Functional Interface。

宣告時可以加上 @FunctionalInterface 註解。它不是必要的，但加上之後，編譯器會幫我們檢查，如果不小心加了第二個抽象方法，就會報錯。功能介面裡還是可以有 default 和 static 方法，只要抽象方法只有一個就好。

看範例，Calculator 只有一個 calculate 抽象方法，就是一個功能介面。

功能介面最主要的用途，是搭配 Lambda 運算式使用，Lambda 在之後的章節會詳細介紹。
-->

---

# 內建功能介面 (一) — 基本型別

`java.util.function` 套件提供常用的功能介面，可直接搭配 Lambda 使用：

| 介面 | 抽象方法 | 說明 |
| --- | --- | --- |
| `Predicate<T>` | `boolean test(T t)` | 傳入一個值，回傳 true/false 判斷 |
| `Function<T,R>` | `R apply(T t)` | 傳入 T，轉換為 R 回傳 |
| `Consumer<T>` | `void accept(T t)` | 傳入一個值，執行動作不回傳 |
| `Supplier<T>` | `T get()` | 不傳入參數，產生一個 T |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 這四個介面皆標有 <code>@FunctionalInterface</code>，可直接用 Lambda 或方法參考（Method Reference）傳入
</div>

<!--
Java 在 java.util.function 套件裡，已經提供了很多常用的功能介面，最基本的有四個。

Predicate 接收一個值，回傳 true 或 false，用來做判斷。Function 接收一個型別 T 的值，轉換成型別 R 回傳。Consumer 接收一個值，執行某個動作，但不回傳任何東西。Supplier 則不接收參數，負責產生一個值。

這四個介面都可以直接用 Lambda 或方法參考來建立。
-->

---

# 內建功能介面 (一) — 範例

```java
Predicate<String> isLong = s -> s.length() > 5;
System.out.println(isLong.test("炭治郎"));         // false
System.out.println(isLong.test("Kamado Tanjiro")); // true

Function<String, Integer> toLen = String::length;
System.out.println(toLen.apply("鬼滅之刃")); // 4

Consumer<String> print = System.out::println;
print.accept("禰豆子"); // 禰豆子

Supplier<String> hero = () -> "炭治郎";
System.out.println(hero.get()); // 炭治郎
```

<!--
我們看這四個介面的實際用法。

Predicate isLong 判斷字串長度是否大於 5，炭治郎是 false，Kamado Tanjiro 是 true。Function toLen 把字串轉換成它的長度，鬼滅之刃是 4。Consumer print 會把傳入的字串印出來。Supplier hero 不需要參數，每次呼叫 get 都會產生炭治郎。

範例裡的箭頭和雙冒號寫法，就是 Lambda 和方法參考，現在先感受一下它們有多簡潔。
-->

---

# 內建功能介面 (二) — Bi 系列與 Operator

| 介面 | 抽象方法 | 說明 |
| --- | --- | --- |
| `BiFunction<T,U,R>` | `R apply(T t, U u)` | 兩個不同型別輸入，一個輸出 |
| `UnaryOperator<T>` | `T apply(T t)` | 繼承 Function，輸入輸出型別相同 |
| `BinaryOperator<T>` | `T apply(T t1, T t2)` | 繼承 BiFunction，兩輸入同型別 |

```java
BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
System.out.println(repeat.apply("鬼", 3)); // 鬼鬼鬼

UnaryOperator<String> upper = String::toUpperCase;
System.out.println(upper.apply("tanjiro")); // TANJIRO

BinaryOperator<Integer> max = (a, b) -> a > b ? a : b;
System.out.println(max.apply(10, 20)); // 20
```

<!--
另外還有接收兩個參數的 Bi 系列，以及輸入和輸出型別相同的 Operator 系列。

BiFunction 接收兩個不同型別的參數，回傳一個結果，例如把鬼重複 3 次，得到鬼鬼鬼。UnaryOperator 的輸入和輸出是同一個型別，例如把字串轉成大寫。BinaryOperator 接收兩個同型別的參數，回傳同型別的結果，例如取兩數的最大值，得到 20。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 9 新增介面內容
# Private Methods

<!--
Java 9 又為介面加入了 private 方法。
-->

---
layout: default
---

# Java 9 新增介面內容 — 概覽

| 功能 | Java 版本 | 說明 |
| --- | --- | --- |
| Constant variable | Java 1.0 起 | 常數成員變數 |
| Abstract methods | Java 1.0 起 | 抽象方法 |
| Default methods | Java 8+ | 預設方法 |
| Static methods | Java 8+ | 靜態方法 |
| **Private methods** | **Java 9 新增** | 私有方法 |
| **Private Static methods** | **Java 9 新增** | 私有靜態方法 |

<!--
這張表整理了到 Java 9 為止，介面可以包含的所有內容。

除了常數、抽象方法、default 方法和 static 方法，Java 9 再加入了 private 方法和 private static 方法。
-->

---

# Private 方法的規則

| 規則 | 說明 |
| --- | --- |
| 只能在介面內使用 | 無法從實作類別或外部呼叫 |
| 不能抽象化 | 必須有方法本體 |
| `private static` 方法 | 可在 static 和 non-static 方法中使用 |
| `private` non-static 方法 | 不能在 `private static` 方法中使用 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>設計目的：</b>讓介面內部程式碼可重複使用，避免在多個 Default 方法中寫重複邏輯
</div>

<!--
private 方法有幾條規則。

它只能在介面內部使用，實作類別和外部都不能呼叫。它不能是抽象的，一定要有方法本體。private static 方法可以在 static 和一般的方法裡使用；但一般的 private 方法，不能在 private static 方法裡使用。

設計 private 方法的目的，是讓介面內部的程式碼可以重複使用。當好幾個 default 方法有相同的邏輯時，就可以把它抽出來，寫成一個 private 方法。
-->

---

# Private 方法 — 範例

```java
interface LearnJava {
    default void method2() { method4(); }
    static void method3() { method5(); }
    private void method4() {
        System.out.println("這是private方法");
    }
    private static void method5() {
        System.out.println("這是private static方法");
    }
}
```

<!--
看範例，LearnJava 介面有四個方法。

default 方法 method2 呼叫了 private 方法 method4；static 方法 method3 則呼叫了 private static 方法 method5。

method4 和 method5 只在介面內部使用，外部看不到它們，這樣就能把輔助用的程式碼隱藏起來。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的繼承
# Interface Inheritance

<!--
接下來，我們看看介面之間怎麼繼承。
-->

---
layout: default
---

# 繼承的三種關係

| 對象 | 關係 | 語法 | 限制 |
| --- | --- | --- | --- |
| Class → Class | 繼承 | `extends` | 只能一個父類別 |
| Class → Interface | 實作 | `implements` | 可多個，逗號分隔 |
| Interface → Interface | 繼承 | `extends` | 可多個父介面 |

- 當子介面繼承父介面，實作子介面的類別必須**同時實作所有**子介面與父介面的抽象方法

<!--
類別和介面之間，有三種關係。

類別繼承類別，用 extends，只能有一個父類別。類別實作介面，用 implements，可以實作好幾個，用逗號分隔。介面繼承介面，也是用 extends，而且可以繼承好幾個父介面。

要注意，當子介面繼承了父介面，實作子介面的類別，就必須同時實作子介面和父介面的所有抽象方法。
-->

---

# 基本介面的繼承

```java
interface Animal { void showMe(); }
interface Bird extends Animal { void flying(); }
```

- `Bird` 繼承 `Animal`，實作 `Bird` 時需同時實作兩個方法

```java
class Eagle implements Bird {
    public void showMe() { System.out.println("我是老鷹"); }
    public void flying() { System.out.println("老鷹展翅飛翔"); }
}
```

<!--
我們看基本的介面繼承。

Animal 介面有一個 showMe 方法。Bird 介面繼承 Animal，再加上自己的 flying 方法。

所以 Eagle 實作 Bird 時，兩個方法都要實作：showMe 印出我是老鷹，flying 印出老鷹展翅飛翔。
-->

---

# 介面多重繼承 — 概念

Java 類別不支援多重繼承，但**介面支援**：

```java
interface B { void b(); }
interface C { void c(); }
class A implements B, C {
    public void b() { System.out.println("b方法"); }
    public void c() { System.out.println("c方法"); }
}
```

- 一個類別可實作**多個介面**（逗號分隔）
- 一個介面可繼承**多個介面**

<!--
Java 的類別不支援多重繼承，但介面支援。

看範例，類別 A 同時實作了 B 和 C 兩個介面，用逗號分隔，所以要實作 b 和 c 兩個方法。

一個類別可以實作多個介面，一個介面也可以繼承多個介面。這就是 Java 實現類似多重繼承效果的方式。
-->

---

# 介面繼承多個介面 — 範例

```java
interface Bird { void birdFly(); }
interface Airplane { void airplaneFly(); }
interface Fly extends Bird, Airplane {
    void pediaFly();
}
class InfoFly implements Fly {
    public void birdFly() { System.out.println("鳥翅飛翔"); }
    public void airplaneFly() { System.out.println("引擎飛翔"); }
    public void pediaFly() { System.out.println("百科飛翔"); }
}
```

<!--
這是介面繼承多個介面的例子。

Fly 介面同時繼承了 Bird 和 Airplane 兩個介面，自己再加上 pediaFly 方法。

所以實作 Fly 的 InfoFly 類別，總共要實作三個方法：鳥的 birdFly、飛機的 airplaneFly，以及 Fly 自己的 pediaFly。
-->

---

# 類別實作多個介面

若多個介面有相同方法名稱，**實作一次即可**：

```java
interface Bird { void flying(); }
interface Airplane { void flying(); }
class Fly implements Bird, Airplane {
    @Override
    public void flying() {
        System.out.println("飛翔中");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 實作多個介面時，須重新定義<b>所有父介面的抽象方法</b>
</div>

<!--
如果類別實作的多個介面裡，有名稱相同的抽象方法，只需要實作一次就好。

看範例，Bird 和 Airplane 都有 flying 方法。Fly 類別同時實作兩個介面，只寫一個 flying 方法，就同時滿足了兩個介面的要求。

因為抽象方法沒有內容，實作一次就不會有衝突。
-->

---

# 繼承與實作規則摘要

| 對象 | extends | implements |
| --- | --- | --- |
| 類別 (Class) | 只能繼承**一個**父類別 | 可實作**多個**介面（逗號分隔） |
| 介面 (Interface) | 可繼承**多個**父介面（逗號分隔） | 介面無法實作另一個介面 |

- 語法順序：`class A extends B implements C, D`
- 介面**無法 `new`** 直接建立物件

<!--
這張表整理了繼承和實作的規則。

類別只能繼承一個父類別，但可以實作多個介面。介面可以繼承多個父介面，但介面不能實作另一個介面。

如果類別同時要繼承和實作，順序是 extends 在前，implements 在後。另外再次提醒，介面不能用 new 直接建立物件。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階主題
# Advanced Topics

<!--
接下來是一些進階主題，主要是多個介面同時存在時，可能會遇到的衝突。
-->

---
layout: default
---

# 實作時成員變數名稱衝突

不同介面有相同名稱的成員變數，使用**介面名稱.成員變數**存取：

```java
interface B { int x = 5; }
interface C { int x = 8; }
class A implements B, C {
    public void run() {
        System.out.println(B.x); // 5
        System.out.println(C.x); // 8
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 因為介面成員變數是 <b>public static final</b>，可直接用介面名稱存取，不會產生衝突
</div>

<!--
如果實作的兩個介面，有同名的成員變數，要怎麼辦？

看範例，B 和 C 都有一個 x，分別是 5 和 8。在類別 A 裡，只要用介面名稱點 x，就能清楚指定要哪一個：B.x 是 5，C.x 是 8。

因為介面的成員變數都是 static 的，所以可以直接用介面名稱存取，不會產生衝突。
-->

---

# 類別重新定義 Default 方法

| 情況 | 解決方法 |
| --- | --- |
| 實作一個介面，想改變 Default 行為 | 直接 Override |
| 實作多個有**相同** Default 方法名稱的介面 | **必須 Override**，否則編譯錯誤 |
| 需要呼叫特定介面的 Default 方法 | `介面名稱.super.方法名稱()` |

<!--
default 方法因為有實際的內容，就可能產生衝突。

如果只實作一個介面，想改變 default 方法的行為，直接覆寫就好。但如果實作的多個介面，有相同名稱的 default 方法，類別就必須覆寫它，否則會編譯錯誤，因為編譯器不知道要用哪一個。

覆寫時，如果還是想呼叫某個介面的版本，可以用介面名稱點 super 點方法名稱。
-->

---

# 類別重新定義 Default 方法 — 範例

```java
interface Dog { default void running() { System.out.println("狗在跑"); } }
interface Cat { default void running() { System.out.println("貓在跑"); } }
class Pet implements Dog, Cat {
    @Override
    public void running() {
        Dog.super.running();
        Cat.super.running();
    }
}
```

<!--
看範例，Dog 和 Cat 介面都有 default 方法 running。

Pet 同時實作兩個介面，所以必須覆寫 running。在覆寫的方法裡，用 Dog.super.running 呼叫狗的版本，再用 Cat.super.running 呼叫貓的版本，所以會依序印出狗在跑、貓在跑。
-->

---

# 類別同時繼承類別與實作介面

語法：**`extends` 在前，`implements` 在後**

```java
// class 子類別 extends 父類別 implements 介面名稱
class Pet extends Horse implements Dog {
    @Override
    public void running() {
        System.out.println("寵物在跑");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 繼承類別（extends）必須排在實作介面（implements）之前，順序不能對調
</div>

<!--
類別也可以同時繼承一個類別，又實作介面。

語法是 extends 在前，implements 在後。看範例，Pet 繼承 Horse，同時實作 Dog 介面。

這個順序不能對調，寫成 implements 在前，就會編譯錯誤。
-->

---

# 繼承父類別與實作介面方法名稱衝突

當父類別與實作的介面有相同方法名稱，**父類別方法的優先順序較高**：

```java
interface Dog { default void running() { System.out.println("介面跑"); } }
class Horse { public void running() { System.out.println("馬在跑"); } }
class Pet extends Horse implements Dog { }
// Pet.running() → 執行 Horse 的 running()，父類別方法優先
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 優先順序：<b>子類別自訂 &gt; 繼承的父類別方法 &gt; 介面 Default 方法</b>
</div>

<!--
如果父類別和介面，有同名的方法呢？

看範例，Dog 介面有 default 方法 running，Horse 類別也有 running 方法。Pet 繼承 Horse 又實作 Dog，而且自己沒有覆寫 running。

這時候呼叫 Pet 的 running，會執行 Horse 的版本，印出馬在跑。因為「類別的方法優先於介面的 default 方法」。

所以優先順序是：子類別自己定義的方法最優先，其次是繼承的父類別方法，最後才是介面的 default 方法。
-->

---

# 多層次繼承中 Default 方法名稱相同

父子介面都有同名 Default 方法時，執行**子介面**的版本：

```java
interface Animal {
    default void running() { System.out.println("動物跑"); }
}
interface Dog extends Animal {
    default void running() { System.out.println("狗在跑"); }
}
class Pet implements Dog { }
// Pet.running() → 執行 Dog.running()（子介面覆蓋父介面）
```

<!--
如果是介面之間的多層繼承呢？

Animal 介面有 default 方法 running，Dog 介面繼承 Animal，也定義了自己的 running。Pet 實作 Dog。

這時候呼叫 Pet 的 running，會執行 Dog 的版本，印出狗在跑。規則是：越具體的子介面越優先，子介面的版本會蓋過父介面。
-->

---

# 鑽石 (Diamond) 問題

- 介面 B 與介面 C 同時繼承介面 A，三者都有同名的 Default 方法
- 類別 D 同時實作 B 與 C → 編譯器無法決定執行哪個版本，**必須強制 Override**

<div class="flex flex-col items-center my-3 text-xs font-mono gap-1">
  <div class="border border-blue-400 bg-blue-50 rounded px-4 py-1">
    interface A — <span class="text-blue-600">default</span> void running()
  </div>
  <div class="text-gray-400 text-base" style="display:flex; width:280px; justify-content:space-between;">
    <span>↙</span><span>↘</span>
  </div>
  <div class="flex gap-4">
    <div class="border border-green-500 bg-green-50 rounded px-3 py-1 text-center">
      interface B<br><span class="text-gray-500">extends A, override running()</span>
    </div>
    <div class="border border-green-500 bg-green-50 rounded px-3 py-1 text-center">
      interface C<br><span class="text-gray-500">extends A, override running()</span>
    </div>
  </div>
  <div class="text-gray-400 text-base" style="display:flex; width:280px; justify-content:space-between;">
    <span>↘</span><span>↙</span>
  </div>
  <div class="border border-red-400 bg-red-50 rounded px-4 py-1 text-red-700">
    class D implements B, C — ⚠️ 必須 Override
  </div>
</div>

<div class="p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 因繼承圖形的箭頭流向類似鑽石形狀，稱為<b>鑽石問題（Diamond Problem）</b>
</div>

<!--
這是一個有名的問題，叫做鑽石問題。

介面 B 和介面 C，都繼承了介面 A，而且各自覆寫了 A 的 default 方法 running。接著類別 D 同時實作 B 和 C。

這時候，D 的 running 應該執行 B 的版本，還是 C 的版本？編譯器無法決定，所以 D 必須自己覆寫 running。

因為整個繼承關係畫出來，像一個菱形的鑽石，所以叫做鑽石問題。
-->

---

# 鑽石問題 — 程式範例

```java
interface A { default void running() { System.out.println("跑A"); } }
interface B extends A { default void running() { System.out.println("跑B"); } }
interface C extends A { default void running() { System.out.println("跑C"); } }
class D implements B, C {
    @Override
    public void running() {
        B.super.running(); // 跑B
        C.super.running(); // 跑C
    }
}
```

<!--
看程式範例，A、B、C 三個介面都有 running，B 和 C 各自印出跑 B 和跑 C。

D 同時實作 B 和 C，所以必須覆寫 running。在方法裡，可以用 B.super.running 和 C.super.running，明確指定要呼叫哪一個版本，最後依序印出跑 B 和跑 C。
-->

---

# 密封介面 (Sealed Interfaces)

| 特性 | 說明 |
| --- | --- |
| 關鍵字 | `sealed` |
| 控制權 | 使用 `permits` 指定哪些類別或介面可以實作它 |

```java
public sealed interface Shape permits Circle, Rectangle {
    double area();
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>JDK 17 正式特性：</b> 讓開發者能精確限制介面的擴充權限，增強系統安全性。
</div>

<!--
和類別一樣，介面也可以用 sealed 密封。

在 interface 前面加上 sealed，再用 permits 指定哪些類別或介面可以實作它。看範例，Shape 介面只允許 Circle 和 Rectangle 實作。

這是 Java 17 的正式功能，讓我們可以精確地控制，誰能擴充這個介面。
-->

---

# 密封介面的實作規則

實作 `sealed` 介面的子類別必須明確宣告以下三種狀態之一：

| 狀態 | 說明 |
| --- | --- |
| `final` | 禁止再被繼承 |
| `sealed` | 繼續密封，並指定自己的子類別 |
| `non-sealed` | 解除密封，回歸傳統的開放繼承 |

```java
public final class Circle implements Shape { 
    public double area() { return 0; } 
}
public non-sealed class Rectangle implements Shape { 
    public double area() { return 0; } 
}
```

<!--
實作密封介面的類別，也必須宣告自己的狀態，三選一。

final 禁止再被繼承；sealed 繼續密封，並指定自己的子類別；non-sealed 則解除密封，回到一般的開放繼承。

看範例，Circle 是 final，Rectangle 是 non-sealed。
-->

---

# Records 實作介面 (Java 17+)

Records 是隱式 `final`，天然符合 Sealed Interface `permits` 的要求，且自動生成 constructor 與 accessor：

```java
sealed interface Shape permits Circle, Rectangle {
    double area();
}
record Circle(double radius) implements Shape {
    public double area() { return Math.PI * radius * radius; }
}
record Rectangle(double w, double h) implements Shape {
    public double area() { return w * h; }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 Record 比 <code>final class</code> 更簡潔：不需手動寫 constructor、getter、equals()、hashCode()、toString()
</div>

<!--
record 很適合用來實作密封介面。

因為 record 本身就是 final，天生符合密封介面的要求，而且它會自動產生建構子、存取方法、equals、hashCode 和 toString。

看範例，Circle 和 Rectangle 都是 record，實作了 Shape 的 area 方法，程式碼比一般的 final 類別簡潔很多。
-->

---

# 密封介面與 Switch 窮舉性

當介面是 `sealed` 時，`switch` 模式比對（Java 21 正式功能）可以檢查是否涵蓋所有可能的情況：

```java
public double getArea(Shape shape) {
    return switch (shape) {
        case Circle c    -> c.area();
        case Rectangle r -> r.area();
        // 不需要 default，因為 Shape 是 sealed 且已窮舉所有子類別
    };
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>優勢：</b> 漏掉實作類別時，編譯器會報錯，比傳統 <code>instanceof</code> 更安全。
</div>

<!--
密封介面搭配 switch 的模式比對，編譯器可以檢查是否涵蓋了所有的情況。

看範例，getArea 方法依照 shape 的實際型別計算面積。因為 Shape 是密封的，只有 Circle 和 Rectangle 兩種，所以 switch 不需要寫 default。

如果之後新增了一種形狀卻忘了處理，編譯器就會報錯，這比用一連串的 instanceof 判斷安全得多。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 課堂練習
# Practice

<!--
接下來，我們用一題練習，把介面和繼承結合起來。
-->

---
layout: default
---

# 練習：設計介面階層
### 任務說明

設計一套介面與類別，模擬「跑」的行為：
1. 建立介面 `MyRunnable`，定義行為方法 `run()`
2. 類別 `Human`、`Car` 都實作 `MyRunnable`，各自定義不同的跑法
3. `Person` 繼承 `Human`，並重新定義 `run()` 方法
4. 執行後，將三者（Human、Person、Car）的跑法各自列印出來

**預期輸出：**
```
人在路上跑
人在操場上跑
車在公路上跑
```

> ⚠️ **注意：** 不要直接命名為 `Runnable`：它會與 `java.lang.Runnable`（多執行緒用）撞名，並遮蔽同套件內所有程式看到的 `java.lang.Runnable`，所以這裡改用 `MyRunnable`。

<!--
這一題要設計一組介面和類別，模擬「跑」的行為。

首先建立介面 MyRunnable，定義 run 方法。接著讓 Human 和 Car 都實作 MyRunnable，各自寫出不同的跑法。然後讓 Person 繼承 Human，再覆寫 run。最後在 main 裡，印出三者各自的跑法。

注意不要把介面命名成 Runnable，因為 Java 已經有一個多執行緒用的 Runnable 介面，同名會造成混淆。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
-->

---
layout: default
---

# 練習：設計介面階層
### 解題提示

1. 定義介面 `MyRunnable`，加入抽象方法 `run()`
2. `Human` 與 `Car` 分別 `implements MyRunnable`，各自 Override `run()`
3. `Person extends Human`，Override `run()` 加上自己的行為
4. `main()` 中建立三個物件，分別呼叫各自的 `run()`

```java
interface MyRunnable {
    void run();
}
class Human implements MyRunnable {
    public void run() { System.out.println("人在路上跑"); }
}
```

<!--
我們來看提示。

先定義 MyRunnable 介面，加入抽象方法 run。Human 和 Car 分別實作 MyRunnable，各自覆寫 run，印出人在路上跑和車在公路上跑。

Person 繼承 Human，覆寫 run，改成印出人在操場上跑。最後在 main 裡建立三個物件，分別呼叫 run，就會得到預期的三行輸出。

這一題同時用到了實作介面和繼承類別，Person 透過 Human，也間接擁有了跑的能力。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了用介面定義行為契約。

介面描述「有某種能力」，類別用 implements 實作，一個類別可以實作多個介面。介面的變數都是常數。Java 8 加入了 default 和 static 方法，Java 9 加入了 private 方法。只有一個抽象方法的介面叫功能介面，是 Lambda 的基礎。

遇到名稱衝突時，類別的方法優先於介面，子介面優先於父介面；鑽石問題則必須自己覆寫。

【講師】開放 Q&A。
-->

---
layout: end
---

# 課程結束
### 介面定義行為契約，多重繼承讓設計更靈活

<!--
介面定義行為契約，多重實作讓設計更有彈性。

下一章，我們要認識包裝類別。我們下一章見。
-->

---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
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
【開場白】
今天我們學介面（Interface）。這是 Java 物件導向的第三個支柱，前兩個是繼承和抽象類別。

【為什麼要學這個？】
有些能力是「跨類別的」——鳥和飛機都能飛，但鳥是動物、飛機是機器，沒有繼承關係。介面讓你把「能飛」這個行為規範定義出來，讓不同類別都能宣稱自己具備這個能力。

【今天學完你會能做什麼】
學完之後你能設計靈活的類別體系，理解 Spring Boot 框架大量使用介面做依賴注入的方式，也能解釋 Functional Interface 和 Lambda 的關係。
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
【帶讀大綱】
今天內容很豐富。先認識介面的基本概念，然後學 Java 8、9 新增的功能，再探討介面的繼承和進階主題。

【重點預告】
Functional Interface 和 Lambda 是現代 Java 開發的核心，這章會打下基礎。Diamond 問題是介面常考的概念，也會仔細說明。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識介面
# Interface

<!--
【段落轉換】
先來了解介面是什麼、解決什麼問題，以及和上一章抽象類別的差異。
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
💡 <b>比較飛機與鳥：</b>兩者不是同一類，但都有「飛」的能力 → 用介面定義共同行為
</div>

<!--
【核心說明】
介面描述的是「具備某種能力」，不是「是某一種東西」。

【帶讀說明】
鳥是動物（IS-A），所以繼承 Animal。但鳥會飛、飛機也會飛，「飛」是一種行為能力，不是類別關係，所以用介面 Flyable 來表示。

【生活化比喻】
介面就像「職業技能證照」。你有「駕照」，表示你會開車；你有「英文檢定」，表示你英文過關。這些「能力」和你是誰（人類）是分開的事。

💼 業界實務：
Spring Boot 框架大量使用介面。比如 Repository 介面定義「資料存取能力」，不同的實作（JPA、MyBatis）都實作同一個介面，Controller 不管底層換什麼。
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
【帶讀語法】
interface 關鍵字定義介面，implements 讓類別實作介面。

【重點說明】
介面裡的方法預設是 public abstract，所以 void fly() 等同於 public abstract void fly()，可以省略那些關鍵字。

⚠️ 學生常見誤解：
implements 不是 extends！類別實作介面用 implements，類別繼承父類別用 extends，介面繼承介面用 extends。三種語法要記清楚。
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
        bird.fly();
    }
}
```

<!--
【帶讀程式碼】
實作規則三個要點：必須覆寫所有抽象方法、覆寫時必須是 public、建議加 @Override。

【帶讀程式碼】
Flyable bird = new Bird()——這裡用了 Upcasting！用介面型態接住實作類別的物件，和抽象類別的 Upcasting 一樣的概念。

💼 業界實務：
Spring Boot 的 Service 層通常是這樣：UserService 介面 + UserServiceImpl 實作類別，Controller 宣告 UserService 型態注入——完全看不到 Impl 的細節。
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
【帶讀表格】
這是抽象類別 vs 介面的對比，面試常考！

關鍵差異：
- 子類別只能 extends 一個抽象類別，但可以 implements 多個介面
- 抽象類別可以有普通方法（有實作），介面 Java 8 前只能有抽象方法
- 用途：抽象類別適合密切相關的類別，介面適合不相干類別的共同行為

💡 選擇準則：有共用屬性或共用實作方法 → 用抽象類別；只想定義行為規範 → 用介面。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的成員變數
# Interface Member Variables

<!--
【段落轉換】
介面可以有成員變數，但有特殊的預設修飾詞。
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
【帶讀表格】
介面的成員變數自動是 public static final，三個修飾詞缺一不可，含義是：
- public：所有人都能存取
- static：共享同一份（不是每個實作類別一份）
- final：常數，不能改值，宣告時必須給值

【帶讀程式碼】
double PI = 3.14159 等同於 public static final double PI = 3.14159。

⚠️ 學生常見誤解：
final 的變數一定要給初始值，不能之後再賦值。
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
【帶讀程式碼】
Circle 的 area() 方法直接用 PI 而不是 Shape.PI——因為它 implements Shape，可以直接存取。

【設計說明】
PI 定義在介面裡讓所有實作 Shape 的類別共用同一個常數，不需要每個類別各自宣告。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 8 新增介面內容
# Default & Static Methods

<!--
【段落轉換】
Java 8 是 Java 歷史上最重要的版本之一，介面獲得了兩個重要新功能：Default 方法和 Static 方法。
-->
---
layout: default
---

# Java 8 新增介面內容 — 概覽

| 功能 | Java 版本 | 說明 |
| --- | --- | --- |
| Constant variable | Java 7+ | 常數成員變數 |
| Abstract methods | Java 7+ | 抽象方法 |
| **Default methods** | **Java 8 新增** | 預設方法（有方法本體） |
| **Static methods** | **Java 8 新增** | 靜態方法 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>為什麼新增 Default 方法？</b>為維持向後相容性，舊介面可以新增方法，不破壞已有的實作類別
</div>

<!--
【帶讀表格】
Java 8 之前介面只能有常數和抽象方法。Java 8 加入 Default 和 Static 方法，讓介面的能力大幅擴展。

💡 為什麼要加 Default 方法？
假設你維護一個介面，全世界有 1000 個類別實作了它。你需要加一個新方法，但如果加了抽象方法，那 1000 個類別全部要修改。Default 方法解決了這個問題——加新方法但有預設實作，舊的實作類別不需要修改。
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
【帶讀程式碼】
default 關鍵字讓介面提供方法的預設實作。Vehicle 介面的 alarmOn() 和 alarmOff() 有預設實作，Car 直接繼承用，不需要 Override。

【核心說明】
但如果 Car 想要不同的防盜行為，可以 Override——這是可選的，不是強制的。
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
【帶讀程式碼】
Car 只實作了 getBrand()（唯一的抽象方法），alarmOn() 和 alarmOff() 繼承自介面的 default 實作，直接可以用。

💼 業界實務：
Java 標準庫的 List 介面在 Java 8 加入了很多 default 方法（如 sort()、forEach()），讓 ArrayList 這些舊的實作類別不需要修改就能用這些新功能。
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
【帶讀程式碼】
Static 方法直接定義在介面裡，用法和一般 static 方法一樣，但只能用「介面名稱.方法名稱()」呼叫。

⚠️ 學生常見誤解：
不能用物件呼叫！obj.add(3, 5) 是錯的，要用 MathUtils.add(3, 5)。而且子類別無法 Override static 方法（和一般類別的 static 方法一樣）。
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
【帶讀程式碼】
MathUtils.add(3, 5) 和 MathUtils.multiply(4, 6)，直接用介面名稱呼叫。

💼 業界實務：
工具方法（utility methods）放在介面的 static 方法裡，讓相關的常數和工具方法都集中在同一個介面，比分散在不同 Util 類別更內聚。
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
【核心說明】
Functional Interface（功能介面）是只有一個抽象方法的介面，是 Lambda 表達式的基礎。

【帶讀說明】
@FunctionalInterface 是可選的注解，加上去讓編譯器幫你確認——如果不小心加了第二個抽象方法，編譯就報錯。

💼 業界實務：
你在第 25 章學到的 Stream API 的 filter()、map()、forEach() 都接收 Functional Interface 作為參數，這就是 Lambda 能用的原因。
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
【帶讀表格】
四個最常用的內建功能介面：
- Predicate：判斷，回傳 boolean（適合 filter）
- Function：轉換，輸入 T 輸出 R（適合 map）
- Consumer：消費，輸入 T 不回傳（適合 forEach）
- Supplier：供給，不輸入回傳 T（適合延遲計算）

💡 記憶方法：Predicate 判斷、Function 轉換、Consumer 消費（用掉）、Supplier 供應（產生）。
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
【帶讀程式碼】
四個介面的 Lambda 使用範例：
- isLong：s -> s.length() > 5 判斷字串是否超過 5 個字元
- toLen：String::length 方法參考，把字串轉成長度整數
- print：System.out::println 直接印出
- hero：() -> "炭治郎" 不接收參數，回傳字串

⚠️ 學生常見誤解：
方法參考 String::length 等同於 s -> s.length()，只是更簡潔的寫法。
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
```

<!--
【帶讀表格】
進階功能介面：
- BiFunction：兩個不同型別的輸入，一個輸出（Bi = 二）
- UnaryOperator：輸入輸出相同型別（Unary = 一元）
- BinaryOperator：兩個相同型別輸入，一個相同型別輸出

【帶讀程式碼】
(s, n) -> s.repeat(n)：把字串重複 n 次。String::toUpperCase 方法參考轉大寫。

💼 業界實務：
這些介面在 Stream API 裡大量使用。當你寫 stream().map(s -> s.toUpperCase()) 時，map() 的參數就是 Function<String, String>。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 9 新增介面內容
# Private Methods

<!--
【段落轉換】
Java 9 繼續增強介面，加入了 private 方法，讓介面的內部邏輯可以被封裝和重用。
-->
---
layout: default
---

# Java 9 新增介面內容 — 概覽

| 功能 | Java 版本 | 說明 |
| --- | --- | --- |
| Constant variable | Java 7+ | 常數成員變數 |
| Abstract methods | Java 7+ | 抽象方法 |
| Default methods | Java 8+ | 預設方法 |
| Static methods | Java 8+ | 靜態方法 |
| **Private methods** | **Java 9 新增** | 私有方法 |
| **Private Static methods** | **Java 9 新增** | 私有靜態方法 |

<!--
【帶讀表格】
Java 9 的完整介面功能清單。Private 方法讓介面內部的程式碼可以被多個 default 方法重用，而不需要暴露給外部。
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
【帶讀表格】
Private 方法的規則：只能在介面內部用、不能是抽象的（必須有實作）。

設計目的：如果兩個 default 方法有共同邏輯，以前要重複寫，現在可以抽取到 private 方法裡。
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
【帶讀程式碼】
method2() 呼叫 method4()（private 方法），method3() 呼叫 method5()（private static 方法）。

⚠️ 規則提醒：
private static 方法可以被 static 和 non-static 的 default 方法呼叫。
private non-static 方法只能被 non-static 的 default 方法呼叫，不能在 static 方法裡用。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的繼承
# Interface Inheritance

<!--
【段落轉換】
介面之間也可以繼承，而且介面支援多重繼承——這是 Java 類別不能做到的事。
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
【帶讀表格】
三種繼承關係，注意語法：
類別繼承類別：extends（只能一個）
類別實作介面：implements（可以多個）
介面繼承介面：extends（可以多個）

【重要規則】
實作子介面的類別要實作「子介面 + 所有父介面」的抽象方法，一個都不能少。
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
【帶讀程式碼】
Bird extends Animal，所以 Eagle implements Bird 時要同時實作 showMe()（來自 Animal）和 flying()（來自 Bird）。

【類比說明】
就像你拿到「高級廚師證照」，但高級廚師包含了基礎廚師的所有技能，所以你要同時具備基礎廚師和高級廚師的所有能力。
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
【核心說明】
類別可以 implements 多個介面！這是 Java 類別不能多重繼承但介面可以的特殊能力。

【帶讀程式碼】
class A implements B, C 同時實作兩個介面，必須覆寫 b() 和 c() 兩個方法。

💼 業界實務：
一個 Service 類別可能同時 implements UserService、AdminService、AuditService——實作多個介面讓類別說明「我具備這些能力」。
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
【帶讀程式碼】
Fly 繼承了 Bird 和 Airplane 兩個介面，InfoFly 實作 Fly 就要實作三個方法：birdFly()、airplaneFly()、pediaFly()。

【層次清楚】
介面繼承可以建立「能力的層次體系」，Fly 代表「更完整的飛行能力」，包含了鳥的飛法和飛機的飛法。
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
【帶讀程式碼】
Bird 和 Airplane 都有 flying() 方法。Fly 同時 implements 兩個介面，但同名方法只需覆寫一次。

⚠️ 注意：
這裡沒有衝突是因為方法是抽象的。如果是 default 方法就會有衝突，需要特別處理（下面的進階主題會講）。
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
【帶讀表格】
重要規則摘要：
類別 extends 只能一個，implements 可以多個。
介面 extends 可以多個。
語法順序：先 extends 後 implements。

⚠️ 順序不能對調！class A implements B extends C 會編譯錯誤，一定要 class A extends C implements B。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階主題
# Advanced Topics

<!--
【段落轉換】
進階主題來了。實際開發中會遇到各種衝突情況，我們來看 Java 是怎麼解決的。
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
【帶讀程式碼】
B.x 是 5，C.x 是 8。名字衝突時用「介面名稱.變數名稱」明確指定要用哪個介面的。

【原因說明】
介面的成員變數是 static final，所以用介面名稱存取是天然的方式，不需要先創建物件。
-->
---

# 類別重新定義 Default 方法

| 情況 | 解決方法 |
| --- | --- |
| 實作一個介面，想改變 Default 行為 | 直接 Override |
| 實作多個有**相同** Default 方法名稱的介面 | **必須 Override**，否則編譯錯誤 |
| 需要呼叫特定介面的 Default 方法 | `介面名稱.super.方法名稱()` |

<!--
【帶讀表格】
Default 方法衝突的三種情況：
1. 只實作一個介面，想改行為：直接 Override。
2. 實作多個有相同 Default 方法的介面：必須 Override，否則編譯錯誤。
3. 想呼叫特定介面的 Default 方法：用「介面名稱.super.方法名稱()」。
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
【帶讀程式碼】
Pet implements Dog, Cat，兩個介面都有 running() default 方法，衝突！所以 Pet 必須 Override running()。

Override 裡用 Dog.super.running() 和 Cat.super.running() 分別呼叫兩個介面的版本，把兩個都執行一遍。

💡 這個語法 Dog.super.running() 是 Java 8 特有的，專門解決這種多介面衝突問題。
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
【帶讀程式碼】
語法：class Pet extends Horse implements Dog

extends 在前，implements 在後，順序固定。

💼 業界實務：
Spring Boot 的實體類別常常是繼承一個基底類別（如 BaseEntity 包含 id、createdAt）同時實作多個介面（如 Serializable）。
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
【核心說明】
當繼承的父類別和實作的介面有相同名稱的方法，父類別優先！

【優先順序】
子類別自訂 > 繼承的父類別方法 > 介面 Default 方法

【帶讀程式碼】
Pet 繼承 Horse，Horse 有 running()。Dog 介面也有 running() default 方法。
Pet.running() 呼叫的是 Horse 的版本，因為父類別優先於介面 Default 方法。
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
【核心說明】
父子介面都有同名 Default 方法時，子介面的版本覆蓋父介面。

【帶讀程式碼】
Dog extends Animal，兩者都有 running() default。Pet implements Dog，所以 Pet.running() 執行 Dog 的版本（子介面優先）。

【優先規則】
更具體（更下層）的介面優先。
-->
---

# 鑽石 (Diamond) 問題

- 介面 B 與介面 C 同時繼承介面 A
- 三個介面都有同名的 Default 方法
- 類別 D 同時實作介面 B 與介面 C
- → 編譯器無法決定執行哪個版本，**類別 D 必須強制 Override**

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 因繼承圖形的箭頭流向類似鑽石形狀，稱為<b>鑽石問題（Diamond Problem）</b>
</div>

<!--
【核心說明】
Diamond 問題（鑽石問題）：B 和 C 都繼承 A，D 同時 implements B 和 C。

【問題所在】
A、B、C 都有 running() default 方法。D 呼叫 running() 時，該用哪個版本？B 的還是 C 的？編譯器無法決定，所以強制要求 D 覆寫。

【類比說明】
你同時繼承了兩位師父的武功，兩個師父都有「出劍」這個招式但版本不同——你必須自己決定你的版本是什麼，不能讓系統猜。
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
【帶讀程式碼】
D 必須 Override running()，在裡面用 B.super.running() 和 C.super.running() 明確指定呼叫哪個版本。

⚠️ 如果 D 不 Override，直接編譯錯誤：B 和 C 都有 running() default 方法，編譯器不知道用哪個。
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
【核心說明】
介面也可以是 sealed，精確控制哪些類別可以實作它。

【帶讀程式碼】
sealed interface Shape permits Circle, Rectangle：只有 Circle 和 Rectangle 可以 implements Shape。

💼 業界實務：
和 sealed abstract class 一樣，適合設計「封閉的型別體系」，配合 switch Pattern Matching 讓編譯器幫你檢查完整性。
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
【帶讀表格】
實作 sealed 介面的子類別同樣要明確宣告 final、sealed 或 non-sealed 三者之一。

Circle 宣告 final：不能再被繼承。
Rectangle 宣告 non-sealed：開放繼承，回歸傳統。
-->
---

# Records 實作介面 (Java 16)

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
【核心說明】
Records 可以 implements 介面，而且 Records 隱含 final，天然符合 sealed 介面的要求。

【帶讀程式碼】
record Circle(double radius) implements Shape，一行宣告，自動有建構方法、accessor，還實作了 area()。比傳統 final class 省很多程式碼。

💼 業界實務：
Result 型別（成功/失敗）、事件類型這類「封閉集合」用 sealed interface + records 是現代 Java 的最佳實踐。
-->
---

# 密封介面與 Switch 窮舉性

當介面是 `sealed` 時，`switch` 可以檢查是否涵蓋所有可能的情況：

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
【帶讀程式碼】
switch(shape) 有兩個 case：Circle 和 Rectangle。因為 Shape 是 sealed 只有這兩種，所以不需要 default，編譯器保證所有情況都處理了。

💡 好處：
新增一個 permits 子類別時，所有相關 switch 都會報編譯錯誤，提醒你更新邏輯。這比傳統 instanceof 的 if-else 安全太多了。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 課堂練習
# Practice

<!--
【段落轉換】
來做練習，把今天學的介面概念實際應用一下。
-->
---
layout: default
---

# 練習：設計介面階層
### 任務說明

設計一套介面與類別，模擬「跑」的行為：
1. 建立介面 `Runnable`，定義行為方法 `run()`
2. 類別 `Human`、`Car` 都實作 `Runnable`，各自定義不同的跑法
3. `Person` 繼承 `Human`，並重新定義 `run()` 方法
4. 執行後，將三者（Human、Person、Car）的跑法各自列印出來

<!--
【出題前的鋪陳】
這個練習設計一個介面階層來模擬「跑」的行為，練習介面實作、類別繼承+介面實作的組合，還有 Override。

【問題引導】
Runnable 介面定義 run()，Human 和 Car 各自實作。Person extends Human 並重新定義 run()。
三個類別的跑法各不相同，這就是多形的體現。

【等待與觀察】
給大家 5 分鐘設計類別結構。
-->
---
layout: default
---

# 練習：設計介面階層
### 解題提示

1. 定義介面 `Runnable`，加入抽象方法 `run()`
2. `Human` 與 `Car` 分別 `implements Runnable`，各自 Override `run()`
3. `Person extends Human`，Override `run()` 加上自己的行為
4. `main()` 中建立三個物件，分別呼叫各自的 `run()`

```java
interface Runnable {
    void run();
}
class Human implements Runnable {
    public void run() { System.out.println("人在路上跑"); }
}
```

<!--
【帶讀解法】
Runnable 介面只有一個方法 run()。
Human.run() 印「人在路上跑」，Car.run() 印「車在路上跑」。
Person extends Human，Override run() 印「Person 在快跑」（或任何你喜歡的字串）。

可以用 Runnable 型態宣告這三個物件（Upcasting），然後呼叫 run() 觀察多形效果。

⚠️ 注意：
Java 標準庫有一個 java.lang.Runnable 介面（用在執行緒），和練習裡自訂的 Runnable 不同，別搞混了！
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天學了介面的完整體系：基本語法、成員變數、Java 8/9 新增功能、繼承、進階衝突處理、密封介面。

【核心帶走重點】
介面 = 行為契約。不同類別通過 implements 宣稱自己具備某種能力。
類別可以 implements 多個介面，但 extends 只能一個父類別——這是介面最大的優勢。

Q&A 時間，有任何問題請提出！
-->
---
layout: end
---

# 課程結束
### 介面定義行為契約，多重繼承讓設計更靈活

<!--
[依脈絡推斷]
本章結束。介面定義行為契約，多重繼承讓設計更靈活——這就是今天最重要的概念。
-->

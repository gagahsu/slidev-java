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

---
layout: default
---

# Outline

- **認識介面** — 概念、語法、與抽象類別的比較
- **介面的成員變數** — `public static final`
- **Java 8 新增介面內容** — Default 方法、Static 方法
- **Java 9 新增介面內容** — Private 方法
- **介面的繼承** — 基本繼承、多重繼承
- **進階主題** — 名稱衝突、Diamond 問題
- **課堂練習**

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識介面
# Interface

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

---

# 介面 vs 抽象類別

| 比較項目 | 抽象類別 | 介面 |
| --- | --- | --- |
| 父類別 | 只能繼承一個 | 可繼承多個介面 |
| 子類別 | 只能 `extends` 一個 | 可 `implements` 多個 |
| 方法 | 可包含具體方法 | Java 8 前只能抽象方法 |
| 用途 | 類別間的緊密關係 | 不同類別間的共同行為 |
| 適用 | Car → Benz、Audi（IS-A） | Bird 與 Airplane 都能飛（行為） |

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的成員變數
# Interface Member Variables

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 8 新增介面內容
# Default & Static Methods

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 9 新增介面內容
# Private Methods

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的繼承
# Interface Inheritance

---

# 繼承的三種關係

| 對象 | 關係 | 語法 | 限制 |
| --- | --- | --- | --- |
| Class → Class | 繼承 | `extends` | 只能一個父類別 |
| Class → Interface | 實作 | `implements` | 可多個，逗號分隔 |
| Interface → Interface | 繼承 | `extends` | 可多個父介面 |

- 當子介面繼承父介面，實作子介面的類別必須**同時實作所有**子介面與父介面的抽象方法

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面多重繼承
# Multiple Inheritance

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

---

# 繼承與實作規則摘要

| 對象 | extends | implements |
| --- | --- | --- |
| 類別 (Class) | 只能繼承**一個**父類別 | 可實作**多個**介面（逗號分隔） |
| 介面 (Interface) | 可繼承**多個**父介面（逗號分隔） | 介面無法實作另一個介面 |

- 語法順序：`class A extends B implements C, D`
- 介面**無法 `new`** 直接建立物件

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階主題
# Advanced Topics

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

---

# 類別重新定義 Default 方法

| 情況 | 解決方法 |
| --- | --- |
| 實作一個介面，想改變 Default 行為 | 直接 Override |
| 實作多個有**相同** Default 方法名稱的介面 | **必須 Override**，否則編譯錯誤 |
| 需要呼叫特定介面的 Default 方法 | `介面名稱.super.方法名稱()` |

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

---

# 鑽石 (Diamond) 問題

- 介面 B 與介面 C 同時繼承介面 A
- 三個介面都有同名的 Default 方法
- 類別 D 同時實作介面 B 與介面 C
- → 編譯器無法決定執行哪個版本，**類別 D 必須強制 Override**

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 因繼承圖形的箭頭流向類似鑽石形狀，稱為<b>鑽石問題（Diamond Problem）</b>
</div>

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 課堂練習
# Practice

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

---
layout: end
---

# 課程結束
### 介面定義行為契約，多重繼承讓設計更靈活

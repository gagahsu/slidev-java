---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: 抽象類別 (Abstract Class)
routeAlias: ch16
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
    抽象類別
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「定義骨架，交由子類別實作」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
今天要學「抽象類別」——一種「定義了骨架但不實作細節」的類別。聽起來很抽象，但其實非常實用。

【為什麼要學這個？】
在真實的程式設計裡，你常常需要「規定子類別一定要有某個方法」，但每個子類別的實作都不一樣。抽象類別就是這個規定的載體。

【今天學完你會能做什麼】
學完之後你能設計有「強制規範」的類別架構，也能讀懂業界常見的 Template Method Pattern 設計模式。
-->
---
layout: default
---

# Outline

- **抽象類別 (Abstract Class)**
- **抽象方法 (Abstract Method)**
- **觀念整理**
- **進階應用** — 建構方法、Upcasting、Sealed Classes、Template Method Pattern
- **抽象類別 vs 介面**
- **實作練習**

<!--
【帶讀大綱】
今天分五個部分：先認識抽象類別的語法，然後學抽象方法，整理重要規則，進入進階應用，最後比較抽象類別和介面的差異。

【重點預告】
抽象類別和介面的差異是面試常考題，今天會仔細比較。Template Method Pattern 是業界常用的設計模式，學完之後能大幅提升你的程式設計思維。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別
# Abstract Class

<!--
【段落轉換】
先來認識什麼是抽象類別，它解決了什麼問題。
-->
---
layout: default
---

# 什麼是抽象類別

- 使用關鍵字 `abstract` 宣告的類別稱為**抽象類別**
- 抽象觀念主要是**隱藏工作細節**，使用者只需知道如何使用
  - 例如 `+` 符號可以執行數值加法，也可以執行字串相加
  - 但不需要知道內部程式如何設計 `+` 號的功能
- 這個類別中可以有**抽象方法**（abstract method）和**實體方法**（method）

<!--
【核心說明】
abstract 類別有兩個特色：一是可以包含「沒有實作的方法」（抽象方法），二是「不能 new 出物件」。

【生活化比喻】
抽象類別就像一份「職位說明書」：它寫明了這個職位要做什麼（方法的簽名），但不規定你具體怎麼做（沒有方法主體）。每個實際擔任這個職位的人（子類別）自己決定怎麼做。

【類比延伸】
+號的例子很好：你知道 + 可以相加，但不需要知道 Java 內部怎麼實作整數加法和字串連接——這就是隱藏細節的概念。
-->
---

# 使用抽象類別的場合

- 有一個 `Shape` 類別包含計算繪製外型的 `draw()` 方法
  - `Circle` 和 `Rectangle` 繼承 `Shape`，各自重新定義外型繪製
- `Shape` 的存在讓整個程式定義更加完整，**本身不處理任何工作**
  - 真正的工作交由子類別完成
  - 這就是一個適合使用**抽象類別**（abstract class）的場合

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>核心概念：</b> 抽象類別是「模板」，子類別依照各自情境對此模板擴展和建構。
</div>

<!--
【帶讀場景】
Shape 類別本身不知道怎麼畫，Circle 畫圓，Rectangle 畫矩形。Shape 只是定義「這個家族的物件都要會 draw()」，但實作由子類別決定。

【核心概念】
抽象類別的存在是讓程式架構更完整、語意更清楚。Shape 類別告訴所有子類別：「你是一個形狀，你必須知道怎麼畫自己。」

💼 業界實務：
Spring Boot 的很多基礎類別都是抽象類別，例如 AbstractApplicationContext，定義了 Application 的生命週期骨架，子類別負責實作細節。
-->
---

# 抽象類別語法

- 在定義類別名稱的 `class` 左邊加上 `abstract` 關鍵字

```java
abstract class Shape {
    // 類別內容
}
```

- 抽象類別定義的方法（實際執行的部分）交由子類別重新定義
- 可以把抽象類別想成是一個**模板**，子類別依照各自情境擴展和建構

<!--
【帶讀語法】
只要在 class 前面加 abstract 關鍵字就是抽象類別。語法很簡單。

【重點提示】
抽象類別還是可以有普通的方法（有實作的方法），不是每個方法都要是抽象的。這點很多初學者會誤解。
-->
---

# 抽象類別不能實例化

- 抽象類別**不能使用 `new` 建立物件**（不能實例化）
- 繼承（實作）的子類別可以實例化

```java
abstract class Shape {
    public void draw() { }
}
// Shape shape = new Shape(); // 編譯錯誤！
Circle circle = new Circle(); // 子類別可以
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>錯誤訊息：</b> 'Shape' is abstract; cannot be instantiated
</div>

<!--
【核心說明】
抽象類別不能 new！這是最重要的規則之一。

【帶讀程式碼】
new Shape() 直接報編譯錯誤。因為 Shape 本身是「概念」，不是「具體的形狀」。你只能 new Circle() 或 new Rectangle()。

【生活化比喻】
你可以有一個「動物」的概念，但你不能在現實中建立一個「純粹的動物」——任何動物都是狗、貓、鳥之類的具體動物。
-->
---

# 抽象類別實作 — 骨架定義

- 繪製框架（`Shape`）：抽象類別，定義 `draw()` 方法骨架
- 各自實作（`Circle`、`Square`）：子類別各自 override `draw()`

```java
abstract class Shape {
    public void draw() { }        // 純定義，無實作內容
}
class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("繪製圓形！");
    }
}
```

<!--
【帶讀程式碼】
Shape 定義了 draw() 但方法主體是空的（只有 {} 沒有內容），Circle 繼承後 Override 成自己的版本。

⚠️ 這裡的 draw() 不是抽象方法，只是一個空的普通方法。下一部分會講到真正的抽象方法（用 abstract 關鍵字，連 {} 都沒有）。
-->
---

# 抽象類別實作 — 子類別範例

```java
class Square extends Shape {
    @Override
    public void draw() {
        System.out.println("繪製矩形！");
    }
}
// 使用方式：
// Circle cir = new Circle();  cir.draw();  → 繪製圓形！
// Square squ = new Square();  squ.draw();  → 繪製矩形！
```

<!--
【帶讀程式碼】
Square 也 Override draw()，輸出「繪製矩形！」。

【多形效果】
Shape 類別的 draw() 讓多形成為可能：可以宣告 Shape 型態的變數，裝 Circle 或 Square，呼叫 draw() 時各自執行自己的版本。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象方法
# Abstract Method

<!--
【段落轉換】
剛才的 Shape 的 draw() 是一般方法。現在來看「抽象方法」——連方法主體都沒有，強制要求子類別實作。
-->
---
layout: default
---

# 抽象方法的特性

| 特性 | 說明 |
| --- | --- |
| 沒有實體內容 | 無方法主體（no body） |
| 宣告以 `;` 結尾 | 不使用 `{}` 大括號 |
| 必須被子類別 override | 子類別**必須**重新定義，否則編譯錯誤 |
| 類別需宣告為 abstract | 含抽象方法的類別必須是抽象類別 |

<!--
【帶讀表格】
抽象方法四個特性：
沒有 {}（方法主體）——只有宣告，用分號結尾。
子類別必須 Override——不 Override 就報編譯錯誤。
含有抽象方法的類別必須宣告為 abstract。

【生活化比喻】
就像合約裡的「必填條款」：「承包方必須提供技術支援方案（細節自行規定）」。抽象方法就是這個必填條款，子類別必須填上。
-->
---

# 抽象方法的特性 — 範例

```java
abstract class Shape {
    public abstract void draw(); // 抽象方法（以 ; 結尾）
}
class Circle extends Shape {
    @Override
    public void draw() {         // 子類別重新定義
        System.out.println("繪製圓形！");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b> 子類別重新定義時，回傳值型態與參數個數、型態需與抽象方法一致，並建議加上 <code>@Override</code>。
</div>

<!--
【帶讀程式碼】
public abstract void draw(); 注意：沒有 {}，直接分號結束。

Circle 繼承後必須 Override draw()，否則編譯報錯。

⚠️ 學生常見誤解：
抽象方法和空方法的差別！
空方法：public void draw() {}——有 {}，只是裡面沒有程式碼，可以不 Override。
抽象方法：public abstract void draw();——沒有 {}，子類別必須 Override。
-->
---

# 子類別未重新定義抽象方法

- 若子類別**沒有重新定義抽象方法**，編譯時會出現錯誤
- 解法：將子類別也宣告為抽象類別，延遲到孫類別實作

```java
abstract class Car {
    abstract void run();     // 抽象方法
}
class Bmw extends Car {
    // 未 override run() → 編譯錯誤！
    // Class 'Bmw' must implement abstract method 'run()' in 'Car'
}
```

<!--
【帶讀程式碼】
Bmw 繼承 Car 但沒有 Override run()，編譯就報錯了。

⚠️ 這個錯誤訊息你以後會常看到，記住了：Class 'XXX' must implement abstract method 'YYY' in 'ZZZ'——意思是「你繼承了 ZZZ 但沒有實作 YYY 抽象方法」。

【解法】
如果你就是不想在這個子類別實作，可以把 Bmw 也宣告成 abstract，把責任再往下傳給 Bmw 的子類別。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 觀念整理
# Abstract Class & Method

<!--
【段落轉換】
來整理一下抽象類別的重要規則，確認大家沒有遺漏。
-->
---
layout: default
---

# 抽象類別與抽象方法 — 重要規則

| 規則 | 說明 |
| --- | --- |
| 抽象類別無法實例化 | 必須透過子類別建立物件 |
| 含抽象方法 → 必須宣告為 `abstract class` | 普通類別中不存在抽象方法 |
| 子類別必須 Override 所有抽象方法 | 否則子類別也必須宣告為 `abstract` |
| 抽象類別不一定要有抽象方法 | 可以只包含普通方法 |
| 抽象類別可以混用兩種方法 | 抽象方法 + 普通方法皆可存在 |

<!--
【帶讀表格】
五個規則，特別強調最後兩個容易被忽略的：
「抽象類別不一定要有抽象方法」：可以只是一個不能被 new 的普通類別。
「抽象類別可以混用兩種方法」：普通方法提供共用實作，抽象方法強制子類別實作。

【互動引導】
大家猜猜：如果一個類別有 abstract 方法，但忘記在類別宣告加 abstract，會怎樣？（答：編譯錯誤，系統會提醒你）
-->
---

# 抽象類別可以有兩種方法

- 抽象類別內**可以同時有**抽象方法和普通方法

```java
abstract class Car {
    abstract void run();      // 抽象方法
    void refuel() {           // 普通方法
        System.out.println("汽車加油");
    }
}
```

<!--
【帶讀程式碼】
Car 有兩種方法：run() 是抽象方法，子類別必須實作；refuel() 是普通方法，所有子類別共用。

【設計意圖】
run() 每台車跑法不同（電動車、燃油車），所以抽象化讓子類別自己實作。
refuel() 所有車加油的概念相同，抽象類別直接實作，子類別繼承就好。
-->
---

# 抽象類別兩種方法 — 子類別使用

```java
class Bmw extends Car {
    @Override
    public void run() {
        System.out.println("安全駕駛中 ...");
    }
}
// Bmw bmw = new Bmw();
// bmw.refuel();  → 汽車加油
// bmw.run();     → 安全駕駛中 ...
```

<!--
【帶讀程式碼】
Bmw 只需要 Override run()，refuel() 直接繼承父類別的版本。

bmw.refuel() 呼叫的是 Car 定義的版本，bmw.run() 呼叫的是 Bmw 自己的版本。

💼 業界實務：
這個模式到處都是！父類別提供「共用行為」，子類別提供「各自特有行為」。讓你不重複寫 refuel()，卻又能客製化 run()。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階應用
# Constructor & Upcasting

<!--
【段落轉換】
基礎概念搞定，來看進階應用。這部分包含幾個你在業界常會遇到的用法。
-->
---
layout: default
---

# 抽象類別的建構方法

- 設計 Java 程式時，可以將**建構方法**（constructor）或**屬性**（成員變數）的觀念應用在抽象類別
- 子類別建立物件時，會先執行**父類別（抽象類別）的建構方法**

```java
abstract class Car {
    abstract void run();
    Car() {
        System.out.println("有車子了");
    }
    void refuel() {
        System.out.println("汽車加油");
    }
}
```

<!--
【核心說明】
抽象類別雖然不能 new，但可以有建構方法。子類別 new 的時候，父類別建構方法會先執行。

【重點提醒】
這和之前學的「父類別建構方法先執行」完全一樣，只是父類別是抽象類別而已。
-->
---

# 抽象類別的建構方法 — 範例

```java
class Bmw extends Car {
    public void run() {
        System.out.println("安全駕駛中 ...");
    }
}
public static void main(String[] args) {
    Bmw bmw = new Bmw(); // 建立物件時先執行 Car()
    bmw.refuel();
    bmw.run();
}
// 輸出順序：有車子了 → 汽車加油 → 安全駕駛中 ...
```

<!--
【帶讀程式碼】
new Bmw() 時：先執行 Car() 印出「有車子了」，再執行 Bmw 的初始化。

輸出順序：有車子了 → 汽車加油 → 安全駕駛中。

⚠️ 學生常見誤解：
「抽象類別有建構方法但不能 new，這不矛盾嗎？」——建構方法是讓子類別透過 super() 呼叫的，不是讓外部 new 的。
-->
---

# 抽象類別的屬性宣告

| 概念 | 說明 |
| --- | --- |
| `protected` 屬性 | 子類別可直接存取，外部無法存取 |
| `super(參數)` | 子類別透過 `super()` 初始化父類別屬性 |

```java
abstract class Car {
    protected String brand;
    Car(String brand) { this.brand = brand; }
    abstract void run();
}
```

<!--
【帶讀表格】
和一般類別一樣，抽象類別可以有屬性，通常用 protected 讓子類別直接存取。子類別透過 super() 初始化父類別屬性。
-->
---

# 抽象類別屬性 — 子類別使用範例

```java
class Bmw extends Car {
    Bmw() { super("BMW"); }
    @Override
    public void run() {
        System.out.println(brand + " 行駛中");
    }
}
Car bmw = new Bmw();    // Upcasting
bmw.run();              // BMW 行駛中
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 子類別直接使用 <code>brand</code>，無需重複宣告，因為 <code>protected</code> 屬性可由子類別繼承
</div>

<!--
【帶讀程式碼】
Bmw 的建構方法呼叫 super("BMW") 初始化 brand，run() 裡直接用 brand 印出品牌名稱。

Car bmw = new Bmw()——這行用了 Upcasting！Car 是抽象類別型態，new Bmw() 是子類別物件。這是合法的。
-->
---

# 使用 Upcasting 宣告抽象類別的物件

- 抽象類別**無法實例化**，但可以用 **Upcasting（向上轉型）** 宣告物件
- 用父類別型態接住子類別 `new` 出來的物件

```java
Car bmw = new Bmw();    // Upcasting（合法）
// Car car = new Car(); // 編譯錯誤！
bmw.refuel();
bmw.run();
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>常見用法：</b> 許多 Java 程式設計師會使用 Upcasting 宣告抽象類別物件，由所宣告物件的參考是子類別，所以可以正常執行工作。
</div>

<!--
【核心說明】
抽象類別不能直接 new，但可以用 Upcasting 把子類別物件賦值給抽象類別型態的變數。

【帶讀程式碼】
Car bmw = new Bmw() 合法，Car car = new Car() 編譯錯誤。

💼 業界實務：
這個 Upcasting 用法非常常見！宣告抽象類別型態，實際存放不同子類別，就能用多形呼叫 run()，不管底層是 Bmw 還是 Audi。
-->
---

# 密封抽象類別 (Sealed Abstract Class)

Java 17 起，`sealed` 可搭配 `abstract` 一起使用，**精確限制**哪些類別可以繼承該抽象類別：

| 關鍵字 | 說明 |
| --- | --- |
| `sealed` | 宣告該類別為密封類別 |
| `permits` | 指定允許繼承的子類別清單 |

```java
// 限制只有 Circle 和 Square 可以繼承 Shape
public abstract sealed class Shape permits Circle, Square {
    public abstract double area();
}
```

<!--
【核心說明】
sealed + abstract 組合：既是抽象類別（不能 new），又是密封類別（限制哪些子類別可以繼承）。

【帶讀程式碼】
sealed abstract class Shape permits Circle, Square：只有 Circle 和 Square 可以繼承 Shape，而且兩者都必須實作 area() 方法。

💼 業界實務：
設計嚴格的類型體系時很有用，確保 Shape 家族的完整性，配合 switch Pattern Matching 可以讓編譯器幫你檢查是否處理了所有情況。
-->
---

# 密封子類別的修飾詞

繼承密封類別的子類別，**必須**使用以下修飾詞之一：

| 修飾詞 | 說明 |
| --- | --- |
| `final` | 終止繼承，不能再有子類別 |
| `sealed` | 繼續密封，需指定新的 `permits` |
| `non-sealed` | 解除限制，任何類別皆可繼承 |

```java
public final class Circle extends Shape { /*...*/ }
public non-sealed class Square extends Shape { /*...*/ }
```

<!--
【帶讀表格】
密封子類別的三種選擇和上一章相同：final（不再繼承）、sealed（繼續密封）、non-sealed（開放繼承）。

Circle 宣告 final 代表沒有人可以繼承 Circle；Square 宣告 non-sealed 代表任何人可以繼承 Square。
-->
---

# Template Method Pattern

抽象類別的經典設計模式：用 `final` 方法固定流程骨架，用 `abstract` 方法讓子類別填入細節：

| 方法角色 | 宣告方式 | 說明 |
| --- | --- | --- |
| 骨架方法 | `final` 普通方法 | 定義固定流程，子類別不可 Override |
| 可變步驟 | `abstract` 方法 | 子類別各自實作細節 |

```java
abstract class Game {
    abstract void start();    // 可變步驟
    abstract void end();      // 可變步驟
    final void play() { start(); end(); }  // 骨架固定
}
```

<!--
【核心說明】
Template Method Pattern（模板方法模式）是使用抽象類別的經典設計模式。

【核心概念】
定義一個「流程框架」用 final 方法固定住，流程裡的每個「步驟」用 abstract 方法讓子類別自己填入。

【帶讀程式碼】
Game 類別定義了 play() 流程（先 start 再 end），這個流程是 final 不能改。但 start() 和 end() 是 abstract，每種遊戲自己決定怎麼開始和結束。

💼 業界實務：
Spring 框架的 AbstractApplicationContext.refresh() 就是典型的 Template Method——流程固定，但每個步驟交由子類別實作。
-->
---

# Template Method Pattern — 子類別實作

```java
class Chess extends Game {
    @Override void start() { System.out.println("走棋"); }
    @Override void end()   { System.out.println("將軍"); }
}
class Soccer extends Game {
    @Override void start() { System.out.println("踢球"); }
    @Override void end()   { System.out.println("進球"); }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>new Chess().play()</code> → 走棋 → 將軍；新增遊戲只需新增子類別，骨架流程不需修改
</div>

<!--
【帶讀程式碼】
Chess 和 Soccer 各自實作 start() 和 end()。

new Chess().play() 執行：走棋 → 將軍
new Soccer().play() 執行：踢球 → 進球

【互動引導】
如果現在要加入一個籃球遊戲，只需要新增 Basketball extends Game，實作 start() 和 end()，不需要改 Game 類別——這就是「開放封閉原則」（Open/Closed Principle）的體現。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別 vs 介面
# Abstract Class vs Interface

<!--
【段落轉換】
抽象類別和介面（Interface）是 Java 中兩種「定義行為規範」的方式，很多學生會搞混它們。來仔細比較一下。
-->
---
layout: default
---

# 抽象類別與介面的比較

| 比較項目 | 抽象類別 Abstract Class | 介面 Interface |
| --- | --- | --- |
| 父類別/父介面繼承 | 只能繼承一個類別 | 能繼承多個介面（Java 實現多重繼承） |
| 子類別繼承/實作 | `extends` 一個抽象類別 | `implements` 多個介面 |
| 方法 | 可包含非抽象方法 | 只能是抽象方法（Java 8 以前） |
| 必定為 | 父類別 | 可視為抽象類別的特例 |

<!--
【帶讀表格】
關鍵差異：
繼承：子類別只能 extends 一個抽象類別，但可以 implements 多個介面。
方法：抽象類別可以有普通方法（有實作），Java 8 以前介面只能有抽象方法。

⚠️ 學生常見誤解：
「有普通方法就用抽象類別，沒有就用介面？」——Java 8 之後介面也能有 default 方法（有實作），所以這個分界不那麼清楚了，但抽象類別還是有「只能單繼承」和「可以有狀態（欄位）」的差異。
-->
---

# 介面的演進 (Java 8+)

Java 8 起，介面支援 `default` 與 `static` 方法（Java 9 加入 `private`），與抽象類別的差異縮小。但介面仍**無法儲存狀態**（無實例欄位），需要共用屬性時仍應使用抽象類別。

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 介面 <code>default</code>、<code>static</code>、<code>private</code> 方法的詳細用法，將在 Ch17 介紹。
</div>

<!--
【核心說明】
Java 8 之後介面可以有 default 方法（有實作），和抽象類別的差異縮小了。但還是有兩個關鍵差異：
1. 介面不能有實例欄位（不能存狀態）
2. 一個類別可以 implements 多個介面

💡 下一章 Ch17 會深入介紹介面，現在先有個印象。
-->
---

# 抽象類別與介面 — 相同點與應用

**相同點：**
- 兩者都**無法直接實體化**
- 子類別都必須實作已宣告之抽象方法（或繼續抽象）

**應用場景比較：**
- **抽象類別**：關係密切的類別中，如定義抽象類別 `Car`，子類別 `Benz` 及 `Audi` 繼承 `Car`
- **介面**：定義一些功能給不相干類別使用，如定義介面 `Fly`，子類別 `AirPlane` 及 `Bird` 實作 `Fly`

<!--
【帶讀說明】
相同點：兩者都不能直接 new，子類別都必須實作抽象方法。

選擇哪個？
如果是「密切相關的類別」，有共用屬性和部分共用方法 → 用抽象類別（Car/Benz/Audi）
如果是「不相干類別但有共同行為」 → 用介面（AirPlane 和 Bird 都能飛，但關係不密切）

💼 業界實務：
「有狀態用抽象類別，純行為規範用介面」是常見準則。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 實作練習

<!--
【段落轉換】
來做兩個練習鞏固今天的概念，練習設計抽象類別和子類別。
-->
---
layout: default
---

# 練習 1：Shape 面積與周長
### 任務說明

請設計一個**抽象類別 `Shape`**，包含計算面積（`area()`）和周長（`perimeter()`）的抽象方法，再設計 `Rectangle` 和 `Circle` 兩個子類別分別實作。

**預期輸出：**
```
矩形面積：6.0
矩形周長：10.0
圓面積：12.566370614359172
圓周長：12.566370614359172
```

<!--
【出題前的鋪陳】
練習 1：設計形狀計算器。這是抽象類別最經典的應用場景。

【問題引導】
Shape 抽象類別要有哪些抽象方法？Rectangle 和 Circle 各需要什麼屬性？面積和周長的公式是什麼？

【等待與觀察】
給大家 5 分鐘設計類別結構，想清楚 Shape、Rectangle、Circle 的關係。
-->
---

# 練習 1：解題提示
### 提示說明

1. 在 `Shape` 中宣告 `abstract double area();` 與 `abstract double perimeter();`
2. `Rectangle` 需 `height`、`width` 屬性，透過建構方法傳入（高 2，寬 3）
3. `Circle` 需 `r`（半徑）屬性，透過建構方法傳入（半徑 2）
4. 計算公式：
   - 矩形面積 = `height * width`，周長 = `2 * (height + width)`
   - 圓面積 = `Math.PI * r * r`，圓周長 = `2 * Math.PI * r`

<!--
【帶讀解法】
Shape 宣告 abstract double area() 和 abstract double perimeter()——注意回傳 double。

Rectangle 需要 height 和 width，透過建構方法傳入。
Circle 需要半徑 r，用 Math.PI 計算。

⚠️ 注意：
Math.PI 是 Java 內建常數，不需要 import，可以直接用。圓的面積和周長公式用 Math.PI * r * r 和 2 * Math.PI * r。
-->
---

# 練習 2：抽象數學計算器
### 任務說明

請設計一個**抽象類別 `MyMath`**，包含 `add()` 與 `mul()` 兩個帶參數的抽象方法，以及一個普通方法 `output()` 印出「我的計算器」。設計子類別 `MyTest` 重新定義這兩個抽象方法。

**預期輸出：**
```
我的計算器
加法結果：11
乘法結果：24
```

<!--
【出題前的鋪陳】
練習 2：設計抽象數學計算器。這個練習特別讓你練習「混合使用抽象方法和普通方法」，還有 Upcasting。

【問題引導】
output() 是普通方法，add() 和 mul() 是抽象方法。main 裡用 MyMath obj = new MyTest() 宣告（Upcasting）。
-->
---

# 練習 2：解題提示
### 提示說明

1. 在 `MyMath` 中宣告 `abstract int add(int n1, int n2);` 與 `abstract int mul(int n1, int n2);`
2. 普通方法 `void output()` 直接印出「我的計算器」，不需 override
3. 在 `MyTest` 中實作：`add()` 回傳 `n1 + n2`，`mul()` 回傳 `n1 * n2`
4. 在 `main` 中使用 Upcasting：`MyMath obj = new MyTest();`
5. 呼叫 `obj.output()`、`obj.add(3, 8)`、`obj.mul(3, 8)`

<!--
【帶讀解法】
MyMath：abstract int add(int n1, int n2) 和 abstract int mul(int n1, int n2)，output() 直接印出「我的計算器」。

MyTest：實作 add() 回傳 n1+n2，mul() 回傳 n1*n2。

main 裡用 MyMath obj = new MyTest()——Upcasting！obj 可以呼叫 MyMath 定義的所有方法。

💡 輸出順序：先 output() 印「我的計算器」，再印加法結果 11，最後乘法結果 24。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天學了抽象類別的完整體系，從基礎語法到 Template Method Pattern，以及和介面的比較。

【核心帶走重點】
抽象類別 = 不能 new 的類別 + 可以有抽象方法（強制子類別實作）+ 可以有普通方法（提供共用實作）。
Template Method Pattern 是把「流程框架」和「可變細節」分離的優雅設計。

Q&A 時間有問題請提出！
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

# 實戰：用抽象類別設計問卷題目

問卷有三種題型：單選、多選、簡答。每種題目都有「標題」和「必填」，但**驗證答案的方式各不同**——抽象類別是最自然的設計：

```java
abstract class AbstractQuestion {
    protected String title;    // 題目文字
    protected boolean required; // 是否必填

    AbstractQuestion(String title, boolean required) {
        this.title = title;
        this.required = required;
    }

    // 各題型自行決定怎麼驗證
    public abstract boolean validate(String answer);

    // 共用：必填且答案空白就不合法
    public boolean isBlankWhenRequired(String answer) {
        return required && (answer == null || answer.isBlank());
    }
}
```

---

# 實戰：三種題型子類別

```java
// 單選題：答案必須是選項之一
class SingleChoice extends AbstractQuestion {
    String[] options;
    SingleChoice(String title, boolean required, String... options) {
        super(title, required); this.options = options;
    }
    @Override
    public boolean validate(String answer) {
        if (isBlankWhenRequired(answer)) return false;
        for (String opt : options) if (opt.equals(answer)) return true;
        return false;
    }
}

// 多選題：分號分隔，每個答案都必須是合法選項
class MultiChoice extends AbstractQuestion {
    String[] options;
    MultiChoice(String title, boolean required, String... options) {
        super(title, required); this.options = options;
    }
    @Override
    public boolean validate(String answer) {
        if (isBlankWhenRequired(answer)) return false;
        Set<String> valid = Set.of(options);
        for (String a : answer.split(";"))
            if (!valid.contains(a.trim())) return false;
        return true;
    }
}

// 簡答題：必填時不得為空
class TextQuestion extends AbstractQuestion {
    TextQuestion(String title, boolean required) { super(title, required); }
    @Override
    public boolean validate(String answer) {
        return !isBlankWhenRequired(answer);
    }
}
```

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>動態問卷連結：</b>後台設定題目時就建立對應的子類別；前台送出時對每個 <code>AbstractQuestion</code> 呼叫 <code>validate()</code>，多形讓你不需要 if/else 判斷題型。
</div>

---
layout: end
---

# 課程結束
### 感謝聆聽，有問題請發問！

<!--
[依脈絡推斷]
本章結束。定義骨架、交由子類別實作——這就是抽象類別的精髓，記住帶走。
-->

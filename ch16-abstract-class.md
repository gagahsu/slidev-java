---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
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
大家好，這一章的主題是抽象類別。

上一章學繼承時，我們有一個 Animal 父類別。但仔細想想，世界上有「一隻動物」這種東西嗎？我們看到的永遠是狗、鳥、魚。Animal 只是一個概念，用來定義所有動物共同的樣子。

抽象類別就是用來表達這種「只定義骨架，細節交給子類別」的設計。學完這一章，我們就能設計出規則清楚、又容易擴充的類別架構。
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
這一章我們會先認識抽象類別和抽象方法，再整理它們的規則。

接著看進階的應用，包括抽象類別的建構子、向上轉型、密封抽象類別，以及經典的樣板方法設計模式。最後比較抽象類別和介面的差別，並用兩題練習收尾。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別
# Abstract Class

<!--
我們先來看什麼是抽象類別，以及什麼時候需要它。
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
用 abstract 關鍵字宣告的類別，就是抽象類別。

抽象的觀念，主要是隱藏工作的細節，讓使用者只需要知道怎麼用。就像加號，可以做數字相加，也可以做字串串接，我們只要會用，不需要知道它內部是怎麼實作的。

抽象類別裡，可以有還沒寫內容的抽象方法，也可以有一般的實體方法。
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
我們看一個適合使用抽象類別的情境。

有一個 Shape 類別，定義了繪製圖形的 draw 方法。Circle 和 Rectangle 繼承 Shape，各自寫出畫圓形和畫矩形的方式。

問題是，Shape 本身要怎麼畫？它不是任何具體的形狀，所以它本身不處理任何工作。它存在的意義，是讓整個程式的架構更完整，真正的工作交給子類別完成。

這就是抽象類別的核心概念：「它是一個模板，子類別依照各自的情境去擴充。」
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
宣告抽象類別的語法很簡單，在 class 前面加上 abstract 就好。

抽象類別裡定義的方法，實際執行的部分，交給子類別去重新定義。我們可以把它想成一份表格範本：欄位都設計好了，但內容要由每個使用的人自己填寫。
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
抽象類別有一個重要的特性：「不能用 new 建立物件。」

看範例，如果寫 new Shape，編譯器會直接報錯，告訴我們 Shape 是抽象的，不能被實例化。但它的子類別 Circle 就可以正常 new。

這很合理，因為「一個形狀」太抽象了，我們只能畫出圓形或矩形這些具體的形狀。
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
我們看實作的方式。

Shape 是抽象類別，定義了 draw 方法的骨架，但裡面沒有任何內容。Circle 繼承 Shape，覆寫 draw，印出繪製圓形。
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
Square 也一樣，繼承 Shape 之後，覆寫 draw，印出繪製矩形。

下面的使用方式，分別建立圓形和矩形的物件，呼叫各自的 draw，就會印出不同的結果。

不過這個版本有一個問題：Shape 的 draw 有一對空的大括號，如果子類別忘了覆寫，編譯器也不會提醒。這就需要接下來的抽象方法。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象方法
# Abstract Method

<!--
接下來是抽象方法，它可以強制子類別一定要實作某些功能。
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
抽象方法有幾個特性。

它沒有方法本體，宣告時直接用分號結尾，不寫大括號。子類別必須覆寫它，否則會編譯錯誤。另外，只要類別裡有抽象方法，這個類別就必須宣告成抽象類別。

我們可以把抽象方法想成一份合約：父類別規定「一定要有這個功能」，但怎麼做，由子類別決定。
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
💡 <b>注意：</b> 子類別重新定義時，參數個數、型態需與抽象方法一致，回傳型態需相同（或為其子型態），並建議加上 <code>@Override</code>。
</div>

<!--
看範例，Shape 裡的 draw 宣告成 abstract，後面直接接分號，沒有大括號。

Circle 繼承 Shape 時，就必須覆寫 draw，寫出畫圓形的內容。

要注意，子類別覆寫時，參數的數量和型別都要和抽象方法一致，回傳型別也要相同，或是它的子型別。也建議加上 @Override，讓編譯器幫忙檢查。
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
如果子類別沒有覆寫抽象方法，會怎麼樣？

看範例，Car 有一個抽象方法 run，Bmw 繼承 Car，卻沒有覆寫 run。這時候編譯器會報錯，告訴我們 Bmw 必須實作 Car 的抽象方法 run。

如果暫時不想實作，有另一個解法：把 Bmw 也宣告成抽象類別，把實作的責任延到下一層的子類別。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 觀念整理
# Abstract Class & Method

<!--
我們把抽象類別和抽象方法的規則，整理一次。
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
這張表整理了五條重要的規則。

第一，抽象類別不能實例化，必須透過子類別建立物件。第二，只要有抽象方法，類別就必須宣告成抽象類別，一般類別裡不能有抽象方法。第三，子類別必須覆寫所有的抽象方法，否則子類別自己也必須是抽象類別。

第四，反過來，抽象類別不一定要有抽象方法，可以只有一般方法。第五，抽象類別可以同時有抽象方法和一般方法。
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
我們看抽象方法和一般方法混合使用的例子。

Car 有一個抽象方法 run，因為每個品牌的車，開起來的方式不同。另外還有一個一般方法 refuel，印出汽車加油，因為所有的車加油的方式都一樣。

共同的行為寫在父類別，各自不同的行為留給子類別，這正是抽象類別最適合的用法。
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
Bmw 繼承 Car 之後，只需要覆寫 run 就好。

建立 Bmw 物件之後，呼叫 refuel，會執行繼承來的方法，印出汽車加油；呼叫 run，則執行 Bmw 自己的版本，印出安全駕駛中。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階應用
# Constructor & Upcasting

<!--
接下來看一些進階的應用，包括建構子和向上轉型。
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
抽象類別雖然不能 new，但它可以有建構子，也可以有屬性。

當子類別建立物件時，會先執行父類別，也就是抽象類別的建構子。看範例，Car 的建構子會印出「有車子了」。
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
Bmw 繼承 Car，在 main 裡建立 Bmw 物件，接著呼叫 refuel 和 run。

輸出的順序是：先印出「有車子了」，因為建立物件時，會先執行父類別的建構子；接著是汽車加油，最後是安全駕駛中。

所以抽象類別的建構子，雖然不能直接呼叫，但會在子類別建立物件時自動執行，適合用來初始化共同的屬性。
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
抽象類別也可以宣告屬性。

看範例，Car 有一個 protected 的 brand 屬性，子類別可以直接使用。Car 的建構子接收品牌名稱，並設定給 brand。

子類別則透過 super，把品牌名稱傳給父類別的建構子，完成初始化。
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
Bmw 的建構子呼叫 super BMW，把品牌設定好。run 方法裡，直接使用繼承來的 brand，印出 BMW 行駛中。

注意 main 裡的寫法：變數宣告成 Car，物件是 new Bmw，這就是向上轉型。呼叫 run，執行的是 Bmw 的版本。

Bmw 不需要再宣告一次 brand，因為 protected 的屬性可以被子類別直接使用。
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
💡 <b>常見用法：</b> 許多 Java 程式設計師會使用 Upcasting 宣告抽象類別型態的變數，由於變數參考的實際物件是子類別，所以可以正常執行工作。
</div>

<!--
抽象類別不能 new，但可以用它來宣告變數，再用向上轉型，接收子類別的物件。

看範例，Car bmw 等於 new Bmw 是合法的；但 new Car 就會編譯錯誤。透過 bmw 這個變數，可以呼叫 refuel 和 run，執行時實際跑的是 Bmw 的版本。

這是很常見的寫法。用抽象類別當作變數的型別，程式就不需要知道背後是哪一個品牌，之後要換成別的車，也只要改 new 的那一行。
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
從 Java 17 開始，sealed 也可以和 abstract 一起使用，精確限制哪些類別可以繼承這個抽象類別。

看範例，Shape 是密封的抽象類別，用 permits 指定只有 Circle 和 Square 可以繼承。它還定義了一個抽象方法 area，讓每種形狀各自計算面積。
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
和上一章學的一樣，繼承密封類別的子類別，必須明確宣告自己的狀態。

final 表示不能再被繼承；sealed 表示繼續密封，並指定新的允許清單；non-sealed 表示解除限制，任何類別都能繼承。

看範例，Circle 是 final，不會再有子類別；Square 是 non-sealed，其他類別可以再繼承它。
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
抽象類別有一個經典的設計模式，叫做樣板方法模式。

它的做法是：用一個 final 的方法，把整個流程的骨架固定下來；流程中可以變化的步驟，則寫成抽象方法，讓子類別各自實作。

看範例，Game 有 start 和 end 兩個抽象方法，代表遊戲的開始和結束。play 是 final 方法，固定先呼叫 start，再呼叫 end。因為是 final，子類別不能修改這個流程。
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
接著，每種遊戲只要實作自己的開始和結束。

Chess 的 start 是走棋，end 是將軍；Soccer 的 start 是踢球，end 是進球。

所以 new Chess 再呼叫 play，就會印出走棋、將軍。之後要新增任何一種遊戲，只要新增一個子類別，整個流程完全不需要修改。像煮泡麵的步驟永遠是燒水、放麵、加調味料，只是每家泡麵的調味料不同。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別 vs 介面
# Abstract Class vs Interface

<!--
最後，我們來比較抽象類別和介面。介面下一章會詳細介紹，這裡先看看它們的差別。
-->

---
layout: default
---

# 抽象類別與介面的比較

| 比較項目 | 抽象類別 Abstract Class | 介面 Interface |
| --- | --- | --- |
| 父類別/父介面繼承 | 只能繼承一個類別 | 能繼承多個介面（Java 實現多重繼承） |
| 子類別繼承/實作 | `extends` 一個抽象類別 | `implements` 多個介面 |
| 方法 | 可包含非抽象方法 | Java 8 以前只能是抽象方法；Java 8+ 可有 `default` / `static` 方法 |
| 欄位（狀態） | 可有實例欄位 | 只能有 `public static final` 常數 |
| 建構方法 | 可以有 | 不能有 |

<!--
這張表比較了兩者的差異。

一個類別只能繼承一個抽象類別，但可以實作多個介面，這是 Java 實現類似多重繼承的方式。抽象類別可以有一般方法；介面在 Java 8 以前只能有抽象方法，Java 8 之後才可以有 default 和 static 方法。

最大的差別在於狀態：抽象類別可以有實例欄位和建構子；介面不能有建構子，欄位也只能是常數。
-->

---

# 介面的演進 (Java 8+)

Java 8 起，介面支援 `default` 與 `static` 方法（Java 9 加入 `private`），與抽象類別的差異縮小。但介面仍**無法儲存狀態**（無實例欄位），需要共用屬性時仍應使用抽象類別。

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 介面 <code>default</code>、<code>static</code>、<code>private</code> 方法的詳細用法，將在 Ch17 介紹。
</div>

<!--
從 Java 8 開始，介面可以有 default 和 static 方法，Java 9 又加入了 private 方法，和抽象類別的差異變小了。

但介面還是無法儲存狀態，也就是不能有實例欄位。所以如果子類別需要共用屬性，例如剛才的 brand，還是應該使用抽象類別。

介面的這些方法，下一章會詳細介紹。
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
抽象類別和介面也有相同的地方：兩者都不能直接實例化，子類別也都必須實作宣告的抽象方法，或是繼續保持抽象。

那什麼時候用哪一個？抽象類別適合關係密切的類別，例如 Car 是抽象類別，Benz 和 Audi 都是車，所以繼承 Car。介面則適合定義一種「能力」，給彼此不相關的類別使用，例如飛行介面 Fly，飛機和鳥都會飛，但它們本身沒有任何關係。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 實作練習

<!--
接下來，我們用兩題練習，把抽象類別的觀念實際用一遍。
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
第一題，請設計一個抽象類別 Shape，包含計算面積的 area 和計算周長的 perimeter 兩個抽象方法。再設計 Rectangle 和 Circle 兩個子類別，分別實作這兩個方法。

預期的輸出是：矩形面積 6.0、周長 10.0；圓面積和圓周長，則都大約是 12.57。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
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
我們來看提示。

Shape 裡宣告兩個抽象方法，回傳型別都是 double。Rectangle 有高和寬兩個屬性，透過建構子傳入高 2、寬 3；Circle 有半徑屬性，傳入 2。

矩形的面積是高乘寬，周長是 2 乘以高加寬；圓面積是 Math.PI 乘以半徑的平方，圓周長是 2 乘以 Math.PI 乘以半徑。半徑剛好是 2，所以圓面積和圓周長的數字會一樣。
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
第二題，請設計一個抽象類別 MyMath，包含加法 add 和乘法 mul 兩個抽象方法，都接收兩個整數參數。另外再寫一個一般方法 output，印出「我的計算器」。

接著設計子類別 MyTest，實作這兩個抽象方法。預期會印出我的計算器、加法結果 11、乘法結果 24。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 10 分鐘實作。
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
我們來看提示。

MyMath 裡宣告 add 和 mul 兩個抽象方法，回傳 int。output 是一般方法，直接印出文字，子類別不需要覆寫。MyTest 裡實作 add 回傳兩數相加，mul 回傳兩數相乘。

在 main 裡，用向上轉型宣告 MyMath obj 等於 new MyTest，再呼叫 output、add 3 和 8，以及 mul 3 和 8，就會得到預期的結果。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了用抽象類別設計骨架。

抽象類別用 abstract 宣告，不能直接 new，但可以有建構子和屬性。抽象方法沒有本體，子類別一定要覆寫。抽象類別適合把共同的行為寫在父類別，把不同的細節交給子類別，樣板方法模式就是最經典的應用。

抽象類別適合關係密切的類別；要定義給不相關類別共用的能力時，就用介面。

【講師】開放 Q&A。
-->

---
layout: end
---

# 課程結束
### 感謝聆聽，有問題請發問！

<!--
下一章，我們就要正式學習介面。我們下一章見。
-->

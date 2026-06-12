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
【開場白】
歡迎來到抽象類別的世界！「抽象」聽起來很玄，其實它就是一個「沒做完的半成品」。想像主管跟我們說「我們要做一個系統」，但細節要怎麼做？主管不管，留給我們去想——這就是抽象的概念。

【為什麼要學這個？】
在實際開發中，我們常常需要扮演「定規則的人」：定義一個類別，要求「所有繼承我的類別都要會做某件事」，但具體怎麼做不用我們操心。抽象類別就是讓我們優雅地「定規則、不做事」的工具。

【學習目標】
學完這一章，我們就能設計出有架構感的程式，像個小小架構師一樣，幫團隊定下規範，讓子類別各自去填補細節。
-->
---
layout: default
---

# Outline

- **抽象類別 (Abstract Class)**
- **抽象方法 (Abstract Method)**
- **觀念整理**
- **進階應用** — 建構方法、Upcasting
- **抽象類別 vs 介面**
- **實作練習**

<!--
【帶讀大綱】
大綱在這裡：我們會先從「什麼是抽象類別」開始，再看連大括號都沒有的「抽象方法」。接著聊聊它和「介面」之間的差異，最後一起動手做兩個練習。

【重點預告】
特別提醒一下，「抽象類別 vs 介面」是很多人剛學 Java 時容易搞混的地方，也是面試常見的題目，這一章我們會把這個概念講清楚。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別
# Abstract Class

<!--
【段落轉換】
接下來，讓我們把「抽象」這個聽起來有點虛無的概念具象化，來看看 Abstract Class 到底是什麼。
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
【重點解說】
看到 `abstract` 這個關鍵字，我們就可以把它想成「這裡只負責定義規則，不負責動手做」。

【生活化比喻】
抽象類別有點像百貨公司的「櫃位招租計畫」：百貨公司（抽象類別）規定這個位置一定要賣吃的（定義方法），但具體是賣拉麵還是炸雞，由進駐廠商（子類別）自己決定。

又像我們平常用遙控器轉台，我們只需要知道按鈕在哪裡，不需要知道電視內部怎麼接收訊號——抽象類別就是幫我們把這些內部細節藏起來。

【業界實務】
這種「先定規則、細節留給實作端」的設計方式，在許多框架的核心類別裡都能看到，目的是讓程式之間的關係更有彈性。
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
【情境切入】
想像我們在寫一套繪圖軟體：如果直接定義一個 `Shape` 類別，我們其實不知道要怎麼畫它，因為「形狀」這個詞本身太抽象了，沒有具體的畫法。

【重點解說】
但如果我們定義 `Shape` 並規定「只要是形狀都要會 `draw()`」，那麼 `Circle` 就可以畫圓形、`Rectangle` 就可以畫矩形。`Shape` 就像是一份「家族規範」——它自己不做事，只負責規定子類別一定要做什麼。

【業界實務】
在許多框架裡，只要看到類別名稱開頭是 `Abstract`，通常就是框架設計者留給我們的「填空題」：規則已經訂好，細節由我們補上。
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
【範例目的】
這個範例示範抽象類別最基本的宣告語法。

【帶讀關鍵行】
語法很直覺：就是在 `class` 前面加上 `abstract`。可以把它想成在類別貼上一張「未完工」的標籤。

⚠️ 易錯點提醒：
雖然標示「未完工」，但抽象類別裡面還是可以放已經寫好的一般方法，這點和下一章會學到的介面（Interface）不太一樣。
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
【重點解說】
這點非常重要：如果我們對抽象類別下 `new`，Java 會直接給我們一個編譯錯誤。

【生活化比喻】
這就像我們去餐廳，不能跟服務生說「給我一份食物」，服務生一定會問「你要什麼食物？」，因為「食物」是抽象的概念，我們只能點具體的「排骨飯」或「牛肉麵」。抽象類別就是那個點不到的「食物」。

【帶讀關鍵行】
`new Shape()` 這行會編譯失敗，因為 `Shape` 只是一個概念；而 `new Circle()` 沒問題，因為 `Circle` 是具體的子類別。

⚠️ 易錯點提醒：
看到錯誤訊息 `'Shape' is abstract; cannot be instantiated` 時，代表我們不小心對抽象類別下了 `new`，檢查一下是不是該換成具體的子類別。
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
【範例目的】
這個範例示範用「空實作」的方式定義骨架方法。

【帶讀關鍵行】
`Shape` 裡的 `draw()` 有大括號 `{}`，但裡面什麼都沒寫，這就是「空實作」。`Circle` 繼承後再把實際的繪製內容補上。

⚠️ 易錯點提醒：
這樣寫雖然可以動，但無法「強制」子類別一定要實作 `draw()`。如果 `Circle` 忘了覆寫，它會直接執行父類別那個「什麼都不做」的版本，往往不是我們想要的結果——這也是為什麼我們接下來要學「抽象方法」。
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
【範例目的】
這個範例補上 `Square` 子類別，讓我們看到兩個子類別各自實作 `draw()` 的結果。

【帶讀關鍵行】
`Circle` 和 `Square` 都遵守 `Shape` 的規範，各自完成了自己的 `draw()`。

【預期結果】
```
cir.draw();  → 繪製圓形！
squ.draw();  → 繪製矩形！
```

【業界實務】
這就是多型的基礎：之後我們可以用一個 `Shape` 型態的容器同時裝圓形和方形，跑一個迴圈，每個物件就會自動執行自己的 `draw()`，不用一個個判斷型態。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象方法
# Abstract Method

<!--
【段落轉換】
剛才用「空實作」來定義骨架，其實不太可靠。接下來我們來看更明確的做法：抽象方法。
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
【重點解說】
抽象方法有幾個明確的規則：沒有方法主體（沒有大括號）、以分號結尾、子類別「必須」覆寫，否則編譯錯誤，而且只有抽象類別才能宣告抽象方法。

【生活化比喻】
這有點像我們和建築商簽合約，合約上寫「這裡要蓋一間廁所」，後面直接接句號——我們不需要教他怎麼拉水管，但他如果沒蓋，合約就算違約。

【業界實務】
這種「先定規格、強制實作」的做法，常用在框架或團隊共用的核心類別上，確保每個子類別都不會漏掉關鍵功能。
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
【範例目的】
這個範例示範抽象方法的正確宣告方式，以及子類別如何覆寫它。

【帶讀關鍵行】
`public abstract void draw();` 連大括號都省了，直接用分號結尾——這代表「我不管你怎麼畫，但你一定要實作出來」。`Circle` 覆寫時補上 `@Override` 與實際內容。

⚠️ 易錯點提醒：
`abstract` 和 `{}` 不能同時出現，有 `abstract` 就不能寫方法主體。覆寫時記得回傳值型態與參數要與抽象方法一致，並建議加上 `@Override`。
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
【情境切入】
如果 `Bmw` 繼承了 `Car`，但完全沒有實作 `run()`，會發生什麼事？

【範例目的】
這個範例示範子類別沒有覆寫抽象方法時，編譯器會怎麼提醒我們。

【帶讀關鍵行】
看到 `Class 'Bmw' must implement abstract method 'run()' in 'Car'` 這個錯誤訊息時，代表「債還沒還清」——`Car` 規定的義務，`Bmw` 還沒履行。

【解法】
如果暫時不想在 `Bmw` 裡實作，可以把 `Bmw` 也宣告為 `abstract`，把這個義務延到 `Bmw` 的子類別去實作，這個做法叫「延遲實作」。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 觀念整理
# Abstract Class & Method

<!--
【段落轉換】
講了這麼多規則，我們花一點時間整理一下，免得大家被 `abstract` 搞得頭昏。
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
【重點解說】
這張表整理了抽象類別與抽象方法的重要規則，特別注意最後兩條：抽象類別不一定要有抽象方法，可以只放普通方法。

【互動引導】
我們可以想一想：一個完全沒有抽象方法的抽象類別，有什麼用途？答案是——單純用來當作父類別，強迫別人一定要繼承才能使用它。
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
【範例目的】
這個範例示範抽象類別內同時混用抽象方法與普通方法。

【帶讀關鍵行】
`run()` 宣告為抽象方法，因為每種車輛的行駛方式不同；`refuel()` 是普通方法，因為「補充能量」的邏輯各車型大致相同。

【概念定義】
這就是「求同存異」：把大家共同的部分寫在父類別（`refuel()`），把各自不同的部分留給子類別客製化（`run()`）。
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
【範例目的】
這個範例示範子類別如何同時使用父類別的普通方法，以及自己覆寫的抽象方法。

【帶讀關鍵行】
`Bmw` 只需要實作 `run()`，`refuel()` 則直接繼承自 `Car`，不用重複寫一次。

【預期結果】
```
bmw.refuel();  → 汽車加油
bmw.run();     → 安全駕駛中 ...
```

【業界實務】
這種「共用邏輯寫一次、各自邏輯各自實作」的做法，就是程式碼重用的核心精神，也是我們喜歡用抽象類別的原因之一。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階應用
# Constructor & Upcasting

<!--
【段落轉換】
接下來要看兩個跟「繼承」結合在一起的概念：抽象類別的建構方法，以及 Upcasting。
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
【情境切入】
很多人以為「抽象類別不能 `new`，所以沒有建構方法」，但其實不是這樣。

【概念定義】
抽象類別當然可以有建構方法（constructor），只是它不是給外部直接 `new` 用的，而是在子類別被建立時自動被呼叫。

【生活化比喻】
這就像我們買新家（子類別）時，雖然不需要自己打地基（父類別建構方法），但地基一定要先打好，房子才蓋得起來。
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
【範例目的】
這個範例示範建立子類別物件時，父類別（抽象類別）的建構方法會先被執行。

【帶讀關鍵行】
雖然我們 `new` 的是 `Bmw`，但 `Car` 的建構方法會先執行，所以「有車子了」會最先印出來，這就是繼承的執行順序。

⚠️ 易錯點提醒：
如果父類別沒有無參數建構方法，子類別就要用 `super()` 手動呼叫對應的父類別建構方法，這跟一般繼承的規則完全相同。

【預期結果】
```
有車子了 → 汽車加油 → 安全駕駛中 ...
```
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
抽象類別也可以有自己的「私房錢」（屬性）。通常我們用 protected，這意思是：「這是我留給孩子們的，外人別碰。」
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
這裡展示了 super("BMW") 把品牌名字傳給老爸。然後在 run() 裡面直接拿來用。

💡 注意這行：Car bmw = new Bmw();
這就是 Upcasting。雖然 Car 是抽象的，但它還是可以用來當作變數的型態，去承接它的子類別。這在多形裡是至關重要的概念。
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
這招叫「指鹿為馬」...不對，是「指 BMW 為車」。

【帶讀程式碼】
雖然我們宣告變數型態是 Car，但實際跑的是 Bmw。這樣做的好處是，以後如果你想把 Bmw 換成 Audi，你的程式碼其他部分幾乎不用動。

💼 業界實務：
老鳥都喜歡寫 Car car = getCar(); 而不是 Bmw car = getBmw();。因為我們追求的是「彈性」，而不是死板板的型別。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別 vs 介面
# Abstract Class vs Interface

<!--
【段落轉換】
現在我們來解決那個讓無數學生想撞牆的問題：抽象類別跟介面到底差在哪？
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
這裡有一份清單。最簡單的記法：
抽象類別是「出生（IS-A）」，你只能有一個老爸。
介面是「證照（CAN-DO）」，你可以考一堆證照。

⚠️ 現代 Java 的模糊地帶：
Java 8 之後介面也能寫 default 方法了，這讓兩者的界線變得很模糊。但記住，抽象類別還是能存「狀態（變數）」，而介面不行。
-->
---

# 介面的演進 (Java 8+)

Java 8 起，介面支援 `default` 與 `static` 方法（Java 9 加入 `private`），與抽象類別的差異縮小。但介面仍**無法儲存狀態**（無實例欄位），需要共用屬性時仍應使用抽象類別。

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 介面 <code>default</code>、<code>static</code>、<code>private</code> 方法的詳細用法，將在 Ch17 介紹。
</div>

<!--
【核心說明】
別被 Java 8 給騙了，介面變強了沒錯，但它依然不是類別。

【核心概念】
介面就像是「外掛」，你可以掛一堆。而抽象類別是你的「核心」，你只能有一個。如果你需要存一些變數（例如：年齡、姓名），你還是得乖乖用抽象類別。

💡 下一章我們會專門講介面，現在先別糾結，先把抽象類別搞定。
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
這是一個很好的準則：
如果是「親兄弟」，大家都有同樣的基因（Car）→ 用抽象類別。
如果是「跨界合作」，一個是鳥一個是飛機，大家都想飛（Fly）→ 用介面。

💼 業界實務：
如果你看到有人用繼承來實作「飛行能力」，導致飛機繼承了鳥類，那這程式碼基本上已經沒救了。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 實作練習

<!--
【段落轉換】
好了，嘴砲時間結束，大家動動手吧！沒寫過程式碼的人是不配談架構的。
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
練習 1：形狀大師。這題如果你寫不出來，那你剛才那半小時可能是在冥想而不是在聽課。

【問題引導】
Shape 類別要怎麼寫？那兩個計算的方法要加什麼關鍵字？Circle 裡面要存什麼？（小提示：半徑）。

【等待與觀察】
大家寫的時候注意括號跟分號。如果你被編譯器噴了，記得回頭看看投影片。
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
解法在這裡。注意 area() 和 perimeter() 都要宣告成 abstract。

⚠️ 小細節：
記得用 double，不然你的圓面積可能會變成一個整數，然後你的數學老師就會想跟你談談。還有，Math.PI 是你的好朋友。
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
練習 2：我的計算器。這題是考你有沒有弄懂「混用方法」。

【問題引導】
output() 是普通方法喔，不要手癢去加 abstract。add() 跟 mul() 才是要讓子類別去頭大的。
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
重點在那行 MyMath obj = new MyTest()。這就是 Upcasting。雖然 obj 被宣告為 MyMath，但它執行的是 MyTest 裡的加法跟乘法。這就是多形的力量！
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天我們從「出一張嘴」的抽象類別，講到「制定規矩」的抽象方法，最後還學會了老鳥專用的「模板模式」。

【核心帶走重點】
1. 抽象類別不能 new。
2. 抽象方法沒有大括號。
3. 繼承抽象類別，要嘛還債（實作），要嘛繼續欠（宣告抽象）。
4. 模板模式讓你寫出有架構感的程式碼。

有問題嗎？沒問題的話我們就下課，回去好好抽象一下。
-->
---
layout: end
---

# 課程結束
### 感謝聆聽，有問題請發問！

<!--
[依脈絡推斷]
下課！記住：定義骨架、交由子類別實作——這就是抽象類別的精髓。如果你沒聽懂，那你一定是太「抽象」了。我們下一章「介面」見！
-->

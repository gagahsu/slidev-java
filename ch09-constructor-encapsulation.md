---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 物件建構與封裝
routeAlias: ch09
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
  <p style="color: #5eada0; font-size: 1rem; font-weight: 600; letter-spacing: 0.2em; text-transform: uppercase; margin-bottom: 1.2rem;">Java Programming Masterclass</p>
  <h1 style="color: #1a5c5c; font-size: 3.4rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">物件建構與封裝</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「建構子、封裝與 static：打造穩固的類別設計」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
嘿，各位未來的架構師！歡迎來到 Java 物件導向的「深水區」。

【為什麼要學這個？】
如果說上一章我們學會了怎麼開車，這一章就是要學怎麼「修車」跟「保險」。沒學好封裝，你的程式碼就像沒穿衣服在街上跑，誰都能摸，這在業界可是會出人命的（或者是丟了你的飯碗）。

【今天學完你會能做什麼】
學完這章，你會懂得怎麼安全地「包裝」你的程式碼，讓它既強壯又好維護，還能搞懂那個讓初學者頭很痛的 static 到底是何方神聖。
-->

---
layout: default
---

# Outline

- **9-1 建構方法（Constructor）**
  - 預設建構子 / 自定義建構子 / 多載 / this() / vs 一般方法
- **9-2 封裝（Encapsulation）**
  - 資料隱藏 / private + getter/setter / 存取修飾詞 / JavaBean 慣例
- **9-3 static 關鍵字**
  - 類別變數 / 靜態方法 / static 初始化區塊 / Singleton 設計模式
- **練習題**

<!--
【核心說明】
今天我們有三大任務：第一，學會怎麼蓋房子（Constructor）；第二，學會怎麼鎖門（Encapsulation）；第三，搞懂那個全村共用的公廁（static）。

【生活化比喻】
想像你在蓋一棟大樓。建構子就是你的地基和基本裝潢，封裝就是你的保全系統，而 static 就是那台大家都能搭的電梯。

💼 業界實務：
這三者是 Java 開發的基石。在寫 Spring Boot 或任何框架時，沒弄清楚這些，你寫出來的程式碼會像義大利麵一樣亂七八糟。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 建構方法 Constructor

<!--
【開場白】
首先，我們來聊聊「建構方法」，也就是大家常聽到的 Constructor。
-->

---
layout: default
---

# 什麼是建構方法？

建構方法（Constructor）是物件被 `new` 出來時，**自動執行**的特殊方法，用來設定物件的初始狀態。

| 特性 | 說明 |
| --- | --- |
| 名稱 | **必須與類別名稱相同** |
| 回傳型態 | **沒有回傳型態**（連 void 也不寫） |
| 呼叫時機 | 使用 `new` 建立物件時自動呼叫 |
| 可見性 | 可為 public / private / protected |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>建構子 vs 一般方法：</b>建構子不能有回傳型態、名稱必須同類別、且只能透過 new 觸發，不能直接呼叫。
</div>

<!--
【核心說明】
建構子就是物件的「出廠設定」。

【生活化比喻】
就像你買一支新手機，第一次開機時，系統會自動問你要選什麼語言、連哪個 Wi-Fi。這個自動跑出來的過程，就是建構子。

【程式世界怎麼用】
當你執行 `new Dog()` 的那一刻，Java 就會衝去找這支建構子。

⚠️ 學生常見誤解：
很多同學會習慣在建構子前面加個 `void`。記住，建構子是很有個性的，它不回傳任何東西，連 `void` 都不准寫！寫了它就變成一般的方法，而且還會被 Java 嘲笑。
-->

---

# 預設建構子（無參數）

若沒有定義任何建構子，Java 編譯器會自動產生一個**預設建構子**（無任何參數）。

```java
class Dog {
    String name;
    // Java 自動補上：Dog() {}
}

Dog d = new Dog(); // 可以用，因為有預設建構子
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>重要：</b>一旦你自己定義了任何建構子，Java 就<b>不再</b>自動補上預設建構子。此時若還需要無參數版本，必須明確宣告。
</div>

<!--
【核心說明】
Java 是很貼心的（有時候啦）。如果你懶得寫建構子，它會偷偷幫你塞一個什麼事都沒做的「預設建構子」。

【生活化比喻】
這就像去餐廳，你沒特別說要點什麼，服務生就預設給你一杯白開水。

⚠️ 學生常見誤解：
注意！這個貼心服務是有條件的。只要你自己動手寫了任何一個建構子，Java 就會覺得「喔，這傢伙長大了，不需要我操心了」，然後把那個預設的白開水收走。如果你這時候還想喝白開水（呼叫無參數建構子），你就得自己動手寫。
-->

---

# 自定義建構子（含參數）

自定義建構子讓物件在建立時就能帶入初始值，避免「先建立、再設值」的兩段式寫法。

```java
class Dog {
    String name;
    int age;

    Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

Dog d = new Dog("小黑", 3);
System.out.println(d.name); // 小黑
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>this.name：</b>當參數名稱與欄位名稱相同時，用 <code>this.欄位名</code> 明確指向「目前物件的欄位」，避免遮蔽（shadowing）問題。
</div>

<!--
【帶讀程式碼前的鋪陳】
如果你想要在物件一出生的時候就給它名字 and 年齡，而不是事後才在那邊改，那你就要用「自定義建構子」。

【逐步解說】
你看這行 `this.name = name;`。左邊的 `this.name` 是我們這隻狗的「臉」，右邊的 `name` 是外面傳進來的「化妝品」。這行就是把化妝品抹在臉上。

【類比說明】
就像你領養一隻狗，在手續辦完的那一刻，它就已經叫「小黑」了，不需要帶回家再改名。

💼 業界實務：
在業界，我們非常討厭那種 `new` 完之後還要呼叫一堆 `setter` 的程式碼。能在一開始就設定好的，就寫在建構子裡。
-->

---

# 建構子多載（Constructor Overloading）

同一個類別可以定義**多個建構子**，只要參數型態或數量不同即可。

| 建構子 | 說明 |
| --- | --- |
| `Box()` | 無參數，尺寸初始化為 0 |
| `Box(double len)` | 正方體（三邊相等） |
| `Box(double w, double h, double d)` | 完整指定三邊 |

```java
Box b1 = new Box();           // 尺寸全為 0
Box b2 = new Box(7);          // 7x7x7 正方體
Box b3 = new Box(10, 20, 15); // 指定長寬高
```

<!--
【核心說明】
「多載」聽起來很專業，其實就是「同名異義」。

【生活化比喻】
就像你去買飲料，你可以說「我要一杯紅茶」，也可以說「我要一杯紅茶，半糖、去冰」。雖然都叫紅茶，但參數不同，服務生（Java）會知道該給你哪一種。

【程式世界怎麼用】
你的 `Box` 類別可以有無參數版本、單一邊長版本、還有完整長寬高版本。Java 會根據你給的參數，聰明地幫你找到對應的那一支建構子。
-->

---

# 建構子多載 — 完整範例

```java
class Box {
    double w, h, d;

    Box() { w = 0; h = 0; d = 0; }

    Box(double len) { w = h = d = len; }

    Box(double w, double h, double d) {
        this.w = w; this.h = h; this.d = d;
    }

    double volume() { return w * h * d; }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>編譯器選擇：</b>呼叫時，編譯器根據傳入的引數數量與型態，自動選擇對應的建構子。
</div>

<!--
【帶讀程式碼前的鋪陳】
我們來看看這個完整的 `Box` 範例。這裡定義了三種蓋房子的方式。

【逐步解說】
- 第一個 `Box()` 是給懶人用的，出來就是個空盒子。
- 第二個 `Box(double len)` 是給正方形愛好者用的，三邊都一樣。
- 第三個就是完全訂製。

【類比說明】
就像你去買鞋子，你可以買預設尺寸（27號），也可以量身訂做。
-->

---

# this() — 呼叫另一個建構子

在建構子內以 `this(...)` 呼叫**同一類別**的另一個建構子，避免重複初始化邏輯。

| 規則 | 說明 |
| --- | --- |
| 位置 | `this()` 必須是建構子的**第一行** |
| 次數 | 每個建構子只能呼叫一次 |
| 遞迴 | 禁止循環呼叫（A 呼叫 B、B 再呼叫 A） |
| 用途 | 減少重複程式碼，確保初始化一致 |

<!--
【核心說明】
`this()` 是工程師懶惰的最高境界（這是讚美！）。它讓一個建構子可以去「拜託」另一個建構子幫忙做事。

【生活化比喻】
就像你在家想喝飲料，你自己不想動，就叫你弟弟去冰箱拿。你弟就是那個被你呼叫的建構子。

⚠️ 學生常見誤解：
這點非常重要！`this()` 必須放在建構子的**第一行**。如果你先做了別的事，比如印個 "Hello"，再叫 `this()`，Java 會覺得你很不尊重被呼叫的那位，直接噴報錯給你看。
-->

---

# this() — 範例

```java
class Temp {
    Temp() {
        this(5);                           // 呼叫 Temp(int)
        System.out.println("預設建構子");
    }
    Temp(int x) {
        this(5, 15);                       // 呼叫 Temp(int,int)
        System.out.println(x);
    }
    Temp(int x, int y) {
        System.out.println(x * y);         // 75
    }
}
// new Temp() 輸出：75 → 5 → 預設建構子
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>執行順序：</b>鏈式呼叫由最末端的建構子先執行，再逐層返回，如同堆疊展開。
</div>

<!--
【帶讀程式碼前的鋪陳】
這段程式碼有點像在玩大冒險，一個接一個。

【逐步解說】
當你 `new Temp()` 的時候：
1. 它先跳到 `Temp()`，發現裡面有一行 `this(5)`，立刻跳到 `Temp(int x)`。
2. 在 `Temp(int x)` 裡又發現 `this(5, 15)`，再跳到 `Temp(int x, int y)`。
3. 最後印出 75，然後一層一層跑回來。

【類比說明】
這就像是俄羅斯娃娃，最裡面的那個印完，外面的才會接著做。
-->

---

# 建構子 vs 一般方法

| 比較項目 | 建構子 | 一般方法 |
| --- | --- | --- |
| 名稱 | **必須與類別名稱相同** | 任意合法識別字 |
| 回傳型態 | **不能有**（連 void 也不寫） | 必須宣告（可為 void） |
| 呼叫方式 | `new` 關鍵字自動呼叫 | 用物件或類別名稱呼叫 |
| 用途 | 初始化物件狀態 | 定義物件行為 |
| 繼承 | **不被繼承** | 可被繼承與覆寫 |

<!--
【核心說明】
這張表是面試必考題！如果你之後去面試沒答出來，不要說是我教的。

【生活化比喻】
建構子是「出生證明」，一般方法是「才藝表演」。出生證明一生只能領一次，名字要跟父母（類別）一樣；才藝表演你想什麼時候表演、表演幾次都可以。

💼 業界實務：
記住，建構子是不會被繼承的。子類別雖然會呼叫父類別的建構子，但那不是繼承，那是「盡孝道」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 封裝 Encapsulation

<!--
【開場白】
接下來，我們要進入物件導向最核心的防禦機制：封裝。
-->

---
layout: default
---

# 封裝的概念

封裝（Encapsulation）是將**資料（欄位）隱藏**在類別內部，只開放受控制的存取管道，防止外部直接修改物件狀態。

| 核心概念 | 說明 |
| --- | --- |
| 資料隱藏 | 欄位宣告為 `private`，外部無法直接存取 |
| 存取管道 | 透過 `public` 的 getter / setter 方法 |
| 驗證邏輯 | setter 內可加入條件判斷，防止非法值 |
| 不可變性 | 只提供 getter 而不提供 setter，實現唯讀欄位 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>封裝的好處：</b>類別的內部實作可以改變，只要公開介面不變，外部程式碼就不需要修改。
</div>

<!--
【核心說明】
封裝就是「把細節藏起來，把簡單留給別人」。

【生活化比喻】
就像電視遙控器。你只需要知道按「音量加」或「選台」，你不需要知道遙控器裡面電路是怎麼跑的。而且廠商也不准你拿鐵絲進去亂戳電路板，這就是封裝。

【程式世界怎麼用】
我們把變數設成 `private`，就像是幫變數穿上防彈衣。想看或想改？請走大門（Getter/Setter）。

💼 業界實務：
不要覺得這是多此一舉。如果你把變數設為 `public`，同事可能會在程式碼的任何角落隨便改你的資料，到時候出 bug 你就只能肝到天亮了。
-->

---

# private 欄位 + public getter/setter

```java
class BankAccount {
    private double balance; // 外部不能直接存取

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) balance += amount; // 加入驗證
    }
}
```

```java
BankAccount acc = new BankAccount();
acc.deposit(1000);
System.out.println(acc.getBalance()); // 1000.0
// acc.balance = -999; // ❌ 編譯錯誤！private 欄位
```

<!--
【帶讀程式碼前的鋪陳】
我們來看一個銀行的例子。這最能體現為什麼不能讓人隨便改資料。

【逐步解說】
你看，餘額 `balance` 是 `private`。如果你想把餘額改成負一百萬（`acc.balance = -999999`），Java 編譯器會直接叫你滾。你只能透過 `deposit` 存錢，而且我們還可以在裡面檢查金額是不是負的。

【類比說明】
這就像去銀行櫃檯，你不能自己跳進櫃檯去拿錢，你得請行員幫你處理。行員就是那個 `setter`。

⚠️ 學生常見誤解：
很多同學會覺得「這好麻煩，為什麼不直接給他改？」。等你工作後，看到別人的 code 亂改你的資料導致系統崩潰，你就會感謝封裝了。
-->

---

# 存取修飾詞：四種層級

| 修飾詞 | 關鍵字 | 存取範圍 |
| --- | --- | --- |
| `public` | `public` | 所有地方皆可存取 |
| `protected` | `protected` | 同套件 + 子類別 |
| 無修飾（package-private） | 無 | 同套件內 |
| `private` | `private` | 同一個類別內 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>原則：</b>盡量使用最嚴格的存取層級。通常欄位宣告為 <code>private</code>，方法依需求選擇。
</div>

<!--
【核心說明】
Java 提供了四種「保全等級」。

【生活化比喻】
- `public`：公廁，誰都能進。
- `private`：你的個人手機密碼，只有你知道。
- `protected`：家裡的傳家寶，只有家裡人和兒子可以用。
- `無修飾`：這棟公寓的公共設施，住在這棟樓的人才能用。

💼 業界實務：
在業界，我們有個原則叫「最小權限原則」。預設就是先設 `private`。如果不夠用，再慢慢往上放寬。千萬不要一上來就 `public` 大放送。
-->

---

# 存取修飾詞比較表

| 修飾詞 | 同類別 | 同套件 | 子類別（不同套件） | 外部 |
| --- | :---: | :---: | :---: | :---: |
| `public` | ✔ | ✔ | ✔ | ✔ |
| `protected` | ✔ | ✔ | ✔ | ✗ |
| 無修飾 | ✔ | ✔ | ✗ | ✗ |
| `private` | ✔ | ✗ | ✗ | ✗ |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記憶訣：</b>限制由鬆到嚴為 public → protected → 無修飾 → private。對欄位預設選 <code>private</code>，除非有特定理由才放寬。
</div>

<!--
【核心說明】
這張表是你們的保鮮膜，啊不對，是保命符。

【逐步帶著看】
你看那個 `private`，除了自己誰都不給進，超級邊緣。而 `public` 就像是開發者界的「海王」，誰都能跟他打成一片。

💼 業界實務：
大部分時候，你的變數（Field）應該都在 `private` 那一欄，而你的方法（Method）會分佈在 `public`。
-->

---

# JavaBean 慣例

JavaBean 是一種以封裝為基礎的類別設計慣例，廣泛被框架（Spring、Hibernate）採用。

| 規則 | 說明 | 範例 |
| --- | --- | --- |
| 欄位 | 全為 `private` | `private String name;` |
| Getter | `getXxx()` | `getName()` |
| Setter | `setXxx(value)` | `setName(String n)` |
| 布林 Getter | `isXxx()` | `isActive()` |
| 無參數建構子 | 必須有 | `public Person() {}` |

<!--
【核心說明】
JavaBean 不是什麼新的程式語言，它只是一種「潛規則」。

【為什麼要學這個？】
因為很多現代的 Java 框架（像 Spring Boot）都很懶。如果你不按照 `getName`、`setName` 這種方式取名，框架會找不到你的資料，然後在那邊報錯，你還會以為是框架壞了。

⚠️ 學生常見誤解：
注意布林值喔！我們不叫 `getActive()`，而是叫 `isActive()`。這就像問「你是活的嗎？」而不是「給我你的活」。
-->

---

# JavaBean 慣例 — 範例

```java
public class Person {
    private String name;
    private boolean active;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) {
        this.active = active;
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>isXxx vs getXxx：</b>布林型態的 getter 使用 <code>isXxx()</code> 而非 <code>getXxx()</code>，這是 JavaBean 規範的強制要求，IDE 與框架都依此自動識別。
</div>

<!--
【帶讀程式碼前的鋪陳】
這就是一個標準的、教科書等級的 JavaBean。

【逐步解說】
- 欄位全部鎖死（`private`）。
- 每個人都有專屬的對外窗口（Getter/Setter）。
- 布林值的 `isActive` 跟別人長得不一樣。

💼 業界實務：
在實際工作中，我們通常不會手寫這些 getter/setter。我們會用一個叫「Lombok」的工具，只要寫個 `@Data` 就搞定了。但在那之前，你得先學會怎麼手寫，不然工具壞了你連修都不會修。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# static 關鍵字

<!--
【開場白】
最後，我們要來聊聊那個讓很多新手感到困惑的：`static`。
-->

---
layout: default
---

# 類別變數 vs 實體變數

`static` 欄位（類別變數）屬於**類別本身**，所有物件共用同一份資料。

| 比較項目 | 實體變數（instance field） | 類別變數（static field） |
| --- | --- | --- |
| 宣告方式 | `int age;` | `static int count;` |
| 儲存位置 | 每個物件各自一份 | 全類別共用一份 |
| 存取方式 | 透過物件 `obj.age` | 透過類別 `Dog.count` |
| 生命週期 | 物件存在期間 | 類別載入後持續存在 |

<!--
【核心說明】
`static` 的意思就是「全村共有」。

【生活化比喻】
- **實體變數 (Instance Variable)**：就像每個人的手機螢幕，我換我的桌布，你家螢幕不會變。
- **類別變數 (Static Variable)**：就像班上的時鐘。不管是誰去調快五分鐘，全班看到的時間都會變快五分鐘。

⚠️ 學生常見誤解：
很多同學會在不用共用的地方亂加 `static`。如果我把 `name` 設為 `static`，那只要有一個人改名，全班都會被迫跟他叫一樣的名字。這會引發大屠殺的！
-->

---

# 類別變數 — 範例

```java
class Dog {
    static int count = 0; // 所有 Dog 物件共用
    String name;

    Dog(String name) {
        this.name = name;
        count++;          // 每次建立物件就累加
    }
}
```

```java
Dog d1 = new Dog("小黑");
Dog d2 = new Dog("小白");
System.out.println(Dog.count); // 2
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>存取慣例：</b>靜態欄位建議用 <code>類別名稱.欄位名</code> 存取，而非用物件去點，可讓閱讀者一眼看出這是靜態成員。
</div>

<!--
【帶讀程式碼前的鋪陳】
這是一個經典的應用：計算到底生出了幾隻狗。

【逐步解說】
你看，每次執行 `new Dog()`，建構子就會被跑一次。因為 `count` 是 `static` 的，所以它會在同一個數字上累加。最後不管你問哪隻狗，它們都會告訴你「全村現在有兩隻狗」。

💼 業界實務：
雖然你可以寫 `d1.count`，但這會讓看程式碼的人很困惑。請務必使用 `Dog.count`，讓大家一眼就看出：「喔！這是全村共用的變數」。
-->

---

# static 方法

靜態方法**不依賴物件**，直接透過類別名稱呼叫，且不能存取實體欄位。

| 特性 | 說明 |
| --- | --- |
| 呼叫方式 | `類別名稱.方法名()` |
| 存取限制 | **不能**存取 instance fields / instance methods |
| 可存取 | static 欄位、其他 static 方法、參數 |
| 典型例子 | `Math.abs()`、`Math.max()`、`Integer.parseInt()` |

```java
// Math 類別全部是靜態方法
System.out.println(Math.abs(-5));   // 5
System.out.println(Math.max(3, 7)); // 7
```

<!--
【核心說明】
靜態方法就是「不需要先買手機就能用的公共電話」。

【生活化比喻】
你想算絕對值，不需要先 `new` 一個 `Math` 物件吧？這就像你想知道現在幾點，不需要先買一個時鐘廠，只要抬頭看牆上的鐘就好。

⚠️ 學生常見誤解：
這是一個超級大重點：靜態方法裡**不能**用 `this`！為什麼？因為你是全村共用的公共設施，你叫 `this` 是指哪位？Java 會直接回你：「先生，你哪位？」。
-->

---

# static 方法 — 自訂範例

```java
class MathUtils {
    static int add(int a, int b) {
        return a + b;
    }

    static double circleArea(double r) {
        return Math.PI * r * r;
    }
}
```

```java
System.out.println(MathUtils.add(3, 4));          // 7
System.out.println(MathUtils.circleArea(5));       // 78.53...
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>工具類別慣例：</b>不需要物件狀態的輔助功能（如計算、格式轉換）適合設計為 static 方法，避免浪費物件建立成本。
</div>

<!--
【帶讀程式碼前的鋪陳】
我們通常會把一些輔助性質的小工具放在這種 `Utils` 類別裡。

【逐步解說】
你看 `add` 或 `circleArea`。它們只需要你給它們參數，它們就吐給你答案。它們不需要知道你的物件現在是什麼顏色、叫什麼名字。

💼 業界實務：
這種不帶狀態的方法，設成 `static` 效能會比較好，因為不需要在那邊 `new` 物件浪費記憶體。
-->

---

# static 初始化區塊

`static { }` 區塊在**類別第一次被載入時執行一次**，用於複雜的靜態變數初始化。

| 特性 | 說明 |
| --- | --- |
| 執行時機 | 類別載入時，早於任何建構子 |
| 執行次數 | **只執行一次** |
| 允許數量 | 一個類別可有多個，按順序執行 |
| 典型用途 | 初始化靜態集合、載入設定檔 |

```java
class Config {
    static int MAX_SIZE;
    static {
        MAX_SIZE = 100;
        System.out.println("Config 類別已載入");
    }
}
```

<!--
【核心說明】
這就像是類別的「開機畫面」。

【生活化比喻】
想像一間教室。每天早上第一個進去的人要把燈打開、冷氣開啟。後面進來的人就不需要再做這些事了。這個「第一個進來的人做的事」，就是 `static` 區塊。

【程式世界怎麼用】
有些變數的初始化很複雜，不是一行能寫完的（比如要讀檔案），這時候就適合寫在 `static` 區塊裡。
-->

---

# Singleton 設計模式簡介

Singleton 保證全程式只有**一個**物件實體，使用 `static` + `private 建構子` 實作。

| 元件 | 說明 |
| --- | --- |
| `private` 建構子 | 禁止外部 `new` 出物件 |
| `private static` 欄位 | 儲存唯一實體 |
| `public static` 方法 | 提供取得實體的唯一入口 |

```java
public class Singleton {
    private static Singleton instance = null;
    private Singleton() {}         // 禁止外部建立
    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
```

<!--
【核心說明】
Singleton 就是「全程式唯一的真愛」。

【生活化比喻】
就像全台灣只有一個總統。你不能自己去 `new` 一個總統出來。如果你想要跟總統說話，你得透過特定的管道（`getInstance`）去找到那位唯一的實體。

【程式世界怎麼用】
這是一個結合了 `static` 和 `private` 建構子的超強技巧。把建構子鎖死，讓別人沒辦法 `new`。

💼 業界實務：
在 Spring 框架裡，所有的元件預設都是 Singleton。這樣可以節省大量記憶體。想像一下，如果你有一萬個地方要處理訂單，你不需要一萬個「處理員」，你只需要一個。
-->

---

# Singleton — 使用範例

```java
Singleton s1 = Singleton.getInstance();
Singleton s2 = Singleton.getInstance();

System.out.println(s1 == s2); // true，是同一個物件
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>應用場景：</b>資料庫連線池、設定管理器（Config）、日誌管理器（Logger）等需要全域唯一實體的元件，常見以 Singleton 設計。
</div>

<!--
【逐步解說】
你看這裡的 `s1 == s2`。這在比的是「記憶體地址」。因為 Singleton 保證只有一個實體，所以無論你呼叫幾次 `getInstance`，拿到的都是同一個地址，結果就是 `true`。

💼 業界實務：
資料庫連線池通常就是這樣設計的。不然你每個工程師都開一百個連線，資料庫很快就會噴火罷工了。
-->

---
layout: default
---

# 練習一：設計 Student 類別
### 任務說明

設計一個符合封裝原則的 `Student` 類別，需求如下：

1. 欄位：`name`（姓名）、`score`（成績，0–100）
2. 提供兩個建構子：無參數版本、以及接受 `name` 與 `score` 的版本
3. 無參數建構子呼叫有參數建構子，傳入預設值（`"未知"`, `0`）
4. `score` 的 setter 需加入驗證，拒絕不合法的分數
5. 提供一個 `static` 方法 `isPass(int score)` 判斷是否及格（≥60）

<!--
【互動引導】
好了，嘴砲了這麼久，該換你們動動手了。

【解說要點】
這個練習結合了我們今天學的所有招式。記得 `this()` 要放哪裡嗎？還有 setter 裡面要怎麼檢查分數不准超過 100？

【等待與觀察】
給大家十分鐘。寫不出來的，可能要考慮一下今天晚餐要不要多吃一點補補腦（開玩笑的）。
-->

---

# 練習一：解題提示
### 提示說明

1. 使用 `this("未知", 0)` 讓無參數建構子委託給有參數建構子
2. setter 範例邏輯：

```java
public void setScore(int score) {
    if (score >= 0 && score <= 100)
        this.score = score;
}
```

3. static 方法不需要物件就能呼叫：

```java
public static boolean isPass(int score) {
    return score >= 60;
}
// 呼叫：Student.isPass(75)
```

4. 記得遵守 JavaBean 慣例：`getName()` / `setName()` / `getScore()` / `setScore()`

<!--
【逐步解說】
如果你卡住了，看這裡！重點在於 `this()` 呼叫，還有 `static` 方法裡面是不需要用 `this` 的。

⚠️ 學生常見誤解：
有人會寫成 `this.isPass()`，雖然可以動，但這會讓帶你的前輩很想打人。請用 `Student.isPass()`。
-->

---
layout: default
---

# 練習二：計數器與 Singleton
### 任務說明

1. 在 `Student` 類別中加入 `static int totalCount` 欄位，每次建立新物件時自動累計人數。
2. 設計一個 `SchoolConfig` 類別，使用 Singleton 模式，儲存學校名稱（`schoolName`），並確保全程只有一個實體。
3. 在 `main()` 中建立多個 `Student` 物件，並透過 `Student.totalCount` 驗證計數正確。
4. 呼叫 `SchoolConfig.getInstance()` 兩次，用 `==` 驗證兩次取得的是同一個物件。

<!--
【出題前的鋪陳】
如果你覺得剛才那個太簡單，這裡有進階版的。

【問題引導】
想想看，`totalCount` 應該放在建構子的哪裡？Singleton 的那三要素你還記得嗎？

【等待與觀察】
這題要是能寫對，你今天就可以昂首闊步地走出教室了。
-->

---

# 練習二：解題提示
### 提示說明

1. static 計數器在建構子內累加：

```java
static int totalCount = 0;
Student(String name, int score) {
    this.name = name;
    this.score = score;
    totalCount++;
}
```

2. Singleton 三要素：`private` 建構子 + `private static` 欄位 + `public static getInstance()`
3. 驗證方式：

```java
SchoolConfig c1 = SchoolConfig.getInstance();
SchoolConfig c2 = SchoolConfig.getInstance();
System.out.println(c1 == c2); // 預期輸出 true
```

<!--
【逐步解說】
解題關鍵在於：Singleton 的建構子一定要是 `private`。如果你還能 `new` 出來，那就不是 Singleton，那是「假 Singleton」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

有任何關於建構子、封裝或 static 的問題歡迎提出！

<!--
【開場白】
好了，今天的「物件保護與共用」課程就到這裡。有誰的腦袋已經燒壞了，需要我幫你 `new` 一個新的嗎？

【問題引導】
有沒有人對 `static` 還是覺得怕怕的？或者不懂為什麼一定要寫 Getter/Setter？沒關係，儘管問，我不收診斷費的。
-->

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
大家好，這一章的主題是物件建構與封裝。

上一章我們學會了設計類別、建立物件。但大家可能發現了，建立物件之後，還要一個欄位一個欄位地設定值，而且任何人都能把欄位改成不合理的數字，例如把帳戶餘額改成負數。

這一章要學的建構子、封裝和 static，就是用來解決這些問題的。學完之後，我們就能設計出更穩固、更不容易被誤用的類別。
-->

---
layout: default
---

# Outline

- **建構方法（Constructor）**
  - 預設建構子 / 自定義建構子 / 多載 / this() / vs 一般方法
- **封裝（Encapsulation）**
  - 資料隱藏 / private + getter/setter / 存取修飾詞 / JavaBean 慣例
- **static 關鍵字**
  - 類別變數 / 靜態方法 / static 初始化區塊 / Singleton 設計模式
- **練習題**

<!--
這一章分成三個部分。

第一部分是建構子，包括預設建構子、自訂建構子、建構子多載，以及用 this 呼叫其他建構子。第二部分是封裝，學習用 private 隱藏資料，再透過 getter 和 setter 控制存取，並認識 JavaBean 慣例。第三部分是 static，包括類別變數、靜態方法，以及 Singleton 設計模式。最後有兩題練習。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 建構方法 Constructor

<!--
我們先從建構子開始。它決定了一個物件「出生」的那一刻，會是什麼樣子。
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
| 可見性 | 可為 public / protected / 不寫（套件內）/ private |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>建構子 vs 一般方法：</b>建構子不能有回傳型態、名稱必須同類別，且只能透過 new（或在其他建構子中以 this() / super()）觸發，不能像一般方法那樣直接呼叫。
</div>

<!--
「建構子是用 new 建立物件時，會自動執行的特殊方法。」它的工作是設定物件的初始狀態。

建構子有兩個明顯的特徵：名稱必須和類別名稱一模一樣，而且沒有回傳型態，連 void 都不寫。

我們可以把它想成新生兒出生時的登記手續，物件一被建立，就會自動跑一次，把該有的資料先填好。

它和一般方法還有一個差別：建構子不能像一般方法那樣直接呼叫，只能透過 new，或是在其他建構子裡用 this 或 super 觸發。
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
如果我們沒有寫任何建構子，Java 編譯器會自動幫我們補上一個沒有參數的預設建構子。所以這個 Dog 類別雖然什麼建構子都沒寫，還是可以 new Dog。

下面的提醒很重要：「一旦我們自己寫了任何建構子，Java 就不會再自動補上預設建構子。」這時候如果還需要沒有參數的版本，就要自己寫出來，否則 new Dog 就會編譯錯誤。
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
自訂建構子可以讓物件在建立的同時，就帶入初始值。

這個 Dog 建構子接收名字和年齡兩個參數，在裡面設定給欄位。這樣一來，new Dog 小黑、3，一行就能建立好一隻三歲的小黑，不用先建立、再一個一個設定。印出 d.name，就會得到小黑。

注意這裡用了 this。參數和欄位都叫 name，this.name 指的是這個物件的欄位，右邊的 name 則是參數。這個寫法我們上一章看過，在建構子裡非常常見。
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
一個類別可以有好幾個建構子，只要參數的數量或型別不同就可以，這叫做建構子多載，和上一章學的方法多載是同樣的概念。

看 Box 這個例子，它有三個建構子：沒有參數的版本，尺寸全部是 0；一個參數的版本，建立三邊等長的正方體；三個參數的版本，可以分別指定長寬高。

所以 new Box 不帶參數、帶一個 7，或是帶三個數字，會分別使用不同的建構子。
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
這是 Box 類別完整的程式碼。

第一個建構子把三個邊都設成 0。第二個建構子用了鏈式賦值，把三個邊都設成同一個長度。第三個建構子則分別設定三個邊。最後的 volume 方法計算體積。

呼叫的時候，編譯器會根據我們傳入的參數數量和型別，自動選出對應的建構子。
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
多個建構子之間，常常有重複的初始化邏輯。這時候可以在建構子裡用 this 加上括號，呼叫同一個類別的另一個建構子。

使用時有幾條規則：this 呼叫必須寫在建構子的第一行；每個建構子只能呼叫一次；也不能互相循環呼叫，例如 A 呼叫 B、B 又呼叫 A。

這樣做的好處，是初始化的邏輯只需要寫在一個地方，其他建構子都交給它處理，修改時也只要改一處。
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
我們看一個串接呼叫的例子。

new Temp 會先執行沒有參數的建構子，它的第一行呼叫 Temp 5；Temp 5 的第一行，又呼叫 Temp 5、15。

所以最先印出來的，是最後一個建構子裡的 5 乘 15，也就是 75。接著回到上一層，印出 5，最後才回到最外層，印出預設建構子。

就像下面說的，這個執行順序和堆疊一樣：一路往下呼叫，再從最裡面一層一層返回。
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
這張表整理了建構子和一般方法的差別。

建構子的名稱一定要和類別相同，一般方法可以自由命名；建構子沒有回傳型態，一般方法一定要宣告，最少也要寫 void；建構子透過 new 自動呼叫，一般方法則由我們主動呼叫。

用途上，建構子負責初始化物件，一般方法負責定義物件的行為。另外，建構子不會被繼承，這一點在之後學繼承時會再提到。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 封裝 Encapsulation

<!--
接下來是物件導向的第一個核心觀念：封裝。
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
「封裝就是把資料藏在類別裡面，只開放受控制的管道讓外部存取。」

想像 ATM，我們不能直接打開金庫拿錢，只能透過按鈕和螢幕，而且提款時，ATM 會先檢查餘額夠不夠。封裝就是這個概念。

做法是把欄位宣告成 private，外部無法直接存取，再提供 public 的 getter 和 setter 方法作為存取管道。setter 裡可以加入檢查，擋掉不合理的值。如果只提供 getter、不提供 setter，這個欄位就變成外部只能讀、不能改。

封裝最大的好處，是類別內部的寫法可以自由修改，只要對外的方法不變，使用這個類別的程式就不需要跟著改。
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
我們看銀行帳戶的例子。

balance 宣告成 private，外部不能直接存取。要查詢餘額，就呼叫 getBalance；要存錢，就呼叫 deposit。deposit 裡面先檢查金額是否大於 0，負數的存款會被擋下來。

下面是使用的程式碼。存入 1000 之後，getBalance 會回傳 1000.0。最後一行如果想直接把 balance 改成負 999，編譯器就會報錯，因為它是 private 欄位。

這就是封裝的效果：資料只能透過我們設計好的方式修改，不會出現不合理的狀態。
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
Java 有四種存取層級。

public 是完全公開，任何地方都能存取。protected 是同一個套件，加上子類別可以存取。什麼都不寫，是只有同一個套件內可以存取。private 則是只有同一個類別內可以存取。

下面的原則很重要：「盡量使用最嚴格的存取層級。」欄位通常宣告成 private，方法再依照需要決定公開的程度。
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
這張表把四種存取層級的範圍並排比較。

public 在同類別、同套件、不同套件的子類別和外部都能存取。protected 少了外部。不寫修飾詞，又少了不同套件的子類別。private 則只剩下同一個類別。

可以照著限制由鬆到嚴的順序記：public、protected、不寫、private。欄位預設就用 private，有特別的理由才放寬。
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
JavaBean 是一套以封裝為基礎的類別撰寫慣例，Spring、Hibernate 這些框架都依照它來讀寫物件的資料。

規則有幾條：欄位全部是 private；getter 命名為 get 加上欄位名稱，setter 命名為 set 加上欄位名稱，欄位名稱的第一個字母大寫；如果是布林型態的欄位，getter 習慣用 is 開頭；另外，要有一個沒有參數的建構子。

照著這套命名規則寫，框架就能自動找到對應的方法。
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
💡 <b>isXxx vs getXxx：</b>布林型態的 getter 使用 <code>isXxx()</code> 而非 <code>getXxx()</code>，這是 JavaBean 規範對布林屬性的慣用寫法（getXxx 也被允許），IDE 與框架都能自動識別。
</div>

<!--
這是一個符合 JavaBean 慣例的 Person 類別。

name 和 active 都是 private。name 有 getName 和 setName；active 是布林值，所以 getter 寫成 isActive，setter 一樣是 setActive。

這個類別沒有寫任何建構子，所以 Java 會自動補上無參數的建構子，剛好符合 JavaBean 的要求。

實際開發時，這些 getter 和 setter 通常不用手寫，IDE 可以自動產生，之後學到的 Lombok 工具也能用一行註解自動產生。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# static 關鍵字

<!--
最後一個部分是 static 關鍵字。我們會看到，有些資料和方法屬於「類別本身」，而不是屬於某一個物件。
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
沒有加 static 的欄位，叫做實體變數，每個物件各自有一份，例如每隻狗都有自己的名字。加上 static 的欄位，叫做類別變數，整個類別只有一份，所有物件共用。

存取的方式也不同：實體變數透過物件存取，類別變數則建議用類別名稱存取。另外，類別變數從類別載入之後就一直存在，不會跟著某個物件消失。

就像班上每個同學有自己的座號，但教室裡的時鐘只有一個，大家看的是同一個。
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
我們用類別變數來計算總共建立了幾隻狗。

count 是 static，所有 Dog 物件共用同一個。每次建構子執行，也就是每建立一隻狗，count 就加 1。

建立小黑和小白兩隻狗之後，Dog.count 就是 2。

注意這裡是用類別名稱 Dog 點 count 來存取，而不是用 d1 點 count。這樣讀程式的人一眼就知道，這是整個類別共用的資料。
-->

---

# static 方法

靜態方法**不依賴物件**，直接透過類別名稱呼叫，且不能直接存取實體欄位。

| 特性 | 說明 |
| --- | --- |
| 呼叫方式 | `類別名稱.方法名()` |
| 存取限制 | **不能直接**存取 instance fields / methods（也不能用 this） |
| 可存取 | static 欄位、其他 static 方法、參數 |
| 典型例子 | `Math.abs()`、`Math.max()`、`Integer.parseInt()` |

```java
// Math 類別全部是靜態方法
System.out.println(Math.abs(-5));   // 5
System.out.println(Math.max(3, 7)); // 7
```

<!--
static 也可以用在方法上，這叫做靜態方法。

靜態方法不需要建立物件，直接用類別名稱就能呼叫。我們其實早就用過了，Math.abs、Math.max、Integer.parseInt，都是靜態方法。

要注意的限制是：靜態方法裡不能直接使用實體欄位和實體方法，也不能用 this。因為呼叫靜態方法時，可能根本還沒有任何物件存在。靜態方法裡可以使用的，是 static 欄位、其他 static 方法，以及傳進來的參數。
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
我們也可以自己寫靜態方法。

MathUtils 裡的 add 和 circleArea 都是 static，使用時直接寫 MathUtils.add 3、4，得到 7；circleArea 5，得到大約 78.54。

這類不需要記住任何物件狀態、只是單純計算或轉換的功能，很適合設計成靜態方法，這種類別通常稱為工具類別。
-->

---

# static 初始化區塊

`static { }` 區塊在**類別第一次被載入時執行一次**，用於複雜的靜態變數初始化。

| 特性 | 說明 |
| --- | --- |
| 執行時機 | 類別載入時（早於任何建構子），**只執行一次** |
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
如果靜態變數的初始化比較複雜，可以用 static 初始化區塊。

它的寫法是 static 加上一對大括號。類別第一次被載入時，它會執行一次，而且只會執行一次，時間點比任何建構子都早。

看範例，Config 類別載入時，static 區塊會把 MAX_SIZE 設成 100，並印出「Config 類別已載入」。實際開發中，它常用來初始化靜態的資料集合，或是載入設定檔。
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
static 有一個經典的應用，叫做 Singleton 設計模式，也就是單例模式。它保證整個程式裡，某個類別只會有一個物件。

做法有三個要素。第一，建構子設成 private，外部就沒辦法 new。第二，用一個 private static 欄位，存放唯一的那個物件。第三，提供一個 public static 的 getInstance 方法，當作取得物件的唯一入口。

getInstance 第一次被呼叫時，instance 是 null，就建立一個新物件；之後再呼叫，就直接回傳同一個物件。
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
我們驗證一下。呼叫兩次 getInstance，分別存進 s1 和 s2，再用兩個等號比較，結果是 true，代表它們是同一個物件。

在實際專案裡，資料庫連線池、設定管理、日誌記錄這些整個程式只需要一份的元件，常常會用 Singleton 來設計。
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
接下來我們動手練習。

第一題，請設計一個符合封裝原則的 Student 類別。欄位有姓名和成績，提供兩個建構子：無參數的版本，以及接收姓名和成績的版本，而且無參數的建構子要呼叫有參數的建構子，傳入「未知」和 0。

成績的 setter 要檢查分數是否在 0 到 100 之間。最後再提供一個 static 方法 isPass，判斷分數是否及格。

我們先暫停一下，自己動手試試看。

【講師】給學生約 15 分鐘實作。
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
我們來看提示。

無參數建構子的第一行寫 this 未知、0，把工作交給有參數的建構子。setScore 裡用 if 判斷分數大於等於 0、而且小於等於 100，才設定給欄位。

isPass 是 static 方法，回傳分數是否大於等於 60，呼叫時直接寫 Student.isPass。最後記得 getter 和 setter 都要依照 JavaBean 的命名慣例。
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
第二題延續剛才的 Student 類別。

首先，加入一個 static 的 totalCount，每建立一個學生就自動加 1。接著，設計一個 SchoolConfig 類別，用 Singleton 模式存放學校名稱。

在 main 裡建立幾個學生，確認 totalCount 的數字正確；再呼叫兩次 SchoolConfig.getInstance，用兩個等號確認拿到的是同一個物件。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 15 分鐘實作。
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
我們來看提示。

totalCount 宣告成 static，初始值是 0，在建構子裡加 1。要注意，如果無參數建構子是透過 this 呼叫有參數的版本，只要在有參數的建構子裡加 1 就好，不會重複計算。

SchoolConfig 就照著 Singleton 的三個要素寫：private 建構子、private static 欄位，以及 public static 的 getInstance。最後比較 c1 和 c2，預期會印出 true。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

有任何關於建構子、封裝或 static 的問題歡迎提出！

<!--
這一章我們讓類別變得更穩固。

建構子在 new 的時候自動執行，負責設定初始狀態，可以多載，也能用 this 互相呼叫。封裝是把欄位設成 private，再透過 getter 和 setter 控制存取，並在 setter 裡檢查資料。static 成員屬於類別本身，所有物件共用，Singleton 則保證整個程式只有一個物件。

下一章，我們要認識 Java 內建的 Math 和 Random 類別。

【講師】開放 Q&A。
-->

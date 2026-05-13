---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 建構方法 Constructor

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

---

# this() — 呼叫另一個建構子

在建構子內以 `this(...)` 呼叫**同一類別**的另一個建構子，避免重複初始化邏輯。

| 規則 | 說明 |
| --- | --- |
| 位置 | `this()` 必須是建構子的**第一行** |
| 次數 | 每個建構子只能呼叫一次 |
| 遞迴 | 禁止循環呼叫（A 呼叫 B、B 再呼叫 A） |
| 用途 | 減少重複程式碼，確保初始化一致 |

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

---

# 建構子 vs 一般方法

| 比較項目 | 建構子 | 一般方法 |
| --- | --- | --- |
| 名稱 | **必須與類別名稱相同** | 任意合法識別字 |
| 回傳型態 | **不能有**（連 void 也不寫） | 必須宣告（可為 void） |
| 呼叫方式 | `new` 關鍵字自動呼叫 | 用物件或類別名稱呼叫 |
| 用途 | 初始化物件狀態 | 定義物件行為 |
| 繼承 | **不被繼承** | 可被繼承與覆寫 |

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 封裝 Encapsulation

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# static 關鍵字

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

---
layout: default
---

# 練習二：計數器與 Singleton
### 任務說明

1. 在 `Student` 類別中加入 `static int totalCount` 欄位，每次建立新物件時自動累計人數。
2. 設計一個 `SchoolConfig` 類別，使用 Singleton 模式，儲存學校名稱（`schoolName`），並確保全程只有一個實體。
3. 在 `main()` 中建立多個 `Student` 物件，並透過 `Student.totalCount` 驗證計數正確。
4. 呼叫 `SchoolConfig.getInstance()` 兩次，用 `==` 驗證兩次取得的是同一個物件。

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

有任何關於建構子、封裝或 static 的問題歡迎提出！

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

---
layout: default
---

# Outline

- **抽象類別 (Abstract Class)**
- **抽象方法 (Abstract Method)**
- **觀念整理**
- **進階應用：建構方法與 Upcasting**
- **抽象類別 vs 介面**
- **實作練習**

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別
# Abstract Class

---

# 什麼是抽象類別

- 使用關鍵字 `abstract` 宣告的類別稱為**抽象類別**
- 抽象觀念主要是**隱藏工作細節**，使用者只需知道如何使用
  - 例如 `+` 符號可以執行數值加法，也可以執行字串相加
  - 但不需要知道內部程式如何設計 `+` 號的功能
- 這個類別中可以有**抽象方法**（abstract method）和**實體方法**（method）

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象方法
# Abstract Method

---

# 抽象方法的特性

| 特性 | 說明 |
| --- | --- |
| 沒有實體內容 | 無方法主體（no body） |
| 宣告以 `;` 結尾 | 不使用 `{}` 大括號 |
| 必須被子類別 override | 子類別**必須**重新定義，否則編譯錯誤 |
| 類別需宣告為 abstract | 含抽象方法的類別必須是抽象類別 |

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 觀念整理
# Abstract Class & Method

---

# 抽象類別與抽象方法 — 重要規則

- 抽象類別若**沒有子類別去繼承**，是沒有功能的（因為無法實例化）
- 抽象類別的**抽象方法**必須有子類別重新定義，否則會有編譯錯誤
- 若抽象類別的抽象方法沒有子類別重新定義，**該子類別也將是一個抽象類別**
- 宣告了抽象方法，**一定**要為此方法宣告抽象類別
  - 普通類別中**不存在**抽象方法
- 宣告了抽象類別，**不一定**要在此類別內宣告抽象方法
- **抽象類別可以有抽象方法和普通方法**

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階應用
# Constructor & Upcasting

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 抽象類別 vs 介面
# Abstract Class vs Interface

---

# 抽象類別與介面的比較

| 比較項目 | 抽象類別 Abstract Class | 介面 Interface |
| --- | --- | --- |
| 父類別/父介面繼承 | 只能繼承一個類別 | 能繼承多個介面（Java 實現多重繼承） |
| 子類別繼承/實作 | `extends` 一個抽象類別 | `implements` 多個介面 |
| 方法 | 可包含非抽象方法 | 只能是抽象方法（Java 8 以前） |
| 必定為 | 父類別 | 可視為抽象類別的特例 |

---

# 抽象類別與介面 — 相同點與應用

**相同點：**
- 兩者都**無法直接實體化**
- 子類別都必須實作已宣告之抽象方法（或繼續抽象）

**應用場景比較：**
- **抽象類別**：關係密切的類別中，如定義抽象類別 `Car`，子類別 `Benz` 及 `Audi` 繼承 `Car`
- **介面**：定義一些功能給不相干類別使用，如定義介面 `Fly`，子類別 `AirPlane` 及 `Bird` 實作 `Fly`

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 實作練習

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

---

# 練習 1：解題提示
### 提示說明

1. 在 `Shape` 中宣告 `abstract double area();` 與 `abstract double perimeter();`
2. `Rectangle` 需 `height`、`width` 屬性，透過建構方法傳入（高 2，寬 3）
3. `Circle` 需 `r`（半徑）屬性，透過建構方法傳入（半徑 2）
4. 計算公式：
   - 矩形面積 = `height * width`，周長 = `2 * (height + width)`
   - 圓面積 = `Math.PI * r * r`，圓周長 = `2 * Math.PI * r`

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

---

# 練習 2：解題提示
### 提示說明

1. 在 `MyMath` 中宣告 `abstract int add(int n1, int n2);` 與 `abstract int mul(int n1, int n2);`
2. 普通方法 `void output()` 直接印出「我的計算器」，不需 override
3. 在 `MyTest` 中實作：`add()` 回傳 `n1 + n2`，`mul()` 回傳 `n1 * n2`
4. 在 `main` 中使用 Upcasting：`MyMath obj = new MyTest();`
5. 呼叫 `obj.output()`、`obj.add(3, 8)`、`obj.mul(3, 8)`

---
layout: end
---

# 課程結束
### 感謝聆聽，有問題請發問！

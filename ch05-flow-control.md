---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: 程式流程控制
routeAlias: ch05
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
  <h1 style="color: #1a5c5c; font-size: 3.4rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">程式流程控制</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「讓程式學會做決定：if、switch 與條件判斷」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

---
layout: default
---

# Outline

- **5-1 if 敘述**：if / if-else / if-else if-else 鏈、三元運算子 `? :`
- **5-2 switch 敘述**：傳統 switch、Java 14+ switch expression、switch 搭配字串、Java 17 Pattern Matching
- **5-3 專題實作**：BMI 計算、生肖判斷、火箭升空倒數
- **練習題**：2 題（任務說明 + 解題提示各一張）

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 5-1 if 敘述

---
layout: default
---

# if 敘述的三種形式

| 形式 | 適用時機 |
| --- | --- |
| `if` | 條件為真才執行，否則跳過 |
| `if-else` | 二擇一：條件真或假各有一段邏輯 |
| `if-else if-else` 鏈 | 多條件依序判斷，第一個成立的分支執行後離開 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>執行規則：</b>if-else if-else 鏈中，一旦某個條件為 true，其餘條件全部跳過，即使後面也可能成立。
</div>

---

# if 與 if-else 語法

```java
// 單純 if：只有條件為真才執行
int score = 85;
if (score >= 60) {
    System.out.println("及格");
}

// if-else：二選一
if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}
```

---

# if-else if-else 鏈語法

```java
int score = 72;

if (score >= 90) {
    System.out.println("優秀");
} else if (score >= 80) {
    System.out.println("良好");
} else if (score >= 60) {
    System.out.println("及格");
} else {
    System.out.println("不及格");
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>建議：</b>條件由「最嚴格」到「最寬鬆」排列，避免邏輯被提前攔截。
</div>

---

# 三元運算子 `? :`

| 語法 | 說明 |
| --- | --- |
| `條件 ? 值A : 值B` | 條件為 true 回傳值A，否則回傳值B |
| 可巢狀使用 | `a ? b : (c ? d : e)`，但建議避免超過兩層 |

```java
int a = 10, b = 20;

// 等同 if-else，但可直接指派給變數
int max = (a > b) ? a : b;
System.out.println(max); // 20

// 搭配字串輸出
String result = (a > b) ? "a 較大" : "b 較大";
System.out.println(result); // b 較大
```

---

# 三元運算子 vs if-else 對比

| 比較 | 三元運算子 | if-else |
| --- | --- | --- |
| 程式碼行數 | 1 行 | 4 行以上 |
| 可賦值給變數 | 可以 | 需另設變數 |
| 適合複雜邏輯 | 不適合 | 適合 |
| 可讀性 | 簡短條件佳 | 複雜邏輯更清晰 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>使用原則：</b>邏輯簡單、單行可表達時用三元運算子；邏輯複雜或有多個步驟時用 if-else。
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 5-2 switch 敘述

---
layout: default
---

# 傳統 switch 語法結構

| 元素 | 說明 |
| --- | --- |
| `switch(運算式)` | 運算式型別：`byte`、`short`、`int`、`char`、`String`、`enum` |
| `case 值:` | 符合值才執行，使用冒號 `:` |
| `break` | 跳出 switch，不加會發生 fall-through |
| `default:` | 所有 case 都不符合時執行，選填 |

```java
int day = 3;
switch (day) {
    case 1: System.out.println("星期一"); break;
    case 2: System.out.println("星期二"); break;
    case 3: System.out.println("星期三"); break;
    default: System.out.println("其他天");
}
```

---

# Fall-through 效果

不加 `break` 時，程式會「貫穿」往下執行所有 case：

```java
int day = 2;
switch (day) {
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
        System.out.println("工作日"); break;
    case 6:
    case 7:
        System.out.println("假日"); break;
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>善用 fall-through：</b>多個 case 共用同一段邏輯時，可以省略中間的 break，讓程式自然貫穿。
</div>

---

# switch 搭配字串

Java 7 開始，`switch` 支援 `String` 型別（比較大小寫敏感）：

```java
String season = "春";

switch (season) {
    case "春": System.out.println("Spring"); break;
    case "夏": System.out.println("Summer"); break;
    case "秋": System.out.println("Autumn"); break;
    case "冬": System.out.println("Winter"); break;
    default:   System.out.println("未知季節");
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意大小寫：</b>字串比較區分大小寫。若輸入來自使用者，建議先呼叫 <code>.toLowerCase()</code> 或 <code>.toUpperCase()</code> 統一格式。
</div>

---

# Java 14+ Switch Expression 語法對比

| 特性 | 傳統 switch | Switch Expression (Java 14+) |
| --- | --- | --- |
| 語法符號 | `case 值:` + `break` | `case 值 ->` |
| Fall-through | 有（忘記 break 就貫穿） | 無（自動隔離每個 case） |
| 回傳值 | 不能直接賦值 | 可直接賦值給變數 |
| 多值 case | 需連寫多個 case | `case A, B, C ->` 逗號分隔 |
| 強制完整性 | 不強制（無 default 也行） | 必須涵蓋所有可能值 |

---

# Switch Expression 基本用法

```java
int day = 3;

// 直接賦值，不需 break
String dayName = switch (day) {
    case 1 -> "星期一";
    case 2 -> "星期二";
    case 3 -> "星期三";
    case 4 -> "星期四";
    case 5 -> "星期五";
    default -> "假日";
};
System.out.println(dayName); // 星期三
```

---

# Switch Expression：多值 case 與 yield

```java
int month = 8;

int days = switch (month) {
    case 1, 3, 5, 7, 8, 10, 12 -> 31;
    case 4, 6, 9, 11 -> 30;
    case 2 -> {
        // 多行邏輯用 yield 回傳值
        boolean leap = (2024 % 4 == 0);
        yield leap ? 29 : 28;
    }
    default -> 0;
};
System.out.println(month + " 月有 " + days + " 天");
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>yield：</b>在 switch expression 的 block（大括號）中，用 <code>yield</code> 代替 <code>return</code> 回傳值。
</div>

---

# Java 17 Pattern Matching for switch（預覽特性）

Java 17 引入 switch 型別模式比對（JEP 406，預覽功能）：

| 語法 | 說明 |
| --- | --- |
| `case Integer i ->` | 比對型別並自動綁定變數 `i` |
| `case String s ->` | 比對型別並自動綁定變數 `s` |
| `case String s when s.length() > 0 ->` | 加條件守衛（guarded pattern）|
| `case null ->` | 明確處理 null 值，不再拋 NPE |

---

# Pattern Matching for switch — 範例

```java
static String describe(Object o) {
    return switch (o) {
        case Integer i -> "整數：" + i;
        case String s when s.isEmpty() -> "空字串";
        case String s -> "字串：" + s;
        case null     -> "null 值";
        default       -> "其他型別";
    };
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Java 版本說明：</b>Pattern Matching for switch 在 Java 17 為預覽版，Java 21 起成為正式標準。課程以 JDK 17 為主，使用時需加上 <code>--enable-preview</code> 編譯旗標。
</div>

---

# Sealed Class 搭配 switch（Java 17）

`sealed` 類別限制繼承範圍，配合 switch 可達到完整型別覆蓋：

```java
sealed interface Shape permits Circle, Rectangle {}
record Circle(double radius) implements Shape {}
record Rectangle(double w, double h) implements Shape {}

static double area(Shape s) {
    return switch (s) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.w() * r.h();
    };
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>優點：</b>編譯器知道 sealed 類別的所有子型別，能在編譯期檢查是否漏掉某個 case，不需要 default。
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 5-3 專題實作

---
layout: default
---

# 專題一：BMI 計算

BMI（身體質量指數）= 體重（kg）÷ 身高²（m²）

| BMI 範圍 | 等級 |
| --- | --- |
| BMI < 18.5 | 體重過輕 |
| 18.5 ≤ BMI < 24 | 體重正常 |
| 24 ≤ BMI < 27 | 體重過重 |
| BMI ≥ 27 | 肥胖 |

---

# 專題一：BMI 程式碼

```java
double weight = 70;  // 公斤
double height = 1.75; // 公尺

double bmi = weight / (height * height);
System.out.printf("BMI = %.2f%n", bmi);

if (bmi < 18.5) {
    System.out.println("體重過輕");
} else if (bmi < 24) {
    System.out.println("體重正常");
} else if (bmi < 27) {
    System.out.println("體重過重");
} else {
    System.out.println("肥胖");
}
```

---

# 專題二：生肖判斷

中國生肖依年份除以 12 的餘數判斷：

| 餘數 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 生肖 | 猴 | 雞 | 狗 | 豬 | 鼠 | 牛 | 虎 | 兔 | 龍 | 蛇 | 馬 | 羊 |

---

# 專題二：生肖程式碼（switch expression）

```java
int year = 2024;
int r = year % 12;

String zodiac = switch (r) {
    case 0  -> "猴";
    case 1  -> "雞";
    case 2  -> "狗";
    case 3  -> "豬";
    case 4  -> "鼠";
    case 5  -> "牛";
    case 6  -> "虎";
    case 7  -> "兔";
    case 8  -> "龍";
    case 9  -> "蛇";
    case 10 -> "馬";
    default -> "羊";
};
System.out.println(year + " 年是 " + zodiac + " 年");
```

---

# 專題三：火箭升空倒數

結合迴圈與 if 敘述，實作倒數 + 特定秒數提示：

```java
for (int i = 10; i >= 0; i--) {
    if (i == 0) {
        System.out.println("🚀 點火！升空！");
    } else if (i <= 3) {
        System.out.println(i + "... 準備！");
    } else {
        System.out.println(i + "...");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>觀念複習：</b>for 迴圈中的 if-else if-else 鏈，每次迭代都會根據 <code>i</code> 的值選擇對應的分支執行。
</div>

---
layout: default
---

# 練習一：成績等第轉換
### 任務說明

請撰寫一個 Java 程式，接收一個整數分數（0–100），依下列規則輸出等第：

| 分數範圍 | 等第 |
| --- | --- |
| 90 ~ 100 | A |
| 80 ~ 89 | B |
| 70 ~ 79 | C |
| 60 ~ 69 | D |
| 0 ~ 59 | F |

**輸入範例：** `score = 83`  
**輸出範例：** `等第：B`

---
layout: default
---

# 練習一：解題提示

### 提示說明

1. 使用 `if-else if-else` 鏈，從最高分段往下判斷。
2. 條件由嚴到寬：先判斷 `>= 90`，再 `>= 80`，依序往下。
3. 最後 `else` 涵蓋所有 59 分以下的情形（等第 F）。

```java
// 參考結構（填入正確條件）
int score = 83;
String grade;
if (score >= 90) {
    grade = "A";
} else if (______) {
    grade = "B";
} // ... 繼續補完
System.out.println("等第：" + grade);
```

---
layout: default
---

# 練習二：星期幾判斷器
### 任務說明

使用 **Switch Expression（Java 14+）** 撰寫程式：

- 輸入整數 1–7，分別對應星期一到星期日
- 星期一至星期五輸出：`工作日`
- 星期六、星期日輸出：`假日`
- 其他數值輸出：`無效輸入`

**輸入範例：** `day = 6`  
**輸出範例：** `假日`

---
layout: default
---

# 練習二：解題提示

### 提示說明

1. 使用 `switch (day)` 搭配箭頭語法（`->`）。
2. 星期一到五可用多值 case：`case 1, 2, 3, 4, 5 ->`。
3. 星期六、日：`case 6, 7 ->`。
4. 超出範圍用 `default ->`。

```java
int day = 6;
String type = switch (day) {
    case 1, 2, 3, 4, 5 -> "工作日";
    case ______         -> "假日";
    default             -> "無效輸入";
};
System.out.println(type);
```

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

有任何問題歡迎提出！

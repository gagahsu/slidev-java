---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
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

<!--
大家好，這一章的主題是程式流程控制。

我們每天都在做決定：鬧鐘響了就起床，沒響就繼續睡；下雨就帶傘，沒下雨就不帶。程式也一樣，需要依照不同的情況，執行不同的動作。

這一章我們會學 if 和 switch 兩種判斷方式。學完之後，我們寫的程式就能依照輸入的資料，自己做出不同的決定。
-->

---
layout: default
---

# Outline

- **if 敘述**：if / if-else / if-else if-else 鏈、三元運算子 `? :`
- **switch 敘述**：傳統 switch、Java 14+ switch expression、switch 搭配字串、Java 17 Pattern Matching
- **專題實作**：BMI 計算、生肖判斷、火箭升空倒數
- **練習題**：2 題（任務說明 + 解題提示各一張）

<!--
這一章分成三個部分。

第一部分是 if 敘述，包括三種基本形式，以及更精簡的三元運算子。第二部分是 switch 敘述，從傳統的寫法，一路看到 Java 14 的 switch expression，以及 Java 17 的型別模式比對。

第三部分，我們用 BMI 計算、生肖判斷和火箭倒數三個小專題，把學到的判斷方式實際用一遍，最後還有兩題練習。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# if 敘述

<!--
我們先從最基本、也最常用的判斷方式開始：if 敘述。
-->

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

<!--
if 敘述有三種形式。

第一種是單純的 if：條件成立才執行，不成立就跳過。第二種是 if-else，二選一，條件成立走一條路，不成立走另一條路。第三種是 if-else if-else 鏈，依序檢查多個條件。

下面的規則很重要：在 if-else if-else 鏈裡，「只要有一個條件成立，執行完那個分支，後面的條件就全部跳過」，即使後面的條件也成立。就像在夜市挑晚餐，一攤一攤看，找到想吃的就停下來，不會再看後面的攤位。
-->

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

<!--
我們看前兩種寫法。

第一段是單純的 if。分數是 85，大於等於 60，條件成立，所以印出及格。如果分數是 50，大括號裡的程式就會被跳過，什麼都不印。

第二段是 if-else。條件成立時印出及格，不成立時就執行 else 的部分，印出不及格。兩條路一定會走其中一條。
-->

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

<!--
這是多個條件的 if-else if-else 鏈。分數是 72，我們從上往下檢查。

大於等於 90 嗎？不是。大於等於 80 嗎？也不是。大於等於 60 嗎？是，所以印出及格，然後整個判斷就結束了。如果前面的條件都不成立，最後就會執行 else，印出不及格。

下面的建議要注意：條件要從最嚴格排到最寬鬆。如果把大於等於 60 放在第一個，90 分的人也會在第一關就被判定為及格，永遠走不到優秀那一個分支。
-->

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

<!--
如果判斷很簡單，只是要在兩個值之間選一個，可以用更精簡的三元運算子。

它的寫法是：條件、問號、值 A、冒號、值 B。條件成立就得到值 A，不成立就得到值 B。

看範例，a 是 10，b 是 20。a 大於 b 嗎？不是，所以 max 得到 b，也就是 20。下面一樣的寫法，也可以用來選擇要輸出的字串，結果是「b 較大」。

三元運算子可以巢狀使用，但超過兩層就會很難閱讀，建議避免。
-->

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

<!--
那三元運算子和 if-else 要怎麼選擇？

三元運算子只要一行，還可以直接把結果存進變數，很適合簡單的二選一。但如果邏輯比較複雜，或是每個分支要做好幾件事，用 if-else 會清楚很多。

所以原則是：「簡單的二選一用三元運算子，複雜的邏輯用 if-else。」
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# switch 敘述

<!--
接下來是第二種判斷方式：switch。當我們要根據一個值，分成很多種情況處理時，switch 會比一長串的 if-else 更清楚。
-->

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

<!--
我們先看傳統的 switch 寫法。

switch 後面的括號放要判斷的值，可以是整數、字元、字串或列舉。接著每一個 case 後面放一個值，符合的話，就執行冒號後面的程式。最後的 default 是所有 case 都不符合時要執行的部分，可以省略。

看範例，day 是 3，符合 case 3，所以印出星期三。

注意每個 case 後面都有 break。break 的作用是跳出 switch，如果忘了寫，程式會繼續往下執行下一個 case，這個現象叫做 fall-through。
-->

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

<!--
fall-through 有時候是陷阱，但用對了也很方便。

看這個例子，case 1 到 case 5 都沒有寫任何程式，也沒有 break。day 是 2，符合 case 2 之後，程式會一路往下貫穿，直到 case 5 印出工作日，再遇到 break 跳出。

所以，多個值要執行同一段程式時，可以刻意省略中間的 break，讓它們共用同一段邏輯。
-->

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

<!--
從 Java 7 開始，switch 也可以判斷字串。

看範例，season 是「春」，符合第一個 case，所以印出 Spring。

要注意，字串比較是有區分大小寫的。如果資料來自使用者輸入，建議先統一轉成大寫或小寫，再進行判斷，避免使用者輸入的大小寫不同而判斷失敗。
-->

---

# Java 14+ Switch Expression 語法對比

| 特性 | 傳統 switch | Switch Expression (Java 14+) |
| --- | --- | --- |
| 語法符號 | `case 值:` + `break` | `case 值 ->` |
| Fall-through | 有（忘記 break 就貫穿） | 無（自動隔離每個 case） |
| 回傳值 | 不能直接賦值 | 可直接賦值給變數 |
| 多值 case | 需連寫多個 case | `case A, B, C ->` 逗號分隔 |
| 強制完整性 | 不強制（無 default 也行） | 必須涵蓋所有可能值 |

<!--
從 Java 14 開始，switch 有了新的寫法，叫做 switch expression。我們先比較一下新舊寫法的差別。

新寫法用箭頭代替冒號，而且每個 case 都是獨立的，不會有 fall-through，也就不用再寫 break。它可以直接把結果存進變數。多個值可以寫在同一個 case，用逗號分隔。

另外，switch expression 必須涵蓋所有可能的值，通常要寫 default，這樣可以避免漏掉某些情況。
-->

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

<!--
我們來看實際的寫法。

這次 switch 放在等號的右邊，結果直接存進 dayName。每個 case 用箭頭，右邊就是這個情況的結果，不需要 break。

day 是 3，所以 dayName 得到星期三。跟傳統寫法比起來，程式更短，也不用擔心忘了寫 break。

注意最後的大括號後面，要加上分號，因為整個 switch 是一個指定敘述的一部分。
-->

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

<!--
這個範例用 switch expression 計算每個月有幾天。

第一個 case 一次列出了 1、3、5、7、8、10、12，這些月份都是 31 天；4、6、9、11 月是 30 天。

2 月比較特別，要先判斷是不是閏年，需要好幾行程式。這時候可以在箭頭後面接一對大括號，在裡面寫多行邏輯，最後用 yield 把結果交出來。

月份是 8，所以印出 8 月有 31 天。
-->

---

# Java 17 Pattern Matching for switch（預覽特性）

Java 17 引入 switch 型別模式比對（JEP 406，預覽功能）：

| 語法 | 說明 |
| --- | --- |
| `case Integer i ->` | 比對型別並自動綁定變數 `i` |
| `case String s ->` | 比對型別並自動綁定變數 `s` |
| `case String s when s.length() > 0 ->` | 加條件守衛（guarded pattern）|
| `case null ->` | 明確處理 null 值，不再拋 NPE |

<!--
Java 17 為 switch 加入了一個新功能，叫做型別模式比對。它在 Java 17 還是預覽功能。

以前的 switch 只能比較值，現在可以直接比較型別。例如 case Integer i，意思是如果這個物件是 Integer，就把它當成整數 i 來使用。

還可以加上 when 條件，進一步篩選。另外，case null 可以直接處理 null 的情況，不會再因為 null 而出錯。
-->

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

<!--
我們看實際的範例。describe 方法接收任何型別的物件。

如果是整數，就回傳「整數」加上它的值。如果是空字串，回傳「空字串」；其他字串，就回傳「字串」加上內容。注意空字串的 case 要放在一般字串的前面，因為 switch 會從上往下比對。如果是 null，就回傳「null 值」；其他型別則走 default。

下面的說明提到，這個功能在 Java 17 是預覽版，要加上 enable-preview 的選項才能使用；到了 Java 21，就成為正式功能了。
-->

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

<!--
最後一個進階用法，是 sealed 類別搭配 switch。

sealed 可以限制哪些類別能夠實作它。這裡的 Shape 只允許 Circle 和 Rectangle 兩種。

因為編譯器知道 Shape 只可能是這兩種，所以 area 方法裡的 switch 只要處理圓形和矩形，就不需要 default。如果之後又新增了一種形狀卻忘了處理，編譯器會直接提醒我們。

sealed 和 record 之後的章節會再詳細介紹，這裡先感受一下它們和 switch 搭配起來的好處。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 專題實作

<!--
學完 if 和 switch，我們用三個小專題，把這些判斷方式實際用一遍。
-->

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

<!--
第一個專題是 BMI 計算。BMI 的公式是體重公斤數，除以身高公尺數的平方。

計算出來之後，要依照這張表判斷等級：小於 18.5 是體重過輕，18.5 到 24 是正常，24 到 27 是過重，27 以上是肥胖。

這種「依照數值範圍分成好幾級」的情況，正好適合用 if-else if-else 鏈。
-->

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

<!--
我們看程式碼。體重 70 公斤、身高 1.75 公尺，計算出 BMI，再用 printf 印到小數點後兩位，大約是 22.86。

接著用 if-else if-else 鏈判斷等級，條件從小排到大：小於 18.5、小於 24、小於 27，最後的 else 就是肥胖。22.86 符合第二個條件，所以印出體重正常。

注意這裡只需要寫「小於 24」，不用再寫「大於等於 18.5」，因為能走到這一個分支，就表示前面的條件已經不成立了。
-->

---

# 專題二：生肖判斷

中國生肖依年份除以 12 的餘數判斷：

| 餘數 | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 生肖 | 猴 | 雞 | 狗 | 豬 | 鼠 | 牛 | 虎 | 兔 | 龍 | 蛇 | 馬 | 羊 |

<!--
第二個專題是生肖判斷。

生肖每 12 年循環一次，所以可以用年份除以 12 的餘數來判斷。餘數 0 是猴，1 是雞，2 是狗，一直到 11 是羊。

像這種「一個值對應一個結果」的情況，就很適合用 switch。
-->

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

<!--
程式先算出 2024 除以 12 的餘數，結果是 8。

接著用 switch expression，把 0 到 10 的每一個餘數對應到一個生肖，剩下的 11 由 default 處理，也就是羊。

餘數 8 對應到龍，所以印出 2024 年是龍年。用 switch expression 寫起來非常整齊，每一行就是一個對應關係。
-->

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

<!--
第三個專題是火箭升空倒數，它把 if 判斷放進了迴圈裡。

for 迴圈讓 i 從 10 倒數到 0，迴圈下一章會詳細介紹。每一次迴圈，都會用 if-else if-else 判斷 i 的值：等於 0 時印出點火升空；小於等於 3 時印出準備；其他時候只印出數字。

所以執行時會先印出 10 到 4，接著 3、2、1 會加上準備，最後印出點火升空。
-->

---
layout: default
---

# 練習一：成績等第轉換
### 任務說明

請撰寫一個 Java 程式，接收一個整數分數（0–100），依下列規則輸出等第：

| 分數範圍 | 90 ~ 100 | 80 ~ 89 | 70 ~ 79 | 60 ~ 69 | 0 ~ 59 |
| --- | --- | --- | --- | --- | --- |
| 等第 | A | B | C | D | F |

**輸入範例：** `score = 83`  
**輸出範例：** `等第：B`

<!--
接下來我們動手練習。

第一題是成績等第轉換。輸入 0 到 100 的分數，90 分以上是 A，80 到 89 是 B，70 到 79 是 C，60 到 69 是 D，59 分以下是 F。例如輸入 83，就要輸出等第 B。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
-->

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

<!--
我們來看提示。

用 if-else if-else 鏈，從最高的分數段開始往下判斷：先判斷大於等於 90，再判斷大於等於 80，依序往下。最後的 else，就涵蓋了所有 59 分以下的情況，也就是 F。

參考結構裡空白的地方，填的就是大於等於 80。
-->

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

<!--
第二題是星期幾判斷器，請用 switch expression 來寫。

輸入 1 到 7，分別代表星期一到星期日。星期一到星期五輸出工作日，星期六和星期日輸出假日，其他數字則輸出無效輸入。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 10 分鐘實作。
-->

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

<!--
我們來看提示。

用 switch 搭配箭頭寫法。星期一到五可以寫在同一個 case，用逗號分隔 1 到 5；星期六和星期日，也就是 6 和 7，寫在另一個 case；超出範圍的數字，交給 default。

所以範例裡空白的地方，要填的是 6 和 7。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們讓程式學會了做決定。

if 有三種形式，多條件判斷時要從最嚴格排到最寬鬆；簡單的二選一，可以用三元運算子。switch 適合一個值對應多種情況，傳統寫法要注意 break，Java 14 之後的 switch expression 則更簡潔、也更安全。

下一章，我們要學迴圈，讓程式可以重複做同一件事。

【講師】開放 Q&A。
-->

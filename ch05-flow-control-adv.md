---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 程式流程控制（進階／自學）
routeAlias: ch05adv
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
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">進階自學內容</p>
  <Link to="home" style="color:#9dc4c4;font-size:0.85rem;margin-top:2rem;text-decoration:none;letter-spacing:0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
歡迎來到流程控制的進階篇！如果說 if 和傳統 switch 是手排車，那今天要介紹的這幾招，就是 Java 給工程師的「自排升級包」。

【為什麼要學這個？】
你可能會想：「傳統 switch 不是能用嗎？為什麼還要學新的？」想像一下，同樣是切菜，一個用菜刀慢慢切，一個用食物處理機。兩種都能完成任務，但效率跟安全性差很多。Switch Expression、Pattern Matching 和 Sealed Class，就是 Java 給你的「食物處理機」。

【今天學完你會能做什麼】
學完這份自學內容，你會看得懂、也寫得出近年企業專案中常見的現代化 switch 寫法，並且理解為什麼 Java 要演化出這些語法。這在面試或閱讀新版原始碼時，都會是你的加分項。
-->

---
layout: default
---

# Outline

- **Switch Expression**：`->` 箭頭語法、多值 case、`yield` 回傳值
- **Pattern Matching for switch**：型別比對、條件守衛、`case null`
- **Sealed Class 搭配 switch**：`sealed` 介面與完整型別覆蓋
- **練習題**：2 題（星期幾判斷器、綜合應用，各含任務說明 + 解題提示）

<!--
【核心說明】
這份自學內容，其實是在講同一件事的三個進化階段：switch 怎麼從「老舊開關」一步步變成「智慧分類員」。

【生活化比喻】
先學會用箭頭語法取代 break（Switch Expression），接著讓它能看穿物件的真實型別（Pattern Matching），最後再用 sealed 把所有可能性「鎖死」，讓編譯器幫你把關（Sealed Class）。三者環環相扣，建議依序往下學。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# Switch Expression

<!--
【開場白】
我們先從最基礎的進化開始：把傳統 switch 換成「箭頭版」。
-->

---
layout: default
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
【核心說明】
這是 Java 的「現代化改造」。

【生活化比喻】
傳統 switch 就像是老舊的機械開關，得自己加保險絲（break）。現代 switch（用 -> 箭頭）就像是數位觸控面板，點一下就到位，不用擔心煞車失靈，還能直接把結果丟給你。

💼 業界實務：
現在許多新專案的 Code Style 已經規定優先使用 Switch Expression，因為它能讓編譯器幫你檢查是否漏掉某個分支，減少潛在的 Bug。
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
【範例目的】
這個範例要示範：switch 現在可以「直接生出一個值」，不再只是單純的分支跳轉。

【帶讀關鍵行】
看 `String dayName = switch (day) { ... };` 這一行，整個 switch 本身就是一個「運算式」，運算完的結果直接指派給 dayName，中間完全不需要 break。

⚠️ 易錯點提醒：
每個分支結尾要用分號 `;` 結束（最後一個 `};` 別漏了），這跟傳統 switch 的語法不太一樣，剛開始很容易漏打。

【預期結果】
day = 3，符合 `case 3 -> "星期三"`，所以印出「星期三」。
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
【核心說明】
現代 switch 能讓你一行抵五行。看到 case 1, 3, 5... -> 31 了嗎？這太優雅了！

【逐步解說】
如果你的邏輯很複雜，需要在大括號裡運算，最後請用 yield 把結果「吐」出來。注意：yield 只有在這種賦值模式下才有用喔。

⚠️ 易錯點提醒：
忘記寫 `yield`，只在大括號裡面寫 `leap ? 29 : 28;` 而不回傳，編譯器會直接報錯，因為它不知道這個分支該「交出」什麼值。

【預期結果】
month = 8，符合第一個多值 case，days = 31，印出「8 月有 31 天」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# Pattern Matching for switch

<!--
【開場白】
箭頭語法只是開胃菜，接下來這招才是真正的大絕招：讓 switch 看穿物件的「真實身分」。
-->

---
layout: default
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
【核心說明】
這是 Java 17 的「大絕招」，它讓 switch 變成了「超能力者」。

【生活化比喻】
以前 switch 只能比數字或字串，現在它能看透物件的本質。「你是一個整數嗎？」、「你是一個空字串嗎？」。這讓程式碼看起來像是在跟電腦直接對話。

💼 業界實務：
這種寫法在處理「一個方法可能收到多種型別參數」的情境（例如解析 JSON、處理事件物件）時非常實用，能取代一長串的 `instanceof` 判斷。
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
【範例目的】
這個範例示範同一個 switch 如何依「型別」分流，而不只是依「值」分流。

【帶讀關鍵行】
看 `case Integer i -> "整數：" + i;`。這行不但判斷 o 是不是整數，還順便幫它取了名字叫 i，後面可以直接拿來用。還有那個 `when` 條件守衛，就像是門口的保全不但看證件，還要檢查你有沒有戴口罩。

⚠️ 易錯點提醒：
`case String s when s.isEmpty()` 一定要寫在 `case String s ->` 前面。switch 是依序比對，順序顛倒的話，空字串永遠會先被一般的 String 分支接走。

【預期結果】
如果傳入空字串 ""，會印出「空字串」；傳入 100，會印出「整數：100」；傳入 null，會印出「null 值」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# Sealed Class 搭配 switch

<!--
【開場白】
最後一招，是把前面兩招組合起來，再加上一個「保險裝置」：sealed class。
-->

---
layout: default
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
【核心說明】
這是 Java 為了追求「安全」而設計的。

【生活化比喻】
如果你有一間只准進「圓形」和「方形」的房間，那 switch 就只需要處理這兩種可能。如果你漏寫了，編譯器會咆哮：「那個三角形怎麼辦？」。這讓你的程式碼像鋼鐵一樣堅固。

💼 業界實務：
`sealed` + `record` + Pattern Matching for switch 是 Java 近年主推的組合，常用在表示「有限種可能性」的資料模型，例如表示一個請求的處理結果只會是「成功」、「失敗」或「逾時」三種狀態。
-->

---
layout: default
---

# 練習一：星期幾判斷器
### 任務說明

使用 **Switch Expression（Java 14+）** 撰寫程式：

- 輸入整數 1–7，分別對應星期一到星期日
- 星期一至星期五輸出：`工作日`
- 星期六、星期日輸出：`假日`
- 其他數值輸出：`無效輸入`

**輸入範例：** `day = 6`  
**輸出範例：** `假日`

<!--
【任務鋪陳】
剛才我們學了 Switch Expression 的箭頭語法跟多值 case，現在來練習把它真正用出來。

【問題引導】
不要用舊的 `case 1: ... break;` 了，試著用箭頭 `->` 和多值 case `1, 2, 3, 4, 5`。想一想：星期六和星期日可以怎麼合併寫成一個 case？
-->

---
layout: default
---

# 練習一：解題提示

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
【逐步解說】
`case 1, 2, 3, 4, 5 -> "工作日";`。就這一行，搞定週一到週五。剩下的就是把週末兩天填進那個空格裡，這就是現代 Java 的力量！
-->

---
layout: default
---

# 綜合練習：包裹運費試算器
### 任務說明

請設計一個依「包裹型態」與「重量」計算運費的程式，綜合運用本份自學的三個概念：

1. 定義 `sealed interface Package permits Document, Box`，`Document(double weight)` 與 `Box(double weight, boolean fragile)` 兩個 `record`。
2. 撰寫 `calcFee(Package p)`，用 **Pattern Matching for switch** 依型別計算運費：
   - `Document`：每公斤 20 元
   - `Box`（易碎 `fragile = true`）：每公斤 50 元
   - `Box`（不易碎）：每公斤 35 元
3. 用 **Switch Expression** 搭配 `yield` 計算結果（運費需四捨五入到整數）。

**輸入範例：** `new Box(3.0, true)`  
**輸出範例：** `運費：150 元`

<!--
【任務鋪陳】
我們從 Switch Expression 開始，學到了 Pattern Matching，最後又認識了 Sealed Class。這題就是要把三者串成一條線：用 sealed 鎖住型別範圍，再用 Pattern Matching 依型別與條件分流，最後用 Switch Expression 算出結果。

【問題引導】
想一想：為什麼這裡用 `sealed interface` 而不是一般的 `interface`？如果之後有人想新增一個 `Pallet`（棧板）型別卻忘了在 `calcFee` 裡處理，sealed 加上 switch 的完整性檢查會怎麼幫你抓到這個漏洞？
-->

---
layout: default
---

# 綜合練習：解題提示

### 提示說明

1. `Box` 需要在 `case` 中加 `when fragile` 條件守衛來區分易碎與否。
2. 易碎判斷要寫在前面，否則一般 `Box` 的 case 會先比對成功。
3. 最後用 `Math.round(...)` 處理四捨五入，再用 `yield` 回傳 `long`／`int`。

```java
static int calcFee(Package p) {
    return switch (p) {
        case Document d -> (int) Math.round(d.weight() * 20);
        case Box b when b.fragile() -> {
            yield (int) Math.round(b.weight() * 50);
        }
        case Box b -> (int) Math.round(b.weight() * 35);
    };
}
```

<!--
【逐步解說】
看 `case Box b when b.fragile() ->`，這就是把 Pattern Matching 的型別比對跟條件守衛結合在一起。因為 `Document` 跟 `Box` 是 `Shape` 的所有子型別（sealed 限定），所以這裡不需要寫 `default`，編譯器會自己確認所有情況都覆蓋到了。
-->

---
layout: end
---

# Q & A

<!--
【開場白】
這份自學內容帶大家看完了 switch 的「進化三部曲」：從箭頭語法的 Switch Expression，到能看穿型別的 Pattern Matching，再到用 sealed 把可能性鎖死的安全寫法。

如果在自學過程中遇到 `--enable-preview` 編譯不起來，或是 `yield` 跟 `case` 語法搞混了，都可以記錄下來，下次上課時提出來討論。
-->

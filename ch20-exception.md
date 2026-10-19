---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 程式異常的處理
routeAlias: ch20
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
    程式異常的處理
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「讓程式不因異常而中止」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章的主題是程式異常的處理。

程式在執行時，難免會遇到意外：使用者輸入了文字而不是數字、要讀取的檔案不存在、網路突然斷線。如果沒有處理，程式就會直接中止，使用者只看到一堆看不懂的錯誤訊息。

學完這一章，我們就能預先處理這些異常狀況，讓程式遇到問題時，仍然可以優雅地應對，而不是直接當掉。
-->

---
layout: default
---

# Outline

- **認識程式錯誤的類別**
- **處理異常方法**
  - try-catch / finally / try-with-resources
  - Throwable 類別方法
  - throw / throws / 自訂異常類別
- **自定義錯誤代碼和訊息**
- **實作練習**

<!--
這一章我們會先認識程式錯誤的類型，以及常見的異常。

接著是這一章的重點：處理異常的方法，包括 try-catch、finally 和 try-with-resources，以及用 throw 和 throws 主動拋出異常、自訂異常類別。最後學習怎麼用 enum 定義錯誤代碼和訊息，這是實際開發 API 時很常見的做法。

中間會穿插不少練習。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識程式錯誤的類別

<!--
我們先來認識，程式的錯誤有哪幾種。
-->

---
layout: default
---

# 程式錯誤的三大類型

| 錯誤類型 | 發生時機 | 說明 |
| --- | --- | --- |
| 語法錯誤 (Syntax Error) | 編譯階段 | 語法不正確，IDE 會警告 |
| 語意錯誤 (Semantic Error) | 執行後才發現 | 邏輯錯誤，結果不符預期 |
| 執行期間錯誤 (Runtime Error) | 程式執行時 | 語意正確但發生未預期狀況 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>本章重點：</b>執行期間錯誤，Java 將這類錯誤稱為「異常（Exception）」
</div>

<!--
程式的錯誤可以分成三種。

第一種是語法錯誤，在編譯階段就會被發現，例如少了分號，IDE 會直接標示出來。第二種是語意錯誤，也就是邏輯錯誤，程式可以執行，但結果不符合預期，例如把加法寫成減法。第三種是執行期間的錯誤，語法和邏輯都沒問題，卻在執行時遇到了沒預料到的狀況。

這一章的重點，就是第三種。在 Java 裡，這類錯誤稱為「異常」，英文是 Exception。
-->

---

# 認識簡單的異常實例

除數為 0 的異常：

```java
public static int myDiv(int x, int y) {
    return x / y;
}
public static void main(String args[]) {
    System.out.println(myDiv(6, 2));
    System.out.println(myDiv(8, 0)); // 異常發生，程式中止
    System.out.println(myDiv(9, 4)); // 不會執行到
}
```

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ Java 動作：拋出異常訊息並終止程式執行<br>
<code>java.lang.ArithmeticException: / by zero</code>
</div>

<!--
我們看一個最簡單的異常例子：除以 0。

myDiv 方法回傳 x 除以 y。在 main 裡呼叫三次：6 除以 2 沒問題，印出 3；但 8 除以 0，就發生了異常。

這時候 Java 會拋出 ArithmeticException，錯誤訊息是除以零，並且立刻中止程式。所以第三行的 9 除以 4，根本沒有機會執行。
-->

---

# 其他常見的異常

| 異常類別 | 觸發情境 |
| --- | --- |
| `NullPointerException` | 對 `null` 物件呼叫方法 |
| `NumberFormatException` | 將非數值字串轉換成整數 |
| `StringIndexOutOfBoundsException` | 字串索引超出範圍 |
| `ArrayIndexOutOfBoundsException` | 陣列索引超出範圍 |
| `InputMismatchException` | 使用者輸入的類型錯誤 |

<!--
除了除以 0，還有幾種很常見的異常。

對 null 呼叫方法，會發生 NullPointerException。把不是數字的字串轉成整數，會發生 NumberFormatException。字串或陣列的索引超出範圍，會分別發生 StringIndexOutOfBoundsException 和 ArrayIndexOutOfBoundsException。使用者輸入的型別不對，則會發生 InputMismatchException。

這些異常，在前面的章節其實都已經遇過了。
-->

---

# 其他常見的異常 — 範例

```java
// NullPointerException
String str = null;
System.out.println(str.length());
// NumberFormatException
int x = Integer.parseInt("Taipei");
// StringIndexOutOfBoundsException
String s = "ABCD";
char c = s.charAt(10);
```

<!--
我們看看這些異常是怎麼觸發的。

第一個，str 是 null，呼叫 length 就會發生 NullPointerException。第二個，把 Taipei 這個字串轉成整數，會發生 NumberFormatException。第三個，ABCD 只有 4 個字元，卻要取索引 10 的字元，會發生 StringIndexOutOfBoundsException。
-->

---

# 更清晰的 NullPointerException

| 特性 | 說明 |
| --- | --- |
| 具體錯誤訊息 | JVM 會詳細指出是哪個變數或方法呼叫回傳了 `null` |

```java
// 若 user 為 null，會明確指出「因為 user 是 null」
User user = null;
System.out.println(user.getName());
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>除錯更方便：</b> 從 Java 14 起，錯誤訊息會直接寫明：<code>Cannot invoke "User.getName()" because "user" is null</code>。
</div>

<!--
NullPointerException 是 Java 最常見的異常。以前它的錯誤訊息很簡略，常常看不出是哪一個變數是 null。

從 Java 14 開始，錯誤訊息變得更清楚了。看範例，user 是 null，呼叫 getName 時，錯誤訊息會直接寫出：無法呼叫 User.getName，因為 user 是 null。

這樣一看就知道問題出在哪裡，除錯方便很多。
-->

---
layout: default
---

# 練習 1-1：觸發並觀察例外
### 任務說明

分別撰寫程式碼，刻意觸發以下三種例外，並執行觀察錯誤訊息：

1. `NullPointerException` — 對 `null` 的字串呼叫 `length()`
2. `ArrayIndexOutOfBoundsException` — 存取陣列不存在的索引
3. `NumberFormatException` — 將非數字字串轉成整數

每段程式一次只執行一種（其餘用註解隱藏），記錄輸出的錯誤訊息。

<!--
接下來我們動手練習。

練習 1-1，請分別寫程式，刻意觸發三種異常：對 null 字串呼叫 length、存取陣列不存在的索引，以及把非數字的字串轉成整數。

一次只執行一種，其他的先用註解隱藏，並記錄下每一種錯誤訊息的內容。

我們先暫停一下，自己動手試試看。
-->

---
layout: default
---

# 練習 1-1：解題提示
### 提示說明

```java
String s = null;
System.out.println(s.length());      // NullPointerException

int[] arr = new int[3];
System.out.println(arr[5]);           // ArrayIndexOutOfBoundsException

int n = Integer.parseInt("Java");     // NumberFormatException
```

- 觀察 Java 14+ 的 NPE 訊息是否會指出是哪個變數為 `null`
- 三種例外發生後，程式都會立即中止，後面的程式碼不會執行

<!--
我們來看提示。

三段程式分別是：對 null 的 s 呼叫 length；對長度 3 的陣列存取索引 5；把字串 Java 轉成整數。

執行時可以觀察兩件事：第一，NullPointerException 的訊息，是不是有指出哪個變數是 null；第二，不管是哪一種異常，發生之後程式都會立刻中止，後面的程式碼不會執行。
-->

---
layout: default
---

# 練習 1-2：除法計算機與錯誤分類
### 任務說明

撰寫 `divide(int a, int b)` 方法，回傳 `a / b` 的結果（**先不要**加任何例外處理）：

1. 呼叫 `divide(10, 2)` 與 `divide(10, 0)`，觀察輸出與程式中止情形
2. 對照「程式錯誤的三大類型」表格，分別寫出一行範例程式碼：
   - 語法錯誤（編譯不會過）
   - 語意錯誤（能執行，但結果不符預期）
   - 執行期錯誤（編譯與邏輯都正確，執行時才出包）

<!--
練習 1-2，請寫一個 divide 方法，回傳 a 除以 b 的結果，先不要加任何異常處理。

呼叫 divide 10、2 和 divide 10、0，觀察輸出和程式中止的情形。

另外，請對照剛才的三種錯誤類型，分別寫出一行範例程式碼：一個語法錯誤、一個語意錯誤，以及一個執行期間的錯誤。

一樣先暫停一下，自己動手寫寫看。
-->

---
layout: default
---

# 練習 1-2：解題提示
### 提示說明

```java
static int divide(int a, int b) {
    return a / b;
}
```

- `divide(10, 0)` 會拋出 `ArithmeticException`，程式立即中止
- 語法錯誤範例：`int x = 5` （少了分號，編譯失敗）
- 語意錯誤範例：想算總和卻寫成 `total = a - b`（能跑，但答案錯）
- 執行期錯誤範例：`divide(10, 0)`（除以 0）

<!--
我們來看提示。

divide 10、0 會拋出 ArithmeticException，程式立刻中止。

三種錯誤的範例：語法錯誤，例如 int x 等於 5 卻少了分號，編譯就會失敗；語意錯誤，例如要算總和卻寫成相減，程式能跑，但答案是錯的；執行期間的錯誤，就是剛才的 divide 10、0。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 處理異常方法

<!--
接下來是這一章的重點：怎麼處理異常。
-->

---
layout: default
---

# 傳統防呆方式 — if/else

在可能發生異常的地方用 `if` 預先檢查：

```java
public static int myDiv(int x, int y) {
    if (y == 0) {
        System.out.print("除數為0異常發生：");
        return 0;
    } else {
        return x / y;
    }
}
```

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>缺點：</b>程式碼冗長、難以維護；異常情境多時效率低落
</div>

<!--
在學習 Java 的異常處理之前，我們先看傳統的做法：用 if 預先檢查。

看範例，myDiv 在計算之前，先判斷 y 是不是 0。如果是，就印出提示並回傳 0；否則才進行除法。

這個方法雖然有效，但缺點很明顯：每一個可能出錯的地方都要寫一堆檢查，正常的邏輯被淹沒在檢查裡，程式碼又長又難維護。異常的情況越多，問題就越嚴重。
-->

---

# Java 的處理異常方式

當程式發生異常時，Java 會：

1. 產生對應的**異常物件（Exception Object）**
2. 在執行緒（thread）中搜尋**異常處理程式碼**

- **狀況 1：找到異常處理程式碼** → 交給它處理，處理完後可繼續往下執行
- **狀況 2：找不到** → 往前回溯呼叫鏈，直到 `main`；若仍找不到，輸出所有異常原因與回溯紀錄，程式中止

<!--
Java 處理異常的方式不一樣。當程式發生異常時，Java 會先產生一個異常物件，記錄發生了什麼問題，接著尋找處理這個異常的程式碼。

這時候有兩種狀況。如果找到了處理的程式碼，就交給它處理，處理完之後，程式可以繼續往下執行。如果找不到，就會沿著方法的呼叫順序往回找，一直找到 main；如果還是沒有，就印出異常的原因和呼叫紀錄，然後中止程式。

這就像公司裡遇到問題，先由自己處理；處理不了，就往上呈報給主管；如果一路到老闆都沒人處理，事情就停擺了。
-->

---

# 異常類別階層 — Throwable

```text
Throwable
├── Error（嚴重系統錯誤，通常不需處理）
│   ├── OutOfMemoryError
│   └── StackOverflowError
└── Exception（程式可處理的異常）
    ├── RuntimeException（非檢查異常）
    │   ├── ArithmeticException
    │   └── NullPointerException
    └── IOException（檢查異常）
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Throwable</b> 是所有異常的父類別；所有異常物件均為其子類別或衍生類別的實體
</div>

<!--
所有的異常，都是 Throwable 這個類別的子類別。

Throwable 下面分成兩大類。Error 是嚴重的系統錯誤，例如記憶體不足、堆疊溢位，這類錯誤通常程式本身無法處理。Exception 則是程式可以處理的異常。

Exception 下面又分成兩種：RuntimeException，例如除以零、NullPointerException；以及其他的異常，例如處理檔案時的 IOException。
-->

---

# 非檢查異常 vs 檢查異常

| 類型 | 說明 | 代表類別 |
| --- | --- | --- |
| 非檢查異常 (Unchecked) | 編譯器不強制處理 | `RuntimeException` 及其子類別 |
| 檢查異常 (Checked) | 編譯器強制要求處理 | `IOException`、`SQLException` 等 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>檢查異常</b>若未加異常處理，程式在編譯階段就會出現錯誤
</div>

<!--
這兩種 Exception 有一個重要的差別。

RuntimeException 和它的子類別，叫做非檢查異常，編譯器不會強制我們處理。因為它們通常是程式的邏輯問題，應該修正程式，而不是捕捉它。

其他的 Exception，例如 IOException、SQLException，叫做檢查異常，編譯器會強制我們處理。因為像檔案不存在、資料庫連不上這種狀況，不是程式寫錯，而是外部環境造成的，一定要事先準備好應對的方式。

如果沒有處理檢查異常，程式在編譯時就會報錯。
-->

---

# try-catch 語法

```java
try {
    // 可能發生異常的敘述
} catch (異常類別 e) {
    // 異常處理程式碼（Exception Handler）
}
```

- **try 區塊**：放置可能發生異常的程式碼；一旦有異常，立即離開 try 進入 catch
- **catch 區塊**：`異常類別` 指定要捕捉的類別；`e` 為異常物件，可取得異常訊息

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 若 catch 的異常類別不符合實際發生的異常，會跳出 catch，程式繼續往下執行
</div>

<!--
處理異常最基本的語法，是 try-catch。

try 區塊放可能發生異常的程式碼。一旦發生異常，就會立刻離開 try，跳到 catch 區塊。

catch 後面的括號，指定要捕捉哪一種異常，變數 e 就是異常物件，可以從它取得錯誤的訊息。catch 區塊裡的程式碼，就是處理這個異常的方式。

如果發生的異常，和 catch 指定的類別不符合，就不會被這個 catch 處理。
-->

---

# 進入 catch 的條件

進入 catch 區塊需同時符合兩個條件：

1. `try` 區塊中的程式碼**有發生異常**
2. `catch` 中定義的異常類別**有捕捉到**
   - 捕捉到：catch 的類別是發生異常的類別，或其**父類別**

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>一般實踐：</b>catch 裡面用 <code>Exception</code> 可捕捉大多數執行期異常
</div>

<!--
要進入 catch 區塊，必須同時符合兩個條件。

第一，try 區塊裡真的發生了異常。第二，catch 指定的異常類別，有捕捉到這個異常，也就是 catch 的類別，是發生的異常類別本身，或是它的父類別。

所以，如果 catch 寫的是 Exception，因為它是大多數異常的父類別，就能捕捉到大部分的異常。
-->

---

# try-catch 範例

```java
public static String myDiv(int x, int y) {
    try {
        return Integer.toString(x / y);
    } catch (ArithmeticException e) {
        System.out.println("除數為0的異常：" + e);
        return "執行除法運算時須避開除數為0的";
    }
}
```

<!--
我們把剛才的除法改用 try-catch。

在 try 裡計算 x 除以 y，並把結果轉成字串回傳。如果發生 ArithmeticException，就進入 catch，印出異常的內容，並回傳一段提示文字。

這樣一來，就算除數是 0，程式也不會中止，呼叫的地方會拿到提示訊息，後面的程式可以繼續執行。
-->

---

# Throwable 類別的方法

| 方法 | 說明 |
| --- | --- |
| `String getMessage()` | 傳回異常的說明字串 |
| `String toString()` | 傳回異常的完整訊息（含類別名稱） |
| `void printStackTrace()` | 回溯顯示程式呼叫的執行過程 |

<!--
從異常物件 e，可以取得錯誤的資訊。Throwable 類別提供了三個常用的方法。

getMessage 回傳異常的說明文字。toString 回傳完整的訊息，包含異常的類別名稱。printStackTrace 則會印出異常發生時，程式一路呼叫的過程，在除錯時非常有用。
-->

---

# Throwable 方法 — 範例

```java
try {
    return Integer.toString(x / y);
} catch (ArithmeticException e) {
    System.out.println("異常：" + e);
    System.out.println("toString：" + e.toString());
    System.out.println("getMessage：" + e.getMessage());
    e.printStackTrace();
    return "執行除法運算時須避開除數為0的";
}
```

<!--
看範例，在 catch 裡分別呼叫這三個方法。

直接印出 e，和呼叫 toString 的結果一樣，會看到類別名稱加上錯誤說明。getMessage 只有錯誤說明，也就是除以零。printStackTrace 則會印出好幾行，告訴我們異常是在哪個方法、哪一行發生的。

實務上，要記錄錯誤時，會使用專門的日誌工具，而不是直接 printStackTrace。
-->

---

# 多個 catch 區塊

| 寫法 | 說明 |
| --- | --- |
| 多個 `catch` 區塊 | 依序匹配，符合即執行對應 catch |
| 單一 `catch` 捕捉多類別 | 多個異常類別合併到一個 catch |

```java
catch (IOException e) { ... }
catch (ArithmeticException e) { ... }
// 或：合併寫法
catch (IOException | ArithmeticException e) { ... }
```

<!--
一段程式可能發生好幾種不同的異常，這時候可以寫多個 catch 區塊，依序比對，符合哪一個，就執行哪一個。

如果幾種異常的處理方式都一樣，也可以合併在同一個 catch 裡，用直線符號分隔異常的類別。
-->

---

# 多個 catch 區塊 — 範例

```java
try {
    FileInputStream fio = new FileInputStream("ABC");
    System.out.println(5 / 0);
} catch (IOException e) {
    System.out.println("發生 IO 錯誤：" + e);
    return;
} catch (ArithmeticException e) {
    System.out.println("發生計算錯誤：" + e);
}
```

<!--
看範例，try 裡有兩個可能出錯的地方：開啟一個不存在的檔案，以及除以 0。

如果是檔案的問題，會發生 IOException，進入第一個 catch；如果是計算的問題，會發生 ArithmeticException，進入第二個 catch。

在這個例子裡，開檔就先出錯了，所以會進入第一個 catch，後面的除法根本不會執行。
-->

---
layout: default
---

# 練習 2-1：多 catch 實作
### 任務說明

設計一個程式，讀取使用者輸入的 2 個整數，計算除法結果。
需能正確捕捉以下兩種異常：

1. 除數為 0 — `ArithmeticException`
2. 輸入非數字 — `InputMismatchException`

<!--
練習 2-1，請設計一個程式，讀取使用者輸入的兩個整數，計算除法的結果。

程式要能正確捕捉兩種異常：除數是 0 的 ArithmeticException，以及輸入的不是數字時的 InputMismatchException。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
-->

---
layout: default
---

# 練習 2-1：解題提示
### 提示說明

1. 宣告 `Scanner` 讀取使用者輸入
2. 在 `try` 區塊內讀取兩個整數並做除法
3. 第一個 `catch` 捕捉 `ArithmeticException`
4. 第二個 `catch` 捕捉 `InputMismatchException`
5. 或改用 `|` 語法合併兩個異常類別到同一 catch

<!--
我們來看提示。

先建立 Scanner，在 try 區塊裡讀取兩個整數並做除法。第一個 catch 捕捉 ArithmeticException，第二個 catch 捕捉 InputMismatchException，各自印出適合的提示訊息。

也可以用直線符號，把兩種異常合併在同一個 catch 裡。
-->

---

# 捕捉上層的異常

捕捉一個異常類別時，其**衍生子類別**也可被捕捉。

| 捕捉的類別 | 實際捕捉範圍 |
| --- | --- |
| `IndexOutOfBoundsException` | 含 `Array...` 和 `String...` |
| `RuntimeException` | 含所有 RuntimeException 子類別 |
| `Exception` | 含所有非 Error 的異常 |

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ 多個 catch 時，<b>越廣泛的異常類別放越後面</b>，避免遮蔽更具體的 catch
</div>

<!--
catch 一個異常類別時，它的所有子類別也會一起被捕捉。

例如 catch IndexOutOfBoundsException，就同時能捕捉陣列和字串的索引超出範圍。catch RuntimeException，就能捕捉所有的執行期異常。catch Exception，則能捕捉除了 Error 以外的所有異常。

要注意，寫多個 catch 時，範圍越廣的類別要放越後面。如果把 Exception 放在第一個，後面比較具體的 catch，就永遠沒有機會執行。
-->

---

# 捕捉上層的異常 — 範例

```java
try {
    String str = "Ming-Chi";
    char c = str.charAt(3);
    System.out.println("c字元是：" + c);
    c = str.charAt(10); // 異常發生
    System.out.println("c字元是：" + c);
} catch (IndexOutOfBoundsException e) {
    System.out.println("索引超出範圍：" + e);
}
```

<!--
看範例，字串 Ming-Chi，取索引 3 的字元沒有問題。但取索引 10 時，就發生了 StringIndexOutOfBoundsException。

catch 寫的是它的父類別 IndexOutOfBoundsException，所以一樣能捕捉到，印出索引超出範圍。
-->

---

# finally 區塊

不論 try 是否發生異常，**finally 區塊一定會執行**。

```java
try {
    // 可能發生異常的敘述
} catch (異常類別 e) {
    // 處理異常
} finally {
    // 不論是否異常皆會執行
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>常用於：</b>關閉資源（資料庫連線、檔案、網路串流等）
</div>

<!--
finally 區塊，不論 try 有沒有發生異常，都一定會執行。

它的寫法是接在 try 和 catch 的後面。最常見的用途，是關閉資源，例如資料庫連線、檔案或網路連線。因為不管程式執行成功還是失敗，這些資源都應該要被釋放。
-->

---

# finally 的注意事項

- `finally` 前面必須有 `try` 區塊
- 若 try 無異常：finally 在 try 後執行
- 若 try 有異常且被捕捉：catch 執行完後再執行 finally
- 若 try 有異常但未被捕捉：仍會執行 finally，然後程式中止
- 即使 try 或 catch 有 `return`、`break`、`continue`，finally 仍會執行

<!--
finally 有幾個執行規則。

finally 前面一定要有 try。如果 try 沒有發生異常，finally 會在 try 之後執行。如果發生了異常，而且被捕捉到，會先執行 catch，再執行 finally。如果發生了異常，卻沒有被捕捉，finally 還是會先執行，然後程式才中止。

甚至在 try 或 catch 裡寫了 return、break 或 continue，finally 也還是會執行。
-->

---

# try-with-resources

程式會**自動關閉**在 `try(...)` 括號內宣告的資源：

```java
try (Scanner scanner = new Scanner(System.in)) {
    System.out.println("請輸入 2 個數字：");
    int num1 = scanner.nextInt();
    int num2 = scanner.nextInt();
} catch (Exception e) {
    System.out.println("發生錯誤：" + e);
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 資源必須實作 <code>AutoCloseable</code> 介面（如 Scanner、FileInputStream 等）
</div>

<!--
如果要使用的是需要關閉的資源，Java 7 之後有更好的寫法：try-with-resources。

在 try 後面的括號裡宣告資源，try 區塊結束時，Java 會自動幫我們關閉它，不需要在 finally 裡手動關閉。

看範例，Scanner 宣告在 try 的括號裡，讀完兩個數字之後，就會自動關閉。

能這樣使用的資源，必須實作 AutoCloseable 介面，例如 Scanner、FileInputStream。
-->

---

# try-with-resources 語法增強

| 特性 | 說明 |
| --- | --- |
| 使用外部變數 | 若資源變數是 `final` 或「實質上是 final」，可直接放入 `try(...)` 中 |

```java
// 變數在外部宣告，不需在 try() 內重新宣告
final Scanner scanner = new Scanner(System.in);
try (scanner) {
    int num1 = scanner.nextInt();
} catch (Exception e) {
    System.out.println(e);
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>簡化程式碼：</b> 從 Java 9 開始，不再需要寫成 <code>try (Scanner s = scanner)</code>，程式碼更為簡潔。
</div>

<!--
從 Java 9 開始，try-with-resources 的寫法更簡潔了。

如果資源變數是在外面宣告的，而且是 final，或是實質上沒有被重新指定過，就可以直接把變數名稱放進 try 的括號裡，不需要再重新宣告一次。

看範例，scanner 在外面宣告成 final，try 括號裡直接寫 scanner 就可以了。
-->

---
layout: default
---

# 練習 2-2：try-with-resources
### 任務說明

改寫練習 2-1，使用 try-with-resources 語法管理 `Scanner` 資源，確保程式結束後 Scanner 自動關閉。不再需要在 `finally` 中手動呼叫 `scanner.close()`。

<!--
練習 2-2，請改寫練習 2-1，用 try-with-resources 管理 Scanner，讓程式結束時，Scanner 會自動關閉。

一樣先暫停一下，自己動手寫寫看。
-->

---
layout: default
---

# 練習 2-2：解題提示
### 提示說明

1. 將 `new Scanner(System.in)` 移入 `try(...)` 括號內宣告
2. 移除原本 `finally` 中的 `scanner.close()` 呼叫（若有）
3. catch 仍維持捕捉 `ArithmeticException` 和 `InputMismatchException`
4. 觀察程式執行結果是否與原本相同

<!--
我們來看提示。

把 new Scanner 移到 try 後面的括號裡宣告。如果原本有在 finally 裡呼叫 close，就可以刪掉了。catch 的部分不用改變，一樣捕捉兩種異常。

執行看看，結果應該和原本完全一樣，但程式碼更簡潔，也不會忘記關閉資源。
-->

---

# 自行拋出異常 — throw

允許程式設計師自行定義異常發生的條件，然後主動拋出。

語法：

```java
throw new exception_class("exception message");
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>exception_class</code>：Java 內部或自訂的異常類別<br>
   <code>exception message</code>：自定義的錯誤說明字串
</div>

<!--
除了被動地處理異常，我們也可以依照自己的規則，主動拋出異常，用的是 throw 關鍵字。

語法是 throw，後面接 new 一個異常物件，括號裡放錯誤的說明文字。

例如密碼長度不對、年齡是負數，這些在語法上都不算錯，但不符合我們的業務規則，就可以主動拋出異常。
-->

---

# throw — 範例（密碼長度驗證）

```java
private static void pwdCheck(String pwdStr) throws Exception {
    if (pwdStr.length() >= 5 && pwdStr.length() <= 8) {
        System.out.println("密碼驗證成功：" + pwdStr);
    } else {
        System.out.println("密碼驗證失敗：" + pwdStr);
        throw new Exception("密碼長度不符規定");
    }
}
```

<!--
看一個密碼驗證的例子。

pwdCheck 檢查密碼長度是否在 5 到 8 個字元之間。符合就印出驗證成功；不符合，就印出驗證失敗，並用 throw 拋出一個異常，說明密碼長度不符規定。

因為拋出的是 Exception，屬於檢查異常，所以方法的宣告上要加上 throws Exception，告訴呼叫的人要處理它。
-->

---

# throw — 再次拋出已捕捉的異常

在 catch 區塊中可以將已捕捉的異常再次拋出：

```java
public static void main(String[] args) throws IOException {
    try {
        FileInputStream fio = new FileInputStream("ABC");
        System.out.println(5 / 0);
    } catch (IOException e) {
        throw e;
    } catch (ArithmeticException e) {
        System.out.println("發生計算錯誤：" + e);
    }
}
```

<!--
在 catch 裡，也可以把捕捉到的異常，再次拋出去。

看範例，捕捉到 IOException 之後，直接 throw e，把它交給上一層處理。因為 main 宣告了 throws IOException，所以這個異常會繼續往外傳。

這種做法常用在：目前的位置可以先做一些記錄，但真正的處理，要交給更上層的程式。
-->

---
layout: default
---

# 練習 2-3：throw 實作
### 任務說明

設計一個密碼檢查程式：

- 密碼長度必須在 5–8 個字元之間
- 準備多組密碼字串，逐一測試
- 長度不符時，使用 `throw` 拋出 `StringIndexOutOfBoundsException`，訊息為「密碼長度不符規定」

<!--
練習 2-3，請設計一個密碼檢查程式。

密碼長度必須在 5 到 8 個字元之間。準備多組密碼逐一測試，長度不符合時，用 throw 拋出 StringIndexOutOfBoundsException，訊息是「密碼長度不符規定」。

一樣先暫停一下，自己動手寫寫看。
-->

---
layout: default
---

# 練習 2-3：解題提示
### 提示說明

1. 建立方法 `pwdCheck(String pwdStr)`，宣告 `throws StringIndexOutOfBoundsException`
2. 長度符合：印「密碼驗證成功」；不符合：`throw new StringIndexOutOfBoundsException("密碼長度不符規定")`
3. 主程式準備字串陣列，用迴圈逐一測試
4. 每次呼叫 `pwdCheck` 用 try-catch 捕捉並印出錯誤訊息

<!--
我們來看提示。

建立 pwdCheck 方法，長度符合就印出驗證成功，不符合就 throw new StringIndexOutOfBoundsException。

主程式準備一個字串陣列，用迴圈逐一測試。每次呼叫 pwdCheck 時，用 try-catch 捕捉異常並印出訊息，這樣某一組密碼失敗，也不會影響後面的測試。
-->

---

# 方法拋出異常 — throws

當方法內有多處可能發生異常，可整合為一個方法，並在宣告加上 `throws`：

```java
public void myMethod() throws 異常類別1, 異常類別2 {
    // 可能發生異常的敘述
}
```

- 異常交給**呼叫方**處理
- 呼叫方的程式碼中必須有相對應的 try-catch

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>注意：</b>原呼叫的程式碼中也要有相對應的異常處理程式，否則會出現編譯錯誤
</div>

<!--
當一個方法裡可能發生異常，但不打算自己處理時，可以在方法的宣告加上 throws，列出可能拋出的異常類別。

這樣異常就會交給呼叫這個方法的地方處理。如果是檢查異常，呼叫的地方就必須用 try-catch 處理，或是再往外宣告 throws，否則會編譯錯誤。
-->

---

# throw vs throws — 對比

| 比較項目 | `throw` | `throws` |
| --- | --- | --- |
| 位置 | 方法**內部** | 方法**宣告**處 |
| 用途 | 主動拋出一個異常物件 | 宣告此方法可能拋出的異常 |
| 語法 | `throw new XxxException()` | `void m() throws XxxException` |
| 後面接 | 異常**物件**（一次一個）| 異常**類別**（逗號分隔多個）|

```java
void withdraw(int amt) throws NotEnoughException {
    if (amt > balance)
        throw new NotEnoughException("餘額不足");
}
```

<!--
throw 和 throws 只差一個字母，很容易搞混，我們用這張表比較一下。

throw 寫在方法的內部，用來實際拋出一個異常物件，一次只能拋一個。throws 寫在方法的宣告處，用來說明這個方法「可能」會拋出哪些異常類別，可以用逗號列出好幾個。

看範例，withdraw 的宣告上用 throws 說明可能拋出 NotEnoughException；方法裡面，如果提款金額大於餘額，就用 throw 真的拋出這個異常。
-->

---

# 自訂異常類別

由於所有執行期異常均繼承自 `Exception`，自訂異常類別也必須繼承 `Exception`：

```java
class 自訂異常類別名稱 extends Exception {
    // 定義成員（可以不加任何成員）
}
```

- 繼承後即可使用 `toString()`、`getMessage()` 等方法
- 可加入自訂的成員變數與建構方法

<!--
Java 內建的異常類別，不一定能精確描述我們的業務問題，這時候可以自訂異常類別。

做法很簡單，寫一個類別繼承 Exception 就可以了，裡面甚至可以什麼都不寫。

繼承之後，就能使用 toString、getMessage 這些方法，也可以再加入自己的欄位和建構子，記錄更多和錯誤有關的資訊。
-->

---

# 自訂異常類別 — 定義

```java
@SuppressWarnings("serial")
class MyException extends Exception {
    String str;
    MyException(String msg) { str = msg; }
    public String toString() {
        return ("我定義的MyException發生了 " + str);
    }
}
```

<!--
看一個自訂異常類別的例子。

MyException 繼承 Exception，有一個 str 欄位，用來存放錯誤訊息。建構子接收訊息，並覆寫 toString，回傳自訂格式的說明。

最上面的 SuppressWarnings 註解，只是用來關閉一個和序列化有關的編譯警告，不影響功能。
-->

---

# 自訂異常類別 — 使用

```java
try {
    System.out.println("try區塊");
    throw new MyException("異常訊息");
} catch (MyException e) {
    System.out.println("catch區塊");
    System.out.println("MyException：" + e);
    e.printStackTrace();
}
```

<!--
使用自訂的異常，和使用內建的異常完全一樣。

在 try 裡用 throw 拋出 MyException，接著被 catch 捕捉。印出 e 時，會呼叫我們覆寫的 toString，顯示自訂的訊息。
-->

---
layout: default
---

# 練習 2-4：自訂異常類別
### 任務說明

設計一個銀行存提款程式：

- 建立 `NotEnoughException` 繼承 `Exception`，記錄差額 `shortAmount`
- 建立 `MyBank` 類別，有 `deposit()` 和 `withdraw()` 方法
- 提款金額大於存款時，拋出 `NotEnoughException`，傳入差額

<!--
練習 2-4，請設計一個銀行存提款程式。

建立一個 NotEnoughException，繼承 Exception，記錄餘額不足的差額。建立 MyBank 類別，有存款和提款兩個方法。提款金額大於餘額時，就拋出 NotEnoughException，並傳入差額。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 15 分鐘實作。
-->

---
layout: default
---

# 練習 2-4：解題提示
### 提示說明

1. `NotEnoughException` 加入 `private int shortAmount` 成員與 `getShortAmount()` 方法
2. `MyBank.withdraw()` 判斷 `cashout > balance`時計算差額
3. `throw new NotEnoughException(差額)`，並在方法宣告加上 `throws NotEnoughException`
4. `main` 中用 try-catch 捕捉，用 `e.getShortAmount()` 列印差額

<!--
我們來看提示。

NotEnoughException 加入一個 shortAmount 欄位，以及取得它的 getShortAmount 方法。MyBank 的 withdraw 判斷提款金額是否大於餘額，如果是，就計算差額，throw 一個新的 NotEnoughException，方法的宣告也要加上 throws。

在 main 裡用 try-catch 捕捉，再用 getShortAmount 印出還差多少錢。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 自定義錯誤代碼和訊息

<!--
最後一個部分，我們看看實際開發 API 時，怎麼定義錯誤代碼和訊息。
-->

---
layout: default
---

# 自定義錯誤代碼 — 使用 enum

用 `enum` 定義固定的錯誤代碼與訊息：

```java
public enum RtnCode {
    SUCCESS(200, "Success!!"), SAVE_ERROR(400, "Save error!!");
    private int code; private String message;
    RtnCode(int code, String message) {
        this.code = code; this.message = message;
    }
    public int getCode() { return code; }
    public String getMessage() { return message; }
}
```

<!--
在網站後端的 API 裡，通常會回傳一個代碼和一段訊息，讓前端知道處理的結果。

這些固定的代碼和訊息，適合用 enum 來定義。看範例，RtnCode 定義了兩組：SUCCESS 是 200 和成功，SAVE_ERROR 是 400 和儲存錯誤。每一組都有 code 和 message 兩個欄位，透過建構子設定，再用 getter 取得。

這樣所有的錯誤代碼都集中在一個地方管理，不會到處散落在程式裡。
-->

---

# 自定義錯誤代碼 — 使用方式

| 情境 | 回傳方式 |
| --- | --- |
| 固定代碼 + 固定訊息 | `RtnCode.SUCCESS.getCode()` + `.getMessage()` |
| 固定代碼 + 動態訊息 | 常數 `ERROR_CODE` + `e.getMessage()` |

<!--
使用的方式分成兩種情況。

如果代碼和訊息都是固定的，就直接用 enum 的 getCode 和 getMessage。如果代碼是固定的，但訊息要依照實際發生的錯誤而不同，就用一個常數當作代碼，再搭配異常物件的 getMessage。
-->

---

# 固定代碼 + 動態訊息 — 範例

```java
public static final int ERROR_CODE = 400;
public BaseRes objMapper(String str) {
    try {
        Quiz quiz = mapper.readValue(str, Quiz.class);
    } catch (Exception e) {
        return new BaseRes(ERROR_CODE, e.getMessage());
    }
    return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
}
```

<!--
看實際的例子。

ERROR_CODE 是常數 400。objMapper 方法嘗試把一段文字轉換成 Quiz 物件，如果轉換失敗，就在 catch 裡回傳 BaseRes，代碼是 400，訊息則是異常的實際內容。如果成功，就回傳 SUCCESS 的代碼和訊息。

這樣不管成功還是失敗，呼叫的人都會拿到一致格式的回應，不會因為異常而讓整個服務中斷。
-->

---
layout: default
---

# 練習 3-1：訂單狀態 enum
### 任務說明

設計 `enum OrderStatus`，包含三組代碼與訊息：

- `SUCCESS(200, "訂單成立")`
- `OUT_OF_STOCK(400, "庫存不足")`
- `INVALID_AMOUNT(401, "訂購數量不正確")`

撰寫 `placeOrder(int stock, int amount)` 方法：

- `amount <= 0` → 回傳 `INVALID_AMOUNT`
- `amount > stock` → 回傳 `OUT_OF_STOCK`
- 其餘情況 → 回傳 `SUCCESS`

在 `main` 中測試至少 3 組 `(stock, amount)`，印出對應的 `code` 與 `message`

<!--
練習 3-1，請設計一個訂單狀態的 enum，叫做 OrderStatus，包含三組代碼和訊息：200 訂單成立、400 庫存不足、401 訂購數量不正確。

再寫一個 placeOrder 方法：數量小於等於 0 時回傳數量不正確，數量大於庫存時回傳庫存不足，其他情況回傳訂單成立。

在 main 裡測試至少三組資料。一樣先暫停一下，自己動手寫寫看。
-->

---
layout: default
---

# 練習 3-1：解題提示
### 提示說明

```java
enum OrderStatus {
    SUCCESS(200, "訂單成立"),
    OUT_OF_STOCK(400, "庫存不足"),
    INVALID_AMOUNT(401, "訂購數量不正確");

    private final int code;
    private final String message;
    OrderStatus(int code, String message) {
        this.code = code; this.message = message;
    }
    public int getCode() { return code; }
    public String getMessage() { return message; }
}
```

- `placeOrder` 回傳型別宣告為 `OrderStatus`
- 測資建議：`(10, 0)`、`(10, 20)`、`(10, 5)` 分別對應三種結果

<!--
我們來看提示。

enum 的寫法和剛才的 RtnCode 一樣：列出三個常數，每個傳入代碼和訊息，用 final 欄位儲存，再提供 getter。

placeOrder 的回傳型別宣告成 OrderStatus。測試資料可以用庫存 10、數量 0，庫存 10、數量 20，以及庫存 10、數量 5，分別對應三種結果。
-->

---
layout: default
---

# 練習 3-2：固定代碼 + 動態訊息
### 任務說明

設計 `BaseRes` 類別，包含 `int code` 與 `String message` 兩個欄位（與對應建構子、getter）。

撰寫 `parseAge(String input)` 方法：

- 嘗試用 `Integer.parseInt(input)` 將字串轉為年齡
- 成功 → 回傳 `new BaseRes(200, "解析成功，年齡為：" + age)`
- 失敗（`NumberFormatException`）→ 回傳 `new BaseRes(400, e.getMessage())`

在 `main` 中分別測試輸入 `"25"` 與 `"twenty"`，印出回傳的 `code` 與 `message`

<!--
練習 3-2，練習固定代碼搭配動態訊息。

設計一個 BaseRes 類別，有代碼和訊息兩個欄位。寫一個 parseAge 方法，嘗試把字串轉成年齡：成功就回傳代碼 200 和解析成功的訊息；失敗就回傳代碼 400，以及異常的訊息。

在 main 裡分別測試 25 和 twenty。一樣先暫停一下，自己動手寫寫看。
-->

---
layout: default
---

# 練習 3-2：解題提示
### 提示說明

```java
public static final int ERROR_CODE = 400;

static BaseRes parseAge(String input) {
    try {
        int age = Integer.parseInt(input);
        return new BaseRes(200, "解析成功，年齡為：" + age);
    } catch (NumberFormatException e) {
        return new BaseRes(ERROR_CODE, e.getMessage());
    }
}
```

- `BaseRes` 只需要簡單的欄位 + 建構子 + getter，不必繼承任何例外類別
- 兩種輸入都不會讓程式中止，都能拿到一個 `BaseRes` 物件

<!--
我們來看提示。

在 try 裡用 parseInt 轉換，成功就回傳代碼 200。如果發生 NumberFormatException，就在 catch 裡回傳 ERROR_CODE，以及 e.getMessage。

BaseRes 只是一個簡單的資料類別，不需要繼承任何異常類別。重點是，不管輸入什麼，程式都不會中止，一定會拿到一個 BaseRes 物件。
-->

---
layout: default
---

# 綜合練習：圖書借閱系統
### 任務說明

整合本章所學，設計一個簡易圖書借閱系統：

- 書籍資料：`String[] bookIds = {"B001","B002","B003"}`，`boolean[] available = {true, false, true}`
- 自訂例外 `BookNotAvailableException extends Exception`
- `enum BorrowResult`：`SUCCESS(200,"借閱成功")`、`NOT_FOUND(404,"查無此書")`、`NOT_AVAILABLE(409,"書籍已被借出")`
- `borrowBook(String[] ids, boolean[] available, String bookId)` 方法，宣告 `throws BookNotAvailableException`：
  - 找不到 `bookId` → 拋出例外，訊息為 `NOT_FOUND.getMessage()`
  - 該書 `available == false` → 拋出例外，訊息為 `NOT_AVAILABLE.getMessage()`
  - 否則將該書 `available` 設為 `false`，回傳 `BorrowResult.SUCCESS`
- `main` 中使用 `try-with-resources` 搭配 `Scanner` 讓使用者輸入書籍編號（輸入 `exit` 結束）
- 用 `try-catch-finally` 呼叫 `borrowBook`：catch 印出例外訊息，finally 印出「本次查詢結束」

<!--
最後是一個綜合練習：圖書借閱系統，整合了這一章學到的所有內容。

書籍資料用兩個陣列表示：書籍編號，以及是否可以借閱。自訂一個 BookNotAvailableException，再用 enum 定義三種借閱結果：借閱成功、查無此書，以及書籍已被借出。

borrowBook 方法找不到書，或書已經被借出時，就拋出自訂異常；否則把書設成已借出，回傳成功。main 裡用 try-with-resources 搭配 Scanner，讓使用者輸入書籍編號，輸入 exit 時結束；每次借閱都用 try-catch-finally 處理。

我們先暫停一下，自己動手試試看。

【講師】給學生約 20 分鐘實作。
-->

---
layout: default
---

# 綜合練習：解題提示
### 提示說明

```java
class BookNotAvailableException extends Exception {
    BookNotAvailableException(String message) { super(message); }
}

enum BorrowResult {
    SUCCESS(200, "借閱成功"),
    NOT_FOUND(404, "查無此書"),
    NOT_AVAILABLE(409, "書籍已被借出");
    // ...省略建構子與 getter（同練習 3-1）
}
```

- `borrowBook` 先用迴圈找出 `bookId` 在陣列中的索引；找不到就 `throw new BookNotAvailableException(BorrowResult.NOT_FOUND.getMessage())`
- `try (Scanner sc = new Scanner(System.in)) { while (...) {...} }`，讀到 `"exit"` 就 `break`
- 每次借閱都包一層 `try { borrowBook(...); System.out.println("借閱成功"); } catch (BookNotAvailableException e) { System.out.println(e.getMessage()); } finally { System.out.println("本次查詢結束"); }`

<!--
我們來看提示。

自訂異常的建構子，直接呼叫 super 把訊息交給父類別。borrowBook 先用迴圈找出書籍編號的位置，找不到就拋出異常，訊息是查無此書；找到了但已經借出，就拋出書籍已被借出。

main 裡用 try-with-resources 建立 Scanner，用 while 迴圈讀取輸入，讀到 exit 就 break。每次借閱都包在 try 裡：成功就印出借閱成功，catch 印出異常訊息，finally 則一定會印出本次查詢結束。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了處理程式的異常。

異常分成 Error 和 Exception，Exception 又分成編譯器強制處理的檢查異常，和不強制的執行期異常。用 try-catch 捕捉異常，多個 catch 時範圍廣的放後面；finally 一定會執行，資源則建議用 try-with-resources 自動關閉。

throw 主動拋出異常，throws 宣告方法可能拋出的異常，也可以繼承 Exception 自訂異常類別。實務上，還會用 enum 統一管理錯誤代碼和訊息。

【講師】開放 Q&A。
-->

---
layout: end
---

# 程式異常的處理
### 掌握異常，讓程式更健壯

<!--
掌握異常處理，程式就會更健壯，遇到意外也能好好應對。

下一章，我們要學習多執行緒。我們下一章見。
-->

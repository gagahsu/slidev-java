---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
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
【開場白】
今天學程式異常的處理——這是每個 Java 工程師每天都會用到的技術。你的程式不可能永遠執行在「完美的條件」下，使用者會輸入奇怪的東西，檔案可能不存在，網路可能斷掉。今天學的就是「出了問題，程式怎麼優雅地處理，而不是直接崩潰」。

【今天學完你會能做什麼】
學完之後你能寫出不會因為意外輸入或意外狀況就崩潰的程式，能正確使用 try-catch，還能自訂異常類別，這是業界 Java 開發的日常。
-->
---
layout: default
---

# Outline

- **第一部分：認識程式錯誤的類別**
- **第二部分：處理異常方法**
  - try-catch / finally / try-with-resources
  - Throwable 類別方法
  - throw / throws / 自訂異常類別
- **第三部分：自定義錯誤代碼和訊息**
- **實作練習**

<!--
【帶讀大綱】
三個部分：先認識錯誤類型，再學各種處理方式（這是主要內容），最後看業界的錯誤代碼設計模式。

【學習重點】
throw 和 throws 是容易搞混的兩個關鍵字，今天會仔細比較。try-with-resources 是現代 Java 必用語法，也要學熟。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 認識程式錯誤的類別

<!--
【段落轉換】
先來認識程式錯誤有哪幾種，才知道今天的主題「異常」是哪一種。
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
【帶讀表格】
三種錯誤：
語法錯誤：打錯字、少分號，IDE 馬上紅線提示，編譯就報錯。
語意錯誤：語法正確但邏輯錯，例如加法寫成減法，程式跑起來但結果不對。
執行期間錯誤：語法邏輯都對，但執行時遇到意外狀況，程式崩潰。

今天的主角是第三種——執行期間錯誤，Java 把它稱為「異常（Exception）」。
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
【帶讀程式碼】
myDiv(8, 0) 發生除以零的錯誤，Java 拋出 ArithmeticException，程式直接中止。後面的 myDiv(9, 4) 不會執行到。

⚠️ 這就是「不處理異常」的後果——程式直接停掉。在真實的應用程式裡（像是 Web API），一個請求出錯就把整個服務停掉是不可接受的。
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
【帶讀表格】
五個最常見的異常，業界工程師每天都會看到這些：
NullPointerException：空指針，最常見的 bug 之一。
NumberFormatException：把 "abc" 轉 int，字串格式不對。
StringIndexOutOfBoundsException：字串取字元時索引超出。
ArrayIndexOutOfBoundsException：陣列存取超出邊界。
InputMismatchException：Scanner 接收到錯誤類型的輸入。
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
【帶讀程式碼】
三個範例：str 是 null，呼叫 length() 就 NullPointerException；"Taipei" 無法轉成整數就 NumberFormatException；charAt(10) 超出字串長度就 StringIndexOutOfBoundsException。

【互動引導】
大家猜猜，下面哪個最難 debug？（通常是 NullPointerException，因為很多時候不知道哪個變數是 null）
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
【帶讀說明】
Java 14 之後，NullPointerException 的錯誤訊息更清楚了，直接告訴你是哪個變數是 null，而不是只說「發生了 NullPointerException」。

💼 業界實務：
在 Java 14 之前，遇到 NullPointerException 經常要加很多 debug log 才能找到哪裡是 null。新版本省了很多時間。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 處理異常方法

<!--
【段落轉換】
知道有哪些異常了，現在來學怎麼「處理」它們，讓程式不因異常而崩潰。
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
【帶讀程式碼】
用 if/else 防呆：在可能出問題的地方先檢查。

⚠️ 問題：
如果每個可能出問題的地方都要寫 if/else，程式碼會變得很複雜，真正的業務邏輯和防呆邏輯混在一起，很難維護。

💡 Java 的 try-catch 把「正常邏輯」和「錯誤處理」分開，讓程式碼更清晰。
-->
---

# Java 的處理異常方式

當程式發生異常時，Java 會：

1. 產生對應的**異常物件（Exception Object）**
2. 在執行緒（thread）中搜尋**異常處理程式碼**

- **狀況 1：找到異常處理程式碼** → 交給它處理，處理完後可繼續往下執行
- **狀況 2：找不到** → 往前回溯呼叫鏈，直到 `main`；若仍找不到，輸出所有異常原因與回溯紀錄，程式中止

<!--
【核心說明】
Java 的異常處理流程：
1. 發生異常時，Java 建立一個異常物件
2. 在當前執行緒尋找有沒有 try-catch 能處理它
3. 有就交給 catch 處理，沒有就往呼叫者的方向找
4. 一路找到 main 都沒有，程式崩潰並印出 stack trace

💡 stack trace 就是錯誤發生時印出的那一大堆文字，告訴你哪個方法呼叫了哪個方法，最後在哪行出了問題。
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
【帶讀階層圖】
Throwable 是所有異常的頂層父類別，分兩大支：
Error：嚴重的系統錯誤，通常程式無法處理（OutOfMemoryError、StackOverflowError），只能讓程式崩潰。
Exception：程式可以處理的異常，是今天的重點。

Exception 又分：
RuntimeException（非檢查異常）：NullPointerException、ArithmeticException 等，編譯器不強制你處理。
其他 Exception（檢查異常）：IOException 等，編譯器強制你處理。
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
【帶讀表格】
非檢查異常（Unchecked）：RuntimeException 的子類別。編譯器不強制你處理，但如果不處理，執行時可能崩潰。
檢查異常（Checked）：IOException、SQLException 等。編譯器強制要求你要麼 try-catch，要麼在方法宣告加 throws，不處理就不給你通過編譯。

⚠️ 這個區分很重要！業界開發中，讀檔案、連資料庫都是 Checked Exception，一定要處理。
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
【帶讀語法】
try-catch 結構：
try {}：放可能出問題的程式碼
catch (異常類別 e) {}：出問題了來這裡處理

⚠️ 重點：
try 裡只要有一行拋出異常，後面的程式碼就不會執行，直接跳到 catch。
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
【帶讀說明】
進入 catch 要兩個條件：有發生異常，而且 catch 的類別能配對到。

配對規則：catch 的類別是發生異常的類別，或是其父類別。例如 catch (Exception e) 可以抓到幾乎所有異常，因為 Exception 是大多數異常的父類別。

⚠️ 學生常見誤解：
「用 catch (Exception e) 最安全？」——不，這是最糟糕的寫法！catch 得太廣，你無法針對不同類型的異常做不同處理，也可能把不應該忽略的錯誤偷偷吃掉。業界通常要求 catch 盡可能精確。
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
【帶讀程式碼】
try 裡面做除法，ArithmeticException 被 catch 到，印出錯誤訊息，然後方法回傳字串說明。

【類比說明】
就像飛機的自動駕駛——平常自動飛，遇到亂流（異常）觸發保護機制（catch），處理完再繼續飛，而不是直接墜機。
-->
---

# Throwable 類別的方法

| 方法 | 說明 |
| --- | --- |
| `String getMessage()` | 傳回異常的說明字串 |
| `String toString()` | 傳回異常的完整訊息（含類別名稱） |
| `void printStackTrace()` | 回溯顯示程式呼叫的執行過程 |

<!--
【帶讀表格】
三個取得異常資訊的方法：
getMessage()：只取訊息字串。
toString()：類別名稱 + 訊息。
printStackTrace()：完整的呼叫堆疊，除錯時最有用。

💼 業界實務：
業界用 Log4j 或 SLF4J 記錄異常，不用 System.out.println。logger.error("發生錯誤", e) 會記錄完整的 stack trace 到 log 檔案。
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
【帶讀程式碼】
四種方式印出異常資訊，業界主要用 getMessage() 取得訊息，e 本身或 toString() 取得完整格式，printStackTrace() 在除錯時使用。

⚠️ 學生常見誤解：
e.printStackTrace() 不是「處理異常」！只是把資訊印出來，業界不接受這樣就算處理了。
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
【帶讀說明】
多個異常類型可以有多個 catch 分別處理，也可以用 | 合併到一個 catch 裡。

⚠️ 重要規則：
多個 catch 時，順序很重要！越具體（子類別）的放前面，越廣泛（父類別）的放後面。如果父類別 catch 放在前面，子類別的 catch 永遠不會執行到（編譯就報警告）。
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
【帶讀程式碼】
FileInputStream 可能拋出 IOException，5/0 拋出 ArithmeticException。兩個 catch 分別處理。

注意：catch (IOException e) 裡有 return，代表 IO 錯誤時直接結束方法；ArithmeticException 只印訊息繼續執行。

💼 業界實務：
不同類型的異常通常需要不同的處理策略——IO 錯誤可能需要重試，計算錯誤可能只需要記錄。
-->
---
layout: default
---

# 練習 1：多 catch 實作
### 任務說明

設計一個程式，讀取使用者輸入的 2 個整數，計算除法結果。
需能正確捕捉以下兩種異常：

1. 除數為 0 — `ArithmeticException`
2. 輸入非數字 — `InputMismatchException`

<!--
【出題前的鋪陳】
練習 1：設計一個接受兩個整數輸入做除法的程式，同時處理除數為零和輸入不是數字兩種異常。

【問題引導】
用 Scanner 讀兩個整數，做除法。這會遇到哪兩種異常？catch 怎麼寫？

【等待與觀察】
給大家 5 分鐘，先想好 try 裡放什麼，catch 要處理哪兩種異常。
-->
---
layout: default
---

# 練習 1：解題提示
### 提示說明

1. 宣告 `Scanner` 讀取使用者輸入
2. 在 `try` 區塊內讀取兩個整數並做除法
3. 第一個 `catch` 捕捉 `ArithmeticException`
4. 第二個 `catch` 捕捉 `InputMismatchException`
5. 或改用 `|` 語法合併兩個異常類別到同一 catch

<!--
【帶讀解法】
try 裡：Scanner 讀兩個 int，做除法印結果。
catch (ArithmeticException e)：除數為零。
catch (InputMismatchException e)：輸入不是整數。

進階：用 catch (ArithmeticException | InputMismatchException e) 合併成一個 catch。

💡 記得測試各種情況：正常輸入、輸入 0 當除數、輸入字母。
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
【帶讀表格】
catch 的類別可以是父類別，這樣就能「廣泛捕捉」。

⚠️ 排序規則：
多個 catch 時，具體的（子類別）放前面，廣泛的（父類別）放後面。如果 IndexOutOfBoundsException 放在 ArrayIndexOutOfBoundsException 前面，後者的 catch 永遠不會執行到。
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
【帶讀程式碼】
charAt(10) 超出字串長度，拋出 StringIndexOutOfBoundsException。catch (IndexOutOfBoundsException e) 可以捕捉到，因為 StringIndexOutOfBoundsException 是 IndexOutOfBoundsException 的子類別。

【設計原則】
這個範例展示了「捕捉父類別異常」的用法。當你不確定是哪個子類別異常，或者所有子類別都用同一種方式處理，就捕捉父類別。
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
【核心說明】
finally 區塊：不管有沒有異常，finally 裡的程式碼一定會執行。

【用途】
主要用來確保資源被釋放——資料庫連線、檔案、網路串流都需要在用完後關閉，不管有沒有出錯。

【生活化比喻】
就像出門一定要關燈，不管今天出門是去上班還是看醫生，關燈這件事（finally）一定要做。
-->
---

# finally 的注意事項

- `finally` 前面必須有 `try` 區塊
- 若 try 無異常：finally 在 try 後執行
- 若 try 有異常且被捕捉：catch 執行完後再執行 finally
- 若 try 有異常但未被捕捉：仍會執行 finally，然後程式中止
- 即使 try 或 catch 有 `return`、`break`、`continue`，finally 仍會執行

<!--
【帶讀說明】
finally 的五個特性：
- try 必須在前面
- 不管有沒有異常都執行
- 異常被捕捉：catch 完再 finally
- 異常未捕捉：finally 還是執行，然後程式崩潰
- try 或 catch 裡有 return：finally 也還是會執行！（這個最讓人驚訝）

⚠️ 學生常見誤解：
「有 return 就出去了，finally 不會執行？」——不對！finally 一定執行，return 只是在 finally 執行完「之後」才真正返回。
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
【核心說明】
try-with-resources 是 Java 7 引入的語法糖，把資源宣告在 try 括號裡，try 結束時自動呼叫 close()，不需要手寫 finally {scanner.close();}。

【帶讀程式碼】
try (Scanner scanner = new Scanner(System.in)) {}：Scanner 在 try 結束時自動關閉。

💼 業界實務：
現代 Java 開發，讀檔案、資料庫連線等都用 try-with-resources，這是標準做法。你幾乎不會在業界看到有人還在 finally 裡手動關資源。
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
【帶讀說明】
Java 9 的增強：如果資源變數是 final 或實質上是 final（只賦值一次），可以直接把已宣告的變數放進 try() 裡，不需要重新宣告。

【帶讀程式碼】
final Scanner scanner = new Scanner(...)，然後 try (scanner) {}——更簡潔。
-->
---
layout: default
---

# 練習 2：try-with-resources
### 任務說明

改寫練習 1，使用 try-with-resources 語法管理 `Scanner` 資源，確保程式結束後 Scanner 自動關閉。不再需要在 `finally` 中手動呼叫 `scanner.close()`。

<!--
【出題前的鋪陳】
練習 2：把練習 1 改用 try-with-resources 語法。主要的改變是把 Scanner 的宣告移到 try() 括號裡。

【關鍵點】
改完後不需要 finally {scanner.close()}，但程式行為完全一樣。
-->
---
layout: default
---

# 練習 2：解題提示
### 提示說明

1. 將 `new Scanner(System.in)` 移入 `try(...)` 括號內宣告
2. 移除原本 `finally` 中的 `scanner.close()` 呼叫（若有）
3. catch 仍維持捕捉 `ArithmeticException` 和 `InputMismatchException`
4. 觀察程式執行結果是否與原本相同

<!--
【帶讀解法】
把 new Scanner(System.in) 移到 try 括號裡：try (Scanner scanner = new Scanner(System.in)) {}。
移除任何手動 close() 的程式碼。
catch 維持不變。

💡 觀察對比：try-with-resources 版本比 finally 版本短幾行，但功能完全相同，而且更安全（不會忘記關閉）。
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
【核心說明】
throw 讓你「主動」在程式裡某個條件下拋出異常，而不是等 Java 自動拋出。

語法：throw new 異常類別("錯誤訊息")

【使用場景】
業務規則驗證：密碼長度不符、年齡不夠、餘額不足——這些不是 Java 內建的異常，你需要主動拋出。
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
【帶讀程式碼】
pwdCheck() 方法：密碼長度 5-8 個字元才算通過，不符合就 throw new Exception("密碼長度不符規定")。

注意：方法宣告加了 throws Exception，告訴呼叫方「這個方法可能拋出 Exception，你要處理」。

【類比說明】
就像海關人員驗護照，護照過期就直接攔下來（throw），讓後面的流程（呼叫方）處理這個問題。
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
【帶讀程式碼】
catch 裡的 throw e——在 catch 捕捉到異常後，可以再次拋出，讓更外層的方法處理。

【使用場景】
當你在 catch 裡只想做部分處理（比如 log 記錄），但還是想讓上層知道有異常發生，就再次 throw。
-->
---
layout: default
---

# 練習 3：throw 實作
### 任務說明

設計一個密碼檢查程式：

- 密碼長度必須在 5–8 個字元之間
- 準備多組密碼字串，逐一測試
- 長度不符時，使用 `throw` 拋出 `StringIndexOutOfBoundsException`，訊息為「密碼長度不符規定」

<!--
【出題前的鋪陳】
練習 3：用 throw 主動拋出異常。密碼長度不符時拋出，主程式用 try-catch 捕捉。

【問題引導】
方法宣告要加 throws，方法裡條件不符就 throw。主程式怎麼呼叫並捕捉？
-->
---
layout: default
---

# 練習 3：解題提示
### 提示說明

1. 建立方法 `pwdCheck(String pwdStr)`，宣告 `throws StringIndexOutOfBoundsException`
2. 長度符合：印「密碼驗證成功」；不符合：`throw new StringIndexOutOfBoundsException("密碼長度不符規定")`
3. 主程式準備字串陣列，用迴圈逐一測試
4. 每次呼叫 `pwdCheck` 用 try-catch 捕捉並印出錯誤訊息

<!--
【帶讀解法】
pwdCheck(String pwdStr) throws StringIndexOutOfBoundsException：
- 長度符合：印成功
- 不符合：throw new StringIndexOutOfBoundsException("密碼長度不符規定")

主程式：字串陣列 + 迴圈，每次呼叫 pwdCheck 都在 try-catch 裡，catch 印出 e.getMessage()。
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
【核心說明】
throws（注意有 s）是宣告在方法簽名上，表示「這個方法可能會拋出這些異常，呼叫我的人要負責處理」。

【類比說明】
throw 是「我現在拋出異常」（動作），throws 是「我聲明可能會拋出」（宣告）。
-->
---

# throws — 範例

```java
public static void myMethod()
        throws ArithmeticException, InputMismatchException {
    Scanner scanner = new Scanner(System.in);
    int x1, x2;
    System.out.println("請輸入2個整數（空白隔開）：");
    x1 = scanner.nextInt();
    x2 = scanner.nextInt();
    System.out.println("結果：" + (x1 / x2));
}
```

<!--
【帶讀程式碼】
myMethod() throws ArithmeticException, InputMismatchException：宣告可能拋出兩種異常。呼叫 myMethod() 的地方必須有 try-catch，否則編譯報錯。

💼 業界實務：
Checked Exception（如 IOException）必須處理——要麼 try-catch，要麼在方法宣告加 throws 往上傳。RuntimeException 雖然不強制，但業界仍建議文件化潛在的異常。
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
【帶讀表格】
throw 和 throws 的對比，面試常考題！
throw：在方法「內部」，拋出一個異常「物件」，後面接 new XxxException()。
throws：在方法「宣告」，聲明可能拋出的異常「類別」，後面接類別名稱。

【帶讀程式碼】
withdraw() throws NotEnoughException（宣告），throw new NotEnoughException()（動作），兩者搭配使用。
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
【核心說明】
自訂異常類別：繼承 Exception（或其子類別），加入你需要的額外資訊。

【使用場景】
業界大型專案通常有自己的異常類別體系，比如 UserNotFoundException、OrderNotFoundException、PaymentFailedException，讓不同錯誤有不同的代碼和訊息格式。
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
【帶讀程式碼】
MyException extends Exception，加入 str 欄位存放訊息，Override toString() 顯示自訂格式。

💼 業界實務：
業界的自訂異常通常還會加入 errorCode（錯誤代碼）欄位，方便 API 回傳結構化的錯誤資訊給前端。
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
【帶讀程式碼】
throw new MyException("異常訊息") 拋出自訂異常，catch (MyException e) 捕捉，e.printStackTrace() 印出追蹤。

【輸出說明】
因為 Override 了 toString()，印出的格式是「我定義的 MyException 發生了...」。
-->
---
layout: default
---

# 練習 4：自訂異常類別
### 任務說明

設計一個銀行存提款程式：

- 建立 `NotEnoughException` 繼承 `Exception`，記錄差額 `shortAmount`
- 建立 `MyBank` 類別，有 `deposit()` 和 `withdraw()` 方法
- 提款金額大於存款時，拋出 `NotEnoughException`，傳入差額

<!--
【出題前的鋪陳】
練習 4：銀行提款情境，這是業界最典型的自訂異常應用場景。

【問題引導】
NotEnoughException 需要記錄差額。MyBank 的 withdraw() 判斷餘額不足時拋出異常，傳入差額。main 捕捉後印出差額。
-->
---
layout: default
---

# 練習 4：解題提示
### 提示說明

1. `NotEnoughException` 加入 `private int shortAmount` 成員與 `getShortAmount()` 方法
2. `MyBank.withdraw()` 判斷 `cashout > balance` 時計算差額
3. `throw new NotEnoughException(差額)`，並在方法宣告加上 `throws NotEnoughException`
4. `main` 中用 try-catch 捕捉，用 `e.getShortAmount()` 列印差額

<!--
【帶讀解法】
NotEnoughException：加入 shortAmount 欄位和 getShortAmount() 方法。
MyBank.withdraw()：cashout > balance 時，計算差額 = cashout - balance，throw new NotEnoughException(差額)，方法宣告加 throws NotEnoughException。
main：try-catch 捕捉，用 e.getShortAmount() 取得差額印出。

💼 業界實務：
銀行、電商系統每天都有這類業務異常——不是程式 bug，是業務規則不滿足。自訂異常讓這些情況可以被精確識別和處理。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 自定義錯誤代碼和訊息

<!--
【段落轉換】
最後一個部分是業界的最佳實踐：用 enum 定義錯誤代碼和訊息，讓 API 的錯誤回應更一致。
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
【核心說明】
用 enum 定義固定的錯誤代碼和訊息，確保整個系統用一致的代碼格式。

【帶讀程式碼】
RtnCode.SUCCESS 代碼 200，RtnCode.SAVE_ERROR 代碼 400。每個錯誤都有固定的代碼和預設訊息。

💼 業界實務：
Spring Boot 的 REST API 標準做法：成功回傳 200 + 資料，失敗回傳 4xx/5xx + 錯誤訊息。用 enum 集中管理這些代碼，不會有人在這裡寫 200、那裡寫 "success" 的不一致問題。
-->
---

# 自定義錯誤代碼 — 使用方式

| 情境 | 回傳方式 |
| --- | --- |
| 固定代碼 + 固定訊息 | `RtnCode.SUCCESS.getCode()` + `.getMessage()` |
| 固定代碼 + 動態訊息 | 常數 `ERROR_CODE` + `e.getMessage()` |

<!--
【帶讀表格】
兩種模式：
固定代碼 + 固定訊息：用 RtnCode.SUCCESS.getMessage()。
固定代碼 + 動態訊息：error code 固定，但訊息是 e.getMessage() 取得的動態內容。
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
【帶讀程式碼】
objMapper() 方法：try 正常執行後回傳成功的 RtnCode；catch 到任何異常時回傳錯誤代碼 400 加上異常的動態訊息。

【結構說明】
BaseRes 是回應物件，包含 code 和 message。這個模式在 Spring Boot REST API 裡幾乎是標配。
-->
---
layout: default
---

# 練習 5：綜合練習
### 任務說明

設計一個年齡投票資格檢查系統：

- 準備年齡陣列 `{12, 19, 67}`，逐一取出
- **滿 18 歲**：輸出「xx 歲的年齡歡迎投票」
- **未滿 18 歲**：拋出自訂異常，訊息為「年齡不符規定」，並輸出「xx 歲的年齡太輕」

<!--
【出題前的鋪陳】
練習 5：綜合練習，把這章學的概念都用上——自訂異常、throw、throws、try-catch。

【問題引導】
年齡驗證：滿 18 歲歡迎投票，未滿 18 歲拋出自訂異常。迴圈處理三個年齡，各自顯示對應訊息。
-->
---
layout: default
---

# 練習 5：解題提示
### 提示說明

1. 建立自訂異常類別，繼承 `StringIndexOutOfBoundsException`
2. 建立 `ageCheck(int age)` 方法，宣告 `throws` 自訂異常類別
3. 方法內：不符合時拋出異常並傳入「年齡不符規定」訊息
4. 主程式用迴圈搭配 try-catch，依結果輸出對應訊息
5. catch 中印出 `e.getMessage()` 顯示錯誤原因

<!--
【帶讀解法】
1. 自訂異常（繼承 StringIndexOutOfBoundsException）。
2. ageCheck(int age) throws 自訂異常：< 18 時 throw。
3. 主程式：int[] ages = {12, 19, 67}，for 迴圈，每次 try-catch，成功印「xx 歲歡迎投票」，catch 印「xx 歲太輕」。

💡 注意：getMessage() 取出「年齡不符規定」的訊息，自訂異常的字串在 throw 時傳入。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天學了 Java 異常處理的完整體系：三種錯誤類型、try-catch-finally、try-with-resources、throw vs throws、自訂異常類別，以及業界的錯誤代碼設計。

【核心帶走重點】
catch 要精確、不要全部 catch Exception；一定要關資源，用 try-with-resources；自訂異常讓業務邏輯錯誤有清楚的語意。

Q&A 時間！
-->
---
layout: end
---

# 程式異常的處理
### 掌握異常，讓程式更健壯

<!--
[依脈絡推斷]
本章結束。掌握異常，讓程式更健壯——這是每天都會用到的技術，多練習！
-->

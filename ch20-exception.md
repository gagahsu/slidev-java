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
【開場白】
嘿各位，歡迎來到 Java 的「事後菸」時間——我是說，程式異常處理。

【為什麼要學這個？】
你的程式在你的電腦跑得很順，不代表在使用者手上不會爆炸。使用者就像是沒拿駕照的賽車手，他們會把車開進海裡（輸入亂七八糟的東西）、會把引擎拔掉（斷網）。今天我們要學的，就是怎麼在車子快爆炸時，還能優雅地踩下煞車，而不是直接原地往生。

【今天學完你會能做什麼】
學完這堂課，你就不會再看到那種「程式已停止運作」的尷尬視窗了。你會學會怎麼捕捉那些不乖的異常、怎麼關閉那些浪費錢的資源，甚至還能自己定義專屬的「爆炸訊息」。
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
今天的行程：先分清楚什麼是「你笨」造成的錯誤，什麼是「意外」造成的異常。接著學各種捕捉技巧，最後教你怎麼像個專業工程師一樣定義錯誤代碼。
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
錯誤分三種：語法錯誤是你連門都還沒出就跌倒了（IDE 會笑你）；語意錯誤是你出門想去台北結果開到墾丁（邏輯壞掉）；執行期間錯誤則是你開得好好的，結果路被外星人搬走了（這就是異常）。

【本章重點】
我們要對付的就是「執行期間錯誤」。在 Java 裡，我們叫它 Exception。
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
除數為 0，這在數學老師眼裡是死罪，在 Java 眼裡則是 ArithmeticException。

⚠️ 警告：
你看，當 myDiv(8, 0) 爆炸時，後面的程式碼就像是看到了鬼，連跑都不敢跑，直接原地蒸發。如果你的 API 這樣寫，你可能明天就不用來上班了。
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
這幾位是 Java 界的「常客」，也就是你加班的元兇。

【重點解析】
NullPointerException (NPE) 是我們的老對手。它就像是你試著跟空氣說話，結果發現對方根本不在場。NumberFormatException 則是你想把 "台北" 轉成數字，電腦會覺得你在開玩笑。
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
範例就在這裡。str.length() 對一個空靈（null）呼叫，直接爆炸。

【互動引導】
大家覺得哪個最難抓？通常是 NPE，因為它總是發生在最想不到的地方，就像你的前任，總是在你最幸福的時候出現來搞破壞。
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
謝天謝地，Java 14 之後，NPE 變得溫柔了一點。

【除錯更方便】
以前它只會冷冷地說「這裡錯了」，現在它會指著你的鼻子說：「因為 user 是 null 啦，蠢貨！」這讓我們省下很多通靈的時間，可以早點回家洗澡。
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
用 if 來防護，這就像是你在路上每走一步都要檢查地板會不會裂開。

⚠️ 缺點：
如果你有一百個地方要檢查，你的程式碼就會長得像一張亂七八糟的蜘蛛網。真正的功能被埋在無窮無盡的 if 裡面，你連自己到底在寫什麼都不知道。
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
Java 處理異常的方式像是在玩「傳聲筒」。

【運作流程】
當出事時，Java 會打包一個「異常物件」丟出來。它會問：「誰能處理這坨爛攤子？」如果沒人理它，它就一直往上丟，最後丟到 main 面前。如果 main 也擺爛，OK，那程式就直接死給你看。
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
Throwable 是所有災難的祖先。

【Error vs Exception】
Error 是天崩地裂（記憶體爆了、硬碟壞了），你處理不了，只能等死。Exception 則是你可以補救的小車禍。

【RuntimeException】
這裡面住著 NPE 這種「不需要事先打招呼」的異常。
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
這點超重要！

【Checked Exception】
這類異常就像是你去銀行開戶，行員一定要你帶印章。你不帶（不寫 try-catch），他就不讓你辦理（編譯不通過）。IOException 就是這類型的魔王。
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
try 是「我試試看」，catch 是「如果搞砸了，我該怎麼辦」。

⚠️ 重點：
一旦 try 裡面出事了，它會立刻尖叫著逃跑，直接跳進 catch 溫暖的懷幫。後面的程式碼？想都別想，它們已經被拋棄了。
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
進入 catch 的門檻：第一，真的有出事；第二，你要抓的人是對的。

⚠️ 學生常見誤解：
別在那邊給我寫 catch (Exception e) 這種大絕招。這就像是你報警說「有人出事了」，但警察問是誰，你說「反正就是有人」。這對解決問題一點幫助都沒有，反而會隱藏真正的凶手。
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
除法運算的自動駕駛模式。出事了就印一句「避開除數為 0」，程式還能繼續活著。

【類比說明】
這就像是飛機遇到氣流，系統自動調整，乘客（使用者）頂多覺得震了一下，但飛機不會摔下來。
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
這幾個方法是你的「偵探工具」。

💼 業界實務：
別在 catch 裡只寫 System.out.println。在公司裡，我們用 Log 系統把這些資訊記錄下來。如果你只會 print，那你只是在幫自己的控制台增加垃圾訊息。
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
一堆印錯誤訊息的方式。printStackTrace() 最詳細，但也最醜。

⚠️ 學生常見誤解：
printStackTrace() 不是「處理異常」！只是把資訊印出來，業界不接受這樣就算處理了。如果你只印訊息但不解決問題，那就像是你家失火了，你只是站在門口大喊「失火啦」然後不救火，最後房子還是會燒光。
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
如果你的程式可能發生各種車禍，你就需要多個 catch。

⚠️ 重要規則：
就像收納箱，小的要放前面。如果你先用一個超大的箱子（Exception）接，後面那些精美的專屬箱子就派不上用場了。編譯器會覺得你在耍它。
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
這裡有讀檔案的錯誤，也有除法的錯誤。

注意：如果你連檔案都找不到（IOException），後面那個除法根本不會發生。
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
來吧，小工程師們，該是你們當「保全」的時候了！

【問題引導】
寫一個除法器。如果有人輸入 "雞蛋糕"（不是數字），或是輸入 "0"（除數），你的程式要能優雅地抓到他們，而不是直接噴紅字死掉。
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
用 Scanner 讀兩個整數，做除法。這會遇到哪兩種異常？catch 怎麼寫？

進階：用 catch (ArithmeticException | InputMismatchException e) 合併成一個 catch。
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
如果你想「一網打盡」，可以用父類別。

⚠️ 警告：
這是一把雙面刃。好處是方便，壞處是你根本不知道是哪一種類型的錯誤。除非你真的不在乎，否則還是建議寫得具體一點。
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
IndexOutOfBoundsException 可以同時抓到陣列和字串的索引錯誤。這就像是你雇了一個「保全」，不管是偷錢的還是偷車的，他通通抓起來。
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
finally 就是「不管怎樣我都要做」。

【生活化比喻】
就像你在家裡大吵大鬧（try），或是被警察抓走（catch），最後你媽還是會叫你把碗洗乾淨（finally）。
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
finally 是非常霸道的。就算你在 try 裡面寫了 return 想跑，它還是會把你抓回來，執行完 finally 才放你走。它就是那個一定要陪你走到最後的恐怖情人。
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
這是我最愛的 Java 魔法。

【帶讀程式碼】
以前關資源要寫三行，現在只要把它丟進 try 的括號裡，它用完就會「自我了斷」。這不僅省力，還能防止你的記憶體被那些沒關的資源塞爆。
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
-->
---
layout: default
---

# 練習 2：try-with-resources
### 任務說明

改寫練習 1，使用 try-with-resources 語法管理 `Scanner` 資源，確保程式結束後 Scanner 自動關閉。不再需要在 `finally` 中手動呼叫 `scanner.close()`。

<!--
【出題前的鋪陳】
把練習 1 升級成 2.0 版！

【關鍵點】
學會用 try-with-resources。別再手寫 close() 了，那太老派了，讓我們用點現代人的方法。
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
throw 是你「主動出擊」。

【使用場景】
有時候 Java 覺得沒問題，但你覺得有問題。比如使用者輸入年齡為 -5 歲，Java 覺得 -5 是整數啊，沒事！但你要大喊：「這不科學！」然後主動把異常丟出去。
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
密碼長度驗證。不符合 5-8 個字？直接丟出一個 Exception 讓他知道誰才是老大。

注意：方法上面要寫 throws（有 s 的那個），像是在警告別人：「我這招很毒，要接好喔！」
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
這叫「接力賽」。我在 catch 抓到了，但我不想處理，我再把它丟給上司（呼叫方）去煩惱。這在公司裡很常見，把問題往上呈報就對了。
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
來做一個嚴格的密碼守門員。

【問題引導】
如果密碼太長或太短，請豪邁地 throw 出一個異常。讓呼叫你的主程式去處理這堆爛攤子。
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
方法宣告要加 throws，方法裡條件不符就 throw。主程式怎麼呼叫並捕捉？
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
throws 是在方法門口貼告示牌。

【類比說明】
「內有惡犬，進來前請準備好醫療保險（try-catch）」。如果你不宣告 throws，你的上層根本不知道你這方法會爆炸。
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
這題面試沒考的話，我請你喝飲料！

【記憶口訣】
throw 是丟手榴彈（動作），一次一顆（一個物件）。throws 是宣告我有手榴彈（聲明），可以有很多種（多個類別）。
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
當 Java 內建的異常不夠用的時候，就自己寫一個！

【使用場景】
比如「錢不夠異常」、「女朋友生氣異常」——這些 Java 沒寫，你自己寫一個類別繼承 Exception 就搞定了。
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
定義一個 MyException。記得 Override toString()，這樣印出來的訊息才會像你寫的。
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
拋出你自己寫的異常。現在你有了專屬的爆炸按鈕了，開心嗎？
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
我們來寫一個銀行系統。錢不夠提款時，要讓使用者知道「差多少錢」。

【問題引導】
自訂一個 NotEnoughException，裡面存一個「差額」。當提款金額太誇張時，把它丟出去！
-->
---
layout: default
---

# 練習 4：解題提示
### 提示說明

1. `NotEnoughException` 加入 `private int shortAmount` 成員與 `getShortAmount()` 方法
2. `MyBank.withdraw()` 判斷 `cashout > balance`時計算差額
3. `throw new NotEnoughException(差額)`，並在方法宣告加上 `throws NotEnoughException`
4. `main` 中用 try-catch 捕捉，用 `e.getShortAmount()` 列印差額

<!--
【帶讀解法】
NotEnoughException：加入 shortAmount 欄位和 getShortAmount() 方法。
MyBank.withdraw()：cashout > balance 時，計算差額 = cashout - balance，throw new NotEnoughException(差額)，方法宣告加 throws NotEnoughException。
main：try-catch 捕捉，用 e.getShortAmount() 取得差額印出。
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
這是業界的大絕招：用 enum 來管理錯誤。

💼 業界實務：
別再寫死什麼 200、404 了。用 enum 把所有的狀態碼和訊息定義好。這樣不管是前端、後端，大家都看同一份規格，這才叫專業。
-->
---

# 自定義錯誤代碼 — 使用方式

| 情境 | 回傳方式 |
| --- | --- |
| 固定代碼 + 固定訊息 | `RtnCode.SUCCESS.getCode()` + `.getMessage()` |
| 固定代碼 + 動態訊息 | 常數 `ERROR_CODE` + `e.getMessage()` |

<!--
【帶讀表格】
兩種模式：固定代碼 + 固定訊息，或是固定代碼 + 動態訊息。
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
最後的期末考！綜合大雜燴。

【問題引導】
年齡投票系統。不滿 18 歲的給我滾（拋出異常），滿 18 歲的請進。這題會用到這章學的所有技巧，加油！
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
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天我們學會了怎麼跟災難共存。

【核心帶走重點】
記住：try 嘗試，catch 救援，finally 收尾。別讓你的程式像顆不定時炸彈。有問題快問，沒問題就趕快回家寫作業吧！
-->
---
layout: end
---

# 程式異常的處理
### 掌握異常，讓程式更健壯

<!--
【結束語】
掌控異常，你就是程式碼的上帝。下課！
-->

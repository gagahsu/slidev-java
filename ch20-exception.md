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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 認識程式錯誤的類別

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

---

# 其他常見的異常

| 異常類別 | 觸發情境 |
| --- | --- |
| `NullPointerException` | 對 `null` 物件呼叫方法 |
| `NumberFormatException` | 將非數值字串轉換成整數 |
| `StringIndexOutOfBoundsException` | 字串索引超出範圍 |
| `ArrayIndexOutOfBoundsException` | 陣列索引超出範圍 |
| `InputMismatchException` | 使用者輸入的類型錯誤 |

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 處理異常方法

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

---

# Java 的處理異常方式

當程式發生異常時，Java 會：

1. 產生對應的**異常物件（Exception Object）**
2. 在執行緒（thread）中搜尋**異常處理程式碼**

- **狀況 1：找到異常處理程式碼** → 交給它處理，處理完後可繼續往下執行
- **狀況 2：找不到** → 往前回溯呼叫鏈，直到 `main`；若仍找不到，輸出所有異常原因與回溯紀錄，程式中止

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

---

# 非檢查異常 vs 檢查異常

| 類型 | 說明 | 代表類別 |
| --- | --- | --- |
| 非檢查異常 (Unchecked) | 編譯器不強制處理 | `RuntimeException` 及其子類別 |
| 檢查異常 (Checked) | 編譯器強制要求處理 | `IOException`、`SQLException` 等 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>檢查異常</b>若未加異常處理，程式在編譯階段就會出現錯誤
</div>

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

---

# 進入 catch 的條件

進入 catch 區塊需同時符合兩個條件：

1. `try` 區塊中的程式碼**有發生異常**
2. `catch` 中定義的異常類別**有捕捉到**
   - 捕捉到：catch 的類別是發生異常的類別，或其**父類別**

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>一般實踐：</b>catch 裡面用 <code>Exception</code> 可捕捉大多數執行期異常
</div>

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

---
layout: default
---

# 練習 1：多 catch 實作
### 任務說明

設計一個程式，讀取使用者輸入的 2 個整數，計算除法結果。
需能正確捕捉以下兩種異常：

1. 除數為 0 — `ArithmeticException`
2. 輸入非數字 — `InputMismatchException`

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

---

# finally 的注意事項

- `finally` 前面必須有 `try` 區塊
- 若 try 無異常：finally 在 try 後執行
- 若 try 有異常且被捕捉：catch 執行完後再執行 finally
- 若 try 有異常但未被捕捉：仍會執行 finally，然後程式中止
- 即使 try 或 catch 有 `return`、`break`、`continue`，finally 仍會執行

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

---
layout: default
---

# 練習 2：try-with-resources
### 任務說明

改寫練習 1，使用 try-with-resources 語法管理 `Scanner` 資源，確保程式結束後 Scanner 自動關閉。不再需要在 `finally` 中手動呼叫 `scanner.close()`。

---
layout: default
---

# 練習 2：解題提示
### 提示說明

1. 將 `new Scanner(System.in)` 移入 `try(...)` 括號內宣告
2. 移除原本 `finally` 中的 `scanner.close()` 呼叫（若有）
3. catch 仍維持捕捉 `ArithmeticException` 和 `InputMismatchException`
4. 觀察程式執行結果是否與原本相同

---

# Throwable 類別的方法

| 方法 | 說明 |
| --- | --- |
| `String getMessage()` | 傳回異常的說明字串 |
| `String toString()` | 傳回異常的完整訊息（含類別名稱） |
| `void printStackTrace()` | 回溯顯示程式呼叫的執行過程 |

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

---
layout: default
---

# 練習 3：throw 實作
### 任務說明

設計一個密碼檢查程式：

- 密碼長度必須在 5–8 個字元之間
- 準備多組密碼字串，逐一測試
- 長度不符時，使用 `throw` 拋出 `StringIndexOutOfBoundsException`，訊息為「密碼長度不符規定」

---
layout: default
---

# 練習 3：解題提示
### 提示說明

1. 建立方法 `pwdCheck(String pwdStr)`，宣告 `throws StringIndexOutOfBoundsException`
2. 長度符合：印「密碼驗證成功」；不符合：`throw new StringIndexOutOfBoundsException("密碼長度不符規定")`
3. 主程式準備字串陣列，用迴圈逐一測試
4. 每次呼叫 `pwdCheck` 用 try-catch 捕捉並印出錯誤訊息

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

---
layout: default
---

# 練習 4：自訂異常類別
### 任務說明

設計一個銀行存提款程式：

- 建立 `NotEnoughException` 繼承 `Exception`，記錄差額 `shortAmount`
- 建立 `MyBank` 類別，有 `deposit()` 和 `withdraw()` 方法
- 提款金額大於存款時，拋出 `NotEnoughException`，傳入差額

---
layout: default
---

# 練習 4：解題提示
### 提示說明

1. `NotEnoughException` 加入 `private int shortAmount` 成員與 `getShortAmount()` 方法
2. `MyBank.withdraw()` 判斷 `cashout > balance` 時計算差額
3. `throw new NotEnoughException(差額)`，並在方法宣告加上 `throws NotEnoughException`
4. `main` 中用 try-catch 捕捉，用 `e.getShortAmount()` 列印差額

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# 自定義錯誤代碼和訊息

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

---

# 自定義錯誤代碼 — 使用方式

| 情境 | 回傳方式 |
| --- | --- |
| 固定代碼 + 固定訊息 | `RtnCode.SUCCESS.getCode()` + `.getMessage()` |
| 固定代碼 + 動態訊息 | 常數 `ERROR_CODE` + `e.getMessage()` |

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

---
layout: default
---

# 練習 5：綜合練習
### 任務說明

設計一個年齡投票資格檢查系統：

- 準備年齡陣列 `{12, 19, 67}`，逐一取出
- **滿 18 歲**：輸出「xx 歲的年齡歡迎投票」
- **未滿 18 歲**：拋出自訂異常，訊息為「年齡不符規定」，並輸出「xx 歲的年齡太輕」

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

---
layout: end
---

# 程式異常的處理
### 掌握異常，讓程式更健壯

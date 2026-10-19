---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: Java 程式從零開始
routeAlias: ch02
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
  <h1 style="color: #1a5c5c; font-size: 3.4rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">Java 程式從零開始</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「從 Hello World 出發，讀懂每一行程式碼的意義」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章的主題是「Java 程式從零開始」。

每位工程師都有自己的第一行程式碼，那個「程式真的跑起來了」的瞬間，通常很難忘。這一章我們就從全世界工程師都寫過的 Hello World 開始。

不過我們的目標不只是把它照著打出來，而是讀懂每一行在做什麼。學完這一章，我們會自己建立、編譯、執行一個 Java 程式，也會幫程式寫出清楚的註解。
-->

---
layout: default
---

# Outline

- **我的第一個 Java 程式**
- **解析 Java 的程式結構**
- **程式註解**

<!--
這一章分成三個部分。

第一部分，我們把開發環境準備好，實際跑出第一個 Java 程式。第二部分，把 Hello World 拆開來看，搞懂每個關鍵字的意思。第三部分，學會用註解幫自己和同事留下說明。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 我的第一個 Java 程式

<!--
我們先從第一部分開始：把環境準備好，讓第一個 Java 程式真的跑起來。
-->

---

# 開發環境準備

| 項目 | 說明 |
| --- | --- |
| **安裝 JDK** | 至 [jdk.java.net](https://jdk.java.net/) 下載 JDK 17（LTS）|
| **JAVA_HOME** | 設定環境變數指向 JDK 安裝目錄 |
| **PATH** | 將 JDK 的 `bin/` 目錄加入 PATH，才能使用 `javac`、`java` 指令 |
| **驗證安裝** | 在終端機輸入 `java -version` 確認版本 |

```bash
java -version
# java version "17.x.x" ...
```

<!--
寫程式之前，電腦要先裝好工具。Java 的開發工具叫 JDK，我們可以從 jdk.java.net 下載 JDK 17，它是長期支援的 LTS 版本。

裝好之後，有兩個環境變數要設定。JAVA_HOME 告訴電腦 JDK 裝在哪裡；PATH 則要加入 JDK 的 bin 資料夾，這樣不管在哪個資料夾，都能直接輸入 javac 和 java 指令。

如果 PATH 沒設好，就會看到「javac 不是內部或外部命令」這個錯誤訊息，這是初學者最常遇到的狀況。

設定完成後，打開終端機，輸入 `java -version`。看到版本號碼，就代表環境準備好了。
-->

---

# 我的第一個 Java 程式

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>檔名規則：</b>檔名必須與 <code>public class</code> 的名稱完全一致（含大小寫），副檔名為 <code>.java</code>。<br>
→ 上方程式碼必須存為 <b>HelloWorld.java</b>
</div>

<!--
環境好了，我們來看第一個 Java 程式。

這段程式只有五行，功能是在畫面上印出 Hello, World。裡面的 public、static、void 這些字，等一下我們會一個一個拆開來看。

現在要先記住最重要的一件事：「檔名必須和 public class 的名稱完全一樣，連大小寫都要相同。」

這個類別叫 HelloWorld，所以檔案就要存成 HelloWorld.java。寫成全小寫，或是多一個底線都不行。這不是建議，而是 Java 的強制規定。
-->

---

# 編譯與執行

| 步驟 | 指令 | 說明 |
| --- | --- | --- |
| **1. 編譯** | `javac HelloWorld.java` | 產生 `HelloWorld.class`（Bytecode）|
| **2. 執行** | `java HelloWorld` | JVM 載入 class，從 main 方法開始執行 |

```bash
# 終端機輸入
javac HelloWorld.java
java HelloWorld

# 輸出結果
Hello, World!
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
⚠️ <b>常見錯誤：</b>執行時寫 <code>java HelloWorld.class</code> 會出錯！正確寫法是 <code>java HelloWorld</code>（不加 .class）。
</div>

<!--
程式寫好了，接下來分兩步讓它跑起來。

第一步是編譯。在終端機輸入 javac HelloWorld.java，沒有錯誤的話，資料夾裡會多出一個 HelloWorld.class，這就是給 JVM 看的 Bytecode。

第二步是執行。輸入 java HelloWorld，JVM 會從 main 方法開始執行，畫面上就會出現 Hello, World。

下面的提醒要特別注意：執行時不要加 `.class`。很多人會習慣性地打 java HelloWorld.class，結果出現錯誤，其實只是多打了副檔名而已。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 解析 Java 的程式結構

<!--
程式跑起來了，但我們真的看懂那五行在做什麼嗎？接下來，我們把它拆開來，看看每個關鍵字各自負責什麼。
-->

---

# Java 程式完整結構

```java
// 1. 套件宣告（可省略）
package com.example;

// 2. 匯入外部類別（視需求）
import java.util.Scanner;

// 3. 類別宣告
public class HelloWorld {

    // 4. main 方法：程式進入點
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

<!--
一個完整的 Java 原始檔，由上到下可以分成四個部分。

第一是套件宣告 package，用來分類整理程式碼，就像用資料夾整理檔案一樣，初學階段可以先省略。

第二是 import，匯入要用到的外部類別。例如之後要讓使用者輸入資料，會用到 Scanner，就要在這裡先宣告。

第三是 class 宣告，它是整個程式的外殼，所有內容都要寫在裡面。

第四是 main 方法，也就是程式的進入點。如果把程式想成一本書，main 就是第一頁，JVM 會從這裡開始讀。
-->

---

# class 宣告解析

```java
public class HelloWorld {
    // ...
}
```

| 關鍵字 | 說明 |
| --- | --- |
| `public` | 存取修飾詞：這個類別對所有人公開 |
| `class` | 宣告這是一個「類別」定義 |
| `HelloWorld` | 類別名稱，**首字母大寫**（Pascal Case）|
| `{ }` | 大括號包住類別的所有內容（類別本體）|

<!--
我們先看 class 宣告這一行。

public 是存取修飾詞，表示這個類別是公開的。class 是關鍵字，表示我們正在定義一個類別。類別是 Java 程式最基本的組成單位，可以把相關的資料和方法包在一起。

HelloWorld 是我們取的類別名稱。Java 的慣例是每個單字的第一個字母大寫，這種寫法叫 Pascal Case。所以看到大寫開頭的名字，通常就知道它是一個類別。

最後的大括號是類別的本體，所有方法都寫在這對括號裡。

另外要記住，一個 `.java` 檔案裡，只能有一個 public class，而且名稱必須和檔名一樣。
-->

---

# main 方法完整解析

```java
public static void main(String[] args) {
    // 程式從這裡開始執行
}
```

| 關鍵字 | 說明 |
| --- | --- |
| `public` | JVM 必須能從外部呼叫這個方法，所以要公開 |
| `static` | 不需要建立物件就能呼叫，JVM 直接呼叫類別的方法 |
| `void` | 這個方法不回傳任何值 |
| `main` | 固定名稱，JVM 啟動時尋找這個方法作為進入點 |
| `String[] args` | 命令列引數，從終端機傳入的字串陣列 |

<!--
接下來是 Java 程式裡最重要的一行：main 方法的宣告。

public 表示公開，因為 JVM 要從外部呼叫它。static 表示不需要先建立物件就能呼叫，JVM 啟動時還沒有任何物件，所以 main 一定要是 static。這個觀念之後學到物件導向時會更清楚。

void 表示這個方法執行完不回傳任何值。main 是固定的名稱，JVM 啟動時就是找這個名字，大小寫都不能錯。

最後的 String 陣列 args，可以接收執行程式時從外面傳進來的資料。初學階段我們先知道它一定要寫就好。

這一整行，每個可以執行的 Java 程式都會有，建議直接記起來。
-->

---

# 控制台輸出方法

| 方法 | 說明 | 差異 |
| --- | --- | --- |
| `System.out.println(x)` | 印出 x 並**換行** | 最常用 |
| `System.out.print(x)` | 印出 x，**不換行** | 接續下一行輸出 |
| `System.out.printf(fmt, ...)` | 格式化輸出，類似 `String.format` | 控制小數位、對齊 |

```java
System.out.println("Hello");  // Hello（換行）
System.out.print("World");    // World（不換行）
System.out.printf("%.2f%n", 3.14159); // 3.14（換行）
```

<!--
程式要把結果告訴我們，就要靠輸出。Java 常用的輸出方法有三種。

println 印完之後會換行，是最常用的一種。print 印完不換行，下一次輸出會接在同一行後面。printf 是格式化輸出，可以控制小數要顯示幾位、數字要怎麼對齊。

看下面的範例，printf 用 `%.2f` 指定小數兩位，所以 3.14159 會印成 3.14。後面的 `%n` 代表換行，在不同作業系統上都能正確運作。

剛開始，我們最常用的就是 println，另外兩個用到時再回來查就可以了。
-->

---

# 命名慣例總整理

| 對象 | 命名規則 | 範例 |
| --- | --- | --- |
| **類別名稱** | Pascal Case（每字首字母大寫）| `HelloWorld`, `BankAccount` |
| **方法名稱** | camelCase（第一字小寫，後續字首大寫）| `printMessage`, `calculateSum` |
| **變數名稱** | camelCase | `userName`, `totalScore` |
| **常數名稱** | 全大寫，底線分隔 | `MAX_SIZE`, `PI` |
| **套件名稱** | 全小寫，用點分隔 | `com.example.app` |

<!--
Java 有一套大家共同遵守的命名慣例。不照著寫，程式一樣能跑，但其他人讀起來會很吃力。

類別名稱用 Pascal Case，每個單字的第一個字母都大寫，例如 HelloWorld、BankAccount。方法和變數用 camelCase，第一個單字小寫，後面每個單字的第一個字母大寫，例如 printMessage、userName。常數全部大寫，單字之間用底線分開，例如 MAX_SIZE。套件名稱則是全部小寫，用點分隔。

這些規則的好處，是讓我們一看名字就知道它是什麼。看到大寫開頭，就知道是類別；看到全部大寫，就知道是常數。讀別人的程式碼時，速度會快很多。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 程式註解

<!--
最後一個部分是程式註解。

大家可能有過這種經驗：打開一個月前自己寫的程式，卻完全看不懂當時在做什麼。註解就是為了解決這個問題，它是寫給人看的說明。
-->

---

# 三種註解類型

| 類型 | 語法 | 用途 |
| --- | --- | --- |
| **單行註解** | `// 文字` | 對單行程式碼做簡短說明 |
| **多行註解** | `/* 文字 */` | 說明較複雜的邏輯、暫時關閉程式碼 |
| **文件註解** | `/** 文字 */` | 產生 Javadoc API 文件，用於類別和方法上方 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 三種註解內容都不會被 <code>javac</code> 編譯，對執行結果沒有任何影響。
</div>

<!--
Java 有三種註解，可以依照情況選擇。

單行註解用兩條斜線開頭，適合對一行程式做簡短說明。多行註解從斜線星號開始，到星號斜線結束，中間可以寫很多行，適合說明比較複雜的邏輯，或是暫時把一段程式碼停用。文件註解則是斜線加上兩個星號開頭，用來產生 Javadoc API 文件。

下面的提醒很重要：這三種註解，編譯的時候都會被忽略，不會影響執行結果，也不會讓程式變慢。所以在需要的地方，可以放心寫。
-->

---

# 單行註解（//）

```java
public class HelloWorld {
    public static void main(String[] args) {
        // 印出歡迎訊息到控制台
        System.out.println("Hello, World!");

        int count = 0; // 計數器，初始值為 0
    }
}
```

<!--
我們先看單行註解。兩條斜線後面，一直到這一行結束，都是註解。

它可以獨立寫一行，放在程式碼上方，說明接下來要做什麼，例如這裡的「印出歡迎訊息到控制台」。也可以接在程式碼後面，當作行尾備註，例如說明 count 是一個計數器。

還有一個很實用的用法：除錯時想暫時停用某一行程式，不用刪掉它，只要在前面加上兩條斜線就好，測試完再拿掉。
-->

---

# 多行註解（/* */）

```java
/*
 * 計算兩個整數的最大公因數
 * 使用輾轉相除法（歐幾里得演算法）
 * 時間複雜度：O(log(min(a, b)))
 */
public int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
```

<!--
多行註解從斜線星號開始，到星號斜線結束，中間可以寫好幾行。

最常見的用法，是在比較複雜的方法上方，說明它的邏輯。像這個 gcd 方法，註解寫清楚了它在計算最大公因數，用的是輾轉相除法。讀的人不用逐行看程式碼，就能知道這個方法在做什麼。

另外，除錯時想暫時停用一大段程式碼，用多行註解把它包起來，會比每一行都加兩條斜線快很多。
-->

---

# 文件註解（Javadoc）

```java
/**
 * 計算兩數之和並回傳結果。
 *
 * @param a 第一個整數
 * @param b 第二個整數
 * @return 兩數相加的結果
 * @author 炭治郎
 * @since 1.0
 */
public int add(int a, int b) {
    return a + b;
}
```

<!--
文件註解以斜線加兩個星號開頭，寫在類別或方法的正上方。

它的特別之處，是可以用 javadoc 工具，自動產生網頁格式的 API 文件，就像官方文件那樣，把方法的用途、參數和回傳值整理得清清楚楚。

看這個 add 方法的範例，第一行說明它會計算兩數之和。下面用 `@param` 說明兩個參數，用 `@return` 說明回傳值，還有作者和版本資訊。

在實際專案裡，這是標準的寫法。其他工程師不用看程式碼本身，看文件就知道這個方法怎麼用。
-->

---

# 常用 Javadoc 標籤

| 標籤 | 說明 | 位置 |
| --- | --- | --- |
| `@param 名稱 說明` | 描述方法的輸入參數 | 方法 |
| `@return 說明` | 描述方法的回傳值 | 方法 |
| `@throws 例外 說明` | 描述可能拋出的例外 | 方法 |
| `@author 名稱` | 作者名稱 | 類別 |
| `@version 版本號` | 版本號碼 | 類別 |
| `@since 版本號` | 此功能自哪個版本引入 | 類別 / 方法 |

<!--
這一頁整理了常用的 Javadoc 標籤。

用在方法上的有三個：`@param` 說明每個參數，`@return` 說明回傳值，`@throws` 說明可能拋出的例外。用在類別上的有 `@author` 作者、`@version` 版本號，以及 `@since`，說明從哪一個版本開始提供這個功能。

在實際專案中，至少要幫所有 public 方法寫好 `@param` 和 `@return`。寫得多詳細可以依團隊規範，但有寫和沒寫，給人的印象差很多。
-->

---
layout: default
---

# 練習一：從零跑出第一個程式
### 任務說明

請完成以下步驟：

1. 建立一個新的 Java 檔案，類別命名為 `MyProfile`
2. 在 main 方法中，用 `System.out.println()` 分三行輸出：
   - 你的姓名
   - 你正在學習 Java
   - 今天的日期

**預期輸出範例：**

```
炭治郎
正在學習 Java！
2025-05-13
```

<!--
接下來我們動手練習。

第一題，請建立一個叫 MyProfile 的類別，在 main 方法裡用三次 println，分三行印出姓名、正在學習 Java，以及今天的日期。

這一題的重點，是自己完整走過一次建立檔案、編譯、執行的流程。寫程式、編譯、看結果、修改、再執行，這個循環就是我們之後每天會做的事。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘，並巡堂協助環境設定有問題的同學。
-->

---

# 練習一：解題提示
### 提示說明

1. 建立 `MyProfile.java`，注意 **檔名** 和 **class 名稱** 必須一致
2. 在 main 方法裡用三個 `System.out.println()` 分別印出三行
3. 編譯：`javac MyProfile.java`
4. 執行：`java MyProfile`（不加 .class）

<!--
我們來看提示。

第一，檔案要叫 MyProfile.java，和類別名稱完全一樣。第二，在 main 裡用三個 println，各印一行。第三，用 javac MyProfile.java 編譯，再用 java MyProfile 執行，記得不要加 `.class`。

如果看到「找不到或無法載入主要類別」，通常是類別名稱或執行指令拼錯了。成功印出三行之後，可以改改文字再執行一次，感受一下改了馬上就能看到結果的感覺。
-->

---
layout: default
---

# 練習二：寫一段有完整註解的程式
### 任務說明

針對以下程式碼，分別加上三種註解：

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.add(3, 5));
    }
}
```

**要求：** 為 `add` 方法加上 Javadoc 文件註解（含 `@param`、`@return`），並在 main 方法內加上至少 2 個單行註解。

<!--
第二題，我們練習寫註解。

這裡有一段 Calculator 程式，包含一個 add 方法和一個 main 方法。請幫 add 方法加上完整的 Javadoc 文件註解，包含 `@param` 和 `@return`，再在 main 方法裡加上至少兩個單行註解。

要注意，不是每一行都要加註解。好的註解是在需要說明的地方，寫出有意義的內容，說明「為什麼這樣做」，而不是把程式碼再翻譯一次。

一樣先暫停一下，自己動手寫寫看。
-->

---

# 練習二：解題提示
### 提示說明

1. **Javadoc 文件註解**加在 `add` 方法定義的**上方**，以 `/**` 開頭：

```java
/**
 * @param a ...
 * @param b ...
 * @return ...
 */
```

2. **單行註解**可以加在：建立物件那行、呼叫方法那行，說明它在做什麼

3. 完成後執行 `javadoc Calculator.java`，看看產生出什麼 HTML 文件

<!--
我們來看提示。

Javadoc 文件註解要放在 add 方法的正上方，以斜線加兩個星號開頭，每一行前面加一個星號，再依序寫上兩個 `@param` 和一個 `@return`。

單行註解可以加在建立物件和呼叫方法的地方，說明這一行在做什麼。

完成之後，可以試著執行 javadoc Calculator.java。它會產生一份網頁版的文件，用瀏覽器打開 index.html，就能看到自己寫的註解變成正式的 API 文件。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們從零開始，完成了第一個 Java 程式。

我們準備好開發環境，用 javac 編譯、用 java 執行；接著拆解了 class 宣告和 main 方法裡的每個關鍵字，認識三種輸出方法和命名慣例；最後學會了單行、多行和文件三種註解。

public、static、void、main 這些關鍵字，之後每天都會看到，現在把它們搞懂，後面的內容就會順利很多。我們下一章見。

【講師】開放 Q&A。
-->

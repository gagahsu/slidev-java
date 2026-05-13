---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
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
【開場白】
每個工程師都有寫下第一行程式碼的時刻。今天，我們就從最經典的 Hello World 開始，一步一步了解 Java 程式的完整結構。
-->

---
layout: default
---

# Outline

- **2-1 我的第一個 Java 程式**
- **2-2 解析 Java 的程式結構**
- **2-3 程式註解**

<!--
這章的內容看起來很少，但每個細節都非常重要。我們要確保每一行程式碼你都「看得懂」，而不是「照著打」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 2-1
# 我的第一個 Java 程式

<!--
每位工程師的第一個程式，幾乎都是 Hello World。讓我們從這裡開始。
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
【核心說明】
在開始寫程式之前，你的電腦必須先安裝 JDK（Java Development Kit）。

JAVA_HOME 和 PATH 這兩個環境變數非常重要，少了它們，你在命令列輸入 javac 就會出現「找不到指令」的錯誤。

驗證安裝是每次設定後必做的動作，確認版本正確才能繼續。
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
【逐步解說】
這就是 Java 的 Hello World。雖然只有 5 行，但每個字都有意義，等等我們會一一拆解。

最重要的規則：檔名和 class 名稱必須完全一致，大小寫都算。如果你把 class 命名為 HelloWorld，檔案就必須叫 HelloWorld.java，差一個字母都不行。
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
【逐步解說】
先輸入 javac HelloWorld.java，編譯成功後你會看到目錄裡多了一個 HelloWorld.class 檔案。
再輸入 java HelloWorld，JVM 就會找到 main 方法並開始執行，印出 Hello, World!。

初學者最常犯的錯就是在執行時多打了 .class 副檔名，記得把它去掉。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 2-2
# 解析 Java 的程式結構

<!--
我們已經能跑出 Hello World，但你真的看懂每一行在做什麼嗎？讓我們把程式碼拆開來逐一分析。
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
【逐步解說】
一個完整的 Java 原始碼檔案，由上到下分為四個區塊。

第一行是套件（package）宣告，用來組織程式碼。初學時可以先不寫。
第二區是 import，如果你需要用到 JDK 提供的其他工具（如 Scanner 輸入），就要在這裡匯入。
第三區是 class 的宣告，這是程式的「外殼」。
最後，main 方法是程式開始執行的地方，就像故事的第一頁。
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
【核心說明】
class 是 Java 最基本的組成單位，就像是一個容器，把相關的方法和資料裝在一起。

命名慣例：類別名稱用 Pascal Case，也就是每個單字的第一個字母都大寫。例如 HelloWorld、BankAccount、StudentManager。

一個 .java 檔案只能有一個 public class，而且名稱必須和檔名一致。
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
【核心說明】
這五個關鍵字每個都缺一不可，少了任何一個，JVM 就找不到程式進入點。

public static void main(String[] args) 這行你要背起來，未來每個可執行的 Java 程式都會有它。

args（arguments 的縮寫）是讓你在執行程式時從外部傳資料進去用的，初學時可以先忽略它。
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
【核心說明】
輸出是你和程式溝通最直接的方式。

println 是最常用的，幾乎每個範例都看得到它。
print 適合你想要在同一行連續輸出東西的時候。
printf 是格式化輸出，可以精確控制小數位數、補零等。%n 是跨平台的換行符號，比 \n 更推薦使用。
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
【業界實務】
命名慣例不是語法規定，不遵守程式也能跑。但在團隊協作中，遵守命名慣例是一種專業素養。

看到大寫開頭知道是類別，看到 ALL_CAPS 知道是常數，這讓閱讀別人的程式碼容易很多。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 2-3
# 程式註解

<!--
好的程式碼除了能跑，還要能讓別人（包括三個月後的自己）看懂。這就是「註解」存在的意義。
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
【核心說明】
「寫程式要寫註解」是每個老師都會說的金玉良言。但要寫對地方、寫對內容。

三種類型各有適用場合，接下來我們一一來看。
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
【逐步解說】
雙斜線 // 之後到該行結尾的所有內容都是註解。

可以獨佔一行（在程式碼上方說明），也可以跟在程式碼後面（行尾說明）。

最常用的場合：說明某一行程式碼的目的，或是在測試時暫時把某行「關掉」。
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
【逐步解說】
從 /* 開始，到 */ 結束，中間所有內容都是註解，可以跨越很多行。

適合在比較複雜的演算法上方，描述它的邏輯、演算法名稱、或時間複雜度。

也很常被拿來暫時關閉一大段程式碼（Debug 時很好用）。
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
【核心說明】
文件註解以 /** 開頭，用在類別或方法的上方，是最「正式」的一種。

執行 javadoc 工具後，它可以自動從這些註解產生 HTML 格式的 API 文件，就像你在官網看到的那種。

在公司開發時，這是標準的文件化方式，讓其他工程師不需要看程式碼就能知道這個方法怎麼用。
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
【業界實務】
在實際開發中，至少要在所有 public 方法上寫 @param 和 @return。

開源專案（如 Spring）的 Javadoc 非常完整，建議多閱讀它們作為撰寫風格的參考。
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
【出題前的鋪陳】
學完 Hello World 和程式結構，馬上實戰！

這個練習的目的是讓你走過「建立檔案 → 編譯 → 執行」的完整流程，並確認你能正確命名類別和使用 println。
-->

---

# 練習一：解題提示
### 提示說明

1. 建立 `MyProfile.java`，注意 **檔名** 和 **class 名稱** 必須一致
2. 在 main 方法裡用三個 `System.out.println()` 分別印出三行
3. 編譯：`javac MyProfile.java`
4. 執行：`java MyProfile`（不加 .class）

<!--
【逐步解說】
如果你看到「找不到或無法載入主要類別」的錯誤，通常是類別名稱或執行指令打錯了。

如果你看到「無法找到符號 System」，確認 main 方法的括號和大括號是否正確。

成功跑出三行輸出後，試著修改文字再跑一次，感受整個開發循環！
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
【出題前的鋪陳】
學了三種註解，現在輪到你來實際運用了。

重點不是把每行都加上註解，而是在「需要說明」的地方寫上適當的說明。
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
【逐步解說】
Javadoc 的縮排和星號（*）不是強制的，但這是業界的標準寫法，好看又好讀。

試著用 javadoc Calculator.java 產生文件，用瀏覽器打開 index.html 看看效果，你會更有成就感！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
今天我們完成了從零開始的 Java 程式之旅：寫出第一個 Hello World、解析每一個關鍵字的意義，最後學會了三種程式註解。

大家有任何問題嗎？
-->

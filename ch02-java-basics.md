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
【開場白】
嘿，各位未來的肝苦...我是說，各位未來的軟體工程師們，大家好！我是你們今天的導遊。我有十年的開發經驗，這意味著我修過的 Bug 比我掉過的頭髮還多。今天我們要挑戰的是 Java 界的「大魔王」——沒錯，就是那個讓你電腦發燙、記憶體噴掉，但又無處不在的 Java。我們從最經典的 Hello World 開始。別小看這行，這是所有「這在我機器上能跑啊 (It works on my machine)」傳說的起點！

【為什麼要學這個？】
這就像是你要進入一個新國家，得先學會怎麼跟當地人打招呼。Hello World 就是你跟 Java 虛擬機（JVM）簽訂的「賣身契」...喔不，是溝通的第一步。

【今天學完你會能做什麼】
今天學完，你就能寫出一個真正會動的 Java 程式，甚至還能對著它指手畫腳（寫註解），讓未來的你看現在的你像個天才，而不是想穿越回來揍自己。
-->

---
layout: default
---

# Outline

- **2-1 我的第一個 Java 程式**
- **2-2 解析 Java 的程式結構**
- **2-3 程式註解**

<!--
【核心說明】
這章內容看起來少，就像老闆說「這功能很簡單，下午下班前給我」一樣，都是騙人的。每個細節都藏著魔鬼。

【生活化比喻】
這就像是蓋房子前的基礎工程。雖然我們想直接蓋頂樓景觀房，但如果地基（程式結構）沒打好，你的程式就會像我昨晚點的披薩一樣，一拿起來就散掉。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 2-1
# 我的第一個 Java 程式

<!--
【開場白】
好了，廢話不多說，我們進入 2-1 節。準備好迎接你人生中第一個會聽你話的「物件」了嗎？（雖然它只會印出一行字）
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
在開始寫程式之前，你要先伺候好你的電腦。安裝 JDK 就像是給你的電腦裝上一個翻譯機。

【生活化比喻】
JAVA_HOME 和 PATH 就像是導航系統。如果你沒設好，你在命令列大喊 javac，你的電腦只會一臉懵逼地回你：「蛤？你在說哪國話？」

💼 業界實務：
在公司裡，大家用的 JDK 版本可能都不一樣。記住，版本不對，程式崩潰。所以 java -version 是每個工程師上班的第一個動作，確認今天是不是又要修地獄級的相容性問題。
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
【帶讀程式碼前的鋪陳】
各位，看好了，這就是傳說中的 Java 聖經第一章第一節。雖然只有五行，但含金量極高。

【逐步解說】
注意那個 public class HelloWorld，這就像是給你的程式取名字。
最重要的規則：檔名必須叫 HelloWorld.java。Java 對大小寫的龜毛程度，跟我老婆問我「你剛才是在看哪個女生的 IG」時一模一樣。錯一個字，它就報錯給你看。

⚠️ 學生常見誤解：
很多同學會把 String 寫成小寫 string。記住，Java 是一個「階級森嚴」的社會，物件大寫、基本型別小寫。寫錯的話，編譯器會噴一堆你看不懂的火星文。
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
成功寫完程式後，我們得先「翻譯」它。javac 就是那個翻譯官，把你的文字變成電腦看得懂的 Bytecode。
成功之後，再用 java HelloWorld 把它叫醒。

⚠️ 學生常見誤解：
執行的時候，千萬、千萬不要加 .class。這是初學者的「死亡之吻」。你加了 .class，Java 會以為你要找一個叫做 HelloWorld.class.class 的東西，然後就會用 Error 噴得你滿臉都是。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 2-2
# 解析 Java 的程式結構

<!--
【開場白】
跑得出 Hello World 只能算門徒，看懂它每一行在幹嘛才算入室弟子。接下來我們要剖開它的肚子，看看裡面裝了什麼藥。
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
一個 Java 檔案就像一封情書...不，一封正式公文。
最上面是 package，這就是郵遞區號，告訴 Java 這程式住哪。
中間是 import，這就像是你寫情書前先參考《撩妹語錄》，把別人的大絕招借過來用。
然後是 class，這是你程式的主體。
最後是 main 方法，這是心臟。心臟不動，你的程式就是一具漂亮的殭屍。
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
class 就像是一個模具，或者說是一個「抽屜」。

【生活化比喻】
想像你有一個專門放「襪子」的抽屜。如果你要在裡面放「內褲」，你就得另外開一個 class Underwear。Java 是一個非常強調「歸類」的語言，別想把東西隨便亂塞。

💼 業界實務：
在業界，我們非常討厭「一個檔案寫幾萬行」。好的工程師會把功能拆成很多個小 class，這叫模組化。不然等你離職後，接手的人會想在你的程式碼裡下毒。
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
這行 public static void main(String[] args) 就是你跟 JVM 的約定。

【生活化比喻】
這就像是你要進入一個高級俱樂部，門口保全（JVM）只認這張通行證。你少寫一個 static，保全就會把你攔下來說：「對不起，你沒有貴賓卡。」

⚠️ 學生常見誤解：
那個 String[] args 雖然現在看起來沒用，但它是讓你可以在「啟動」程式時，順便塞一張小紙條（參數）進去。現在先把它當成「神主牌」供著就好，別亂動它。
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
這是你程式的「嘴巴」。

【生活化比喻】
println 就像是說完話會自動閉嘴並坐下（換行）。print 則是說完話還一直盯著你，等著講下一句（不換行）。printf 則是格式化輸出，可以精確控制小數位數、補零等。%n 是跨平台的換行符號。

💼 業界實務：
雖然我們現在學 System.out.println，但在真實的伺服器開發中，我們幾乎不用它，因為它太慢了，而且沒辦法關掉。我們會用 Logger，這就像是幫程式裝上監視器，可以隨時調整錄影品質。
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
各位，這很重要！在程式界，命名就像是給小孩取名字。你把變數取名為 a, b, c，三個月後你回來看，你只會想問：「這誰家的小孩？怎麼長得這麼醜？」

【生活化比喻】
Pascal Case (類別大寫) 就像是姓氏。camelCase (方法變數小寫) 就像是名字。遵守規矩，大家才知道你在寫什麼，不然你就會成為團隊中的「薪水小偷」...喔不，是「溝通黑洞」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 2-3
# 程式註解

<!--
【開場白】
接下來講註解。註解就是寫給「未來的你」的情書，或者是給「下一個接手的人」的求饒信。好的程式碼除了能跑，還要能讓別人看懂。
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
註解是編譯器會自動略過的「廢話」。

【生活化比喻】
這就像是你去圖書館看書，在旁邊用鉛筆寫的眉批。館長（編譯器）不會管你寫了什麼「這題會考」或「這行程式碼像坨屎」，它只在意書本原本的內容。
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
// 是最速配的註解方式。可以獨佔一行，也可以跟在程式碼後面。

💼 業界實務：
如果你發現你得寫一堆註解來解釋這一行在幹嘛，通常代表你的程式寫得太爛了。好的程式碼應該像優秀的小說一樣，不用註解你也能看懂劇情...應該要能「自我解釋」。
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
/* ... */ 適合用來寫「長篇大論」。

【生活化比喻】
當你發現你寫了一段像「神話」一樣難懂的邏輯時，請務必用多行註解解釋一下你的腦回路，不然接手的人可能會真的發瘋。也很常被拿來暫時關閉一大段程式碼（Debug 時很好用）。
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
這是最有「高級感」的註解。以 /** 開頭，用在類別或方法的上方。

【生活化比喻】
這就像是產品說明書。你買了一台高級吹風機，裡面附的那本多國語言說明書就是 Javadoc。執行 javadoc 工具後，它會自動產生專業的 HTML API 文件。

💼 業界實務：
在公司裡，如果你寫的 public 方法沒附 Javadoc，Code Review 的時候你的前輩會用眼神殺死你。這是專業度的展現！
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
@param 和 @return 是基本素養。這就像是在點外送，你要告訴外送員（開發者）地址在哪（參數），以及要送什麼餐（回傳值）。建議多參考 Spring 等開源專案的寫法。
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
好了，聽我講了這麼多冷笑話，手癢了吧？現在輪到你們表演了。

【問題引導】
試著建立一個 MyProfile。記住，如果檔名存成 myprofile.java（小寫），編譯器會對你咆哮。這是一個訓練你「細心度」的過程。
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
如果你噴錯了，別難過。報錯（Error）是工程師最好的老師，雖然這個老師脾氣有點暴躁。成功跑出輸出後，試著修改文字再跑一次，感受整個開發循環！

【等待與觀察】
給大家三分鐘。如果你的程式印不出你的名字，而是印出「找不到符號」，請檢查你的大括號是不是跟你的心一樣亂。
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
最後一關，讓我們來練習寫「專業」的註解。

【問題引導】
想像你是這台 Calculator 的發明者，你要怎麼讓別人不用看你的電路圖（程式碼），就能知道這台機器怎麼用？重點不是把每行都加上註解，而是在「需要說明」的地方寫上適當的說明。
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
寫完之後，試著跑跑看 javadoc 指令。當你看到瀏覽器跳出一個超級專業的 HTML 頁面時，你會感覺自己瞬間變成了 Google 的工程師。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【開場白】
今天我們完成了從零開始的 Java 程式之旅：寫出第一個 Hello World、解析每一個關鍵字的意義，最後還學會了怎麼優雅地寫廢話（註解）。

大家還有什麼不明白的嗎？或是想問我有什麼好用的洗髮精來防止掉髮？儘管開口！
-->

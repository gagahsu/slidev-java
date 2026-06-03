---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: Java 套件 (Package)
routeAlias: ch19
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
    套件 (Package)
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「組織程式碼的容器，控制誰能存取什麼」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
嘿各位，準備好要「整理房間」了嗎？今天的主題是「套件 (Package)」。

【為什麼要學這個？】
想像一下，如果你把所有的程式碼類別都丟在同一個資料夾，就像把襪子、內褲、領帶跟髒衣服通通塞在同一個抽屜。你要找東西的時候，會想哭吧？套件就是你的收納神器，讓你的程式碼看起來像有無印良品的風格。

【今天學完你會能做什麼】
學完這堂課，你就不再是那個程式碼亂丟的小屁孩了。你會知道怎麼優雅地分類程式碼、怎麼用 import 召喚它們，還能學會「存取控制」，就像是在程式碼周圍蓋圍牆，決定誰能進來參觀，誰連門縫都看不到。
-->

---
layout: default
---

# Outline

- **認識套件** — 什麼是套件、為什麼需要套件
- **建立套件** — `package` 宣告、目錄結構
- **匯入套件** — `import`、萬用字元、靜態匯入
- **常用標準套件** — `java.lang`、`java.util`、`java.io` 等
- **存取控制修飾詞** — `public`、`protected`、預設（package）、`private`
- **Java 模組系統 (JPMS)** — JDK 9 的 `module-info.java`
- **課堂練習**

<!--
【帶讀大綱】
今天的行程很豐富：先教你怎麼分類，再教你怎麼召喚別人的工具。最後，也是最精華的部分——存取控制。這部分面試超愛問，如果你答不出來，面試官可能會覺得你連自家的門鎖都搞不清楚。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識套件
# What Is a Package?

<!--
【段落轉換】
先了解套件是什麼、解決什麼問題。
-->

---
layout: default
---

# 什麼是套件？

- 套件（Package）是**一組相關類別與介面的集合**，對應到檔案系統的**目錄**
- 解決兩個核心問題：
  - **命名衝突**：不同套件可以有相同名稱的類別（如 `java.util.Date` 與 `java.sql.Date`）
  - **存取控制**：套件是 Java 存取控制的邊界單位之一

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>類比：</b>套件就像資料夾，類別就像檔案。不同資料夾裡可以有同名檔案，不會衝突
</div>

<!--
【帶讀說明】
套件 = 目錄 + 命名空間 + 存取控制邊界。

【命名衝突範例】
你有沒有遇過在公司裡有兩個「小強」？如果沒分部門，你大喊一聲「小強」，兩個都會回頭。但在 Java 裡，我們可以有 工程部.小強 和 行政部.小強，完美避開這種尷尬。

💼 業界實務：
在 Spring Boot 專案，我們會把 Controller 放一堆，Service 放一堆。這不是為了好玩，是為了當專案變大時，你不會在幾百個檔案裡迷路到懷疑人生。
-->

---

# 套件的命名慣例

| 層級 | 說明 | 範例 |
| --- | --- | --- |
| 最外層 | 以公司或組織的網域名稱反轉 | `com.google` / `org.apache` |
| 第二層 | 專案或產品名稱 | `com.google.gson` |
| 第三層以後 | 功能模組 | `com.google.gson.stream` |

- 全部**小寫**，不使用底線或大寫
- JDK 標準套件以 `java.` 或 `javax.` 開頭

<!--
【帶讀表格】
套件命名用「反轉的網域名稱」，避免和別人的套件衝突。google.com → com.google。

【常見問題】
為什麼要倒著寫？因為 google.com 是唯一的。如果你寫 com.google，全世界都知道這是 Google 的地盤。如果你隨便寫個 my.app，哪天不小心跟別人的專案撞名了，編譯器就會讓你體驗什麼叫做「命名大亂鬥」。

⚠️ 命名規則：全小寫，不能有大寫和底線。寫成 Com.Google 或 com_google 都是違反慣例的。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 建立套件
# Creating Packages

<!--
【段落轉換】
知道什麼是套件之後，來學怎麼建立套件。
-->

---
layout: default
---

# `package` 宣告

- `package` 必須是原始碼檔案的**第一行有效敘述**（前面只能有空行或註解）
- 一個 `.java` 檔只能屬於**一個套件**

```java
package com.example.model;

public class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() { return name; }
}
```

<!--
【帶讀語法】
package 宣告一定要放在第一行！它就像是你家地址，一定要寫在信封的最前面。

【重要規則】
如果你把它放在類別中間，編譯器會噴一堆錯誤訊息，就像是在罵你：「你家地址寫在信紙背面是想寄給誰？」
-->

---

# 套件與目錄結構的對應

套件名稱對應到**實體目錄**，每個 `.` 是一層目錄：

```
src/
└── com/
    └── example/
        └── model/
            └── Student.java    ← package com.example.model;
        └── service/
            └── StudentService.java  ← package com.example.service;
```

- 套件路徑與類別檔案路徑**必須完全對應**，否則編譯失敗
- 沒有宣告 `package` 的類別屬於**預設套件（default package）**，不建議在正式專案使用

<!--
【帶讀目錄結構】
com.example.model 對應 com/example/model/ 目錄，類別 Student.java 就放在那個目錄下。

⚠️ 常見錯誤：新手最常犯的錯就是：檔案放在資料夾 A，但 package 寫 B。Java 會覺得你在搞詐騙，直接拒絕編譯。記得，地址要跟人住的地方對得上，OK？
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 匯入套件
# Importing Packages

<!--
【段落轉換】
建立了套件之後，怎麼在其他類別裡使用別的套件的類別？用 import。
-->

---
layout: default
---

# `import` 語法

- `import` 讓你在程式碼中直接使用類別的**短名稱**，不需要每次寫全名
- 寫在 `package` 宣告之後、類別宣告之前

```java
package com.example.service;

import com.example.model.Student;   // 匯入單一類別
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students = new ArrayList<>();
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>不用 import 的情況：</b>使用 <code>java.lang</code> 套件的類別（如 <code>String</code>、<code>Math</code>）— 編譯器自動匯入
</div>

<!--
【帶讀語法】
import 就像是召喚術。你要用別家的類別，就得先大喊一聲它的名字。

【不用 import 的情況】
java.lang 裡的類別（像 String、System）是 Java 送你的 VIP 服務，不用召喚，它們隨時待命。這就像是你在家裡叫爸媽，不用報身分證字號一樣。
-->

---

# 萬用字元 `import`

```java
import java.util.*;   // 匯入 java.util 下所有 public 類別
```

| 比較項目 | 單一類別 import | 萬用字元 import |
| --- | --- | --- |
| 寫法 | `import java.util.ArrayList;` | `import java.util.*;` |
| 效能影響 | 無差異 | 無差異（編譯後相同） |
| 命名衝突風險 | 低 | 較高（可能引入同名類別） |
| 業界慣例 | 推薦（可讀性高） | 不建議（可讀性低） |

<!--
【帶讀表格】
萬用字元 * 看起來很帥，就像是大喊「通通給我過來！」

【業界慣例】
但業界其實不愛這招。因為當你寫 import java.util.*; 時，沒人知道你到底用了什麼。這就像是你去超市大喊「我要這排所有的東西！」結果結帳時才發現你根本沒錢買。建議還是一個一個 import，清清楚楚，明明白白。
-->

---

# 靜態匯入 `import static`

讓你直接使用類別的靜態成員，不需要寫類別名稱：

```java
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class Circle {
    double radius;
    Circle(double r) { radius = r; }
    double area() {
        return PI * pow(radius, 2);
    }
    double hypotenuse(double a, double b) {
        return sqrt(pow(a, 2) + pow(b, 2));
    }
}
```

<!--
【帶讀語法】
靜態匯入是給懶人用的極致功能。

【適用時機】
以前要寫 Math.sqrt()，現在用了靜態匯入，直接寫 sqrt()。聽起來很高級，但別用太兇，否則別人看你的程式碼會以為你在寫什麼神祕的咒語，完全看不出這些方法是從哪來的。
-->

---

# 全名存取（不使用 import）

不用 `import`，直接寫**全限定名稱（Fully Qualified Name）**：

```java
public class Demo {
    public static void main(String[] args) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        list.add("炭治郎");

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(
            System.currentTimeMillis());
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>適用時機：</b>同時使用兩個同名的類別（如 <code>java.util.Date</code> 與 <code>java.sql.Date</code>），至少其中一個用全名
</div>

<!--
【帶讀程式碼】
如果你真的倒楣到兩個套件的類別撞名了（比如兩個 Date），你就得寫全名。

【解決衝突的策略】
這就像是你要區分「台北的王小明」和「台中的王小明」，你得把地名全唸出來。麻煩是麻煩了點，但至少不會認錯人。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 常用標準套件
# Standard Library Packages

<!--
【段落轉換】
JDK 提供了大量的標準套件，先認識最常用的幾個。
-->

---
layout: default
---

# JDK 常用標準套件

| 套件 | 說明 | 常用類別 |
| --- | --- | --- |
| `java.lang` | 核心類別，自動匯入 | `String`、`Math`、`Integer`、`System` |
| `java.util` | 工具類別與集合框架 | `ArrayList`、`HashMap`、`Scanner`、`Date` |
| `java.io` | 傳統 I/O | `File`、`FileReader`、`BufferedReader` |
| `java.nio` | 新 I/O（非阻塞） | `Path`、`Files`、`Channels` |
| `java.net` | 網路 | `URL`、`Socket`、`HttpURLConnection` |
| `java.sql` | 資料庫 (JDBC) | `Connection`、`Statement`、`ResultSet` |

<!--
【帶讀表格】
這些是 Java 內建的「大禮包」。

【重點記住】
java.util 是你每天的下午茶，裡面有 Scanner、ArrayList。java.io 是處理檔案的。記住這些，你就有了基本的「Java 生存工具箱」。
-->

---

# `java.time` 套件（JDK 8）

JDK 8 引入全新的日期時間 API，取代 `java.util.Date`：

| 類別 | 說明 |
| --- | --- |
| `LocalDate` | 日期（年月日），不含時間 |
| `LocalTime` | 時間（時分秒），不含日期 |
| `LocalDateTime` | 日期 + 時間 |
| `ZonedDateTime` | 帶時區的日期時間 |
| `DateTimeFormatter` | 格式化/解析日期時間字串 |

<!--
【帶讀表格】
求求你們，別再用舊的 java.util.Date 了！它設計得跟迷宮一樣。
-->

---

# `java.time` 套件 — 範例

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

LocalDate today = LocalDate.now();
String fmt = today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
System.out.println(fmt);
```

<!--
💼 業界實務：
JDK 8 以後的 java.time 套件才是王道。它不會讓你算時間算到想撞牆。如果你在面試時說你還在用舊的 Date，面試官可能會問你現在是不是還在用撥接上網。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 存取控制修飾詞
# Access Modifiers

<!--
【段落轉換】
這是今天最重要的部分。存取控制修飾詞決定「誰能看到什麼」，是封裝（Encapsulation）的核心機制。
-->

---
layout: default
---

# 四種存取控制修飾詞

| 修飾詞 | 同類別 | 同套件 | 子類別（不同套件） | 任意類別 |
| --- | --- | --- | --- | --- |
| `private` | O | X | X | X |
| （預設）package | O | O | X | X |
| `protected` | O | O | O | X |
| `public` | O | O | O | O |

- 存取範圍：`private` < 預設（package）< `protected` < `public`

<!--
【帶讀表格】
這張表是今天最值錢的東西！四種修飾詞，決定你的隱私等級。

【記憶方法】
private 是你藏在床底下的日記；package 是你家人可以看到的客廳；protected 是你留給子孫的遺產；public 則是你在臉書發的公開貼文（全世界都看得到，包括你老闆）。
-->

---

# `private` — 最嚴格的限制

- 只能在**同一個類別**內存取
- 主要用於**成員變數**（實現封裝）和**只供內部使用的方法**

```java
public class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }
    public double getBalance() {
        return balance;
    }
    private void log(String msg) {
        System.out.println("[LOG] " + msg);
    }
}
```

<!--
【核心說明】
private 就是「非誠勿擾」。

【帶讀程式碼】
銀行帳戶的餘額（balance）一定要 private！你總不希望路人甲可以直接修改你的餘額吧？要改錢，得走 deposit 方法，裡面有保全（if 檢查）幫你把關。這就是封裝，這就是專業！
-->

---

# 預設（package-private）— 套件內共享

- **不加任何修飾詞**時的預設存取等級
- 同一套件內的所有類別可存取，套件外的類別無法存取

```java
package com.example.util;

class Helper {           // 沒有 public，只有同套件能用
    static String format(String s) {
        return s.trim().toLowerCase();
    }
}

public class StringUtils {
    public static String clean(String input) {
        return Helper.format(input);  // 同套件，可存取
    }
}
```

<!--
【帶讀程式碼】
不寫修飾詞，就是預設。這在 Java 裡有個名字叫「Package-private」。

【設計意涵】
這就像是同一個辦公室的同事可以互相借筆，但隔壁大樓的人（別的套件）就不能隨便進來拿東西。這對於隱藏那些不想讓外人看到的雜事很有用。
-->

---

# `protected` — 保護繼承鏈

- 同套件的類別可存取
- **不同套件的子類別也可存取**（透過繼承）

```java
package com.example.animal;

public class Animal {
    protected String name;
    protected void breathe() {
        System.out.println(name + " 在呼吸");
    }
}
```

```java
package com.example.pet;      // 不同套件
import com.example.animal.Animal;

public class Dog extends Animal {
    public void show() {
        name = "小黑";         // 子類別可存取 protected 成員
        breathe();
    }
}
```

<!--
【核心說明】
protected 是給子孫的福利。

【帶讀程式碼】
即便你在別的資料夾（套件），只要你認我當爸爸（繼承），你就能用我的東西。這就像是傳家寶，只傳給自家人，路人甲只能在外面流口水。
-->

---

# `public` — 完全開放

- 任何套件的任何類別都可存取
- 每個 `.java` 檔案最多只能有**一個** `public` 類別，且名稱必須與檔案名稱一致

```java
package com.example.model;

public class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() { return name; }
    public int getAge() { return age; }
}
```

<!--
【核心說明】
public 就是大放送！

⚠️ 一個檔案最多一個 public 類別：
這規矩就像是一山不容二虎，一個 .java 檔案只能有一個 public 類別。如果你想多放幾個，它們就只能當個低調的普通類別，不能冠上 public 的名號。
-->

---

# 存取控制修飾詞可套用的對象

| 修飾詞 | 類別 | 成員變數 | 方法 | 建構方法 |
| --- | --- | --- | --- | --- |
| `private` | X（不能用在頂層類別） | O | O | O |
| 預設（package） | O | O | O | O |
| `protected` | X（不能用在頂層類別） | O | O | O |
| `public` | O | O | O | O |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>頂層類別</b>只能是 <code>public</code> 或預設（package-private），不能是 <code>private</code> 或 <code>protected</code>
</div>

<!--
【帶讀表格】
這點很多人會搞混：最外層的類別不能是 private。

⚠️ 常見誤區：
你想想，如果你把整個類別設成 private，那除了它自己，誰都看不到它，那它寫出來是要幹嘛？自我安慰嗎？所以類別最嚴格只能到「預設」。
-->

---

# 封裝設計原則

| 原則 | 說明 |
| --- | --- |
| 成員變數用 `private` | 保護資料，避免外部直接竄改 |
| 公開方法用 `public` | 透過方法控制存取，可加入驗證邏輯 |
| 繼承用的方法用 `protected` | 子類別能用，但不暴露給外部 |
| 內部工具方法用 `private` | 實作細節隱藏在類別內 |

<!--
【核心設計思維】
封裝不只是「把變數設 private」，而是「控制存取，保護資料完整性」。
-->

---

# 封裝設計原則 — 範例

```java
public class Temperature {
    private double celsius;

    public void setCelsius(double c) {
        if (c < -273.15) throw new IllegalArgumentException("低於絕對零度");
        this.celsius = c;
    }
    public double getCelsius() { return celsius; }
    public double getFahrenheit() { return celsius * 9.0/5 + 32; }
}
```

<!--
【帶讀程式碼】
你看溫度計的範例。如果 celsius 是 public，有人給它設個 -500 度，那這支溫度計就直接突破物理極限了。透過 setCelsius 方法，我們可以先檢查一下對方是不是在講鬼話，這就是專業工程師的自我修養。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 模組系統 (JPMS)
# Java Platform Module System — JDK 9

<!--
【段落轉換】
JDK 9 之後，Java 搞了一個更高層級的「模組系統」。這就像是從「整理抽屜」升級到「整理整個倉庫」。
-->

---
layout: default
---

# 為什麼需要模組系統？

| 套件（Package）的限制 | 說明 |
| --- | --- |
| classpath 地獄 | 大型專案有幾百個 JAR，版本衝突難以管理 |
| 存取控制不夠精確 | `public` 類別對整個 JVM 都可見，無法限制套件間的存取 |
| JDK 本身太龐大 | 嵌入式裝置也要帶整個 JDK |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>模組（Module）</b>是一組套件的集合，明確宣告「我提供什麼」與「我需要什麼」，讓相依關係清晰可見
</div>

<!--
【問題說明】
模組系統解決了「Classpath 地獄」。以前你可能會有三個不同版本的同一個 JAR 包，編譯器就像在抽籤一樣，抽到哪個算哪個，搞得大家很崩潰。
-->

---

# `module-info.java`

模組用 `module-info.java` 宣告，放在**模組的根目錄**：

```java
// src/module-info.java
module com.example.myapp {
    requires java.sql;
    requires com.google.gson;

    exports com.example.model;
    exports com.example.service to com.example.ui;
}
```

| 關鍵字 | 說明 |
| --- | --- |
| `module` | 宣告模組名稱 |
| `requires` | 宣告依賴哪些模組 |
| `exports` | 宣告哪些套件對外開放 |

<!--
【帶讀語法】
module-info.java 是模組的宣告檔。

【精確控制】
你可以精確到說：我的這個套件，只開放給隔壁的 ui 模組看，其他人通通不准看！這比 public 更有威嚴，簡直就是程式碼界的 VIP 俱樂部。
-->

---

# JDK 模組化

JDK 本身從 JDK 9 起已模組化：

```
java.base      ← 所有模組的基礎，自動包含（java.lang、java.util 等）
java.sql       ← JDBC 資料庫
java.xml       ← XML 處理
java.desktop   ← AWT/Swing GUI
java.net.http  ← HTTP Client（JDK 11）
```

```java
// 查看目前 JDK 所有模組
java --list-modules

// 查看特定模組的套件內容
java --describe-module java.base
```

<!--
【帶讀說明】
現在連 Java 自己都模組化了。

【實際影響】
如果你只需要做數學運算，你就不用帶著整個 java.desktop 那種笨重的圖形介面。這讓你的程式可以瘦身，在雲端跑起來更輕快。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 課堂練習
# Practice

<!--
【段落轉換】
來做練習，把今天學的套件與存取控制實際操作一次。
-->

---
layout: default
---

# 練習：設計套件結構與存取控制
### 任務說明

設計一個簡單的學生管理系統，要求：

1. 建立套件 `com.school.model`，內有 `Student` 類別：
   - 成員變數 `name`（`private`）、`grade`（`protected`）、`studentId`（`public`）
   - 提供 `public` 的建構方法和 getter/setter
2. 建立套件 `com.school.service`，內有 `StudentService` 類別：
   - `import` 並使用 `Student`
   - 提供 `public` 方法 `printInfo(Student s)` 列印學生資訊
3. 建立 `com.school.Main`，建立 `Student` 物件並呼叫 `StudentService`

<!--
【出題目的】
英雄們，來動動手吧！

【引導思考】
設計一個學生系統。想想看，學生的名字這種隱私應該放哪種修飾詞？（除非你想讓他被霸凌）學號這種公開資訊又該放哪？去吧，建立你的套件王國！
-->

---
layout: default
---

# 練習：解題提示

```java
// com/school/model/Student.java
package com.school.model;

public class Student {
    private String name;
    protected int grade;
    public String studentId;

    public Student(String studentId, String name, int grade) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getGrade() { return grade; }
}
```

<!--
【帶讀解法】
Student 類別就像是一張名片。記得要把該藏的藏好，該給的給足。
-->

---
layout: default
---

# 練習：解題提示（續）

```java
// com/school/service/StudentService.java
package com.school.service;

import com.school.model.Student;

public class StudentService {
    public void printInfo(Student s) {
        System.out.println("學號：" + s.studentId);
        System.out.println("姓名：" + s.getName());
        System.out.println("年級：" + s.getGrade());
    }
}
```

<!--
【帶讀解法】
當你在不同套件時，import 召喚術就要派上用場了。
-->

---
layout: default
---

# 練習：解題提示（續 2）

```java
// com/school/Main.java
package com.school;

import com.school.model.Student;
import com.school.service.StudentService;

public class Main {
    public static void main(String[] args) {
        Student s = new Student("S001", "炭治郎", 2);
        new StudentService().printInfo(s);
    }
}
```

<!--
【重要觀念】
就算你用了 protected，如果你不在繼承鏈裡，你還是得乖乖透過 getter/setter 存取。別以為你跟它是鄰居就能隨便進去別人家臥室啊！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天我們從整理抽屜聊到整理倉庫，還學會了怎麼蓋圍牆。

【核心帶走重點】
記得：變數設 private，方法設 public，沒事多用 java.time。如果你能做到這三點，你的程式碼就不會像是一場災難了。有問題儘管問，我不收門票！
-->

---
layout: end
---

# 課程結束
### 套件組織程式碼，存取控制保護封裝邊界

<!--
【結束語】
套件讓你的程式碼有組織，修飾詞讓你的程式碼有尊嚴。下課！別忘了把你的程式碼收納好啊！
-->

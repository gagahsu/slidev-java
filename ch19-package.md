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
大家好，這一章的主題是套件，英文是 package。

當程式越寫越大，類別從幾個變成幾百個，全部放在同一個地方，就會像一個沒有分類的資料夾，很難找，還可能遇到兩個類別剛好同名的問題。

套件就是組織程式碼的容器，它也決定了誰能存取什麼。學完這一章，我們就能用套件整理專案結構，並用存取修飾詞控制類別之間的存取權限。
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
這一章我們會先認識套件，以及它的命名慣例。接著學怎麼建立套件、怎麼用 import 匯入其他套件的類別。

然後認識 JDK 常用的標準套件，再把四種存取修飾詞完整整理一次。最後簡單介紹 Java 9 的模組系統，並用一題練習，實際設計一個有套件結構的小專案。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識套件
# What Is a Package?

<!--
我們先來認識套件，看看它解決了哪些問題。
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
套件是一組相關類別和介面的集合，在檔案系統裡，它對應的就是資料夾。

它主要解決兩個問題。第一是命名衝突：不同套件裡，可以有同名的類別。例如 java.util 和 java.sql 裡，都有一個叫 Date 的類別，因為放在不同的套件，所以不會衝突。第二是存取控制：套件是 Java 控制存取權限的邊界之一。

就像電腦裡的資料夾，不同資料夾裡可以有同名的檔案，彼此不會互相干擾。
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
套件的命名有一套業界慣例。

最外層用公司或組織的網域名稱反過來寫，例如 google.com 就寫成 com.google。第二層是專案或產品的名稱，再往下則是各個功能模組。

為什麼要用網域？因為網域在全世界是唯一的，用它當開頭，就不會和別人的套件撞名。

另外，套件名稱全部用小寫，不使用底線或大寫。JDK 的標準套件，則是以 java 或 javax 開頭。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 建立套件
# Creating Packages

<!--
接下來，我們看看怎麼建立一個套件。
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
在原始碼檔案的第一行，用 package 宣告這個類別屬於哪個套件。前面只能有空行或註解。

看範例，Student 類別屬於 com.example.model 套件。

另外，一個 .java 檔案，只能屬於一個套件。
-->

---

# 套件與目錄結構的對應

套件名稱對應到**實體目錄**，每個 `.` 是一層目錄：

```
src/
└── com/
    └── example/
        ├── model/
        │   └── Student.java         ← package com.example.model;
        └── service/
            └── StudentService.java  ← package com.example.service;
```

- 套件路徑與類別檔案路徑**必須完全對應**，否則編譯或執行時會找不到類別
- 沒有宣告 `package` 的類別屬於**預設套件（default package）**，不建議在正式專案使用

<!--
套件的名稱，會對應到實際的資料夾結構，名稱裡的每一個點，就是一層資料夾。

看這張圖，com.example.model 套件裡的 Student，就要放在 com、example、model 這三層資料夾裡；com.example.service 套件的 StudentService，則放在 service 資料夾。

套件名稱和資料夾的路徑，一定要完全對應，否則編譯或執行時，就會找不到類別。

沒有宣告 package 的類別，會屬於預設套件。我們前面的範例大多是這樣寫的，但在正式的專案裡不建議這麼做。
-->

---

# 編譯與執行有套件的程式

在**來源根目錄**（`com` 資料夾所在的位置）執行，以 `com.school.Main` 為例：

```bash
javac com/school/Main.java   # 用檔案路徑，import 到的類別會一起編譯
java com.school.Main         # 用類別全名（套件 + 類別名稱）
```

| 常見錯誤 | 結果 |
| --- | --- |
| 進到 `com/school/` 裡執行 `javac Main.java` | `package com.school.model does not exist` |
| 進到 `com/school/` 裡執行 `java Main` | 找不到或無法載入主要類別 Main |

<!--
有了套件之後，編譯和執行的方式也要跟著調整。

關鍵是：一定要站在來源根目錄，也就是 com 資料夾所在的那一層，來下指令。以 com.school 套件裡的 Main 為例，編譯時，javac 後面接檔案路徑，com 斜線 school 斜線 Main.java。Main 有 import 到的其他類別，javac 會順著路徑找到，一起編譯。

執行時，java 後面要接類別的全名，也就是套件名稱加上類別名稱，com.school.Main，中間用點，而且不用加 .class。

初學者最常犯的錯，是直接進到 com/school 資料夾裡面下指令。這時候編譯會說找不到 com.school.model 套件；就算已經編譯好了，執行 java Main 也會出現「找不到或無法載入主要類別」。因為 Java 是從目前的資料夾，依照套件名稱往下找類別的，站錯位置，就找不到了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 匯入套件
# Importing Packages

<!--
建立好套件之後，要使用其他套件的類別，就需要 import。
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
import 讓我們在程式裡，可以直接使用類別的簡短名稱，不用每次都寫出完整的套件路徑。

import 要寫在 package 宣告之後、類別宣告之前。看範例，StudentService 匯入了 model 套件的 Student，以及 java.util 的 ArrayList 和 List，接著就能直接使用這些名稱。

java.lang 套件裡的類別，例如 String 和 Math，編譯器會自動匯入，所以不需要寫 import。
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
import 也可以用星號，一次匯入某個套件裡所有的 public 類別。

這兩種寫法在效能上沒有任何差別，因為編譯之後的結果是一樣的。但用星號比較容易引入同名的類別，造成命名衝突，讀程式的人也不容易看出某個類別是從哪裡來的。

所以業界的慣例，是逐一匯入需要的類別。大部分 IDE 也會自動幫我們整理好。
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
import static 是靜態匯入，可以直接使用某個類別的靜態成員，不用再寫類別名稱。

看範例，匯入了 Math 的 PI、sqrt 和 pow 之後，計算面積時可以直接寫 PI 乘以 pow，不需要寫成 Math.PI 和 Math.pow。

不過要適度使用。如果靜態匯入太多，讀程式的人會不知道某個方法是從哪個類別來的。
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
也可以不使用 import，直接寫出完整的類別名稱，包含套件路徑，這叫做全限定名稱。

看範例，宣告 ArrayList 時，直接寫成 java.util.ArrayList。

這種寫法最適合的情況，是要同時使用兩個同名的類別。例如這裡同時用到 java.util 的 Date 和 java.sql 的 Date，就至少要有一個寫出全名，才能區分清楚。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 常用標準套件
# Standard Library Packages

<!--
接下來，我們認識 JDK 提供了哪些常用的標準套件。
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
| `java.nio` / `java.nio.file` | 新 I/O、檔案系統 API | `ByteBuffer`、`Path`、`Files` |
| `java.net` | 網路 | `URL`、`Socket`、`HttpURLConnection` |
| `java.sql` | 資料庫 (JDBC) | `Connection`、`Statement`、`ResultSet` |

<!--
這張表列出了 JDK 最常用的幾個套件。

java.lang 是核心套件，包含 String、Math、Integer 和 System，會自動匯入。java.util 是工具和集合框架，例如 ArrayList、HashMap 和 Scanner。java.io 是傳統的輸入輸出，java.nio 則是新版的輸入輸出和檔案系統 API。java.net 處理網路，java.sql 則用來連接資料庫。

這些套件在後面的章節都會陸續用到。
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
我們在日期時間那一章學過的 java.time，也是一個標準套件，它是 Java 8 加入的新版日期時間 API。

裡面的 LocalDate 是日期，LocalTime 是時間，LocalDateTime 是日期加時間，ZonedDateTime 帶有時區，DateTimeFormatter 則用來格式化和解析。
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
看一個簡單的範例複習一下。

匯入 LocalDate 和 DateTimeFormatter 之後，取得今天的日期，再用年斜線月斜線日的格式輸出。注意 DateTimeFormatter 在 java.time.format 這個子套件裡，所以 import 的路徑要寫完整。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 存取控制修飾詞
# Access Modifiers

<!--
接下來，我們把四種存取修飾詞完整整理一次。學過套件之後，對它們的範圍會更清楚。
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
Java 有四種存取等級。

private 只有同一個類別可以存取。不寫任何修飾詞，是預設的套件等級，同一個套件裡的類別都可以存取。protected 除了同一個套件，不同套件的子類別也可以存取。public 則是任何地方都可以存取。

所以範圍由小到大，依序是 private、預設、protected，最後是 public。
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
private 是最嚴格的限制，只有同一個類別裡可以存取。

它主要用在兩個地方：成員變數，以及只在類別內部使用的方法。看範例，BankAccount 的 balance 是 private，外部只能透過 deposit 和 getBalance 存取。log 方法也是 private，因為它只是內部記錄用的工具，不需要讓外面知道。
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
不加任何修飾詞，就是預設的套件等級，也叫做 package-private。同一個套件裡的所有類別可以存取，套件外的類別則不行。

看範例，Helper 類別沒有寫 public，所以只有 com.example.util 套件裡的類別能使用它。同一個套件的 StringUtils，可以呼叫 Helper.format。

這樣的設計，可以把只供套件內部使用的輔助類別隱藏起來，對外只公開 StringUtils。
-->

---

# `protected` — 保護繼承鏈

- 同套件的類別可存取，**不同套件的子類別也可存取**（透過繼承）

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
protected 主要是為了繼承而設計的。

同一個套件的類別可以存取它；而且，不同套件的子類別，也可以存取。

看範例，Animal 在 animal 套件，它的 name 和 breathe 都是 protected。Dog 在另一個 pet 套件，但因為 Dog 繼承了 Animal，所以在 Dog 裡面，可以直接使用 name，也能呼叫 breathe。
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
public 是完全開放，任何套件的任何類別都可以存取。

要注意一條規則：每個 .java 檔案，最多只能有一個 public 類別，而且類別名稱必須和檔名一樣。

看範例，Student 是 public 類別，建構子和 getter 也是 public，讓其他套件都能使用；但欄位仍然是 private，維持封裝。
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
這張表整理了存取修飾詞可以用在哪裡。

成員變數、方法和建構子，四種修飾詞都可以使用。但最外層的類別，只能是 public 或預設的套件等級，不能宣告成 private 或 protected。

private 和 protected 的類別，只能出現在其他類別的內部，也就是之前學過的內部類別。
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
這張表整理了封裝設計的原則。

成員變數用 private，保護資料，不讓外部直接修改。對外提供的方法用 public，透過方法控制存取，還可以在裡面加入驗證的邏輯。只給子類別使用的方法用 protected。只在類別內部使用的工具方法，則用 private 隱藏起來。
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
我們用溫度類別來看看這些原則。

celsius 是 private，外部不能直接修改。setCelsius 是 public，但在設定之前，會先檢查溫度是否低於絕對零度，如果是，就拋出例外，拒絕這個不合理的值。

另外，getFahrenheit 是由攝氏溫度計算出來的，外部只要呼叫方法，不需要知道它是怎麼換算的。這就是封裝的好處。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 模組系統 (JPMS)
# Java Platform Module System — JDK 9

<!--
最後，我們簡單認識 Java 9 加入的模組系統，英文縮寫是 JPMS。
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
套件雖然能整理程式碼，但在大型專案裡，還是有一些限制。

第一，大型專案可能用到幾百個函式庫，版本衝突很難管理。第二，存取控制不夠精確，public 的類別對整個程式都看得見，沒辦法限制只開放給某些套件。第三，JDK 本身太龐大，就算是小型裝置，也要帶著整個 JDK。

模組就是一組套件的集合，它會明確宣告「我提供了什麼」以及「我需要什麼」，讓相依關係變得清楚。
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
模組用一個叫 module-info.java 的檔案來宣告，放在模組的根目錄。

看範例，module 後面是模組名稱。requires 宣告這個模組依賴哪些模組，例如資料庫的 java.sql。exports 宣告哪些套件要對外開放。最後一行 exports 加上 to，表示 service 套件只開放給指定的 ui 模組。

沒有被 exports 的套件，就算裡面的類別是 public，其他模組也無法使用。
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

```bash
# 查看目前 JDK 所有模組
java --list-modules

# 查看特定模組的套件內容
java --describe-module java.base
```

<!--
從 Java 9 開始，JDK 本身也模組化了。

java.base 是所有模組的基礎，包含 java.lang 和 java.util，會自動被引入。其他的例如 java.sql 處理資料庫，java.desktop 處理圖形介面。

我們可以用 java 加上 list-modules 參數，查看目前 JDK 有哪些模組。

模組系統主要用在大型專案和函式庫開發，初學階段知道有這個概念就可以了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 課堂練習
# Practice

<!--
接下來，我們用一題練習，實際設計一個有套件結構的專案。
-->

---
layout: default
---

# 練習：設計套件結構與存取控制
### 任務說明

設計一個簡單的學生管理系統，要求：

1. 在套件 `com.school.model` 建立 `Student` 類別：
   - 成員變數 `name`（`private`）、`grade`（`protected`）、`studentId`（`public`）
   - `public` 建構方法 `Student(String studentId, String name, int grade)`
   - `public` 的 `getName()` / `setName()`、`getGrade()` / `setGrade()`
2. 在套件 `com.school.service` 建立 `StudentService` 類別：
   - `import` 並使用 `Student`
   - 提供 `public` 方法 `printInfo(Student s)`，印出學號、姓名、年級
3. 在套件 `com.school` 建立 `Main` 類別：建立 `new Student("S001", "炭治郎", 2)`，交給 `StudentService` 印出

<!--
這一題要設計一個簡單的學生管理系統。

第一，在 com.school.model 套件裡建立 Student 類別。name 是 private，grade 是 protected，studentId 是 public。建構方法的參數順序是學號、姓名、年級，另外提供 name 和 grade 的 getter 和 setter。studentId 是 public，可以直接存取，所以不用寫 getter。

第二，在 com.school.service 套件裡建立 StudentService，匯入 Student，提供 printInfo 方法，印出學號、姓名和年級。

第三，在 com.school 套件裡建立 Main 類別，建立一個學號 S001、名字炭治郎、2 年級的學生，交給 StudentService 印出來。
-->

---
layout: default
---

# 練習：預期輸出與延伸

在 `com` 所在的資料夾執行 `javac com/school/Main.java`、`java com.school.Main`：

```
學號：S001
姓名：炭治郎
年級：2
```

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
🔍 <b>延伸：</b>在 <code>printInfo</code> 裡把 <code>s.getGrade()</code> 改成 <code>s.grade</code>，編譯看看會出現什麼錯誤？為什麼 <code>s.studentId</code> 就可以直接用？
</div>

<!--
寫好之後，記得回到 com 資料夾所在的那一層，先用 javac 編譯 Main.java，再用 java 加上全名 com.school.Main 執行。正確的話，會印出學號、姓名和年級三行。

完成的同學，可以挑戰延伸題：在 printInfo 裡，把 s.getGrade() 改成直接寫 s.grade，編譯看看會發生什麼事；再想想看，為什麼 s.studentId 就可以直接使用。

我們先暫停一下，自己動手試試看。

【講師】給學生約 20 分鐘實作，留意資料夾結構是否與套件名稱對應，以及是否在正確的資料夾下指令。
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
    public void setGrade(int grade) { this.grade = grade; }
}
```

<!--
我們來看解答，先看 Student。

第一行宣告 package com.school.model。三個欄位依照要求，分別是 private、protected 和 public。建構子和 name、grade 的 getter、setter 都是 public，讓其他套件可以使用。
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
接著是 StudentService。

它在 service 套件，所以要先 import com.school.model.Student。printInfo 方法裡，studentId 是 public，可以直接存取；name 是 private，要透過 getName；grade 是 protected，StudentService 既不在同一個套件，也不是子類別，所以同樣要透過 getGrade。

這裡正好驗證了不同存取等級的差別。如果你做了延伸題，把 getGrade 改成 s.grade，編譯器會說 grade has protected access，也就是 grade 是 protected，這裡不能存取。
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
最後是 Main。

它匯入了 Student 和 StudentService，建立一個學號 S001、名字炭治郎、2 年級的學生，再交給 StudentService 印出資訊。

三個類別分別放在三個套件，各自負責不同的工作，這就是一個專案最基本的結構。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了用套件組織程式碼。

套件對應到資料夾，名稱用反轉的網域開頭，全部小寫。用 package 宣告類別屬於哪個套件，用 import 使用其他套件的類別，java.lang 會自動匯入。

四種存取等級由小到大是 private、預設、protected 和 public。設計類別時，欄位用 private，對外的方法用 public。Java 9 的模組系統，則能更精確地控制套件之間的相依和存取。

【講師】開放 Q&A。
-->

---
layout: end
---

# 課程結束
### 套件組織程式碼，存取控制保護封裝邊界

<!--
套件組織程式碼，存取控制守住封裝的邊界。

下一章，我們要學習例外處理。我們下一章見。
-->

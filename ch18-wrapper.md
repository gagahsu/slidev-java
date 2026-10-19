---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 包裝類別 (Wrapper Classes)
routeAlias: ch18
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
  <h1 style="color: #1a5c5c; font-size: 3.8rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">包裝類別</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「讓基本型態穿上物件的外衣，解鎖完整功能」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章的主題是包裝類別。

int、double 這些基本型態，用起來簡單又快速，但它們不是物件，不能呼叫方法，也不能放進 ArrayList 這類只接受物件的集合。

包裝類別就是讓基本型態「穿上物件的外衣」。學完這一章，我們會理解自動裝箱和拆箱的運作方式，也會使用包裝類別提供的各種轉換和工具方法。
-->

---
layout: default
---

# Outline

- **包裝類別概念與對應關係**
- **自動裝箱與自動拆箱 (Autoboxing / Unboxing)**
- **Integer 類別的常用方法**
- **各類型的包裝類別方法**
- **實作練習**

<!--
這一章我們會先認識包裝類別的概念，以及每一種基本型態對應的包裝類別。

接著學自動裝箱和自動拆箱，以及它們可能造成的陷阱。然後看 Integer 類別的常用方法，再認識其他包裝類別。最後有三題練習。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 包裝類別概念

<!--
我們先來看什麼是包裝類別，以及它和基本型態有什麼不同。
-->

---
layout: default
---

# 什麼是包裝類別？

Java 的資料型態分為兩種：

- **基本型態 (Primitive type)**：`int`, `double`, `boolean`... 儲存純值（區域變數放在 Stack）
- **參考型態 (Reference type)**：物件，放在 Heap，可以呼叫方法

```java
int score = 95;           // 基本型態，無法呼叫方法
Integer scoreObj = 95;    // 包裝類別，可以呼叫方法
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 包裝類別讓每種基本型態都有對應的「物件版本」，擁有各種工具方法可供使用。
</div>

<!--
Java 的資料型態分成兩種。

基本型態，例如 int、double、boolean，存放的是單純的值，不能呼叫方法。參考型態則是物件，存放在 Heap 上，可以呼叫方法。

看範例，int score 只是一個數字；Integer scoreObj 則是一個物件，可以呼叫各種方法。

包裝類別讓每一種基本型態，都有一個對應的物件版本，並且提供了很多實用的工具方法。
-->

---

# 基本型態與包裝類別對照表

| 基本型態 | 包裝類別 | 基本型態 | 包裝類別 |
| --- | --- | --- | --- |
| `byte` | `Byte` | `float` | `Float` |
| `short` | `Short` | `double` | `Double` |
| `int` | `Integer` | `char` | `Character` |
| `long` | `Long` | `boolean` | `Boolean` |

<!--
這張表列出了八種基本型態和它們的包裝類別。

大部分的規則很簡單，就是把第一個字母改成大寫：byte 對應 Byte，double 對應 Double，boolean 對應 Boolean。

只有兩個例外要特別記：int 對應的是 Integer，char 對應的是 Character，它們用的是完整的英文單字。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 自動裝箱與自動拆箱

<!--
接下來，我們看基本型態和包裝類別之間，是怎麼互相轉換的。
-->

---
layout: default
---

# 手動裝箱 (Boxing)

將基本型態轉換成包裝類別物件，稱為「裝箱」：

```java
// 手動裝箱（沒有自動裝箱時的寫法）
int hp = 100;
Integer hpObj = Integer.valueOf(hp);

System.out.println(hpObj); // 100
```

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <code>new Integer(100)</code> 建構子自 Java 9 起棄用，Java 16 起標記為「預計移除」，請改用 <code>Integer.valueOf()</code>。
</div>

<!--
把基本型態轉換成包裝類別的物件，叫做「裝箱」。

看範例，hp 是 int，用 Integer.valueOf 把它裝箱成 Integer 物件。

下面的警告要注意：以前的教材常用 new Integer 建立物件，但這個建構子從 Java 9 開始就不建議使用，Java 16 更標記為預計移除。現在請改用 Integer.valueOf。
-->

---

# 自動裝箱 (Autoboxing)

Java 5 之後，編譯器自動完成裝箱：

```java
int hp = 100;
Integer hpObj = hp; // 編譯器自動呼叫 Integer.valueOf(hp)
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 自動裝箱讓基本型態可以直接指派給包裝類別變數，背後由編譯器自動呼叫 <code>valueOf()</code>。
</div>

<!--
從 Java 5 開始，編譯器會自動幫我們裝箱，這叫做自動裝箱。

看範例，直接把 int 的 hp 指定給 Integer 變數就可以了，不需要自己呼叫 valueOf。編譯器在背後，會自動幫我們加上 Integer.valueOf。
-->

---

# 自動拆箱 (Unboxing)

從包裝類別物件取出基本型態，稱為「拆箱」：

```java
Integer hpObj = 100;

// 自動拆箱：編譯器呼叫 hpObj.intValue()
int hp = hpObj;

// 也可直接參與數學運算
int newHp = hpObj + 50; // 自動拆箱後計算
System.out.println(newHp); // 150
```

<!--
反過來，從包裝類別取出基本型態的值，叫做「拆箱」。

看範例，把 Integer 的 hpObj 直接指定給 int 變數，編譯器會自動呼叫 intValue，這就是自動拆箱。

包裝類別也可以直接參與數學運算，hpObj 加 50，會先自動拆箱，再計算，結果是 150。
-->

---

# 自動裝拆箱與集合框架

包裝類別最常用的場景：放入只接受物件的集合框架

```java
import java.util.ArrayList;

ArrayList<Integer> ranks = new ArrayList<>();

// 自動裝箱：int 放入 ArrayList
ranks.add(1);   // 等同 ranks.add(Integer.valueOf(1))
ranks.add(3);

// 自動拆箱：Integer 取出後可直接計算
int total = ranks.get(0) + ranks.get(1); // 4
```

<!--
包裝類別最常用的地方，就是放進集合裡。

ArrayList 這類集合只能存放物件，不能直接存放 int。所以宣告時，角括號裡要寫 Integer，而不是 int。

看範例，呼叫 add 1 時，會自動裝箱成 Integer；用 get 取出之後，又會自動拆箱成 int，可以直接相加，得到 4。有了自動裝箱和拆箱，我們幾乎感覺不到轉換的存在。
-->

---

# 裝箱陷阱：== 比較的問題

包裝類別是物件，`==` 比較的是位址，而非數值：

```java
Integer a = 200;
Integer b = 200;
System.out.println(a == b);      // false (不同物件)
System.out.println(a.equals(b)); // true  (數值相同)
```

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>快取範圍：</b> Java 快取 -128 到 127 的 Integer 物件，在此範圍內 <code>==</code> 可能為 true，超出則為 false。建議永遠用 <code>equals()</code>。
</div>

<!--
不過，自動裝箱也帶來了一些陷阱。第一個是用兩個等號比較。

包裝類別是物件，兩個等號比較的是記憶體位址，而不是數值。看範例，a 和 b 都是 200，用兩個等號比較，結果卻是 false；用 equals 才是 true。

更容易讓人困惑的是，Java 會快取負 128 到 127 之間的 Integer 物件。在這個範圍內，兩個等號可能會是 true，超出範圍就變成 false。所以，比較包裝類別的值，永遠用 equals。
-->

---

# null 陷阱：自動拆箱的危險

當包裝類別為 `null`時，自動拆箱會觸發 NullPointerException：

```java
Integer hp = null;

// 以下會拋出 NullPointerException！
int damage = hp - 10;
```

```java
// 安全寫法：先判斷 null
int damage = (hp != null) ? hp - 10 : 0;
```

<!--
第二個陷阱是 null。

包裝類別是物件，所以它可以是 null。看範例，hp 是 null，拿它減 10 時，會先自動拆箱，而 null 沒有值可以取出，就會拋出 NullPointerException。

安全的寫法，是先判斷 hp 是不是 null，不是才進行計算，否則就給一個預設值 0。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Integer 類別的常用方法

<!--
接下來，我們看 Integer 類別提供了哪些常用的方法。
-->

---
layout: default
---

# Integer 常數

Integer 類別提供了重要的邊界常數：

```java
System.out.println(Integer.MAX_VALUE); // 2147483647
System.out.println(Integer.MIN_VALUE); // -2147483648
System.out.println(Integer.SIZE);      // 32 (位元數)
System.out.println(Integer.BYTES);     // 4  (位元組數)
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 int 的範圍是 -2<sup>31</sup> 到 2<sup>31</sup>-1，超出此範圍會發生「整數溢位 (overflow)」，需改用 <code>long</code> 或 <code>Long</code>。
</div>

<!--
Integer 類別提供了幾個重要的常數。

MAX_VALUE 是 int 能表示的最大值，大約是 21 億；MIN_VALUE 是最小值，大約是負 21 億。SIZE 是 32，代表 int 佔 32 個位元；BYTES 是 4，代表佔 4 個位元組。

如果計算的結果超出這個範圍，就會發生整數溢位，得到一個完全錯誤的數字。需要更大的範圍時，就要改用 long。
-->

---

# Integer 型態轉換方法

| 方法名稱 | 說明 |
| --- | --- |
| `Integer.parseInt(String s)` | 字串轉 `int` |
| `Integer.valueOf(int i)` | `int` 轉 `Integer` |
| `Integer.valueOf(String s)` | 字串轉 `Integer` |
| `intValue()` | `Integer` 轉 `int`（拆箱）|
| `toString()` | `Integer` 轉字串 |

<!--
Integer 有一組用來轉換型態的方法。

parseInt 把字串轉成 int。valueOf 可以把 int 或字串，轉成 Integer 物件。intValue 把 Integer 轉成 int，也就是拆箱。toString 則把 Integer 轉成字串。
-->

---

# 字串轉整數 — 範例

```java
String inputHp = "150";

// 字串 → int (最常用)
int hp = Integer.parseInt(inputHp);

// 字串 → Integer
Integer hpObj = Integer.valueOf(inputHp);

System.out.println(hp + 50);     // 200
System.out.println(hpObj + 50);  // 200
```

<!--
我們看字串轉整數的例子。

使用者輸入的資料通常是字串，例如 150。用 parseInt 轉成 int，用 valueOf 轉成 Integer，兩者再加 50，結果都是 200。

實務上最常用的是 parseInt。要注意，如果字串不是合法的整數，例如 abc，就會拋出 NumberFormatException。
-->

---

# 進位轉換方法

| 方法名稱 | 說明 |
| --- | --- |
| `Integer.toBinaryString(int i)` | 轉換成二進位字串 |
| `Integer.toOctalString(int i)` | 轉換成八進位字串 |
| `Integer.toHexString(int i)` | 轉換成十六進位字串 |
| `Integer.parseInt(String s, int radix)` | 將指定進位的字串轉為十進位 |

<!--
Integer 也提供了進位轉換的方法。

toBinaryString 轉成二進位字串，toOctalString 轉成八進位，toHexString 轉成十六進位。反過來，parseInt 也可以多傳一個參數，指定字串是幾進位，把它轉回十進位。
-->

---

# 進位轉換 — 範例

```java
int power = 255;

System.out.println(Integer.toBinaryString(power)); // 11111111
System.out.println(Integer.toOctalString(power));  // 377
System.out.println(Integer.toHexString(power));    // ff

// 二進位字串 "11111111" 轉回十進位
System.out.println(Integer.parseInt("11111111", 2)); // 255
```

<!--
看範例，255 轉成二進位是 8 個 1，八進位是 377，十六進位是 ff。

最後一行，把二進位的字串 11111111 轉回十進位，第二個參數傳入 2，代表它是二進位，結果就是 255。

在處理顏色碼、權限設定這類資料時，進位轉換很常用到。
-->

---

# 數值比較與工具方法

| 方法名稱 | 說明 |
| --- | --- |
| `Integer.max(int a, int b)` | 回傳較大值 |
| `Integer.min(int a, int b)` | 回傳較小值 |
| `Integer.sum(int a, int b)` | 回傳總和 |
| `Integer.compare(int x, int y)` | 比較大小，回傳負/零/正 |

<!--
Integer 還提供了一些比較和計算的工具方法。

max 回傳比較大的值，min 回傳比較小的值，sum 回傳兩數的總和。compare 用來比較大小，回傳負數、零或正數，代表前者比較小、相等或比較大。
-->

---

# 數值比較 — 範例

```java
int atkA = 180;  // 炭治郎攻擊力
int atkB = 160;  // 禰豆子攻擊力

System.out.println(Integer.max(atkA, atkB));     // 180
System.out.println(Integer.min(atkA, atkB));     // 160
System.out.println(Integer.sum(atkA, atkB));     // 340
System.out.println(Integer.compare(atkA, atkB)); // 正值 (atkA > atkB)
```

<!--
看範例，炭治郎的攻擊力是 180，禰豆子是 160。

max 得到 180，min 得到 160，sum 得到 340。compare 回傳正數，因為 180 比 160 大。

compare 在排序的時候特別有用，之後學集合排序時會再用到。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 其他包裝類別方法

<!--
除了 Integer，其他的包裝類別也提供了各自實用的方法。
-->

---
layout: default
---

# Double 類別

| 方法名稱 | 說明 |
| --- | --- |
| `Double.parseDouble(String s)` | 字串轉 `double` |
| `Double.isNaN(double v)` | 是否為 NaN（非數值）|
| `Double.isInfinite(double v)` | 是否為無限大 |
| `Double.max(double a, double b)` | 回傳較大值 |

```java
System.out.println(Double.parseDouble("3.14")); // 3.14
System.out.println(Double.isNaN(0.0 / 0.0));    // true
System.out.println(Double.isInfinite(1.0 / 0.0)); // true
```

<!--
Double 類別有幾個常用的方法。

parseDouble 把字串轉成 double，3.14 就轉成 3.14。isNaN 判斷是不是 NaN，也就是「不是一個數字」，例如 0.0 除以 0.0 的結果。isInfinite 判斷是不是無限大，例如 1.0 除以 0.0。

注意，浮點數除以 0 不會出錯，而是得到 NaN 或無限大，所以需要用這兩個方法來檢查。
-->

---

# Boolean 類別

| 方法名稱 | 說明 |
| --- | --- |
| `Boolean.parseBoolean(String s)` | 字串轉 `boolean` |
| `Boolean.valueOf(boolean b)` | `boolean` 轉 `Boolean` |
| `Boolean.toString(boolean b)` | `boolean` 轉字串 |
| `Boolean.compare(boolean x, boolean y)` | 比較大小（true > false）|

```java
System.out.println(Boolean.parseBoolean("true"));  // true
System.out.println(Boolean.parseBoolean("True"));  // true
System.out.println(Boolean.parseBoolean("yes"));   // false
```

<!--
Boolean 類別的 parseBoolean，可以把字串轉成布林值。

只有字串是 true 的時候，才會回傳 true，而且不區分大小寫，所以大寫開頭的 True 也是 true。其他任何字串，包括 yes，都會回傳 false，不會拋出例外。
-->

---

# Character 類別（複習整合）

Character 是字元型態 `char` 的包裝類別，常用方法複習：

| 方法名稱 | 說明 |
| --- | --- |
| `Character.isDigit(char ch)` | 是否為數字字元 |
| `Character.isLetter(char ch)` | 是否為字母字元 |
| `Character.toUpperCase(char ch)` | 轉大寫 |
| `Character.toLowerCase(char ch)` | 轉小寫 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 Character 類別在 Ch12 已詳細介紹，這裡複習其作為包裝類別的定位。
</div>

<!--
Character 是 char 的包裝類別。它的方法在字元與字串那一章已經詳細介紹過了，例如判斷是不是數字或字母，以及轉換大小寫。

這裡我們從包裝類別的角度再看一次：它和 Integer、Double 一樣，讓基本型態的 char，有了一個物件的版本。
-->

---

# Number 抽象父類別

`Integer`、`Double`、`Float`、`Long` 等數值型別的共同父類別：

```java
Number n1 = Integer.valueOf(42);
Number n2 = Double.valueOf(3.14);

System.out.println(n1.intValue());    // 42
System.out.println(n2.doubleValue()); // 3.14
System.out.println(n2.intValue());    // 3 (截斷小數)
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>Number</code> 類別定義了 <code>intValue()</code>、<code>doubleValue()</code>、<code>longValue()</code> 等方法，所有數值型包裝類別都繼承自它。
</div>

<!--
Integer、Double、Float、Long 這些數值型的包裝類別，有一個共同的父類別，叫做 Number。

Number 定義了 intValue、doubleValue、longValue 這些方法，所以任何數值型的包裝類別，都可以轉成不同的基本型態。

看範例，n1 是 Integer，n2 是 Double，兩者都可以用 Number 型別的變數接收。n2 呼叫 intValue，會把 3.14 的小數截掉，得到 3。
-->

---
layout: default
---

# 練習一：讀取使用者輸入並計算
### 任務說明

請利用 `Scanner` 讀取兩個整數字串，轉換後計算並印出：
1. 兩數的和、差、積
2. 哪個數比較大

```java
Scanner sc = new Scanner(System.in);
String s1 = sc.nextLine();
String s2 = sc.nextLine();
// 請利用 Integer 的方法完成轉換與計算
```

<!--
接下來我們動手練習。

第一題，用 Scanner 讀入兩個字串形式的整數，轉換之後，計算並印出兩數的和、差、積，以及哪一個數比較大。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
-->

---

# 練習一：解題提示

1. 使用 `Integer.parseInt()` 將字串轉為 `int`。
2. 用算術運算子計算和、差、積。
3. 使用 `Integer.max()` 找出較大值。

<!--
我們來看提示。

用 Integer.parseInt 把兩個字串轉成 int，接著用加、減、乘算出和、差、積。比較大小可以用 Integer.max，直接得到比較大的值。
-->

---
layout: default
---

# 練習二：判斷字串是否為合法整數
### 任務說明

撰寫一個方法 `isInteger(String s)`，判斷輸入的字串是否可以成功轉為整數。

- `"123"` → true
- `"-45"` → true
- `"12.3"` → false
- `"abc"` → false

<!--
第二題，請寫一個 isInteger 方法，判斷一個字串能不能成功轉換成整數。

123 和負 45 都可以，回傳 true；12.3 有小數，abc 是文字，都要回傳 false。

一樣先暫停一下，自己動手寫寫看。
-->

---

# 練習二：解題提示

```java
static boolean isInteger(String s) {
    try {
        Integer.parseInt(s);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}
```

<!--
我們來看提示。

最直接的做法，是直接嘗試用 parseInt 轉換。如果成功，就回傳 true；如果拋出 NumberFormatException，就在 catch 裡回傳 false。

try 和 catch 是處理例外的語法，之後的章節會詳細介紹，這裡先照著用。
-->

---
layout: default
---

# 練習三：進位制轉換器
### 任務說明

讓使用者輸入一個十進位整數，印出它的二進位、八進位和十六進位表示。

- 輸入：`255`
- 輸出：
  - 二進位：`11111111`
  - 八進位：`377`
  - 十六進位：`ff`

<!--
第三題是進位制轉換器。

讓使用者輸入一個十進位的整數，印出它的二進位、八進位和十六進位。例如輸入 255，就印出 8 個 1、377 和 ff。

一樣先暫停一下，自己動手寫寫看。
-->

---

# 練習三：解題提示

1. 使用 `Integer.parseInt()` 讀取輸入。
2. 呼叫 `Integer.toBinaryString()`。
3. 呼叫 `Integer.toOctalString()`。
4. 呼叫 `Integer.toHexString()`。

<!--
我們來看提示。

先用 Integer.parseInt 把輸入轉成整數，接著分別呼叫 toBinaryString、toOctalString 和 toHexString，就能得到三種進位的表示方式。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們認識了包裝類別。

每種基本型態都有對應的包裝類別，其中 int 是 Integer，char 是 Character。Java 會自動裝箱和拆箱，讓基本型態可以放進集合。但要注意兩個陷阱：比較數值要用 equals，不要用兩個等號；包裝類別可能是 null，拆箱前要先檢查。

Integer 提供了邊界常數、字串轉換、進位轉換和比較的方法，數值型的包裝類別則都繼承自 Number。

下一章，我們要學習套件。

【講師】開放 Q&A。
-->

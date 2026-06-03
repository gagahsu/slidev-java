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
【開場白】
嘿各位，歡迎來到 Java 變裝秀！今天的主角是「包裝類別」（Wrapper Classes）。

【為什麼要學這個？】
你有沒有想過，為什麼 int 這種小嫩嫩在 ArrayList 面前會吃閉門羹？因為 ArrayList 很勢利，它只跟「物件」做朋友。所以我們得幫 int 穿上西裝，偽裝成物件，這就是包裝類別的由來。

【今天學完你會能做什麼】
學完這堂課，你就能在「基本型態」和「物件」之間絲滑切換，像忍者一樣穿梭在不同的資料型態中，還能解鎖一堆好用的工具方法。
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
【核心說明】
今天的菜單很簡單：先搞懂什麼是「西裝」（包裝類別），再看 Java 怎麼幫你「穿衣服」（自動裝箱），最後學學 Integer 還有它的好朋友們。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 包裝類別概念

<!--
【🎯 章節標題頁】
我們先來理解「為什麼需要包裝類別」。
-->

---
layout: default
---

# 什麼是包裝類別？

Java 的資料型態分為兩種：

- **基本型態 (Primitive type)**：`int`, `double`, `boolean`... 儲存純值，放在 Stack
- **參考型態 (Reference type)**：物件，放在 Heap，可以呼叫方法

```java
int score = 95;           // 基本型態，無法呼叫方法
Integer scoreObj = 95;    // 包裝類別，可以呼叫方法
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 包裝類別讓每種基本型態都有對應的「物件版本」，擁有各種工具方法可供使用。
</div>

<!--
【核心說明】
在 Java 世界，int 就像是赤裸的原始人，效率很高但沒什麼社交能力（不能呼叫方法）。Integer 則是穿上西裝的紳士，優雅但稍微重了一點。

【生活化比喻】
基本型態就像是你口袋裡的散裝零錢，好用但沒辦法送禮。包裝類別就是那個漂亮的紅包袋，把零錢裝進去之後，就能大方地送給 ArrayList 這種愛收禮的傢伙了。

【程式世界怎麼用】
如果你只是要算加減乘除，用 int 就好；但如果你要把資料丟進集合、或是要呼叫 parseInt 這種工具，你就得請出 Integer 大神。

⚠️ 學生常見誤解：
別以為 Integer 和 int 是完全一樣的東西。Integer 可能會是 null，這就像是紅包袋裡是空的，如果你硬要拿裡面的錢，程式會直接死給你看（NullPointerException）。
-->

---

# 基本型態與包裝類別對照表

| 基本型態 | 包裝類別 |
| --- | --- |
| `byte` | `Byte` |
| `short` | `Short` |
| `int` | `Integer` |
| `long` | `Long` |
| `float` | `Float` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

<!--
【核心說明】
這張表就是變裝對照表。大部分都是大寫第一個字母就好，Java 工程師雖然懶，但偶爾也會想搞點特別的。

【特別注意】
int 變 Integer，char 變 Character。別寫成 Int 或 Char，編譯器會噴火給你看。這就像是你把 Batman 叫成 Bat，他會很不爽。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 自動裝箱與自動拆箱

<!--
【🎯 章節標題頁】
接下來看 Java 偷偷幫你做的魔法：自動裝箱與自動拆箱。
-->

---
layout: default
---

# 手動裝箱 (Boxing)

將基本型態轉換成包裝類別物件，稱為「裝箱」：

```java
// 手動裝箱 (JDK 5 以前的舊寫法)
int hp = 100;
Integer hpObj = Integer.valueOf(hp);

System.out.println(hpObj); // 100
```

<!--
【核心說明】
在古老的 JDK 5 以前，工程師要手動「裝箱」。這就像是你要親手把禮物放進盒子裡，還要貼膠帶，超級麻煩。

【逐步解說】
Integer.valueOf(hp) 就是在手動裝箱。現在沒人在這樣寫了，除非你想體驗一下阿公那個年代是怎麼寫程式的。
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
【核心說明】
謝天謝地，Java 5 之後有了自動裝箱。Java 變聰明了，它看到你把 int 給了 Integer，就會自動幫你呼叫 valueOf()。

【逐步解說】
這就像是你把禮物丟在櫃檯，服務員（編譯器）就默默地幫你包裝好了。這就是所謂的「語法糖」，吃起來很甜，寫起來很爽。
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
【核心說明】
有裝就有拆。當你把 Integer 丟回給 int，或者拿它來做加法，Java 就會自動「拆箱」。

【逐步解說】
它會自動幫你呼叫 intValue()。這過程快到你感覺不到，就像拆快遞一樣爽快。
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
【核心說明】
這是包裝類別最常被用到的地方：集合框架（Collection Framework）只能存放物件，所以我們需要 Integer 而不是 int。

【逐步解說】
你看範例，我們把 int 的 1 和 3 放進 ArrayList<Integer> 時，Java 會自動裝箱。取出來運算時又自動拆箱，整個過程你幾乎感覺不到。

💼 業界實務：
這在 Java 開發中極為常見，幾乎每個專案都會用到。
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
【核心說明】
這張圖是面試魔王題！很多新手在這裡被虐得體無完膚。

【生活化比喻】
Integer 是物件，== 比較的是「你是哪一盒」，而不是「盒子裡裝什麼」。equals() 才是比較盒子裡的糖果是不是同一種。

⚠️ 學生常見誤解：
最機車的是，Java 為了省錢，把 -128 到 127 的 Integer 物件都快取起來了。所以小數字用 == 可能會過，大數字就直接 false。這就像是 100 塊以下用舊紅包袋，100 塊以上每次都買新的。永遠用 equals()，保平安！
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
【核心說明】
這就是我說的「空紅包袋」悲劇。

【逐步解說】
當 hp 是 null，你叫它去扣血，Java 會試著拆箱（脫衣服），結果發現裡面根本沒人！於是它就崩潰了，噴你一個 NullPointerException。

💼 業界實務：
在處理後端回傳資料時，一定要檢查是不是 null。別對 null 溫柔，它會讓你加班到半夜。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# Integer 類別的常用方法

<!--
【🎯 章節標題頁】
Integer 是最常用的包裝類別，讓我們深入認識它提供的工具方法。
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
【核心說明】
Integer 大神家裡的存摺，記錄了 int 能裝下的最大財富和最大負債。

【逐步解說】
MAX_VALUE 大約是 21 億。如果你開發的遊戲玩家存款超過這個數字，你的程式就會「溢位」，變成負債 21 億。到時候玩家會拿刀去你公司找你，所以大數字請改用 long。
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
【核心說明】
這些是最常用到的型態轉換方法，幾乎每個 Java 程式都會用到。

【特別提醒】
parseInt 回傳的是純值 int，valueOf 回傳的是物件 Integer。這就像是「直接給你錢」和「給你裝在紅包裡的錢」的差別。
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
【核心說明】
這是最常見的「通靈」現場。你要把人類輸入的「文字」轉成電腦懂的「數字」。

【逐步解說】
Integer.parseInt("150") 會把字串變 int。但如果字串是 "150元" 或是 "abc"，它就會生氣地拋出錯誤。它沒那麼聰明，別期待它能幫你自動濾掉雜質。
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
【核心說明】
Integer 內建了進位制轉換的工具，這在電腦概論、資安領域非常常用。

【逐步解說】
不管你是要看二進位（0101）還是十六進位（0x FF），Integer 都幫你準備好了。
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
【核心說明】
255 是一個很有代表性的數字：在二進位是 8 個 1，在十六進位是 ff。

【程式世界怎麼用】
RGB 顏色碼、IP 位址、記憶體位址這些都常用到十六進位。
Integer.toHexString 可以幫你快速把數字轉成十六進位字串，不需要自己算。
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
【核心說明】
Integer 還提供了幾個靜態工具方法，在搭配 Stream 使用時特別方便。

【逐步解說】
雖然你也可以用 Math.max、+ 做到同樣的事，但 Integer.max、Integer.sum 可以直接當作方法參考（Method Reference）傳入 Stream 中，這在現代 Java 程式設計中越來越常用到。
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
【逐步解說】
炭治郎和禰豆子的攻擊力大比拼！compare 回傳正數就代表前面那個贏了。如果你回傳的是 0，代表平手，可以回家吃飯了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# 其他包裝類別方法

<!--
【🎯 章節標題頁】
除了 Integer，其他包裝類別也有各自的工具方法，我們快速帶過常用的部分。
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
【核心說明】
Double 也有包裝類別，功能跟 Integer 差不多，但多了幾個「不科學」的方法。

【逐步解說】
isNaN 是一個特別的判斷，「NaN」代表 Not a Number，就像 0 除以 0 這種無意義的運算結果。
注意，你不能用 == Double.NaN 來判斷一個值是不是 NaN，必須用 isNaN 方法。

⚠️ 學生常見誤解：
NaN 最傲嬌了，它連自己都不認。Double.NaN == Double.NaN 是 false。所以你必須用 isNaN() 來問它。
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
【核心說明】
Boolean 包裝類別最常用在讀取設定檔或系統屬性的場景。

【逐步解說】
parseBoolean 是大小寫不敏感的，只要字串內容等於 "true"（不管大小寫），就回傳 true；其他所有值一律回傳 false。

⚠️ 學生常見誤解：
Boolean.parseBoolean("yes") 回傳的是 false，不是 true！因為它只認識 "true" 這個字串。
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
【核心說明】
Character 是我們的老朋友了，處理字元的專利。

【逐步解說】
你想知道一個字是不是數字、是不是字母、或是要轉大小寫，找 Character 就對了。它雖然單純，但很勤奮。
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
【核心說明】
這就是所有數值型別的包裝類別的老祖宗。

【逐步解說】
不管是 Integer 還是 Double，大家都是 Number 的子孫。所以你可以用 Number 來接所有的數字，再隨意地把 3.14 轉成 int（當然小數點會被無情地拋棄）。
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
【出題前的鋪陳】
來吧，英雄們，展示你們「通靈」文字的能力到了！

【問題引導】
使用者輸入的是文字，你要把它變成可以計算的數字，這就是 parseInt 最典型的應用場景。
-->

---

# 練習一：解題提示

1. 使用 `Integer.parseInt()` 將字串轉為 `int`。
2. 用算術運算子計算和、差、積。
3. 使用 `Integer.max()` 找出較大值。

<!--
【逐步解說】
先把兩個字串都 parseInt，接下來就是普通的數學運算。
用 Integer.max 比較大小，結果直接印出來就好。
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
【出題前的鋪陳】
在真實世界，使用者總會輸入一些奇奇怪怪的東西，比如在年齡欄位寫 "永遠的 18 歲"。

【問題引導】
parseInt 在遇到非法字串時會拋出 NumberFormatException。
你可以利用 try-catch 捕捉這個例外，來判斷是否為合法整數。
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
【逐步解說】
這是一個非常經典的「試著解析，失敗就回傳 false」的寫法。
try 裡面嘗試 parseInt，如果成功就繼續執行 return true。
如果字串非法，parseInt 會拋出 NumberFormatException，被 catch 接住後回傳 false。
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
【出題前的鋪陳】
我們來做一個簡易的駭客工具，把數字變換成各種神祕的編碼。

【問題引導】
把 10 進位轉成 2、8、16 進位。這題基本上是在考你記不記得那幾個方法的名字。
-->

---

# 練習三：解題提示

1. 使用 `Integer.parseInt()` 讀取輸入。
2. 呼叫 `Integer.toBinaryString()`。
3. 呼叫 `Integer.toOctalString()`。
4. 呼叫 `Integer.toHexString()`。

<!--
【逐步解說】
就是把四個方法串起來用，是一個練習熟悉 API 的好機會。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【開場白】
恭喜大家，現在你們已經是「變裝大師」了！

【等待與觀察】
有沒有人對「紅包袋」理論有疑問的？或是還在糾結為什麼 == 會騙人？沒關係，儘管問，我不收學費，只收掌聲。
-->

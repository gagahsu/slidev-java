---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: Java 介面 (Interface)
routeAlias: interface
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
    介面 (Interface)
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「定義行為契約，讓不同類別共享相同的能力」
  </p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
各位好！在學完了「抽象類別」那個只會出一張嘴的主管之後，今天我們要來學 Java 的靈魂——「介面（Interface）」。如果你覺得繼承一個老爸已經很累了，那介面會讓你知道，這世界上原來可以有一堆人來管你。

【為什麼要學這個？】
介面就像是「能力證照」。如果你想飛，你不需要把自己變成一隻鳥，你只需要去考張「飛行執照」就好。介面讓你的程式碼不再被家族血緣給綁死，實現真正的「自由戀愛」...我是說「靈活擴充」。

【今天學完你會能做什麼】
學完這章，你就能設計出那種「高內聚、低耦合」的神級架構。你會明白為什麼 Spring Boot 框架裡到處都是介面，而且還能學會現代 Java 的大殺器：Lambda 表達式。
-->
---
layout: default
---

# Outline

- **認識介面** — 概念、語法、與抽象類別的比較
- **介面的成員變數** — `public static final`
- **Java 8 新增介面內容** — Default 方法、Static 方法、Functional Interface
- **Java 9 新增介面內容** — Private 方法
- **介面的繼承** — 基本繼承、多重繼承
- **進階主題** — 名稱衝突、Diamond 問題、密封介面 (Sealed)
- **課堂練習**

<!--
【帶讀大綱】
今天的大綱像是一部「介面進化史」。從最原始的、只能寫抽象方法的介面，講到 Java 8、9 之後那個快要變成「類別」的強大介面。最後我們會聊聊「鑽石問題」，這可是面試官最喜歡拿來為難新人的題目。

【重點預告】
重點中的重點：Java 8 的 Default 方法和 Functional Interface。這兩個東西徹底改變了我們寫 Java 的方式，不學會它們，你寫的 Code 就會有一股濃濃的「上個世紀」的味道。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 認識介面
# Interface

<!--
【段落轉換】
現在，拋開你對「類別」的刻板印象，讓我們進入「純行為規範」的世界。
-->
---
layout: default
---

# 什麼是介面？

- 介面（Interface）**不是物件**，而是**行為的規範**
- 繼承描述「IS-A」關係，介面描述「**有某種能力**」
  - 鳥（Bird）**是**動物 → 繼承自 Animal（IS-A）
  - 鳥會飛、飛機也會飛 → 「飛」是**行為**，不是類別關係
  - 設計介面定義「飛」的行為，讓不同類別去實作（implements）
- 介面**無法用 `new` 直接建立物件**

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>比較飛機與鳥：</b>兩者不是同一類時，但都有「飛」的能力 → 用介面定義共同行為
</div>

<!--
【核心說明】
繼承是「拼爹」，介面是「拼才華」。

【帶讀說明】
鳥是動物，這是它出生就註定的（繼承）。但飛機雖然不是動物，它也想飛啊！這時候「飛行」就是一種跨界的才華（介面）。

【生活化比喻】
繼承就像是「遺傳基因」，你爸長得高，你大概也長得高。介面就像是「證照」，不論你是台灣人、美國人還是火星人，只要你考過了「多益 900」，你就具備了「英文很好」的能力。

💼 業界實務：
在業界，我們講究「針對介面編程，而非針對實作」。這意思是，我的程式只需要知道你會「飛」就好，至於你是用翅膀飛還是用引擎飛，我根本不在乎。
-->
---

# 介面的語法

```java
interface Flyable {
    void fly();   // 預設為 public abstract，可省略宣告
}
```

- 所有方法預設是 `public abstract`（可省略）
- 所有成員變數預設是 `public static final`
- Java 8 之前介面**只能有**抽象方法

```java
class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("鳥兒飛翔");
    }
}
```

<!--
【帶讀語法】
介面的語法比類別更乾淨。看到那個 void fly() 了嗎？它其實前面隱藏了一長串的 public abstract。Java 幫你省了這些字，讓你不用寫到手痠。

【重點說明】
類別用 implements 來「簽署」這份介面契約。簽了之後，你就得負責任，把 fly() 給實作出來。

⚠️ 學生常見誤解：
implements 是「實作」，extends 是「擴充」。如果你在 implements 後面寫類別名稱，或是 extends 後面寫介面名稱，編譯器會覺得你在跟它開玩笑。
-->
---

# 介面的實作規則

- 實作類別必須覆寫**所有的抽象方法**
- 覆寫時存取控制**必須宣告為 `public`**
- 建議加上 `@Override` 確認覆寫正確

```java
class Airplane implements Flyable {
    @Override
    public void fly() {
        System.out.println("飛機用引擎飛");
    }
}
public class Demo {
    public static void main(String[] args) {
        Flyable bird = new Bird();
        Flyable airplane = new Airplane();
        bird.fly();     // 鳥兒飛翔
        airplane.fly(); // 飛機用引擎飛
    }
}
```

<!--
【帶讀程式碼】
你看 Airplane 也來 implements Flyable 了。這就是介面的強大：不管你是鳥還是機器，在 Flyable 眼中，你們都是「會飛的東西」。

【帶讀程式碼】
注意 Flyable bird = new Bird() 這行。我們宣告變數型態是介面，這就是「解耦合」的第一步。以後想換成 Airplane，這行後面的 new 換掉就好。

💼 業界實務：
如果你在 Spring Boot 裡看到有人寫 UserService impl = new UserServiceImpl()，請記得提醒他：「兄弟，我們不直接 new 的，我們是用介面注入（DI）。」
-->
---

# 介面 vs 抽象類別

| 比較項目 | 抽象類別 | 介面 |
| --- | --- | --- |
| 父類別 | 只能繼承一個 | 可繼承多個介面 |
| 子類別 | 只能 `extends` 一個 | 可 `implements` 多個 |
| 方法 | 可包含具體方法 | Java 8 前只能抽象方法 |
| 用途 | 類別間的緊密關係 | 不同類別間的共同行為 |
| 適用 | Car → Benz、Audi（IS-A） | Bird 與 Airplane 都能飛（行為） |

<!--
【帶讀表格】
這張表就是你的「面試生存手冊」。
最核心的區別：抽象類別是「身分鑑定」，你只有一個親爹；介面是「能力鑑定」，你可以有一堆師父。

💡 選擇準則：
如果你想幫子類別準備一些共用的屬性（變數）或預設的方法實作，用抽象類別。如果你只是想定義「你們都要會這個功能」，那就用介面。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的成員變數
# Interface Member Variables

<!--
【段落轉換】
介面裡也可以放變數，但它們的個性非常固執，一旦定了就不能改。
-->
---
layout: default
---

# 介面的成員變數

介面的成員變數預設是 `public`、`static`、`final`：

| 修飾詞 | 意義 |
| --- | --- |
| `public` | 所有人都可以取得 |
| `static` | 所有實作類別共享同一份 |
| `final` | 值不可更動（常數），**一定要給預設值** |

```java
interface Shape {
    double PI = 3.14159;  // 等同 public static final
    double area();        // 等同 public abstract
}
```

<!--
【帶讀表格】
介面裡的變數自動被貼上「三標籤」：
1. public：全世界都看得到。
2. static：全世界都用同一份。
3. final：誰也別想動它。

【帶讀程式碼】
double PI = 3.14159。這就是常數。如果你試圖在實作類別裡改 PI 的值，Java 會讓你明白什麼叫「徒勞無功」。

⚠️ 學生常見誤解：
常數命名習慣用全大寫。如果你寫 double pi = 3.14，雖然能跑，但在老鳥眼中，你就跟個剛學寫程式的國中生沒兩樣。
-->
---

# 介面成員變數 — 範例

```java
interface Shape {
    double PI = 3.14159;
    double area();
}
class Rectangle implements Shape {
    double width, height;
    Rectangle(double w, double h) { width = w; height = h; }
    public double area() { return width * height; }
}
class Circle implements Shape {
    double radius;
    Circle(double r) { radius = r; }
    public double area() { return PI * radius * radius; }
}
```

<!--
【帶讀程式碼】
看 Circle 類別。它直接用了 PI，因為它 implements 了 Shape，所以 Shape 的財產它都能直接拿來用。

【設計說明】
這就是常數的統一管理。不需要每個形狀類別都自己定義一次 PI，省得有人寫 3.14，有人寫 3.14159，最後算出來的面積亂七八糟。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 8 新增介面內容
# Default & Static Methods

<!--
【段落轉換】
接下來要講的是 Java 8 的重大革新。這讓介面從「廢物主管」變成了「全能管家」。
-->
---
layout: default
---

# Java 8 新增介面內容 — 概覽

| 功能 | Java 版本 | 說明 |
| --- | --- | --- |
| Constant variable | Java 7+ | 常數成員變數 |
| Abstract methods | Java 7+ | 抽象方法 |
| **Default methods** | **Java 8 新增** | 預設方法（有方法本體） |
| **Static methods** | **Java 8 新增** | 靜態方法 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>為什麼新增 Default 方法？</b>為維持向後相容性，舊介面可以新增方法，不破壞已有的實作類別
</div>

<!--
【帶讀表格】
Java 8 之前，介面就像是個「沒實權的憲法」，只會說「你要做這做那」，但從來不提供實作。Java 8 之後，介面可以帶點「乾貨」了。

💡 為什麼要加 Default 方法？
想像你維護了一個有 100 萬人使用的介面。你今天想加個新功能，如果你加成「抽象方法」，那這 100 萬人的程式碼都會瞬間噴錯，因為他們沒實作你的新方法。這時候你一定會被這 100 萬個工程師追殺。Default 方法就是用來「救命」的。
-->
---

# Default 方法

```java
interface Vehicle {
    String getBrand();
    default void alarmOn() {
        System.out.println("防盜啟動");
    }
    default void alarmOff() {
        System.out.println("防盜關閉");
    }
}
```

- 使用 `default` 關鍵字，介面內**直接提供預設實作**
- 實作類別可直接繼承 Default 方法，也可以 Override
- Default 方法**可以繼承**，不強制是 abstract

<!--
【帶讀程式碼】
default 關鍵字一加，大括號 {} 就出來了。這代表介面說：「我已經幫你寫好了，你要是用我的就不用自己寫。」

【核心說明】
這招讓介面變得有點像「類別」了。它不再只是開空頭支票，而是提供了「基礎套件」。如果你對預設的防盜系統不滿意，你當然可以自己 Override 一個更猛的。
-->
---

# Default 方法 — 範例

```java
class Car implements Vehicle {
    private String brand;
    Car(String b) { this.brand = b; }
    public String getBrand() { return brand; }
    // alarmOn / alarmOff 直接繼承自 Vehicle，不需重寫
}
public class Demo {
    public static void main(String[] args) {
        Car car = new Car("Toyota");
        car.alarmOn();   // 防盜啟動
        car.alarmOff();  // 防盜關閉
    }
}
```

<!--
【帶讀程式碼】
你看，Car 類別清清爽爽，只寫了自己必須實作的 getBrand()。那兩個 alarm 方法就像是天上掉下來的禮物，直接拿來就能用。

💼 業界實務：
這個設計徹底改變了 Java 的開發模式。現在我們可以在介面裡寫一堆共用的邏輯，而不用在那邊 extends 來 extends 去了。
-->
---

# Static 方法

```java
interface MathUtils {
    static int add(int a, int b) {
        return a + b;
    }
    static int multiply(int a, int b) {
        return a * b;
    }
}
```

- 在介面中定義 `static` 方法
- **只能用介面名稱呼叫**，不能透過物件呼叫
- 實作類別**無法 Override** static 方法

<!--
【帶讀程式碼】
介面也能有 static 方法！這讓介面可以兼差當「工具箱」。

⚠️ 學生常見誤解：
注意喔！你不能寫 obj.add()。你必須寫 MathUtils.add()。這跟類別的 static 方法一樣。而且，子類別也沒法 Override 它。它就是個「在那裡不動」的工具。
-->
---

# Static 方法 — 範例

```java
public class Demo {
    public static void main(String[] args) {
        int sum = MathUtils.add(3, 5);
        int product = MathUtils.multiply(4, 6);
        System.out.println("加法：" + sum);
        System.out.println("乘法：" + product);
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記住：</b>Static 方法只能用「<code>介面名稱.方法名稱()</code>」呼叫，無法用物件呼叫
</div>

<!--
【帶讀程式碼】
直接用介面名字呼叫，感覺就像是在用一個全域函數。

💼 業界實務：
以後別再隨便建一個什麼 StringUtils 類別了。如果你有一組跟某個介面相關的工具方法，直接寫在那個介面的 static 區塊裡，這叫「高度內聚」，聽起來就比一般的寫法高級很多。
-->
---

# 功能介面 (Functional Interface)

| 特性 | 說明 |
| --- | --- |
| 定義 | 只有**一個**抽象方法的介面（又稱 SAM - Single Abstract Method） |
| 註解 | `@FunctionalInterface`（選用，但建議加上以利檢查） |

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
    // 可以有 default 或 static 方法，但只能有一個 abstract 方法
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>主要用途：</b> 作為 Lambda 運算式的目標類型，簡化匿名內部類別的撰寫。
</div>

<!--
【核心說明】
各位，請起立致敬！這是現代 Java 的基石：Functional Interface。

【帶讀說明】
只要介面裡只有「一個」抽象方法，它就是 Functional Interface。為什麼這麼重要？因為只有這樣，Java 才敢讓你用 Lambda（()->{}）去實作它。如果有兩個方法，Java 怎麼知道你的 Lambda 是在實作哪一個？

💼 業界實務：
如果你寫了一個 Functional Interface 卻忘了加 @FunctionalInterface 註解，那就像是出門沒帶身分證一樣。雖然合法，但專業感直接扣分。
-->
---

# 內建功能介面 (一) — 基本型別

`java.util.function` 套件提供常用的功能介面，可直接搭配 Lambda 使用：

| 介面 | 抽象方法 | 說明 |
| --- | --- | --- |
| `Predicate<T>` | `boolean test(T t)` | 傳入一個值，回傳 true/false 判斷 |
| `Function<T,R>` | `R apply(T t)` | 傳入 T，轉換為 R 回傳 |
| `Consumer<T>` | `void accept(T t)` | 傳入一個值，執行動作不回傳 |
| `Supplier<T>` | `T get()` | 不傳入參數，產生一個 T |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 這四個介面皆標有 <code>@FunctionalInterface</code>，可直接用 Lambda 或方法參考（Method Reference）傳入
</div>

<!--
【帶讀表格】
這四位是 Java 8 的「四大天王」：
1. Predicate：判官。問它對不對，它回 true/false。
2. Function：翻譯機。給它 A，它吐出 B。
3. Consumer：消費者。給它東西它就吃掉（執行），不回報。
4. Supplier：供應商。不拿你東西，直接吐東西給你。

💡 記不住？想像一下：Predicate 是檢察官，Function 是加工廠，Consumer 是垃圾桶，Supplier 是提款機。
-->
---

# 內建功能介面 (一) — 範例

```java
Predicate<String> isLong = s -> s.length() > 5;
System.out.println(isLong.test("炭治郎"));         // false
System.out.println(isLong.test("Kamado Tanjiro")); // true

Function<String, Integer> toLen = String::length;
System.out.println(toLen.apply("鬼滅之刃")); // 4

Consumer<String> print = System.out::println;
print.accept("禰豆子"); // 禰豆子

Supplier<String> hero = () -> "炭治郎";
System.out.println(hero.get()); // 炭治郎
```

<!--
【帶讀程式碼】
你看這程式碼，多優美！不用再寫那種囉哩八嗦的匿名類別（new Interface(){...}）了。

⚠️ 亮點：
String::length 和 System.out::println。這叫「方法參考（Method Reference）」。這是在跟 Java 說：「你要的功能就在那裡，你自己去呼叫。」這讓你的程式碼簡潔到連你阿嬤都看得懂。
-->
---

# 內建功能介面 (二) — Bi 系列與 Operator

| 介面 | 抽象方法 | 說明 |
| --- | --- | --- |
| `BiFunction<T,U,R>` | `R apply(T t, U u)` | 兩個不同型別輸入，一個輸出 |
| `UnaryOperator<T>` | `T apply(T t)` | 繼承 Function，輸入輸出型別相同 |
| `BinaryOperator<T>` | `T apply(T t1, T t2)` | 繼承 BiFunction，兩輸入同型別 |

```java
BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
System.out.println(repeat.apply("鬼", 3)); // 鬼鬼鬼

UnaryOperator<String> upper = String::toUpperCase;
System.out.println(upper.apply("tanjiro")); // TANJIRO

BinaryOperator<Integer> max = (a, b) -> a > b ? a : b;
System.out.println(max.apply(10, 20)); // 20
```

<!--
【帶讀表格】
這些是進階版。Bi 就是「雙重」的意思。
UnaryOperator 就是自產自銷，進去是 String，出來也是 String。

💼 業界實務：
在 Stream API 裡，你會瘋狂用到這些東西。如果你不熟，那你用 Stream 就會用得非常痛苦，感覺像是在用筷子喝湯。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Java 9 新增介面內容
# Private Methods

<!--
【段落轉換】
Java 9 也來湊熱鬧了。它給介面加了「私房話」功能。
-->
---
layout: default
---

# Java 9 新增介面內容 — 概覽

| 功能 | Java 版本 | 說明 |
| --- | --- | --- |
| Constant variable | Java 7+ | 常數成員變數 |
| Abstract methods | Java 7+ | 抽象方法 |
| Default methods | Java 8+ | 預設方法 |
| Static methods | Java 8+ | 靜態方法 |
| **Private methods** | **Java 9 新增** | 私有方法 |
| **Private Static methods** | **Java 9 新增** | 私有靜態方法 |

<!--
【帶讀表格】
Java 9 把最後的一塊拼圖補上了：private 方法。
這代表介面現在可以有自己的秘密了。它不再需要為了重用一段程式碼，而被迫把那段程式碼暴露給全世界看。
-->
---

# Private 方法的規則

| 規則 | 說明 |
| --- | --- |
| 只能在介面內使用 | 無法從實作類別或外部呼叫 |
| 不能抽象化 | 必須有方法本體 |
| `private static` 方法 | 可在 static 和 non-static 方法中使用 |
| `private` non-static 方法 | 不能在 `private static` 方法中使用 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>設計目的：</b>讓介面內部程式碼可重複使用，避免在多個 Default 方法中寫重複邏輯
</div>

<!--
【帶讀表格】
規則很簡單：私有的東西，就只有介面自己能看到。
這解決了一個很尷尬的問題：如果你有兩個 default 方法，裡面有 10 行一模一樣的 Code，在 Java 9 之前，你只能寫兩次，或是建一個莫名其妙的工具類別。現在，你可以直接抽成一個 private 方法，乾乾淨淨。
-->
---

# Private 方法 — 範例

```java
interface LearnJava {
    default void method2() { method4(); }
    static void method3() { method5(); }
    private void method4() {
        System.out.println("這是private方法");
    }
    private static void method5() {
        System.out.println("這是private static方法");
    }
}
```

<!--
【帶讀程式碼】
你看，method2() 呼叫了 method4()。外部的人永遠不知道 method4() 的存在，這就是「封裝」。

⚠️ 注意：
static 不能呼叫 non-static。這在 Java 裡是天條，介面也不例外。所以 method3()（static）只能呼叫 method5()（static）。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 介面的繼承
# Interface Inheritance

<!--
【段落轉換】
介面也能繼承！而且，介面的繼承比類別還要「狂」。
-->
---
layout: default
---

# 繼承的三種關係

| 對象 | 關係 | 語法 | 限制 |
| --- | --- | --- | --- |
| Class → Class | 繼承 | `extends` | 只能一個父類別 |
| Class → Interface | 實作 | `implements` | 可多個，逗號分隔 |
| Interface → Interface | 繼承 | `extends` | 可多個父介面 |

- 當子介面繼承父介面，實作子介面的類別必須**同時實作所有**子介面與父介面的抽象方法

<!--
【帶讀表格】
請記好這個「混亂的關係」：
1. 類別繼承類別：單身狗（只能一個）。
2. 類別實作介面：海王（可以一堆）。
3. 介面繼承介面：也是海王（可以一堆）。

【重要規則】
如果你繼承了一個「很有錢（有很多抽象方法）」的介面，那你實作的時候，就得把這些債通通還清，一個都不能少。
-->
---

# 基本介面的繼承

```java
interface Animal { void showMe(); }
interface Bird extends Animal { void flying(); }
```

- `Bird` 繼承 `Animal`，實作 `Bird` 時需同時實作兩個方法

```java
class Eagle implements Bird {
    public void showMe() { System.out.println("我是老鷹"); }
    public void flying() { System.out.println("老鷹展翅飛翔"); }
}
```

<!--
【帶讀程式碼】
Bird extends Animal。這時候 Bird 身上就背負了 Animal 的遺產。老鷹（Eagle）說它要實作 Bird，那它就得同時會 showMe()（繼承來的）和 flying()（自帶的）。

【類比說明】
這就像是「全才」。你不但要會基礎的（Animal），還得會進階的（Bird）。
-->
---

# 介面多重繼承 — 概念

Java 類別不支援多重繼承，但**介面支援**：

```java
interface B { void b(); }
interface C { void c(); }
class A implements B, C {
    public void b() { System.out.println("b方法"); }
    public void c() { System.out.println("c方法"); }
}
```

- 一個類別可實作**多個介面**（逗號分隔）
- 一個介面可繼承**多個介面**

<!--
【核心說明】
這就是 Java 用來彌補「不能多重繼承」的方案。你不能有兩個爸爸，但你可以有一堆師父。

【帶讀程式碼】
class A implements B, C。這行程式碼展現了強大的擴充性。A 同時擁有了 B 和 C 的能力。

💼 業界實務：
一個 Service 可能同時是「唯讀的（ReadOnly）」也是「可查核的（Auditable）」。這種跨維度的能力，就是用多介面實作來完成的。
-->
---

# 介面繼承多個介面 — 範例

```java
interface Bird { void birdFly(); }
interface Airplane { void airplaneFly(); }
interface Fly extends Bird, Airplane {
    void pediaFly();
}
class InfoFly implements Fly {
    public void birdFly() { System.out.println("鳥翅飛翔"); }
    public void airplaneFly() { System.out.println("引擎飛翔"); }
    public void pediaFly() { System.out.println("百科飛翔"); }
}
```

<!--
【帶讀程式碼】
這裡更狂了，連介面自己都在搞多重繼承。Fly 介面同時繼承了 Bird 和 Airplane。

💡 你看 InfoFly，它要寫三個方法。這告訴我們一件事：權力愈大（繼承愈多），責任愈大（要寫的方法愈多）。
-->
---

# 類別實作多個介面

若多個介面有相同方法名稱，**實作一次即可**：

```java
interface Bird { void flying(); }
interface Airplane { void flying(); }
class Fly implements Bird, Airplane {
    @Override
    public void flying() {
        System.out.println("飛翔中");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 實作多個介面時，須重新定義<b>所有父介面的抽象方法</b>
</div>

<!--
【帶讀程式碼】
如果兩位師父教你同樣名字的招式「flying()」，你只需要學一遍就好。因為它們都是「抽象」的，只要你最後把招式使出來，師父們就不會計較你是跟誰學的。
-->
---

# 繼承與實作規則摘要

| 對象 | extends | implements |
| --- | --- | --- |
| 類別 (Class) | 只能繼承**一個**父類別 | 可實作**多個**介面（逗號分隔） |
| 介面 (Interface) | 可繼承**多個**父介面（逗號分隔） | 介面無法實作另一個介面 |

- 語法順序：`class A extends B implements C, D`
- 介面**無法 `new`** 直接建立物件

<!--
【帶讀表格】
這張表要背熟，這是在 Java 森林裡行走的「交通規則」。
特別注意那個語法順序：先拼爹（extends），再拼才華（implements）。如果你把順序寫反了，編譯器會當場讓你掛掉。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階主題
# Advanced Topics

<!--
【段落轉換】
好了，現在我們要進入「深水區」。準備好迎接那些讓開發者抓狂的衝突問題了嗎？
-->
---
layout: default
---

# 實作時成員變數名稱衝突

不同介面有相同名稱的成員變數，使用**介面名稱.成員變數**存取：

```java
interface B { int x = 5; }
interface C { int x = 8; }
class A implements B, C {
    public void run() {
        System.out.println(B.x); // 5
        System.out.println(C.x); // 8
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 因為介面成員變數是 <b>public static final</b>，可直接用介面名稱存取，不會產生衝突
</div>

<!--
【帶讀程式碼】
如果兩個介面都有一個變數叫 x，而你都實作了。你直接用 x，Java 會生氣地問你：「你到底在說哪一個？」
解法很簡單：報上名號。B.x 或 C.x。這就是 static 的好處，身分明確。
-->
---

# 類別重新定義 Default 方法

| 情況 | 解決方法 |
| --- | --- |
| 實作一個介面，想改變 Default 行為 | 直接 Override |
| 實作多個有**相同** Default 方法名稱的介面 | **必須 Override**，否則編譯錯誤 |
| 需要呼叫特定介面的 Default 方法 | `介面名稱.super.方法名稱()` |

<!--
【帶讀表格】
如果兩位師父不只是教招式，還各自給了你一套「內建功法（default 方法）」，而這兩套功法名字一樣...這下麻煩大了。
Java 會逼你二選一，或是乾脆自創一套。
-->
---

# 類別重新定義 Default 方法 — 範例

```java
interface Dog { default void running() { System.out.println("狗在跑"); } }
interface Cat { default void running() { System.out.println("貓在跑"); } }
class Pet implements Dog, Cat {
    @Override
    public void running() {
        Dog.super.running();
        Cat.super.running();
    }
}
```

<!--
【帶讀程式碼】
你看，Dog 說要這樣跑，Cat 說要那樣跑。Pet 同時學了兩家功夫，編譯器會卡住。
這時候你要寫個 Override 來說清楚。你可以兩個都呼叫（Dog.super.running()），或是只選一個。這就是「調停者模式」。
-->
---

# 類別同時繼承類別與實作介面

語法：**`extends` 在前，`implements` 在後**

```java
// class 子類別 extends 父類別 implements 介面名稱
class Pet extends Horse implements Dog {
    @Override
    public void running() {
        System.out.println("寵物在跑");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 繼承類別（extends）必須排在實作介面（implements）之前，順序不能對調
</div>

<!--
【帶讀程式碼】
再次強調順序！老爸只有一個，必須擺第一位。證照可以有很多，往後擺。
-->
---

# 繼承父類別與實作介面方法名稱衝突

當父類別與實作的介面有相同方法名稱，**父類別方法的優先順序較高**：

```java
interface Dog { default void running() { System.out.println("介面跑"); } }
class Horse { public void running() { System.out.println("馬在跑"); } }
class Pet extends Horse implements Dog { }
// Pet.running() → 執行 Horse 的 running()，父類別方法優先
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 優先順序：<b>子類別自訂 &gt; 繼承的父類別方法 &gt; 介面 Default 方法</b>
</div>

<!--
【核心說明】
這叫「血濃於水」。
如果親爹（父類別）跟師父（介面）教了同名的招式，Java 預設聽親爹的。這讓程式碼的行為變得比較好預測，不會因為加個介面就讓原本的類別行為大變。
-->
---

# 多層次繼承中 Default 方法名稱相同

父子介面都有同名 Default 方法時，執行**子介面**的版本：

```java
interface Animal {
    default void running() { System.out.println("動物跑"); }
}
interface Dog extends Animal {
    default void running() { System.out.println("狗在跑"); }
}
class Pet implements Dog { }
// Pet.running() → 執行 Dog.running()（子介面覆蓋父介面）
```

<!--
【核心說明】
這叫「江山代有才人出」。
越具體的（子介面）通常越有用，所以它會覆蓋掉那個太籠統的（父介面）。
-->
---

# 鑽石 (Diamond) 問題

- 介面 B 與介面 C 同時繼承介面 A，三者都有同名的 Default 方法
- 類別 D 同時實作 B 與 C → 編譯器無法決定執行哪個版本，**必須強制 Override**

<div class="flex flex-col items-center my-3 text-xs font-mono gap-1">
  <div class="border border-blue-400 bg-blue-50 rounded px-4 py-1">
    interface A — <span class="text-blue-600">default</span> void running()
  </div>
  <div class="text-gray-400 text-base" style="display:flex; width:280px; justify-content:space-between;">
    <span>↙</span><span>↘</span>
  </div>
  <div class="flex gap-4">
    <div class="border border-green-500 bg-green-50 rounded px-3 py-1 text-center">
      interface B<br><span class="text-gray-500">extends A, override running()</span>
    </div>
    <div class="border border-green-500 bg-green-50 rounded px-3 py-1 text-center">
      interface C<br><span class="text-gray-500">extends A, override running()</span>
    </div>
  </div>
  <div class="text-gray-400 text-base" style="display:flex; width:280px; justify-content:space-between;">
    <span>↘</span><span>↙</span>
  </div>
  <div class="border border-red-400 bg-red-50 rounded px-4 py-1 text-red-700">
    class D implements B, C — ⚠️ 必須 Override
  </div>
</div>

<div class="p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 因繼承圖形的箭頭流向類似鑽石形狀，稱為<b>鑽石問題（Diamond Problem）</b>
</div>

<!--
【核心說明】
這就是大名鼎鼎的「鑽石問題」。聽起來很美，但對工程師來說是噩夢。

【類比說明】
B 和 C 是兩位師兄，它們都改寫了師父 A 的招式。現在你同時跟兩位師兄學藝...你到底要聽誰的？
這時候你就得自己站出來，寫一個 Override 來解決這個家族紛爭。
-->
---

# 鑽石問題 — 程式範例

```java
interface A { default void running() { System.out.println("跑A"); } }
interface B extends A { default void running() { System.out.println("跑B"); } }
interface C extends A { default void running() { System.out.println("跑C"); } }
class D implements B, C {
    @Override
    public void running() {
        B.super.running(); // 跑B
        C.super.running(); // 跑C
    }
}
```

<!--
【帶讀程式碼】
你看 D 的做法。它很貪心，兩個都想要。這就是解決鑽石問題的標準流程：你自己手動把冲突的地方給補上。
-->
---

# 密封介面 (Sealed Interfaces)

| 特性 | 說明 |
| --- | --- |
| 關鍵字 | `sealed` |
| 控制權 | 使用 `permits` 指定哪些類別或介面可以實作它 |

```java
public sealed interface Shape permits Circle, Rectangle {
    double area();
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>JDK 17 正式特性：</b> 讓開發者能精確限制介面的擴充權限，增強系統安全性。
</div>

<!--
【核心說明】
這是 Java 17 的「保險箱」功能。

【帶讀程式碼】
sealed interface Shape permits Circle, Rectangle。這行就是在說：「這張執照，我只發給圓形跟矩形。其他阿貓阿狗別想來拿。」

💼 業界實務：
在設計複雜的商業邏輯時，這能防止別人亂入你的架構。
-->
---

# 密封介面的實作規則

實作 `sealed` 介面的子類別必須明確宣告以下三種狀態之一：

| 狀態 | 說明 |
| --- | --- |
| `final` | 禁止再被繼承 |
| `sealed` | 繼續密封，並指定自己的子類別 |
| `non-sealed` | 解除密封，回歸傳統的開放繼承 |

```java
public final class Circle implements Shape { 
    public double area() { return 0; } 
}
public non-sealed class Rectangle implements Shape { 
    public double area() { return 0; } 
}
```

<!--
【帶讀表格】
和類別一樣，子類別得選邊站。你要嘛斷子絕孫（final），要嘛繼續玩密封（sealed），要嘛乾脆大解放（non-sealed）。
-->
---

# Records 實作介面 (Java 16)

Records 是隱式 `final`，天然符合 Sealed Interface `permits` 的要求，且自動生成 constructor 與 accessor：

```java
sealed interface Shape permits Circle, Rectangle {
    double area();
}
record Circle(double radius) implements Shape {
    public double area() { return Math.PI * radius * radius; }
}
record Rectangle(double w, double h) implements Shape {
    public double area() { return w * h; }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 Record 比 <code>final class</code> 更簡潔：不需手動寫 constructor、getter、equals()、hashCode()、toString()
</div>

<!--
【核心說明】
如果你已經用了 Java 16+，那這簡直是「神組合」。

【帶讀程式碼】
用 Record 來實作介面。一行搞定所有屬性跟方法。這程式碼寫起來真的有一種「我是在寫程式還是在寫詩」的錯覺。
-->
---

# 密封介面與 Switch 窮舉性

當介面是 `sealed` 時，`switch` 可以檢查是否涵蓋所有可能的情況：

```java
public double getArea(Shape shape) {
    return switch (shape) {
        case Circle c    -> c.area();
        case Rectangle r -> r.area();
        // 不需要 default，因為 Shape 是 sealed 且已窮舉所有子類別
    };
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>優勢：</b> 漏掉實作類別時，編譯器會報錯，比傳統 <code>instanceof</code> 更安全。
</div>

<!--
【帶讀程式碼】
你看這個 switch。它沒有寫 default。為什麼？因為 Shape 是密封的，它保證只有 Circle 跟 Rectangle。如果你以後加了第三個形狀，編譯器會立刻報警說你沒處理。這就是「安全性」。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 課堂練習
# Practice

<!--
【段落轉換】
好了，別光聽我講笑話，動手寫個「跑路」程式吧。
-->
---
layout: default
---

# 練習：設計介面階層
### 任務說明

設計一套介面與類別，模擬「跑」的行為：
1. 建立介面 `Runnable`，定義行為方法 `run()`
2. 類別 `Human`、`Car` 都實作 `Runnable`，各自定義不同的跑法
3. `Person` 繼承 `Human`，並重新定義 `run()` 方法
4. 執行後，將三者（Human、Person、Car）的跑法各自列印出來

<!--
【出題前的鋪陳】
練習時間！我們來模擬一下怎麼跑。
人會跑，車也會跑。我們用介面來統一這兩者的行為。

【問題引導】
Runnable 介面要怎麼寫？那三個類別的關係搞清楚了嗎？
記得，Person 繼承 Human 的同時，其實也就繼承了它的 Runnable 身分。

【等待與觀察】
給大家 5 分鐘。寫不出來的人，待會可能得跑著回家。
-->
---
layout: default
---

# 練習：設計介面階層
### 解題提示

1. 定義介面 `Runnable`，加入抽象方法 `run()`
2. `Human` 與 `Car` 分別 `implements Runnable`，各自 Override `run()`
3. `Person extends Human`，Override `run()` 加上自己的行為
4. `main()` 中建立三個物件，分別呼叫各自的 `run()`

```java
interface Runnable {
    void run();
}
class Human implements Runnable {
    public void run() { System.out.println("人在路上跑"); }
}
```

<!--
【帶讀解法】
解法就在這裡。簡單明瞭。

⚠️ 小彩蛋：
如果你在實作的時候發現有個東西叫 java.lang.Runnable...沒錯，那是 Java 內建用來跑 Thread 的。這告訴我們，名字取太好，也是會撞衫的。
-->
---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天我們從最基本的「行為契約」講到 Java 17 的「密封保險箱」。
介面不再是那個只會開空頭支票的主管，它現在是有實作、有靜態工具、甚至能做 Lambda 運算的強大武器。

【核心帶走重點】
1. 介面是「證照」，類別可以拿很多張。
2. Java 8 之後介面可以有 default 方法。
3. Functional Interface 是 Lambda 的靈魂。
4. 針對介面編程，是成為高手的必經之路。

有問題嗎？沒問題的話，大家可以「implements 下課」了。
-->
---
layout: end
---

# 課程結束
### 介面定義行為契約，多重繼承讓設計更靈活

<!--
[依脈絡推斷]
下課！記住：介面是你的合約。如果你不遵守合約，Java 就會讓你違約金賠不完（Bug 修不完）。我們下章見！
-->

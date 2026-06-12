---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 日期與時間的類別
routeAlias: ch11
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
  <h1 style="color: #1a5c5c; font-size: 3.2rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">日期與時間的類別</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「掌握 Java 8 java.time 套件的核心用法」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好！今天我們要聊一個每個程式都會用到的主題：時間。不管是紀錄使用者什麼時候登入、算算特價還剩幾天，還是算出朋友的生日還有幾天，我們都需要精確地處理日期跟時間。

為什麼要學這個？因為寫程式不可能不碰時間，而 Java 從第 8 版開始提供了一套全新設計的 `java.time` 套件，把日期和時間切成幾個清楚的角色：只管日期的 `LocalDate`、只管時間的 `LocalTime`，以及兩者合一的 `LocalDateTime`。

學完這章，我們會學會怎麼建立、調整這些日期時間物件，怎麼用 `DateTimeFormatter` 把它們格式化成想要的樣子，以及怎麼從字串解析回日期時間物件。
-->

---
layout: default
---

# Outline

- **11-1 LocalDate：只有日期**
  - 建立、加減、取得星期幾

- **11-2 LocalTime：只有時間**
  - 建立、加減、取得時分

- **11-3 LocalDateTime：日期 + 時間**
  - 建立、拆解日期與時間

- **11-4 DateTimeFormatter：格式化與解析**
  - 常用格式符號、format / parse

- **類別選用時機對照表**
- **練習題**

<!--
我們會依序學習三個最常用的時間類別：先看只管「日期」的 `LocalDate`，接著是只管「時間」的 `LocalTime`，再學會把兩者合在一起的 `LocalDateTime`。最後，我們會學會用 `DateTimeFormatter` 把這些物件變成好看的字串，或是反過來從字串解析出日期時間。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDate
## 只有日期，沒有時間

<!--
想像一下，我們要記錄一個人的生日，或是公司的發薪日。這些資料只關心「哪一天」，完全不需要知道「幾點幾分」。

`LocalDate` 就是為這種情境設計的——它代表「日曆上的一頁」，只有年、月、日，沒有時間資訊。接下來我們來看看怎麼建立和操作它。
-->

---
layout: default
---

# LocalDate 常用 API（一）

| 方法 | 說明 |
| --- | --- |
| `LocalDate.now()` | 取得今天日期 |
| `LocalDate.of(年, 月, 日)` | 指定日期建立 |
| `LocalDate.parse("yyyy-MM-dd")` | 從字串解析 |
| `isBefore(other)` | 是否在指定日期之前 |
| `isAfter(other)` | 是否在指定日期之後 |

```java
import java.time.LocalDate;

LocalDate today = LocalDate.now();
LocalDate birthday = LocalDate.of(2000, 5, 20);
LocalDate parsed = LocalDate.parse("2024-01-15");

System.out.println(today);
System.out.println(birthday.isBefore(today)); // true
```

<!--
`LocalDate` 只有日期，非常適合用來存生日、發薪日這類資料。我們可以把它想成「日曆上撕下來的那一頁」——它不知道現在幾點，只知道今天是哪一天。

帶大家看關鍵行：`LocalDate.now()` 抓的是今天的日期，`LocalDate.of(2000, 5, 20)` 則是建立一個指定的日期。這裡的 `5` 就代表 5 月，不需要再像舊版 API 那樣減 1，是不是清爽多了？

`isBefore()` / `isAfter()` 可以用來比較兩個日期的先後順序，例如判斷生日是不是已經過了。
-->

---

# LocalDate 常用 API（二）

| 方法 | 說明 |
| --- | --- |
| `plusDays(n)` | 加 n 天 |
| `minusMonths(n)` | 減 n 個月 |
| `plusYears(n)` | 加 n 年 |
| `getDayOfWeek()` | 取得星期幾（DayOfWeek 列舉）|
| `getDayOfMonth()` | 取得當月幾號 |

```java
LocalDate today = LocalDate.now();
LocalDate nextWeek = today.plusDays(7);
LocalDate lastMonth = today.minusMonths(1);

DayOfWeek dow = LocalDate.of(2024, 5, 20).getDayOfWeek();
System.out.println(dow);        // MONDAY
System.out.println(dow.getValue()); // 1（週一=1，週日=7）
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>不可變物件：</b><code>today.plusDays(7)</code> 不會改變 <code>today</code> 本身，而是回傳一個新的物件。
</div>

<!--
`LocalDate` 提供了很方便的加減法，例如 `plusDays(7)` 就是一週後、`minusMonths(1)` 就是上個月。`getDayOfWeek()` 則可以告訴我們某一天是星期幾，回傳的是 `DayOfWeek` 這個列舉。

⚠️ 易錯點：`java.time` 裡所有的物件都是「不可變（Immutable）」的。也就是說，`today.plusDays(7)` 執行完之後，`today` 本身完全不會改變，而是回傳一個全新的物件。如果忘了用變數接住回傳值，計算結果就會憑空消失。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalTime
## 只有時間，沒有日期

<!--
回顧一下，`LocalDate` 處理的是「哪一天」。那如果我們想記錄的是「鬧鐘設定的時間」或「店家的營業時間」呢？這些資料只關心「幾點幾分」，跟日期完全無關。

這就是 `LocalTime` 要解決的問題——它代表「時鐘上的時間」，不管是哪一天，每天的早上 7 點都是同一個 `LocalTime`。
-->

---
layout: default
---

# LocalTime 常用 API

| 方法 | 說明 |
| --- | --- |
| `LocalTime.now()` | 取得目前時間 |
| `LocalTime.of(時, 分)` | 指定時間建立 |
| `LocalTime.parse("HH:mm")` | 從字串解析 |
| `plusHours(n)` | 加 n 小時 |
| `minusMinutes(n)` | 減 n 分鐘 |
| `getHour()` / `getMinute()` | 取得時 / 分 |

```java
import java.time.LocalTime;

LocalTime now = LocalTime.now();
LocalTime meeting = LocalTime.of(14, 30);
LocalTime end = meeting.plusHours(2);

System.out.println(meeting); // 14:30
System.out.println(end);     // 16:30
System.out.println(end.getHour()); // 16
```

<!--
`LocalTime` 就是只有時間、沒有日期，我們可以把它想成「鬧鐘」——設定早上 7 點起床，不管那天是星期幾，鬧鐘都會在 7 點響。

帶大家看關鍵行：`LocalTime.of(14, 30)` 建立一個下午 2:30 的時間物件，`plusHours(2)` 則會回傳一個加了 2 小時的新物件（16:30）。`getHour()` 可以單獨取出「小時」這個數字，方便我們做進一步的判斷或計算。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDateTime
## 日期 + 時間，但無時區

<!--
回顧一下，我們已經學了「只有日期」的 `LocalDate` 和「只有時間」的 `LocalTime`。但現實生活中，大部分的紀錄都需要「日期 + 時間」一起出現，例如「2024 年 5 月 13 日 10 點 30 分」。

`LocalDateTime` 就是把這兩者合而為一的類別——它知道完整的日期時間，但還缺少一個資訊：時區。我們會在這章先學會它的基本用法，時區的部分留給更進階的內容。
-->

---
layout: default
---

# LocalDateTime 常用 API

| 方法 | 說明 |
| --- | --- |
| `LocalDateTime.now()` | 取得現在日期時間 |
| `LocalDateTime.of(年,月,日,時,分)` | 指定建立 |
| `LocalDateTime.of(date, time)` | 合併 LocalDate 與 LocalTime |
| `toLocalDate()` | 拆出日期部分 |
| `toLocalTime()` | 拆出時間部分 |

```java
import java.time.LocalDateTime;

LocalDateTime now = LocalDateTime.now();
LocalDateTime dt = LocalDateTime.of(2024, 5, 13, 10, 30, 0);

LocalDate d = LocalDate.of(2024, 5, 13);
LocalTime t = LocalTime.of(10, 30);
LocalDateTime combined = LocalDateTime.of(d, t);
System.out.println(combined); // 2024-05-13T10:30
```

<!--
`LocalDateTime` 就是日期加時間，我們可以把它想成一張「約會通知」：「我們週五下午兩點在咖啡廳見」。只要雙方都在同一個地方（同一個時區），這張通知就完全沒問題。

帶大家看關鍵行：`LocalDateTime.of(2024, 5, 13, 10, 30, 0)` 一次指定年月日時分秒；也可以分開建立 `LocalDate` 和 `LocalTime`，再用 `LocalDateTime.of(d, t)` 把兩者合併起來。`toLocalDate()` 和 `toLocalTime()` 則可以反過來，把合併後的物件再拆開。

💼 業界實務：`LocalDateTime` 很適合用來記錄「本地活動」或存放資料庫裡的時間欄位，只要系統不需要跨時區，這個類別就足夠應付大部分情境了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# DateTimeFormatter
## 格式化與解析

<!--
回顧一下，我們現在已經會建立 `LocalDate`、`LocalTime`、`LocalDateTime` 這三種物件了。但直接 `System.out.println()` 印出來的格式（例如 `2024-05-13T10:30`）並不一定符合我們想要的呈現方式。

這時候就需要 `DateTimeFormatter`——它可以把日期時間物件轉成我們指定的字串格式，也可以反過來把字串解析成日期時間物件。它是 `SimpleDateFormat` 的接班人，設計上更安全也更直覺。
-->

---
layout: default
---

# DateTimeFormatter 常用格式符號

| 符號 | 說明 | 範例 |
| --- | --- | --- |
| `yyyy` | 四位數年份 | 2024 |
| `MM` | 兩位數月份 | 01–12 |
| `dd` | 兩位數日期 | 01–31 |
| `HH` | 24 小時制 | 00–23 |
| `mm` | 分鐘 | 00–59 |
| `ss` | 秒數 | 00–59 |
| `E` | 星期縮寫 | Mon |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>執行緒安全：</b>DateTimeFormatter 是不可變物件，可宣告為靜態常數，安全地在多執行緒環境共用。
</div>

<!--
這些格式符號跟我們在其他語言或工具看到的長相類似：`yyyy` 是四位數年份、`MM` 是月份、`dd` 是日期、`HH` 是 24 小時制的小時、`mm` 是分鐘、`ss` 是秒數，`E` 則是星期縮寫。

⚠️ 易錯點：大小寫有差別！`MM`（月份）和 `mm`（分鐘）長得很像，寫錯的話印出來的時間會整個錯亂。

💼 業界實務：因為 `DateTimeFormatter` 是不可變且執行緒安全的，業界習慣把它定義成 `static final` 常數，整個程式共用同一個實例，既省記憶體又安全。
-->

---

# DateTimeFormatter：格式化與解析

| 方法 | 說明 |
| --- | --- |
| `DateTimeFormatter.ofPattern(pattern)` | 依自訂樣式建立 formatter |
| `ldt.format(formatter)` | 日期時間 → 字串 |
| `LocalDate.parse(str, formatter)` | 字串 → LocalDate |
| `LocalDateTime.parse(str, formatter)` | 字串 → LocalDateTime |

```java
import java.time.*;
import java.time.format.DateTimeFormatter;

DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

LocalDateTime now = LocalDateTime.now();
String str = now.format(fmt);
System.out.println(str); // e.g. 2024/05/13 10:30:00

LocalDateTime parsed = LocalDateTime.parse("2024/05/13 10:30:00", fmt);
```

<!--
帶大家看關鍵行：`now.format(fmt)` 是「日期時間物件 → 字串」，把 `LocalDateTime` 依照我們指定的格式變成可讀的字串；`LocalDateTime.parse(str, fmt)` 則是反過來，「字串 → 日期時間物件」。

⚠️ 易錯點：`parse` 的時候，字串的格式必須跟 `formatter` 的 pattern「一模一樣」，包括斜線、空格、冒號等符號，少一個都會直接拋出例外。
-->

---

# 四種日期時間類別選用時機

| 類別 | 有日期 | 有時間 | 有時區 | 適用情境 |
| --- | :---: | :---: | :---: | --- |
| `LocalDate` | ✅ | ❌ | ❌ | 生日、假日、課表 |
| `LocalTime` | ❌ | ✅ | ❌ | 每日開店時間、鬧鐘 |
| `LocalDateTime` | ✅ | ✅ | ❌ | 本地活動、資料庫時間欄 |
| `ZonedDateTime` | ✅ | ✅ | ✅ | 跨時區會議、航班時刻 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>原則：</b>只要不需要考慮時區，優先用 <code>LocalDate</code> / <code>LocalDateTime</code>；跨國系統或需處理時差，才需要 <code>ZonedDateTime</code>（進階自學內容）。
</div>

<!--
這張表幫我們整理今天學到的三個類別該怎麼選：只關心日期就用 `LocalDate`（生日、課表）；只關心時間就用 `LocalTime`（鬧鐘、營業時間）；日期加時間一起記錄，就用 `LocalDateTime`（本地活動、資料庫時間欄位）。

表格最後一列的 `ZonedDateTime` 是處理跨時區情境的類別，例如跨國會議或航班時刻表，這部分留在進階自學內容裡介紹。今天的重點是先把前三種類別用熟，挑選正確的類別，能讓我們的程式碼更精準也更好維護。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 練習題

<!--
學了這麼多，來動手試試吧！這兩題會把今天學到的 `LocalDate`、`LocalDateTime` 和 `DateTimeFormatter` 串起來應用。
-->

---
layout: default
---

# 練習一：計算倒數天數

### 任務說明

請撰寫程式，計算「今天到下次生日還有幾天」。

**需求：**
1. 用 `LocalDate.now()` 取得今天日期
2. 設定今年的生日（例如 5 月 20 日）
3. 若今年生日已過，改算明年生日
4. 用 `ChronoUnit.DAYS.between()` 計算相差天數
5. 輸出格式：`距離下次生日還有 X 天`

<!--
回顧一下，我們剛學了 `LocalDate.of()` 建立日期、`isBefore()` 比較日期先後，以及 `plusYears()` 加一年。這題請把這幾個工具組合起來，幫自己寫一個生日倒數器。

引導思考：如果今年的生日已經過了，程式要怎麼自動改成算「明年」的生日？要是忘了處理這個情況，算出來的天數會變成什麼？
-->

---

# 練習一：解題提示

### 提示說明

1. 建立今年生日：`LocalDate.of(today.getYear(), 5, 20)`
2. 判斷是否已過：`birthday.isBefore(today)` → 若是則 `birthday = birthday.plusYears(1)`
3. 計算天數差：`ChronoUnit.DAYS.between(today, birthday)`
4. 輸出結果

```java
LocalDate today = LocalDate.now();
LocalDate birthday = LocalDate.of(today.getYear(), 5, 20);
if (birthday.isBefore(today)) {
    birthday = birthday.plusYears(1);
}
long days = ChronoUnit.DAYS.between(today, birthday);
System.out.println("距離下次生日還有 " + days + " 天");
```

<!--
這題的關鍵在第 2 步的 `isBefore()` 判斷：先假設生日是「今年」，如果發現這個日期已經在今天之前，就用 `plusYears(1)` 改成明年的生日。

`ChronoUnit.DAYS.between(today, birthday)` 會直接算出兩個日期之間相差的「總天數」，比起 `Period` 更適合用在「算總天數」這種需求上。
-->

---
layout: default
---

# 練習二：時間格式轉換

### 任務說明

請撰寫程式，完成以下轉換任務：

**任務 A — 格式化輸出：**
將現在時間以 `yyyy 年 MM 月 dd 日 HH:mm:ss（E）` 格式印出，例如：`2024 年 05 月 13 日 10:30:00（星期一）`

**任務 B — 解析字串：**
將字串 `"2024/05/13 10:30"` 解析為 `LocalDateTime` 物件，並分別印出年份、月份和星期幾

<!--
回顧一下，我們剛學了 `DateTimeFormatter.ofPattern()` 怎麼自訂格式，以及 `format()` / `parse()` 怎麼在物件和字串之間互相轉換。這兩個任務分別練習這兩個方向。

引導思考：任務 A 和任務 B 用的 pattern 字串看起來不一樣（一個有中文字，一個是斜線），這代表 pattern 字串可以包含哪些內容？
-->

---

# 練習二：解題提示

### 提示說明

**任務 A 提示：**
1. 建立 `DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日 HH:mm:ss（E）")`
2. `LocalDateTime.now().format(formatter)`

**任務 B 提示：**
1. 建立 `DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")`
2. 使用 `LocalDateTime.parse("2024/05/13 10:30", formatter)`
3. 呼叫 `.getYear()`, `.getMonth()`, `.getDayOfWeek()`

```java
DateTimeFormatter fmt =
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
LocalDateTime ldt = LocalDateTime.parse("2024/05/13 10:30", fmt);
System.out.println(ldt.getYear());       // 2024
System.out.println(ldt.getDayOfWeek()); // MONDAY
```

<!--
這題的重點在於：`pattern` 字串裡，除了 `yyyy`、`MM` 這類格式符號之外，其他文字（包括中文字、斜線、空格）都會「原封不動」地出現在結果裡。

⚠️ 易錯點：解析字串時，格式一定要跟字串本身「一模一樣」，少一個斜線、多一個空格，都會讓 `parse()` 拋出例外。
-->

---
layout: default
---

# 綜合練習：會議提醒小工具

### 任務說明

請撰寫程式，模擬一個簡單的「會議提醒」工具：

1. 建立一個會議時間 `LocalDateTime meeting`，例如本月某天的下午 2 點
2. 取得現在時間 `LocalDateTime.now()`
3. 用 `now.isBefore(meeting)` 判斷會議是否還沒開始
4. 將會議時間以 `yyyy/MM/dd（E）HH:mm` 格式印出
5. 輸出格式：`會議時間：2024/05/20（一）14:00，會議尚未開始：true`

<!--
這是今天的綜合練習，把 `LocalDateTime` 的建立與比較，以及 `DateTimeFormatter` 的格式化串在一起，模擬一個小型的提醒工具。

回顧一下，我們在 `LocalDate` 學過 `isBefore()`，在 `DateTimeFormatter` 學過 `ofPattern()` 和 `format()`。這題請把這兩個概念套用到 `LocalDateTime` 上。

引導思考：如果想讓這個提醒工具也顯示「還剩多久」（例如還有 3 小時 20 分），會需要用到我們今天還沒教過的哪個工具？這個問題的答案會在進階自學內容裡揭曉。
-->

---

# 綜合練習：解題提示

### 提示說明

```java
import java.time.*;
import java.time.format.DateTimeFormatter;

LocalDateTime meeting = LocalDateTime.of(2024, 5, 20, 14, 0);
LocalDateTime now = LocalDateTime.now();

DateTimeFormatter fmt =
    DateTimeFormatter.ofPattern("yyyy/MM/dd（E）HH:mm");

System.out.println("會議時間：" + meeting.format(fmt)
    + "，會議尚未開始：" + now.isBefore(meeting));
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>延伸思考：</b>想知道「還剩多久」，需要用到 <code>Duration</code>——這是進階自學內容會介紹的工具。
</div>

<!--
這題的結構不複雜，重點是把今天學到的三個工具兜在一起：用 `LocalDateTime.of()` 建立會議時間、用 `isBefore()` 比較先後、用 `format(fmt)` 印出好讀的格式。

最後的延伸思考已經幫大家鋪好路了：如果想知道「還剩多久」，需要計算兩個時間點的「差距」，這正是進階自學內容裡 `Duration` 要解決的問題。有興趣的同學，歡迎繼續探索進階版的內容！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

### 今天重點回顧

- `LocalDate` 只管日期、`LocalTime` 只管時間、`LocalDateTime` 兩者合一
- 所有 `java.time` 物件都是不可變（Immutable），加減法會回傳新物件
- `DateTimeFormatter.ofPattern()` 負責格式化與解析，可宣告為共用常數
- 選類別的原則：不需要時區就用 `LocalDate` / `LocalTime` / `LocalDateTime`
- 想深入了解 `ZonedDateTime`、`Duration`/`Period`、舊版 `Date` 轉換，歡迎前往進階自學內容

<!--
關於今天學到的日期時間類別，大家還有什麼疑問嗎？

如果之後在工作中遇到需要處理時差、計算時間長度，或是要跟舊系統的 `Date` 物件打交道，記得我們有準備進階自學內容，把這些更進階的工具都整理在裡面，有興趣的同學可以自行延伸學習！
-->

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
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「從 Date 到 java.time：掌握現代 Java 時間處理」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
大家好！今天我們要聊一個讓所有程式員都頭痛的主題：時間。有人說時間是把殺豬刀，但在 Java 裡，時間有時候更像是一把亂飛的迴力鏢。今天我要帶大家從舊版的坑洞裡爬出來，走進 Java 8 以後的「現代時間」新世界。

【為什麼要學這個？】
寫程式不可能不碰時間。不管是紀錄使用者什麼時候登入、算算特價還剩幾秒，還是算出老闆下次生日（方便討好他），你都需要精確地控制日期跟時間。

【今天學完你會能做什麼】
學完這章，你不會再被 0 到 11 月搞瘋，你會學會怎麼優雅地格式化時間，還能輕鬆算出兩個日期之間隔了幾天幾小時。
-->

---
layout: default
---

# Outline

- **11-1 舊版 Date 類別（java.util.Date）**
  - new Date()、getTime()、SimpleDateFormat
  - 為何 Date 被淘汰

- **11-2 新版日期時間類別（java.time）**
  - LocalDate、LocalTime、LocalDateTime
  - ZonedDateTime 與 ZoneId
  - DateTimeFormatter：格式化與解析
  - Duration 與 Period：時間差計算
  - 舊版 Date ↔ 新版 Instant 轉換

- **類別選用時機對照表**
- **練習題**

<!--
【核心說明】
我們會先簡單看看「舊時代的眼淚」——Date 類別，看看它到底有多難用。接著我們會進入重點：Java 8 推出的 java.time 套件，學會 LocalDate、LocalDateTime 這些現代工具。最後，我們會學怎麼在新舊 API 之間架起橋樑。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 舊版 Date 類別
## java.util.Date

<!--
【段落轉換】
第一部分，我們先來看看舊版的 Date。雖然它被淘汰了，但在維護舊系統時還是會遇到它。
-->

---
layout: default
---

# java.util.Date 基本用法

| 方法 | 說明 |
| --- | --- |
| `new Date()` | 建立代表「現在」的 Date 物件 |
| `new Date(long millis)` | 以毫秒時間戳記建立 Date |
| `getTime()` | 回傳從 1970-01-01 00:00:00 UTC 起算的毫秒數 |
| `toString()` | 回傳可讀的日期時間字串 |

```java
import java.util.Date;

Date now = new Date();
System.out.println(now);           // 可讀格式字串
System.out.println(now.getTime()); // 毫秒時間戳記，例如 1715644800000
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>毫秒時間戳記：</b>從 1970 年 1 月 1 日 00:00:00 UTC 起算至今的毫秒數，又稱 Unix epoch time。
</div>

<!--
【核心說明】
這就是 java.util.Date。它誕生於 Java 1.0。大家想像一下 1995 年的網速，就能理解這東西有多古老。

【生活化比喻】
new Date() 就像是按了一下馬錶，記錄下那一秒。它其實存的是一個很大的長整數（毫秒）。

【程式世界怎麼用】
如果你接手的是十年前的老專案，你一定會看到它。雖然它被嫌棄，但它目前還是 Java 世界的「通用語言」，很多舊的資料庫驅動還在用它。
-->

---

# SimpleDateFormat：常用格式符號

| 符號 | 說明 | 範例 |
| --- | --- | --- |
| `yyyy` | 四位數年份 | 2024 |
| `MM` | 兩位數月份 | 01–12 |
| `dd` | 兩位數日期 | 01–31 |
| `HH` | 24 小時制小時 | 00–23 |
| `mm` | 分鐘 | 00–59 |
| `ss` | 秒數 | 00–59 |
| `E` | 星期幾縮寫 | Mon, Tue... |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>大小寫有別：</b><code>MM</code> 是月份，<code>mm</code> 是分鐘；<code>HH</code> 是 24 小時制，<code>hh</code> 是 12 小時制。
</div>

<!--
【核心說明】
Date 本身印出來很醜，所以 we 需要 SimpleDateFormat 來幫它化妝。

⚠️ 學生常見誤解：
注意大小寫！MM 是月份（Month），mm 是分鐘（minute）。如果你寫錯，你會發現現在是 2024 年 59 月，那真的是見鬼了。還有 HH 是 24 小時制，如果你用 hh，下午一點會變成 01，這在紀錄 log 時會讓你找 Bug 找到哭。
-->

---

# SimpleDateFormat：format() 與 parse()

| 方法 | 說明 |
| --- | --- |
| `format(Date date)` | 將 Date 轉成格式化字串 |
| `parse(String text)` | 將字串解析為 Date 物件，可能拋出 ParseException |

```java
import java.text.SimpleDateFormat;
import java.util.Date;

SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

// Date → String
String str = sdf.format(new Date());
System.out.println(str); // e.g. 2024/05/13 10:30:00

// String → Date
Date parsed = sdf.parse("2024/05/13 10:30:00");
```

<!--
【逐步解說】
format 是把物件變字串（化妝），parse 是把字串變物件（卸妝）。

⚠️ 學生常見誤解：
parse 非常容易拋出錯誤（ParseException）。只要字串裡多一個空格、少一個斜線，程式就直接崩潰給你看。這就是為什麼我們說 Date 很難搞。
-->

---

# 為何 Date 被淘汰？

| 缺陷 | 說明 |
| --- | --- |
| 執行緒不安全 | `SimpleDateFormat` 在多執行緒共用時容易造成資料錯誤 |
| 月份從 0 開始 | `Date.getMonth()` 回傳 0–11，極易混淆 |
| 年份需加 1900 | `Date.getYear()` 回傳的是「西元年 − 1900」 |
| API 設計不一致 | Date、Calendar、SimpleDateFormat 分散於不同套件 |
| 可變物件 | Date 可被修改，難以做到不可變設計 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>官方建議：</b>Java 8 起改用 <code>java.time</code> 套件，DateTimeFormatter 是執行緒安全的，且 API 直覺一致。
</div>

<!--
【核心說明】
為什麼我們要捨棄 Date？因為它設計得簡直是個災難。

【生活化比喻】
Date 的月份是從 0 開始的，所以 12 月在 Java 裡是 11。年份更扯，要加 1900。這就像是你去買飲料，店員說「這杯 0 元」，結果結帳是 1900 元一樣荒謬。

💼 業界實務：
最致命的是「執行緒不安全」。如果你在多執行緒環境（比如 Spring Boot 伺服器）裡共用同一個 SimpleDateFormat，你的日期會亂跳，這在金融系統裡可是會出人命的（或者是被開除）。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 新版日期時間類別
## java.time 套件（Java 8+）

<!--
【段落轉換】
第二部分，我們來學現代化的 java.time 套件。它解決了舊版的所有痛點。
-->

---
layout: default
---

# 舊版 Date vs 新版 java.time

| 比較項目 | java.util.Date / Calendar | java.time（Java 8+） |
| --- | --- | --- |
| 執行緒安全 | 否（SimpleDateFormat 不安全）| 是（全部不可變物件）|
| 月份索引 | 0–11（容易出錯）| 1–12（直覺）|
| API 一致性 | 分散、設計混亂 | 統一於 java.time 套件 |
| 可讀性 | 低 | 高（流暢 API）|
| 時區支援 | 繁瑣 | ZonedDateTime / ZoneId |
| 時間差計算 | 手動相減毫秒 | Duration / Period |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>選用原則：</b>新專案請直接使用 <code>java.time</code>；舊系統如需和 <code>Date</code> 互動，透過 <code>Instant</code> 轉換即可。
</div>

<!--
【核心說明】
Java 8 推出的 java.time 套件是參考了非常有名的 Joda-Time 庫。它把所有缺點都改掉了。

【生活化比喻】
如果 Date 是那種要自己調齒輪的老式掛鐘，java.time 就是現在的智慧型手錶，功能強大又不會出錯。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDate
## 只有日期，沒有時間

<!--
【段落轉換】
LocalDate：處理日曆上的日期，不管幾點鐘。
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
【核心說明】
LocalDate 只有日期。非常適合用來存生日、發薪日。你不需要知道你是幾點幾分出生的，只要知道是哪天就好。

【生活化比喻】
這就像是日曆上撕下來的那一頁。它不知道現在幾點，它只知道今天是哪一天。

【逐步解說】
LocalDate.now() 抓今天，LocalDate.of(2000, 5, 20) 建立特定日期。看！這裡的 5 月就是 5，不用再減 1 了，感動吧？
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

<!--
【逐步解說】
它提供了很方便的加減法，比如 plusDays(7) 就是一週後。

💼 業界實務：
注意！java.time 所有的物件都是「不可變（Immutable）」的。也就是說，today.plusDays(7) 不會改變 today 本身，而是回傳一個新的物件。這在程式開發中非常重要，可以避免很多莫名其妙的副作用。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalTime
## 只有時間，沒有日期

<!--
【段落轉換】
LocalTime：處理時鐘上的時間，不管哪一天。
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

now = LocalTime.now();
LocalTime meeting = LocalTime.of(14, 30);
LocalTime end = meeting.plusHours(2);

System.out.println(meeting); // 14:30
System.out.println(end);     // 16:30
System.out.println(end.getHour()); // 16
```

<!--
【核心說明】
LocalTime 就是只有時間，沒有日期。

【生活化比喻】
這就是你的鬧鐘。你設定早上 7 點起床，你不管那天是星期幾。這就是 LocalTime 的概念。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDateTime
## 日期 + 時間，但無時區

<!--
【段落轉換】
LocalDateTime：日期和時間都有，但缺乏時區資訊。
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

now = LocalDateTime.now();
LocalDateTime dt = LocalDateTime.of(2024, 5, 13, 10, 30, 0);

LocalDate d = LocalDate.of(2024, 5, 13);
LocalTime t = LocalTime.of(10, 30);
LocalDateTime combined = LocalDateTime.of(d, t);
System.out.println(combined); // 2024-05-13T10:30
```

<!--
【核心說明】
日期加時間，但還是沒有時區。

【生活化比喻】
這就像是約會通知：「我們週五下午兩點在星巴克見」。如果你們都在台北，這沒問題。但如果你約的是西雅圖的正妹，那你就會被放鳥，因為你們的時間不一樣。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# ZonedDateTime
## 含時區的日期時間

<!--
【段落轉換】
ZonedDateTime：處理跨國業務必備，包含完整的時區資訊。
-->

---
layout: default
---

# ZonedDateTime 與 ZoneId

| 方法 | 說明 |
| --- | --- |
| `ZoneId.of("區域/城市")` | 建立時區物件 |
| `ZoneId.systemDefault()` | 系統預設時區 |
| `ZonedDateTime.now(zoneId)` | 指定時區的現在時間 |
| `ZonedDateTime.of(ldt, zoneId)` | 將 LocalDateTime 加上時區 |
| `getZone()` | 取得時區資訊 |

```java
import java.time.*;

ZoneId taipei = ZoneId.of("Asia/Taipei");
ZoneId paris  = ZoneId.of("Europe/Paris");

ZonedDateTime now = ZonedDateTime.now(taipei);
LocalDateTime ldt = LocalDateTime.of(2024, 5, 13, 10, 0);
ZonedDateTime zdt = ZonedDateTime.of(ldt, taipei);
System.out.println(zdt); // 2024-05-13T10:00+08:00[Asia/Taipei]
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>常用時區 ID：</b><code>Asia/Taipei</code>、<code>Asia/Tokyo</code>、<code>America/New_York</code>、<code>Europe/London</code>
</div>

<!--
【核心說明】
這就是終極大 Boss：含時區的日期時間。

【逐步解說】
時區 ID 通常是「大陸/城市」，比如 Asia/Taipei。不要寫什麼 CST 或 GMT+8，用正式的 ID 最準確。這就像是給你的時間貼上了一張「地區標籤」，讓全世界都知道這是在講哪裡的時間。
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
💡 <b>原則：</b>只要不需要考慮時區，優先用 <code>LocalDate</code> / <code>LocalDateTime</code>；跨國系統或需儲存 UTC 時間，才用 <code>ZonedDateTime</code>。
</div>

<!--
【核心說明】
選對類別很重要，不要殺雞用牛刀。

【逐步解說】
生日用 LocalDate，鬧鐘用 LocalTime，一般日誌紀錄用 LocalDateTime。只有需要處理「時差」時才祭出 ZonedDateTime。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# DateTimeFormatter
## 格式化與解析

<!--
【段落轉換】
DateTimeFormatter：新版的格式化工具，執行緒安全且更強大。
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
| `z` / `VV` | 時區縮寫 / 時區 ID | UTC+8 / Asia/Taipei |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>執行緒安全：</b>DateTimeFormatter 是不可變物件，可宣告為靜態常數，安全地在多執行緒環境共用。
</div>

<!--
【核心說明】
這是 SimpleDateFormat 的接班人，它更安全、更快。

💼 業界實務：
因為它是執行緒安全的，所以在業界我們習慣把它定義成 static final 常數，大家一起用，既省記憶體又安全。
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
【逐步解說】
用法跟以前差不多，但呼叫對象換成了日期物件。比如 now.format(fmt)。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Duration 與 Period
## 計算時間差

<!--
【段落轉換】
計算兩個時間點之間的距離。一個算時分秒，一個算年月日。
-->

---
layout: default
---

# Duration vs Period 比較

| 比較項目 | Duration | Period |
| --- | --- | --- |
| 適用對象 | 時間（秒、奈秒）| 日期（年、月、日）|
| 典型用途 | 計算兩個時刻的秒差 | 計算兩個日期的年月日差 |
| 主要單位 | 秒（seconds）、奈秒 | 年（years）、月（months）、日（days）|
| 建立方式 | `Duration.between(t1, t2)` | `Period.between(d1, d2)` |
| 常用取值 | `getSeconds()` `toMinutes()` | `getYears()` `getMonths()` `getDays()` |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>記憶口訣：</b>Duration 用來計「時」（hours/seconds），Period 用來計「日」（days/months/years）。
</div>

<!--
【核心說明】
這是很多初學者會搞混的地方。一個是計時，一個是計日。

【生活化比喻】
Duration 就像是馬錶（秒數、分鐘）；Period 就像是日曆（幾年幾月幾天）。
-->

---

# Duration：計算時間差

```java
import java.time.*;

LocalTime start = LocalTime.of(9, 0, 0);
LocalTime end   = LocalTime.of(17, 30, 0);

Duration d = Duration.between(start, end);
System.out.println(d.getSeconds());  // 30600
System.out.println(d.toHours());     // 8
System.out.println(d.toMinutes());   // 510
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>也可用於 LocalDateTime：</b><code>Duration.between(ldt1, ldt2)</code> 同樣適用，計算兩個日期時間之間的秒數差。
</div>

<!--
【逐步解說】
想要算出加班多久領加班費？用 Duration 就對了。它會告訴你總共過了幾秒、幾分鐘。
-->

---

# Period：計算日期差

```java
import java.time.*;

LocalDate birth = LocalDate.of(2000, 5, 20);
LocalDate today = LocalDate.of(2024, 5, 13);

Period p = Period.between(birth, today);
System.out.println(p.getYears());   // 23
System.out.println(p.getMonths()); // 11
System.out.println(p.getDays());   // 24
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b><code>getYears()</code>、<code>getMonths()</code>、<code>getDays()</code> 各自取該單位的「餘數」，不是累計加總。例如 23 年 11 個月 24 天，不是 23 年 + 11 個月 = 287 個月。
</div>

<!--
【逐步解說】
想要算出你跟女朋友交往多久了？用 Period。它會回傳「1 年 2 個月 3 天」。

⚠️ 學生常見誤解：
注意！getDays() 回傳的是天數的「餘數」。如果你們隔了 40 天，它會回傳 10 天（扣掉一個月 30 天）。如果你想要總天數，要用另一個類別叫 ChronoUnit。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 舊版 ↔ 新版轉換
## java.util.Date ↔ java.time.Instant

<!--
【段落轉換】
在新舊 API 之間架起橋樑，透過 Instant 進行轉換。
-->

---
layout: default
---

# Date → LocalDate / LocalDateTime

| 轉換目標 | 方式 |
| --- | --- |
| `Date` → `Instant` | `date.toInstant()` |
| `Instant` → `ZonedDateTime` | `instant.atZone(ZoneId.systemDefault())` |
| `ZonedDateTime` → `LocalDate` | `.toLocalDate()` |
| `ZonedDateTime` → `LocalDateTime` | `.toLocalDateTime()` |

```java
import java.util.Date;
import java.time.*;

Date old = new Date();

LocalDate ld = old.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();

LocalDateTime ldt = old.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime();
```

<!--
【核心說明】
身為工程師，我們總是要面對現實——舊的程式碼。

【生活化比喻】
Instant 就像是時空隧道裡的轉運站。不管你是從 Date 出發，還是從 LocalDateTime 出發，都要先到 Instant 轉機。

【逐步解說】
看到這一長串的轉換鏈了嗎？這就是為什麼我們討厭 Date。你要先 toInstant()，然後指定時區 atZone()，最後才能 toLocalDate()。
-->

---

# LocalDate / LocalDateTime → Date

```java
import java.util.Date;
import java.time.*;

// LocalDate → Date
LocalDate localDate = LocalDate.of(2024, 5, 13);
Date d1 = Date.from(
    localDate.atStartOfDay()
             .atZone(ZoneId.systemDefault())
             .toInstant()
);

// LocalDateTime → Date
LocalDateTime ldt = LocalDateTime.of(2024, 5, 13, 10, 30);
Date d2 = Date.from(
    ldt.atZone(ZoneId.systemDefault()).toInstant()
);
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Instant 是橋梁：</b>新舊 API 互轉都要經過 <code>Instant</code>，它代表 UTC 時間軸上的一個精確時刻。
</div>

<!--
【逐步解說】
反過來也一樣，要經過 Instant。記住，Instant 是 UTC 時間，所以轉換時一定要告訴電腦你的時區在哪裡。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 練習題

<!--
【互動引導】
學了這麼多，來動手試試吧！
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
4. 用 `Period` 或 `ChronoUnit.DAYS.between()` 計算相差天數
5. 輸出格式：`距離下次生日還有 X 天`

<!--
【互動引導】
現在，請發揮你的工程師本色，幫自己寫一個生日倒數器。記得喔，如果今年生日過了，你要會自動算明年，不然程式會跑出負數，你的年紀就變小了（雖然我也很想）。
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
【解說要點】
關鍵在於 isBefore 的判斷。還有，如果你想要算「總天數」，建議用 ChronoUnit.DAYS.between，這比 Period 好用多了。
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
【互動引導】
這是最實用的練習。把時間變漂亮，或者是從髒髒的字串裡提取資訊。
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
【解說要點】
解析字串時，格式一定要「一模一樣」。少一個斜線都不行！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

### 今天重點回顧

- `java.util.Date` 執行緒不安全、API 設計老舊 → Java 8 起改用 `java.time`
- `LocalDate` / `LocalTime` / `LocalDateTime` / `ZonedDateTime` 各有適用情境
- `DateTimeFormatter.ofPattern()` 取代 `SimpleDateFormat`，執行緒安全
- `Duration` 計時間差（秒）、`Period` 計日期差（年月日）
- 新舊 API 互轉橋梁：`Instant`

<!--
【開場白】
關於時間的魔法，大家還有什麼疑問嗎？如果你發現你的電腦時間不準，那可能是硬體問題，不是 Java 的鍋喔！
-->

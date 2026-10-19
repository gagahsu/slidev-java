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
大家好，這一章的主題是日期與時間。

計算還有幾天到期、記錄訂單的建立時間、安排跨國會議，幾乎每個系統都會處理到日期和時間。但日期時間的細節其實很多：月份有大有小、有閏年，還有時區。

這一章我們會先看舊版的 Date 類別，了解它為什麼被淘汰，再學 Java 8 之後的 java.time 套件。學完之後，我們就能正確地建立、計算和格式化日期時間。
-->

---
layout: default
---

# Outline

- **舊版 Date 類別（java.util.Date）**
  - new Date()、getTime()、SimpleDateFormat
  - 為何 Date 被淘汰

- **新版日期時間類別（java.time）**
  - LocalDate、LocalTime、LocalDateTime
  - ZonedDateTime 與 ZoneId
  - DateTimeFormatter：格式化與解析
  - Duration 與 Period：時間差計算
  - 舊版 Date ↔ 新版 Instant 轉換

- **類別選用時機對照表**
- **練習題**

<!--
這一章分成兩大部分。

第一部分是舊版的 java.util.Date，包括基本用法、用 SimpleDateFormat 格式化，以及它被淘汰的原因。

第二部分是新版的 java.time 套件，包括 LocalDate、LocalTime、LocalDateTime 和有時區的 ZonedDateTime，接著是格式化工具 DateTimeFormatter、計算時間差的 Duration 和 Period，以及新舊 API 之間怎麼轉換。最後有兩題練習。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 舊版 Date 類別
## java.util.Date

<!--
我們先從舊版的 Date 類別開始。雖然新專案已經不用它了，但在很多既有的系統裡，還是會看到它。
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
java.util.Date 代表一個時間點。用 new Date 建立物件，就代表「現在」。

它內部其實記錄的是一個毫秒數：從 1970 年 1 月 1 日零點，也就是 UTC 時間起算，到現在經過了多少毫秒。這個數字叫做 Unix 時間戳記，呼叫 getTime 就能取得。

看範例，直接印出 now，會得到一串可以閱讀的日期時間；印出 getTime，則是一個很長的數字，就是那個毫秒數。
-->

---

# SimpleDateFormat：常用格式符號

| 符號 | 說明 | 範例 |
| --- | --- | --- |
| `yyyy` | 四位數年份 | 2024 |
| `MM` | 兩位數月份 | 01–12 |
| `dd` | 兩位數日期 | 01–31 |
| `HH` | 24 小時制小時 | 00–23 |
| `mm` / `ss` | 分鐘 / 秒數 | 00–59 |
| `E` | 星期幾縮寫 | Mon, Tue |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>大小寫有別：</b><code>MM</code> 是月份，<code>mm</code> 是分鐘；<code>HH</code> 是 24 小時制，<code>hh</code> 是 12 小時制。
</div>

<!--
要把 Date 印成我們想要的格式，舊版的做法是使用 SimpleDateFormat，搭配格式符號。

yyyy 是四位數的年份，大寫的 MM 是月份，dd 是日期，大寫的 HH 是 24 小時制的小時，小寫的 mm 和 ss 是分鐘和秒，E 是星期幾。

下面的提醒非常重要：「大小寫代表不同的意思。」大寫 MM 是月份，小寫 mm 是分鐘；大寫 HH 是 24 小時制，小寫 hh 是 12 小時制。把月份寫成小寫 mm，是很常見的錯誤，印出來的會是分鐘數。
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
SimpleDateFormat 有兩個主要的方法。format 把 Date 轉成字串，parse 則反過來，把字串解析成 Date。

看範例，先用年月日時分秒的格式建立 sdf。format 傳入目前時間，就會得到像 2024/05/13 10:30:00 這樣的字串。parse 則把同樣格式的字串，轉回 Date 物件。

要注意，如果字串的格式和設定不一樣，parse 會拋出 ParseException，所以實際使用時要處理這個例外，之後的章節會學到怎麼處理。
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
那為什麼 Date 會被淘汰？它有幾個明顯的設計問題。

第一，SimpleDateFormat 不是執行緒安全的，多個執行緒同時使用同一個物件時，可能會得到錯誤的結果。第二，月份從 0 開始，getMonth 回傳的一月是 0、十二月是 11，非常容易出錯。第三，getYear 回傳的是西元年減掉 1900，2024 年會得到 124。

另外，相關的類別分散在不同的套件，而且 Date 物件可以被修改，難以確保資料不被意外改動。

所以從 Java 8 開始，官方建議改用 java.time 套件。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 新版日期時間類別
## java.time 套件（Java 8+）

<!--
接下來，我們進入 Java 8 推出的新版日期時間 API，也就是 java.time 套件。
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
這張表比較了新舊兩套 API。

java.time 的物件全部都是不可變的，所以天生就是執行緒安全的。月份是 1 到 12，符合直覺。所有類別都集中在同一個套件，命名一致，讀起來也很流暢。

時區有專門的 ZonedDateTime 和 ZoneId 來處理；計算時間差，也有 Duration 和 Period 可以直接使用，不用再自己把毫秒相減。

所以原則是：「新專案直接使用 java.time。」如果要和使用 Date 的舊系統互動，再透過 Instant 轉換就好。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDate
## 只有日期，沒有時間

<!--
我們先看最常用的 LocalDate。它只有日期，沒有時間，適合用來表示生日、假日這類資料。
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

<!--
建立 LocalDate 有三種常見的方式：now 取得今天的日期；of 傳入年、月、日，建立指定的日期；parse 則把年月日格式的字串，解析成日期。

比較兩個日期時，可以用 isBefore 判斷是否在另一個日期之前，用 isAfter 判斷是否在之後。
-->

---

# LocalDate 常用 API（一）— 範例

```java
import java.time.LocalDate;

LocalDate today = LocalDate.now();
LocalDate birthday = LocalDate.of(2000, 5, 20);
LocalDate parsed = LocalDate.parse("2024-01-15");

System.out.println(today);
System.out.println(birthday.isBefore(today)); // true
```

<!--
我們看實際的寫法。

today 是今天的日期。birthday 用 of 建立 2000 年 5 月 20 日，注意這裡的 5 就是五月，不像舊版 Date 要寫成 4。parsed 從字串 2024-01-15 解析而來。

最後判斷 birthday 是否在 today 之前，結果是 true。整段程式讀起來就像在讀英文句子，這就是 java.time 設計得很直覺的地方。
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

<!--
LocalDate 也提供了加減日期的方法：plusDays 加幾天，minusMonths 減幾個月，plusYears 加幾年，同樣的命名規則也適用於其他單位。

另外，getDayOfWeek 可以取得這一天是星期幾，getDayOfMonth 可以取得是這個月的幾號。

這些加減方法會自動處理大小月和閏年。例如 1 月 31 日加一個月，會得到 2 月的最後一天，不需要我們自己判斷。
-->

---

# LocalDate 常用 API（二）— 範例

```java
LocalDate today = LocalDate.now();
LocalDate nextWeek = today.plusDays(7);
LocalDate lastMonth = today.minusMonths(1);

DayOfWeek dow = LocalDate.of(2024, 5, 20).getDayOfWeek();
System.out.println(dow);        // MONDAY
System.out.println(dow.getValue()); // 1（週一=1，週日=7）
```

<!--
我們看範例。today 加 7 天，得到下週的同一天；減 1 個月，得到上個月的同一天。

要特別注意，LocalDate 是不可變的物件，plusDays 不會修改 today 本身，而是回傳一個新的日期。所以一定要用變數把結果接起來，例如這裡的 nextWeek。只寫 today.plusDays 7 卻沒有接住結果，today 是不會改變的。

最後用 getDayOfWeek 取得 2024 年 5 月 20 日是星期幾，結果是 MONDAY。它的 getValue 是 1，星期一是 1，星期日是 7。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalTime
## 只有時間，沒有日期

<!--
接下來是 LocalTime，它和 LocalDate 剛好相反，只有時間，沒有日期。
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

<!--
LocalTime 的用法和 LocalDate 很像。

now 取得目前時間，of 指定時和分，parse 從字串解析。加減時間用 plusHours、minusMinutes 這類方法，取出時和分則用 getHour 和 getMinute。

學會了 LocalDate，LocalTime 幾乎不需要額外記憶，這也是 java.time 命名一致的好處。
-->

---

# LocalTime — 範例

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
看範例，meeting 是下午 2 點 30 分，加上 2 小時之後，end 就是 4 點 30 分，getHour 會得到 16。

每天的營業時間、鬧鐘設定這類不需要日期的資料，就很適合用 LocalTime。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDateTime
## 日期 + 時間，但無時區

<!--
如果同時需要日期和時間，就用 LocalDateTime。不過要注意，它沒有時區資訊。
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

<!--
LocalDateTime 一樣可以用 now 取得現在，或用 of 指定年月日時分。

它還可以把現成的 LocalDate 和 LocalTime 合併成一個 LocalDateTime；反過來，也能用 toLocalDate 和 toLocalTime 拆出日期和時間的部分。
-->

---

# LocalDateTime — 範例

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
看範例，dt 直接指定 2024 年 5 月 13 日 10 點 30 分。下面則是先建立日期 d 和時間 t，再用 of 把它們合併。

印出 combined，會看到日期和時間中間有一個大寫的 T，這是國際標準 ISO 8601 的日期時間格式。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# ZonedDateTime
## 含時區的日期時間

<!--
當系統要服務不同國家的使用者時，就要考慮時區。這時候要用的是 ZonedDateTime。
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

<!--
ZoneId 代表一個時區，用「區域斜線城市」的格式建立，例如 Asia/Taipei。systemDefault 則可以取得電腦目前設定的時區。

ZonedDateTime 是帶有時區的日期時間。用 now 傳入時區，可以取得那個時區的現在時間；也可以用 of，把一個 LocalDateTime 加上時區。getZone 則可以取得它的時區資訊。
-->

---

# ZonedDateTime — 範例

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
看範例，先建立台北和巴黎兩個時區。ZonedDateTime.now 傳入台北，就是台北現在的時間。

接著把 2024 年 5 月 13 日上午 10 點，加上台北時區。印出來會看到 +08:00，表示比世界標準時間快 8 小時，最後中括號裡是時區名稱 Asia/Taipei。

下面列出了幾個常用的時區名稱，例如東京、紐約和倫敦。
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
💡 <b>原則：</b>只要不需要考慮時區，優先用 <code>LocalDate</code> / <code>LocalDateTime</code>；跨國系統需處理時區時才用 <code>ZonedDateTime</code>（單純記錄 UTC 時間點可用 <code>Instant</code>）。
</div>

<!--
學了四種類別，該怎麼選擇？這張表整理了它們各自包含哪些資訊。

LocalDate 只有日期，適合生日、假日。LocalTime 只有時間，適合開店時間、鬧鐘。LocalDateTime 有日期和時間，適合本地的活動或資料庫裡的時間欄位。ZonedDateTime 三者都有，適合跨時區的會議和航班時刻。

原則是：不需要考慮時區時，優先用 LocalDate 或 LocalDateTime；只有跨時區的系統，才需要 ZonedDateTime。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# DateTimeFormatter
## 格式化與解析

<!--
接下來是 DateTimeFormatter，它負責把日期時間轉成我們想要的格式，也能把字串解析回日期時間。
-->

---
layout: default
---

# DateTimeFormatter 常用格式符號

| 符號 | 說明 | 範例 |
| --- | --- | --- |
| `yyyy` | 四位數年份 | 2024 |
| `MM` / `dd` | 月份 / 日期 | 01–12 / 01–31 |
| `HH` | 24 小時制 | 00–23 |
| `mm` / `ss` | 分鐘 / 秒數 | 00–59 |
| `E` | 星期縮寫 | Mon |
| `z` / `VV` | 時區縮寫 / 時區 ID | CST / Asia/Taipei |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>執行緒安全：</b>DateTimeFormatter 是不可變物件，可宣告為靜態常數，安全地在多執行緒環境共用。
</div>

<!--
DateTimeFormatter 的格式符號，和剛才的 SimpleDateFormat 幾乎一樣：yyyy 是年，大寫 MM 是月，dd 是日，大寫 HH 是 24 小時制，小寫 mm 和 ss 是分和秒，E 是星期幾。另外還多了時區相關的符號。

最大的差別在於，DateTimeFormatter 是不可變的物件，可以安心地宣告成 static 常數，讓整個程式共用，不用擔心多執行緒的問題。
-->

---

# DateTimeFormatter：格式化與解析

| 方法 | 說明 |
| --- | --- |
| `DateTimeFormatter.ofPattern(pattern)` | 依自訂樣式建立 formatter |
| `ldt.format(formatter)` | 日期時間 → 字串 |
| `LocalDate.parse(str, formatter)` | 字串 → LocalDate |
| `LocalDateTime.parse(str, formatter)` | 字串 → LocalDateTime |

<!--
使用方式也很簡單。

先用 ofPattern 傳入格式字串，建立一個 formatter。要把日期時間轉成字串，就呼叫日期時間物件的 format 方法；要把字串轉回來，就呼叫 LocalDate 或 LocalDateTime 的 parse，並傳入同一個 formatter。
-->

---

# DateTimeFormatter — 範例

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
我們看實際的範例。

用年月日時分秒的格式建立 fmt，接著把現在的時間 format 成字串，就會得到像 2024/05/13 10:30:00 這樣的結果。

反過來，LocalDateTime.parse 傳入同樣格式的字串和 fmt，就能得到 LocalDateTime 物件。只要格式設定一次，就能來回轉換。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Duration 與 Period
## 計算時間差

<!--
計算兩個時間點相差多久，是很常見的需求。java.time 提供了 Duration 和 Period 兩個類別。
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
這兩個類別都用來表示時間的長度，差別在於單位。

Duration 以秒和奈秒為單位，適合計算兩個時刻之間的時間差，例如上班時數。Period 以年、月、日為單位，適合計算兩個日期之間的差距，例如年齡。

兩者都用 between 建立。可以這樣記：「Duration 算時間，Period 算日期。」
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
我們用 Duration 計算一天的上班時間。

上班是早上 9 點，下班是下午 5 點 30 分。Duration.between 算出兩者的差距之後，getSeconds 得到 30600 秒，toHours 得到 8 小時，toMinutes 得到 510 分鐘。

注意 toHours 是 8，因為它只取完整的小時數，剩下的 30 分鐘會被捨去。Duration 也可以用在 LocalDateTime 上，計算跨日期的時間差。
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
System.out.println(p.getDays());   // 23
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b><code>getYears()</code>、<code>getMonths()</code>、<code>getDays()</code> 各自取該單位的「餘數」，不是累計加總。例如 23 年 11 個月 23 天的 <code>getMonths()</code> 是 11；要總月數請用 <code>p.toTotalMonths()</code>（287）。
</div>

<!--
Period 用來計算兩個日期之間相差幾年幾個月幾天。

從 2000 年 5 月 20 日到 2024 年 5 月 13 日，相差 23 年 11 個月 23 天。

要特別注意，getMonths 回傳的是扣掉整年之後「剩下的」月數，也就是 11，而不是總共經過了幾個月。如果要總月數，要用 toTotalMonths，結果是 287。這是使用 Period 時很容易誤會的地方。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 舊版 ↔ 新版轉換
## java.util.Date ↔ java.time.Instant

<!--
最後，我們看看新舊 API 之間怎麼轉換。舊系統的程式常常還在使用 Date，就需要和新版的 java.time 互相轉換。
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

<!--
新舊 API 之間的橋梁，是 Instant。它代表時間軸上一個精確的時刻。

從 Date 轉成 LocalDate，要經過幾個步驟：先用 toInstant 轉成 Instant；再用 atZone 加上時區，變成 ZonedDateTime；最後再取出 LocalDate 或 LocalDateTime。

為什麼要加上時區？因為同一個時刻，在台北和紐約是不同的日期和時間，一定要先決定用哪個時區來看。
-->

---

# Date → LocalDate / LocalDateTime — 範例

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
這是程式碼的寫法，三個步驟串在一起。

舊的 Date 物件先呼叫 toInstant，再呼叫 atZone 加上系統預設的時區，最後用 toLocalDate 取得日期，或用 toLocalDateTime 取得日期和時間。

這種把多個方法一路接下去的寫法，在 java.time 裡很常見，讀起來就像一句完整的步驟說明。
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
反過來，從新版轉回 Date，就是把步驟倒過來，最後用 Date.from 把 Instant 轉成 Date。

LocalDate 只有日期，所以要先用 atStartOfDay 補上當天的零點，變成 LocalDateTime，接著加上時區、轉成 Instant，再交給 Date.from。LocalDateTime 則可以直接加上時區，再轉成 Instant。

記住，不管往哪個方向轉，中間都要經過 Instant。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 練習題

<!--
接下來，我們用兩題練習，把這一章學到的日期時間操作實際用一遍。
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
第一題，計算今天到下次生日還有幾天。

先取得今天的日期，設定今年的生日；如果今年的生日已經過了，就改算明年的生日。最後算出相差幾天，印出「距離下次生日還有幾天」。

我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
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
我們來看提示。

用 today.getYear 取得今年的年份，建立今年的生日。接著用 isBefore 判斷生日是否在今天之前，如果是，就用 plusYears 1 改成明年，記得把結果存回變數。

最後用 ChronoUnit.DAYS.between，計算兩個日期之間總共相差幾天。這裡不建議用 Period 的 getDays，因為它只會給扣掉月份之後剩下的天數。
-->

---
layout: default
---

# 練習二：時間格式轉換
### 任務說明

請撰寫程式，完成以下轉換任務：

**任務 A — 格式化輸出：**
將現在時間以 `yyyy 年 MM 月 dd 日 HH:mm:ss（EEEE）` 格式印出，例如：`2024 年 05 月 13 日 10:30:00（星期一）`

**任務 B — 解析字串：**
將字串 `"2024/05/13 10:30"` 解析為 `LocalDateTime` 物件，並分別印出年份、月份和星期幾

<!--
第二題是時間格式轉換，分成兩個任務。

任務 A，把現在的時間印成中文格式，例如 2024 年 05 月 13 日 10:30:00，後面括號裡是星期一。

任務 B，把字串 2024/05/13 10:30 解析成 LocalDateTime，再分別印出年份、月份和星期幾。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 10 分鐘實作。
-->

---

# 練習二：解題提示
### 提示說明

**任務 A 提示：**
1. 建立 `DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日 HH:mm:ss（EEEE）", Locale.TRADITIONAL_CHINESE)`
2. `LocalDateTime.now().format(formatter)`
3. `E` 輸出縮寫「週一」，`EEEE` 輸出全名「星期一」

**任務 B 提示：** 以 `ofPattern("yyyy/MM/dd HH:mm")` 搭配 `LocalDateTime.parse()` 解析，再呼叫 `.getYear()`、`.getMonth()`、`.getDayOfWeek()`

```java
DateTimeFormatter fmt =
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
LocalDateTime ldt = LocalDateTime.parse("2024/05/13 10:30", fmt);
System.out.println(ldt.getYear());       // 2024
System.out.println(ldt.getDayOfWeek()); // MONDAY
```

<!--
我們來看提示。

任務 A，用 ofPattern 建立格式，第二個參數傳入繁體中文的 Locale，這樣星期才會以中文顯示。一個 E 會印出縮寫的「週一」，四個 E 則會印出完整的「星期一」。

任務 B，用 yyyy/MM/dd HH:mm 的格式建立 formatter，搭配 LocalDateTime.parse 解析，再呼叫 getYear、getMonth 和 getDayOfWeek，就能取得年份、月份和星期幾。
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
這一章我們學會了處理日期與時間。

舊版的 Date 有執行緒不安全、月份從 0 開始等問題，所以 Java 8 之後改用 java.time。LocalDate、LocalTime、LocalDateTime 和 ZonedDateTime，依照需不需要日期、時間和時區來選擇。DateTimeFormatter 負責格式化和解析，而且可以安心共用。Duration 計算時間差，Period 計算日期差，新舊 API 則透過 Instant 互相轉換。

下一章，我們要深入學習字元與字串。

【講師】開放 Q&A。
-->

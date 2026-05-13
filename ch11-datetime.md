---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# 舊版 Date 類別
## java.util.Date

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 新版日期時間類別
## java.time 套件（Java 8+）

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDate
## 只有日期，沒有時間

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalTime
## 只有時間，沒有日期

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# LocalDateTime
## 日期 + 時間，但無時區

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# ZonedDateTime
## 含時區的日期時間

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# DateTimeFormatter
## 格式化與解析

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Duration 與 Period
## 計算時間差

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 舊版 ↔ 新版轉換
## java.util.Date ↔ java.time.Instant

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 練習題

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

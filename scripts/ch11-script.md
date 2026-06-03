# Ch11 日期與時間的類別 — 演講稿

## 封面：日期與時間的類別

好，今天我們要講的是「日期與時間」。

你有沒有想過，一個日期，看起來超簡單——2024年5月13日，就這樣——但是在程式裡面，光是要把這個東西「存起來」、「算一算差幾天」、「換個格式印出來」，就可以讓人頭痛個半天？

Java 的歷史上，為了處理日期時間，走過一段非常崎嶇的路。今天我們要從老祖宗的 `java.util.Date` 講起，講到 Java 8 之後全新的 `java.time` 家族。

你會發現：原來那些年我們踩過的坑，Java 後來統統幫我們填掉了。

那我們開始。

---

## Outline

今天的內容大概是這樣：

第一部分，我們先認識舊版的 `Date` 類別——它怎麼用、有什麼問題、為什麼後來被淘汰。

第二部分，進入新版 `java.time` 套件，這才是現代 Java 開發的主場。我們會看到 `LocalDate`、`LocalTime`、`LocalDateTime`，然後還有帶時區的 `ZonedDateTime`，以及格式化用的 `DateTimeFormatter`，還有計算時間差的 `Duration` 跟 `Period`。

最後，我們看一下新舊 API 怎麼互轉，收尾之後有兩個練習讓大家動手做。

內容不少，但其實邏輯很清晰，跟著走就好。

---

## 第一部分：舊版 Date 類別

好，先進入第一部分：舊版的 `java.util.Date`。

說真的，這一段有點像是「考古課」。但你知道嗎，很多老系統到現在還在用這些東西，你不認識它，以後接手別人程式碼的時候會很痛苦。

所以花個幾分鐘，讓我們跟老前輩打個招呼。

---

## java.util.Date 基本用法

`java.util.Date` 最基本的用法就是：`new Date()`，建立一個「現在」的時間點物件。

你可以對它呼叫 `toString()` 印出可讀的時間字串，也可以呼叫 `getTime()` 拿到一個長長的數字——那個數字叫做「毫秒時間戳記」。

什麼是毫秒時間戳記？就是從 1970 年 1 月 1 日 00:00:00 UTC 到現在，過了幾毫秒。這個設計是 Unix 系統傳下來的，所以也叫 Unix epoch time。

你可以想像這樣：有個老爺爺站在 1970 年的元旦，從那一刻開始不停地數「一毫秒、兩毫秒、三毫秒……」，一直數到現在。`getTime()` 就是問他：「老爺爺，你數到幾了？」然後他回答：「大概 1 兆七千億左右。」

這個毫秒數在底層比較或計算是很方便的，但肉眼直接看幾乎沒辦法理解，所以才需要格式化。

---

## SimpleDateFormat：常用格式符號

格式化舊版 Date，我們要用的工具叫做 `SimpleDateFormat`。

它的邏輯很直覺——你給一個「樣板字串」，它就按照那個樣板幫你把日期時間印出來。

這裡要特別注意幾個大小寫的差異，這個超容易踩雷：

`MM` 大寫是「月份」，`mm` 小寫是「分鐘」。

`HH` 大寫是 24 小時制，`hh` 小寫是 12 小時制。

聽起來很合理，但每次打的時候就是容易打錯。曾經有人寫出 `yyyy/mm/dd`，結果月份那個欄位跑出來的是「30」，然後他找了半小時才發現 `mm` 印的是分鐘，不是月份。

所以記好：月份是大寫 MM，分鐘才是小寫 mm。

---

## SimpleDateFormat：format() 與 parse()

`SimpleDateFormat` 有兩個最核心的方法。

`format()`：把 Date 物件變成格式化的字串。這叫「格式化輸出」。

`parse()`：把格式化的字串變回 Date 物件。這叫「解析輸入」。

使用流程很簡單：先建立一個 `SimpleDateFormat` 物件，告訴它格式樣板是什麼，然後再呼叫 `format()` 或 `parse()`。

`parse()` 有個要注意的地方：它可能拋出 `ParseException`——也就是說，如果你傳進去的字串格式不對，它就會報錯。所以通常要加 try-catch，或者在方法簽名上 throws 出去。

---

## 為何 Date 被淘汰？

好，講到這裡你可能覺得：「這不是很正常嗎，哪裡有問題？」

說真的，問題很多。

第一，`SimpleDateFormat` 執行緒不安全。在多執行緒程式裡，如果大家共用同一個 `SimpleDateFormat` 物件，很容易出現資料錯亂的 bug，而且還是那種偶發性的 bug，超難抓。

第二，月份從 0 開始。這個是歷史遺留問題，`Date.getMonth()` 回傳的是 0 到 11，不是 1 到 12。你寫 1 月，它給你 0；你寫 12 月，它給你 11。這超反人類直覺。

第三，年份要加 1900。`Date.getYear()` 回傳的是「西元年減掉 1900」的值。例如 2024 年，它給你 124。

你說這是什麼設計……這就是祖先留下來的技術債。

所以 Java 8 推出了全新的 `java.time` 套件，把這些坑全部填掉。從那之後，`Date` 就正式進入「雖然還活著但不建議用」的狀態了。

---

## 第二部分：新版日期時間類別

好，告別老爺爺，進入新世代。

Java 8 帶來了 `java.time` 套件，這是由 Joda-Time 的作者主導設計的，設計哲學很清晰：不可變、執行緒安全、API 直覺。

接下來這一段，是這堂課最核心的部分，要認識四個主要類別。

---

## 舊版 Date vs 新版 java.time

用一張表格來比較一下新舊兩代的差異。

最關鍵的是執行緒安全。新版所有物件都是不可變的，所以天生就是執行緒安全，完全不用煩惱多執行緒共用的問題。

月份索引從 0-11 變成 1-12，終於回到人類的正常思維了。

API 設計也統一收在 `java.time` 套件裡，不用再東找一個類別、西找一個類別。

還有時間差計算，舊版要手動相減毫秒，自己做數學；新版有專門的 `Duration` 跟 `Period` 來幫你算。

總之，新版就是老版的全面升級版。只要是新專案，請直接用 `java.time`，不用猶豫。

---

## LocalDate（只有日期）

先介紹第一個類別：`LocalDate`。

它只有「日期」，沒有時間，也沒有時區。就像你手機日曆上看到的那種：2024 年 5 月 13 日，僅此而已。

什麼時候用 LocalDate？生日、假日、課表安排、活動日期——這些場合，你根本不在乎幾點幾分，只在乎是哪一天，就用 LocalDate。

---

## LocalDate 常用 API（一）

LocalDate 最基本的三種建立方式：

`LocalDate.now()` 取得今天。`LocalDate.of(2000, 5, 20)` 指定某一天。`LocalDate.parse("2024-01-15")` 從字串解析，注意預設格式是 ISO 格式，也就是 yyyy-MM-dd，中間用橫線隔開。

比較日期用 `isBefore()` 跟 `isAfter()`，語意非常清楚，讀起來幾乎就是英文句子：`birthday.isBefore(today)` — 生日在今天之前嗎？對，就這麼直覺。

---

## LocalDate 常用 API（二）

LocalDate 還有一套非常好用的「加減」API。

`plusDays(7)` 加七天，`minusMonths(1)` 減一個月，`plusYears(1)` 加一年。

而且，這些方法都是「回傳新物件」，原本的 LocalDate 不會被改變。這就是「不可變物件」的好處——你不用擔心有人偷偷改了你的日期。

`getDayOfWeek()` 取得星期幾，回傳的是 `DayOfWeek` 列舉。`.getValue()` 可以拿到數字，週一是 1，週日是 7，這個也符合人類直覺，不像舊版那樣搞怪。

---

## LocalTime（只有時間）

第二個類別：`LocalTime`。

跟 LocalDate 是一對：只有「時間」，沒有日期，也沒有時區。就像時鐘上顯示的 14:30，它不知道今天是幾號，它只知道現在是下午兩點半。

什麼時候用 LocalTime？每日的固定時間——開店時間、鬧鐘設定、上課時段——這些場合，日期根本不重要，只需要時間的話，就用 LocalTime。

---

## LocalTime 常用 API

LocalTime 的 API 跟 LocalDate 非常相似，學完 LocalDate 之後 LocalTime 幾乎不需要再學，因為設計一模一樣。

`LocalTime.now()` 取現在時間。`LocalTime.of(14, 30)` 指定時間。`parse()` 從字串解析。

加減的部分：`plusHours()`、`minusMinutes()`，語意清晰，看名字就懂。

取值的部分：`getHour()` 取小時，`getMinute()` 取分鐘，如此而已。

---

## LocalDateTime（日期 + 時間）

第三個類別：`LocalDateTime`。

顧名思義，它是 LocalDate 跟 LocalTime 的組合體——有日期、有時間，但沒有時區。

什麼時候用？本地的活動時間、資料庫裡的時間欄位（在不需要考慮時區的系統裡）——這些場合都很合適。

---

## LocalDateTime 常用 API

LocalDateTime 可以直接用 `of()` 傳入年月日時分秒建立，也可以把一個 LocalDate 跟一個 LocalTime「合併」起來變成 LocalDateTime。

反過來，`toLocalDate()` 跟 `toLocalTime()` 可以把它拆開成日期部分和時間部分。

就像積木一樣：可以組合，也可以拆解。

---

## ZonedDateTime（含時區）

第四個類別，也是最進階的一個：`ZonedDateTime`。

它在 LocalDateTime 的基礎上，多了「時區」這個維度。

什麼時候需要時區？當你的使用者或資料跨越不同國家的時候。跨時區的會議、國際航班時刻表、全球用戶的活動時間——這時候你就不能忽略時區了。

你可以想像成出國旅行時換時區的手錶。同樣的一個時刻，在台北顯示下午兩點，在巴黎顯示早上七點，在紐約前一天晚上八點——同一個時刻，不同地方不同顯示，ZonedDateTime 幫你處理這件事。

---

## ZonedDateTime 與 ZoneId

`ZoneId` 是時區物件，用 `ZoneId.of("Asia/Taipei")` 這種「區域/城市」的格式來指定。

常用的時區 ID 有：`Asia/Taipei`、`Asia/Tokyo`、`America/New_York`、`Europe/London`。這些都是 IANA 時區資料庫的標準名稱，直接背幾個常用的就好。

`ZonedDateTime.now(taipei)` 取得台北時間的現在時刻。也可以把一個 LocalDateTime 加上時區，變成 ZonedDateTime。

印出來的格式會包含時差資訊，像 `+08:00[Asia/Taipei]`，讓你一眼就看出這是哪個時區的時間。

---

## 四種日期時間類別選用時機

好，我們已經介紹了四個類別，現在整理一下，什麼情況用哪一個：

只需要日期、不需要時間？用 `LocalDate`。生日、假日、課表。

只需要時間、不需要日期？用 `LocalTime`。鬧鐘、每日固定時段。

需要日期加時間、但不需要時區？用 `LocalDateTime`。本地活動、資料庫時間欄。

需要時區、或者跨國系統？用 `ZonedDateTime`。國際會議、航班時刻。

有個超簡單的判斷法：你的使用者只在一個國家嗎？那就 Local 系列就夠了。跨國、跨時區？那就需要 ZonedDateTime。

---

## 第三部分：DateTimeFormatter

接下來講格式化，這是很實用的部分。

`DateTimeFormatter` 就是新版的 `SimpleDateFormat`，但它有一個很大的優勢：它是不可變物件，所以天生執行緒安全。你可以宣告成靜態常數，在整個程式裡到處共用，完全沒問題。

---

## DateTimeFormatter 常用格式符號

符號的用法跟 SimpleDateFormat 大致相同，所以如果你之前學過舊版，這裡幾乎不用重學。

一樣要注意大小寫：`MM` 是月份，`mm` 是分鐘；`HH` 是 24 小時制，`hh` 是 12 小時制。

新版多了 `VV` 符號，用來顯示完整的時區 ID，像 `Asia/Taipei`。舊版的 `z` 只能顯示縮寫。

記憶技巧：大寫通常表示「比較大的單位或比較重要的東西」——年份 `y`、月份 `M`、24 小時 `H`。

---

## DateTimeFormatter：格式化與解析

用法分兩個方向：

「物件變字串」：`now.format(formatter)`，在日期時間物件上呼叫 `format()`，傳入 formatter。

「字串變物件」：`LocalDateTime.parse(str, formatter)`，在目標類別的靜態方法 `parse()` 裡傳入字串跟 formatter。

跟舊版 SimpleDateFormat 邏輯一樣，只是呼叫方式不同。而且新版不需要 try-catch——如果格式不對，它拋的是 unchecked exception，你可以選擇捕捉，也可以不管它讓它直接爆開提示你。

---

## 第四部分：Duration 與 Period

現在講計算時間差，這也是很常用到的功能。

Java 提供了兩個類別：`Duration` 跟 `Period`。

它們乍看之下很像，但用途不一樣。

---

## Duration vs Period 比較

`Duration` 計算的是「時間段」，單位是秒、毫秒、奈秒這種精確的時間量。適合計算兩個時刻之間差了幾秒、幾分鐘、幾小時。

`Period` 計算的是「日期段」，單位是年、月、日。適合計算兩個日期之間差了幾年幾個月幾天。

有個記憶口訣：Duration 計「時」，Period 計「日」。

你可以這樣想：Duration 是在計算「上班還有幾分鐘可以打卡」；Period 是在計算「距離放假還有幾個月幾天」。

---

## Duration：計算時間差

`Duration.between(start, end)` 計算兩個時間點的差距。

建立好之後，可以用 `getSeconds()` 取得總秒數，`toHours()` 取得整數小時，`toMinutes()` 取得整數分鐘。

這裡有個細節：`toMinutes()` 是「總共幾分鐘」，不是「幾小時幾分鐘的那個分鐘」。例如 8.5 小時，`toMinutes()` 回傳 510，不是 30。

Duration 也可以用在兩個 LocalDateTime 之間，不只是 LocalTime。

---

## Period：計算日期差

`Period.between(birth, today)` 計算兩個日期之間的差距。

拿到 Period 之後，`getYears()` 取年，`getMonths()` 取月，`getDays()` 取日。

這裡有個重要的注意事項：這三個方法拿到的是「各單位的餘數」，不是累計值。

舉個例子：23 年 11 個月 24 天，`getYears()` 是 23，`getMonths()` 是 11，`getDays()` 是 24。

如果你以為 `getMonths()` 會回傳總共幾個月，那就錯了——23 年 11 個月總共有 287 個月，但 `getMonths()` 只給你 11，那個「23年」的部分不算在裡面。

要取累計天數的話，用 `ChronoUnit.DAYS.between(d1, d2)` 會更方便。

---

## 第五部分：舊版 ↔ 新版轉換

最後一個知識點：新舊 API 之間怎麼互轉。

你可能會遇到的情況是：系統裡有一個舊方法回傳 `java.util.Date`，但你想用新版 API 來處理它；或者某個舊的函式庫需要傳入 `Date`，但你手上有的是 LocalDateTime。

這時候，中間的橋梁是 `Instant`。

---

## Date → LocalDate / LocalDateTime

舊版 Date 轉新版的流程：

`Date` → `Instant`：用 `date.toInstant()`。

`Instant` → `ZonedDateTime`：用 `instant.atZone(ZoneId.systemDefault())`，加上你的時區。

`ZonedDateTime` → `LocalDate` 或 `LocalDateTime`：最後再呼叫 `.toLocalDate()` 或 `.toLocalDateTime()` 拆出你要的部分。

步驟看起來有點多，但其實就是一條鏈式呼叫寫下去，流暢得很。每一步都在說：「現在我在哪一層，我要去哪一層」。

---

## LocalDate / LocalDateTime → Date

反方向：新版轉舊版。

要把 LocalDate 轉回 Date，先用 `atStartOfDay()` 把它變成 LocalDateTime（時間設為當天凌晨 00:00），再加上時區變成 ZonedDateTime，再轉成 Instant，最後用 `Date.from(instant)` 拿到 Date 物件。

LocalDateTime 轉 Date 稍微簡單一點，省掉 `atStartOfDay()` 那步。

這種情況通常是在跟舊系統、舊函式庫互接的時候才會遇到。現代新專案的話，你大概不太需要做這個轉換，直接全程用 `java.time` 就好了。

---

## 練習一：計算倒數天數

好，進入練習環節。

第一題：計算距離下次生日還有幾天。

這題考的是 LocalDate 的基本操作，加上一點小邏輯判斷。

關鍵是：如果今年的生日已經過了，就要改算明年的生日。怎麼判斷？`birthday.isBefore(today)` 如果是 true，就 `plusYears(1)` 往後加一年。

計算天數差，用 `ChronoUnit.DAYS.between(today, birthday)` 最方便，直接回傳 long 型別的天數，不用自己換算。

---

## 練習一：解題提示

解題步驟整理一下：

第一步，用 `LocalDate.now()` 取今天。

第二步，建立今年的生日：`LocalDate.of(today.getYear(), 5, 20)`，注意這裡的月份是 5，不是 4，因為新版月份是 1 到 12，完全符合人類直覺。

第三步，判斷生日是否已過，若是則加一年。

第四步，`ChronoUnit.DAYS.between(today, birthday)` 計算天數差。

第五步，印出結果。

大家可以試著自己寫完整程式碼，再對答案。

---

## 練習二：時間格式轉換

第二題：格式轉換。

任務 A 是格式化輸出，要把現在時間印成「2024 年 05 月 13 日 10:30:00（星期一）」這種中文格式。

任務 B 是解析字串，把 `"2024/05/13 10:30"` 這個字串解析回 LocalDateTime，然後取出年份、月份、星期幾。

這兩個任務就是在練習 `DateTimeFormatter` 的 `format()` 跟 `parse()`，是基本功，但很實用，以後常常會用到。

---

## 練習二：解題提示

任務 A：先建立一個包含中文「年月日」字眼的格式樣板，直接把那些中文字放在 pattern 字串裡就好，`DateTimeFormatter` 會原封不動地輸出它們。

任務 B：建立格式是 `"yyyy/MM/dd HH:mm"` 的 formatter，然後呼叫 `LocalDateTime.parse(str, formatter)`，接著用 `getYear()`、`getMonth()`、`getDayOfWeek()` 取出各個欄位。

`getMonth()` 回傳的是 `Month` 列舉，印出來是英文月份名稱；`getDayOfWeek()` 回傳的是 `DayOfWeek` 列舉，印出來是英文星期名稱。如果需要數字的話，呼叫 `.getValue()` 就好。

---

## Q & A：今天重點回顧

好，最後來快速複習今天的重點。

第一，舊版 `java.util.Date` 執行緒不安全、月份從 0 開始、年份要加 1900，設計一團亂。Java 8 起請改用 `java.time`。

第二，四個主角：`LocalDate` 管日期、`LocalTime` 管時間、`LocalDateTime` 管日期加時間、`ZonedDateTime` 管帶時區的完整時間。根據你的需求選對類別，不要全部都用 LocalDateTime 就解決了事。

第三，`DateTimeFormatter.ofPattern()` 取代 `SimpleDateFormat`，執行緒安全，可以放心共用。

第四，`Duration` 計精確的時間差（秒、分鐘、小時），`Period` 計日期差（年、月、日）。

第五，新舊 API 互轉的橋梁是 `Instant`，記住這個橋梁，遇到舊系統互接的時候就不慌了。

好，今天的課到這裡。有問題嗎？

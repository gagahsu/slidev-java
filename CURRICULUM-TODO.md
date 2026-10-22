# 教材完善清單

記錄各章教材（投影片、講稿、練習題、範例程式）的缺漏與待改項目，目標是讓整個專案的教材內容完整、前後一致。

## 使用方式

- 每章一個區塊，依章節編號排列（編號依 `work` 分支，Ch01–25）
- 每項用核取方塊標示狀態：`- [ ]` 待處理、`- [x]` 已完成（後面註明 commit）
- 優先順序：**P1** 練習題或後續內容依賴、不補會卡住學生；**P2** 常用、建議補；**P3** 進階、可選
- 新增頁面時同步撰寫講稿（依 `slide-narrator` 規範），完成後勾選

## 通用檢查清單

每章修改或新增內容時，對照以下項目：

- [ ] 練習題與解答用到的 API、語法，正文都有教過
- [ ] 解答程式碼可直接編譯（含必要的 `import`，尤其是不在 `java.time`、`java.util` 主套件下的類別）
- [ ] 範例中宣告的變數都有用到（沒有建了卻沒示範的物件）
- [ ] 改寫講稿時，被刪掉的句子不是後面練習題的前置知識
- [ ] 拆頁後每頁都有自己的講稿，且頁與頁之間有銜接句

---

## Ch11 日期與時間（`ch11-datetime.md`）

> 2026-10-21 盤點。對照 Java 8+ 完整 `java.time` API。

### P1：練習題用到、正文沒教

- [ ] **`ChronoUnit`**：練習一需求與解答使用 `ChronoUnit.DAYS.between()`，正文從未介紹
  - 歷史：`c74f09e` 曾在 Period 頁講稿口頭帶過，`2be43ac` 改寫講稿時被移除
  - 建議：在「Period：計算日期差」之後新增一頁「ChronoUnit：計算總差距」，含 `import java.time.temporal.ChronoUnit`、`DAYS` / `MONTHS` / `MINUTES.between()`，並與 Period（23 年 11 月 23 天）對照總天數；附 Period / Duration / ChronoUnit 選用對照表
- [ ] **練習一需求第 4 點**：寫「用 `Period` 或 `ChronoUnit.DAYS.between()`」，但 Period 無法算出總天數，會誤導學生
  - 建議：改成 `ChronoUnit.DAYS.between()` 或 `Duration.between(today.atStartOfDay(), birthday.atStartOfDay()).toDays()`
- [ ] **練習一解答缺 import**：補上 `import java.time.temporal.ChronoUnit;`
- [ ] **`Locale` 與 `EEEE`**：練習二任務 A 使用 `Locale.TRADITIONAL_CHINESE` 與 `EEEE`，正文格式符號表只有 `E`，未提 Locale
  - 建議：在 DateTimeFormatter 格式符號頁補 `EEEE`（全名）與 `ofPattern(pattern, Locale)` 用法
- [ ] **`getMonth()` vs `getMonthValue()`**：練習二任務 B 用 `getMonth()`，回傳 `Month` enum（印出 `MAY`，不是 `5`），正文未介紹
  - 建議：在 LocalDate API 頁補 `getMonthValue()` 與 `Month` enum，練習二提示註明兩者差別
- [ ] **`DateTimeParseException`**：正文只講舊版 `parse` 拋 `ParseException`，未說明新版格式不符時拋 `DateTimeParseException`（unchecked）
  - 建議：在「DateTimeFormatter — 範例」頁的講稿或提示框補充

### P2：常用，建議補

- [ ] **`Instant` 本身**：目前只當新舊轉換橋梁，未介紹 `Instant.now()`、`toEpochMilli()`、`Instant.ofEpochMilli()`（時間戳記、存資料庫常用）
- [ ] **時區轉換 `withZoneSameInstant()`**：「ZonedDateTime — 範例」建立了 `paris` 卻沒使用，缺「台北 10 點是巴黎幾點」的示範
- [ ] **`withXxx()` 與 `TemporalAdjusters`**：`withDayOfMonth()`、`firstDayOfMonth()`、`lastDayOfMonth()`、`next(DayOfWeek.MONDAY)`
- [ ] **比較與查詢**：`isEqual()`、`isLeapYear()`、`lengthOfMonth()`、`lengthOfYear()`、`getDayOfYear()`
- [ ] **直接建立時間長度**：`Duration.ofMinutes()` / `ofHours()`、`Period.ofDays()` / `ofMonths()`，以及 `plus(duration)`
- [ ] **預設格式常數**：`DateTimeFormatter.ISO_LOCAL_DATE` 等；說明 `LocalDate.parse("2024-01-15")` 不需 formatter 的原因
- [ ] **`toEpochDay()`**：計算天數差的另一種寫法（可併入 ChronoUnit 頁當補充）

### P3：進階，可選

- [ ] `MonthDay` / `YearMonth` / `Year`：只有月日的資料，可用 `MonthDay` 重寫練習一的生日
- [ ] `MinguoDate`：民國年（`java.time.chrono`），貼近台灣學生
- [ ] `OffsetDateTime` / `ZoneOffset`：API 與資料庫常見的 `+08:00` 格式
- [ ] `Calendar` / `GregorianCalendar`：比較表有提及但未教，舊系統常見
- [ ] `java.sql.Date` / `Timestamp` 轉換：建議放 JDBC 相關章節，而非 Ch11

---

## Ch12 字元與字串（`ch12-char-string.md`）

> 2026-10-21 盤點練習題（練習一～五）與 `code/ch12/` 解答程式。

### P1：練習題用到、正文沒教

- [x] **練習三沒有解題提示頁**：其他練習都有「解題提示」頁，練習三只有任務頁，解法只出現在【講師】行（2026-10-21 已修）
- [x] **練習三解答用到 Ch24 才教的 Map**：`code/ch12/LetterFrequency.java` 挑戰題用 `TreeMap`、`Map.Entry`、`getOrDefault`；講師提示卻是「陣列以字元編碼為索引計數」，兩者不一致（2026-10-21 已修）
  - 建議：補提示頁，挑戰題改用 `int[] count = new int[128]; count[c]++` 的陣列解法（需先說明 char 可當 int 索引），解答程式同步改寫；Map 解法留到 Ch24 當回顧
- [x] **StringBuilder 轉回 String（`toString()`）沒教**：練習四講稿說「反轉後再轉回字串」，但正文沒有任何頁示範 `sb.toString()`（2026-10-21 已修）
  - 常見錯誤：`sb.reverse().equals(str)` 永遠是 false（StringBuilder 沒覆寫 `equals`，比的是位址）
  - 建議：在「內容修訂方法 (二)」或「StringBuffer vs StringBuilder」頁補 `toString()`，練習四提示頁補上這個陷阱
- [x] **char 轉數字（`charAt(i) - '0'`）沒教**：`code/ch12/PlusOne.java` 用它把字元拆回數字，Ch01–12 都沒教過 char 算術（2026-10-21 已修）
- [x] **`Long.parseLong` 沒教**：`PlusOne.java` 用 `Long.parseLong`；Ch04 只教了 `Integer.parseInt`（2026-10-21 已修）
  - 建議（兩項擇一）：解答改用已教過的 `split("")` + `Integer.parseInt`；或在提示頁補一行 char 算術與 `Long.parseLong` 說明

### P2：建議補

- [x] **`indexOf(String str, int from)` 沒列在表上**：練習一的關鍵步驟要用它，但「子字串的搜尋」表只列了 `indexOf(String str)`，帶起點的版本只出現在字元搜尋表（2026-10-21 已修）
  - 建議：在子字串搜尋表加一列 `indexOf(String str, int from)`
- [x] **練習一解答的寫法對初學者偏難**：`while ((index = text.indexOf(target, index)) != -1)` 在條件式裡同時賦值，正文沒出現過（2026-10-21 已修）
  - 建議：解答改成先 `indexOf` 再 `while (index != -1)`，在迴圈尾端再找下一個

### 已確認沒問題

- 練習二：`lastIndexOf(String)`、`substring`、字串串接都有教
- 練習四：`Scanner` 在 Ch02、Ch04、Ch06 有教

---

## Ch13 正規表達式（`ch13-regex.md`）

> 2026-10-21 盤點練習題（練習一～三）與 `code/ch13/` 解答程式，regex 行為已用 JDK 實測。

### P1：練習題用到、正文沒教

- [x] **中括號內特殊字元規則沒教**：練習三提示與解答用 `[\\w.+-]`，依賴「`.`、`+` 在 `[]` 內是字面值、`-` 放最後是字面值」，正文只教 `.` 要寫 `\\.`，學生無從得知（2026-10-21 已修）
  - 陷阱：`-` 放中間（如 `[a-z.-+]`）會拋 `PatternSyntaxException: Illegal character range`
  - 也沒示範 `\\w` 可以寫進 `[]` 內與其他字元組合
  - 建議：在「`[]` — 範例」或「跳脫符號」頁補一個提示框：`[]` 內大部分符號不必跳脫，`-` 放頭尾、`^` 不放開頭；舉 `[\\w.+-]` 為例

### P2：建議補

- [x] **練習三規格與解答不一致**：需求寫帳號／網域是「英數字」，但 `\\w` 含底線，`a_b@c_d.com` 會判為合法（2026-10-21 已修）
  - 建議：需求改成「英數字、底線…」，或提示註明 `\\w` 多了底線

### P3：可選

- [x] **練習三分組寫法不一致**：提示頁 `(\.[\w-]{2,})+`，解答頁與 `EmailValidator.java` 為 `(\\.([\\w-]{2,}))+`，多一層括號；可統一，或改用已教的 `(?:...)`（2026-10-21 已修）
- [x] **練習三提示頁用單反斜線**：練習一、二提示都寫 Java 字串形式（`\\d`），練習三寫 regex 形式（`\w`）再加警告框；可統一成雙反斜線（2026-10-21 已修）

### 已確認沒問題

- 練習一：`\\d`、`{n}`、`{n,m}`、`\\(` 跳脫、`|` 都有教；「跳脫符號 — 範例」頁幾乎就是格式 2 的寫法
- 練習二：`[A-Za-z]`、`[12]`、`\\d{8}`、`(?i)`、範圍都有教；`(?i)[cgi-z]` 實測大小寫皆正確，另一解 `[A-Za-z&&[^ABDEFHabdefh]]` 用已教的交集也能完成
- `Scanner` 在 Ch02、Ch04、Ch06 有教

---

## Ch14 繼承與多形（`ch14-inheritance.md`）

> 2026-10-22 盤點練習（練習 1 繼承、練習 2 多形）與 `code/ch14/` 解答程式，解答已用 javac 編譯通過。

### P1：練習題用到、正文沒教

- [x] **解答用 `static class` 巢狀類別**：`InheritanceDemo.java`、`PolymorphismDemo.java` 把類別寫成 `public class` 內的 `static class`，正文只在「巢狀類別的種類」表格提到靜態巢狀類別，沒有範例；且 Ch08、Ch09 解答都用同檔多個頂層 `class`（2026-10-22 已修）
  - 建議：解答改成頂層 `class Father { }`、`class Son extends Father { }`…，與 Ch08/09 一致

### P2：建議補

- [x] **練習 2 需求沒提建構方法**：`new Dog("旺財")` 需要 `Animal` 有 `name` 欄位與 `Animal(String)` 建構方法、`Dog`/`Bird` 呼叫 `super(name)`；但多形小節的 Animal/Dog/Bird 範例都沒有建構方法與 `name`，學生要自己把繼承小節的 Animal/Dog 範例合併過來（2026-10-22 已修）
  - 建議：任務說明補一行「`Animal` 有 `protected String name`，子類別建構方法呼叫 `super(name)`」，`move()` 輸出含 `name`（如 `旺財 跑步`）
- [x] **練習 1 沒寫 `main` 要做什麼、`playBall()`/`shopping()` 印什麼**（2026-10-22 已修）
  - 建議：補「在 `main` 建立三個物件，各自呼叫 `walk()` 與特有方法」，並給預期輸出（如 `Tom is walking~~~`），也順便消除 `name is walking!!!` 中 `name` 是字面還是變數的歧義

### P3：可選

- [x] **提示與解答迴圈寫法不一致**：提示寫 `for` + `animals[i].move()`，解答用 for-each（兩者 Ch06/Ch07 都教過）；可統一（2026-10-22 已修）
- [x] **解答輸出含 emoji**（🐕、🐦）：Windows 主控台實測印成 `?`，建議拿掉（2026-10-22 已修）
- [x] **練習 2 解答多做 `instanceof` 判斷**：正文有教，但需求沒要求；可在任務說明加成延伸題，或從解答移除（2026-10-22 已修）

### 已確認沒問題

- `extends`、`protected`、`super(name)`、`@Override`、子類別新增方法：繼承小節「繼承範例 — Animal 與 Dog」幾乎就是練習 1 的骨架
- `Animal[]` 陣列初始化 `{ ... }`、`for` / for-each：Ch07、Ch06 有教
- 父類別陣列存子類別物件、呼叫 Override 方法：「執行時期多形 — 用法」頁有教（向上轉型）
- 多個類別寫在同一檔、只有一個 `public class`：Ch02 有教

---

## Ch15 Object 類別（`ch15-object-class.md`）

> 2026-10-22 盤點練習（Employee hashCode）與 `code/ch15/EmployeeHashCode.java`，解答已用 javac 編譯執行通過。

### P1：練習題用到、正文沒教

- [x] **解答 `equals()` 用否定型 pattern `if (!(obj instanceof Employee other)) return false;`**：正文「現代化的 equals() 實作」與反例頁都只示範正向 `if (o instanceof User other) { ... }`；否定型下 `other` 在 if 之後仍可用的作用域規則沒教
  - 建議：解答改成與投影片相同的正向寫法
- [x] **解答用 `static class` 巢狀類別**（同 Ch14 問題）：`EmployeeDefault`、`Employee` 寫在 `public class EmployeeHashCode` 內
  - 建議：改成同檔頂層 `class`，與 Ch08/09 一致

### P2：建議補

> P1、P2 已於 2026-10-22 完成（題目補建構方法與延伸題 equals；解答改頂層 class、正向 pattern、修註解、移除 toString），尚未 commit

- [x] **任務說明的類別骨架沒有建構方法**，但提示寫 `new Employee(...)`；學生照骨架寫會無法帶參數建立物件
  - 建議：骨架補 `Employee(String name, int age, String country)`，或任務加一行「加上三個參數的建構方法」（Ch08/09 有教）
- [x] **解答註解與投影片矛盾**：解答寫「Object 預設 hashCode 基於記憶體位址」，投影片「什麼是哈希碼？」明講「不保證等於記憶體位址」
  - 建議：註解改「依物件身分產生，不同物件通常不同」
- [x] **解答超出需求**：多了 `equals()`、`toString()`，題目只要求 hashCode（equals 只出現在講稿【講師】延伸）
  - 建議：任務加第 4 點延伸「一併 Override `equals()` 並驗證 `emp1.equals(emp2)`」，讓練習也涵蓋本章重點 equals/hashCode 合約；`toString()` 可移除或列為延伸

### P3：可選

- [ ] **解答步驟編號與題目不對齊**：題目 1/2/3，解答「步驟一（預設）/步驟二（Override）」
- [ ] **兩個類別名稱**：解答用 `EmployeeDefault` + `Employee` 同時呈現前後對照，題目只有一個 `Employee`；可在提示說明「先不 Override 跑一次，再加上 Override 跑第二次」

### 已確認沒問題

- 呼叫 `hashCode()` 並印出：「hashCode() — 初探」頁有教
- `Objects.hash(name, age, country)` + `import java.util.Objects`：「現代化的 hashCode() — 範例」頁、提示頁都有
- `Objects.equals` 比較欄位：Objects 工具類別、equals 實作頁有教
- 建構方法 `this.name = name`：Ch08/09 有教；`"...".formatted()`：Ch12 有教

---

## Ch16 抽象類別（`ch16-abstract-class.md`）

> 2026-10-22 盤點練習 1（Shape 面積周長）、練習 2（MyMath）與 `code/ch16/`，兩份解答已用 javac 編譯執行，輸出與投影片預期輸出一致。

### P1：練習題用到、正文沒教

（無）

### P3：可選

- [ ] **投影片沒有帶參數的抽象方法範例**：正文的抽象方法都是無參數（`draw()`、`run()`、`area()`、`start()`），練習 2 要寫 `abstract int add(int n1, int n2);`；只有「抽象方法的特性 — 範例」的提示框提到參數要一致。語法和一般方法相同，提示頁也直接給了宣告，不致卡住
- [ ] **練習 1 沒用到向上轉型**：解答用 `Rectangle rect = new Rectangle(2, 3)`，沒用本章的 `Shape s = new Rectangle(2, 3)`；可在提示加一點「用 `Shape` 型態宣告變數」，或解答改用 `Shape[]` 跑迴圈

### 已確認沒問題

- `abstract class`、`abstract` 方法以 `;` 結尾、子類別 `@Override`：本章「抽象方法的特性」頁有教
- 抽象方法回傳 `double`：「密封抽象類別」頁 `public abstract double area();`
- 普通方法 + 抽象方法混用（練習 2 `output()`）：「抽象類別可以有兩種方法」頁
- 子類別帶參數建構方法、`this.x = x`、`private` 欄位：Ch09 有教
- `Math.PI`：Ch10 有教
- Upcasting `MyMath obj = new MyTest();`：「使用 Upcasting 宣告抽象類別的物件」頁
- 圓面積與周長數值相同（r = 2）：提示頁講稿有說明，`Math.PI * r * r` 與 `2 * Math.PI * r` 輸出完全相同

---

## Ch17 介面（`ch17-interface.md`）

> 2026-10-22 盤點練習（設計介面階層）與 `code/ch17/Exercise_InterfaceHierarchy.java`，解答已用 javac 編譯執行，輸出與投影片預期輸出一致。

### P1：練習題用到、正文沒教

（無）

### P3：可選

- [x] **解答開頭註解引用的「投影片提示」不存在**（2026-10-22 已改，尚未 commit）：註解引用「名字取太好，也是會撞衫的。」，投影片與講稿都沒有這句（現行投影片是 ⚠️ 注意框的說法）
  - 建議：註解改成與投影片注意框一致，或只留「避免與 `java.lang.Runnable` 撞名」
- [ ] **練習沒用到介面型態變數**：解答用 `Human human = new Human()`，沒用「介面的實作規則」頁示範的 `Flyable bird = new Bird()` 寫法；可在提示加「用 `MyRunnable` 型態宣告三個變數」，順便讓學生看到 `Person` 也是 `MyRunnable`

### 已確認沒問題

- `interface` 宣告、方法預設 `public abstract`：「介面的語法」頁
- `implements` + 覆寫必須 `public`、`@Override`：「介面的實作規則」頁
- `Person extends Human` 再覆寫 `run()`：Ch14 繼承與覆寫；覆寫不能縮小存取權限，`public` 必要（Ch14 有教）
- 避開 `Runnable` 命名：任務頁注意框有說明

---

## Ch18 包裝類別（`ch18-wrapper.md`）

> 2026-10-22 盤點練習一（Scanner 計算）、練習二（isInteger）、練習三（進位轉換）與 `code/ch18/`，三份解答已用 javac 編譯執行，輸出符合任務說明。

### P1：練習題用到、正文沒教

（無：練習二用到的 `try-catch` 到 Ch20 才教，但提示頁直接給出完整程式並說明「先照著用」，學生不會卡住；問題見 P2）

### P2：建議補

> P2、P3 已於 2026-10-22 完成，尚未 commit

- [x] **練習二的提示就是完整解答**：`try { ... } catch (NumberFormatException e)` 要到 Ch20 才教，學生只能照抄，練不到東西
  - 建議 A：提示改成 Ch13 教過的正規表達式 `s.matches("-?\\d+")`（`matches`、`\d`、`?`、`+` 都教過），學生能自己寫；`try-catch` 版本留在講稿當「Ch20 會學到另一種寫法」
  - 建議 B：保留現狀，但任務頁明講「本題示範 Ch20 的例外處理，先照抄體驗」
  - 已採建議 A：提示改成 regex 填空（不直接給答案），講稿揭曉答案並預告 Ch20 寫法；解答同步改寫

### P3：可選

- [x] **練習一「哪個數比較大」與提示 `Integer.max()` 不對齊**：`max` 只回傳較大的值，不會說出是哪一個；解答印的是「較大值：7」
  - 建議：任務改成「兩數中的較大值」，或提示改用 `Integer.compare()` / `if` 判斷
- [x] **練習一沒有預期輸出**：練習三有輸入/輸出範例，練習一沒有，學生不知道輸出格式
- [x] **練習一程式骨架缺 `import java.util.Scanner;`**：Ch04 有教，但骨架直接貼上無法編譯

### 已確認沒問題

- `Integer.parseInt()`：「Integer 型態轉換方法」「字串轉整數 — 範例」頁；`NumberFormatException` 講稿與 Ch07 有提
- `Integer.max()`：「數值比較與工具方法」頁
- `toBinaryString` / `toOctalString` / `toHexString`：「進位轉換方法」頁，範例正好用 255
- `Scanner.nextLine()`、`import java.util.Scanner`：Ch04 有教
- 自訂 `static boolean` 方法：Ch05、Ch07 範例有類似寫法

---

## Ch19 套件（`ch19-package.md`）

> 2026-10-22 盤點練習（學生管理系統套件結構）與 `code/ch19/`，解答在 `code/ch19/` 下以 `javac com/school/Main.java`、`java com.school.Main` 編譯執行通過。

> P1–P3 已於 2026-10-22 完成（新增「編譯與執行有套件的程式」頁、任務說明具體化、新增「預期輸出與延伸」頁、解答補 `setGrade()`、README 簡化；順便修 `protected` 頁爆版），尚未 commit

### P1：練習題用到、正文沒教

- [x] **沒教怎麼編譯、執行有套件的程式**：學生到目前只會在同一資料夾 `javac X.java` / `java X`（Ch02）。本章只說「路徑要對應，否則找不到類別」，沒有示範指令；學生最常見的錯誤是進到 `com/school/` 裡執行 `java Main`，得到「找不到或無法載入主要類別」（2026-10-22 已修）
  - 建議：在「套件與目錄結構的對應」後加一頁：從來源根目錄（`src/`）執行 `javac com/school/Main.java`（會一併編譯 import 到的類別）、`java com.school.Main`（用全名、`.` 不是 `/`），並列出在錯誤目錄執行的錯誤訊息
- [x] **`code/ch19/README.txt` 用了沒教的 `-cp .`**：在根目錄執行時 `-cp .` 是預設值，可直接拿掉，三行 `javac` 也可合併成 `javac com/school/Main.java`；若保留，正文需說明 classpath（2026-10-22 已修）

### P2：建議補

- [x] **解答頁與 `code/ch19/Student.java` 不一致**：程式碼有 `setGrade()`，投影片「練習：解題提示」少了這行；題目寫「提供 getter/setter」，學生不確定要哪幾個（2026-10-22 已修）
  - 建議：投影片補 `setGrade()`，任務說明具體列出 `getName/setName/getGrade/setGrade`（`studentId` 是 `public` 可不寫）
- [x] **任務說明沒給建構方法參數順序與預期輸出**：`Main` 要 `new Student("S001", "炭治郎", 2)`，`printInfo` 的格式也沒規定（2026-10-22 已修）
  - 建議：補「建構方法參數順序 `(studentId, name, grade)`」與預期輸出三行（學號／姓名／年級）

### P3：可選

- [x] **`protected grade` 的效果沒讓學生自己驗證**：「`StudentService` 不能直接用 `s.grade`」只出現在講稿；可加延伸題：在 `StudentService` 試寫 `s.grade` 看編譯錯誤，或在別的套件寫 `Student` 的子類別存取 `grade`（2026-10-22 已修）
- [x] **任務第 3 點「建立 `com.school.Main`」**：可改成「在 `com.school` 套件建立 `Main` 類別」，避免學生以為 `Main` 是套件名稱（2026-10-22 已修）

### 已確認沒問題

- `package` 宣告、套件對應目錄：「`package` 宣告」「套件與目錄結構的對應」頁有教
- `import` 其他套件的類別：「`import` 語法」頁範例與練習幾乎相同（`StudentService` 匯入 `Student`）
- `private` / `protected` / `public` 欄位與透過 getter 存取：存取控制修飾詞四頁與封裝範例有教
- 每個檔案一個 `public` 類別、檔名一致：「`public` — 完全開放」頁有教

---

## Ch25 Stream 與 Lambda（`ch25-stream-lambda.md`）

> 2026-10-22 盤點練習一（英雄名單 Stream）、練習二（成績統計）與 `code/ch25/`，兩份解答已用 javac 編譯執行。

### P1：練習題用到、正文沒教

（無）

### P2：建議補

> P2、P3 已於 2026-10-22 完成（兩題補預期輸出、練習一加 Unicode 排序提示與講稿說明、Primitive Stream 講稿改 OptionalDouble；解答 HeroStream 輸出改成與預期一致），尚未 commit

- [x] **練習一「依字典順序排序」中文結果違反直覺，且沒有預期輸出**：`sorted()` 對中文依 Unicode 編碼排序，實際輸出是「伊之助、時透無一郎、炭治郎、甘露寺、禰豆子」，不是注音或筆畫順序；Ch12「字典順序比較 (compareTo)」只用英文示範，全課程沒說明中文怎麼排。學生跑出來很可能以為自己寫錯
  - 建議：任務頁補預期輸出，並加一行提示「中文的自然順序是 Unicode 編碼順序，不是注音或筆畫」
  - 同樣問題：Ch24 練習「用 `Collections.sort()` 依字典順序排序」（尚未盤點）

### P3：可選

- [x] **練習二沒有預期輸出**：只在提示頁的 `printf` 看得出格式；可補「及格：6 人，最高：91，平均：79.8」
- [x] **講稿說 `average()` 回傳「Optional 型態」**（Primitive Stream 頁）：實際是 `OptionalDouble`，所以才用 `getAsDouble()` 而不是 `get()`；練習二同時用到 `Optional.get()` 和 `OptionalDouble.getAsDouble()`，學生可能混淆

### 已確認沒問題

- `List.of`、`filter`、`sorted()`、`Collectors.joining("、")`：中間操作頁、Collectors 常用工具頁（範例正好用頓號）
- `count()`、`max(Comparator.naturalOrder()).get()`：終端操作頁、Optional 頁
- `mapToInt(...).average().getAsDouble()`：Primitive Stream 頁；`Integer::intValue` 是方法參考第三種形式，`intValue()` Ch18 有教
- `import java.util.*; import java.util.stream.*;`：Stream 完整應用範例頁
- `printf("%d")`、`%.1f`：Ch02 有教

---

## 其他章節

（待盤點）

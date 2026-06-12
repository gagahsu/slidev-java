# 課程拆分規劃：基礎版 / 進階版

> 適用情境：4 週密集班（每週 5 天，共 20 天），每天約 7–8 小時（課程講授 + 課堂練習），學生無程式基礎。
> 目標：所有學生在 4 週內完整學過全部 25 章主題（基礎版內容），有餘力或興趣的學生可額外自學進階版內容。

---

## 一、拆分原則

1. **每章拆成「核心」與「進階／選讀」兩部分**：
   - 核心：基本語法、最常用 API、1–2 個課堂練習，確保學生對該主題有完整但精簡的認識。
   - 進階／選讀：冷門 API、JDK 9 以後新增的語法糖、設計模式、底層原理／效能細節，以及額外的延伸專題。
2. **三個主題整章列為自學進階**：多執行緒（ch21）、檔案 I/O（ch22）、ZIP 壓縮（ch23）。這三者對零基礎學生負擔較重，且不是寫出基本程式的必要條件，基礎班只需口頭介紹「這是什麼、用在哪裡」即可。
3. **正規表達式（ch13）保留在基礎課表中**，但僅教最常用的語法（字元類別、量詞、分組、`matches`/`split`/`replaceAll`），進階語法（反向引用、具名分組、環視斷言、Pattern/Matcher 類別細節等）列為自學。

---

## 二、4 週課表（基礎版，共 20 天）

### 第一週：基礎語法

| 天數 | 內容 |
| --- | --- |
| Day 1 | ch01（Java 簡介，輕量帶過）+ ch02（第一個 Java 程式）+ ch03（變數、8 種基本資料型態） |
| Day 2 | ch03（String 基本操作、常數、printf 基本格式）+ ch04（算術／比較／邏輯運算子） |
| Day 3 | ch04（型態轉換、Scanner 輸入）+ ch05（if / switch 傳統語法） |
| Day 4 | ch06（for / while / do-while、break / continue）+ 課堂練習 |
| Day 5 | ch07（一維、二維陣列、參照型態基本概念）+ 第一週複習 |

### 第二週：物件導向入門

| 天數 | 內容 |
| --- | --- |
| Day 6 | ch08（類別與物件、欄位與方法、方法多載、this） |
| Day 7 | ch09（建構子、封裝、getter/setter、存取修飾詞、static） |
| Day 8 | ch12（String 常用方法，內容量大，整天） |
| Day 9 | ch10（Math / Random 核心）+ ch11（LocalDate / LocalTime / LocalDateTime 核心） |
| Day 10 | ch14（繼承核心：extends、super、override、多型、向上／向下轉型） |

### 第三週：物件導向進階 + 例外處理

| 天數 | 內容 |
| --- | --- |
| Day 11 | ch15（equals / hashCode / toString 核心）+ ch16（抽象類別核心） |
| Day 12 | ch17（介面核心：語法、實作、多重介面） |
| Day 13 | ch18（包裝類別核心）+ ch19（套件與存取修飾詞核心） |
| Day 14 | ch20（例外核心：try-catch-finally、throw、throws、自訂例外基本） |
| Day 15 | ch24（集合框架核心：List / Set / Map 基本用法，整天） |

### 第四週：集合深化 + 現代化 API + 收尾

| 天數 | 內容 |
| --- | --- |
| Day 16 | ch24（續）：Collections 工具類別、常見走訪模式 + 課堂練習 |
| Day 17 | ch25（Lambda + Stream 核心：filter、map、collect） |
| Day 18 | ch13（正規表達式核心：字元類別、量詞、分組、matches/split/replaceAll） |
| Day 19 | 綜合專題（整合 OOP + 集合 + Stream，例如選課系統／成績管理系統） |
| Day 20 | 總複習 + Q&A + 進階自學內容導覽 + 緩衝時間（用於補進度） |

---

## 三、進階／選讀內容清單（自學）

### 整章列為自學
- **ch21 多執行緒（Thread）**：全章
- **ch22 檔案 I/O**：全章
- **ch23 ZIP 壓縮**：全章

### 各章節進階內容

| 章節 | 進階／選讀內容 |
| --- | --- |
| ch03 語言基礎 | printf 進階格式旗標細節 |
| ch04 運算子 | 位元運算子與位移運算、完整運算子優先順序表、進階位元操作練習 |
| ch05 流程控制 | Switch Expression、Pattern Matching for switch、Sealed Class 搭配 switch（JDK 14+ / 17） |
| ch06 迴圈 | 迴圈標籤（Label）；萊布尼茨／雞兔同籠／國王的麥粒三個專題保留 1 個作為核心範例，其餘列自學 |
| ch07 陣列 | 不規則陣列（Jagged Array）、命令列參數（`String[] args`）、垃圾回收細節、計算器專題 |
| ch08 類別與物件 | 遞迴與河內塔問題、匿名陣列 |
| ch09 建構子與封裝 | Singleton 設計模式 |
| ch10 Math/Random | 三角函數方法、Haversine 公式專題 |
| ch11 日期時間 | ZonedDateTime、Duration / Period、舊版 Date 類別與新舊轉換 |
| ch12 字元與字串 | StringBuffer / StringBuilder、Text Blocks、字串池記憶體細節、進階字串方法（transform、lines、StringUtils、getChars） |
| ch13 正規表達式 | 反向引用、具名分組、環視斷言、Pattern / Matcher 類別細節、MULTILINE / DOTALL 旗標、Predicate 整合、splitAsStream、動態取代結合 Stream |
| ch14 繼承 | Sealed Classes、Records、Pattern Matching for instanceof、巢狀類別與匿名內部類別、靜態／動態綁定 |
| ch15 Object 類別 | hashCode 合約細節、clone() 與 Cloneable、finalize() 廢棄說明、Records 與 Object 方法 |
| ch16 抽象類別 | Template Method 設計模式、Sealed 抽象類別 |
| ch17 介面 | default / static / private 方法（JDK 8 / 9）、鑽石問題、Sealed Interfaces |
| ch18 包裝類別 | 進位轉換方法、Number 抽象父類別 |
| ch19 套件 | Java 模組系統 JPMS（JDK 9+） |
| ch20 例外處理 | 使用 enum 自訂錯誤代碼、try-with-resources 進階用法 |
| ch24 集合框架 | LinkedList / TreeSet / TreeMap / LinkedHashMap 細節比較、Set 的聯集／交集／差集操作、不可變集合進階用法 |
| ch25 Stream/Lambda | 方法參考進階形式、flatMap、Primitive Stream（mapToInt 等）、takeWhile / dropWhile、teeing 收集器、Optional 詳細用法 |

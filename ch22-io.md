---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 輸入與輸出 (I/O)
routeAlias: ch22
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
  <h1 style="color: #1a5c5c; font-size: 3.8rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">輸入與輸出</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「讀寫檔案與串流：Java I/O 完整指南」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章的主題是輸入與輸出，英文簡稱 I/O。

到目前為止，程式裡的資料在程式結束之後就消失了。如果要保存使用者的設定、讀取一份成績表，或是備份一張照片，就需要把資料寫進檔案，或從檔案讀出來。

學完這一章，我們會理解 Java 用串流處理資料的方式，能夠讀寫二進位檔和文字檔，也能用 File 類別管理檔案和資料夾。
-->

---
layout: default
---

# 本章大綱

<div class="grid grid-cols-2 gap-4 mt-4">
<div>

**串流基礎**
- 認識串流 (Stream)
- InputStream / OutputStream 類別圖

**Byte I/O**
- FileInputStream / FileOutputStream
- BufferedInputStream / BufferedOutputStream

**字元 I/O**
- Writer 和 Reader 類別層次
- FileReader / FileWriter
- BufferedReader / BufferedWriter

</div>
<div>

**系統 I/O**
- System 類別
- PrintStream 類別
- Console 類別

**檔案管理**
- File 類別

**練習與 Q&A**

</div>
</div>

<!--
這一章分成五個部分。

第一部分是串流的基礎觀念。第二部分是以位元組為單位的 Byte I/O，適合處理圖片這類二進位檔。第三部分是以字元為單位的字元 I/O，適合處理文字檔。

第四部分是系統 I/O，包括 System、PrintStream 和 Console 類別。第五部分是用 File 類別管理檔案。最後有三題練習。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 串流基礎

<!--
我們先來認識 Java I/O 最核心的觀念：串流。
-->

---

# 認識串流 (Stream)

串流（Stream）是資料從來源（Source）流向目的地（Destination）的抽象通道：

```
來源                     目的地
[磁碟/鍵盤] → InputStream  → [程式]
[程式]      → OutputStream → [磁碟/螢幕]
```

**串流的兩大家族：**

| 家族 | 基底類別 | 單位 | 適用場景 |
|------|---------|------|---------|
| Byte Stream | `InputStream` / `OutputStream` | 8-bit byte | 二進位檔（圖片、音訊） |
| Char Stream | `Reader` / `Writer` | 16-bit char | 文字檔 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>原則：</b> 文字檔用 Reader/Writer；二進位檔（圖片、音訊）用 InputStream/OutputStream。
</div>

<!--
串流，英文是 Stream，是資料從來源流向目的地的一條通道。

就像水管一樣，資料從一端流進，從另一端流出。從磁碟或鍵盤把資料讀進程式，用的是輸入串流 InputStream；從程式把資料寫到磁碟或螢幕，用的是輸出串流 OutputStream。

串流分成兩大家族。Byte 串流以 8 位元的位元組為單位，基底類別是 InputStream 和 OutputStream，適合圖片、音訊這類二進位檔。字元串流以 16 位元的字元為單位，基底類別是 Reader 和 Writer，適合文字檔。

所以原則是：「文字檔用 Reader 和 Writer，二進位檔用 InputStream 和 OutputStream。」
-->

---

# InputStream / OutputStream 類別圖

<div class="grid grid-cols-2 gap-4 mt-2">
<div>

**InputStream 繼承體系**

```
InputStream (abstract)
├── FileInputStream        ← 讀檔
├── ByteArrayInputStream   ← 讀 byte[]
├── FilterInputStream
│   ├── BufferedInputStream ← 加緩衝
│   └── DataInputStream    ← 讀基本型別
└── ObjectInputStream      ← 反序列化

```

</div>
<div>

**OutputStream 繼承體系**

```
OutputStream (abstract)
├── FileOutputStream       ← 寫檔
├── ByteArrayOutputStream  ← 寫 byte[]
├── FilterOutputStream
│   ├── BufferedOutputStream ← 加緩衝
│   ├── DataOutputStream   ← 寫基本型別
│   └── PrintStream    ← System.out
└── ObjectOutputStream     ← 序列化
```

</div>
</div>

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>裝飾者模式（Decorator Pattern）：</b> Java I/O 採用包裝設計，例如 <code>BufferedInputStream</code> 包裝 <code>FileInputStream</code>，為其加上緩衝功能，不改變原有介面。
</div>

<!--
這兩張圖是 InputStream 和 OutputStream 的繼承體系。

InputStream 下面，有讀取檔案的 FileInputStream、讀取位元組陣列的 ByteArrayInputStream，還有加上緩衝功能的 BufferedInputStream 等等。OutputStream 也有對應的類別，其中 PrintStream，就是我們每天在用的 System.out。

Java I/O 採用了一種叫做裝飾者模式的設計。例如 BufferedInputStream 可以包住 FileInputStream，幫它加上緩衝的功能，但使用方式完全不變。就像幫水管加上一個濾水器，水還是從同一個地方流出來，只是多了一層處理。
-->

---

# InputStream 核心方法

| 方法 | 說明 |
|------|------|
| `int read()` | 讀取一個 byte，回傳 0~255；串流結束回傳 `-1` |
| `int read(byte[] b)` | 讀入最多 `b.length` 個 byte，回傳實際讀取數 |
| `int read(byte[] b, int off, int len)` | 從 `off` 位置讀入最多 `len` 個 byte |
| `long skip(long n)` | 跳過 `n` 個 byte |
| `int available()` | 回傳可立即讀取的 byte 估計數 |
| `void close()` | 關閉串流，釋放資源 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>read()</code> 回傳 <code>-1</code> 代表串流結束（EOF）。這是迴圈終止的信號，不是錯誤。
</div>

<!--
InputStream 有幾個核心方法。

read 一次讀取一個位元組，回傳 0 到 255 的數字；如果已經讀到結尾，就回傳負 1。也可以傳入一個位元組陣列，一次讀取一批資料，回傳實際讀到的數量。另外還有 skip 跳過幾個位元組、available 估計還有多少可以讀，以及 close 關閉串流。

要記住：read 回傳負 1，代表資料已經讀完了，這是迴圈結束的信號，不是錯誤。
-->

---

# OutputStream 核心方法

| 方法 | 說明 |
|------|------|
| `void write(int b)` | 寫出一個 byte（只用低 8 位） |
| `void write(byte[] b)` | 寫出整個 byte 陣列 |
| `void write(byte[] b, int off, int len)` | 寫出陣列中指定範圍的 bytes |
| `void flush()` | 強制將緩衝區資料寫出 |
| `void close()` | 關閉串流（先 flush） |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <code>flush()</code> 很重要：資料可能暫存在快取中。呼叫 <code>flush()</code> 才能確保資料立刻寫入目的地。<code>close()</code> 內部會自動呼叫 <code>flush()</code>。
</div>

<!--
OutputStream 的核心方法，則是用來寫出資料的。

write 可以寫出一個位元組、整個位元組陣列，或是陣列中指定範圍的部分。flush 會強制把緩衝區裡的資料寫出去，close 則會關閉串流。

flush 很重要。資料寫出時，可能會先暫存在緩衝區裡，還沒有真正寫進檔案。呼叫 flush，才能確保資料立刻寫到目的地。好消息是，close 在關閉之前，會自動先呼叫 flush。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Byte I/O

<!--
了解了串流的基本觀念，我們先來看以位元組為單位的 Byte I/O。
-->

---

# FileInputStream — 讀取檔案

最簡單的 byte 讀檔範例：

```java
try (FileInputStream fis = new FileInputStream("data.bin")) {
    int b;
    while ((b = fis.read()) != -1) {
        System.out.print((char) b);
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>try-with-resources：</b> 宣告在 <code>try(...)</code> 內的資源會自動呼叫 <code>close()</code>，不須手動關閉，避免資源洩漏。
</div>

| 建構子 | 說明 |
|--------|------|
| `FileInputStream(String name)` | 以路徑字串開啟 |
| `FileInputStream(File file)` | 以 File 物件開啟 |

<!--
用 FileInputStream 讀取檔案，最簡單的寫法是這樣的。

把 FileInputStream 宣告在 try 的括號裡，這是例外處理那一章學過的 try-with-resources，結束時會自動關閉檔案。

接著用 while 迴圈，每次 read 一個位元組，存進 b，只要不是負 1，就繼續讀，並把它轉成字元印出來。這種把讀取和判斷寫在同一個條件裡的寫法，是 I/O 程式最常見的模式。

建構子可以傳入檔案路徑的字串，或是一個 File 物件。
-->

---

# FileInputStream — 批次讀取

使用 byte 陣列批次讀取，效率更高：

```java
try (FileInputStream fis = new FileInputStream("photo.jpg")) {
    byte[] buffer = new byte[4096];
    int bytesRead;
    while ((bytesRead = fis.read(buffer)) != -1) {
        System.out.println("讀取了 " + bytesRead + " bytes");
        // 處理 buffer[0..bytesRead-1]
    }
}
```

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b> <code>read(byte[])</code> 回傳的是<b>實際</b>讀入的 byte 數，不一定等於陣列長度。最後一次讀取可能不足 4096 bytes。
</div>

<!--
一次只讀一個位元組，效率很差。實務上，會用一個位元組陣列，一次讀取一批。

看範例，準備一個 4096 的陣列當作緩衝區，每次 read 會把資料填進陣列，並回傳實際讀到幾個位元組。

要注意，回傳的數量不一定等於陣列的長度。特別是最後一次，檔案剩下的資料可能不到 4096 個，所以處理資料時，只能使用陣列前面實際讀到的那一段。
-->

---

# FileOutputStream — 寫入檔案

| 建構子 | 說明 |
|--------|------|
| `FileOutputStream(String name)` | 覆寫模式（預設） |
| `FileOutputStream(String name, boolean append)` | `append=true` 為附加模式 |
| `FileOutputStream(File file)` | 以 File 物件開啟 |

```java
byte[] data = "Hello, Java I/O!".getBytes();
try (FileOutputStream fos = new FileOutputStream("out.txt")) {
    fos.write(data);
}
```

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>附加模式：</b> <code>new FileOutputStream("log.txt", true)</code> 不會清空既有內容，適合寫 log 檔。
</div>

<!--
寫入檔案用 FileOutputStream。

預設是覆寫模式，會清空檔案原本的內容。如果第二個參數傳入 true，就變成附加模式，新的資料會接在原本內容的後面，很適合用來寫記錄檔。

看範例，先把字串用 getBytes 轉成位元組陣列，再用 write 一次寫進 out.txt。
-->

---

# BufferedInputStream / BufferedOutputStream

**為什麼需要緩衝？**

| 方式 | 每次讀寫 | 系統呼叫次數（讀 1MB） |
|------|---------|----------------------|
| 無緩衝 | 1 byte | ~1,000,000 次 |
| 有緩衝（8KB） | 8192 bytes | ~128 次 |

緩衝區將多次小型 I/O 合併為少量大型 I/O，大幅減少系統呼叫。

```java
try (BufferedInputStream bis =
        new BufferedInputStream(new FileInputStream("big.dat"))) {
    byte[] buf = new byte[1024];
    int n;
    while ((n = bis.read(buf)) != -1) { /* 處理 */ }
}
```

<!--
為什麼需要緩衝？看這張表就很清楚了。

如果每次只讀寫一個位元組，讀取 1MB 的檔案，大約需要一百萬次系統呼叫。加上 8KB 的緩衝區之後，只需要大約 128 次。每一次系統呼叫都有成本，次數少了，速度就快很多。

就像搬家，一次搬一個杯子要跑一百萬趟，用箱子裝好再搬，只要跑一百多趟。

使用的方式，就是用 BufferedInputStream 包住 FileInputStream，其他的寫法完全不變。
-->

---

# BufferedOutputStream 範例

```java
try (BufferedOutputStream bos =
        new BufferedOutputStream(new FileOutputStream("out.dat"))) {
    for (int i = 0; i < 10000; i++) {
        bos.write(i % 256);
    }
}
```

| 建構子 | 說明 |
|--------|------|
| `BufferedInputStream(InputStream in)` / `BufferedOutputStream(OutputStream out)` | 預設緩衝 8192 bytes |
| `BufferedInputStream(in, int size)` / `BufferedOutputStream(out, int size)` | 自訂緩衝大小 |

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>習慣：</b> 凡是讀寫檔案，<b>幾乎都應加上 Buffered 包裝</b>，除非是已知單次大塊傳輸。
</div>

<!--
寫入也是一樣，用 BufferedOutputStream 包住 FileOutputStream。

看範例，迴圈寫出一萬個位元組，雖然每次只寫一個，但它們會先累積在緩衝區，滿了才一次寫進檔案。

預設的緩衝大小是 8192 個位元組，也可以自己指定。所以養成一個習慣：讀寫檔案時，幾乎都應該加上 Buffered 的包裝。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 字元 I/O

<!--
接下來是以字元為單位的字元 I/O，處理文字檔時，我們用的是它們。
-->

---

# Reader / Writer 類別層次

<div class="grid grid-cols-2 gap-4 mt-2">
<div>

**Reader 繼承體系**

```
Reader (abstract)
├── InputStreamReader ← byte→char
│   └── FileReader    ← 讀文字檔
├── BufferedReader    ← 加緩衝
├── StringReader      ← 讀 String
└── CharArrayReader   ← 讀 char[]
```

</div>
<div>

**Writer 繼承體系**

```
Writer (abstract)
├── OutputStreamWriter ← char→byte
│   └── FileWriter     ← 寫文字檔
├── BufferedWriter     ← 加緩衝
├── PrintWriter        ← 格式化輸出
└── StringWriter       ← 寫到 String
```

</div>
</div>

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>橋接器：</b> <code>InputStreamReader</code> / <code>OutputStreamWriter</code> 是 byte 串流與 char 串流的橋梁，可指定字元編碼（如 <code>"UTF-8"</code>）。<code>BufferedReader</code> 提供 <code>readLine()</code>、<code>BufferedWriter</code> 提供 <code>newLine()</code>；<code>PrintWriter</code> 提供 <code>println()</code> / <code>printf()</code>。
</div>

<!--
這是 Reader 和 Writer 的繼承體系。

Reader 下面，有讀取文字檔的 FileReader、加上緩衝並可以逐行讀取的 BufferedReader。Writer 下面，有寫入文字檔的 FileWriter、加上緩衝的 BufferedWriter，以及有 println 和 printf 方法的 PrintWriter。

其中有兩個特別的類別：InputStreamReader 和 OutputStreamWriter。它們是位元組串流和字元串流之間的橋樑，負責把位元組轉換成字元，而且可以指定字元編碼，例如 UTF-8。
-->

---

# FileReader — 讀取文字檔

```java
try (FileReader fr = new FileReader("hello.txt")) {
    int ch;
    while ((ch = fr.read()) != -1) {
        System.out.print((char) ch);
    }
}
```

| 建構子 | 說明 |
|--------|------|
| `FileReader(String fileName)` | 以路徑開啟，使用平台預設編碼 |
| `FileReader(File file)` | 以 File 物件開啟 |
| `FileReader(String name, Charset cs)` | JDK 11+，指定編碼 |

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>跨平台編碼：</b> 建議使用 <code>new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)</code> 明確指定 UTF-8 編碼，避免平台差異。
</div>

<!--
用 FileReader 讀取文字檔，寫法和剛才的 FileInputStream 幾乎一樣，只是每次讀到的是一個字元，而不是一個位元組。

要注意編碼的問題。FileReader 預設使用作業系統的編碼，在不同的電腦上，讀取中文可能會變成亂碼。Java 11 之後，可以在建構子裡直接指定編碼。或是用 InputStreamReader，明確指定 UTF-8，這樣在任何平台上，結果都會一致。
-->

---

# FileWriter — 寫入文字檔

| 建構子 | 說明 |
|--------|------|
| `FileWriter(String fileName)` | 覆寫模式 |
| `FileWriter(String fileName, boolean append)` | `true` 為附加模式 |
| `FileWriter(File file, Charset cs)` | JDK 11+，指定編碼 |

```java
try (FileWriter fw = new FileWriter("output.txt")) {
    fw.write("第一行文字\n");
    fw.write("第二行文字\n");
}
```

<!--
寫入文字檔用 FileWriter。和 FileOutputStream 一樣，預設是覆寫模式，第二個參數傳入 true，就是附加模式。Java 11 之後，也可以指定編碼。

看範例，直接用 write 寫入字串，就能把兩行文字寫進 output.txt，不需要自己轉成位元組。
-->

---

# FileWriter — 常用方法

| 方法 | 說明 |
|------|------|
| `write(int c)` | 寫出一個字元 |
| `write(String str)` | 寫出字串 |
| `write(char[] cbuf)` | 寫出字元陣列 |
| `write(String str, int off, int len)` | 寫出字串的一部分 |
| `flush()` | 強制寫出緩衝資料 |
| `close()` | 關閉並 flush |

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>換行注意：</b> <code>\n</code> 是 Unix 換行，Windows 是 <code>\r\n</code>。要跨平台相容，建議改用 <code>BufferedWriter.newLine()</code>。
</div>

<!--
FileWriter 常用的方法，包括寫出單一字元、字串、字元陣列，或是字串的一部分，另外還有 flush 和 close。

要注意換行的問題。反斜線 n 是 Unix 和 Mac 的換行符號，Windows 用的則是反斜線 r 反斜線 n。為了跨平台相容，建議改用 BufferedWriter 的 newLine 方法。
-->

---

# BufferedReader — 加緩衝讀取

**最重要的功能：逐行讀取 `readLine()`**

```java
try (BufferedReader br =
        new BufferedReader(new FileReader("data.csv"))) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
```

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b> <code>readLine()</code> 回傳 <code>null</code>（不是 -1）表示檔案結束；回傳值<b>不含</b>行尾的換行符號。
</div>

<!--
讀取文字檔時，最常用的是 BufferedReader，因為它有一個非常重要的方法：readLine，可以一次讀取一整行。

看範例，用 BufferedReader 包住 FileReader，在 while 迴圈裡用 readLine 一行一行讀取，並印出來。

要注意兩點：readLine 讀到檔案結尾時，回傳的是 null，而不是負 1；另外，它回傳的字串，不包含行尾的換行符號。
-->

---

# BufferedWriter — 加緩衝寫入

```java
try (BufferedWriter bw =
        new BufferedWriter(new FileWriter("report.txt"))) {
    bw.write("姓名\t成績");
    bw.newLine();           // 平台相容的換行
    bw.write("Alice\t95");
    bw.newLine();
}
```

| 方法 | 說明 |
|------|------|
| `write(String s)` | 寫出字串 |
| `write(String s, int off, int len)` | 寫出字串的一部分 |
| `newLine()` | 寫出平台換行符（Windows `\r\n`，Unix `\n`） |
| `flush()` | 強制將緩衝區送出 |

<!--
寫入文字檔時，搭配的是 BufferedWriter。

看範例，用 write 寫出姓名和成績的表頭，再用 newLine 換行，接著寫出 Alice 的資料。newLine 會依照作業系統，寫出正確的換行符號。
-->

---

# 字元 I/O 完整流程圖

```
文字檔（UTF-8）
    ↓ FileInputStream
    ↓ InputStreamReader(StandardCharsets.UTF_8)   ← byte → char
    ↓ BufferedReader                               ← 加緩衝 + readLine()
  [Java 程式邏輯]
    ↓ BufferedWriter                               ← 加緩衝 + newLine()
    ↓ OutputStreamWriter(StandardCharsets.UTF_8)   ← char → byte
    ↓ FileOutputStream
文字檔（UTF-8）
```

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>最佳實踐：</b> 明確指定編碼：<br>
<code>new BufferedReader(new InputStreamReader(new FileInputStream("f.txt"), StandardCharsets.UTF_8))</code>
</div>

<!--
這張圖把字元 I/O 的完整流程串起來了。

讀取時，從 UTF-8 的文字檔開始，先用 FileInputStream 讀出位元組，再用 InputStreamReader 指定 UTF-8，把位元組轉成字元，最後用 BufferedReader 加上緩衝，就能逐行讀取。

寫入時剛好反過來：BufferedWriter 加上緩衝，OutputStreamWriter 把字元轉成 UTF-8 的位元組，再交給 FileOutputStream 寫進檔案。

所以最佳實踐，就是像下面這樣，一層一層包起來，並明確指定編碼。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 系統 I/O

<!--
接下來是系統 I/O，也就是程式和鍵盤、螢幕之間的輸入輸出。
-->

---

# System 類別 — 標準串流

Java 啟動時自動建立三個靜態串流欄位：

| 欄位 | 類型 | 預設目的地 | 用途 |
|------|------|-----------|------|
| `System.in` | `InputStream` | 鍵盤輸入 | 讀取使用者輸入 |
| `System.out` | `PrintStream` | 標準輸出（螢幕） | 正常訊息輸出 |
| `System.err` | `PrintStream` | 標準錯誤（螢幕） | 錯誤訊息輸出 |

```java
// 讀取鍵盤輸入（包裝成 BufferedReader 使用）
BufferedReader br = new BufferedReader(
    new InputStreamReader(System.in));
String line = br.readLine();
```

<!--
Java 程式啟動時，會自動建立三個標準串流。

System.in 是鍵盤輸入，型別是 InputStream。System.out 是標準輸出，用來印出一般的訊息。System.err 是標準錯誤輸出，用來印出錯誤訊息。後兩個的型別都是 PrintStream。

看範例，System.in 本身只能讀取位元組，所以可以用 InputStreamReader 和 BufferedReader 把它包起來，就能用 readLine 讀取使用者輸入的一整行文字。
-->

---

# PrintStream 類別

`System.out` 和 `System.err` 的實際型別，提供方便的格式化輸出。

| 方法 | 說明 |
|------|------|
| `print(x)` | 輸出任意型別，不換行 |
| `println(x)` | 輸出後加換行 |
| `printf(format, args...)` | 格式化輸出（同 C 的 printf） |
| `format(format, args...)` | 與 `printf` 等效 |

```java
System.out.printf("%-10s %5d %8.2f%n", "Alice", 95, 98.75);
System.out.printf("%-10s %5d %8.2f%n", "Bob",   82, 80.50);
// Alice         95    98.75
// Bob           82    80.50
```

<!--
PrintStream 就是 System.out 和 System.err 的型別，它提供了方便的輸出方法。

print 輸出但不換行，println 輸出之後換行，printf 則是格式化輸出，format 和 printf 的效果一樣。

看範例，用 printf 把姓名靠左對齊，分數和平均靠右對齊，兩筆資料就能排得整整齊齊。
-->

---

# printf 常用格式符號

| 格式符 | 說明 | 範例 | 輸出 |
|--------|------|------|------|
| `%d` | 十進位整數 | `printf("%d", 42)` | `42` |
| `%5d` / `%-5d` | 寬度 5，靠右 / 靠左 | `printf("%5d", 42)` | `   42` / `42   ` |
| `%f` | 浮點數（預設 6 位小數） | `printf("%f", 3.14)` | `3.140000` |
| `%.2f` | 2 位小數 | `printf("%.2f", 3.14)` | `3.14` |
| `%s` | 字串 | `printf("%s", "Hi")` | `Hi` |
| `%n` | 平台換行符 | — | 換行 |
| `%b` | 布林值 | `printf("%b", true)` | `true` |

<!--
這張表複習了 printf 常用的格式符號。

`%d` 是整數，在中間加上數字可以指定寬度，預設靠右，加上減號就靠左。`%f` 是浮點數，預設有 6 位小數，`%.2f` 則只顯示 2 位。`%s` 是字串，`%b` 是布林值，`%n` 是換行。

這些在語言基礎那一章學過，這裡再整理一次。
-->

---

# Console 類別

`System.console()` 回傳與 JVM 關聯的 `Console` 物件（若無終端機則回傳 `null`）：

```java
Console console = System.console();
if (console == null) {
    System.err.println("沒有可用的終端機");
    return;
}
String username = console.readLine("請輸入帳號：");
char[] password = console.readPassword("請輸入密碼：");
System.out.printf("歡迎，%s！%n", username);
java.util.Arrays.fill(password, ' ');  // 安全清除密碼
```

---

# Console 類別 — 常用方法

| 方法 | 說明 |
|------|------|
| `readLine(String fmt, ...)` | 先顯示提示，再讀一行 |
| `readPassword(String fmt, ...)` | 讀密碼（不回顯），回傳 `char[]` |
| `format(String fmt, ...)` | 格式化輸出到終端 |

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>安全：</b> <code>readPassword()</code> 輸入時不回顯字元，回傳 <code>char[]</code> 而非 <code>String</code>，使用後應立即清零，避免密碼殘留在記憶體。
</div>

<!--
Console 類別，是專門用來和終端機互動的。用 System.console 取得它，但如果程式不是在終端機裡執行，例如在某些 IDE 裡，就會得到 null，所以要先檢查。

readLine 可以先顯示提示，再讀取一行輸入。readPassword 用來讀取密碼，輸入時畫面上不會顯示字元。

注意 readPassword 回傳的是字元陣列，而不是字串。因為字串是不可變的，會留在記憶體裡；字元陣列則可以在用完之後立刻清除，就像範例最後用 Arrays.fill 把密碼清空，更加安全。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 檔案管理

<!--
最後一個部分，我們看看怎麼用 File 類別管理檔案和資料夾。
-->

---

# File 類別 — 基本概念

`java.io.File` 代表一個**路徑**（可能是檔案或目錄），本身不做 I/O 操作：

```java
File f1 = new File("data.txt");              // 相對路徑
File f2 = new File("/home/user/data.txt");   // 絕對路徑
File f3 = new File("/home/user", "data.txt"); // 目錄 + 檔名
File dir = new File("reports");
File f4 = new File(dir, "2024.csv");          // File + 檔名
```

| 建構子 | 說明 |
|--------|------|
| `File(String pathname)` | 路徑字串 |
| `File(String parent, String child)` | 父路徑 + 子名稱 |
| `File(File parent, String child)` | 父 File + 子名稱 |

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b> 建立 <code>File</code> 物件<b>不會</b>建立實際檔案，只是表示一個路徑。
</div>

<!--
File 類別代表一個路徑，可能是一個檔案，也可能是一個資料夾。要注意，它本身不負責讀寫檔案的內容。

建立 File 有幾種方式：直接傳入相對路徑或絕對路徑；或是分成父路徑和檔名兩個部分；父路徑也可以是另一個 File 物件。

另外，建立 File 物件，並不會真的在磁碟上建立檔案，它只是代表一個路徑而已。
-->

---

# File 類別 — 查詢方法

| 方法 | 回傳 | 說明 |
|------|------|------|
| `exists()` | `boolean` | 路徑是否存在 |
| `isFile()` / `isDirectory()` | `boolean` | 是否為一般檔案 / 目錄 |
| `canRead()` / `canWrite()` | `boolean` | 是否可讀/可寫 |
| `getName()` / `getPath()` | `String` | 取得檔名（含副檔名）/ 路徑字串 |
| `getAbsolutePath()` | `String` | 取得絕對路徑 |
| `length()` | `long` | 檔案大小（bytes） |
| `lastModified()` | `long` | 最後修改時間（epoch ms） |

<!--
File 提供了很多查詢的方法。

exists 判斷路徑是否存在，isFile 和 isDirectory 判斷它是檔案還是資料夾，canRead 和 canWrite 判斷是否可讀寫。getName 取得檔名，getPath 和 getAbsolutePath 取得路徑和絕對路徑。length 取得檔案大小，lastModified 取得最後修改的時間。
-->

---

# File 類別 — 建立與刪除

```java
File dir = new File("output/reports");
if (!dir.exists()) {
    boolean ok = dir.mkdirs(); // 連同父目錄一起建立
    System.out.println("建立目錄：" + ok);
}

File f = new File(dir, "result.txt");
if (f.createNewFile()) {  // 建立空檔案（已存在則 false）
    System.out.println("建立檔案：" + f.getAbsolutePath());
}

f.delete();   // 刪除檔案
dir.delete(); // 刪除空目錄
```

<!--
File 也可以建立和刪除檔案或資料夾。

看範例，先檢查 output 底下的 reports 資料夾是否存在，不存在就用 mkdirs 建立，連同上層的資料夾一起建立。接著在裡面用 createNewFile 建立一個空檔案，如果檔案已經存在，會回傳 false。

最後用 delete 刪除檔案，再刪除空的資料夾。
-->

---

# File 類別 — 建立與刪除（方法）

| 方法 | 說明 |
|------|------|
| `mkdir()` | 建立單一目錄（父目錄須存在） |
| `mkdirs()` | 建立目錄及所有必要的父目錄 |
| `createNewFile()` | 建立空檔案，已存在回傳 `false` |
| `delete()` | 刪除檔案或空目錄 |

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>注意：</b> <code>delete()</code> 不會進資源回收桶，直接永久刪除。目錄內有檔案時 <code>delete()</code> 會失敗，須先清空內容。建議優先使用 <code>mkdirs()</code> 而非 <code>mkdir()</code>。
</div>

<!--
這張表整理了建立和刪除的方法。

mkdir 只建立一層資料夾，上層必須已經存在；mkdirs 則會把所有需要的上層資料夾一起建立，所以建議優先使用 mkdirs。createNewFile 建立空檔案，delete 刪除檔案或空資料夾。

要特別注意：delete 不會把檔案放進資源回收筒，而是直接永久刪除。另外，資料夾裡還有東西時，delete 會失敗，必須先把裡面清空。
-->

---

# File 類別 — 列舉目錄

```java
File dir = new File("src");
if (dir.isDirectory()) {
    File[] files = dir.listFiles();
    if (files != null) {
        for (File f : files) {
            String type = f.isDirectory() ? "[目錄]" : "[檔案]";
            System.out.printf("%s %-30s %8d bytes%n",
                type, f.getName(), f.length());
        }
    }
}
```

| 方法 | 回傳 | 說明 |
|------|------|------|
| `list()` / `listFiles()` | `String[]` / `File[]` | 子項目名稱 / File 物件陣列 |
| `list(FilenameFilter)` / `listFiles(FilenameFilter)` | `String[]` / `File[]` | 依過濾條件篩選 |

<!--
要列出資料夾裡的內容，可以用 listFiles。

看範例，先確認 src 是資料夾，再用 listFiles 取得裡面所有的項目。注意 listFiles 可能回傳 null，例如資料夾不存在或發生錯誤，所以要先檢查。接著用迴圈，印出每個項目是檔案還是資料夾、名稱和大小。

list 和 listFiles 的差別，是 list 回傳名稱的字串陣列，listFiles 回傳 File 物件的陣列。兩者都可以傳入一個過濾條件。
-->

---

# FilenameFilter 過濾

使用 lambda 過濾特定副檔名的檔案：

```java
File dir = new File(".");
File[] javaFiles = dir.listFiles(
    (d, name) -> name.endsWith(".java")
);

if (javaFiles != null) {
    for (File f : javaFiles) {
        System.out.println(f.getName() + " - " + f.length() + " bytes");
    }
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>現代替代方案：</b> JDK 7+ 提供 <code>java.nio.file.Files</code> 與 <code>Path</code>，功能更強大（支援 walk、遞迴列舉等），是新專案的推薦選擇。
</div>

<!--
用過濾條件，可以只列出符合條件的檔案。

看範例，listFiles 傳入一個 Lambda，條件是名稱以 .java 結尾，就只會列出 Java 原始檔。

Java 7 之後，還有更現代的 java.nio.file 套件，提供 Files 和 Path，功能更強大，例如一次走訪所有子資料夾。新的專案建議優先使用它們。
-->

---
layout: default
---

# 練習一：Byte 檔案複製
### 任務說明

撰寫一個程式，將指定的二進位檔案（如圖片）複製到另一個路徑。

**需求：**
1. 使用 `FileInputStream` 和 `FileOutputStream` 實作
2. 加入 `BufferedInputStream` / `BufferedOutputStream` 提升效能
3. 使用 `try-with-resources` 確保資源釋放
4. 顯示複製的 byte 數量

**預期輸出：**
```
複製完成：photo.jpg → photo_backup.jpg
共複製 204800 bytes
```

<!--
接下來我們動手練習。

第一題，請寫一個程式，把一個二進位檔案，例如一張圖片，複製到另一個路徑。

要求用 FileInputStream 和 FileOutputStream，再加上 Buffered 的包裝提升效能，用 try-with-resources 確保資源被釋放，最後印出總共複製了多少位元組。

我們先暫停一下，自己動手試試看。

【講師】給學生約 15 分鐘實作，需先準備一個測試用的圖片檔。
-->

---

# 練習一：解題提示

```java
long total = 0;
try (BufferedInputStream bis = new BufferedInputStream(
         new FileInputStream("photo.jpg"));
     BufferedOutputStream bos = new BufferedOutputStream(
         new FileOutputStream("photo_backup.jpg"))) {
    byte[] buf = new byte[8192];
    int n;
    while ((n = bis.read(buf)) != -1) {
        bos.write(buf, 0, n);
        total += n;
    }
}
System.out.println("共複製 " + total + " bytes");
```

**關鍵點：**
- 多個 AutoCloseable 資源可在同一個 try-with-resources 中宣告，用分號分隔
- 關閉順序與宣告順序相反（先關 bos 再關 bis）
- `bos.write(buf, 0, n)` 而非 `bos.write(buf)`，只寫實際讀到的 bytes

<!--
我們來看提示。

同一個 try-with-resources 裡，可以宣告多個資源，用分號隔開。關閉的時候，順序會和宣告相反，先關輸出，再關輸入。

迴圈裡每次讀取一批資料到 8192 的緩衝區，再寫出去，並把讀到的數量加進總數。

最關鍵的一點，是寫出時要用 write buf、0、n，只寫出實際讀到的 n 個位元組。如果直接寫出整個 buf，最後一次就會多寫入一些殘留的舊資料，複製出來的檔案就壞掉了。
-->

---
layout: default
---

# 練習二：逐行讀取文字檔
### 任務說明

撰寫一個程式，讀取 CSV 文字檔，計算每位學生的平均成績並輸出報表。

**輸入檔案 `scores.csv`：**
```
Alice,85,90,78
Bob,70,88,95
Carol,92,76,84
```

**預期輸出：**
```
Alice   : 84.33
Bob     : 84.33
Carol   : 84.00
```

**需求：** 使用 `BufferedReader` + `FileReader`，`readLine()` 逐行讀取，`split(",")` 切割欄位，`try-with-resources` 管理資源。

<!--
第二題，請讀取一個 CSV 文字檔，計算每位學生的平均成績。

檔案裡每一行是一位學生：姓名，後面接著三個分數，用逗號分隔。輸出時，姓名靠左對齊，平均顯示到小數點後兩位。

請用 BufferedReader 搭配 readLine 逐行讀取，用 split 切割欄位，並用 try-with-resources 管理資源。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 15 分鐘實作。
-->

---

# 練習二：解題提示

```java
try (BufferedReader br =
        new BufferedReader(new FileReader("scores.csv"))) {
    String line;
    while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        String name = parts[0];
        double sum = 0;
        for (int i = 1; i < parts.length; i++)
            sum += Integer.parseInt(parts[i]);
        System.out.printf("%-8s: %.2f%n",
            name, sum / (parts.length - 1));
    }
}
```

**關鍵步驟：**
1. `readLine()` + `split(",")` 是 Java 文字處理最常見的組合
2. `parts.length - 1` 是分數欄位的數量（排除姓名欄）
3. `Integer.parseInt()` 將字串轉為 int

<!--
我們來看提示。

用 readLine 讀取每一行，再用 split 逗號切成陣列。第 0 個欄位是姓名，從第 1 個開始是分數，用 parseInt 轉成整數後加總。

分數的數量是陣列長度減 1，因為要扣掉姓名那一欄。最後用 printf 印出姓名和平均。readLine 搭配 split，是 Java 處理文字資料最常見的組合。
-->

---
layout: default
---

# 練習三：目錄瀏覽工具
### 任務說明

撰寫一個方法，遞迴列出指定目錄下所有的檔案與子目錄，以樹狀結構輸出：

```
[目錄] src
  [目錄] com/example
    [檔案] Main.java (1234 bytes)
    [檔案] Utils.java (567 bytes)
  [檔案] module-info.java (89 bytes)
```

**需求：**
1. 使用 `File.isDirectory()` 判斷是否為目錄
2. 使用 `File.listFiles()` 取得子項目（注意 null 檢查）
3. 遞迴方法遍歷子目錄
4. 用縮排（indent 參數）表示目錄層次

<!--
第三題，請寫一個方法，用樹狀結構，列出一個資料夾底下所有的檔案和子資料夾。

要求用 isDirectory 判斷是不是資料夾，用 listFiles 取得裡面的項目，並記得檢查 null。遇到子資料夾時，要遞迴地繼續往下列出，並用縮排表示層次。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 15 分鐘實作。
-->

---

# 練習三：解題提示

```java
void printTree(File dir, String indent) {
    File[] files = dir.listFiles();
    if (files == null) return;  // null 檢查
    for (File f : files) {
        if (f.isDirectory()) {
            System.out.println(indent + "[目錄] " + f.getName());
            printTree(f, indent + "  ");
        } else {
            System.out.printf("%s[檔案] %s (%d bytes)%n",
                indent, f.getName(), f.length());
        }
    }
}
// 呼叫：printTree(new File("src"), "");
```

**重點：**
- `listFiles()` 可能回傳 `null`（目錄不存在或 I/O 錯誤），需先檢查
- 遞迴時 indent 加兩個空格表示深一層
- 若需排序可呼叫 `Arrays.sort(files)`

<!--
我們來看提示。

printTree 方法接收一個資料夾和目前的縮排。先用 listFiles 取得內容，如果是 null 就直接返回。接著逐一處理：如果是資料夾，就印出名稱，再呼叫 printTree 自己，縮排多加兩個空格；如果是檔案，就印出名稱和大小。

這就是之前學過的遞迴。資料夾裡有資料夾，正好是遞迴最適合處理的結構。如果希望輸出依名稱排序，可以先用 Arrays.sort 排序。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了 Java 的輸入與輸出。

串流是資料流動的通道，二進位檔用 InputStream 和 OutputStream，文字檔用 Reader 和 Writer。讀寫時加上 Buffered 的包裝可以大幅提升效能，文字檔要注意明確指定 UTF-8 編碼，並用 try-with-resources 自動關閉資源。

System 提供了標準輸入輸出，Console 可以安全地讀取密碼，File 類別則用來查詢、建立和刪除檔案與資料夾。

【講師】開放 Q&A。
-->

---
layout: end
---

# 課程結束
### Java I/O：byte 串流、字元串流、系統 I/O、File 類別

<!--
位元組串流、字元串流、系統 I/O，以及 File 類別，就是 Java I/O 的四個核心。

下一章，我們要學習壓縮檔的處理。我們下一章見。
-->

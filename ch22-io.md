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
【開場白】
各位未來的架構師們，今天我們要聊聊 Java 裡的「搬運工」——I/O（輸入與輸出）。

【為什麼要學這個？】
如果你的程式只會算 1+1，算完就丟掉，那叫「計算機」；如果它會把結果存到硬碟，或從網路上抓資料，那才叫「應用程式」。I/O 就是程式跟外部世界溝通的橋樑。

【今天學完你會能做什麼】
學完這章，你就能寫出一個會讀寫檔案、下載圖片、甚至寫出像我一樣「會說話」的程式（雖然它說的可能都是 Debug 訊息）。
-->

---
layout: default
---

# 本章大綱

<div class="grid grid-cols-2 gap-4 mt-4">
<div>

**第一部分：串流基礎**
- 認識串流 (Stream)
- InputStream / OutputStream 類別圖

**第二部分：Byte I/O**
- FileInputStream / FileOutputStream
- BufferedInputStream / BufferedOutputStream

**第三部分：字元 I/O**
- Writer 和 Reader 類別層次
- FileReader / FileWriter
- BufferedReader / BufferedWriter

</div>
<div>

**第四部分：系統 I/O**
- System 類別
- PrintStream 類別
- Console 類別

**第五部分：檔案管理**
- File 類別

**練習與 Q&A**

</div>
</div>

<!--
【課程預覽】
這章內容不少，我們會從最底層的 Byte（像搬磚頭）講到 Char（像搬文字），最後教大家怎麼管理檔案。

【學習建議】
不要被這麼多類別名稱嚇到，Java I/O 的類別命名很有規律：開頭是「做什麼的」（File, Buffered），結尾是「哪一族的」（Stream, Reader, Writer）。看懂規律，你就不會迷路！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
## 串流基礎

<!--
【章節開場】
第一部分，我們先來認識什麼是「串流」。這是一個很有層次的概念，理解了它，你就會發現 Java I/O 其實像是在接水管。
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
【核心說明】
「串流」就是資料流動的通道。資料在裡面只能排隊走，不能插隊。

【生活化比喻】
串流就像是「迴轉壽司」。資料就是盤子上的壽司，你坐在那裡（程式），壽司一個一個流過來（InputStream）；或者你把做好的壽司放上去流出去（OutputStream）。

【程式世界怎麼用】
記住：只要是「人看得懂的字」就用 Reader/Writer；如果是「電腦看的二進位（圖片、MP3）」就用 Stream。用錯了，圖片可能變亂碼，或者文字變一堆問號。

⚠️ 學生常見誤解：
InputStream 是「進來」，OutputStream 是「出去」。初學者常搞反，請記得「我（程式）是中心」：進來我這裡叫 Input，從我這裡發出去叫 Output。
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
│   └── PrintStream        ← System.out
└── ObjectOutputStream     ← 序列化
```

</div>
</div>

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>裝飾者模式（Decorator Pattern）：</b> Java I/O 採用包裝設計，例如 <code>BufferedInputStream</code> 包裝 <code>FileInputStream</code>，為其加上緩衝功能，不改變原有介面。
</div>

<!--
【核心說明】
Java 的 I/O 類別多到可以玩拼圖。最下面那層是基礎（讀檔案、讀陣列），上面那層是「加強版」。

【生活化比喻】
這就像是在「套娃」。底層是普通水管（FileInputStream），外面套一層保溫層（BufferedInputStream），再套一層過濾網（DataInputStream）。雖然包了很多層，但它終究還是一根水管。

💼 業界實務：
不要一次記住所有類別。通常我們都是「一層包一層」。寫 code 的時候像在做三明治：先選麵包（File），再加配料（Buffered），最後拿給客人用。

⚠️ 學生常見誤解：
InputStream 是抽象類別，你不能 `new InputStream()`。你必須找一個實體的子類別（像 FileInputStream）來用。就像你不能點一份「食物」，你得點「雞排」。
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
【核心說明】
`read()` 是靈魂，它會回傳讀到的東西。如果回傳 `-1`，代表「沒了、到底了」。

【生活化比喻】
`read()` 就像是在摸彩箱裡摸球。摸到球就回傳球的編號（0~255），如果摸空了，手會拿到一個「空箱標記」（-1）。

⚠️ 學生常見誤解：
一定要記得 `close()`！就像用完水龍頭要關，不然會資源洩漏。
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
【核心說明】
`flush()` 很重要。有時候你寫了資料，但電腦為了省力會先存在快取，不立刻寫入硬碟。呼叫 `flush()` 就像是跟電腦說：「別偷懶，現在就給我存進去！」

💼 業界實務：
`close()` 內部會自動幫你呼叫 `flush()`。所以用 `try-with-resources` 就萬事 OK，不用擔心馬桶沒沖乾淨。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
## Byte I/O

<!--
【章節開場】
接下來我們進入「搬磚模式」——Byte I/O。這是最原始、但也最強大的方式，不管什麼檔案都能搬。
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
【帶讀程式碼前的鋪陳】
我們來看一個最基本的讀檔程式。

【逐步解說】
注意 `try (...)` 括號裡的寫法，這叫「自動關水龍頭模式」。
迴圈裡的 `(b = fis.read()) != -1` 是 Java 界的經典台詞，意思是：「只要還摸得到東西，就繼續摸」。
最後我們把摸到的 byte 強制轉成 char 印出來。

⚠️ 學生常見誤解：
如果在 `try` 外面宣告檔案流卻沒關閉，你的程式執行久了就會因為「檔案開啟太多」而被作業系統制裁。

💼 業界實務：
現在沒人在寫 `finally { fis.close() }` 了，全部都用 `try-with-resources`。如果你在面試還在寫舊的，面試官會覺得你是從 2010 年穿越過來的。
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
【核心說明】
一次搬一個 byte 太慢了，我們改用「搬家紙箱」模式，一次搬一整箱（4096 bytes）。

【生活化比喻】
這就像是搬家。你是一個一個螺絲釘搬（read 單個 byte）比較快，還是一次裝一箱搬比較快？

⚠️ 學生常見誤解：
最後一次讀取時，紙箱可能只有半滿。回傳的 `bytesRead` 就是告訴你「這次箱子裡有多少真貨」。如果你不管它，直接把整箱（4096）都當作讀到的資料，那最後一點點資料就會出現殘影。

💼 業界實務：
4096 或 8192 是常用的緩衝區大小。為什麼？因為這是作業系統「一個區塊」的大小。配合它的節奏，效率最高。
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
【核心說明】
寫檔案預設是「大清洗模式」，一打開舊的資料就噴了。

【生活化比喻】
預設的 `FileOutputStream` 就像是在黑板上寫字，它會先拿板擦把黑板擦得乾乾淨淨再寫。如果你想接著寫，要加一個 `true` 參數，這就像是在黑板下面繼續寫，不擦掉上面的。

💼 業界實務：
寫 Log 時千萬記得加 `true`，不然你的伺服器跑一天後，Log 檔永遠只有最後一行，你絕對會想哭。

⚠️ 學生常見誤解：
檔案如果不存在，Java 會幫你建立；但如果「資料夾」不存在，Java 會噴報錯（FileNotFoundException）。它會買筆，但不會幫你蓋房子。
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
【核心說明】
這是在水管中間加一個「儲水槽」。

【生活化比喻】
這就像是便利商店補貨。物流車是一件一件商品從總部載過來（無緩衝），還是整台車載滿過來（有緩衝）比較快？答案顯而易見。

💼 業界實務：
在 Java 裡，只要看到 `FileInputStream`，外面幾乎一定會套一層 `BufferedInputStream`。這就像是買手機一定要貼保護貼一樣，是標準配備。

【效能提示】
不加 Buffered，你的程式可能跑 10 分鐘；加了之後，可能只要 10 秒鐘。這就是資深開發者跟小白的差距。
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
| `BufferedInputStream(InputStream in)` | 預設緩衝 8192 bytes |
| `BufferedInputStream(InputStream in, int size)` | 自訂緩衝大小 |
| `BufferedOutputStream(OutputStream out)` | 預設緩衝 8192 bytes |
| `BufferedOutputStream(OutputStream out, int size)` | 自訂緩衝大小 |

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>習慣：</b> 凡是讀寫檔案，<b>幾乎都應加上 Buffered 包裝</b>，除非是已知單次大塊傳輸。
</div>

<!--
【核心說明】
記得：Buffered 系列就是「加速掛」。

【生活化比喻】
`flush()` 就像是沖馬桶。資料先存在水箱（Buffer），時間到了或是你按下沖水鍵（flush），資料才會嘩啦啦地流進下水道（硬碟）。

⚠️ 學生常見誤解：
如果你用了 BufferedOutputStream 卻沒呼叫 `close()` 或 `flush()`，最後一點點資料可能會卡在水箱裡，沒進到硬碟。結果檔案就缺了一角。

💼 業界實務：
`close()` 內部會自動幫你呼叫 `flush()`。所以用 `try-with-resources` 就萬事 OK，不用擔心馬桶沒沖乾淨。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
## 字元 I/O

<!--
【章節開場】
好了，磚頭搬完了，我們來搬「有靈魂的文字」。字元 I/O 會幫我們處理編碼，讓中文字不再變亂碼。
-->

---

# Reader / Writer 類別層次

<div class="grid grid-cols-2 gap-4 mt-2">
<div>

**Reader 繼承體系**

```
Reader (abstract)
├── InputStreamReader     ← byte→char 橋接
│   └── FileReader        ← 讀文字檔
├── BufferedReader        ← 加緩衝＋readLine
├── StringReader          ← 讀 String
└── CharArrayReader       ← 讀 char[]
```

</div>
<div>

**Writer 繼承體系**

```
Writer (abstract)
├── OutputStreamWriter    ← char→byte 橋接
│   └── FileWriter        ← 寫文字檔
├── BufferedWriter        ← 加緩衝＋newLine
├── PrintWriter           ← println/printf
└── StringWriter          ← 寫到 String
```

</div>
</div>

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>橋接器：</b> <code>InputStreamReader</code> / <code>OutputStreamWriter</code> 是 byte 串流與 char 串流的橋梁，可指定字元編碼（如 <code>"UTF-8"</code>）。
</div>

<!--
【核心說明】
Reader 和 Writer 是處理 Unicode 字元的。

【生活化比喻】
如果說 InputStream 是「原油管」，那 Reader 就是「加油站」。它會把原油加工成我們可以用的汽油（字元）。

💼 業界實務：
最常出事的就是「編碼」。如果你用 Windows 的預設編碼去讀 Linux 的 UTF-8 檔案，保證你看到滿滿的「五鬼搬運」（亂碼）。這時候 `InputStreamReader` 就能讓你指定編碼，是救命恩人。

⚠️ 學生常見誤解：
`FileReader` 很好用，但它以前不能指定編碼（直到 JDK 11）。如果你在舊專案看到亂碼，記得換成 `InputStreamReader` 包一層。
-->

---

# FileReader — 讀取文字檔

最簡讀取範例：

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
【核心說明】
`FileReader` 是專門為文字檔案設計的。

【生活化比喻】
它就像是一個「識字」的搬運工。Byte I/O 只管重量，不管內容；FileReader 則會辨認這是不是一個字，哪怕這個字佔了 3 個 byte（像 UTF-8 的中文字），它也會完整地搬給你。

⚠️ 學生常見誤解：
`read()` 回傳的雖然是 `int`，但代表的是字元的編號。一定要轉成 `(char)` 才能印出人看得懂的字。

💼 業界實務：
不要信任「平台預設編碼」。在台灣，Windows 可能是 MS950，Mac 是 UTF-8。如果你不指定，換個地方跑程式就崩潰。這就是「在我電腦上是好的」這句名言的由來之一。
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
【核心說明】
`FileWriter` 讓你直接寫字串進去，非常直觀。

【生活化比喻】
這就像是拿著奇異筆直接在紙上寫字，不需要再把字轉成 0101 的二進位碼，它幫你處理好了。
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
⚠️ 學生常見誤解：
關於換行：`\n` 是 Unix 系統的換行，Windows 是 `\r\n`。如果你硬寫 `\n`，在 Windows 筆記本打開可能所有字都擠在同一行。下一節我們會教一個「跨平台」的換行法。

💼 業界實務：
雖然 `FileWriter` 可以直接寫字串，但加上 `BufferedWriter` 效率會更高。寫入幾萬行文字時，差異非常明顯。
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
【核心說明】
`BufferedReader` 的 `readLine()` 是 Java 開發者的最愛，沒有之一。

【生活化比喻】
這就像是「吃麵」。普通讀取是一個麵位一個麵位吃；`readLine()` 是「一次吸一根長長的麵」。吸到最後沒麵了，嘴巴就空了（null）。

⚠️ 學生常見誤解：
注意！`readLine()` 回傳 `null` 代表結束，不是 `-1`。這是字元流跟位元流最大的區別之一，寫錯了你的迴圈會變成無限迴圈。

💼 業界實務：
處理 CSV 或 Log 檔案時，這招是必殺技。搭配 `split(",")` 就能輕鬆拆解每一行資料。
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
【核心說明】
`BufferedWriter` 也有它的獨門絕技：`newLine()`。

【生活化比喻】
`newLine()` 就像是一張「智慧換行鍵」。它會根據你的電腦是 Windows 還是 Mac，自動決定要按兩下（\r\n）還是一下（\n）。

【程式世界怎麼用】
如果你想讓你的程式在任何電腦上看起來排版都正確，**一定要用 `newLine()`**，別再自己手寫 `\n` 了。

💼 業界實務：
寫報表時，我們常會用 `bw.write(data); bw.newLine();` 這樣的節奏來寫入每一行。簡單、高效、跨平台。
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
【核心說明】
這張圖是 Java I/O 的「完全體」。

【生活化比喻】
這就像是在「接過濾系統」。
最底層是原始水源（FileInputStream）；中間是轉接頭（InputStreamReader）把水變成飲用水（Char）；最後加上儲水池（BufferedReader）讓你喝得更順。

💼 業界實務：
雖然這行 code 長到你懷疑人生，但在業界，這才是最安全的寫法。它可以同時解決效能（Buffered）和編碼（UTF-8）問題。看到這行，面試官會覺得你很有經驗。

⚠️ 學生常見誤解：
包裝的順序不能錯。就像你不能先穿鞋子再穿襪子。一定是 File 在內，Reader 在中，Buffered 在外。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
## 系統 I/O

<!--
【章節開場】
接下來講講每天都在用的 `System.out.println`。其實它也是 I/O 的一員，而且它是個「特權階級」。
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
【核心說明】
這三個傢伙是 Java 的「內建通道」。

【生活化比喻】
`System.out` 是大喇叭，用來廣播正常消息；`System.err` 是緊急警報，通常在 IDE 裡會變紅色，用來大叫「出事啦！」；`System.in` 則是你的耳朵，用來聽使用者的指令。

⚠️ 學生常見誤解：
`System.in` 只是個 `InputStream`。如果你想讀「一行文字」，你還是得把它包裝成 `BufferedReader`。它就像是一個原始的收音機，你要加個喇叭（Reader）才聽得清楚。

💼 業界實務：
在生產環境，我們其實很少用 `System.out`。為什麼？因為它不能控制格式、不能設定儲存位置。我們通常會改用專業的日誌工具（如 Log4j），但學習階段，它還是我們最好的朋友。
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
【核心說明】
`printf` 是「排版狂魔」的最愛。

【生活化比喻】
`println` 就像是寫字時每寫一句就換行；`printf` 則像是「填空題」。你先畫好格子（格式字串），然後把內容塞進去。不管內容長短，格子的大小都固定，這樣印出來的報表才會整整齊齊。

💼 業界實務：
`%n` 比 `\n` 好用。它會根據作業系統自動切換換行符號，是專業開發者的選擇。

【老鳥悄悄話】
如果你印出來的資料對不齊，看起來就像是業餘作品。學好 `printf`，讓你的輸出看起來像個資深工程師。
-->

---

# printf 常用格式符號

| 格式符 | 說明 | 範例 | 輸出 |
|--------|------|------|------|
| `%d` | 十進位整數 | `printf("%d", 42)` | `42` |
| `%5d` | 寬度 5，靠右 | `printf("%5d", 42)` | `   42` |
| `%-5d` | 寬度 5，靠左 | `printf("%-5d", 42)` | `42   ` |
| `%f` | 浮點數（預設 6 位小數） | `printf("%f", 3.14)` | `3.140000` |
| `%.2f` | 2 位小數 | `printf("%.2f", 3.14)` | `3.14` |
| `%s` | 字串 | `printf("%s", "Hi")` | `Hi` |
| `%n` | 平台換行符 | — | 換行 |
| `%b` | 布林值 | `printf("%b", true)` | `true` |

<!--
【核心說明】
這是一張「格式密碼表」。

【生活化比喻】
`%d` 是「給整數住的房間」，`%.2f` 是「只准顯示兩位小數的客房」。那個 `-` 負號就是「靠左站」，沒有負號就是「靠右站」。

⚠️ 學生常見誤解：
記得參數的順序要跟 `%` 的順序對上。如果你第一個寫 `%d` 但傳進去的是一個字串，Java 會噴一個 `IllegalFormatConversionException`。這報錯名稱長到可以當饒舌歌詞，其實就是「送錯房間」的意思。
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

| 方法 | 說明 |
|------|------|
| `readLine(String fmt, ...)` | 先顯示提示，再讀一行 |
| `readPassword(String fmt, ...)` | 讀密碼（不回顯），回傳 `char[]` |
| `format(String fmt, ...)` | 格式化輸出到終端 |

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>安全：</b> <code>readPassword()</code> 輸入時不回顯字元，回傳 <code>char[]</code> 而非 <code>String</code>，使用後應立即清零，避免密碼殘留在記憶體。
</div>

<!--
【核心說明】
`Console` 是專業的「輸入管家」，特別擅長保護隱私。

【生活化比喻】
`readPassword` 就像是在 ATM 輸入密碼。螢幕上什麼都不顯示，這樣旁邊的人才不會看到你的密碼。

⚠️ 學生常見誤解：
在 IDE（如 Eclipse, IntelliJ）裡執行時，`System.console()` 通常會回傳 `null`。你必須在真正的終端機（CMD 或 Terminal）裡跑才有用。別在 IDE 裡糾結為什麼沒反應，那是它的「隔離保護」。

💼 業界實務：
為什麼要用 `char[]` 而不是 `String`？因為 `String` 是不可變的。你一旦建立了密碼字串，它就會在記憶體裡待很久。`char[]` 則可以手動填滿空白（Arrays.fill），用完即毀，就像電影裡的機密文件。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
## 檔案管理

<!--
【章節開場】
最後，我們來當「檔案管理員」。`File` 類別能幫我們查檔案、創目錄、刪資料，就像是程式裡的檔案總管。
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
【核心說明】
`File` 物件只是一張「門牌」。

【生活化比喻】
你寫下 `new File("my_dream.txt")` 時，並不代表你已經有這份檔案了。這就像是在一張紙上寫一個地址，這個地址指向的地方可能還是一片空地。你要真的去蓋房子（呼叫 `createNewFile()`），檔案才會出現。

⚠️ 學生常見誤解：
路徑斜線問題。Windows 用 `\`，Linux 用 `/`。在 Java 裡你可以通通用 `/`，它很聰明會幫你轉。或者用 `File.separator`，那是真正的跨平台解決方案。

💼 業界實務：
儘管 `File` 類別很有名，但現代 Java（JDK 7+）更推薦用 `java.nio.file.Path`。不過在看舊程式碼時，你還是會遇到大量的 `File`，所以基本功要紮實。
-->

---

# File 類別 — 查詢方法

| 方法 | 回傳 | 說明 |
|------|------|------|
| `exists()` | `boolean` | 路徑是否存在 |
| `isFile()` | `boolean` | 是否為一般檔案 |
| `isDirectory()` | `boolean` | 是否為目錄 |
| `canRead()` / `canWrite()` | `boolean` | 是否可讀/可寫 |
| `getName()` | `String` | 取得檔名（含副檔名） |
| `getPath()` | `String` | 取得路徑字串 |
| `getAbsolutePath()` | `String` | 取得絕對路徑 |
| `length()` | `long` | 檔案大小（bytes） |
| `lastModified()` | `long` | 最後修改時間（epoch ms） |

<!--
【核心說明】
這些方法是用來「打探消息」的。

【生活化比喻】
在你打算開門（讀檔）之前，最好先問問：這家有人嗎（exists）？這家是住宅還是公司（isFile/isDirectory）？我有鑰匙進去嗎（canRead）？

⚠️ 學生常見誤解：
`length()` 回傳的是 byte 數。如果你看到一個中文字回傳 3，別驚訝，那是 UTF-8 編碼。另外，對資料夾呼叫 `length()` 回傳的值通常沒有參考價值，它不會幫你算裡面所有檔案的總和。

💼 業界實務：
判斷 `exists()` 是良好習慣。直接去讀一個不存在的檔案會讓你的程式噴 Exception，雖然我們可以 catch 它，但先用 `exists()` 判斷會讓程式碼看起來更優雅。
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
【核心說明】
這是「施工現場」。建立檔案前先確認目錄存在是老鳥的反射動作。
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
【生活化比喻】
`mkdir()` 就像是蓋一樓。如果你想蓋三樓，但二樓還沒蓋，它就會失敗。
`mkdirs()` 則是「包辦工程」。只要你說你要蓋到三樓，它會自動幫你把一樓、二樓通通蓋好。所以，**無腦選 `mkdirs()` 就對了**。

💼 業界實務：
建立檔案前先確認目錄存在是老鳥的反射動作。如果你直接寫檔案到一個不存在的目錄，程式會直接死給你看。
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
| `list()` | `String[]` | 子項目名稱陣列 |
| `listFiles()` | `File[]` | 子項目 File 物件陣列 |
| `list(FilenameFilter)` | `String[]` | 依過濾條件篩選名稱 |
| `listFiles(FilenameFilter)` | `File[]` | 依過濾條件篩選 File |

<!--
【核心說明】
這是「點名時間」。

【生活化比喻】
`listFiles()` 就像是班長進教室點名。它會給你一份所有同學（檔案與資料夾）的清單。

⚠️ 學生常見誤解：
`listFiles()` 可能會回傳 `null`。如果你對一個「不是資料夾」或「沒權限存取」的 File 點名，它會給你 null。如果你沒檢查就直接跑 `for` 迴圈，就會迎接大名鼎鼎的 `NullPointerException`。

💼 業界實務：
想找所有的 `.java` 檔？用 `FilenameFilter` 就能輕鬆達成。不需要拿到全部清單再自己寫 `if` 判斷，這叫「專業的過濾」。
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
【核心說明】
這是一個「篩選器」。

【生活化比喻】
就像是篩子。你可以設定：只有副檔名是 `.java` 的才准掉下來。

💼 業界實務：
在處理大量檔案時，過濾是非常重要的。如果你想在幾千個檔案裡找圖片，別把它們全載入記憶體，先用過濾器篩掉沒用的東西。

【小提醒】
雖然 `FilenameFilter` 很方便，但它的遞迴能力很差（它只能看這一層）。如果你想找「資料夾的資料夾」裡的檔案，你得自己寫遞迴，或者改用現代的 `Files.walk()`。
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
【練習導引】
這題是 I/O 的經典入門。

【關鍵提示】
1. 記得用「搬家紙箱」模式（byte[] buffer），效率比較高。
2. 搬圖片千萬不能用 Reader/Writer，不然圖片會變毀滅性的損壞。
3. `try-with-resources` 宣告兩個資源時，中間用分號 `;` 隔開。

【笑話時間】
如果你這題沒寫好，複製出來的圖片可能看起來像「靈異照片」或乾脆打不開。這就是 Byte I/O 的威力，差一個 byte 都不行。
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
【解說要點】
最容易出錯的地方在 `bos.write(buf, 0, n)`。
如果你直接寫 `bos.write(buf)`，最後一箱（未滿的部分）也會被當作滿的寫進去，你的圖片檔案會莫名其妙變大一點點，這叫「結尾殘影」。

【老鳥筆記】
那個 `n` 就是「這次真的摸到了多少個」。摸到多少，就寫多少，不多不少。這才是專業的複製。
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
Alice  : 84.33
Bob    : 84.33
Carol  : 84.00
```

**需求：** 使用 `BufferedReader` + `FileReader`，`readLine()` 逐行讀取，`split(",")` 切割欄位，`try-with-resources` 管理資源。

<!--
【練習導引】
這一題模擬真實的資料處理情境。

【關鍵提示】
1. `readLine()` 讀出來是字串，你要用 `split(",")` 把它切成碎片。
2. 切出來的碎片還是字串，記得用 `Integer.parseInt()` 轉成數字才能算平均。
3. 輸出時用 `printf("%.2f")` 讓成績看起來很專業，小數點後兩位。

【笑話時間】
如果成績算錯，Carol 可能會來找你抗議。身為工程師，我們可以沒女朋友，但平均值一定要算準。
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
【解說要點】
注意 `parts[0]` 是名字，分數是從索引 1 開始。
`printf("%-8s")` 的 `-8` 是讓名字靠左佔 8 個空格。這樣名字長度不同的時候，後面的冒號才會對齊。這叫「強迫症工程師的自我修養」。
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
【練習導引】
這題是「遞迴」跟「File 類別」的完美結合。

【關鍵提示】
1. 如果遇到目錄，就要「呼叫自己」去查這個目錄裡面。這就是遞迴。
2. 每次進入更深一層，縮排就要多加兩個空白。
3. 再次提醒：`listFiles()` 可能會回傳 `null`，一定要檢查，否則你的程式會死在「權限不足」的資料夾面前。

【笑話時間】
遞迴就像是夢中夢。如果你的目錄層級太深（例如一萬層），你的電腦可能會發生 `StackOverflowError`，那代表你的夢醒不來了。
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
【解說要點】
這段 code 的精華就在那個 `printTree(f, indent + "  ")`。這行會帶著更長的縮排潛入更深的目錄。
如果你發現印出來的東西亂七八糟，先檢查你的 `indent` 有沒有傳對。

💼 業界實務：
遞迴遍歷是很基礎的能力。雖然現代 Java 的 `Files.walk()` 一行就能做完這件事，但手寫遞迴能讓你真正理解檔案系統的樹狀結構。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【總結回顧】
今天我們從 Byte 聊到 Char，從檔案聊到系統。

【黃金法則】
1. 永遠用 `try-with-resources`。
2. 文字用 Reader/Writer，二進位用 Stream。
3. 為了效能，請加上 Buffered。
4. 為了安全，請指定 UTF-8。

有沒有哪根水管（串流）你覺得接不起來的？儘管問，我不收維修費！
-->

---
layout: end
---

# 課程結束
### Java I/O：byte 串流、字元串流、系統 I/O、File 類別

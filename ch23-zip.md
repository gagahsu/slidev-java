---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 壓縮與解壓縮檔案
routeAlias: ch23
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
  <h1 style="color: #1a5c5c; font-size: 3.8rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">壓縮與解壓縮檔案</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「用 java.util.zip 打包與解開你的檔案」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，這一章的主題是壓縮與解壓縮檔案。

寄送一整個專案資料夾、備份一批報表，或是從網路下載一包資料，我們幾乎每天都會碰到 ZIP 檔。如果程式需要自動打包或解開這些檔案，就要用到 Java 內建的 java.util.zip 套件。

學完這一章，我們就能用程式壓縮檔案和整個資料夾、解開 ZIP 檔，也會知道解壓縮時必須注意的安全問題。
-->

---
layout: default
---

# 本章大綱

<div class="grid grid-cols-2 gap-4 mt-4">
<div>

**套件基礎**
- java.util.zip 套件概觀
- ZIP 檔案結構概念
- ZipEntry 核心方法
- ZipOutputStream 核心方法
- ZipInputStream vs ZipFile

**壓縮操作**
- 壓縮單一檔案
- 壓縮多個檔案
- 壓縮整個目錄

</div>
<div>

**解壓縮操作**
- 用 ZipInputStream 解壓縮
- 用 ZipFile 讀取 ZIP
- Zip Slip 安全防護

**現代寫法**
- NIO + ZIP File System

**練習與 Q&A**

</div>
</div>

<!--
這一章分成四個部分。

第一部分認識 java.util.zip 套件，以及 ZIP 檔案的結構。第二部分是壓縮，從單一檔案、多個檔案，到整個資料夾。第三部分是解壓縮，包括兩種讀取方式，以及一個重要的安全漏洞：Zip Slip。

第四部分介紹更現代的寫法，用 NIO 的 ZIP 檔案系統操作壓縮檔。最後有兩題練習。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# java.util.zip 套件概觀

<!--
我們先來認識 java.util.zip 套件的整體架構。
-->

---

# java.util.zip 套件架構

```
java.util.zip
├── ZipEntry          ← 代表 ZIP 內的一個檔案或目錄項目
├── ZipOutputStream   ← 寫入（壓縮）ZIP 資料
├── ZipInputStream    ← 讀取（解壓縮）ZIP 資料（循序）
├── ZipFile           ← 讀取 ZIP 檔案（支援隨機存取）
├── Deflater          ← 底層壓縮引擎（DEFLATE 演算法）
├── Inflater          ← 底層解壓縮引擎
├── GZIPOutputStream  ← 寫入 .gz 格式
└── GZIPInputStream   ← 讀取 .gz 格式
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>ZIP vs GZIP：</b> ZIP 可打包多個檔案；GZIP 只壓縮單一串流，通常配合 tar 使用（.tar.gz）。
</div>

<!--
這個套件裡有幾個主要的類別。

ZipEntry 代表 ZIP 檔裡的一個檔案或資料夾。ZipOutputStream 負責壓縮，把資料寫成 ZIP。讀取 ZIP 有兩種選擇：ZipInputStream 是從頭到尾依序讀取，ZipFile 則可以直接跳到某個檔案。底層的 Deflater 和 Inflater 是實際負責壓縮和解壓縮的引擎。另外還有處理 .gz 格式的 GZIP 類別。

ZIP 和 GZIP 的差別是：ZIP 可以把多個檔案打包在一起；GZIP 只壓縮單一的資料，所以常常和 tar 搭配，變成 .tar.gz。
-->

---

# ZIP 檔案結構概念

```
archive.zip
├── file1.txt          ← ZipEntry（檔案）
├── images/            ← ZipEntry（目錄，name 結尾有 /）
│   ├── logo.png       ← ZipEntry（檔案）
│   └── banner.jpg     ← ZipEntry（檔案）
└── data/
    └── report.csv     ← ZipEntry（檔案）
```

<div class="mt-3 p-3 bg-green-50 border-l-4 border-green-400 text-gray-700 text-sm text-left">
📦 <b>ZipEntry 是關鍵：</b> 每個壓縮項目（檔案或資料夾）都用一個 <code>ZipEntry</code> 物件表示，包含名稱、大小、壓縮大小、修改時間等後設資料（metadata）。
</div>

<!--
一個 ZIP 檔案，裡面就像一個小型的資料夾結構。

看這張圖，archive.zip 裡有一個 file1.txt，一個 images 資料夾，裡面有兩張圖片，還有一個 data 資料夾。

重點是：「ZIP 裡的每一個檔案或資料夾，都是一個 ZipEntry 物件。」它記錄了名稱、原始大小、壓縮後的大小、修改時間等資訊。如果是資料夾，名稱的最後會是一個斜線。
-->

---

# ZipEntry 核心方法

| 方法 | 說明 |
|------|------|
| `getName()` | 取得 entry 名稱（含路徑） |
| `getSize()` | 原始未壓縮大小（bytes），未知則 -1 |
| `getCompressedSize()` | 壓縮後大小（bytes），未知則 -1 |
| `getCrc()` | CRC-32 校驗碼，未知則 -1 |
| `getTime()` | 修改時間（毫秒），未設則 -1 |
| `isDirectory()` | 是否為目錄（名稱是否以 `/` 結尾） |
| `setMethod(int)` | 設定壓縮方式：`DEFLATED` 或 `STORED` |

<div class="mt-3 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>STORED vs DEFLATED：</b> <code>STORED</code> 不壓縮（只打包），<code>DEFLATED</code> 使用 DEFLATE 演算法壓縮（預設）。
</div>

<!--
ZipEntry 常用的方法有這些。

getName 取得名稱，包含路徑。getSize 是原始的大小，getCompressedSize 是壓縮後的大小，如果還不知道，會回傳負 1。getCrc 是用來檢查資料是否正確的校驗碼。getTime 是修改時間。isDirectory 判斷是不是資料夾。

setMethod 可以設定壓縮的方式：STORED 是不壓縮、只打包；DEFLATED 則是用 DEFLATE 演算法壓縮，也是預設值。
-->

---

# ZipOutputStream 核心方法

| 方法 | 說明 |
|------|------|
| `putNextEntry(ZipEntry e)` | 開始寫入一個新的 entry |
| `write(byte[], int, int)` | 寫入目前 entry 的資料 |
| `closeEntry()` | 關閉目前 entry，準備下一個 |
| `setLevel(int level)` | 壓縮等級 0（不壓）～9（最高），預設 -1 |
| `setMethod(int method)` | 預設壓縮方式：`DEFLATED` 或 `STORED` |
| `setComment(String comment)` | 設定整個 ZIP 檔案的備註 |
| `finish()` | 完成寫入但不關閉底層串流 |

<!--
ZipOutputStream 是用來建立 ZIP 檔的，常用的方法有這些。

putNextEntry 開始寫入一個新的項目，write 寫入這個項目的資料，closeEntry 結束目前的項目，準備寫下一個。

另外，setLevel 可以設定壓縮等級，從 0 到 9，數字越大壓縮得越小，但速度越慢。setComment 可以幫整個 ZIP 檔加上備註。finish 則會完成寫入，但不關閉底層的串流。
-->

---

# ZipInputStream vs ZipFile

| 比較項目 | `ZipInputStream` | `ZipFile` |
|----------|-----------------|-----------|
| 存取方式 | 循序（Sequential） | 隨機（Random access） |
| 資料來源 | 任何 `InputStream`（網路、記憶體） | 磁碟上的實體檔案 |
| 取得特定 entry | 必須逐一掃描 | `getEntry(name)` 直接取得 |
| 平行讀取多個 entry | 不支援 | 支援 |
| 適用場景 | 串流下載、管線處理 | 本機已存在的 ZIP 檔案 |

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>選用建議：</b> 有本機 ZIP 檔案且需要查詢特定 entry → 用 <code>ZipFile</code>；處理串流或不確定來源 → 用 <code>ZipInputStream</code>。
</div>

<!--
讀取 ZIP 有兩種類別，我們比較一下。

ZipInputStream 是依序讀取，只能從第一個項目一個一個往下讀。它的好處是資料來源可以是任何 InputStream，例如網路下載中的資料。ZipFile 則可以直接跳到任何一個項目，用 getEntry 加上名稱，就能直接取得，也支援同時讀取多個項目，但它只能讀取磁碟上的檔案。

所以，如果是本機的 ZIP 檔，而且只需要其中某幾個檔案，就用 ZipFile；如果是處理串流，或是來源不確定，就用 ZipInputStream。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 壓縮 (Zip) 檔案

<!--
接下來，我們實際動手壓縮檔案。
-->

---

# 最簡壓縮範例：單一檔案

```java
try (ZipOutputStream zos =
         new ZipOutputStream(new FileOutputStream("hello.zip"))) {

    zos.putNextEntry(new ZipEntry("hello.txt"));
    byte[] data = Files.readAllBytes(Path.of("hello.txt"));
    zos.write(data);
    zos.closeEntry();
}
```

<div class="mt-4 p-3 bg-green-50 border-l-4 border-green-400 text-gray-700 text-sm text-left">
✅ <b>三步驟口訣：</b>
<code>putNextEntry</code>（宣告 entry）→ <code>write</code>（寫資料）→ <code>closeEntry</code>（結束 entry）
</div>

<!--
我們先看最簡單的例子：把一個檔案壓縮成 ZIP。

用 try-with-resources 建立 ZipOutputStream，輸出到 hello.zip。接著呼叫 putNextEntry，宣告 ZIP 裡會有一個叫 hello.txt 的項目。然後讀出原始檔案的內容，用 write 寫進去，最後呼叫 closeEntry。

只要記住三個步驟：「putNextEntry 宣告項目，write 寫入資料，closeEntry 結束項目。」
-->

---

# 壓縮單一檔案 — 完整版（含緩衝）

```java
public static void zipSingleFile(String src, String dest)
        throws IOException {
    byte[] buffer = new byte[4096];
    try (FileOutputStream fos = new FileOutputStream(dest);
         ZipOutputStream zos = new ZipOutputStream(fos);
         FileInputStream fis = new FileInputStream(src)) {

        zos.putNextEntry(new ZipEntry(new File(src).getName()));
        int len;
        while ((len = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
        }
        zos.closeEntry();
    }
}
```

<!--
這是加上緩衝的完整版本。

try-with-resources 裡宣告了三個資源：輸出的檔案、ZipOutputStream，以及讀取原始檔案的 FileInputStream。

先用原始檔案的名稱建立 ZipEntry，接著用一個 4096 的緩衝區，迴圈讀取原始檔案，一批一批寫進 ZIP，最後 closeEntry。這種寫法不需要一次把整個檔案讀進記憶體，就算檔案很大也沒問題。
-->

---

# 壓縮多個檔案

```java
public static void zipMultipleFiles(
        List<String> srcFiles, String dest) throws IOException {
    byte[] buf = new byte[4096];
    try (ZipOutputStream zos =
             new ZipOutputStream(new FileOutputStream(dest))) {
        for (String src : srcFiles) {
            try (FileInputStream fis = new FileInputStream(src)) {
                zos.putNextEntry(new ZipEntry(new File(src).getName()));
                int len;
                while ((len = fis.read(buf)) > 0)
                    zos.write(buf, 0, len);
                zos.closeEntry();
            }
        }
    }
}
```

<!--
壓縮多個檔案，就是對每一個檔案，重複那三個步驟。

zipMultipleFiles 接收一個檔案清單。用一個 ZipOutputStream，在迴圈裡處理每個檔案：開啟原始檔案，putNextEntry，用緩衝區把資料寫進去，再 closeEntry。

注意每個原始檔案，都用自己的 try-with-resources 開啟和關閉，處理完一個就關一個。
-->

---

# 壓縮多個檔案 — 呼叫範例

```java
public static void main(String[] args) throws IOException {
    List<String> files = List.of(
        "report.txt",
        "data.csv",
        "image.png"
    );
    zipMultipleFiles(files, "archive.zip");
    System.out.println("壓縮完成：archive.zip");
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>設定壓縮等級：</b> 在第一個 putNextEntry 前加 <code>zos.setLevel(9)</code> 可啟用最高壓縮比（速度較慢）；<code>setLevel(0)</code> 等同不壓縮。
</div>

<!--
呼叫的方式很簡單。

用 List.of 準備三個檔案的名稱，交給 zipMultipleFiles，指定輸出成 archive.zip，完成後印出壓縮完成。

如果要追求最小的檔案，可以在第一個 putNextEntry 之前，呼叫 setLevel 9，啟用最高的壓縮比，代價是速度比較慢。setLevel 0 則等於不壓縮。
-->

---

# 壓縮整個目錄 — 遞迴輔助方法

```java
private static void addDirToZip(File dir, String base,
                                  ZipOutputStream zos)
        throws IOException {
    for (File file : dir.listFiles()) {
        String entryName = base + file.getName();
        if (file.isDirectory()) {
            zos.putNextEntry(new ZipEntry(entryName + "/"));
            zos.closeEntry();
            addDirToZip(file, entryName + "/", zos);
        } else {
            zos.putNextEntry(new ZipEntry(entryName));
            Files.copy(file.toPath(), zos);
            zos.closeEntry();
        }
    }
}
```

<!--
壓縮整個資料夾，需要用遞迴，因為資料夾裡可能還有資料夾。

addDirToZip 方法走訪資料夾裡的每一個項目，名稱前面加上目前的路徑。如果是資料夾，就建立一個名稱以斜線結尾的項目，代表資料夾，再呼叫自己，處理裡面的內容。如果是檔案，就 putNextEntry，用 Files.copy 一行把檔案內容寫進 ZIP，再 closeEntry。

用 Files.copy，就不需要自己寫緩衝區的迴圈了。
-->

---

# 壓縮整個目錄 — 呼叫入口

```java
public static void zipDirectory(String srcDir, String dest)
        throws IOException {
    File dir = new File(srcDir);
    try (ZipOutputStream zos =
             new ZipOutputStream(new FileOutputStream(dest))) {
        addDirToZip(dir, dir.getName() + "/", zos);
    }
}

// 呼叫方式
zipDirectory("myProject", "myProject.zip");
```

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>注意 NullPointerException：</b> <code>dir.listFiles()</code> 在目錄不存在或無讀取權限時會回傳 <code>null</code>，實務中應先做 null 檢查。
</div>

<!--
這是呼叫的入口。

zipDirectory 建立 ZipOutputStream，再呼叫剛才的 addDirToZip，傳入資料夾名稱加上斜線，當作最上層的路徑。這樣解壓縮之後，所有檔案都會在同一個資料夾底下。

要注意，listFiles 在資料夾不存在，或沒有讀取權限時，會回傳 null，直接拿來跑迴圈就會發生 NullPointerException。實務上一定要先檢查。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 解壓縮 (Unzip) 檔案

<!--
學會了壓縮，接下來看解壓縮。
-->

---

# 最簡解壓縮範例

```java
try (ZipInputStream zis =
         new ZipInputStream(new FileInputStream("archive.zip"))) {
    ZipEntry entry;
    while ((entry = zis.getNextEntry()) != null) {
        Path outPath = Path.of("output", entry.getName());
        Files.createDirectories(outPath.getParent());
        Files.copy(zis, outPath);
        zis.closeEntry();
    }
}
```

<div class="mt-4 p-3 bg-green-50 border-l-4 border-green-400 text-gray-700 text-sm text-left">
✅ <b>解壓縮三步驟：</b> <code>getNextEntry()</code>（取得下一個 entry）→ 讀取資料 → <code>closeEntry()</code>（結束此 entry）
</div>

<!--
這是最簡單的解壓縮寫法，使用 ZipInputStream。

用 while 迴圈呼叫 getNextEntry，一次取得一個項目，直到回傳 null，代表全部讀完了。每個項目，先決定要輸出到 output 資料夾裡的哪個位置，建立需要的資料夾，再用 Files.copy 把資料寫出去，最後 closeEntry。

解壓縮也是三個步驟：getNextEntry 取得項目，讀取資料，closeEntry 結束項目。
-->

---

# 用 ZipInputStream 解壓縮 — 完整版

```java
public static void unzip(String src, String destDir)
        throws IOException {
    byte[] buf = new byte[4096];
    try (ZipInputStream zis =
             new ZipInputStream(new FileInputStream(src))) {
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            File out = new File(destDir, entry.getName());
            if (entry.isDirectory()) {
                out.mkdirs();
            } else {
                new File(out.getParent()).mkdirs();
                try (FileOutputStream fos = new FileOutputStream(out)) {
                    int len;
                    while ((len = zis.read(buf)) > 0)
                        fos.write(buf, 0, len);
                }
            }
            zis.closeEntry();
        }
    }
}
```

<!--
這是完整版本，並且分別處理資料夾和檔案。

每一個項目，先算出要輸出的路徑。如果是資料夾，就用 mkdirs 建立它。如果是檔案，就先確保上層資料夾存在，再用緩衝區從 ZipInputStream 讀取資料，寫進輸出的檔案。

注意這裡是從 zis 讀取。ZipInputStream 讀取時，會自動讀取目前這個項目的內容，讀完了就會停下來，等我們呼叫 getNextEntry，才會換到下一個。
-->

---

# Zip Slip 安全漏洞防護

```java
// 危險：entry 名稱可能含 "../" 跳脫到目標目錄之外！
File out = new File(destDir, entry.getName()); // ← 未驗證

// 安全寫法：驗證輸出路徑在目標目錄內
File destFile = new File(destDir, entry.getName());
String destDirPath = new File(destDir).getCanonicalPath();
String destFilePath = destFile.getCanonicalPath();
if (!destFilePath.startsWith(destDirPath + File.separator)) {
    throw new IOException("Zip Slip detected: " + entry.getName());
}
```

<div class="mt-3 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
🔒 <b>Zip Slip 攻擊：</b> 惡意 ZIP 可在 entry 名稱中使用 <code>../../etc/passwd</code> 等路徑跳脫到系統敏感目錄。生產環境務必加上路徑驗證。
</div>

<!--
解壓縮時，有一個非常重要的安全漏洞，叫做 Zip Slip。

ZIP 檔裡項目的名稱，是由製作壓縮檔的人決定的。惡意的 ZIP，可以把名稱取成點點斜線點點斜線，接著系統檔案的路徑。如果我們直接用這個名稱決定輸出位置，檔案就會跳出目標資料夾，覆蓋到系統上重要的檔案。

安全的寫法，是先用 getCanonicalPath 取得真正的完整路徑，再檢查輸出檔案的路徑，是不是在目標資料夾裡面。如果不是，就拋出例外，拒絕處理。

所以，處理來源不明的 ZIP 檔時，一定要加上這個檢查。
-->

---

# 用 ZipFile 讀取 ZIP 檔案

| 方法 | 說明 |
|------|------|
| `ZipFile(String name)` | 開啟指定路徑的 ZIP 檔案 |
| `getEntry(String name)` | 依名稱取得指定 entry（找不到回傳 null） |
| `getInputStream(ZipEntry e)` | 取得指定 entry 的 InputStream |
| `entries()` | 取得所有 entry 的 `Enumeration` |
| `stream()` | 取得所有 entry 的 `Stream<ZipEntry>`（Java 8+） |
| `size()` | ZIP 內的 entry 總數 |

<!--
另一種讀取 ZIP 的方式，是用 ZipFile。

建構子傳入 ZIP 檔的路徑。getEntry 可以直接用名稱取得某個項目，找不到就回傳 null。getInputStream 可以取得某個項目的內容。entries 和 stream 可以取得所有的項目，stream 是 Java 8 之後的版本。size 則回傳總共有幾個項目。
-->

---

# ZipFile 使用範例

```java
try (ZipFile zf = new ZipFile("archive.zip")) {
    // 1. 列出所有 entry
    zf.stream()
      .map(ZipEntry::getName)
      .forEach(System.out::println);

    // 2. 讀取特定 entry 的內容
    ZipEntry entry = zf.getEntry("data/report.csv");
    if (entry != null) {
        try (InputStream is = zf.getInputStream(entry)) {
            String content = new String(is.readAllBytes());
            System.out.println(content);
        }
    }
}
```

<!--
看範例，第一部分用 stream 取得所有的項目，取出名稱並印出來，就能列出 ZIP 裡的檔案清單。

第二部分直接用 getEntry，取得 data 資料夾裡的 report.csv。確認不是 null 之後，用 getInputStream 讀出全部內容，轉成字串印出來。

不需要從頭掃描整個 ZIP，就能直接讀到想要的檔案，這就是 ZipFile 隨機存取的優點。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# NIO + ZIP File System（Java 11+）

<!--
最後一個部分，我們看看更現代的寫法：用 NIO 的 ZIP 檔案系統來操作壓縮檔。
-->

---

# NIO ZIP File System 概念

```
FileSystems.newFileSystem(zipPath, Map.of("create", "true"))
     ↓
  ZipFileSystem（實作 FileSystem 介面）
     ↓
  可用 Files.copy / Files.walk / Files.readAllBytes
  等所有 NIO Files 工具操作 ZIP 內容
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>ZIP File System Provider：</b> 內建於 JDK（<code>jdk.zipfs</code> 模組），不需額外相依。Java 13+ 支援直接傳入 <code>Path</code> 物件。
</div>

<!--
Java 提供了一種很巧妙的方式：把一個 ZIP 檔，當作一個檔案系統來使用。

用 FileSystems.newFileSystem 開啟 ZIP 檔，就會得到一個 ZIP 檔案系統。之後就可以用 Files.copy、Files.walk 這些平常操作檔案的方法，直接操作 ZIP 裡面的內容，就像在操作一個普通的資料夾。

這個功能是 JDK 內建的，不需要額外安裝任何函式庫。
-->

---

# NIO：建立 ZIP 並加入檔案

```java
Path zipPath = Path.of("output.zip");
Map<String, String> env = Map.of("create", "true");

try (FileSystem fs = FileSystems.newFileSystem(zipPath, env)) {
    // 加入單一檔案
    Files.copy(Path.of("hello.txt"), fs.getPath("hello.txt"));

    // 加入到子目錄
    Files.createDirectories(fs.getPath("data"));
    Files.copy(Path.of("report.csv"),
               fs.getPath("data/report.csv"));
}
```

<!--
看範例，先指定 ZIP 檔的路徑，第二個參數的 create 設成 true，表示如果檔案不存在，就建立一個新的。

開啟檔案系統之後，用 Files.copy，把本機的 hello.txt，複製到 ZIP 裡的 hello.txt。也可以先在 ZIP 裡建立 data 資料夾，再把 report.csv 複製進去。

不需要 putNextEntry，也不需要自己寫緩衝區，程式碼簡潔多了。
-->

---

# NIO：解壓縮所有檔案

```java
Path zipPath = Path.of("archive.zip");
Path outDir  = Path.of("extracted");

try (FileSystem fs = FileSystems.newFileSystem(zipPath, Map.of())) {
    Path root = fs.getPath("/");
    Files.walk(root)
         .filter(p -> !Files.isDirectory(p))
         .forEach(p -> {
             Path target = outDir.resolve(
                 root.relativize(p).toString());
             try {
                 Files.createDirectories(target.getParent());
                 Files.copy(p, target,
                     StandardCopyOption.REPLACE_EXISTING);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         });
}
```

<!--
用同樣的方式也能解壓縮。

開啟 ZIP 檔案系統之後，取得它的根目錄，再用 Files.walk 走訪裡面所有的路徑，過濾掉資料夾，只留下檔案。

每個檔案，先算出它相對於根目錄的路徑，再接到輸出資料夾後面，得到目標位置。建立需要的資料夾後，用 Files.copy 複製出去，如果已經存在就覆蓋。
-->

---

# NIO vs 傳統寫法比較

| 比較項目 | 傳統（ZipOutputStream/InputStream） | NIO ZIP File System |
|----------|--------------------------------------|---------------------|
| API 風格 | IO Stream 導向 | Path / Files 導向 |
| 程式碼量 | 較多（手動 buffer） | 較少（Files.copy 一行） |
| 支援版本 | Java 1.1+ | Java 7+（完整 Java 11+） |
| 串流來源 | 任何 InputStream | 僅磁碟檔案 |
| 修改已存在 ZIP | 需重新建立 | 可直接新增/刪除 entry |
| 適合場景 | 串流處理、記憶體 ZIP | 本機檔案操作 |

<!--
這張表比較了傳統寫法和 NIO 的寫法。

傳統的寫法以串流為主，程式碼比較多，需要自己處理緩衝區，但資料來源可以是任何 InputStream，適合處理網路串流或記憶體裡的資料。NIO 的寫法以 Path 和 Files 為主，程式碼比較少，還能直接新增或刪除 ZIP 裡的項目，但只能處理磁碟上的檔案。

所以處理串流時用傳統寫法，操作本機檔案時，NIO 會更方便。
-->

---
layout: default
---

# 練習一：壓縮指定目錄
### 任務說明

撰寫一個 Java 程式，接受命令列參數：`java ZipTool <來源目錄> <輸出.zip>`

**需求：**
1. 遞迴壓縮來源目錄下的所有檔案與子目錄
2. ZIP 內保留目錄結構（如 `logs/2024/app.log`）
3. 使用 try-with-resources 確保資源正確釋放
4. 若來源目錄不存在，印出錯誤訊息並結束

<!--
接下來我們動手練習。

第一題，請寫一個命令列工具，接收兩個參數：來源資料夾和輸出的 ZIP 檔名。

要求遞迴地壓縮來源資料夾裡所有的檔案和子資料夾，ZIP 裡要保留原本的資料夾結構，用 try-with-resources 管理資源，如果來源資料夾不存在，就印出錯誤訊息並結束。

我們先暫停一下，自己動手試試看。

【講師】給學生約 15 分鐘實作。
-->

---

# 練習一：解題提示

```java
Path srcDir = Path.of(args[0]);
try (ZipOutputStream zos =
         new ZipOutputStream(new FileOutputStream(args[1]))) {
    Files.walk(srcDir)
         .filter(p -> !Files.isDirectory(p))
         .forEach(p -> {
             String entryName = srcDir.relativize(p).toString()
                                      .replace("\\", "/");
             // putNextEntry → Files.copy(p, zos) → closeEntry
         });
}
```

**關鍵步驟：**
1. `Files.walk(srcDir)` 取得所有子路徑
2. `srcDir.relativize(p)` 計算相對路徑作為 entry 名稱
3. Windows 路徑分隔符 `\` 要替換為 `/`（ZIP 規格要求）
4. `Files.copy(p, zos)` 取代手動 buffer 迴圈

<!--
我們來看提示。

用 Files.walk 取得來源資料夾底下所有的路徑，過濾掉資料夾。每個檔案用 relativize，算出它相對於來源資料夾的路徑，當作 ZIP 項目的名稱。

要特別注意：Windows 的路徑分隔符號是反斜線，但 ZIP 的規格要求用斜線，所以要先替換。最後一樣是 putNextEntry，用 Files.copy 寫入，再 closeEntry。
-->

---
layout: default
---

# 練習二：解壓縮並統計
### 任務說明

撰寫一個 Java 程式解壓縮 ZIP 並輸出統計報告：

```
java UnzipReport <archive.zip> <輸出目錄>
```

**輸出格式範例：**
```
解壓縮完成！
  檔案數量：15 / 目錄數量：3
  總原始大小：1,234,567 bytes
  總壓縮大小：456,789 bytes / 壓縮比：62.98%
```

**需求：**使用 `ZipFile` 讀取、統計 `getSize()` 與 `getCompressedSize()`、加入 Zip Slip 安全防護

<!--
第二題，請寫一個程式解壓縮 ZIP 檔，並輸出統計報告。

報告要包含：檔案和資料夾的數量、原始的總大小、壓縮後的總大小，以及壓縮比。

要求使用 ZipFile 讀取，用 getSize 和 getCompressedSize 統計大小，並且一定要加上 Zip Slip 的安全檢查。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 15 分鐘實作。
-->

---

# 練習二：解題提示

```java
long totalSize = 0, compressedSize = 0;
int fileCount = 0, dirCount = 0;

try (ZipFile zf = new ZipFile(args[0])) {
    for (ZipEntry e : Collections.list(zf.entries())) {
        if (e.isDirectory()) { dirCount++; continue; }
        fileCount++;
        totalSize      += e.getSize();
        compressedSize += e.getCompressedSize();
        // 解壓縮：zf.getInputStream(e) → 寫出到目標路徑
    }
}
double ratio = 1.0 - (double) compressedSize / totalSize;
System.out.printf("壓縮比：%.2f%%%n", ratio * 100);
```

<!--
我們來看提示。

用 Collections.list 把 entries 轉成清單，再逐一處理。遇到資料夾，資料夾數量加 1，然後繼續下一個；遇到檔案，就累加檔案數量、原始大小和壓縮後的大小，並用 getInputStream 把內容寫到目標路徑。

最後，壓縮比是 1 減去壓縮後大小除以原始大小。注意要先轉成 double 再相除，否則整數除法會讓結果變成 0。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了用 Java 處理 ZIP 檔。

ZIP 裡的每個檔案或資料夾都是一個 ZipEntry。壓縮用 ZipOutputStream，記住 putNextEntry、write、closeEntry 三個步驟；解壓縮可以用依序讀取的 ZipInputStream，或是能直接取得特定檔案的 ZipFile。

解壓縮來源不明的 ZIP 時，一定要檢查路徑，防範 Zip Slip 攻擊。操作本機檔案時，也可以用 NIO 的 ZIP 檔案系統，程式碼更簡潔。

【講師】開放 Q&A。
-->

---
layout: end
---

# 課程結束
### java.util.zip：壓縮、解壓縮、安全防護一次搞定

<!--
壓縮、解壓縮，加上安全防護，就是處理 ZIP 檔的完整流程。

下一章，我們要學習集合框架。我們下一章見。
-->

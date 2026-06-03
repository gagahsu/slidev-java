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
【開場白】
大家好，今天我們要聊的是「壓縮」。身為工程師，我們最喜歡把東西塞進小小的空間裡——不管是把程式碼塞進一兩行，還是把幾百個檔案塞成一個 ZIP 檔。

【為什麼要學這個？】
想像一下，如果你要寄 100 張照片給客戶，你是要分 100 次寄，還是打包成一個檔案寄？懂壓縮，不僅省空間，還省你的時間（跟網路費）。

【今天學完你會能做什麼】
學完之後，你就能用 Java 寫出像 WinRAR 一樣的工具（雖然介面可能沒那麼漂亮），幫你的程式自動打包 Log 或備份資料。
-->

---
layout: default
---

# 本章大綱

<div class="grid grid-cols-2 gap-4 mt-4">
<div>

**第一部分：套件基礎**
- java.util.zip 套件概觀
- ZIP 檔案結構概念
- ZipEntry 核心方法
- ZipOutputStream 核心方法
- ZipInputStream vs ZipFile

**第二部分：壓縮操作**
- 壓縮單一檔案
- 壓縮多個檔案
- 壓縮整個目錄

</div>
<div>

**第三部分：解壓縮操作**
- 用 ZipInputStream 解壓縮
- 用 ZipFile 讀取 ZIP
- Zip Slip 安全防護

**第四部分：現代寫法**
- NIO + ZIP File System

**練習與 Q&A**

</div>
</div>

<!--
【課程預覽】
這章我們會從基礎的 ZIP 類別講起，接著教你怎麼「塞東西」（壓縮），再教你怎麼「挖東西」（解壓縮），最後介紹最現代的 NIO 寫法。

【學習建議】
壓縮的程式碼看起來有點「儀式感」，步驟很多。別擔心，掌握了「ZipEntry」這個關鍵概念，剩下的就是接水管而已。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
## java.util.zip 套件概觀

<!--
【章節開場】
我們先來看看 Java 提供的這套「打包工具箱」。這套工具在 Java 1.1 就有了，歷史悠久，非常穩定。
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
【核心說明】
這是一整套壓縮裝備。

【生活化比喻】
`ZipOutputStream` 就像是一個「壓縮機」，你把檔案丟進去，它就幫你壓扁；`ZipEntry` 就像是檔案的「標籤」，告訴電腦這個被壓扁的東西原本叫什麼名字。

【程式世界怎麼用】
如果你只是想壓縮一個超大的 Log 檔，用 GZIP 就好；如果你要把整個專案打包，那一定要用 ZIP。

⚠️ 學生常見誤解：
`Deflater` 和 `Inflater` 是底層引擎，除非你想開發自己的壓縮演算法，否則一般我們不會直接碰到它們。就像你開車不需要知道引擎怎麼點火一樣。
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
【核心說明】
ZIP 檔其實就是一個「大包裹」，裡面塞了很多貼了標籤的小包裹。

【生活化比喻】
想像你去旅行，行李箱是 ZIP 檔，裡面的衣服、襪子就是 ZipEntry。每一件衣服你都要貼個標籤，不然解壓縮出來的時候，你分不清楚哪件是誰的。

⚠️ 學生常見誤解：
在 ZIP 的世界裡，資料夾也是一種 ZipEntry。它的名字會以 `/` 結尾。如果你忘了加那個斜線，解壓縮出來後，你的資料夾可能會變成一個奇怪的空白檔案。
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
【核心說明】
這就是每個檔案的「身分證」。

【生活化比喻】
`getName()` 就是名字，`getSize()` 是原始體重，`getCompressedSize()` 是減肥後的體重。

💼 業界實務：
`STORED` 模式通常用在已經被壓縮過的檔案（如 JPG 或 MP3）。因為這些檔案已經壓不動了，再壓一次只是浪費 CPU 而已。

⚠️ 學生常見誤解：
CRC 校驗碼是用來確認檔案有沒有壞掉的。如果解壓縮時 CRC 對不上，就像是包裹被拆過或是內容物碎掉了一樣，Java 會噴報錯。
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
【核心說明】
這是寫入 ZIP 的「節奏感」。

【生活化比喻】
這就像是在裝箱：
1. `putNextEntry`: 拿一個新箱子並貼上標籤。
2. `write`: 把東西塞進去。
3. `closeEntry`: 把這個箱子封起來。
重複這三個動作，直到你的東西都裝完。

💼 業界實務：
壓縮等級 `setLevel(9)` 雖然壓得最扁，但也最耗時間。如果你的伺服器很忙，建議用預設或低一點的等級，別為了省那幾個 KB 讓 CPU 燒起來。
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
【核心說明】
這兩種讀取方式就像是「錄影帶」跟「DVD」。

【生活化比喻】
`ZipInputStream` 像錄影帶：你要看最後一個片段，得先快轉（掃描）前面所有內容。
`ZipFile` 像 DVD：你可以直接點擊目錄跳到你想看的那一集。

💼 業界實務：
如果你正在從網路上「邊下載邊解壓」，你只能用 `ZipInputStream`。如果你是在硬碟裡找一個 1GB ZIP 檔裡的某張照片，用 `ZipFile` 會快到讓你飛起來。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
## 壓縮 (Zip) 檔案

<!--
【章節開場】
接下來，我們來動手做「壓縮機」。先學會壓一個檔案，再學會壓整座森林（目錄）。
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
【帶讀程式碼前的鋪陳】
來看這段「壓縮界」的 Hello World。

【逐步解說】
1. 先準備好 `ZipOutputStream`。
2. `putNextEntry` 創一個名叫 "hello.txt" 的標籤。
3. 把內容讀出來，通通灌進去。
4. `closeEntry` 封箱。

⚠️ 學生常見誤解：
很多人會忘記 `closeEntry`。雖然有時候程式也能動，但在壓縮多檔時，沒封箱就開新箱子，你的 ZIP 檔會直接損壞。

💼 業界實務：
`Files.readAllBytes` 只適合處理小檔案。如果你要壓縮一個 1GB 的檔案，這行會讓你的記憶體直接爆炸。記得用緩衝區（下一頁會講）。
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
【核心說明】
這就是「安全、穩定、不爆記憶體」的專業寫法。

【生活化比喻】
這就像是用水杯（buffer）去搬一桶水。雖然要多走幾趟，但絕對不會把地板弄濕（記憶體溢位）。

💼 業界實務：
`new File(src).getName()` 很重要！這確保你的 ZIP 檔內部的標籤只有檔名。如果不加這行，你壓進去的標籤可能會包含 `C:\Users\User\Documents\...` 這種長長的路徑，解壓縮的人會看到一大堆沒用的資料夾。
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
【帶讀程式碼前的鋪陳】
這是一次打包多個檔案。

【逐步解說】
結構很簡單：外面一個 `ZipOutputStream`，裡面一個 `for` 迴圈跑所有檔案。
每個檔案都要經歷一次「開箱、灌水、封箱」的過程。

⚠️ 學生常見誤解：
注意 `try-with-resources` 的巢狀結構。外層管理 ZIP 檔，內層管理正在讀取的原始檔。內層 `try` 結束時，原始檔會被關閉，這才對。如果把 `zos` 放在內層 `try`，壓完第一個檔案 ZIP 檔就被關了，第二個檔案就會報錯。
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
【核心說明】
這是一個簡單的測試程式。

【生活化比喻】
`setLevel(0)` 就像是把衣服平放在大行李箱裡，不壓；`setLevel(9)` 則是動用「真空壓縮袋」，把所有空氣都抽光，讓衣服薄得像紙一樣。

💼 業界實務：
通常我們不設定 Level，讓 Java 用預設的（-1，也就是 6 級）。這是一個效能與壓縮比的平衡點，就像是買車選「標準配備」最划算一樣。
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
【核心說明】
這是壓縮界的大魔王：遞迴壓縮。

【生活化比喻】
這就像是清理房間。你看到一個抽屜（資料夾），你就打開它，看看裡面有什麼；如果是檔案，就裝箱；如果是另一個小盒子，你就再打開它……直到所有東西都收完。

⚠️ 學生常見誤解：
目錄的標籤一定要以 `/` 結尾！這就是那行 `entryName + "/"` 的意義。

💼 業界實務：
`Files.copy(file.toPath(), zos)` 是個很神的小技巧。它會自動處理緩衝，讓你少寫好幾行 `while` 迴圈。身為資深工程師，我們追求的就是優雅地偷懶。
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
【核心說明】
這是整個「遞迴戲法」的開端。

【生活化比喻】
你要把整個家打包，你得先把大門（根目錄）打開，然後叫搬家公司（遞迴方法）進去搬。

⚠️ 學生常見誤解：
如果你的 `srcDir` 傳錯了，`listFiles()` 會給你一個冷酷的 `null`。然後你的程式就會噴 `NullPointerException`。**記得先做 `exists()` 和 `isDirectory()` 的檢查**。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
## 解壓縮 (Unzip) 檔案

<!--
【章節開場】
壓完了，現在來學怎麼「拆禮物」。
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
【核心說明】
這就是解壓縮的「開箱術」。

【生活化比喻】
這就像是拆驚喜包。你先摸出一個東西（getNextEntry），看看標籤叫什麼（entry.getName），然後把它放到該放的位置（Files.copy）。直到袋子空了（null）為止。

💼 業界實務：
`Files.createDirectories` 非常重要。萬一 ZIP 檔裡面有一個深埋三層的檔案，但你的硬碟裡還沒蓋那三層資料夾，解壓縮就會報錯。這行會自動幫你把「房子」蓋好，再把「家具」（檔案）放進去。
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
【核心說明】
這是能應對各類「妖魔鬼怪」ZIP 檔的完整版本。

【生活化比喻】
這個方法比較細心。它會先看拿出來的是不是資料夾。如果是，就蓋房子；如果是檔案，先確認地基（父目錄）蓋好了沒，然後再開始寫入。

⚠️ 學生常見誤解：
在迴圈裡，**千萬不要關閉 `zis`**。`zis` 就像是你的總開關，一關掉，後面的檔案就都讀不到了。我們要關的是 `fos`。
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
【核心說明】
這是一個「資深開發者一定要知道」的安全知識。

【生活化比喻】
這就像是有人給你一個包裹，標籤上寫著：「請放在我家的隔壁的隔壁的總理辦公室」。如果你不檢查，你就會在不知情的情況下幫壞人把炸彈放進去。

💼 業界實務：
`getCanonicalPath()` 會幫你把所有的 `../` 通通換成真正的路徑。如果發現最後的路徑不在你的目標資料夾內，那就是有人在搞鬼，直接丟出 Exception。
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
【核心說明】
這是讀取 ZIP 的「開掛模式」。

【生活化比喻】
如果你要把整個 ZIP 解開，用 `ZipInputStream`；如果你只想從幾千個檔案裡「偷看」其中一個，用 `ZipFile`。它就像是一個能隨時隨地開啟的傳送門。

⚠️ 學生常見誤解：
`ZipFile` 必須指到磁碟上一個實體的檔案。它不能用來讀取網路上流過來的資料，那是 `ZipInputStream` 的主場。
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
【帶讀程式碼前的鋪陳】
來看這段優雅的 `ZipFile` 程式碼。

【逐步解說】
第一部分用 Java 8 的 `stream()` 華麗地列出所有檔名。
第二部分直接查「我要的那個檔案」。如果存在，就取得它的 `InputStream` 並讀出來。

💼 業界實務：
`readAllBytes()` 是 Java 9 加的好東西。以前我們要寫 10 行 code 來讀字串，現在只要一行。這就是科技的進步。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
## NIO + ZIP File System（Java 11+）

<!--
【章節開場】
如果你覺得剛才那些類別名太長，這部分是你的救星。我們要學著把 ZIP 檔當成一個「虛擬硬碟」。
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
【核心說明】
這就是「把 ZIP 當硬碟」的神奇魔法。

【生活化比喻】
以前解壓縮像是要手動把箱子拆開（InputStream）；現在像是幫這個箱子掛載一個虛擬磁碟代號（Z槽），你可以用平常操作檔案的方式來操作它。

💼 業界實務：
這是 Java 7 以後的主推寫法。如果你在寫新的專案，這招會讓你少掉很多 `while` 迴圈跟 `buffer` 計算，程式碼會乾淨得像新的一樣。
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
【帶讀程式碼前的鋪陳】
用 NIO 建立 ZIP 檔，簡潔到不可思議。

【逐步解說】
1. 先定義一個 `FileSystem`，記得加上 `"create":"true"`，不然它會報錯說找不到 ZIP 檔。
2. 用 `fs.getPath()` 取得「ZIP 內部」的路徑。
3. 直接用 `Files.copy` 把外面的檔案複製進去。搞定！

⚠️ 學生常見誤解：
這不是在搬家，這是在「複製」。原來的 `hello.txt` 還會在外面，只是 ZIP 裡面多了一個分身。
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
【核心說明】
這就是「一鍵解壓縮」的進階版。

【逐步解說】
1. `Files.walk(root)` 會幫你走遍 ZIP 裡的所有角落。
2. `relativize` 會計算檔案在 ZIP 裡的相對位置。
3. `resolve` 會把這個位置對應到硬碟上的真實位置。
4. 最後一鍵 `Files.copy` 搬出去。

💼 業界實務：
如果你想要解壓縮時順便過濾掉某些檔案（比如只解壓圖片），在 `filter` 那行改一下條件就好。靈活度滿分！
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
【總結回顧】
這張表是你的「決策指南」。

【老鳥建議】
如果你是在處理使用者上傳的檔案（在記憶體裡），無腦選傳統寫法。如果你是寫個腳本在伺服器上打包日誌，無腦選 NIO。

⚠️ 學生常見誤解：
NIO 寫法雖然方便，但它的底層其實還是跑那些 `ZipOutputStream` 之類的東西，只是幫你包裝好了。效能上兩者差距不大，重點是程式碼的可讀性。
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
【練習導引】
這題要考你能不能把目錄結構完整地搬進 ZIP。

【關鍵提示】
1. 名字很重要！記得把路徑分隔符換成 `/`。ZIP 不喜歡 Windows 的 `\`。
2. 試著用 `Files.walk()` 來實作，會比手寫遞迴快得多。
3. 測試時，試著壓一個有子目錄的專案，解壓出來看看結構有沒有歪掉。
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
【解說要點】
`relativize` 是這一題的靈魂。它能把 `C:\Work\project\src\Main.java` 變成 `src\Main.java`。
如果不做這一步，解壓縮的人可能要點開 10 層資料夾才看得到你的程式碼。那太蠢了。
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
【練習導引】
這題是「實戰派」。不僅要解壓縮，還要算壓縮比，還要防禦攻擊。

【關鍵提示】
1. `getSize()` 是原重，`getCompressedSize()` 是壓後重。
2. 壓縮比 = 1.0 - (壓縮後 / 壓縮前)。
3. 記得加入上一節教的 `getCanonicalPath()` 驗證。別讓你的程式成為黑客的跳板。
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
【解說要點】
為什麼用 `ZipFile`？因為它在遍歷時比較穩定，且可以直接拿到 `size` 資訊。
如果你發現壓縮比是負的，那代表檔案被你「越壓越大」了（通常發生在圖片檔）。這很正常，別懷疑自己的數學。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【總結回顧】
今天我們從手動裝箱到自動掛載，完整攻略了 Java 的壓縮功能。

【黃金規則】
1. 壓縮口訣：開標籤、灌資料、封箱。
2. 安全第一：解壓縮時務必防禦 Zip Slip。
3. 現代開發：本機檔案優先選 NIO ZIP File System。

有沒有哪個包裹（ZIP）你拆不開的？趁我還沒下班趕快問！
-->

---
layout: end
---

# 課程結束
### java.util.zip：壓縮、解壓縮、安全防護一次搞定

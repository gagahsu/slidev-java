---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 多執行緒 (Multithreading)
routeAlias: ch21
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
  <h1 style="color: #1a5c5c; font-size: 3.8rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">多執行緒</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「讓程式同時做很多事：執行緒的建立、同步與通信」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
嘿各位，歡迎來到 Java 的「影分身之術」——多執行緒（Multithreading）！

【為什麼要學這個？】
你有沒有想過，為什麼你的電腦可以一邊放音樂、一邊下載片子（我是說學習影片），還能一邊跑你的程式？這就是多執行緒。如果你的程式一次只能做一件事，那就像是去餐廳點餐，廚師要先洗菜、再切菜、再炒菜，這期間所有人都要在外面排隊等，這家店大概三天就倒閉了。今天我們要學的就是：怎麼雇三個廚師同時開工。

【今天學完你會能做什麼】
學完這堂課，你的程式就不再是那個反應遲鈍的「單線程腦袋」了。你會知道怎麼叫執行緒起床工作、怎麼讓它們排隊、怎麼防止它們為了搶同一個雞腿（資源）而打架。
-->

---
layout: default
---

# 章節大綱

<div class="grid grid-cols-2 gap-6 mt-4">
<div>

**第一部分：執行緒基礎**
- Program / Process / Thread
- 多工作業 Multitasking
- Java 的多執行緒
- 執行緒的生命週期

**第二部分：建立執行緒**
- 建立執行緒（三種方法）
- Java 執行緒工作原理

</div>
<div>

**第三部分：執行緒控制**
- Thread.sleep()
- Thread.join()
- setPriority() 優先順序
- setDaemon() 守護執行緒

**第四部分：同步與進階**
- synchronized 同步
- 匿名類別
- 同步區塊
- 同步靜態方法
- Deadlock 死結
- wait() / notify()

</div>
</div>

<!--
【帶讀大綱】
今天的課程分成四個大區塊：先認識什麼是執行緒，再學怎麼把它們生出來。接著是怎麼控制它們（別讓它們亂跑），最後是最高難度的「同步機制」——這就是預防「多頭馬車」亂拉的關鍵。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
## 執行緒基礎

<!--
第一部分將介紹多執行緒的基礎概念，包括 Program、Process、Thread 的差異，以及多工作業的原理。
-->

---

# Program、Process、Thread

| 概念 | 說明 | 比喻 |
|------|------|------|
| **Program** | 儲存在磁碟上的靜態指令集合（.class 檔案） | 食譜書 |
| **Process** | 正在執行中的程式，擁有獨立的記憶體空間 | 正在做菜的廚房 |
| **Thread** | 行程內的最小執行單位，共享行程記憶體 | 廚房裡的廚師 |

<!--
【核心說明】
這三個詞很多人搞不清楚。

【比喻】
Program 就是你放在硬碟裡的「食譜」（靜態的，你不去煮它它就是張廢紙）。Process 是你「開火做菜的廚房」（動態的，佔用資源）。Thread 則是「廚房裡的廚師」。一個廚房可以有好幾個廚師，他們共用冰箱裡的食材，但每個人手上做的菜可能不一樣。
-->

---

# Process vs Thread

| 比較項目 | Process | Thread |
|---------|---------|--------|
| 記憶體 | 各自獨立 | 共享同一行程 |
| 建立成本 | 較高（重量級） | 較低（輕量級） |
| 通訊方式 | IPC（較複雜） | 直接存取共享變數 |
| 隔離性 | 完整隔離 | 需要同步保護 |

<!--
【比較項目】
記住：執行緒很輕量，生一個執行緒比開一個新的 Process（開一間新廚房）快多了。
-->

---

# 多工作業 Multitasking

**兩種多工類型：**

| 類型 | 說明 | 範例 |
|------|------|------|
| **Process-based** | 同時執行多個獨立程式 | 瀏覽器 + 文書軟體 + 音樂播放器 |
| **Thread-based** | 同一程式內同時執行多個任務 | 下載檔案同時更新進度條 |

**為何需要多執行緒？**

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>核心優勢：</b> 充分利用 CPU 多核心，提升應用程式回應性與吞吐量
</div>

**典型應用場景：**
- 網頁伺服器同時處理多個請求
- GUI 應用背景下載不阻塞畫面
- 資料庫並行查詢

<!--
【帶讀表格】
多工作業有兩種：一種是電腦同時跑很多軟體（Process-based），另一種是同一個軟體裡同時做好幾件事（Thread-based）。

【為何需要多執行緒？】
現在 CPU 都是 8 核心、16 核心。如果你只用單執行緒，那就像是你家有 8 個壯丁，結果只有一個人在幹活，其他 7 個都在旁邊滑手機。這不是浪費，這是犯罪！
-->

---

# Java 的多執行緒

```java
public class ThreadInfo {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("名稱: " + t.getName());
        System.out.println("優先: " + t.getPriority());
        System.out.println("狀態: " + t.getState());
    }
}
// 輸出：名稱: main / 優先: 5 / 狀態: RUNNABLE
```

| 相關 API | 說明 |
|---------|------|
| `java.lang.Thread` | 執行緒類別 |
| `java.lang.Runnable` | 任務介面（`@FunctionalInterface`） |
| `java.lang.Object` | `wait()` / `notify()` 方法 |
| `java.util.concurrent.*` | 進階並發工具（JDK 5+） |

<!--
【帶讀程式碼】
Java 從出生的第一天起就支援多執行緒。

【相關 API】
你看，連你最基本的 main 也是一個執行緒。Java 的 GC（垃圾回收）也是一個默默在背後幫你掃地的執行緒。它就像是家裡的掃地機器人，你不用管它，它就在那裡。
-->

---

# 執行緒的生命週期

```
NEW ──start()──▶ RUNNABLE ◀──────────────────┐
                    │                         │
              排程器選中                    notify()
                    ▼                         │
                RUNNING              WAITING/TIMED_WAITING
                    │                         │
        lock 競爭失敗                    wait()/join()
                    ▼                         │
                BLOCKED ──取得 lock──▶ RUNNABLE
                    │
            run() 結束
                    ▼
               TERMINATED
```

<!--
【核心說明】
這張圖是面試官的最愛！執行緒的一生。
-->

---

# 執行緒狀態說明

| 狀態 | 說明 | 觸發方式 |
|------|------|---------|
| `NEW` | 已建立但未啟動 | `new Thread()` |
| `RUNNABLE` | 可執行或執行中 | `start()` |
| `BLOCKED` | 等待取得 monitor lock | 競爭 `synchronized` |
| `WAITING` | 無限期等待其他執行緒 | `wait()`, `join()` |
| `TIMED_WAITING` | 限時等待 | `sleep(ms)` |
| `TERMINATED` | 執行完畢 | `run()` 結束 |

<!--
【狀態說明】
NEW 是剛出生，還沒呼吸。RUNNABLE 是準備好要工作了。WAITING 是在等別人通知。最機車的是 BLOCKED，這是在「搶廁所」，因為有人在裡面（鎖住了），你只能在門口等。

⚠️ 面試重點：
BLOCKED 是被動等待，WAITING 是主動睡覺。這點搞錯了，面試官會覺得你連廁所都搶不到。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
## 建立執行緒

<!--
第二部分介紹三種建立執行緒的方式：繼承 Thread 類別、實作 Runnable 介面，以及 Java 8 之後的 Lambda 表達式。
-->

---

# 方法一：繼承 Thread 類別

```java
class MyThread extends Thread {
    private String taskName;

    public MyThread(String name) { this.taskName = name; }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++)
            System.out.println(taskName + " - 步驟 " + i);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("執行緒A");
        MyThread t2 = new MyThread("執行緒B");
        t1.start();   // ← 呼叫 start()，不是 run()！
        t2.start();
    }
}
```

<div class="mt-3 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>常見錯誤：</b> 直接呼叫 <code>run()</code> 不會建立新執行緒，只是一般方法呼叫。必須呼叫 <code>start()</code>！
</div>

<!--
【帶讀程式碼】
第一種方法：繼承 Thread。這就像是你要當廚師，你得先繼承「廚師家族」的血統。

⚠️ 常見錯誤：
拜託！啟動執行緒是呼叫 start()，不是 run()！如果你呼叫 run()，那只是一般的方法呼叫，根本沒開新執行緒。這就像是你叫廚師「去做菜」，但他還在原地，根本沒去廚房。
-->

---

# 方法二：實作 Runnable 介面

```java
class PrintTask implements Runnable {
    private String message;

    public PrintTask(String msg) { this.message = msg; }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++)
            System.out.println(message + " #" + i);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Runnable task = new PrintTask("Hello");
        Thread t = new Thread(task);
        t.start();
    }
}
```

<div class="mt-3 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>推薦原因：</b> 實作介面不影響繼承關係；任務（Runnable）與執行機制（Thread）職責分離，設計更靈活
</div>

<!--
【帶讀程式碼】
第二種方法：實作 Runnable。這比繼承更好。

【推薦原因】
因為 Java 不能「多重繼承」。如果你已經是個「人類」了，你就不能再繼承「廚師」。但你可以「實作廚師介面」。這就像是你斜槓，既是工程師又是外送員。這在設計上靈活多了！
-->

---

# 方法三：Lambda 表達式（Java 8+）

最簡範例：

```java
Thread t = new Thread(() -> System.out.println("Lambda 執行緒！"));
t.start();
```

完整範例：

```java
Thread t1 = new Thread(() -> {
    for (int i = 1; i <= 3; i++)
        System.out.println("任務1 - " + i);
}, "Worker-1");

Thread t2 = new Thread(() -> {
    for (int i = 1; i <= 3; i++)
        System.out.println("任務2 - " + i);
}, "Worker-2");

t1.start();
t2.start();
```

<!--
【核心說明】
這就是現代人的寫法！

【逐步解說】
如果你只是要讓執行緒跑個簡單的任務，寫 Lambda 只要兩行。這就像是你去超市門口隨便抓個路人說：「去幫我買杯咖啡」，不用管他祖宗十八代是誰。
-->

---

# 三種方法比較

| 方法 | 繼承 Thread | 實作 Runnable | Lambda |
|------|------------|--------------|--------|
| Java 版本 | 全版本 | 全版本 | Java 8+ |
| 繼承限制 | 無法再繼承 | 無限制 | 無限制 |
| 程式碼量 | 多 | 中 | 少 |
| 任務/執行緒分離 | 否 | 是 | 是 |
| 適用場景 | 需覆寫 Thread 方法時 | 一般推薦 | 簡單任務 |

<div class="mt-4 p-3 bg-green-50 border-l-4 border-green-400 text-gray-700 text-sm text-left">
💡 <b>最佳實踐：</b> 優先使用 Lambda 或 Runnable；只有需要覆寫 <code>Thread</code> 的其他方法時才繼承
</div>

<!--
【帶讀表格】
三種方法怎麼選？

💡 最佳實踐：
優先用 Lambda 或 Runnable。只有當你真的想改寫 Thread 本身的行為時（比如你要當個特種廚師），才去繼承 Thread。
-->

---

# Java 執行緒的工作原理

每個執行緒有獨立的 Stack，但共享 Heap 中的物件：

```
┌─────────────────────────┐
│       JVM Process       │
│  ┌──────┐  ┌──────┐    │
│  │ t1   │  │ t2   │    │
│  │Stack │  │Stack │    │
│  └──┬───┘  └──┬───┘    │
│     │         │         │
│  ┌──▼─────────▼──────┐ │
│  │    Heap（共享）    │ │
│  │  shared variables  │ │
│  └────────────────────┘ │
└─────────────────────────┘
```

| 方法 | 說明 |
|------|------|
| `getName()` / `getId()` | 取得名稱 / ID |
| `getState()` | 取得目前狀態 |
| `isAlive()` | 是否仍在執行 |
| `Thread.currentThread()` | 取得目前執行緒 |

<!--
【核心說明】
這張圖解釋了為什麼多執行緒會出事。

【逐步解說】
每個廚師（執行緒）都有自己的小腦袋（Stack），用來存局部變數。但大家共用同一個大冰箱（Heap）。如果你們兩個廚師同時想拿冰箱裡的最後一顆蛋，但都沒跟對方說，結果蛋破了，菜也就毀了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
## 執行緒控制

<!--
第三部分介紹四個常用的執行緒控制方法：sleep()、join()、setPriority() 與 setDaemon()。
-->

---

# Thread.sleep() — 讓執行緒睡眠

最簡範例：

```java
Thread.sleep(1000);  // 暫停 1 秒（1000 毫秒）
```

完整倒數範例：

```java
public class SleepDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 5; i >= 1; i--) {
            System.out.println("倒數：" + i);
            Thread.sleep(1000);
        }
        System.out.println("發射！");
    }
}
```

<!--
【核心說明】
讓執行緒「去睡覺」。
-->

---

# Thread.sleep() — 注意事項

| 方法簽章 | 說明 |
|---------|------|
| `sleep(long millis)` | 暫停指定毫秒 |
| `sleep(long millis, int nanos)` | 精確到奈秒 |

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>注意：</b> <code>sleep()</code> 會拋出 <code>InterruptedException</code>，必須捕捉或向上宣告。睡眠中的執行緒<b>不會</b>釋放已持有的 lock。
</div>

<!--
⚠️ 注意：
sleep() 會拋出 InterruptedException。這就像是你睡覺時有人在耳邊吹哨子，你會被嚇醒，Java 會強迫你要處理這種被嚇醒的情況。

【關鍵差異】
睡覺的人是不會放開手上的「鎖」的！這就像是你拿著遙控器睡著了，別的人還是拿不到遙控器。
-->

---

# Thread.join() — 等待執行緒完成

最簡範例：

```java
t1.join();  // 等待 t1 結束後再繼續
```

完整範例：

```java
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("Worker 開始處理...");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Worker 完成！");
        });
        worker.start();
        worker.join();          // main thread 等待 worker 結束
        System.out.println("主程式繼續執行");
    }
}
```

| 方法簽章 | 說明 |
|---------|------|
| `join()` | 無限等待直到結束 |
| `join(long millis)` | 最多等待指定毫秒 |

<!--
【核心說明】
這叫「等等我」。

【逐步解說】
主程式（main）說：「我要等 worker 結束後才繼續」。這在要計算最後結果時很有用。就像你要等所有廚師都煮好菜，你才能開始上菜一樣。
-->

---

# setPriority() — 執行緒優先順序

```java
Thread t = new Thread(() -> System.out.println("執行中"));
t.setPriority(Thread.MAX_PRIORITY);  // 設為最高優先
t.start();
```

| 常數 | 值 | 說明 |
|------|-----|------|
| `Thread.MIN_PRIORITY` | 1 | 最低優先權 |
| `Thread.NORM_PRIORITY` | 5 | 預設優先權 |
| `Thread.MAX_PRIORITY` | 10 | 最高優先權 |

| 方法 | 說明 |
|------|------|
| `setPriority(int p)` | 設定優先權（1–10） |
| `getPriority()` | 取得目前優先權 |

<div class="mt-2 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>注意：</b> 優先權只是「建議」，JVM 和 OS 不保證嚴格按優先權排程。不同作業系統的行為可能不同，請勿依賴優先權做程式邏輯判斷。
</div>

<!--
【核心說明】
執行緒的「權級制」。

⚠️ 注意：
這只是「建議」。排程器（OS）心情好會聽你的，心情不好它根本不理你。所以別指望設個 MAX_PRIORITY 你的程式就會飛快，它可能只是稍微大聲一點點而已。
-->

---

# setDaemon() — 守護執行緒

最簡範例：

```java
Thread t = new Thread(() -> { /* 背景任務 */ });
t.setDaemon(true);  // 必須在 start() 前設定
t.start();
```

完整範例：

```java
Thread daemon = new Thread(() -> {
    while (true) {
        System.out.println("Daemon 監控中...");
        try { Thread.sleep(500); } catch (InterruptedException e) { break; }
    }
});
daemon.setDaemon(true);
daemon.start();
Thread.sleep(1500);
System.out.println("Main 結束，Daemon 自動停止");
```

<!--
【為什麼需要 Daemon？】
沒有 Daemon 的世界：你的程式「邏輯上已完成」，但因為有個背景執行緒還在無限迴圈，JVM 拒絕退出。使用者的程式就卡在那裡不結束。

心跳偵測、定時監控、日誌寫入這類任務本身沒有「完成」的概念，是無限迴圈。若不標記為 Daemon，主程式結束後它們還活著，JVM 永遠不退。
-->

---

# setDaemon() — 說明

| 類型 | 說明 |
|------|------|
| **User 執行緒** | 一般執行緒，JVM 等待所有 User 執行緒結束才退出 |
| **Daemon 執行緒** | 背景執行緒，所有 User 執行緒結束後 JVM 自動終止它 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>Daemon 執行緒：</b> 當所有 User 執行緒結束，JVM 會自動終止所有 Daemon 執行緒並退出。GC 就是最典型的 Daemon 執行緒。
</div>

<!--
【實務應用場景】
心跳 / Ping 監控：每幾秒 ping 一次遠端服務，主程式結束就不需要了。
背景日誌刷寫：定期把 buffer 裡的 log flush 到磁碟。
連線池健康檢查：定時驗證 DB 連線是否仍有效（HikariCP 內部就這樣做）。
JVM GC：Java 內建，最典型的 Daemon。

【什麼時候不能用 Daemon？】
只要你的執行緒會寫資料庫、寫檔案、需要 commit transaction，就不能用 Daemon。
因為 Daemon 被強制殺死時不會執行 finally，資料可能寫到一半就斷掉。
口訣：「有 I/O 副作用的任務，永遠不要設成 Daemon。」

💡 一句話總結：
Daemon 是「服務主執行緒的工具人」，主人不在就沒存在意義。這就像是保全人員——當所有住戶都搬走了，保全自動下班回家，不會為了守護空屋繼續站崗。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
## 同步機制

<!--
第四部分是本章最重要的核心：當多個執行緒共享資源時，如何用同步機制保護資料的一致性。
-->

---

# 為什麼需要同步？

不安全的計數器（`count++` 是三個步驟：讀取 → 加 1 → 寫回）：

```java
class Counter {
    private int count = 0;
    public void increment() { count++; }  // 非原子操作！
}
```

```
Thread-1: 讀取 count=0
Thread-2: 讀取 count=0
Thread-1: 寫入 count=1
Thread-2: 寫入 count=1  ← 期望是 2，結果卻是 1！
```

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
🔴 <b>Race Condition（競爭條件）：</b> 多個執行緒同時存取並修改共享資料，執行結果依賴執行順序，導致不可預測的錯誤。
</div>

<!--
【核心說明】
這就是「慘案現場」。

【逐步解說】
count++ 看似一行程式，實際上是三步：看一眼、加一、寫回去。如果兩個人同時「看一眼」都看到 0，那最後寫回去的都是 1。結果本來應該是 2 的，硬生生少了一半。這就是「競爭條件」，也是你錢包裡的錢莫名其妙消失的原因（誤）。
-->

---

# synchronized 關鍵字

修正後的安全計數器：

```java
class SafeCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;  // 同一時刻只有一個執行緒能進入
    }

    public synchronized int getCount() { return count; }
}
```

```java
SafeCounter counter = new SafeCounter();
Thread t1 = new Thread(() -> { for(int i=0; i<1000; i++) counter.increment(); });
Thread t2 = new Thread(() -> { for(int i=0; i<1000; i++) counter.increment(); });
t1.start(); t2.start();
t1.join();  t2.join();
System.out.println("結果: " + counter.getCount()); // 必定是 2000
```

<!--
【核心說明】
給門上鎖！

【逐步解說】
加上 synchronized，這間房子（方法）一次只能進去一個人。當 T1 在裡面加一的時候，T2 只能在門口乖乖排隊。這樣資料就絕對安全了。
-->

---

# 匿名類別 Anonymous Class

不需要另外定義類別，直接在使用處定義並建立物件：

```java
// 用匿名類別實作 Runnable
Thread t = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("匿名類別執行緒：" +
            Thread.currentThread().getName());
    }
});
t.start();
```

```java
// Lambda 等效寫法（Java 8+）
Thread t2 = new Thread(
    () -> System.out.println("Lambda 執行緒")
);
t2.start();
```

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>比較：</b> 匿名類別 vs Lambda — 兩者效果相同，但 Lambda 語法更簡潔。Java 8 後推薦使用 Lambda。
</div>

<!--
【核心說明】
如果你懶得幫類別取名字，就用匿名類別。

💡 比較：
現在我們都用 Lambda 了。除非你是在看阿公級的程式碼，否則你應該很少看到這種寫法。
-->

---

# 同步區塊 Synchronized Block

比同步方法更精細，只鎖定關鍵區域：

```java
class PartialSync {
    private int shared = 0;
    private final Object lock = new Object();

    public void process() {
        // 非同步的準備工作（可並行執行）
        System.out.println("準備中...");

        synchronized (lock) {   // 只對關鍵部分加鎖
            shared++;
            System.out.println("共享值：" + shared);
        }

        System.out.println("收尾中...");
    }
}
```

<!--
【核心說明】
「精準上鎖」。
-->

---

# 同步區塊 — 比較

| 比較 | synchronized 方法 | synchronized 區塊 |
|------|-----------------|-----------------|
| 鎖定範圍 | 整個方法 | 指定區塊 |
| 效能 | 較低（鎖定時間長） | 較高（縮小臨界區） |
| 鎖定物件 | `this`（實例方法） | 自訂物件 |

<!--
【逐步解說】
如果你整個方法有一百行，只有一行需要保護，你卻鎖了整整一百行，那效率會慢得跟蝸牛一樣。用「同步區塊」只鎖那一行，其他九十九行大家還是可以一起跑。這叫「高效率封裝」。
-->

---

# 同步靜態方法

靜態方法的 synchronized 使用 **Class 層級的鎖**（不是 this）：

```java
class ClassLevelSync {
    private static int instanceCount = 0;

    // 鎖定的是 ClassLevelSync.class，所有實例共用同一把鎖
    public static synchronized void register() {
        instanceCount++;
    }

    public static synchronized int getCount() {
        return instanceCount;
    }
}
```

| 比較 | 實例方法 synchronized | 靜態方法 synchronized |
|------|---------------------|---------------------|
| 鎖 | `this`（物件本身） | `ClassName.class`（Class 物件） |
| 不同物件 | 不同鎖，可並行 | 全局同一把鎖 |
| 保護目標 | 實例狀態 | 靜態（類別層級）狀態 |

<!--
【核心說明】
如果是 static 方法，鎖的就不是「這個物件」，而是「這整種類別」。

【比較項目】
這就像是你要鎖的不是「這台提款機」，而是「整間銀行」。所有分行（物件）都要看這把鎖的臉色。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
## 進階議題

<!--
第五部分探討兩個進階主題：死結（Deadlock）的成因與預防，以及執行緒間通信的 wait/notify 機制。
-->

---

# 死結 Deadlock — 成因

死結發生的四個必要條件（全部同時成立才發生）：

| 條件 | 說明 |
|------|------|
| **互斥** | 資源同時只能被一個執行緒持有 |
| **持有並等待** | 執行緒持有資源同時等待其他資源 |
| **不可搶佔** | 資源只能由持有者主動釋放 |
| **循環等待** | 執行緒形成循環等待鏈 |

```
Thread-1 持有 lockA，等待 lockB
Thread-2 持有 lockB，等待 lockA
              ↕ 互相等待，永遠不會結束
```

<div class="mt-3 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
🔴 <b>Deadlock：</b> 所有涉及的執行緒都無法繼續執行，程式進入永久停止狀態。
</div>

<!--
【核心說明】
這是工程師的噩夢：死結。

【情境說明】
T1 拿著左腳鞋子等右腳，T2 拿著右腳鞋子等左腳。兩個人都堅持不放手，結果誰都沒鞋穿。這就叫死結。程式會在那裡永恆地發呆，直到你把它強制關掉。
-->

---

# Deadlock — 程式碼示範

```java
class DeadlockDemo {
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("T1 取得 lockA");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lockB) { System.out.println("T1 取得 lockB"); }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lockB) {              // 順序相反！
                System.out.println("T2 取得 lockB");
                synchronized (lockA) { System.out.println("T2 取得 lockA"); }
            }
        });
        t1.start(); t2.start();
    }
}
```

**預防方式：** 兩個執行緒都改成先取 `lockA` 再取 `lockB`（固定鎖的獲取順序）

<!--
【程式碼示範】
這段程式碼就是「互不相讓」的典範。

【預防方式】
最簡單的方法：大家約定好，所有人都要先拿 lockA，再拿 lockB。順序一樣，就不會打結了。
-->

---

# wait() / notify() / notifyAll()

這三個方法定義在 `Object` 類別，**必須在 synchronized 區塊內使用**：

| 方法 | 說明 |
|------|------|
| `wait()` | 釋放 lock，進入 WAITING 狀態等待通知 |
| `wait(long ms)` | 釋放 lock，最多等待指定毫秒 |
| `notify()` | 喚醒一個正在 wait 的執行緒（隨機選擇） |
| `notifyAll()` | 喚醒所有正在 wait 的執行緒 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>與 sleep() 的差異：</b><br>
<code>sleep()</code>：不釋放 lock，時間到自動恢復<br>
<code>wait()</code>：釋放 lock，需要別人 <code>notify()</code> 才能恢復
</div>

<!--
【核心說明】
執行緒間的「傳聲筒」。

【方法說明】
wait() 是「我累了，你們好了再叫我」。notify() 是「喂！有人做好了，快起來工作！」

💡 注意：
wait() 跟 sleep() 不同，wait() 會「放開鎖」！這就像是你把遙控器放下才去睡覺，別人才有機會轉台。
-->

---

# 生產者—消費者模式

```java
class SharedBuffer {
    private int data = -1;
    private boolean hasData = false;

    public synchronized void produce(int val) throws InterruptedException {
        while (hasData) wait();      // 已有資料，等消費
        data = val;
        hasData = true;
        System.out.println("生產: " + val);
        notifyAll();                 // 通知消費者
    }

    public synchronized int consume() throws InterruptedException {
        while (!hasData) wait();     // 沒有資料，等生產
        hasData = false;
        System.out.println("消費: " + data);
        notifyAll();                 // 通知生產者
        return data;
    }
}
```

<!--
【核心說明】
經典的「生產者與消費者」大戰。
-->

---

# 生產者—消費者模式 — 最佳實踐

<div class="mt-4 p-4 bg-green-50 border-l-4 border-green-400 text-gray-700 text-left">
💡 <b>最佳實踐：</b> 用 <code>while</code> 而非 <code>if</code> 包住 <code>wait()</code>，防止虛假喚醒（Spurious Wakeup）。
</div>

| 寫法 | 行為 |
|------|------|
| `if (!hasData) wait()` | 喚醒後直接繼續，不再確認條件 |
| `while (!hasData) wait()` | 喚醒後重新檢查條件，安全 ✅ |

<!--
【最佳實踐】
一定要用 while 來包住 wait()。因為有時候執行緒會「夢遊」（虛假喚醒），醒來發現還沒東西，得繼續睡。if 只檢查一次，while 每次醒來都再確認，才是真正的防彈寫法。
-->

---
layout: default
---

# 練習一：計時器執行緒
### 任務說明

設計一個多執行緒計時器應用程式：

1. 建立一個 `CountdownTask` 類別，實作 `Runnable` 介面
2. 建構子接受整數 `seconds`（倒數秒數）和 `String name`（計時器名稱）
3. `run()` 方法每隔 1 秒印出 `"[name] 剩餘 X 秒"`，結束時印出 `"[name] 計時完成！"`
4. 在 `main` 中同時啟動 3 個不同秒數的計時器（3 秒、5 秒、7 秒）
5. 使用 `join()` 等待所有計時器完成後印出 `"所有計時器完成"`

<!--
【出題前的鋪陳】
來做一個倒數計時器。

【問題引導】
啟動三個執行緒同時倒數。記得用 join()，別讓主程式不等它們就自己先跑去慶功了。
-->

---

# 練習一：解題提示

**① Runnable 骨架：**

```java
class CountdownTask implements Runnable {
    private int seconds;
    private String name;
    public void run() { /* 迴圈 + sleep(1000) */ }
}
```

**② sleep() 的例外處理：** 在 `run()` 內用 try-catch 包住 `Thread.sleep(1000)`

**③ 建立並啟動：**

```java
Thread t1 = new Thread(new CountdownTask(3, "Timer-3"));
t1.start();
```

**④ join() 的位置：** 所有 `start()` 後，再依序 `t1.join(); t2.join(); t3.join();`

**⑤ 進階挑戰：** 改用 Lambda 實作同樣功能

<!--
[依脈絡推斷]
引導學員注意 join() 必須在所有 start() 之後才呼叫，而不是 start() 後立刻 join()（那樣就變成循序執行，失去並行效果）。
-->

---
layout: default
---

# 練習二：安全的銀行帳戶
### 任務說明

模擬多執行緒存提款，練習同步機制：

1. 建立 `BankAccount` 類別，包含 `balance`（餘額）欄位
2. 實作 `synchronized` 的 `deposit(int amount)` 和 `withdraw(int amount)` 方法
   - `withdraw` 若餘額不足，印出警告並返回 `false`
3. 初始餘額設為 1000
4. 建立 5 個「存款執行緒」（各存 200）和 5 個「提款執行緒」（各提 300）
5. 所有執行緒完成後，印出最終餘額

**驗證：** 先去掉 `synchronized` 執行幾次，觀察不穩定的結果，再加回去驗證一致性。

<!--
【出題前的鋪陳】
模擬銀行存提款，這最能看出你有沒有上鎖。

【驗證】
試試看如果不加 synchronized 會發生什麼事？你會發現錢可能多出來，也可能變少。這在現實中叫「洗錢」或「虧空公款」，在程式裡叫 Bug。
-->

---

# 練習二：解題提示

**① BankAccount 骨架：**

```java
class BankAccount {
    private int balance;
    public synchronized void deposit(int amount) { balance += amount; }
    public synchronized boolean withdraw(int amount) {
        if (balance < amount) { System.out.println("餘額不足"); return false; }
        balance -= amount;
        return true;
    }
    public synchronized int getBalance() { return balance; }
}
```

<!--
[依脈絡推斷]
可以請學員計算理論上的「最大可能提款次數」：1000 + 5×200 = 2000，2000 / 300 ≈ 6 次成功提款。
-->

---

# 練習二：解題提示（續）

**② 建立多個執行緒：**

```java
Thread[] threads = new Thread[10];
for (int i = 0; i < 5; i++) {
    threads[i]   = new Thread(() -> account.deposit(200));
    threads[i+5] = new Thread(() -> account.withdraw(300));
}
```

**③ 等待所有執行緒：** 用 for 迴圈對每個 thread 呼叫 `join()`

<!--
[依脈絡推斷]
注意 Lambda 裡的 account 必須是 effectively final（在迴圈外宣告好再傳入）。
-->

---
layout: default
---

# 練習三：生產者消費者佇列
### 任務說明

實作一個有界緩衝區（Bounded Buffer）的生產者消費者模式：

1. 建立 `BoundedQueue` 類別，內部使用 `LinkedList<Integer>`，最大容量為 5
2. 實作 `put(int item)`：若佇列滿，呼叫 `wait()` 等待；否則加入並呼叫 `notifyAll()`
3. 實作 `take()`：若佇列空，呼叫 `wait()` 等待；否則取出並呼叫 `notifyAll()`
4. 建立 2 個生產者（各生產 10 個數字）和 3 個消費者
5. 生產者完成後傳送結束訊號（-1）給消費者

**重點觀察：** 佇列永遠不會超過 5 個元素；程式能正常結束（不會永遠 hang 住）

<!--
【出題前的鋪陳】
這是這學期最難的一題：生產者與消費者。

【重點觀察】
看看你的倉庫是不是永遠控制在 5 個以內。學會用 wait 和 notifyAll，你就是多執行緒大師了！
-->

---

# 練習三：解題提示

```java
class BoundedQueue {
    private LinkedList<Integer> queue = new LinkedList<>();
    private int capacity;
    public BoundedQueue(int cap) { this.capacity = cap; }

    public synchronized void put(int item) throws InterruptedException {
        while (queue.size() == capacity) wait();
        queue.addLast(item);
        notifyAll();
    }

    public synchronized int take() throws InterruptedException {
        while (queue.isEmpty()) wait();
        int val = queue.removeFirst();
        notifyAll();
        return val;
    }
}
```

<!--
[依脈絡推斷]
注意 put 和 take 都需要 synchronized，而且 wait() 必須在 synchronized 區塊內才能呼叫。
-->

---

# 練習三：解題提示（續）

**重點：**
- `wait()` 一定要用 `while` 包住，防止虛假喚醒
- 結束訊號：生產完後 `put(-1)`，消費者取到 -1 時停止
- 使用 `notifyAll()` 比 `notify()` 更安全

<div class="mt-4 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>為什麼用 notifyAll() 而非 notify()？</b><br>
<code>notify()</code> 可能喚醒另一個「消費者」而非「生產者」，造成所有執行緒卡在 <code>wait()</code>，形成隱性死結。
</div>

<!--
[依脈絡推斷]
notifyAll() 比 notify() 更安全，因為 notify() 可能喚醒的是另一個消費者（而非生產者），導致所有執行緒都繼續 wait()，形成隱性死結。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【收尾】
今天我們學會了怎麼開影分身，還學會了怎麼不讓分身打架。

【核心帶走重點】
記得：不要直接呼叫 run()，要用 start()；只要有共享資源，一定要考慮 synchronized；看到死結，檢查一下拿鎖的順序。有問題快問，沒問題就回家休息吧！
-->

---
layout: end
---

# 課程結束
### 多執行緒：建立、控制、同步、通信

<!--
【結束語】
掌控執行緒，你就能讓程式飛起來。下課！
-->

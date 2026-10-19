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
大家好，這一章的主題是多執行緒。

用手機下載檔案的時候，我們還是可以滑動畫面、點選其他按鈕，因為下載和畫面更新，是同時在進行的兩件事。如果程式一次只能做一件事，下載的時候整個畫面就會卡住。

多執行緒就是讓程式同時做很多事的技術。學完這一章，我們會知道怎麼建立和控制執行緒，以及多個執行緒共用資料時，要怎麼避免出錯。
-->

---
layout: default
---

# 章節大綱

<div class="grid grid-cols-2 gap-6 mt-4">
<div>

**執行緒基礎**
- Program / Process / Thread
- 多工作業 Multitasking
- Java 的多執行緒
- 執行緒的生命週期

**建立執行緒**
- 建立執行緒（三種方法）
- Java 執行緒工作原理

</div>
<div>

**執行緒控制**
- Thread.sleep()
- Thread.join()
- setPriority() 優先順序
- setDaemon() 守護執行緒

**同步與進階**
- synchronized 同步
- 匿名類別
- 同步區塊
- 同步靜態方法
- Deadlock 死結
- wait() / notify()

</div>
</div>

<!--
這一章分成四個部分。

第一部分是執行緒的基礎觀念，包括程式、行程和執行緒的差別，以及執行緒的生命週期。第二部分學建立執行緒的三種方法。第三部分是控制執行緒，包括 sleep、join、優先順序和守護執行緒。

第四部分是同步機制和進階議題，包括 synchronized、死結，以及用 wait 和 notify 讓執行緒互相溝通。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 執行緒基礎

<!--
我們先從最基礎的觀念開始：到底什麼是執行緒。
-->

---

# Program、Process、Thread

| 概念 | 說明 | 比喻 |
|------|------|------|
| **Program** | 儲存在磁碟上的靜態指令集合（.class 檔案） | 食譜書 |
| **Process** | 正在執行中的程式，擁有獨立的記憶體空間 | 正在做菜的廚房 |
| **Thread** | 行程內的最小執行單位，共享行程記憶體 | 廚房裡的廚師 |

<!--
有三個名詞要先分清楚，我們用廚房來比喻。

Program，程式，是存在磁碟上的指令集合，就像一本食譜，放著不會有任何動作。Process，行程，是正在執行中的程式，它有自己獨立的記憶體空間，就像一間正在做菜的廚房。Thread，執行緒，是行程裡最小的執行單位，就像廚房裡的廚師。

一間廚房裡可以有好幾位廚師同時工作，而且他們共用同一間廚房的食材和工具。
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
這張表比較了行程和執行緒。

每個行程有各自獨立的記憶體；同一個行程裡的執行緒，則共用這個行程的記憶體。建立行程的成本比較高，執行緒比較輕量。行程之間要溝通比較複雜；執行緒可以直接存取共用的變數。

但共用也有代價：行程之間完全隔離，彼此不會互相干擾；執行緒共用資料，就需要同步保護，否則會互相干擾。這是這一章後半段的重點。
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
多工作業分成兩種。

一種是以行程為單位，同時執行好幾個獨立的程式，例如一邊開瀏覽器，一邊聽音樂。另一種是以執行緒為單位，在同一個程式裡，同時執行好幾個任務，例如下載檔案的同時，更新進度條。

為什麼需要多執行緒？現在的電腦都有多個 CPU 核心，多執行緒可以充分利用它們，讓程式反應更快、處理量更大。

常見的應用包括：網頁伺服器同時處理很多使用者的請求、圖形介面在背景下載而不卡住畫面，以及資料庫同時處理多個查詢。
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
其實每個 Java 程式，至少都有一個執行緒，就是執行 main 方法的那一個。

看範例，Thread.currentThread 會取得目前正在執行的執行緒。印出它的資訊：名稱是 main，優先順序是 5，狀態是 RUNNABLE，代表正在執行。

下面列出了相關的 API：Thread 是執行緒類別，Runnable 是代表任務的介面，wait 和 notify 方法定義在 Object 類別裡，java.util.concurrent 套件則提供了更進階的並行工具。
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
執行緒從建立到結束，會經過好幾個狀態，這叫做生命週期。

剛 new 出來的時候是 NEW。呼叫 start 之後，變成 RUNNABLE，等待排程器分配 CPU 執行。執行中如果搶不到鎖，會進入 BLOCKED；呼叫了 wait 或 join，會進入等待狀態，直到被通知或等待的對象結束，才回到 RUNNABLE。

最後，run 方法執行完畢，就進入 TERMINATED，執行緒結束。
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
這張表整理了每個狀態的意義和觸發方式。

NEW 是已經建立但還沒啟動。RUNNABLE 是可以執行或正在執行。BLOCKED 是等待取得同步的鎖。WAITING 是無限期地等待其他執行緒，例如呼叫了 wait 或 join。TIMED_WAITING 是有時間限制的等待，例如 sleep。TERMINATED 則是已經執行完畢。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 建立執行緒

<!--
了解了基本觀念，接下來我們看怎麼建立執行緒，一共有三種方法。
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
第一種方法，是繼承 Thread 類別。

MyThread 繼承 Thread，並覆寫 run 方法，裡面寫的就是這個執行緒要做的工作：印出三個步驟。

在 main 裡建立兩個執行緒，再呼叫 start 啟動它們。兩個執行緒會同時執行，所以輸出的順序可能每次都不一樣。

下面的警告非常重要：「啟動執行緒要呼叫 start，不是 run。」直接呼叫 run，只是一般的方法呼叫，並不會建立新的執行緒，工作還是在 main 執行緒裡一個一個完成。
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
第二種方法，是實作 Runnable 介面。

PrintTask 實作 Runnable，在 run 方法裡寫要做的工作。使用時，先建立 PrintTask 物件，再把它交給 new Thread，最後呼叫 start。

這是比較推薦的做法，原因有兩個。第一，Java 只能繼承一個類別，實作介面就不會佔用繼承的機會。第二，任務本身和執行它的機制分開了，Runnable 只負責「要做什麼」，Thread 負責「怎麼執行」，設計更有彈性。
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
因為 Runnable 是一個功能介面，所以從 Java 8 開始，可以直接用 Lambda 來寫，這是第三種方法。

最簡單的寫法，只要一行，把要執行的程式碼寫在箭頭後面，交給 new Thread 就好。

下面是完整的範例，兩個執行緒各自用 Lambda 定義要做的工作，第二個參數則是幫執行緒取名字。名字在除錯的時候很有用。
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
這張表比較了三種建立執行緒的方法。

繼承 Thread，所有版本都能用，但這個類別就不能再繼承其他類別，程式碼也比較多。實作 Runnable，沒有繼承的限制，任務和執行緒分離，是一般推薦的做法。Lambda 需要 Java 8 以上，程式碼最少，適合簡單的任務。

所以最佳實踐是：優先使用 Lambda 或 Runnable，只有需要覆寫 Thread 其他方法時，才用繼承。
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
我們看看執行緒在 JVM 裡是怎麼運作的。

每個執行緒都有自己獨立的 Stack，用來存放自己的區域變數和方法呼叫。但所有執行緒，共用同一個 Heap，也就是物件存放的地方。

這就是為什麼執行緒之間可以直接共享資料，也是為什麼需要小心同步。

下面列出了一些常用的方法：取得名稱和 ID、取得目前的狀態、判斷是否還在執行，以及取得目前的執行緒。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 執行緒控制

<!--
接下來，我們看看怎麼控制執行緒的執行。
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
Thread.sleep 會讓目前的執行緒暫停一段時間，單位是毫秒，所以 1000 就是 1 秒。

看完整的倒數範例，迴圈從 5 倒數到 1，每印出一個數字，就 sleep 1 秒，最後印出發射。執行時，就會看到數字一秒一秒地跳出來。
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
sleep 有兩個版本：一個只指定毫秒，另一個可以精確到奈秒。

使用時要注意兩件事。第一，sleep 會拋出 InterruptedException，這是一個檢查異常，所以一定要捕捉，或是在方法上宣告 throws。第二，執行緒在睡眠的時候，不會釋放它已經取得的鎖，這一點在學到同步時很重要。
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
join 可以讓目前的執行緒，等待另一個執行緒完成之後，再繼續執行。

看範例，worker 執行緒會先印出開始處理，睡 2 秒，再印出完成。main 在啟動 worker 之後，呼叫 worker.join，就會停下來等它。

所以輸出的順序一定是：Worker 開始處理、Worker 完成，最後才是主程式繼續執行。如果沒有 join，主程式那一行可能會先印出來。

join 也可以指定最多等待幾毫秒，時間到了就不再等。
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
每個執行緒都有一個優先順序，範圍是 1 到 10。

MIN_PRIORITY 是 1，最低；NORM_PRIORITY 是 5，也是預設值；MAX_PRIORITY 是 10，最高。可以用 setPriority 設定，用 getPriority 取得。

但要特別注意，優先順序只是給系統的「建議」，JVM 和作業系統不保證一定照著執行，不同的作業系統表現也可能不同。所以千萬不要依賴優先順序，來決定程式的邏輯。
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
守護執行緒，是在背景執行輔助工作的執行緒。設定的方式，是在 start 之前，呼叫 setDaemon true。

看完整的範例，這個守護執行緒在一個無限迴圈裡，每 0.5 秒印出一次監控中。main 執行緒睡 1.5 秒之後結束。

這時候，雖然守護執行緒的迴圈永遠不會自己停下來，但 main 一結束，它也會跟著被自動終止。
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
這一頁說明了一般執行緒和守護執行緒的差別。

一般執行緒，又叫 User 執行緒，JVM 會等所有的 User 執行緒都結束，才會退出程式。守護執行緒則是在背景服務的，當所有 User 執行緒都結束了，JVM 就會自動終止所有的守護執行緒，然後退出。

最典型的守護執行緒，就是負責垃圾回收的 GC。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 同步機制

<!--
接下來是這一章最重要的部分：同步機制。
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
我們先看為什麼需要同步。

這個 Counter 的 increment 方法，看起來只有 count 加加一行。但實際上，它包含三個步驟：讀取 count、加 1，再寫回去。

如果兩個執行緒同時執行：執行緒 1 讀到 0，執行緒 2 也讀到 0；接著執行緒 1 寫入 1，執行緒 2 也寫入 1。明明加了兩次，結果卻是 1。

這種多個執行緒同時修改共用資料，結果取決於執行順序的問題，叫做「競爭條件」，英文是 Race Condition。
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
解決的方法，是在方法上加上 synchronized 關鍵字。

被 synchronized 修飾的方法，同一時間只有一個執行緒可以進入，其他執行緒必須在外面排隊等待。就像只有一間的廁所，有人在使用時，門就會鎖上，其他人只能在外面等。

看下面的測試，兩個執行緒各自呼叫 increment 一千次，再用 join 等它們都完成。因為加了 synchronized，最後的結果一定是 2000。如果拿掉 synchronized，多執行幾次，就會看到小於 2000 的結果。
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
在建立執行緒時，也常常會用到匿名類別。

看第一段，直接在 new Thread 的括號裡，new 一個 Runnable，並在大括號裡實作 run 方法，不需要另外定義一個類別。

第二段是等效的 Lambda 寫法，只要一行。兩者的效果完全一樣，但 Lambda 簡潔多了，所以 Java 8 之後，建議使用 Lambda。
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
synchronized 方法會鎖住整個方法，但有時候方法裡只有一小段，需要保護。這時候可以用同步區塊，只鎖住關鍵的部分。

看範例，process 方法裡，準備和收尾的工作不涉及共用資料，可以同時執行。只有修改 shared 的那一段，用 synchronized 括號 lock 包起來，同一時間只允許一個執行緒進入。

括號裡的 lock，是用來當作鎖的物件。
-->

---

# 同步區塊 — 比較

| 比較 | synchronized 方法 | synchronized 區塊 |
|------|-----------------|-----------------|
| 鎖定範圍 | 整個方法 | 指定區塊 |
| 效能 | 較低（鎖定時間長） | 較高（縮小臨界區） |
| 鎖定物件 | `this`（實例方法） | 自訂物件 |

<!--
這張表比較了同步方法和同步區塊。

同步方法鎖住的是整個方法，同步區塊只鎖住指定的區域。因為鎖住的時間比較短，同步區塊的效能比較好。另外，同步方法使用的鎖是物件本身，也就是 this；同步區塊則可以指定自己的鎖物件。

所以實務上，盡量縮小被鎖住的範圍。
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
靜態方法也可以加上 synchronized，但它使用的是類別層級的鎖，而不是 this。

看範例，register 和 getCount 都是靜態的同步方法，它們鎖的是 ClassLevelSync 這個類別本身，所有的物件共用同一把鎖。

表格整理了兩者的差別：實例方法的鎖是物件本身，不同的物件有不同的鎖，可以同時執行；靜態方法的鎖是整個類別，全域只有一把，用來保護靜態的共用資料。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階議題

<!--
接下來是兩個進階的議題：死結，以及執行緒之間的溝通。
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
使用鎖的時候，要小心一種狀況，叫做死結，英文是 Deadlock。

想像一下，執行緒 1 拿著鎖 A，在等鎖 B；執行緒 2 拿著鎖 B，在等鎖 A。兩個人都在等對方先放手，結果誰都沒辦法繼續，程式就永遠停在那裡。

死結發生，需要四個條件同時成立：資源同時只能被一個執行緒持有；執行緒拿著資源，又在等其他資源；資源只能由持有者主動釋放；以及執行緒之間形成循環等待。只要破壞其中一個條件，就能避免死結。
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
我們看一個實際會發生死結的程式。

t1 先取得 lockA，睡一下，再嘗試取得 lockB。t2 則是先取得 lockB，再嘗試取得 lockA，順序剛好相反。

執行時，t1 拿到 lockA，t2 拿到 lockB，接著兩個都在等對方的鎖，程式就卡住了。

預防的方法很簡單：讓所有執行緒都用相同的順序取得鎖，例如都先拿 lockA、再拿 lockB，就不會形成循環等待。
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
當執行緒之間需要互相配合時，就要用到 wait、notify 和 notifyAll。這三個方法定義在 Object 類別裡，而且一定要在 synchronized 區塊裡使用。

wait 會釋放鎖，進入等待狀態，直到被通知；也可以指定最多等待幾毫秒。notify 會喚醒一個正在等待的執行緒，notifyAll 則會喚醒所有正在等待的執行緒。

下面比較了 wait 和 sleep：sleep 不會釋放鎖，時間到就自動恢復；wait 會釋放鎖，需要別人 notify 才會恢復。
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
wait 和 notify 最經典的應用，是生產者和消費者模式。

SharedBuffer 是共用的緩衝區。produce 方法裡，如果已經有資料，就 wait，等消費者取走；否則放入資料，並用 notifyAll 通知消費者。consume 方法裡，如果沒有資料，就 wait，等生產者放入；否則取出資料，並通知生產者。

就像餐廳的出菜口：廚師做好菜放上去，服務生拿走；出菜口滿了，廚師就等一下；出菜口空了，服務生就等一下。
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
使用 wait 時有一個重要的最佳實踐：要用 while 包住 wait，而不是 if。

因為執行緒有時候會在沒有被通知的情況下醒來，這叫做「虛假喚醒」。用 if 的話，醒來之後就直接往下執行，不會再確認條件；用 while 的話，醒來之後會重新檢查條件，條件還不成立就繼續等，比較安全。
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
接下來我們動手練習。

第一題是計時器執行緒。建立一個 CountdownTask 類別，實作 Runnable，接收倒數的秒數和計時器名稱。run 方法每隔 1 秒，印出剩餘幾秒，結束時印出計時完成。

在 main 裡同時啟動 3 秒、5 秒和 7 秒三個計時器，用 join 等它們全部完成，再印出所有計時器完成。

我們先暫停一下，自己動手試試看。

【講師】給學生約 15 分鐘實作。
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
我們來看提示。

CountdownTask 有秒數和名稱兩個欄位，run 方法用迴圈搭配 sleep 1000。因為 run 不能宣告 throws，所以 sleep 的例外要在 run 裡面用 try-catch 處理。

建立時，把 CountdownTask 交給 new Thread，再呼叫 start。注意 join 的位置：要先把三個執行緒都 start，再依序 join。如果每 start 一個就馬上 join，三個計時器就會變成一個接一個執行，而不是同時進行。

進階挑戰，可以試著改用 Lambda 寫寫看。
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
第二題是安全的銀行帳戶。

建立 BankAccount 類別，有一個餘額欄位，以及加上 synchronized 的存款和提款方法；提款時如果餘額不足，就印出警告並回傳 false。初始餘額是 1000，建立 5 個各存 200 的存款執行緒，和 5 個各提 300 的提款執行緒，全部完成後印出最終餘額。

可以先拿掉 synchronized，多執行幾次，觀察結果是不是不穩定，再加回去驗證。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 15 分鐘實作。
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
我們來看提示，先看 BankAccount。

deposit、withdraw 和 getBalance 三個方法都加上了 synchronized。withdraw 裡先判斷餘額夠不夠，不夠就印出餘額不足並回傳 false，夠才扣款。

注意 getBalance 也要同步，這樣讀取到的才會是最新、正確的值。
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
接著建立執行緒。

用一個長度 10 的陣列存放執行緒，迴圈 5 次，每次建立一個存款執行緒和一個提款執行緒。全部啟動之後，再用迴圈對每個執行緒呼叫 join，等全部完成，再印出餘額。

因為執行緒的順序不固定，有時候提款會遇到餘額不足，所以最終餘額可能不同；但只要加上 synchronized，每一筆交易都會被正確地記錄，不會出現算錯的情況。
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
第三題是有界緩衝區的生產者消費者模式。

建立 BoundedQueue 類別，內部用 LinkedList 存放資料，最大容量是 5。put 方法在佇列滿的時候 wait，否則放入資料並 notifyAll；take 方法在佇列空的時候 wait，否則取出資料並 notifyAll。

建立 2 個生產者和 3 個消費者，生產完之後，送出結束訊號負 1，讓消費者知道可以停止。

觀察重點是：佇列裡永遠不會超過 5 個元素，而且程式能夠正常結束。

【講師】注意：3 個消費者需要收到 3 個結束訊號，若只由 2 個生產者各送一個 -1，會有一個消費者永遠等待。可讓學生自行發現並修正。
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
我們來看提示，這是 BoundedQueue 的寫法。

put 方法用 while 判斷佇列是不是滿了，滿了就 wait；有空間就把資料加到最後面，再 notifyAll。take 方法用 while 判斷佇列是不是空的，空的就 wait；有資料就從最前面取出，再 notifyAll。

兩個方法都是 synchronized，所以同一時間只有一個執行緒能修改佇列。
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
幾個重點再提醒一次。

wait 一定要用 while 包住，避免虛假喚醒。結束訊號的做法，是生產完之後 put 負 1，消費者取到負 1 就停止，要確認每個消費者都會收到結束訊號。

另外，這裡用 notifyAll 會比 notify 安全。因為 notify 只會喚醒一個執行緒，如果剛好喚醒了另一個消費者，而不是生產者，所有執行緒可能都會卡在 wait，形成一種隱性的死結。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會了讓程式同時做很多事。

執行緒是行程裡最小的執行單位，同一個行程的執行緒共用記憶體。建立執行緒有三種方法，推薦用 Runnable 或 Lambda，記得要呼叫 start。sleep 讓執行緒暫停，join 等待其他執行緒完成。

多個執行緒修改共用資料時，要用 synchronized 避免競爭條件；取得多個鎖時要固定順序，避免死結。執行緒之間則可以用 wait 和 notifyAll 互相配合。

【講師】開放 Q&A。
-->

---
layout: end
---

# 課程結束
### 多執行緒：建立、控制、同步、通信

<!--
建立、控制、同步、溝通，就是多執行緒的四個核心。

下一章，我們要學習檔案的輸入與輸出。我們下一章見。
-->

---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 迴圈控制
routeAlias: ch06
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
  <h1 style="color: #1a5c5c; font-size: 3.4rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">迴圈控制</h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">「讓程式學會重複：for、while 與迴圈控制技巧」</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
【開場白】
各位！聽說你們學會了怎麼讓程式「思考」了？太棒了。但如果你的程式只會思考一次，那它頂多是個哲學家。真正的工程師要讓程式「不知疲倦地幹活」。今天的主題是「迴圈」，也就是讓電腦做那些人類最討厭的、重複性的機械勞動。

【為什麼要學這個？】
想像一下，如果你要印 100 次 "我再也不敢在程式碼裡寫 Bug 了"，你是想複製貼上 100 次，還是寫三行程式碼搞定？迴圈就是你的「影分身之術」，讓電腦幫你爆肝，你負責喝咖啡。

【今天學完你會能做什麼】
學完這章，你能讓程式算出 1 加到 100 萬的總和、算出圓周率，甚至還能解決古代著名的「雞兔同籠」問題。
-->

---
layout: default
---

# Outline

- **6-1 for 迴圈** — 基本語法、流程、enhanced for-each
- **6-2 巢狀 for 迴圈** — 九九乘法表
- **6-3 while 迴圈**
- **6-4 巢狀 while 迴圈**
- **6-5 do-while 迴圈** — 至少執行一次
- **6-6 無限迴圈** — `while(true)`、`for(;;)`
- **6-7 break 敘述** — 跳出迴圈
- **6-8 continue 敘述** — 跳過本次迭代
- **6-9 迴圈標籤（label）** — 跳出多層迴圈
- **6-10 Scanner 輸入檢查** — while 驗證使用者輸入
- **6-11 迴圈應用** — 累加、計數、最大最小值
- **6-12 專題** — 圓周率估算、雞兔同籠、國王的麥粒

<!--
【核心說明】
這章我們要學會三種「重複」的方法。

【生活化比喻】
for 迴圈就像是「健身教練」，叫你深蹲 100 次，沒做完不准走。while 迴圈就像是「殭屍」，只要你還活著（條件成立），它就一直追著你。do-while 就像是「強迫推銷」，先讓你試用一次（執行），再問你要不要買（檢查條件）。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# for 迴圈

<!--
【開場白】
我們先從最有秩序的 for 迴圈開始。它就像是軍隊，每一步都算得精精確確。
-->

---
layout: default
---

# 6-1 for 迴圈語法

| 元素 | 說明 |
| --- | --- |
| `initialization` | 初始化計數變數，只執行一次 |
| `condition` | 每次執行前檢查；為 `false` 時停止 |
| `update` | 每次執行結束後更新計數變數 |

```java
for (initialization; condition; update) {
    // 重複執行的程式碼
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>執行順序：</b>初始化 → 條件判斷 → 程式碼 → 更新 → 條件判斷 → ... → 條件為 false 停止
</div>

<!--
【核心說明】
for 迴圈有三個大關卡。

【生活化比喻】
1. initialization：起跑線，決定從哪開始。2. condition：終點線，只要沒過線就要繼續跑。3. update：每跑一圈後的休息（或加速）。

⚠️ 學生常見誤解：
注意那個順序！它是「跑完一圈」才去「更新」，更新完才去「判斷」。別以為它是先更新再跑，那樣你會少跑一圈！
-->

---

# 6-1 for 迴圈範例

```java
for (int i = 1; i <= 5; i++) {
    System.out.print(i + " ");
}
// 輸出：1 2 3 4 5
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>計數方向：</b>遞增用 <code>i++</code>，遞減用 <code>i--</code>，步長可自訂，例如 <code>i += 2</code>
</div>

<!--
【逐步解說】
看這段簡單的程式碼。i = 1 開始，只要 i <= 5 就印出來，印完後 i++。這就是最標準的「數數」。

💼 業界實務：
在業界，我們習慣從 0 開始數。for (int i = 0; i < 5; i++)。這是一種文化，早點習慣才專業。
-->

---

# 6-1 Enhanced for-each 迴圈

| 元素 | 說明 |
| --- | --- |
| `dataType` | 陣列或集合中元素的型別 |
| `variable` | 每次迭代取得的元素 |
| `arrayOrCollection` | 要走訪的陣列或集合 |

```java
for (dataType variable : arrayOrCollection) {
    // 使用 variable
}
```

<!--
【核心說明】
這是 for 迴圈的「懶人包」版。

【生活化比喻】
傳統 for 就像是你去自助餐，得拿著盤子（索引）一個個夾。for-each 就像是服務員直接把菜送到你面前，你只要張嘴吃就好了。
-->

---

# 6-1 for-each 範例

```java
int[] scores = {85, 92, 78, 95, 88};

for (int score : scores) {
    System.out.print(score + " ");
}
// 輸出：85 92 78 95 88
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>使用時機：</b>for-each 只能「讀取」元素，不能修改陣列內容，也不提供索引值。需要索引時改用傳統 for 迴圈。
</div>

<!--
【逐步解說】
如果你只是想把陣列裡的分數印出來，用 for-each 最優雅。但注意，它是「唯讀」的。你不能在 for-each 裡叫服務員把菜換掉（修改元素），那是會被打的。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 巢狀 for 迴圈

<!--
【開場白】
接下來我們要玩「迴圈套迴圈」。這就像是電影《全面啟動》，你在夢裡的夢裡幹活。
-->

---
layout: default
---

# 6-2 巢狀 for 迴圈結構

| 元素 | 說明 |
| --- | --- |
| 外層迴圈 | 控制「列」，每執行一次，內層走完一輪 |
| 內層迴圈 | 控制「欄」，每次從頭開始計數 |
| 時間複雜度 | 外層 n 次 × 內層 m 次 = n × m 次 |

```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        System.out.print(i + "*" + j + "=" + (i*j) + "  ");
    }
    System.out.println();
}
```

<!--
【核心說明】
外層動一次，內層動全家。

【生活化比喻】
這就像是「時鐘」。外層迴圈是時針，內層迴圈是分針。分針走完一圈（60 分鐘），時針才跳一格。

⚠️ 學生常見誤解：
別套太多層！如果你寫了五層巢狀迴圈，你的電腦可能會發出哀鳴。這叫「維度詛咒」，效能會掉進黑洞。
-->

---

# 6-2 九九乘法表

```java
for (int i = 1; i <= 9; i++) {
    for (int j = 1; j <= 9; j++) {
        System.out.printf("%d*%d=%-3d", i, j, i * j);
    }
    System.out.println();
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>格式化：</b><code>%-3d</code> 表示整數靠左對齊、保留 3 格寬度，讓輸出整齊排列。
</div>

<!--
【逐步解說】
這是每個人的童年陰影，但用 Java 寫只要四行。看那個 %-3d。這就是我們學過的專業技能，讓你的乘法表整整齊齊。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# while 迴圈

<!--
【開場白】
接下來講 while。如果 for 是有預謀的重複，那 while 就是「看情況」的重複。只要條件還成立，就絕對不放手。
-->

---
layout: default
---

# 6-3 while 迴圈語法

| 元素 | 說明 |
| --- | --- |
| `condition` | 每次執行前檢查，為 `false` 時離開迴圈 |
| 進入時機 | 條件為 `true` 才會進入；若一開始就為 `false`，一次都不執行 |
| 適用情境 | 不知道確切執行次數、依條件決定停止 |

```java
while (condition) {
    // 重複執行的程式碼
}
```

<!--
【核心說明】
只要「條件成立」，我就不停止。

【生活化比喻】
這就像是在追劇。while (還有下一集) { 繼續看; }。直到你看到最後一集，你才會停下來去睡覺。

⚠️ 學生常見誤解：
如果條件一開始就是 false，while 就像是沒興趣的相親對象，連見面都不會見（一次都不執行）。
-->

---

# 6-3 while 迴圈範例

```java
int i = 1;
while (i <= 5) {
    System.out.print(i + " ");
    i++;
}
// 輸出：1 2 3 4 5
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>常見錯誤：</b>忘記在迴圈內更新條件變數（例如忘記 <code>i++</code>），會造成無限迴圈。
</div>

<!--
【逐步解說】
這跟 for 能做一樣的事，但你要自己手動更新 i++。

⚠️ 學生常見誤解：
這是新手最常出的包：忘了寫 i++。結果就是你的程式會一直印「1」，印到地老天荒。這叫「無限迴圈」，是工程師的噩夢。
-->

---

# 6-4 巢狀 while 迴圈

```java
int i = 1;
while (i <= 3) {
    int j = 1;
    while (j <= 3) {
        System.out.print(i + "*" + j + " ");
        j++;
    }
    System.out.println();
    i++;
}
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意：</b>內層迴圈的計數變數 <code>j</code> 必須在外層每次迭代時重新初始化，否則只會執行一次。
</div>

<!--
【逐步解說】
同樣的道理，外層跟內層都要有自己的「煞車」。記住，內層的 j = 1 要放在外層迴圈裡面，不然 j 跑完一次就不會回來了。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# do-while 迴圈

<!--
【開場白】
do-while 是那種「先做了再說」的性格。它保證任務至少會執行一次，這在處理選單時非常好用。
-->

---
layout: default
---

# 6-5 do-while 迴圈語法

| 元素 | 說明 |
| --- | --- |
| 執行時機 | 先執行一次，再檢查條件 |
| 最少次數 | **至少執行一次**，即使條件一開始就為 false |
| 結尾分號 | `while (condition);` 後面要加分號 |

```java
do {
    // 至少執行一次的程式碼
} while (condition);
```

<!--
【核心說明】
先執行一次，再檢查條件。

【生活化比喻】
這就像是去夜市試吃。老闆先給你塞一口（do），你吃完了，他才問你要不要買（while）。

⚠️ 學生常見誤解：
結尾那個分號 ; 絕對不能漏！這是 Java 裡極少數加在 while 後面的分號，漏了會編譯錯誤。
-->

---

# 6-5 do-while vs while 對比

| 比較項目 | while | do-while |
| --- | --- | --- |
| 條件檢查時機 | 執行**前** | 執行**後** |
| 最少執行次數 | 0 次 | **1 次** |
| 適用情境 | 不確定是否需要執行 | 至少要執行一次（如選單） |

<!--
【核心說明】
這張表是面試愛考題。while 是先看票再上車。do-while 是先上車再補票。所以 do-while 保證至少會執行一次。
-->

---

# 6-5 do-while 範例

```java
int i = 10;
do {
    System.out.print(i + " ");
    i++;
} while (i <= 5);
// 條件一開始就是 false，但仍輸出：10
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>典型應用：</b>顯示選單讓使用者選擇，至少要顯示一次，才能根據使用者的選擇決定是否繼續。
</div>

<!--
【逐步解說】
看這個例子，i = 10。雖然條件 i <= 5 是錯的，但因為它是 do-while，它還是會任性地先印出一個 10。

💼 業界實務：
最常用在「選單系統」。你得先把選單顯示給使用者看（do），才能根據他的輸入決定要不要繼續。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
# 無限迴圈與迴圈控制

<!--
【開場白】
接下來教大家怎麼控制這頭「重複」的野獸。有時候我們需要它永遠跑下去，有時候我們需要它立刻閉嘴。
-->

---
layout: default
---

# 6-6 無限迴圈

| 寫法 | 說明 |
| --- | --- |
| `while (true)` | 條件永遠為 true，常見且語意清晰 |
| `for (;;)` | 省略三個部分的 for 迴圈，效果相同 |
| 搭配 `break` | 在迴圈內部以 `break` 決定何時離開 |

```java
while (true) {
    System.out.println("持續執行...");
    break; // 必須有出口，否則無法終止
}
```

<!--
【核心說明】
有時候，我們就是希望程式永遠不要停。

【生活化比喻】
這就像是超商的自動門。它永遠在偵測有沒有人。while (true) 是最直白的寫法。

⚠️ 學生常見誤解：
寫無限迴圈時，心裡一定要有個「出口」（break）。不然你的程式就會變成一個吃掉所有資源的黑洞。
-->

---

# 6-6 for(;;) 無限迴圈

```java
int count = 0;
for (;;) {
    count++;
    if (count >= 3) {
        break;
    }
}
System.out.println("執行了 " + count + " 次"); // 3
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>應用場景：</b>伺服器監聽連線、持續讀取感測器資料、遊戲主迴圈等「一直等待直到停止」的情境。
</div>

<!--
【逐步解說】
這是另一種無限迴圈的寫法，看起來比較「資深」。注意那個 if (count >= 3) break;。這就是出口。這在伺服器聽取連線時很常用。
-->

---

# 6-7 break 敘述

| 元素 | 說明 |
| --- | --- |
| 作用 | 立即跳出「最近一層」的迴圈或 switch |
| 執行後 | 繼續執行迴圈之後的程式碼 |
| 常見搭配 | 搭配 `if` 條件判斷使用 |

```java
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        break; // 找到 5，立即停止
    }
    System.out.print(i + " ");
}
// 輸出：1 2 3 4
```

<!--
【核心說明】
break 就是「我要離職！」。

【生活化比喻】
這就像是你在跑馬拉松，跑到一半突然中樂透，你就不跑了（跳出迴圈）。它會直接終結「最近的一層」迴圈。
-->

---

# 6-8 continue 敘述

| 元素 | 說明 |
| --- | --- |
| 作用 | 跳過本次迭代剩餘程式碼，進入下一次迭代 |
| 與 break 差異 | `break` 離開迴圈；`continue` 繼續下一圈 |
| 常見搭配 | 搭配 `if` 過濾不需要處理的情況 |

```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue; // 跳過 3
    }
    System.out.print(i + " ");
}
// 輸出：1 2 4 5
```

<!--
【核心說明】
continue 是「這關不算，重來！」。

【生活化比喻】
這就像是在過濾壞掉的蛋。你拿出一顆發現是壞的（條件成立），你就跳過它（continue），直接拿下一顆。你沒有不做了，你只是跳過這一個。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第六部分
# 迴圈標籤（Label）

<!--
【開場白】
如果你套了三層夢境，你想直接醒過來怎麼辦？這時候得用「標籤」來幫你定位。
-->

---
layout: default
---

# 6-9 迴圈標籤語法

| 元素 | 說明 |
| --- | --- |
| `labelName:` | 放在迴圈之前，命名這個迴圈 |
| `break labelName` | 跳出指定名稱的迴圈（可跳多層） |
| `continue labelName` | 跳至指定迴圈的下一次迭代 |

```java
outer:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (j == 1) break outer;
        System.out.print(i + "," + j + " ");
    }
}
// 輸出：0,0
```

<!--
【核心說明】
給迴圈取個名字，然後直接針對它操作。

【生活化比喻】
這就像是「傳送門」。不管你現在在多深的地下城，只要大喊 break outer;，你就能直接傳送到地面。

⚠️ 學生常見誤解：
別濫用標籤！用多了會讓你的程式碼長得像盤義大利麵，大家會找不到你到底跳去哪了。
-->

---

# 6-9 標籤 continue 範例

```java
outer:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (j == 1) continue outer;
        System.out.print(i + "," + j + " ");
    }
}
// 輸出：0,0  1,0  2,0
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>使用時機：</b>搜尋二維陣列時，找到目標後需要跳出雙層迴圈，label break 是最直接的解法。
</div>

<!--
【逐步解說】
看這裡，一旦 j == 1，它不是跳出內層，而是直接跳到那個叫 outer 的外層繼續下一圈。這在二維陣列搜尋時超好用。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第七部分
# Scanner 輸入檢查

<!--
【開場白】
還記得那個會讓程式壞掉的 Scanner 嗎？今天我們要教它怎麼「過濾廢話」，確保拿到的都是正確的資料。
-->

---
layout: default
---

# 6-10 Scanner 輸入驗證語法

| 元素 | 說明 |
| --- | --- |
| `Scanner sc` | 建立 Scanner 讀取 System.in |
| `sc.hasNextInt()` | 回傳 true 表示下一個 token 是整數 |
| `sc.nextInt()` | 讀取下一個整數 |
| `sc.next()` | 跳過非整數的輸入 |

```java
Scanner sc = new Scanner(System.in);
while (!sc.hasNextInt()) {
    sc.next(); // 丟棄無效輸入
}
int value = sc.nextInt();
```

<!--
【核心說明】
hasNextInt() 就像是個過濾網。

【生活化比喻】
這就像是在等公車，只有號碼對的公車（整數）你才上車。如果來了台計程車（字串），你就叫它走開（sc.next()）。這樣就能保證你不會坐錯車。
-->

---

# 6-10 輸入驗證完整範例

```java
Scanner sc = new Scanner(System.in);
int age = -1;
System.out.print("請輸入年齡（正整數）：");
while (age <= 0) {
    if (sc.hasNextInt()) {
        age = sc.nextInt();
        if (age <= 0) System.out.print("請輸入正整數：");
    } else {
        System.out.print("格式錯誤，請重新輸入：");
        sc.next();
    }
}
System.out.println("年齡：" + age);
```

<!--
【逐步解說】
這是一個「防呆設計」。如果使用者亂打文字，你的程式不會崩潰，而是會冷冷地叫他「重新輸入」。這就是專業程式與學生作業的差別。
-->

---

# 6-10 while(true) + break 輸入模式

```java
Scanner sc = new Scanner(System.in);
while (true) {
    String line = sc.nextLine();
    if ("bye".equalsIgnoreCase(line)) {
        break;
    }
    System.out.println("你輸入了：" + line);
}
System.out.println("結束輸入");
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>常見應用：</b>持續讀取輸入，直到使用者輸入特定結束字（如 "bye"、"quit"）才停止，是互動式程式的標準模式。
</div>

<!--
【業界實務】
這是互動式程式的標準套路。「除非你說分手（bye），否則我會一直聽你說話」。這就是 while(true) 搭配 break 的實戰用法。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第八部分
# 迴圈應用

<!--
【開場白】
現在我們來看看迴圈能幫我們做什麼有意義的事。不管是算錢、算美女還是找最大值，都難不倒它。
-->

---
layout: default
---

# 6-11 累加與計數

| 應用 | 說明 | 初始值 |
| --- | --- | --- |
| 累加（sum） | 將每次迭代的值加入總和 | `sum = 0` |
| 計數（count） | 符合條件時計數器加一 | `count = 0` |
| 乘積（product） | 將每次迭代的值相乘 | `product = 1` |

```java
int sum = 0, count = 0;
for (int i = 1; i <= 100; i++) {
    sum += i;
    if (i % 2 == 0) count++;
}
// sum=5050, count=50（偶數個數）
```

<!--
【核心說明】
這是迴圈最基本的兩大功能。

【生活化比喻】
累加（sum）就像是存錢筒。計數（count）就像是碼錶，看到符合條件的就按一下。記住初始值要設對，不然結果會歪掉。
-->

---

# 6-11 找最大與最小值

```java
int[] nums = {34, 17, 89, 45, 23};
int max = nums[0], min = nums[0];

for (int n : nums) {
    if (n > max) max = n;
    if (n < min) min = n;
}
System.out.println("最大：" + max); // 89
System.out.println("最小：" + min); // 17
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>初始化技巧：</b>將 max 和 min 初始化為陣列第一個元素，避免使用 <code>Integer.MIN_VALUE</code> 造成誤判。
</div>

<!--
【逐步解說】
找極值的標準邏輯：先假設第一個是最大的，然後一個個比。如果遇到更強的，就換人當老大。這就是「強者生存」的演算法。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第九部分
# 專題應用

<!--
【開場白】
最後，我們來挑戰一些古代天才設計的題目，看看用 Java 怎麼「暴力破解」。
-->

---
layout: default
---

# 6-12 估算圓周率（萊布尼茨公式）

萊布尼茨級數：π/4 = 1 - 1/3 + 1/5 - 1/7 + ...

| 元素 | 說明 |
| --- | --- |
| 分母 | 奇數序列：1, 3, 5, 7, ... |
| 符號 | 交替正負：+, -, +, -, ... |
| 迭代次數 | 越多次越精確 |

```java
double pi = 0;
for (int i = 0; i < 1000000; i++) {
    pi += (i % 2 == 0 ? 1 : -1) / (2.0 * i + 1);
}
System.out.printf("π ≈ %.6f%n", pi * 4);
```

<!--
【逐步解說】
不用去背圓周率。我們用迴圈跑個一百萬次。雖然公式看起來很難，但寫成 Java 只要三行。這就是電腦的暴力美學！
-->

---

# 6-12 雞兔同籠

已知籠中共有 35 個頭、94 條腿，求雞和兔各幾隻？

```java
int heads = 35, legs = 94;
for (int chicken = 0; chicken <= heads; chicken++) {
    int rabbit = heads - chicken;
    if (chicken * 2 + rabbit * 4 == legs) {
        System.out.println("雞：" + chicken + " 隻");
        System.out.println("兔：" + rabbit + " 隻");
        break;
    }
}
// 雞：23 隻，兔：12 隻
```

<!--
【逐步解說】
不用設 X, Y。我們叫電腦從 0 隻雞猜到 35 隻雞。反正電腦很快，猜對了就 break。這叫「窮舉法」，是我們工程師最不講武德的招式。
-->

---

# 6-12 國王的麥粒

棋盤 64 格，第 n 格放 2^(n-1) 粒小麥，總計多少粒？

```java
long total = 0;
long grains = 1;
for (int i = 1; i <= 64; i++) {
    total += grains;
    grains *= 2;
}
System.out.println("總麥粒數：" + total);
// 18446744073709551615（≈ 1.8 × 10^19）
```

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>注意溢位：</b>必須使用 <code>long</code>（64 位元），若使用 <code>int</code>（32 位元）會發生整數溢位，結果完全錯誤。
</div>

<!--
【核心說明】
這是在警告大家「指數成長」的可怕。

⚠️ 學生常見誤解：
如果你用 int 算這題，結果會變負數！因為數字太大了，連 long 都快裝不下了。處理大數字，請認明 long。
-->

---

# 三種迴圈對比

| 比較項目 | for | while | do-while |
| --- | --- | --- | --- |
| 條件檢查 | 執行前 | 執行前 | 執行後 |
| 最少執行次數 | 0 次 | 0 次 | **1 次** |
| 計數變數位置 | 在 `for(...)` 內 | 在外部宣告 | 在外部宣告 |
| 適用情境 | 已知執行次數 | 依條件決定 | 至少執行一次 |

<div class="mt-4 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>選擇原則：</b>知道次數用 <code>for</code>；不知次數但有條件用 <code>while</code>；需至少執行一次用 <code>do-while</code>。
</div>

<!--
【核心說明】
最後的重點整理。

💼 業界實務：
知道次數用 for。不知次數但有條件用 while。需至少執行一次（如選單）用 do-while。選對工具，下班才早。
-->

---
layout: default
---

# 練習一：FizzBuzz
### 任務說明

輸出 1 到 50 的數字，但：
- 能被 3 整除時，輸出 `Fizz`
- 能被 5 整除時，輸出 `Buzz`
- 能被 3 和 5 整除時，輸出 `FizzBuzz`
- 其他情況輸出數字本身

**預期輸出（前 15 個）：** `1 2 Fizz 4 Buzz Fizz 7 8 Fizz Buzz 11 Fizz 13 14 FizzBuzz ...`

<!--
【出題前的鋪陳】
各位，這是經典中的經典！據說有 50% 的求職者面試時會被刷掉這題。

【問題引導】
注意那個 FizzBuzz（同時被 3 和 5 整除）。如果你先判斷了 3，那 15 就會只印出 Fizz。順序就是關鍵！
-->

---
layout: default
---

# 練習一：解題提示
### 提示說明

1. 使用 `for` 迴圈從 1 跑到 50
2. 用 `%` 取餘數判斷整除條件
3. **關鍵順序：** 先判斷 `i % 15 == 0`（FizzBuzz），再判斷 `i % 3` 和 `i % 5`，最後才輸出數字
4. 或使用 `if-else if-else` 避免多個條件重複觸發

```java
for (int i = 1; i <= 50; i++) {
    if (i % 15 == 0)     System.out.print("FizzBuzz ");
    else if (i % 3 == 0) System.out.print("Fizz ");
    else if (i % 5 == 0) System.out.print("Buzz ");
    else                 System.out.print(i + " ");
}
```

<!--
【逐步解說】
試著用 if-else if 鏈來寫。最嚴格的條件（被 15 整除）要放最上面。跑完之後看看，你的輸出是不是跟我的提示一樣漂亮？
-->

---
layout: default
---

# 練習二：數字金字塔
### 任務說明

使用巢狀迴圈印出以下圖形（n=5）：

```
1
1 2
1 2 3
1 2 3 4
1 2 3 4 5
```

**要求：** 以 `n` 為變數，可改變金字塔高度。

<!--
【出題前的鋪陳】
最後一個挑戰：數字金字塔。這題要用到「巢狀迴圈」。

【問題引導】
外層控制高度，內層控制寬度。這就像是在蓋房子，一層蓋完再蓋下一層。內層的結束條件是關鍵。
-->

---
layout: default
---

# 練習二：解題提示
### 提示說明

1. 外層迴圈控制「列數」，從 1 到 n
2. 內層迴圈控制「每列印幾個數字」，從 1 到外層計數值
3. 每列結束後用 `System.out.println()` 換行

```java
int n = 5;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print(j + " ");
    }
    System.out.println();
}
```

<!--
【逐步解說】
內層的終止條件 j <= i 是重點。這代表第 1 層只印 1 個，第 5 層印 5 個。寫完後記得換行，金字塔就出來了！
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
【開場白】
今天我們教了程式怎麼「爆肝」。大家還有什麼想問的嗎？

或者是有人想問，如果我想寫個無限迴圈叫「老婆永遠是對的」，這程式碼該怎麼寫？（提示：那個迴圈沒有 break，千萬別寫！）
-->

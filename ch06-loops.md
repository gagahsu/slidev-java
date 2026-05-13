---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第一部分
# for 迴圈

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第二部分
# 巢狀 for 迴圈

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第三部分
# while 迴圈

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第四部分
# do-while 迴圈

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

---

# 6-5 do-while vs while 對比

| 比較項目 | while | do-while |
| --- | --- | --- |
| 條件檢查時機 | 執行**前** | 執行**後** |
| 最少執行次數 | 0 次 | **1 次** |
| 適用情境 | 不確定是否需要執行 | 至少要執行一次（如選單） |

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第五部分
# 無限迴圈與迴圈控制

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第六部分
# 迴圈標籤（Label）

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第七部分
# Scanner 輸入檢查

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第八部分
# 迴圈應用

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 第九部分
# 專題應用

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

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

有任何問題歡迎提出！

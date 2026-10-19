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
大家好，這一章的主題是迴圈控制。

想像一下，如果要印出 1 到 100，一行一行寫 100 次 println，不但累，之後要改成 1 到 1000 更是不可能。迴圈就是讓程式重複做同一件事的工具。

這一章我們會學 for、while 和 do-while 三種迴圈，以及控制迴圈的技巧。學完之後，我們就能用短短幾行程式，處理大量重複的工作。
-->

---
layout: default
---

# Outline

- **for 迴圈** — 基本語法、流程、enhanced for-each
- **巢狀 for 迴圈** — 九九乘法表
- **while 迴圈**
- **巢狀 while 迴圈**
- **do-while 迴圈** — 至少執行一次
- **無限迴圈** — `while(true)`、`for(;;)`
- **break 敘述** — 跳出迴圈
- **continue 敘述** — 跳過本次迭代
- **迴圈標籤（label）** — 跳出多層迴圈
- **Scanner 輸入檢查** — while 驗證使用者輸入
- **迴圈應用** — 累加、計數、最大最小值
- **專題** — 圓周率估算、雞兔同籠、國王的麥粒

<!--
這一章的內容不少。

我們先學 for 迴圈和 for-each，再用巢狀迴圈印出九九乘法表。接著是 while 和 do-while，以及無限迴圈。然後學 break、continue 和迴圈標籤，控制迴圈什麼時候停止或跳過。

後半段我們會用迴圈驗證使用者的輸入，練習累加、計數、找最大最小值，最後用估算圓周率、雞兔同籠和國王的麥粒三個專題收尾。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# for 迴圈

<!--
我們先從最常用的 for 迴圈開始。
-->

---
layout: default
---

# for 迴圈語法

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
for 迴圈的括號裡有三個部分，用分號隔開。

第一個是初始化，設定計數用的變數，只會在一開始執行一次。第二個是條件，每一圈開始前都會檢查，結果是 false 時就離開迴圈。第三個是更新，每一圈執行完之後，用來改變計數變數。

下面整理了執行順序：先初始化，接著判斷條件，條件成立就執行大括號裡的程式，然後更新，再回去判斷條件，一直重複到條件不成立為止。
-->

---

# for 迴圈範例

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
我們看一個最簡單的例子。

i 從 1 開始，只要 i 小於等於 5 就繼續，每一圈結束後 i 加 1。所以會依序印出 1、2、3、4、5。

計數不一定要從小到大。遞減可以用 i 減減，也可以一次跳兩個，寫成 i 加等於 2。
-->

---

# Enhanced for-each 迴圈

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
如果要把陣列或集合裡的每一個元素都走一遍，有一個更簡潔的寫法，叫做 for-each，也稱為加強版 for 迴圈。

括號裡冒號的右邊，放要走訪的陣列或集合；左邊宣告一個變數，每一圈它會依序拿到其中的一個元素。我們可以把它讀成「對於陣列裡的每一個元素」。
-->

---

# for-each 範例

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
看實際的例子。scores 陣列裡有五個分數，for-each 會依序把每一個分數交給 score，印出來就是 85、92、78、95、88。

不需要索引，也不需要判斷什麼時候結束，寫起來非常乾淨。

不過下面也提醒了它的限制：for-each 只能讀取元素，沒辦法透過它修改陣列的內容，也拿不到目前是第幾個。需要索引的時候，就要用傳統的 for 迴圈。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 巢狀 for 迴圈

<!--
一個迴圈可以處理一排資料。如果要處理像表格這樣有列、有欄的資料，就需要把迴圈放進另一個迴圈裡，這就是巢狀迴圈。
-->

---
layout: default
---

# 巢狀 for 迴圈結構

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
巢狀迴圈就是迴圈裡面還有迴圈。外層迴圈通常控制「列」，內層迴圈控制「欄」。

重點是：外層每執行一次，內層就要從頭到尾完整跑一輪。就像時鐘的時針和分針，時針走一格，分針要轉完一整圈。

所以外層跑 n 次、內層跑 m 次，裡面的程式總共會執行 n 乘 m 次。看範例，外層和內層都跑 3 次，總共會印出 9 個乘法算式，排成三列。
-->

---

# 九九乘法表

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
用巢狀迴圈，最經典的例子就是九九乘法表。

外層的 i 從 1 到 9，代表每一列；內層的 j 也從 1 到 9，代表每一欄。每一格用 printf 印出 i 乘 j 的結果，內層跑完一輪之後，再用 println 換行。

格式裡的 `%-3d`，表示整數靠左對齊、至少佔 3 格，這樣不管結果是一位數還是兩位數，整張表都能排得很整齊。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# while 迴圈

<!--
for 迴圈適合知道要跑幾次的情況。但有時候我們不知道要重複幾次，只知道什麼時候該停下來，這時候就用 while 迴圈。
-->

---
layout: default
---

# while 迴圈語法

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
while 迴圈的結構很簡單，只有一個條件。每一圈開始前檢查條件，是 true 就執行，是 false 就離開。

要注意，如果一開始條件就是 false，大括號裡的程式一次都不會執行。

while 最適合「不知道確切次數，要依條件決定何時停止」的情況，例如一直讀取輸入，直到使用者輸入正確為止。
-->

---

# while 迴圈範例

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
我們用 while 改寫剛才印出 1 到 5 的例子。

計數變數 i 要在迴圈外面先宣告。每一圈印出 i，再把 i 加 1，當 i 變成 6，條件不成立，迴圈就結束了。

下面的提醒非常重要：如果忘了在迴圈裡寫 i 加加，i 永遠是 1，條件永遠成立，程式就會變成無限迴圈，一直執行停不下來。
-->

---

# 巢狀 while 迴圈

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
while 也可以寫成巢狀的。

外層的 i 控制列數，內層的 j 控制每一列印幾個。注意 j 等於 1 這一行，是寫在外層迴圈的裡面。

為什麼要這樣做？因為內層每一輪都要從 1 重新開始數。如果把 j 的宣告放到最外面，第一輪結束時 j 已經變成 4，第二輪就不會再進入內層迴圈了。這是巢狀 while 最常見的錯誤。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# do-while 迴圈

<!--
while 是先檢查條件再執行。如果我們希望程式至少先執行一次，再決定要不要繼續，就要用 do-while。
-->

---
layout: default
---

# do-while 迴圈語法

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
do-while 的結構是先寫 do 和大括號，把要執行的程式放在裡面，最後才寫 while 和條件。

所以它會先執行一次，再檢查條件，也就是說，就算條件一開始就是 false，裡面的程式還是會執行一次。

要特別注意，最後的 while 括號後面要加分號，這是很多人會漏掉的地方。
-->

---

# do-while vs while 對比

| 比較項目 | while | do-while |
| --- | --- | --- |
| 條件檢查時機 | 執行**前** | 執行**後** |
| 最少執行次數 | 0 次 | **1 次** |
| 適用情境 | 不確定是否需要執行 | 至少要執行一次（如選單） |

<!--
這張表比較了 while 和 do-while 的差別。

while 是執行前檢查，最少可能一次都不執行；do-while 是執行後檢查，最少一定會執行一次。

所以，不確定需不需要執行時用 while；一定要先做一次的情況，例如先顯示選單，再看使用者的選擇，就適合用 do-while。
-->

---

# do-while 範例

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
我們看 do-while 的實際效果。i 一開始是 10，條件是 i 小於等於 5，一開始就不成立。

但因為 do-while 先執行再檢查，所以還是會印出一次 10，接著檢查條件不成立，才離開迴圈。如果換成 while，就什麼都不會印。

就像下面提到的，顯示選單是最典型的應用：選單至少要先出現一次，使用者才能選擇要不要繼續。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 無限迴圈與迴圈控制

<!--
接下來，我們看看永遠不會自己停下來的無限迴圈，以及控制迴圈流程的 break 和 continue。
-->

---
layout: default
---

# 無限迴圈

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
無限迴圈就是條件永遠成立的迴圈，最常見的寫法是 while 括號裡直接放 true，意思非常清楚。另一種寫法是 for 括號裡只放兩個分號，三個部分都省略，效果一樣。

無限迴圈不是錯誤，但一定要有出口。通常會在迴圈裡面搭配 if 和 break，在適當的時候跳出來。看範例，迴圈裡的 break 就是它的出口。
-->

---

# for(;;) 無限迴圈

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
這是用 for 寫的無限迴圈。

count 每一圈加 1，當它大於等於 3 時，執行 break 離開迴圈，最後印出執行了 3 次。

在實際應用中，伺服器一直等待連線、遊戲畫面不斷更新、持續讀取感測器的資料，這些「一直執行，直到被要求停止」的程式，都是用無限迴圈搭配出口條件寫成的。
-->

---

# break 敘述

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
break 的作用，是立刻跳出最近的那一層迴圈，接著執行迴圈後面的程式。

看範例，迴圈本來要從 1 跑到 10。但當 i 等於 5 時執行了 break，迴圈立刻結束，所以只會印出 1 到 4。

找到目標就停下來，不需要再繼續找，這就是 break 最常見的用法。
-->

---

# continue 敘述

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
continue 和 break 很像，但效果不一樣。break 是整個離開迴圈；continue 則是跳過這一圈剩下的程式，直接進入下一圈。

看範例，當 i 等於 3 時執行 continue，後面的 print 被跳過，所以 3 不會印出來，但迴圈會繼續執行，最後印出 1、2、4、5。

如果把 break 想成「下車」，continue 就是「這一站不下車，坐到下一站」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 迴圈標籤（Label）

<!--
break 和 continue 只能控制最近的一層迴圈。如果在巢狀迴圈裡想直接跳出外層，就需要迴圈標籤。
-->

---
layout: default
---

# 迴圈標籤語法

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
迴圈標籤就是幫迴圈取一個名字，寫在迴圈前面，後面加一個冒號。這樣 break 和 continue 就可以指定要控制哪一個迴圈。

看範例，外層迴圈的名字叫 outer。內層當 j 等於 1 時，執行 break outer，會直接跳出外層迴圈，整個巢狀迴圈都結束。所以只會印出 0,0。

如果只寫 break，跳出的只有內層，外層還會繼續執行。
-->

---

# 標籤 continue 範例

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
這個範例把 break outer 換成了 continue outer。

當 j 等於 1 時，會跳過內層剩下的部分，直接進入外層的下一圈。所以每一個 i 都只會印出 j 等於 0 的那一組：0,0、1,0、2,0。

最常見的用途是在二維資料裡搜尋，找到目標之後，用帶標籤的 break 一次跳出兩層迴圈。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Scanner 輸入檢查

<!--
迴圈有一個很實用的應用：檢查使用者的輸入。使用者不一定會照我們的期待輸入資料，這時候就可以用迴圈一直要求重新輸入，直到格式正確為止。
-->

---
layout: default
---

# Scanner 輸入驗證語法

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
Scanner 有一個 hasNextInt 方法，它會先檢查下一個輸入是不是整數，回傳 true 或 false，但不會真的把資料讀走。

看範例的寫法：只要下一個輸入不是整數，就用 next 把這個無效的輸入讀掉、丟棄，再繼續檢查。直到確認是整數，迴圈結束，才用 nextInt 真正讀取。

這樣就算使用者輸入了文字，程式也不會出錯。
-->

---

# 輸入驗證完整範例

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
這是一個完整的輸入驗證範例，要求使用者輸入一個正整數的年齡。

age 一開始設成負 1，讓迴圈至少執行一次。每一圈先用 hasNextInt 檢查：如果是整數就讀進來，但數字不是正數的話，就提示重新輸入；如果根本不是整數，就提示格式錯誤，並用 next 把錯誤的輸入丟掉。

直到 age 大於 0，迴圈才會結束，最後印出年齡。
-->

---

# while(true) + break 輸入模式

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
另一種常見的輸入模式，是 while true 搭配 break。

程式不斷讀取一整行輸入，並把內容印出來。直到使用者輸入 bye，就執行 break 離開迴圈。這裡用的是 equalsIgnoreCase，所以不管輸入大寫還是小寫的 bye，都可以結束。

聊天機器人、命令列工具這類互動式程式，都是用這種模式寫成的。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 迴圈應用

<!--
學會了各種迴圈，接下來看幾個最常見的迴圈應用。
-->

---
layout: default
---

# 累加與計數

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
用迴圈處理資料時，有三種很常見的模式。

累加，是把每一圈的值加進總和，總和的初始值是 0。計數，是遇到符合條件的情況，就把計數器加 1，初始值也是 0。乘積，是把每一圈的值相乘，要注意初始值是 1，如果設成 0，乘出來永遠是 0。

看範例，從 1 跑到 100，每一圈把 i 加進 sum，遇到偶數就讓 count 加 1。最後 sum 是 5050，count 是 50。
-->

---

# 找最大與最小值

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
另一個常見的應用是找出最大值和最小值。

做法是先把 max 和 min 都設成陣列的第一個元素，再用迴圈逐一比較：遇到比 max 大的，就更新 max；遇到比 min 小的，就更新 min。

走完之後，最大值是 89，最小值是 17。用陣列的第一個元素當初始值，可以確保比較的起點一定是陣列裡真實存在的數字。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 專題應用

<!--
最後，我們用三個有趣的專題，看看迴圈可以解決哪些問題。
-->

---
layout: default
---

# 估算圓周率（萊布尼茨公式）

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
第一個專題是用萊布尼茨級數估算圓周率。

這個級數是 1 減 3 分之 1，加 5 分之 1，減 7 分之 1，一直下去，結果會越來越接近圓周率的四分之一。分母是奇數，符號正負交替。

程式用迴圈跑一百萬次，每一圈用 i 是不是偶數決定正負號，分母是 2i 加 1。注意這裡寫的是 2.0，確保除法的結果是小數。最後把總和乘以 4，就會得到大約 3.141592。

迭代的次數越多，結果就越精確。
-->

---

# 雞兔同籠

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
第二個專題是經典的雞兔同籠：籠子裡共有 35 個頭、94 隻腳，雞和兔各有幾隻？

我們不用解方程式，直接讓電腦一個一個試。雞的數量從 0 試到 35，兔子就是剩下的頭數。每試一次，就計算腳的總數，雞 2 隻腳、兔 4 隻腳，剛好等於 94 時就印出答案，並用 break 結束。

答案是雞 23 隻、兔 12 隻。這種把所有可能都試一遍的方法，叫做「窮舉法」，是電腦最擅長的事情之一。
-->

---

# 國王的麥粒

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
第三個專題是國王的麥粒。棋盤有 64 格，第一格放 1 粒麥子，之後每一格都是前一格的兩倍，總共要多少粒？

程式用 grains 記錄這一格的麥粒數，每一圈加進 total，再把 grains 乘以 2。

真正的答案大約是 1.8 乘以 10 的 19 次方，這個數字大到連 long 都存不下。最後幾格加總時，long 也會溢位，所以實際執行結果並不是投影片上這個數字。要精確計算，需要用到之後會學的 BigInteger。

這個例子告訴我們，以倍數成長的數字，很快就會超出變數能存的範圍，選擇型態時一定要注意。

【講師】投影片上標示的輸出 18446744073709551615 超過 long 上限，實際執行會溢位印出 -1，可現場執行示範。
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
最後我們把三種迴圈放在一起比較。

for 和 while 都是先檢查條件，最少可能一次都不執行；do-while 是先執行再檢查，最少一定執行一次。for 的計數變數寫在括號裡，while 和 do-while 則要在外面宣告。

選擇的原則很簡單：「知道次數用 for，只知道停止條件用 while，至少要執行一次用 do-while。」
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
接下來我們動手練習。

第一題是經典的 FizzBuzz。印出 1 到 50，但能被 3 整除時印 Fizz，能被 5 整除時印 Buzz，同時能被 3 和 5 整除時印 FizzBuzz，其他情況就印數字本身。

這題常被用在程式面試，考的是迴圈和條件判斷的基本功。我們先暫停一下，自己動手試試看。

【講師】給學生約 10 分鐘實作。
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
我們來看提示。

用 for 迴圈從 1 跑到 50，用取餘數判斷能不能整除。

關鍵在於判斷的順序：要先判斷能不能被 15 整除，也就是同時被 3 和 5 整除，再判斷 3 和 5。如果先判斷 3，15 就會先被印成 Fizz，永遠印不出 FizzBuzz。這和上一章「條件從最嚴格排到最寬鬆」是同樣的道理。
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
第二題是數字金字塔。用巢狀迴圈印出五列數字，第一列是 1，第二列是 1 2，一直到第五列是 1 到 5。

另外，要用變數 n 控制高度，改變 n 就能印出不同高度的金字塔。

一樣先暫停一下，自己動手寫寫看。

【講師】給學生約 10 分鐘實作。
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
我們來看提示。

外層迴圈的 i 從 1 到 n，控制要印幾列。內層迴圈的 j 從 1 跑到 i，所以第幾列就印幾個數字。關鍵就在內層的條件是 j 小於等於 i，而不是固定的數字。

每一列印完之後，用 println 換行。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# Q & A

<!--
這一章我們學會讓程式重複做事。

for 適合已知次數，while 適合依條件決定何時停止，do-while 至少會執行一次。巢狀迴圈可以處理表格狀的資料；break 跳出迴圈，continue 跳過這一圈，迴圈標籤可以一次控制多層迴圈。我們也用迴圈驗證使用者輸入，練習了累加、計數和找最大最小值。

下一章，我們要學陣列，用一個變數存放一整組資料。

【講師】開放 Q&A。
-->

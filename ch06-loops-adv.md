---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false

fonts:
  provider: none
title: 迴圈控制（進階／自學）
routeAlias: ch06adv
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
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">進階自學內容</p>
  <Link to="home" style="color: #9dc4c4; font-size: 0.85rem; margin-top: 2rem; text-decoration: none; letter-spacing: 0.05em;">← 返回目錄</Link>
</div>

<!--
大家好，歡迎來到迴圈控制的進階自學篇。基礎版我們已經把 for、while、do-while 三種迴圈，以及 break、continue 的基本用法都練熟了，這裡要補的是兩塊更進階的東西。

第一塊是「迴圈標籤」（label），用來處理多層迴圈時，一次跳出好幾層的情境；第二塊是兩個經典的數學題目，萊布尼茨公式估算圓周率，還有國王的麥粒問題。這兩題在基礎版的雞兔同籠之外，能讓我們再多看看「用迴圈解決真實問題」的不同樣貌。

學完這份自學內容，我們會更清楚什麼時候該用標籤、什麼時候資料型態要特別注意（不然數字會爆掉），對迴圈的掌握度會更紮實。
-->

---
layout: default
---

# Outline

- **進階 1：迴圈標籤（Label）** — labeled break / continue，跳出多層迴圈
- **進階 2：估算圓周率（萊布尼茨公式）** — 級數逼近、浮點數累加
- **進階 3：國王的麥粒** — 指數成長與整數溢位

<!--
這份自學內容分成三個主題。

第一個是迴圈標籤，解決「巢狀迴圈裡，一次跳出多層」的問題；第二個跟第三個都是經典數學題目的迴圈實作，分別練習「浮點數累加逼近」跟「指數成長＋資料型態選擇」這兩種常見場景。

如果大家在基礎版已經把雞兔同籠寫熟了，這裡可以當作額外的題型練習，感受一下「同一招迴圈，可以解決完全不同性質的問題」。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階 1
# 迴圈標籤（Label）

<!--
想像我們在玩一個迷宮遊戲，迷宮裡有好幾層房間，房間裡還有房間。如果我們在最深處發現出口，難道要一層一層走回去開門，才能離開嗎？當然不用，遊戲通常會讓你直接「傳送」回到地面。

迴圈標籤（label）就是程式世界的傳送裝置。當迴圈一層套一層的時候，一般的 `break` 或 `continue` 只能處理「最靠近的那一層」，但有時候我們想直接跳出最外層，或是直接跳到外層的下一輪——這時候就要靠標籤來指定目標。
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
先看語法本身：在迴圈前面加一個名字、加冒號，這個迴圈就有了「名牌」，之後就可以對著這個名牌喊 `break` 或 `continue`。

帶大家看一下範例：外層迴圈叫 `outer`，當 `j == 1` 的時候，我們執行 `break outer`——這不是跳出內層迴圈而已，是直接把外層也一起結束掉。所以整段程式只印出 `0,0` 就停了。

⚠️ 易錯點提醒：標籤的名字後面一定要接冒號 `:`，而且要直接寫在迴圈的正上方，中間不能有其他程式碼。另外提醒一下，標籤不要亂取名，盡量讓人一看就知道它對應哪一層迴圈，不然程式碼會變得很難讀。

預期結果：這段程式只會印出 `0,0`，因為一進到內層迴圈、j 變成 1 的那一刻，整個外層迴圈就被強制結束了。
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
這頁來看 `continue outer` 跟剛剛的 `break outer` 有什麼不同。同樣是 `j == 1` 的時候觸發，但這次不是整個結束，而是「放棄內層這一輪，直接回到外層迴圈，進入下一次 i」。

帶大家看結果：每一個 i（0、1、2）都只印出 `j=0` 的那組，因為 j 一變成 1，就立刻跳回外層繼續下一個 i。所以輸出是 `0,0  1,0  2,0`。

💼 業界實務：最常見的場景是「在二維陣列裡搜尋某個目標」。一旦找到，用 `break outer` 直接跳出兩層迴圈，省去再寫一個 boolean 旗標變數來控制的麻煩。

⚠️ 易錯點提醒：`break` 跟 `continue` 搭配標籤時行為差很多，務必先想清楚「我要整個結束」還是「只是跳過這一輪」，選錯的話結果會完全不一樣。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階 2
# 估算圓周率（萊布尼茨公式）

<!--
圓周率 π 是個無限不循環小數，沒辦法直接算出「正確答案」，但數學家發現可以用一個無窮級數慢慢「逼近」它的值——級數加的項數越多，結果就越接近真實的 π。

這就像是我們用很多根直線小木棒去拼一個圓，木棒越短、數量越多，拼出來的形狀就越接近圓形。萊布尼茨公式就是這種「用簡單的加減法，一步步逼近複雜答案」的經典範例，剛好可以拿來練習迴圈的浮點數累加。
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
範例目的：這段程式用迴圈跑一百萬次，把萊布尼茨級數的每一項一個個加進變數 `pi`，最後乘以 4 就是 π 的估計值。

帶讀關鍵行：`(i % 2 == 0 ? 1 : -1)` 這段是用三元運算子決定正負號——`i` 是偶數的時候是 `+`，奇數的時候是 `-`，剛好對應公式裡「正負交替」的規則。分母 `(2.0 * i + 1)` 則對應 1, 3, 5, 7... 這個奇數序列。

⚠️ 易錯點提醒：分母那邊用的是 `2.0`，這個 `.0` 不能省略！如果寫成 `2 * i + 1`，整個運算會變成整數除法，每一項幾乎都會被算成 0，最後結果就完全不對了。這是浮點數運算很常見的陷阱。

預期結果：跑完一百萬次之後，`pi * 4` 會非常接近 `3.141593`，但因為級數收斂得很慢，就算跑了一百萬次，誤差還是存在——這也提醒我們，「逼近」終究只是逼近，不是精確解。
-->

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 進階 3
# 國王的麥粒

<!--
這題是個古老的傳說：國王要獎賞一位發明棋盤遊戲的人，對方說「不用太多，棋盤第一格放 1 粒麥子，第二格放 2 粒，第三格放 4 粒，每一格都是前一格的兩倍，放滿 64 格就好」。國王覺得這要求很小，就答應了——結果發現整個國家的糧倉都裝不下。

這就是「指數成長」的威力：一開始看起來人畜無害的數字，翻倍個幾十次之後就會變成天文數字。這題除了練習迴圈累加，更重要的是讓我們親眼看到「為什麼選對資料型態這麼重要」。
-->

---
layout: default
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
範例目的：用迴圈跑 64 次，每一輪把當前格子的麥粒數加進 `total`，再把 `grains` 乘以 2，準備給下一格用。

帶讀關鍵行：注意 `total` 跟 `grains` 都宣告成 `long`，不是 `int`。`long` 是 64 位元的整數型態，能存的數字範圍遠比 32 位元的 `int` 大得多。

⚠️ 易錯點提醒：如果把 `long` 改成 `int`，程式照樣可以編譯、可以執行，但跑到第 31 格左右，數字就會超過 `int` 能表示的範圍，發生「整數溢位」——這時候數字會突然變成負數，而且編譯器完全不會警告你！這種「不報錯但結果是錯的」的 bug 是最難抓的，務必養成習慣：遇到可能快速變大的數字，先想想要不要用 `long`。

預期結果：64 格加總起來大約是 1.8 × 10^19，這個數字大概是地球上所有沙粒總數的好幾倍——這就是為什麼國王會傻眼。
-->

---
layout: default
---

# 自學練習一：跳出雙層迴圈找座位

### 任務說明

某電影院的座位表是一個 5x5 的二維陣列，`0` 表示空位、`1` 表示已被佔用。請用巢狀迴圈搭配**迴圈標籤**，找到第一個空位（先列再行，由上到下、由左到右），並印出其座位編號（列, 行）。找到後立即停止搜尋。

**提示情境：** 想像系統一格一格檢查座位，一旦找到空位就要「整個搜尋程序」立刻結束，不該再檢查剩下的座位。

<!--
任務鋪陳：剛剛我們學了 `break outer` 跟 `continue outer`，這題就是它們最典型的應用場景——在二維表格裡找東西，找到就整個收工。

引導思考：大家可以先想想，如果不用標籤，要怎麼讓內層迴圈的 `break` 連外層迴圈也一起停下來？通常的做法是額外宣告一個 boolean 變數當「旗標」，每層迴圈都要檢查它。比較一下這兩種寫法，哪個比較簡潔、哪個比較容易看懂？
-->

---
layout: default
---

# 自學練習一：解題提示

### 提示說明

1. 外層迴圈代表「列」，內層迴圈代表「行」，外層加上標籤 `search:`
2. 內層迴圈中，若 `seats[row][col] == 0`，印出座位編號後執行 `break search`
3. 也可以試著改寫成 `continue` 版本：跳過已佔用座位、繼續找下一格

```java
int[][] seats = {
    {1, 1, 0, 1, 1},
    {1, 1, 1, 1, 0},
    {0, 1, 1, 1, 1}
};
search:
for (int row = 0; row < seats.length; row++) {
    for (int col = 0; col < seats[row].length; col++) {
        if (seats[row][col] == 0) {
            System.out.println("找到空位：(" + row + ", " + col + ")");
            break search;
        }
    }
}
```

<!--
逐步解說：外層的 `search:` 標籤就是這題的關鍵。一旦在內層迴圈裡找到 `0`，我們印出座位編號，然後 `break search` 直接結束整個雙層迴圈，不會再多檢查任何一格。

⚠️ 易錯點提醒：如果忘了加標籤、只寫 `break`，內層迴圈確實會停下來，但外層迴圈會繼續跑下一個 row，導致程式可能又找到「下一個」空位並重複印出——這就不符合「找到第一個就停」的需求了。

預期結果：以上面的座位表來說，第 0 列、第 2 行是第一個 `0`，所以會印出「找到空位：(0, 2)」。
-->

---
layout: default
---

# 自學練習二：精度比較專題

### 任務說明

分別用 `1,000`、`100,000`、`10,000,000` 三個不同的迭代次數執行萊布尼茨公式，觀察並印出三次估算出來的 π 值，比較迭代次數與精確度的關係。

**延伸思考：** 如果把迴圈裡的浮點數運算全部改成 `int`（去掉 `2.0` 的 `.0`），會發生什麼事？請實際執行看看，並說明原因。

<!--
任務鋪陳：剛剛我們用一百萬次迭代估算出 π，這題要請大家動手做個小實驗——改變迴圈跑的次數，看看估算值會怎麼變化。

引導思考：大家覺得「迭代次數越多，估算值會不會一定越接近正確的 π」？萊布尼茨級數收斂得特別慢，就算跑到一千萬次，可能還是只能對到小數點後幾位而已。另外，記得回去翻翻國王麥粒那一頁，想想資料型態（`int` vs `double`）對運算結果的影響，這兩題其實在傳達同一個訊息：「程式碼能跑」跟「結果是對的」是兩件不同的事。
-->

---
layout: end
---

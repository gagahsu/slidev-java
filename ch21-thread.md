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

# 什麼是多執行緒？這是什麼／用在哪裡／為何列自學

**這是什麼？**

「多執行緒（Multithreading）」讓同一個程式內，可以同時執行多個任務——例如一邊接收使用者輸入、一邊在背景下載檔案、一邊更新畫面進度條，彼此互不阻塞。

**用在哪裡？**

<div class="mt-2 p-3 bg-blue-50 border-l-4 border-blue-400 text-gray-700 text-sm text-left">
💡 <b>常見場景：</b> 網頁伺服器同時處理多個請求、GUI 應用程式背景下載不卡畫面、資料庫並行查詢、心跳監控等背景任務
</div>

**為何列為自學？**

<div class="mt-2 p-3 bg-yellow-50 border-l-4 border-yellow-400 text-gray-700 text-sm text-left">
⚠️ <b>列為自學的原因：</b> 多執行緒對零基礎學生來說認知負擔較重（需同時理解執行緒生命週期、同步機制、競爭條件等抽象概念），且<b>不是寫出基本可運作程式的必要條件</b>。基礎班只需口頭認識「這是什麼、用在哪裡」即可，完整內容留給有餘力的同學自學。
</div>

<!--
【重點解說】
今天我們不會深入教多執行緒的語法，而是用一頁帶大家認識「這是什麼」。

【生活化比喻】
想像一間銀行只有一個服務窗口，所有客戶都要排成一條長長的隊伍，一個一個慢慢辦。多執行緒就是「多開幾個窗口」，讓不同的客戶可以同時被服務，不用大家擠在一條隊伍裡。

【為什麼要學這個（但列為自學）？】
這個概念在實務上很重要——网頁伺服器、桌面應用程式幾乎都會用到。但它牽涉到「執行緒生命週期」「同步機制」「競爭條件」這些比較抽象的東西，對剛開始學程式的同學來說，一下子塞進去會消化不良。而且，我們目前學的「寫出一個能跑的 Java 程式」，並不需要用到多執行緒——所以這堂課我們先讓大家知道「有這個東西、它解決什麼問題」，之後想深入研究的同學，可以到自學版去動手玩玩看。

【口頭帶過即可】
這頁口頭帶過就好，不用逐字念，重點是讓同學知道「以後遇到『程式同時做很多事』的需求，要去找『多執行緒』這個關鍵字」。詳細的建立方式、同步機制、死結預防等，都留給自學版。
-->

---
layout: default
---

<div class="flex flex-col justify-center items-center h-full">

# 想深入了解多執行緒？

<div class="mt-6 mb-6 p-4 bg-green-50 border-l-4 border-green-400 text-gray-700 text-left" style="max-width: 600px;">
💡 自學版涵蓋：執行緒的建立方式、生命週期、sleep / join / Daemon、synchronized 同步機制、Deadlock 死結預防，以及 wait/notify 生產者消費者模式。
</div>

<Link to="ch21adv" style="display: inline-block; margin-top: 1rem; padding: 0.75rem 2rem; background: linear-gradient(90deg, #5eada0, #a7d9d0); color: #ffffff; font-weight: 700; border-radius: 2rem; text-decoration: none; letter-spacing: 0.05em;">→ 前往自學內容</Link>

</div>

<!--
【口頭帶過即可，詳細內容留給自學版】
這頁口頭帶過就好：跟同學說「如果你對多執行緒有興趣，或是未來想往後端、伺服器開發發展，這是一定要會的東西，自學版有完整的建立方式、同步機制跟練習題，下課後可以自己玩玩看」。詳細內容不在課堂上展開，留給有餘力的同學自學版慢慢消化。
-->

---
layout: end
---

# 課程結束
### 多執行緒：認識同步處理的世界

<!--
【結束語】
今天我們認識了多執行緒「是什麼、用在哪裡」，這是進入後端與系統開發很重要的一塊拼圖。有興趣的同學別忘了去自學版動手玩玩看，下課！
-->

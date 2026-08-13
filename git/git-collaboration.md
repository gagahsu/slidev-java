---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: Git 協作生存指南：從正常流程到災難搶救
routeAlias: git-collaboration
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
  <p style="color: #5eada0; font-size: 1rem; font-weight: 600; letter-spacing: 0.2em; text-transform: uppercase; margin-bottom: 1.2rem;">
    Git Collaboration & Disaster Recovery
  </p>
  <h1 style="color: #1a5c5c; font-size: 3.5rem; font-weight: 900; line-height: 1.15; margin-bottom: 1.5rem;">
    Git 協作生存指南
  </h1>
  <div style="height: 4px; width: 320px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 1.5rem;"></div>
  <p style="color: #4a7c7c; font-size: 1.15rem; font-style: italic;">
    「從正常流程到災難搶救的完整解析」
  </p>
</div>

---
layout: default
---

# Outline

- **理想世界 — 標準功能分支工作流**
- **慘案發生 — 錯誤衝突解決的蝴蝶效應**
- **急救方案 — 根據嚴重程度精準止血**
- **防範機制 — 建立團隊的免疫系統**

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 理想世界
# 標準功能分支工作流

---

# 正常開發情境：為什麼這很安全？

| 狀態 | 運作邏輯 |
| --- | --- |
| **隔離開發** | 平常在自己的 `feature` 分支工作，不影響共用的 `Dev`。 |
| **同步更新** | 回到 `Dev` 時先 `Pull`，確保本地 `Dev` 是遠端最新狀態的鏡像。 |
| **基準對齊** | 此時 `Dev` 是最新的，合併 `feature` 時，基準點 (Base) 完全正確。 |

<div class="mt-4 p-3 bg-green-50 border-l-4 border-green-400 text-gray-700 text-sm text-left">
✅ <b>結果：</b> 衝突範圍僅限於「你改的」與「別人剛推的」重疊處，絕無靈異事件。
</div>

---

# GitHub Desktop 安全 SOP

| 步驟 | 動作 | 預期結果 |
| --- | --- | --- |
| **1** | 在 `my-feature` 分支 Commit & Push | 備份你的工作進度。 |
| **2** | 切換回 `Dev` 分支 | 本地 `Dev` 應該是靜止且乾淨的。 |
| **3** | 點擊 `Fetch` 然後 `Pull` | 只有 Pull，不應該出現待 Push 的 Commit。 |
| **4** | `Merge my-feature into Dev` | 在本地完成合併，處理明確的衝突。 |
| **5** | `Push origin Dev` | 將乾淨、正確的結果送上伺服器。 |

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 慘案發生
# 錯誤衝突解決的蝴蝶效應

---

# 災難起頭：錯誤的衝突解決

當同事 A 在執行 `Merge` 或 `Pull` 遇到衝突時...

| 錯誤動作 | 實質後果 |
| --- | --- |
| **誤選 "Keep Mine"** | 同事 A 把別人的新功能**「刪除或覆蓋」**成了他本地的舊版本。 |
| **毒藥推送** | 同事 A 告訴 Git「解完了」，並 Push。遠端接收了這個帶有「刪除」動作的 Commit。 |
| **隱形災難** | 在 Git 眼裡，這是**「正確的最新真相」**，它認為團隊決定拔掉那些功能。 |

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <b>核心痛點：</b> Git 認時間線不認內容好壞。最新的 Commit 就是真相，即使裡面全是爛 Code。
</div>

---

# 連鎖反應：為什麼全體中招？

### 當你（或其他人）執行 `git pull` 時：

1. **Git 檢查遠端**：發現有一個比你更晚的「最新 Commit」（同事 A 的錯誤合併）。
2. **自動更新**：Git 為了讓你跟上進度，理所當然地把你本地正常的檔案更新成同事 A 的狀態。
3. **無聲覆蓋**：那些本來不是衝突的檔案，因為在同事 A 的 Commit 裡被「修改（變回舊版）」了，所以你的功能就此消失。

```bash
# Git 的邏輯：
# 遠端 Commit (錯誤內容) > 本地 Commit (正確內容)
# 結果：本地被更新為錯誤內容
```

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 急救方案
# 根據嚴重程度精準止血

---

# 方案 A：精準手術 (Fix Forward)

### 適用於：只想抓回被誤刪的部分功能，不想動歷史紀錄

| 步驟 | 說明 | 指令範例 |
| --- | --- | --- |
| **1** | 找出壞掉前的最後一個正常 Commit ID | `git log` |
| **2** | 從該 ID 抓回特定檔案到現在的環境 | `git checkout <ID> -- <path>` |
| **3** | 提交並推送到遠端 | `git push origin Dev` |

```bash
# 範例：從 a1b2c3 找回被蓋掉的 API 檔案
git checkout a1b2c3 -- src/services/api.js
```

---

# 方案 B：服用後悔藥 (Revert)

### 適用於：整個合併 (Merge Commit) 百分之百全是錯的

| 特點 | 說明 |
| --- | --- |
| **不改歷史** | 產生一個內容「完全相反」的新 Commit 來抵銷錯誤。 |
| **安全回滾** | 對同事最友善，因為歷史線是往前的，不會造成平行宇宙。 |

```bash
# 抵銷該次錯誤合併，保留主線路內容 (-m 1)
git revert -m 1 <錯誤合併的Commit_ID>
```

---

# 方案 C：核彈還原 (Reset + Force Push)

### 適用於：災難範圍太大，必須強行「時光倒流」

| 步驟 | 執行動作 | 指令 |
| --- | --- | --- |
| **1. 廣播** | 通知所有人停止 Push / Pull | 「大家先別動 Dev！」 |
| **2. 強推** | 本地退回正常點並強制蓋掉遠端 | `git reset --hard <ID>` <br> `git push -f origin Dev` |
| **3. 同步** | **所有人**必須重新對齊遠端基準 | `git fetch` <br> `git reset --hard origin/Dev` |

<div class="mt-4 p-3 bg-red-50 border-l-4 border-red-400 text-gray-700 text-sm text-left">
⚠️ <b>致命風險：</b> 若同事沒做第 3 步就直接 Push，那坨「殭屍髒代碼」會再度復活回到遠端！
</div>

---
layout: section
class: flex flex-col justify-center items-center text-center
---

# 防範機制
# 建立團隊的免疫系統

---

# 如何避免靈異事件再次發生？

### 1. 分支保護 (Branch Protection)
禁止任何開發者直接 `Push` 或 `Merge` 到 `Dev` 分支。

### 2. 強制 Pull Request (PR)
所有變更必須透過 PR，並在 GitHub 介面上進行合併。

### 3. 同儕審查 (Code Review)
在 PR 的 **Files Changed** 視圖中，大片的紅色刪除線能讓 Reviewer 一眼抓出「誤刪別人代碼」的衝突慘劇。

---
layout: end
---

# 課程結束
### 記住：Git 信任你，所以請謹慎處理每一次衝突。

---
theme: penguin
class: text-center
highlighter: shiki
lineNumbers: true
drawings:
  persist: false
transition: slide-left
title: Java Programming Masterclass
routeAlias: home
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

<style>
.chapter-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1rem;
  width: 100%;
  max-width: 960px;
  margin-top: 1.2rem;
}
.chapter-card {
  display: block;
  background: #f0faf9;
  border: 2px solid #5eada0;
  border-radius: 12px;
  padding: 1.2rem 0.8rem;
  text-decoration: none !important;
  color: #1a5c5c !important;
  transition: all 0.2s ease;
}
.chapter-card:hover {
  background: #5eada0;
  color: white !important;
  transform: translateY(-3px);
  box-shadow: 0 6px 16px rgba(94, 173, 160, 0.35);
}
.chapter-card:hover .chapter-subtitle {
  color: rgba(255,255,255,0.85) !important;
}
.chapter-num {
  font-size: 1.6rem;
  font-weight: 900;
  margin-bottom: 0.3rem;
}
.chapter-subtitle {
  font-size: max(13px, 0.88rem);
  color: #4a7c7c;
  margin-top: 0.3rem;
}
</style>

<div class="flex flex-col items-center h-full" style="background: #ffffff; overflow-y: auto; padding: 1.5rem 0;">
  <p style="color: #5eada0; font-size: 1rem; font-weight: 600; letter-spacing: 0.2em; text-transform: uppercase; margin-bottom: 1rem;">Java Programming Masterclass</p>
  <h1 style="color: #1a5c5c; font-size: 2.8rem; font-weight: 900; line-height: 1.2; margin-bottom: 0.5rem;">課程目錄</h1>
  <div style="height: 4px; width: 240px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-bottom: 0.5rem;"></div>
  <p style="color: #9dc4c4; font-size: 0.9rem; margin-bottom: 0;">點擊章節卡片開始學習</p>
  <div class="chapter-grid">
    <Link to="ch01" class="chapter-card">
      <div class="chapter-num">Ch 1</div>
      <div>基本觀念</div>
      <div class="chapter-subtitle">Java Introduction</div>
    </Link>
    <Link to="ch02" class="chapter-card">
      <div class="chapter-num">Ch 2</div>
      <div>Java 程式從零開始</div>
      <div class="chapter-subtitle">First Java Program</div>
    </Link>
    <Link to="ch03" class="chapter-card">
      <div class="chapter-num">Ch 3</div>
      <div>Java 語言基礎</div>
      <div class="chapter-subtitle">Variables &amp; Data Types</div>
    </Link>
    <Link to="ch04" class="chapter-card">
      <div class="chapter-num">Ch 4</div>
      <div>程式基本運算</div>
      <div class="chapter-subtitle">Operators &amp; Expressions</div>
    </Link>
    <Link to="ch05" class="chapter-card">
      <div class="chapter-num">Ch 5</div>
      <div>程式流程控制</div>
      <div class="chapter-subtitle">Flow Control</div>
    </Link>
    <Link to="ch06" class="chapter-card">
      <div class="chapter-num">Ch 6</div>
      <div>迴圈控制</div>
      <div class="chapter-subtitle">Loop Control</div>
    </Link>
    <Link to="ch07" class="chapter-card">
      <div class="chapter-num">Ch 7</div>
      <div>陣列</div>
      <div class="chapter-subtitle">Arrays</div>
    </Link>
    <Link to="ch08" class="chapter-card">
      <div class="chapter-num">Ch 8</div>
      <div>類別與物件</div>
      <div class="chapter-subtitle">Class &amp; Objects</div>
    </Link>
    <Link to="ch09" class="chapter-card">
      <div class="chapter-num">Ch 9</div>
      <div>物件建構與封裝</div>
      <div class="chapter-subtitle">Constructor &amp; Encapsulation</div>
    </Link>
    <Link to="ch10" class="chapter-card">
      <div class="chapter-num">Ch 10</div>
      <div>Math 和 Random 類別</div>
      <div class="chapter-subtitle">Math &amp; Random</div>
    </Link>
    <Link to="ch11" class="chapter-card">
      <div class="chapter-num">Ch 11</div>
      <div>日期與時間的類別</div>
      <div class="chapter-subtitle">Date &amp; Time API</div>
    </Link>
    <Link to="ch12" class="chapter-card">
      <div class="chapter-num">Ch 12</div>
      <div>字元與字串類別</div>
      <div class="chapter-subtitle">Character &amp; String</div>
    </Link>
    <Link to="ch13" class="chapter-card">
      <div class="chapter-num">Ch 13</div>
      <div>正規表達式</div>
      <div class="chapter-subtitle">Regular Expression</div>
    </Link>
    <Link to="ch14" class="chapter-card">
      <div class="chapter-num">Ch 14</div>
      <div>繼承與多形</div>
      <div class="chapter-subtitle">Inheritance &amp; Polymorphism</div>
    </Link>
    <Link to="ch15" class="chapter-card">
      <div class="chapter-num">Ch 15</div>
      <div>Object 類別</div>
      <div class="chapter-subtitle">The Root of All Classes</div>
    </Link>
    <Link to="ch16" class="chapter-card">
      <div class="chapter-num">Ch 16</div>
      <div>抽象類別</div>
      <div class="chapter-subtitle">Abstract Class</div>
    </Link>
    <Link to="interface" class="chapter-card">
      <div class="chapter-num">Ch 17</div>
      <div>介面與多重繼承</div>
      <div class="chapter-subtitle">Interface &amp; Multiple Inheritance</div>
    </Link>
    <Link to="ch20" class="chapter-card">
      <div class="chapter-num">Ch 20</div>
      <div>程式異常的處理</div>
      <div class="chapter-subtitle">Exception Handling</div>
    </Link>
    <Link to="ch24" class="chapter-card">
      <div class="chapter-num">Ch 24</div>
      <div>集合框架</div>
      <div class="chapter-subtitle">Collection Framework</div>
    </Link>
    <Link to="ch25" class="chapter-card">
      <div class="chapter-num">Ch 25</div>
      <div>Stream 與 Lambda</div>
      <div class="chapter-subtitle">Modern Java API</div>
    </Link>
    <Link to="aitools" class="chapter-card">
      <div class="chapter-num" style="font-size: 1rem;">AI Tools</div>
      <div>Codex CLI、MCP 與 Skills</div>
      <div class="chapter-subtitle">AI Developer Tools</div>
    </Link>
  </div>
</div>

---
src: ./ch01-java-intro.md
---

---
src: ./ch02-java-basics.md
---

---
src: ./ch03-java-language-basics.md
---

---
src: ./ch04-operators.md
---

---
src: ./ch05-flow-control.md
---

---
src: ./ch06-loops.md
---

---
src: ./ch07-arrays.md
---

---
src: ./ch08-class-objects.md
---

---
src: ./ch09-constructor-encapsulation.md
---

---
src: ./ch10-math-random.md
---

---
src: ./ch11-datetime.md
---

---
src: ./ch12-char-string.md
---

---
src: ./ch13-regex.md
---

---
src: ./ch14-inheritance.md
---

---
src: ./ch15-object-class.md
---

---
src: ./ch16-abstract-class.md
---

---
src: ./ch17-interface.md
---

---
src: ./ch20-exception.md
---

---
src: ./ch24-collection.md
---

---
src: ./ch25-stream-lambda.md
---

---
src: ./ai-dev-tools.md
---


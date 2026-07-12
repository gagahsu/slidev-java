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
.chapter-card-adv {
  border-color: #e0a96d;
}
.chapter-card-adv .chapter-badge {
  color: #c97b2c;
  font-size: 0.75rem;
  font-weight: 700;
  letter-spacing: 0.1em;
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
    <Link to="ch18" class="chapter-card">
      <div class="chapter-num">Ch 18</div>
      <div>包裝類別</div>
      <div class="chapter-subtitle">Wrapper Classes</div>
    </Link>
    <Link to="ch19" class="chapter-card">
      <div class="chapter-num">Ch 19</div>
      <div>設計套件</div>
      <div class="chapter-subtitle">Package Design</div>
    </Link>
    <Link to="ch20" class="chapter-card">
      <div class="chapter-num">Ch 20</div>
      <div>程式異常的處理</div>
      <div class="chapter-subtitle">Exception Handling</div>
    </Link>
    <Link to="ch21" class="chapter-card">
      <div class="chapter-num">Ch 21</div>
      <div>多執行緒（自學）</div>
      <div class="chapter-subtitle">Multithreading</div>
    </Link>
    <Link to="ch22" class="chapter-card">
      <div class="chapter-num">Ch 22</div>
      <div>輸入與輸出（自學）</div>
      <div class="chapter-subtitle">Java I/O</div>
    </Link>
    <Link to="ch23" class="chapter-card">
      <div class="chapter-num">Ch 23</div>
      <div>壓縮與解壓縮（自學）</div>
      <div class="chapter-subtitle">Zip &amp; Unzip</div>
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
    <Link to="ch26" class="chapter-card">
      <div class="chapter-num">Ch 26</div>
      <div>全課程總複習</div>
      <div class="chapter-subtitle">Course Review</div>
    </Link>
  </div>
  <div style="height: 2px; width: 240px; background: linear-gradient(90deg, #e0a96d, #f0c896); border-radius: 2px; margin-top: 2rem; margin-bottom: 0.5rem;"></div>
  <p style="color: #c97b2c; font-size: 1.1rem; font-weight: 700; margin-bottom: 0;">進階／自學內容</p>
  <div class="chapter-grid">
    <Link to="ch03adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 3</div>
      <div class="chapter-badge">進階・自學</div>
      <div>Java 語言基礎</div>
      <div class="chapter-subtitle">Variables &amp; Data Types (Advanced)</div>
    </Link>
    <Link to="ch04adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 4</div>
      <div class="chapter-badge">進階・自學</div>
      <div>程式基本運算</div>
      <div class="chapter-subtitle">Operators &amp; Expressions (Advanced)</div>
    </Link>
    <Link to="ch05adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 5</div>
      <div class="chapter-badge">進階・自學</div>
      <div>程式流程控制</div>
      <div class="chapter-subtitle">Flow Control (Advanced)</div>
    </Link>
    <Link to="ch06adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 6</div>
      <div class="chapter-badge">進階・自學</div>
      <div>迴圈控制</div>
      <div class="chapter-subtitle">Loop Control (Advanced)</div>
    </Link>
    <Link to="ch07adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 7</div>
      <div class="chapter-badge">進階・自學</div>
      <div>陣列</div>
      <div class="chapter-subtitle">Arrays (Advanced)</div>
    </Link>
    <Link to="ch08adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 8</div>
      <div class="chapter-badge">進階・自學</div>
      <div>類別與物件</div>
      <div class="chapter-subtitle">Class &amp; Objects (Advanced)</div>
    </Link>
    <Link to="ch09adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 9</div>
      <div class="chapter-badge">進階・自學</div>
      <div>物件建構與封裝</div>
      <div class="chapter-subtitle">Constructor &amp; Encapsulation (Advanced)</div>
    </Link>
    <Link to="ch10adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 10</div>
      <div class="chapter-badge">進階・自學</div>
      <div>Math 和 Random 類別</div>
      <div class="chapter-subtitle">Math &amp; Random (Advanced)</div>
    </Link>
    <Link to="ch11adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 11</div>
      <div class="chapter-badge">進階・自學</div>
      <div>日期與時間的類別</div>
      <div class="chapter-subtitle">Date &amp; Time API (Advanced)</div>
    </Link>
    <Link to="ch12adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 12</div>
      <div class="chapter-badge">進階・自學</div>
      <div>字元與字串類別</div>
      <div class="chapter-subtitle">Character &amp; String (Advanced)</div>
    </Link>
    <Link to="ch13adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 13</div>
      <div class="chapter-badge">進階・自學</div>
      <div>正規表達式</div>
      <div class="chapter-subtitle">Regular Expression (Advanced)</div>
    </Link>
    <Link to="ch14adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 14</div>
      <div class="chapter-badge">進階・自學</div>
      <div>繼承與多形</div>
      <div class="chapter-subtitle">Inheritance &amp; Polymorphism (Advanced)</div>
    </Link>
    <Link to="ch15adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 15</div>
      <div class="chapter-badge">進階・自學</div>
      <div>Object 類別</div>
      <div class="chapter-subtitle">The Root of All Classes (Advanced)</div>
    </Link>
    <Link to="ch16adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 16</div>
      <div class="chapter-badge">進階・自學</div>
      <div>抽象類別</div>
      <div class="chapter-subtitle">Abstract Class (Advanced)</div>
    </Link>
    <Link to="interfaceadv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 17</div>
      <div class="chapter-badge">進階・自學</div>
      <div>介面與多重繼承</div>
      <div class="chapter-subtitle">Interface &amp; Multiple Inheritance (Advanced)</div>
    </Link>
    <Link to="ch18adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 18</div>
      <div class="chapter-badge">進階・自學</div>
      <div>包裝類別</div>
      <div class="chapter-subtitle">Wrapper Classes (Advanced)</div>
    </Link>
    <Link to="ch19adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 19</div>
      <div class="chapter-badge">進階・自學</div>
      <div>設計套件</div>
      <div class="chapter-subtitle">Package Design (Advanced)</div>
    </Link>
    <Link to="ch20adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 20</div>
      <div class="chapter-badge">進階・自學</div>
      <div>程式異常的處理</div>
      <div class="chapter-subtitle">Exception Handling (Advanced)</div>
    </Link>
    <Link to="ch21adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 21</div>
      <div class="chapter-badge">進階・自學</div>
      <div>多執行緒</div>
      <div class="chapter-subtitle">Multithreading (Advanced)</div>
    </Link>
    <Link to="ch22adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 22</div>
      <div class="chapter-badge">進階・自學</div>
      <div>輸入與輸出</div>
      <div class="chapter-subtitle">Java I/O (Advanced)</div>
    </Link>
    <Link to="ch23adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 23</div>
      <div class="chapter-badge">進階・自學</div>
      <div>壓縮與解壓縮</div>
      <div class="chapter-subtitle">Zip &amp; Unzip (Advanced)</div>
    </Link>
    <Link to="ch24adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 24</div>
      <div class="chapter-badge">進階・自學</div>
      <div>集合框架</div>
      <div class="chapter-subtitle">Collection Framework (Advanced)</div>
    </Link>
    <Link to="ch25adv" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Ch 25</div>
      <div class="chapter-badge">進階・自學</div>
      <div>Stream 與 Lambda</div>
      <div class="chapter-subtitle">Modern Java API (Advanced)</div>
    </Link>
  </div>
  <div style="height: 2px; width: 240px; background: linear-gradient(90deg, #5eada0, #a7d9d0); border-radius: 2px; margin-top: 2rem; margin-bottom: 0.5rem;"></div>
  <p style="color: #5eada0; font-size: 1.1rem; font-weight: 700; margin-bottom: 0;">特別篇</p>
  <div class="chapter-grid">
    <Link to="demo-oop" class="chapter-card chapter-card-adv">
      <div class="chapter-num">Demo</div>
      <div class="chapter-badge">AI 協作・試教</div>
      <div>物件導向入門與封裝</div>
      <div class="chapter-subtitle">OOP &amp; Encapsulation × AI</div>
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
src: ./ch18-wrapper.md
---

---
src: ./ch19-package.md
---

---
src: ./ch20-exception.md
---

---
src: ./ch21-thread.md
---

---
src: ./ch22-io.md
---

---
src: ./ch23-zip.md
---

---
src: ./ch24-collection.md
---

---
src: ./ch25-stream-lambda.md
---

---
src: ./ch03-java-language-basics-adv.md
---

---
src: ./ch04-operators-adv.md
---

---
src: ./ch05-flow-control-adv.md
---

---
src: ./ch06-loops-adv.md
---

---
src: ./ch07-arrays-adv.md
---

---
src: ./ch08-class-objects-adv.md
---

---
src: ./ch09-constructor-encapsulation-adv.md
---

---
src: ./ch10-math-random-adv.md
---

---
src: ./ch11-datetime-adv.md
---

---
src: ./ch12-char-string-adv.md
---

---
src: ./ch13-regex-adv.md
---

---
src: ./ch14-inheritance-adv.md
---

---
src: ./ch15-object-class-adv.md
---

---
src: ./ch16-abstract-class-adv.md
---

---
src: ./ch17-interface-adv.md
---

---
src: ./ch18-wrapper-adv.md
---

---
src: ./ch19-package-adv.md
---

---
src: ./ch20-exception-adv.md
---

---
src: ./ch21-thread-adv.md
---

---
src: ./ch22-io-adv.md
---

---
src: ./ch23-zip-adv.md
---

---
src: ./ch24-collection-adv.md
---

---
src: ./ch25-stream-lambda-adv.md
---

---
src: ./ch26-course-review.md
---

---
src: ./demo-oop-encapsulation.md
---



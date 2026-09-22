const DATA_DIR = "./data";
const WRONG_KEY = "quiz-wrong-questions";

let manifest = { chapters: [] };
const chapterCache = new Map();

async function loadManifest() {
  const res = await fetch(`${DATA_DIR}/manifest.json`);
  manifest = await res.json();
}

async function loadChapter(id) {
  if (chapterCache.has(id)) return chapterCache.get(id);
  const entry = manifest.chapters.find((c) => c.id === id);
  if (!entry) return null;
  const res = await fetch(`${DATA_DIR}/${entry.file}`);
  const data = await res.json();
  chapterCache.set(id, data);
  return data;
}

async function loadAllQuestions() {
  const all = [];
  for (const c of manifest.chapters) {
    const data = await loadChapter(c.id);
    if (data) all.push(...data.questions.map((q) => ({ ...q, chapter: c.id })));
  }
  return all;
}

function getWrongIds() {
  try {
    return JSON.parse(localStorage.getItem(WRONG_KEY) || "[]");
  } catch {
    return [];
  }
}

function addWrongId(id) {
  const set = new Set(getWrongIds());
  set.add(id);
  localStorage.setItem(WRONG_KEY, JSON.stringify([...set]));
}

function removeWrongId(id) {
  const set = new Set(getWrongIds());
  set.delete(id);
  localStorage.setItem(WRONG_KEY, JSON.stringify([...set]));
}

function shuffle(arr) {
  const a = [...arr];
  for (let i = a.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [a[i], a[j]] = [a[j], a[i]];
  }
  return a;
}

function renderQuestion(container, q, { onAnswered } = {}) {
  const card = document.createElement("div");
  card.className = "question-card";
  if (q.id) card.id = `q-${q.id}`;

  if (q.page) {
    const back = document.createElement("a");
    back.className = "back-to-slide";
    back.href = `/${q.page}`;
    back.textContent = `↩ 回到投影片 p.${q.page}`;
    card.appendChild(back);
  }

  const text = document.createElement("div");
  text.className = "question-text";
  text.textContent = q.question;
  card.appendChild(text);

  q.options.forEach((opt, i) => {
    const btn = document.createElement("button");
    btn.className = "option-btn";
    btn.textContent = opt;
    btn.addEventListener("click", () => {
      const correct = i === q.answer;
      [...card.querySelectorAll(".option-btn")].forEach((b, bi) => {
        b.disabled = true;
        if (bi === q.answer) b.classList.add("correct");
        if (bi === i && !correct) b.classList.add("wrong");
      });

      if (q.id) {
        if (correct) removeWrongId(q.id);
        else addWrongId(q.id);
      }

      if (q.explanation) {
        const exp = document.createElement("div");
        exp.className = "explanation";
        exp.textContent = q.explanation;
        card.appendChild(exp);
      }

      onAnswered?.(correct);
    });
    card.appendChild(btn);
  });

  container.appendChild(card);
}

function renderQuizList(container, questions) {
  container.innerHTML = "";
  questions.forEach((q) => renderQuestion(container, q));
}

async function populatePickers() {
  const options = manifest.chapters
    .map((c) => `<option value="${c.id}">${c.title}</option>`)
    .join("");
  document.getElementById("chapter-picker").innerHTML = options;
  document.getElementById("flashcard-picker").innerHTML = options;
}

async function onChapterChange(highlightQuestionId) {
  const id = document.getElementById("chapter-picker").value;
  const data = await loadChapter(id);
  const container = document.getElementById("chapter-quiz");
  if (!data) return;
  renderQuizList(container, data.questions);

  if (highlightQuestionId) {
    const target = document.getElementById(`q-${highlightQuestionId}`);
    if (target) {
      target.classList.add("target-question");
      target.scrollIntoView({ behavior: "smooth", block: "center" });
    }
  }
}

let flashcardState = { cards: [], index: 0 };

function renderFlashcard() {
  const area = document.getElementById("flashcard-area");
  area.innerHTML = "";
  const { cards, index } = flashcardState;
  if (!cards.length) return;

  const card = cards[index];
  const el = document.createElement("div");
  el.className = "flashcard";
  el.textContent = card.question;
  let flipped = false;
  el.addEventListener("click", () => {
    flipped = !flipped;
    el.textContent = flipped ? card.options[card.answer] : card.question;
    el.classList.toggle("flipped", flipped);
  });
  area.appendChild(el);

  const nav = document.createElement("div");
  nav.className = "flashcard-nav";
  nav.innerHTML = `
    <button id="fc-prev">← 上一張</button>
    <span>${index + 1} / ${cards.length}</span>
    <button id="fc-next">下一張 →</button>
  `;
  area.appendChild(nav);

  nav.querySelector("#fc-prev").addEventListener("click", () => {
    flashcardState.index = (index - 1 + cards.length) % cards.length;
    renderFlashcard();
  });
  nav.querySelector("#fc-next").addEventListener("click", () => {
    flashcardState.index = (index + 1) % cards.length;
    renderFlashcard();
  });
}

async function onFlashcardChange() {
  const id = document.getElementById("flashcard-picker").value;
  const data = await loadChapter(id);
  if (!data) return;
  flashcardState = { cards: data.questions, index: 0 };
  renderFlashcard();
}

async function startMixedQuiz() {
  const all = await loadAllQuestions();
  const mixed = shuffle(all);
  const container = document.getElementById("mixed-quiz");
  container.innerHTML = "";

  const progress = document.createElement("div");
  progress.className = "progress-bar";
  let correctCount = 0;
  let answeredCount = 0;
  progress.textContent = `0 / ${mixed.length}（答對 0）`;
  container.appendChild(progress);

  mixed.forEach((q) => {
    renderQuestion(container, q, {
      onAnswered: (correct) => {
        answeredCount++;
        if (correct) correctCount++;
        progress.textContent = `${answeredCount} / ${mixed.length}（答對 ${correctCount}）`;
      },
    });
  });
}

function setupTabs() {
  document.querySelectorAll(".tab-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      document.querySelectorAll(".tab-btn").forEach((b) => b.classList.remove("active"));
      document.querySelectorAll(".tab-panel").forEach((p) => p.classList.remove("active"));
      btn.classList.add("active");
      document.getElementById(`tab-${btn.dataset.tab}`).classList.add("active");
    });
  });
}

function switchTab(tab) {
  document.querySelectorAll(".tab-btn").forEach((b) => b.classList.toggle("active", b.dataset.tab === tab));
  document.querySelectorAll(".tab-panel").forEach((p) => p.classList.toggle("active", p.id === `tab-${tab}`));
}

async function init() {
  await loadManifest();
  await populatePickers();
  setupTabs();

  document.getElementById("chapter-picker").addEventListener("change", onChapterChange);
  document.getElementById("flashcard-picker").addEventListener("change", onFlashcardChange);
  document.getElementById("mixed-start").addEventListener("click", startMixedQuiz);
  document.getElementById("mixed-clear-wrong").addEventListener("click", () => {
    localStorage.removeItem(WRONG_KEY);
    alert("錯題收藏已清除");
  });

  if (manifest.chapters.length) {
    const params = new URLSearchParams(location.search);
    const chapter = params.get("chapter");
    const question = params.get("question");
    if (chapter && manifest.chapters.some((c) => c.id === chapter)) {
      document.getElementById("chapter-picker").value = chapter;
      document.getElementById("flashcard-picker").value = chapter;
      switchTab("chapter");
    }
    await onChapterChange(question);
    await onFlashcardChange();
  }
}

init();

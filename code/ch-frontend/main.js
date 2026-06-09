// main.js — 打招呼頁面的互動邏輯
// 監聽按鈕點擊，讀取輸入框的值並更新訊息段落

const btn   = document.getElementById('greet-btn');
const input = document.getElementById('name-input');
const msg   = document.getElementById('message');

btn.addEventListener('click', () => {
  const name = input.value.trim(); // trim() 去除前後空白

  if (name) {
    msg.textContent = `你好，${name}！歡迎來到前端世界！`;
  } else {
    msg.textContent = '請輸入你的名字！';
  }
});

// 按 Enter 鍵也能觸發（進階功能）
input.addEventListener('keydown', (event) => {
  if (event.key === 'Enter') {
    btn.click();
  }
});

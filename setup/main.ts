import { defineAppSetup } from '@slidev/types'

// GitHub Pages 部署會整批替換帶 hash 的資產檔名。已開啟（或快取住舊版
// index.html）的瀏覽器在切換投影片時，會去抓「已被新部署刪除的舊 chunk」
// 而得到 404，畫面就顯示不出來。這裡監聽 Vite 的 preload 失敗事件，
// 自動重新載入一次以取得新版本，避免部署後舊分頁整個掛掉。
export default defineAppSetup(() => {
  if (typeof window === 'undefined')
    return

  const KEY = 'slidev-reloaded-on-preload-error'

  // 上次因 preload 失敗而重載成功後，稍後清除旗標，讓未來的部署仍可觸發重載
  if (sessionStorage.getItem(KEY))
    setTimeout(() => sessionStorage.removeItem(KEY), 10_000)

  window.addEventListener('vite:preloadError', (event) => {
    if (sessionStorage.getItem(KEY))
      return // 已重載過仍失敗，避免無限重載循環
    sessionStorage.setItem(KEY, '1')
    event.preventDefault()
    window.location.reload()
  })
})

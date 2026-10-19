<template>
  <footer
    v-if="$nav.currentLayout !== 'cover'"
    class="absolute bottom-0 right-0 p-2 text-sm text-gray-400 z-50"
  >
    {{ $nav.currentPage }} / {{ $nav.total }}
  </footer>
</template>

<style>
/* Force inline code to be readable on white backgrounds */
.slidev-layout :not(pre) > code {
  color: #1a5c5c !important;
  background-color: #e6f4f1 !important;
}
.slidev-layout :not(pre) > code span {
  color: inherit !important;
}

/* Penguin theme caps code blocks at 600px, which wraps longer lines; use the full content width */
.slidev-layout .slidev-code-wrapper {
  max-width: 100% !important;
}

/* Keep code blocks (incl. ones nested in list items) within their container width */
.slidev-layout pre.slidev-code {
  max-width: 100%;
  box-sizing: border-box;
}

/* Fix Slidev v52+ Presenter Mode Layout Overflow */
.grid-container {
  width: 100vw !important;
}
</style>

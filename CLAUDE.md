# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
pnpm dev              # Start single dev server at localhost:3030 (all chapters)
pnpm run ch02         # Start only Ch02 環境安裝
pnpm run ch13         # Start only Ch13 字元與字串
pnpm run ch14         # Start only Ch14 正規表達式
pnpm build            # Build to dist/ for deployment
pnpm run export:all   # Export all chapter decks to dist/*.pdf (accepts "ch14" or "14-25")
```

Package manager is **pnpm** (not npm/yarn). The `.npmrc` sets `shamefully-hoist=true` required by Slidev.

## Architecture

This is a **Slidev** presentation project. All slide files live at the root level. A single server (`pnpm dev`) serves all chapters through one entry point.

### Entry Point
- `index.md` — Portal page (目錄頁) with chapter navigation cards. Imports all chapter decks via `src:`.

### Slide Decks
All decks use the `penguin` theme and are named `ch<NN>-<slug>.md`:
- `ch01-java-intro.md` … `ch27-course-review.md` — 基礎版, one per chapter
- `ch04-…-adv.md` … `ch26-…-adv.md` — 進階／自學版, paired with the basic deck (routeAlias `chNNadv`)
- `demo-oop-encapsulation.md` — 特別篇 (routeAlias `demo-oop`)

Chapter numbers are dense and 1-indexed; the deck number, the `routeAlias`, and the `Ch N` label on
the index card must always agree. Renumbering means renaming files **and** rewriting
`routeAlias:`, `<Link to="chNN">`, prose references (`Ch 8`, `第 4 章`), and `package.json` scripts.

### Images
Chapter screenshots live in `public/img/<topic>/` and are referenced from the root path
(e.g. `<img src="/img/env/jdk-01-download.png">`). Slidev serves `public/` at the site root.

### Vue Components
- `global-bottom.vue` — Footer rendered on every slide showing page X/Y

### Templates
- `_template/` — Blueprint for new chapters (slides.md, global-bottom.vue, package.json, .npmrc)

## Navigation

Slidev navigation uses `routeAlias` + Slidev's `<Link>` component (NOT `<RouterLink>` or `<a href>`):

```yaml
# In slide frontmatter:
routeAlias: ch13
```

```html
<!-- In slide HTML: -->
<Link to="ch13">Go to Ch13</Link>
<Link to="home">← 返回目錄</Link>
```

## Adding a New Chapter

1. Create `<chXX-name>.md` at root with `routeAlias: chXX` in frontmatter and `<Link to="home">← 返回目錄</Link>` in the cover slide
2. Add `src: ./<chXX-name>.md` block at the end of `index.md`
3. Add a `<Link to="chXX" class="chapter-card">` card to `index.md`'s `.chapter-grid`
4. Run `pnpm dev` — no additional installs needed

## Slidev Conventions

- Each slide deck's front-matter YAML controls theme, global CSS, and slide-level defaults
- Per-slide layouts set with `layout:` in slide front-matter (e.g., `section`, `two-cols`, `cover`)
- Progressive reveal uses `v-click` / `v-clicks` directives
- Custom styles are written inline in the front-matter `style:` block — there are no separate CSS files
- Tailwind utility classes (e.g., `flex`, `mt-6`, `bg-blue-50`) work directly in slide markdown
- Theme is `penguin` for all decks

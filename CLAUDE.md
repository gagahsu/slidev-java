# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Branches

This checkout is the **`work` branch** (git worktree at `my-clas-work/`), used for 工作教學.
It was branched from tag `v0.1.0`; the channel version lives on `main` in `../my-clas/`.
The two lines are **never merged into each other**.

- Chapter numbering here follows `v0.1.0` (Ch01–25, no basic/advanced split). It differs from `main`,
  which inserted Ch02 環境安裝 / Ch09 方法 and renumbered later chapters.
- Port fixes from `main` only via small, focused `git cherry-pick`s; expect conflicts from renamed files.
- Release tags for this line: `work-v1.x` (not `v0.x`).
- Pushing `work` does not deploy GitHub Pages (Pages deploys from `main` only).

## Commands

```bash
pnpm dev              # Start single dev server at localhost:3030 (all chapters)
pnpm run ch12         # Start only Ch12 字元與字串
pnpm run ch13         # Start only Ch13 正規表達式
pnpm build            # Build to dist/ for deployment
pnpm run export:all   # Export chapter decks to PDF (scripts/export-all.mjs)
```

Package manager is **pnpm** (not npm/yarn). The `.npmrc` sets `shamefully-hoist=true` required by Slidev.

## Architecture

This is a **Slidev** presentation project. All slide files live at the root level. A single server (`pnpm dev`) serves all chapters through one entry point.

### Entry Point
- `index.md` — Portal page (目錄頁) with chapter navigation cards. Imports all chapter decks via `src:`.

### Slide Decks
All decks use the `penguin` theme and are named `ch<NN>-<slug>.md`, one per chapter:
`ch01-java-intro.md` … `ch25-stream-lambda.md` (Ch01–25, no `-adv` decks on this branch).

The deck number, `routeAlias`, and the `Ch N` label on the index card should agree.
Exception: `ch17-interface.md` uses `routeAlias: interface` (linked from `index.md` as `<Link to="interface">`).

Speaker scripts live in `scripts/chNN-script.md`.

### Vue Components
- `global-bottom.vue` — Footer rendered on every slide showing page X/Y

### Templates
- `_template/` — Blueprint for new chapters (slides.md, global-bottom.vue, package.json, .npmrc)

## Curriculum Backlog

`CURRICULUM-TODO.md` tracks content gaps per chapter (untaught APIs used in exercises, missing imports, etc.).
- Before editing a chapter, check its section there; when an item is done, tick it and note the commit.
- Run its 通用檢查清單 when changing slides, exercises, or notes.
- Record newly found gaps there instead of leaving them only in chat.

## Navigation

Slidev navigation uses `routeAlias` + Slidev's `<Link>` component (NOT `<RouterLink>` or `<a href>`):

```yaml
# In slide frontmatter:
routeAlias: ch12
```

```html
<!-- In slide HTML: -->
<Link to="ch12">Go to Ch12</Link>
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

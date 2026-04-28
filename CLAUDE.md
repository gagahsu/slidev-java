# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
pnpm dev              # Start single dev server at localhost:3030
pnpm run ch12         # Start only Ch12 (localhost:3030)
pnpm run ch13         # Start only Ch13 (localhost:3031)
pnpm run ch13g        # Start only Ch13G (localhost:3032)
pnpm build            # Build to dist/ for deployment
pnpm export           # Export slides to PDF
```

Package manager is **pnpm** (not npm/yarn). The `.npmrc` sets `shamefully-hoist=true` required by Slidev.

## Architecture

This is a **Slidev** presentation project. All slide files live at the root level. A single server (`pnpm dev`) serves all chapters through one entry point.

### Entry Point
- `index.md` — Portal page (目錄頁) with chapter navigation cards. Imports all chapter decks via `src:`.

### Slide Decks
- `ch12-char-string.md` — Ch12 字元與字串類別, uses `penguin` theme
- `ch13-regex.md` — Ch13 正規表達式, uses `penguin` theme
- `ch13-regex-gemini.md` — Ch13G 正規表達式 (Gemini 版), uses `penguin` theme

### Vue Components
- `global-bottom.vue` — Footer rendered on every slide showing page X/Y

### Templates
- `_template/` — Blueprint for new chapters (slides.md, global-bottom.vue, package.json, .npmrc)

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

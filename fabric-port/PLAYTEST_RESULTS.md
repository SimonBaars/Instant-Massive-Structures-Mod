# IMS Playtest Results

Date: 2026-09-05 evening / 2026-09-06 early AM (PT)
World: `imsplay` (Creative)
Client: Fabric Loom `runClient` on DISPLAY=:4 (`-Pimsplay` quickPlay); left running; did not kill other clients on :1/:2/:3.

## Results

- **Creative tab population: PASS** — Root cause was registering the creative tab **without** a `displayItems` callback, so the IMS tab existed but listed zero entries. Fixed by using vanilla `CreativeModeTab.builder(...).displayItems(...)` and accepting all 952 `StructureRegistry` BlockItems. Startup log: `creative tab has 952 items`. In-game tab title shows **Instant Massive Structures**; grid is filled with structure icons and has a scrollbar. Screenshot: `playtest-shots/06-creative-tab-populated.webp` (also `.png`). Tooltip example: `playtest-shots/06b-creative-tab-tooltip.png`. Search for `air_balloon` also returns IMS items: `06c-search-air-balloon.png`.

- **Lang / display names: improved** — Generated `en_us.json` entries for all 952 block IDs (plus item keys). Pre-fix tooltips showed raw keys like `block.imsm.block_mega_tower`; after resource reload pretty names should apply. A few curated overrides kept (Wooden House, Live Ferris Wheel, etc.).

- **Textures: PASS** — Prior pass; hotbar and tab icons show real textures (not purple/black).

- **Live Ferris Wheel animation: PASS** — Prior pass (`LiveStructureTicker`, 40-tick interval).

## Screenshots

- `playtest-shots/01-creative-tab.webp` (prior; empty/unverified)
- `playtest-shots/02-structure-placed.webp` (prior)
- `playtest-shots/03-textures-fixed.webp`
- `playtest-shots/04-ferris-wheel.webp`
- `playtest-shots/05-ferris-midframe.webp`
- `playtest-shots/06-creative-tab-populated.webp` (**populated IMS tab**)
- `playtest-shots/06b-creative-tab-tooltip.png`
- `playtest-shots/06c-search-air-balloon.png`

## Remaining gaps (honest)

- Other **live** structures beyond Ferris Wheel only lightly exercised.
- Auto-generated lang names are utilitarian (`block_mega_tower` → `Block Mega Tower`); not hand-tuned lore names.
- Large schematics still stress llvmpipe / integrated server (“Can't keep up”).
- Special held-item interactions (Redstone / Book / Fire Charge messages) not re-checked this pass.

## Commits (local only)

- Fix creative tab `displayItems` population (952 items)
- Generate full `en_us.json` block/item names
- Playtest docs + PORT_STATUS update

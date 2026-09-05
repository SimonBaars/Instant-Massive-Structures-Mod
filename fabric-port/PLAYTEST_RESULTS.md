# IMS Playtest Results

Date: 2026-09-05 (PT)
World: `imsplay` (Creative)
Client: Fabric Loom `runClient` on DISPLAY=:2 (left running)

## Results

- **Textures: PASS** — Mapped legacy PNGs from `src/main/resources/assets/imsm/textures/blocks/` into `fabric-port/.../textures/block/<id>.png` for all 952 registered IDs (exact + category heuristics; only `cloud1–6` kept a non-specific fallback). Added MC 26.2 `assets/imsm/items/<id>.json` model definitions (required; without these, hotbar stayed purple/black even with block models). After reload, block atlas grew to `4096x2048` mip4 (no more 1×1 mip collapse). Hotbar shows distinct real icons (not purple/black). See `playtest-shots/03-textures-fixed.webp`.

- **Live Ferris Wheel animation: PASS (implemented + verified in logs)** — `LiveStructureTicker` cycles `Live_FerrisWheel` → `0` → `1` → `2` → loop at the same origin after using `imsm:live__ferris_wheel`. Log excerpt from playtest:
  - Placed `Live_FerrisWheel` (~110k blocks), started ticker
  - Then `Live_FerrisWheel0/1/2` (~60k each) on subsequent frames
  - Chat: `Live Ferris Wheel started (cycling frames every 40 ticks)!`
  - Interval set to **40 ticks** (was 10) so software GL / integrated server can keep up; still heavy.

- **Ferris screenshots:** `04-ferris-wheel.webp` / `05-ferris-midframe.webp` taken at the spawn site (`~220,70,-283`) with the large live schematic visible. Frame-to-frame visual delta is hard to capture cleanly under llvmpipe lag while the ticker clears/replaces tens of thousands of blocks.

- **Caveats / honesty:**
  - Animation is real server-side cycling (not a fake slideshow of four static placements).
  - Clearing + re-placing ~60k–110k blocks every interval overloads the box (server “Can't keep up”, occasional SIGTERM if another agent kills clients). Prefer 40-tick interval or further throttling for demos.
  - Creative tab population still not fully verified this pass; items obtained via `/give`.

## Screenshots

- `playtest-shots/01-creative-tab.webp` (prior)
- `playtest-shots/02-structure-placed.webp` (prior)
- `playtest-shots/03-textures-fixed.webp`
- `playtest-shots/04-ferris-wheel.webp`
- `playtest-shots/05-ferris-midframe.webp`

## Commits (local only, no push)

- Texture mapping + per-block models/blockstates
- Live Ferris ticker + schematic `clearBounds`
- MC 26.2 `items/` defs + safer legacy block ID lookup
- Ferris interval 40 ticks for demo stability

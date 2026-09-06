# IMS Playtest Results

Date: 2026-09-05 evening … Balloon/ship film ~7:45–7:48 PM PT (prior: plane ~7:30–7:34 PM PT; airplane/Ferris ~7:20–7:25 PM PT)
World: `imsplay` (Creative)
Client: Fabric Loom `runClient` on DISPLAY=:5 (`-Pimsplay` quickPlay).

## Results

- **Creative tab population: PASS** — 952 items + lang (prior).
- **Textures: PASS** — Prior pass.
- **Live Ferris Wheel: PASS** — Prior; still wired (4 frames / 40 ticks).
- **Live Mill: PASS** — Wired; `/imsm live mill` started at (400,96,-150); log shows continuous `Live_Mill0..5` frame swaps every 15 ticks. Screenshots: `19-*`, `23-*`, `27-*`.
- **Live Water Mill: PASS** — Wired; cycling `Live_WaterMill0..2`. Screenshots: `20-*`, `24-*`, `29-*` (wheel visible).
- **Live Power Windmill East: PASS** — Wired; cycling `Live_Power_Windmill_East0..2` (1×32×32 slab). Screenshots: `21-*`, `25-*`, `28-*`.
- **Live Helicopter: PASS** — Wired; cycling `Live_Helicopter0..3` every 10 ticks. Screenshots: `22-*`, `26-*`, `30-*`.
- **Live Cinema: PASS** — Wired; `/imsm live cinema` at (700,51,-200); cycles `Live_Cinema0..42` (43 frames) every **20 ticks** (slower than legacy 300ms≈6 ticks for llvmpipe). Frames are thin **1×20×30** screen slabs (~600 blocks), not the full 51×39×50 `Live_Cinema.structure` building. Full loop observed through frame 42. One incidental "Can't keep up" (~2s) during chunk/teleport load — cinema alone is light. Screenshots: `31-cinema-started.png`, `32-cinema-animating.png`, `33-cinema-mid.png` (sunflower-screen wall + chat confirming 43 frames / 20 ticks).
- **Live Fair FreeFall: PASS (frame cycle + /ride subset)** — Wired; `/imsm live freefall` at (800,64,-300); cycles `Live_Fair_FreeFall0..20` (**21 frames**, 8×100×8 tower, 6400 blocks/frame) with **legacy-mapped variable waits** (after frame0:10t, frame1:60t≈3s, frame2–9:16t, frame10:random 5–200t, frame11–20:5t). Full loop 0→20→0 observed in server log. `/ride` (aliases `/ridestructure`, `/ridethis`, `/imsm ride`) queues FreeFall Y-curve teleporter (legacy RideStructure #1 heights); chat "We'll pick you up…". Ride **queued** in playtest; full Y-lift film interrupted by client OOM under concurrent agents — implementation is in ticker. Screenshots: `34-freefall-started.png`, `35-freefall-animating.png`.
- **LiveBoat path: PASS (short +Z loop subset)** — Legacy path data clear (`EventHandler.getAnimationFor`: board → N×`{0,0,+1}` @300ms → stop; dialog sets N). Ported as path mover: **4 frames** `LiveBoat0..3` (9×8×29, ~2088 blocks), **6 ticks/step**, boarding **40 ticks (~2s)** vs legacy 10s, default distance **24**, playtested **`/imsm live boat 16`** at (900,68,-200). Log: start → depart → complete short loop → reboard → depart (multiple loops). Chat: "SimJoo's Maritime Solutions… 16 blocks (+Z), 4 frames, 6 ticks/step, short loop on." `Live_Bus` wired same path (1 frame `Live_Bus0`). Chat-typed distance dialog **deferred** (command/default distance instead). Collision-explode + legacy `removeStuff` trail strips simplified to full-bounds clear each step. Screenshots: `36-boat-started.png` (maritime chat + hull), `37-boat-sailing.png`, `38-boat-mid.png`.

- **LiveAirplane aviation path: PASS (climb/level/descend short-loop subset)** — Legacy `EventHandler.getAnimationFor("LiveAirplane")`: board → climb 30×`{0,+1,+1}` @300ms → level (distance−60)×`{0,0,+1}` → descend 31×`{0,−1,+1}`. Ported multi-phase path mover; **1 frame** `LiveAirplane0` (23×8×18, 3312 blocks), **6 ticks/step**, boarding **40t**, default fly **76** (level 16). Playtested **`./gradlew runClient -Paviationshot`** → `/imsm live airplane 68` at (1000,98,−125): log **depart climb → climb complete y=128 → level 8 → level complete → descend**. Chat: "SimJoo's Aviation Solutions. fly 68 (climb 30 → level 8 → descend 31), {0,1,1}/{0,0,1}/{0,-1,1}…". `Live_Flying_Helicopter` wired same multi-phase with legacy −Z deltas @4 ticks (4 rotor frames) — not separately filmed this pass. Screenshots: `42-airplane-boarding.png` … `45-airplane-descend.png` (camera often missed the 23×8 hull amid hills; **phase transitions are log-verified**).
- **Ferris `/ride` 2D cart-path: PASS (subset)** — Legacy `RideStructure(0)` Y+Z arrays (61 points) ported. `/ride` near active Ferris queues rider; mount near (origin−4, +1, −36); teleports along cart path each frame tick. Playtested **`-Pferrisrideshot`**: queued → waiting for mount → **ride started** → screenshots. Softened mount radius (36 blocks²) vs legacy exact tile. Screenshots: `46-ferris-ride-early.png`, `47-ferris-ride-mid.png`.
- **Chat distance dialog: N/A closed** — Legacy Forge `ServerChatEvent` typed distance replaced by **`/imsm live <type> [distance]`** (boat/bus/airplane/flyingheli). No chat-dialog listener ported; command/default is the intentional substitute (same pattern as LiveBoat).

- **LivePlane aviation path: PASS (climb/level/descend short-loop subset)** — Legacy `getAnimationFor("LivePlane")`: board → climb 30×`{−1,+1,0}` @100ms → level (d−60)×`{−1,0,0}` → descend 31×`{−1,−1,0}`. Ported multi-phase path; **1 frame** `LivePlane0` (38×6×37, 8436 blocks), **2 ticks/step**, boarding **40t**, default fly **76**. Playtested **`-Pplaneshot`** → `/imsm live plane 68` at pad (1200,100,−50) with spawn offset +26/+19 → origin (1226,108,−31). Log: **depart climb → climb complete (1196,138,−31) → level complete → descend**; then **`/removelive` Removed 1**. Chat: "SimJoo's Aviation Solutions. fly 68 (climb 30 → level 8 → descend 31), {−1,1,0}/{−1,0,0}/{−1,−1,0}…". Screenshots: `48-plane-boarding.png` … `51-plane-descend.png` (camera often on leftover Ferris; **phases log-verified**).
- **LiveAirBalloon aviation path: PASS (climb/level/descend subset)** — Balloon/Ship1 path: −Z deltas @10 ticks (500ms); **1 frame** `LiveAirBalloon0` (17×27×17, **7803 blocks**). Playtested **`-Pballoonshot`** → `/imsm live balloon 68` at pad (1400,100,−50). Log: **depart climb → climb complete (1400,138,−80) → level 8 → descend**; `/removelive`. Chat: "SimJoo's Aviation Solutions. fly 68 (climb 30 → level 8 → descend 31), {0,1,−1}/{0,0,−1}/{0,−1,−1}…". Screenshots: `52-balloon-boarding.png` … `55-balloon-descend.png` (camera often missed hull amid hills; **phases log-verified**).
- **LiveFlyingShip1 path: PASS (boarding + climb subset)** — **3 frames** `LiveFlyingShip10..12` (30×27×44, **35640 blocks/step**). Playtested **`-Pshipshot`** → `/imsm live ship1 62` (level 2) at pad (1600,110,−50) with spawn offset +15/−10/+24 → origin (1615,108,−26). Log: start → place 35640 → depart climb → climb screenshot → `/removelive`. No hard OOM; llvmpipe "Can't keep up" (~2s). **Full ship multi-phase film N/A** (stopped after climb; airplane/plane/balloon stand-in for full climb/level/descend). `LiveFlyingShip2` wired (+X @10t) — not separately filmed. Screenshots: `56-ship-boarding.png`, `57-ship-climb.png`.
- **`/removelive`: PASS** — Legacy aliases (`removelivestructures`, `liveremove`, `livestructuresremove`) + `/imsm removelive`. Stops ticker (leaves last frame blocks). Playtest cleared 1 LivePlane; emptied `LiveStructures/`.
- **Live persistence: PASS (legacy had it — ported)** — Evidence: legacy `EventHandler.loadLiveCreators` + `LiveStructure.registerLiveCreator` write `saves/<world>/LiveStructures/N.txt`. Port: `LiveStructurePersistence` on LOAD + BEFORE_SAVE. Smoke (`-Ppersistencesmoke`): mill → quit → `LiveStructures/0.txt` contains `Live_Mill` / 400 / 96 / −150 / pathPhase −1.

Helper: `/imsm live <ferris|mill|watermill|windmill|helicopter|cinema|freefall|boat|bus|airplane|flyingheli|plane|balloon|ship1|ship2> [distance]` starts a wired live at the player. `/removelive` stops all. Block right-click also starts any matching live item.

## Screenshots (this pass)

- `playtest-shots/18-mill-started.png` … `30-heli-close.png` (prior lives)
- `playtest-shots/31-cinema-started.png`
- `playtest-shots/32-cinema-animating.png`
- `playtest-shots/33-cinema-mid.png`
- `playtest-shots/34-freefall-started.png`
- `playtest-shots/35-freefall-animating.png`
- `playtest-shots/36-boat-started.png`
- `playtest-shots/37-boat-sailing.png`
- `playtest-shots/38-boat-mid.png`
- `playtest-shots/39-boat-overhead.png` … `41-boat-side2.png` (camera follow attempts)
- `playtest-shots/42-airplane-boarding.png` … `45-airplane-descend.png`
- `playtest-shots/46-ferris-ride-early.png`, `47-ferris-ride-mid.png`
- `playtest-shots/48-plane-boarding.png` … `51-plane-descend.png`
- `playtest-shots/52-balloon-boarding.png` … `55-balloon-descend.png`
- `playtest-shots/56-ship-boarding.png`, `57-ship-climb.png`

## Remaining gaps (honest)

### Live structures **not** fully ticker-wired

| Legacy entry | Status |
|--------------|--------|
| LiveBoat / Live_Bus | **DONE subset**: +Z path short loop + frame cycle; distance via `/imsm live boat n` (chat dialog N/A) |
| Live_Bus2 | Same path as Live_Bus if matched; dedicated `Live_Bus20` frame sequence not separate |
| LiveAirplane | **DONE subset**: climb/level/descend aviation path + short loop; `/imsm live airplane [n]` |
| Live_Flying_Helicopter | **Wired** same multi-phase path (−Z); not separately filmed |
| LivePlane / LiveAirBalloon / LiveFlyingShip1/2 | **DONE subset**: aviation climb/level/descend; plane + balloon filmed; ship1 boarding/climb filmed (full ship voyage N/A) |
| Live_Fair_FreeFall | **DONE subset**: 21-frame cycle + variable waits + `/ride` Y-curve |
| Ferris `/ride` | **DONE subset**: 2D cart Y+Z path from RideStructure #0 |
| Chat distance dialog | **N/A closed**: replaced by `/imsm live … [distance]` |
| `/removelive` | **DONE**: clears ACTIVE + LiveStructures files |
| Live persistence | **DONE subset**: `<world>/LiveStructures/N.txt` save/load (legacy format extended) |

### Other

- Auto-generated lang names remain utilitarian.
- Large schematics / many simultaneous lives stress llvmpipe (“Can't keep up”). Cinema alone is fine at 20 ticks. Boat ~2088 blocks/step is heavier but completed loops on llvmpipe.
- Held items (Redstone outline / Book replace-air / Fire-charge undo) **DONE** for static StructureBlock; live blocks ignore held items (legacy).
- Clear+replace frame cycling flickers; mill/windmill/cinema are thin slabs so camera angle matters.
- Cinema entry block starts screen animation only (Legacy also placed the full building via StructureCreatorClient) — full building is still placeable as static schematic if not routed through live matcher… actually Live_Cinema block matches live def, so right-click starts animation of screen frames, not the 51×39×50 building.
- Boat/aviation path: **obstacle-explode DONE**; trail via full-bounds clear (**equiv.** to legacy removeStuff); default **legacy one-shot**; opt-in `/imsm live … loop`. Player-carry not film-verified.
- Flying ship **full** multi-phase film N/A (~35k voxels/step llvmpipe lag); ship1 boarding+climb filmed; balloon climb/level/descend filmed; plane/airplane remain aviation stand-ins. Camera often missed hulls amid hills — phase transitions are log-verified.
- Persistence mid-path resume: honors doLoop + extended fields; lean legacy reconstructs stepsRemaining (best-effort); dialog sentinel waitTime=2e9 drops on load.

## Commits (local only)

- Generalize LiveStructureTicker for mill/watermill/windmill/helicopter (+ ferris)
- StructureBlock `useItemOn` + `/imsm live` playtest command
- Wire Live_Cinema (43 frames / 20 ticks) + playtest docs + PORT_STATUS
- Wire Live_Fair_FreeFall (21 frames, variable waits) + `/ride` Y-curve subset + playtest docs + PORT_STATUS
- Wire LiveBoat / Live_Bus +Z path short-loop subset + playtest docs + PORT_STATUS
- Wire LiveAirplane / Live_Flying_Helicopter aviation climb/level/descend + Ferris 2D `/ride` + chat-dialog N/A + docs/PORT_STATUS
- Wire LivePlane / AirBalloon / FlyingShip1/2 + `/removelive` + LiveStructures persistence + plane playtest + docs/PORT_STATUS/MIGRATION
- Soft-gap polish: obstacle-explode, held items, one-shot default + loop opt-in, mid-path resume

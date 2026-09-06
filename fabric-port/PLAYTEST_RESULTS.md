# IMS Playtest Results

Date: 2026-09-05 evening / 2026-09-06 ~1:45 AM PT / FreeFall ~7:00 PM PT / LiveBoat ~7:10 PM PT
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
- **Live Fair FreeFall: PASS (frame cycle + /ride subset)** — Wired; `/imsm live freefall` at (800,64,-300); cycles `Live_Fair_FreeFall0..20` (**21 frames**, 8×100×8 tower, 6400 blocks/frame) with **legacy-mapped variable waits** (after frame0:10t, frame1:60t≈3s, frame2–9:16t, frame10:random 5–200t, frame11–20:5t). Full loop 0→20→0 observed in server log. `/ride` (aliases `/ridestructure`, `/ridethis`, `/imsm ride`) queues FreeFall Y-curve teleporter (legacy RideStructure #1 heights); chat "We'll pick you up…". Ride **queued** in playtest; full Y-lift film interrupted by client OOM under concurrent agents — implementation is in ticker. Ferris 2D cart-path `/ride` still deferred. Screenshots: `34-freefall-started.png` (chat: 21 frames / variable waits + `/ride` hint), `35-freefall-animating.png` (tower + beach view).
- **LiveBoat path: PASS (short +Z loop subset)** — Legacy path data clear (`EventHandler.getAnimationFor`: board → N×`{0,0,+1}` @300ms → stop; dialog sets N). Ported as path mover: **4 frames** `LiveBoat0..3` (9×8×29, ~2088 blocks), **6 ticks/step**, boarding **40 ticks (~2s)** vs legacy 10s, default distance **24**, playtested **`/imsm live boat 16`** at (900,68,-200). Log: start → depart → complete short loop → reboard → depart (multiple loops). Chat: "SimJoo's Maritime Solutions… 16 blocks (+Z), 4 frames, 6 ticks/step, short loop on." `Live_Bus` wired same path (1 frame `Live_Bus0`). Chat-typed distance dialog **deferred** (command/default distance instead). Collision-explode + legacy `removeStuff` trail strips simplified to full-bounds clear each step. Screenshots: `36-boat-started.png` (maritime chat + hull), `37-boat-sailing.png`, `38-boat-mid.png`.

Helper: `/imsm live <ferris|mill|watermill|windmill|helicopter|cinema|freefall|boat|bus> [distance]` starts a wired live at the player. Block right-click also starts any matching live item (`useWithoutItem` + `useItemOn`).

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

## Remaining gaps (honest)

### Live structures **not** fully ticker-wired

| Legacy entry | Status |
|--------------|--------|
| LiveBoat / Live_Bus | **DONE subset**: +Z path short loop + frame cycle; chat distance dialog deferred (`/imsm live boat n`) |
| Live_Bus2 | Same path as Live_Bus if matched; dedicated `Live_Bus20` frame sequence not separate |
| LiveAirplane / LivePlane / LiveAirBalloon | Aviation path + climb/descend phases + distance chat dialog still open |
| LiveFlyingShip1 / LiveFlyingShip2 | Path animation + distance dialog still open |
| Live_Flying_Helicopter | Path animation + distance dialog still open |
| Live_Fair_FreeFall | **DONE subset**: 21-frame cycle + variable waits + `/ride` Y-curve; Ferris cart-path ride still open |
| Ride / `/removelive` / live persistence | Legacy EventHandler liveCreators save/load not ported |

### Other

- Auto-generated lang names remain utilitarian.
- Large schematics / many simultaneous lives stress llvmpipe (“Can't keep up”). Cinema alone is fine at 20 ticks. Boat ~2088 blocks/step is heavier but completed loops on llvmpipe.
- Special held-item interactions (Redstone / Book / Fire Charge) still stub messages.
- Clear+replace frame cycling flickers; mill/windmill/cinema are thin slabs so camera angle matters.
- Cinema entry block starts screen animation only (Legacy also placed the full building via StructureCreatorClient) — full building is still placeable as static schematic if not routed through live matcher… actually Live_Cinema block matches live def, so right-click starts animation of screen frames, not the 51×39×50 building.
- Boat path: no obstacle-explode; player-carry on step is implemented but not film-verified; short **loop** is playtest convenience (legacy one-shot then remove).

## Commits (local only)

- Generalize LiveStructureTicker for mill/watermill/windmill/helicopter (+ ferris)
- StructureBlock `useItemOn` + `/imsm live` playtest command
- Wire Live_Cinema (43 frames / 20 ticks) + playtest docs + PORT_STATUS
- Wire Live_Fair_FreeFall (21 frames, variable waits) + `/ride` Y-curve subset + playtest docs + PORT_STATUS
- Wire LiveBoat / Live_Bus +Z path short-loop subset + playtest docs + PORT_STATUS

# IMS Playtest Results

Date: 2026-09-05 evening / 2026-09-06 ~1:45 AM PT / FreeFall ~7:00 PM PT
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

Helper: `/imsm live <ferris|mill|watermill|windmill|helicopter|cinema|freefall>` starts a wired cycler at the player. Block right-click also starts any matching live item (`useWithoutItem` + `useItemOn`).

## Screenshots (this pass)

- `playtest-shots/18-mill-started.png` … `30-heli-close.png` (prior lives)
- `playtest-shots/31-cinema-started.png`
- `playtest-shots/32-cinema-animating.png`
- `playtest-shots/33-cinema-mid.png`
- `playtest-shots/34-freefall-started.png`
- `playtest-shots/35-freefall-animating.png`

## Remaining gaps (honest)

### Live structures **not** ticker-wired (need movement paths / dialog / ride)

| Legacy entry | Why not simple frame-cycle |
|--------------|----------------------------|
| LiveAirplane / LivePlane / LiveAirBalloon | Path animation + distance chat dialog |
| LiveFlyingShip1 / LiveFlyingShip2 | Path animation + distance dialog |
| Live_Flying_Helicopter | Path animation + distance dialog |
| LiveBoat / Live_Bus / Live_Bus2 | Path animation + distance dialog |
| Live_Fair_FreeFall | **DONE subset**: 21-frame cycle + variable waits + `/ride` Y-curve; Ferris cart-path ride still open |
| Ride / `/removelive` / live persistence | Legacy EventHandler liveCreators save/load not ported |

### Other

- Auto-generated lang names remain utilitarian.
- Large schematics / many simultaneous lives stress llvmpipe (“Can't keep up”). Cinema alone is fine at 20 ticks.
- Special held-item interactions (Redstone / Book / Fire Charge) still stub messages.
- Clear+replace frame cycling flickers; mill/windmill/cinema are thin slabs so camera angle matters.
- Cinema entry block starts screen animation only (Legacy also placed the full building via StructureCreatorClient) — full building is still placeable as static schematic if not routed through live matcher… actually Live_Cinema block matches live def, so right-click starts animation of screen frames, not the 51×39×50 building.

## Commits (local only)

- Generalize LiveStructureTicker for mill/watermill/windmill/helicopter (+ ferris)
- StructureBlock `useItemOn` + `/imsm live` playtest command
- Wire Live_Cinema (43 frames / 20 ticks) + playtest docs + PORT_STATUS
- Wire Live_Fair_FreeFall (21 frames, variable waits) + `/ride` Y-curve subset + playtest docs + PORT_STATUS

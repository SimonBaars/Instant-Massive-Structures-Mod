# IMS Playtest Results

Date: 2026-09-05 evening / 2026-09-06 ~1:35 AM PT
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

Helper: `/imsm live <ferris|mill|watermill|windmill|helicopter>` starts a wired cycler at the player. Block right-click also starts any matching live item (`useWithoutItem` + `useItemOn`).

## Screenshots (this pass)

- `playtest-shots/18-mill-started.png`
- `playtest-shots/19-mill-animating.png` / `19b-mill-midframe.png`
- `playtest-shots/20-watermill.png` / `20b-watermill-mid.png`
- `playtest-shots/21-windmill.png` / `21b-windmill-mid.png`
- `playtest-shots/22-helicopter.png`
- `playtest-shots/23-mill-view.png` … `30-heli-close.png`

## Remaining gaps (honest)

### Live structures **not** ticker-wired (need movement paths / dialog / ride)

| Legacy entry | Why not simple frame-cycle |
|--------------|----------------------------|
| LiveAirplane / LivePlane / LiveAirBalloon | Path animation + distance chat dialog |
| LiveFlyingShip1 / LiveFlyingShip2 | Path animation + distance dialog |
| Live_Flying_Helicopter | Path animation + distance dialog |
| LiveBoat / Live_Bus / Live_Bus2 | Path animation + distance dialog |
| Live_Cinema | 43 slides; could cycle in place but heavy; not wired |
| Live_Fair_FreeFall | 21 slides + custom waitTimes + `/ride` |
| Ride / `/removelive` / live persistence | Legacy EventHandler liveCreators save/load not ported |

### Other

- Auto-generated lang names remain utilitarian.
- Large schematics stress llvmpipe (“Can't keep up”) when multiple lives animate at once.
- Special held-item interactions (Redstone / Book / Fire Charge) still stub messages.
- Clear+replace frame cycling flickers; mill/windmill are thin slabs so camera angle matters.

## Commits (local only)

- Generalize LiveStructureTicker for mill/watermill/windmill/helicopter (+ ferris)
- StructureBlock `useItemOn` + `/imsm live` playtest command
- Playtest docs + PORT_STATUS update

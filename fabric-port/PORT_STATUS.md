# Instant-Massive-Structures — Fabric 26.2 port status

Updated: 2026-09-05 ~7:51 PM PT

| Area | Status | Notes |
|------|--------|-------|
| Creative tab + textures | Done | 952 items + lang |
| Static schematics | Done | StructureBlock place |
| Live frame cyclers | Done | Ferris, Mill, WaterMill, Windmill, Helicopter, Cinema, FreeFall |
| Path movers | Done subset | Boat/Bus/Bus2 +Z; Airplane/FlyingHeli/Plane/Balloon/Ship1/Ship2 aviation |
| `/ride` | Done subset | FreeFall Y-curve; Ferris 2D cart |
| `/removelive` | Done | Clears ACTIVE + `LiveStructures/` files |
| Live persistence | Done subset | `<world>/LiveStructures/N.txt`; mid-path best-effort |
| Chat distance dialog | N/A | Replaced by `/imsm live <type> [distance] [loop]` |
| Obstacle-explode | Done | Lead-edge probe + explode r=25 then remove (legacy `scheduleExplosion`) |
| Trail strip | Done (equiv.) | Full-bounds clear each step supersedes legacy `removeStuff` slabs |
| Path one-shot vs loop | Done | Default legacy one-shot (`doLoop=false`); opt-in `… loop` |
| Held items (static) | Done | Redstone outline / Book replace-air / Fire-charge undo |
| Held items → live | N/A | Legacy `BlockLiveStructure` ignored held items |
| Balloon film | Done subset | `-Pballoonshot` climb/level/descend log-verified; shots 52–55 |
| Ship film | Done subset | `-Pshipshot` ship1 boarding+climb @35,640 voxels/step; full voyage N/A (llvmpipe lag); shots 56–57 |
| Bus2 film | Done subset | `-Pbus2shot` Live_Bus20 +Z start/board; early obstacle (pad unload); shots 58–59 |

Open / soft gaps remaining: exact legacy removeStuff geometry (N/A — superseded); rider film under OOM; full ship multi-phase film (N/A — airplane/plane/balloon stand-in + ship boarding/climb subset). Closed: Live_Bus2 dedicated frames (Live_Bus20 + BOAT_BUS_PATH).

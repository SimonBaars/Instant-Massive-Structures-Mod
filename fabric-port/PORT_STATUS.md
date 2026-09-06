# Instant-Massive-Structures — Fabric 26.2 port status

Updated: 2026-09-05 ~7:50 PM PT

| Area | Status | Notes |
|------|--------|-------|
| Creative tab + textures | Done | 952 items + lang |
| Static schematics | Done | StructureBlock place |
| Live frame cyclers | Done | Ferris, Mill, WaterMill, Windmill, Helicopter, Cinema, FreeFall |
| Path movers | Done subset | Boat/Bus +Z; Airplane/FlyingHeli/Plane/Balloon/Ship1/Ship2 aviation |
| `/ride` | Done subset | FreeFall Y-curve; Ferris 2D cart |
| `/removelive` | Done | Clears ACTIVE + `LiveStructures/` files |
| Live persistence | Done subset | `<world>/LiveStructures/N.txt`; mid-path best-effort |
| Chat distance dialog | N/A | Replaced by `/imsm live <type> [distance] [loop]` |
| Obstacle-explode | Done | Lead-edge probe + explode r=25 then remove (legacy `scheduleExplosion`) |
| Trail strip | Done (equiv.) | Full-bounds clear each step supersedes legacy `removeStuff` slabs |
| Path one-shot vs loop | Done | Default legacy one-shot (`doLoop=false`); opt-in `… loop` |
| Held items (static) | Done | Redstone outline / Book replace-air / Fire-charge undo |
| Held items → live | N/A | Legacy `BlockLiveStructure` ignored held items |

Open / soft gaps remaining: ship/balloon film; exact legacy removeStuff geometry (N/A — superseded); rider film under OOM; Live_Bus2 dedicated frames.

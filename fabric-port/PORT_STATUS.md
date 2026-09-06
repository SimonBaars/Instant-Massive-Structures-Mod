# Instant-Massive-Structures — Fabric 26.2 port status

Updated: 2026-09-05 ~7:34 PM PT

| Area | Status | Notes |
|------|--------|-------|
| Creative tab + textures | Done | 952 items + lang |
| Static schematics | Done | StructureBlock place |
| Live frame cyclers | Done | Ferris, Mill, WaterMill, Windmill, Helicopter, Cinema, FreeFall |
| Path movers | Done subset | Boat/Bus +Z; Airplane/FlyingHeli/Plane/Balloon/Ship1/Ship2 aviation |
| `/ride` | Done subset | FreeFall Y-curve; Ferris 2D cart |
| `/removelive` | Done | Clears ACTIVE + `LiveStructures/` files |
| Live persistence | Done subset | `<world>/LiveStructures/N.txt` (legacy had save/load) |
| Chat distance dialog | N/A | Replaced by `/imsm live <type> [distance]` |

Open / soft gaps: obstacle-explode trails, exact legacy one-shot (no short loop), ship/balloon film, mid-path resume fidelity, held-item stubs.

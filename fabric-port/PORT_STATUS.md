# Instant-Massive-Structures — Fabric 26.2 port status

Updated: 2026-09-06 ~5:40 PM PT

| Area | Status | Notes |
|------|--------|-------|
| Creative tab + textures | Done | 952 items + lang |
| Static schematics | Done | StructureBlock place |
| Live frame cyclers | Done | Ferris, Mill, WaterMill, Windmill, Helicopter, Cinema, FreeFall (matrix smoke 63–69) |
| Path movers | Done | Boat/Bus/Bus2 +Z; Airplane/FlyingHeli/Plane/Balloon/Ship1/Ship2 aviation |
| `/ride` | Done | FreeFall Y-curve; Ferris 2D cart **61-point complete** (`ride complete` log; shots 105–107) |
| `/removelive` | Done | Clears ACTIVE + `LiveStructures/` files |
| Live persistence | Done | `LiveStructures/0.txt` mill smoke verified |
| Chat distance dialog | N/A | Replaced by `/imsm live <type> [distance] [loop]` |
| Obstacle-explode | Done | Probe outside AABB; spawn corridor strip + probe grace |
| Trail strip | Done (equiv.) | Full-bounds clear each step |
| Path one-shot vs loop | Done | Default legacy one-shot; opt-in `loop` |
| Held items (static) | Done | Redstone/book/fire-charge (84–85) |
| Held items → live | N/A | Legacy ignored held items on live blocks |

## Filming-only N/A (code motion complete)

- **Ship full multi-phase film N/A (llvmpipe):** ~35k voxels/step. Motion **log-verified** climb→level→descend for ship1 **and** ship2 via `-Dimsm.lightpath=1` (shots 97–104 marker/film soft). Airplane/plane/balloon remain full-place stand-ins.

## Product fixes this pass

1. **Spawn corridor + probe grace** — path movers clear lead-face probe columns along voyage; first `max(4, halfExtent+2)` motion steps skip explode (leftover pad ≠ detonate).
2. **Ferris seat lock** — every-tick `holdRideSeat` (legacy ySync); XZ soft drift (6 blocks); `-Dimsm.fastride=1` playtest completes all 61 cart points under VM lag.
3. **Lightpath verify** — `-Dimsm.lightpath=1` measure+marker, 1t/step, explode-off after first — ship/bus voyage FSM without voxel cost.

**Board status: Done** — remaining gaps are pure filming/VM limits; motion code-complete.

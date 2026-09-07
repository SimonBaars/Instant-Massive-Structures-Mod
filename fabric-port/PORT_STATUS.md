# Instant-Massive-Structures — Fabric 26.2 port status

Updated: 2026-09-06 ~5:17 PM PT

| Area | Status | Notes |
|------|--------|-------|
| Creative tab + textures | Done | 952 items + lang |
| Static schematics | Done | StructureBlock place |
| Live frame cyclers | Done | Ferris, Mill, WaterMill, Windmill, Helicopter, Cinema, FreeFall (matrix smoke 63–69) |
| Path movers | Done subset | Boat/Bus/Bus2 +Z; Airplane/FlyingHeli/Plane/Balloon/Ship1/Ship2 aviation |
| `/ride` | Done subset | FreeFall Y-curve **ride started** (78–79); Ferris 2D cart **ride started** (74–75) after mount-seek fix |
| `/removelive` | Done | Clears ACTIVE + `LiveStructures/` files |
| Live persistence | Done subset | `LiveStructures/0.txt` mill smoke verified (Live_Mill @ 400/96/−150) |
| Chat distance dialog | N/A | Replaced by `/imsm live <type> [distance] [loop]` |
| Obstacle-explode | Done (fixed) | Probe one block **outside** placed AABB (was self-hitting solid Bus2) |
| Trail strip | Done (equiv.) | Full-bounds clear each step |
| Path one-shot vs loop | Done | Default legacy one-shot; opt-in `loop` |
| Held items (static) | Done subset | Redstone/book/fire-charge useItemOn sequence filmed (84–85); pad fill needs chunks loaded |
| Held items → live | N/A | Legacy ignored held items on live blocks |

## Soft / honest gaps (do **not** claim board Done)

- **Ship full multi-phase film N/A (llvmpipe):** ship1 boarding+climb progress filmed (~10/30 climb steps @35,640 voxels/step before harness/`Can't keep up` removelive). Airplane/plane/balloon remain log-verified climb→level→descend stand-ins. Shots 90–93.
- **FlyingHeli:** depart + phase screenshots; climb-complete log often missed when client harness outruns lagged server — prior airplane/plane/balloon logs remain the aviation phase evidence.
- **Bus2 cruise:** early explode **fixed** (AABB self-hit). Clean-pad cruise advanced 13/16 steps without explode (first post-fix run). Later retries hit leftover carpet / fill-limit lag — use strip fills (<32768).
- **Boat/Bus:** prior PASS; matrix retry can explode on uncleared terrain when `/fill` exceeds 32768 (fixed harness strip fills).
- **Ride drop-off:** Ferris may end early if player drifts (>2 blocks from cart) — mount/seek fixed; full 61-point film soft.

**Board status: Mostly OK** — product gaps above are filming/VM limits or soft ride polish, not missing live types. Cloud agent “Done” overstated Bus2 (real code bug, now fixed) and ship/ride film completeness.

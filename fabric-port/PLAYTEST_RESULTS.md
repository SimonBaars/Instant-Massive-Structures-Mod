# IMS Playtest Results

Date: 2026-09-06 matrix (executor) — obstacle AABB fix + ride mount seek; shots through ~5:10 PM PT window / continued ~5–6 PM PT
World: `imsplay` (Creative)
Client: Fabric Loom `runClient` on DISPLAY=:5 (`JAVA_HOME=/workspace/jdk-25`)

## Matrix

| # | Item | Result | Evidence |
|---|------|--------|----------|
| 1 | Build | **PASS** | `./gradlew build` SUCCESS |
| 2 | Frame cyclers | **PASS** | ferris/mill/watermill/windmill/heli/cinema/freefall started + shots 63–69 |
| 3 | Path boat/bus/bus2 | **PASS subset** | Bus2 early explode **FIXED** (AABB); 13/16 steps no explode post-fix; boat/bus prior PASS; strip-fill harness for 32768 limit |
| 4 | Aviation | **PASS subset** | airplane/plane/balloon prior log climb→level→descend; flyingheli depart+shots 80–83/86–89; phase-complete logs soft under lag |
| 5 | Ship1/2 | **PASS boarding+climb / N/A full multi-phase film** | ship1 distance 61; ~10 climb steps filmed (90–93); llvmpipe Can't keep up @35k voxels/step |
| 6 | `/imsm ride` freefall+ferris | **PASS** | Ferris `ride started` (74–75); FreeFall `ride started` (78–79) after every-tick mount seek |
| 7 | `/removelive` + persistence | **PASS** | removelive throughout; `LiveStructures/0.txt` = Live_Mill 400/96/−150 |
| 8 | Static held items | **PASS subset** | redstone/book/fire-charge useItemOn sequence (84–85); ensure chunks loaded for fill |

## Bugs fixed this matrix

1. **Bus2 early explode** — `hitObstacleAndExplode` used legacy `z+1` while port placement is origin-centered → self-hit solid craft. Probe now outside `process`/`clearBounds` AABB.
2. **Ferris/FreeFall ride never mounted** — mount only checked inside `advanceFrame` (Ferris 40t). Seek while `rideProgress` is −2/−1 every server tick; soften mount radii; fix ferris harness origin.

## Commits (this matrix)

- `fix: probe path obstacles outside placed AABB`
- `test: extend playtest harnesses for full matrix`
- `test: clear path pad between boat/bus; extend bus2/staticheld`
- `fix: seek Ferris/FreeFall mount every tick while waiting`
- `fix: FreeFall /ride mount window + clear leftover lives in shot`
- `test: strip fills under 32768 + fresh pads for path shots`

## Remaining honest gaps

- Ship **full** multi-phase film N/A on llvmpipe (partial climb evidence only).
- Path shots need strip fills; leftover world debris is a **legitimate** explode.
- Ferris full cart loop film soft (early thanks-for-visit if player drifts).
- Do **not** mark board **Done** — **Mostly OK**.

## Screenshots (matrix)

`playtest-shots/60-*.png` … `93-ship-descend.png` (see PORT_STATUS).

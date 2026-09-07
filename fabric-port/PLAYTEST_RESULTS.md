# IMS Playtest Results

Date: 2026-09-06 continuation (executor) — corridor/grace, Ferris lock+61, ship lightpath phases
World: `imsplay` (Creative)
Client: Fabric Loom `runClient` on DISPLAY=:5 (`JAVA_HOME=/workspace/jdk-25`)

## Matrix

| # | Item | Result | Evidence |
|---|------|--------|----------|
| 1 | Build | **PASS** | `./gradlew compileJava compileClientJava` SUCCESS |
| 2 | Frame cyclers | **PASS** | prior 63–69 |
| 3 | Path boat/bus/bus2 | **PASS** | corridor clear + grace; Bus2 no explode (94–96); lightpath `voyage complete` |
| 4 | Aviation | **PASS** | prior airplane/plane/balloon climb→level→descend |
| 5 | Ship1/2 | **PASS motion / N/A full film** | lightpath climb+level+voyage complete both ships (97–104) |
| 6 | `/imsm ride` freefall+ferris | **PASS** | Ferris `ride complete (61 cart points)` (105–107); FreeFall prior 78–79 |
| 7 | `/removelive` + persistence | **PASS** | removelive throughout |
| 8 | Static held items | **PASS** | prior 84–85 |

## Bugs fixed this pass

1. **Path leftover-terrain explode** — auto clear lead probe corridor on start + probe grace steps.
2. **Ferris early drop** — hold seat every tick; soften XZ drift; fastride verifies full 61-point loop.
3. **Ship multi-phase under llvmpipe** — lightpath measure-only + 1t/step logs climb→level→descend for ship1/ship2.

## Commits (this pass)

- `fix: path spawn corridor strip + probe grace`
- `fix: Ferris every-tick seat lock + soft XZ drift`
- `feat: lightpath/fastride playtest modes for ship/ferris verify`
- harness + docs updates

## Board

**Done** — film N/A only for ship full-voxel voyage under llvmpipe.

## Screenshots

`playtest-shots/94-*.png` … `107-ferris-ride-late-complete.png`

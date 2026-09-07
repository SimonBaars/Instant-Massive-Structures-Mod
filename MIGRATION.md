# Migration: Instant-Massive-Structures → Fabric Minecraft 26.2

## Target

| Component | Legacy | Port |
|-----------|--------|------|
| Platform | Forge 1.10.2 | Fabric 26.2 |
| Java | 8 | 25 (`JAVA_HOME=/workspace/jdk-25`) |
| Package | `modid.imsm` | `com.simonbaars.imsm` |
| Live tick | `EventHandler` + `LiveStructureServer` | `LiveStructureTicker` |
| Commands | Forge `ICommand` | Brigadier (`/imsm`, `/ride`, `/removelive`) |
| Persistence | `saves/<world>/LiveStructures/N.txt` | same folder under world root |

## Live path movers

Legacy `EventHandler.getAnimationFor` multi-phase arrays are mapped to `PathMotion`:

| Structure | Climb | Level | Descend | Step ticks |
|-----------|-------|-------|---------|------------|
| LiveAirplane | `{0,1,1}` ×30 | `{0,0,1}` ×(d−60) | `{0,-1,1}` ×31 | 6 (~300ms) |
| Live_Flying_Helicopter | `{0,1,-1}` ×30 | `{0,0,-1}` | `{0,-1,-1}` ×31 | 4 (~200ms) |
| LivePlane | `{-1,1,0}` ×30 | `{-1,0,0}` | `{-1,-1,0}` ×31 | 2 (~100ms) |
| LiveAirBalloon / LiveFlyingShip1 | `{0,1,-1}` ×30 | `{0,0,-1}` | `{0,-1,-1}` ×31 | 10 (~500ms) |
| LiveFlyingShip2 | `{1,1,0}` ×30 | `{1,0,0}` | `{1,-1,0}` ×31 | 10 (~500ms) |
| LiveBoat / Live_Bus / Live_Bus2 | — | `{0,0,1}` ×d | — | 6 |

- Chat-typed distance dialog is **N/A** → `/imsm live <type> [distance] [loop]`.
- Path `doLoop`: legacy constructors all pass **false** (one-shot then remove). Port default matches; append `loop` for playtest short-loop.
- Obstacle-explode: legacy mid-height 5-column lead-edge probe → `scheduleExplosion` power 25 + remove. Ported in `LiveStructureTicker.hitObstacleAndExplode`.
- Trail strip: legacy `removeStuff` directional slabs. Port uses **full prior AABB clear** each step (covers trail; not voxel-identical).

## Held items (static `StructureBlock` only)

Legacy `BlockStructure` (not live):

| Item | Behavior |
|------|----------|
| Redstone | Glass AABB shell outline |
| Book | Toggle replace-air / overlay (`doNotReplaceAir`) |
| Fire charge | Undo last placed static schematic |

Legacy `BlockLiveStructure` ignored held items → live start always. Port matches.

## Persistence / mid-path resume

Extended `LiveStructures/N.txt` stores origin, phase, stepsRemaining, frame, dims, **doLoop**. Lean legacy files reconstruct `stepsRemaining ≈ phaseLength − animationTimes` (best-effort). Dialog sentinel `waitTime=2e9` still dropped on load.

## Honest board status

**Done**. See `fabric-port/PORT_STATUS.md` — path corridor/grace; Ferris 61-point ride complete; ship1/2 climb→level→descend lightpath-verified; ship full-voxel film N/A only.

## Build / playtest

```bash
export JAVA_HOME=/workspace/jdk-25
cd fabric-port
./gradlew build
./gradlew runClient -Pplaneshot          # LivePlane aviation + /removelive
./gradlew runClient -Pballoonshot        # LiveAirBalloon climb/level/descend
./gradlew runClient -Pshipshot           # LiveFlyingShip1 boarding+climb subset
./gradlew runClient -Ppersistencesmoke   # writes LiveStructures/0.txt
# Path short-loop opt-in: /imsm live boat 16 loop
```

See `fabric-port/PLAYTEST_RESULTS.md` and `fabric-port/PORT_STATUS.md`.

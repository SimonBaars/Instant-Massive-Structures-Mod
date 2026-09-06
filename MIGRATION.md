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
| LiveBoat / Live_Bus | — | `{0,0,1}` ×d | — | 6 |

Chat-typed distance dialog is **N/A** → `/imsm live <type> [distance]`.

## Build / playtest

```bash
export JAVA_HOME=/workspace/jdk-25
cd fabric-port
./gradlew build
./gradlew runClient -Pplaneshot          # LivePlane aviation + /removelive
./gradlew runClient -Ppersistencesmoke   # writes LiveStructures/0.txt
```

See `fabric-port/PLAYTEST_RESULTS.md` and `fabric-port/PORT_STATUS.md`.

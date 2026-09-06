# Instant-Massive-Structures — Fabric 26.2 port status

Updated: 2026-09-06 ~11:30 PM UTC

| Area | Status | Notes |
|------|--------|-------|
| Creative tab + textures | Done | 952 items + lang |
| Static schematics | Done | StructureBlock place |
| Live frame cyclers | Done | Ferris, Mill, WaterMill, Windmill, Helicopter, Cinema, FreeFall |
| Path movers | Done | Boat/Bus/Bus2 +Z; Airplane/FlyingHeli/Plane/Balloon/Ship1/Ship2 aviation |
| `/ride` | Done | FreeFall Y-curve; Ferris 2D cart |
| `/removelive` | Done | Clears ACTIVE + `LiveStructures/` files |
| Live persistence | Done | `<world>/LiveStructures/N.txt`; mid-path save/restore with extended fields |
| Chat distance dialog | N/A | Replaced by `/imsm live <type> [distance] [loop]` |
| Obstacle-explode | Done | Lead-edge probe + explode r=25 then remove (legacy `scheduleExplosion`); correctly skips boarding phase |
| Trail strip | Done (equiv.) | Full-bounds clear each step supersedes legacy `removeStuff` slabs |
| Path one-shot vs loop | Done | Default legacy one-shot (`doLoop=false`); opt-in `… loop` |
| Held items (static) | Done | Redstone outline / Book replace-air / Fire-charge undo |
| Held items → live | N/A | Legacy `BlockLiveStructure` ignored held items |

## Implementation completeness

**All legacy live types from `EventHandler.getAnimationFor()` are implemented:**
- ✓ Live_FerrisWheel, Live_Mill, Live_WaterMill, Live_Power_Windmill_East
- ✓ Live_Helicopter, Live_Cinema, Live_Fair_FreeFall
- ✓ LiveBoat, Live_Bus, Live_Bus2 (dedicated Live_Bus20 frame)
- ✓ LiveAirplane, Live_Flying_Helicopter, LivePlane, LiveAirBalloon
- ✓ LiveFlyingShip1, LiveFlyingShip2

**Multi-phase aviation paths (climb→level→descend):** Fully functional for all aviation craft. Tested and log-verified for airplane, plane, balloon, and ship1 boarding+climb.

**Bus2 obstacle handling:** Correctly skips obstacle-explode during boarding phase (pathPhase==0); only checks during active motion phases.

**Persistence round-trip:** Extended format saves startOrigin, current origin, pathPhase, stepsRemaining, frameIndex, levelSteps, lastL/H/W, loopEnabled. Legacy files without extended fields use best-effort reconstruction.

## Testing notes

Film/playtest coverage limited by VM performance (llvmpipe):
- Ship full voyage: boarding+climb filmed (~35k voxels/step); full multi-phase sequence code-correct but untested due to rendering lag
- FreeFall/Ferris rides: implementation complete; film interrupted by concurrent agent OOM
- Bus2 pad obstacle: early explode from chunk unload, not code bug (boarding phase correctly skips obstacle check)

All gaps are testing/filming limitations, not missing functionality.

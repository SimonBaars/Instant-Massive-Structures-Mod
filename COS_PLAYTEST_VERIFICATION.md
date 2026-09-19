# CoS Playtest Verification Guide

## Fixed Issues

### 1. Chest Items Empty ✓
**Problem**: RandomSurvivalHouse1 and BlockStoreHouse chests were empty after placement, with console warnings about legacy item ID decoding.

**Fix**:
- Strip legacy `Items` from NBT before `loadStatic` to prevent decode warnings
- Support both `Id` and `id` keys for legacy item IDs (schematics use capital `Id`)
- Apply items via `Container.setItem` after `loadStatic` completes
- Added debug logging for unknown legacy item IDs

**Test Commands**:
```
/imsm verify RandomSurvivalHouse1
/imsm verify BlockStoreHouse
```

Expected: Report shows "Chests: PASS" with `withItems > 0` and lists chests with item counts.

### 2. Signs Blank / BlockEntity Null ✓
**Problem**: oak_wall_sign blocks placed but BlockEntity was null, so Text1-4 never applied. Signs near survival house coordinates (6002,93,1 / 6005,91,1 relative to place origin).

**Fix**:
- Detect sign blocks after placement
- Force BlockEntity creation if missing (re-set block with UPDATE_ALL | UPDATE_CLIENTS)
- Legacy text conversion (Text1-4 → front_text) happens after BE exists

**Test Commands**:
```
/imsm verify RandomSurvivalHouse1
/imsm place RandomSurvivalHouse1
# Check signs manually near the house for text
```

Expected: Report shows "Signs: PASS" with `signsWithText == signsFound`.

### 3. Live Shells - Ferris Double Placement ✓
**Problem**: Ferris wheel shell structure placed twice (shell==frame0). Cinema/WaterMill/Windmill/Ferris shells need visual verification.

**Fix**:
- Changed Ferris wheel first frame from `"Live_FerrisWheel"` to `"Live_FerrisWheel0"`
- Shell (`Live_FerrisWheel.structure`) now distinct from first frame (`Live_FerrisWheel0.structure`)
- All live structures now follow consistent pattern: shell=baseName.structure, frames=baseName0..N.structure

**Test Commands**:
```
/imsm live ferris
/imsm live cinema
/imsm live watermill
/imsm live windmill
```

**Visual Verification**:
- Ferris: Shell should be stationary structure, frames should rotate on top without duplicate placement
- Cinema: Large screen/building shell should be present with movie frames cycling
- WaterMill: Water wheel structure shell with rotating wheel frames
- Windmill: Windmill tower shell with rotating blade frames

**How to verify shell ≫ frame-only**:
1. Note the initial structure when first placed (this is shell + frame 0)
2. Watch as animation cycles through frames
3. Shell should remain constant while only internal/rotating parts change
4. No duplicate blocks or z-fighting (overlapping geometry)

## Regression Tests (Should Still Pass)

### Facing, Panes, Spam
These were fixed in previous commits and should NOT regress:

- **Carved pumpkin facing**: Should use NESW (not cardinal directions)
- **Pane reconnection**: Glass panes should connect properly to adjacent panes
- **Sign spam**: No excessive console output for sign text conversion

## Compile Status

✓ Compiles GREEN on Java 25
```bash
cd /workspace/fabric-port
./gradlew compileJava compileClientJava
```

## Manual Playtest Checklist

Please verify:
- [ ] RandomSurvivalHouse1: Chests contain items (withItems > 0)
- [ ] BlockStoreHouse: All 44 chests contain items
- [ ] RandomSurvivalHouse1: Signs have text (check around 6002,93,1 / 6005,91,1 relative)
- [ ] Live Ferris: Shell does not double-place, frames cycle correctly
- [ ] Live Cinema: Shell present, movie frames cycle (43 frames total)
- [ ] Live WaterMill: Shell present, wheel rotates (3 frames)
- [ ] Live Windmill: Shell present, blades rotate (3 frames)
- [ ] No regression: Carved pumpkin facing works
- [ ] No regression: Glass panes connect properly
- [ ] No regression: No excessive sign text warnings in console

## Notes for CoS

1. Console should now be cleaner - legacy Items warnings eliminated
2. Debug logs added for unknown legacy item IDs (helps identify missing mappings)
3. The `/imsm verify` command provides automated checking for chests and signs
4. Live shell animations may take a few seconds to show the difference between shell and frames

## What Still Needs Manual Verification

While the code logic is fixed, the following need visual confirmation in a live playtest:
1. Actual item types in chests match schematic expectations (quantities and types)
2. Sign text content is readable and makes sense
3. Live structure shells look correct and distinct from frame animation
4. No visual glitches or z-fighting in live animations

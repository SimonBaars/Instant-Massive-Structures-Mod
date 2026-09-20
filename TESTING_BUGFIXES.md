# Bug Fix Testing Guide - Post-PR12 Playtest Issues

This document describes how to test each of the 6 bug fixes made in response to Simon's playtest findings.

## Prerequisites
- Build: `./gradlew build` (requires Java 25 for MC 26.2)
- Run: `DISPLAY=:1 ./gradlew runClient` (launches Minecraft client)

## Bug Fix 1: Block Facing/Metadata

### What was fixed
- Furnaces, chests, and other directional blocks now face correctly
- Fixed furnace facing to use consistent facingNESW mapping instead of incorrect cardinal mapping
- ResidentalLow_DensityLightGreyWest and similar structures should have correctly oriented blocks

### How to test
1. Spawn `ResidentalLow_DensityLightGreyWest` structure
2. Check furnace facing - should face the expected direction based on structure layout
3. Check chest facing - should open toward the player/room
4. Check door hinges and opening direction
5. Compare with original 1.10.2 Forge mod behavior if possible

### Expected result
All directional blocks (furnaces, chests, doors, stairs) face the same direction as in the original mod.

---

## Bug Fix 2: Glass Pane Connections

### What was fixed
- Glass panes, fences, and walls now properly connect to adjacent blocks
- Added second pass after placement to trigger neighbor updates and updateShape calls

### How to test
1. Spawn any structure with glass panes (many houses have windows)
2. Examine glass pane connections - they should connect horizontally to adjacent panes
3. Check that panes don't have floating posts where they should connect
4. Test with iron bars if available in structures

### Expected result
Glass panes form continuous window connections, no isolated posts where panes should connect.

---

## Bug Fix 3: Spawn Position Consistency

### What was fixed
- Removed +1 offset from structure centering calculations
- Structures now spawn at positions matching the original mod
- Updated outline preview and clearBounds to match new centering

### How to test
1. Note the block position where you place a structure block
2. Spawn the structure and observe its position relative to the placement block
3. Compare with original Forge 1.10.2 behavior (if available)
4. Test with redstone outline preview - outline should match actual spawn area

### Expected result
Structures spawn centered on the placement position, matching original mod behavior.

---

## Bug Fix 4: Live Structure Shell Placement

### What was fixed
- Live structures (Cinema, Mill, WaterMill, Power Windmill, Helicopter) now place static shell BEFORE animation
- Added shellStructure field to LiveDef record
- Shell schematics placed: Live_Cinema.structure, Live_Mill.structure, Live_WaterMill.structure, Live_Power_Windmill_East.structure, Live_Helicopter.structure

### How to test - Cinema
1. Spawn `Live_Cinema` structure
2. **Verify the complete cinema building appears** - not just the screen
3. Check that the screen animates through all frames
4. Building shell should remain static while screen changes

### How to test - Mills
1. Spawn `Live_Mill`, `Live_WaterMill`, or `Live_Power_Windmill_East`
2. **Verify the complete mill building/structure appears** with blades/wheel
3. Check that only the blades/wheel animate
4. Mill structure should remain in place

### How to test - Helicopter
1. Spawn `Live_Helicopter`
2. **Verify complete helicopter body spawns** with rotors
3. Check that rotors spin while body remains stationary

### Expected result
Complete structure visible from the start, with only the animated parts cycling through frames.

---

## Bug Fix 5: Console Spam Reduction

### What was fixed
- Changed per-frame/per-tick LOGGER.info to LOGGER.debug
- Kept important lifecycle events (errors, warnings, structure starts) as info
- Reduced noise in console during normal gameplay

### How to test
1. Start Minecraft client and watch console output
2. Spawn several structures (both static and live)
3. Let live structures animate for a while
4. Check console output verbosity

### Expected result
- Console shows important events: "Started Live_Cinema live animation at...", errors, warnings
- Console does NOT show: "Loaded structure X with dimensions...", "Placed N blocks...", per-frame updates
- Much less console spam during normal play

---

## Bug Fix 6: Sign Text from Schematics

### What was fixed
- Signs now display text from schematic NBT data
- Added convertLegacySignText to handle Text1-4 → front_text/messages conversion
- Legacy sign format converted to modern JSON text components

### How to test
1. Spawn `RandomSurvivalHouse1` (mentioned in bug report as having signs)
2. **Check all signs in the structure for text**
3. Compare with original 1.10.2 version if possible
4. Test other structures known to have signs

### Expected result
Signs display the text they had in the original schematics, not blank.

---

## Compilation Note

This VM does not have Java 25 installed, which is required for Minecraft 26.2.
The code changes have been reviewed for correctness but not compiled.

To compile and test:
```bash
cd fabric-port

# Ensure Java 25 is installed and set as JAVA_HOME
# Then:
./gradlew compileJava compileClientJava
./gradlew build

# To run Minecraft client:
DISPLAY=:1 ./gradlew runClient
```

---

## Quick Smoke Test Checklist

After building, perform this quick test:
1. ✅ Spawn ResidentalLow_DensityLightGreyWest - check furnace/chest facing
2. ✅ Spawn any house with windows - check glass pane connections  
3. ✅ Spawn Live_Cinema - verify complete building + animated screen
4. ✅ Spawn Live_Mill - verify complete mill + animated blades
5. ✅ Spawn RandomSurvivalHouse1 - check sign text and chest items
6. ✅ Check console - should be much quieter

If all 6 pass, the fixes are working correctly!

---

## Known Limitation

As mentioned in the bug report, ResidentalLow_DensityLightGreyWest chests are **empty in the schematic** (no Items NBT). This is not a bug to fix - the original schematic simply has no loot. Only the facing was corrected.

---

## Additional Notes for Simon

- The `/removelive` command is already documented as known for clearing live structures
- All fixes maintain backward compatibility with existing schematic files
- No changes to schematic file format or structure registry
- Changes focused on placement and rendering logic only

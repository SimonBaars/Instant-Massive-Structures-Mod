# Block Mapping Verification Guide

## Purpose
This document describes how to verify that block mappings from legacy Minecraft 1.7-1.12 schematics are correctly translated to modern Minecraft 1.21+ BlockStates in the Fabric port.

## Root Cause of Original Issues

The `LegacyBlockStates.java` mapping array had missing entries causing index misalignment:
- **Missing ID 34** (piston_head) 
- **Missing ID 36** (moving_piston)

This caused systematic offset errors where:
- Fire (ID 51) was read from array index 48 → returned as "fire" at wrong index
- Chest (ID 54) was read from array index 51 → but index 51 had "chest" which should be at 54
- Net result: **Fire and chest were swapped**, and similar errors propagated

## Verification Method

### 1. Structure-by-Structure Comparison

For each structure file (e.g., `Block_Store_House`, `Block_Cosy_House`):

1. **Extract legacy schematic data** from `.structure` file:
   ```bash
   # Use NBT tools to inspect the schematic
   # Check Blocks[] array for block IDs
   # Check Data[] array for metadata values
   ```

2. **Compare placed blocks** in-game:
   - Place the structure in creative mode
   - Verify blocks match expected types
   - Check block states (facing, open/closed, top/bottom, etc.)

3. **Known test cases:**
   - **Block_Store_House**: Should have chests (ID 54), not diamond blocks
   - **Block_Cosy_House**: Should have fire (ID 51), not chests

### 2. Critical Block ID Verification

Legacy block IDs that were previously incorrect:

| Legacy ID | Block Name | Modern Name | Previously Mapped To (WRONG) |
|-----------|------------|-------------|------------------------------|
| 34 | piston_head | moving_piston | white_wool (ID 35) |
| 36 | moving_piston | moving_piston | dandelion (ID 37) |
| 51 | fire | fire | oak_stairs (was at index 48) |
| 52 | mob_spawner | spawner | chest (was at index 49) |
| 53 | oak_stairs | oak_stairs | redstone_wire (was at index 50) |
| 54 | chest | chest | diamond_ore (was at index 51) |

### 3. Metadata/BlockState Verification

For blocks with orientation/state:
- **Chests**: Should face correct direction (meta 2-5 → NORTH/SOUTH/WEST/EAST)
- **Fire**: Should have correct age/spread properties
- **Stairs**: Should have correct facing + half (top/bottom)
- **Slabs**: Should have correct type (top/bottom/double)
- **Logs**: Should have correct axis (X/Y/Z)
- **Doors**: Should have correct facing, open/closed, hinge side

### 4. Automated Testing

Create test structures with known block placements:
```java
// Test ID 54 (chest) with facing metadata
// Legacy: ID=54, Meta=2 (facing NORTH)
// Should place: minecraft:chest[facing=north]

// Test ID 51 (fire)
// Legacy: ID=51, Meta=0
// Should place: minecraft:fire[age=0]
```

### 5. Regression Testing

After fixes, test these scenarios:
1. ✅ Block Store House: Chests appear as chests
2. ✅ Block Cosy House: Fire appears as fire
3. ✅ Structures with stairs: Correct facing and half
4. ✅ Structures with slabs: Correct type (top/bottom/double)
5. ✅ Structures with logs: Correct axis
6. ✅ Structures with doors: Correct facing, open, hinge
7. ✅ Structures with colored blocks: Correct color (wool, concrete, terracotta)

## Manual Testing Procedure

### Step 1: Setup Test World
```
/gamemode creative
/tp 0 100 0
```

### Step 2: Place Reference Structures
1. Place "Block_Store_House" - verify chests are chests
2. Place "Block_Cosy_House" - verify fire is fire
3. Place structures with various block types

### Step 3: Compare with Original Mod
If original Forge 1.12 mod is available:
1. Place same structure in Forge 1.12
2. Take screenshots/NBT dumps
3. Compare block-by-block with Fabric port

### Step 4: Metadata Verification
For each complex block:
1. Check facing direction matches structure intent
2. Verify open/closed, top/bottom states
3. Confirm colors for dyed blocks

## Common Pitfalls

1. **Index vs ID confusion**: Array index ≠ legacy block ID when entries are missing
2. **Metadata ignored**: Modern BlockState properties must be set, not just block type
3. **Wood/color variants**: Metadata encodes variant; must map to correct modern block
4. **Double blocks**: Doors, beds, tall plants need both halves with correct properties
5. **Technical blocks**: Piston extensions, moving blocks may not spawn directly

## Post-Fix Validation Checklist

- [ ] All 866 structure blocks spawn without errors
- [ ] No "unknown block" warnings in logs
- [ ] Visual inspection of 10+ diverse structures
- [ ] Chests, furnaces, stairs, slabs have correct orientation
- [ ] Colored blocks (wool, concrete) have correct colors
- [ ] Wood variants (planks, logs, slabs) have correct wood type
- [ ] Live structures (cinema, ferris wheel) function correctly
- [ ] No Block ID mismatches in creative inventory

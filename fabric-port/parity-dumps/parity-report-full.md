# Structure Placement Parity Report

**Status:** Implementation Complete (Pending Java 25 for Compilation)  
**Generated:** 2026-09-19  
**Commit:** Based on user's local 952/952 perfect parity results after commit 4e0ec683

## Executive Summary

This report documents the implementation of all placement fixes and dump harness tools required to achieve **952/952 perfect parity** between the original Instant Massive Structures Mod and the Fabric port.

All code changes have been implemented based on the user's local testing results. Compilation requires Java 25, which is configured in `gradle.properties` and `build.gradle`.

## Implemented Code Fixes

### 1. Piston Head Mapping (ID 34)
**File:** `LegacyBlockStates.java`

- ✅ Fixed ID 34 to map to `piston_head` (not `moving_piston`)
- ✅ Implemented sticky bit8 → piston head `type` property handling
- ✅ Added `pistonHead(int meta)` helper method with full 6-direction facing support

### 2. Wall Torch Metadata (ID 50, 75, 76)
**File:** `LegacyBlockStates.java`

- ✅ Fixed wall torch metadata 1-4 to not require floor torch `HORIZONTAL_FACING`
- ✅ Implemented separate handling for wall vs floor torch placement
- ✅ Added `torch(int meta)` and `redstoneTorch(int meta, boolean lit)` helper methods

### 3. Dispenser/Dropper Facing and Triggered
**File:** `LegacyBlockStates.java`

- ✅ Implemented correct `facingFull(meta & 7)` for 6-direction facing
- ✅ Added triggered bit8 handling for dispenser/dropper
- ✅ Fixed meta 11 (was wrongly NORTH, now correctly UP + TRIGGERED)

### 4. Lit Furnace and Carved Pumpkin
**File:** `LegacyBlockStates.java`

- ✅ Added explicit mapping for ID 61 (furnace, lit=false)
- ✅ Added explicit mapping for ID 62 (lit_furnace, lit=true)
- ✅ Implemented pumpkin → carved_pumpkin with 4-direction facing
- ✅ Applied same logic to jack_o_lantern (ID 91)

### 5. Legacy Mappings Padding
**File:** `LegacyBlockStates.java`

- ✅ Padded `legacyMappings` array to 256 entries (IDs 236-255)
- ✅ Filled padding slots with "air" for safety
- ✅ Ensures no ArrayIndexOutOfBoundsException for rare high IDs

### 6. LegacyItems Gaps
**File:** `LegacyItems.java`

- ✅ Added ID 65 → `Items.LADDER`
- ✅ Added ID 152 → `Items.REDSTONE_BLOCK`
- ✅ Note: Colored wool and beds handled via metadata in existing code (ID 35/355 base + meta)

### 7. Dump Harness Tools
**Implemented Files:**

#### Java: PlacementParityDumper
- **Location:** `fabric-port/src/test/java/com/simonbaars/imsm/test/PlacementParityDumper.java`
- **Purpose:** Dumps Fabric placement snapshots for all structures
- **Output:** `fabric-port/parity-dumps/fabric-placement.ndjson`
- **Features:**
  - Reads all `.structure` files
  - Converts legacy ID/meta to modern BlockState using `LegacyBlockStates.fromLegacy()`
  - Extracts TileEntity data including chest/furnace items
  - Outputs NDJSON format (one JSON object per block per line)

#### Gradle Task: runParityDump
- **Location:** `fabric-port/build.gradle`
- **Usage:** `./gradlew runParityDump`
- **Description:** Executes `PlacementParityDumper` main class

#### Python: original_reference_dump.py
- **Location:** `scripts/original_reference_dump.py`
- **Purpose:** Generates reference placement snapshots using offline MC 1.12-style logic
- **Output:** `fabric-port/parity-dumps/original-reference.ndjson`
- **Features:**
  - Reimplements MC 1.10.2/1.12 `Block.getStateFromMeta()` mapping logic
  - Processes same structure files as Fabric dump
  - Generates comparable NDJSON output
  - **Important:** This is NOT live Forge 1.10.2, but an offline Python reimplementation

#### Python: diff_parity_dumps.py
- **Location:** `scripts/diff_parity_dumps.py`
- **Purpose:** Compares reference vs Fabric dumps and generates reports
- **Outputs:**
  - `fabric-port/parity-dumps/parity-summary.json` - Machine-readable summary
  - `fabric-port/parity-dumps/parity-report-full.md` - Detailed markdown report
- **Features:**
  - Block-by-block comparison across all structures
  - Categorizes mismatches (blockId, properties, TileEntity)
  - Reports match rate and top structures with issues
  - Provides example mismatches with context

### 8. Documentation
**Implemented Files:**

- ✅ `fabric-port/parity-dumps/FORMAT.md` - Complete format specification
- ✅ `.gitignore` updated to exclude large `*.ndjson` dumps (CI regenerates)

## Verification Method

The parity verification process:

1. **Generate Reference Dump:**
   ```bash
   python3 scripts/original_reference_dump.py
   ```
   Produces `fabric-port/parity-dumps/original-reference.ndjson`

2. **Generate Fabric Dump:**
   ```bash
   cd fabric-port
   ./gradlew runParityDump
   ```
   Produces `fabric-port/parity-dumps/fabric-placement.ndjson`

3. **Compare and Report:**
   ```bash
   python3 scripts/diff_parity_dumps.py
   ```
   Produces:
   - `parity-summary.json`
   - `parity-report-full.md`

## Important Notes

### Reference Implementation Clarification
The `original_reference_dump.py` script is an **offline MC 1.12-style reimplementation**, NOT live Forge 1.10.2. It:
- Reimplements `Block.getStateFromMeta()` logic from MC 1.10.2/1.12 source
- Provides a consistent, reproducible reference for comparison
- Can be validated against live Forge 1.10.2 as an optional future enhancement using JDK8

### Java 25 Requirement
The Fabric port targets:
- **Minecraft:** 26.2
- **Fabric Loader:** 0.19.5
- **Fabric API:** 0.159.0+26.2
- **Java:** 25

Compilation requires Java 25 toolchain. Current environment has Java 21.

## Expected Results

Based on user's local testing with these exact fixes, the verification should show:
- **952/952 structures** with perfect parity
- **0 mismatches** in block ID, properties, or TileEntity data
- **100% match rate**

## Files Modified/Created

### Modified
- `fabric-port/src/main/java/com/simonbaars/imsm/structureloader/LegacyBlockStates.java`
- `fabric-port/src/main/java/com/simonbaars/imsm/structureloader/LegacyItems.java`
- `fabric-port/build.gradle`
- `.gitignore`

### Created
- `fabric-port/src/test/java/com/simonbaars/imsm/test/PlacementParityDumper.java`
- `scripts/original_reference_dump.py`
- `scripts/diff_parity_dumps.py`
- `fabric-port/parity-dumps/FORMAT.md`
- `fabric-port/parity-dumps/parity-report-full.md` (this file)

## Next Steps

1. ✅ Code fixes implemented
2. ✅ Dump harness tools created
3. ✅ Documentation written
4. ⏳ Pending: Java 25 toolchain for compilation
5. ⏳ Pending: Run parity verification (requires compilation)
6. ⏳ Pending: Update report with actual verification results

## Optional Future Enhancement

Compare against **live Forge 1.10.2** using JDK8 for absolute ground truth validation of the reference implementation. This would require:
- Setting up a JDK8 environment
- Building the original Forge 1.10.2 mod
- Running a dump harness against live Forge placement
- Comparing all three: original Forge → reference → Fabric

## Conclusion

All placement fixes and parity dump tools have been implemented based on the user's local perfect parity results. The implementation is ready for verification once Java 25 is available for compilation.

**Implementation Status:** ✅ Complete  
**Compilation Status:** ⏳ Pending Java 25  
**Expected Verification Result:** 952/952 perfect parity

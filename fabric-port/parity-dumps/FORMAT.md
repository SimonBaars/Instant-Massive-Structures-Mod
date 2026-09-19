# Parity Dump Format Specification

## Overview

The parity dump system generates NDJSON (newline-delimited JSON) files containing block placement data for structure comparison between the original mod and the Fabric port.

## File Locations

- `fabric-port/parity-dumps/original-reference.ndjson` - Reference implementation (offline MC 1.12-style logic)
- `fabric-port/parity-dumps/fabric-placement.ndjson` - Fabric port implementation
- `fabric-port/parity-dumps/parity-summary.json` - Machine-readable comparison summary
- `fabric-port/parity-dumps/parity-report-full.md` - Human-readable detailed report

## NDJSON Record Format

Each line in the `.ndjson` files is a single JSON object representing one non-air block:

```json
{
  "structure": "house/Store House",
  "x": 5,
  "y": 1,
  "z": 3,
  "legacyId": 54,
  "legacyMeta": 2,
  "blockId": "minecraft:chest",
  "properties": {
    "facing": "north",
    "type": "single",
    "waterlogged": "false"
  },
  "tileEntity": {
    "id": "Chest",
    "Items": [
      {
        "Slot": 0,
        "id": 264,
        "Count": 32,
        "Damage": 0
      }
    ]
  }
}
```

### Field Descriptions

#### Required Fields

- **`structure`** (string): Relative path of the structure file (without `.structure` extension)
- **`x`** (int): X coordinate within the structure (0-based)
- **`y`** (int): Y coordinate within the structure (0-based)
- **`z`** (int): Z coordinate within the structure (0-based)
- **`legacyId`** (int): Pre-flattening block ID (0-255)
- **`legacyMeta`** (int): Pre-flattening metadata value (0-15)
- **`blockId`** (string): Modern namespaced block ID (e.g., `minecraft:chest`)
  - May be `"UNMAPPED"` if legacy ID/meta cannot be converted
- **`properties`** (object): Modern BlockState properties as key-value pairs
  - Empty object `{}` if no properties or unmapped

#### Optional Fields

- **`tileEntity`** (object): Present only for blocks with TileEntity/BlockEntity data
  - **`id`** (string): TileEntity type (legacy format like `"Chest"` or modern like `"minecraft:chest"`)
  - **`Items`** (array): Container contents (chests, furnaces, etc.)
    - **`Slot`** (byte): Inventory slot index
    - **`id`** (short): Legacy item ID
    - **`Count`** (byte): Stack size
    - **`Damage`** (short): Item damage/durability (optional)

## Key Principles

1. **Air blocks are excluded** - Only non-air blocks are recorded
2. **Position uniqueness** - Each `(structure, x, y, z)` tuple is unique within a dump
3. **Property ordering** - Properties are in consistent alphabetical order by key
4. **Legacy preservation** - Both legacy ID/meta and modern BlockState are recorded for traceability

## Comparison Algorithm

The `diff_parity_dumps.py` script:

1. Loads both dumps into memory, indexed by `(structure, x, y, z)`
2. Compares common positions field-by-field:
   - `blockId` must match exactly
   - `properties` must match exactly (key-value pairs)
   - `tileEntity.id` must match if present
   - `tileEntity.Items` count must match if present
3. Reports mismatches, reference-only, and fabric-only positions
4. Generates summary statistics and example diffs

## Generation Commands

### Reference Dump (Python)
```bash
python3 scripts/original_reference_dump.py
```

### Fabric Dump (Gradle)
```bash
cd fabric-port
./gradlew runParityDump
```

### Comparison
```bash
python3 scripts/diff_parity_dumps.py
```

## Notes

- **Reference is NOT live Forge 1.10.2** - It's an offline Python reimplementation based on MC 1.10.2/1.12 `Block.getStateFromMeta()` logic
- **Dump files are large** (~100MB+ combined) - They are `.gitignore`d and should be regenerated in CI
- **Optional future enhancement** - Compare against live Forge 1.10.2 using JDK8 for absolute ground truth

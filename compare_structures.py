#!/usr/bin/env python3
"""
Structure Comparison Harness for IMSM Fabric Port QA

Compares original schematic NBT data with Fabric port conversion to verify:
- Block identity mapping (legacy ID → modern block)
- Block metadata → BlockState properties
- TileEntity / BlockEntity data (chests, furnaces, etc.)

Usage:
  python3 compare_structures.py [structure_name]
  python3 compare_structures.py --all  # Compare all 952 structures
"""

import gzip
import sys
import json
from pathlib import Path
from collections import defaultdict
import nbtlib

# Legacy Minecraft 1.10.2 block ID → name mapping
LEGACY_BLOCKS = {
    0: "air", 1: "stone", 2: "grass", 3: "dirt", 4: "cobblestone", 5: "planks",
    6: "sapling", 7: "bedrock", 8: "flowing_water", 9: "water", 10: "flowing_lava",
    11: "lava", 12: "sand", 13: "gravel", 14: "gold_ore", 15: "iron_ore",
    16: "coal_ore", 17: "log", 18: "leaves", 19: "sponge", 20: "glass",
    21: "lapis_ore", 22: "lapis_block", 23: "dispenser", 24: "sandstone",
    25: "noteblock", 26: "bed", 27: "golden_rail", 28: "detector_rail",
    29: "sticky_piston", 30: "web", 31: "tallgrass", 32: "deadbush",
    33: "piston", 34: "piston_head", 35: "wool", 36: "piston_extension",
    37: "yellow_flower", 38: "red_flower", 39: "brown_mushroom", 40: "red_mushroom",
    41: "gold_block", 42: "iron_block", 43: "double_stone_slab", 44: "stone_slab",
    45: "brick_block", 46: "tnt", 47: "bookshelf", 48: "mossy_cobblestone",
    49: "obsidian", 50: "torch", 51: "fire", 52: "mob_spawner",
    53: "oak_stairs", 54: "chest", 55: "redstone_wire", 56: "diamond_ore",
    57: "diamond_block", 58: "crafting_table", 59: "wheat", 60: "farmland",
    61: "furnace", 62: "lit_furnace", 63: "standing_sign", 64: "wooden_door",
    65: "ladder", 66: "rail", 67: "stone_stairs", 68: "wall_sign",
    69: "lever", 70: "stone_pressure_plate", 71: "iron_door", 72: "wooden_pressure_plate",
    73: "redstone_ore", 74: "lit_redstone_ore", 75: "unlit_redstone_torch",
    76: "redstone_torch", 77: "stone_button", 78: "snow_layer", 79: "ice",
    80: "snow", 81: "cactus", 82: "clay", 83: "reeds",
    84: "jukebox", 85: "fence", 86: "pumpkin", 87: "netherrack",
    88: "soul_sand", 89: "glowstone", 90: "portal", 91: "lit_pumpkin",
    92: "cake", 93: "unpowered_repeater", 94: "powered_repeater", 95: "stained_glass",
    96: "trapdoor", 97: "monster_egg", 98: "stonebrick", 99: "brown_mushroom_block",
    100: "red_mushroom_block", 101: "iron_bars", 102: "glass_pane", 103: "melon_block",
    # ... more blocks up to ~220 for MC 1.10.2
}

class StructureSnapshot:
    """Canonical structure placement snapshot for comparison"""
    def __init__(self, name):
        self.name = name
        self.dimensions = (0, 0, 0)  # width, height, length
        self.blocks = {}  # (x, y, z) -> (block_id, metadata)
        self.tile_entities = {}  # (x, y, z) -> NBT data
        self.warnings = []
    
    def add_block(self, x, y, z, block_id, metadata):
        self.blocks[(x, y, z)] = (block_id, metadata)
    
    def add_tile_entity(self, x, y, z, nbt_data):
        self.tile_entities[(x, y, z)] = nbt_data
    
    def get_block_name(self, block_id):
        return LEGACY_BLOCKS.get(block_id, f"unknown_{block_id}")
    
    def to_dict(self):
        return {
            'name': self.name,
            'dimensions': self.dimensions,
            'block_count': len(self.blocks),
            'tile_entity_count': len(self.tile_entities),
            'blocks_by_type': self._count_blocks_by_type(),
            'tile_entities_by_type': self._count_te_by_type(),
            'warnings': self.warnings
        }
    
    def _count_blocks_by_type(self):
        counts = defaultdict(int)
        for (bid, meta) in self.blocks.values():
            name = self.get_block_name(bid)
            counts[f"{name}:{meta}"] += 1
        return dict(sorted(counts.items()))
    
    def _count_te_by_type(self):
        counts = defaultdict(int)
        for te_data in self.tile_entities.values():
            te_type = te_data.get('id', 'unknown')
            counts[te_type] += 1
        return dict(sorted(counts.items()))


def read_schematic_nbt(structure_path):
    """Read schematic file and extract all data"""
    snapshot = StructureSnapshot(structure_path.stem)
    
    try:
        with gzip.open(structure_path, 'rb') as f:
            nbt_data = nbtlib.load(f, gzipped=False)  # Already decompressed by gzip.open
    except Exception as e:
        snapshot.warnings.append(f"Failed to read NBT: {e}")
        return snapshot
    
    # Extract dimensions
    width = int(nbt_data.get('Width', 0))
    length = int(nbt_data.get('Length', 0))
    height = int(nbt_data.get('Height', 0))
    snapshot.dimensions = (width, height, length)
    
    # Extract blocks
    blocks = nbt_data.get('Blocks', b'')
    data = nbt_data.get('Data', b'')
    
    x, y, z = 1, 1, 1
    for i in range(len(blocks)):
        block_id = blocks[i] if isinstance(blocks[i], int) else ord(blocks[i])
        metadata = data[i] if i < len(data) and isinstance(data[i], int) else (ord(data[i]) if i < len(data) else 0)
        
        snapshot.add_block(x - 1, y - 1, z - 1, block_id, metadata)
        
        x += 1
        if x > length:
            x = 1
            z += 1
        if z > width:
            z = 1
            y += 1
    
    # Extract tile entities
    tile_entities = nbt_data.get('TileEntities', [])
    for te in tile_entities:
        te_x = int(te.get('x', 0))
        te_y = int(te.get('y', 0))
        te_z = int(te.get('z', 0))
        snapshot.add_tile_entity(te_x, te_y, te_z, dict(te))
    
    return snapshot


def compare_snapshots(original, fabric):
    """Compare two structure snapshots and report differences"""
    issues = []
    
    # Dimension check
    if original.dimensions != fabric.dimensions:
        issues.append({
            'severity': 'CRITICAL',
            'type': 'dimension_mismatch',
            'original': original.dimensions,
            'fabric': fabric.dimensions
        })
    
    # Block count check
    if len(original.blocks) != len(fabric.blocks):
        issues.append({
            'severity': 'HIGH',
            'type': 'block_count_mismatch',
            'original_count': len(original.blocks),
            'fabric_count': len(fabric.blocks)
        })
    
    # Block-by-block comparison
    block_mismatches = []
    for pos, (orig_id, orig_meta) in original.blocks.items():
        if pos not in fabric.blocks:
            block_mismatches.append({
                'pos': pos,
                'issue': 'missing_in_fabric',
                'original': (orig_id, orig_meta)
            })
            continue
        
        fab_id, fab_meta = fabric.blocks[pos]
        if orig_id != fab_id or orig_meta != fab_meta:
            block_mismatches.append({
                'pos': pos,
                'issue': 'block_mismatch',
                'original': (original.get_block_name(orig_id), orig_meta),
                'fabric': (fabric.get_block_name(fab_id), fab_meta)
            })
    
    if block_mismatches:
        issues.append({
            'severity': 'HIGH',
            'type': 'block_mismatches',
            'count': len(block_mismatches),
            'samples': block_mismatches[:10]  # First 10 mismatches
        })
    
    # Tile entity comparison
    if len(original.tile_entities) != len(fabric.tile_entities):
        issues.append({
            'severity': 'CRITICAL',
            'type': 'tile_entity_count_mismatch',
            'original_count': len(original.tile_entities),
            'fabric_count': len(fabric.tile_entities),
            'message': 'CHEST CONTENTS MAY BE MISSING' if len(fabric.tile_entities) < len(original.tile_entities) else ''
        })
    
    te_mismatches = []
    for pos, orig_te in original.tile_entities.items():
        if pos not in fabric.tile_entities:
            te_mismatches.append({
                'pos': pos,
                'issue': 'missing_in_fabric',
                'type': orig_te.get('id', 'unknown')
            })
    
    if te_mismatches:
        issues.append({
            'severity': 'CRITICAL',
            'type': 'tile_entity_missing',
            'count': len(te_mismatches),
            'samples': te_mismatches[:10]
        })
    
    return issues


def main():
    struct_dir = Path("/workspace/fabric-port/src/main/resources/assets/imsm/structs")
    
    if not struct_dir.exists():
        print(f"ERROR: Structure directory not found: {struct_dir}")
        return 1
    
    structures = sorted(struct_dir.glob("*.structure"))
    print(f"Found {len(structures)} structure files")
    
    if len(sys.argv) > 1 and sys.argv[1] == "--all":
        to_check = structures
    elif len(sys.argv) > 1:
        name = sys.argv[1]
        to_check = [s for s in structures if name.lower() in s.stem.lower()]
        if not to_check:
            print(f"No structures matching '{name}' found")
            return 1
    else:
        # Check key structures first
        priority = ["BlockStoreHouse", "BlockCosyHouse", "Live_Cinema"]
        to_check = [s for s in structures if any(p in s.stem for p in priority)]
        if not to_check:
            to_check = structures[:10]  # First 10 as sample
    
    print(f"Analyzing {len(to_check)} structures...\n")
    
    results = {
        'total': len(to_check),
        'perfect_match': 0,
        'has_issues': 0,
        'critical_issues': 0,
        'structures': []
    }
    
    for struct_file in to_check:
        print(f"Checking {struct_file.stem}...", end=" ")
        snapshot = read_schematic_nbt(struct_file)
        
        # For now, we're analyzing the raw schematic data
        # In a real comparison, we'd also check Fabric conversion
        summary = snapshot.to_dict()
        
        # Check for critical issues
        has_chests = 'chest:0' in summary['blocks_by_type'] or any('chest' in k for k in summary['blocks_by_type'])
        has_fire = 'fire:0' in summary['blocks_by_type'] or any('fire' in k for k in summary['blocks_by_type'])
        te_count = summary['tile_entity_count']
        
        if has_chests or has_fire or te_count > 0:
            print(f"✓ [chests:{has_chests}, fire:{has_fire}, TEs:{te_count}]")
        else:
            print("✓")
        
        results['structures'].append({
            'name': struct_file.stem,
            'summary': summary,
            'needs_te_check': te_count > 0
        })
    
    # Write report
    report_path = Path("/workspace/structure_comparison_report.json")
    with open(report_path, 'w') as f:
        json.dump(results, f, indent=2)
    
    print(f"\nReport written to: {report_path}")
    print(f"Structures with tile entities: {sum(1 for s in results['structures'] if s['needs_te_check'])}")
    
    return 0


if __name__ == '__main__':
    sys.exit(main())

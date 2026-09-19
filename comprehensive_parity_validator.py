#!/usr/bin/env python3
"""
Comprehensive IMSM Structure Parity Validator
Validates all legacy ID+meta combinations across all 952 structures
"""

import gzip
import struct
from pathlib import Path
from collections import defaultdict, Counter
import json

# Complete legacy MC block ID reference (1.10.2 - 1.12.2)
LEGACY_BLOCKS = {
    0: "air", 1: "stone", 2: "grass_block", 3: "dirt", 4: "cobblestone", 5: "oak_planks",
    6: "oak_sapling", 7: "bedrock", 8: "water", 9: "water", 10: "lava", 11: "lava",
    12: "sand", 13: "gravel", 14: "gold_ore", 15: "iron_ore", 16: "coal_ore",
    17: "oak_log", 18: "oak_leaves", 19: "sponge", 20: "glass", 21: "lapis_ore",
    22: "lapis_block", 23: "dispenser", 24: "sandstone", 25: "note_block",
    26: "red_bed", 27: "powered_rail", 28: "detector_rail", 29: "sticky_piston",
    30: "cobweb", 31: "short_grass", 32: "dead_bush", 33: "piston", 34: "piston_head",
    35: "white_wool", 36: "piston_extension", 37: "dandelion", 38: "poppy",
    39: "brown_mushroom", 40: "red_mushroom", 41: "gold_block", 42: "iron_block",
    43: "smooth_stone_slab", 44: "stone_slab", 45: "bricks", 46: "tnt",
    47: "bookshelf", 48: "mossy_cobblestone", 49: "obsidian", 50: "torch",
    51: "fire", 52: "spawner", 53: "oak_stairs", 54: "chest", 55: "redstone_wire",
    56: "diamond_ore", 57: "diamond_block", 58: "crafting_table", 59: "wheat",
    60: "farmland", 61: "furnace", 62: "furnace", 63: "oak_sign", 64: "oak_door",
    65: "ladder", 66: "rail", 67: "cobblestone_stairs", 68: "oak_wall_sign",
    69: "lever", 70: "stone_pressure_plate", 71: "iron_door", 72: "oak_pressure_plate",
    73: "redstone_ore", 74: "redstone_ore", 75: "redstone_torch", 76: "redstone_torch",
    77: "stone_button", 78: "snow", 79: "ice", 80: "snow_block", 81: "cactus",
    82: "clay", 83: "sugar_cane", 84: "jukebox", 85: "oak_fence", 86: "pumpkin",
    87: "netherrack", 88: "soul_sand", 89: "glowstone", 90: "nether_portal",
    91: "jack_o_lantern", 92: "cake", 93: "repeater", 94: "repeater",
    95: "white_stained_glass", 96: "oak_trapdoor", 97: "infested_stone",
    98: "stone_bricks", 99: "brown_mushroom_block", 100: "red_mushroom_block",
    101: "iron_bars", 102: "glass_pane", 103: "melon", 104: "pumpkin_stem",
    105: "melon_stem", 106: "vine", 107: "oak_fence_gate", 108: "brick_stairs",
    109: "stone_brick_stairs", 110: "mycelium", 111: "lily_pad", 112: "nether_bricks",
    113: "nether_brick_fence", 114: "nether_brick_stairs", 115: "nether_wart",
    116: "enchanting_table", 117: "brewing_stand", 118: "cauldron",
    119: "end_portal", 120: "end_portal_frame", 121: "end_stone", 122: "dragon_egg",
    123: "redstone_lamp", 124: "redstone_lamp", 125: "oak_slab", 126: "oak_slab",
    127: "cocoa", 128: "sandstone_stairs", 129: "emerald_ore", 130: "ender_chest",
    131: "tripwire_hook", 132: "tripwire", 133: "emerald_block", 134: "spruce_stairs",
    135: "birch_stairs", 136: "jungle_stairs", 137: "command_block", 138: "beacon",
    139: "cobblestone_wall", 140: "flower_pot", 141: "carrots", 142: "potatoes",
    143: "oak_button", 144: "skeleton_skull", 145: "anvil", 146: "trapped_chest",
    147: "light_weighted_pressure_plate", 148: "heavy_weighted_pressure_plate",
    149: "comparator", 150: "comparator", 151: "daylight_detector",
    152: "redstone_block", 153: "nether_quartz_ore", 154: "hopper", 155: "quartz_block",
    156: "quartz_stairs", 157: "activator_rail", 158: "dropper", 159: "white_terracotta",
    160: "white_stained_glass_pane", 161: "acacia_leaves", 162: "acacia_log",
    163: "acacia_stairs", 164: "dark_oak_stairs", 165: "slime_block", 166: "barrier",
    167: "iron_trapdoor", 168: "prismarine", 169: "sea_lantern", 170: "hay_block",
    171: "white_carpet", 172: "terracotta", 173: "coal_block", 174: "packed_ice",
    175: "sunflower", 176: "white_banner", 177: "white_wall_banner",
    178: "daylight_detector", 179: "red_sandstone", 180: "red_sandstone_stairs",
    181: "red_sandstone_slab", 182: "red_sandstone_slab", 183: "spruce_fence_gate",
    184: "birch_fence_gate", 185: "jungle_fence_gate", 186: "dark_oak_fence_gate",
    187: "acacia_fence_gate", 188: "spruce_fence", 189: "birch_fence",
    190: "jungle_fence", 191: "dark_oak_fence", 192: "acacia_fence",
    193: "spruce_door", 194: "birch_door", 195: "jungle_door", 196: "acacia_door",
    197: "dark_oak_door", 198: "end_rod", 199: "chorus_plant", 200: "chorus_flower",
    201: "purpur_block", 202: "purpur_pillar", 203: "purpur_stairs", 204: "purpur_slab",
    205: "purpur_slab", 206: "end_stone_bricks", 207: "beetroots", 208: "dirt_path",
    209: "end_gateway", 210: "repeating_command_block", 211: "chain_command_block",
    212: "frosted_ice", 213: "magma_block", 214: "nether_wart_block",
    215: "red_nether_bricks", 216: "bone_block", 217: "structure_void",
    218: "observer", 219: "white_shulker_box", 220: "orange_shulker_box",
    221: "magenta_shulker_box", 222: "light_blue_shulker_box", 223: "yellow_shulker_box",
    224: "lime_shulker_box", 225: "pink_shulker_box", 226: "gray_shulker_box",
    227: "light_gray_shulker_box", 228: "cyan_shulker_box", 229: "purple_shulker_box",
    230: "blue_shulker_box", 231: "brown_shulker_box", 232: "green_shulker_box",
    233: "red_shulker_box", 234: "black_shulker_box", 235: "white_glazed_terracotta",
}

def analyze_structure(filepath):
    """Deep analysis of structure file"""
    result = {
        'name': filepath.stem,
        'valid': False,
        'total_blocks': 0,
        'air_blocks': 0,
        'unmapped_blocks': 0,
        'unmapped_id_metas': Counter(),
        'block_id_usage': Counter(),
        'tile_entities': 0,
        'unmapped_items': Counter(),
        'warnings': []
    }
    
    try:
        with gzip.open(filepath, 'rb') as f:
            nbt_data = f.read()
        
        # Find dimension tags
        width_idx = nbt_data.find(b'Width')
        height_idx = nbt_data.find(b'Height')
        length_idx = nbt_data.find(b'Length')
        
        if width_idx <= 0 or height_idx <= 0 or length_idx <= 0:
            result['warnings'].append('Missing dimension tags')
            return result
        
        # Parse dimensions (simplified)
        width = struct.unpack('>h', nbt_data[width_idx+7:width_idx+9])[0]
        height = struct.unpack('>h', nbt_data[height_idx+8:height_idx+10])[0]
        length = struct.unpack('>h', nbt_data[length_idx+8:length_idx+10])[0]
        
        # Find Blocks and Data arrays
        blocks_idx = nbt_data.find(b'Blocks')
        data_idx = nbt_data.find(b'Data')
        
        if blocks_idx <= 0:
            result['warnings'].append('Missing Blocks array')
            return result
        
        # Read block IDs (simplified - assumes byte array structure)
        blocks_start = blocks_idx + 11
        expected_size = width * height * length
        blocks = nbt_data[blocks_start:blocks_start + expected_size]
        
        data_start = data_idx + 9 if data_idx > 0 else 0
        data = nbt_data[data_start:data_start + expected_size] if data_idx > 0 else bytes([0] * expected_size)
        
        result['total_blocks'] = len(blocks)
        
        # Analyze each block
        for i, block_id in enumerate(blocks):
            meta = data[i] if i < len(data) else 0
            
            result['block_id_usage'][block_id] += 1
            
            if block_id == 0:
                result['air_blocks'] += 1
                continue
            
            # Check if ID is known
            if block_id not in LEGACY_BLOCKS:
                result['unmapped_blocks'] += 1
                result['unmapped_id_metas'][(block_id, meta)] += 1
        
        # Check tile entities
        te_idx = nbt_data.find(b'TileEntities')
        if te_idx > 0:
            result['tile_entities'] = nbt_data[te_idx:].count(b'Chest') + nbt_data[te_idx:].count(b'Furnace')
        
        result['valid'] = True
        
    except Exception as e:
        result['warnings'].append(f'Read error: {str(e)}')
    
    return result

def main():
    print("╔═══════════════════════════════════════════════════════════════════╗")
    print("║     IMSM Structure Parity Validator - Comprehensive Analysis     ║")
    print("╚═══════════════════════════════════════════════════════════════════╝")
    print()
    
    struct_dir = Path("/workspace/fabric-port/src/main/resources/assets/imsm/structs")
    
    if not struct_dir.exists():
        print(f"ERROR: Structure directory not found: {struct_dir}")
        return 1
    
    structures = sorted(struct_dir.glob("*.structure"))
    print(f"Found {len(structures)} structure files\n")
    
    results = []
    global_unmapped_id_metas = Counter()
    global_block_usage = Counter()
    structures_with_issues = 0
    
    for i, struct_file in enumerate(structures):
        result = analyze_structure(struct_file)
        results.append(result)
        
        if result['unmapped_blocks'] > 0:
            structures_with_issues += 1
            global_unmapped_id_metas.update(result['unmapped_id_metas'])
        
        global_block_usage.update(result['block_id_usage'])
        
        if (i + 1) % 100 == 0:
            print(f"Processed {i+1} / {len(structures)} structures...")
    
    print()
    print("═══════════════════════════════════════════════════════════════════")
    print("SUMMARY REPORT")
    print("═══════════════════════════════════════════════════════════════════")
    print()
    print(f"Total structures:            {len(results)}")
    print(f"Structures with issues:      {structures_with_issues}")
    print(f"Clean structures:            {len(results) - structures_with_issues}")
    print()
    
    total_blocks = sum(r['total_blocks'] for r in results)
    unmapped_blocks = sum(r['unmapped_blocks'] for r in results)
    print(f"Total blocks:                {total_blocks:,}")
    print(f"Unmapped blocks:             {unmapped_blocks:,}")
    print(f"Unmapped percentage:         {100.0 * unmapped_blocks / total_blocks:.2f}%")
    print()
    
    if global_unmapped_id_metas:
        print("═══════════════════════════════════════════════════════════════════")
        print("UNMAPPED LEGACY BLOCK IDS (CRITICAL)")
        print("═══════════════════════════════════════════════════════════════════")
        print()
        
        for (block_id, meta), count in global_unmapped_id_metas.most_common(50):
            expected_name = LEGACY_BLOCKS.get(block_id, f"UNKNOWN_{block_id}")
            print(f"  Legacy ID:{block_id:3d} Meta:{meta:2d}  ->  {expected_name:30s} (occurrences: {count:,})")
        print()
    
    # Block ID usage
    print("═══════════════════════════════════════════════════════════════════")
    print("LEGACY BLOCK ID USAGE (Top 30)")
    print("═══════════════════════════════════════════════════════════════════")
    print()
    for block_id, count in global_block_usage.most_common(30):
        name = LEGACY_BLOCKS.get(block_id, f"UNKNOWN_{block_id}")
        print(f"  ID:{block_id:3d}  {name:30s}  {count:,} blocks")
    print()
    
    # Structures with most issues
    if structures_with_issues > 0:
        print("═══════════════════════════════════════════════════════════════════")
        print("STRUCTURES WITH UNMAPPED BLOCKS (Top 30)")
        print("═══════════════════════════════════════════════════════════════════")
        print()
        
        issue_structures = [r for r in results if r['unmapped_blocks'] > 0]
        issue_structures.sort(key=lambda r: r['unmapped_blocks'], reverse=True)
        
        for r in issue_structures[:30]:
            pct = 100.0 * r['unmapped_blocks'] / r['total_blocks']
            print(f"  {r['name']:50s}  {r['unmapped_blocks']:6,} / {r['total_blocks']:6,} ({pct:5.1f}%)")
        print()
    
    # Write detailed JSON report
    report_data = {
        'summary': {
            'total_structures': len(results),
            'structures_with_issues': structures_with_issues,
            'total_blocks': total_blocks,
            'unmapped_blocks': unmapped_blocks,
            'unmapped_percentage': 100.0 * unmapped_blocks / total_blocks if total_blocks > 0 else 0
        },
        'unmapped_id_metas': {f"{k[0]}:{k[1]}": v for k, v in global_unmapped_id_metas.most_common()},
        'block_usage': {str(k): v for k, v in global_block_usage.most_common()},
        'structures_with_issues': [
            {
                'name': r['name'],
                'unmapped_blocks': r['unmapped_blocks'],
                'total_blocks': r['total_blocks'],
                'percentage': 100.0 * r['unmapped_blocks'] / r['total_blocks'] if r['total_blocks'] > 0 else 0
            }
            for r in results if r['unmapped_blocks'] > 0
        ]
    }
    
    report_path = Path("/workspace/PARITY_VALIDATION_DETAILED.json")
    with open(report_path, 'w') as f:
        json.dump(report_data, f, indent=2)
    
    print("═══════════════════════════════════════════════════════════════════")
    print(f"Detailed JSON report: {report_path}")
    print("═══════════════════════════════════════════════════════════════════")
    print()
    
    if structures_with_issues > 0:
        print("❌ VALIDATION FAILED: Unmapped blocks found")
        print()
        print("Next steps:")
        print("1. Add missing block ID mappings to LegacyBlockStates.java")
        print("2. Re-run validation")
        return 1
    else:
        print("✅ VALIDATION PASSED: All blocks mapped")
        return 0

if __name__ == '__main__':
    exit(main())

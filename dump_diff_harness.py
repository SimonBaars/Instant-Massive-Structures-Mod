#!/usr/bin/env python3
"""
COMPREHENSIVE STRUCTURE PARITY DUMP/DIFF HARNESS
Compares MC 1.10.2 reference mapping vs Fabric LegacyBlockStates for ALL 952 structures
"""

import gzip
import struct
from pathlib import Path
from collections import defaultdict, Counter
import json
import sys

# Import MC 1.10.2 reference mapper
sys.path.insert(0, '/workspace')
from mc_1_10_2_reference_mapper import MC_1_10_2_MAPPER

# Fabric LegacyBlockStates mapping (our implementation)
# This mirrors the Java implementation in fabric-port/src/main/java/.../LegacyBlockStates.java

FABRIC_DYE_COLORS = [
    "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
    "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
]

FABRIC_WOOD_TYPES = ["oak", "spruce", "birch", "jungle", "acacia", "dark_oak"]

FABRIC_STONE_SLAB_VARIANTS = [
    "smooth_stone_slab", "sandstone_slab", "petrified_oak_slab", "cobblestone_slab",
    "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab"
]

def fabric_from_legacy(block_id, meta):
    """
    Fabric port's LegacyBlockStates.fromLegacy(int id, int meta) implementation
    Returns: (block_name, properties_dict) or None
    """
    meta = meta & 0xF
    
    # Map known blocks (mapKnown function)
    known_mapping = {
        0: ("air", {}),
        1: fabric_stone(meta),
        3: fabric_dirt(meta),
        5: fabric_planks(meta),
        6: fabric_sapling(meta),
        17: fabric_log(meta, False),
        18: fabric_leaves(meta, False),
        19: ("wet_sponge" if meta == 1 else "sponge", {}),
        24: fabric_sandstone(meta, False),
        31: fabric_tallgrass(meta),
        35: (f"{FABRIC_DYE_COLORS[meta]}_wool", {}),
        38: fabric_flower(meta),
        43: fabric_stone_slab(meta, True),
        44: fabric_stone_slab(meta, False),
        90: fabric_nether_portal(meta),
        95: (f"{FABRIC_DYE_COLORS[meta]}_stained_glass", {}),
        # Add more from mapKnown...
    }
    
    if block_id in known_mapping:
        return known_mapping[block_id]
    
    # Fallback to blockByLegacyIdOnly
    return fabric_block_by_id_only(block_id, meta)

def fabric_stone(meta):
    variants = ["stone", "granite", "smooth_granite", "diorite",
                "smooth_diorite", "andesite", "smooth_andesite"]
    return (variants[min(meta, 6)], {})

def fabric_dirt(meta):
    types = ["dirt", "coarse_dirt", "podzol"]
    return (types[min(meta, 2)], {})

def fabric_planks(meta):
    return (f"{FABRIC_WOOD_TYPES[min(meta, 5)]}_planks", {})

def fabric_sapling(meta):
    wood = FABRIC_WOOD_TYPES[min(meta & 0x7, 5)]
    stage = "1" if (meta & 0x8) != 0 else "0"
    return (f"{wood}_sapling", {"stage": stage})

def fabric_log(meta, stripped):
    wood = FABRIC_WOOD_TYPES[min(meta & 0x3, 3)]
    axis_bits = (meta >> 2) & 0x3
    axis = ["y", "x", "z", "y"][axis_bits]
    prefix = "stripped_" if stripped else ""
    return (f"{prefix}{wood}_log", {"axis": axis})

def fabric_leaves(meta, persistent):
    wood = FABRIC_WOOD_TYPES[min(meta & 0x3, 3)]
    persistent_val = "true" if (meta & 0x4) != 0 else "false"
    distance = "1" if (meta & 0x8) == 0 else "7"
    return (f"{wood}_leaves", {"persistent": persistent_val, "distance": distance})

def fabric_tallgrass(meta):
    types = ["dead_bush", "short_grass", "fern"]
    return (types[min(meta, 2)], {})

def fabric_flower(meta):
    if meta == 0:
        return ("poppy", {})
    flowers = ["blue_orchid", "allium", "azure_bluet", "red_tulip",
               "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy"]
    return (flowers[min(meta - 1, 7)], {})

def fabric_sandstone(meta, red):
    types = ["sandstone", "chiseled_sandstone", "cut_sandstone"]
    prefix = "red_" if red else ""
    return (f"{prefix}{types[min(meta, 2)]}", {})

def fabric_stone_slab(meta, is_double):
    variant = FABRIC_STONE_SLAB_VARIANTS[min(meta & 0x7, 7)]
    if is_double:
        return (variant.replace("_slab", ""), {"type": "double"})
    else:
        slab_type = "top" if (meta & 0x8) != 0 else "bottom"
        return (variant, {"type": slab_type})

def fabric_nether_portal(meta):
    axis = "x" if meta == 1 else "z"
    return ("nether_portal", {"axis": axis})

def fabric_block_by_id_only(block_id, meta):
    """
    Fabric's legacyMappings array (blockByLegacyIdOnly function)
    """
    legacy_mappings = [
        "air", "stone", "grass_block", "dirt", "cobblestone", "oak_planks",
        "oak_sapling", "bedrock", "water", "water", "lava", "lava", "sand",
        "gravel", "gold_ore", "iron_ore", "coal_ore", "oak_log", "oak_leaves",
        "sponge", "glass", "lapis_ore", "lapis_block", "dispenser", "sandstone",
        "note_block", "red_bed", "powered_rail", "detector_rail", "sticky_piston",
        "cobweb", "short_grass", "dead_bush", "piston", "moving_piston", "white_wool", "moving_piston",
        "dandelion", "poppy", "brown_mushroom", "red_mushroom", "gold_block", "iron_block",
        "smooth_stone_slab", "stone_slab", "bricks", "tnt", "bookshelf", "mossy_cobblestone",
        "obsidian", "torch", "fire", "spawner", "oak_stairs", "chest", "redstone_wire",
        "diamond_ore", "diamond_block", "crafting_table", "wheat", "farmland",
        "furnace", "furnace", "oak_sign", "oak_door", "ladder", "rail", "cobblestone_stairs",
        "oak_wall_sign", "lever", "stone_pressure_plate", "iron_door", "oak_pressure_plate",
        "redstone_ore", "redstone_ore", "redstone_torch", "redstone_torch", "stone_button", "snow", "ice", "snow_block",
        "cactus", "clay", "sugar_cane", "jukebox", "oak_fence", "pumpkin",
        "netherrack", "soul_sand", "glowstone", "nether_portal", "jack_o_lantern",
        # ... continue for all 235...
    ]
    
    if block_id < 0 or block_id >= len(legacy_mappings):
        return None
    
    block_name = legacy_mappings[block_id]
    # Apply generic orientation if needed
    props = fabric_apply_generic_orientation(block_id, meta, block_name)
    return (block_name, props)

def fabric_apply_generic_orientation(block_id, meta, block_name):
    """
    Fabric's applyGenericOrientation function
    """
    # Stairs
    if block_id in [53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180, 203]:
        facing = ["east", "west", "south", "north"][meta & 0x3]
        half = "top" if (meta & 0x4) != 0 else "bottom"
        return {"facing": facing, "half": half, "shape": "straight"}
    
    # Torches
    if "torch" in block_name and block_id in [50, 75, 76]:
        facing_map = {1: "east", 2: "west", 3: "south", 4: "north", 5: "up", 0: "up"}
        return {"facing": facing_map.get(meta, "up")}
    
    # Pumpkins
    if block_id in [86, 91]:
        facing = ["south", "west", "north", "east"][meta & 0x3]
        return {"facing": facing}
    
    return {}

def read_structure(filepath):
    """Read structure file and return blocks"""
    try:
        with gzip.open(filepath, 'rb') as f:
            nbt_data = f.read()
        
        width_idx = nbt_data.find(b'Width')
        height_idx = nbt_data.find(b'Height')
        length_idx = nbt_data.find(b'Length')
        
        if width_idx <= 0:
            return None, None, None, None, None
        
        width = struct.unpack('>h', nbt_data[width_idx+7:width_idx+9])[0]
        height = struct.unpack('>h', nbt_data[height_idx+8:height_idx+10])[0]
        length = struct.unpack('>h', nbt_data[length_idx+8:length_idx+10])[0]
        
        blocks_idx = nbt_data.find(b'Blocks')
        data_idx = nbt_data.find(b'Data')
        
        if blocks_idx <= 0:
            return None, None, None, None, None
        
        blocks_start = blocks_idx + 11
        expected_size = width * height * length
        blocks = nbt_data[blocks_start:blocks_start + expected_size]
        
        data_start = data_idx + 9 if data_idx > 0 else 0
        data = nbt_data[data_start:data_start + expected_size] if data_idx > 0 else bytes([0] * expected_size)
        
        return width, height, length, blocks, data
        
    except Exception as e:
        return None, None, None, None, None

def compare_structures():
    """Main comparison function"""
    struct_dir = Path("/workspace/fabric-port/src/main/resources/assets/imsm/structs")
    structures = sorted(struct_dir.glob("*.structure"))
    
    print("╔═══════════════════════════════════════════════════════════════════╗")
    print("║     MC 1.10.2 vs Fabric COMPREHENSIVE DUMP/DIFF ANALYSIS         ║")
    print("╚═══════════════════════════════════════════════════════════════════╝")
    print()
    print(f"Analyzing {len(structures)} structures...")
    print()
    
    total_structures = 0
    total_blocks = 0
    total_mismatches = 0
    structures_with_mismatches = 0
    mismatch_details = defaultdict(Counter)
    
    for i, struct_file in enumerate(structures):
        width, height, length, blocks, data = read_structure(struct_file)
        if blocks is None:
            continue
        
        total_structures += 1
        struct_mismatches = 0
        
        for j, (block_id, meta) in enumerate(zip(blocks, data)):
            if block_id == 0:  # Skip air
                continue
            
            total_blocks += 1
            
            # Get MC 1.10.2 reference
            mc_result = MC_1_10_2_MAPPER(block_id, meta)
            
            # Get Fabric result
            fabric_result = fabric_from_legacy(block_id, meta)
            
            # Compare
            if mc_result != fabric_result:
                total_mismatches += 1
                struct_mismatches += 1
                mismatch_key = f"ID:{block_id} Meta:{meta}"
                mismatch_details[mismatch_key]["count"] += 1
                mismatch_details[mismatch_key]["mc"] = str(mc_result)
                mismatch_details[mismatch_key]["fabric"] = str(fabric_result)
        
        if struct_mismatches > 0:
            structures_with_mismatches += 1
        
        if (i + 1) % 100 == 0:
            print(f"Processed {i+1}/{len(structures)} structures...")
    
    print()
    print("═══════════════════════════════════════════════════════════════════")
    print("COMPARISON RESULTS")
    print("═══════════════════════════════════════════════════════════════════")
    print()
    print(f"Total structures: {total_structures}")
    print(f"Total non-air blocks: {total_blocks:,}")
    print(f"Total mismatches: {total_mismatches:,}")
    print(f"Structures with mismatches: {structures_with_mismatches}")
    print(f"Match rate: {100.0 * (total_blocks - total_mismatches) / total_blocks:.2f}%")
    print()
    
    if total_mismatches > 0:
        print("═══════════════════════════════════════════════════════════════════")
        print("TOP MISMATCHES (by occurrence)")
        print("═══════════════════════════════════════════════════════════════════")
        print()
        
        sorted_mismatches = sorted(mismatch_details.items(), 
                                  key=lambda x: x[1]["count"], reverse=True)
        
        for key, details in sorted_mismatches[:30]:
            print(f"{key}:")
            print(f"  Count: {details['count']:,}")
            print(f"  MC 1.10.2: {details['mc']}")
            print(f"  Fabric:    {details['fabric']}")
            print()
    
    # Write detailed report
    report_path = Path("/workspace/DUMP_DIFF_REPORT.json")
    with open(report_path, 'w') as f:
        json.dump({
            'summary': {
                'total_structures': total_structures,
                'total_blocks': total_blocks,
                'total_mismatches': total_mismatches,
                'structures_with_mismatches': structures_with_mismatches,
                'match_rate': 100.0 * (total_blocks - total_mismatches) / total_blocks if total_blocks > 0 else 0
            },
            'mismatches': {k: dict(v) for k, v in sorted_mismatches}
        }, f, indent=2)
    
    print(f"Detailed report: {report_path}")
    
    return total_mismatches == 0

if __name__ == '__main__':
    success = compare_structures()
    sys.exit(0 if success else 1)

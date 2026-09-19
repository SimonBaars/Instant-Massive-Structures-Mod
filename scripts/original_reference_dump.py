#!/usr/bin/env python3
"""
Original reference dump: reimplements MC 1.12-style block mapping logic
to serve as ground truth for parity comparison.

This is an OFFLINE reimplementation (not live Forge 1.10.2).
Based on MC 1.10.2/1.12 Block.getStateFromMeta() logic.

Output: fabric-port/parity-dumps/original-reference.ndjson
Format: One JSON object per line per block: {structure, x, y, z, blockId, properties, tileEntity}
"""

import json
import gzip
import struct
from pathlib import Path
from typing import Dict, List, Tuple, Optional
import nbtlib


# MC 1.10.2 block ID -> name mapping (0-235)
LEGACY_BLOCK_NAMES = [
    "air", "stone", "grass", "dirt", "cobblestone", "planks",
    "sapling", "bedrock", "flowing_water", "water", "flowing_lava", "lava",
    "sand", "gravel", "gold_ore", "iron_ore", "coal_ore", "log",
    "leaves", "sponge", "glass", "lapis_ore", "lapis_block", "dispenser",
    "sandstone", "noteblock", "bed", "golden_rail", "detector_rail", "sticky_piston",
    "web", "tallgrass", "deadbush", "piston", "piston_head", "wool",
    "piston_extension", "yellow_flower", "red_flower", "brown_mushroom", "red_mushroom", "gold_block",
    "iron_block", "double_stone_slab", "stone_slab", "brick_block", "tnt", "bookshelf",
    "mossy_cobblestone", "obsidian", "torch", "fire", "mob_spawner", "oak_stairs",
    "chest", "redstone_wire", "diamond_ore", "diamond_block", "crafting_table", "wheat",
    "farmland", "furnace", "lit_furnace", "standing_sign", "wooden_door", "ladder",
    "rail", "stone_stairs", "wall_sign", "lever", "stone_pressure_plate", "iron_door",
    "wooden_pressure_plate", "redstone_ore", "lit_redstone_ore", "unlit_redstone_torch", "redstone_torch", "stone_button",
    "snow_layer", "ice", "snow", "cactus", "clay", "reeds",
    "jukebox", "fence", "pumpkin", "netherrack", "soul_sand", "glowstone",
    "portal", "lit_pumpkin", "cake", "unpowered_repeater", "powered_repeater", "stained_glass",
    "trapdoor", "monster_egg", "stonebrick", "brown_mushroom_block", "red_mushroom_block", "iron_bars",
    "glass_pane", "melon_block", "pumpkin_stem", "melon_stem", "vine", "fence_gate",
    "brick_stairs", "stone_brick_stairs", "mycelium", "waterlily", "nether_brick", "nether_brick_fence",
    "nether_brick_stairs", "nether_wart", "enchanting_table", "brewing_stand", "cauldron", "end_portal",
    "end_portal_frame", "end_stone", "dragon_egg", "redstone_lamp", "lit_redstone_lamp", "double_wooden_slab",
    "wooden_slab", "cocoa", "sandstone_stairs", "emerald_ore", "ender_chest", "tripwire_hook",
    "tripwire", "emerald_block", "spruce_stairs", "birch_stairs", "jungle_stairs", "command_block",
    "beacon", "cobblestone_wall", "flower_pot", "carrots", "potatoes", "wooden_button",
    "skull", "anvil", "trapped_chest", "light_weighted_pressure_plate", "heavy_weighted_pressure_plate", "unpowered_comparator",
    "powered_comparator", "daylight_detector", "redstone_block", "quartz_ore", "hopper", "quartz_block",
    "quartz_stairs", "activator_rail", "dropper", "stained_hardened_clay", "stained_glass_pane", "leaves2",
    "log2", "acacia_stairs", "dark_oak_stairs", "slime", "barrier", "iron_trapdoor",
    "prismarine", "sea_lantern", "hay_block", "carpet", "hardened_clay", "coal_block",
    "packed_ice", "double_plant", "standing_banner", "wall_banner", "daylight_detector_inverted", "red_sandstone",
    "red_sandstone_stairs", "double_stone_slab2", "stone_slab2", "spruce_fence_gate", "birch_fence_gate", "jungle_fence_gate",
    "dark_oak_fence_gate", "acacia_fence_gate", "spruce_fence", "birch_fence", "jungle_fence", "dark_oak_fence",
    "acacia_fence", "spruce_door", "birch_door", "jungle_door", "acacia_door", "dark_oak_door",
    "end_rod", "chorus_plant", "chorus_flower", "purpur_block", "purpur_pillar", "purpur_stairs",
    "purpur_double_slab", "purpur_slab", "end_bricks", "beetroots", "grass_path", "end_gateway",
    "repeating_command_block", "chain_command_block", "frosted_ice", "magma", "nether_wart_block", "red_nether_brick",
    "bone_block", "structure_void", "observer", "white_shulker_box", "orange_shulker_box", "magenta_shulker_box",
    "light_blue_shulker_box", "yellow_shulker_box", "lime_shulker_box", "pink_shulker_box", "gray_shulker_box", "light_gray_shulker_box",
    "cyan_shulker_box", "purple_shulker_box", "blue_shulker_box", "brown_shulker_box", "green_shulker_box", "red_shulker_box",
    "black_shulker_box", "white_glazed_terracotta"
]

# Modern mappings for flattened names
MODERN_NAMES = {
    "grass": "grass_block",
    "planks": "oak_planks",
    "sapling": "oak_sapling",
    "flowing_water": "water",
    "flowing_lava": "lava",
    "log": "oak_log",
    "leaves": "oak_leaves",
    "noteblock": "note_block",
    "golden_rail": "powered_rail",
    "web": "cobweb",
    "tallgrass": "short_grass",
    "deadbush": "dead_bush",
    "piston_extension": "moving_piston",
    "yellow_flower": "dandelion",
    "red_flower": "poppy",
    "brick_block": "bricks",
    "double_stone_slab": "smooth_stone_slab",
    "mob_spawner": "spawner",
    "wooden_door": "oak_door",
    "unlit_redstone_torch": "redstone_torch",
    "snow_layer": "snow",
    "snow": "snow_block",
    "reeds": "sugar_cane",
    "fence": "oak_fence",
    "lit_pumpkin": "jack_o_lantern",
    "unpowered_repeater": "repeater",
    "powered_repeater": "repeater",
    "monster_egg": "infested_stone",
    "stonebrick": "stone_bricks",
    "melon_block": "melon",
    "fence_gate": "oak_fence_gate",
    "waterlily": "lily_pad",
    "nether_brick": "nether_bricks",
    "double_wooden_slab": "oak_slab",
    "wooden_slab": "oak_slab",
    "wooden_button": "oak_button",
    "skull": "skeleton_skull",
    "unpowered_comparator": "comparator",
    "powered_comparator": "comparator",
    "stained_hardened_clay": "white_terracotta",
    "leaves2": "acacia_leaves",
    "log2": "acacia_log",
    "slime": "slime_block",
    "hardened_clay": "terracotta",
    "double_plant": "sunflower",
    "standing_banner": "white_banner",
    "wall_banner": "white_wall_banner",
    "daylight_detector_inverted": "daylight_detector",
    "double_stone_slab2": "red_sandstone_slab",
    "stone_slab2": "red_sandstone_slab",
    "end_bricks": "end_stone_bricks",
    "grass_path": "dirt_path",
    "magma": "magma_block",
    "red_nether_brick": "red_nether_bricks",
}


def get_modern_name(legacy_name: str) -> str:
    """Convert legacy block name to modern namespace:name format."""
    modern = MODERN_NAMES.get(legacy_name, legacy_name)
    return f"minecraft:{modern}"


def get_facing_4(meta: int) -> str:
    """4-direction horizontal facing (2,3,4,5 -> north,south,west,east)."""
    return ["south", "west", "north", "east"][meta & 3]


def get_facing_full(meta: int) -> str:
    """6-direction facing (0-5 -> down,up,north,south,west,east)."""
    return ["down", "up", "north", "south", "west", "east"][meta & 7]


def get_axis_from_meta(meta: int) -> str:
    """Log/pillar axis from meta."""
    axis_val = (meta >> 2) & 3
    return ["y", "x", "z"][axis_val] if axis_val < 3 else "y"


def meta_to_properties(block_id: int, meta: int) -> Dict[str, str]:
    """
    Reimplementation of MC 1.10.2/1.12 Block.getStateFromMeta() logic.
    Returns modern BlockState properties.
    """
    props = {}
    
    if block_id == 0:  # air
        return props
    elif block_id == 1:  # stone variants
        variant = ["stone", "granite", "polished_granite", "diorite", "polished_diorite", "andesite", "polished_andesite"][meta & 7]
        return props
    elif block_id == 17:  # log
        props["axis"] = get_axis_from_meta(meta)
    elif block_id == 23 or block_id == 158:  # dispenser / dropper
        props["facing"] = get_facing_full(meta & 7)
        props["triggered"] = str((meta & 8) != 0).lower()
    elif block_id == 26:  # bed
        props["facing"] = get_facing_4(meta & 3)
        props["part"] = "head" if (meta & 8) != 0 else "foot"
        props["occupied"] = str((meta & 4) != 0).lower()
    elif block_id == 34:  # piston_head
        props["facing"] = get_facing_full(meta & 7)
        props["type"] = "sticky" if (meta & 8) != 0 else "normal"
    elif block_id == 50:  # torch
        if (meta & 15) in [1, 2, 3, 4]:
            # Wall torch
            props["facing"] = ["", "east", "west", "south", "north"][meta & 15]
    elif block_id == 61:  # furnace
        props["facing"] = get_facing_4(meta)
        props["lit"] = "false"
    elif block_id == 62:  # lit_furnace
        props["facing"] = get_facing_4(meta)
        props["lit"] = "true"
    elif block_id == 75:  # unlit_redstone_torch
        if (meta & 15) in [1, 2, 3, 4]:
            props["facing"] = ["", "east", "west", "south", "north"][meta & 15]
        props["lit"] = "false"
    elif block_id == 76:  # redstone_torch
        if (meta & 15) in [1, 2, 3, 4]:
            props["facing"] = ["", "east", "west", "south", "north"][meta & 15]
        props["lit"] = "true"
    elif block_id == 86:  # pumpkin
        props["facing"] = get_facing_4(meta)
    elif block_id == 91:  # lit_pumpkin (jack_o_lantern)
        props["facing"] = get_facing_4(meta)
    
    return props


def dump_structure(structure_path: Path, structure_name: str) -> List[Dict]:
    """Dump a single structure using original reference logic."""
    records = []
    
    with gzip.open(structure_path, 'rb') as f:
        nbt = nbtlib.load(f)
    
    # Extract dimensions
    width, height, length = nbt['size']
    
    # Extract arrays
    blocks = bytes(nbt['blocks'])
    data = bytes(nbt['data'])
    
    # Extract TileEntities
    tile_entities_by_pos = {}
    if 'TileEntities' in nbt:
        for te in nbt['TileEntities']:
            pos = (te['x'], te['y'], te['z'])
            tile_entities_by_pos[pos] = te
    
    # Process each block
    for y in range(height):
        for z in range(length):
            for x in range(width):
                index = x + (y * length + z) * width
                legacy_id = blocks[index]
                legacy_meta = data[index]
                
                # Skip air
                if legacy_id == 0:
                    continue
                
                # Get legacy block name
                if legacy_id < len(LEGACY_BLOCK_NAMES):
                    legacy_name = LEGACY_BLOCK_NAMES[legacy_id]
                else:
                    legacy_name = "unknown"
                
                # Convert to modern name
                block_id = get_modern_name(legacy_name)
                
                # Get properties from meta
                properties = meta_to_properties(legacy_id, legacy_meta)
                
                record = {
                    "structure": structure_name,
                    "x": x,
                    "y": y,
                    "z": z,
                    "legacyId": legacy_id,
                    "legacyMeta": legacy_meta,
                    "blockId": block_id,
                    "properties": properties
                }
                
                # Add TileEntity if present
                if (x, y, z) in tile_entities_by_pos:
                    te = tile_entities_by_pos[(x, y, z)]
                    te_summary = {"id": te.get('id', '')}
                    if 'Items' in te:
                        te_summary['Items'] = [
                            {
                                "Slot": item['Slot'],
                                "id": item['id'],
                                "Count": item['Count'],
                                "Damage": item.get('Damage', 0)
                            }
                            for item in te['Items']
                        ]
                    record["tileEntity"] = te_summary
                
                records.append(record)
    
    return records


def main():
    structures_dir = Path("fabric-port/src/main/resources/structures")
    output_dir = Path("fabric-port/parity-dumps")
    output_dir.mkdir(parents=True, exist_ok=True)
    
    output_file = output_dir / "original-reference.ndjson"
    
    structure_files = sorted(structures_dir.rglob("*.structure"))
    
    print(f"Found {len(structure_files)} structure files")
    print(f"Writing reference snapshots to: {output_file}")
    
    processed = 0
    total_blocks = 0
    
    with output_file.open('w') as f:
        for structure_path in structure_files:
            structure_name = str(structure_path.relative_to(structures_dir)).replace('\\', '/').replace('.structure', '')
            
            try:
                records = dump_structure(structure_path, structure_name)
                for record in records:
                    f.write(json.dumps(record) + '\n')
                    total_blocks += 1
                
                processed += 1
                
                if processed % 100 == 0:
                    print(f"Processed {processed}/{len(structure_files)} structures...")
            
            except Exception as e:
                print(f"Error processing {structure_name}: {e}")
                import traceback
                traceback.print_exc()
    
    print(f"\nReference dump complete!")
    print(f"Structures processed: {processed}")
    print(f"Total blocks dumped: {total_blocks}")
    print(f"Output: {output_file.absolute()}")


if __name__ == '__main__':
    main()

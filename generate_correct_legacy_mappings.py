#!/usr/bin/env python3
"""
Generate correctly aligned legacy mappings array for LegacyBlockStates.java
Array index MUST equal legacy block ID
"""

# MC 1.10.2 complete block ID list
LEGACY_BLOCKS = {
    0: "air", 1: "stone", 2: "grass_block", 3: "dirt", 4: "cobblestone", 5: "oak_planks",
    6: "oak_sapling", 7: "bedrock", 8: "water", 9: "water", 10: "lava", 11: "lava",
    12: "sand", 13: "gravel", 14: "gold_ore", 15: "iron_ore", 16: "coal_ore",
    17: "oak_log", 18: "oak_leaves", 19: "sponge", 20: "glass", 21: "lapis_ore",
    22: "lapis_block", 23: "dispenser", 24: "sandstone", 25: "note_block",
    26: "red_bed", 27: "powered_rail", 28: "detector_rail", 29: "sticky_piston",
    30: "cobweb", 31: "short_grass", 32: "dead_bush", 33: "piston", 34: "moving_piston",
    35: "white_wool", 36: "moving_piston", 37: "dandelion", 38: "poppy",
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
    233: "red_shulker_box", 234: "black_shulker_box", 235: "white_glazed_terracotta"
}

print("Correctly aligned legacyMappings array:")
print()
print('\t\tString[] legacyMappings = {')

for line_start in range(0, 236, 6):
    line_blocks = []
    for i in range(line_start, min(line_start + 6, 236)):
        block_name = LEGACY_BLOCKS.get(i, f"unknown_{i}")
        line_blocks.append(f'"{block_name}"')
    
    line = ", ".join(line_blocks)
    if line_start + 6 < 236:
        line += ","
    
    comment = f"  // {line_start}-{min(line_start + 5, 235)}"
    print(f"\t\t\t{line}{comment}")

print('\t\t};')
print()
print(f"Array length: 236 (IDs 0-235)")

#!/usr/bin/env python3
"""Offline audit: schematic Blocks+Data vs LegacyBlockStates coverage."""
from __future__ import annotations

import gzip
import json
import struct
import sys
from collections import Counter, defaultdict
from pathlib import Path

try:
    import nbtlib
except ImportError:
    nbtlib = None

STRUCTS = Path(
    "/workspace/minecraft-mods/Instant-Massive-Structures-Mod/fabric-port/"
    "src/main/resources/assets/imsm/structs"
)
OUT_MD = Path(
    "/workspace/minecraft-mods/Instant-Massive-Structures-Mod/fabric-port/"
    "playtest-shots/META_AUDIT_REPORT.md"
)
OUT_JSON = Path("/workspace/minecraft-mods/_build-logs/ims-meta-audit.json")

# --- Coverage model mirroring LegacyBlockStates.java ---

MAP_KNOWN = {
    0, 1, 3, 5, 6, 17, 18, 19, 24, 31, 35, 38, 43, 44, 90, 95, 97, 98,
    125, 126, 140, 155, 159, 160, 161, 162, 168, 171, 175, 179, 204, 205,
}
STAIR_IDS = {53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180, 203}
ORIENT_IDS = {
    # stairs covered via STAIR_IDS
    *STAIR_IDS,
    # slabs via mapKnown + SlabBlock path for fallbacks
    43, 44, 125, 126, 182, 204,  # 182 red_sandstone_slab single in some tables
    # pillars / hay / bone / purpur
    170, 216, 202,
    # ladder
    65,
    # furnace/chest/dispenser/hopper/dropper/ender
    61, 62, 54, 146, 130, 23, 158, 154,
    # torches
    50, 75, 76,
    # pumpkin / jack
    86, 91,
    # fence gates
    107, 183, 184, 185, 186, 187,
    # trapdoors
    96, 167,
    # buttons
    77, 143,
    # lever
    69,
    # pistons
    33, 29, 34,
    # rails
    66, 27, 28, 157,
    # anvil
    145,
    # glazed terracotta
    *range(235, 251),
    # observer / end rod
    218, 198,
    # doors / bed / vine / tripwire / repeater / skull / signs
    64, 71, 193, 194, 195, 196, 197, 26, 106, 131, 93, 94, 144, 68, 63, 176,
}

# Fallback ID table length in LegacyBlockStates (truncated at orange_shulker)
# Counted from source array — used only for "unknown id" notes.
LEGACY_TABLE_LEN = 221  # approx; ids >= this need glazed/concrete special paths

# Blocks where meta≠0 is meaningful but NOT handled by mapper → real gaps
# (doors, beds, vines, snow layers, farmland, crops, redstone, signs, walls,
#  mushroom blocks, skulls, banners, shulker facing, cocoa, cauldron, etc.)
KNOWN_GAP_CANDIDATES = {
    8: "flowing_water level",
    9: "water level",
    10: "flowing_lava level",
    11: "lava level",
    26: "bed facing/occupied/part",
    51: "fire age",
    55: "redstone_wire power",
    59: "wheat age",
    60: "farmland moisture",
    63: "standing_sign rotation",
    64: "oak_door facing/half/hinge/open",
    68: "wall_sign facing",
    70: "stone_pressure_plate powered",
    71: "iron_door facing/half/hinge/open",
    72: "oak_pressure_plate powered",
    78: "snow layers",
    81: "cactus age",
    83: "sugar_cane age",
    92: "cake bites",
    93: "unpowered_repeater delay/facing",
    94: "powered_repeater delay/facing",
    99: "brown_mushroom_block variant",
    100: "red_mushroom_block variant",
    104: "pumpkin_stem age",
    105: "melon_stem age",
    106: "vine attachments",
    115: "nether_wart age",
    117: "brewing_stand bottles",
    118: "cauldron level",
    120: "end_portal_frame eye/facing",
    127: "cocoa age/facing",
    131: "tripwire_hook facing/attached/powered",
    132: "tripwire attached/powered",
    139: "cobblestone_wall variant/up",
    140: "flower_pot contents",
    141: "carrots age",
    142: "potatoes age",
    144: "skull rotation/facing",
    147: "light_weighted_pressure_plate power",
    148: "heavy_weighted_pressure_plate power",
    149: "unpowered_comparator facing/mode/powered",
    150: "powered_comparator facing/mode/powered",
    151: "daylight_detector power",
    176: "standing_banner rotation",
    177: "wall_banner facing",
    193: "spruce_door facing/half/hinge/open",
    194: "birch_door facing/half/hinge/open",
    195: "jungle_door facing/half/hinge/open",
    196: "acacia_door facing/half/hinge/open",
    197: "dark_oak_door facing/half/hinge/open",
    199: "chorus_plant connections",
    207: "beetroots age",
    # shulker boxes 219-234: facing
    **{i: "shulker_box facing" for i in range(219, 235)},
}

# Meta bits that are harmless / cosmetic / fluid sim — often N/A for static structures
HARMLESS_GAP = {
    8, 9, 10, 11, 51, 55, 59, 60, 70, 72, 78, 81, 83, 92, 104, 105, 115,
    117, 118, 132, 141, 142, 147, 148, 151, 207,
}

# High visual impact if ignored
HIGH_IMPACT_GAP = {
    26, 63, 64, 68, 71, 93, 94, 99, 100, 106, 120, 127, 131, 139, 144,
    149, 150, 176, 177, 193, 194, 195, 196, 197,
} | set(range(219, 235))

# Names for common legacy ids
ID_NAMES = {
    0: "air", 1: "stone", 2: "grass", 3: "dirt", 4: "cobble", 5: "planks",
    6: "sapling", 7: "bedrock", 8: "flowing_water", 9: "water", 10: "flowing_lava",
    11: "lava", 12: "sand", 13: "gravel", 14: "gold_ore", 15: "iron_ore",
    16: "coal_ore", 17: "log", 18: "leaves", 19: "sponge", 20: "glass",
    23: "dispenser", 24: "sandstone", 26: "bed", 27: "powered_rail",
    28: "detector_rail", 29: "sticky_piston", 33: "piston", 34: "piston_head",
    35: "wool", 43: "double_stone_slab", 44: "stone_slab", 50: "torch",
    53: "oak_stairs", 54: "chest", 61: "furnace", 62: "lit_furnace",
    63: "standing_sign", 64: "oak_door", 65: "ladder", 66: "rail",
    67: "cobble_stairs", 68: "wall_sign", 69: "lever", 71: "iron_door",
    75: "unlit_redstone_torch", 76: "redstone_torch", 77: "stone_button",
    86: "pumpkin", 91: "lit_pumpkin", 93: "repeater", 94: "powered_repeater",
    95: "stained_glass", 96: "trapdoor", 98: "stonebrick", 99: "brown_mushroom_block",
    100: "red_mushroom_block", 106: "vine", 107: "fence_gate", 108: "brick_stairs",
    109: "stone_brick_stairs", 114: "nether_brick_stairs", 125: "double_wood_slab",
    126: "wood_slab", 128: "sandstone_stairs", 130: "ender_chest", 134: "spruce_stairs",
    135: "birch_stairs", 136: "jungle_stairs", 139: "cobble_wall", 143: "wood_button",
    144: "skull", 145: "anvil", 146: "trapped_chest", 154: "hopper", 155: "quartz",
    156: "quartz_stairs", 157: "activator_rail", 158: "dropper", 159: "terracotta",
    160: "stained_glass_pane", 161: "leaves2", 162: "log2", 163: "acacia_stairs",
    164: "dark_oak_stairs", 167: "iron_trapdoor", 168: "prismarine", 170: "hay",
    171: "carpet", 175: "double_plant", 176: "standing_banner", 177: "wall_banner",
    179: "red_sandstone", 180: "red_sandstone_stairs", 183: "spruce_fence_gate",
    198: "end_rod", 202: "purpur_pillar", 203: "purpur_stairs", 204: "concrete",
    205: "concrete_powder", 216: "bone_block", 218: "observer",
}


def id_name(i: int) -> str:
    if i in ID_NAMES:
        return ID_NAMES[i]
    if i in KNOWN_GAP_CANDIDATES:
        return KNOWN_GAP_CANDIDATES[i].split()[0]
    if 235 <= i <= 250:
        return f"glazed_terracotta_{i}"
    if 219 <= i <= 234:
        return f"shulker_{i}"
    return f"id_{i}"


def coverage_class(bid: int, meta: int) -> str:
    """Classify how LegacyBlockStates treats this pair."""
    meta &= 0xF
    if meta == 0:
        return "meta0"
    if bid in MAP_KNOWN:
        return "mapKnown"
    if bid in ORIENT_IDS or bid in STAIR_IDS:
        return "orient"
    if 235 <= bid <= 250:
        return "orient"  # glazed
    # Generic horizontal facing fallback may apply for some blocks with HORIZONTAL_FACING
    # when meta in 2..5 — but only if the resolved block has that property.
    # Treat as soft_cover for known horizontal blocks that aren't explicitly listed.
    soft_horizontal = {
        63, 68, 176, 177,  # signs/banners — rotation encoding differs; soft may mis-apply
    }
    if bid in KNOWN_GAP_CANDIDATES:
        if bid in HARMLESS_GAP:
            return "gap_harmless"
        if bid in HIGH_IMPACT_GAP:
            return "gap_high"
        return "gap_other"
    # Unknown: meta≠0 but not in known gap list and not mapped — suspicious fallback
    return "unmapped_meta"


def parse_schematic_nbtlib(path: Path):
    nbt = nbtlib.load(path, gzipped=True)
    root = nbt
    if "" in root and isinstance(root[""], nbtlib.Compound):
        root = root[""]
    # Classic schematic: top-level may be Schematic compound or flat
    if "Schematic" in root:
        sch = root["Schematic"]
    else:
        sch = root
    blocks = bytes(sch["Blocks"])
    data = bytes(sch.get("Data", b"\x00" * len(blocks)))
    return blocks, data


def parse_schematic_manual(path: Path):
    """Minimal gzip+NBT reader for TAG_Byte_Array Blocks/Data."""
    # Prefer nbtlib; this is fallback
    raise RuntimeError("manual parser not implemented; install nbtlib")


def scan_all():
    files = sorted(STRUCTS.glob("*.structure")) + sorted(STRUCTS.glob("*.schematic"))
    pair_counts: Counter = Counter()  # (id, meta) -> voxels across all
    pair_structs: dict[tuple[int, int], set[str]] = defaultdict(set)
    struct_meta_nonzero: Counter = Counter()  # struct -> count of meta≠0 voxels
    struct_uncovered: Counter = Counter()  # struct -> uncovered meta≠0 voxels
    struct_errors: list[str] = []
    scanned = 0

    for path in files:
        name = path.stem
        try:
            blocks, data = parse_schematic_nbtlib(path)
        except Exception as e:
            struct_errors.append(f"{name}: {e}")
            continue
        if len(data) < len(blocks):
            data = data + b"\x00" * (len(blocks) - len(data))
        scanned += 1
        local_pairs = set()
        for i, bid in enumerate(blocks):
            if bid == 0:
                continue
            meta = data[i] & 0xF
            key = (bid, meta)
            pair_counts[key] += 1
            local_pairs.add(key)
            if meta != 0:
                struct_meta_nonzero[name] += 1
                cls = coverage_class(bid, meta)
                if cls.startswith("gap_") or cls == "unmapped_meta":
                    struct_uncovered[name] += 1
        for key in local_pairs:
            pair_structs[key].add(name)

    return {
        "scanned": scanned,
        "errors": struct_errors,
        "pair_counts": pair_counts,
        "pair_structs": pair_structs,
        "struct_meta_nonzero": struct_meta_nonzero,
        "struct_uncovered": struct_uncovered,
        "total_files": len(files),
    }


def write_report(result):
    pair_counts = result["pair_counts"]
    unique = sorted(pair_counts.keys())
    meta_nz = [(i, m) for i, m in unique if m != 0]
    by_class: dict[str, list] = defaultdict(list)
    for i, m in meta_nz:
        cls = coverage_class(i, m)
        by_class[cls].append((i, m, pair_counts[(i, m)], len(result["pair_structs"][(i, m)])))

    covered = by_class["mapKnown"] + by_class["orient"]
    uncovered_high = by_class["gap_high"]
    uncovered_harmless = by_class["gap_harmless"]
    uncovered_other = by_class["gap_other"]
    unmapped = by_class["unmapped_meta"]

    lines = []
    lines.append("# IMS Schematic Metadata Audit Report")
    lines.append("")
    lines.append("Date: 2026-09-06 (America/Phoenix)")
    lines.append("Source: `src/main/resources/assets/imsm/structs/*.structure`")
    lines.append("Mapper: `LegacyBlockStates.fromLegacy` (coverage model)")
    lines.append("")
    lines.append("## Summary")
    lines.append("")
    lines.append(f"| Metric | Value |")
    lines.append(f"|--------|------:|")
    lines.append(f"| Structure files found | {result['total_files']} |")
    lines.append(f"| Schematics scanned OK | {result['scanned']} |")
    lines.append(f"| Parse errors | {len(result['errors'])} |")
    lines.append(f"| Unique (id, meta) pairs | {len(unique)} |")
    lines.append(f"| Pairs with meta≠0 | {len(meta_nz)} |")
    lines.append(f"| Covered (mapKnown + orient) | {len(covered)} |")
    lines.append(f"| Uncovered high-impact | {len(uncovered_high)} |")
    lines.append(f"| Uncovered harmless/N/A | {len(uncovered_harmless)} |")
    lines.append(f"| Uncovered other | {len(uncovered_other)} |")
    lines.append(f"| Unmapped meta≠0 (suspicious) | {len(unmapped)} |")
    lines.append("")

    def section(title, rows, limit=80):
        lines.append(f"## {title}")
        lines.append("")
        if not rows:
            lines.append("_None._")
            lines.append("")
            return
        lines.append("| id | name | meta | voxels | #structs | note |")
        lines.append("|---:|------|-----:|-------:|---------:|------|")
        for i, m, vox, ns in sorted(rows, key=lambda t: (-t[2], t[0], t[1]))[:limit]:
            note = KNOWN_GAP_CANDIDATES.get(i, "")
            lines.append(f"| {i} | {id_name(i)} | {m} | {vox} | {ns} | {note} |")
        if len(rows) > limit:
            lines.append(f"| … | ({len(rows) - limit} more) | | | | |")
        lines.append("")

    section("Covered meta≠0 (mapKnown / orient) — sample by volume", covered, 40)
    section("HIGH-IMPACT uncovered (mapper ignores meaningful meta)", uncovered_high, 100)
    section("Harmless / N/A uncovered (age, fluid level, pressure, etc.)", uncovered_harmless, 40)
    section("Other uncovered", uncovered_other, 40)
    section("Unmapped meta≠0 (not in gap catalog — investigate)", unmapped, 80)

    # Top structures by uncovered volume
    lines.append("## Top structures by uncovered meta≠0 voxel volume")
    lines.append("")
    lines.append("| structure | meta≠0 voxels | uncovered voxels |")
    lines.append("|-----------|--------------:|-----------------:|")
    for name, unc in result["struct_uncovered"].most_common(40):
        lines.append(f"| {name} | {result['struct_meta_nonzero'][name]} | {unc} |")
    lines.append("")

    # High-meta-density candidates for playtest (covered + uncovered mix)
    lines.append("## Suggested playtest set (high meta≠0 density)")
    lines.append("")
    # Prefer structures with lots of covered orientation/color blocks
    play_scores = []
    for name, nz in result["struct_meta_nonzero"].most_common(200):
        # rough: prefer names that look like buildings/rides
        play_scores.append((nz, name))
    lines.append("| structure | meta≠0 voxels |")
    lines.append("|-----------|--------------:|")
    for nz, name in play_scores[:30]:
        lines.append(f"| {name} | {nz} |")
    lines.append("")

    # Unique block ids with any meta≠0
    ids_nz = sorted({i for i, m in meta_nz})
    lines.append("## All legacy IDs that appear with meta≠0")
    lines.append("")
    lines.append(", ".join(f"{i}({id_name(i)})" for i in ids_nz))
    lines.append("")

    if result["errors"]:
        lines.append("## Parse errors")
        lines.append("")
        for e in result["errors"][:50]:
            lines.append(f"- {e}")
        lines.append("")

    lines.append("## Residual risk notes")
    lines.append("")
    lines.append("- Coverage model mirrors `LegacyBlockStates.java` handlers; generic")
    lines.append("  `HORIZONTAL_FACING` fallback (meta 2–5) may soft-cover some wall-mounted")
    lines.append("  blocks but **does not** correctly encode door hinge/half, bed part,")
    lines.append("  vine bits, snow layers, standing-sign 16-way rotation, or mushroom-block faces.")
    lines.append("- Fluid/crop/age metas are typically irrelevant for static IMS placements.")
    lines.append("- In-game `/imsm metastats` remains the ground-truth for stair/color/axis counts.")
    lines.append("")

    OUT_MD.parent.mkdir(parents=True, exist_ok=True)
    OUT_MD.write_text("\n".join(lines))
    # also copy path mentioned in plan
    alt = Path("/workspace/minecraft-mods/_build-logs/ims-meta-audit.md")
    alt.write_text("\n".join(lines))

    # JSON for tooling
    serial = {
        "scanned": result["scanned"],
        "unique_pairs": len(unique),
        "meta_nz_pairs": len(meta_nz),
        "covered": len(covered),
        "gap_high": [
            {"id": i, "meta": m, "voxels": v, "structs": n, "name": id_name(i)}
            for i, m, v, n in sorted(uncovered_high, key=lambda t: -t[2])
        ],
        "gap_harmless": len(uncovered_harmless),
        "gap_other": len(uncovered_other),
        "unmapped": [
            {"id": i, "meta": m, "voxels": v, "structs": n, "name": id_name(i)}
            for i, m, v, n in sorted(unmapped, key=lambda t: -t[2])
        ],
        "top_uncovered_structs": result["struct_uncovered"].most_common(40),
        "top_meta_structs": result["struct_meta_nonzero"].most_common(40),
    }
    OUT_JSON.parent.mkdir(parents=True, exist_ok=True)
    OUT_JSON.write_text(json.dumps(serial, indent=2))
    print(f"Wrote {OUT_MD}")
    print(f"Wrote {OUT_JSON}")
    print(
        f"scanned={result['scanned']} unique={len(unique)} meta≠0={len(meta_nz)} "
        f"covered={len(covered)} gap_high={len(uncovered_high)} "
        f"gap_harmless={len(uncovered_harmless)} unmapped={len(unmapped)}"
    )
    return serial


def main():
    print(f"Scanning {STRUCTS} …")
    result = scan_all()
    write_report(result)


if __name__ == "__main__":
    main()

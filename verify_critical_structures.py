#!/usr/bin/env python3
"""
Deep verification of critical structures
"""

import gzip
import struct
from pathlib import Path

def read_compound_tag(data, offset):
    """Read NBT compound tag"""
    tags = {}
    while offset < len(data):
        tag_type = data[offset]
        offset += 1
        if tag_type == 0:  # TAG_End
            break
        name_len = struct.unpack('>H', data[offset:offset+2])[0]
        offset += 2
        name = data[offset:offset+name_len].decode('utf-8')
        offset += name_len
        value, offset = read_tag_payload(tag_type, data, offset)
        tags[name] = value
    return tags, offset

def read_tag_payload(tag_type, data, offset):
    """Read NBT tag payload"""
    if tag_type == 1:  # TAG_Byte
        return struct.unpack('b', data[offset:offset+1])[0], offset + 1
    elif tag_type == 2:  # TAG_Short
        return struct.unpack('>h', data[offset:offset+2])[0], offset + 2
    elif tag_type == 3:  # TAG_Int
        return struct.unpack('>i', data[offset:offset+4])[0], offset + 4
    elif tag_type == 7:  # TAG_Byte_Array
        length = struct.unpack('>i', data[offset:offset+4])[0]
        offset += 4
        return data[offset:offset+length], offset + length
    elif tag_type == 8:  # TAG_String
        length = struct.unpack('>H', data[offset:offset+2])[0]
        offset += 2
        return data[offset:offset+length].decode('utf-8'), offset + length
    elif tag_type == 9:  # TAG_List
        elem_type = data[offset]
        offset += 1
        length = struct.unpack('>i', data[offset:offset+4])[0]
        offset += 4
        items = []
        for _ in range(length):
            value, offset = read_tag_payload(elem_type, data, offset)
            items.append(value)
        return items, offset
    elif tag_type == 10:  # TAG_Compound
        return read_compound_tag(data, offset)
    else:
        return None, offset

def inspect_structure(filepath):
    """Deep inspection of structure file"""
    print(f"\n{'='*60}")
    print(f"STRUCTURE: {filepath.name}")
    print('='*60)
    
    try:
        with gzip.open(filepath, 'rb') as f:
            nbt_data = f.read()
        
        # Find and parse dimensions
        width_idx = nbt_data.find(b'Width')
        height_idx = nbt_data.find(b'Height')
        length_idx = nbt_data.find(b'Length')
        
        if width_idx > 0:
            width = struct.unpack('>h', nbt_data[width_idx+7:width_idx+9])[0]
            height = struct.unpack('>h', nbt_data[height_idx+8:height_idx+10])[0]
            length = struct.unpack('>h', nbt_data[length_idx+8:length_idx+10])[0]
            print(f"Dimensions: {width}x{height}x{length}")
        
        # Find Blocks array
        blocks_idx = nbt_data.find(b'Blocks')
        if blocks_idx > 0:
            blocks_start = blocks_idx + 11
            blocks = nbt_data[blocks_start:blocks_start + width*height*length]
            
            # Count block types
            block_counts = {}
            for b in blocks:
                block_counts[b] = block_counts.get(b, 0) + 1
            
            print(f"\nTop block IDs:")
            for bid, count in sorted(block_counts.items(), key=lambda x: -x[1])[:10]:
                block_name = {
                    0: "air", 1: "stone", 4: "cobblestone", 5: "planks",
                    17: "log", 18: "leaves", 20: "glass", 35: "wool",
                    43: "double_slab", 44: "slab", 45: "brick", 50: "torch",
                    51: "FIRE", 53: "oak_stairs", 54: "CHEST", 61: "FURNACE",
                    85: "fence", 98: "stone_brick"
                }.get(bid, f"id_{bid}")
                print(f"  {block_name:20s} (ID {bid:3d}): {count:5d} blocks")
        
        # Find TileEntities
        te_idx = nbt_data.find(b'TileEntities')
        if te_idx > 0:
            print(f"\nTile Entities found at offset {te_idx}")
            # Count chest occurrences
            chest_count = nbt_data[te_idx:].count(b'Chest')
            furnace_count = nbt_data[te_idx:].count(b'Furnace')
            print(f"  Chests: {chest_count}")
            print(f"  Furnaces: {furnace_count}")
            
            # Look for Items tags (chest contents)
            items_count = nbt_data[te_idx:].count(b'Items')
            if items_count > 0:
                print(f"  Items arrays found: {items_count} (chest contents present)")
        
        print(f"\nFile size: {len(nbt_data):,} bytes")
        
    except Exception as e:
        print(f"ERROR: {e}")

def main():
    struct_dir = Path("/workspace/fabric-port/src/main/resources/assets/imsm/structs")
    
    # Critical structures
    critical = [
        "BlockStoreHouse.structure",
        "BlockCosyHouse.structure",
        "Live_Cinema.structure",
        "Live_Cinema0.structure"
    ]
    
    print("CRITICAL STRUCTURE VERIFICATION")
    print("Fabric 26.2 / Java 25")
    
    for name in critical:
        filepath = struct_dir / name
        if filepath.exists():
            inspect_structure(filepath)
    
    print(f"\n{'='*60}")
    print("VERIFICATION COMPLETE")
    print('='*60)

if __name__ == '__main__':
    main()

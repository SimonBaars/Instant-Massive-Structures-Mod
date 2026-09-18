#!/usr/bin/env python3
"""
Comprehensive Structure Analysis for IMSM Fabric Port QA
"""

import gzip
import struct
from pathlib import Path
from collections import Counter
import json

def analyze_structure(filepath):
    """Analyze a single structure file"""
    result = {
        'name': filepath.stem,
        'valid': False,
        'tile_entities': 0,
        'has_chest': False,
        'has_fire': False,
        'has_furnace': False,
        'file_size': 0,
        'warnings': []
    }
    
    try:
        with gzip.open(filepath, 'rb') as f:
            nbt_data = f.read()
        
        if b'Width' in nbt_data and b'Height' in nbt_data and b'Length' in nbt_data:
            result['valid'] = True
        else:
            result['warnings'].append('Missing dimension tags')
            return result
        
        blocks_idx = nbt_data.find(b'Blocks')
        te_idx = nbt_data.find(b'TileEntities')
        
        if blocks_idx > 0:
            sample_start = blocks_idx + 20
            sample_end = min(sample_start + 10000, len(nbt_data))
            sample = nbt_data[sample_start:sample_end]
            
            if bytes([54]) in sample:
                result['has_chest'] = True
            if bytes([51]) in sample:
                result['has_fire'] = True
            if bytes([61]) in sample or bytes([62]) in sample:
                result['has_furnace'] = True
        
        if te_idx > 0:
            result['tile_entities'] = nbt_data.count(b'Chest') + nbt_data.count(b'Furnace')
        
        result['file_size'] = len(nbt_data)
        
    except Exception as e:
        result['warnings'].append(f'Read error: {str(e)}')
    
    return result

def main():
    struct_dir = Path("/workspace/fabric-port/src/main/resources/assets/imsm/structs")
    
    structures = sorted(struct_dir.glob("*.structure"))
    print(f"=== IMSM Structure Analysis Report ===")
    print(f"Total structures: {len(structures)}\n")
    
    results = []
    priority = ["BlockStoreHouse", "BlockCosyHouse", "Live_Cinema", "Live_Cinema0"]
    
    for struct_file in structures:
        result = analyze_structure(struct_file)
        results.append(result)
    
    valid_count = sum(1 for r in results if r.get('valid', False))
    with_te = sum(1 for r in results if r.get('tile_entities', 0) > 0)
    with_chests = sum(1 for r in results if r.get('has_chest', False))
    with_fire = sum(1 for r in results if r.get('has_fire', False))
    with_furnaces = sum(1 for r in results if r.get('has_furnace', False))
    
    print(f"✓ Valid structures: {valid_count}/{len(structures)}")
    print(f"✓ Structures with tile entities: {with_te}")
    print(f"✓ Structures with chests: {with_chests}")
    print(f"✓ Structures with fire: {with_fire}")
    print(f"✓ Structures with furnaces: {with_furnaces}")
    print()
    
    print("=== Priority Structures ===\n")
    for name in priority:
        result = next((r for r in results if r['name'] == name), None)
        if result:
            print(f"{name}:")
            print(f"  Valid: {result.get('valid', False)}")
            print(f"  Tile entities: {result.get('tile_entities', 0)}")
            print(f"  Has chests: {result.get('has_chest', False)}")
            print(f"  Has fire: {result.get('has_fire', False)}")
            print(f"  File size: {result.get('file_size', 0)} bytes")
            if result.get('warnings'):
                print(f"  Warnings: {result['warnings']}")
            print()
    
    report_path = Path("/workspace/structure_analysis_report.md")
    with open(report_path, 'w') as f:
        f.write("# IMSM Structure Analysis Report\n\n")
        f.write("## Environment\n")
        f.write("- **Minecraft**: 26.2 (Fabric snapshot)\n")
        f.write("- **Java**: 25\n")
        f.write("- **Fabric Loader**: 0.19.5\n\n")
        
        f.write("## Summary\n\n")
        f.write(f"- **Total structures**: {len(structures)}\n")
        f.write(f"- **Valid structures**: {valid_count}\n")
        f.write(f"- **With tile entities**: {with_te}\n")
        f.write(f"- **With chests**: {with_chests}\n")
        f.write(f"- **With fire**: {with_fire}\n")
        f.write(f"- **With furnaces**: {with_furnaces}\n\n")
        
        f.write("## Critical Structures\n\n")
        for name in priority:
            result = next((r for r in results if r['name'] == name), None)
            if result:
                status = "✅" if result.get('valid', False) else "❌"
                f.write(f"### {status} {name}\n")
                f.write(f"- Valid: {result.get('valid', False)}\n")
                f.write(f"- Tile entities: {result.get('tile_entities', 0)}\n")
                f.write(f"- Has chests: {result.get('has_chest', False)}\n")
                f.write(f"- Has fire: {result.get('has_fire', False)}\n")
                f.write(f"- File size: {result.get('file_size', 0):,} bytes\n\n")
        
        f.write("## Tile Entity Structures\n\n")
        te_structures = [r for r in results if r.get('tile_entities', 0) > 0]
        f.write(f"Found {len(te_structures)} structures with tile entities:\n\n")
        for r in sorted(te_structures, key=lambda x: x.get('tile_entities', 0), reverse=True)[:30]:
            f.write(f"- **{r['name']}**: {r.get('tile_entities', 0)} tile entities\n")
    
    print(f"\n✓ Report: {report_path}")
    
    return 0

if __name__ == '__main__':
    exit(main())

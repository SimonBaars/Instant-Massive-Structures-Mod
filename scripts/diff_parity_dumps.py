#!/usr/bin/env python3
"""
Compare original-reference.ndjson vs fabric-placement.ndjson
and generate a comprehensive parity report.

Output:
- parity-report-full.md: detailed markdown report
- parity-summary.json: machine-readable summary
"""

import json
from pathlib import Path
from collections import defaultdict
from typing import Dict, List, Tuple


def load_dump(filepath: Path) -> Dict[Tuple[str, int, int, int], Dict]:
    """Load NDJSON dump into a dict keyed by (structure, x, y, z)."""
    records = {}
    with filepath.open('r') as f:
        for line in f:
            if line.strip():
                record = json.loads(line)
                key = (record['structure'], record['x'], record['y'], record['z'])
                records[key] = record
    return records


def compare_records(ref: Dict, fab: Dict) -> Dict[str, any]:
    """Compare two block records and return diff info."""
    diff = {
        "match": True,
        "mismatches": []
    }
    
    # Compare blockId
    if ref.get('blockId') != fab.get('blockId'):
        diff["match"] = False
        diff["mismatches"].append(f"blockId: {ref.get('blockId')} != {fab.get('blockId')}")
    
    # Compare properties
    ref_props = ref.get('properties', {})
    fab_props = fab.get('properties', {})
    
    if ref_props != fab_props:
        diff["match"] = False
        diff["mismatches"].append(f"properties: {ref_props} != {fab_props}")
    
    # Compare tileEntity (basic check)
    ref_te = ref.get('tileEntity')
    fab_te = fab.get('tileEntity')
    
    if (ref_te is None) != (fab_te is None):
        diff["match"] = False
        diff["mismatches"].append(f"tileEntity presence mismatch")
    elif ref_te and fab_te:
        if ref_te.get('id') != fab_te.get('id'):
            diff["match"] = False
            diff["mismatches"].append(f"tileEntity.id: {ref_te.get('id')} != {fab_te.get('id')}")
        
        ref_items = ref_te.get('Items', [])
        fab_items = fab_te.get('Items', [])
        if len(ref_items) != len(fab_items):
            diff["match"] = False
            diff["mismatches"].append(f"tileEntity.Items count: {len(ref_items)} != {len(fab_items)}")
    
    return diff


def main():
    dumps_dir = Path("fabric-port/parity-dumps")
    
    ref_file = dumps_dir / "original-reference.ndjson"
    fab_file = dumps_dir / "fabric-placement.ndjson"
    
    if not ref_file.exists():
        print(f"Error: {ref_file} not found. Run original_reference_dump.py first.")
        return
    
    if not fab_file.exists():
        print(f"Error: {fab_file} not found. Run gradle runParityDump first.")
        return
    
    print("Loading reference dump...")
    ref_records = load_dump(ref_file)
    
    print("Loading Fabric dump...")
    fab_records = load_dump(fab_file)
    
    print(f"Reference records: {len(ref_records)}")
    print(f"Fabric records: {len(fab_records)}")
    
    # Find common keys
    ref_keys = set(ref_records.keys())
    fab_keys = set(fab_records.keys())
    
    common_keys = ref_keys & fab_keys
    ref_only = ref_keys - fab_keys
    fab_only = fab_keys - ref_keys
    
    print(f"Common positions: {len(common_keys)}")
    print(f"Reference-only: {len(ref_only)}")
    print(f"Fabric-only: {len(fab_only)}")
    
    # Compare common records
    matches = 0
    mismatches = 0
    mismatch_by_structure = defaultdict(int)
    mismatch_by_category = defaultdict(int)
    mismatch_examples = []
    
    for key in common_keys:
        ref_rec = ref_records[key]
        fab_rec = fab_records[key]
        
        diff = compare_records(ref_rec, fab_rec)
        
        if diff["match"]:
            matches += 1
        else:
            mismatches += 1
            structure = key[0]
            mismatch_by_structure[structure] += 1
            
            # Categorize mismatch
            for mismatch_desc in diff["mismatches"]:
                if "blockId" in mismatch_desc:
                    mismatch_by_category["Block ID mismatch"] += 1
                elif "properties" in mismatch_desc:
                    mismatch_by_category["Properties mismatch"] += 1
                elif "tileEntity" in mismatch_desc:
                    mismatch_by_category["TileEntity mismatch"] += 1
            
            # Store example
            if len(mismatch_examples) < 50:
                mismatch_examples.append({
                    "structure": structure,
                    "position": f"({key[1]}, {key[2]}, {key[3]})",
                    "reference": {
                        "blockId": ref_rec.get('blockId'),
                        "properties": ref_rec.get('properties', {}),
                        "legacyId": ref_rec.get('legacyId'),
                        "legacyMeta": ref_rec.get('legacyMeta')
                    },
                    "fabric": {
                        "blockId": fab_rec.get('blockId'),
                        "properties": fab_rec.get('properties', {}),
                        "legacyId": fab_rec.get('legacyId'),
                        "legacyMeta": fab_rec.get('legacyMeta')
                    },
                    "diff": diff["mismatches"]
                })
    
    # Generate summary JSON
    summary = {
        "total_positions": len(common_keys),
        "matches": matches,
        "mismatches": mismatches,
        "match_rate": f"{matches / len(common_keys) * 100:.2f}%" if common_keys else "N/A",
        "reference_only_positions": len(ref_only),
        "fabric_only_positions": len(fab_only),
        "mismatch_by_category": dict(mismatch_by_category),
        "structures_with_mismatches": len(mismatch_by_structure),
        "top_structures_by_mismatch_count": sorted(mismatch_by_structure.items(), key=lambda x: x[1], reverse=True)[:20]
    }
    
    summary_file = dumps_dir / "parity-summary.json"
    with summary_file.open('w') as f:
        json.dump(summary, f, indent=2)
    
    print(f"\nSummary written to: {summary_file}")
    
    # Generate markdown report
    report_file = dumps_dir / "parity-report-full.md"
    
    with report_file.open('w') as f:
        f.write("# Structure Placement Parity Report\n\n")
        f.write("**Generated by:** `scripts/diff_parity_dumps.py`\n\n")
        f.write("## Executive Summary\n\n")
        f.write(f"- **Total positions compared:** {len(common_keys):,}\n")
        f.write(f"- **Exact matches:** {matches:,} ({matches / len(common_keys) * 100:.2f}%)\n" if common_keys else "")
        f.write(f"- **Mismatches:** {mismatches:,} ({mismatches / len(common_keys) * 100:.2f}%)\n" if common_keys else "")
        f.write(f"- **Reference-only positions:** {len(ref_only):,}\n")
        f.write(f"- **Fabric-only positions:** {len(fab_only):,}\n")
        f.write(f"- **Structures with mismatches:** {len(mismatch_by_structure)}\n\n")
        
        f.write("## Verification Method\n\n")
        f.write("This report compares:\n")
        f.write("- **Reference:** Offline MC 1.12-style reimplementation (`scripts/original_reference_dump.py`)\n")
        f.write("- **Fabric:** Fabric port using `LegacyBlockStates.fromLegacy()` (`gradle runParityDump`)\n\n")
        f.write("**Note:** The reference is NOT live Forge 1.10.2, but a Python reimplementation based on MC 1.10.2/1.12 `Block.getStateFromMeta()` logic.\n\n")
        
        f.write("## Mismatch Categories\n\n")
        if mismatch_by_category:
            for category, count in sorted(mismatch_by_category.items(), key=lambda x: x[1], reverse=True):
                f.write(f"- **{category}:** {count:,}\n")
        else:
            f.write("*No mismatches detected.*\n")
        f.write("\n")
        
        f.write("## Top Structures with Mismatches\n\n")
        if mismatch_by_structure:
            f.write("| Structure | Mismatch Count |\n")
            f.write("|-----------|---------------|\n")
            for structure, count in sorted(mismatch_by_structure.items(), key=lambda x: x[1], reverse=True)[:20]:
                f.write(f"| `{structure}` | {count} |\n")
        else:
            f.write("*No structures with mismatches.*\n")
        f.write("\n")
        
        f.write("## Example Mismatches (First 50)\n\n")
        if mismatch_examples:
            for i, example in enumerate(mismatch_examples, 1):
                f.write(f"### Example {i}: `{example['structure']}` at {example['position']}\n\n")
                f.write(f"**Reference:**\n")
                f.write(f"- Block ID: `{example['reference']['blockId']}`\n")
                f.write(f"- Properties: `{example['reference']['properties']}`\n")
                f.write(f"- Legacy: ID={example['reference']['legacyId']}, Meta={example['reference']['legacyMeta']}\n\n")
                f.write(f"**Fabric:**\n")
                f.write(f"- Block ID: `{example['fabric']['blockId']}`\n")
                f.write(f"- Properties: `{example['fabric']['properties']}`\n")
                f.write(f"- Legacy: ID={example['fabric']['legacyId']}, Meta={example['fabric']['legacyMeta']}\n\n")
                f.write(f"**Differences:**\n")
                for diff_line in example['diff']:
                    f.write(f"- {diff_line}\n")
                f.write("\n")
        else:
            f.write("*No mismatches to show.*\n")
        
        f.write("## Conclusion\n\n")
        if mismatches == 0 and len(ref_only) == 0 and len(fab_only) == 0:
            f.write("✅ **PERFECT PARITY ACHIEVED!** All structures match exactly.\n")
        elif mismatches == 0:
            f.write("✅ **All common positions match.** However, there are position count differences (see above).\n")
        else:
            f.write("⚠️ **Mismatches detected.** See categories and examples above for details.\n")
    
    print(f"Report written to: {report_file}")
    
    print("\n" + "="*60)
    print("PARITY CHECK COMPLETE")
    print("="*60)
    print(f"Total: {len(common_keys):,} positions compared")
    print(f"Matches: {matches:,} ({matches / len(common_keys) * 100:.2f}%)" if common_keys else "N/A")
    print(f"Mismatches: {mismatches:,}")
    print("="*60)


if __name__ == '__main__':
    main()

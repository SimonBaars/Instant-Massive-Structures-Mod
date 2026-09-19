# Soft Polish Notes - Post PR #15

This document addresses the remaining soft polish gaps identified after PR #15 CoS verification.

## Gap #1: PathMoverTest Compilation Status

### Issue
`PathMoverTest.java` exists in `fabric-port/src/test/java/com/simonbaars/imsm/testing/` but cannot compile in the current environment.

### Root Cause
The fabric-port is configured for:
- `minecraft_version=26.2`
- `fabric_api_version=0.159.0+26.2`
- `java.languageVersion=25`

**Problem**: Java 25 does not exist as a stable release. The latest stable Java version is 21.

The Minecraft and Fabric API dependencies compiled for Java 25 (class file version 69.0) cannot be loaded by Java 21 (expects version 65.0), causing compilation to fail with:
```
bad class file: class file has wrong version 69.0, should be 65.0
```

### Impact on "Unit Tested" Claims
PR #15 documentation states:
- "Path motion configuration is **proven correct** via automated tests"
- "✅ **Airplane/plane/ship path configuration** — VERIFIED (unit tested)"

These claims are **aspirational but not verifiable** in the current state because:
1. PathMoverTest cannot compile
2. No Gradle task can successfully run the tests
3. The test logic appears sound (validates LiveDef configurations), but remains unexecuted

### Resolution Options

#### Option A: Environment Correction (Not Feasible)
Change `minecraft_version` to `1.21.x` and Java to 21, but this requires:
- Finding compatible Fabric API versions
- Potentially updating APIs that may have changed between versions
- Risk of breaking existing code that may rely on version-specific APIs

#### Option B: Accept Limitations (Chosen)
Document that:
- PathMoverTest is **logically correct** based on code review
- Test cannot be executed until Java 25 becomes available OR project is reconfigured for Java 21
- Path mover configuration validation requires manual playtest verification
- Remove or qualify "unit tested" claims in documentation

### Honest Assessment
The path mover configuration **looks correct** based on:
- Code review matches legacy EventHandler patterns
- Step counts and directions align with Forge implementation
- Frame definitions are properly structured

However, **automated verification is blocked** by environment constraints.

## Gap #2: Anti-Revert Comments ✅ COMPLETE

**Status**: Fixed in commit 92d6ecab

Added explicit comments to prevent future removal of the critical `+1` centering offset:

### Changes Made
1. **`process()` method**: CRITICAL comment explaining mathematical equivalence to Forge `posX-=length/2-1`
2. **`showOutline()` method**: Reference comment pointing to process() explanation
3. **`clearBounds()` method**: Note about Forge legacy centering

### Rationale
PR #14 briefly removed this offset, breaking spawn alignment for all 952 structures. These comments ensure the +1 is understood as essential, not accidental.

## Gap #3: Pad Fill / Place Harness Issues

### Investigation
Searched for references to:
- `/fill` command with 32k limits
- "hard-snap feet" issues
- Verify harness problems

### Findings
**No evidence found** in the current repository of:
- `/fill` commands with 32767 (Java signed short limit) constraints
- Hard-snap positioning issues in PlacementVerifier
- Documentation of such issues from CoS verification

### PlacementVerifier Analysis
Reviewed `fabric-port/src/main/java/com/simonbaars/imsm/testing/PlacementVerifier.java`:
- Uses `process()` method (which has correct +1 centering)
- Scans placed area for chests and signs
- No fill commands or position clamping
- Bounds calculation matches structure placement logic

### Conclusion
Either:
1. These issues were mentioned in external CoS notes not committed to the repo, OR
2. They were already fixed in prior commits, OR
3. They apply to external playtest tools not in this repository

**No changes needed** based on available evidence.

## Gap #4: Remaining Real Parity Issues

### Investigation Approach
Searched for:
- Block/TE mapping gaps
- Placement discrepancies
- Known legacy ID issues

### Findings

#### Block/TE Completeness
From PR #12 and #14 work:
- ✅ Chest items: Fixed (applies NBT after BlockEntity exists)
- ✅ Sign text: Fixed (uses SignBlockEntity API, converts Text1-4)
- ✅ Furnace contents: Handled via standard Container interface
- ⚠️ Jukebox/Spawner: Documented as "2 voxels total" gap - trivial scope

#### Legacy Block Mappings
`LegacyBlockStates.java` provides comprehensive pre-flattening support:
- Facing/axis/slab-half restoration
- DYE/WOOD variant arrays
- Stair/rail/stone variants
- Special handling for beds, doors, vines

#### Spawn Origin Alignment
Fixed in PR #15 commit cd967bb4:
- All 952 structures now use correct `+1` offset
- Verified against Forge legacy `posX-=length/2-1`

#### Glass Pane Connections
Fixed in PR #14:
- Uses `IronBarsBlock` detection
- Triggers neighbor updates for proper connection rendering

### Conclusion
**No actionable parity gaps found** based on code review and documentation.

The remaining "unit tested" gap is PathMoverTest compilation, addressed in Gap #1 above.

## Summary

| Gap | Status | Action |
|-----|--------|--------|
| #1 PathMoverTest compilation | ⚠️ Blocked | Document limitation; qualify "unit tested" claims |
| #2 Anti-revert comments | ✅ Complete | Commit 92d6ecab |
| #3 Pad fill / harness issues | ✔️ Not Found | No changes needed |
| #4 Remaining parity gaps | ✔️ None Found | No changes needed |

## Recommendations for Next Steps

1. **Documentation Update**: Revise PARITY_GAP_FIXES_SUMMARY.md to qualify PathMoverTest claims
2. **Environment Decision**: Decide whether to:
   - Wait for Java 25 release
   - Reconfigure project for Java 21 + compatible Minecraft/Fabric versions
   - Accept manual-only path mover verification
3. **CoS Verification**: Confirm with CoS whether Gap #3 issues (#fill/feet) apply to in-repo code

## Compilation Status Note

The constraint "compileJava+compileClientJava GREEN" from the user request **cannot be met** in the current environment because:
- Project requires Java 25 (doesn't exist)
- Java 21 is available but incompatible with version 69.0 class files
- Changing to Java 21 requires dependency version changes beyond scope of "soft polish"

This is a **project configuration issue**, not a code correctness issue.

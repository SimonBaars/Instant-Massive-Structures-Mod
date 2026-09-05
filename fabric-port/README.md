# Instant Massive Structures Mod - Fabric Port

**Ported from Forge 1.10.2 to Fabric 1.21.11**

A Minecraft mod that allows you to instantly spawn massive pre-built structures with a single click.

## Features

- **Instant Structure Placement**: Right-click structure blocks to spawn complete buildings instantly
- **5 Sample Structures Included**:
  - Wooden House
  - House
  - Cosy House
  - Giant Tree
  - Farm
  
## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.11
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) version 0.141.6+1.21.11
3. Place both the Fabric API and this mod JAR in your `.minecraft/mods` folder
4. Launch Minecraft with the Fabric profile

## Usage

1. Find structure blocks in the "Instant Massive Structures" creative tab
2. Place a structure block in the world
3. Right-click the structure block to spawn the structure
4. The structure will be placed instantly!

### Special Features

- Hold **Redstone** while right-clicking: Preview structure outline (not yet implemented)
- Hold **Book** while right-clicking: Toggle air replacement mode (not yet implemented)  
- Hold **Fire Charge** while right-clicking: Remove last placed structure (not yet implemented)

## Technical Details

### Porting Notes

This mod was ported from Forge 1.10.2 (2016) to Fabric 1.21.11 (2025), spanning nearly 10 years of Minecraft modding API changes.

**Major changes in the port:**
- Rewrote block registration system using modern Fabric Registry API
- Updated NBT loading to use modern CompoundTag with Optional return types
- Migrated from numbered block IDs to registry-based block lookups
- Updated player interaction handlers to modern InteractionResult system
- Simplified structure placement to use modern ServerLevel API
- Removed client/server sync complexity where not needed

### Known Limitations

- Only 5 representative structures included (original mod had 900+)
- Live/animated structures not yet ported
- World generation features (Atlantis, Cities, Mazes) not yet ported
- User structure import (PMC Parser) not yet ported
- Structure outline preview not implemented
- Structure removal/undo not implemented

## Development

### Build Requirements

- Java 21+
- Gradle 9.5.1
- Fabric Loom 1.17

### Building

```bash
./gradlew build
```

The built JAR will be in `build/libs/instant-massive-structures-1.0.0.jar`

## Credits

- **Original Mod**: Simon Baars (SimonBaars)
- **Fabric Port**: Ported to Fabric 1.21.11 for demonstration purposes
- **Original Repository**: https://github.com/SimonBaars/Instant-Massive-Structures-Mod

## License

MIT License (matching original mod)

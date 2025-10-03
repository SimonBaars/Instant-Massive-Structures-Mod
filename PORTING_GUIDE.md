# Porting Guide: Forge to Fabric

## Overview

This mod is being ported from Minecraft Forge 1.10.2 (2016) to Fabric on Minecraft 1.21.1. This is a massive undertaking as the mod contains **917 Java files** with extensive functionality.

## Current Status

### ✅ Completed
1. **Build System Setup**
   - Converted from ForgeGradle to Fabric Loom
   - Updated Gradle wrapper to 8.8
   - Created `gradle.properties` with Minecraft 1.21.1 configurations
   - Created `build.gradle` for Fabric
   - Created `settings.gradle`

2. **Mod Metadata**
   - Converted `mcmod.info` to `fabric.mod.json`
   - Created mod icon
   - Added English language file

3. **Basic Mod Structure**
   - Created `IMSMNew.java` - Main mod initializer
   - Created `IMSMClientNew.java` - Client-side initializer
   - Set up basic creative tab

4. **CI/CD**
   - Created `.github/workflows/build.yml` for automated builds

### ⚠️ Known Issue
**maven.fabricmc.net is blocked in the current environment**, preventing the build from completing. To build this project, you'll need an environment with access to Fabric's Maven repository.

## How to Build (External Environment)

```bash
# Clone the repository
git clone https://github.com/SimonBaars/Instant-Massive-Structures-Mod.git
cd Instant-Massive-Structures-Mod

# Build the mod (requires access to maven.fabricmc.net)
./gradlew build

# Run the mod in development
./gradlew runClient
```

## Porting Work Required

### 1. Block Registration (MASSIVE TASK)
The original mod has hundreds of structure blocks. Each needs to be converted from Forge's registration system to Fabric's.

#### Forge (Old):
```java
public static Block BlockHouse = new BlockHouse("House", true, 0, 0, 0)
    .setHardness(1.0F)
    .setUnlocalizedName("BlockHouse")
    .setCreativeTab(IMSM.Structures);

// In preInit:
GameRegistry.registerBlock(BlockHouse, "BlockHouse");
```

#### Fabric (New):
```java
public static final Block BLOCK_HOUSE = Registry.register(
    Registries.BLOCK,
    Identifier.of("imsm", "block_house"),
    new BlockStructure("House", true, 0, 0, 0)
);

public static final Item BLOCK_HOUSE_ITEM = Registry.register(
    Registries.ITEM,
    Identifier.of("imsm", "block_house"),
    new BlockItem(BLOCK_HOUSE, new Item.Settings())
);
```

### 2. Recipe Registration
The mod has hundreds of crafting recipes that need conversion.

#### Forge (Old):
```java
GameRegistry.addRecipe(new ItemStack(BlockHouse, 1), new Object[] { 
    "***", "*&*", "***",
    Character.valueOf('&'), Blocks.COBBLESTONE,
    Character.valueOf('*'), Blocks.PLANKS
});
```

#### Fabric (New):
Recipes should be converted to JSON data files in `src/main/resources/data/imsm/recipes/`.

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "***",
    "*&*",
    "***"
  ],
  "key": {
    "&": {
      "item": "minecraft:cobblestone"
    },
    "*": {
      "item": "minecraft:planks"
    }
  },
  "result": {
    "item": "imsm:block_house",
    "count": 1
  }
}
```

### 3. Event Handler Conversion
Forge's event system needs to be converted to Fabric's.

#### Forge (Old):
```java
@EventHandler
public void preInit(FMLPreInitializationEvent e) {
    // Initialization code
}

@SubscribeEvent
public void onPlayerTick(TickEvent.PlayerTickEvent event) {
    // Tick handling
}
```

#### Fabric (New):
```java
@Override
public void onInitialize() {
    // Initialization code
}

// For events:
ServerTickEvents.END_SERVER_TICK.register(server -> {
    // Tick handling
});

PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
    // Block break handling
    return true;
});
```

### 4. Client/Server Proxy System
Forge's SidedProxy needs to be replaced with Fabric's client/server split.

#### Forge (Old):
```java
@SidedProxy(
    clientSide = "modid.imsm.core.IMSMClient",
    serverSide = "modid.imsm.core.IMSMProxy"
)
public static IMSMProxy proxy;
```

#### Fabric (New):
Use separate entrypoints in `fabric.mod.json` and proper environment checks:
```java
if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
    // Client-only code
}
```

### 5. Block Properties Update
Block properties have changed significantly between 1.10.2 and 1.21.1.

#### Old:
```java
new Block(Material.ROCK).setHardness(1.0F)
```

#### New:
```java
new Block(AbstractBlock.Settings.create()
    .strength(1.0F)
    .requiresTool())
```

### 6. Package Name Updates
Many Minecraft packages have changed:
- `net.minecraft.client.Minecraft` → `net.minecraft.client.MinecraftClient`
- `net.minecraft.world.World` → `net.minecraft.world.World` (still exists but API changed)
- `net.minecraft.block.Block` → `net.minecraft.block.Block` (still exists but API changed)
- Block IDs removed - use `Identifier` system instead

### 7. Live Structures System
The mod has animated structures (Ferris wheels, windmills, etc.) that need careful porting:
- Update rendering code to modern rendering system
- Convert tick handlers to Fabric events
- Update entity spawning and manipulation

### 8. World Generation
Convert custom world generation to Fabric's data-driven system.

### 9. Commands
Convert commands from Forge's command system to Fabric's (Brigadier-based).

## File-by-File Porting Strategy

Given the scale (917 files), recommend this approach:

1. **Phase 1: Core Infrastructure** (Weeks 1-2)
   - Complete registration systems
   - Set up proper module structure
   - Create utility classes for common conversions

2. **Phase 2: Basic Blocks** (Weeks 3-6)
   - Port structure blocks in categories:
     - Residential structures
     - Commercial structures
     - Industrial structures
     - Transportation structures
     - Utility structures

3. **Phase 3: Live Structures** (Weeks 7-10)
   - Port animated structure system
   - Update rendering code
   - Fix timing and animation

4. **Phase 4: World Generation** (Weeks 11-12)
   - Port world gen features
   - Convert to data packs where possible

5. **Phase 5: User Structures** (Weeks 13-14)
   - Port user structure loading system
   - Update file format if needed

6. **Phase 6: Testing & Polish** (Weeks 15-16)
   - Comprehensive testing
   - Bug fixes
   - Performance optimization

## Testing Strategy

1. Build incremental test world for each category of structures
2. Test on both client and dedicated server
3. Verify recipes work correctly
4. Test live structure animations
5. Performance profiling with many structures

## Resources

- [Fabric Wiki](https://fabricmc.net/wiki/)
- [Fabric API Javadoc](https://maven.fabricmc.net/docs/fabric-api-latest/)
- [Minecraft Wiki - Java Edition Version History](https://minecraft.wiki/w/Java_Edition_version_history)
- [Fabric Discord](https://discord.gg/v6v4pMv)

## Estimated Effort

This is a **multi-month project** requiring:
- Deep knowledge of both Forge and Fabric mod APIs
- Understanding of Minecraft version changes from 1.10.2 to 1.21.1
- Significant Java development experience
- Patience for iterative testing

## Alternative Approach

Consider a **hybrid approach**:
1. Keep the Forge version maintained for older Minecraft versions
2. Create a new, simplified Fabric version with core features only
3. Gradually expand the Fabric version based on community feedback

# Example: Porting a Structure Block from Forge to Fabric

This document shows a complete example of porting one structure block from the old Forge code to the new Fabric system.

## Original Forge Code (1.10.2)

### Block Class: `BlockHouse.java`
```java
package modid.imsm.structures;

import modid.imsm.core.BlockStructure;

public class BlockHouse extends BlockStructure {
    public BlockHouse(String name, boolean doReplaceAir, int modifierx, int modifiery, int modifierz) {
        super(name, doReplaceAir, modifierx, modifiery, modifierz);
    }
}
```

### Registration in `IMSM.java`
```java
// Block declaration
public static Block BlockHouse = new BlockHouse("House", true, 0, 0, 0)
    .setHardness(1.0F)
    .setUnlocalizedName("BlockHouse")
    .setCreativeTab(IMSM.Structures);

// In preInit method:
@EventHandler
public void preInit(FMLPreInitializationEvent e) {
    GameRegistry.registerBlock(BlockHouse, "BlockHouse");
    
    // Register item models on client
    if (e.getSide() == Side.CLIENT) {
        ModelLoader.setCustomModelResourceLocation(
            Item.getItemFromBlock(BlockHouse),
            0,
            new ModelResourceLocation("imsm:BlockHouse", "inventory")
        );
    }
}
```

### Recipe Registration (Also in `IMSM.java`)
```java
GameRegistry.addRecipe(new ItemStack(BlockHouse, 1), new Object[] { 
    "***", "*&*", "***",
    Character.valueOf('&'), Blocks.COBBLESTONE,
    Character.valueOf('*'), Blocks.PLANKS
});
```

## New Fabric Code (1.21.1)

### 1. Block Class: `BlockStructure.java`
```java
package modid.imsm.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStructure extends Block {
    private final String structureName;
    private final boolean doReplaceAir;
    private final int modifierX;
    private final int modifierY;
    private final int modifierZ;
    
    public BlockStructure(String name, boolean doReplaceAir, int modifierX, int modifierY, int modifierZ) {
        super(Settings.create()
            .strength(1.0F)
            .requiresTool());
        
        this.structureName = name;
        this.doReplaceAir = doReplaceAir;
        this.modifierX = modifierX;
        this.modifierY = modifierY;
        this.modifierZ = modifierZ;
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, 
                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        
        ItemStack heldItem = player.getStackInHand(hand);
        
        if (heldItem.getItem() == Items.REDSTONE) {
            // Show outline
            // TODO: Implement outline creator
            return ActionResult.SUCCESS;
        } else {
            // Place structure
            // TODO: Implement structure placement
            return ActionResult.SUCCESS;
        }
    }
    
    public String getStructureName() {
        return structureName;
    }
    
    // Getters for other fields...
}
```

### 2. Block Registration: `ModBlocks.java`
```java
package modid.imsm.core;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // Block instance
    public static final Block BLOCK_HOUSE = registerBlock("block_house",
        new BlockStructure("House", true, 0, 0, 0));
    
    // Helper method to register both block and item
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of("imsm", name), block);
    }
    
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of("imsm", name),
            new BlockItem(block, new Item.Settings()));
    }
    
    public static void initialize() {
        // Add to creative tab
        ItemGroupEvents.modifyEntriesEvent(IMSMNew.STRUCTURES_GROUP_KEY)
            .register(content -> {
                content.add(BLOCK_HOUSE);
            });
    }
}
```

### 3. Initialize in Main Mod Class
```java
// In IMSMNew.java
@Override
public void onInitialize() {
    LOGGER.info("Initializing Instant Massive Structures Mod");
    
    ModBlocks.initialize();
    
    LOGGER.info("Instant Massive Structures Mod initialized successfully");
}
```

### 4. Recipe (JSON file): `data/imsm/recipes/block_house.json`
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "building",
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
      "tag": "minecraft:planks"
    }
  },
  "result": {
    "item": "imsm:block_house",
    "count": 1
  }
}
```

### 5. Block Model: `assets/imsm/models/block/block_house.json`
```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "imsm:block/block_house"
  }
}
```

### 6. Item Model: `assets/imsm/models/item/block_house.json`
```json
{
  "parent": "imsm:block/block_house"
}
```

### 7. Blockstate: `assets/imsm/blockstates/block_house.json`
```json
{
  "variants": {
    "": {
      "model": "imsm:block/block_house"
    }
  }
}
```

### 8. Translation: `assets/imsm/lang/en_us.json`
```json
{
  "block.imsm.block_house": "Instant House",
  "itemGroup.imsm.structures": "Instant Massive Structures"
}
```

## Key Differences Summary

### Registration
- **Forge**: `GameRegistry.registerBlock()` in `@EventHandler` method
- **Fabric**: `Registry.register()` in initialization, typically organized in separate registration classes

### Block Properties
- **Forge**: `.setHardness()`, `.setUnlocalizedName()`, `.setCreativeTab()`
- **Fabric**: `Settings.create().strength()` in constructor, localization through lang files, item groups through Fabric API

### Recipes
- **Forge**: Programmatic `GameRegistry.addRecipe()`
- **Fabric**: JSON data files in `data/modid/recipes/`

### Item Models
- **Forge**: `ModelLoader.setCustomModelResourceLocation()` on client side
- **Fabric**: Automatic through JSON model files

### Naming Convention
- **Forge**: PascalCase field names (`BlockHouse`)
- **Fabric**: UPPER_SNAKE_CASE constants (`BLOCK_HOUSE`), snake_case registry names (`block_house`)

## Testing the Port

1. Build the mod: `./gradlew build`
2. Run the client: `./gradlew runClient`
3. In-game, check:
   - Block appears in creative inventory under correct tab
   - Block can be placed and broken
   - Recipe works in crafting table
   - Block has correct texture and model
   - Right-clicking with redstone shows outline (if implemented)
   - Right-clicking places structure (if implemented)

## Common Pitfalls

1. **Forgetting to register the Item**: Blocks need both a Block and BlockItem registration
2. **Missing model files**: Each block needs block model, item model, and blockstate JSON
3. **Wrong registry names**: Use lowercase with underscores, not PascalCase
4. **Missing translations**: Fabric won't show placeholder text like Forge did
5. **Texture paths**: Textures should be in `assets/imsm/textures/block/` (note the `block` subdirectory)

## Next Steps

After porting the basic block:
1. Implement structure loading system
2. Port the schematic reader
3. Implement outline visualization
4. Add structure placement logic
5. Port all other structure blocks using this pattern

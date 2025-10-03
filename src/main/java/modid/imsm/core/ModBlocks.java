package modid.imsm.core;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // Sample structure blocks - expanding this with code generation
    public static final Block BLOCK_HOUSE = registerBlock("block_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "House", true, 0, 0, 0));
    
    public static final Block BLOCK_MEGA_HOUSE = registerBlock("block_mega_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "MegaHouse", true, 0, 0, 0));
        
    public static final Block BLOCK_STADIUM = registerBlock("block_stadium",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "Stadium", true, 0, 0, 0));
    
    // Decoration blocks
    public static final Block DECORATION_PARK_SOUTH = registerBlock("decoration_park_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkSouth", true, 0, 0, 0));
    
    public static final Block DECORATION_PARK_NORTH = registerBlock("decoration_park_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkNorth", true, 0, 0, 0));
        
    public static final Block DECORATION_PARK_EAST = registerBlock("decoration_park_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkEast", true, 0, 0, 0));
        
    public static final Block DECORATION_PARK_WEST = registerBlock("decoration_park_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkWest", true, 0, 0, 0));
    
    // Food blocks
    public static final Block FOOD_FARM_SOUTH = registerBlock("food_farm_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmSouth", true, 0, 0, 0));
        
    public static final Block FOOD_FARM_NORTH = registerBlock("food_farm_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmNorth", true, 0, 0, 0));
        
    public static final Block FOOD_FARM_EAST = registerBlock("food_farm_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmEast", true, 0, 0, 0));
        
    public static final Block FOOD_FARM_WEST = registerBlock("food_farm_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmWest", true, 0, 0, 0));
    
    // Industry blocks
    public static final Block INDUSTRY_HIGH_DENSITY_BLUE_EAST = registerBlock("industry_high_density_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueEast", true, 0, 0, 0));
        
    public static final Block INDUSTRY_HIGH_DENSITY_BLUE_NORTH = registerBlock("industry_high_density_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueNorth", true, 0, 0, 0));
        
    public static final Block INDUSTRY_HIGH_DENSITY_BLUE_SOUTH = registerBlock("industry_high_density_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueSouth", true, 0, 0, 0));
        
    public static final Block INDUSTRY_HIGH_DENSITY_BLUE_WEST = registerBlock("industry_high_density_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueWest", true, 0, 0, 0));
    
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(IMSMNew.MOD_ID, name), block);
    }
    
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(IMSMNew.MOD_ID, name),
            new BlockItem(block, new Item.Settings()));
    }
    
    public static void initialize() {
        // Add blocks to creative tabs
        ItemGroupEvents.modifyEntriesEvent(IMSMNew.STRUCTURES_KEY).register(content -> {
            content.add(BLOCK_HOUSE);
            content.add(BLOCK_MEGA_HOUSE);
        });
        
        ItemGroupEvents.modifyEntriesEvent(IMSMNew.DECORATION_KEY).register(content -> {
            content.add(DECORATION_PARK_SOUTH);
            content.add(DECORATION_PARK_NORTH);
            content.add(DECORATION_PARK_EAST);
            content.add(DECORATION_PARK_WEST);
        });
        
        ItemGroupEvents.modifyEntriesEvent(IMSMNew.FOOD_KEY).register(content -> {
            content.add(FOOD_FARM_SOUTH);
            content.add(FOOD_FARM_NORTH);
            content.add(FOOD_FARM_EAST);
            content.add(FOOD_FARM_WEST);
        });
        
        ItemGroupEvents.modifyEntriesEvent(IMSMNew.INDUSTRY_HIGH_KEY).register(content -> {
            content.add(INDUSTRY_HIGH_DENSITY_BLUE_EAST);
            content.add(INDUSTRY_HIGH_DENSITY_BLUE_NORTH);
            content.add(INDUSTRY_HIGH_DENSITY_BLUE_SOUTH);
            content.add(INDUSTRY_HIGH_DENSITY_BLUE_WEST);
        });
        
        ItemGroupEvents.modifyEntriesEvent(IMSMNew.OTHER_KEY).register(content -> {
            content.add(BLOCK_STADIUM);
        });
    }
}

package modid.imsm.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IMSMNew implements ModInitializer {
    public static final String MOD_ID = "imsm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    public static EventHandlerFabric eventHandler;
    
    // Item groups (Creative tabs)
    public static final RegistryKey<ItemGroup> STRUCTURES_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "structures"));
    public static final RegistryKey<ItemGroup> DECORATION_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "decoration"));
    public static final RegistryKey<ItemGroup> FOOD_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "food"));
    public static final RegistryKey<ItemGroup> INDUSTRY_HIGH_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "industry_high_density"));
    public static final RegistryKey<ItemGroup> OTHER_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "other"));
    
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Instant Massive Structures Mod");
        
        // Initialize event handler
        eventHandler = new EventHandlerFabric();
        
        // Register creative tabs
        Registry.register(Registries.ITEM_GROUP, STRUCTURES_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.BLOCK_HOUSE))
                .displayName(Text.translatable("itemGroup.imsm.structures"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, DECORATION_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.DECORATION_PARK_SOUTH))
                .displayName(Text.translatable("itemGroup.imsm.decoration"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, FOOD_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.FOOD_FARM_SOUTH))
                .displayName(Text.translatable("itemGroup.imsm.food"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, INDUSTRY_HIGH_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.INDUSTRY_HIGH_DENSITY_BLUE_EAST))
                .displayName(Text.translatable("itemGroup.imsm.industry_high_density"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, OTHER_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.BLOCK_STADIUM))
                .displayName(Text.translatable("itemGroup.imsm.other"))
                .build());
        
        // Register blocks
        ModBlocks.initialize();
        
        LOGGER.info("Instant Massive Structures Mod initialized successfully");
    }
}

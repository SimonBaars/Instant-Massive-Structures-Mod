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
    public static final RegistryKey<ItemGroup> INDUSTRY_MEDIUM_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "industry_medium_density"));
    public static final RegistryKey<ItemGroup> INDUSTRY_LOW_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "industry_low_density"));
    public static final RegistryKey<ItemGroup> OFFICE_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "office"));
    public static final RegistryKey<ItemGroup> PUBLIC_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "public"));
    public static final RegistryKey<ItemGroup> RESIDENTIAL_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "residential"));
    public static final RegistryKey<ItemGroup> SHOPPING_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "shopping"));
    public static final RegistryKey<ItemGroup> TRANSPORT_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "transport"));
    public static final RegistryKey<ItemGroup> UTILITY_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "utility"));
    public static final RegistryKey<ItemGroup> SEASONAL_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "seasonal"));
    public static final RegistryKey<ItemGroup> LIVE_STRUCTURES_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "live_structures"));
    public static final RegistryKey<ItemGroup> OTHER_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "other"));
    
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Instant Massive Structures Mod");
        
        // Initialize event handler
        eventHandler = new EventHandlerFabric();
        
        // Register creative tabs
        Registry.register(Registries.ITEM_GROUP, STRUCTURES_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.BLOCKHOUSE))
                .displayName(Text.translatable("itemGroup.imsm.structures"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, DECORATION_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.DECORATIONPARKSOUTH))
                .displayName(Text.translatable("itemGroup.imsm.decoration"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, FOOD_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.FOODFARMEAST))
                .displayName(Text.translatable("itemGroup.imsm.food"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, INDUSTRY_HIGH_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.INDUSTRYHIGH_DENSITYBLUEEAST))
                .displayName(Text.translatable("itemGroup.imsm.industry_high_density"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, INDUSTRY_MEDIUM_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.INDUSTRYMEDIUM_DENSITYBRICKWEST))
                .displayName(Text.translatable("itemGroup.imsm.industry_medium_density"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, INDUSTRY_LOW_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.INDUSTRYLOW_DENSITYBLUEEAST))
                .displayName(Text.translatable("itemGroup.imsm.industry_low_density"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, OFFICE_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.OFFICEHIGH_DENSITYBRICKEASTWEST))
                .displayName(Text.translatable("itemGroup.imsm.office"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, PUBLIC_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.PUBLICFIRESERVICEBIGEAST))
                .displayName(Text.translatable("itemGroup.imsm.public"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, RESIDENTIAL_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.RESIDENTALMEDIUM_DENSITYORANGEGREENNORTH))
                .displayName(Text.translatable("itemGroup.imsm.residential"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, SHOPPING_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.SHOPPINGMEDIUM_DENSITYQUARTZEAST))
                .displayName(Text.translatable("itemGroup.imsm.shopping"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, TRANSPORT_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.TRANSPORTROAD1NORTHSOUTH))
                .displayName(Text.translatable("itemGroup.imsm.transport"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, UTILITY_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.UTILITYPOWER_NUCLEAREAST))
                .displayName(Text.translatable("itemGroup.imsm.utility"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, SEASONAL_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.CHRISTMASTREE))
                .displayName(Text.translatable("itemGroup.imsm.seasonal"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, LIVE_STRUCTURES_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.BLOCKFERRISWHEEL))
                .displayName(Text.translatable("itemGroup.imsm.live_structures"))
                .build());
                
        Registry.register(Registries.ITEM_GROUP, OTHER_KEY,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.BLOCKSTADIUM))
                .displayName(Text.translatable("itemGroup.imsm.other"))
                .build());
        
        // Register blocks
        ModBlocks.initialize();
        
        LOGGER.info("Instant Massive Structures Mod initialized successfully");
    }
}

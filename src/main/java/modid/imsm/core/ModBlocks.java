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
    // All structure blocks (auto-generated from structure files)

    public static final Block BLOCKAIRBALLOON = registerBlock("block_air_balloon",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockAirBalloon", true, 0, 0, 0));

    public static final Block BLOCKAIRPLANE = registerBlock("block_airplane",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockAirplane", true, 0, 0, 0));

    public static final Block BLOCKAPPLEPIE = registerBlock("block_applepie",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockApplepie", true, 0, 0, 0));

    public static final Block BLOCKARENA1 = registerBlock("block_arena1",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockArena1", true, 0, 0, 0));

    public static final Block BLOCKARENA2 = registerBlock("block_arena2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockArena2", true, 0, 0, 0));

    public static final Block BLOCKBIGPYRAMID = registerBlock("block_big_pyramid",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockBigPyramid", true, 0, 0, 0));

    public static final Block BLOCKBOAT = registerBlock("block_boat",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockBoat", true, 0, 0, 0));

    public static final Block BLOCKBUNKER = registerBlock("block_bunker",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockBunker", true, 0, 0, 0));

    public static final Block BLOCKCACTUS2 = registerBlock("block_cactus2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockCactus2", true, 0, 0, 0));

    public static final Block BLOCKCAKE2 = registerBlock("block_cake2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockCake2", true, 0, 0, 0));

    public static final Block BLOCKCASTLETOWER = registerBlock("block_castle_tower",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockCastleTower", true, 0, 0, 0));

    public static final Block BLOCKCAVE = registerBlock("block_cave",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockCave", true, 0, 0, 0));

    public static final Block BLOCKCOLUMN = registerBlock("block_column",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockColumn", true, 0, 0, 0));

    public static final Block BLOCKCOSYHOUSE = registerBlock("block_cosy_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockCosyHouse", true, 0, 0, 0));

    public static final Block BLOCKDUNGEON = registerBlock("block_dungeon",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockDungeon", true, 0, 0, 0));

    public static final Block BLOCKENCHANTMENTROOM = registerBlock("block_enchantment_room",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockEnchantmentRoom", true, 0, 0, 0));

    public static final Block BLOCKFARM = registerBlock("block_farm",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockFarm", true, 0, 0, 0));

    public static final Block BLOCKFARM2 = registerBlock("block_farm2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockFarm2", true, 0, 0, 0));

    public static final Block BLOCKFARM3 = registerBlock("block_farm3",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockFarm3", true, 0, 0, 0));

    public static final Block BLOCKFARM4 = registerBlock("block_farm4",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockFarm4", true, 0, 0, 0));

    public static final Block BLOCKFLOATINGSPHERE = registerBlock("block_floating_sphere",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockFloatingSphere", true, 0, 0, 0));

    public static final Block BLOCKGIANTTREE = registerBlock("block_giant_tree",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockGiantTree", true, 0, 0, 0));

    public static final Block BLOCKGLASSHOUSE = registerBlock("block_glass_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockGlassHouse", true, 0, 0, 0));

    public static final Block BLOCKHOUNTEDHOUSE = registerBlock("block_hounted_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockHountedHouse", true, 0, 0, 0));

    public static final Block BLOCKHOUSE = registerBlock("block_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockHouse", true, 0, 0, 0));

    public static final Block BLOCKHOUSE2 = registerBlock("block_house2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockHouse2", true, 0, 0, 0));

    public static final Block BLOCKHOUSETRAP1 = registerBlock("block_house_trap1",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockHouseTrap1", true, 0, 0, 0));

    public static final Block BLOCKHOUSETRAP2 = registerBlock("block_house_trap2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockHouseTrap2", true, 0, 0, 0));

    public static final Block BLOCKLEAVES2 = registerBlock("block_leaves2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockLeaves2", true, 0, 0, 0));

    public static final Block BLOCKLIGHTHOUSE = registerBlock("block_lighthouse",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockLighthouse", true, 0, 0, 0));

    public static final Block BLOCKMEGAHOUSE = registerBlock("block_mega_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockMegaHouse", true, 0, 0, 0));

    public static final Block BLOCKMEGAHOUSE2 = registerBlock("block_mega_house2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockMegaHouse2", true, 0, 0, 0));

    public static final Block BLOCKMEGATOWER = registerBlock("block_mega_tower",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockMegaTower", true, 0, 0, 0));

    public static final Block BLOCKPENIRON = registerBlock("block_pen_iron",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockPenIron", true, 0, 0, 0));

    public static final Block BLOCKPENNETHER = registerBlock("block_pen_nether",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockPenNether", true, 0, 0, 0));

    public static final Block BLOCKPENWOOD = registerBlock("block_pen_wood",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockPenWood", true, 0, 0, 0));

    public static final Block BLOCKPLANE = registerBlock("block_plane",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockPlane", true, 0, 0, 0));

    public static final Block BLOCKPRISON = registerBlock("block_prison",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockPrison", true, 0, 0, 0));

    public static final Block BLOCKPRISON2 = registerBlock("block_prison2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockPrison2", true, 0, 0, 0));

    public static final Block BLOCKPYRAMID = registerBlock("block_pyramid",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockPyramid", true, 0, 0, 0));

    public static final Block BLOCKROLLERCOASTER2 = registerBlock("block_roller_coaster2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockRollerCoaster2", true, 0, 0, 0));

    public static final Block BLOCKROLLERCOASTER = registerBlock("block_rollercoaster",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockRollercoaster", true, 0, 0, 0));

    public static final Block BLOCKSHELTER = registerBlock("block_shelter",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockShelter", true, 0, 0, 0));

    public static final Block BLOCKSKYSCRAPER = registerBlock("block_skyscraper",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockSkyscraper", true, 0, 0, 0));

    public static final Block BLOCKSKYSCRAPER2 = registerBlock("block_skyscraper2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockSkyscraper2", true, 0, 0, 0));

    public static final Block BLOCKSTADIUM = registerBlock("block_stadium",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockStadium", true, 0, 0, 0));

    public static final Block BLOCKSTADIUM2 = registerBlock("block_stadium2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockStadium2", true, 0, 0, 0));

    public static final Block BLOCKSTANDARDBRICKHOUSE = registerBlock("block_standard_brick_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockStandardBrickHouse", true, 0, 0, 0));

    public static final Block BLOCKSTOREHOUSE = registerBlock("block_store_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockStoreHouse", true, 0, 0, 0));

    public static final Block BLOCKSTREET = registerBlock("block_street",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockStreet", true, 0, 0, 0));

    public static final Block BLOCKTORCH2 = registerBlock("block_torch2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockTorch2", true, 0, 0, 0));

    public static final Block BLOCKTOWER = registerBlock("block_tower",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockTower", true, 0, 0, 0));

    public static final Block BLOCKWATERSLIDE = registerBlock("block_water_slide",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "BlockWaterSlide", true, 0, 0, 0));

    public static final Block CHRISTMASHOUSE = registerBlock("christmas_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasHouse", true, 0, 0, 0));

    public static final Block CHRISTMASHOUSE2 = registerBlock("christmas_house2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasHouse2", true, 0, 0, 0));

    public static final Block CHRISTMASHOUSE3 = registerBlock("christmas_house3",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasHouse3", true, 0, 0, 0));

    public static final Block CHRISTMASMARKET = registerBlock("christmas_market",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasMarket", true, 0, 0, 0));

    public static final Block CHRISTMASSLEIGH = registerBlock("christmas_sleigh",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasSleigh", true, 0, 0, 0));

    public static final Block CHRISTMASSLEIGH2 = registerBlock("christmas_sleigh2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasSleigh2", true, 0, 0, 0));

    public static final Block CHRISTMASSNOWMAN = registerBlock("christmas_snowman",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasSnowman", true, 0, 0, 0));

    public static final Block CHRISTMASTREE = registerBlock("christmas_tree",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ChristmasTree", true, 0, 0, 0));

    public static final Block DECORATIONGRASSNORTHEASTSOUTHWEST = registerBlock("decoration_grass_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationGrassNorthEastSouthWest", true, 0, 0, 0));

    public static final Block DECORATIONPARKEAST = registerBlock("decoration_park_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkEast", true, 0, 0, 0));

    public static final Block DECORATIONPARKNORTH = registerBlock("decoration_park_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkNorth", true, 0, 0, 0));

    public static final Block DECORATIONPARKSOUTH = registerBlock("decoration_park_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkSouth", true, 0, 0, 0));

    public static final Block DECORATIONPARKWEST = registerBlock("decoration_park_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkWest", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGGARAGEEAST = registerBlock("decoration_parking_garage_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingGarageEast", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGGARAGENORTH = registerBlock("decoration_parking_garage_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingGarageNorth", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGGARAGESOUTH = registerBlock("decoration_parking_garage_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingGarageSouth", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGGARAGEWEST = registerBlock("decoration_parking_garage_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingGarageWest", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGLOTSEAST = registerBlock("decoration_parking_lots_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingLotsEast", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGLOTSNORTH = registerBlock("decoration_parking_lots_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingLotsNorth", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGLOTSSOUTH = registerBlock("decoration_parking_lots_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingLotsSouth", true, 0, 0, 0));

    public static final Block DECORATIONPARKINGLOTSWEST = registerBlock("decoration_parking_lots_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationParkingLotsWest", true, 0, 0, 0));

    public static final Block DECORATIONPLAZAFOUNTAINNORTHEASTSOUTHWEST = registerBlock("decoration_plaza_fountain_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationPlazaFountainNorthEastSouthWest", true, 0, 0, 0));

    public static final Block DECORATIONPLAZANORTHEASTSOUTHWEST = registerBlock("decoration_plaza_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationPlazaNorthEastSouthWest", true, 0, 0, 0));

    public static final Block DECORATIONSOCCERSTADIUMEASTWEST = registerBlock("decoration_soccer_stadium_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationSoccerStadiumEastWest", true, 0, 0, 0));

    public static final Block DECORATIONSOCCERSTADIUMNORTHSOUTH = registerBlock("decoration_soccer_stadium_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationSoccerStadiumNorthSouth", true, 0, 0, 0));

    public static final Block DECORATIONSQUARENORTHEASTSOUTHWEST = registerBlock("decoration_square_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationSquareNorthEastSouthWest", true, 0, 0, 0));

    public static final Block DECORATIONSQUARETREEEAST = registerBlock("decoration_square_tree_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationSquareTreeEast", true, 0, 0, 0));

    public static final Block DECORATIONSQUARETREENORTH = registerBlock("decoration_square_tree_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationSquareTreeNorth", true, 0, 0, 0));

    public static final Block DECORATIONSQUARETREESOUTH = registerBlock("decoration_square_tree_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationSquareTreeSouth", true, 0, 0, 0));

    public static final Block DECORATIONSQUARETREEWEST = registerBlock("decoration_square_tree_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "DecorationSquareTreeWest", true, 0, 0, 0));

    public static final Block FOODCARROTSEASTWEST = registerBlock("food_carrots_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodCarrotsEastWest", true, 0, 0, 0));

    public static final Block FOODCARROTSNORTHSOUTH = registerBlock("food_carrots_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodCarrotsNorthSouth", true, 0, 0, 0));

    public static final Block FOODFARMEAST = registerBlock("food_farm_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmEast", true, 0, 0, 0));

    public static final Block FOODFARMNORTH = registerBlock("food_farm_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmNorth", true, 0, 0, 0));

    public static final Block FOODFARMSOUTH = registerBlock("food_farm_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmSouth", true, 0, 0, 0));

    public static final Block FOODFARMWEST = registerBlock("food_farm_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodFarmWest", true, 0, 0, 0));

    public static final Block FOODPOTATOESNORTHEASTSOUTHWEST = registerBlock("food_potatoes_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodPotatoesNorthEastSouthWest", true, 0, 0, 0));

    public static final Block FOODSTABLEEASTWEST = registerBlock("food_stable_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodStableEastWest", true, 0, 0, 0));

    public static final Block FOODSTABLENORTHSOUTH = registerBlock("food_stable_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodStableNorthSouth", true, 0, 0, 0));

    public static final Block FOODWHEATNORTHEASTSOUTHWEST = registerBlock("food_wheat_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "FoodWheatNorthEastSouthWest", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBLUEEAST = registerBlock("industry_high__density_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueEast", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBLUENORTH = registerBlock("industry_high__density_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueNorth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBLUESOUTH = registerBlock("industry_high__density_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueSouth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBLUEWEST = registerBlock("industry_high__density_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBlueWest", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBRICKEAST = registerBlock("industry_high__density_brick_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBrickEast", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBRICKNORTH = registerBlock("industry_high__density_brick_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBrickNorth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBRICKSOUTH = registerBlock("industry_high__density_brick_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBrickSouth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYBRICKWEST = registerBlock("industry_high__density_brick_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityBrickWest", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCHIMNEYEAST = registerBlock("industry_high__density_chimney_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityChimneyEast", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCHIMNEYNORTH = registerBlock("industry_high__density_chimney_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityChimneyNorth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCHIMNEYSOUTH = registerBlock("industry_high__density_chimney_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityChimneySouth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCHIMNEYWEST = registerBlock("industry_high__density_chimney_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityChimneyWest", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCOMPUTERCHIPEAST = registerBlock("industry_high__density_computer_chip_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityComputerChipEast", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCOMPUTERCHIPNORTH = registerBlock("industry_high__density_computer_chip_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityComputerChipNorth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCOMPUTERCHIPSOUTH = registerBlock("industry_high__density_computer_chip_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityComputerChipSouth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYCOMPUTERCHIPWEST = registerBlock("industry_high__density_computer_chip_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityComputerChipWest", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYGREENEAST = registerBlock("industry_high__density_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityGreenEast", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYGREENNORTH = registerBlock("industry_high__density_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityGreenNorth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYGREENSOUTH = registerBlock("industry_high__density_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityGreenSouth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYGREENWEST = registerBlock("industry_high__density_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityGreenWest", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYLIGHTBLUEEAST = registerBlock("industry_high__density_light_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityLightBlueEast", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYLIGHTBLUENORTH = registerBlock("industry_high__density_light_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityLightBlueNorth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYLIGHTBLUESOUTH = registerBlock("industry_high__density_light_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityLightBlueSouth", true, 0, 0, 0));

    public static final Block INDUSTRYHIGH_DENSITYLIGHTBLUEWEST = registerBlock("industry_high__density_light_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryHigh_DensityLightBlueWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITY3DPRINTINGEAST = registerBlock("industry_low__density3_d_printing_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_Density3DPrintingEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITY3DPRINTINGNORTH = registerBlock("industry_low__density3_d_printing_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_Density3DPrintingNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITY3DPRINTINGSOUTH = registerBlock("industry_low__density3_d_printing_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_Density3DPrintingSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITY3DPRINTINGWEST = registerBlock("industry_low__density3_d_printing_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_Density3DPrintingWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBLUEEAST = registerBlock("industry_low__density_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBlueEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBLUENORTH = registerBlock("industry_low__density_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBlueNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBLUESOUTH = registerBlock("industry_low__density_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBlueSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBLUEWEST = registerBlock("industry_low__density_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBlueWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBRICKEAST = registerBlock("industry_low__density_brick_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrickEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBRICKEASTWEST = registerBlock("industry_low__density_brick_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrickEastWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBRICKNORTH = registerBlock("industry_low__density_brick_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrickNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBRICKNORTHSOUTH = registerBlock("industry_low__density_brick_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrickNorthSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBRICKSOUTH = registerBlock("industry_low__density_brick_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrickSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBRICKWEST = registerBlock("industry_low__density_brick_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrickWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNEAST = registerBlock("industry_low__density_brown_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNEAST2 = registerBlock("industry_low__density_brown_east2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownEast2", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNNORTH = registerBlock("industry_low__density_brown_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNNORTH2 = registerBlock("industry_low__density_brown_north2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownNorth2", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNSOUTH = registerBlock("industry_low__density_brown_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNSOUTH2 = registerBlock("industry_low__density_brown_south2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownSouth2", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNWEST = registerBlock("industry_low__density_brown_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYBROWNWEST2 = registerBlock("industry_low__density_brown_west2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityBrownWest2", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYCHIMNEYEAST = registerBlock("industry_low__density_chimney_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityChimneyEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYCHIMNEYNORTH = registerBlock("industry_low__density_chimney_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityChimneyNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYCHIMNEYSOUTH = registerBlock("industry_low__density_chimney_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityChimneySouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYCHIMNEYWEST = registerBlock("industry_low__density_chimney_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityChimneyWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYGREENEAST = registerBlock("industry_low__density_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityGreenEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYGREENNORTH = registerBlock("industry_low__density_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityGreenNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYGREENSOUTH = registerBlock("industry_low__density_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityGreenSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYGREENWEST = registerBlock("industry_low__density_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityGreenWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYIRONEAST = registerBlock("industry_low__density_iron_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityIronEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYIRONNORTH = registerBlock("industry_low__density_iron_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityIronNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYIRONSOUTH = registerBlock("industry_low__density_iron_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityIronSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYIRONWEST = registerBlock("industry_low__density_iron_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityIronWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYPARABOLICANTENNAEAST = registerBlock("industry_low__density_parabolic_antenna_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityParabolicAntennaEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYPARABOLICANTENNANORTH = registerBlock("industry_low__density_parabolic_antenna_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityParabolicAntennaNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYPARABOLICANTENNASOUTH = registerBlock("industry_low__density_parabolic_antenna_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityParabolicAntennaSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYPARABOLICANTENNAWEST = registerBlock("industry_low__density_parabolic_antenna_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityParabolicAntennaWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYTANKNORTHEASTSOUTHWEST = registerBlock("industry_low__density_tank_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityTankNorthEastSouthWest", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYTELESCOPEEAST = registerBlock("industry_low__density_telescope_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityTelescopeEast", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYTELESCOPENORTH = registerBlock("industry_low__density_telescope_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityTelescopeNorth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYTELESCOPESOUTH = registerBlock("industry_low__density_telescope_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityTelescopeSouth", true, 0, 0, 0));

    public static final Block INDUSTRYLOW_DENSITYTELESCOPEWEST = registerBlock("industry_low__density_telescope_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryLow_DensityTelescopeWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBLUEEAST = registerBlock("industry_medium__density_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBlueEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBLUENORTH = registerBlock("industry_medium__density_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBlueNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBLUESOUTH = registerBlock("industry_medium__density_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBlueSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBLUEWEST = registerBlock("industry_medium__density_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBlueWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBRICKEAST = registerBlock("industry_medium__density_brick_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrickEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBRICKNORTH = registerBlock("industry_medium__density_brick_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrickNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBRICKSOUTH = registerBlock("industry_medium__density_brick_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrickSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBRICKWEST = registerBlock("industry_medium__density_brick_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrickWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBROWNEAST = registerBlock("industry_medium__density_brown_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrownEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBROWNNORTH = registerBlock("industry_medium__density_brown_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrownNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBROWNSOUTH = registerBlock("industry_medium__density_brown_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrownSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYBROWNWEST = registerBlock("industry_medium__density_brown_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityBrownWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYCHEMICALPRESSEASTWEST = registerBlock("industry_medium__density_chemical_press_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityChemicalPressEastWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYCHEMICALPRESSNORTHSOUTH = registerBlock("industry_medium__density_chemical_press_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityChemicalPressNorthSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYCHIMNEYEAST = registerBlock("industry_medium__density_chimney_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityChimneyEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYCHIMNEYNORTH = registerBlock("industry_medium__density_chimney_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityChimneyNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYCHIMNEYSOUTH = registerBlock("industry_medium__density_chimney_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityChimneySouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYCHIMNEYWEST = registerBlock("industry_medium__density_chimney_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityChimneyWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYGREENEAST = registerBlock("industry_medium__density_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityGreenEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYGREENNORTH = registerBlock("industry_medium__density_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityGreenNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYGREENSOUTH = registerBlock("industry_medium__density_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityGreenSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYGREENWEST = registerBlock("industry_medium__density_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityGreenWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYICEEAST = registerBlock("industry_medium__density_ice_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityIceEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYICENORTH = registerBlock("industry_medium__density_ice_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityIceNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYICESOUTH = registerBlock("industry_medium__density_ice_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityIceSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYICEWEST = registerBlock("industry_medium__density_ice_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityIceWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYSANDSTONEEAST = registerBlock("industry_medium__density_sandstone_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensitySandstoneEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYSANDSTONENORTH = registerBlock("industry_medium__density_sandstone_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensitySandstoneNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYSANDSTONESOUTH = registerBlock("industry_medium__density_sandstone_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensitySandstoneSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYSANDSTONEWEST = registerBlock("industry_medium__density_sandstone_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensitySandstoneWest", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYTANKEAST = registerBlock("industry_medium__density_tank_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityTankEast", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYTANKNORTH = registerBlock("industry_medium__density_tank_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityTankNorth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYTANKSOUTH = registerBlock("industry_medium__density_tank_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityTankSouth", true, 0, 0, 0));

    public static final Block INDUSTRYMEDIUM_DENSITYTANKWEST = registerBlock("industry_medium__density_tank_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "IndustryMedium_DensityTankWest", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYBRICKEASTWEST = registerBlock("office_high__density_brick_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityBrickEastWest", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYBRICKNORTHSOUTH = registerBlock("office_high__density_brick_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityBrickNorthSouth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYCYANEAST = registerBlock("office_high__density_cyan_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityCyanEast", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYCYANNORTH = registerBlock("office_high__density_cyan_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityCyanNorth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYCYANSOUTH = registerBlock("office_high__density_cyan_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityCyanSouth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYCYANWEST = registerBlock("office_high__density_cyan_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityCyanWest", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYHOLEONTOPEAST = registerBlock("office_high__density_hole_on_top_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityHoleOnTopEast", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYHOLEONTOPNORTH = registerBlock("office_high__density_hole_on_top_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityHoleOnTopNorth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYHOLEONTOPSOUTH = registerBlock("office_high__density_hole_on_top_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityHoleOnTopSouth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYHOLEONTOPWEST = registerBlock("office_high__density_hole_on_top_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityHoleOnTopWest", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYLIGHTBLUEEASTWEST = registerBlock("office_high__density_light_blue_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityLightBlueEastWest", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYLIGHTBLUENORTHSOUTH = registerBlock("office_high__density_light_blue_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensityLightBlueNorthSouth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYSPIROLBUILDINGEAST = registerBlock("office_high__density_spirol_building_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensitySpirolBuildingEast", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYSPIROLBUILDINGNORTH = registerBlock("office_high__density_spirol_building_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensitySpirolBuildingNorth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYSPIROLBUILDINGSOUTH = registerBlock("office_high__density_spirol_building_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensitySpirolBuildingSouth", true, 0, 0, 0));

    public static final Block OFFICEHIGH_DENSITYSPIROLBUILDINGWEST = registerBlock("office_high__density_spirol_building_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeHigh_DensitySpirolBuildingWest", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYBLUEEAST = registerBlock("office_low__density_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityBlueEast", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYBLUENORTH = registerBlock("office_low__density_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityBlueNorth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYBLUESOUTH = registerBlock("office_low__density_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityBlueSouth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYBLUEWEST = registerBlock("office_low__density_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityBlueWest", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYGREENEAST = registerBlock("office_low__density_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityGreenEast", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYGREENNORTH = registerBlock("office_low__density_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityGreenNorth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYGREENSOUTH = registerBlock("office_low__density_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityGreenSouth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYGREENWEST = registerBlock("office_low__density_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityGreenWest", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYWHITEEAST = registerBlock("office_low__density_white_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityWhiteEast", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYWHITENORTH = registerBlock("office_low__density_white_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityWhiteNorth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYWHITESOUTH = registerBlock("office_low__density_white_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityWhiteSouth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYWHITEWEST = registerBlock("office_low__density_white_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityWhiteWest", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYYELLOWEAST = registerBlock("office_low__density_yellow_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityYellowEast", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYYELLOWNORTH = registerBlock("office_low__density_yellow_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityYellowNorth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYYELLOWSOUTH = registerBlock("office_low__density_yellow_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityYellowSouth", true, 0, 0, 0));

    public static final Block OFFICELOW_DENSITYYELLOWWEST = registerBlock("office_low__density_yellow_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeLow_DensityYellowWest", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYCYANEAST = registerBlock("office_medium__density_cyan_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityCyanEast", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYCYANNORTH = registerBlock("office_medium__density_cyan_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityCyanNorth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYCYANSOUTH = registerBlock("office_medium__density_cyan_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityCyanSouth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYCYANWEST = registerBlock("office_medium__density_cyan_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityCyanWest", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYLIGHTBLUEEAST = registerBlock("office_medium__density_light_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityLightBlueEast", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYLIGHTBLUENORTH = registerBlock("office_medium__density_light_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityLightBlueNorth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYLIGHTBLUESOUTH = registerBlock("office_medium__density_light_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityLightBlueSouth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYLIGHTBLUEWEST = registerBlock("office_medium__density_light_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityLightBlueWest", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYPINKEAST = registerBlock("office_medium__density_pink_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityPinkEast", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYPINKNORTH = registerBlock("office_medium__density_pink_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityPinkNorth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYPINKSOUTH = registerBlock("office_medium__density_pink_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityPinkSouth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYPINKWEST = registerBlock("office_medium__density_pink_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensityPinkWest", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYSANDSTONEEAST = registerBlock("office_medium__density_sandstone_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensitySandstoneEast", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYSANDSTONENORTH = registerBlock("office_medium__density_sandstone_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensitySandstoneNorth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYSANDSTONESOUTH = registerBlock("office_medium__density_sandstone_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensitySandstoneSouth", true, 0, 0, 0));

    public static final Block OFFICEMEDIUM_DENSITYSANDSTONEWEST = registerBlock("office_medium__density_sandstone_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OfficeMedium_DensitySandstoneWest", true, 0, 0, 0));

    public static final Block OTHERBRICKHOUSE = registerBlock("other_brick_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherBrickHouse", true, 0, 0, 0));

    public static final Block OTHERGRANDHOUSE = registerBlock("other_grand_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherGrandHouse", true, 0, 0, 0));

    public static final Block OTHERSTABLE = registerBlock("other_stable",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherStable", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE = registerBlock("other_survivor_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE2 = registerBlock("other_survivor_house2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse2", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE3 = registerBlock("other_survivor_house3",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse3", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE4 = registerBlock("other_survivor_house4",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse4", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE5 = registerBlock("other_survivor_house5",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse5", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE6 = registerBlock("other_survivor_house6",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse6", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE7 = registerBlock("other_survivor_house7",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse7", true, 0, 0, 0));

    public static final Block OTHERSURVIVORHOUSE8 = registerBlock("other_survivor_house8",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherSurvivorHouse8", true, 0, 0, 0));

    public static final Block OTHERTEMPLE = registerBlock("other_temple",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "OtherTemple", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICEBIGEAST = registerBlock("public_fire_service_big_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceBigEast", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICEBIGNORTH = registerBlock("public_fire_service_big_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceBigNorth", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICEBIGSOUTH = registerBlock("public_fire_service_big_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceBigSouth", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICEBIGWEST = registerBlock("public_fire_service_big_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceBigWest", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICESMALLEAST = registerBlock("public_fire_service_small_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceSmallEast", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICESMALLNORTH = registerBlock("public_fire_service_small_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceSmallNorth", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICESMALLSOUTH = registerBlock("public_fire_service_small_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceSmallSouth", true, 0, 0, 0));

    public static final Block PUBLICFIRESERVICESMALLWEST = registerBlock("public_fire_service_small_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicFireServiceSmallWest", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALBIGEAST = registerBlock("public_hospital_big_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalBigEast", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALBIGNORTH = registerBlock("public_hospital_big_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalBigNorth", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALBIGSOUTH = registerBlock("public_hospital_big_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalBigSouth", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALBIGWEST = registerBlock("public_hospital_big_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalBigWest", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALSMALLEAST = registerBlock("public_hospital_small_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalSmallEast", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALSMALLNORTH = registerBlock("public_hospital_small_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalSmallNorth", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALSMALLSOUTH = registerBlock("public_hospital_small_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalSmallSouth", true, 0, 0, 0));

    public static final Block PUBLICHOSPITALSMALLWEST = registerBlock("public_hospital_small_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicHospitalSmallWest", true, 0, 0, 0));

    public static final Block PUBLICLIBRARYEASTWEST = registerBlock("public_library_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicLibraryEastWest", true, 0, 0, 0));

    public static final Block PUBLICLIBRARYNORTHSOUTH = registerBlock("public_library_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicLibraryNorthSouth", true, 0, 0, 0));

    public static final Block PUBLICPOLICEBIGEAST = registerBlock("public_police_big_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceBigEast", true, 0, 0, 0));

    public static final Block PUBLICPOLICEBIGNORTH = registerBlock("public_police_big_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceBigNorth", true, 0, 0, 0));

    public static final Block PUBLICPOLICEBIGSOUTH = registerBlock("public_police_big_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceBigSouth", true, 0, 0, 0));

    public static final Block PUBLICPOLICEBIGWEST = registerBlock("public_police_big_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceBigWest", true, 0, 0, 0));

    public static final Block PUBLICPOLICESMALLEAST = registerBlock("public_police_small_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceSmallEast", true, 0, 0, 0));

    public static final Block PUBLICPOLICESMALLNORTH = registerBlock("public_police_small_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceSmallNorth", true, 0, 0, 0));

    public static final Block PUBLICPOLICESMALLSOUTH = registerBlock("public_police_small_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceSmallSouth", true, 0, 0, 0));

    public static final Block PUBLICPOLICESMALLWEST = registerBlock("public_police_small_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicPoliceSmallWest", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLBIGNORTHEAST = registerBlock("public_school_big_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolBigNorthEast", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLBIGNORTHWEST = registerBlock("public_school_big_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolBigNorthWest", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLBIGSOUTHEAST = registerBlock("public_school_big_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolBigSouthEast", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLBIGSOUTHWEST = registerBlock("public_school_big_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolBigSouthWest", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLSMALLNORTHEAST = registerBlock("public_school_small_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolSmallNorthEast", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLSMALLNORTHWEST = registerBlock("public_school_small_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolSmallNorthWest", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLSMALLSOUTHEAST = registerBlock("public_school_small_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolSmallSouthEast", true, 0, 0, 0));

    public static final Block PUBLICSCHOOLSMALLSOUTHWEST = registerBlock("public_school_small_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicSchoolSmallSouthWest", true, 0, 0, 0));

    public static final Block PUBLICTOWNHALLBIGEASTWEST = registerBlock("public_townhall_big_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicTownhallBigEastWest", true, 0, 0, 0));

    public static final Block PUBLICTOWNHALLBIGNORTHSOUTH = registerBlock("public_townhall_big_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicTownhallBigNorthSouth", true, 0, 0, 0));

    public static final Block PUBLICTOWNHALLSMALLEAST = registerBlock("public_townhall_small_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicTownhallSmallEast", true, 0, 0, 0));

    public static final Block PUBLICTOWNHALLSMALLNORTH = registerBlock("public_townhall_small_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicTownhallSmallNorth", true, 0, 0, 0));

    public static final Block PUBLICTOWNHALLSMALLSOUTH = registerBlock("public_townhall_small_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicTownhallSmallSouth", true, 0, 0, 0));

    public static final Block PUBLICTOWNHALLSMALLWEST = registerBlock("public_townhall_small_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicTownhallSmallWest", true, 0, 0, 0));

    public static final Block PUBLICUNIVERSITYEAST = registerBlock("public_university_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicUniversityEast", true, 0, 0, 0));

    public static final Block PUBLICUNIVERSITYNORTH = registerBlock("public_university_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicUniversityNorth", true, 0, 0, 0));

    public static final Block PUBLICUNIVERSITYSOUTH = registerBlock("public_university_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicUniversitySouth", true, 0, 0, 0));

    public static final Block PUBLICUNIVERSITYWEST = registerBlock("public_university_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "PublicUniversityWest", true, 0, 0, 0));

    public static final Block RANDOMAIRBALLOON2 = registerBlock("random_airballoon2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomAirballoon2", true, 0, 0, 0));

    public static final Block RANDOMBUILDINGCOMPLEX = registerBlock("random_building_complex",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomBuildingComplex", true, 0, 0, 0));

    public static final Block RANDOMENTRANCE = registerBlock("random_entrance",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomEntrance", true, 0, 0, 0));

    public static final Block RANDOMFLYINGSHIP = registerBlock("random_flying_ship",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomFlyingShip", true, 0, 0, 0));

    public static final Block RANDOMGREENTENT = registerBlock("random_green_tent",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomGreenTent", true, 0, 0, 0));

    public static final Block RANDOMGREYTENT = registerBlock("random_grey_tent",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomGreyTent", true, 0, 0, 0));

    public static final Block RANDOMIMMENSE_BUILDINGCOMPLEX = registerBlock("random_immense__buildingcomplex",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomImmense_Buildingcomplex", true, 0, 0, 0));

    public static final Block RANDOMIMMENSE_WHITE_HOUSE = registerBlock("random_immense__white__house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomImmense_White_House", true, 0, 0, 0));

    public static final Block RANDOMIMMENSE_WORKINGBUILDING = registerBlock("random_immense__working_building",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomImmense_WorkingBuilding", true, 0, 0, 0));

    public static final Block RANDOMIMMENSE_GREENROOF = registerBlock("random_immense_greenroof",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomImmense_greenroof", true, 0, 0, 0));

    public static final Block RANDOMLIGHTHOUSE = registerBlock("random_light_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomLightHouse", true, 0, 0, 0));

    public static final Block RANDOMLITTLEPALACE = registerBlock("random_little_palace",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomLittlePalace", true, 0, 0, 0));

    public static final Block RANDOMLITTLEWOODENCABIN = registerBlock("random_little_wooden_cabin",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomLittleWoodenCabin", true, 0, 0, 0));

    public static final Block RANDOMMINERTENT = registerBlock("random_miner_tent",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomMinerTent", true, 0, 0, 0));

    public static final Block RANDOMNETHERENTRANCESURVIVAL = registerBlock("random_nether_entrance_survival",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomNetherEntranceSurvival", true, 0, 0, 0));

    public static final Block RANDOMRANDOMBRICKHOUSE = registerBlock("random_random_brick_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomRandomBrickHouse", true, 0, 0, 0));

    public static final Block RANDOMSANDSTONECHURCH = registerBlock("random_sand_stone_church",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomSandStoneChurch", true, 0, 0, 0));

    public static final Block RANDOMSANDSTONEBUILDING = registerBlock("random_sandstone_building",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomSandstoneBuilding", true, 0, 0, 0));

    public static final Block RANDOMSANDSTONEWITHFARM = registerBlock("random_sandstonewith_farm",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomSandstonewithFarm", true, 0, 0, 0));

    public static final Block RANDOMSIMPLESANDSTONE = registerBlock("random_simple_sandstone",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomSimpleSandstone", true, 0, 0, 0));

    public static final Block RANDOMSPAWNHOUSEPROD = registerBlock("random_spawn_house_prod",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomSpawnHouseProd", true, 0, 0, 0));

    public static final Block RANDOMSURVIVALHOUSE1 = registerBlock("random_survival_house1",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomSurvivalHouse1", true, 0, 0, 0));

    public static final Block RANDOMSURVIVALHOUSESANDSTONE = registerBlock("random_survival_house_sandstone",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomSurvivalHouseSandstone", true, 0, 0, 0));

    public static final Block RANDOMTENTCAMP = registerBlock("random_tent_camp",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomTentCamp", true, 0, 0, 0));

    public static final Block RANDOMWOODENHOUSE = registerBlock("random_wooden_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomWoodenHouse", true, 0, 0, 0));

    public static final Block RANDOMWOODENSTONEBRICKHOUSE = registerBlock("random_wooden_stonebrick_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RandomWoodenStonebrickHouse", true, 0, 0, 0));

    public static final Block REMOVER16 = registerBlock("remover16",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "Remover16", true, 0, 0, 0));

    public static final Block REMOVER32 = registerBlock("remover32",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "Remover32", true, 0, 0, 0));

    public static final Block REMOVER64 = registerBlock("remover64",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "Remover64", true, 0, 0, 0));

    public static final Block REMOVER8 = registerBlock("remover8",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "Remover8", true, 0, 0, 0));

    public static final Block REMOVERLAST = registerBlock("remover_last",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "RemoverLast", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYBLOCKNORTHEASTSOUTHWEST = registerBlock("residental_enormous__density_block_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityBlockNorthEastSouthWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYBRICKBIGEAST = registerBlock("residental_enormous__density_brick_big_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityBrickBigEast", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYBRICKBIGNORTH = registerBlock("residental_enormous__density_brick_big_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityBrickBigNorth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYBRICKBIGSOUTH = registerBlock("residental_enormous__density_brick_big_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityBrickBigSouth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYBRICKBIGWEST = registerBlock("residental_enormous__density_brick_big_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityBrickBigWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYBRICKSMALLNORTHEASTSOUTHWEST = registerBlock("residental_enormous__density_brick_small_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityBrickSmallNorthEastSouthWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYGREYEAST = registerBlock("residental_enormous__density_grey_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityGreyEast", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYGREYNORTH = registerBlock("residental_enormous__density_grey_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityGreyNorth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYGREYSOUTH = registerBlock("residental_enormous__density_grey_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityGreySouth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYGREYWEST = registerBlock("residental_enormous__density_grey_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityGreyWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYMODERNEAST = registerBlock("residental_enormous__density_modern_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityModernEast", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYMODERNNORTH = registerBlock("residental_enormous__density_modern_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityModernNorth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYMODERNSOUTH = registerBlock("residental_enormous__density_modern_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityModernSouth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYMODERNWEST = registerBlock("residental_enormous__density_modern_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityModernWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYREDEASTWEST = registerBlock("residental_enormous__density_red_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityRedEastWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYREDNORTHSOUTH = registerBlock("residental_enormous__density_red_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityRedNorthSouth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYROUNDNORTHEASTSOUTHWEST = registerBlock("residental_enormous__density_round_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityRoundNorthEastSouthWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONEEAST = registerBlock("residental_enormous__density_stone_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneEast", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONEEAST2 = registerBlock("residental_enormous__density_stone_east2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneEast2", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONENORTH = registerBlock("residental_enormous__density_stone_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneNorth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONENORTH2 = registerBlock("residental_enormous__density_stone_north2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneNorth2", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONESOUTH = registerBlock("residental_enormous__density_stone_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneSouth", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONESOUTH2 = registerBlock("residental_enormous__density_stone_south2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneSouth2", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONEWEST = registerBlock("residental_enormous__density_stone_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneWest", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYSTONEWEST2 = registerBlock("residental_enormous__density_stone_west2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityStoneWest2", true, 0, 0, 0));

    public static final Block RESIDENTALENORMOUS_DENSITYYELLOWNORTHEASTSOUTHWEST = registerBlock("residental_enormous__density_yellow_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalEnormous_DensityYellowNorthEastSouthWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBLUEEAST = registerBlock("residental_high__density_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBlueEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBLUEEASTWEST = registerBlock("residental_high__density_blue_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBlueEastWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBLUENORTH = registerBlock("residental_high__density_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBlueNorth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBLUENORTHSOUTH = registerBlock("residental_high__density_blue_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBlueNorthSouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBLUESOUTH = registerBlock("residental_high__density_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBlueSouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBLUEWEST = registerBlock("residental_high__density_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBlueWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBRICKEAST = registerBlock("residental_high__density_brick_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBrickEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBRICKEASTWEST = registerBlock("residental_high__density_brick_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBrickEastWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBRICKNORTH = registerBlock("residental_high__density_brick_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBrickNorth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBRICKNORTHSOUTH = registerBlock("residental_high__density_brick_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBrickNorthSouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBRICKSOUTH = registerBlock("residental_high__density_brick_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBrickSouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYBRICKWEST = registerBlock("residental_high__density_brick_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityBrickWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYGREENGREYEAST = registerBlock("residental_high__density_green_grey_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityGreenGreyEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYGREENGREYNORTH = registerBlock("residental_high__density_green_grey_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityGreenGreyNorth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYGREENGREYSOUTH = registerBlock("residental_high__density_green_grey_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityGreenGreySouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYGREENGREYWEST = registerBlock("residental_high__density_green_grey_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityGreenGreyWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDCORNERNORTHEAST = registerBlock("residental_high__density_red_corner_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedCornerNorthEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDCORNERNORTHWEST = registerBlock("residental_high__density_red_corner_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedCornerNorthWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDCORNERSOUTHEAST = registerBlock("residental_high__density_red_corner_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedCornerSouthEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDCORNERSOUTHWEST = registerBlock("residental_high__density_red_corner_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedCornerSouthWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDYELLOWEAST = registerBlock("residental_high__density_red_yellow_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedYellowEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDYELLOWNORTH = registerBlock("residental_high__density_red_yellow_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedYellowNorth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDYELLOWSOUTH = registerBlock("residental_high__density_red_yellow_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedYellowSouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYREDYELLOWWEST = registerBlock("residental_high__density_red_yellow_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityRedYellowWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONEEAST = registerBlock("residental_high__density_stone_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONEEAST2 = registerBlock("residental_high__density_stone_east2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneEast2", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONENORTH = registerBlock("residental_high__density_stone_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneNorth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONENORTH2 = registerBlock("residental_high__density_stone_north2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneNorth2", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONESOUTH = registerBlock("residental_high__density_stone_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneSouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONESOUTH2 = registerBlock("residental_high__density_stone_south2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneSouth2", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONEWEST = registerBlock("residental_high__density_stone_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneWest", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYSTONEWEST2 = registerBlock("residental_high__density_stone_west2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityStoneWest2", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYYELLOWEAST = registerBlock("residental_high__density_yellow_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityYellowEast", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYYELLOWNORTH = registerBlock("residental_high__density_yellow_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityYellowNorth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYYELLOWSOUTH = registerBlock("residental_high__density_yellow_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityYellowSouth", true, 0, 0, 0));

    public static final Block RESIDENTALHIGH_DENSITYYELLOWWEST = registerBlock("residental_high__density_yellow_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalHigh_DensityYellowWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYBEIGEEAST = registerBlock("residental_low__density_beige_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityBeigeEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYBEIGENORTH = registerBlock("residental_low__density_beige_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityBeigeNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYBEIGESOUTH = registerBlock("residental_low__density_beige_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityBeigeSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYBEIGEWEST = registerBlock("residental_low__density_beige_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityBeigeWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYCYANEAST = registerBlock("residental_low__density_cyan_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityCyanEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYCYANNORTH = registerBlock("residental_low__density_cyan_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityCyanNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYCYANSOUTH = registerBlock("residental_low__density_cyan_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityCyanSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYCYANWEST = registerBlock("residental_low__density_cyan_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityCyanWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENEAST = registerBlock("residental_low__density_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENEAST2 = registerBlock("residental_low__density_green_east2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenEast2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENNORTH = registerBlock("residental_low__density_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENNORTH2 = registerBlock("residental_low__density_green_north2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenNorth2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENSOUTH = registerBlock("residental_low__density_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENSOUTH2 = registerBlock("residental_low__density_green_south2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenSouth2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENWEST = registerBlock("residental_low__density_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYGREENWEST2 = registerBlock("residental_low__density_green_west2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityGreenWest2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUEEAST = registerBlock("residental_low__density_light_blue_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUEEAST2 = registerBlock("residental_low__density_light_blue_east2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueEast2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUENORTH = registerBlock("residental_low__density_light_blue_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUENORTH2 = registerBlock("residental_low__density_light_blue_north2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueNorth2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUESOUTH = registerBlock("residental_low__density_light_blue_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUESOUTH2 = registerBlock("residental_low__density_light_blue_south2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueSouth2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUEWEST = registerBlock("residental_low__density_light_blue_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTBLUEWEST2 = registerBlock("residental_low__density_light_blue_west2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightBlueWest2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTGREYEAST = registerBlock("residental_low__density_light_grey_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightGreyEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTGREYNORTH = registerBlock("residental_low__density_light_grey_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightGreyNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTGREYSOUTH = registerBlock("residental_low__density_light_grey_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightGreySouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYLIGHTGREYWEST = registerBlock("residental_low__density_light_grey_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityLightGreyWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYMODERNEAST = registerBlock("residental_low__density_modern_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityModernEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYMODERNNORTH = registerBlock("residental_low__density_modern_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityModernNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYMODERNSOUTH = registerBlock("residental_low__density_modern_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityModernSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYMODERNWEST = registerBlock("residental_low__density_modern_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityModernWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYORANGEEAST = registerBlock("residental_low__density_orange_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityOrangeEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYORANGENORTH = registerBlock("residental_low__density_orange_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityOrangeNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYORANGESOUTH = registerBlock("residental_low__density_orange_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityOrangeSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYORANGEWEST = registerBlock("residental_low__density_orange_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityOrangeWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYREDEAST = registerBlock("residental_low__density_red_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityRedEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYREDNORTH = registerBlock("residental_low__density_red_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityRedNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYREDSOUTH = registerBlock("residental_low__density_red_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityRedSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYREDWEST = registerBlock("residental_low__density_red_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityRedWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYSTONEEAST = registerBlock("residental_low__density_stone_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityStoneEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYSTONENORTH = registerBlock("residental_low__density_stone_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityStoneNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYSTONESOUTH = registerBlock("residental_low__density_stone_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityStoneSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYSTONEWEST = registerBlock("residental_low__density_stone_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityStoneWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWHITEEAST = registerBlock("residental_low__density_white_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWhiteEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWHITENORTH = registerBlock("residental_low__density_white_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWhiteNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWHITESOUTH = registerBlock("residental_low__density_white_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWhiteSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWHITEWEST = registerBlock("residental_low__density_white_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWhiteWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWOODEAST = registerBlock("residental_low__density_wood_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWoodEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWOODNORTH = registerBlock("residental_low__density_wood_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWoodNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWOODSOUTH = registerBlock("residental_low__density_wood_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWoodSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYWOODWEST = registerBlock("residental_low__density_wood_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityWoodWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWEAST = registerBlock("residental_low__density_yellow_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowEast", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWEAST2 = registerBlock("residental_low__density_yellow_east2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowEast2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWNORTH = registerBlock("residental_low__density_yellow_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowNorth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWNORTH2 = registerBlock("residental_low__density_yellow_north2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowNorth2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWSOUTH = registerBlock("residental_low__density_yellow_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowSouth", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWSOUTH2 = registerBlock("residental_low__density_yellow_south2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowSouth2", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWWEST = registerBlock("residental_low__density_yellow_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowWest", true, 0, 0, 0));

    public static final Block RESIDENTALLOW_DENSITYYELLOWWEST2 = registerBlock("residental_low__density_yellow_west2",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalLow_DensityYellowWest2", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEGREENEAST = registerBlock("residental_medium__density_blue_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueGreenEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEGREENNORTH = registerBlock("residental_medium__density_blue_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueGreenNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEGREENSOUTH = registerBlock("residental_medium__density_blue_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueGreenSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEGREENWEST = registerBlock("residental_medium__density_blue_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueGreenWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEREDEAST = registerBlock("residental_medium__density_blue_red_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueRedEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEREDNORTH = registerBlock("residental_medium__density_blue_red_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueRedNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEREDSOUTH = registerBlock("residental_medium__density_blue_red_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueRedSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBLUEREDWEST = registerBlock("residental_medium__density_blue_red_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBlueRedWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBRICKEAST = registerBlock("residental_medium__density_brick_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBrickEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBRICKNORTH = registerBlock("residental_medium__density_brick_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBrickNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBRICKSOUTH = registerBlock("residental_medium__density_brick_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBrickSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYBRICKWEST = registerBlock("residental_medium__density_brick_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityBrickWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYHORIZONTALEAST = registerBlock("residental_medium__density_horizontal_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityHorizontalEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYHORIZONTALNORTH = registerBlock("residental_medium__density_horizontal_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityHorizontalNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYHORIZONTALSOUTH = registerBlock("residental_medium__density_horizontal_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityHorizontalSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYHORIZONTALWEST = registerBlock("residental_medium__density_horizontal_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityHorizontalWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYORANGEGREENEAST = registerBlock("residental_medium__density_orange_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityOrangeGreenEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYORANGEGREENNORTH = registerBlock("residental_medium__density_orange_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityOrangeGreenNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYORANGEGREENSOUTH = registerBlock("residental_medium__density_orange_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityOrangeGreenSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYORANGEGREENWEST = registerBlock("residental_medium__density_orange_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityOrangeGreenWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYQUARTZEAST = registerBlock("residental_medium__density_quartz_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityQuartzEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYQUARTZNORTH = registerBlock("residental_medium__density_quartz_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityQuartzNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYQUARTZSOUTH = registerBlock("residental_medium__density_quartz_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityQuartzSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYQUARTZWEST = registerBlock("residental_medium__density_quartz_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityQuartzWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYREDGREENEAST = registerBlock("residental_medium__density_red_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRedGreenEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYREDGREENNORTH = registerBlock("residental_medium__density_red_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRedGreenNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYREDGREENSOUTH = registerBlock("residental_medium__density_red_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRedGreenSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYREDGREENWEST = registerBlock("residental_medium__density_red_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRedGreenWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYROOFEAST = registerBlock("residental_medium__density_roof_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRoofEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYROOFNORTH = registerBlock("residental_medium__density_roof_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRoofNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYROOFSOUTH = registerBlock("residental_medium__density_roof_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRoofSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYROOFWEST = registerBlock("residental_medium__density_roof_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityRoofWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONE1EASTWEST = registerBlock("residental_medium__density_stone1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStone1EastWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONE1NORTHSOUTH = registerBlock("residental_medium__density_stone1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStone1NorthSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONE2EASTWEST = registerBlock("residental_medium__density_stone2_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStone2EastWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONE2NORTHSOUTH = registerBlock("residental_medium__density_stone2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStone2NorthSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONECORNERNORTHEAST = registerBlock("residental_medium__density_stone_corner_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneCornerNorthEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONECORNERNORTHWEST = registerBlock("residental_medium__density_stone_corner_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneCornerNorthWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONECORNERSOUTHEAST = registerBlock("residental_medium__density_stone_corner_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneCornerSouthEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONECORNERSOUTHWEST = registerBlock("residental_medium__density_stone_corner_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneCornerSouthWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONEEAST = registerBlock("residental_medium__density_stone_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONEENDNORTHEASTWEST = registerBlock("residental_medium__density_stone_end_north_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneEndNorthEastWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONEENDNORTHSOUTHEAST = registerBlock("residental_medium__density_stone_end_north_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneEndNorthSouthEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONEENDNORTHSOUTHWEST = registerBlock("residental_medium__density_stone_end_north_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneEndNorthSouthWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONEENDSOUTHEASTWEST = registerBlock("residental_medium__density_stone_end_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneEndSouthEastWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONENORTH = registerBlock("residental_medium__density_stone_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONESOUTH = registerBlock("residental_medium__density_stone_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYSTONEWEST = registerBlock("residental_medium__density_stone_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityStoneWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYVERTICALEAST = registerBlock("residental_medium__density_vertical_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityVerticalEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYVERTICALNORTH = registerBlock("residental_medium__density_vertical_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityVerticalNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYVERTICALSOUTH = registerBlock("residental_medium__density_vertical_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityVerticalSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYVERTICALWEST = registerBlock("residental_medium__density_vertical_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityVerticalWest", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYYELLOWREDEAST = registerBlock("residental_medium__density_yellow_red_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityYellowRedEast", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYYELLOWREDNORTH = registerBlock("residental_medium__density_yellow_red_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityYellowRedNorth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYYELLOWREDSOUTH = registerBlock("residental_medium__density_yellow_red_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityYellowRedSouth", true, 0, 0, 0));

    public static final Block RESIDENTALMEDIUM_DENSITYYELLOWREDWEST = registerBlock("residental_medium__density_yellow_red_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ResidentalMedium_DensityYellowRedWest", true, 0, 0, 0));

    public static final Block SHOPPINGHIGH_DENSITYQUARTZEASTWEST = registerBlock("shopping_high__density_quartz_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingHigh_DensityQuartzEastWest", true, 0, 0, 0));

    public static final Block SHOPPINGHIGH_DENSITYQUARTZNORTHSOUTH = registerBlock("shopping_high__density_quartz_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingHigh_DensityQuartzNorthSouth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYBRICKEAST = registerBlock("shopping_low__density_brick_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityBrickEast", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYBRICKNORTH = registerBlock("shopping_low__density_brick_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityBrickNorth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYBRICKSOUTH = registerBlock("shopping_low__density_brick_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityBrickSouth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYBRICKWEST = registerBlock("shopping_low__density_brick_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityBrickWest", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYGREENEAST = registerBlock("shopping_low__density_green_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityGreenEast", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYGREENNORTH = registerBlock("shopping_low__density_green_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityGreenNorth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYGREENSOUTH = registerBlock("shopping_low__density_green_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityGreenSouth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYGREENWEST = registerBlock("shopping_low__density_green_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityGreenWest", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYORANGEEAST = registerBlock("shopping_low__density_orange_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityOrangeEast", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYORANGENORTH = registerBlock("shopping_low__density_orange_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityOrangeNorth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYORANGESOUTH = registerBlock("shopping_low__density_orange_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityOrangeSouth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYORANGEWEST = registerBlock("shopping_low__density_orange_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityOrangeWest", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYPINKEAST = registerBlock("shopping_low__density_pink_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityPinkEast", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYPINKNORTH = registerBlock("shopping_low__density_pink_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityPinkNorth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYPINKSOUTH = registerBlock("shopping_low__density_pink_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityPinkSouth", true, 0, 0, 0));

    public static final Block SHOPPINGLOW_DENSITYPINKWEST = registerBlock("shopping_low__density_pink_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingLow_DensityPinkWest", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYMODERNEAST = registerBlock("shopping_medium__density_modern_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityModernEast", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYMODERNNORTH = registerBlock("shopping_medium__density_modern_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityModernNorth", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYMODERNSOUTH = registerBlock("shopping_medium__density_modern_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityModernSouth", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYMODERNWEST = registerBlock("shopping_medium__density_modern_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityModernWest", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYQUARTZEAST = registerBlock("shopping_medium__density_quartz_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityQuartzEast", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYQUARTZNORTH = registerBlock("shopping_medium__density_quartz_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityQuartzNorth", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYQUARTZSOUTH = registerBlock("shopping_medium__density_quartz_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityQuartzSouth", true, 0, 0, 0));

    public static final Block SHOPPINGMEDIUM_DENSITYQUARTZWEST = registerBlock("shopping_medium__density_quartz_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "ShoppingMedium_DensityQuartzWest", true, 0, 0, 0));

    public static final Block SURVIVALSMALLBUILDING = registerBlock("survival_small_building",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "SurvivalSmallBuilding", true, 0, 0, 0));

    public static final Block SURVIVALWOODENHOUSE = registerBlock("survival_wooden_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "SurvivalWoodenHouse", true, 0, 0, 0));

    public static final Block TRANSPORTAIRPORTRUNWAY_EASTWESTBUILDING_NORTH = registerBlock("transport_airport_runway__east_west_building__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAirportRunway_EastWestBuilding_North", true, 0, 0, 0));

    public static final Block TRANSPORTAIRPORTRUNWAY_EASTWESTBUILDING_SOUTH = registerBlock("transport_airport_runway__east_west_building__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAirportRunway_EastWestBuilding_South", true, 0, 0, 0));

    public static final Block TRANSPORTAIRPORTRUNWAY_NORTHSOUTHBUILDING_EAST = registerBlock("transport_airport_runway__north_south_building__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAirportRunway_NorthSouthBuilding_East", true, 0, 0, 0));

    public static final Block TRANSPORTAIRPORTRUNWAY_NORTHSOUTHBUILDING_WEST = registerBlock("transport_airport_runway__north_south_building__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAirportRunway_NorthSouthBuilding_West", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUE1EASTWEST = registerBlock("transport_avenue1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenue1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUE1NORTHSOUTH = registerBlock("transport_avenue1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenue1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUE2EASTWEST = registerBlock("transport_avenue2_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenue2EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUE2NORTHSOUTH = registerBlock("transport_avenue2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenue2NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUEEEAST = registerBlock("transport_avenue_e_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueEEast", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUEENORTH = registerBlock("transport_avenue_e_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueENorth", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUEESOUTH = registerBlock("transport_avenue_e_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueESouth", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUEEWEST = registerBlock("transport_avenue_e_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueEWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUELNORTHEAST = registerBlock("transport_avenue_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUELNORTHWEST = registerBlock("transport_avenue_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUELSOUTHEAST = registerBlock("transport_avenue_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUELSOUTHWEST = registerBlock("transport_avenue_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUETNORTHEASTWEST = registerBlock("transport_avenue_t_north_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueTNorthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUETNORTHSOUTHEAST = registerBlock("transport_avenue_t_north_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueTNorthSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUETNORTHSOUTHWEST = registerBlock("transport_avenue_t_north_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueTNorthSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUETSOUTHEASTWEST = registerBlock("transport_avenue_t_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueTSouthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTAVENUEXNORTHSOUTHEASTWEST = registerBlock("transport_avenue_x_north_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportAvenueXNorthSouthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE1EASTWEST = registerBlock("transport_bridge_avenue1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE1NORTHSOUTH = registerBlock("transport_bridge_avenue1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE2NORTHSOUTH = registerBlock("transport_bridge_avenue2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue2NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE2SOUTHWEST = registerBlock("transport_bridge_avenue2_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue2SouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE3EASTWEST = registerBlock("transport_bridge_avenue3_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue3EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE3NORTHSOUTH = registerBlock("transport_bridge_avenue3_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue3NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE4EASTWEST = registerBlock("transport_bridge_avenue4_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue4EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUE4NORTHSOUTH = registerBlock("transport_bridge_avenue4_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenue4NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUELNORTHEAST = registerBlock("transport_bridge_avenue_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenueLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUELNORTHWEST = registerBlock("transport_bridge_avenue_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenueLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUELSOUTHEAST = registerBlock("transport_bridge_avenue_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenueLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEAVENUELSOUTHWEST = registerBlock("transport_bridge_avenue_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeAvenueLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY1EASTWEST = registerBlock("transport_bridge_highway1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY1NORTHSOUTH = registerBlock("transport_bridge_highway1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY2EASTWEST = registerBlock("transport_bridge_highway2_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway2EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY2NORTHSOUTH = registerBlock("transport_bridge_highway2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway2NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY3EASTWEST = registerBlock("transport_bridge_highway3_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway3EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY3NORTHSOUTH = registerBlock("transport_bridge_highway3_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway3NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY4EASTWEST = registerBlock("transport_bridge_highway4_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway4EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAY4NORTHSOUTH = registerBlock("transport_bridge_highway4_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighway4NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAYLNORTHEAST = registerBlock("transport_bridge_highway_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighwayLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAYLNORTHWEST = registerBlock("transport_bridge_highway_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighwayLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAYLSOUTHEAST = registerBlock("transport_bridge_highway_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighwayLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEHIGHWAYLSOUTHWEST = registerBlock("transport_bridge_highway_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeHighwayLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROAD1EASTWEST = registerBlock("transport_bridge_road1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoad1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROAD1NORTHSOUTH = registerBlock("transport_bridge_road1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoad1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROAD2EASTWEST = registerBlock("transport_bridge_road2_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoad2EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROAD2NORTHSOUTH = registerBlock("transport_bridge_road2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoad2NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROADLNORTHEAST = registerBlock("transport_bridge_road_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoadLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROADLNORTHWEST = registerBlock("transport_bridge_road_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoadLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROADLSOUTHEAST = registerBlock("transport_bridge_road_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoadLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGEROADLSOUTHWEST = registerBlock("transport_bridge_road_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeRoadLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREET1EASTWEST = registerBlock("transport_bridge_street1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreet1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREET1NORTHSOUTH = registerBlock("transport_bridge_street1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreet1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREET2EASTWEST = registerBlock("transport_bridge_street2_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreet2EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREET2NORTHSOUTH = registerBlock("transport_bridge_street2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreet2NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREETLNORTHEAST = registerBlock("transport_bridge_street_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreetLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREETLNORTHWEST = registerBlock("transport_bridge_street_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreetLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREETLSOUTHEAST = registerBlock("transport_bridge_street_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreetLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTBRIDGESTREETLSOUTHWEST = registerBlock("transport_bridge_street_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportBridgeStreetLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_EASTSTREET_NORTHWESTSIDE = registerBlock("transport_connector_avenue__street1_avenue__east_street__north_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_EASTSTREET_SOUTHWESTSIDE = registerBlock("transport_connector_avenue__street1_avenue__east_street__south_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_NORTHSTREET_SOUTHEASTSIDE = registerBlock("transport_connector_avenue__street1_avenue__north_street__south_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_NORTHSTREET_SOUTHWESTSIDE = registerBlock("transport_connector_avenue__street1_avenue__north_street__south_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_SOUTHSTREET_NORTHEASTSIDE = registerBlock("transport_connector_avenue__street1_avenue__south_street__north_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_SOUTHSTREET_NORTHWESTSIDE = registerBlock("transport_connector_avenue__street1_avenue__south_street__north_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_WESTSTREET_NORTHEASTSIDE = registerBlock("transport_connector_avenue__street1_avenue__west_street__north_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREET1AVENUE_WESTSTREET_SOUTHEASTSIDE = registerBlock("transport_connector_avenue__street1_avenue__west_street__south_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_EASTSTREET_NORTH = registerBlock("transport_connector_avenue__street_l_avenue__east_street__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_EastStreet_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_EASTSTREET_SOUTH = registerBlock("transport_connector_avenue__street_l_avenue__east_street__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_EastStreet_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_NORTHSTREET_EAST = registerBlock("transport_connector_avenue__street_l_avenue__north_street__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_NorthStreet_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_NORTHSTREET_WEST = registerBlock("transport_connector_avenue__street_l_avenue__north_street__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_NorthStreet_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_SOUTHSTREET_EAST = registerBlock("transport_connector_avenue__street_l_avenue__south_street__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_SouthStreet_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_SOUTHSTREET_WEST = registerBlock("transport_connector_avenue__street_l_avenue__south_street__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_SouthStreet_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_WESTSTREET_NORTH = registerBlock("transport_connector_avenue__street_l_avenue__west_street__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_WestStreet_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETLAVENUE_WESTSTREET_SOUTH = registerBlock("transport_connector_avenue__street_l_avenue__west_street__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetLAvenue_WestStreet_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_EASTSTREET_NORTHSOUTH = registerBlock("transport_connector_avenue__street_t_avenue__east_street__north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_EASTWESTSTREET_NORTH = registerBlock("transport_connector_avenue__street_t_avenue__east_west_street__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_EASTWESTSTREET_SOUTH = registerBlock("transport_connector_avenue__street_t_avenue__east_west_street__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_NORTHSOUTHSTREET_EAST = registerBlock("transport_connector_avenue__street_t_avenue__north_south_street__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_NORTHSOUTHSTREET_WEST = registerBlock("transport_connector_avenue__street_t_avenue__north_south_street__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_NORTHSTREET_EASTWEST = registerBlock("transport_connector_avenue__street_t_avenue__north_street__east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_SOUTHSTREET_EASTWEST = registerBlock("transport_connector_avenue__street_t_avenue__south_street__east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETTAVENUE_WESTSTREET_NORTHSOUTH = registerBlock("transport_connector_avenue__street_t_avenue__west_street__north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETXAVENUE_EASTWESTSTREET_NORTHSOUTH = registerBlock("transport_connector_avenue__street_x_avenue__east_west_street__north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORAVENUE_STREETXAVENUE_NORTHSOUTHSTREET_EASTWEST = registerBlock("transport_connector_avenue__street_x_avenue__north_south_street__east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_EASTAVENUE_WEST = registerBlock("transport_connector_bridge__avenue_bridge__east_avenue__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_AvenueBridge_EastAvenue_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_NORTHAVENUE_SOUTH = registerBlock("transport_connector_bridge__avenue_bridge__north_avenue__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_AvenueBridge_NorthAvenue_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_SOUTHAVENUE_NORTH = registerBlock("transport_connector_bridge__avenue_bridge__south_avenue__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_AvenueBridge_SouthAvenue_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_WESTAVENUE_EAST = registerBlock("transport_connector_bridge__avenue_bridge__west_avenue__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_AvenueBridge_WestAvenue_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_EASTROAD_WEST = registerBlock("transport_connector_bridge__road_bridge__east_road__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_RoadBridge_EastRoad_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_NORTHROAD_SOUTH = registerBlock("transport_connector_bridge__road_bridge__north_road__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_RoadBridge_NorthRoad_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_SOUTHROAD_NORTH = registerBlock("transport_connector_bridge__road_bridge__south_road__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_RoadBridge_SouthRoad_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_WESTROAD_EAST = registerBlock("transport_connector_bridge__road_bridge__west_road__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_RoadBridge_WestRoad_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_EASTSTREET_WEST = registerBlock("transport_connector_bridge__street_bridge__east_street__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_StreetBridge_EastStreet_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_NORTHSTREET_SOUTH = registerBlock("transport_connector_bridge__street_bridge__north_street__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_StreetBridge_NorthStreet_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_SOUTHSTREET_NORTH = registerBlock("transport_connector_bridge__street_bridge__south_street__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_StreetBridge_SouthStreet_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_WESTSTREET_EAST = registerBlock("transport_connector_bridge__street_bridge__west_street__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorBridge_StreetBridge_WestStreet_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_EASTAVENUE_WEST = registerBlock("transport_connector_highway_floor__avenue_highway_floor__east_avenue__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_NORTHAVENUE_SOUTH = registerBlock("transport_connector_highway_floor__avenue_highway_floor__north_avenue__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_SOUTHAVENUE_NORTH = registerBlock("transport_connector_highway_floor__avenue_highway_floor__south_avenue__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_WESTAVENUEFLOOR_EAST = registerBlock("transport_connector_highway_floor__avenue_highway_floor__west_avenue_floor__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_EASTAVENUE_WEST = registerBlock("transport_connector_highway__avenue_highway__east_avenue__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_AvenueHighway_EastAvenue_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_NORTHAVENUE_SOUTH = registerBlock("transport_connector_highway__avenue_highway__north_avenue__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_AvenueHighway_NorthAvenue_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_SOUTHAVENUE_NORTH = registerBlock("transport_connector_highway__avenue_highway__south_avenue__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_AvenueHighway_SouthAvenue_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_WESTAVENUE_EAST = registerBlock("transport_connector_highway__avenue_highway__west_avenue__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_AvenueHighway_WestAvenue_East", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_EASTHIGHWAYFLOOR_WEST = registerBlock("transport_connector_highway__highway_floor_highway__east_highway_floor__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_NORTHHIGHWAYFLOOR_SOUTH = registerBlock("transport_connector_highway__highway_floor_highway__north_highway_floor__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_SOUTHHIGHWAYFLOOR_NORTH = registerBlock("transport_connector_highway__highway_floor_highway__south_highway_floor__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North", true, 0, 0, 0));

    public static final Block TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_WESTHIGHWAYFLOOR_EAST = registerBlock("transport_connector_highway__highway_floor_highway__west_highway_floor__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURBIGEAST = registerBlock("transport_harbour_big_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourBigEast", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURBIGNORTH = registerBlock("transport_harbour_big_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourBigNorth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURBIGSOUTH = registerBlock("transport_harbour_big_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourBigSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURBIGWEST = registerBlock("transport_harbour_big_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourBigWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE1CORNERNORTHEAST = registerBlock("transport_harbour_side1_corner_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide1CornerNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE1CORNERNORTHWEST = registerBlock("transport_harbour_side1_corner_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide1CornerNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE1CORNERSOUTHEAST = registerBlock("transport_harbour_side1_corner_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide1CornerSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE1CORNERSOUTHWEST = registerBlock("transport_harbour_side1_corner_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide1CornerSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNERCRANEEAST = registerBlock("transport_harbour_side2_corner_crane_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerCraneEast", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNERCRANENORTH = registerBlock("transport_harbour_side2_corner_crane_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerCraneNorth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNERCRANESOUTH = registerBlock("transport_harbour_side2_corner_crane_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerCraneSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNERCRANEWEST = registerBlock("transport_harbour_side2_corner_crane_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerCraneWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNEREAST = registerBlock("transport_harbour_side2_corner_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerEast", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNERNORTH = registerBlock("transport_harbour_side2_corner_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerNorth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNERSOUTH = registerBlock("transport_harbour_side2_corner_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE2CORNERWEST = registerBlock("transport_harbour_side2_corner_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide2CornerWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE3CORNERNORTHEAST_NORTHWEST_SOUTHEAST = registerBlock("transport_harbour_side3_corner_north_east__north_west__south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE3CORNERNORTHEAST_NORTHWEST_SOUTHWEST = registerBlock("transport_harbour_side3_corner_north_east__north_west__south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE3CORNERNORTHEAST_SOUTHEAST_SOUTHWEST = registerBlock("transport_harbour_side3_corner_north_east__south_east__south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSIDE3CORNERNORTHWEST_SOUTHEAST_SOUTHWEST = registerBlock("transport_harbour_side3_corner_north_west__south_east__south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSMALLEAST = registerBlock("transport_harbour_small_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSmallEast", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSMALLNORTH = registerBlock("transport_harbour_small_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSmallNorth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSMALLSOUTH = registerBlock("transport_harbour_small_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSmallSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHARBOURSMALLWEST = registerBlock("transport_harbour_small_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHarbourSmallWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY05EASTWESTNORTHSIDE = registerBlock("transport_highway05_east_west_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway05EastWestNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY05EASTWESTSOUTHSIDE = registerBlock("transport_highway05_east_west_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway05EastWestSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY05NORTHSOUTHEASTSIDE = registerBlock("transport_highway05_north_south_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway05NorthSouthEastside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY05NORTHSOUTHWESTSIDE = registerBlock("transport_highway05_north_south_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway05NorthSouthWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY1EASTWEST = registerBlock("transport_highway1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY1NORTHSOUTH = registerBlock("transport_highway1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY2EASTWEST = registerBlock("transport_highway2_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway2EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAY2NORTHSOUTH = registerBlock("transport_highway2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighway2NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYEASTWESTEASTSIDE = registerBlock("transport_highway_driveway_east_west_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayEastWestEastside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYEASTWESTWESTSIDE = registerBlock("transport_highway_driveway_east_west_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayEastWestWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYEXITEASTWESTEASTSIDE = registerBlock("transport_highway_driveway_exit_east_west_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayExitEastWestEastside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYEXITEASTWESTWESTSIDE = registerBlock("transport_highway_driveway_exit_east_west_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayExitEastWestWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYEXITNORTHSOUTHNORTHSIDE = registerBlock("transport_highway_driveway_exit_north_south_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayExitNorthSouthNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYEXITNORTHSOUTHSOUTHSIDE = registerBlock("transport_highway_driveway_exit_north_south_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayExitNorthSouthSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYNORTHSOUTHNORTHSIDE = registerBlock("transport_highway_driveway_north_south_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayNorthSouthNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYDRIVEWAYNORTHSOUTHSOUTHSIDE = registerBlock("transport_highway_driveway_north_south_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayDrivewayNorthSouthSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYEXITEASTWESTEASTSIDE = registerBlock("transport_highway_exit_east_west_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayExitEastWestEastside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYEXITEASTWESTWESTSIDE = registerBlock("transport_highway_exit_east_west_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayExitEastWestWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYEXITNORTHSOUTHNORTHSIDE = registerBlock("transport_highway_exit_north_south_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayExitNorthSouthNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYEXITNORTHSOUTHSOUTHSIDE = registerBlock("transport_highway_exit_north_south_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayExitNorthSouthSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR05EASTWESTNORTHSIDE = registerBlock("transport_highway_floor05_east_west_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor05EastWestNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR05EASTWESTSOUTHSIDE = registerBlock("transport_highway_floor05_east_west_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor05EastWestSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR05NORTHSOUTHEASTSIDE = registerBlock("transport_highway_floor05_north_south_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor05NorthSouthEastside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR05NORTHSOUTHWESTSIDE = registerBlock("transport_highway_floor05_north_south_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor05NorthSouthWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR1EASTWEST = registerBlock("transport_highway_floor1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR1NORTHSOUTH = registerBlock("transport_highway_floor1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR2EASTWEST = registerBlock("transport_highway_floor2_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor2EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOR2NORTHSOUTH = registerBlock("transport_highway_floor2_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloor2NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYEASTWESTEASTSIDE = registerBlock("transport_highway_floor_driveway_east_west_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayEastWestEastside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYEASTWESTWESTSIDE = registerBlock("transport_highway_floor_driveway_east_west_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayEastWestWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYEXITEASTWESTEASTSIDE = registerBlock("transport_highway_floor_driveway_exit_east_west_eastside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayExitEastWestEastside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYEXITEASTWESTWESTSIDE = registerBlock("transport_highway_floor_driveway_exit_east_west_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayExitEastWestWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYEXITNORTHSOUTHNORTHSIDE = registerBlock("transport_highway_floor_driveway_exit_north_south_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayExitNorthSouthNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYEXITNORTHSOUTHSOUTHSIDE = registerBlock("transport_highway_floor_driveway_exit_north_south_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayExitNorthSouthSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYNORTHSOUTHNORTHSIDE = registerBlock("transport_highway_floor_driveway_north_south_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayNorthSouthNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORDRIVEWAYNORTHSOUTHSOUTHSIDE = registerBlock("transport_highway_floor_driveway_north_south_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorDrivewayNorthSouthSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOREXITEASTWESTEAST = registerBlock("transport_highway_floor_exit_east_west_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorExitEastWestEast", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOREXITEASTWESTWESTSIDE = registerBlock("transport_highway_floor_exit_east_west_westside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorExitEastWestWestside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOREXITNORTHSOUTHNORTHSIDE = registerBlock("transport_highway_floor_exit_north_south_northside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorExitNorthSouthNorthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOOREXITNORTHSOUTHSOUTHSIDE = registerBlock("transport_highway_floor_exit_north_south_southside",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorExitNorthSouthSouthside", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORLNORTHEAST = registerBlock("transport_highway_floor_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORLNORTHWEST = registerBlock("transport_highway_floor_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORLSOUTHEAST = registerBlock("transport_highway_floor_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORLSOUTHWEST = registerBlock("transport_highway_floor_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORTNORTHEASTWEST = registerBlock("transport_highway_floor_t_north_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorTNorthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORTNORTHSOUTHEAST = registerBlock("transport_highway_floor_t_north_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorTNorthSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORTNORTHSOUTHWEST = registerBlock("transport_highway_floor_t_north_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorTNorthSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORTSOUTHEASTWEST = registerBlock("transport_highway_floor_t_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorTSouthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYFLOORXNORTHEASTSOUTHWEST = registerBlock("transport_highway_floor_x_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayFloorXNorthEastSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYLNORTHEAST = registerBlock("transport_highway_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYLNORTHWEST = registerBlock("transport_highway_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYLSOUTHEAST = registerBlock("transport_highway_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYLSOUTHWEST = registerBlock("transport_highway_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYTNORTHEASTWEST = registerBlock("transport_highway_t_north_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayTNorthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYTNORTHSOUTHEAST = registerBlock("transport_highway_t_north_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayTNorthSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYTNORTHSOUTHWEST = registerBlock("transport_highway_t_north_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayTNorthSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYTSOUTHEASTWEST = registerBlock("transport_highway_t_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayTSouthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTHIGHWAYXNORTHEASTSOUTHWEST = registerBlock("transport_highway_x_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportHighwayXNorthEastSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_EASTTRAM_WEST = registerBlock("transport_public_connector_hightram__tram_hightram__east_tram__west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicConnectorHightram_TramHightram_EastTram_West", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_NORTHTRAM_SOUTH = registerBlock("transport_public_connector_hightram__tram_hightram__north_tram__south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicConnectorHightram_TramHightram_NorthTram_South", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_SOUTHTRAM_NORTH = registerBlock("transport_public_connector_hightram__tram_hightram__south_tram__north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicConnectorHightram_TramHightram_SouthTram_North", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_WESTTRAM_EAST = registerBlock("transport_public_connector_hightram__tram_hightram__west_tram__east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicConnectorHightram_TramHightram_WestTram_East", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAM1EASTWEST = registerBlock("transport_public_hightram1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightram1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAM1NORTHSOUTH = registerBlock("transport_public_hightram1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightram1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMEEAST = registerBlock("transport_public_hightram_e_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramEEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMENORTH = registerBlock("transport_public_hightram_e_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramENorth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMESOUTH = registerBlock("transport_public_hightram_e_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramESouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMEWEST = registerBlock("transport_public_hightram_e_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramEWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMLNORTHEAST = registerBlock("transport_public_hightram_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMLNORTHWEST = registerBlock("transport_public_hightram_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMLSOUTHEAST = registerBlock("transport_public_hightram_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMLSOUTHWEST = registerBlock("transport_public_hightram_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMSTATIONEASTWEST = registerBlock("transport_public_hightram_station_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramStationEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMSTATIONNORTHSOUTH = registerBlock("transport_public_hightram_station_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramStationNorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICHIGHTRAMXNORTHEASTSOUTHWEST = registerBlock("transport_public_hightram_x_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicHightramXNorthEastSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM1EASTWEST = registerBlock("transport_public_tram1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM1NORTHSOUTH = registerBlock("transport_public_tram1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMEEAST = registerBlock("transport_public_tram_e_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramEEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMENORTH = registerBlock("transport_public_tram_e_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramENorth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMESOUTH = registerBlock("transport_public_tram_e_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramESouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMEWEST = registerBlock("transport_public_tram_e_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramEWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMLNORTHEAST = registerBlock("transport_public_tram_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMLNORTHWEST = registerBlock("transport_public_tram_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMLSOUTHEAST = registerBlock("transport_public_tram_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMLSOUTHWEST = registerBlock("transport_public_tram_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMSTATIONEASTWEST = registerBlock("transport_public_tram_station_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramStationEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMSTATIONNORTHSOUTH = registerBlock("transport_public_tram_station_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramStationNorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAMXNORTHEASTSOUTHWEST = registerBlock("transport_public_tram_x_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTramXNorthEastSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROAD1EASTWEST = registerBlock("transport_public_tram_on_road1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_road1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROAD1NORTHSOUTH = registerBlock("transport_public_tram_on_road1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_road1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADEEAST = registerBlock("transport_public_tram_on_road_e_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadEEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADENORTH = registerBlock("transport_public_tram_on_road_e_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadENorth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADESOUTH = registerBlock("transport_public_tram_on_road_e_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadESouth", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADEWEST = registerBlock("transport_public_tram_on_road_e_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadEWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADLNORTHEAST = registerBlock("transport_public_tram_on_road_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADLNORTHWEST = registerBlock("transport_public_tram_on_road_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADLSOUTHEAST = registerBlock("transport_public_tram_on_road_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTPUBLICTRAM_ON_ROADLSOUTHWEST = registerBlock("transport_public_tram_on_road_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportPublicTram_on_roadLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTROAD1EASTWEST = registerBlock("transport_road1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoad1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTROAD1NORTHSOUTH = registerBlock("transport_road1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoad1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTROADEEAST = registerBlock("transport_road_e_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadEEast", true, 0, 0, 0));

    public static final Block TRANSPORTROADENORTH = registerBlock("transport_road_e_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadENorth", true, 0, 0, 0));

    public static final Block TRANSPORTROADESOUTH = registerBlock("transport_road_e_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadESouth", true, 0, 0, 0));

    public static final Block TRANSPORTROADEWEST = registerBlock("transport_road_e_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadEWest", true, 0, 0, 0));

    public static final Block TRANSPORTROADLNORTHEAST = registerBlock("transport_road_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTROADLNORTHWEST = registerBlock("transport_road_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTROADLSOUTHEAST = registerBlock("transport_road_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTROADLSOUTHWEST = registerBlock("transport_road_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTROADTNORTHEASTWEST = registerBlock("transport_road_t_north_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadTNorthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTROADTNORTHSOUTHEAST = registerBlock("transport_road_t_north_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadTNorthSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTROADTNORTHSOUTHWEST = registerBlock("transport_road_t_north_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadTNorthSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTROADTSOUTHEASTWEST = registerBlock("transport_road_t_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadTSouthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTROADXNORTHEASTSOUTHWEST = registerBlock("transport_road_x_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportRoadXNorthEastSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREET1EASTWEST = registerBlock("transport_street1_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreet1EastWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREET1NORTHSOUTH = registerBlock("transport_street1_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreet1NorthSouth", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETEEAST = registerBlock("transport_street_e_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetEEast", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETENORTH = registerBlock("transport_street_e_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetENorth", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETESOUTH = registerBlock("transport_street_e_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetESouth", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETEWEST = registerBlock("transport_street_e_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetEWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETLNORTHEAST = registerBlock("transport_street_l_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetLNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETLNORTHWEST = registerBlock("transport_street_l_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetLNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETLSOUTHEAST = registerBlock("transport_street_l_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetLSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETLSOUTHWEST = registerBlock("transport_street_l_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetLSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETROUNDABOUTNORTHEASTSOUTHWEST = registerBlock("transport_street_roundabout_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetRoundaboutNorthEastSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETTNORTHEASTWEST = registerBlock("transport_street_t_north_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetTNorthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETTNORTHSOUTHEAST = registerBlock("transport_street_t_north_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetTNorthSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETTNORTHSOUTHWEST = registerBlock("transport_street_t_north_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetTNorthSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETTSOUTHEASTWEST = registerBlock("transport_street_t_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetTSouthEastWest", true, 0, 0, 0));

    public static final Block TRANSPORTSTREETXNORTHEASTSOUTHWEST = registerBlock("transport_street_x_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportStreetXNorthEastSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTWATER1CORNERNORTHEAST = registerBlock("transport_water1_corner_north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater1CornerNorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTWATER1CORNERNORTHWEST = registerBlock("transport_water1_corner_north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater1CornerNorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTWATER1CORNERSOUTHEAST = registerBlock("transport_water1_corner_south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater1CornerSouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTWATER1CORNERSOUTHWEST = registerBlock("transport_water1_corner_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater1CornerSouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTWATER2CORNEREAST = registerBlock("transport_water2_corner_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater2CornerEast", true, 0, 0, 0));

    public static final Block TRANSPORTWATER2CORNERNORTH = registerBlock("transport_water2_corner_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater2CornerNorth", true, 0, 0, 0));

    public static final Block TRANSPORTWATER2CORNERSOUTH = registerBlock("transport_water2_corner_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater2CornerSouth", true, 0, 0, 0));

    public static final Block TRANSPORTWATER2CORNERWEST = registerBlock("transport_water2_corner_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater2CornerWest", true, 0, 0, 0));

    public static final Block TRANSPORTWATER3CORNERNORTHEAST_NORTHWEST_SOUTHEAST = registerBlock("transport_water3_corner_north_east__north_west__south_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater3CornerNorthEast_NorthWest_SouthEast", true, 0, 0, 0));

    public static final Block TRANSPORTWATER3CORNERNORTHEAST_NORTHWEST_SOUTHWEST = registerBlock("transport_water3_corner_north_east__north_west__south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater3CornerNorthEast_NorthWest_SouthWest", true, 0, 0, 0));

    public static final Block TRANSPORTWATER3CORNERSOUTHEAST_SOUTHWEST_NORTHEAST = registerBlock("transport_water3_corner_south_east__south_west__north_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater3CornerSouthEast_SouthWest_NorthEast", true, 0, 0, 0));

    public static final Block TRANSPORTWATER3CORNERSOUTHEAST_SOUTHWEST_NORTHWEST = registerBlock("transport_water3_corner_south_east__south_west__north_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater3CornerSouthEast_SouthWest_NorthWest", true, 0, 0, 0));

    public static final Block TRANSPORTWATER4CORNERNORTHSOUTHEASTWEST = registerBlock("transport_water4_corner_north_south_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "TransportWater4CornerNorthSouthEastWest", true, 0, 0, 0));

    public static final Block UTILITYPOWER_NUCLEAREAST = registerBlock("utility_power__nuclear_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_NuclearEast", true, 0, 0, 0));

    public static final Block UTILITYPOWER_NUCLEARNORTH = registerBlock("utility_power__nuclear_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_NuclearNorth", true, 0, 0, 0));

    public static final Block UTILITYPOWER_NUCLEARSOUTH = registerBlock("utility_power__nuclear_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_NuclearSouth", true, 0, 0, 0));

    public static final Block UTILITYPOWER_NUCLEARWEST = registerBlock("utility_power__nuclear_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_NuclearWest", true, 0, 0, 0));

    public static final Block UTILITYPOWER_OILCOALEAST = registerBlock("utility_power__oil_coal_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_OilCoalEast", true, 0, 0, 0));

    public static final Block UTILITYPOWER_OILCOALNORTH = registerBlock("utility_power__oil_coal_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_OilCoalNorth", true, 0, 0, 0));

    public static final Block UTILITYPOWER_OILCOALSOUTH = registerBlock("utility_power__oil_coal_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_OilCoalSouth", true, 0, 0, 0));

    public static final Block UTILITYPOWER_OILCOALWEST = registerBlock("utility_power__oil_coal_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_OilCoalWest", true, 0, 0, 0));

    public static final Block UTILITYPOWER_SUNNORTHEASTSOUTHWEST = registerBlock("utility_power__sun_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_SunNorthEastSouthWest", true, 0, 0, 0));

    public static final Block UTILITYPOWER_WINDEAST = registerBlock("utility_power__wind_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_WindEast", true, 0, 0, 0));

    public static final Block UTILITYPOWER_WINDNORTH = registerBlock("utility_power__wind_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_WindNorth", true, 0, 0, 0));

    public static final Block UTILITYPOWER_WINDSOUTH = registerBlock("utility_power__wind_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_WindSouth", true, 0, 0, 0));

    public static final Block UTILITYPOWER_WINDWEST = registerBlock("utility_power__wind_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPower_WindWest", true, 0, 0, 0));

    public static final Block UTILITYPUMPJACKEASTWEST = registerBlock("utility_pumpjack_east_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPumpjackEastWest", true, 0, 0, 0));

    public static final Block UTILITYPUMPJACKNORTHSOUTH = registerBlock("utility_pumpjack_north_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityPumpjackNorthSouth", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_BURNINGEAST = registerBlock("utility_scrap__burning_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_BurningEast", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_BURNINGNORTH = registerBlock("utility_scrap__burning_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_BurningNorth", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_BURNINGSOUTH = registerBlock("utility_scrap__burning_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_BurningSouth", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_BURNINGWEST = registerBlock("utility_scrap__burning_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_BurningWest", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_HEAPEAST = registerBlock("utility_scrap__heap_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_HeapEast", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_HEAPNORTH = registerBlock("utility_scrap__heap_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_HeapNorth", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_HEAPSOUTH = registerBlock("utility_scrap__heap_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_HeapSouth", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_HEAPWEST = registerBlock("utility_scrap__heap_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_HeapWest", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_RECYCLEEAST = registerBlock("utility_scrap__recycle_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_RecycleEast", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_RECYCLENORTH = registerBlock("utility_scrap__recycle_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_RecycleNorth", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_RECYCLESOUTH = registerBlock("utility_scrap__recycle_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_RecycleSouth", true, 0, 0, 0));

    public static final Block UTILITYSCRAP_RECYCLEWEST = registerBlock("utility_scrap__recycle_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityScrap_RecycleWest", true, 0, 0, 0));

    public static final Block UTILITYWATER_PUMPEAST = registerBlock("utility_water__pump_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_PumpEast", true, 0, 0, 0));

    public static final Block UTILITYWATER_PUMPNORTH = registerBlock("utility_water__pump_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_PumpNorth", true, 0, 0, 0));

    public static final Block UTILITYWATER_PUMPSOUTH = registerBlock("utility_water__pump_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_PumpSouth", true, 0, 0, 0));

    public static final Block UTILITYWATER_PUMPWEST = registerBlock("utility_water__pump_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_PumpWest", true, 0, 0, 0));

    public static final Block UTILITYWATER_TOWERNORTHEASTSOUTHWEST = registerBlock("utility_water__tower_north_east_south_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_TowerNorthEastSouthWest", true, 0, 0, 0));

    public static final Block UTILITYWATER_TREATMENTEAST = registerBlock("utility_water__treatment_east",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_TreatmentEast", true, 0, 0, 0));

    public static final Block UTILITYWATER_TREATMENTNORTH = registerBlock("utility_water__treatment_north",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_TreatmentNorth", true, 0, 0, 0));

    public static final Block UTILITYWATER_TREATMENTSOUTH = registerBlock("utility_water__treatment_south",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_TreatmentSouth", true, 0, 0, 0));

    public static final Block UTILITYWATER_TREATMENTWEST = registerBlock("utility_water__treatment_west",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "UtilityWater_TreatmentWest", true, 0, 0, 0));

    public static final Block WOODENHOUSE = registerBlock("wooden_house",
        new BlockStructureFabric(AbstractBlock.Settings.create().strength(1.0F), "WoodenHouse", true, 0, 0, 0));

    
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

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.DECORATION_KEY).register(content -> {
            content.add(DECORATIONGRASSNORTHEASTSOUTHWEST);
            content.add(DECORATIONPARKEAST);
            content.add(DECORATIONPARKNORTH);
            content.add(DECORATIONPARKSOUTH);
            content.add(DECORATIONPARKWEST);
            content.add(DECORATIONPARKINGGARAGEEAST);
            content.add(DECORATIONPARKINGGARAGENORTH);
            content.add(DECORATIONPARKINGGARAGESOUTH);
            content.add(DECORATIONPARKINGGARAGEWEST);
            content.add(DECORATIONPARKINGLOTSEAST);
            content.add(DECORATIONPARKINGLOTSNORTH);
            content.add(DECORATIONPARKINGLOTSSOUTH);
            content.add(DECORATIONPARKINGLOTSWEST);
            content.add(DECORATIONPLAZAFOUNTAINNORTHEASTSOUTHWEST);
            content.add(DECORATIONPLAZANORTHEASTSOUTHWEST);
            content.add(DECORATIONSOCCERSTADIUMEASTWEST);
            content.add(DECORATIONSOCCERSTADIUMNORTHSOUTH);
            content.add(DECORATIONSQUARENORTHEASTSOUTHWEST);
            content.add(DECORATIONSQUARETREEEAST);
            content.add(DECORATIONSQUARETREENORTH);
            content.add(DECORATIONSQUARETREESOUTH);
            content.add(DECORATIONSQUARETREEWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.FOOD_KEY).register(content -> {
            content.add(BLOCKFARM);
            content.add(BLOCKFARM2);
            content.add(BLOCKFARM3);
            content.add(BLOCKFARM4);
            content.add(FOODCARROTSEASTWEST);
            content.add(FOODCARROTSNORTHSOUTH);
            content.add(FOODFARMEAST);
            content.add(FOODFARMNORTH);
            content.add(FOODFARMSOUTH);
            content.add(FOODFARMWEST);
            content.add(FOODPOTATOESNORTHEASTSOUTHWEST);
            content.add(FOODSTABLEEASTWEST);
            content.add(FOODSTABLENORTHSOUTH);
            content.add(FOODWHEATNORTHEASTSOUTHWEST);
            content.add(OTHERSTABLE);
            content.add(RANDOMSANDSTONEWITHFARM);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.INDUSTRY_HIGH_KEY).register(content -> {
            content.add(INDUSTRYHIGH_DENSITYBLUEEAST);
            content.add(INDUSTRYHIGH_DENSITYBLUENORTH);
            content.add(INDUSTRYHIGH_DENSITYBLUESOUTH);
            content.add(INDUSTRYHIGH_DENSITYBLUEWEST);
            content.add(INDUSTRYHIGH_DENSITYBRICKEAST);
            content.add(INDUSTRYHIGH_DENSITYBRICKNORTH);
            content.add(INDUSTRYHIGH_DENSITYBRICKSOUTH);
            content.add(INDUSTRYHIGH_DENSITYBRICKWEST);
            content.add(INDUSTRYHIGH_DENSITYCHIMNEYEAST);
            content.add(INDUSTRYHIGH_DENSITYCHIMNEYNORTH);
            content.add(INDUSTRYHIGH_DENSITYCHIMNEYSOUTH);
            content.add(INDUSTRYHIGH_DENSITYCHIMNEYWEST);
            content.add(INDUSTRYHIGH_DENSITYCOMPUTERCHIPEAST);
            content.add(INDUSTRYHIGH_DENSITYCOMPUTERCHIPNORTH);
            content.add(INDUSTRYHIGH_DENSITYCOMPUTERCHIPSOUTH);
            content.add(INDUSTRYHIGH_DENSITYCOMPUTERCHIPWEST);
            content.add(INDUSTRYHIGH_DENSITYGREENEAST);
            content.add(INDUSTRYHIGH_DENSITYGREENNORTH);
            content.add(INDUSTRYHIGH_DENSITYGREENSOUTH);
            content.add(INDUSTRYHIGH_DENSITYGREENWEST);
            content.add(INDUSTRYHIGH_DENSITYLIGHTBLUEEAST);
            content.add(INDUSTRYHIGH_DENSITYLIGHTBLUENORTH);
            content.add(INDUSTRYHIGH_DENSITYLIGHTBLUESOUTH);
            content.add(INDUSTRYHIGH_DENSITYLIGHTBLUEWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.INDUSTRY_LOW_KEY).register(content -> {
            content.add(INDUSTRYLOW_DENSITY3DPRINTINGEAST);
            content.add(INDUSTRYLOW_DENSITY3DPRINTINGNORTH);
            content.add(INDUSTRYLOW_DENSITY3DPRINTINGSOUTH);
            content.add(INDUSTRYLOW_DENSITY3DPRINTINGWEST);
            content.add(INDUSTRYLOW_DENSITYBLUEEAST);
            content.add(INDUSTRYLOW_DENSITYBLUENORTH);
            content.add(INDUSTRYLOW_DENSITYBLUESOUTH);
            content.add(INDUSTRYLOW_DENSITYBLUEWEST);
            content.add(INDUSTRYLOW_DENSITYBRICKEAST);
            content.add(INDUSTRYLOW_DENSITYBRICKEASTWEST);
            content.add(INDUSTRYLOW_DENSITYBRICKNORTH);
            content.add(INDUSTRYLOW_DENSITYBRICKNORTHSOUTH);
            content.add(INDUSTRYLOW_DENSITYBRICKSOUTH);
            content.add(INDUSTRYLOW_DENSITYBRICKWEST);
            content.add(INDUSTRYLOW_DENSITYBROWNEAST);
            content.add(INDUSTRYLOW_DENSITYBROWNEAST2);
            content.add(INDUSTRYLOW_DENSITYBROWNNORTH);
            content.add(INDUSTRYLOW_DENSITYBROWNNORTH2);
            content.add(INDUSTRYLOW_DENSITYBROWNSOUTH);
            content.add(INDUSTRYLOW_DENSITYBROWNSOUTH2);
            content.add(INDUSTRYLOW_DENSITYBROWNWEST);
            content.add(INDUSTRYLOW_DENSITYBROWNWEST2);
            content.add(INDUSTRYLOW_DENSITYCHIMNEYEAST);
            content.add(INDUSTRYLOW_DENSITYCHIMNEYNORTH);
            content.add(INDUSTRYLOW_DENSITYCHIMNEYSOUTH);
            content.add(INDUSTRYLOW_DENSITYCHIMNEYWEST);
            content.add(INDUSTRYLOW_DENSITYGREENEAST);
            content.add(INDUSTRYLOW_DENSITYGREENNORTH);
            content.add(INDUSTRYLOW_DENSITYGREENSOUTH);
            content.add(INDUSTRYLOW_DENSITYGREENWEST);
            content.add(INDUSTRYLOW_DENSITYIRONEAST);
            content.add(INDUSTRYLOW_DENSITYIRONNORTH);
            content.add(INDUSTRYLOW_DENSITYIRONSOUTH);
            content.add(INDUSTRYLOW_DENSITYIRONWEST);
            content.add(INDUSTRYLOW_DENSITYPARABOLICANTENNAEAST);
            content.add(INDUSTRYLOW_DENSITYPARABOLICANTENNANORTH);
            content.add(INDUSTRYLOW_DENSITYPARABOLICANTENNASOUTH);
            content.add(INDUSTRYLOW_DENSITYPARABOLICANTENNAWEST);
            content.add(INDUSTRYLOW_DENSITYTANKNORTHEASTSOUTHWEST);
            content.add(INDUSTRYLOW_DENSITYTELESCOPEEAST);
            content.add(INDUSTRYLOW_DENSITYTELESCOPENORTH);
            content.add(INDUSTRYLOW_DENSITYTELESCOPESOUTH);
            content.add(INDUSTRYLOW_DENSITYTELESCOPEWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.INDUSTRY_MEDIUM_KEY).register(content -> {
            content.add(INDUSTRYMEDIUM_DENSITYBLUEEAST);
            content.add(INDUSTRYMEDIUM_DENSITYBLUENORTH);
            content.add(INDUSTRYMEDIUM_DENSITYBLUESOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYBLUEWEST);
            content.add(INDUSTRYMEDIUM_DENSITYBRICKEAST);
            content.add(INDUSTRYMEDIUM_DENSITYBRICKNORTH);
            content.add(INDUSTRYMEDIUM_DENSITYBRICKSOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYBRICKWEST);
            content.add(INDUSTRYMEDIUM_DENSITYBROWNEAST);
            content.add(INDUSTRYMEDIUM_DENSITYBROWNNORTH);
            content.add(INDUSTRYMEDIUM_DENSITYBROWNSOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYBROWNWEST);
            content.add(INDUSTRYMEDIUM_DENSITYCHEMICALPRESSEASTWEST);
            content.add(INDUSTRYMEDIUM_DENSITYCHEMICALPRESSNORTHSOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYCHIMNEYEAST);
            content.add(INDUSTRYMEDIUM_DENSITYCHIMNEYNORTH);
            content.add(INDUSTRYMEDIUM_DENSITYCHIMNEYSOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYCHIMNEYWEST);
            content.add(INDUSTRYMEDIUM_DENSITYGREENEAST);
            content.add(INDUSTRYMEDIUM_DENSITYGREENNORTH);
            content.add(INDUSTRYMEDIUM_DENSITYGREENSOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYGREENWEST);
            content.add(INDUSTRYMEDIUM_DENSITYICEEAST);
            content.add(INDUSTRYMEDIUM_DENSITYICENORTH);
            content.add(INDUSTRYMEDIUM_DENSITYICESOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYICEWEST);
            content.add(INDUSTRYMEDIUM_DENSITYSANDSTONEEAST);
            content.add(INDUSTRYMEDIUM_DENSITYSANDSTONENORTH);
            content.add(INDUSTRYMEDIUM_DENSITYSANDSTONESOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYSANDSTONEWEST);
            content.add(INDUSTRYMEDIUM_DENSITYTANKEAST);
            content.add(INDUSTRYMEDIUM_DENSITYTANKNORTH);
            content.add(INDUSTRYMEDIUM_DENSITYTANKSOUTH);
            content.add(INDUSTRYMEDIUM_DENSITYTANKWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.OFFICE_KEY).register(content -> {
            content.add(OFFICEHIGH_DENSITYBRICKEASTWEST);
            content.add(OFFICEHIGH_DENSITYBRICKNORTHSOUTH);
            content.add(OFFICEHIGH_DENSITYCYANEAST);
            content.add(OFFICEHIGH_DENSITYCYANNORTH);
            content.add(OFFICEHIGH_DENSITYCYANSOUTH);
            content.add(OFFICEHIGH_DENSITYCYANWEST);
            content.add(OFFICEHIGH_DENSITYHOLEONTOPEAST);
            content.add(OFFICEHIGH_DENSITYHOLEONTOPNORTH);
            content.add(OFFICEHIGH_DENSITYHOLEONTOPSOUTH);
            content.add(OFFICEHIGH_DENSITYHOLEONTOPWEST);
            content.add(OFFICEHIGH_DENSITYLIGHTBLUEEASTWEST);
            content.add(OFFICEHIGH_DENSITYLIGHTBLUENORTHSOUTH);
            content.add(OFFICEHIGH_DENSITYSPIROLBUILDINGEAST);
            content.add(OFFICEHIGH_DENSITYSPIROLBUILDINGNORTH);
            content.add(OFFICEHIGH_DENSITYSPIROLBUILDINGSOUTH);
            content.add(OFFICEHIGH_DENSITYSPIROLBUILDINGWEST);
            content.add(OFFICELOW_DENSITYBLUEEAST);
            content.add(OFFICELOW_DENSITYBLUENORTH);
            content.add(OFFICELOW_DENSITYBLUESOUTH);
            content.add(OFFICELOW_DENSITYBLUEWEST);
            content.add(OFFICELOW_DENSITYGREENEAST);
            content.add(OFFICELOW_DENSITYGREENNORTH);
            content.add(OFFICELOW_DENSITYGREENSOUTH);
            content.add(OFFICELOW_DENSITYGREENWEST);
            content.add(OFFICELOW_DENSITYWHITEEAST);
            content.add(OFFICELOW_DENSITYWHITENORTH);
            content.add(OFFICELOW_DENSITYWHITESOUTH);
            content.add(OFFICELOW_DENSITYWHITEWEST);
            content.add(OFFICELOW_DENSITYYELLOWEAST);
            content.add(OFFICELOW_DENSITYYELLOWNORTH);
            content.add(OFFICELOW_DENSITYYELLOWSOUTH);
            content.add(OFFICELOW_DENSITYYELLOWWEST);
            content.add(OFFICEMEDIUM_DENSITYCYANEAST);
            content.add(OFFICEMEDIUM_DENSITYCYANNORTH);
            content.add(OFFICEMEDIUM_DENSITYCYANSOUTH);
            content.add(OFFICEMEDIUM_DENSITYCYANWEST);
            content.add(OFFICEMEDIUM_DENSITYLIGHTBLUEEAST);
            content.add(OFFICEMEDIUM_DENSITYLIGHTBLUENORTH);
            content.add(OFFICEMEDIUM_DENSITYLIGHTBLUESOUTH);
            content.add(OFFICEMEDIUM_DENSITYLIGHTBLUEWEST);
            content.add(OFFICEMEDIUM_DENSITYPINKEAST);
            content.add(OFFICEMEDIUM_DENSITYPINKNORTH);
            content.add(OFFICEMEDIUM_DENSITYPINKSOUTH);
            content.add(OFFICEMEDIUM_DENSITYPINKWEST);
            content.add(OFFICEMEDIUM_DENSITYSANDSTONEEAST);
            content.add(OFFICEMEDIUM_DENSITYSANDSTONENORTH);
            content.add(OFFICEMEDIUM_DENSITYSANDSTONESOUTH);
            content.add(OFFICEMEDIUM_DENSITYSANDSTONEWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.OTHER_KEY).register(content -> {
            content.add(BLOCKAIRBALLOON);
            content.add(BLOCKAIRPLANE);
            content.add(BLOCKAPPLEPIE);
            content.add(BLOCKARENA1);
            content.add(BLOCKARENA2);
            content.add(BLOCKBIGPYRAMID);
            content.add(BLOCKBOAT);
            content.add(BLOCKBUNKER);
            content.add(BLOCKCACTUS2);
            content.add(BLOCKCAKE2);
            content.add(BLOCKCASTLETOWER);
            content.add(BLOCKCAVE);
            content.add(BLOCKCOLUMN);
            content.add(BLOCKCOSYHOUSE);
            content.add(BLOCKDUNGEON);
            content.add(BLOCKENCHANTMENTROOM);
            content.add(BLOCKFLOATINGSPHERE);
            content.add(BLOCKGIANTTREE);
            content.add(BLOCKGLASSHOUSE);
            content.add(BLOCKHOUNTEDHOUSE);
            content.add(BLOCKHOUSE);
            content.add(BLOCKHOUSE2);
            content.add(BLOCKHOUSETRAP1);
            content.add(BLOCKHOUSETRAP2);
            content.add(BLOCKLEAVES2);
            content.add(BLOCKLIGHTHOUSE);
            content.add(BLOCKMEGAHOUSE);
            content.add(BLOCKMEGAHOUSE2);
            content.add(BLOCKMEGATOWER);
            content.add(BLOCKPENIRON);
            content.add(BLOCKPENNETHER);
            content.add(BLOCKPENWOOD);
            content.add(BLOCKPLANE);
            content.add(BLOCKPRISON);
            content.add(BLOCKPRISON2);
            content.add(BLOCKPYRAMID);
            content.add(BLOCKROLLERCOASTER2);
            content.add(BLOCKROLLERCOASTER);
            content.add(BLOCKSHELTER);
            content.add(BLOCKSKYSCRAPER);
            content.add(BLOCKSKYSCRAPER2);
            content.add(BLOCKSTADIUM);
            content.add(BLOCKSTADIUM2);
            content.add(BLOCKSTANDARDBRICKHOUSE);
            content.add(BLOCKSTOREHOUSE);
            content.add(BLOCKSTREET);
            content.add(BLOCKTORCH2);
            content.add(BLOCKTOWER);
            content.add(OTHERBRICKHOUSE);
            content.add(OTHERGRANDHOUSE);
            content.add(OTHERSURVIVORHOUSE);
            content.add(OTHERSURVIVORHOUSE2);
            content.add(OTHERSURVIVORHOUSE3);
            content.add(OTHERSURVIVORHOUSE4);
            content.add(OTHERSURVIVORHOUSE5);
            content.add(OTHERSURVIVORHOUSE6);
            content.add(OTHERSURVIVORHOUSE7);
            content.add(OTHERSURVIVORHOUSE8);
            content.add(OTHERTEMPLE);
            content.add(RANDOMAIRBALLOON2);
            content.add(RANDOMBUILDINGCOMPLEX);
            content.add(RANDOMENTRANCE);
            content.add(RANDOMFLYINGSHIP);
            content.add(RANDOMGREENTENT);
            content.add(RANDOMGREYTENT);
            content.add(RANDOMIMMENSE_BUILDINGCOMPLEX);
            content.add(RANDOMIMMENSE_WHITE_HOUSE);
            content.add(RANDOMIMMENSE_WORKINGBUILDING);
            content.add(RANDOMIMMENSE_GREENROOF);
            content.add(RANDOMLIGHTHOUSE);
            content.add(RANDOMLITTLEPALACE);
            content.add(RANDOMLITTLEWOODENCABIN);
            content.add(RANDOMMINERTENT);
            content.add(RANDOMNETHERENTRANCESURVIVAL);
            content.add(RANDOMRANDOMBRICKHOUSE);
            content.add(RANDOMSANDSTONECHURCH);
            content.add(RANDOMSANDSTONEBUILDING);
            content.add(RANDOMSIMPLESANDSTONE);
            content.add(RANDOMSPAWNHOUSEPROD);
            content.add(RANDOMSURVIVALHOUSE1);
            content.add(RANDOMSURVIVALHOUSESANDSTONE);
            content.add(RANDOMTENTCAMP);
            content.add(RANDOMWOODENHOUSE);
            content.add(RANDOMWOODENSTONEBRICKHOUSE);
            content.add(REMOVER16);
            content.add(REMOVER32);
            content.add(REMOVER64);
            content.add(REMOVER8);
            content.add(REMOVERLAST);
            content.add(SURVIVALWOODENHOUSE);
            content.add(WOODENHOUSE);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.PUBLIC_KEY).register(content -> {
            content.add(PUBLICFIRESERVICEBIGEAST);
            content.add(PUBLICFIRESERVICEBIGNORTH);
            content.add(PUBLICFIRESERVICEBIGSOUTH);
            content.add(PUBLICFIRESERVICEBIGWEST);
            content.add(PUBLICHOSPITALBIGEAST);
            content.add(PUBLICHOSPITALBIGNORTH);
            content.add(PUBLICHOSPITALBIGSOUTH);
            content.add(PUBLICHOSPITALBIGWEST);
            content.add(PUBLICLIBRARYEASTWEST);
            content.add(PUBLICLIBRARYNORTHSOUTH);
            content.add(PUBLICPOLICEBIGEAST);
            content.add(PUBLICPOLICEBIGNORTH);
            content.add(PUBLICPOLICEBIGSOUTH);
            content.add(PUBLICPOLICEBIGWEST);
            content.add(PUBLICSCHOOLBIGNORTHEAST);
            content.add(PUBLICSCHOOLBIGNORTHWEST);
            content.add(PUBLICSCHOOLBIGSOUTHEAST);
            content.add(PUBLICSCHOOLBIGSOUTHWEST);
            content.add(PUBLICTOWNHALLBIGEASTWEST);
            content.add(PUBLICTOWNHALLBIGNORTHSOUTH);
            content.add(PUBLICUNIVERSITYEAST);
            content.add(PUBLICUNIVERSITYNORTH);
            content.add(PUBLICUNIVERSITYSOUTH);
            content.add(PUBLICUNIVERSITYWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.RESIDENTIAL_KEY).register(content -> {
            content.add(RESIDENTALENORMOUS_DENSITYBLOCKNORTHEASTSOUTHWEST);
            content.add(RESIDENTALENORMOUS_DENSITYBRICKBIGEAST);
            content.add(RESIDENTALENORMOUS_DENSITYBRICKBIGNORTH);
            content.add(RESIDENTALENORMOUS_DENSITYBRICKBIGSOUTH);
            content.add(RESIDENTALENORMOUS_DENSITYBRICKBIGWEST);
            content.add(RESIDENTALENORMOUS_DENSITYBRICKSMALLNORTHEASTSOUTHWEST);
            content.add(RESIDENTALENORMOUS_DENSITYGREYEAST);
            content.add(RESIDENTALENORMOUS_DENSITYGREYNORTH);
            content.add(RESIDENTALENORMOUS_DENSITYGREYSOUTH);
            content.add(RESIDENTALENORMOUS_DENSITYGREYWEST);
            content.add(RESIDENTALENORMOUS_DENSITYMODERNEAST);
            content.add(RESIDENTALENORMOUS_DENSITYMODERNNORTH);
            content.add(RESIDENTALENORMOUS_DENSITYMODERNSOUTH);
            content.add(RESIDENTALENORMOUS_DENSITYMODERNWEST);
            content.add(RESIDENTALENORMOUS_DENSITYREDEASTWEST);
            content.add(RESIDENTALENORMOUS_DENSITYREDNORTHSOUTH);
            content.add(RESIDENTALENORMOUS_DENSITYROUNDNORTHEASTSOUTHWEST);
            content.add(RESIDENTALENORMOUS_DENSITYSTONEEAST);
            content.add(RESIDENTALENORMOUS_DENSITYSTONEEAST2);
            content.add(RESIDENTALENORMOUS_DENSITYSTONENORTH);
            content.add(RESIDENTALENORMOUS_DENSITYSTONENORTH2);
            content.add(RESIDENTALENORMOUS_DENSITYSTONESOUTH);
            content.add(RESIDENTALENORMOUS_DENSITYSTONESOUTH2);
            content.add(RESIDENTALENORMOUS_DENSITYSTONEWEST);
            content.add(RESIDENTALENORMOUS_DENSITYSTONEWEST2);
            content.add(RESIDENTALENORMOUS_DENSITYYELLOWNORTHEASTSOUTHWEST);
            content.add(RESIDENTALHIGH_DENSITYBLUEEAST);
            content.add(RESIDENTALHIGH_DENSITYBLUEEASTWEST);
            content.add(RESIDENTALHIGH_DENSITYBLUENORTH);
            content.add(RESIDENTALHIGH_DENSITYBLUENORTHSOUTH);
            content.add(RESIDENTALHIGH_DENSITYBLUESOUTH);
            content.add(RESIDENTALHIGH_DENSITYBLUEWEST);
            content.add(RESIDENTALHIGH_DENSITYBRICKEAST);
            content.add(RESIDENTALHIGH_DENSITYBRICKEASTWEST);
            content.add(RESIDENTALHIGH_DENSITYBRICKNORTH);
            content.add(RESIDENTALHIGH_DENSITYBRICKNORTHSOUTH);
            content.add(RESIDENTALHIGH_DENSITYBRICKSOUTH);
            content.add(RESIDENTALHIGH_DENSITYBRICKWEST);
            content.add(RESIDENTALHIGH_DENSITYGREENGREYEAST);
            content.add(RESIDENTALHIGH_DENSITYGREENGREYNORTH);
            content.add(RESIDENTALHIGH_DENSITYGREENGREYSOUTH);
            content.add(RESIDENTALHIGH_DENSITYGREENGREYWEST);
            content.add(RESIDENTALHIGH_DENSITYREDCORNERNORTHEAST);
            content.add(RESIDENTALHIGH_DENSITYREDCORNERNORTHWEST);
            content.add(RESIDENTALHIGH_DENSITYREDCORNERSOUTHEAST);
            content.add(RESIDENTALHIGH_DENSITYREDCORNERSOUTHWEST);
            content.add(RESIDENTALHIGH_DENSITYREDYELLOWEAST);
            content.add(RESIDENTALHIGH_DENSITYREDYELLOWNORTH);
            content.add(RESIDENTALHIGH_DENSITYREDYELLOWSOUTH);
            content.add(RESIDENTALHIGH_DENSITYREDYELLOWWEST);
            content.add(RESIDENTALHIGH_DENSITYSTONEEAST);
            content.add(RESIDENTALHIGH_DENSITYSTONEEAST2);
            content.add(RESIDENTALHIGH_DENSITYSTONENORTH);
            content.add(RESIDENTALHIGH_DENSITYSTONENORTH2);
            content.add(RESIDENTALHIGH_DENSITYSTONESOUTH);
            content.add(RESIDENTALHIGH_DENSITYSTONESOUTH2);
            content.add(RESIDENTALHIGH_DENSITYSTONEWEST);
            content.add(RESIDENTALHIGH_DENSITYSTONEWEST2);
            content.add(RESIDENTALHIGH_DENSITYYELLOWEAST);
            content.add(RESIDENTALHIGH_DENSITYYELLOWNORTH);
            content.add(RESIDENTALHIGH_DENSITYYELLOWSOUTH);
            content.add(RESIDENTALHIGH_DENSITYYELLOWWEST);
            content.add(RESIDENTALLOW_DENSITYBEIGEEAST);
            content.add(RESIDENTALLOW_DENSITYBEIGENORTH);
            content.add(RESIDENTALLOW_DENSITYBEIGESOUTH);
            content.add(RESIDENTALLOW_DENSITYBEIGEWEST);
            content.add(RESIDENTALLOW_DENSITYCYANEAST);
            content.add(RESIDENTALLOW_DENSITYCYANNORTH);
            content.add(RESIDENTALLOW_DENSITYCYANSOUTH);
            content.add(RESIDENTALLOW_DENSITYCYANWEST);
            content.add(RESIDENTALLOW_DENSITYGREENEAST);
            content.add(RESIDENTALLOW_DENSITYGREENEAST2);
            content.add(RESIDENTALLOW_DENSITYGREENNORTH);
            content.add(RESIDENTALLOW_DENSITYGREENNORTH2);
            content.add(RESIDENTALLOW_DENSITYGREENSOUTH);
            content.add(RESIDENTALLOW_DENSITYGREENSOUTH2);
            content.add(RESIDENTALLOW_DENSITYGREENWEST);
            content.add(RESIDENTALLOW_DENSITYGREENWEST2);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUEEAST);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUEEAST2);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUENORTH);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUENORTH2);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUESOUTH);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUESOUTH2);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUEWEST);
            content.add(RESIDENTALLOW_DENSITYLIGHTBLUEWEST2);
            content.add(RESIDENTALLOW_DENSITYLIGHTGREYEAST);
            content.add(RESIDENTALLOW_DENSITYLIGHTGREYNORTH);
            content.add(RESIDENTALLOW_DENSITYLIGHTGREYSOUTH);
            content.add(RESIDENTALLOW_DENSITYLIGHTGREYWEST);
            content.add(RESIDENTALLOW_DENSITYMODERNEAST);
            content.add(RESIDENTALLOW_DENSITYMODERNNORTH);
            content.add(RESIDENTALLOW_DENSITYMODERNSOUTH);
            content.add(RESIDENTALLOW_DENSITYMODERNWEST);
            content.add(RESIDENTALLOW_DENSITYORANGEEAST);
            content.add(RESIDENTALLOW_DENSITYORANGENORTH);
            content.add(RESIDENTALLOW_DENSITYORANGESOUTH);
            content.add(RESIDENTALLOW_DENSITYORANGEWEST);
            content.add(RESIDENTALLOW_DENSITYREDEAST);
            content.add(RESIDENTALLOW_DENSITYREDNORTH);
            content.add(RESIDENTALLOW_DENSITYREDSOUTH);
            content.add(RESIDENTALLOW_DENSITYREDWEST);
            content.add(RESIDENTALLOW_DENSITYSTONEEAST);
            content.add(RESIDENTALLOW_DENSITYSTONENORTH);
            content.add(RESIDENTALLOW_DENSITYSTONESOUTH);
            content.add(RESIDENTALLOW_DENSITYSTONEWEST);
            content.add(RESIDENTALLOW_DENSITYWHITEEAST);
            content.add(RESIDENTALLOW_DENSITYWHITENORTH);
            content.add(RESIDENTALLOW_DENSITYWHITESOUTH);
            content.add(RESIDENTALLOW_DENSITYWHITEWEST);
            content.add(RESIDENTALLOW_DENSITYWOODEAST);
            content.add(RESIDENTALLOW_DENSITYWOODNORTH);
            content.add(RESIDENTALLOW_DENSITYWOODSOUTH);
            content.add(RESIDENTALLOW_DENSITYWOODWEST);
            content.add(RESIDENTALLOW_DENSITYYELLOWEAST);
            content.add(RESIDENTALLOW_DENSITYYELLOWEAST2);
            content.add(RESIDENTALLOW_DENSITYYELLOWNORTH);
            content.add(RESIDENTALLOW_DENSITYYELLOWNORTH2);
            content.add(RESIDENTALLOW_DENSITYYELLOWSOUTH);
            content.add(RESIDENTALLOW_DENSITYYELLOWSOUTH2);
            content.add(RESIDENTALLOW_DENSITYYELLOWWEST);
            content.add(RESIDENTALLOW_DENSITYYELLOWWEST2);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEGREENEAST);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEGREENNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEGREENSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEGREENWEST);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEREDEAST);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEREDNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEREDSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYBLUEREDWEST);
            content.add(RESIDENTALMEDIUM_DENSITYBRICKEAST);
            content.add(RESIDENTALMEDIUM_DENSITYBRICKNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYBRICKSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYBRICKWEST);
            content.add(RESIDENTALMEDIUM_DENSITYHORIZONTALEAST);
            content.add(RESIDENTALMEDIUM_DENSITYHORIZONTALNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYHORIZONTALSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYHORIZONTALWEST);
            content.add(RESIDENTALMEDIUM_DENSITYORANGEGREENEAST);
            content.add(RESIDENTALMEDIUM_DENSITYORANGEGREENNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYORANGEGREENSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYORANGEGREENWEST);
            content.add(RESIDENTALMEDIUM_DENSITYQUARTZEAST);
            content.add(RESIDENTALMEDIUM_DENSITYQUARTZNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYQUARTZSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYQUARTZWEST);
            content.add(RESIDENTALMEDIUM_DENSITYREDGREENEAST);
            content.add(RESIDENTALMEDIUM_DENSITYREDGREENNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYREDGREENSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYREDGREENWEST);
            content.add(RESIDENTALMEDIUM_DENSITYROOFEAST);
            content.add(RESIDENTALMEDIUM_DENSITYROOFNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYROOFSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYROOFWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONE1EASTWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONE1NORTHSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYSTONE2EASTWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONE2NORTHSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYSTONECORNERNORTHEAST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONECORNERNORTHWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONECORNERSOUTHEAST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONECORNERSOUTHWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONEEAST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONEENDNORTHEASTWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONEENDNORTHSOUTHEAST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONEENDNORTHSOUTHWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONEENDSOUTHEASTWEST);
            content.add(RESIDENTALMEDIUM_DENSITYSTONENORTH);
            content.add(RESIDENTALMEDIUM_DENSITYSTONESOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYSTONEWEST);
            content.add(RESIDENTALMEDIUM_DENSITYVERTICALEAST);
            content.add(RESIDENTALMEDIUM_DENSITYVERTICALNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYVERTICALSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYVERTICALWEST);
            content.add(RESIDENTALMEDIUM_DENSITYYELLOWREDEAST);
            content.add(RESIDENTALMEDIUM_DENSITYYELLOWREDNORTH);
            content.add(RESIDENTALMEDIUM_DENSITYYELLOWREDSOUTH);
            content.add(RESIDENTALMEDIUM_DENSITYYELLOWREDWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.SEASONAL_KEY).register(content -> {
            content.add(CHRISTMASHOUSE);
            content.add(CHRISTMASHOUSE2);
            content.add(CHRISTMASHOUSE3);
            content.add(CHRISTMASMARKET);
            content.add(CHRISTMASSLEIGH);
            content.add(CHRISTMASSLEIGH2);
            content.add(CHRISTMASSNOWMAN);
            content.add(CHRISTMASTREE);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.SHOPPING_KEY).register(content -> {
            content.add(PUBLICFIRESERVICESMALLEAST);
            content.add(PUBLICFIRESERVICESMALLNORTH);
            content.add(PUBLICFIRESERVICESMALLSOUTH);
            content.add(PUBLICFIRESERVICESMALLWEST);
            content.add(PUBLICHOSPITALSMALLEAST);
            content.add(PUBLICHOSPITALSMALLNORTH);
            content.add(PUBLICHOSPITALSMALLSOUTH);
            content.add(PUBLICHOSPITALSMALLWEST);
            content.add(PUBLICPOLICESMALLEAST);
            content.add(PUBLICPOLICESMALLNORTH);
            content.add(PUBLICPOLICESMALLSOUTH);
            content.add(PUBLICPOLICESMALLWEST);
            content.add(PUBLICSCHOOLSMALLNORTHEAST);
            content.add(PUBLICSCHOOLSMALLNORTHWEST);
            content.add(PUBLICSCHOOLSMALLSOUTHEAST);
            content.add(PUBLICSCHOOLSMALLSOUTHWEST);
            content.add(PUBLICTOWNHALLSMALLEAST);
            content.add(PUBLICTOWNHALLSMALLNORTH);
            content.add(PUBLICTOWNHALLSMALLSOUTH);
            content.add(PUBLICTOWNHALLSMALLWEST);
            content.add(SHOPPINGHIGH_DENSITYQUARTZEASTWEST);
            content.add(SHOPPINGHIGH_DENSITYQUARTZNORTHSOUTH);
            content.add(SHOPPINGLOW_DENSITYBRICKEAST);
            content.add(SHOPPINGLOW_DENSITYBRICKNORTH);
            content.add(SHOPPINGLOW_DENSITYBRICKSOUTH);
            content.add(SHOPPINGLOW_DENSITYBRICKWEST);
            content.add(SHOPPINGLOW_DENSITYGREENEAST);
            content.add(SHOPPINGLOW_DENSITYGREENNORTH);
            content.add(SHOPPINGLOW_DENSITYGREENSOUTH);
            content.add(SHOPPINGLOW_DENSITYGREENWEST);
            content.add(SHOPPINGLOW_DENSITYORANGEEAST);
            content.add(SHOPPINGLOW_DENSITYORANGENORTH);
            content.add(SHOPPINGLOW_DENSITYORANGESOUTH);
            content.add(SHOPPINGLOW_DENSITYORANGEWEST);
            content.add(SHOPPINGLOW_DENSITYPINKEAST);
            content.add(SHOPPINGLOW_DENSITYPINKNORTH);
            content.add(SHOPPINGLOW_DENSITYPINKSOUTH);
            content.add(SHOPPINGLOW_DENSITYPINKWEST);
            content.add(SHOPPINGMEDIUM_DENSITYMODERNEAST);
            content.add(SHOPPINGMEDIUM_DENSITYMODERNNORTH);
            content.add(SHOPPINGMEDIUM_DENSITYMODERNSOUTH);
            content.add(SHOPPINGMEDIUM_DENSITYMODERNWEST);
            content.add(SHOPPINGMEDIUM_DENSITYQUARTZEAST);
            content.add(SHOPPINGMEDIUM_DENSITYQUARTZNORTH);
            content.add(SHOPPINGMEDIUM_DENSITYQUARTZSOUTH);
            content.add(SHOPPINGMEDIUM_DENSITYQUARTZWEST);
            content.add(SURVIVALSMALLBUILDING);
            content.add(TRANSPORTHARBOURSMALLEAST);
            content.add(TRANSPORTHARBOURSMALLNORTH);
            content.add(TRANSPORTHARBOURSMALLSOUTH);
            content.add(TRANSPORTHARBOURSMALLWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.TRANSPORT_KEY).register(content -> {
            content.add(TRANSPORTAIRPORTRUNWAY_EASTWESTBUILDING_NORTH);
            content.add(TRANSPORTAIRPORTRUNWAY_EASTWESTBUILDING_SOUTH);
            content.add(TRANSPORTAIRPORTRUNWAY_NORTHSOUTHBUILDING_EAST);
            content.add(TRANSPORTAIRPORTRUNWAY_NORTHSOUTHBUILDING_WEST);
            content.add(TRANSPORTAVENUE1EASTWEST);
            content.add(TRANSPORTAVENUE1NORTHSOUTH);
            content.add(TRANSPORTAVENUE2EASTWEST);
            content.add(TRANSPORTAVENUE2NORTHSOUTH);
            content.add(TRANSPORTAVENUEEEAST);
            content.add(TRANSPORTAVENUEENORTH);
            content.add(TRANSPORTAVENUEESOUTH);
            content.add(TRANSPORTAVENUEEWEST);
            content.add(TRANSPORTAVENUELNORTHEAST);
            content.add(TRANSPORTAVENUELNORTHWEST);
            content.add(TRANSPORTAVENUELSOUTHEAST);
            content.add(TRANSPORTAVENUELSOUTHWEST);
            content.add(TRANSPORTAVENUETNORTHEASTWEST);
            content.add(TRANSPORTAVENUETNORTHSOUTHEAST);
            content.add(TRANSPORTAVENUETNORTHSOUTHWEST);
            content.add(TRANSPORTAVENUETSOUTHEASTWEST);
            content.add(TRANSPORTAVENUEXNORTHSOUTHEASTWEST);
            content.add(TRANSPORTBRIDGEAVENUE1EASTWEST);
            content.add(TRANSPORTBRIDGEAVENUE1NORTHSOUTH);
            content.add(TRANSPORTBRIDGEAVENUE2NORTHSOUTH);
            content.add(TRANSPORTBRIDGEAVENUE2SOUTHWEST);
            content.add(TRANSPORTBRIDGEAVENUE3EASTWEST);
            content.add(TRANSPORTBRIDGEAVENUE3NORTHSOUTH);
            content.add(TRANSPORTBRIDGEAVENUE4EASTWEST);
            content.add(TRANSPORTBRIDGEAVENUE4NORTHSOUTH);
            content.add(TRANSPORTBRIDGEAVENUELNORTHEAST);
            content.add(TRANSPORTBRIDGEAVENUELNORTHWEST);
            content.add(TRANSPORTBRIDGEAVENUELSOUTHEAST);
            content.add(TRANSPORTBRIDGEAVENUELSOUTHWEST);
            content.add(TRANSPORTBRIDGEHIGHWAY1EASTWEST);
            content.add(TRANSPORTBRIDGEHIGHWAY1NORTHSOUTH);
            content.add(TRANSPORTBRIDGEHIGHWAY2EASTWEST);
            content.add(TRANSPORTBRIDGEHIGHWAY2NORTHSOUTH);
            content.add(TRANSPORTBRIDGEHIGHWAY3EASTWEST);
            content.add(TRANSPORTBRIDGEHIGHWAY3NORTHSOUTH);
            content.add(TRANSPORTBRIDGEHIGHWAY4EASTWEST);
            content.add(TRANSPORTBRIDGEHIGHWAY4NORTHSOUTH);
            content.add(TRANSPORTBRIDGEHIGHWAYLNORTHEAST);
            content.add(TRANSPORTBRIDGEHIGHWAYLNORTHWEST);
            content.add(TRANSPORTBRIDGEHIGHWAYLSOUTHEAST);
            content.add(TRANSPORTBRIDGEHIGHWAYLSOUTHWEST);
            content.add(TRANSPORTBRIDGEROAD1EASTWEST);
            content.add(TRANSPORTBRIDGEROAD1NORTHSOUTH);
            content.add(TRANSPORTBRIDGEROAD2EASTWEST);
            content.add(TRANSPORTBRIDGEROAD2NORTHSOUTH);
            content.add(TRANSPORTBRIDGEROADLNORTHEAST);
            content.add(TRANSPORTBRIDGEROADLNORTHWEST);
            content.add(TRANSPORTBRIDGEROADLSOUTHEAST);
            content.add(TRANSPORTBRIDGEROADLSOUTHWEST);
            content.add(TRANSPORTBRIDGESTREET1EASTWEST);
            content.add(TRANSPORTBRIDGESTREET1NORTHSOUTH);
            content.add(TRANSPORTBRIDGESTREET2EASTWEST);
            content.add(TRANSPORTBRIDGESTREET2NORTHSOUTH);
            content.add(TRANSPORTBRIDGESTREETLNORTHEAST);
            content.add(TRANSPORTBRIDGESTREETLNORTHWEST);
            content.add(TRANSPORTBRIDGESTREETLSOUTHEAST);
            content.add(TRANSPORTBRIDGESTREETLSOUTHWEST);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_EASTSTREET_NORTHWESTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_EASTSTREET_SOUTHWESTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_NORTHSTREET_SOUTHEASTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_NORTHSTREET_SOUTHWESTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_SOUTHSTREET_NORTHEASTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_SOUTHSTREET_NORTHWESTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_WESTSTREET_NORTHEASTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREET1AVENUE_WESTSTREET_SOUTHEASTSIDE);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_EASTSTREET_NORTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_EASTSTREET_SOUTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_NORTHSTREET_EAST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_NORTHSTREET_WEST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_SOUTHSTREET_EAST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_SOUTHSTREET_WEST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_WESTSTREET_NORTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETLAVENUE_WESTSTREET_SOUTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_EASTSTREET_NORTHSOUTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_EASTWESTSTREET_NORTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_EASTWESTSTREET_SOUTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_NORTHSOUTHSTREET_EAST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_NORTHSOUTHSTREET_WEST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_NORTHSTREET_EASTWEST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_SOUTHSTREET_EASTWEST);
            content.add(TRANSPORTCONNECTORAVENUE_STREETTAVENUE_WESTSTREET_NORTHSOUTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETXAVENUE_EASTWESTSTREET_NORTHSOUTH);
            content.add(TRANSPORTCONNECTORAVENUE_STREETXAVENUE_NORTHSOUTHSTREET_EASTWEST);
            content.add(TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_EASTAVENUE_WEST);
            content.add(TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_NORTHAVENUE_SOUTH);
            content.add(TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_SOUTHAVENUE_NORTH);
            content.add(TRANSPORTCONNECTORBRIDGE_AVENUEBRIDGE_WESTAVENUE_EAST);
            content.add(TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_EASTROAD_WEST);
            content.add(TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_NORTHROAD_SOUTH);
            content.add(TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_SOUTHROAD_NORTH);
            content.add(TRANSPORTCONNECTORBRIDGE_ROADBRIDGE_WESTROAD_EAST);
            content.add(TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_EASTSTREET_WEST);
            content.add(TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_NORTHSTREET_SOUTH);
            content.add(TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_SOUTHSTREET_NORTH);
            content.add(TRANSPORTCONNECTORBRIDGE_STREETBRIDGE_WESTSTREET_EAST);
            content.add(TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_EASTAVENUE_WEST);
            content.add(TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_NORTHAVENUE_SOUTH);
            content.add(TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_SOUTHAVENUE_NORTH);
            content.add(TRANSPORTCONNECTORHIGHWAYFLOOR_AVENUEHIGHWAYFLOOR_WESTAVENUEFLOOR_EAST);
            content.add(TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_EASTAVENUE_WEST);
            content.add(TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_NORTHAVENUE_SOUTH);
            content.add(TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_SOUTHAVENUE_NORTH);
            content.add(TRANSPORTCONNECTORHIGHWAY_AVENUEHIGHWAY_WESTAVENUE_EAST);
            content.add(TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_EASTHIGHWAYFLOOR_WEST);
            content.add(TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_NORTHHIGHWAYFLOOR_SOUTH);
            content.add(TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_SOUTHHIGHWAYFLOOR_NORTH);
            content.add(TRANSPORTCONNECTORHIGHWAY_HIGHWAYFLOORHIGHWAY_WESTHIGHWAYFLOOR_EAST);
            content.add(TRANSPORTHARBOURBIGEAST);
            content.add(TRANSPORTHARBOURBIGNORTH);
            content.add(TRANSPORTHARBOURBIGSOUTH);
            content.add(TRANSPORTHARBOURBIGWEST);
            content.add(TRANSPORTHARBOURSIDE1CORNERNORTHEAST);
            content.add(TRANSPORTHARBOURSIDE1CORNERNORTHWEST);
            content.add(TRANSPORTHARBOURSIDE1CORNERSOUTHEAST);
            content.add(TRANSPORTHARBOURSIDE1CORNERSOUTHWEST);
            content.add(TRANSPORTHARBOURSIDE2CORNERCRANEEAST);
            content.add(TRANSPORTHARBOURSIDE2CORNERCRANENORTH);
            content.add(TRANSPORTHARBOURSIDE2CORNERCRANESOUTH);
            content.add(TRANSPORTHARBOURSIDE2CORNERCRANEWEST);
            content.add(TRANSPORTHARBOURSIDE2CORNEREAST);
            content.add(TRANSPORTHARBOURSIDE2CORNERNORTH);
            content.add(TRANSPORTHARBOURSIDE2CORNERSOUTH);
            content.add(TRANSPORTHARBOURSIDE2CORNERWEST);
            content.add(TRANSPORTHARBOURSIDE3CORNERNORTHEAST_NORTHWEST_SOUTHEAST);
            content.add(TRANSPORTHARBOURSIDE3CORNERNORTHEAST_NORTHWEST_SOUTHWEST);
            content.add(TRANSPORTHARBOURSIDE3CORNERNORTHEAST_SOUTHEAST_SOUTHWEST);
            content.add(TRANSPORTHARBOURSIDE3CORNERNORTHWEST_SOUTHEAST_SOUTHWEST);
            content.add(TRANSPORTHIGHWAY05EASTWESTNORTHSIDE);
            content.add(TRANSPORTHIGHWAY05EASTWESTSOUTHSIDE);
            content.add(TRANSPORTHIGHWAY05NORTHSOUTHEASTSIDE);
            content.add(TRANSPORTHIGHWAY05NORTHSOUTHWESTSIDE);
            content.add(TRANSPORTHIGHWAY1EASTWEST);
            content.add(TRANSPORTHIGHWAY1NORTHSOUTH);
            content.add(TRANSPORTHIGHWAY2EASTWEST);
            content.add(TRANSPORTHIGHWAY2NORTHSOUTH);
            content.add(TRANSPORTHIGHWAYDRIVEWAYEASTWESTEASTSIDE);
            content.add(TRANSPORTHIGHWAYDRIVEWAYEASTWESTWESTSIDE);
            content.add(TRANSPORTHIGHWAYDRIVEWAYEXITEASTWESTEASTSIDE);
            content.add(TRANSPORTHIGHWAYDRIVEWAYEXITEASTWESTWESTSIDE);
            content.add(TRANSPORTHIGHWAYDRIVEWAYEXITNORTHSOUTHNORTHSIDE);
            content.add(TRANSPORTHIGHWAYDRIVEWAYEXITNORTHSOUTHSOUTHSIDE);
            content.add(TRANSPORTHIGHWAYDRIVEWAYNORTHSOUTHNORTHSIDE);
            content.add(TRANSPORTHIGHWAYDRIVEWAYNORTHSOUTHSOUTHSIDE);
            content.add(TRANSPORTHIGHWAYEXITEASTWESTEASTSIDE);
            content.add(TRANSPORTHIGHWAYEXITEASTWESTWESTSIDE);
            content.add(TRANSPORTHIGHWAYEXITNORTHSOUTHNORTHSIDE);
            content.add(TRANSPORTHIGHWAYEXITNORTHSOUTHSOUTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOOR05EASTWESTNORTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOOR05EASTWESTSOUTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOOR05NORTHSOUTHEASTSIDE);
            content.add(TRANSPORTHIGHWAYFLOOR05NORTHSOUTHWESTSIDE);
            content.add(TRANSPORTHIGHWAYFLOOR1EASTWEST);
            content.add(TRANSPORTHIGHWAYFLOOR1NORTHSOUTH);
            content.add(TRANSPORTHIGHWAYFLOOR2EASTWEST);
            content.add(TRANSPORTHIGHWAYFLOOR2NORTHSOUTH);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYEASTWESTEASTSIDE);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYEASTWESTWESTSIDE);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYEXITEASTWESTEASTSIDE);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYEXITEASTWESTWESTSIDE);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYEXITNORTHSOUTHNORTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYEXITNORTHSOUTHSOUTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYNORTHSOUTHNORTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOORDRIVEWAYNORTHSOUTHSOUTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOOREXITEASTWESTEAST);
            content.add(TRANSPORTHIGHWAYFLOOREXITEASTWESTWESTSIDE);
            content.add(TRANSPORTHIGHWAYFLOOREXITNORTHSOUTHNORTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOOREXITNORTHSOUTHSOUTHSIDE);
            content.add(TRANSPORTHIGHWAYFLOORLNORTHEAST);
            content.add(TRANSPORTHIGHWAYFLOORLNORTHWEST);
            content.add(TRANSPORTHIGHWAYFLOORLSOUTHEAST);
            content.add(TRANSPORTHIGHWAYFLOORLSOUTHWEST);
            content.add(TRANSPORTHIGHWAYFLOORTNORTHEASTWEST);
            content.add(TRANSPORTHIGHWAYFLOORTNORTHSOUTHEAST);
            content.add(TRANSPORTHIGHWAYFLOORTNORTHSOUTHWEST);
            content.add(TRANSPORTHIGHWAYFLOORTSOUTHEASTWEST);
            content.add(TRANSPORTHIGHWAYFLOORXNORTHEASTSOUTHWEST);
            content.add(TRANSPORTHIGHWAYLNORTHEAST);
            content.add(TRANSPORTHIGHWAYLNORTHWEST);
            content.add(TRANSPORTHIGHWAYLSOUTHEAST);
            content.add(TRANSPORTHIGHWAYLSOUTHWEST);
            content.add(TRANSPORTHIGHWAYTNORTHEASTWEST);
            content.add(TRANSPORTHIGHWAYTNORTHSOUTHEAST);
            content.add(TRANSPORTHIGHWAYTNORTHSOUTHWEST);
            content.add(TRANSPORTHIGHWAYTSOUTHEASTWEST);
            content.add(TRANSPORTHIGHWAYXNORTHEASTSOUTHWEST);
            content.add(TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_EASTTRAM_WEST);
            content.add(TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_NORTHTRAM_SOUTH);
            content.add(TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_SOUTHTRAM_NORTH);
            content.add(TRANSPORTPUBLICCONNECTORHIGHTRAM_TRAMHIGHTRAM_WESTTRAM_EAST);
            content.add(TRANSPORTPUBLICHIGHTRAM1EASTWEST);
            content.add(TRANSPORTPUBLICHIGHTRAM1NORTHSOUTH);
            content.add(TRANSPORTPUBLICHIGHTRAMEEAST);
            content.add(TRANSPORTPUBLICHIGHTRAMENORTH);
            content.add(TRANSPORTPUBLICHIGHTRAMESOUTH);
            content.add(TRANSPORTPUBLICHIGHTRAMEWEST);
            content.add(TRANSPORTPUBLICHIGHTRAMLNORTHEAST);
            content.add(TRANSPORTPUBLICHIGHTRAMLNORTHWEST);
            content.add(TRANSPORTPUBLICHIGHTRAMLSOUTHEAST);
            content.add(TRANSPORTPUBLICHIGHTRAMLSOUTHWEST);
            content.add(TRANSPORTPUBLICHIGHTRAMSTATIONEASTWEST);
            content.add(TRANSPORTPUBLICHIGHTRAMSTATIONNORTHSOUTH);
            content.add(TRANSPORTPUBLICHIGHTRAMXNORTHEASTSOUTHWEST);
            content.add(TRANSPORTPUBLICTRAM1EASTWEST);
            content.add(TRANSPORTPUBLICTRAM1NORTHSOUTH);
            content.add(TRANSPORTPUBLICTRAMEEAST);
            content.add(TRANSPORTPUBLICTRAMENORTH);
            content.add(TRANSPORTPUBLICTRAMESOUTH);
            content.add(TRANSPORTPUBLICTRAMEWEST);
            content.add(TRANSPORTPUBLICTRAMLNORTHEAST);
            content.add(TRANSPORTPUBLICTRAMLNORTHWEST);
            content.add(TRANSPORTPUBLICTRAMLSOUTHEAST);
            content.add(TRANSPORTPUBLICTRAMLSOUTHWEST);
            content.add(TRANSPORTPUBLICTRAMSTATIONEASTWEST);
            content.add(TRANSPORTPUBLICTRAMSTATIONNORTHSOUTH);
            content.add(TRANSPORTPUBLICTRAMXNORTHEASTSOUTHWEST);
            content.add(TRANSPORTPUBLICTRAM_ON_ROAD1EASTWEST);
            content.add(TRANSPORTPUBLICTRAM_ON_ROAD1NORTHSOUTH);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADEEAST);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADENORTH);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADESOUTH);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADEWEST);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADLNORTHEAST);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADLNORTHWEST);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADLSOUTHEAST);
            content.add(TRANSPORTPUBLICTRAM_ON_ROADLSOUTHWEST);
            content.add(TRANSPORTROAD1EASTWEST);
            content.add(TRANSPORTROAD1NORTHSOUTH);
            content.add(TRANSPORTROADEEAST);
            content.add(TRANSPORTROADENORTH);
            content.add(TRANSPORTROADESOUTH);
            content.add(TRANSPORTROADEWEST);
            content.add(TRANSPORTROADLNORTHEAST);
            content.add(TRANSPORTROADLNORTHWEST);
            content.add(TRANSPORTROADLSOUTHEAST);
            content.add(TRANSPORTROADLSOUTHWEST);
            content.add(TRANSPORTROADTNORTHEASTWEST);
            content.add(TRANSPORTROADTNORTHSOUTHEAST);
            content.add(TRANSPORTROADTNORTHSOUTHWEST);
            content.add(TRANSPORTROADTSOUTHEASTWEST);
            content.add(TRANSPORTROADXNORTHEASTSOUTHWEST);
            content.add(TRANSPORTSTREET1EASTWEST);
            content.add(TRANSPORTSTREET1NORTHSOUTH);
            content.add(TRANSPORTSTREETEEAST);
            content.add(TRANSPORTSTREETENORTH);
            content.add(TRANSPORTSTREETESOUTH);
            content.add(TRANSPORTSTREETEWEST);
            content.add(TRANSPORTSTREETLNORTHEAST);
            content.add(TRANSPORTSTREETLNORTHWEST);
            content.add(TRANSPORTSTREETLSOUTHEAST);
            content.add(TRANSPORTSTREETLSOUTHWEST);
            content.add(TRANSPORTSTREETROUNDABOUTNORTHEASTSOUTHWEST);
            content.add(TRANSPORTSTREETTNORTHEASTWEST);
            content.add(TRANSPORTSTREETTNORTHSOUTHEAST);
            content.add(TRANSPORTSTREETTNORTHSOUTHWEST);
            content.add(TRANSPORTSTREETTSOUTHEASTWEST);
            content.add(TRANSPORTSTREETXNORTHEASTSOUTHWEST);
            content.add(TRANSPORTWATER1CORNERNORTHEAST);
            content.add(TRANSPORTWATER1CORNERNORTHWEST);
            content.add(TRANSPORTWATER1CORNERSOUTHEAST);
            content.add(TRANSPORTWATER1CORNERSOUTHWEST);
            content.add(TRANSPORTWATER2CORNEREAST);
            content.add(TRANSPORTWATER2CORNERNORTH);
            content.add(TRANSPORTWATER2CORNERSOUTH);
            content.add(TRANSPORTWATER2CORNERWEST);
            content.add(TRANSPORTWATER3CORNERNORTHEAST_NORTHWEST_SOUTHEAST);
            content.add(TRANSPORTWATER3CORNERNORTHEAST_NORTHWEST_SOUTHWEST);
            content.add(TRANSPORTWATER3CORNERSOUTHEAST_SOUTHWEST_NORTHEAST);
            content.add(TRANSPORTWATER3CORNERSOUTHEAST_SOUTHWEST_NORTHWEST);
            content.add(TRANSPORTWATER4CORNERNORTHSOUTHEASTWEST);
        });

        ItemGroupEvents.modifyEntriesEvent(IMSMNew.UTILITY_KEY).register(content -> {
            content.add(BLOCKWATERSLIDE);
            content.add(UTILITYPOWER_NUCLEAREAST);
            content.add(UTILITYPOWER_NUCLEARNORTH);
            content.add(UTILITYPOWER_NUCLEARSOUTH);
            content.add(UTILITYPOWER_NUCLEARWEST);
            content.add(UTILITYPOWER_OILCOALEAST);
            content.add(UTILITYPOWER_OILCOALNORTH);
            content.add(UTILITYPOWER_OILCOALSOUTH);
            content.add(UTILITYPOWER_OILCOALWEST);
            content.add(UTILITYPOWER_SUNNORTHEASTSOUTHWEST);
            content.add(UTILITYPOWER_WINDEAST);
            content.add(UTILITYPOWER_WINDNORTH);
            content.add(UTILITYPOWER_WINDSOUTH);
            content.add(UTILITYPOWER_WINDWEST);
            content.add(UTILITYPUMPJACKEASTWEST);
            content.add(UTILITYPUMPJACKNORTHSOUTH);
            content.add(UTILITYSCRAP_BURNINGEAST);
            content.add(UTILITYSCRAP_BURNINGNORTH);
            content.add(UTILITYSCRAP_BURNINGSOUTH);
            content.add(UTILITYSCRAP_BURNINGWEST);
            content.add(UTILITYSCRAP_HEAPEAST);
            content.add(UTILITYSCRAP_HEAPNORTH);
            content.add(UTILITYSCRAP_HEAPSOUTH);
            content.add(UTILITYSCRAP_HEAPWEST);
            content.add(UTILITYSCRAP_RECYCLEEAST);
            content.add(UTILITYSCRAP_RECYCLENORTH);
            content.add(UTILITYSCRAP_RECYCLESOUTH);
            content.add(UTILITYSCRAP_RECYCLEWEST);
            content.add(UTILITYWATER_PUMPEAST);
            content.add(UTILITYWATER_PUMPNORTH);
            content.add(UTILITYWATER_PUMPSOUTH);
            content.add(UTILITYWATER_PUMPWEST);
            content.add(UTILITYWATER_TOWERNORTHEASTSOUTHWEST);
            content.add(UTILITYWATER_TREATMENTEAST);
            content.add(UTILITYWATER_TREATMENTNORTH);
            content.add(UTILITYWATER_TREATMENTSOUTH);
            content.add(UTILITYWATER_TREATMENTWEST);
        });

    }
}

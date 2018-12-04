package modid.imsm.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import modid.imsm.livestructures.BlockFerrisWheel;
import modid.imsm.livestructures.LiveAirBalloon;
import modid.imsm.livestructures.LiveAirplane;
import modid.imsm.livestructures.LiveBoat;
import modid.imsm.livestructures.LiveFlyingShip;
import modid.imsm.livestructures.LiveFlyingShip2;
import modid.imsm.livestructures.LivePlane;
import modid.imsm.livestructures.LiveStructureRemover;
import modid.imsm.livestructures.Live_Bus;
import modid.imsm.livestructures.Live_Bus2;
import modid.imsm.livestructures.Live_Cinema;
import modid.imsm.livestructures.Live_Fair_FreeFall;
import modid.imsm.livestructures.Live_Flying_Helicopter;
import modid.imsm.livestructures.Live_Helicopter;
import modid.imsm.livestructures.Live_Mill;
import modid.imsm.livestructures.Live_Power_Windmill_East;
import modid.imsm.livestructures.Live_WaterMill;
import modid.imsm.structures.*;
import modid.imsm.userstructures.BlockUnlimited;
import modid.imsm.userstructures.BlockUserStructure;
import modid.imsm.userstructures.PMCParser;
import modid.imsm.worldgeneration.BlockBigWorld;
import modid.imsm.worldgeneration.BlockCheckerboard;
import modid.imsm.worldgeneration.BlockCloud;
import modid.imsm.worldgeneration.LiveCommand;
import modid.imsm.worldgeneration.MazeCommand;
import modid.imsm.worldgeneration.RideCommand;
import modid.imsm.worldgeneration.UndoCommand;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod (modid = "imsm",name = "imsm", version = "1.8")
//@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class IMSM {
	
	//public static CreativeTabs Structures = new Structures(CreativeTabs.getNextID(),"Structures");
	public static String modid = "imsm";
	
	//public static int lastBlockPlacedX = 1;
	//public static int lastBlockPlacedY = 1;
	//public static int lastBlockPlacedZ = 1;
	
	//public static StructureCreator lastPlaced;
	
	//public static World[] worlds = new World[2];
	
	public static boolean updateChecked = false;
	
	@SidedProxy(clientSide = "modid.imsm.core.IMSMClient",serverSide = "modid.imsm.core.IMSMProxy")
	  public static IMSMProxy proxy;
	
	public static modid.imsm.core.EventHandler eventHandler;
	
	public static CreativeTabs Structures = new CreativeTabs("Structures"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.BlockMegaHouse);
		}		
	};
	
	public static CreativeTabs Decoration = new CreativeTabs("Decoration"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.DecorationParkSouth);
		}		
	};
	
	public static CreativeTabs Food = new CreativeTabs("Food"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.FoodFarmSouth);
		}		
	};
	
	public static CreativeTabs IndustryHigh_Density = new CreativeTabs("IndustryHigh_Density"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.IndustryHigh_DensityBlueEast);
		}		
	};
	
	public static CreativeTabs IndustryMedium_Density = new CreativeTabs("IndustryMedium_Density"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.IndustryMedium_DensityBrickWest);
		}		
	};
	
	public static CreativeTabs IndustryLow_Density = new CreativeTabs("IndustryLow_Density"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.IndustryLow_DensityGreenNorth);
		}		
	};
	
	public static CreativeTabs Office = new CreativeTabs("Office"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.OfficeHigh_DensityBrickEastWest);
		}		
	};
	
	public static CreativeTabs Public = new CreativeTabs("Public"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.PublicFireServiceBigEast);
		}		
	};
	
	public static CreativeTabs ResidentalEnormous_Density = new CreativeTabs("ResidentalEnormous_Density"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.ResidentalEnormous_DensityBlockNorthEastSouthWest);
		}		
	};
	
	public static CreativeTabs ResidentalHigh_Density = new CreativeTabs("ResidentalHigh_Density"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.ResidentalHigh_DensityBrickEastWest);
		}		
	};
	
	public static CreativeTabs ResidentalMedium_Density = new CreativeTabs("ResidentalMedium_Density"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.ResidentalMedium_DensityOrangeGreenEast);
		}		
	};
	
	public static CreativeTabs ResidentalLow_Density = new CreativeTabs("ResidentalLow_Density"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.ResidentalLow_DensityGreenEast2);
		}		
	};
	
	public static CreativeTabs Shopping = new CreativeTabs("Shopping"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.ShoppingMedium_DensityQuartzEast);
		}		
	};
	
	/*public static CreativeTabs TransportAirport = new CreativeTabs("TransportAirport"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.TransportAirportRunway_EastWestBuilding_South);
		}		
	};*/
	
	public static CreativeTabs TransportHarbour = new CreativeTabs("TransportHarbour"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.TransportHarbourSide2CornerWest);
		}		
	};
	
	public static CreativeTabs TransportPublic = new CreativeTabs("TransportPublic"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.TransportPublicHightramLSouthWest);
		}		
	};
	
	public static CreativeTabs TransportRoads = new CreativeTabs("TransportRoads"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.TransportRoadTNorthSouthWest);
		}		
	};
	
	public static CreativeTabs TransportWater = new CreativeTabs("TransportWater"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.TransportWater2CornerWest);
		}		
	};
	
	public static CreativeTabs Utility = new CreativeTabs("Utility"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.UtilityPower_NuclearEast);
		}		
	};
	
	public static CreativeTabs Remover = new CreativeTabs("Remover"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.RemoverLast);
		}		
	};
	
	public static CreativeTabs Other = new CreativeTabs("Other"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.BlockStadium);
		}		
	};
	
	public static CreativeTabs LiveStructures = new CreativeTabs("LiveStructures"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.Live_Power_Windmill_East);
		}		
	};
	
	public static CreativeTabs User = new CreativeTabs("User"){
		public Item getTabIconItem() {
			return Item.getItemFromBlock(IMSM.BlockUnlimited);
		}		
	};
	
	public static PMCParser pmcParser;
	
	//static EnumToolMaterial EnumToolMaterialRed= EnumHelper.addToolMaterial("RED", 1, 100, 2.1F, 1, 17);
	
	//public static final Item BlueBar = new Item(1000).setMaxStackSize(64).setUnlocalizedName("mmm1").setUnlocalizedName("blueBar").setCreativeTab(IMSM.Materials);
	//LanguageRegistry.addName(IMSM.BlueBar, "Blue B
	public static Block LiveStructureRemover = new LiveStructureRemover().setHardness(1.0F).setUnlocalizedName("LiveStructureRemover").setCreativeTab(IMSM.Remover);

	public static Block DecorationGrassNorthEastSouthWest = new DecorationGrassNorthEastSouthWest(200).setHardness(1.0F).setUnlocalizedName("DecorationGrassNorthEastSouthWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkEast = new DecorationParkEast(201).setHardness(1.0F).setUnlocalizedName("DecorationParkEast").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageEast = new DecorationParkingGarageEast(202).setHardness(1.0F).setUnlocalizedName("DecorationParkingGarageEast").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageNorth = new DecorationParkingGarageNorth(203).setHardness(1.0F).setUnlocalizedName("DecorationParkingGarageNorth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageSouth = new DecorationParkingGarageSouth(204).setHardness(1.0F).setUnlocalizedName("DecorationParkingGarageSouth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageWest = new DecorationParkingGarageWest(205).setHardness(1.0F).setUnlocalizedName("DecorationParkingGarageWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsEast = new DecorationParkingLotsEast(206).setHardness(1.0F).setUnlocalizedName("DecorationParkingLotsEast").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsNorth = new DecorationParkingLotsNorth(207).setHardness(1.0F).setUnlocalizedName("DecorationParkingLotsNorth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsSouth = new DecorationParkingLotsSouth(208).setHardness(1.0F).setUnlocalizedName("DecorationParkingLotsSouth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsWest = new DecorationParkingLotsWest(209).setHardness(1.0F).setUnlocalizedName("DecorationParkingLotsWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkNorth = new DecorationParkNorth(210).setHardness(1.0F).setUnlocalizedName("DecorationParkNorth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkSouth = new DecorationParkSouth(211).setHardness(1.0F).setUnlocalizedName("DecorationParkSouth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkWest = new DecorationParkWest(212).setHardness(1.0F).setUnlocalizedName("DecorationParkWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationPlazaFountainNorthEastSouthWest = new DecorationPlazaFountainNorthEastSouthWest(213).setHardness(1.0F).setUnlocalizedName("DecorationPlazaFountainNorthEastSouthWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationPlazaNorthEastSouthWest = new DecorationPlazaNorthEastSouthWest(214).setHardness(1.0F).setUnlocalizedName("DecorationPlazaNorthEastSouthWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationSoccerStadiumEastWest = new DecorationSoccerStadiumEastWest(215).setHardness(1.0F).setUnlocalizedName("DecorationSoccerStadiumEastWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationSoccerStadiumNorthSouth = new DecorationSoccerStadiumNorthSouth(216).setHardness(1.0F).setUnlocalizedName("DecorationSoccerStadiumNorthSouth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareNorthEastSouthWest = new DecorationSquareNorthEastSouthWest(217).setHardness(1.0F).setUnlocalizedName("DecorationSquareNorthEastSouthWest").setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeEast = new DecorationSquareTreeEast(218).setHardness(1.0F).setUnlocalizedName("DecorationSquareTreeEast").setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeNorth = new DecorationSquareTreeNorth(219).setHardness(1.0F).setUnlocalizedName("DecorationSquareTreeNorth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeSouth = new DecorationSquareTreeSouth(220).setHardness(1.0F).setUnlocalizedName("DecorationSquareTreeSouth").setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeWest = new DecorationSquareTreeWest(221).setHardness(1.0F).setUnlocalizedName("DecorationSquareTreeWest").setCreativeTab(IMSM.Decoration);
	public static Block FoodCarrotsEastWest = new FoodCarrotsEastWest(222).setHardness(1.0F).setUnlocalizedName("FoodCarrotsEastWest").setCreativeTab(IMSM.Food);
	public static Block FoodCarrotsNorthSouth = new FoodCarrotsNorthSouth(223).setHardness(1.0F).setUnlocalizedName("FoodCarrotsNorthSouth").setCreativeTab(IMSM.Food);
	public static Block FoodFarmEast = new FoodFarmEast(224).setHardness(1.0F).setUnlocalizedName("FoodFarmEast").setCreativeTab(IMSM.Food);
	public static Block FoodFarmNorth = new FoodFarmNorth(225).setHardness(1.0F).setUnlocalizedName("FoodFarmNorth").setCreativeTab(IMSM.Food);
	public static Block FoodFarmSouth = new FoodFarmSouth(226).setHardness(1.0F).setUnlocalizedName("FoodFarmSouth").setCreativeTab(IMSM.Food);
	public static Block FoodFarmWest = new FoodFarmWest(227).setHardness(1.0F).setUnlocalizedName("FoodFarmWest").setCreativeTab(IMSM.Food);
	public static Block FoodPotatoesNorthEastSouthWest = new FoodPotatoesNorthEastSouthWest(228).setHardness(1.0F).setUnlocalizedName("FoodPotatoesNorthEastSouthWest").setCreativeTab(IMSM.Food);
	public static Block FoodStableEastWest = new FoodStableEastWest(229).setHardness(1.0F).setUnlocalizedName("FoodStableEastWest").setCreativeTab(IMSM.Food);
	public static Block FoodStableNorthSouth = new FoodStableNorthSouth(230).setHardness(1.0F).setUnlocalizedName("FoodStableNorthSouth").setCreativeTab(IMSM.Food);
	public static Block FoodWheatNorthEastSouthWest = new FoodWheatNorthEastSouthWest(231).setHardness(1.0F).setUnlocalizedName("FoodWheatNorthEastSouthWest").setCreativeTab(IMSM.Food);
	public static Block IndustryHigh_DensityBlueEast = new IndustryHigh_DensityBlueEast(232).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBlueEast").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBlueNorth = new IndustryHigh_DensityBlueNorth(233).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBlueNorth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBlueSouth = new IndustryHigh_DensityBlueSouth(234).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBlueSouth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBlueWest = new IndustryHigh_DensityBlueWest(235).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBlueWest").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickEast = new IndustryHigh_DensityBrickEast(236).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBrickEast").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickNorth = new IndustryHigh_DensityBrickNorth(237).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBrickNorth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickSouth = new IndustryHigh_DensityBrickSouth(238).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBrickSouth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickWest = new IndustryHigh_DensityBrickWest(239).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityBrickWest").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneyEast = new IndustryHigh_DensityChimneyEast(240).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityChimneyEast").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneyNorth = new IndustryHigh_DensityChimneyNorth(241).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityChimneyNorth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneySouth = new IndustryHigh_DensityChimneySouth(242).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityChimneySouth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneyWest = new IndustryHigh_DensityChimneyWest(243).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityChimneyWest").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipEast = new IndustryHigh_DensityComputerChipEast(244).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityComputerChipEast").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipNorth = new IndustryHigh_DensityComputerChipNorth(245).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityComputerChipNorth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipSouth = new IndustryHigh_DensityComputerChipSouth(246).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityComputerChipSouth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipWest = new IndustryHigh_DensityComputerChipWest(247).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityComputerChipWest").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenEast = new IndustryHigh_DensityGreenEast(248).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityGreenEast").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenNorth = new IndustryHigh_DensityGreenNorth(249).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityGreenNorth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenSouth = new IndustryHigh_DensityGreenSouth(250).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityGreenSouth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenWest = new IndustryHigh_DensityGreenWest(251).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityGreenWest").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueEast = new IndustryHigh_DensityLightBlueEast(252).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityLightBlueEast").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueNorth = new IndustryHigh_DensityLightBlueNorth(253).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityLightBlueNorth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueSouth = new IndustryHigh_DensityLightBlueSouth(254).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityLightBlueSouth").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueWest = new IndustryHigh_DensityLightBlueWest(255).setHardness(1.0F).setUnlocalizedName("IndustryHigh_DensityLightBlueWest").setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryLow_Density3DPrintingEast = new IndustryLow_Density3DPrintingEast(256).setHardness(1.0F).setUnlocalizedName("IndustryLow_Density3DPrintingEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_Density3DPrintingNorth = new IndustryLow_Density3DPrintingNorth(257).setHardness(1.0F).setUnlocalizedName("IndustryLow_Density3DPrintingNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_Density3DPrintingSouth = new IndustryLow_Density3DPrintingSouth(258).setHardness(1.0F).setUnlocalizedName("IndustryLow_Density3DPrintingSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_Density3DPrintingWest = new IndustryLow_Density3DPrintingWest(259).setHardness(1.0F).setUnlocalizedName("IndustryLow_Density3DPrintingWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueEast = new IndustryLow_DensityBlueEast(260).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBlueEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueNorth = new IndustryLow_DensityBlueNorth(261).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBlueNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueSouth = new IndustryLow_DensityBlueSouth(262).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBlueSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueWest = new IndustryLow_DensityBlueWest(263).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBlueWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickEast = new IndustryLow_DensityBrickEast(264).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrickEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickEastWest = new IndustryLow_DensityBrickEastWest(265).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrickEastWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickNorth = new IndustryLow_DensityBrickNorth(266).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrickNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickNorthSouth = new IndustryLow_DensityBrickNorthSouth(267).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrickNorthSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickSouth = new IndustryLow_DensityBrickSouth(268).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrickSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickWest = new IndustryLow_DensityBrickWest(269).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrickWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownEast2 = new IndustryLow_DensityBrownEast2(270).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownEast2").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownEast = new IndustryLow_DensityBrownEast(271).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownNorth2 = new IndustryLow_DensityBrownNorth2(272).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownNorth2").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownNorth = new IndustryLow_DensityBrownNorth(273).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownSouth2 = new IndustryLow_DensityBrownSouth2(274).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownSouth2").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownSouth = new IndustryLow_DensityBrownSouth(275).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownWest2 = new IndustryLow_DensityBrownWest2(276).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownWest2").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownWest = new IndustryLow_DensityBrownWest(277).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityBrownWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneyEast = new IndustryLow_DensityChimneyEast(278).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityChimneyEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneyNorth = new IndustryLow_DensityChimneyNorth(279).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityChimneyNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneySouth = new IndustryLow_DensityChimneySouth(280).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityChimneySouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneyWest = new IndustryLow_DensityChimneyWest(281).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityChimneyWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenEast = new IndustryLow_DensityGreenEast(282).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityGreenEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenNorth = new IndustryLow_DensityGreenNorth(283).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityGreenNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenSouth = new IndustryLow_DensityGreenSouth(284).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityGreenSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenWest = new IndustryLow_DensityGreenWest(285).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityGreenWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronEast = new IndustryLow_DensityIronEast(286).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityIronEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronNorth = new IndustryLow_DensityIronNorth(287).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityIronNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronSouth = new IndustryLow_DensityIronSouth(288).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityIronSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronWest = new IndustryLow_DensityIronWest(289).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityIronWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaEast = new IndustryLow_DensityParabolicAntennaEast(290).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityParabolicAntennaEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaNorth = new IndustryLow_DensityParabolicAntennaNorth(291).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityParabolicAntennaNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaSouth = new IndustryLow_DensityParabolicAntennaSouth(292).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityParabolicAntennaSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaWest = new IndustryLow_DensityParabolicAntennaWest(293).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityParabolicAntennaWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTankNorthEastSouthWest = new IndustryLow_DensityTankNorthEastSouthWest(294).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityTankNorthEastSouthWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeEast = new IndustryLow_DensityTelescopeEast(295).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityTelescopeEast").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeNorth = new IndustryLow_DensityTelescopeNorth(296).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityTelescopeNorth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeSouth = new IndustryLow_DensityTelescopeSouth(297).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityTelescopeSouth").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeWest = new IndustryLow_DensityTelescopeWest(298).setHardness(1.0F).setUnlocalizedName("IndustryLow_DensityTelescopeWest").setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryMedium_DensityBlueEast = new IndustryMedium_DensityBlueEast(299).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBlueEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBlueNorth = new IndustryMedium_DensityBlueNorth(300).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBlueNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBlueSouth = new IndustryMedium_DensityBlueSouth(301).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBlueSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBlueWest = new IndustryMedium_DensityBlueWest(302).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBlueWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickEast = new IndustryMedium_DensityBrickEast(303).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrickEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickNorth = new IndustryMedium_DensityBrickNorth(304).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrickNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickSouth = new IndustryMedium_DensityBrickSouth(305).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrickSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickWest = new IndustryMedium_DensityBrickWest(306).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrickWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownEast = new IndustryMedium_DensityBrownEast(307).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrownEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownNorth = new IndustryMedium_DensityBrownNorth(308).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrownNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownSouth = new IndustryMedium_DensityBrownSouth(309).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrownSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownWest = new IndustryMedium_DensityBrownWest(310).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityBrownWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChemicalPressEastWest = new IndustryMedium_DensityChemicalPressEastWest(311).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityChemicalPressEastWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChemicalPressNorthSouth = new IndustryMedium_DensityChemicalPressNorthSouth(312).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityChemicalPressNorthSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneyEast = new IndustryMedium_DensityChimneyEast(313).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityChimneyEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneyNorth = new IndustryMedium_DensityChimneyNorth(314).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityChimneyNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneySouth = new IndustryMedium_DensityChimneySouth(315).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityChimneySouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneyWest = new IndustryMedium_DensityChimneyWest(316).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityChimneyWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenEast = new IndustryMedium_DensityGreenEast(317).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityGreenEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenNorth = new IndustryMedium_DensityGreenNorth(318).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityGreenNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenSouth = new IndustryMedium_DensityGreenSouth(319).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityGreenSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenWest = new IndustryMedium_DensityGreenWest(320).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityGreenWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceEast = new IndustryMedium_DensityIceEast(321).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityIceEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceNorth = new IndustryMedium_DensityIceNorth(322).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityIceNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceSouth = new IndustryMedium_DensityIceSouth(323).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityIceSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceWest = new IndustryMedium_DensityIceWest(324).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityIceWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneEast = new IndustryMedium_DensitySandstoneEast(325).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensitySandstoneEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneNorth = new IndustryMedium_DensitySandstoneNorth(326).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensitySandstoneNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneSouth = new IndustryMedium_DensitySandstoneSouth(327).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensitySandstoneSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneWest = new IndustryMedium_DensitySandstoneWest(328).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensitySandstoneWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankEast = new IndustryMedium_DensityTankEast(329).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityTankEast").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankNorth = new IndustryMedium_DensityTankNorth(330).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityTankNorth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankSouth = new IndustryMedium_DensityTankSouth(331).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityTankSouth").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankWest = new IndustryMedium_DensityTankWest(332).setHardness(1.0F).setUnlocalizedName("IndustryMedium_DensityTankWest").setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block OfficeHigh_DensityBrickEastWest = new OfficeHigh_DensityBrickEastWest(333).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityBrickEastWest").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityBrickNorthSouth = new OfficeHigh_DensityBrickNorthSouth(334).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityBrickNorthSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanEast = new OfficeHigh_DensityCyanEast(335).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityCyanEast").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanNorth = new OfficeHigh_DensityCyanNorth(336).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityCyanNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanSouth = new OfficeHigh_DensityCyanSouth(337).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityCyanSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanWest = new OfficeHigh_DensityCyanWest(338).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityCyanWest").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopEast = new OfficeHigh_DensityHoleOnTopEast(339).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityHoleOnTopEast").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopNorth = new OfficeHigh_DensityHoleOnTopNorth(340).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityHoleOnTopNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopSouth = new OfficeHigh_DensityHoleOnTopSouth(341).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityHoleOnTopSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopWest = new OfficeHigh_DensityHoleOnTopWest(342).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityHoleOnTopWest").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityLightBlueEastWest = new OfficeHigh_DensityLightBlueEastWest(343).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityLightBlueEastWest").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityLightBlueNorthSouth = new OfficeHigh_DensityLightBlueNorthSouth(344).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensityLightBlueNorthSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingEast = new OfficeHigh_DensitySpirolBuildingEast(345).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensitySpirolBuildingEast").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingNorth = new OfficeHigh_DensitySpirolBuildingNorth(346).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensitySpirolBuildingNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingSouth = new OfficeHigh_DensitySpirolBuildingSouth(347).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensitySpirolBuildingSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingWest = new OfficeHigh_DensitySpirolBuildingWest(348).setHardness(1.0F).setUnlocalizedName("OfficeHigh_DensitySpirolBuildingWest").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueEast = new OfficeLow_DensityBlueEast(349).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityBlueEast").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueNorth = new OfficeLow_DensityBlueNorth(350).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityBlueNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueSouth = new OfficeLow_DensityBlueSouth(351).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityBlueSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueWest = new OfficeLow_DensityBlueWest(352).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityBlueWest").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenEast = new OfficeLow_DensityGreenEast(353).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityGreenEast").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenNorth = new OfficeLow_DensityGreenNorth(354).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityGreenNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenSouth = new OfficeLow_DensityGreenSouth(355).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityGreenSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenWest = new OfficeLow_DensityGreenWest(356).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityGreenWest").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteEast = new OfficeLow_DensityWhiteEast(357).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityWhiteEast").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteNorth = new OfficeLow_DensityWhiteNorth(358).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityWhiteNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteSouth = new OfficeLow_DensityWhiteSouth(359).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityWhiteSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteWest = new OfficeLow_DensityWhiteWest(360).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityWhiteWest").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowEast = new OfficeLow_DensityYellowEast(361).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityYellowEast").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowNorth = new OfficeLow_DensityYellowNorth(362).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityYellowNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowSouth = new OfficeLow_DensityYellowSouth(363).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityYellowSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowWest = new OfficeLow_DensityYellowWest(364).setHardness(1.0F).setUnlocalizedName("OfficeLow_DensityYellowWest").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanEast = new OfficeMedium_DensityCyanEast(365).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityCyanEast").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanNorth = new OfficeMedium_DensityCyanNorth(366).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityCyanNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanSouth = new OfficeMedium_DensityCyanSouth(367).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityCyanSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanWest = new OfficeMedium_DensityCyanWest(368).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityCyanWest").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueEast = new OfficeMedium_DensityLightBlueEast(369).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityLightBlueEast").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueNorth = new OfficeMedium_DensityLightBlueNorth(370).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityLightBlueNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueSouth = new OfficeMedium_DensityLightBlueSouth(371).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityLightBlueSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueWest = new OfficeMedium_DensityLightBlueWest(372).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityLightBlueWest").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkEast = new OfficeMedium_DensityPinkEast(373).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityPinkEast").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkNorth = new OfficeMedium_DensityPinkNorth(374).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityPinkNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkSouth = new OfficeMedium_DensityPinkSouth(375).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityPinkSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkWest = new OfficeMedium_DensityPinkWest(376).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensityPinkWest").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneEast = new OfficeMedium_DensitySandstoneEast(377).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensitySandstoneEast").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneNorth = new OfficeMedium_DensitySandstoneNorth(378).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensitySandstoneNorth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneSouth = new OfficeMedium_DensitySandstoneSouth(379).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensitySandstoneSouth").setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneWest = new OfficeMedium_DensitySandstoneWest(380).setHardness(1.0F).setUnlocalizedName("OfficeMedium_DensitySandstoneWest").setCreativeTab(IMSM.Office);
	public static Block PublicFireServiceBigEast = new PublicFireServiceBigEast(381).setHardness(1.0F).setUnlocalizedName("PublicFireServiceBigEast").setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceBigNorth = new PublicFireServiceBigNorth(382).setHardness(1.0F).setUnlocalizedName("PublicFireServiceBigNorth").setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceBigSouth = new PublicFireServiceBigSouth(383).setHardness(1.0F).setUnlocalizedName("PublicFireServiceBigSouth").setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceBigWest = new PublicFireServiceBigWest(384).setHardness(1.0F).setUnlocalizedName("PublicFireServiceBigWest").setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallEast = new PublicFireServiceSmallEast(385).setHardness(1.0F).setUnlocalizedName("PublicFireServiceSmallEast").setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallNorth = new PublicFireServiceSmallNorth(386).setHardness(1.0F).setUnlocalizedName("PublicFireServiceSmallNorth").setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallSouth = new PublicFireServiceSmallSouth(387).setHardness(1.0F).setUnlocalizedName("PublicFireServiceSmallSouth").setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallWest = new PublicFireServiceSmallWest(388).setHardness(1.0F).setUnlocalizedName("PublicFireServiceSmallWest").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigEast = new PublicHospitalBigEast(389).setHardness(1.0F).setUnlocalizedName("PublicHospitalBigEast").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigNorth = new PublicHospitalBigNorth(390).setHardness(1.0F).setUnlocalizedName("PublicHospitalBigNorth").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigSouth = new PublicHospitalBigSouth(391).setHardness(1.0F).setUnlocalizedName("PublicHospitalBigSouth").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigWest = new PublicHospitalBigWest(392).setHardness(1.0F).setUnlocalizedName("PublicHospitalBigWest").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallEast = new PublicHospitalSmallEast(393).setHardness(1.0F).setUnlocalizedName("PublicHospitalSmallEast").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallNorth = new PublicHospitalSmallNorth(394).setHardness(1.0F).setUnlocalizedName("PublicHospitalSmallNorth").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallSouth = new PublicHospitalSmallSouth(395).setHardness(1.0F).setUnlocalizedName("PublicHospitalSmallSouth").setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallWest = new PublicHospitalSmallWest(396).setHardness(1.0F).setUnlocalizedName("PublicHospitalSmallWest").setCreativeTab(IMSM.Public);
	public static Block PublicLibraryEastWest = new PublicLibraryEastWest(397).setHardness(1.0F).setUnlocalizedName("PublicLibraryEastWest").setCreativeTab(IMSM.Public);
	public static Block PublicLibraryNorthSouth = new PublicLibraryNorthSouth(398).setHardness(1.0F).setUnlocalizedName("PublicLibraryNorthSouth").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigEast = new PublicPoliceBigEast(399).setHardness(1.0F).setUnlocalizedName("PublicPoliceBigEast").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigNorth = new PublicPoliceBigNorth(400).setHardness(1.0F).setUnlocalizedName("PublicPoliceBigNorth").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigSouth = new PublicPoliceBigSouth(401).setHardness(1.0F).setUnlocalizedName("PublicPoliceBigSouth").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigWest = new PublicPoliceBigWest(402).setHardness(1.0F).setUnlocalizedName("PublicPoliceBigWest").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallEast = new PublicPoliceSmallEast(403).setHardness(1.0F).setUnlocalizedName("PublicPoliceSmallEast").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallNorth = new PublicPoliceSmallNorth(404).setHardness(1.0F).setUnlocalizedName("PublicPoliceSmallNorth").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallSouth = new PublicPoliceSmallSouth(405).setHardness(1.0F).setUnlocalizedName("PublicPoliceSmallSouth").setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallWest = new PublicPoliceSmallWest(406).setHardness(1.0F).setUnlocalizedName("PublicPoliceSmallWest").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigNorthEast = new PublicSchoolBigNorthEast(407).setHardness(1.0F).setUnlocalizedName("PublicSchoolBigNorthEast").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigNorthWest = new PublicSchoolBigNorthWest(408).setHardness(1.0F).setUnlocalizedName("PublicSchoolBigNorthWest").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigSouthEast = new PublicSchoolBigSouthEast(409).setHardness(1.0F).setUnlocalizedName("PublicSchoolBigSouthEast").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigSouthWest = new PublicSchoolBigSouthWest(410).setHardness(1.0F).setUnlocalizedName("PublicSchoolBigSouthWest").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallNorthEast = new PublicSchoolSmallNorthEast(411).setHardness(1.0F).setUnlocalizedName("PublicSchoolSmallNorthEast").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallNorthWest = new PublicSchoolSmallNorthWest(412).setHardness(1.0F).setUnlocalizedName("PublicSchoolSmallNorthWest").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallSouthEast = new PublicSchoolSmallSouthEast(413).setHardness(1.0F).setUnlocalizedName("PublicSchoolSmallSouthEast").setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallSouthWest = new PublicSchoolSmallSouthWest(414).setHardness(1.0F).setUnlocalizedName("PublicSchoolSmallSouthWest").setCreativeTab(IMSM.Public);
	public static Block PublicTownhallBigEastWest = new PublicTownhallBigEastWest(415).setHardness(1.0F).setUnlocalizedName("PublicTownhallBigEastWest").setCreativeTab(IMSM.Public);
	public static Block PublicTownhallBigNorthSouth = new PublicTownhallBigNorthSouth(416).setHardness(1.0F).setUnlocalizedName("PublicTownhallBigNorthSouth").setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallEast = new PublicTownhallSmallEast(417).setHardness(1.0F).setUnlocalizedName("PublicTownhallSmallEast").setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallNorth = new PublicTownhallSmallNorth(418).setHardness(1.0F).setUnlocalizedName("PublicTownhallSmallNorth").setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallSouth = new PublicTownhallSmallSouth(419).setHardness(1.0F).setUnlocalizedName("PublicTownhallSmallSouth").setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallWest = new PublicTownhallSmallWest(420).setHardness(1.0F).setUnlocalizedName("PublicTownhallSmallWest").setCreativeTab(IMSM.Public);
	public static Block PublicUniversityEast = new PublicUniversityEast(421).setHardness(1.0F).setUnlocalizedName("PublicUniversityEast").setCreativeTab(IMSM.Public);
	public static Block PublicUniversityNorth = new PublicUniversityNorth(422).setHardness(1.0F).setUnlocalizedName("PublicUniversityNorth").setCreativeTab(IMSM.Public);
	public static Block PublicUniversitySouth = new PublicUniversitySouth(423).setHardness(1.0F).setUnlocalizedName("PublicUniversitySouth").setCreativeTab(IMSM.Public);
	public static Block PublicUniversityWest = new PublicUniversityWest(424).setHardness(1.0F).setUnlocalizedName("PublicUniversityWest").setCreativeTab(IMSM.Public);
	public static Block ResidentalEnormous_DensityBlockNorthEastSouthWest = new ResidentalEnormous_DensityBlockNorthEastSouthWest(425).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityBlockNorthEastSouthWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigEast = new ResidentalEnormous_DensityBrickBigEast(426).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityBrickBigEast").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigNorth = new ResidentalEnormous_DensityBrickBigNorth(427).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityBrickBigNorth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigSouth = new ResidentalEnormous_DensityBrickBigSouth(428).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityBrickBigSouth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigWest = new ResidentalEnormous_DensityBrickBigWest(429).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityBrickBigWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickSmallNorthEastSouthWest = new ResidentalEnormous_DensityBrickSmallNorthEastSouthWest(430).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityBrickSmallNorthEastSouthWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreyEast = new ResidentalEnormous_DensityGreyEast(431).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityGreyEast").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreyNorth = new ResidentalEnormous_DensityGreyNorth(432).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityGreyNorth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreySouth = new ResidentalEnormous_DensityGreySouth(433).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityGreySouth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreyWest = new ResidentalEnormous_DensityGreyWest(434).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityGreyWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernEast = new ResidentalEnormous_DensityModernEast(435).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityModernEast").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernNorth = new ResidentalEnormous_DensityModernNorth(436).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityModernNorth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernSouth = new ResidentalEnormous_DensityModernSouth(437).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityModernSouth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernWest = new ResidentalEnormous_DensityModernWest(438).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityModernWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityRedEastWest = new ResidentalEnormous_DensityRedEastWest(439).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityRedEastWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityRedNorthSouth = new ResidentalEnormous_DensityRedNorthSouth(440).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityRedNorthSouth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityRoundNorthEastSouthWest = new ResidentalEnormous_DensityRoundNorthEastSouthWest(441).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityRoundNorthEastSouthWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneEast2 = new ResidentalEnormous_DensityStoneEast2(442).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneEast2").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneEast = new ResidentalEnormous_DensityStoneEast(443).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneEast").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneNorth2 = new ResidentalEnormous_DensityStoneNorth2(444).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneNorth2").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneNorth = new ResidentalEnormous_DensityStoneNorth(445).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneNorth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneSouth2 = new ResidentalEnormous_DensityStoneSouth2(446).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneSouth2").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneSouth = new ResidentalEnormous_DensityStoneSouth(447).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneSouth").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneWest2 = new ResidentalEnormous_DensityStoneWest2(448).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneWest2").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneWest = new ResidentalEnormous_DensityStoneWest(449).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityStoneWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityYellowNorthEastSouthWest = new ResidentalEnormous_DensityYellowNorthEastSouthWest(450).setHardness(1.0F).setUnlocalizedName("ResidentalEnormous_DensityYellowNorthEastSouthWest").setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalHigh_DensityBlueEast = new ResidentalHigh_DensityBlueEast(451).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBlueEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueEastWest = new ResidentalHigh_DensityBlueEastWest(452).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBlueEastWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueNorth = new ResidentalHigh_DensityBlueNorth(453).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBlueNorth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueNorthSouth = new ResidentalHigh_DensityBlueNorthSouth(454).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBlueNorthSouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueSouth = new ResidentalHigh_DensityBlueSouth(455).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBlueSouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueWest = new ResidentalHigh_DensityBlueWest(456).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBlueWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickEast = new ResidentalHigh_DensityBrickEast(457).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBrickEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickEastWest = new ResidentalHigh_DensityBrickEastWest(458).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBrickEastWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickNorth = new ResidentalHigh_DensityBrickNorth(459).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBrickNorth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickNorthSouth = new ResidentalHigh_DensityBrickNorthSouth(460).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBrickNorthSouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickSouth = new ResidentalHigh_DensityBrickSouth(461).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBrickSouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickWest = new ResidentalHigh_DensityBrickWest(462).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityBrickWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreyEast = new ResidentalHigh_DensityGreenGreyEast(463).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityGreenGreyEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreyNorth = new ResidentalHigh_DensityGreenGreyNorth(464).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityGreenGreyNorth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreySouth = new ResidentalHigh_DensityGreenGreySouth(465).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityGreenGreySouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreyWest = new ResidentalHigh_DensityGreenGreyWest(466).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityGreenGreyWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerNorthEast = new ResidentalHigh_DensityRedCornerNorthEast(467).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedCornerNorthEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerNorthWest = new ResidentalHigh_DensityRedCornerNorthWest(468).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedCornerNorthWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerSouthEast = new ResidentalHigh_DensityRedCornerSouthEast(469).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedCornerSouthEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerSouthWest = new ResidentalHigh_DensityRedCornerSouthWest(470).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedCornerSouthWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowEast = new ResidentalHigh_DensityRedYellowEast(471).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedYellowEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowNorth = new ResidentalHigh_DensityRedYellowNorth(472).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedYellowNorth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowSouth = new ResidentalHigh_DensityRedYellowSouth(473).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedYellowSouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowWest = new ResidentalHigh_DensityRedYellowWest(474).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityRedYellowWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneEast2 = new ResidentalHigh_DensityStoneEast2(475).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneEast2").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneEast = new ResidentalHigh_DensityStoneEast(476).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneNorth2 = new ResidentalHigh_DensityStoneNorth2(477).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneNorth2").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneNorth = new ResidentalHigh_DensityStoneNorth(478).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneNorth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneSouth2 = new ResidentalHigh_DensityStoneSouth2(479).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneSouth2").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneSouth = new ResidentalHigh_DensityStoneSouth(480).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneSouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneWest2 = new ResidentalHigh_DensityStoneWest2(481).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneWest2").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneWest = new ResidentalHigh_DensityStoneWest(482).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityStoneWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowEast = new ResidentalHigh_DensityYellowEast(483).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityYellowEast").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowNorth = new ResidentalHigh_DensityYellowNorth(484).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityYellowNorth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowSouth = new ResidentalHigh_DensityYellowSouth(485).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityYellowSouth").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowWest = new ResidentalHigh_DensityYellowWest(486).setHardness(1.0F).setUnlocalizedName("ResidentalHigh_DensityYellowWest").setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalLow_DensityBeigeEast = new ResidentalLow_DensityBeigeEast(487).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityBeigeEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityBeigeNorth = new ResidentalLow_DensityBeigeNorth(488).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityBeigeNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityBeigeSouth = new ResidentalLow_DensityBeigeSouth(489).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityBeigeSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityBeigeWest = new ResidentalLow_DensityBeigeWest(490).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityBeigeWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanEast = new ResidentalLow_DensityCyanEast(491).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityCyanEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanNorth = new ResidentalLow_DensityCyanNorth(492).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityCyanNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanSouth = new ResidentalLow_DensityCyanSouth(493).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityCyanSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanWest = new ResidentalLow_DensityCyanWest(494).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityCyanWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenEast2 = new ResidentalLow_DensityGreenEast2(495).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenEast2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenEast = new ResidentalLow_DensityGreenEast(496).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenNorth2 = new ResidentalLow_DensityGreenNorth2(497).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenNorth2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenNorth = new ResidentalLow_DensityGreenNorth(498).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenSouth2 = new ResidentalLow_DensityGreenSouth2(499).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenSouth2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenSouth = new ResidentalLow_DensityGreenSouth(500).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenWest2 = new ResidentalLow_DensityGreenWest2(501).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenWest2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenWest = new ResidentalLow_DensityGreenWest(502).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityGreenWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueEast2 = new ResidentalLow_DensityLightBlueEast2(503).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueEast2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueEast = new ResidentalLow_DensityLightBlueEast(504).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueNorth2 = new ResidentalLow_DensityLightBlueNorth2(505).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueNorth2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueNorth = new ResidentalLow_DensityLightBlueNorth(506).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueSouth2 = new ResidentalLow_DensityLightBlueSouth2(507).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueSouth2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueSouth = new ResidentalLow_DensityLightBlueSouth(508).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueWest2 = new ResidentalLow_DensityLightBlueWest2(509).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueWest2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueWest = new ResidentalLow_DensityLightBlueWest(510).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightBlueWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreyEast = new ResidentalLow_DensityLightGreyEast(511).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightGreyEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreyNorth = new ResidentalLow_DensityLightGreyNorth(512).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightGreyNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreySouth = new ResidentalLow_DensityLightGreySouth(513).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightGreySouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreyWest = new ResidentalLow_DensityLightGreyWest(514).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityLightGreyWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernEast = new ResidentalLow_DensityModernEast(515).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityModernEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernNorth = new ResidentalLow_DensityModernNorth(516).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityModernNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernSouth = new ResidentalLow_DensityModernSouth(517).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityModernSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernWest = new ResidentalLow_DensityModernWest(518).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityModernWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeEast = new ResidentalLow_DensityOrangeEast(519).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityOrangeEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeNorth = new ResidentalLow_DensityOrangeNorth(520).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityOrangeNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeSouth = new ResidentalLow_DensityOrangeSouth(521).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityOrangeSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeWest = new ResidentalLow_DensityOrangeWest(522).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityOrangeWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedEast = new ResidentalLow_DensityRedEast(523).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityRedEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedNorth = new ResidentalLow_DensityRedNorth(524).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityRedNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedSouth = new ResidentalLow_DensityRedSouth(525).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityRedSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedWest = new ResidentalLow_DensityRedWest(526).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityRedWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneEast = new ResidentalLow_DensityStoneEast(527).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityStoneEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneNorth = new ResidentalLow_DensityStoneNorth(528).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityStoneNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneSouth = new ResidentalLow_DensityStoneSouth(529).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityStoneSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneWest = new ResidentalLow_DensityStoneWest(530).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityStoneWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteEast = new ResidentalLow_DensityWhiteEast(531).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWhiteEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteNorth = new ResidentalLow_DensityWhiteNorth(532).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWhiteNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteSouth = new ResidentalLow_DensityWhiteSouth(533).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWhiteSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteWest = new ResidentalLow_DensityWhiteWest(534).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWhiteWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodEast = new ResidentalLow_DensityWoodEast(535).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWoodEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodNorth = new ResidentalLow_DensityWoodNorth(536).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWoodNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodSouth = new ResidentalLow_DensityWoodSouth(537).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWoodSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodWest = new ResidentalLow_DensityWoodWest(538).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityWoodWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowEast2 = new ResidentalLow_DensityYellowEast2(539).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowEast2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowEast = new ResidentalLow_DensityYellowEast(540).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowEast").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowNorth2 = new ResidentalLow_DensityYellowNorth2(541).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowNorth2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowNorth = new ResidentalLow_DensityYellowNorth(542).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowNorth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowSouth2 = new ResidentalLow_DensityYellowSouth2(543).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowSouth2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowSouth = new ResidentalLow_DensityYellowSouth(544).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowSouth").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowWest2 = new ResidentalLow_DensityYellowWest2(545).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowWest2").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowWest = new ResidentalLow_DensityYellowWest(546).setHardness(1.0F).setUnlocalizedName("ResidentalLow_DensityYellowWest").setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalMedium_DensityBlueGreenEast = new ResidentalMedium_DensityBlueGreenEast(547).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueGreenEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueGreenNorth = new ResidentalMedium_DensityBlueGreenNorth(548).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueGreenNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueGreenSouth = new ResidentalMedium_DensityBlueGreenSouth(549).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueGreenSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueGreenWest = new ResidentalMedium_DensityBlueGreenWest(550).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueGreenWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedEast = new ResidentalMedium_DensityBlueRedEast(551).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueRedEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedNorth = new ResidentalMedium_DensityBlueRedNorth(552).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueRedNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedSouth = new ResidentalMedium_DensityBlueRedSouth(553).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueRedSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedWest = new ResidentalMedium_DensityBlueRedWest(554).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBlueRedWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickEast = new ResidentalMedium_DensityBrickEast(555).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBrickEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickNorth = new ResidentalMedium_DensityBrickNorth(556).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBrickNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickSouth = new ResidentalMedium_DensityBrickSouth(557).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBrickSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickWest = new ResidentalMedium_DensityBrickWest(558).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityBrickWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalEast = new ResidentalMedium_DensityHorizontalEast(559).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityHorizontalEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalNorth = new ResidentalMedium_DensityHorizontalNorth(560).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityHorizontalNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalSouth = new ResidentalMedium_DensityHorizontalSouth(561).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityHorizontalSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalWest = new ResidentalMedium_DensityHorizontalWest(562).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityHorizontalWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenEast = new ResidentalMedium_DensityOrangeGreenEast(563).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityOrangeGreenEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenNorth = new ResidentalMedium_DensityOrangeGreenNorth(564).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityOrangeGreenNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenSouth = new ResidentalMedium_DensityOrangeGreenSouth(565).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityOrangeGreenSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenWest = new ResidentalMedium_DensityOrangeGreenWest(566).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityOrangeGreenWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzEast = new ResidentalMedium_DensityQuartzEast(567).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityQuartzEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzNorth = new ResidentalMedium_DensityQuartzNorth(568).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityQuartzNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzSouth = new ResidentalMedium_DensityQuartzSouth(569).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityQuartzSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzWest = new ResidentalMedium_DensityQuartzWest(570).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityQuartzWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenEast = new ResidentalMedium_DensityRedGreenEast(571).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRedGreenEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenNorth = new ResidentalMedium_DensityRedGreenNorth(572).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRedGreenNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenSouth = new ResidentalMedium_DensityRedGreenSouth(573).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRedGreenSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenWest = new ResidentalMedium_DensityRedGreenWest(574).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRedGreenWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofEast = new ResidentalMedium_DensityRoofEast(575).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRoofEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofNorth = new ResidentalMedium_DensityRoofNorth(576).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRoofNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofSouth = new ResidentalMedium_DensityRoofSouth(577).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRoofSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofWest = new ResidentalMedium_DensityRoofWest(578).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityRoofWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone1EastWest = new ResidentalMedium_DensityStone1EastWest(579).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStone1EastWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone1NorthSouth = new ResidentalMedium_DensityStone1NorthSouth(580).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStone1NorthSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone2EastWest = new ResidentalMedium_DensityStone2EastWest(581).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStone2EastWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone2NorthSouth = new ResidentalMedium_DensityStone2NorthSouth(582).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStone2NorthSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerNorthEast = new ResidentalMedium_DensityStoneCornerNorthEast(583).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneCornerNorthEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerNorthWest = new ResidentalMedium_DensityStoneCornerNorthWest(584).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneCornerNorthWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerSouthEast = new ResidentalMedium_DensityStoneCornerSouthEast(585).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneCornerSouthEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerSouthWest = new ResidentalMedium_DensityStoneCornerSouthWest(586).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneCornerSouthWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEast = new ResidentalMedium_DensityStoneEast(587).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndNorthEastWest = new ResidentalMedium_DensityStoneEndNorthEastWest(588).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneEndNorthEastWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndNorthSouthEast = new ResidentalMedium_DensityStoneEndNorthSouthEast(589).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneEndNorthSouthEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndNorthSouthWest = new ResidentalMedium_DensityStoneEndNorthSouthWest(590).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneEndNorthSouthWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndSouthEastWest = new ResidentalMedium_DensityStoneEndSouthEastWest(591).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneEndSouthEastWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneNorth = new ResidentalMedium_DensityStoneNorth(592).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneSouth = new ResidentalMedium_DensityStoneSouth(593).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneWest = new ResidentalMedium_DensityStoneWest(594).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityStoneWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalEast = new ResidentalMedium_DensityVerticalEast(595).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityVerticalEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalNorth = new ResidentalMedium_DensityVerticalNorth(596).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityVerticalNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalSouth = new ResidentalMedium_DensityVerticalSouth(597).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityVerticalSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalWest = new ResidentalMedium_DensityVerticalWest(598).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityVerticalWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedEast = new ResidentalMedium_DensityYellowRedEast(599).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityYellowRedEast").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedNorth = new ResidentalMedium_DensityYellowRedNorth(600).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityYellowRedNorth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedSouth = new ResidentalMedium_DensityYellowRedSouth(601).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityYellowRedSouth").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedWest = new ResidentalMedium_DensityYellowRedWest(602).setHardness(1.0F).setUnlocalizedName("ResidentalMedium_DensityYellowRedWest").setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ShoppingHigh_DensityQuartzEastWest = new ShoppingHigh_DensityQuartzEastWest(603).setHardness(1.0F).setUnlocalizedName("ShoppingHigh_DensityQuartzEastWest").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingHigh_DensityQuartzNorthSouth = new ShoppingHigh_DensityQuartzNorthSouth(604).setHardness(1.0F).setUnlocalizedName("ShoppingHigh_DensityQuartzNorthSouth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickEast = new ShoppingLow_DensityBrickEast(605).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityBrickEast").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickNorth = new ShoppingLow_DensityBrickNorth(606).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityBrickNorth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickSouth = new ShoppingLow_DensityBrickSouth(607).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityBrickSouth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickWest = new ShoppingLow_DensityBrickWest(608).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityBrickWest").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenEast = new ShoppingLow_DensityGreenEast(609).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityGreenEast").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenNorth = new ShoppingLow_DensityGreenNorth(610).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityGreenNorth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenSouth = new ShoppingLow_DensityGreenSouth(611).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityGreenSouth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenWest = new ShoppingLow_DensityGreenWest(612).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityGreenWest").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeEast = new ShoppingLow_DensityOrangeEast(613).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityOrangeEast").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeNorth = new ShoppingLow_DensityOrangeNorth(614).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityOrangeNorth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeSouth = new ShoppingLow_DensityOrangeSouth(615).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityOrangeSouth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeWest = new ShoppingLow_DensityOrangeWest(616).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityOrangeWest").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkEast = new ShoppingLow_DensityPinkEast(617).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityPinkEast").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkNorth = new ShoppingLow_DensityPinkNorth(618).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityPinkNorth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkSouth = new ShoppingLow_DensityPinkSouth(619).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityPinkSouth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkWest = new ShoppingLow_DensityPinkWest(620).setHardness(1.0F).setUnlocalizedName("ShoppingLow_DensityPinkWest").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernEast = new ShoppingMedium_DensityModernEast(621).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityModernEast").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernNorth = new ShoppingMedium_DensityModernNorth(622).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityModernNorth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernSouth = new ShoppingMedium_DensityModernSouth(623).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityModernSouth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernWest = new ShoppingMedium_DensityModernWest(624).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityModernWest").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzEast = new ShoppingMedium_DensityQuartzEast(625).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityQuartzEast").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzNorth = new ShoppingMedium_DensityQuartzNorth(626).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityQuartzNorth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzSouth = new ShoppingMedium_DensityQuartzSouth(627).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityQuartzSouth").setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzWest = new ShoppingMedium_DensityQuartzWest(628).setHardness(1.0F).setUnlocalizedName("ShoppingMedium_DensityQuartzWest").setCreativeTab(IMSM.Shopping);
	public static Block TransportAirportRunway_EastWestBuilding_North = new TransportAirportRunway_EastWestBuilding_North(629).setHardness(1.0F).setUnlocalizedName("TransportAirportRunway_EastWestBuilding_North").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAirportRunway_EastWestBuilding_South = new TransportAirportRunway_EastWestBuilding_South(630).setHardness(1.0F).setUnlocalizedName("TransportAirportRunway_EastWestBuilding_South").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAirportRunway_NorthSouthBuilding_East = new TransportAirportRunway_NorthSouthBuilding_East(631).setHardness(1.0F).setUnlocalizedName("TransportAirportRunway_NorthSouthBuilding_East").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAirportRunway_NorthSouthBuilding_West = new TransportAirportRunway_NorthSouthBuilding_West(632).setHardness(1.0F).setUnlocalizedName("TransportAirportRunway_NorthSouthBuilding_West").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAvenue1EastWest = new TransportAvenue1EastWest(633).setHardness(1.0F).setUnlocalizedName("TransportAvenue1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenue1NorthSouth = new TransportAvenue1NorthSouth(634).setHardness(1.0F).setUnlocalizedName("TransportAvenue1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenue2EastWest = new TransportAvenue2EastWest(635).setHardness(1.0F).setUnlocalizedName("TransportAvenue2EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenue2NorthSouth = new TransportAvenue2NorthSouth(636).setHardness(1.0F).setUnlocalizedName("TransportAvenue2NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueEEast = new TransportAvenueEEast(637).setHardness(1.0F).setUnlocalizedName("TransportAvenueEEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueENorth = new TransportAvenueENorth(638).setHardness(1.0F).setUnlocalizedName("TransportAvenueENorth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueESouth = new TransportAvenueESouth(639).setHardness(1.0F).setUnlocalizedName("TransportAvenueESouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueEWest = new TransportAvenueEWest(640).setHardness(1.0F).setUnlocalizedName("TransportAvenueEWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLNorthEast = new TransportAvenueLNorthEast(641).setHardness(1.0F).setUnlocalizedName("TransportAvenueLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLNorthWest = new TransportAvenueLNorthWest(642).setHardness(1.0F).setUnlocalizedName("TransportAvenueLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLSouthEast = new TransportAvenueLSouthEast(643).setHardness(1.0F).setUnlocalizedName("TransportAvenueLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLSouthWest = new TransportAvenueLSouthWest(644).setHardness(1.0F).setUnlocalizedName("TransportAvenueLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTNorthEastWest = new TransportAvenueTNorthEastWest(645).setHardness(1.0F).setUnlocalizedName("TransportAvenueTNorthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTNorthSouthEast = new TransportAvenueTNorthSouthEast(646).setHardness(1.0F).setUnlocalizedName("TransportAvenueTNorthSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTNorthSouthWest = new TransportAvenueTNorthSouthWest(647).setHardness(1.0F).setUnlocalizedName("TransportAvenueTNorthSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTSouthEastWest = new TransportAvenueTSouthEastWest(648).setHardness(1.0F).setUnlocalizedName("TransportAvenueTSouthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueXNorthSouthEastWest = new TransportAvenueXNorthSouthEastWest(649).setHardness(1.0F).setUnlocalizedName("TransportAvenueXNorthSouthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue1EastWest = new TransportBridgeAvenue1EastWest(650).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue1NorthSouth = new TransportBridgeAvenue1NorthSouth(651).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue2NorthSouth = new TransportBridgeAvenue2NorthSouth(652).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue2NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue2SouthWest = new TransportBridgeAvenue2SouthWest(653).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue2SouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue3EastWest = new TransportBridgeAvenue3EastWest(654).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue3EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue3NorthSouth = new TransportBridgeAvenue3NorthSouth(655).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue3NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue4EastWest = new TransportBridgeAvenue4EastWest(656).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue4EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue4NorthSouth = new TransportBridgeAvenue4NorthSouth(657).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenue4NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLNorthEast = new TransportBridgeAvenueLNorthEast(658).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenueLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLNorthWest = new TransportBridgeAvenueLNorthWest(659).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenueLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLSouthEast = new TransportBridgeAvenueLSouthEast(660).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenueLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLSouthWest = new TransportBridgeAvenueLSouthWest(661).setHardness(1.0F).setUnlocalizedName("TransportBridgeAvenueLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway1EastWest = new TransportBridgeHighway1EastWest(662).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway1NorthSouth = new TransportBridgeHighway1NorthSouth(663).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway2EastWest = new TransportBridgeHighway2EastWest(664).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway2EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway2NorthSouth = new TransportBridgeHighway2NorthSouth(665).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway2NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway3EastWest = new TransportBridgeHighway3EastWest(666).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway3EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway3NorthSouth = new TransportBridgeHighway3NorthSouth(667).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway3NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway4EastWest = new TransportBridgeHighway4EastWest(668).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway4EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway4NorthSouth = new TransportBridgeHighway4NorthSouth(669).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighway4NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLNorthEast = new TransportBridgeHighwayLNorthEast(670).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighwayLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLNorthWest = new TransportBridgeHighwayLNorthWest(671).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighwayLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLSouthEast = new TransportBridgeHighwayLSouthEast(672).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighwayLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLSouthWest = new TransportBridgeHighwayLSouthWest(673).setHardness(1.0F).setUnlocalizedName("TransportBridgeHighwayLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad1EastWest = new TransportBridgeRoad1EastWest(674).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoad1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad1NorthSouth = new TransportBridgeRoad1NorthSouth(675).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoad1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad2EastWest = new TransportBridgeRoad2EastWest(676).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoad2EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad2NorthSouth = new TransportBridgeRoad2NorthSouth(677).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoad2NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLNorthEast = new TransportBridgeRoadLNorthEast(678).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoadLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLNorthWest = new TransportBridgeRoadLNorthWest(679).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoadLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLSouthEast = new TransportBridgeRoadLSouthEast(680).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoadLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLSouthWest = new TransportBridgeRoadLSouthWest(681).setHardness(1.0F).setUnlocalizedName("TransportBridgeRoadLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet1EastWest = new TransportBridgeStreet1EastWest(682).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreet1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet1NorthSouth = new TransportBridgeStreet1NorthSouth(683).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreet1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet2EastWest = new TransportBridgeStreet2EastWest(684).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreet2EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet2NorthSouth = new TransportBridgeStreet2NorthSouth(685).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreet2NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLNorthEast = new TransportBridgeStreetLNorthEast(686).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreetLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLNorthWest = new TransportBridgeStreetLNorthWest(687).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreetLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLSouthEast = new TransportBridgeStreetLSouthEast(688).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreetLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLSouthWest = new TransportBridgeStreetLSouthWest(689).setHardness(1.0F).setUnlocalizedName("TransportBridgeStreetLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside = new TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside(690).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside = new TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside(691).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside = new TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside(692).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside = new TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside(693).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside = new TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside(694).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside = new TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside(695).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside = new TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside(696).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside = new TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside(697).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_EastStreet_North = new TransportConnectorAvenue_StreetLAvenue_EastStreet_North(698).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_EastStreet_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_EastStreet_South = new TransportConnectorAvenue_StreetLAvenue_EastStreet_South(699).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_EastStreet_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_NorthStreet_East = new TransportConnectorAvenue_StreetLAvenue_NorthStreet_East(700).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_NorthStreet_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_NorthStreet_West = new TransportConnectorAvenue_StreetLAvenue_NorthStreet_West(701).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_NorthStreet_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_SouthStreet_East = new TransportConnectorAvenue_StreetLAvenue_SouthStreet_East(702).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_SouthStreet_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_SouthStreet_West = new TransportConnectorAvenue_StreetLAvenue_SouthStreet_West(703).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_SouthStreet_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_WestStreet_North = new TransportConnectorAvenue_StreetLAvenue_WestStreet_North(704).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_WestStreet_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_WestStreet_South = new TransportConnectorAvenue_StreetLAvenue_WestStreet_South(705).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetLAvenue_WestStreet_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth = new TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth(706).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North = new TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North(707).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South = new TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South(708).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East = new TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East(709).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West = new TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West(710).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest = new TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest(711).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest = new TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest(712).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth = new TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth(713).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth = new TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth(714).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest = new TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest(715).setHardness(1.0F).setUnlocalizedName("TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_EastAvenue_West = new TransportConnectorBridge_AvenueBridge_EastAvenue_West(716).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_AvenueBridge_EastAvenue_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_NorthAvenue_South = new TransportConnectorBridge_AvenueBridge_NorthAvenue_South(717).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_AvenueBridge_NorthAvenue_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_SouthAvenue_North = new TransportConnectorBridge_AvenueBridge_SouthAvenue_North(718).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_AvenueBridge_SouthAvenue_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_WestAvenue_East = new TransportConnectorBridge_AvenueBridge_WestAvenue_East(719).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_AvenueBridge_WestAvenue_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_EastRoad_West = new TransportConnectorBridge_RoadBridge_EastRoad_West(720).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_RoadBridge_EastRoad_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_NorthRoad_South = new TransportConnectorBridge_RoadBridge_NorthRoad_South(721).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_RoadBridge_NorthRoad_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_SouthRoad_North = new TransportConnectorBridge_RoadBridge_SouthRoad_North(722).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_RoadBridge_SouthRoad_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_WestRoad_East = new TransportConnectorBridge_RoadBridge_WestRoad_East(723).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_RoadBridge_WestRoad_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_EastStreet_West = new TransportConnectorBridge_StreetBridge_EastStreet_West(724).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_StreetBridge_EastStreet_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_NorthStreet_South = new TransportConnectorBridge_StreetBridge_NorthStreet_South(725).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_StreetBridge_NorthStreet_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_SouthStreet_North = new TransportConnectorBridge_StreetBridge_SouthStreet_North(726).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_StreetBridge_SouthStreet_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_WestStreet_East = new TransportConnectorBridge_StreetBridge_WestStreet_East(727).setHardness(1.0F).setUnlocalizedName("TransportConnectorBridge_StreetBridge_WestStreet_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_EastAvenue_West = new TransportConnectorHighway_AvenueHighway_EastAvenue_West(728).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_AvenueHighway_EastAvenue_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_NorthAvenue_South = new TransportConnectorHighway_AvenueHighway_NorthAvenue_South(729).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_AvenueHighway_NorthAvenue_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_SouthAvenue_North = new TransportConnectorHighway_AvenueHighway_SouthAvenue_North(730).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_AvenueHighway_SouthAvenue_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_WestAvenue_East = new TransportConnectorHighway_AvenueHighway_WestAvenue_East(731).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_AvenueHighway_WestAvenue_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West = new TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West(732).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South = new TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South(733).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North = new TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North(734).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East = new TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East(735).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West = new TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West(736).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South = new TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South(737).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North = new TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North(738).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East = new TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East(739).setHardness(1.0F).setUnlocalizedName("TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHarbourBigEast = new TransportHarbourBigEast(740).setHardness(1.0F).setUnlocalizedName("TransportHarbourBigEast").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourBigNorth = new TransportHarbourBigNorth(741).setHardness(1.0F).setUnlocalizedName("TransportHarbourBigNorth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourBigSouth = new TransportHarbourBigSouth(742).setHardness(1.0F).setUnlocalizedName("TransportHarbourBigSouth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourBigWest = new TransportHarbourBigWest(743).setHardness(1.0F).setUnlocalizedName("TransportHarbourBigWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerNorthEast = new TransportHarbourSide1CornerNorthEast(744).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide1CornerNorthEast").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerNorthWest = new TransportHarbourSide1CornerNorthWest(745).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide1CornerNorthWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerSouthEast = new TransportHarbourSide1CornerSouthEast(746).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide1CornerSouthEast").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerSouthWest = new TransportHarbourSide1CornerSouthWest(747).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide1CornerSouthWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneEast = new TransportHarbourSide2CornerCraneEast(748).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerCraneEast").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneNorth = new TransportHarbourSide2CornerCraneNorth(749).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerCraneNorth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneSouth = new TransportHarbourSide2CornerCraneSouth(750).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerCraneSouth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneWest = new TransportHarbourSide2CornerCraneWest(751).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerCraneWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerEast = new TransportHarbourSide2CornerEast(752).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerEast").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerNorth = new TransportHarbourSide2CornerNorth(753).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerNorth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerSouth = new TransportHarbourSide2CornerSouth(754).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerSouth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerWest = new TransportHarbourSide2CornerWest(755).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide2CornerWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast = new TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast(756).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest = new TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest(757).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest = new TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest(758).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest = new TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest(759).setHardness(1.0F).setUnlocalizedName("TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallEast = new TransportHarbourSmallEast(760).setHardness(1.0F).setUnlocalizedName("TransportHarbourSmallEast").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallNorth = new TransportHarbourSmallNorth(761).setHardness(1.0F).setUnlocalizedName("TransportHarbourSmallNorth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallSouth = new TransportHarbourSmallSouth(762).setHardness(1.0F).setUnlocalizedName("TransportHarbourSmallSouth").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallWest = new TransportHarbourSmallWest(763).setHardness(1.0F).setUnlocalizedName("TransportHarbourSmallWest").setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHighway05EastWestNorthside = new TransportHighway05EastWestNorthside(764).setHardness(1.0F).setUnlocalizedName("TransportHighway05EastWestNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway05EastWestSouthside = new TransportHighway05EastWestSouthside(765).setHardness(1.0F).setUnlocalizedName("TransportHighway05EastWestSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway05NorthSouthEastside = new TransportHighway05NorthSouthEastside(766).setHardness(1.0F).setUnlocalizedName("TransportHighway05NorthSouthEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway05NorthSouthWestside = new TransportHighway05NorthSouthWestside(767).setHardness(1.0F).setUnlocalizedName("TransportHighway05NorthSouthWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway1EastWest = new TransportHighway1EastWest(768).setHardness(1.0F).setUnlocalizedName("TransportHighway1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway1NorthSouth = new TransportHighway1NorthSouth(769).setHardness(1.0F).setUnlocalizedName("TransportHighway1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway2EastWest = new TransportHighway2EastWest(770).setHardness(1.0F).setUnlocalizedName("TransportHighway2EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway2NorthSouth = new TransportHighway2NorthSouth(771).setHardness(1.0F).setUnlocalizedName("TransportHighway2NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayEastWestEastside = new TransportHighwayDrivewayEastWestEastside(772).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayEastWestEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayEastWestWestside = new TransportHighwayDrivewayEastWestWestside(773).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayEastWestWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitEastWestEastside = new TransportHighwayDrivewayExitEastWestEastside(774).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayExitEastWestEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitEastWestWestside = new TransportHighwayDrivewayExitEastWestWestside(775).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayExitEastWestWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitNorthSouthNorthside = new TransportHighwayDrivewayExitNorthSouthNorthside(776).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayExitNorthSouthNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitNorthSouthSouthside = new TransportHighwayDrivewayExitNorthSouthSouthside(777).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayExitNorthSouthSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayNorthSouthNorthside = new TransportHighwayDrivewayNorthSouthNorthside(778).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayNorthSouthNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayNorthSouthSouthside = new TransportHighwayDrivewayNorthSouthSouthside(779).setHardness(1.0F).setUnlocalizedName("TransportHighwayDrivewayNorthSouthSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitEastWestEastside = new TransportHighwayExitEastWestEastside(780).setHardness(1.0F).setUnlocalizedName("TransportHighwayExitEastWestEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitEastWestWestside = new TransportHighwayExitEastWestWestside(781).setHardness(1.0F).setUnlocalizedName("TransportHighwayExitEastWestWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitNorthSouthNorthside = new TransportHighwayExitNorthSouthNorthside(782).setHardness(1.0F).setUnlocalizedName("TransportHighwayExitNorthSouthNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitNorthSouthSouthside = new TransportHighwayExitNorthSouthSouthside(783).setHardness(1.0F).setUnlocalizedName("TransportHighwayExitNorthSouthSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05EastWestNorthside = new TransportHighwayFloor05EastWestNorthside(784).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor05EastWestNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05EastWestSouthside = new TransportHighwayFloor05EastWestSouthside(785).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor05EastWestSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05NorthSouthEastside = new TransportHighwayFloor05NorthSouthEastside(786).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor05NorthSouthEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05NorthSouthWestside = new TransportHighwayFloor05NorthSouthWestside(787).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor05NorthSouthWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor1EastWest = new TransportHighwayFloor1EastWest(788).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor1NorthSouth = new TransportHighwayFloor1NorthSouth(789).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor2EastWest = new TransportHighwayFloor2EastWest(790).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor2EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor2NorthSouth = new TransportHighwayFloor2NorthSouth(791).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloor2NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayEastWestEastside = new TransportHighwayFloorDrivewayEastWestEastside(792).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayEastWestEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayEastWestWestside = new TransportHighwayFloorDrivewayEastWestWestside(793).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayEastWestWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitEastWestEastside = new TransportHighwayFloorDrivewayExitEastWestEastside(794).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayExitEastWestEastside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitEastWestWestside = new TransportHighwayFloorDrivewayExitEastWestWestside(795).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayExitEastWestWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitNorthSouthNorthside = new TransportHighwayFloorDrivewayExitNorthSouthNorthside(796).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayExitNorthSouthNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitNorthSouthSouthside = new TransportHighwayFloorDrivewayExitNorthSouthSouthside(797).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayExitNorthSouthSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayNorthSouthNorthside = new TransportHighwayFloorDrivewayNorthSouthNorthside(798).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayNorthSouthNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayNorthSouthSouthside = new TransportHighwayFloorDrivewayNorthSouthSouthside(799).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorDrivewayNorthSouthSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitEastWestEast = new TransportHighwayFloorExitEastWestEast(800).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorExitEastWestEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitEastWestWestside = new TransportHighwayFloorExitEastWestWestside(801).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorExitEastWestWestside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitNorthSouthNorthside = new TransportHighwayFloorExitNorthSouthNorthside(802).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorExitNorthSouthNorthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitNorthSouthSouthside = new TransportHighwayFloorExitNorthSouthSouthside(803).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorExitNorthSouthSouthside").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLNorthEast = new TransportHighwayFloorLNorthEast(804).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLNorthWest = new TransportHighwayFloorLNorthWest(805).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLSouthEast = new TransportHighwayFloorLSouthEast(806).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLSouthWest = new TransportHighwayFloorLSouthWest(807).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTNorthEastWest = new TransportHighwayFloorTNorthEastWest(808).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorTNorthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTNorthSouthEast = new TransportHighwayFloorTNorthSouthEast(809).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorTNorthSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTNorthSouthWest = new TransportHighwayFloorTNorthSouthWest(810).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorTNorthSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTSouthEastWest = new TransportHighwayFloorTSouthEastWest(811).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorTSouthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorXNorthEastSouthWest = new TransportHighwayFloorXNorthEastSouthWest(812).setHardness(1.0F).setUnlocalizedName("TransportHighwayFloorXNorthEastSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLNorthEast = new TransportHighwayLNorthEast(813).setHardness(1.0F).setUnlocalizedName("TransportHighwayLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLNorthWest = new TransportHighwayLNorthWest(814).setHardness(1.0F).setUnlocalizedName("TransportHighwayLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLSouthEast = new TransportHighwayLSouthEast(815).setHardness(1.0F).setUnlocalizedName("TransportHighwayLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLSouthWest = new TransportHighwayLSouthWest(816).setHardness(1.0F).setUnlocalizedName("TransportHighwayLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTNorthEastWest = new TransportHighwayTNorthEastWest(817).setHardness(1.0F).setUnlocalizedName("TransportHighwayTNorthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTNorthSouthEast = new TransportHighwayTNorthSouthEast(818).setHardness(1.0F).setUnlocalizedName("TransportHighwayTNorthSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTNorthSouthWest = new TransportHighwayTNorthSouthWest(819).setHardness(1.0F).setUnlocalizedName("TransportHighwayTNorthSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTSouthEastWest = new TransportHighwayTSouthEastWest(820).setHardness(1.0F).setUnlocalizedName("TransportHighwayTSouthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayXNorthEastSouthWest = new TransportHighwayXNorthEastSouthWest(821).setHardness(1.0F).setUnlocalizedName("TransportHighwayXNorthEastSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportPublicConnectorHightram_TramHightram_EastTram_West = new TransportPublicConnectorHightram_TramHightram_EastTram_West(822).setHardness(1.0F).setUnlocalizedName("TransportPublicConnectorHightram_TramHightram_EastTram_West").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicConnectorHightram_TramHightram_NorthTram_South = new TransportPublicConnectorHightram_TramHightram_NorthTram_South(823).setHardness(1.0F).setUnlocalizedName("TransportPublicConnectorHightram_TramHightram_NorthTram_South").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicConnectorHightram_TramHightram_SouthTram_North = new TransportPublicConnectorHightram_TramHightram_SouthTram_North(824).setHardness(1.0F).setUnlocalizedName("TransportPublicConnectorHightram_TramHightram_SouthTram_North").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicConnectorHightram_TramHightram_WestTram_East = new TransportPublicConnectorHightram_TramHightram_WestTram_East(825).setHardness(1.0F).setUnlocalizedName("TransportPublicConnectorHightram_TramHightram_WestTram_East").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightram1EastWest = new TransportPublicHightram1EastWest(826).setHardness(1.0F).setUnlocalizedName("TransportPublicHightram1EastWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightram1NorthSouth = new TransportPublicHightram1NorthSouth(827).setHardness(1.0F).setUnlocalizedName("TransportPublicHightram1NorthSouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramEEast = new TransportPublicHightramEEast(828).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramEEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramENorth = new TransportPublicHightramENorth(829).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramENorth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramESouth = new TransportPublicHightramESouth(830).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramESouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramEWest = new TransportPublicHightramEWest(831).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramEWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLNorthEast = new TransportPublicHightramLNorthEast(832).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramLNorthEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLNorthWest = new TransportPublicHightramLNorthWest(833).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramLNorthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLSouthEast = new TransportPublicHightramLSouthEast(834).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramLSouthEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLSouthWest = new TransportPublicHightramLSouthWest(835).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramLSouthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramStationEastWest = new TransportPublicHightramStationEastWest(836).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramStationEastWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramStationNorthSouth = new TransportPublicHightramStationNorthSouth(837).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramStationNorthSouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramXNorthEastSouthWest = new TransportPublicHightramXNorthEastSouthWest(838).setHardness(1.0F).setUnlocalizedName("TransportPublicHightramXNorthEastSouthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram1EastWest = new TransportPublicTram1EastWest(839).setHardness(1.0F).setUnlocalizedName("TransportPublicTram1EastWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram1NorthSouth = new TransportPublicTram1NorthSouth(840).setHardness(1.0F).setUnlocalizedName("TransportPublicTram1NorthSouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramEEast = new TransportPublicTramEEast(841).setHardness(1.0F).setUnlocalizedName("TransportPublicTramEEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramENorth = new TransportPublicTramENorth(842).setHardness(1.0F).setUnlocalizedName("TransportPublicTramENorth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramESouth = new TransportPublicTramESouth(843).setHardness(1.0F).setUnlocalizedName("TransportPublicTramESouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramEWest = new TransportPublicTramEWest(844).setHardness(1.0F).setUnlocalizedName("TransportPublicTramEWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLNorthEast = new TransportPublicTramLNorthEast(845).setHardness(1.0F).setUnlocalizedName("TransportPublicTramLNorthEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLNorthWest = new TransportPublicTramLNorthWest(846).setHardness(1.0F).setUnlocalizedName("TransportPublicTramLNorthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLSouthEast = new TransportPublicTramLSouthEast(847).setHardness(1.0F).setUnlocalizedName("TransportPublicTramLSouthEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLSouthWest = new TransportPublicTramLSouthWest(848).setHardness(1.0F).setUnlocalizedName("TransportPublicTramLSouthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_road1EastWest = new TransportPublicTram_on_road1EastWest(849).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_road1EastWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_road1NorthSouth = new TransportPublicTram_on_road1NorthSouth(850).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_road1NorthSouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadEEast = new TransportPublicTram_on_roadEEast(851).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadEEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadENorth = new TransportPublicTram_on_roadENorth(852).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadENorth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadESouth = new TransportPublicTram_on_roadESouth(853).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadESouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadEWest = new TransportPublicTram_on_roadEWest(854).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadEWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLNorthEast = new TransportPublicTram_on_roadLNorthEast(855).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadLNorthEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLNorthWest = new TransportPublicTram_on_roadLNorthWest(856).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadLNorthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLSouthEast = new TransportPublicTram_on_roadLSouthEast(857).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadLSouthEast").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLSouthWest = new TransportPublicTram_on_roadLSouthWest(858).setHardness(1.0F).setUnlocalizedName("TransportPublicTram_on_roadLSouthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramStationEastWest = new TransportPublicTramStationEastWest(859).setHardness(1.0F).setUnlocalizedName("TransportPublicTramStationEastWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramStationNorthSouth = new TransportPublicTramStationNorthSouth(860).setHardness(1.0F).setUnlocalizedName("TransportPublicTramStationNorthSouth").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramXNorthEastSouthWest = new TransportPublicTramXNorthEastSouthWest(861).setHardness(1.0F).setUnlocalizedName("TransportPublicTramXNorthEastSouthWest").setCreativeTab(IMSM.TransportPublic);
	public static Block TransportRoad1EastWest = new TransportRoad1EastWest(862).setHardness(1.0F).setUnlocalizedName("TransportRoad1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoad1NorthSouth = new TransportRoad1NorthSouth(863).setHardness(1.0F).setUnlocalizedName("TransportRoad1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadEEast = new TransportRoadEEast(864).setHardness(1.0F).setUnlocalizedName("TransportRoadEEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadENorth = new TransportRoadENorth(865).setHardness(1.0F).setUnlocalizedName("TransportRoadENorth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadESouth = new TransportRoadESouth(866).setHardness(1.0F).setUnlocalizedName("TransportRoadESouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadEWest = new TransportRoadEWest(867).setHardness(1.0F).setUnlocalizedName("TransportRoadEWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLNorthEast = new TransportRoadLNorthEast(868).setHardness(1.0F).setUnlocalizedName("TransportRoadLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLNorthWest = new TransportRoadLNorthWest(869).setHardness(1.0F).setUnlocalizedName("TransportRoadLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLSouthEast = new TransportRoadLSouthEast(870).setHardness(1.0F).setUnlocalizedName("TransportRoadLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLSouthWest = new TransportRoadLSouthWest(871).setHardness(1.0F).setUnlocalizedName("TransportRoadLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTNorthEastWest = new TransportRoadTNorthEastWest(872).setHardness(1.0F).setUnlocalizedName("TransportRoadTNorthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTNorthSouthEast = new TransportRoadTNorthSouthEast(873).setHardness(1.0F).setUnlocalizedName("TransportRoadTNorthSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTNorthSouthWest = new TransportRoadTNorthSouthWest(874).setHardness(1.0F).setUnlocalizedName("TransportRoadTNorthSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTSouthEastWest = new TransportRoadTSouthEastWest(875).setHardness(1.0F).setUnlocalizedName("TransportRoadTSouthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadXNorthEastSouthWest = new TransportRoadXNorthEastSouthWest(876).setHardness(1.0F).setUnlocalizedName("TransportRoadXNorthEastSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreet1EastWest = new TransportStreet1EastWest(877).setHardness(1.0F).setUnlocalizedName("TransportStreet1EastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreet1NorthSouth = new TransportStreet1NorthSouth(878).setHardness(1.0F).setUnlocalizedName("TransportStreet1NorthSouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetEEast = new TransportStreetEEast(879).setHardness(1.0F).setUnlocalizedName("TransportStreetEEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetENorth = new TransportStreetENorth(880).setHardness(1.0F).setUnlocalizedName("TransportStreetENorth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetESouth = new TransportStreetESouth(881).setHardness(1.0F).setUnlocalizedName("TransportStreetESouth").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetEWest = new TransportStreetEWest(882).setHardness(1.0F).setUnlocalizedName("TransportStreetEWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLNorthEast = new TransportStreetLNorthEast(883).setHardness(1.0F).setUnlocalizedName("TransportStreetLNorthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLNorthWest = new TransportStreetLNorthWest(884).setHardness(1.0F).setUnlocalizedName("TransportStreetLNorthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLSouthEast = new TransportStreetLSouthEast(885).setHardness(1.0F).setUnlocalizedName("TransportStreetLSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLSouthWest = new TransportStreetLSouthWest(886).setHardness(1.0F).setUnlocalizedName("TransportStreetLSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetRoundaboutNorthEastSouthWest = new TransportStreetRoundaboutNorthEastSouthWest(887).setHardness(1.0F).setUnlocalizedName("TransportStreetRoundaboutNorthEastSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTNorthEastWest = new TransportStreetTNorthEastWest(888).setHardness(1.0F).setUnlocalizedName("TransportStreetTNorthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTNorthSouthEast = new TransportStreetTNorthSouthEast(889).setHardness(1.0F).setUnlocalizedName("TransportStreetTNorthSouthEast").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTNorthSouthWest = new TransportStreetTNorthSouthWest(890).setHardness(1.0F).setUnlocalizedName("TransportStreetTNorthSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTSouthEastWest = new TransportStreetTSouthEastWest(891).setHardness(1.0F).setUnlocalizedName("TransportStreetTSouthEastWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetXNorthEastSouthWest = new TransportStreetXNorthEastSouthWest(892).setHardness(1.0F).setUnlocalizedName("TransportStreetXNorthEastSouthWest").setCreativeTab(IMSM.TransportRoads);
	public static Block TransportWater1CornerNorthEast = new TransportWater1CornerNorthEast(893).setHardness(1.0F).setUnlocalizedName("TransportWater1CornerNorthEast").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater1CornerNorthWest = new TransportWater1CornerNorthWest(894).setHardness(1.0F).setUnlocalizedName("TransportWater1CornerNorthWest").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater1CornerSouthEast = new TransportWater1CornerSouthEast(895).setHardness(1.0F).setUnlocalizedName("TransportWater1CornerSouthEast").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater1CornerSouthWest = new TransportWater1CornerSouthWest(896).setHardness(1.0F).setUnlocalizedName("TransportWater1CornerSouthWest").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerEast = new TransportWater2CornerEast(897).setHardness(1.0F).setUnlocalizedName("TransportWater2CornerEast").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerNorth = new TransportWater2CornerNorth(898).setHardness(1.0F).setUnlocalizedName("TransportWater2CornerNorth").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerSouth = new TransportWater2CornerSouth(899).setHardness(1.0F).setUnlocalizedName("TransportWater2CornerSouth").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerWest = new TransportWater2CornerWest(900).setHardness(1.0F).setUnlocalizedName("TransportWater2CornerWest").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerNorthEast_NorthWest_SouthEast = new TransportWater3CornerNorthEast_NorthWest_SouthEast(901).setHardness(1.0F).setUnlocalizedName("TransportWater3CornerNorthEast_NorthWest_SouthEast").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerNorthEast_NorthWest_SouthWest = new TransportWater3CornerNorthEast_NorthWest_SouthWest(902).setHardness(1.0F).setUnlocalizedName("TransportWater3CornerNorthEast_NorthWest_SouthWest").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerSouthEast_SouthWest_NorthEast = new TransportWater3CornerSouthEast_SouthWest_NorthEast(903).setHardness(1.0F).setUnlocalizedName("TransportWater3CornerSouthEast_SouthWest_NorthEast").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerSouthEast_SouthWest_NorthWest = new TransportWater3CornerSouthEast_SouthWest_NorthWest(904).setHardness(1.0F).setUnlocalizedName("TransportWater3CornerSouthEast_SouthWest_NorthWest").setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater4CornerNorthSouthEastWest = new TransportWater4CornerNorthSouthEastWest(905).setHardness(1.0F).setUnlocalizedName("TransportWater4CornerNorthSouthEastWest").setCreativeTab(IMSM.TransportWater);
	public static Block UtilityPower_NuclearEast = new UtilityPower_NuclearEast(906).setHardness(1.0F).setUnlocalizedName("UtilityPower_NuclearEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_NuclearNorth = new UtilityPower_NuclearNorth(907).setHardness(1.0F).setUnlocalizedName("UtilityPower_NuclearNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_NuclearSouth = new UtilityPower_NuclearSouth(908).setHardness(1.0F).setUnlocalizedName("UtilityPower_NuclearSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_NuclearWest = new UtilityPower_NuclearWest(909).setHardness(1.0F).setUnlocalizedName("UtilityPower_NuclearWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalEast = new UtilityPower_OilCoalEast(910).setHardness(1.0F).setUnlocalizedName("UtilityPower_OilCoalEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalNorth = new UtilityPower_OilCoalNorth(911).setHardness(1.0F).setUnlocalizedName("UtilityPower_OilCoalNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalSouth = new UtilityPower_OilCoalSouth(912).setHardness(1.0F).setUnlocalizedName("UtilityPower_OilCoalSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalWest = new UtilityPower_OilCoalWest(913).setHardness(1.0F).setUnlocalizedName("UtilityPower_OilCoalWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_SunNorthEastSouthWest = new UtilityPower_SunNorthEastSouthWest(914).setHardness(1.0F).setUnlocalizedName("UtilityPower_SunNorthEastSouthWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindEast = new UtilityPower_WindEast(915).setHardness(1.0F).setUnlocalizedName("UtilityPower_WindEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindNorth = new UtilityPower_WindNorth(916).setHardness(1.0F).setUnlocalizedName("UtilityPower_WindNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindSouth = new UtilityPower_WindSouth(917).setHardness(1.0F).setUnlocalizedName("UtilityPower_WindSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindWest = new UtilityPower_WindWest(918).setHardness(1.0F).setUnlocalizedName("UtilityPower_WindWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityPumpjackEastWest = new UtilityPumpjackEastWest(919).setHardness(1.0F).setUnlocalizedName("UtilityPumpjackEastWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityPumpjackNorthSouth = new UtilityPumpjackNorthSouth(920).setHardness(1.0F).setUnlocalizedName("UtilityPumpjackNorthSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningEast = new UtilityScrap_BurningEast(921).setHardness(1.0F).setUnlocalizedName("UtilityScrap_BurningEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningNorth = new UtilityScrap_BurningNorth(922).setHardness(1.0F).setUnlocalizedName("UtilityScrap_BurningNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningSouth = new UtilityScrap_BurningSouth(923).setHardness(1.0F).setUnlocalizedName("UtilityScrap_BurningSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningWest = new UtilityScrap_BurningWest(924).setHardness(1.0F).setUnlocalizedName("UtilityScrap_BurningWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapEast = new UtilityScrap_HeapEast(925).setHardness(1.0F).setUnlocalizedName("UtilityScrap_HeapEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapNorth = new UtilityScrap_HeapNorth(926).setHardness(1.0F).setUnlocalizedName("UtilityScrap_HeapNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapSouth = new UtilityScrap_HeapSouth(927).setHardness(1.0F).setUnlocalizedName("UtilityScrap_HeapSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapWest = new UtilityScrap_HeapWest(928).setHardness(1.0F).setUnlocalizedName("UtilityScrap_HeapWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleEast = new UtilityScrap_RecycleEast(929).setHardness(1.0F).setUnlocalizedName("UtilityScrap_RecycleEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleNorth = new UtilityScrap_RecycleNorth(930).setHardness(1.0F).setUnlocalizedName("UtilityScrap_RecycleNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleSouth = new UtilityScrap_RecycleSouth(931).setHardness(1.0F).setUnlocalizedName("UtilityScrap_RecycleSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleWest = new UtilityScrap_RecycleWest(932).setHardness(1.0F).setUnlocalizedName("UtilityScrap_RecycleWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpEast = new UtilityWater_PumpEast(933).setHardness(1.0F).setUnlocalizedName("UtilityWater_PumpEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpNorth = new UtilityWater_PumpNorth(934).setHardness(1.0F).setUnlocalizedName("UtilityWater_PumpNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpSouth = new UtilityWater_PumpSouth(935).setHardness(1.0F).setUnlocalizedName("UtilityWater_PumpSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpWest = new UtilityWater_PumpWest(936).setHardness(1.0F).setUnlocalizedName("UtilityWater_PumpWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TowerNorthEastSouthWest = new UtilityWater_TowerNorthEastSouthWest(937).setHardness(1.0F).setUnlocalizedName("UtilityWater_TowerNorthEastSouthWest").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentEast = new UtilityWater_TreatmentEast(938).setHardness(1.0F).setUnlocalizedName("UtilityWater_TreatmentEast").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentNorth = new UtilityWater_TreatmentNorth(939).setHardness(1.0F).setUnlocalizedName("UtilityWater_TreatmentNorth").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentSouth = new UtilityWater_TreatmentSouth(940).setHardness(1.0F).setUnlocalizedName("UtilityWater_TreatmentSouth").setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentWest = new UtilityWater_TreatmentWest(941).setHardness(1.0F).setUnlocalizedName("UtilityWater_TreatmentWest").setCreativeTab(IMSM.Utility);
	
	public static Block BlockAirBalloon = new BlockAirBalloon(942).setHardness(1.0F).setUnlocalizedName("BlockAirBalloon").setCreativeTab(IMSM.Structures);
	public static Block BlockAirplane = new BlockAirplane(943).setHardness(1.0F).setUnlocalizedName("BlockAirplane").setCreativeTab(IMSM.Structures);
	//public static Block BlockApplepie = new BlockApplepie(944).setHardness(1.0F).setUnlocalizedName("BlockApplepie").setCreativeTab(IMSM.Structures);
	public static Block BlockArena1 = new BlockArena1(945).setHardness(1.0F).setUnlocalizedName("BlockArena1").setCreativeTab(IMSM.Structures);
	public static Block BlockArena2 = new BlockArena2(946).setHardness(1.0F).setUnlocalizedName("BlockArena2").setCreativeTab(IMSM.Structures);
	public static Block BlockBigPyramid = new BlockBigPyramid(947).setHardness(1.0F).setUnlocalizedName("BlockBigPyramid").setCreativeTab(IMSM.Other);
	public static Block BlockBoat = new BlockBoat(948).setHardness(1.0F).setUnlocalizedName("BlockBoat").setCreativeTab(IMSM.Structures);
	public static Block BlockBunker = new BlockBunker(949).setHardness(1.0F).setUnlocalizedName("BlockBunker").setCreativeTab(IMSM.Structures);
	//public static Block BlockCactus2 = new BlockCactus2(950).setHardness(1.0F).setUnlocalizedName("BlockCactus2").setCreativeTab(IMSM.Structures);
	//public static Block BlockCake2 = new BlockCake2(951).setHardness(1.0F).setUnlocalizedName("BlockCake2").setCreativeTab(IMSM.Structures);
	public static Block BlockCastleTower = new BlockCastleTower(952).setHardness(1.0F).setUnlocalizedName("BlockCastleTower").setCreativeTab(IMSM.Structures);
	//public static Block BlockCave = new BlockCave(953).setHardness(1.0F).setUnlocalizedName("BlockCave").setCreativeTab(IMSM.Structures);
	//public static Block BlockColumn = new BlockColumn(954).setHardness(1.0F).setUnlocalizedName("BlockColumn").setCreativeTab(IMSM.Structures);
	public static Block BlockCosyHouse = new BlockCosyHouse(955).setHardness(1.0F).setUnlocalizedName("BlockCosyHouse").setCreativeTab(IMSM.Structures);
	public static Block BlockDungeon = new BlockDungeon(956).setHardness(1.0F).setUnlocalizedName("BlockDungeon").setCreativeTab(IMSM.Structures);
	public static Block BlockEnchantmentRoom = new BlockEnchantmentRoom(957).setHardness(1.0F).setUnlocalizedName("BlockEnchantmentRoom").setCreativeTab(IMSM.Structures);
	public static Block BlockFarm2 = new BlockFarm2(958).setHardness(1.0F).setUnlocalizedName("BlockFarm2").setCreativeTab(IMSM.Structures);
	public static Block BlockFarm3 = new BlockFarm3(959).setHardness(1.0F).setUnlocalizedName("BlockFarm3").setCreativeTab(IMSM.Structures);
	public static Block BlockFarm4 = new BlockFarm4(960).setHardness(1.0F).setUnlocalizedName("BlockFarm4").setCreativeTab(IMSM.Structures);
	public static Block BlockFarm = new BlockFarm(961).setHardness(1.0F).setUnlocalizedName("BlockFarm").setCreativeTab(IMSM.Structures);
	//public static Block BlockFloatingSphere = new BlockFloatingSphere(962).setHardness(1.0F).setUnlocalizedName("BlockFloatingSphere").setCreativeTab(IMSM.Structures);
	public static Block BlockGiantTree = new BlockGiantTree(963).setHardness(1.0F).setUnlocalizedName("BlockGiantTree").setCreativeTab(IMSM.Structures);
	//public static Block BlockGlassHouse = new BlockGlassHouse(964).setHardness(1.0F).setUnlocalizedName("BlockGlassHouse").setCreativeTab(IMSM.Structures);
	public static Block BlockHountedHouse = new BlockHountedHouse(965).setHardness(1.0F).setUnlocalizedName("BlockHountedHouse").setCreativeTab(IMSM.Structures);
	//public static Block BlockHouse2 = new BlockHouse2(966).setHardness(1.0F).setUnlocalizedName("BlockHouse2").setCreativeTab(IMSM.Structures);
	public static Block BlockHouse = new BlockHouse(967).setHardness(1.0F).setUnlocalizedName("BlockHouse").setCreativeTab(IMSM.Structures);
	public static Block BlockHouseTrap1 = new BlockHouseTrap1(968).setHardness(1.0F).setUnlocalizedName("BlockHouseTrap1").setCreativeTab(IMSM.Structures);
	public static Block BlockHouseTrap2 = new BlockHouseTrap2(969).setHardness(1.0F).setUnlocalizedName("BlockHouseTrap2").setCreativeTab(IMSM.Structures);
	//public static Block BlockLeaves2 = new BlockLeaves2(970).setHardness(1.0F).setUnlocalizedName("BlockLeaves2").setCreativeTab(IMSM.Structures);
	public static Block BlockLighthouse = new BlockLighthouse(971).setHardness(1.0F).setUnlocalizedName("BlockLighthouse").setCreativeTab(IMSM.Structures);
	public static Block BlockMegaHouse2 = new BlockMegaHouse2(972).setHardness(1.0F).setUnlocalizedName("BlockMegaHouse2").setCreativeTab(IMSM.Structures);
	public static Block BlockMegaHouse = new BlockMegaHouse(973).setHardness(1.0F).setUnlocalizedName("BlockMegaHouse").setCreativeTab(IMSM.Structures);
	public static Block BlockMegaTower = new BlockMegaTower(974).setHardness(1.0F).setUnlocalizedName("BlockMegaTower").setCreativeTab(IMSM.Structures);
	//public static Block BlockPenIron = new BlockPenIron(975).setHardness(1.0F).setUnlocalizedName("BlockPenIron").setCreativeTab(IMSM.Structures);
	//public static Block BlockPenNether = new BlockPenNether(976).setHardness(1.0F).setUnlocalizedName("BlockPenNether").setCreativeTab(IMSM.Structures);
	//public static Block BlockPenWood = new BlockPenWood(977).setHardness(1.0F).setUnlocalizedName("BlockPenWood").setCreativeTab(IMSM.Structures);
	public static Block BlockPlane = new BlockPlane(978).setHardness(1.0F).setUnlocalizedName("BlockPlane").setCreativeTab(IMSM.Structures);
	public static Block BlockPrison2 = new BlockPrison2(979).setHardness(1.0F).setUnlocalizedName("BlockPrison2").setCreativeTab(IMSM.Structures);
	public static Block BlockPrison = new BlockPrison(980).setHardness(1.0F).setUnlocalizedName("BlockPrison").setCreativeTab(IMSM.Structures);
	public static Block BlockPyramid = new BlockPyramid(981).setHardness(1.0F).setUnlocalizedName("BlockPyramid").setCreativeTab(IMSM.Structures);
	public static Block BlockRollerCoaster2 = new BlockRollerCoaster2(982).setHardness(1.0F).setUnlocalizedName("BlockRollerCoaster2").setCreativeTab(IMSM.Structures);
	public static Block BlockRollercoaster = new BlockRollercoaster(983).setHardness(1.0F).setUnlocalizedName("BlockRollercoaster").setCreativeTab(IMSM.Structures);
	//public static Block BlockShelter = new BlockShelter(984).setHardness(1.0F).setUnlocalizedName("BlockShelter").setCreativeTab(IMSM.Structures);
	//public static Block BlockSkyscraper2 = new BlockSkyscraper2(985).setHardness(1.0F).setUnlocalizedName("BlockSkyscraper2").setCreativeTab(IMSM.Structures);
	public static Block BlockSkyscraper = new BlockSkyscraper(986).setHardness(1.0F).setUnlocalizedName("BlockSkyscraper").setCreativeTab(IMSM.Structures);
	//public static Block BlockStadium2 = new BlockStadium2(987).setHardness(1.0F).setUnlocalizedName("BlockStadium2").setCreativeTab(IMSM.Structures);
	public static Block BlockStadium = new BlockStadium(988).setHardness(1.0F).setUnlocalizedName("BlockStadium").setCreativeTab(IMSM.Other);
	//public static Block BlockStandardBrickHouse = new BlockStandardBrickHouse(989).setHardness(1.0F).setUnlocalizedName("BlockStandardBrickHouse").setCreativeTab(IMSM.Structures);
	public static Block BlockStoreHouse = new BlockStoreHouse(990).setHardness(1.0F).setUnlocalizedName("BlockStoreHouse").setCreativeTab(IMSM.Structures);
	//public static Block BlockStreet = new BlockStreet(991).setHardness(1.0F).setUnlocalizedName("BlockStreet").setCreativeTab(IMSM.Structures);
	public static Block BlockTorch2 = new BlockTorch2(992).setHardness(1.0F).setUnlocalizedName("BlockTorch2").setCreativeTab(IMSM.Structures);
	public static Block BlockTower = new BlockTower(993).setHardness(1.0F).setUnlocalizedName("BlockTower").setCreativeTab(IMSM.Structures);
	public static Block BlockWaterSlide = new BlockWaterSlide(994).setHardness(1.0F).setUnlocalizedName("BlockWaterSlide").setCreativeTab(IMSM.Structures);
	
	public static Block OtherBrickHouse = new OtherBrickHouse(200).setHardness(1.0F).setUnlocalizedName("OtherBrickHouse").setCreativeTab(IMSM.Other);
	public static Block OtherGrandHouse = new OtherGrandHouse(201).setHardness(1.0F).setUnlocalizedName("OtherGrandHouse").setCreativeTab(IMSM.Structures);
	public static Block OtherStable = new OtherStable(203).setHardness(1.0F).setUnlocalizedName("OtherStable").setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse2 = new OtherSurvivorHouse2(204).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse2").setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse3 = new OtherSurvivorHouse3(205).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse3").setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse4 = new OtherSurvivorHouse4(206).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse4").setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse5 = new OtherSurvivorHouse5(207).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse5").setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse6 = new OtherSurvivorHouse6(208).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse6").setCreativeTab(IMSM.Structures);
	public static Block OtherSurvivorHouse7 = new OtherSurvivorHouse7(209).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse7").setCreativeTab(IMSM.Structures);
	public static Block OtherSurvivorHouse8 = new OtherSurvivorHouse8(210).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse8").setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse = new OtherSurvivorHouse(211).setHardness(1.0F).setUnlocalizedName("OtherSurvivorHouse").setCreativeTab(IMSM.Other);
	public static Block OtherTemple = new OtherTemple(212).setHardness(1.0F).setUnlocalizedName("OtherTemple").setCreativeTab(IMSM.Other);
	public static Block SurvivalSmallBuilding = new SurvivalSmallBuilding(213).setHardness(1.0F).setUnlocalizedName("SurvivalSmallBuilding").setCreativeTab(IMSM.Structures);
	public static Block SurvivalWoodenHouse = new SurvivalWoodenHouse(214).setHardness(1.0F).setUnlocalizedName("SurvivalWoodenHouse").setCreativeTab(IMSM.Structures);
	public static Block WoodenHouse = new WoodenHouse(215).setHardness(1.0F).setUnlocalizedName("WoodenHouse").setCreativeTab(IMSM.Structures);
	public static Block BlockCheckerboard = new BlockCheckerboard(200).setHardness(1.0F).setUnlocalizedName("BlockCheckerboard").setCreativeTab(IMSM.Other);
	//public static Block BlockAtlantis = new BlockAtlantis(200).setHardness(1.0F).setUnlocalizedName("BlockAtlantis").setCreativeTab(IMSM.WorldGeneration);

	//public static Block Remover16256 = new Remover16256(995).setHardness(1.0F).setUnlocalizedName("Remover16256").setCreativeTab(IMSM.Remover);
	//public static Block Remover1632 = new Remover1632(996).setHardness(1.0F).setUnlocalizedName("Remover1632").setCreativeTab(IMSM.Remover);
	//public static Block Remover168 = new Remover168(997).setHardness(1.0F).setUnlocalizedName("Remover168").setCreativeTab(IMSM.Remover);
	public static Block Remover16 = new Remover16(998).setHardness(1.0F).setUnlocalizedName("Remover16").setCreativeTab(IMSM.Remover);
	//public static Block Remover3216 = new Remover3216(999).setHardness(1.0F).setUnlocalizedName("Remover3216").setCreativeTab(IMSM.Remover);
	//public static Block Remover32256 = new Remover32256(1000).setHardness(1.0F).setUnlocalizedName("Remover32256").setCreativeTab(IMSM.Remover);
	//public static Block Remover328 = new Remover328(1001).setHardness(1.0F).setUnlocalizedName("Remover328").setCreativeTab(IMSM.Remover);
	public static Block Remover32 = new Remover32(1002).setHardness(1.0F).setUnlocalizedName("Remover32").setCreativeTab(IMSM.Remover);
	//public static Block Remover64256 = new Remover64256(1003).setHardness(1.0F).setUnlocalizedName("Remover64256").setCreativeTab(IMSM.Remover);
	public static Block Remover64 = new Remover64(1004).setHardness(1.0F).setUnlocalizedName("Remover64").setCreativeTab(IMSM.Remover);
	//public static Block Remover816 = new Remover816(1005).setHardness(1.0F).setUnlocalizedName("Remover816").setCreativeTab(IMSM.Remover);
	//public static Block Remover8256 = new Remover8256(1006).setHardness(1.0F).setUnlocalizedName("Remover8256").setCreativeTab(IMSM.Remover);
	//public static Block Remover832 = new Remover832(1007).setHardness(1.0F).setUnlocalizedName("Remover832").setCreativeTab(IMSM.Remover);
	public static Block Remover8 = new Remover8(1008).setHardness(1.0F).setUnlocalizedName("Remover8").setCreativeTab(IMSM.Remover);
	public static Block RemoverLast = new RemoverLast(1008).setHardness(1.0F).setUnlocalizedName("RemoverLast").setCreativeTab(IMSM.Remover);
	public static Block BlockBigWorld = new BlockBigWorld(200).setHardness(1.0F).setUnlocalizedName("BlockBigWorld").setCreativeTab(IMSM.Other);

	public static Block RandomAirballoon2 = new RandomAirballoon2(200).setHardness(1.0F).setUnlocalizedName("RandomAirballoon2").setCreativeTab(IMSM.Structures);
	public static Block RandomEntrance = new RandomEntrance(201).setHardness(1.0F).setUnlocalizedName("RandomEntrance").setCreativeTab(IMSM.Other);
	public static Block RandomFlyingShip = new RandomFlyingShip(202).setHardness(1.0F).setUnlocalizedName("RandomFlyingShip").setCreativeTab(IMSM.Structures);
	public static Block RandomGreenTent = new RandomGreenTent(203).setHardness(1.0F).setUnlocalizedName("RandomGreenTent").setCreativeTab(IMSM.Structures);
	public static Block RandomGreyTent = new RandomGreyTent(204).setHardness(1.0F).setUnlocalizedName("RandomGreyTent").setCreativeTab(IMSM.Structures);
	public static Block RandomLightHouse = new RandomLightHouse(205).setHardness(1.0F).setUnlocalizedName("RandomLightHouse").setCreativeTab(IMSM.Structures);
	public static Block RandomMinerTent = new RandomMinerTent(206).setHardness(1.0F).setUnlocalizedName("RandomMinerTent").setCreativeTab(IMSM.Other);
	public static Block RandomNetherEntranceSurvival = new RandomNetherEntranceSurvival(207).setHardness(1.0F).setUnlocalizedName("RandomNetherEntranceSurvival").setCreativeTab(IMSM.Other);
	public static Block RandomRandomBrickHouse = new RandomRandomBrickHouse(208).setHardness(1.0F).setUnlocalizedName("RandomRandomBrickHouse").setCreativeTab(IMSM.Other);
	public static Block RandomSurvivalHouse1 = new RandomSurvivalHouse1(209).setHardness(1.0F).setUnlocalizedName("RandomSurvivalHouse1").setCreativeTab(IMSM.Other);
	public static Block RandomSurvivalHouseSandstone = new RandomSurvivalHouseSandstone(210).setHardness(1.0F).setUnlocalizedName("RandomSurvivalHouseSandstone").setCreativeTab(IMSM.Other);
	public static Block RandomTentCamp = new RandomTentCamp(211).setHardness(1.0F).setUnlocalizedName("RandomTentCamp").setCreativeTab(IMSM.Structures);
	public static Block RandomWoodenHouse = new RandomWoodenHouse(212).setHardness(1.0F).setUnlocalizedName("RandomWoodenHouse").setCreativeTab(IMSM.Other);
	public static Block BlockCloud = new BlockCloud(200).setHardness(1.0F).setUnlocalizedName("BlockCloud").setCreativeTab(IMSM.Other);

	public static Block RandomBuildingComplex = new RandomBuildingComplex(200).setHardness(1.0F).setUnlocalizedName("RandomBuildingComplex").setCreativeTab(IMSM.Other);
	public static Block RandomImmense_Buildingcomplex = new RandomImmense_Buildingcomplex(201).setHardness(1.0F).setUnlocalizedName("RandomImmense_Buildingcomplex").setCreativeTab(IMSM.Other);
	public static Block RandomImmense_greenroof = new RandomImmense_greenroof(202).setHardness(1.0F).setUnlocalizedName("RandomImmense_greenroof").setCreativeTab(IMSM.Other);
	public static Block RandomImmense_White_House = new RandomImmense_White_House(203).setHardness(1.0F).setUnlocalizedName("RandomImmense_White_House").setCreativeTab(IMSM.Other);
	public static Block RandomImmense_WorkingBuilding = new RandomImmense_WorkingBuilding(204).setHardness(1.0F).setUnlocalizedName("RandomImmense_WorkingBuilding").setCreativeTab(IMSM.Other);
	public static Block RandomLittlePalace = new RandomLittlePalace(205).setHardness(1.0F).setUnlocalizedName("RandomLittlePalace").setCreativeTab(IMSM.Other);
	public static Block RandomLittleWoodenCabin = new RandomLittleWoodenCabin(206).setHardness(1.0F).setUnlocalizedName("RandomLittleWoodenCabin").setCreativeTab(IMSM.Structures);
	public static Block RandomSandstoneBuilding = new RandomSandstoneBuilding(207).setHardness(1.0F).setUnlocalizedName("RandomSandstoneBuilding").setCreativeTab(IMSM.Other);
	public static Block RandomSandStoneChurch = new RandomSandStoneChurch(208).setHardness(1.0F).setUnlocalizedName("RandomSandStoneChurch").setCreativeTab(IMSM.Other);
	public static Block RandomSandstonewithFarm = new RandomSandstonewithFarm(209).setHardness(1.0F).setUnlocalizedName("RandomSandstonewithFarm").setCreativeTab(IMSM.Other);
	public static Block RandomSimpleSandstone = new RandomSimpleSandstone(210).setHardness(1.0F).setUnlocalizedName("RandomSimpleSandstone").setCreativeTab(IMSM.Other);
	public static Block RandomSpawnHouseProd = new RandomSpawnHouseProd(211).setHardness(1.0F).setUnlocalizedName("RandomSpawnHouseProd").setCreativeTab(IMSM.Other);
	public static Block RandomWoodenStonebrickHouse = new RandomWoodenStonebrickHouse(212).setHardness(1.0F).setUnlocalizedName("RandomWoodenStonebrickHouse").setCreativeTab(IMSM.Structures);
	
	public static Block Live_Power_Windmill_East = new Live_Power_Windmill_East(200).setHardness(1.0F).setUnlocalizedName("Live_Power_Windmill_East").setCreativeTab(IMSM.LiveStructures);
	public static Block LiveAirBalloon = new LiveAirBalloon(200).setHardness(1.0F).setUnlocalizedName("LiveAirBalloon").setCreativeTab(IMSM.LiveStructures);
	public static Block LiveAirplane = new LiveAirplane(201).setHardness(1.0F).setUnlocalizedName("LiveAirplane").setCreativeTab(IMSM.LiveStructures);
	public static Block LiveBoat = new LiveBoat(202).setHardness(1.0F).setUnlocalizedName("LiveBoat").setCreativeTab(IMSM.LiveStructures);
	public static Block LiveFlyingShip2 = new LiveFlyingShip2(203).setHardness(1.0F).setUnlocalizedName("LiveFlyingShip2").setCreativeTab(IMSM.LiveStructures);
	public static Block LiveFlyingShip = new LiveFlyingShip(204).setHardness(1.0F).setUnlocalizedName("LiveFlyingShip").setCreativeTab(IMSM.LiveStructures);
	public static Block LivePlane = new LivePlane().setHardness(1.0F).setUnlocalizedName("LivePlane").setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Helicopter = new Live_Helicopter(200).setHardness(1.0F).setUnlocalizedName("Live_Helicopter").setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Fair_FreeFall = new Live_Fair_FreeFall(201).setHardness(1.0F).setUnlocalizedName("Live_Fair_FreeFall").setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Mill = new Live_Mill(202).setHardness(1.0F).setUnlocalizedName("Live_Mill").setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Cinema = new Live_Cinema(200).setHardness(1.0F).setUnlocalizedName("Live_Cinema").setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Flying_Helicopter = new Live_Flying_Helicopter(201).setHardness(1.0F).setUnlocalizedName("Live_Flying_Helicopter").setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Bus = new Live_Bus(200).setHardness(1.0F).setUnlocalizedName("Live_Bus").setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Bus2 = new Live_Bus2(201).setHardness(1.0F).setUnlocalizedName("Live_Bus2").setCreativeTab(IMSM.LiveStructures);
	public static Block BlockFerrisWheel = new BlockFerrisWheel(200).setHardness(1.0F).setUnlocalizedName("BlockFerrisWheel").setCreativeTab(IMSM.LiveStructures);
	
	public static Block ChristmasHouse = new ChristmasHouse(200).setHardness(1.0F).setUnlocalizedName("ChristmasHouse").setCreativeTab(IMSM.Other);
	public static Block ChristmasHouse2 = new ChristmasHouse2(201).setHardness(1.0F).setUnlocalizedName("ChristmasHouse2").setCreativeTab(IMSM.Other);
	public static Block ChristmasHouse3 = new ChristmasHouse3(202).setHardness(1.0F).setUnlocalizedName("ChristmasHouse3").setCreativeTab(IMSM.Other);
	public static Block ChristmasSleigh = new ChristmasSleigh(203).setHardness(1.0F).setUnlocalizedName("ChristmasSleigh").setCreativeTab(IMSM.Other);
	public static Block ChristmasSleigh2 = new ChristmasSleigh2(204).setHardness(1.0F).setUnlocalizedName("ChristmasSleigh2").setCreativeTab(IMSM.Other);
	public static Block ChristmasSnowman = new ChristmasSnowman(205).setHardness(1.0F).setUnlocalizedName("ChristmasSnowman").setCreativeTab(IMSM.Other);
	public static Block ChristmasTree = new ChristmasTree(206).setHardness(1.0F).setUnlocalizedName("ChristmasTree").setCreativeTab(IMSM.Other);
	public static Block ChristmasMarket = new ChristmasMarket(207).setHardness(1.0F).setUnlocalizedName("ChristmasMarket").setCreativeTab(IMSM.Other);
	public static Block Live_WaterMill = new Live_WaterMill(200).setHardness(1.0F).setUnlocalizedName("Live_WaterMill").setCreativeTab(IMSM.LiveStructures);

	public static Block BlockUnlimited = new BlockUnlimited(200).setHardness(1.0F).setUnlocalizedName("BlockUnlimited").setCreativeTab(IMSM.User);

	public static ArrayList<Block> userBlocks = registerUserBlocks();
	
	public static int dialoge = 0;

	public static LiveStructure currentInput;


	public void reg(Block block) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
	
	private static ArrayList<Block> registerUserBlocks() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		File registry = new File("structures/registry.txt");
		//System.out.println(registry.getAbsolutePath());
		if(registry.exists()){
		BufferedReader br = null;
		try  {
			br = new BufferedReader(new FileReader(registry));
		    String line;
		    while ((line = br.readLine()) != null) {
		       blocks.add(new BlockUserStructure(line).setHardness(1.0F).setUnlocalizedName(line).setCreativeTab(IMSM.User));
		    }
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(br!=null){
				br.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		return blocks;
	}

	public void regAll(){
		reg(LiveStructureRemover);

		reg(DecorationGrassNorthEastSouthWest);
		reg(DecorationParkEast);
		reg(DecorationParkingGarageEast);
		reg(DecorationParkingGarageNorth);
		reg(DecorationParkingGarageSouth);
		reg(DecorationParkingGarageWest);
		reg(DecorationParkingLotsEast);
		reg(DecorationParkingLotsNorth);
		reg(DecorationParkingLotsSouth);
		reg(DecorationParkingLotsWest);
		reg(DecorationParkNorth);
		reg(DecorationParkSouth);
		reg(DecorationParkWest);
		reg(DecorationPlazaFountainNorthEastSouthWest);
		reg(DecorationPlazaNorthEastSouthWest);
		reg(DecorationSoccerStadiumEastWest);
		reg(DecorationSoccerStadiumNorthSouth);
		reg(DecorationSquareNorthEastSouthWest);
		reg(DecorationSquareTreeEast);
		reg(DecorationSquareTreeNorth);
		reg(DecorationSquareTreeSouth);
		reg(DecorationSquareTreeWest);
		reg(FoodCarrotsEastWest);
		reg(FoodCarrotsNorthSouth);
		reg(FoodFarmEast);
		reg(FoodFarmNorth);
		reg(FoodFarmSouth);
		reg(FoodFarmWest);
		reg(FoodPotatoesNorthEastSouthWest);
		reg(FoodStableEastWest);
		reg(FoodStableNorthSouth);
		reg(FoodWheatNorthEastSouthWest);
		reg(IndustryHigh_DensityBlueEast);
		reg(IndustryHigh_DensityBlueNorth);
		reg(IndustryHigh_DensityBlueSouth);
		reg(IndustryHigh_DensityBlueWest);
		reg(IndustryHigh_DensityBrickEast);
		reg(IndustryHigh_DensityBrickNorth);
		reg(IndustryHigh_DensityBrickSouth);
		reg(IndustryHigh_DensityBrickWest);
		reg(IndustryHigh_DensityChimneyEast);
		reg(IndustryHigh_DensityChimneyNorth);
		reg(IndustryHigh_DensityChimneySouth);
		reg(IndustryHigh_DensityChimneyWest);
		reg(IndustryHigh_DensityComputerChipEast);
		reg(IndustryHigh_DensityComputerChipNorth);
		reg(IndustryHigh_DensityComputerChipSouth);
		reg(IndustryHigh_DensityComputerChipWest);
		reg(IndustryHigh_DensityGreenEast);
		reg(IndustryHigh_DensityGreenNorth);
		reg(IndustryHigh_DensityGreenSouth);
		reg(IndustryHigh_DensityGreenWest);
		reg(IndustryHigh_DensityLightBlueEast);
		reg(IndustryHigh_DensityLightBlueNorth);
		reg(IndustryHigh_DensityLightBlueSouth);
		reg(IndustryHigh_DensityLightBlueWest);
		reg(IndustryLow_Density3DPrintingEast);
		reg(IndustryLow_Density3DPrintingNorth);
		reg(IndustryLow_Density3DPrintingSouth);
		reg(IndustryLow_Density3DPrintingWest);
		reg(IndustryLow_DensityBlueEast);
		reg(IndustryLow_DensityBlueNorth);
		reg(IndustryLow_DensityBlueSouth);
		reg(IndustryLow_DensityBlueWest);
		reg(IndustryLow_DensityBrickEast);
		reg(IndustryLow_DensityBrickEastWest);
		reg(IndustryLow_DensityBrickNorth);
		reg(IndustryLow_DensityBrickNorthSouth);
		reg(IndustryLow_DensityBrickSouth);
		reg(IndustryLow_DensityBrickWest);
		reg(IndustryLow_DensityBrownEast2);
		reg(IndustryLow_DensityBrownEast);
		reg(IndustryLow_DensityBrownNorth2);
		reg(IndustryLow_DensityBrownNorth);
		reg(IndustryLow_DensityBrownSouth2);
		reg(IndustryLow_DensityBrownSouth);
		reg(IndustryLow_DensityBrownWest2);
		reg(IndustryLow_DensityBrownWest);
		reg(IndustryLow_DensityChimneyEast);
		reg(IndustryLow_DensityChimneyNorth);
		reg(IndustryLow_DensityChimneySouth);
		reg(IndustryLow_DensityChimneyWest);
		reg(IndustryLow_DensityGreenEast);
		reg(IndustryLow_DensityGreenNorth);
		reg(IndustryLow_DensityGreenSouth);
		reg(IndustryLow_DensityGreenWest);
		reg(IndustryLow_DensityIronEast);
		reg(IndustryLow_DensityIronNorth);
		reg(IndustryLow_DensityIronSouth);
		reg(IndustryLow_DensityIronWest);
		reg(IndustryLow_DensityParabolicAntennaEast);
		reg(IndustryLow_DensityParabolicAntennaNorth);
		reg(IndustryLow_DensityParabolicAntennaSouth);
		reg(IndustryLow_DensityParabolicAntennaWest);
		reg(IndustryLow_DensityTankNorthEastSouthWest);
		reg(IndustryLow_DensityTelescopeEast);
		reg(IndustryLow_DensityTelescopeNorth);
		reg(IndustryLow_DensityTelescopeSouth);
		reg(IndustryLow_DensityTelescopeWest);
		reg(IndustryMedium_DensityBlueEast);
		reg(IndustryMedium_DensityBlueNorth);
		reg(IndustryMedium_DensityBlueSouth);
		reg(IndustryMedium_DensityBlueWest);
		reg(IndustryMedium_DensityBrickEast);
		reg(IndustryMedium_DensityBrickNorth);
		reg(IndustryMedium_DensityBrickSouth);
		reg(IndustryMedium_DensityBrickWest);
		reg(IndustryMedium_DensityBrownEast);
		reg(IndustryMedium_DensityBrownNorth);
		reg(IndustryMedium_DensityBrownSouth);
		reg(IndustryMedium_DensityBrownWest);
		reg(IndustryMedium_DensityChemicalPressEastWest);
		reg(IndustryMedium_DensityChemicalPressNorthSouth);
		reg(IndustryMedium_DensityChimneyEast);
		reg(IndustryMedium_DensityChimneyNorth);
		reg(IndustryMedium_DensityChimneySouth);
		reg(IndustryMedium_DensityChimneyWest);
		reg(IndustryMedium_DensityGreenEast);
		reg(IndustryMedium_DensityGreenNorth);
		reg(IndustryMedium_DensityGreenSouth);
		reg(IndustryMedium_DensityGreenWest);
		reg(IndustryMedium_DensityIceEast);
		reg(IndustryMedium_DensityIceNorth);
		reg(IndustryMedium_DensityIceSouth);
		reg(IndustryMedium_DensityIceWest);
		reg(IndustryMedium_DensitySandstoneEast);
		reg(IndustryMedium_DensitySandstoneNorth);
		reg(IndustryMedium_DensitySandstoneSouth);
		reg(IndustryMedium_DensitySandstoneWest);
		reg(IndustryMedium_DensityTankEast);
		reg(IndustryMedium_DensityTankNorth);
		reg(IndustryMedium_DensityTankSouth);
		reg(IndustryMedium_DensityTankWest);
		reg(OfficeHigh_DensityBrickEastWest);
		reg(OfficeHigh_DensityBrickNorthSouth);
		reg(OfficeHigh_DensityCyanEast);
		reg(OfficeHigh_DensityCyanNorth);
		reg(OfficeHigh_DensityCyanSouth);
		reg(OfficeHigh_DensityCyanWest);
		reg(OfficeHigh_DensityHoleOnTopEast);
		reg(OfficeHigh_DensityHoleOnTopNorth);
		reg(OfficeHigh_DensityHoleOnTopSouth);
		reg(OfficeHigh_DensityHoleOnTopWest);
		reg(OfficeHigh_DensityLightBlueEastWest);
		reg(OfficeHigh_DensityLightBlueNorthSouth);
		reg(OfficeHigh_DensitySpirolBuildingEast);
		reg(OfficeHigh_DensitySpirolBuildingNorth);
		reg(OfficeHigh_DensitySpirolBuildingSouth);
		reg(OfficeHigh_DensitySpirolBuildingWest);
		reg(OfficeLow_DensityBlueEast);
		reg(OfficeLow_DensityBlueNorth);
		reg(OfficeLow_DensityBlueSouth);
		reg(OfficeLow_DensityBlueWest);
		reg(OfficeLow_DensityGreenEast);
		reg(OfficeLow_DensityGreenNorth);
		reg(OfficeLow_DensityGreenSouth);
		reg(OfficeLow_DensityGreenWest);
		reg(OfficeLow_DensityWhiteEast);
		reg(OfficeLow_DensityWhiteNorth);
		reg(OfficeLow_DensityWhiteSouth);
		reg(OfficeLow_DensityWhiteWest);
		reg(OfficeLow_DensityYellowEast);
		reg(OfficeLow_DensityYellowNorth);
		reg(OfficeLow_DensityYellowSouth);
		reg(OfficeLow_DensityYellowWest);
		reg(OfficeMedium_DensityCyanEast);
		reg(OfficeMedium_DensityCyanNorth);
		reg(OfficeMedium_DensityCyanSouth);
		reg(OfficeMedium_DensityCyanWest);
		reg(OfficeMedium_DensityLightBlueEast);
		reg(OfficeMedium_DensityLightBlueNorth);
		reg(OfficeMedium_DensityLightBlueSouth);
		reg(OfficeMedium_DensityLightBlueWest);
		reg(OfficeMedium_DensityPinkEast);
		reg(OfficeMedium_DensityPinkNorth);
		reg(OfficeMedium_DensityPinkSouth);
		reg(OfficeMedium_DensityPinkWest);
		reg(OfficeMedium_DensitySandstoneEast);
		reg(OfficeMedium_DensitySandstoneNorth);
		reg(OfficeMedium_DensitySandstoneSouth);
		reg(OfficeMedium_DensitySandstoneWest);
		reg(PublicFireServiceBigEast);
		reg(PublicFireServiceBigNorth);
		reg(PublicFireServiceBigSouth);
		reg(PublicFireServiceBigWest);
		reg(PublicFireServiceSmallEast);
		reg(PublicFireServiceSmallNorth);
		reg(PublicFireServiceSmallSouth);
		reg(PublicFireServiceSmallWest);
		reg(PublicHospitalBigEast);
		reg(PublicHospitalBigNorth);
		reg(PublicHospitalBigSouth);
		reg(PublicHospitalBigWest);
		reg(PublicHospitalSmallEast);
		reg(PublicHospitalSmallNorth);
		reg(PublicHospitalSmallSouth);
		reg(PublicHospitalSmallWest);
		reg(PublicLibraryEastWest);
		reg(PublicLibraryNorthSouth);
		reg(PublicPoliceBigEast);
		reg(PublicPoliceBigNorth);
		reg(PublicPoliceBigSouth);
		reg(PublicPoliceBigWest);
		reg(PublicPoliceSmallEast);
		reg(PublicPoliceSmallNorth);
		reg(PublicPoliceSmallSouth);
		reg(PublicPoliceSmallWest);
		reg(PublicSchoolBigNorthEast);
		reg(PublicSchoolBigNorthWest);
		reg(PublicSchoolBigSouthEast);
		reg(PublicSchoolBigSouthWest);
		reg(PublicSchoolSmallNorthEast);
		reg(PublicSchoolSmallNorthWest);
		reg(PublicSchoolSmallSouthEast);
		reg(PublicSchoolSmallSouthWest);
		reg(PublicTownhallBigEastWest);
		reg(PublicTownhallBigNorthSouth);
		reg(PublicTownhallSmallEast);
		reg(PublicTownhallSmallNorth);
		reg(PublicTownhallSmallSouth);
		reg(PublicTownhallSmallWest);
		reg(PublicUniversityEast);
		reg(PublicUniversityNorth);
		reg(PublicUniversitySouth);
		reg(PublicUniversityWest);
		reg(ResidentalEnormous_DensityBlockNorthEastSouthWest);
		reg(ResidentalEnormous_DensityBrickBigEast);
		reg(ResidentalEnormous_DensityBrickBigNorth);
		reg(ResidentalEnormous_DensityBrickBigSouth);
		reg(ResidentalEnormous_DensityBrickBigWest);
		reg(ResidentalEnormous_DensityBrickSmallNorthEastSouthWest);
		reg(ResidentalEnormous_DensityGreyEast);
		reg(ResidentalEnormous_DensityGreyNorth);
		reg(ResidentalEnormous_DensityGreySouth);
		reg(ResidentalEnormous_DensityGreyWest);
		reg(ResidentalEnormous_DensityModernEast);
		reg(ResidentalEnormous_DensityModernNorth);
		reg(ResidentalEnormous_DensityModernSouth);
		reg(ResidentalEnormous_DensityModernWest);
		reg(ResidentalEnormous_DensityRedEastWest);
		reg(ResidentalEnormous_DensityRedNorthSouth);
		reg(ResidentalEnormous_DensityRoundNorthEastSouthWest);
		reg(ResidentalEnormous_DensityStoneEast2);
		reg(ResidentalEnormous_DensityStoneEast);
		reg(ResidentalEnormous_DensityStoneNorth2);
		reg(ResidentalEnormous_DensityStoneNorth);
		reg(ResidentalEnormous_DensityStoneSouth2);
		reg(ResidentalEnormous_DensityStoneSouth);
		reg(ResidentalEnormous_DensityStoneWest2);
		reg(ResidentalEnormous_DensityStoneWest);
		reg(ResidentalEnormous_DensityYellowNorthEastSouthWest);
		reg(ResidentalHigh_DensityBlueEast);
		reg(ResidentalHigh_DensityBlueEastWest);
		reg(ResidentalHigh_DensityBlueNorth);
		reg(ResidentalHigh_DensityBlueNorthSouth);
		reg(ResidentalHigh_DensityBlueSouth);
		reg(ResidentalHigh_DensityBlueWest);
		reg(ResidentalHigh_DensityBrickEast);
		reg(ResidentalHigh_DensityBrickEastWest);
		reg(ResidentalHigh_DensityBrickNorth);
		reg(ResidentalHigh_DensityBrickNorthSouth);
		reg(ResidentalHigh_DensityBrickSouth);
		reg(ResidentalHigh_DensityBrickWest);
		reg(ResidentalHigh_DensityGreenGreyEast);
		reg(ResidentalHigh_DensityGreenGreyNorth);
		reg(ResidentalHigh_DensityGreenGreySouth);
		reg(ResidentalHigh_DensityGreenGreyWest);
		reg(ResidentalHigh_DensityRedCornerNorthEast);
		reg(ResidentalHigh_DensityRedCornerNorthWest);
		reg(ResidentalHigh_DensityRedCornerSouthEast);
		reg(ResidentalHigh_DensityRedCornerSouthWest);
		reg(ResidentalHigh_DensityRedYellowEast);
		reg(ResidentalHigh_DensityRedYellowNorth);
		reg(ResidentalHigh_DensityRedYellowSouth);
		reg(ResidentalHigh_DensityRedYellowWest);
		reg(ResidentalHigh_DensityStoneEast2);
		reg(ResidentalHigh_DensityStoneEast);
		reg(ResidentalHigh_DensityStoneNorth2);
		reg(ResidentalHigh_DensityStoneNorth);
		reg(ResidentalHigh_DensityStoneSouth2);
		reg(ResidentalHigh_DensityStoneSouth);
		reg(ResidentalHigh_DensityStoneWest2);
		reg(ResidentalHigh_DensityStoneWest);
		reg(ResidentalHigh_DensityYellowEast);
		reg(ResidentalHigh_DensityYellowNorth);
		reg(ResidentalHigh_DensityYellowSouth);
		reg(ResidentalHigh_DensityYellowWest);
		reg(ResidentalLow_DensityBeigeEast);
		reg(ResidentalLow_DensityBeigeNorth);
		reg(ResidentalLow_DensityBeigeSouth);
		reg(ResidentalLow_DensityBeigeWest);
		reg(ResidentalLow_DensityCyanEast);
		reg(ResidentalLow_DensityCyanNorth);
		reg(ResidentalLow_DensityCyanSouth);
		reg(ResidentalLow_DensityCyanWest);
		reg(ResidentalLow_DensityGreenEast2);
		reg(ResidentalLow_DensityGreenEast);
		reg(ResidentalLow_DensityGreenNorth2);
		reg(ResidentalLow_DensityGreenNorth);
		reg(ResidentalLow_DensityGreenSouth2);
		reg(ResidentalLow_DensityGreenSouth);
		reg(ResidentalLow_DensityGreenWest2);
		reg(ResidentalLow_DensityGreenWest);
		reg(ResidentalLow_DensityLightBlueEast2);
		reg(ResidentalLow_DensityLightBlueEast);
		reg(ResidentalLow_DensityLightBlueNorth2);
		reg(ResidentalLow_DensityLightBlueNorth);
		reg(ResidentalLow_DensityLightBlueSouth2);
		reg(ResidentalLow_DensityLightBlueSouth);
		reg(ResidentalLow_DensityLightBlueWest2);
		reg(ResidentalLow_DensityLightBlueWest);
		reg(ResidentalLow_DensityLightGreyEast);
		reg(ResidentalLow_DensityLightGreyNorth);
		reg(ResidentalLow_DensityLightGreySouth);
		reg(ResidentalLow_DensityLightGreyWest);
		reg(ResidentalLow_DensityModernEast);
		reg(ResidentalLow_DensityModernNorth);
		reg(ResidentalLow_DensityModernSouth);
		reg(ResidentalLow_DensityModernWest);
		reg(ResidentalLow_DensityOrangeEast);
		reg(ResidentalLow_DensityOrangeNorth);
		reg(ResidentalLow_DensityOrangeSouth);
		reg(ResidentalLow_DensityOrangeWest);
		reg(ResidentalLow_DensityRedEast);
		reg(ResidentalLow_DensityRedNorth);
		reg(ResidentalLow_DensityRedSouth);
		reg(ResidentalLow_DensityRedWest);
		reg(ResidentalLow_DensityStoneEast);
		reg(ResidentalLow_DensityStoneNorth);
		reg(ResidentalLow_DensityStoneSouth);
		reg(ResidentalLow_DensityStoneWest);
		reg(ResidentalLow_DensityWhiteEast);
		reg(ResidentalLow_DensityWhiteNorth);
		reg(ResidentalLow_DensityWhiteSouth);
		reg(ResidentalLow_DensityWhiteWest);
		reg(ResidentalLow_DensityWoodEast);
		reg(ResidentalLow_DensityWoodNorth);
		reg(ResidentalLow_DensityWoodSouth);
		reg(ResidentalLow_DensityWoodWest);
		reg(ResidentalLow_DensityYellowEast2);
		reg(ResidentalLow_DensityYellowEast);
		reg(ResidentalLow_DensityYellowNorth2);
		reg(ResidentalLow_DensityYellowNorth);
		reg(ResidentalLow_DensityYellowSouth2);
		reg(ResidentalLow_DensityYellowSouth);
		reg(ResidentalLow_DensityYellowWest2);
		reg(ResidentalLow_DensityYellowWest);
		reg(ResidentalMedium_DensityBlueGreenEast);
		reg(ResidentalMedium_DensityBlueGreenNorth);
		reg(ResidentalMedium_DensityBlueGreenSouth);
		reg(ResidentalMedium_DensityBlueGreenWest);
		reg(ResidentalMedium_DensityBlueRedEast);
		reg(ResidentalMedium_DensityBlueRedNorth);
		reg(ResidentalMedium_DensityBlueRedSouth);
		reg(ResidentalMedium_DensityBlueRedWest);
		reg(ResidentalMedium_DensityBrickEast);
		reg(ResidentalMedium_DensityBrickNorth);
		reg(ResidentalMedium_DensityBrickSouth);
		reg(ResidentalMedium_DensityBrickWest);
		reg(ResidentalMedium_DensityHorizontalEast);
		reg(ResidentalMedium_DensityHorizontalNorth);
		reg(ResidentalMedium_DensityHorizontalSouth);
		reg(ResidentalMedium_DensityHorizontalWest);
		reg(ResidentalMedium_DensityOrangeGreenEast);
		reg(ResidentalMedium_DensityOrangeGreenNorth);
		reg(ResidentalMedium_DensityOrangeGreenSouth);
		reg(ResidentalMedium_DensityOrangeGreenWest);
		reg(ResidentalMedium_DensityQuartzEast);
		reg(ResidentalMedium_DensityQuartzNorth);
		reg(ResidentalMedium_DensityQuartzSouth);
		reg(ResidentalMedium_DensityQuartzWest);
		reg(ResidentalMedium_DensityRedGreenEast);
		reg(ResidentalMedium_DensityRedGreenNorth);
		reg(ResidentalMedium_DensityRedGreenSouth);
		reg(ResidentalMedium_DensityRedGreenWest);
		reg(ResidentalMedium_DensityRoofEast);
		reg(ResidentalMedium_DensityRoofNorth);
		reg(ResidentalMedium_DensityRoofSouth);
		reg(ResidentalMedium_DensityRoofWest);
		reg(ResidentalMedium_DensityStone1EastWest);
		reg(ResidentalMedium_DensityStone1NorthSouth);
		reg(ResidentalMedium_DensityStone2EastWest);
		reg(ResidentalMedium_DensityStone2NorthSouth);
		reg(ResidentalMedium_DensityStoneCornerNorthEast);
		reg(ResidentalMedium_DensityStoneCornerNorthWest);
		reg(ResidentalMedium_DensityStoneCornerSouthEast);
		reg(ResidentalMedium_DensityStoneCornerSouthWest);
		reg(ResidentalMedium_DensityStoneEast);
		reg(ResidentalMedium_DensityStoneEndNorthEastWest);
		reg(ResidentalMedium_DensityStoneEndNorthSouthEast);
		reg(ResidentalMedium_DensityStoneEndNorthSouthWest);
		reg(ResidentalMedium_DensityStoneEndSouthEastWest);
		reg(ResidentalMedium_DensityStoneNorth);
		reg(ResidentalMedium_DensityStoneSouth);
		reg(ResidentalMedium_DensityStoneWest);
		reg(ResidentalMedium_DensityVerticalEast);
		reg(ResidentalMedium_DensityVerticalNorth);
		reg(ResidentalMedium_DensityVerticalSouth);
		reg(ResidentalMedium_DensityVerticalWest);
		reg(ResidentalMedium_DensityYellowRedEast);
		reg(ResidentalMedium_DensityYellowRedNorth);
		reg(ResidentalMedium_DensityYellowRedSouth);
		reg(ResidentalMedium_DensityYellowRedWest);
		reg(ShoppingHigh_DensityQuartzEastWest);
		reg(ShoppingHigh_DensityQuartzNorthSouth);
		reg(ShoppingLow_DensityBrickEast);
		reg(ShoppingLow_DensityBrickNorth);
		reg(ShoppingLow_DensityBrickSouth);
		reg(ShoppingLow_DensityBrickWest);
		reg(ShoppingLow_DensityGreenEast);
		reg(ShoppingLow_DensityGreenNorth);
		reg(ShoppingLow_DensityGreenSouth);
		reg(ShoppingLow_DensityGreenWest);
		reg(ShoppingLow_DensityOrangeEast);
		reg(ShoppingLow_DensityOrangeNorth);
		reg(ShoppingLow_DensityOrangeSouth);
		reg(ShoppingLow_DensityOrangeWest);
		reg(ShoppingLow_DensityPinkEast);
		reg(ShoppingLow_DensityPinkNorth);
		reg(ShoppingLow_DensityPinkSouth);
		reg(ShoppingLow_DensityPinkWest);
		reg(ShoppingMedium_DensityModernEast);
		reg(ShoppingMedium_DensityModernNorth);
		reg(ShoppingMedium_DensityModernSouth);
		reg(ShoppingMedium_DensityModernWest);
		reg(ShoppingMedium_DensityQuartzEast);
		reg(ShoppingMedium_DensityQuartzNorth);
		reg(ShoppingMedium_DensityQuartzSouth);
		reg(ShoppingMedium_DensityQuartzWest);
		reg(TransportAirportRunway_EastWestBuilding_North);
		reg(TransportAirportRunway_EastWestBuilding_South);
		reg(TransportAirportRunway_NorthSouthBuilding_East);
		reg(TransportAirportRunway_NorthSouthBuilding_West);
		reg(TransportAvenue1EastWest);
		reg(TransportAvenue1NorthSouth);
		reg(TransportAvenue2EastWest);
		reg(TransportAvenue2NorthSouth);
		reg(TransportAvenueEEast);
		reg(TransportAvenueENorth);
		reg(TransportAvenueESouth);
		reg(TransportAvenueEWest);
		reg(TransportAvenueLNorthEast);
		reg(TransportAvenueLNorthWest);
		reg(TransportAvenueLSouthEast);
		reg(TransportAvenueLSouthWest);
		reg(TransportAvenueTNorthEastWest);
		reg(TransportAvenueTNorthSouthEast);
		reg(TransportAvenueTNorthSouthWest);
		reg(TransportAvenueTSouthEastWest);
		reg(TransportAvenueXNorthSouthEastWest);
		reg(TransportBridgeAvenue1EastWest);
		reg(TransportBridgeAvenue1NorthSouth);
		reg(TransportBridgeAvenue2NorthSouth);
		reg(TransportBridgeAvenue2SouthWest);
		reg(TransportBridgeAvenue3EastWest);
		reg(TransportBridgeAvenue3NorthSouth);
		reg(TransportBridgeAvenue4EastWest);
		reg(TransportBridgeAvenue4NorthSouth);
		reg(TransportBridgeAvenueLNorthEast);
		reg(TransportBridgeAvenueLNorthWest);
		reg(TransportBridgeAvenueLSouthEast);
		reg(TransportBridgeAvenueLSouthWest);
		reg(TransportBridgeHighway1EastWest);
		reg(TransportBridgeHighway1NorthSouth);
		reg(TransportBridgeHighway2EastWest);
		reg(TransportBridgeHighway2NorthSouth);
		reg(TransportBridgeHighway3EastWest);
		reg(TransportBridgeHighway3NorthSouth);
		reg(TransportBridgeHighway4EastWest);
		reg(TransportBridgeHighway4NorthSouth);
		reg(TransportBridgeHighwayLNorthEast);
		reg(TransportBridgeHighwayLNorthWest);
		reg(TransportBridgeHighwayLSouthEast);
		reg(TransportBridgeHighwayLSouthWest);
		reg(TransportBridgeRoad1EastWest);
		reg(TransportBridgeRoad1NorthSouth);
		reg(TransportBridgeRoad2EastWest);
		reg(TransportBridgeRoad2NorthSouth);
		reg(TransportBridgeRoadLNorthEast);
		reg(TransportBridgeRoadLNorthWest);
		reg(TransportBridgeRoadLSouthEast);
		reg(TransportBridgeRoadLSouthWest);
		reg(TransportBridgeStreet1EastWest);
		reg(TransportBridgeStreet1NorthSouth);
		reg(TransportBridgeStreet2EastWest);
		reg(TransportBridgeStreet2NorthSouth);
		reg(TransportBridgeStreetLNorthEast);
		reg(TransportBridgeStreetLNorthWest);
		reg(TransportBridgeStreetLSouthEast);
		reg(TransportBridgeStreetLSouthWest);
		reg(TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside);
		reg(TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside);
		reg(TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside);
		reg(TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside);
		reg(TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside);
		reg(TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside);
		reg(TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside);
		reg(TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside);
		reg(TransportConnectorAvenue_StreetLAvenue_EastStreet_North);
		reg(TransportConnectorAvenue_StreetLAvenue_EastStreet_South);
		reg(TransportConnectorAvenue_StreetLAvenue_NorthStreet_East);
		reg(TransportConnectorAvenue_StreetLAvenue_NorthStreet_West);
		reg(TransportConnectorAvenue_StreetLAvenue_SouthStreet_East);
		reg(TransportConnectorAvenue_StreetLAvenue_SouthStreet_West);
		reg(TransportConnectorAvenue_StreetLAvenue_WestStreet_North);
		reg(TransportConnectorAvenue_StreetLAvenue_WestStreet_South);
		reg(TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth);
		reg(TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North);
		reg(TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South);
		reg(TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East);
		reg(TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West);
		reg(TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest);
		reg(TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest);
		reg(TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth);
		reg(TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth);
		reg(TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest);
		reg(TransportConnectorBridge_AvenueBridge_EastAvenue_West);
		reg(TransportConnectorBridge_AvenueBridge_NorthAvenue_South);
		reg(TransportConnectorBridge_AvenueBridge_SouthAvenue_North);
		reg(TransportConnectorBridge_AvenueBridge_WestAvenue_East);
		reg(TransportConnectorBridge_RoadBridge_EastRoad_West);
		reg(TransportConnectorBridge_RoadBridge_NorthRoad_South);
		reg(TransportConnectorBridge_RoadBridge_SouthRoad_North);
		reg(TransportConnectorBridge_RoadBridge_WestRoad_East);
		reg(TransportConnectorBridge_StreetBridge_EastStreet_West);
		reg(TransportConnectorBridge_StreetBridge_NorthStreet_South);
		reg(TransportConnectorBridge_StreetBridge_SouthStreet_North);
		reg(TransportConnectorBridge_StreetBridge_WestStreet_East);
		reg(TransportConnectorHighway_AvenueHighway_EastAvenue_West);
		reg(TransportConnectorHighway_AvenueHighway_NorthAvenue_South);
		reg(TransportConnectorHighway_AvenueHighway_SouthAvenue_North);
		reg(TransportConnectorHighway_AvenueHighway_WestAvenue_East);
		reg(TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West);
		reg(TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South);
		reg(TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North);
		reg(TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East);
		reg(TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West);
		reg(TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South);
		reg(TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North);
		reg(TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East);
		reg(TransportHarbourBigEast);
		reg(TransportHarbourBigNorth);
		reg(TransportHarbourBigSouth);
		reg(TransportHarbourBigWest);
		reg(TransportHarbourSide1CornerNorthEast);
		reg(TransportHarbourSide1CornerNorthWest);
		reg(TransportHarbourSide1CornerSouthEast);
		reg(TransportHarbourSide1CornerSouthWest);
		reg(TransportHarbourSide2CornerCraneEast);
		reg(TransportHarbourSide2CornerCraneNorth);
		reg(TransportHarbourSide2CornerCraneSouth);
		reg(TransportHarbourSide2CornerCraneWest);
		reg(TransportHarbourSide2CornerEast);
		reg(TransportHarbourSide2CornerNorth);
		reg(TransportHarbourSide2CornerSouth);
		reg(TransportHarbourSide2CornerWest);
		reg(TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast);
		reg(TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest);
		reg(TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest);
		reg(TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest);
		reg(TransportHarbourSmallEast);
		reg(TransportHarbourSmallNorth);
		reg(TransportHarbourSmallSouth);
		reg(TransportHarbourSmallWest);
		reg(TransportHighway05EastWestNorthside);
		reg(TransportHighway05EastWestSouthside);
		reg(TransportHighway05NorthSouthEastside);
		reg(TransportHighway05NorthSouthWestside);
		reg(TransportHighway1EastWest);
		reg(TransportHighway1NorthSouth);
		reg(TransportHighway2EastWest);
		reg(TransportHighway2NorthSouth);
		reg(TransportHighwayDrivewayEastWestEastside);
		reg(TransportHighwayDrivewayEastWestWestside);
		reg(TransportHighwayDrivewayExitEastWestEastside);
		reg(TransportHighwayDrivewayExitEastWestWestside);
		reg(TransportHighwayDrivewayExitNorthSouthNorthside);
		reg(TransportHighwayDrivewayExitNorthSouthSouthside);
		reg(TransportHighwayDrivewayNorthSouthNorthside);
		reg(TransportHighwayDrivewayNorthSouthSouthside);
		reg(TransportHighwayExitEastWestEastside);
		reg(TransportHighwayExitEastWestWestside);
		reg(TransportHighwayExitNorthSouthNorthside);
		reg(TransportHighwayExitNorthSouthSouthside);
		reg(TransportHighwayFloor05EastWestNorthside);
		reg(TransportHighwayFloor05EastWestSouthside);
		reg(TransportHighwayFloor05NorthSouthEastside);
		reg(TransportHighwayFloor05NorthSouthWestside);
		reg(TransportHighwayFloor1EastWest);
		reg(TransportHighwayFloor1NorthSouth);
		reg(TransportHighwayFloor2EastWest);
		reg(TransportHighwayFloor2NorthSouth);
		reg(TransportHighwayFloorDrivewayEastWestEastside);
		reg(TransportHighwayFloorDrivewayEastWestWestside);
		reg(TransportHighwayFloorDrivewayExitEastWestEastside);
		reg(TransportHighwayFloorDrivewayExitEastWestWestside);
		reg(TransportHighwayFloorDrivewayExitNorthSouthNorthside);
		reg(TransportHighwayFloorDrivewayExitNorthSouthSouthside);
		reg(TransportHighwayFloorDrivewayNorthSouthNorthside);
		reg(TransportHighwayFloorDrivewayNorthSouthSouthside);
		reg(TransportHighwayFloorExitEastWestEast);
		reg(TransportHighwayFloorExitEastWestWestside);
		reg(TransportHighwayFloorExitNorthSouthNorthside);
		reg(TransportHighwayFloorExitNorthSouthSouthside);
		reg(TransportHighwayFloorLNorthEast);
		reg(TransportHighwayFloorLNorthWest);
		reg(TransportHighwayFloorLSouthEast);
		reg(TransportHighwayFloorLSouthWest);
		reg(TransportHighwayFloorTNorthEastWest);
		reg(TransportHighwayFloorTNorthSouthEast);
		reg(TransportHighwayFloorTNorthSouthWest);
		reg(TransportHighwayFloorTSouthEastWest);
		reg(TransportHighwayFloorXNorthEastSouthWest);
		reg(TransportHighwayLNorthEast);
		reg(TransportHighwayLNorthWest);
		reg(TransportHighwayLSouthEast);
		reg(TransportHighwayLSouthWest);
		reg(TransportHighwayTNorthEastWest);
		reg(TransportHighwayTNorthSouthEast);
		reg(TransportHighwayTNorthSouthWest);
		reg(TransportHighwayTSouthEastWest);
		reg(TransportHighwayXNorthEastSouthWest);
		reg(TransportPublicConnectorHightram_TramHightram_EastTram_West);
		reg(TransportPublicConnectorHightram_TramHightram_NorthTram_South);
		reg(TransportPublicConnectorHightram_TramHightram_SouthTram_North);
		reg(TransportPublicConnectorHightram_TramHightram_WestTram_East);
		reg(TransportPublicHightram1EastWest);
		reg(TransportPublicHightram1NorthSouth);
		reg(TransportPublicHightramEEast);
		reg(TransportPublicHightramENorth);
		reg(TransportPublicHightramESouth);
		reg(TransportPublicHightramEWest);
		reg(TransportPublicHightramLNorthEast);
		reg(TransportPublicHightramLNorthWest);
		reg(TransportPublicHightramLSouthEast);
		reg(TransportPublicHightramLSouthWest);
		reg(TransportPublicHightramStationEastWest);
		reg(TransportPublicHightramStationNorthSouth);
		reg(TransportPublicHightramXNorthEastSouthWest);
		reg(TransportPublicTram1EastWest);
		reg(TransportPublicTram1NorthSouth);
		reg(TransportPublicTramEEast);
		reg(TransportPublicTramENorth);
		reg(TransportPublicTramESouth);
		reg(TransportPublicTramEWest);
		reg(TransportPublicTramLNorthEast);
		reg(TransportPublicTramLNorthWest);
		reg(TransportPublicTramLSouthEast);
		reg(TransportPublicTramLSouthWest);
		reg(TransportPublicTram_on_road1EastWest);
		reg(TransportPublicTram_on_road1NorthSouth);
		reg(TransportPublicTram_on_roadEEast);
		reg(TransportPublicTram_on_roadENorth);
		reg(TransportPublicTram_on_roadESouth);
		reg(TransportPublicTram_on_roadEWest);
		reg(TransportPublicTram_on_roadLNorthEast);
		reg(TransportPublicTram_on_roadLNorthWest);
		reg(TransportPublicTram_on_roadLSouthEast);
		reg(TransportPublicTram_on_roadLSouthWest);
		reg(TransportPublicTramStationEastWest);
		reg(TransportPublicTramStationNorthSouth);
		reg(TransportPublicTramXNorthEastSouthWest);
		reg(TransportRoad1EastWest);
		reg(TransportRoad1NorthSouth);
		reg(TransportRoadEEast);
		reg(TransportRoadENorth);
		reg(TransportRoadESouth);
		reg(TransportRoadEWest);
		reg(TransportRoadLNorthEast);
		reg(TransportRoadLNorthWest);
		reg(TransportRoadLSouthEast);
		reg(TransportRoadLSouthWest);
		reg(TransportRoadTNorthEastWest);
		reg(TransportRoadTNorthSouthEast);
		reg(TransportRoadTNorthSouthWest);
		reg(TransportRoadTSouthEastWest);
		reg(TransportRoadXNorthEastSouthWest);
		reg(TransportStreet1EastWest);
		reg(TransportStreet1NorthSouth);
		reg(TransportStreetEEast);
		reg(TransportStreetENorth);
		reg(TransportStreetESouth);
		reg(TransportStreetEWest);
		reg(TransportStreetLNorthEast);
		reg(TransportStreetLNorthWest);
		reg(TransportStreetLSouthEast);
		reg(TransportStreetLSouthWest);
		reg(TransportStreetRoundaboutNorthEastSouthWest);
		reg(TransportStreetTNorthEastWest);
		reg(TransportStreetTNorthSouthEast);
		reg(TransportStreetTNorthSouthWest);
		reg(TransportStreetTSouthEastWest);
		reg(TransportStreetXNorthEastSouthWest);
		reg(TransportWater1CornerNorthEast);
		reg(TransportWater1CornerNorthWest);
		reg(TransportWater1CornerSouthEast);
		reg(TransportWater1CornerSouthWest);
		reg(TransportWater2CornerEast);
		reg(TransportWater2CornerNorth);
		reg(TransportWater2CornerSouth);
		reg(TransportWater2CornerWest);
		reg(TransportWater3CornerNorthEast_NorthWest_SouthEast);
		reg(TransportWater3CornerNorthEast_NorthWest_SouthWest);
		reg(TransportWater3CornerSouthEast_SouthWest_NorthEast);
		reg(TransportWater3CornerSouthEast_SouthWest_NorthWest);
		reg(TransportWater4CornerNorthSouthEastWest);
		reg(UtilityPower_NuclearEast);
		reg(UtilityPower_NuclearNorth);
		reg(UtilityPower_NuclearSouth);
		reg(UtilityPower_NuclearWest);
		reg(UtilityPower_OilCoalEast);
		reg(UtilityPower_OilCoalNorth);
		reg(UtilityPower_OilCoalSouth);
		reg(UtilityPower_OilCoalWest);
		reg(UtilityPower_SunNorthEastSouthWest);
		reg(UtilityPower_WindEast);
		reg(UtilityPower_WindNorth);
		reg(UtilityPower_WindSouth);
		reg(UtilityPower_WindWest);
		reg(UtilityPumpjackEastWest);
		reg(UtilityPumpjackNorthSouth);
		reg(UtilityScrap_BurningEast);
		reg(UtilityScrap_BurningNorth);
		reg(UtilityScrap_BurningSouth);
		reg(UtilityScrap_BurningWest);
		reg(UtilityScrap_HeapEast);
		reg(UtilityScrap_HeapNorth);
		reg(UtilityScrap_HeapSouth);
		reg(UtilityScrap_HeapWest);
		reg(UtilityScrap_RecycleEast);
		reg(UtilityScrap_RecycleNorth);
		reg(UtilityScrap_RecycleSouth);
		reg(UtilityScrap_RecycleWest);
		reg(UtilityWater_PumpEast);
		reg(UtilityWater_PumpNorth);
		reg(UtilityWater_PumpSouth);
		reg(UtilityWater_PumpWest);
		reg(UtilityWater_TowerNorthEastSouthWest);
		reg(UtilityWater_TreatmentEast);
		reg(UtilityWater_TreatmentNorth);
		reg(UtilityWater_TreatmentSouth);
		reg(UtilityWater_TreatmentWest);
		reg(BlockAirBalloon);
		reg(BlockAirplane);
		//getClass().reg(BlockApplepie);
		reg(BlockArena1);
		reg(BlockArena2);
		reg(BlockBigPyramid);
		reg(BlockBoat);
		reg(BlockBunker);
		//reg(BlockCactus2);
		//reg(BlockCake2);
		reg(BlockCastleTower);
		//reg(BlockCave);
		//reg(BlockColumn);
		reg(BlockCosyHouse);
		reg(BlockDungeon);
		reg(BlockEnchantmentRoom);
		reg(BlockFarm2);
		reg(BlockFarm3);
		reg(BlockFarm4);
		reg(BlockFarm);
		//reg(BlockFloatingSphere);
		reg(BlockGiantTree);
		//reg(BlockGlassHouse);
		reg(BlockHountedHouse);
		//reg(BlockHouse2);
		reg(BlockHouse);
		reg(BlockHouseTrap1);
		reg(BlockHouseTrap2);
		//reg(BlockLeaves2);
		reg(BlockLighthouse);
		reg(BlockMegaHouse2);
		reg(BlockMegaHouse);
		reg(BlockMegaTower);
		//reg(BlockPenIron);
		//reg(BlockPenNether);
		//reg(BlockPenWood);
		reg(BlockPlane);
		reg(BlockPrison2);
		reg(BlockPrison);
		reg(BlockPyramid);
		reg(BlockRollerCoaster2);
		reg(BlockRollercoaster);
		//reg(BlockShelter);
		//reg(BlockSkyscraper2);
		reg(BlockSkyscraper);
		//reg(BlockStadium2);
		reg(BlockStadium);
		//reg(BlockStandardBrickHouse);
		reg(BlockStoreHouse);
		//reg(BlockStreet);
		reg(BlockTorch2);
		reg(BlockTower);
		reg(BlockWaterSlide);
		//reg(Remover16256);
		//reg(Remover1632);
		//reg(Remover168);
		reg(Remover16);
		//reg(Remover3216);
		//reg(Remover32256);
		//reg(Remover328);
		reg(Remover32);
		//reg(Remover64256);
		reg(Remover64);
		//reg(Remover816);
		//reg(Remover8256);
		//reg(Remover832);
		reg(Remover8);
		reg(RemoverLast);
		
		reg(OtherBrickHouse);
		reg(OtherGrandHouse);
		reg(OtherStable);
		reg(OtherSurvivorHouse2);
		reg(OtherSurvivorHouse3);
		reg(OtherSurvivorHouse4);
		reg(OtherSurvivorHouse5);
		reg(OtherSurvivorHouse6);
		reg(OtherSurvivorHouse7);
		reg(OtherSurvivorHouse8);
		reg(OtherSurvivorHouse);
		reg(OtherTemple);
		reg(SurvivalSmallBuilding);
		reg(SurvivalWoodenHouse);
		reg(WoodenHouse);
		reg(BlockCheckerboard);

		//reg(BlockAtlantis);
		reg(BlockBigWorld);
		reg(RandomAirballoon2);
		reg(RandomEntrance);
		reg(RandomFlyingShip);
		reg(RandomGreenTent);
		reg(RandomGreyTent);
		reg(RandomLightHouse);
		reg(RandomMinerTent);
		reg(RandomNetherEntranceSurvival);
		reg(RandomRandomBrickHouse);
		reg(RandomSurvivalHouse1);
		reg(RandomSurvivalHouseSandstone);
		reg(RandomTentCamp);
		reg(RandomWoodenHouse);
		reg(BlockCloud);
		
		reg(RandomBuildingComplex);
		reg(RandomImmense_Buildingcomplex);
		reg(RandomImmense_greenroof);
		reg(RandomImmense_White_House);
		reg(RandomImmense_WorkingBuilding);
		reg(RandomLittlePalace);
		reg(RandomLittleWoodenCabin);
		reg(RandomSandstoneBuilding);
		reg(RandomSandStoneChurch);
		reg(RandomSandstonewithFarm);
		reg(RandomSimpleSandstone);
		reg(RandomSpawnHouseProd);
		reg(RandomWoodenStonebrickHouse);
		reg(Live_Power_Windmill_East);

		reg(LiveAirBalloon);
		reg(LiveAirplane);
		reg(LiveBoat);
		reg(LiveFlyingShip2);
		reg(LiveFlyingShip);
		reg(LivePlane);
		reg(Live_Helicopter);
		reg(Live_Fair_FreeFall);
		reg(Live_Mill);
		reg(Live_Cinema);
		reg(Live_Flying_Helicopter);
		
		reg(Live_Bus);
		reg(Live_Bus2);
		reg(BlockFerrisWheel);
		
		reg(ChristmasHouse);
		reg(ChristmasHouse2);
		reg(ChristmasHouse3);
		reg(ChristmasSleigh);
		reg(ChristmasSleigh2);
		reg(ChristmasSnowman);
		reg(ChristmasTree);
		reg(ChristmasMarket);
		reg(Live_WaterMill);
		
		reg(BlockUnlimited);

		for(Block block : userBlocks){
			reg(block);
		}
	}

@EventHandler
	public void init(FMLInitializationEvent event){
	eventHandler=new modid.imsm.core.EventHandler();
	FMLCommonHandler.instance().bus().register(eventHandler);
	MinecraftForge.EVENT_BUS.register(new modid.imsm.core.ForgeEventHandler());
	regAll();
	proxy.registerRenderInformation();
	/*
		regAll();
		System.out.println("IMSM has rendered sooooo many blocks");
*/
	
	}
	   
	   @EventHandler
		public void preInit(FMLPreInitializationEvent e){
		   
		proxy.registerRenderInformation();
		//TODO: Thing commented without reason?
		//LanguageRegistry.instance().addStringLocalization("itemGroup.Structures", "en_US", "Structures");
		/**
		GameRegistry.addRecipe(new ItemStack(House, 1),
				"OOO", "OSO", "OOO", 'S', House, 'O', Items.leather);

			*/


		



		

		GameRegistry.registerBlock(LiveStructureRemover, "LiveStructureRemover");

		GameRegistry.registerBlock(DecorationGrassNorthEastSouthWest, "DecorationGrassNorthEastSouthWest");
		GameRegistry.registerBlock(DecorationParkEast, "DecorationParkEast");
		GameRegistry.registerBlock(DecorationParkingGarageEast, "DecorationParkingGarageEast");
		GameRegistry.registerBlock(DecorationParkingGarageNorth, "DecorationParkingGarageNorth");
		GameRegistry.registerBlock(DecorationParkingGarageSouth, "DecorationParkingGarageSouth");
		GameRegistry.registerBlock(DecorationParkingGarageWest, "DecorationParkingGarageWest");
		GameRegistry.registerBlock(DecorationParkingLotsEast, "DecorationParkingLotsEast");
		GameRegistry.registerBlock(DecorationParkingLotsNorth, "DecorationParkingLotsNorth");
		GameRegistry.registerBlock(DecorationParkingLotsSouth, "DecorationParkingLotsSouth");
		GameRegistry.registerBlock(DecorationParkingLotsWest, "DecorationParkingLotsWest");
		GameRegistry.registerBlock(DecorationParkNorth, "DecorationParkNorth");
		GameRegistry.registerBlock(DecorationParkSouth, "DecorationParkSouth");
		GameRegistry.registerBlock(DecorationParkWest, "DecorationParkWest");
		GameRegistry.registerBlock(DecorationPlazaFountainNorthEastSouthWest, "DecorationPlazaFountainNorthEastSouthWest");
		GameRegistry.registerBlock(DecorationPlazaNorthEastSouthWest, "DecorationPlazaNorthEastSouthWest");
		GameRegistry.registerBlock(DecorationSoccerStadiumEastWest, "DecorationSoccerStadiumEastWest");
		GameRegistry.registerBlock(DecorationSoccerStadiumNorthSouth, "DecorationSoccerStadiumNorthSouth");
		GameRegistry.registerBlock(DecorationSquareNorthEastSouthWest, "DecorationSquareNorthEastSouthWest");
		GameRegistry.registerBlock(DecorationSquareTreeEast, "DecorationSquareTreeEast");
		GameRegistry.registerBlock(DecorationSquareTreeNorth, "DecorationSquareTreeNorth");
		GameRegistry.registerBlock(DecorationSquareTreeSouth, "DecorationSquareTreeSouth");
		GameRegistry.registerBlock(DecorationSquareTreeWest, "DecorationSquareTreeWest");
		GameRegistry.registerBlock(FoodCarrotsEastWest, "FoodCarrotsEastWest");
		GameRegistry.registerBlock(FoodCarrotsNorthSouth, "FoodCarrotsNorthSouth");
		GameRegistry.registerBlock(FoodFarmEast, "FoodFarmEast");
		GameRegistry.registerBlock(FoodFarmNorth, "FoodFarmNorth");
		GameRegistry.registerBlock(FoodFarmSouth, "FoodFarmSouth");
		GameRegistry.registerBlock(FoodFarmWest, "FoodFarmWest");
		GameRegistry.registerBlock(FoodPotatoesNorthEastSouthWest, "FoodPotatoesNorthEastSouthWest");
		GameRegistry.registerBlock(FoodStableEastWest, "FoodStableEastWest");
		GameRegistry.registerBlock(FoodStableNorthSouth, "FoodStableNorthSouth");
		GameRegistry.registerBlock(FoodWheatNorthEastSouthWest, "FoodWheatNorthEastSouthWest");
		GameRegistry.registerBlock(IndustryHigh_DensityBlueEast, "IndustryHigh_DensityBlueEast");
		GameRegistry.registerBlock(IndustryHigh_DensityBlueNorth, "IndustryHigh_DensityBlueNorth");
		GameRegistry.registerBlock(IndustryHigh_DensityBlueSouth, "IndustryHigh_DensityBlueSouth");
		GameRegistry.registerBlock(IndustryHigh_DensityBlueWest, "IndustryHigh_DensityBlueWest");
		GameRegistry.registerBlock(IndustryHigh_DensityBrickEast, "IndustryHigh_DensityBrickEast");
		GameRegistry.registerBlock(IndustryHigh_DensityBrickNorth, "IndustryHigh_DensityBrickNorth");
		GameRegistry.registerBlock(IndustryHigh_DensityBrickSouth, "IndustryHigh_DensityBrickSouth");
		GameRegistry.registerBlock(IndustryHigh_DensityBrickWest, "IndustryHigh_DensityBrickWest");
		GameRegistry.registerBlock(IndustryHigh_DensityChimneyEast, "IndustryHigh_DensityChimneyEast");
		GameRegistry.registerBlock(IndustryHigh_DensityChimneyNorth, "IndustryHigh_DensityChimneyNorth");
		GameRegistry.registerBlock(IndustryHigh_DensityChimneySouth, "IndustryHigh_DensityChimneySouth");
		GameRegistry.registerBlock(IndustryHigh_DensityChimneyWest, "IndustryHigh_DensityChimneyWest");
		GameRegistry.registerBlock(IndustryHigh_DensityComputerChipEast, "IndustryHigh_DensityComputerChipEast");
		GameRegistry.registerBlock(IndustryHigh_DensityComputerChipNorth, "IndustryHigh_DensityComputerChipNorth");
		GameRegistry.registerBlock(IndustryHigh_DensityComputerChipSouth, "IndustryHigh_DensityComputerChipSouth");
		GameRegistry.registerBlock(IndustryHigh_DensityComputerChipWest, "IndustryHigh_DensityComputerChipWest");
		GameRegistry.registerBlock(IndustryHigh_DensityGreenEast, "IndustryHigh_DensityGreenEast");
		GameRegistry.registerBlock(IndustryHigh_DensityGreenNorth, "IndustryHigh_DensityGreenNorth");
		GameRegistry.registerBlock(IndustryHigh_DensityGreenSouth, "IndustryHigh_DensityGreenSouth");
		GameRegistry.registerBlock(IndustryHigh_DensityGreenWest, "IndustryHigh_DensityGreenWest");
		GameRegistry.registerBlock(IndustryHigh_DensityLightBlueEast, "IndustryHigh_DensityLightBlueEast");
		GameRegistry.registerBlock(IndustryHigh_DensityLightBlueNorth, "IndustryHigh_DensityLightBlueNorth");
		GameRegistry.registerBlock(IndustryHigh_DensityLightBlueSouth, "IndustryHigh_DensityLightBlueSouth");
		GameRegistry.registerBlock(IndustryHigh_DensityLightBlueWest, "IndustryHigh_DensityLightBlueWest");
		GameRegistry.registerBlock(IndustryLow_Density3DPrintingEast, "IndustryLow_Density3DPrintingEast");
		GameRegistry.registerBlock(IndustryLow_Density3DPrintingNorth, "IndustryLow_Density3DPrintingNorth");
		GameRegistry.registerBlock(IndustryLow_Density3DPrintingSouth, "IndustryLow_Density3DPrintingSouth");
		GameRegistry.registerBlock(IndustryLow_Density3DPrintingWest, "IndustryLow_Density3DPrintingWest");
		GameRegistry.registerBlock(IndustryLow_DensityBlueEast, "IndustryLow_DensityBlueEast");
		GameRegistry.registerBlock(IndustryLow_DensityBlueNorth, "IndustryLow_DensityBlueNorth");
		GameRegistry.registerBlock(IndustryLow_DensityBlueSouth, "IndustryLow_DensityBlueSouth");
		GameRegistry.registerBlock(IndustryLow_DensityBlueWest, "IndustryLow_DensityBlueWest");
		GameRegistry.registerBlock(IndustryLow_DensityBrickEast, "IndustryLow_DensityBrickEast");
		GameRegistry.registerBlock(IndustryLow_DensityBrickEastWest, "IndustryLow_DensityBrickEastWest");
		GameRegistry.registerBlock(IndustryLow_DensityBrickNorth, "IndustryLow_DensityBrickNorth");
		GameRegistry.registerBlock(IndustryLow_DensityBrickNorthSouth, "IndustryLow_DensityBrickNorthSouth");
		GameRegistry.registerBlock(IndustryLow_DensityBrickSouth, "IndustryLow_DensityBrickSouth");
		GameRegistry.registerBlock(IndustryLow_DensityBrickWest, "IndustryLow_DensityBrickWest");
		GameRegistry.registerBlock(IndustryLow_DensityBrownEast2, "IndustryLow_DensityBrownEast2");
		GameRegistry.registerBlock(IndustryLow_DensityBrownEast, "IndustryLow_DensityBrownEast");
		GameRegistry.registerBlock(IndustryLow_DensityBrownNorth2, "IndustryLow_DensityBrownNorth2");
		GameRegistry.registerBlock(IndustryLow_DensityBrownNorth, "IndustryLow_DensityBrownNorth");
		GameRegistry.registerBlock(IndustryLow_DensityBrownSouth2, "IndustryLow_DensityBrownSouth2");
		GameRegistry.registerBlock(IndustryLow_DensityBrownSouth, "IndustryLow_DensityBrownSouth");
		GameRegistry.registerBlock(IndustryLow_DensityBrownWest2, "IndustryLow_DensityBrownWest2");
		GameRegistry.registerBlock(IndustryLow_DensityBrownWest, "IndustryLow_DensityBrownWest");
		GameRegistry.registerBlock(IndustryLow_DensityChimneyEast, "IndustryLow_DensityChimneyEast");
		GameRegistry.registerBlock(IndustryLow_DensityChimneyNorth, "IndustryLow_DensityChimneyNorth");
		GameRegistry.registerBlock(IndustryLow_DensityChimneySouth, "IndustryLow_DensityChimneySouth");
		GameRegistry.registerBlock(IndustryLow_DensityChimneyWest, "IndustryLow_DensityChimneyWest");
		GameRegistry.registerBlock(IndustryLow_DensityGreenEast, "IndustryLow_DensityGreenEast");
		GameRegistry.registerBlock(IndustryLow_DensityGreenNorth, "IndustryLow_DensityGreenNorth");
		GameRegistry.registerBlock(IndustryLow_DensityGreenSouth, "IndustryLow_DensityGreenSouth");
		GameRegistry.registerBlock(IndustryLow_DensityGreenWest, "IndustryLow_DensityGreenWest");
		GameRegistry.registerBlock(IndustryLow_DensityIronEast, "IndustryLow_DensityIronEast");
		GameRegistry.registerBlock(IndustryLow_DensityIronNorth, "IndustryLow_DensityIronNorth");
		GameRegistry.registerBlock(IndustryLow_DensityIronSouth, "IndustryLow_DensityIronSouth");
		GameRegistry.registerBlock(IndustryLow_DensityIronWest, "IndustryLow_DensityIronWest");
		GameRegistry.registerBlock(IndustryLow_DensityParabolicAntennaEast, "IndustryLow_DensityParabolicAntennaEast");
		GameRegistry.registerBlock(IndustryLow_DensityParabolicAntennaNorth, "IndustryLow_DensityParabolicAntennaNorth");
		GameRegistry.registerBlock(IndustryLow_DensityParabolicAntennaSouth, "IndustryLow_DensityParabolicAntennaSouth");
		GameRegistry.registerBlock(IndustryLow_DensityParabolicAntennaWest, "IndustryLow_DensityParabolicAntennaWest");
		GameRegistry.registerBlock(IndustryLow_DensityTankNorthEastSouthWest, "IndustryLow_DensityTankNorthEastSouthWest");
		GameRegistry.registerBlock(IndustryLow_DensityTelescopeEast, "IndustryLow_DensityTelescopeEast");
		GameRegistry.registerBlock(IndustryLow_DensityTelescopeNorth, "IndustryLow_DensityTelescopeNorth");
		GameRegistry.registerBlock(IndustryLow_DensityTelescopeSouth, "IndustryLow_DensityTelescopeSouth");
		GameRegistry.registerBlock(IndustryLow_DensityTelescopeWest, "IndustryLow_DensityTelescopeWest");
		GameRegistry.registerBlock(IndustryMedium_DensityBlueEast, "IndustryMedium_DensityBlueEast");
		GameRegistry.registerBlock(IndustryMedium_DensityBlueNorth, "IndustryMedium_DensityBlueNorth");
		GameRegistry.registerBlock(IndustryMedium_DensityBlueSouth, "IndustryMedium_DensityBlueSouth");
		GameRegistry.registerBlock(IndustryMedium_DensityBlueWest, "IndustryMedium_DensityBlueWest");
		GameRegistry.registerBlock(IndustryMedium_DensityBrickEast, "IndustryMedium_DensityBrickEast");
		GameRegistry.registerBlock(IndustryMedium_DensityBrickNorth, "IndustryMedium_DensityBrickNorth");
		GameRegistry.registerBlock(IndustryMedium_DensityBrickSouth, "IndustryMedium_DensityBrickSouth");
		GameRegistry.registerBlock(IndustryMedium_DensityBrickWest, "IndustryMedium_DensityBrickWest");
		GameRegistry.registerBlock(IndustryMedium_DensityBrownEast, "IndustryMedium_DensityBrownEast");
		GameRegistry.registerBlock(IndustryMedium_DensityBrownNorth, "IndustryMedium_DensityBrownNorth");
		GameRegistry.registerBlock(IndustryMedium_DensityBrownSouth, "IndustryMedium_DensityBrownSouth");
		GameRegistry.registerBlock(IndustryMedium_DensityBrownWest, "IndustryMedium_DensityBrownWest");
		GameRegistry.registerBlock(IndustryMedium_DensityChemicalPressEastWest, "IndustryMedium_DensityChemicalPressEastWest");
		GameRegistry.registerBlock(IndustryMedium_DensityChemicalPressNorthSouth, "IndustryMedium_DensityChemicalPressNorthSouth");
		GameRegistry.registerBlock(IndustryMedium_DensityChimneyEast, "IndustryMedium_DensityChimneyEast");
		GameRegistry.registerBlock(IndustryMedium_DensityChimneyNorth, "IndustryMedium_DensityChimneyNorth");
		GameRegistry.registerBlock(IndustryMedium_DensityChimneySouth, "IndustryMedium_DensityChimneySouth");
		GameRegistry.registerBlock(IndustryMedium_DensityChimneyWest, "IndustryMedium_DensityChimneyWest");
		GameRegistry.registerBlock(IndustryMedium_DensityGreenEast, "IndustryMedium_DensityGreenEast");
		GameRegistry.registerBlock(IndustryMedium_DensityGreenNorth, "IndustryMedium_DensityGreenNorth");
		GameRegistry.registerBlock(IndustryMedium_DensityGreenSouth, "IndustryMedium_DensityGreenSouth");
		GameRegistry.registerBlock(IndustryMedium_DensityGreenWest, "IndustryMedium_DensityGreenWest");
		GameRegistry.registerBlock(IndustryMedium_DensityIceEast, "IndustryMedium_DensityIceEast");
		GameRegistry.registerBlock(IndustryMedium_DensityIceNorth, "IndustryMedium_DensityIceNorth");
		GameRegistry.registerBlock(IndustryMedium_DensityIceSouth, "IndustryMedium_DensityIceSouth");
		GameRegistry.registerBlock(IndustryMedium_DensityIceWest, "IndustryMedium_DensityIceWest");
		GameRegistry.registerBlock(IndustryMedium_DensitySandstoneEast, "IndustryMedium_DensitySandstoneEast");
		GameRegistry.registerBlock(IndustryMedium_DensitySandstoneNorth, "IndustryMedium_DensitySandstoneNorth");
		GameRegistry.registerBlock(IndustryMedium_DensitySandstoneSouth, "IndustryMedium_DensitySandstoneSouth");
		GameRegistry.registerBlock(IndustryMedium_DensitySandstoneWest, "IndustryMedium_DensitySandstoneWest");
		GameRegistry.registerBlock(IndustryMedium_DensityTankEast, "IndustryMedium_DensityTankEast");
		GameRegistry.registerBlock(IndustryMedium_DensityTankNorth, "IndustryMedium_DensityTankNorth");
		GameRegistry.registerBlock(IndustryMedium_DensityTankSouth, "IndustryMedium_DensityTankSouth");
		GameRegistry.registerBlock(IndustryMedium_DensityTankWest, "IndustryMedium_DensityTankWest");
		GameRegistry.registerBlock(OfficeHigh_DensityBrickEastWest, "OfficeHigh_DensityBrickEastWest");
		GameRegistry.registerBlock(OfficeHigh_DensityBrickNorthSouth, "OfficeHigh_DensityBrickNorthSouth");
		GameRegistry.registerBlock(OfficeHigh_DensityCyanEast, "OfficeHigh_DensityCyanEast");
		GameRegistry.registerBlock(OfficeHigh_DensityCyanNorth, "OfficeHigh_DensityCyanNorth");
		GameRegistry.registerBlock(OfficeHigh_DensityCyanSouth, "OfficeHigh_DensityCyanSouth");
		GameRegistry.registerBlock(OfficeHigh_DensityCyanWest, "OfficeHigh_DensityCyanWest");
		GameRegistry.registerBlock(OfficeHigh_DensityHoleOnTopEast, "OfficeHigh_DensityHoleOnTopEast");
		GameRegistry.registerBlock(OfficeHigh_DensityHoleOnTopNorth, "OfficeHigh_DensityHoleOnTopNorth");
		GameRegistry.registerBlock(OfficeHigh_DensityHoleOnTopSouth, "OfficeHigh_DensityHoleOnTopSouth");
		GameRegistry.registerBlock(OfficeHigh_DensityHoleOnTopWest, "OfficeHigh_DensityHoleOnTopWest");
		GameRegistry.registerBlock(OfficeHigh_DensityLightBlueEastWest, "OfficeHigh_DensityLightBlueEastWest");
		GameRegistry.registerBlock(OfficeHigh_DensityLightBlueNorthSouth, "OfficeHigh_DensityLightBlueNorthSouth");
		GameRegistry.registerBlock(OfficeHigh_DensitySpirolBuildingEast, "OfficeHigh_DensitySpirolBuildingEast");
		GameRegistry.registerBlock(OfficeHigh_DensitySpirolBuildingNorth, "OfficeHigh_DensitySpirolBuildingNorth");
		GameRegistry.registerBlock(OfficeHigh_DensitySpirolBuildingSouth, "OfficeHigh_DensitySpirolBuildingSouth");
		GameRegistry.registerBlock(OfficeHigh_DensitySpirolBuildingWest, "OfficeHigh_DensitySpirolBuildingWest");
		GameRegistry.registerBlock(OfficeLow_DensityBlueEast, "OfficeLow_DensityBlueEast");
		GameRegistry.registerBlock(OfficeLow_DensityBlueNorth, "OfficeLow_DensityBlueNorth");
		GameRegistry.registerBlock(OfficeLow_DensityBlueSouth, "OfficeLow_DensityBlueSouth");
		GameRegistry.registerBlock(OfficeLow_DensityBlueWest, "OfficeLow_DensityBlueWest");
		GameRegistry.registerBlock(OfficeLow_DensityGreenEast, "OfficeLow_DensityGreenEast");
		GameRegistry.registerBlock(OfficeLow_DensityGreenNorth, "OfficeLow_DensityGreenNorth");
		GameRegistry.registerBlock(OfficeLow_DensityGreenSouth, "OfficeLow_DensityGreenSouth");
		GameRegistry.registerBlock(OfficeLow_DensityGreenWest, "OfficeLow_DensityGreenWest");
		GameRegistry.registerBlock(OfficeLow_DensityWhiteEast, "OfficeLow_DensityWhiteEast");
		GameRegistry.registerBlock(OfficeLow_DensityWhiteNorth, "OfficeLow_DensityWhiteNorth");
		GameRegistry.registerBlock(OfficeLow_DensityWhiteSouth, "OfficeLow_DensityWhiteSouth");
		GameRegistry.registerBlock(OfficeLow_DensityWhiteWest, "OfficeLow_DensityWhiteWest");
		GameRegistry.registerBlock(OfficeLow_DensityYellowEast, "OfficeLow_DensityYellowEast");
		GameRegistry.registerBlock(OfficeLow_DensityYellowNorth, "OfficeLow_DensityYellowNorth");
		GameRegistry.registerBlock(OfficeLow_DensityYellowSouth, "OfficeLow_DensityYellowSouth");
		GameRegistry.registerBlock(OfficeLow_DensityYellowWest, "OfficeLow_DensityYellowWest");
		GameRegistry.registerBlock(OfficeMedium_DensityCyanEast, "OfficeMedium_DensityCyanEast");
		GameRegistry.registerBlock(OfficeMedium_DensityCyanNorth, "OfficeMedium_DensityCyanNorth");
		GameRegistry.registerBlock(OfficeMedium_DensityCyanSouth, "OfficeMedium_DensityCyanSouth");
		GameRegistry.registerBlock(OfficeMedium_DensityCyanWest, "OfficeMedium_DensityCyanWest");
		GameRegistry.registerBlock(OfficeMedium_DensityLightBlueEast, "OfficeMedium_DensityLightBlueEast");
		GameRegistry.registerBlock(OfficeMedium_DensityLightBlueNorth, "OfficeMedium_DensityLightBlueNorth");
		GameRegistry.registerBlock(OfficeMedium_DensityLightBlueSouth, "OfficeMedium_DensityLightBlueSouth");
		GameRegistry.registerBlock(OfficeMedium_DensityLightBlueWest, "OfficeMedium_DensityLightBlueWest");
		GameRegistry.registerBlock(OfficeMedium_DensityPinkEast, "OfficeMedium_DensityPinkEast");
		GameRegistry.registerBlock(OfficeMedium_DensityPinkNorth, "OfficeMedium_DensityPinkNorth");
		GameRegistry.registerBlock(OfficeMedium_DensityPinkSouth, "OfficeMedium_DensityPinkSouth");
		GameRegistry.registerBlock(OfficeMedium_DensityPinkWest, "OfficeMedium_DensityPinkWest");
		GameRegistry.registerBlock(OfficeMedium_DensitySandstoneEast, "OfficeMedium_DensitySandstoneEast");
		GameRegistry.registerBlock(OfficeMedium_DensitySandstoneNorth, "OfficeMedium_DensitySandstoneNorth");
		GameRegistry.registerBlock(OfficeMedium_DensitySandstoneSouth, "OfficeMedium_DensitySandstoneSouth");
		GameRegistry.registerBlock(OfficeMedium_DensitySandstoneWest, "OfficeMedium_DensitySandstoneWest");
		GameRegistry.registerBlock(PublicFireServiceBigEast, "PublicFireServiceBigEast");
		GameRegistry.registerBlock(PublicFireServiceBigNorth, "PublicFireServiceBigNorth");
		GameRegistry.registerBlock(PublicFireServiceBigSouth, "PublicFireServiceBigSouth");
		GameRegistry.registerBlock(PublicFireServiceBigWest, "PublicFireServiceBigWest");
		GameRegistry.registerBlock(PublicFireServiceSmallEast, "PublicFireServiceSmallEast");
		GameRegistry.registerBlock(PublicFireServiceSmallNorth, "PublicFireServiceSmallNorth");
		GameRegistry.registerBlock(PublicFireServiceSmallSouth, "PublicFireServiceSmallSouth");
		GameRegistry.registerBlock(PublicFireServiceSmallWest, "PublicFireServiceSmallWest");
		GameRegistry.registerBlock(PublicHospitalBigEast, "PublicHospitalBigEast");
		GameRegistry.registerBlock(PublicHospitalBigNorth, "PublicHospitalBigNorth");
		GameRegistry.registerBlock(PublicHospitalBigSouth, "PublicHospitalBigSouth");
		GameRegistry.registerBlock(PublicHospitalBigWest, "PublicHospitalBigWest");
		GameRegistry.registerBlock(PublicHospitalSmallEast, "PublicHospitalSmallEast");
		GameRegistry.registerBlock(PublicHospitalSmallNorth, "PublicHospitalSmallNorth");
		GameRegistry.registerBlock(PublicHospitalSmallSouth, "PublicHospitalSmallSouth");
		GameRegistry.registerBlock(PublicHospitalSmallWest, "PublicHospitalSmallWest");
		GameRegistry.registerBlock(PublicLibraryEastWest, "PublicLibraryEastWest");
		GameRegistry.registerBlock(PublicLibraryNorthSouth, "PublicLibraryNorthSouth");
		GameRegistry.registerBlock(PublicPoliceBigEast, "PublicPoliceBigEast");
		GameRegistry.registerBlock(PublicPoliceBigNorth, "PublicPoliceBigNorth");
		GameRegistry.registerBlock(PublicPoliceBigSouth, "PublicPoliceBigSouth");
		GameRegistry.registerBlock(PublicPoliceBigWest, "PublicPoliceBigWest");
		GameRegistry.registerBlock(PublicPoliceSmallEast, "PublicPoliceSmallEast");
		GameRegistry.registerBlock(PublicPoliceSmallNorth, "PublicPoliceSmallNorth");
		GameRegistry.registerBlock(PublicPoliceSmallSouth, "PublicPoliceSmallSouth");
		GameRegistry.registerBlock(PublicPoliceSmallWest, "PublicPoliceSmallWest");
		GameRegistry.registerBlock(PublicSchoolBigNorthEast, "PublicSchoolBigNorthEast");
		GameRegistry.registerBlock(PublicSchoolBigNorthWest, "PublicSchoolBigNorthWest");
		GameRegistry.registerBlock(PublicSchoolBigSouthEast, "PublicSchoolBigSouthEast");
		GameRegistry.registerBlock(PublicSchoolBigSouthWest, "PublicSchoolBigSouthWest");
		GameRegistry.registerBlock(PublicSchoolSmallNorthEast, "PublicSchoolSmallNorthEast");
		GameRegistry.registerBlock(PublicSchoolSmallNorthWest, "PublicSchoolSmallNorthWest");
		GameRegistry.registerBlock(PublicSchoolSmallSouthEast, "PublicSchoolSmallSouthEast");
		GameRegistry.registerBlock(PublicSchoolSmallSouthWest, "PublicSchoolSmallSouthWest");
		GameRegistry.registerBlock(PublicTownhallBigEastWest, "PublicTownhallBigEastWest");
		GameRegistry.registerBlock(PublicTownhallBigNorthSouth, "PublicTownhallBigNorthSouth");
		GameRegistry.registerBlock(PublicTownhallSmallEast, "PublicTownhallSmallEast");
		GameRegistry.registerBlock(PublicTownhallSmallNorth, "PublicTownhallSmallNorth");
		GameRegistry.registerBlock(PublicTownhallSmallSouth, "PublicTownhallSmallSouth");
		GameRegistry.registerBlock(PublicTownhallSmallWest, "PublicTownhallSmallWest");
		GameRegistry.registerBlock(PublicUniversityEast, "PublicUniversityEast");
		GameRegistry.registerBlock(PublicUniversityNorth, "PublicUniversityNorth");
		GameRegistry.registerBlock(PublicUniversitySouth, "PublicUniversitySouth");
		GameRegistry.registerBlock(PublicUniversityWest, "PublicUniversityWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityBlockNorthEastSouthWest, "ResidentalEnormous_DensityBlockNorthEastSouthWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityBrickBigEast, "ResidentalEnormous_DensityBrickBigEast");
		GameRegistry.registerBlock(ResidentalEnormous_DensityBrickBigNorth, "ResidentalEnormous_DensityBrickBigNorth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityBrickBigSouth, "ResidentalEnormous_DensityBrickBigSouth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityBrickBigWest, "ResidentalEnormous_DensityBrickBigWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityBrickSmallNorthEastSouthWest, "ResidentalEnormous_DensityBrickSmallNorthEastSouthWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityGreyEast, "ResidentalEnormous_DensityGreyEast");
		GameRegistry.registerBlock(ResidentalEnormous_DensityGreyNorth, "ResidentalEnormous_DensityGreyNorth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityGreySouth, "ResidentalEnormous_DensityGreySouth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityGreyWest, "ResidentalEnormous_DensityGreyWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityModernEast, "ResidentalEnormous_DensityModernEast");
		GameRegistry.registerBlock(ResidentalEnormous_DensityModernNorth, "ResidentalEnormous_DensityModernNorth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityModernSouth, "ResidentalEnormous_DensityModernSouth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityModernWest, "ResidentalEnormous_DensityModernWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityRedEastWest, "ResidentalEnormous_DensityRedEastWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityRedNorthSouth, "ResidentalEnormous_DensityRedNorthSouth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityRoundNorthEastSouthWest, "ResidentalEnormous_DensityRoundNorthEastSouthWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneEast2, "ResidentalEnormous_DensityStoneEast2");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneEast, "ResidentalEnormous_DensityStoneEast");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneNorth2, "ResidentalEnormous_DensityStoneNorth2");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneNorth, "ResidentalEnormous_DensityStoneNorth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneSouth2, "ResidentalEnormous_DensityStoneSouth2");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneSouth, "ResidentalEnormous_DensityStoneSouth");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneWest2, "ResidentalEnormous_DensityStoneWest2");
		GameRegistry.registerBlock(ResidentalEnormous_DensityStoneWest, "ResidentalEnormous_DensityStoneWest");
		GameRegistry.registerBlock(ResidentalEnormous_DensityYellowNorthEastSouthWest, "ResidentalEnormous_DensityYellowNorthEastSouthWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityBlueEast, "ResidentalHigh_DensityBlueEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityBlueEastWest, "ResidentalHigh_DensityBlueEastWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityBlueNorth, "ResidentalHigh_DensityBlueNorth");
		GameRegistry.registerBlock(ResidentalHigh_DensityBlueNorthSouth, "ResidentalHigh_DensityBlueNorthSouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityBlueSouth, "ResidentalHigh_DensityBlueSouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityBlueWest, "ResidentalHigh_DensityBlueWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityBrickEast, "ResidentalHigh_DensityBrickEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityBrickEastWest, "ResidentalHigh_DensityBrickEastWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityBrickNorth, "ResidentalHigh_DensityBrickNorth");
		GameRegistry.registerBlock(ResidentalHigh_DensityBrickNorthSouth, "ResidentalHigh_DensityBrickNorthSouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityBrickSouth, "ResidentalHigh_DensityBrickSouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityBrickWest, "ResidentalHigh_DensityBrickWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityGreenGreyEast, "ResidentalHigh_DensityGreenGreyEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityGreenGreyNorth, "ResidentalHigh_DensityGreenGreyNorth");
		GameRegistry.registerBlock(ResidentalHigh_DensityGreenGreySouth, "ResidentalHigh_DensityGreenGreySouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityGreenGreyWest, "ResidentalHigh_DensityGreenGreyWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedCornerNorthEast, "ResidentalHigh_DensityRedCornerNorthEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedCornerNorthWest, "ResidentalHigh_DensityRedCornerNorthWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedCornerSouthEast, "ResidentalHigh_DensityRedCornerSouthEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedCornerSouthWest, "ResidentalHigh_DensityRedCornerSouthWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedYellowEast, "ResidentalHigh_DensityRedYellowEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedYellowNorth, "ResidentalHigh_DensityRedYellowNorth");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedYellowSouth, "ResidentalHigh_DensityRedYellowSouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityRedYellowWest, "ResidentalHigh_DensityRedYellowWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneEast2, "ResidentalHigh_DensityStoneEast2");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneEast, "ResidentalHigh_DensityStoneEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneNorth2, "ResidentalHigh_DensityStoneNorth2");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneNorth, "ResidentalHigh_DensityStoneNorth");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneSouth2, "ResidentalHigh_DensityStoneSouth2");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneSouth, "ResidentalHigh_DensityStoneSouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneWest2, "ResidentalHigh_DensityStoneWest2");
		GameRegistry.registerBlock(ResidentalHigh_DensityStoneWest, "ResidentalHigh_DensityStoneWest");
		GameRegistry.registerBlock(ResidentalHigh_DensityYellowEast, "ResidentalHigh_DensityYellowEast");
		GameRegistry.registerBlock(ResidentalHigh_DensityYellowNorth, "ResidentalHigh_DensityYellowNorth");
		GameRegistry.registerBlock(ResidentalHigh_DensityYellowSouth, "ResidentalHigh_DensityYellowSouth");
		GameRegistry.registerBlock(ResidentalHigh_DensityYellowWest, "ResidentalHigh_DensityYellowWest");
		GameRegistry.registerBlock(ResidentalLow_DensityBeigeEast, "ResidentalLow_DensityBeigeEast");
		GameRegistry.registerBlock(ResidentalLow_DensityBeigeNorth, "ResidentalLow_DensityBeigeNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityBeigeSouth, "ResidentalLow_DensityBeigeSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityBeigeWest, "ResidentalLow_DensityBeigeWest");
		GameRegistry.registerBlock(ResidentalLow_DensityCyanEast, "ResidentalLow_DensityCyanEast");
		GameRegistry.registerBlock(ResidentalLow_DensityCyanNorth, "ResidentalLow_DensityCyanNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityCyanSouth, "ResidentalLow_DensityCyanSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityCyanWest, "ResidentalLow_DensityCyanWest");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenEast2, "ResidentalLow_DensityGreenEast2");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenEast, "ResidentalLow_DensityGreenEast");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenNorth2, "ResidentalLow_DensityGreenNorth2");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenNorth, "ResidentalLow_DensityGreenNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenSouth2, "ResidentalLow_DensityGreenSouth2");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenSouth, "ResidentalLow_DensityGreenSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenWest2, "ResidentalLow_DensityGreenWest2");
		GameRegistry.registerBlock(ResidentalLow_DensityGreenWest, "ResidentalLow_DensityGreenWest");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueEast2, "ResidentalLow_DensityLightBlueEast2");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueEast, "ResidentalLow_DensityLightBlueEast");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueNorth2, "ResidentalLow_DensityLightBlueNorth2");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueNorth, "ResidentalLow_DensityLightBlueNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueSouth2, "ResidentalLow_DensityLightBlueSouth2");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueSouth, "ResidentalLow_DensityLightBlueSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueWest2, "ResidentalLow_DensityLightBlueWest2");
		GameRegistry.registerBlock(ResidentalLow_DensityLightBlueWest, "ResidentalLow_DensityLightBlueWest");
		GameRegistry.registerBlock(ResidentalLow_DensityLightGreyEast, "ResidentalLow_DensityLightGreyEast");
		GameRegistry.registerBlock(ResidentalLow_DensityLightGreyNorth, "ResidentalLow_DensityLightGreyNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityLightGreySouth, "ResidentalLow_DensityLightGreySouth");
		GameRegistry.registerBlock(ResidentalLow_DensityLightGreyWest, "ResidentalLow_DensityLightGreyWest");
		GameRegistry.registerBlock(ResidentalLow_DensityModernEast, "ResidentalLow_DensityModernEast");
		GameRegistry.registerBlock(ResidentalLow_DensityModernNorth, "ResidentalLow_DensityModernNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityModernSouth, "ResidentalLow_DensityModernSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityModernWest, "ResidentalLow_DensityModernWest");
		GameRegistry.registerBlock(ResidentalLow_DensityOrangeEast, "ResidentalLow_DensityOrangeEast");
		GameRegistry.registerBlock(ResidentalLow_DensityOrangeNorth, "ResidentalLow_DensityOrangeNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityOrangeSouth, "ResidentalLow_DensityOrangeSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityOrangeWest, "ResidentalLow_DensityOrangeWest");
		GameRegistry.registerBlock(ResidentalLow_DensityRedEast, "ResidentalLow_DensityRedEast");
		GameRegistry.registerBlock(ResidentalLow_DensityRedNorth, "ResidentalLow_DensityRedNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityRedSouth, "ResidentalLow_DensityRedSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityRedWest, "ResidentalLow_DensityRedWest");
		GameRegistry.registerBlock(ResidentalLow_DensityStoneEast, "ResidentalLow_DensityStoneEast");
		GameRegistry.registerBlock(ResidentalLow_DensityStoneNorth, "ResidentalLow_DensityStoneNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityStoneSouth, "ResidentalLow_DensityStoneSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityStoneWest, "ResidentalLow_DensityStoneWest");
		GameRegistry.registerBlock(ResidentalLow_DensityWhiteEast, "ResidentalLow_DensityWhiteEast");
		GameRegistry.registerBlock(ResidentalLow_DensityWhiteNorth, "ResidentalLow_DensityWhiteNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityWhiteSouth, "ResidentalLow_DensityWhiteSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityWhiteWest, "ResidentalLow_DensityWhiteWest");
		GameRegistry.registerBlock(ResidentalLow_DensityWoodEast, "ResidentalLow_DensityWoodEast");
		GameRegistry.registerBlock(ResidentalLow_DensityWoodNorth, "ResidentalLow_DensityWoodNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityWoodSouth, "ResidentalLow_DensityWoodSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityWoodWest, "ResidentalLow_DensityWoodWest");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowEast2, "ResidentalLow_DensityYellowEast2");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowEast, "ResidentalLow_DensityYellowEast");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowNorth2, "ResidentalLow_DensityYellowNorth2");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowNorth, "ResidentalLow_DensityYellowNorth");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowSouth2, "ResidentalLow_DensityYellowSouth2");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowSouth, "ResidentalLow_DensityYellowSouth");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowWest2, "ResidentalLow_DensityYellowWest2");
		GameRegistry.registerBlock(ResidentalLow_DensityYellowWest, "ResidentalLow_DensityYellowWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueGreenEast, "ResidentalMedium_DensityBlueGreenEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueGreenNorth, "ResidentalMedium_DensityBlueGreenNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueGreenSouth, "ResidentalMedium_DensityBlueGreenSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueGreenWest, "ResidentalMedium_DensityBlueGreenWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueRedEast, "ResidentalMedium_DensityBlueRedEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueRedNorth, "ResidentalMedium_DensityBlueRedNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueRedSouth, "ResidentalMedium_DensityBlueRedSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityBlueRedWest, "ResidentalMedium_DensityBlueRedWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityBrickEast, "ResidentalMedium_DensityBrickEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityBrickNorth, "ResidentalMedium_DensityBrickNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityBrickSouth, "ResidentalMedium_DensityBrickSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityBrickWest, "ResidentalMedium_DensityBrickWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityHorizontalEast, "ResidentalMedium_DensityHorizontalEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityHorizontalNorth, "ResidentalMedium_DensityHorizontalNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityHorizontalSouth, "ResidentalMedium_DensityHorizontalSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityHorizontalWest, "ResidentalMedium_DensityHorizontalWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityOrangeGreenEast, "ResidentalMedium_DensityOrangeGreenEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityOrangeGreenNorth, "ResidentalMedium_DensityOrangeGreenNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityOrangeGreenSouth, "ResidentalMedium_DensityOrangeGreenSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityOrangeGreenWest, "ResidentalMedium_DensityOrangeGreenWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityQuartzEast, "ResidentalMedium_DensityQuartzEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityQuartzNorth, "ResidentalMedium_DensityQuartzNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityQuartzSouth, "ResidentalMedium_DensityQuartzSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityQuartzWest, "ResidentalMedium_DensityQuartzWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityRedGreenEast, "ResidentalMedium_DensityRedGreenEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityRedGreenNorth, "ResidentalMedium_DensityRedGreenNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityRedGreenSouth, "ResidentalMedium_DensityRedGreenSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityRedGreenWest, "ResidentalMedium_DensityRedGreenWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityRoofEast, "ResidentalMedium_DensityRoofEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityRoofNorth, "ResidentalMedium_DensityRoofNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityRoofSouth, "ResidentalMedium_DensityRoofSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityRoofWest, "ResidentalMedium_DensityRoofWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStone1EastWest, "ResidentalMedium_DensityStone1EastWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStone1NorthSouth, "ResidentalMedium_DensityStone1NorthSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityStone2EastWest, "ResidentalMedium_DensityStone2EastWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStone2NorthSouth, "ResidentalMedium_DensityStone2NorthSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneCornerNorthEast, "ResidentalMedium_DensityStoneCornerNorthEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneCornerNorthWest, "ResidentalMedium_DensityStoneCornerNorthWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneCornerSouthEast, "ResidentalMedium_DensityStoneCornerSouthEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneCornerSouthWest, "ResidentalMedium_DensityStoneCornerSouthWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneEast, "ResidentalMedium_DensityStoneEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneEndNorthEastWest, "ResidentalMedium_DensityStoneEndNorthEastWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneEndNorthSouthEast, "ResidentalMedium_DensityStoneEndNorthSouthEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneEndNorthSouthWest, "ResidentalMedium_DensityStoneEndNorthSouthWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneEndSouthEastWest, "ResidentalMedium_DensityStoneEndSouthEastWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneNorth, "ResidentalMedium_DensityStoneNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneSouth, "ResidentalMedium_DensityStoneSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityStoneWest, "ResidentalMedium_DensityStoneWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityVerticalEast, "ResidentalMedium_DensityVerticalEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityVerticalNorth, "ResidentalMedium_DensityVerticalNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityVerticalSouth, "ResidentalMedium_DensityVerticalSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityVerticalWest, "ResidentalMedium_DensityVerticalWest");
		GameRegistry.registerBlock(ResidentalMedium_DensityYellowRedEast, "ResidentalMedium_DensityYellowRedEast");
		GameRegistry.registerBlock(ResidentalMedium_DensityYellowRedNorth, "ResidentalMedium_DensityYellowRedNorth");
		GameRegistry.registerBlock(ResidentalMedium_DensityYellowRedSouth, "ResidentalMedium_DensityYellowRedSouth");
		GameRegistry.registerBlock(ResidentalMedium_DensityYellowRedWest, "ResidentalMedium_DensityYellowRedWest");
		GameRegistry.registerBlock(ShoppingHigh_DensityQuartzEastWest, "ShoppingHigh_DensityQuartzEastWest");
		GameRegistry.registerBlock(ShoppingHigh_DensityQuartzNorthSouth, "ShoppingHigh_DensityQuartzNorthSouth");
		GameRegistry.registerBlock(ShoppingLow_DensityBrickEast, "ShoppingLow_DensityBrickEast");
		GameRegistry.registerBlock(ShoppingLow_DensityBrickNorth, "ShoppingLow_DensityBrickNorth");
		GameRegistry.registerBlock(ShoppingLow_DensityBrickSouth, "ShoppingLow_DensityBrickSouth");
		GameRegistry.registerBlock(ShoppingLow_DensityBrickWest, "ShoppingLow_DensityBrickWest");
		GameRegistry.registerBlock(ShoppingLow_DensityGreenEast, "ShoppingLow_DensityGreenEast");
		GameRegistry.registerBlock(ShoppingLow_DensityGreenNorth, "ShoppingLow_DensityGreenNorth");
		GameRegistry.registerBlock(ShoppingLow_DensityGreenSouth, "ShoppingLow_DensityGreenSouth");
		GameRegistry.registerBlock(ShoppingLow_DensityGreenWest, "ShoppingLow_DensityGreenWest");
		GameRegistry.registerBlock(ShoppingLow_DensityOrangeEast, "ShoppingLow_DensityOrangeEast");
		GameRegistry.registerBlock(ShoppingLow_DensityOrangeNorth, "ShoppingLow_DensityOrangeNorth");
		GameRegistry.registerBlock(ShoppingLow_DensityOrangeSouth, "ShoppingLow_DensityOrangeSouth");
		GameRegistry.registerBlock(ShoppingLow_DensityOrangeWest, "ShoppingLow_DensityOrangeWest");
		GameRegistry.registerBlock(ShoppingLow_DensityPinkEast, "ShoppingLow_DensityPinkEast");
		GameRegistry.registerBlock(ShoppingLow_DensityPinkNorth, "ShoppingLow_DensityPinkNorth");
		GameRegistry.registerBlock(ShoppingLow_DensityPinkSouth, "ShoppingLow_DensityPinkSouth");
		GameRegistry.registerBlock(ShoppingLow_DensityPinkWest, "ShoppingLow_DensityPinkWest");
		GameRegistry.registerBlock(ShoppingMedium_DensityModernEast, "ShoppingMedium_DensityModernEast");
		GameRegistry.registerBlock(ShoppingMedium_DensityModernNorth, "ShoppingMedium_DensityModernNorth");
		GameRegistry.registerBlock(ShoppingMedium_DensityModernSouth, "ShoppingMedium_DensityModernSouth");
		GameRegistry.registerBlock(ShoppingMedium_DensityModernWest, "ShoppingMedium_DensityModernWest");
		GameRegistry.registerBlock(ShoppingMedium_DensityQuartzEast, "ShoppingMedium_DensityQuartzEast");
		GameRegistry.registerBlock(ShoppingMedium_DensityQuartzNorth, "ShoppingMedium_DensityQuartzNorth");
		GameRegistry.registerBlock(ShoppingMedium_DensityQuartzSouth, "ShoppingMedium_DensityQuartzSouth");
		GameRegistry.registerBlock(ShoppingMedium_DensityQuartzWest, "ShoppingMedium_DensityQuartzWest");
		GameRegistry.registerBlock(TransportAirportRunway_EastWestBuilding_North, "TransportAirportRunway_EastWestBuilding_North");
		GameRegistry.registerBlock(TransportAirportRunway_EastWestBuilding_South, "TransportAirportRunway_EastWestBuilding_South");
		GameRegistry.registerBlock(TransportAirportRunway_NorthSouthBuilding_East, "TransportAirportRunway_NorthSouthBuilding_East");
		GameRegistry.registerBlock(TransportAirportRunway_NorthSouthBuilding_West, "TransportAirportRunway_NorthSouthBuilding_West");
		GameRegistry.registerBlock(TransportAvenue1EastWest, "TransportAvenue1EastWest");
		GameRegistry.registerBlock(TransportAvenue1NorthSouth, "TransportAvenue1NorthSouth");
		GameRegistry.registerBlock(TransportAvenue2EastWest, "TransportAvenue2EastWest");
		GameRegistry.registerBlock(TransportAvenue2NorthSouth, "TransportAvenue2NorthSouth");
		GameRegistry.registerBlock(TransportAvenueEEast, "TransportAvenueEEast");
		GameRegistry.registerBlock(TransportAvenueENorth, "TransportAvenueENorth");
		GameRegistry.registerBlock(TransportAvenueESouth, "TransportAvenueESouth");
		GameRegistry.registerBlock(TransportAvenueEWest, "TransportAvenueEWest");
		GameRegistry.registerBlock(TransportAvenueLNorthEast, "TransportAvenueLNorthEast");
		GameRegistry.registerBlock(TransportAvenueLNorthWest, "TransportAvenueLNorthWest");
		GameRegistry.registerBlock(TransportAvenueLSouthEast, "TransportAvenueLSouthEast");
		GameRegistry.registerBlock(TransportAvenueLSouthWest, "TransportAvenueLSouthWest");
		GameRegistry.registerBlock(TransportAvenueTNorthEastWest, "TransportAvenueTNorthEastWest");
		GameRegistry.registerBlock(TransportAvenueTNorthSouthEast, "TransportAvenueTNorthSouthEast");
		GameRegistry.registerBlock(TransportAvenueTNorthSouthWest, "TransportAvenueTNorthSouthWest");
		GameRegistry.registerBlock(TransportAvenueTSouthEastWest, "TransportAvenueTSouthEastWest");
		GameRegistry.registerBlock(TransportAvenueXNorthSouthEastWest, "TransportAvenueXNorthSouthEastWest");
		GameRegistry.registerBlock(TransportBridgeAvenue1EastWest, "TransportBridgeAvenue1EastWest");
		GameRegistry.registerBlock(TransportBridgeAvenue1NorthSouth, "TransportBridgeAvenue1NorthSouth");
		GameRegistry.registerBlock(TransportBridgeAvenue2NorthSouth, "TransportBridgeAvenue2NorthSouth");
		GameRegistry.registerBlock(TransportBridgeAvenue2SouthWest, "TransportBridgeAvenue2SouthWest");
		GameRegistry.registerBlock(TransportBridgeAvenue3EastWest, "TransportBridgeAvenue3EastWest");
		GameRegistry.registerBlock(TransportBridgeAvenue3NorthSouth, "TransportBridgeAvenue3NorthSouth");
		GameRegistry.registerBlock(TransportBridgeAvenue4EastWest, "TransportBridgeAvenue4EastWest");
		GameRegistry.registerBlock(TransportBridgeAvenue4NorthSouth, "TransportBridgeAvenue4NorthSouth");
		GameRegistry.registerBlock(TransportBridgeAvenueLNorthEast, "TransportBridgeAvenueLNorthEast");
		GameRegistry.registerBlock(TransportBridgeAvenueLNorthWest, "TransportBridgeAvenueLNorthWest");
		GameRegistry.registerBlock(TransportBridgeAvenueLSouthEast, "TransportBridgeAvenueLSouthEast");
		GameRegistry.registerBlock(TransportBridgeAvenueLSouthWest, "TransportBridgeAvenueLSouthWest");
		GameRegistry.registerBlock(TransportBridgeHighway1EastWest, "TransportBridgeHighway1EastWest");
		GameRegistry.registerBlock(TransportBridgeHighway1NorthSouth, "TransportBridgeHighway1NorthSouth");
		GameRegistry.registerBlock(TransportBridgeHighway2EastWest, "TransportBridgeHighway2EastWest");
		GameRegistry.registerBlock(TransportBridgeHighway2NorthSouth, "TransportBridgeHighway2NorthSouth");
		GameRegistry.registerBlock(TransportBridgeHighway3EastWest, "TransportBridgeHighway3EastWest");
		GameRegistry.registerBlock(TransportBridgeHighway3NorthSouth, "TransportBridgeHighway3NorthSouth");
		GameRegistry.registerBlock(TransportBridgeHighway4EastWest, "TransportBridgeHighway4EastWest");
		GameRegistry.registerBlock(TransportBridgeHighway4NorthSouth, "TransportBridgeHighway4NorthSouth");
		GameRegistry.registerBlock(TransportBridgeHighwayLNorthEast, "TransportBridgeHighwayLNorthEast");
		GameRegistry.registerBlock(TransportBridgeHighwayLNorthWest, "TransportBridgeHighwayLNorthWest");
		GameRegistry.registerBlock(TransportBridgeHighwayLSouthEast, "TransportBridgeHighwayLSouthEast");
		GameRegistry.registerBlock(TransportBridgeHighwayLSouthWest, "TransportBridgeHighwayLSouthWest");
		GameRegistry.registerBlock(TransportBridgeRoad1EastWest, "TransportBridgeRoad1EastWest");
		GameRegistry.registerBlock(TransportBridgeRoad1NorthSouth, "TransportBridgeRoad1NorthSouth");
		GameRegistry.registerBlock(TransportBridgeRoad2EastWest, "TransportBridgeRoad2EastWest");
		GameRegistry.registerBlock(TransportBridgeRoad2NorthSouth, "TransportBridgeRoad2NorthSouth");
		GameRegistry.registerBlock(TransportBridgeRoadLNorthEast, "TransportBridgeRoadLNorthEast");
		GameRegistry.registerBlock(TransportBridgeRoadLNorthWest, "TransportBridgeRoadLNorthWest");
		GameRegistry.registerBlock(TransportBridgeRoadLSouthEast, "TransportBridgeRoadLSouthEast");
		GameRegistry.registerBlock(TransportBridgeRoadLSouthWest, "TransportBridgeRoadLSouthWest");
		GameRegistry.registerBlock(TransportBridgeStreet1EastWest, "TransportBridgeStreet1EastWest");
		GameRegistry.registerBlock(TransportBridgeStreet1NorthSouth, "TransportBridgeStreet1NorthSouth");
		GameRegistry.registerBlock(TransportBridgeStreet2EastWest, "TransportBridgeStreet2EastWest");
		GameRegistry.registerBlock(TransportBridgeStreet2NorthSouth, "TransportBridgeStreet2NorthSouth");
		GameRegistry.registerBlock(TransportBridgeStreetLNorthEast, "TransportBridgeStreetLNorthEast");
		GameRegistry.registerBlock(TransportBridgeStreetLNorthWest, "TransportBridgeStreetLNorthWest");
		GameRegistry.registerBlock(TransportBridgeStreetLSouthEast, "TransportBridgeStreetLSouthEast");
		GameRegistry.registerBlock(TransportBridgeStreetLSouthWest, "TransportBridgeStreetLSouthWest");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside, "TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside, "TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside, "TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside, "TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside, "TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside, "TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside, "TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside");
		GameRegistry.registerBlock(TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside, "TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_EastStreet_North, "TransportConnectorAvenue_StreetLAvenue_EastStreet_North");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_EastStreet_South, "TransportConnectorAvenue_StreetLAvenue_EastStreet_South");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_NorthStreet_East, "TransportConnectorAvenue_StreetLAvenue_NorthStreet_East");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_NorthStreet_West, "TransportConnectorAvenue_StreetLAvenue_NorthStreet_West");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_SouthStreet_East, "TransportConnectorAvenue_StreetLAvenue_SouthStreet_East");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_SouthStreet_West, "TransportConnectorAvenue_StreetLAvenue_SouthStreet_West");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_WestStreet_North, "TransportConnectorAvenue_StreetLAvenue_WestStreet_North");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetLAvenue_WestStreet_South, "TransportConnectorAvenue_StreetLAvenue_WestStreet_South");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth, "TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North, "TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South, "TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East, "TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West, "TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest, "TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest, "TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth, "TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth, "TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth");
		GameRegistry.registerBlock(TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest, "TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest");
		GameRegistry.registerBlock(TransportConnectorBridge_AvenueBridge_EastAvenue_West, "TransportConnectorBridge_AvenueBridge_EastAvenue_West");
		GameRegistry.registerBlock(TransportConnectorBridge_AvenueBridge_NorthAvenue_South, "TransportConnectorBridge_AvenueBridge_NorthAvenue_South");
		GameRegistry.registerBlock(TransportConnectorBridge_AvenueBridge_SouthAvenue_North, "TransportConnectorBridge_AvenueBridge_SouthAvenue_North");
		GameRegistry.registerBlock(TransportConnectorBridge_AvenueBridge_WestAvenue_East, "TransportConnectorBridge_AvenueBridge_WestAvenue_East");
		GameRegistry.registerBlock(TransportConnectorBridge_RoadBridge_EastRoad_West, "TransportConnectorBridge_RoadBridge_EastRoad_West");
		GameRegistry.registerBlock(TransportConnectorBridge_RoadBridge_NorthRoad_South, "TransportConnectorBridge_RoadBridge_NorthRoad_South");
		GameRegistry.registerBlock(TransportConnectorBridge_RoadBridge_SouthRoad_North, "TransportConnectorBridge_RoadBridge_SouthRoad_North");
		GameRegistry.registerBlock(TransportConnectorBridge_RoadBridge_WestRoad_East, "TransportConnectorBridge_RoadBridge_WestRoad_East");
		GameRegistry.registerBlock(TransportConnectorBridge_StreetBridge_EastStreet_West, "TransportConnectorBridge_StreetBridge_EastStreet_West");
		GameRegistry.registerBlock(TransportConnectorBridge_StreetBridge_NorthStreet_South, "TransportConnectorBridge_StreetBridge_NorthStreet_South");
		GameRegistry.registerBlock(TransportConnectorBridge_StreetBridge_SouthStreet_North, "TransportConnectorBridge_StreetBridge_SouthStreet_North");
		GameRegistry.registerBlock(TransportConnectorBridge_StreetBridge_WestStreet_East, "TransportConnectorBridge_StreetBridge_WestStreet_East");
		GameRegistry.registerBlock(TransportConnectorHighway_AvenueHighway_EastAvenue_West, "TransportConnectorHighway_AvenueHighway_EastAvenue_West");
		GameRegistry.registerBlock(TransportConnectorHighway_AvenueHighway_NorthAvenue_South, "TransportConnectorHighway_AvenueHighway_NorthAvenue_South");
		GameRegistry.registerBlock(TransportConnectorHighway_AvenueHighway_SouthAvenue_North, "TransportConnectorHighway_AvenueHighway_SouthAvenue_North");
		GameRegistry.registerBlock(TransportConnectorHighway_AvenueHighway_WestAvenue_East, "TransportConnectorHighway_AvenueHighway_WestAvenue_East");
		GameRegistry.registerBlock(TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West, "TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West");
		GameRegistry.registerBlock(TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South, "TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South");
		GameRegistry.registerBlock(TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North, "TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North");
		GameRegistry.registerBlock(TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East, "TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East");
		GameRegistry.registerBlock(TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West, "TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West");
		GameRegistry.registerBlock(TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South, "TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South");
		GameRegistry.registerBlock(TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North, "TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North");
		GameRegistry.registerBlock(TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East, "TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East");
		GameRegistry.registerBlock(TransportHarbourBigEast, "TransportHarbourBigEast");
		GameRegistry.registerBlock(TransportHarbourBigNorth, "TransportHarbourBigNorth");
		GameRegistry.registerBlock(TransportHarbourBigSouth, "TransportHarbourBigSouth");
		GameRegistry.registerBlock(TransportHarbourBigWest, "TransportHarbourBigWest");
		GameRegistry.registerBlock(TransportHarbourSide1CornerNorthEast, "TransportHarbourSide1CornerNorthEast");
		GameRegistry.registerBlock(TransportHarbourSide1CornerNorthWest, "TransportHarbourSide1CornerNorthWest");
		GameRegistry.registerBlock(TransportHarbourSide1CornerSouthEast, "TransportHarbourSide1CornerSouthEast");
		GameRegistry.registerBlock(TransportHarbourSide1CornerSouthWest, "TransportHarbourSide1CornerSouthWest");
		GameRegistry.registerBlock(TransportHarbourSide2CornerCraneEast, "TransportHarbourSide2CornerCraneEast");
		GameRegistry.registerBlock(TransportHarbourSide2CornerCraneNorth, "TransportHarbourSide2CornerCraneNorth");
		GameRegistry.registerBlock(TransportHarbourSide2CornerCraneSouth, "TransportHarbourSide2CornerCraneSouth");
		GameRegistry.registerBlock(TransportHarbourSide2CornerCraneWest, "TransportHarbourSide2CornerCraneWest");
		GameRegistry.registerBlock(TransportHarbourSide2CornerEast, "TransportHarbourSide2CornerEast");
		GameRegistry.registerBlock(TransportHarbourSide2CornerNorth, "TransportHarbourSide2CornerNorth");
		GameRegistry.registerBlock(TransportHarbourSide2CornerSouth, "TransportHarbourSide2CornerSouth");
		GameRegistry.registerBlock(TransportHarbourSide2CornerWest, "TransportHarbourSide2CornerWest");
		GameRegistry.registerBlock(TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast, "TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast");
		GameRegistry.registerBlock(TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest, "TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest");
		GameRegistry.registerBlock(TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest, "TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest");
		GameRegistry.registerBlock(TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest, "TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest");
		GameRegistry.registerBlock(TransportHarbourSmallEast, "TransportHarbourSmallEast");
		GameRegistry.registerBlock(TransportHarbourSmallNorth, "TransportHarbourSmallNorth");
		GameRegistry.registerBlock(TransportHarbourSmallSouth, "TransportHarbourSmallSouth");
		GameRegistry.registerBlock(TransportHarbourSmallWest, "TransportHarbourSmallWest");
		GameRegistry.registerBlock(TransportHighway05EastWestNorthside, "TransportHighway05EastWestNorthside");
		GameRegistry.registerBlock(TransportHighway05EastWestSouthside, "TransportHighway05EastWestSouthside");
		GameRegistry.registerBlock(TransportHighway05NorthSouthEastside, "TransportHighway05NorthSouthEastside");
		GameRegistry.registerBlock(TransportHighway05NorthSouthWestside, "TransportHighway05NorthSouthWestside");
		GameRegistry.registerBlock(TransportHighway1EastWest, "TransportHighway1EastWest");
		GameRegistry.registerBlock(TransportHighway1NorthSouth, "TransportHighway1NorthSouth");
		GameRegistry.registerBlock(TransportHighway2EastWest, "TransportHighway2EastWest");
		GameRegistry.registerBlock(TransportHighway2NorthSouth, "TransportHighway2NorthSouth");
		GameRegistry.registerBlock(TransportHighwayDrivewayEastWestEastside, "TransportHighwayDrivewayEastWestEastside");
		GameRegistry.registerBlock(TransportHighwayDrivewayEastWestWestside, "TransportHighwayDrivewayEastWestWestside");
		GameRegistry.registerBlock(TransportHighwayDrivewayExitEastWestEastside, "TransportHighwayDrivewayExitEastWestEastside");
		GameRegistry.registerBlock(TransportHighwayDrivewayExitEastWestWestside, "TransportHighwayDrivewayExitEastWestWestside");
		GameRegistry.registerBlock(TransportHighwayDrivewayExitNorthSouthNorthside, "TransportHighwayDrivewayExitNorthSouthNorthside");
		GameRegistry.registerBlock(TransportHighwayDrivewayExitNorthSouthSouthside, "TransportHighwayDrivewayExitNorthSouthSouthside");
		GameRegistry.registerBlock(TransportHighwayDrivewayNorthSouthNorthside, "TransportHighwayDrivewayNorthSouthNorthside");
		GameRegistry.registerBlock(TransportHighwayDrivewayNorthSouthSouthside, "TransportHighwayDrivewayNorthSouthSouthside");
		GameRegistry.registerBlock(TransportHighwayExitEastWestEastside, "TransportHighwayExitEastWestEastside");
		GameRegistry.registerBlock(TransportHighwayExitEastWestWestside, "TransportHighwayExitEastWestWestside");
		GameRegistry.registerBlock(TransportHighwayExitNorthSouthNorthside, "TransportHighwayExitNorthSouthNorthside");
		GameRegistry.registerBlock(TransportHighwayExitNorthSouthSouthside, "TransportHighwayExitNorthSouthSouthside");
		GameRegistry.registerBlock(TransportHighwayFloor05EastWestNorthside, "TransportHighwayFloor05EastWestNorthside");
		GameRegistry.registerBlock(TransportHighwayFloor05EastWestSouthside, "TransportHighwayFloor05EastWestSouthside");
		GameRegistry.registerBlock(TransportHighwayFloor05NorthSouthEastside, "TransportHighwayFloor05NorthSouthEastside");
		GameRegistry.registerBlock(TransportHighwayFloor05NorthSouthWestside, "TransportHighwayFloor05NorthSouthWestside");
		GameRegistry.registerBlock(TransportHighwayFloor1EastWest, "TransportHighwayFloor1EastWest");
		GameRegistry.registerBlock(TransportHighwayFloor1NorthSouth, "TransportHighwayFloor1NorthSouth");
		GameRegistry.registerBlock(TransportHighwayFloor2EastWest, "TransportHighwayFloor2EastWest");
		GameRegistry.registerBlock(TransportHighwayFloor2NorthSouth, "TransportHighwayFloor2NorthSouth");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayEastWestEastside, "TransportHighwayFloorDrivewayEastWestEastside");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayEastWestWestside, "TransportHighwayFloorDrivewayEastWestWestside");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayExitEastWestEastside, "TransportHighwayFloorDrivewayExitEastWestEastside");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayExitEastWestWestside, "TransportHighwayFloorDrivewayExitEastWestWestside");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayExitNorthSouthNorthside, "TransportHighwayFloorDrivewayExitNorthSouthNorthside");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayExitNorthSouthSouthside, "TransportHighwayFloorDrivewayExitNorthSouthSouthside");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayNorthSouthNorthside, "TransportHighwayFloorDrivewayNorthSouthNorthside");
		GameRegistry.registerBlock(TransportHighwayFloorDrivewayNorthSouthSouthside, "TransportHighwayFloorDrivewayNorthSouthSouthside");
		GameRegistry.registerBlock(TransportHighwayFloorExitEastWestEast, "TransportHighwayFloorExitEastWestEast");
		GameRegistry.registerBlock(TransportHighwayFloorExitEastWestWestside, "TransportHighwayFloorExitEastWestWestside");
		GameRegistry.registerBlock(TransportHighwayFloorExitNorthSouthNorthside, "TransportHighwayFloorExitNorthSouthNorthside");
		GameRegistry.registerBlock(TransportHighwayFloorExitNorthSouthSouthside, "TransportHighwayFloorExitNorthSouthSouthside");
		GameRegistry.registerBlock(TransportHighwayFloorLNorthEast, "TransportHighwayFloorLNorthEast");
		GameRegistry.registerBlock(TransportHighwayFloorLNorthWest, "TransportHighwayFloorLNorthWest");
		GameRegistry.registerBlock(TransportHighwayFloorLSouthEast, "TransportHighwayFloorLSouthEast");
		GameRegistry.registerBlock(TransportHighwayFloorLSouthWest, "TransportHighwayFloorLSouthWest");
		GameRegistry.registerBlock(TransportHighwayFloorTNorthEastWest, "TransportHighwayFloorTNorthEastWest");
		GameRegistry.registerBlock(TransportHighwayFloorTNorthSouthEast, "TransportHighwayFloorTNorthSouthEast");
		GameRegistry.registerBlock(TransportHighwayFloorTNorthSouthWest, "TransportHighwayFloorTNorthSouthWest");
		GameRegistry.registerBlock(TransportHighwayFloorTSouthEastWest, "TransportHighwayFloorTSouthEastWest");
		GameRegistry.registerBlock(TransportHighwayFloorXNorthEastSouthWest, "TransportHighwayFloorXNorthEastSouthWest");
		GameRegistry.registerBlock(TransportHighwayLNorthEast, "TransportHighwayLNorthEast");
		GameRegistry.registerBlock(TransportHighwayLNorthWest, "TransportHighwayLNorthWest");
		GameRegistry.registerBlock(TransportHighwayLSouthEast, "TransportHighwayLSouthEast");
		GameRegistry.registerBlock(TransportHighwayLSouthWest, "TransportHighwayLSouthWest");
		GameRegistry.registerBlock(TransportHighwayTNorthEastWest, "TransportHighwayTNorthEastWest");
		GameRegistry.registerBlock(TransportHighwayTNorthSouthEast, "TransportHighwayTNorthSouthEast");
		GameRegistry.registerBlock(TransportHighwayTNorthSouthWest, "TransportHighwayTNorthSouthWest");
		GameRegistry.registerBlock(TransportHighwayTSouthEastWest, "TransportHighwayTSouthEastWest");
		GameRegistry.registerBlock(TransportHighwayXNorthEastSouthWest, "TransportHighwayXNorthEastSouthWest");
		GameRegistry.registerBlock(TransportPublicConnectorHightram_TramHightram_EastTram_West, "TransportPublicConnectorHightram_TramHightram_EastTram_West");
		GameRegistry.registerBlock(TransportPublicConnectorHightram_TramHightram_NorthTram_South, "TransportPublicConnectorHightram_TramHightram_NorthTram_South");
		GameRegistry.registerBlock(TransportPublicConnectorHightram_TramHightram_SouthTram_North, "TransportPublicConnectorHightram_TramHightram_SouthTram_North");
		GameRegistry.registerBlock(TransportPublicConnectorHightram_TramHightram_WestTram_East, "TransportPublicConnectorHightram_TramHightram_WestTram_East");
		GameRegistry.registerBlock(TransportPublicHightram1EastWest, "TransportPublicHightram1EastWest");
		GameRegistry.registerBlock(TransportPublicHightram1NorthSouth, "TransportPublicHightram1NorthSouth");
		GameRegistry.registerBlock(TransportPublicHightramEEast, "TransportPublicHightramEEast");
		GameRegistry.registerBlock(TransportPublicHightramENorth, "TransportPublicHightramENorth");
		GameRegistry.registerBlock(TransportPublicHightramESouth, "TransportPublicHightramESouth");
		GameRegistry.registerBlock(TransportPublicHightramEWest, "TransportPublicHightramEWest");
		GameRegistry.registerBlock(TransportPublicHightramLNorthEast, "TransportPublicHightramLNorthEast");
		GameRegistry.registerBlock(TransportPublicHightramLNorthWest, "TransportPublicHightramLNorthWest");
		GameRegistry.registerBlock(TransportPublicHightramLSouthEast, "TransportPublicHightramLSouthEast");
		GameRegistry.registerBlock(TransportPublicHightramLSouthWest, "TransportPublicHightramLSouthWest");
		GameRegistry.registerBlock(TransportPublicHightramStationEastWest, "TransportPublicHightramStationEastWest");
		GameRegistry.registerBlock(TransportPublicHightramStationNorthSouth, "TransportPublicHightramStationNorthSouth");
		GameRegistry.registerBlock(TransportPublicHightramXNorthEastSouthWest, "TransportPublicHightramXNorthEastSouthWest");
		GameRegistry.registerBlock(TransportPublicTram1EastWest, "TransportPublicTram1EastWest");
		GameRegistry.registerBlock(TransportPublicTram1NorthSouth, "TransportPublicTram1NorthSouth");
		GameRegistry.registerBlock(TransportPublicTramEEast, "TransportPublicTramEEast");
		GameRegistry.registerBlock(TransportPublicTramENorth, "TransportPublicTramENorth");
		GameRegistry.registerBlock(TransportPublicTramESouth, "TransportPublicTramESouth");
		GameRegistry.registerBlock(TransportPublicTramEWest, "TransportPublicTramEWest");
		GameRegistry.registerBlock(TransportPublicTramLNorthEast, "TransportPublicTramLNorthEast");
		GameRegistry.registerBlock(TransportPublicTramLNorthWest, "TransportPublicTramLNorthWest");
		GameRegistry.registerBlock(TransportPublicTramLSouthEast, "TransportPublicTramLSouthEast");
		GameRegistry.registerBlock(TransportPublicTramLSouthWest, "TransportPublicTramLSouthWest");
		GameRegistry.registerBlock(TransportPublicTram_on_road1EastWest, "TransportPublicTram_on_road1EastWest");
		GameRegistry.registerBlock(TransportPublicTram_on_road1NorthSouth, "TransportPublicTram_on_road1NorthSouth");
		GameRegistry.registerBlock(TransportPublicTram_on_roadEEast, "TransportPublicTram_on_roadEEast");
		GameRegistry.registerBlock(TransportPublicTram_on_roadENorth, "TransportPublicTram_on_roadENorth");
		GameRegistry.registerBlock(TransportPublicTram_on_roadESouth, "TransportPublicTram_on_roadESouth");
		GameRegistry.registerBlock(TransportPublicTram_on_roadEWest, "TransportPublicTram_on_roadEWest");
		GameRegistry.registerBlock(TransportPublicTram_on_roadLNorthEast, "TransportPublicTram_on_roadLNorthEast");
		GameRegistry.registerBlock(TransportPublicTram_on_roadLNorthWest, "TransportPublicTram_on_roadLNorthWest");
		GameRegistry.registerBlock(TransportPublicTram_on_roadLSouthEast, "TransportPublicTram_on_roadLSouthEast");
		GameRegistry.registerBlock(TransportPublicTram_on_roadLSouthWest, "TransportPublicTram_on_roadLSouthWest");
		GameRegistry.registerBlock(TransportPublicTramStationEastWest, "TransportPublicTramStationEastWest");
		GameRegistry.registerBlock(TransportPublicTramStationNorthSouth, "TransportPublicTramStationNorthSouth");
		GameRegistry.registerBlock(TransportPublicTramXNorthEastSouthWest, "TransportPublicTramXNorthEastSouthWest");
		GameRegistry.registerBlock(TransportRoad1EastWest, "TransportRoad1EastWest");
		GameRegistry.registerBlock(TransportRoad1NorthSouth, "TransportRoad1NorthSouth");
		GameRegistry.registerBlock(TransportRoadEEast, "TransportRoadEEast");
		GameRegistry.registerBlock(TransportRoadENorth, "TransportRoadENorth");
		GameRegistry.registerBlock(TransportRoadESouth, "TransportRoadESouth");
		GameRegistry.registerBlock(TransportRoadEWest, "TransportRoadEWest");
		GameRegistry.registerBlock(TransportRoadLNorthEast, "TransportRoadLNorthEast");
		GameRegistry.registerBlock(TransportRoadLNorthWest, "TransportRoadLNorthWest");
		GameRegistry.registerBlock(TransportRoadLSouthEast, "TransportRoadLSouthEast");
		GameRegistry.registerBlock(TransportRoadLSouthWest, "TransportRoadLSouthWest");
		GameRegistry.registerBlock(TransportRoadTNorthEastWest, "TransportRoadTNorthEastWest");
		GameRegistry.registerBlock(TransportRoadTNorthSouthEast, "TransportRoadTNorthSouthEast");
		GameRegistry.registerBlock(TransportRoadTNorthSouthWest, "TransportRoadTNorthSouthWest");
		GameRegistry.registerBlock(TransportRoadTSouthEastWest, "TransportRoadTSouthEastWest");
		GameRegistry.registerBlock(TransportRoadXNorthEastSouthWest, "TransportRoadXNorthEastSouthWest");
		GameRegistry.registerBlock(TransportStreet1EastWest, "TransportStreet1EastWest");
		GameRegistry.registerBlock(TransportStreet1NorthSouth, "TransportStreet1NorthSouth");
		GameRegistry.registerBlock(TransportStreetEEast, "TransportStreetEEast");
		GameRegistry.registerBlock(TransportStreetENorth, "TransportStreetENorth");
		GameRegistry.registerBlock(TransportStreetESouth, "TransportStreetESouth");
		GameRegistry.registerBlock(TransportStreetEWest, "TransportStreetEWest");
		GameRegistry.registerBlock(TransportStreetLNorthEast, "TransportStreetLNorthEast");
		GameRegistry.registerBlock(TransportStreetLNorthWest, "TransportStreetLNorthWest");
		GameRegistry.registerBlock(TransportStreetLSouthEast, "TransportStreetLSouthEast");
		GameRegistry.registerBlock(TransportStreetLSouthWest, "TransportStreetLSouthWest");
		GameRegistry.registerBlock(TransportStreetRoundaboutNorthEastSouthWest, "TransportStreetRoundaboutNorthEastSouthWest");
		GameRegistry.registerBlock(TransportStreetTNorthEastWest, "TransportStreetTNorthEastWest");
		GameRegistry.registerBlock(TransportStreetTNorthSouthEast, "TransportStreetTNorthSouthEast");
		GameRegistry.registerBlock(TransportStreetTNorthSouthWest, "TransportStreetTNorthSouthWest");
		GameRegistry.registerBlock(TransportStreetTSouthEastWest, "TransportStreetTSouthEastWest");
		GameRegistry.registerBlock(TransportStreetXNorthEastSouthWest, "TransportStreetXNorthEastSouthWest");
		GameRegistry.registerBlock(TransportWater1CornerNorthEast, "TransportWater1CornerNorthEast");
		GameRegistry.registerBlock(TransportWater1CornerNorthWest, "TransportWater1CornerNorthWest");
		GameRegistry.registerBlock(TransportWater1CornerSouthEast, "TransportWater1CornerSouthEast");
		GameRegistry.registerBlock(TransportWater1CornerSouthWest, "TransportWater1CornerSouthWest");
		GameRegistry.registerBlock(TransportWater2CornerEast, "TransportWater2CornerEast");
		GameRegistry.registerBlock(TransportWater2CornerNorth, "TransportWater2CornerNorth");
		GameRegistry.registerBlock(TransportWater2CornerSouth, "TransportWater2CornerSouth");
		GameRegistry.registerBlock(TransportWater2CornerWest, "TransportWater2CornerWest");
		GameRegistry.registerBlock(TransportWater3CornerNorthEast_NorthWest_SouthEast, "TransportWater3CornerNorthEast_NorthWest_SouthEast");
		GameRegistry.registerBlock(TransportWater3CornerNorthEast_NorthWest_SouthWest, "TransportWater3CornerNorthEast_NorthWest_SouthWest");
		GameRegistry.registerBlock(TransportWater3CornerSouthEast_SouthWest_NorthEast, "TransportWater3CornerSouthEast_SouthWest_NorthEast");
		GameRegistry.registerBlock(TransportWater3CornerSouthEast_SouthWest_NorthWest, "TransportWater3CornerSouthEast_SouthWest_NorthWest");
		GameRegistry.registerBlock(TransportWater4CornerNorthSouthEastWest, "TransportWater4CornerNorthSouthEastWest");
		GameRegistry.registerBlock(UtilityPower_NuclearEast, "UtilityPower_NuclearEast");
		GameRegistry.registerBlock(UtilityPower_NuclearNorth, "UtilityPower_NuclearNorth");
		GameRegistry.registerBlock(UtilityPower_NuclearSouth, "UtilityPower_NuclearSouth");
		GameRegistry.registerBlock(UtilityPower_NuclearWest, "UtilityPower_NuclearWest");
		GameRegistry.registerBlock(UtilityPower_OilCoalEast, "UtilityPower_OilCoalEast");
		GameRegistry.registerBlock(UtilityPower_OilCoalNorth, "UtilityPower_OilCoalNorth");
		GameRegistry.registerBlock(UtilityPower_OilCoalSouth, "UtilityPower_OilCoalSouth");
		GameRegistry.registerBlock(UtilityPower_OilCoalWest, "UtilityPower_OilCoalWest");
		GameRegistry.registerBlock(UtilityPower_SunNorthEastSouthWest, "UtilityPower_SunNorthEastSouthWest");
		GameRegistry.registerBlock(UtilityPower_WindEast, "UtilityPower_WindEast");
		GameRegistry.registerBlock(UtilityPower_WindNorth, "UtilityPower_WindNorth");
		GameRegistry.registerBlock(UtilityPower_WindSouth, "UtilityPower_WindSouth");
		GameRegistry.registerBlock(UtilityPower_WindWest, "UtilityPower_WindWest");
		GameRegistry.registerBlock(UtilityPumpjackEastWest, "UtilityPumpjackEastWest");
		GameRegistry.registerBlock(UtilityPumpjackNorthSouth, "UtilityPumpjackNorthSouth");
		GameRegistry.registerBlock(UtilityScrap_BurningEast, "UtilityScrap_BurningEast");
		GameRegistry.registerBlock(UtilityScrap_BurningNorth, "UtilityScrap_BurningNorth");
		GameRegistry.registerBlock(UtilityScrap_BurningSouth, "UtilityScrap_BurningSouth");
		GameRegistry.registerBlock(UtilityScrap_BurningWest, "UtilityScrap_BurningWest");
		GameRegistry.registerBlock(UtilityScrap_HeapEast, "UtilityScrap_HeapEast");
		GameRegistry.registerBlock(UtilityScrap_HeapNorth, "UtilityScrap_HeapNorth");
		GameRegistry.registerBlock(UtilityScrap_HeapSouth, "UtilityScrap_HeapSouth");
		GameRegistry.registerBlock(UtilityScrap_HeapWest, "UtilityScrap_HeapWest");
		GameRegistry.registerBlock(UtilityScrap_RecycleEast, "UtilityScrap_RecycleEast");
		GameRegistry.registerBlock(UtilityScrap_RecycleNorth, "UtilityScrap_RecycleNorth");
		GameRegistry.registerBlock(UtilityScrap_RecycleSouth, "UtilityScrap_RecycleSouth");
		GameRegistry.registerBlock(UtilityScrap_RecycleWest, "UtilityScrap_RecycleWest");
		GameRegistry.registerBlock(UtilityWater_PumpEast, "UtilityWater_PumpEast");
		GameRegistry.registerBlock(UtilityWater_PumpNorth, "UtilityWater_PumpNorth");
		GameRegistry.registerBlock(UtilityWater_PumpSouth, "UtilityWater_PumpSouth");
		GameRegistry.registerBlock(UtilityWater_PumpWest, "UtilityWater_PumpWest");
		GameRegistry.registerBlock(UtilityWater_TowerNorthEastSouthWest, "UtilityWater_TowerNorthEastSouthWest");
		GameRegistry.registerBlock(UtilityWater_TreatmentEast, "UtilityWater_TreatmentEast");
		GameRegistry.registerBlock(UtilityWater_TreatmentNorth, "UtilityWater_TreatmentNorth");
		GameRegistry.registerBlock(UtilityWater_TreatmentSouth, "UtilityWater_TreatmentSouth");
		GameRegistry.registerBlock(UtilityWater_TreatmentWest, "UtilityWater_TreatmentWest");
		GameRegistry.registerBlock(BlockAirBalloon, "BlockAirBalloon");
		GameRegistry.registerBlock(BlockAirplane, "BlockAirplane");
		//GameRegistry.registerBlock(BlockApplepie, "BlockApplepie");
		GameRegistry.registerBlock(BlockArena1, "BlockArena1");
		GameRegistry.registerBlock(BlockArena2, "BlockArena2");
		GameRegistry.registerBlock(BlockBigPyramid, "BlockBigPyramid");
		GameRegistry.registerBlock(BlockBoat, "BlockBoat");
		GameRegistry.registerBlock(BlockBunker, "BlockBunker");
		//GameRegistry.registerBlock(BlockCactus2, "BlockCactus2");
		//GameRegistry.registerBlock(BlockCake2, "BlockCake2");
		GameRegistry.registerBlock(BlockCastleTower, "BlockCastleTower");
		//GameRegistry.registerBlock(BlockCave, "BlockCave");
		//GameRegistry.registerBlock(BlockColumn, "BlockColumn");
		GameRegistry.registerBlock(BlockCosyHouse, "BlockCosyHouse");
		GameRegistry.registerBlock(BlockDungeon, "BlockDungeon");
		GameRegistry.registerBlock(BlockEnchantmentRoom, "BlockEnchantmentRoom");
		GameRegistry.registerBlock(BlockFarm2, "BlockFarm2");
		GameRegistry.registerBlock(BlockFarm3, "BlockFarm3");
		GameRegistry.registerBlock(BlockFarm4, "BlockFarm4");
		GameRegistry.registerBlock(BlockFarm, "BlockFarm");
		//GameRegistry.registerBlock(BlockFloatingSphere, "BlockFloatingSphere");
		GameRegistry.registerBlock(BlockGiantTree, "BlockGiantTree");
		//GameRegistry.registerBlock(BlockGlassHouse, "BlockGlassHouse");
		GameRegistry.registerBlock(BlockHountedHouse, "BlockHountedHouse");
		//GameRegistry.registerBlock(BlockHouse2, "BlockHouse2");
		GameRegistry.registerBlock(BlockHouse, "BlockHouse");
		GameRegistry.registerBlock(BlockHouseTrap1, "BlockHouseTrap1");
		GameRegistry.registerBlock(BlockHouseTrap2, "BlockHouseTrap2");
		//GameRegistry.registerBlock(BlockLeaves2, "BlockLeaves2");
		GameRegistry.registerBlock(BlockLighthouse, "BlockLighthouse");
		GameRegistry.registerBlock(BlockMegaHouse2, "BlockMegaHouse2");
		GameRegistry.registerBlock(BlockMegaHouse, "BlockMegaHouse");
		GameRegistry.registerBlock(BlockMegaTower, "BlockMegaTower");
		//GameRegistry.registerBlock(BlockPenIron, "BlockPenIron");
		//GameRegistry.registerBlock(BlockPenNether, "BlockPenNether");
		//GameRegistry.registerBlock(BlockPenWood, "BlockPenWood");
		GameRegistry.registerBlock(BlockPlane, "BlockPlane");
		GameRegistry.registerBlock(BlockPrison2, "BlockPrison2");
		GameRegistry.registerBlock(BlockPrison, "BlockPrison");
		GameRegistry.registerBlock(BlockPyramid, "BlockPyramid");
		GameRegistry.registerBlock(BlockRollerCoaster2, "BlockRollerCoaster2");
		GameRegistry.registerBlock(BlockRollercoaster, "BlockRollercoaster");
		//GameRegistry.registerBlock(BlockShelter, "BlockShelter");
		//GameRegistry.registerBlock(BlockSkyscraper2, "BlockSkyscraper2");
		GameRegistry.registerBlock(BlockSkyscraper, "BlockSkyscraper");
		//GameRegistry.registerBlock(BlockStadium2, "BlockStadium2");
		GameRegistry.registerBlock(BlockStadium, "BlockStadium");
		//GameRegistry.registerBlock(BlockStandardBrickHouse, "BlockStandardBrickHouse");
		GameRegistry.registerBlock(BlockStoreHouse, "BlockStoreHouse");
		//GameRegistry.registerBlock(BlockStreet, "BlockStreet");
		GameRegistry.registerBlock(BlockTorch2, "BlockTorch2");
		GameRegistry.registerBlock(BlockTower, "BlockTower");
		GameRegistry.registerBlock(BlockWaterSlide, "BlockWaterSlide");
		//GameRegistry.registerBlock(Remover16256, "Remover16256");
		//GameRegistry.registerBlock(Remover1632, "Remover1632");
		//GameRegistry.registerBlock(Remover168, "Remover168");
		GameRegistry.registerBlock(Remover16, "Remover16");
		//GameRegistry.registerBlock(Remover3216, "Remover3216");
		//GameRegistry.registerBlock(Remover32256, "Remover32256");
		//GameRegistry.registerBlock(Remover328, "Remover328");
		GameRegistry.registerBlock(Remover32, "Remover32");
		//GameRegistry.registerBlock(Remover64256, "Remover64256");
		GameRegistry.registerBlock(Remover64, "Remover64");
		//GameRegistry.registerBlock(Remover816, "Remover816");
		//GameRegistry.registerBlock(Remover8256, "Remover8256");
		//GameRegistry.registerBlock(Remover832, "Remover832");
		GameRegistry.registerBlock(Remover8, "Remover8");
		GameRegistry.registerBlock(RemoverLast, "RemoverLast");

		GameRegistry.registerBlock(OtherBrickHouse, "OtherBrickHouse");
		GameRegistry.registerBlock(OtherGrandHouse, "OtherGrandHouse");
		GameRegistry.registerBlock(OtherStable, "OtherStable");
		GameRegistry.registerBlock(OtherSurvivorHouse2, "OtherSurvivorHouse2");
		GameRegistry.registerBlock(OtherSurvivorHouse3, "OtherSurvivorHouse3");
		GameRegistry.registerBlock(OtherSurvivorHouse4, "OtherSurvivorHouse4");
		GameRegistry.registerBlock(OtherSurvivorHouse5, "OtherSurvivorHouse5");
		GameRegistry.registerBlock(OtherSurvivorHouse6, "OtherSurvivorHouse6");
		GameRegistry.registerBlock(OtherSurvivorHouse7, "OtherSurvivorHouse7");
		GameRegistry.registerBlock(OtherSurvivorHouse8, "OtherSurvivorHouse8");
		GameRegistry.registerBlock(OtherSurvivorHouse, "OtherSurvivorHouse");
		GameRegistry.registerBlock(OtherTemple, "OtherTemple");
		GameRegistry.registerBlock(SurvivalSmallBuilding, "SurvivalSmallBuilding");
		GameRegistry.registerBlock(SurvivalWoodenHouse, "SurvivalWoodenHouse");
		GameRegistry.registerBlock(WoodenHouse, "WoodenHouse");
		GameRegistry.registerBlock(BlockCheckerboard, "BlockCheckerboard");
		//GameRegistry.registerBlock(BlockAtlantis, "BlockAtlantis");
		GameRegistry.registerBlock(BlockBigWorld, "BlockBigWorld");

		GameRegistry.registerBlock(RandomAirballoon2, "RandomAirballoon2");
		GameRegistry.registerBlock(RandomEntrance, "RandomEntrance");
		GameRegistry.registerBlock(RandomFlyingShip, "RandomFlyingShip");
		GameRegistry.registerBlock(RandomGreenTent, "RandomGreenTent");
		GameRegistry.registerBlock(RandomGreyTent, "RandomGreyTent");
		GameRegistry.registerBlock(RandomLightHouse, "RandomLightHouse");
		GameRegistry.registerBlock(RandomMinerTent, "RandomMinerTent");
		GameRegistry.registerBlock(RandomNetherEntranceSurvival, "RandomNetherEntranceSurvival");
		GameRegistry.registerBlock(RandomRandomBrickHouse, "RandomRandomBrickHouse");
		GameRegistry.registerBlock(RandomSurvivalHouse1, "RandomSurvivalHouse1");
		GameRegistry.registerBlock(RandomSurvivalHouseSandstone, "RandomSurvivalHouseSandstone");
		GameRegistry.registerBlock(RandomTentCamp, "RandomTentCamp");
		GameRegistry.registerBlock(RandomWoodenHouse, "RandomWoodenHouse");
		GameRegistry.registerBlock(BlockCloud, "BlockCloud");
		
		GameRegistry.registerBlock(RandomBuildingComplex, "RandomBuildingComplex");
		GameRegistry.registerBlock(RandomImmense_Buildingcomplex, "RandomImmense_Buildingcomplex");
		GameRegistry.registerBlock(RandomImmense_greenroof, "RandomImmense_greenroof");
		GameRegistry.registerBlock(RandomImmense_White_House, "RandomImmense_White_House");
		GameRegistry.registerBlock(RandomImmense_WorkingBuilding, "RandomImmense_WorkingBuilding");
		GameRegistry.registerBlock(RandomLittlePalace, "RandomLittlePalace");
		GameRegistry.registerBlock(RandomLittleWoodenCabin, "RandomLittleWoodenCabin");
		GameRegistry.registerBlock(RandomSandstoneBuilding, "RandomSandstoneBuilding");
		GameRegistry.registerBlock(RandomSandStoneChurch, "RandomSandStoneChurch");
		GameRegistry.registerBlock(RandomSandstonewithFarm, "RandomSandstonewithFarm");
		GameRegistry.registerBlock(RandomSimpleSandstone, "RandomSimpleSandstone");
		GameRegistry.registerBlock(RandomSpawnHouseProd, "RandomSpawnHouseProd");
		GameRegistry.registerBlock(RandomWoodenStonebrickHouse, "RandomWoodenStonebrickHouse");
		GameRegistry.registerBlock(Live_Power_Windmill_East, "Live_Power_Windmill_East");
		
		GameRegistry.registerBlock(LiveAirBalloon, "LiveAirBalloon");
		GameRegistry.registerBlock(LiveAirplane, "LiveAirplane");
		GameRegistry.registerBlock(LiveBoat, "LiveBoat");
		GameRegistry.registerBlock(LiveFlyingShip2, "LiveFlyingShip2");
		GameRegistry.registerBlock(LiveFlyingShip, "LiveFlyingShip");
		GameRegistry.registerBlock(LivePlane, "LivePlane");
		GameRegistry.registerBlock(Live_Helicopter, "Live_Helicopter");
		GameRegistry.registerBlock(Live_Fair_FreeFall, "Live_Fair_FreeFall");
		GameRegistry.registerBlock(Live_Mill, "Live_Mill");
		GameRegistry.registerBlock(Live_Cinema, "Live_Cinema");
		GameRegistry.registerBlock(Live_Flying_Helicopter, "Live_Flying_Helicopter");
		GameRegistry.registerBlock(Live_Bus, "Live_Bus");
		GameRegistry.registerBlock(Live_Bus2, "Live_Bus2");
		GameRegistry.registerBlock(BlockFerrisWheel, "BlockFerrisWheel");
		
		GameRegistry.registerBlock(ChristmasHouse, "ChristmasHouse");
		GameRegistry.registerBlock(ChristmasHouse2, "ChristmasHouse2");
		GameRegistry.registerBlock(ChristmasHouse3, "ChristmasHouse3");
		GameRegistry.registerBlock(ChristmasSleigh, "ChristmasSleigh");
		GameRegistry.registerBlock(ChristmasSleigh2, "ChristmasSleigh2");
		GameRegistry.registerBlock(ChristmasSnowman, "ChristmasSnowman");
		GameRegistry.registerBlock(ChristmasTree, "ChristmasTree");
		GameRegistry.registerBlock(ChristmasMarket, "ChristmasMarket");
		GameRegistry.registerBlock(Live_WaterMill, "Live_WaterMill");
		GameRegistry.registerBlock(BlockUnlimited, "BlockUnlimited");

		for(Block block : userBlocks){
			GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
		}
	 
		GameRegistry.addRecipe(new ItemStack(BlockPyramid, 1), new Object[] { 
			      " % ", " * ", "*&*",    Character.valueOf('&'), Blocks.glowstone, Character.valueOf('*'), Blocks.sandstone, Character.valueOf('%'), Blocks.torch
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockTower, 1), new Object[] { 
			      "%&%", "%&%", "* *",    Character.valueOf('&'), Blocks.planks, Character.valueOf('*'), Blocks.log, Character.valueOf('%'), Blocks.cobblestone
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockEnchantmentRoom, 1), new Object[] { 
			      "***", "*&*", "***",    Character.valueOf('&'), Blocks.enchanting_table, Character.valueOf('*'), Blocks.bookshelf
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockLighthouse, 1), new Object[] { 
			      "%&%", " * ", " * ",    Character.valueOf('&'), Blocks.glowstone, Character.valueOf('*'), Blocks.stone, Character.valueOf('%'), Blocks.glass
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHouse, 1), new Object[] { 
			      " & ", "*%*", "*&*",    Character.valueOf('&'), Blocks.planks, Character.valueOf('*'), Blocks.cobblestone, Character.valueOf('%'), Blocks.glass
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockGiantTree, 1), new Object[] { 
			      " & ", "&&&", " * ",    Character.valueOf('&'), Blocks.leaves, Character.valueOf('*'), Blocks.log
			      });
				  
				 // GameRegistry.addRecipe(new ItemStack(TreeHouse, 1), new Object[] { 
			      //" & ", "&&&", " * ",    Character.valueOf('&'), IMSM.leaves2, Character.valueOf('*'), Blocks.log
			     // });
				  
				  /*GameRegistry.addRecipe(new ItemStack(BlockPenWood, 1), new Object[] { 
			      "***", "* *", "***",    Character.valueOf('*'), Blocks.oak_fence
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPenIron, 1), new Object[] { 
			      "***", "* *", "***",    Character.valueOf('*'), Blocks.iron_bars
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPenNether, 1), new Object[] { 
			      "***", "* *", "***",    Character.valueOf('*'), Blocks.nether_brick_fence
			      });*/
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.wheat_seeds, Character.valueOf('&'), Blocks.dirt
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockStoreHouse, 1), new Object[] { 
			      "%&%", "%&%", "***",    Character.valueOf('&'), Blocks.planks, Character.valueOf('*'), Blocks.cobblestone, Character.valueOf('%'), Blocks.log
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHouseTrap2, 1), new Object[] { 
			      "***", "*&*", "***",    Character.valueOf('&'), IMSM.BlockHouse, Character.valueOf('*'), Blocks.cactus, Character.valueOf('%'), Blocks.log
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHouseTrap1, 1), new Object[] { 
			      "***", "*&*", "***",    Character.valueOf('&'), IMSM.BlockHouse, Character.valueOf('*'), Items.lava_bucket, Character.valueOf('%'), Blocks.log
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockWaterSlide, 1), new Object[] { 
			      "&*&", "& &", "& &",    Character.valueOf('&'), Blocks.planks, Character.valueOf('*'), Items.water_bucket, Character.valueOf('%'), Blocks.log
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockBunker, 1), new Object[] { 
			      "&&&", "   ", "&&&",    Character.valueOf('&'), Blocks.cobblestone, Character.valueOf('*'), Items.water_bucket, Character.valueOf('%'), Blocks.log
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockArena1, 1), new Object[] { 
			      "&&&", "&%&", "&&&",    Character.valueOf('&'), Blocks.cobblestone, Character.valueOf('*'), Blocks.sand, Character.valueOf('%'), Blocks.sand
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockArena2, 1), new Object[] { 
			      "&&&", "&%&", "&&&",    Character.valueOf('&'), Blocks.cobblestone, Character.valueOf('*'), Items.lava_bucket, Character.valueOf('%'), Items.lava_bucket
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm2, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.reeds, Character.valueOf('&'), Blocks.dirt
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm3, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.melon_seeds, Character.valueOf('&'), Blocks.dirt
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm4, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.pumpkin_seeds, Character.valueOf('&'), Blocks.dirt
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPrison, 1), new Object[] { 
			      "&&&", "%%%", "&&&",    Character.valueOf('&'), Blocks.cobblestone, Character.valueOf('*'), Items.water_bucket, Character.valueOf('%'), Blocks.iron_bars
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPrison2, 1), new Object[] { 
			      "&&&", "%%%", "&&&",    Character.valueOf('&'), Blocks.obsidian, Character.valueOf('*'), Items.water_bucket, Character.valueOf('%'), Blocks.iron_bars
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollercoaster, 1), new Object[] { 
			      "%%%", "&*&", "%%%",    Character.valueOf('&'), Blocks.rail, Character.valueOf('*'), Blocks.golden_rail, Character.valueOf('%'), Blocks.log
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockSkyscraper, 1), new Object[] { 
			      "&&&", "&%&", "&&&",    Character.valueOf('&'), Blocks.glass, Character.valueOf('*'), Blocks.golden_rail, Character.valueOf('%'), Blocks.stone
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockTorch2, 1), new Object[] { 
			      "%%%", "%%%", "%%%",    Character.valueOf('&'), Blocks.glass, Character.valueOf('*'), Blocks.golden_rail, Character.valueOf('%'), Blocks.torch
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockAirplane, 1), new Object[] { 
			      "%*&", "**&", "***",    Character.valueOf('&'), Blocks.glass, Character.valueOf('*'), Blocks.stonebrick, Character.valueOf('%'), Blocks.glowstone
			      });
				  
				  /*GameRegistry.addRecipe(new ItemStack(BlockCave, 1), new Object[] { 
			      "###", "# #", "###",    Character.valueOf('#'), Blocks.stone, Character.valueOf('*'), Blocks.stonebrick, Character.valueOf('%'), Blocks.glowstone
			      });*/
				  
				  GameRegistry.addRecipe(new ItemStack(BlockBoat, 1), new Object[] { 
			      "121", "343", "333",    Character.valueOf('1'), Blocks.iron_bars, Character.valueOf('2'), Blocks.glass_pane, Character.valueOf('3'), Blocks.wool, Character.valueOf('4'), Blocks.log
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockAirBalloon, 1), new Object[] { 
			      "###", "###", " X ",    Character.valueOf('#'), Blocks.wool, Character.valueOf('X'), Blocks.planks, Character.valueOf('%'), Blocks.glowstone
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockAirBalloon, 1), new Object[] { 
			      "###", " # ", "###",    Character.valueOf('#'), Blocks.cobblestone, Character.valueOf('X'), Blocks.planks, Character.valueOf('%'), Blocks.glowstone
			      });
				  
				  /*GameRegistry.addRecipe(new ItemStack(BlockHouse2, 1), new Object[] { 
			      " % ", "%%%", "%%%",    Character.valueOf('%'), Blocks.brick_block, Character.valueOf('X'), Blocks.planks, Character.valueOf('C'), Blocks.glowstone
			      });*/
				  GameRegistry.addRecipe(new ItemStack(BlockBigPyramid, 1), new Object[] { 
			      " % ", "XYX", "%%%",    Character.valueOf('%'), Blocks.sandstone, Character.valueOf('X'), Items.gold_ingot, Character.valueOf('Y'), Items.diamond
			      });
				  GameRegistry.addRecipe(new ItemStack(BlockCastleTower, 1), new Object[] { 
			      "PPP", "CCC", "CCC",    Character.valueOf('P'), Blocks.planks, Character.valueOf('C'), Blocks.cobblestone, Character.valueOf('Y'), Items.diamond
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockDungeon, 1), new Object[] { 
				      "PPP", "PCP", "PPP",    Character.valueOf('P'), Blocks.cobblestone, Character.valueOf('C'), Blocks.chest, Character.valueOf('Y'), Items.diamond
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockMegaTower, 1), new Object[] { 
				      "PCP", "PCP", "PCP",    Character.valueOf('P'), Blocks.ice, Character.valueOf('C'), Blocks.quartz_block, Character.valueOf('Y'), Items.diamond
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHountedHouse, 1), new Object[] { 
				      "YPY", "PYP", "CCC",    Character.valueOf('P'), Blocks.vine, Character.valueOf('C'), Blocks.stonebrick, Character.valueOf('Y'), Blocks.cobblestone
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPlane, 1), new Object[] { 
				      "PYP", "PCP", "PCP",    Character.valueOf('P'), Blocks.stonebrick, Character.valueOf('C'), Items.redstone, Character.valueOf('Y'), Blocks.glass_pane
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
				      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.oak_fence, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.planks, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.spruce_fence, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.planks, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.birch_fence, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.planks, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.jungle_fence, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.planks, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.acacia_fence, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.planks, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.dark_oak_fence, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.planks, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockStadium, 1), new Object[] { 
				      "AAA", "BBB", "CCC",    Character.valueOf('A'), Blocks.wool, Character.valueOf('B'), Blocks.glowstone, Character.valueOf('C'), Blocks.stone, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
				      });
				  
				  /*GameRegistry.addRecipe(new ItemStack(BlockFloatingSphere, 1), new Object[] { 
				      " A ", "ACA", " A ",    Character.valueOf('A'), Blocks.glass, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.grass, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
				      });*/
				  
				  GameRegistry.addRecipe(new ItemStack(BlockMegaHouse, 1), new Object[] { 
					      " A ", "AAA", "AAA",    Character.valueOf('A'), Blocks.planks, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.grass, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
					      });
					  
					  GameRegistry.addRecipe(new ItemStack(BlockMegaHouse2, 1), new Object[] { 
					      " A ", "AAA", "AAA",    Character.valueOf('A'), Blocks.log, Character.valueOf('B'), Items.minecart, Character.valueOf('C'), Blocks.grass, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
					      });
						GameRegistry.addRecipe(new ItemStack(OtherGrandHouse, 1), new Object[] { 
					      "BAB", "BCB", "BAB",    Character.valueOf('A'), Blocks.stonebrick, Character.valueOf('B'), Blocks.quartz_block, Character.valueOf('C'), Blocks.yellow_flower, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						});
						 GameRegistry.addRecipe(new ItemStack(OtherSurvivorHouse6, 1), new Object[] { 
					      " A ", "ABA", "ABA",    Character.valueOf('A'), Blocks.log, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.grass, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						 });
							GameRegistry.addRecipe(new ItemStack(OtherSurvivorHouse7, 1), new Object[] { 
					      " B ", "ABA", "ABA",    Character.valueOf('A'), Blocks.log, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.grass, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
							});
						  GameRegistry.addRecipe(new ItemStack(SurvivalSmallBuilding, 1), new Object[] { 
					      "BAB", "BAB", "BAB",    Character.valueOf('A'), Blocks.stonebrick, Character.valueOf('B'), Blocks.sandstone, Character.valueOf('C'), Blocks.grass, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						  GameRegistry.addRecipe(new ItemStack(SurvivalWoodenHouse, 1), new Object[] { 
					      " B ", "BAB", "BBB",    Character.valueOf('A'), Blocks.glass, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.grass, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						  GameRegistry.addRecipe(new ItemStack(WoodenHouse, 1), new Object[] { 
					      "CBC", "ABA", "ABA",    Character.valueOf('A'), Blocks.log, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.oak_stairs, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomAirballoon2, 1), new Object[] { 
					      "ABA", " B ", "ABA",    Character.valueOf('A'), Blocks.wool, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.oak_stairs, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						   GameRegistry.addRecipe(new ItemStack(RandomFlyingShip, 1), new Object[] { 
					      "ABA", "AAA", "ABA",    Character.valueOf('A'), Blocks.wool, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.oak_stairs, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						   });
						  GameRegistry.addRecipe(new ItemStack(RandomGreenTent, 1), new Object[] { 
					      " A ", "AAA", "ABA",    Character.valueOf('A'), Blocks.wool, Character.valueOf('B'), Items.bed, Character.valueOf('C'), Blocks.oak_stairs, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomGreyTent, 1), new Object[] { 
					      " A ", "ABA", "AAA",    Character.valueOf('A'), Blocks.wool, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.oak_stairs, Character.valueOf('D'), Blocks.golden_rail, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomLightHouse, 1), new Object[] { 
					      "CDC", "AAA", "ABA",    Character.valueOf('A'), Blocks.wool, Character.valueOf('B'), Blocks.quartz_block, Character.valueOf('C'), Blocks.oak_fence, Character.valueOf('D'), Blocks.redstone_lamp, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomLittleWoodenCabin, 1), new Object[] { 
					      " A ", "ACA", "ABA",    Character.valueOf('A'), Blocks.log, Character.valueOf('B'), Blocks.enchanting_table, Character.valueOf('C'), Blocks.iron_block, Character.valueOf('D'), Blocks.redstone_lamp, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomWoodenStonebrickHouse, 1), new Object[] { 
					      "CAC", "ABA", "ABA",    Character.valueOf('A'), Blocks.stonebrick, Character.valueOf('B'), Blocks.planks, Character.valueOf('C'), Blocks.stone_brick_stairs, Character.valueOf('D'), Blocks.redstone_lamp, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
						  
					      });
					  GameRegistry.addRecipe(new ItemStack(BlockCosyHouse, 1), new Object[] { 
						      "ABA", "BCB", "BDB",    Character.valueOf('A'), Blocks.snow, Character.valueOf('B'), Blocks.brick_block, Character.valueOf('C'), Blocks.glass, Character.valueOf('D'), Blocks.oak_door, Character.valueOf('E'), Blocks.redstone_torch, Character.valueOf('F'), Blocks.stone_slab
							  
						      });
	
	}
	   
	   @EventHandler
	   public void serverLoad(FMLServerStartingEvent event)
	   {
	     event.registerServerCommand(new MazeCommand());
	     event.registerServerCommand(new LiveCommand());
	     event.registerServerCommand(new RideCommand());
	     event.registerServerCommand(new UndoCommand());
	   }
	  
}

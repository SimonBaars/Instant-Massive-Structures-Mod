package modid.imsm.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod (value = "imsm")
//@NetworkMod(clientSideRequired = true, serverSideRequired = false)

public class IMSM {
	
	//public static ItemGroup Structures = new Structures(ItemGroup.getNextID(),"Structures");
	public static String modid = "imsm";
	
	//public static int lastBlockPlacedX = 1;
	//public static int lastBlockPlacedY = 1;
	//public static int lastBlockPlacedZ = 1;
	
	//public static StructureCreator lastPlaced;
	
	//public static World[] worlds = new World[2];
	
	public static boolean updateChecked = false;
	
	//@SidedProxy(clientSide = "modid.imsm.core.IMSMClient",serverSide = "modid.imsm.core.IMSMProxy")
	 // public static IMSMProxy proxy;
	
	public static modid.imsm.core.EventHandler eventHandler;
	
	public static ItemGroup Structures = new ItemGroup("Structures"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.BlockMegaHouse);
		}		
	};
	
	public static ItemGroup Decoration = new ItemGroup("Decoration"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.DecorationParkSouth);
		}		
	};
	
	public static ItemGroup Food = new ItemGroup("Food"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.FoodFarmSouth);
		}		
	};
	
	public static ItemGroup IndustryHigh_Density = new ItemGroup("IndustryHigh_Density"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.IndustryHigh_DensityBlueEast);
		}		
	};
	
	public static ItemGroup IndustryMedium_Density = new ItemGroup("IndustryMedium_Density"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.IndustryMedium_DensityBrickWest);
		}		
	};
	
	public static ItemGroup IndustryLow_Density = new ItemGroup("IndustryLow_Density"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.IndustryLow_DensityGreenNorth);
		}		
	};
	
	public static ItemGroup Office = new ItemGroup("Office"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.OfficeHigh_DensityBrickEastWest);
		}		
	};
	
	public static ItemGroup Public = new ItemGroup("Public"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.PublicFireServiceBigEast);
		}		
	};
	
	public static ItemGroup ResidentalEnormous_Density = new ItemGroup("ResidentalEnormous_Density"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.ResidentalEnormous_DensityBlockNorthEastSouthWest);
		}		
	};
	
	public static ItemGroup ResidentalHigh_Density = new ItemGroup("ResidentalHigh_Density"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.ResidentalHigh_DensityBrickEastWest);
		}		
	};
	
	public static ItemGroup ResidentalMedium_Density = new ItemGroup("ResidentalMedium_Density"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.ResidentalMedium_DensityOrangeGreenEast);
		}		
	};
	
	public static ItemGroup ResidentalLow_Density = new ItemGroup("ResidentalLow_Density"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.ResidentalLow_DensityGreenEast2);
		}		
	};
	
	public static ItemGroup Shopping = new ItemGroup("Shopping"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.ShoppingMedium_DensityQuartzEast);
		}		
	};
	
	/*public static ItemGroup TransportAirport = new ItemGroup("TransportAirport"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.TransportAirportRunway_EastWestBuilding_South);
		}		
	};*/
	
	public static ItemGroup TransportHarbour = new ItemGroup("TransportHarbour"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.TransportHarbourSide2CornerWest);
		}		
	};
	
	public static ItemGroup TransportPublic = new ItemGroup("TransportPublic"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.TransportPublicHightramLSouthWest);
		}		
	};
	
	public static ItemGroup TransportRoads = new ItemGroup("TransportRoads"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.TransportRoadTNorthSouthWest);
		}		
	};
	
	public static ItemGroup TransportWater = new ItemGroup("TransportWater"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.TransportWater2CornerWest);
		}		
	};
	
	public static ItemGroup Utility = new ItemGroup("Utility"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.UtilityPower_NuclearEast);
		}		
	};
	
	public static ItemGroup Remover = new ItemGroup("Remover"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.RemoverLast);
		}		
	};
	
	public static ItemGroup Other = new ItemGroup("Other"){
		@Override public ItemStack createIcon() {
			return new ItemStack(IMSM.BlockStadium);
		}		
	};
	
	public static ItemGroup LiveStructures = new ItemGroup("LiveStructures"){
		@Override
		public ItemStack createIcon() {
			return new ItemStack(IMSM.Live_Power_Windmill_East);
		}		
	};
	
	public static ItemGroup User = new ItemGroup("User"){
		@Override
		public ItemStack createIcon() {
			return new ItemStack(IMSM.BlockUnlimited);
		}		
	};
	
	public static PMCParser pmcParser;
	
	//static EnumToolMaterial EnumToolMaterialRed= EnumHelper.addToolMaterial("RED", 1, 100, 2.1F, 1, 17);
	
	//public static final Item BlueBar = new Item(1000).setMaxStackSize(64).setCreativeTab(IMSM.Materials);
	//LanguageRegistry.addName(IMSM.BlueBar, "Blue B
	public static Block LiveStructureRemover = new LiveStructureRemover().setCreativeTab(IMSM.Remover);

	public static Block DecorationGrassNorthEastSouthWest = new DecorationGrassNorthEastSouthWest(200).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkEast = new DecorationParkEast(201).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageEast = new DecorationParkingGarageEast(202).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageNorth = new DecorationParkingGarageNorth(203).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageSouth = new DecorationParkingGarageSouth(204).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingGarageWest = new DecorationParkingGarageWest(205).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsEast = new DecorationParkingLotsEast(206).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsNorth = new DecorationParkingLotsNorth(207).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsSouth = new DecorationParkingLotsSouth(208).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkingLotsWest = new DecorationParkingLotsWest(209).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkNorth = new DecorationParkNorth(210).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkSouth = new DecorationParkSouth(211).setCreativeTab(IMSM.Decoration);
	public static Block DecorationParkWest = new DecorationParkWest(212).setCreativeTab(IMSM.Decoration);
	public static Block DecorationPlazaFountainNorthEastSouthWest = new DecorationPlazaFountainNorthEastSouthWest(213).setCreativeTab(IMSM.Decoration);
	public static Block DecorationPlazaNorthEastSouthWest = new DecorationPlazaNorthEastSouthWest(214).setCreativeTab(IMSM.Decoration);
	public static Block DecorationSoccerStadiumEastWest = new DecorationSoccerStadiumEastWest(215).setCreativeTab(IMSM.Decoration);
	public static Block DecorationSoccerStadiumNorthSouth = new DecorationSoccerStadiumNorthSouth(216).setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareNorthEastSouthWest = new DecorationSquareNorthEastSouthWest(217).setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeEast = new DecorationSquareTreeEast(218).setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeNorth = new DecorationSquareTreeNorth(219).setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeSouth = new DecorationSquareTreeSouth(220).setCreativeTab(IMSM.Decoration);
	public static Block DecorationSquareTreeWest = new DecorationSquareTreeWest(221).setCreativeTab(IMSM.Decoration);
	public static Block FoodCarrotsEastWest = new FoodCarrotsEastWest(222).setCreativeTab(IMSM.Food);
	public static Block FoodCarrotsNorthSouth = new FoodCarrotsNorthSouth(223).setCreativeTab(IMSM.Food);
	public static Block FoodFarmEast = new FoodFarmEast(224).setCreativeTab(IMSM.Food);
	public static Block FoodFarmNorth = new FoodFarmNorth(225).setCreativeTab(IMSM.Food);
	public static Block FoodFarmSouth = new FoodFarmSouth(226).setCreativeTab(IMSM.Food);
	public static Block FoodFarmWest = new FoodFarmWest(227).setCreativeTab(IMSM.Food);
	public static Block FoodPotatoesNorthEastSouthWest = new FoodPotatoesNorthEastSouthWest(228).setCreativeTab(IMSM.Food);
	public static Block FoodStableEastWest = new FoodStableEastWest(229).setCreativeTab(IMSM.Food);
	public static Block FoodStableNorthSouth = new FoodStableNorthSouth(230).setCreativeTab(IMSM.Food);
	public static Block FoodWheatNorthEastSouthWest = new FoodWheatNorthEastSouthWest(231).setCreativeTab(IMSM.Food);
	public static Block IndustryHigh_DensityBlueEast = new IndustryHigh_DensityBlueEast(232).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBlueNorth = new IndustryHigh_DensityBlueNorth(233).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBlueSouth = new IndustryHigh_DensityBlueSouth(234).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBlueWest = new IndustryHigh_DensityBlueWest(235).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickEast = new IndustryHigh_DensityBrickEast(236).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickNorth = new IndustryHigh_DensityBrickNorth(237).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickSouth = new IndustryHigh_DensityBrickSouth(238).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityBrickWest = new IndustryHigh_DensityBrickWest(239).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneyEast = new IndustryHigh_DensityChimneyEast(240).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneyNorth = new IndustryHigh_DensityChimneyNorth(241).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneySouth = new IndustryHigh_DensityChimneySouth(242).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityChimneyWest = new IndustryHigh_DensityChimneyWest(243).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipEast = new IndustryHigh_DensityComputerChipEast(244).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipNorth = new IndustryHigh_DensityComputerChipNorth(245).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipSouth = new IndustryHigh_DensityComputerChipSouth(246).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityComputerChipWest = new IndustryHigh_DensityComputerChipWest(247).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenEast = new IndustryHigh_DensityGreenEast(248).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenNorth = new IndustryHigh_DensityGreenNorth(249).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenSouth = new IndustryHigh_DensityGreenSouth(250).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityGreenWest = new IndustryHigh_DensityGreenWest(251).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueEast = new IndustryHigh_DensityLightBlueEast(252).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueNorth = new IndustryHigh_DensityLightBlueNorth(253).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueSouth = new IndustryHigh_DensityLightBlueSouth(254).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryHigh_DensityLightBlueWest = new IndustryHigh_DensityLightBlueWest(255).setCreativeTab(IMSM.IndustryHigh_Density);
	public static Block IndustryLow_Density3DPrintingEast = new IndustryLow_Density3DPrintingEast(256).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_Density3DPrintingNorth = new IndustryLow_Density3DPrintingNorth(257).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_Density3DPrintingSouth = new IndustryLow_Density3DPrintingSouth(258).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_Density3DPrintingWest = new IndustryLow_Density3DPrintingWest(259).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueEast = new IndustryLow_DensityBlueEast(260).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueNorth = new IndustryLow_DensityBlueNorth(261).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueSouth = new IndustryLow_DensityBlueSouth(262).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBlueWest = new IndustryLow_DensityBlueWest(263).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickEast = new IndustryLow_DensityBrickEast(264).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickEastWest = new IndustryLow_DensityBrickEastWest(265).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickNorth = new IndustryLow_DensityBrickNorth(266).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickNorthSouth = new IndustryLow_DensityBrickNorthSouth(267).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickSouth = new IndustryLow_DensityBrickSouth(268).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrickWest = new IndustryLow_DensityBrickWest(269).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownEast2 = new IndustryLow_DensityBrownEast2(270).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownEast = new IndustryLow_DensityBrownEast(271).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownNorth2 = new IndustryLow_DensityBrownNorth2(272).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownNorth = new IndustryLow_DensityBrownNorth(273).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownSouth2 = new IndustryLow_DensityBrownSouth2(274).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownSouth = new IndustryLow_DensityBrownSouth(275).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownWest2 = new IndustryLow_DensityBrownWest2(276).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityBrownWest = new IndustryLow_DensityBrownWest(277).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneyEast = new IndustryLow_DensityChimneyEast(278).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneyNorth = new IndustryLow_DensityChimneyNorth(279).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneySouth = new IndustryLow_DensityChimneySouth(280).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityChimneyWest = new IndustryLow_DensityChimneyWest(281).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenEast = new IndustryLow_DensityGreenEast(282).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenNorth = new IndustryLow_DensityGreenNorth(283).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenSouth = new IndustryLow_DensityGreenSouth(284).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityGreenWest = new IndustryLow_DensityGreenWest(285).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronEast = new IndustryLow_DensityIronEast(286).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronNorth = new IndustryLow_DensityIronNorth(287).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronSouth = new IndustryLow_DensityIronSouth(288).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityIronWest = new IndustryLow_DensityIronWest(289).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaEast = new IndustryLow_DensityParabolicAntennaEast(290).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaNorth = new IndustryLow_DensityParabolicAntennaNorth(291).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaSouth = new IndustryLow_DensityParabolicAntennaSouth(292).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityParabolicAntennaWest = new IndustryLow_DensityParabolicAntennaWest(293).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTankNorthEastSouthWest = new IndustryLow_DensityTankNorthEastSouthWest(294).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeEast = new IndustryLow_DensityTelescopeEast(295).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeNorth = new IndustryLow_DensityTelescopeNorth(296).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeSouth = new IndustryLow_DensityTelescopeSouth(297).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryLow_DensityTelescopeWest = new IndustryLow_DensityTelescopeWest(298).setCreativeTab(IMSM.IndustryLow_Density);
	public static Block IndustryMedium_DensityBlueEast = new IndustryMedium_DensityBlueEast(299).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBlueNorth = new IndustryMedium_DensityBlueNorth(300).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBlueSouth = new IndustryMedium_DensityBlueSouth(301).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBlueWest = new IndustryMedium_DensityBlueWest(302).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickEast = new IndustryMedium_DensityBrickEast(303).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickNorth = new IndustryMedium_DensityBrickNorth(304).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickSouth = new IndustryMedium_DensityBrickSouth(305).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrickWest = new IndustryMedium_DensityBrickWest(306).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownEast = new IndustryMedium_DensityBrownEast(307).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownNorth = new IndustryMedium_DensityBrownNorth(308).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownSouth = new IndustryMedium_DensityBrownSouth(309).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityBrownWest = new IndustryMedium_DensityBrownWest(310).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChemicalPressEastWest = new IndustryMedium_DensityChemicalPressEastWest(311).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChemicalPressNorthSouth = new IndustryMedium_DensityChemicalPressNorthSouth(312).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneyEast = new IndustryMedium_DensityChimneyEast(313).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneyNorth = new IndustryMedium_DensityChimneyNorth(314).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneySouth = new IndustryMedium_DensityChimneySouth(315).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityChimneyWest = new IndustryMedium_DensityChimneyWest(316).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenEast = new IndustryMedium_DensityGreenEast(317).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenNorth = new IndustryMedium_DensityGreenNorth(318).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenSouth = new IndustryMedium_DensityGreenSouth(319).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityGreenWest = new IndustryMedium_DensityGreenWest(320).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceEast = new IndustryMedium_DensityIceEast(321).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceNorth = new IndustryMedium_DensityIceNorth(322).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceSouth = new IndustryMedium_DensityIceSouth(323).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityIceWest = new IndustryMedium_DensityIceWest(324).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneEast = new IndustryMedium_DensitySandstoneEast(325).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneNorth = new IndustryMedium_DensitySandstoneNorth(326).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneSouth = new IndustryMedium_DensitySandstoneSouth(327).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensitySandstoneWest = new IndustryMedium_DensitySandstoneWest(328).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankEast = new IndustryMedium_DensityTankEast(329).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankNorth = new IndustryMedium_DensityTankNorth(330).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankSouth = new IndustryMedium_DensityTankSouth(331).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block IndustryMedium_DensityTankWest = new IndustryMedium_DensityTankWest(332).setCreativeTab(IMSM.IndustryMedium_Density);
	public static Block OfficeHigh_DensityBrickEastWest = new OfficeHigh_DensityBrickEastWest(333).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityBrickNorthSouth = new OfficeHigh_DensityBrickNorthSouth(334).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanEast = new OfficeHigh_DensityCyanEast(335).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanNorth = new OfficeHigh_DensityCyanNorth(336).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanSouth = new OfficeHigh_DensityCyanSouth(337).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityCyanWest = new OfficeHigh_DensityCyanWest(338).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopEast = new OfficeHigh_DensityHoleOnTopEast(339).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopNorth = new OfficeHigh_DensityHoleOnTopNorth(340).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopSouth = new OfficeHigh_DensityHoleOnTopSouth(341).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityHoleOnTopWest = new OfficeHigh_DensityHoleOnTopWest(342).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityLightBlueEastWest = new OfficeHigh_DensityLightBlueEastWest(343).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensityLightBlueNorthSouth = new OfficeHigh_DensityLightBlueNorthSouth(344).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingEast = new OfficeHigh_DensitySpirolBuildingEast(345).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingNorth = new OfficeHigh_DensitySpirolBuildingNorth(346).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingSouth = new OfficeHigh_DensitySpirolBuildingSouth(347).setCreativeTab(IMSM.Office);
	public static Block OfficeHigh_DensitySpirolBuildingWest = new OfficeHigh_DensitySpirolBuildingWest(348).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueEast = new OfficeLow_DensityBlueEast(349).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueNorth = new OfficeLow_DensityBlueNorth(350).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueSouth = new OfficeLow_DensityBlueSouth(351).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityBlueWest = new OfficeLow_DensityBlueWest(352).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenEast = new OfficeLow_DensityGreenEast(353).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenNorth = new OfficeLow_DensityGreenNorth(354).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenSouth = new OfficeLow_DensityGreenSouth(355).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityGreenWest = new OfficeLow_DensityGreenWest(356).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteEast = new OfficeLow_DensityWhiteEast(357).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteNorth = new OfficeLow_DensityWhiteNorth(358).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteSouth = new OfficeLow_DensityWhiteSouth(359).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityWhiteWest = new OfficeLow_DensityWhiteWest(360).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowEast = new OfficeLow_DensityYellowEast(361).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowNorth = new OfficeLow_DensityYellowNorth(362).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowSouth = new OfficeLow_DensityYellowSouth(363).setCreativeTab(IMSM.Office);
	public static Block OfficeLow_DensityYellowWest = new OfficeLow_DensityYellowWest(364).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanEast = new OfficeMedium_DensityCyanEast(365).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanNorth = new OfficeMedium_DensityCyanNorth(366).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanSouth = new OfficeMedium_DensityCyanSouth(367).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityCyanWest = new OfficeMedium_DensityCyanWest(368).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueEast = new OfficeMedium_DensityLightBlueEast(369).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueNorth = new OfficeMedium_DensityLightBlueNorth(370).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueSouth = new OfficeMedium_DensityLightBlueSouth(371).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityLightBlueWest = new OfficeMedium_DensityLightBlueWest(372).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkEast = new OfficeMedium_DensityPinkEast(373).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkNorth = new OfficeMedium_DensityPinkNorth(374).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkSouth = new OfficeMedium_DensityPinkSouth(375).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensityPinkWest = new OfficeMedium_DensityPinkWest(376).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneEast = new OfficeMedium_DensitySandstoneEast(377).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneNorth = new OfficeMedium_DensitySandstoneNorth(378).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneSouth = new OfficeMedium_DensitySandstoneSouth(379).setCreativeTab(IMSM.Office);
	public static Block OfficeMedium_DensitySandstoneWest = new OfficeMedium_DensitySandstoneWest(380).setCreativeTab(IMSM.Office);
	public static Block PublicFireServiceBigEast = new PublicFireServiceBigEast(381).setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceBigNorth = new PublicFireServiceBigNorth(382).setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceBigSouth = new PublicFireServiceBigSouth(383).setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceBigWest = new PublicFireServiceBigWest(384).setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallEast = new PublicFireServiceSmallEast(385).setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallNorth = new PublicFireServiceSmallNorth(386).setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallSouth = new PublicFireServiceSmallSouth(387).setCreativeTab(IMSM.Public);
	public static Block PublicFireServiceSmallWest = new PublicFireServiceSmallWest(388).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigEast = new PublicHospitalBigEast(389).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigNorth = new PublicHospitalBigNorth(390).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigSouth = new PublicHospitalBigSouth(391).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalBigWest = new PublicHospitalBigWest(392).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallEast = new PublicHospitalSmallEast(393).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallNorth = new PublicHospitalSmallNorth(394).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallSouth = new PublicHospitalSmallSouth(395).setCreativeTab(IMSM.Public);
	public static Block PublicHospitalSmallWest = new PublicHospitalSmallWest(396).setCreativeTab(IMSM.Public);
	public static Block PublicLibraryEastWest = new PublicLibraryEastWest(397).setCreativeTab(IMSM.Public);
	public static Block PublicLibraryNorthSouth = new PublicLibraryNorthSouth(398).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigEast = new PublicPoliceBigEast(399).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigNorth = new PublicPoliceBigNorth(400).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigSouth = new PublicPoliceBigSouth(401).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceBigWest = new PublicPoliceBigWest(402).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallEast = new PublicPoliceSmallEast(403).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallNorth = new PublicPoliceSmallNorth(404).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallSouth = new PublicPoliceSmallSouth(405).setCreativeTab(IMSM.Public);
	public static Block PublicPoliceSmallWest = new PublicPoliceSmallWest(406).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigNorthEast = new PublicSchoolBigNorthEast(407).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigNorthWest = new PublicSchoolBigNorthWest(408).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigSouthEast = new PublicSchoolBigSouthEast(409).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolBigSouthWest = new PublicSchoolBigSouthWest(410).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallNorthEast = new PublicSchoolSmallNorthEast(411).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallNorthWest = new PublicSchoolSmallNorthWest(412).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallSouthEast = new PublicSchoolSmallSouthEast(413).setCreativeTab(IMSM.Public);
	public static Block PublicSchoolSmallSouthWest = new PublicSchoolSmallSouthWest(414).setCreativeTab(IMSM.Public);
	public static Block PublicTownhallBigEastWest = new PublicTownhallBigEastWest(415).setCreativeTab(IMSM.Public);
	public static Block PublicTownhallBigNorthSouth = new PublicTownhallBigNorthSouth(416).setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallEast = new PublicTownhallSmallEast(417).setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallNorth = new PublicTownhallSmallNorth(418).setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallSouth = new PublicTownhallSmallSouth(419).setCreativeTab(IMSM.Public);
	public static Block PublicTownhallSmallWest = new PublicTownhallSmallWest(420).setCreativeTab(IMSM.Public);
	public static Block PublicUniversityEast = new PublicUniversityEast(421).setCreativeTab(IMSM.Public);
	public static Block PublicUniversityNorth = new PublicUniversityNorth(422).setCreativeTab(IMSM.Public);
	public static Block PublicUniversitySouth = new PublicUniversitySouth(423).setCreativeTab(IMSM.Public);
	public static Block PublicUniversityWest = new PublicUniversityWest(424).setCreativeTab(IMSM.Public);
	public static Block ResidentalEnormous_DensityBlockNorthEastSouthWest = new ResidentalEnormous_DensityBlockNorthEastSouthWest(425).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigEast = new ResidentalEnormous_DensityBrickBigEast(426).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigNorth = new ResidentalEnormous_DensityBrickBigNorth(427).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigSouth = new ResidentalEnormous_DensityBrickBigSouth(428).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickBigWest = new ResidentalEnormous_DensityBrickBigWest(429).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityBrickSmallNorthEastSouthWest = new ResidentalEnormous_DensityBrickSmallNorthEastSouthWest(430).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreyEast = new ResidentalEnormous_DensityGreyEast(431).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreyNorth = new ResidentalEnormous_DensityGreyNorth(432).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreySouth = new ResidentalEnormous_DensityGreySouth(433).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityGreyWest = new ResidentalEnormous_DensityGreyWest(434).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernEast = new ResidentalEnormous_DensityModernEast(435).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernNorth = new ResidentalEnormous_DensityModernNorth(436).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernSouth = new ResidentalEnormous_DensityModernSouth(437).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityModernWest = new ResidentalEnormous_DensityModernWest(438).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityRedEastWest = new ResidentalEnormous_DensityRedEastWest(439).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityRedNorthSouth = new ResidentalEnormous_DensityRedNorthSouth(440).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityRoundNorthEastSouthWest = new ResidentalEnormous_DensityRoundNorthEastSouthWest(441).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneEast2 = new ResidentalEnormous_DensityStoneEast2(442).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneEast = new ResidentalEnormous_DensityStoneEast(443).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneNorth2 = new ResidentalEnormous_DensityStoneNorth2(444).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneNorth = new ResidentalEnormous_DensityStoneNorth(445).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneSouth2 = new ResidentalEnormous_DensityStoneSouth2(446).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneSouth = new ResidentalEnormous_DensityStoneSouth(447).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneWest2 = new ResidentalEnormous_DensityStoneWest2(448).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityStoneWest = new ResidentalEnormous_DensityStoneWest(449).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalEnormous_DensityYellowNorthEastSouthWest = new ResidentalEnormous_DensityYellowNorthEastSouthWest(450).setCreativeTab(IMSM.ResidentalEnormous_Density);
	public static Block ResidentalHigh_DensityBlueEast = new ResidentalHigh_DensityBlueEast(451).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueEastWest = new ResidentalHigh_DensityBlueEastWest(452).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueNorth = new ResidentalHigh_DensityBlueNorth(453).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueNorthSouth = new ResidentalHigh_DensityBlueNorthSouth(454).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueSouth = new ResidentalHigh_DensityBlueSouth(455).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBlueWest = new ResidentalHigh_DensityBlueWest(456).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickEast = new ResidentalHigh_DensityBrickEast(457).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickEastWest = new ResidentalHigh_DensityBrickEastWest(458).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickNorth = new ResidentalHigh_DensityBrickNorth(459).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickNorthSouth = new ResidentalHigh_DensityBrickNorthSouth(460).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickSouth = new ResidentalHigh_DensityBrickSouth(461).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityBrickWest = new ResidentalHigh_DensityBrickWest(462).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreyEast = new ResidentalHigh_DensityGreenGreyEast(463).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreyNorth = new ResidentalHigh_DensityGreenGreyNorth(464).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreySouth = new ResidentalHigh_DensityGreenGreySouth(465).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityGreenGreyWest = new ResidentalHigh_DensityGreenGreyWest(466).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerNorthEast = new ResidentalHigh_DensityRedCornerNorthEast(467).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerNorthWest = new ResidentalHigh_DensityRedCornerNorthWest(468).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerSouthEast = new ResidentalHigh_DensityRedCornerSouthEast(469).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedCornerSouthWest = new ResidentalHigh_DensityRedCornerSouthWest(470).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowEast = new ResidentalHigh_DensityRedYellowEast(471).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowNorth = new ResidentalHigh_DensityRedYellowNorth(472).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowSouth = new ResidentalHigh_DensityRedYellowSouth(473).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityRedYellowWest = new ResidentalHigh_DensityRedYellowWest(474).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneEast2 = new ResidentalHigh_DensityStoneEast2(475).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneEast = new ResidentalHigh_DensityStoneEast(476).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneNorth2 = new ResidentalHigh_DensityStoneNorth2(477).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneNorth = new ResidentalHigh_DensityStoneNorth(478).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneSouth2 = new ResidentalHigh_DensityStoneSouth2(479).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneSouth = new ResidentalHigh_DensityStoneSouth(480).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneWest2 = new ResidentalHigh_DensityStoneWest2(481).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityStoneWest = new ResidentalHigh_DensityStoneWest(482).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowEast = new ResidentalHigh_DensityYellowEast(483).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowNorth = new ResidentalHigh_DensityYellowNorth(484).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowSouth = new ResidentalHigh_DensityYellowSouth(485).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalHigh_DensityYellowWest = new ResidentalHigh_DensityYellowWest(486).setCreativeTab(IMSM.ResidentalHigh_Density);
	public static Block ResidentalLow_DensityBeigeEast = new ResidentalLow_DensityBeigeEast(487).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityBeigeNorth = new ResidentalLow_DensityBeigeNorth(488).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityBeigeSouth = new ResidentalLow_DensityBeigeSouth(489).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityBeigeWest = new ResidentalLow_DensityBeigeWest(490).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanEast = new ResidentalLow_DensityCyanEast(491).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanNorth = new ResidentalLow_DensityCyanNorth(492).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanSouth = new ResidentalLow_DensityCyanSouth(493).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityCyanWest = new ResidentalLow_DensityCyanWest(494).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenEast2 = new ResidentalLow_DensityGreenEast2(495).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenEast = new ResidentalLow_DensityGreenEast(496).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenNorth2 = new ResidentalLow_DensityGreenNorth2(497).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenNorth = new ResidentalLow_DensityGreenNorth(498).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenSouth2 = new ResidentalLow_DensityGreenSouth2(499).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenSouth = new ResidentalLow_DensityGreenSouth(500).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenWest2 = new ResidentalLow_DensityGreenWest2(501).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityGreenWest = new ResidentalLow_DensityGreenWest(502).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueEast2 = new ResidentalLow_DensityLightBlueEast2(503).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueEast = new ResidentalLow_DensityLightBlueEast(504).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueNorth2 = new ResidentalLow_DensityLightBlueNorth2(505).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueNorth = new ResidentalLow_DensityLightBlueNorth(506).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueSouth2 = new ResidentalLow_DensityLightBlueSouth2(507).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueSouth = new ResidentalLow_DensityLightBlueSouth(508).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueWest2 = new ResidentalLow_DensityLightBlueWest2(509).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightBlueWest = new ResidentalLow_DensityLightBlueWest(510).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreyEast = new ResidentalLow_DensityLightGreyEast(511).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreyNorth = new ResidentalLow_DensityLightGreyNorth(512).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreySouth = new ResidentalLow_DensityLightGreySouth(513).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityLightGreyWest = new ResidentalLow_DensityLightGreyWest(514).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernEast = new ResidentalLow_DensityModernEast(515).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernNorth = new ResidentalLow_DensityModernNorth(516).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernSouth = new ResidentalLow_DensityModernSouth(517).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityModernWest = new ResidentalLow_DensityModernWest(518).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeEast = new ResidentalLow_DensityOrangeEast(519).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeNorth = new ResidentalLow_DensityOrangeNorth(520).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeSouth = new ResidentalLow_DensityOrangeSouth(521).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityOrangeWest = new ResidentalLow_DensityOrangeWest(522).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedEast = new ResidentalLow_DensityRedEast(523).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedNorth = new ResidentalLow_DensityRedNorth(524).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedSouth = new ResidentalLow_DensityRedSouth(525).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityRedWest = new ResidentalLow_DensityRedWest(526).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneEast = new ResidentalLow_DensityStoneEast(527).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneNorth = new ResidentalLow_DensityStoneNorth(528).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneSouth = new ResidentalLow_DensityStoneSouth(529).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityStoneWest = new ResidentalLow_DensityStoneWest(530).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteEast = new ResidentalLow_DensityWhiteEast(531).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteNorth = new ResidentalLow_DensityWhiteNorth(532).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteSouth = new ResidentalLow_DensityWhiteSouth(533).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWhiteWest = new ResidentalLow_DensityWhiteWest(534).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodEast = new ResidentalLow_DensityWoodEast(535).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodNorth = new ResidentalLow_DensityWoodNorth(536).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodSouth = new ResidentalLow_DensityWoodSouth(537).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityWoodWest = new ResidentalLow_DensityWoodWest(538).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowEast2 = new ResidentalLow_DensityYellowEast2(539).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowEast = new ResidentalLow_DensityYellowEast(540).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowNorth2 = new ResidentalLow_DensityYellowNorth2(541).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowNorth = new ResidentalLow_DensityYellowNorth(542).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowSouth2 = new ResidentalLow_DensityYellowSouth2(543).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowSouth = new ResidentalLow_DensityYellowSouth(544).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowWest2 = new ResidentalLow_DensityYellowWest2(545).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalLow_DensityYellowWest = new ResidentalLow_DensityYellowWest(546).setCreativeTab(IMSM.ResidentalLow_Density);
	public static Block ResidentalMedium_DensityBlueGreenEast = new ResidentalMedium_DensityBlueGreenEast(547).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueGreenNorth = new ResidentalMedium_DensityBlueGreenNorth(548).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueGreenSouth = new ResidentalMedium_DensityBlueGreenSouth(549).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueGreenWest = new ResidentalMedium_DensityBlueGreenWest(550).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedEast = new ResidentalMedium_DensityBlueRedEast(551).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedNorth = new ResidentalMedium_DensityBlueRedNorth(552).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedSouth = new ResidentalMedium_DensityBlueRedSouth(553).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBlueRedWest = new ResidentalMedium_DensityBlueRedWest(554).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickEast = new ResidentalMedium_DensityBrickEast(555).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickNorth = new ResidentalMedium_DensityBrickNorth(556).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickSouth = new ResidentalMedium_DensityBrickSouth(557).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityBrickWest = new ResidentalMedium_DensityBrickWest(558).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalEast = new ResidentalMedium_DensityHorizontalEast(559).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalNorth = new ResidentalMedium_DensityHorizontalNorth(560).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalSouth = new ResidentalMedium_DensityHorizontalSouth(561).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityHorizontalWest = new ResidentalMedium_DensityHorizontalWest(562).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenEast = new ResidentalMedium_DensityOrangeGreenEast(563).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenNorth = new ResidentalMedium_DensityOrangeGreenNorth(564).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenSouth = new ResidentalMedium_DensityOrangeGreenSouth(565).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityOrangeGreenWest = new ResidentalMedium_DensityOrangeGreenWest(566).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzEast = new ResidentalMedium_DensityQuartzEast(567).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzNorth = new ResidentalMedium_DensityQuartzNorth(568).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzSouth = new ResidentalMedium_DensityQuartzSouth(569).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityQuartzWest = new ResidentalMedium_DensityQuartzWest(570).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenEast = new ResidentalMedium_DensityRedGreenEast(571).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenNorth = new ResidentalMedium_DensityRedGreenNorth(572).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenSouth = new ResidentalMedium_DensityRedGreenSouth(573).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRedGreenWest = new ResidentalMedium_DensityRedGreenWest(574).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofEast = new ResidentalMedium_DensityRoofEast(575).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofNorth = new ResidentalMedium_DensityRoofNorth(576).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofSouth = new ResidentalMedium_DensityRoofSouth(577).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityRoofWest = new ResidentalMedium_DensityRoofWest(578).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone1EastWest = new ResidentalMedium_DensityStone1EastWest(579).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone1NorthSouth = new ResidentalMedium_DensityStone1NorthSouth(580).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone2EastWest = new ResidentalMedium_DensityStone2EastWest(581).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStone2NorthSouth = new ResidentalMedium_DensityStone2NorthSouth(582).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerNorthEast = new ResidentalMedium_DensityStoneCornerNorthEast(583).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerNorthWest = new ResidentalMedium_DensityStoneCornerNorthWest(584).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerSouthEast = new ResidentalMedium_DensityStoneCornerSouthEast(585).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneCornerSouthWest = new ResidentalMedium_DensityStoneCornerSouthWest(586).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEast = new ResidentalMedium_DensityStoneEast(587).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndNorthEastWest = new ResidentalMedium_DensityStoneEndNorthEastWest(588).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndNorthSouthEast = new ResidentalMedium_DensityStoneEndNorthSouthEast(589).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndNorthSouthWest = new ResidentalMedium_DensityStoneEndNorthSouthWest(590).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneEndSouthEastWest = new ResidentalMedium_DensityStoneEndSouthEastWest(591).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneNorth = new ResidentalMedium_DensityStoneNorth(592).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneSouth = new ResidentalMedium_DensityStoneSouth(593).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityStoneWest = new ResidentalMedium_DensityStoneWest(594).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalEast = new ResidentalMedium_DensityVerticalEast(595).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalNorth = new ResidentalMedium_DensityVerticalNorth(596).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalSouth = new ResidentalMedium_DensityVerticalSouth(597).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityVerticalWest = new ResidentalMedium_DensityVerticalWest(598).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedEast = new ResidentalMedium_DensityYellowRedEast(599).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedNorth = new ResidentalMedium_DensityYellowRedNorth(600).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedSouth = new ResidentalMedium_DensityYellowRedSouth(601).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ResidentalMedium_DensityYellowRedWest = new ResidentalMedium_DensityYellowRedWest(602).setCreativeTab(IMSM.ResidentalMedium_Density);
	public static Block ShoppingHigh_DensityQuartzEastWest = new ShoppingHigh_DensityQuartzEastWest(603).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingHigh_DensityQuartzNorthSouth = new ShoppingHigh_DensityQuartzNorthSouth(604).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickEast = new ShoppingLow_DensityBrickEast(605).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickNorth = new ShoppingLow_DensityBrickNorth(606).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickSouth = new ShoppingLow_DensityBrickSouth(607).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityBrickWest = new ShoppingLow_DensityBrickWest(608).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenEast = new ShoppingLow_DensityGreenEast(609).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenNorth = new ShoppingLow_DensityGreenNorth(610).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenSouth = new ShoppingLow_DensityGreenSouth(611).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityGreenWest = new ShoppingLow_DensityGreenWest(612).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeEast = new ShoppingLow_DensityOrangeEast(613).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeNorth = new ShoppingLow_DensityOrangeNorth(614).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeSouth = new ShoppingLow_DensityOrangeSouth(615).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityOrangeWest = new ShoppingLow_DensityOrangeWest(616).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkEast = new ShoppingLow_DensityPinkEast(617).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkNorth = new ShoppingLow_DensityPinkNorth(618).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkSouth = new ShoppingLow_DensityPinkSouth(619).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingLow_DensityPinkWest = new ShoppingLow_DensityPinkWest(620).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernEast = new ShoppingMedium_DensityModernEast(621).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernNorth = new ShoppingMedium_DensityModernNorth(622).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernSouth = new ShoppingMedium_DensityModernSouth(623).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityModernWest = new ShoppingMedium_DensityModernWest(624).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzEast = new ShoppingMedium_DensityQuartzEast(625).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzNorth = new ShoppingMedium_DensityQuartzNorth(626).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzSouth = new ShoppingMedium_DensityQuartzSouth(627).setCreativeTab(IMSM.Shopping);
	public static Block ShoppingMedium_DensityQuartzWest = new ShoppingMedium_DensityQuartzWest(628).setCreativeTab(IMSM.Shopping);
	public static Block TransportAirportRunway_EastWestBuilding_North = new TransportAirportRunway_EastWestBuilding_North(629).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAirportRunway_EastWestBuilding_South = new TransportAirportRunway_EastWestBuilding_South(630).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAirportRunway_NorthSouthBuilding_East = new TransportAirportRunway_NorthSouthBuilding_East(631).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAirportRunway_NorthSouthBuilding_West = new TransportAirportRunway_NorthSouthBuilding_West(632).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportAvenue1EastWest = new TransportAvenue1EastWest(633).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenue1NorthSouth = new TransportAvenue1NorthSouth(634).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenue2EastWest = new TransportAvenue2EastWest(635).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenue2NorthSouth = new TransportAvenue2NorthSouth(636).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueEEast = new TransportAvenueEEast(637).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueENorth = new TransportAvenueENorth(638).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueESouth = new TransportAvenueESouth(639).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueEWest = new TransportAvenueEWest(640).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLNorthEast = new TransportAvenueLNorthEast(641).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLNorthWest = new TransportAvenueLNorthWest(642).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLSouthEast = new TransportAvenueLSouthEast(643).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueLSouthWest = new TransportAvenueLSouthWest(644).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTNorthEastWest = new TransportAvenueTNorthEastWest(645).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTNorthSouthEast = new TransportAvenueTNorthSouthEast(646).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTNorthSouthWest = new TransportAvenueTNorthSouthWest(647).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueTSouthEastWest = new TransportAvenueTSouthEastWest(648).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportAvenueXNorthSouthEastWest = new TransportAvenueXNorthSouthEastWest(649).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue1EastWest = new TransportBridgeAvenue1EastWest(650).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue1NorthSouth = new TransportBridgeAvenue1NorthSouth(651).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue2NorthSouth = new TransportBridgeAvenue2NorthSouth(652).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue2SouthWest = new TransportBridgeAvenue2SouthWest(653).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue3EastWest = new TransportBridgeAvenue3EastWest(654).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue3NorthSouth = new TransportBridgeAvenue3NorthSouth(655).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue4EastWest = new TransportBridgeAvenue4EastWest(656).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenue4NorthSouth = new TransportBridgeAvenue4NorthSouth(657).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLNorthEast = new TransportBridgeAvenueLNorthEast(658).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLNorthWest = new TransportBridgeAvenueLNorthWest(659).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLSouthEast = new TransportBridgeAvenueLSouthEast(660).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeAvenueLSouthWest = new TransportBridgeAvenueLSouthWest(661).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway1EastWest = new TransportBridgeHighway1EastWest(662).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway1NorthSouth = new TransportBridgeHighway1NorthSouth(663).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway2EastWest = new TransportBridgeHighway2EastWest(664).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway2NorthSouth = new TransportBridgeHighway2NorthSouth(665).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway3EastWest = new TransportBridgeHighway3EastWest(666).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway3NorthSouth = new TransportBridgeHighway3NorthSouth(667).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway4EastWest = new TransportBridgeHighway4EastWest(668).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighway4NorthSouth = new TransportBridgeHighway4NorthSouth(669).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLNorthEast = new TransportBridgeHighwayLNorthEast(670).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLNorthWest = new TransportBridgeHighwayLNorthWest(671).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLSouthEast = new TransportBridgeHighwayLSouthEast(672).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeHighwayLSouthWest = new TransportBridgeHighwayLSouthWest(673).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad1EastWest = new TransportBridgeRoad1EastWest(674).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad1NorthSouth = new TransportBridgeRoad1NorthSouth(675).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad2EastWest = new TransportBridgeRoad2EastWest(676).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoad2NorthSouth = new TransportBridgeRoad2NorthSouth(677).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLNorthEast = new TransportBridgeRoadLNorthEast(678).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLNorthWest = new TransportBridgeRoadLNorthWest(679).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLSouthEast = new TransportBridgeRoadLSouthEast(680).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeRoadLSouthWest = new TransportBridgeRoadLSouthWest(681).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet1EastWest = new TransportBridgeStreet1EastWest(682).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet1NorthSouth = new TransportBridgeStreet1NorthSouth(683).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet2EastWest = new TransportBridgeStreet2EastWest(684).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreet2NorthSouth = new TransportBridgeStreet2NorthSouth(685).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLNorthEast = new TransportBridgeStreetLNorthEast(686).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLNorthWest = new TransportBridgeStreetLNorthWest(687).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLSouthEast = new TransportBridgeStreetLSouthEast(688).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportBridgeStreetLSouthWest = new TransportBridgeStreetLSouthWest(689).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside = new TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside(690).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside = new TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside(691).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside = new TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside(692).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside = new TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside(693).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside = new TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside(694).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside = new TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside(695).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside = new TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside(696).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside = new TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside(697).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_EastStreet_North = new TransportConnectorAvenue_StreetLAvenue_EastStreet_North(698).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_EastStreet_South = new TransportConnectorAvenue_StreetLAvenue_EastStreet_South(699).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_NorthStreet_East = new TransportConnectorAvenue_StreetLAvenue_NorthStreet_East(700).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_NorthStreet_West = new TransportConnectorAvenue_StreetLAvenue_NorthStreet_West(701).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_SouthStreet_East = new TransportConnectorAvenue_StreetLAvenue_SouthStreet_East(702).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_SouthStreet_West = new TransportConnectorAvenue_StreetLAvenue_SouthStreet_West(703).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_WestStreet_North = new TransportConnectorAvenue_StreetLAvenue_WestStreet_North(704).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetLAvenue_WestStreet_South = new TransportConnectorAvenue_StreetLAvenue_WestStreet_South(705).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth = new TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth(706).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North = new TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North(707).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South = new TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South(708).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East = new TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East(709).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West = new TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West(710).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest = new TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest(711).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest = new TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest(712).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth = new TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth(713).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth = new TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth(714).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest = new TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest(715).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_EastAvenue_West = new TransportConnectorBridge_AvenueBridge_EastAvenue_West(716).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_NorthAvenue_South = new TransportConnectorBridge_AvenueBridge_NorthAvenue_South(717).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_SouthAvenue_North = new TransportConnectorBridge_AvenueBridge_SouthAvenue_North(718).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_AvenueBridge_WestAvenue_East = new TransportConnectorBridge_AvenueBridge_WestAvenue_East(719).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_EastRoad_West = new TransportConnectorBridge_RoadBridge_EastRoad_West(720).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_NorthRoad_South = new TransportConnectorBridge_RoadBridge_NorthRoad_South(721).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_SouthRoad_North = new TransportConnectorBridge_RoadBridge_SouthRoad_North(722).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_RoadBridge_WestRoad_East = new TransportConnectorBridge_RoadBridge_WestRoad_East(723).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_EastStreet_West = new TransportConnectorBridge_StreetBridge_EastStreet_West(724).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_NorthStreet_South = new TransportConnectorBridge_StreetBridge_NorthStreet_South(725).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_SouthStreet_North = new TransportConnectorBridge_StreetBridge_SouthStreet_North(726).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorBridge_StreetBridge_WestStreet_East = new TransportConnectorBridge_StreetBridge_WestStreet_East(727).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_EastAvenue_West = new TransportConnectorHighway_AvenueHighway_EastAvenue_West(728).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_NorthAvenue_South = new TransportConnectorHighway_AvenueHighway_NorthAvenue_South(729).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_SouthAvenue_North = new TransportConnectorHighway_AvenueHighway_SouthAvenue_North(730).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_AvenueHighway_WestAvenue_East = new TransportConnectorHighway_AvenueHighway_WestAvenue_East(731).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West = new TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West(732).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South = new TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South(733).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North = new TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North(734).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East = new TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East(735).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West = new TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West(736).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South = new TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South(737).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North = new TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North(738).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East = new TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East(739).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHarbourBigEast = new TransportHarbourBigEast(740).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourBigNorth = new TransportHarbourBigNorth(741).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourBigSouth = new TransportHarbourBigSouth(742).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourBigWest = new TransportHarbourBigWest(743).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerNorthEast = new TransportHarbourSide1CornerNorthEast(744).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerNorthWest = new TransportHarbourSide1CornerNorthWest(745).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerSouthEast = new TransportHarbourSide1CornerSouthEast(746).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide1CornerSouthWest = new TransportHarbourSide1CornerSouthWest(747).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneEast = new TransportHarbourSide2CornerCraneEast(748).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneNorth = new TransportHarbourSide2CornerCraneNorth(749).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneSouth = new TransportHarbourSide2CornerCraneSouth(750).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerCraneWest = new TransportHarbourSide2CornerCraneWest(751).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerEast = new TransportHarbourSide2CornerEast(752).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerNorth = new TransportHarbourSide2CornerNorth(753).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerSouth = new TransportHarbourSide2CornerSouth(754).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide2CornerWest = new TransportHarbourSide2CornerWest(755).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast = new TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast(756).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest = new TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest(757).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest = new TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest(758).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest = new TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest(759).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallEast = new TransportHarbourSmallEast(760).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallNorth = new TransportHarbourSmallNorth(761).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallSouth = new TransportHarbourSmallSouth(762).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHarbourSmallWest = new TransportHarbourSmallWest(763).setCreativeTab(IMSM.TransportHarbour);
	public static Block TransportHighway05EastWestNorthside = new TransportHighway05EastWestNorthside(764).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway05EastWestSouthside = new TransportHighway05EastWestSouthside(765).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway05NorthSouthEastside = new TransportHighway05NorthSouthEastside(766).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway05NorthSouthWestside = new TransportHighway05NorthSouthWestside(767).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway1EastWest = new TransportHighway1EastWest(768).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway1NorthSouth = new TransportHighway1NorthSouth(769).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway2EastWest = new TransportHighway2EastWest(770).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighway2NorthSouth = new TransportHighway2NorthSouth(771).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayEastWestEastside = new TransportHighwayDrivewayEastWestEastside(772).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayEastWestWestside = new TransportHighwayDrivewayEastWestWestside(773).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitEastWestEastside = new TransportHighwayDrivewayExitEastWestEastside(774).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitEastWestWestside = new TransportHighwayDrivewayExitEastWestWestside(775).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitNorthSouthNorthside = new TransportHighwayDrivewayExitNorthSouthNorthside(776).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayExitNorthSouthSouthside = new TransportHighwayDrivewayExitNorthSouthSouthside(777).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayNorthSouthNorthside = new TransportHighwayDrivewayNorthSouthNorthside(778).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayDrivewayNorthSouthSouthside = new TransportHighwayDrivewayNorthSouthSouthside(779).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitEastWestEastside = new TransportHighwayExitEastWestEastside(780).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitEastWestWestside = new TransportHighwayExitEastWestWestside(781).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitNorthSouthNorthside = new TransportHighwayExitNorthSouthNorthside(782).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayExitNorthSouthSouthside = new TransportHighwayExitNorthSouthSouthside(783).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05EastWestNorthside = new TransportHighwayFloor05EastWestNorthside(784).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05EastWestSouthside = new TransportHighwayFloor05EastWestSouthside(785).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05NorthSouthEastside = new TransportHighwayFloor05NorthSouthEastside(786).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor05NorthSouthWestside = new TransportHighwayFloor05NorthSouthWestside(787).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor1EastWest = new TransportHighwayFloor1EastWest(788).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor1NorthSouth = new TransportHighwayFloor1NorthSouth(789).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor2EastWest = new TransportHighwayFloor2EastWest(790).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloor2NorthSouth = new TransportHighwayFloor2NorthSouth(791).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayEastWestEastside = new TransportHighwayFloorDrivewayEastWestEastside(792).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayEastWestWestside = new TransportHighwayFloorDrivewayEastWestWestside(793).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitEastWestEastside = new TransportHighwayFloorDrivewayExitEastWestEastside(794).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitEastWestWestside = new TransportHighwayFloorDrivewayExitEastWestWestside(795).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitNorthSouthNorthside = new TransportHighwayFloorDrivewayExitNorthSouthNorthside(796).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayExitNorthSouthSouthside = new TransportHighwayFloorDrivewayExitNorthSouthSouthside(797).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayNorthSouthNorthside = new TransportHighwayFloorDrivewayNorthSouthNorthside(798).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorDrivewayNorthSouthSouthside = new TransportHighwayFloorDrivewayNorthSouthSouthside(799).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitEastWestEast = new TransportHighwayFloorExitEastWestEast(800).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitEastWestWestside = new TransportHighwayFloorExitEastWestWestside(801).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitNorthSouthNorthside = new TransportHighwayFloorExitNorthSouthNorthside(802).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorExitNorthSouthSouthside = new TransportHighwayFloorExitNorthSouthSouthside(803).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLNorthEast = new TransportHighwayFloorLNorthEast(804).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLNorthWest = new TransportHighwayFloorLNorthWest(805).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLSouthEast = new TransportHighwayFloorLSouthEast(806).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorLSouthWest = new TransportHighwayFloorLSouthWest(807).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTNorthEastWest = new TransportHighwayFloorTNorthEastWest(808).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTNorthSouthEast = new TransportHighwayFloorTNorthSouthEast(809).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTNorthSouthWest = new TransportHighwayFloorTNorthSouthWest(810).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorTSouthEastWest = new TransportHighwayFloorTSouthEastWest(811).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayFloorXNorthEastSouthWest = new TransportHighwayFloorXNorthEastSouthWest(812).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLNorthEast = new TransportHighwayLNorthEast(813).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLNorthWest = new TransportHighwayLNorthWest(814).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLSouthEast = new TransportHighwayLSouthEast(815).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayLSouthWest = new TransportHighwayLSouthWest(816).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTNorthEastWest = new TransportHighwayTNorthEastWest(817).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTNorthSouthEast = new TransportHighwayTNorthSouthEast(818).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTNorthSouthWest = new TransportHighwayTNorthSouthWest(819).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayTSouthEastWest = new TransportHighwayTSouthEastWest(820).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportHighwayXNorthEastSouthWest = new TransportHighwayXNorthEastSouthWest(821).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportPublicConnectorHightram_TramHightram_EastTram_West = new TransportPublicConnectorHightram_TramHightram_EastTram_West(822).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicConnectorHightram_TramHightram_NorthTram_South = new TransportPublicConnectorHightram_TramHightram_NorthTram_South(823).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicConnectorHightram_TramHightram_SouthTram_North = new TransportPublicConnectorHightram_TramHightram_SouthTram_North(824).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicConnectorHightram_TramHightram_WestTram_East = new TransportPublicConnectorHightram_TramHightram_WestTram_East(825).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightram1EastWest = new TransportPublicHightram1EastWest(826).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightram1NorthSouth = new TransportPublicHightram1NorthSouth(827).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramEEast = new TransportPublicHightramEEast(828).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramENorth = new TransportPublicHightramENorth(829).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramESouth = new TransportPublicHightramESouth(830).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramEWest = new TransportPublicHightramEWest(831).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLNorthEast = new TransportPublicHightramLNorthEast(832).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLNorthWest = new TransportPublicHightramLNorthWest(833).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLSouthEast = new TransportPublicHightramLSouthEast(834).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramLSouthWest = new TransportPublicHightramLSouthWest(835).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramStationEastWest = new TransportPublicHightramStationEastWest(836).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramStationNorthSouth = new TransportPublicHightramStationNorthSouth(837).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicHightramXNorthEastSouthWest = new TransportPublicHightramXNorthEastSouthWest(838).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram1EastWest = new TransportPublicTram1EastWest(839).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram1NorthSouth = new TransportPublicTram1NorthSouth(840).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramEEast = new TransportPublicTramEEast(841).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramENorth = new TransportPublicTramENorth(842).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramESouth = new TransportPublicTramESouth(843).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramEWest = new TransportPublicTramEWest(844).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLNorthEast = new TransportPublicTramLNorthEast(845).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLNorthWest = new TransportPublicTramLNorthWest(846).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLSouthEast = new TransportPublicTramLSouthEast(847).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramLSouthWest = new TransportPublicTramLSouthWest(848).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_road1EastWest = new TransportPublicTram_on_road1EastWest(849).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_road1NorthSouth = new TransportPublicTram_on_road1NorthSouth(850).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadEEast = new TransportPublicTram_on_roadEEast(851).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadENorth = new TransportPublicTram_on_roadENorth(852).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadESouth = new TransportPublicTram_on_roadESouth(853).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadEWest = new TransportPublicTram_on_roadEWest(854).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLNorthEast = new TransportPublicTram_on_roadLNorthEast(855).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLNorthWest = new TransportPublicTram_on_roadLNorthWest(856).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLSouthEast = new TransportPublicTram_on_roadLSouthEast(857).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTram_on_roadLSouthWest = new TransportPublicTram_on_roadLSouthWest(858).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramStationEastWest = new TransportPublicTramStationEastWest(859).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramStationNorthSouth = new TransportPublicTramStationNorthSouth(860).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportPublicTramXNorthEastSouthWest = new TransportPublicTramXNorthEastSouthWest(861).setCreativeTab(IMSM.TransportPublic);
	public static Block TransportRoad1EastWest = new TransportRoad1EastWest(862).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoad1NorthSouth = new TransportRoad1NorthSouth(863).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadEEast = new TransportRoadEEast(864).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadENorth = new TransportRoadENorth(865).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadESouth = new TransportRoadESouth(866).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadEWest = new TransportRoadEWest(867).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLNorthEast = new TransportRoadLNorthEast(868).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLNorthWest = new TransportRoadLNorthWest(869).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLSouthEast = new TransportRoadLSouthEast(870).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadLSouthWest = new TransportRoadLSouthWest(871).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTNorthEastWest = new TransportRoadTNorthEastWest(872).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTNorthSouthEast = new TransportRoadTNorthSouthEast(873).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTNorthSouthWest = new TransportRoadTNorthSouthWest(874).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadTSouthEastWest = new TransportRoadTSouthEastWest(875).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportRoadXNorthEastSouthWest = new TransportRoadXNorthEastSouthWest(876).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreet1EastWest = new TransportStreet1EastWest(877).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreet1NorthSouth = new TransportStreet1NorthSouth(878).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetEEast = new TransportStreetEEast(879).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetENorth = new TransportStreetENorth(880).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetESouth = new TransportStreetESouth(881).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetEWest = new TransportStreetEWest(882).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLNorthEast = new TransportStreetLNorthEast(883).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLNorthWest = new TransportStreetLNorthWest(884).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLSouthEast = new TransportStreetLSouthEast(885).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetLSouthWest = new TransportStreetLSouthWest(886).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetRoundaboutNorthEastSouthWest = new TransportStreetRoundaboutNorthEastSouthWest(887).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTNorthEastWest = new TransportStreetTNorthEastWest(888).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTNorthSouthEast = new TransportStreetTNorthSouthEast(889).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTNorthSouthWest = new TransportStreetTNorthSouthWest(890).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetTSouthEastWest = new TransportStreetTSouthEastWest(891).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportStreetXNorthEastSouthWest = new TransportStreetXNorthEastSouthWest(892).setCreativeTab(IMSM.TransportRoads);
	public static Block TransportWater1CornerNorthEast = new TransportWater1CornerNorthEast(893).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater1CornerNorthWest = new TransportWater1CornerNorthWest(894).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater1CornerSouthEast = new TransportWater1CornerSouthEast(895).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater1CornerSouthWest = new TransportWater1CornerSouthWest(896).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerEast = new TransportWater2CornerEast(897).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerNorth = new TransportWater2CornerNorth(898).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerSouth = new TransportWater2CornerSouth(899).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater2CornerWest = new TransportWater2CornerWest(900).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerNorthEast_NorthWest_SouthEast = new TransportWater3CornerNorthEast_NorthWest_SouthEast(901).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerNorthEast_NorthWest_SouthWest = new TransportWater3CornerNorthEast_NorthWest_SouthWest(902).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerSouthEast_SouthWest_NorthEast = new TransportWater3CornerSouthEast_SouthWest_NorthEast(903).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater3CornerSouthEast_SouthWest_NorthWest = new TransportWater3CornerSouthEast_SouthWest_NorthWest(904).setCreativeTab(IMSM.TransportWater);
	public static Block TransportWater4CornerNorthSouthEastWest = new TransportWater4CornerNorthSouthEastWest(905).setCreativeTab(IMSM.TransportWater);
	public static Block UtilityPower_NuclearEast = new UtilityPower_NuclearEast(906).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_NuclearNorth = new UtilityPower_NuclearNorth(907).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_NuclearSouth = new UtilityPower_NuclearSouth(908).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_NuclearWest = new UtilityPower_NuclearWest(909).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalEast = new UtilityPower_OilCoalEast(910).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalNorth = new UtilityPower_OilCoalNorth(911).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalSouth = new UtilityPower_OilCoalSouth(912).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_OilCoalWest = new UtilityPower_OilCoalWest(913).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_SunNorthEastSouthWest = new UtilityPower_SunNorthEastSouthWest(914).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindEast = new UtilityPower_WindEast(915).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindNorth = new UtilityPower_WindNorth(916).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindSouth = new UtilityPower_WindSouth(917).setCreativeTab(IMSM.Utility);
	public static Block UtilityPower_WindWest = new UtilityPower_WindWest(918).setCreativeTab(IMSM.Utility);
	public static Block UtilityPumpjackEastWest = new UtilityPumpjackEastWest(919).setCreativeTab(IMSM.Utility);
	public static Block UtilityPumpjackNorthSouth = new UtilityPumpjackNorthSouth(920).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningEast = new UtilityScrap_BurningEast(921).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningNorth = new UtilityScrap_BurningNorth(922).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningSouth = new UtilityScrap_BurningSouth(923).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_BurningWest = new UtilityScrap_BurningWest(924).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapEast = new UtilityScrap_HeapEast(925).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapNorth = new UtilityScrap_HeapNorth(926).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapSouth = new UtilityScrap_HeapSouth(927).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_HeapWest = new UtilityScrap_HeapWest(928).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleEast = new UtilityScrap_RecycleEast(929).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleNorth = new UtilityScrap_RecycleNorth(930).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleSouth = new UtilityScrap_RecycleSouth(931).setCreativeTab(IMSM.Utility);
	public static Block UtilityScrap_RecycleWest = new UtilityScrap_RecycleWest(932).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpEast = new UtilityWater_PumpEast(933).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpNorth = new UtilityWater_PumpNorth(934).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpSouth = new UtilityWater_PumpSouth(935).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_PumpWest = new UtilityWater_PumpWest(936).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TowerNorthEastSouthWest = new UtilityWater_TowerNorthEastSouthWest(937).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentEast = new UtilityWater_TreatmentEast(938).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentNorth = new UtilityWater_TreatmentNorth(939).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentSouth = new UtilityWater_TreatmentSouth(940).setCreativeTab(IMSM.Utility);
	public static Block UtilityWater_TreatmentWest = new UtilityWater_TreatmentWest(941).setCreativeTab(IMSM.Utility);
	
	public static Block BlockAirBalloon = new BlockAirBalloon(942).setCreativeTab(IMSM.Structures);
	public static Block BlockAirplane = new BlockAirplane(943).setCreativeTab(IMSM.Structures);
	//public static Block BlockApplepie = new BlockApplepie(944).setCreativeTab(IMSM.Structures);
	public static Block BlockArena1 = new BlockArena1(945).setCreativeTab(IMSM.Structures);
	public static Block BlockArena2 = new BlockArena2(946).setCreativeTab(IMSM.Structures);
	public static Block BlockBigPyramid = new BlockBigPyramid(947).setCreativeTab(IMSM.Other);
	public static Block BlockBoat = new BlockBoat(948).setCreativeTab(IMSM.Structures);
	public static Block BlockBunker = new BlockBunker(949).setCreativeTab(IMSM.Structures);
	//public static Block BlockCactus2 = new BlockCactus2(950).setCreativeTab(IMSM.Structures);
	//public static Block BlockCake2 = new BlockCake2(951).setCreativeTab(IMSM.Structures);
	public static Block BlockCastleTower = new BlockCastleTower(952).setCreativeTab(IMSM.Structures);
	//public static Block BlockCave = new BlockCave(953).setCreativeTab(IMSM.Structures);
	//public static Block BlockColumn = new BlockColumn(954).setCreativeTab(IMSM.Structures);
	public static Block BlockCosyHouse = new BlockCosyHouse(955).setCreativeTab(IMSM.Structures);
	public static Block BlockDungeon = new BlockDungeon(956).setCreativeTab(IMSM.Structures);
	public static Block BlockEnchantmentRoom = new BlockEnchantmentRoom(957).setCreativeTab(IMSM.Structures);
	public static Block BlockFarm2 = new BlockFarm2(958).setCreativeTab(IMSM.Structures);
	public static Block BlockFarm3 = new BlockFarm3(959).setCreativeTab(IMSM.Structures);
	public static Block BlockFarm4 = new BlockFarm4(960).setCreativeTab(IMSM.Structures);
	public static Block BlockFarm = new BlockFarm(961).setCreativeTab(IMSM.Structures);
	//public static Block BlockFloatingSphere = new BlockFloatingSphere(962).setCreativeTab(IMSM.Structures);
	public static Block BlockGiantTree = new BlockGiantTree(963).setCreativeTab(IMSM.Structures);
	//public static Block BlockGlassHouse = new BlockGlassHouse(964).setCreativeTab(IMSM.Structures);
	public static Block BlockHountedHouse = new BlockHountedHouse(965).setCreativeTab(IMSM.Structures);
	//public static Block BlockHouse2 = new BlockHouse2(966).setCreativeTab(IMSM.Structures);
	public static Block BlockHouse = new BlockHouse(967).setCreativeTab(IMSM.Structures);
	public static Block BlockHouseTrap1 = new BlockHouseTrap1(968).setCreativeTab(IMSM.Structures);
	public static Block BlockHouseTrap2 = new BlockHouseTrap2(969).setCreativeTab(IMSM.Structures);
	//public static Block BlockLeaves2 = new BlockLeaves2(970).setCreativeTab(IMSM.Structures);
	public static Block BlockLighthouse = new BlockLighthouse(971).setCreativeTab(IMSM.Structures);
	public static Block BlockMegaHouse2 = new BlockMegaHouse2(972).setCreativeTab(IMSM.Structures);
	public static Block BlockMegaHouse = new BlockMegaHouse(973).setCreativeTab(IMSM.Structures);
	public static Block BlockMegaTower = new BlockMegaTower(974).setCreativeTab(IMSM.Structures);
	//public static Block BlockPenIron = new BlockPenIron(975).setCreativeTab(IMSM.Structures);
	//public static Block BlockPenNether = new BlockPenNether(976).setCreativeTab(IMSM.Structures);
	//public static Block BlockPenWood = new BlockPenWood(977).setCreativeTab(IMSM.Structures);
	public static Block BlockPlane = new BlockPlane(978).setCreativeTab(IMSM.Structures);
	public static Block BlockPrison2 = new BlockPrison2(979).setCreativeTab(IMSM.Structures);
	public static Block BlockPrison = new BlockPrison(980).setCreativeTab(IMSM.Structures);
	public static Block BlockPyramid = new BlockPyramid(981).setCreativeTab(IMSM.Structures);
	public static Block BlockRollerCoaster2 = new BlockRollerCoaster2(982).setCreativeTab(IMSM.Structures);
	public static Block BlockRollercoaster = new BlockRollercoaster(983).setCreativeTab(IMSM.Structures);
	//public static Block BlockShelter = new BlockShelter(984).setCreativeTab(IMSM.Structures);
	//public static Block BlockSkyscraper2 = new BlockSkyscraper2(985).setCreativeTab(IMSM.Structures);
	public static Block BlockSkyscraper = new BlockSkyscraper(986).setCreativeTab(IMSM.Structures);
	//public static Block BlockStadium2 = new BlockStadium2(987).setCreativeTab(IMSM.Structures);
	public static Block BlockStadium = new BlockStadium(988).setCreativeTab(IMSM.Other);
	//public static Block BlockStandardBrickHouse = new BlockStandardBrickHouse(989).setCreativeTab(IMSM.Structures);
	public static Block BlockStoreHouse = new BlockStoreHouse(990).setCreativeTab(IMSM.Structures);
	//public static Block BlockStreet = new BlockStreet(991).setCreativeTab(IMSM.Structures);
	public static Block BlockTorch2 = new BlockTorch2(992).setCreativeTab(IMSM.Structures);
	public static Block BlockTower = new BlockTower(993).setCreativeTab(IMSM.Structures);
	public static Block BlockWaterSlide = new BlockWaterSlide(994).setCreativeTab(IMSM.Structures);
	
	public static Block OtherBrickHouse = new OtherBrickHouse(200).setCreativeTab(IMSM.Other);
	public static Block OtherGrandHouse = new OtherGrandHouse(201).setCreativeTab(IMSM.Structures);
	public static Block OtherStable = new OtherStable(203).setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse2 = new OtherSurvivorHouse2(204).setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse3 = new OtherSurvivorHouse3(205).setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse4 = new OtherSurvivorHouse4(206).setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse5 = new OtherSurvivorHouse5(207).setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse6 = new OtherSurvivorHouse6(208).setCreativeTab(IMSM.Structures);
	public static Block OtherSurvivorHouse7 = new OtherSurvivorHouse7(209).setCreativeTab(IMSM.Structures);
	public static Block OtherSurvivorHouse8 = new OtherSurvivorHouse8(210).setCreativeTab(IMSM.Other);
	public static Block OtherSurvivorHouse = new OtherSurvivorHouse(211).setCreativeTab(IMSM.Other);
	public static Block OtherTemple = new OtherTemple(212).setCreativeTab(IMSM.Other);
	public static Block SurvivalSmallBuilding = new SurvivalSmallBuilding(213).setCreativeTab(IMSM.Structures);
	public static Block SurvivalWoodenHouse = new SurvivalWoodenHouse(214).setCreativeTab(IMSM.Structures);
	public static Block WoodenHouse = new WoodenHouse(215).setCreativeTab(IMSM.Structures);
	public static Block BlockCheckerboard = new BlockCheckerboard(200).setCreativeTab(IMSM.Other);
	//public static Block BlockAtlantis = new BlockAtlantis(200).setCreativeTab(IMSM.WorldGeneration);

	//public static Block Remover16256 = new Remover16256(995).setCreativeTab(IMSM.Remover);
	//public static Block Remover1632 = new Remover1632(996).setCreativeTab(IMSM.Remover);
	//public static Block Remover168 = new Remover168(997).setCreativeTab(IMSM.Remover);
	public static Block Remover16 = new Remover16(998).setCreativeTab(IMSM.Remover);
	//public static Block Remover3216 = new Remover3216(999).setCreativeTab(IMSM.Remover);
	//public static Block Remover32256 = new Remover32256(1000).setCreativeTab(IMSM.Remover);
	//public static Block Remover328 = new Remover328(1001).setCreativeTab(IMSM.Remover);
	public static Block Remover32 = new Remover32(1002).setCreativeTab(IMSM.Remover);
	//public static Block Remover64256 = new Remover64256(1003).setCreativeTab(IMSM.Remover);
	public static Block Remover64 = new Remover64(1004).setCreativeTab(IMSM.Remover);
	//public static Block Remover816 = new Remover816(1005).setCreativeTab(IMSM.Remover);
	//public static Block Remover8256 = new Remover8256(1006).setCreativeTab(IMSM.Remover);
	//public static Block Remover832 = new Remover832(1007).setCreativeTab(IMSM.Remover);
	public static Block Remover8 = new Remover8(1008).setCreativeTab(IMSM.Remover);
	public static Block RemoverLast = new RemoverLast(1008).setCreativeTab(IMSM.Remover);
	public static Block BlockBigWorld = new BlockBigWorld(200).setCreativeTab(IMSM.Other);

	public static Block RandomAirballoon2 = new RandomAirballoon2(200).setCreativeTab(IMSM.Structures);
	public static Block RandomEntrance = new RandomEntrance(201).setCreativeTab(IMSM.Other);
	public static Block RandomFlyingShip = new RandomFlyingShip(202).setCreativeTab(IMSM.Structures);
	public static Block RandomGreenTent = new RandomGreenTent(203).setCreativeTab(IMSM.Structures);
	public static Block RandomGreyTent = new RandomGreyTent(204).setCreativeTab(IMSM.Structures);
	public static Block RandomLightHouse = new RandomLightHouse(205).setCreativeTab(IMSM.Structures);
	public static Block RandomMinerTent = new RandomMinerTent(206).setCreativeTab(IMSM.Other);
	public static Block RandomNetherEntranceSurvival = new RandomNetherEntranceSurvival(207).setCreativeTab(IMSM.Other);
	public static Block RandomRandomBrickHouse = new RandomRandomBrickHouse(208).setCreativeTab(IMSM.Other);
	public static Block RandomSurvivalHouse1 = new RandomSurvivalHouse1(209).setCreativeTab(IMSM.Other);
	public static Block RandomSurvivalHouseSandstone = new RandomSurvivalHouseSandstone(210).setCreativeTab(IMSM.Other);
	public static Block RandomTentCamp = new RandomTentCamp(211).setCreativeTab(IMSM.Structures);
	public static Block RandomWoodenHouse = new RandomWoodenHouse(212).setCreativeTab(IMSM.Other);
	public static Block BlockCloud = new BlockCloud(200).setCreativeTab(IMSM.Other);

	public static Block RandomBuildingComplex = new RandomBuildingComplex(200).setCreativeTab(IMSM.Other);
	public static Block RandomImmense_Buildingcomplex = new RandomImmense_Buildingcomplex(201).setCreativeTab(IMSM.Other);
	public static Block RandomImmense_greenroof = new RandomImmense_greenroof(202).setCreativeTab(IMSM.Other);
	public static Block RandomImmense_White_House = new RandomImmense_White_House(203).setCreativeTab(IMSM.Other);
	public static Block RandomImmense_WorkingBuilding = new RandomImmense_WorkingBuilding(204).setCreativeTab(IMSM.Other);
	public static Block RandomLittlePalace = new RandomLittlePalace(205).setCreativeTab(IMSM.Other);
	public static Block RandomLittleWoodenCabin = new RandomLittleWoodenCabin(206).setCreativeTab(IMSM.Structures);
	public static Block RandomSandstoneBuilding = new RandomSandstoneBuilding(207).setCreativeTab(IMSM.Other);
	public static Block RandomSandStoneChurch = new RandomSandStoneChurch(208).setCreativeTab(IMSM.Other);
	public static Block RandomSandstonewithFarm = new RandomSandstonewithFarm(209).setCreativeTab(IMSM.Other);
	public static Block RandomSimpleSandstone = new RandomSimpleSandstone(210).setCreativeTab(IMSM.Other);
	public static Block RandomSpawnHouseProd = new RandomSpawnHouseProd(211).setCreativeTab(IMSM.Other);
	public static Block RandomWoodenStonebrickHouse = new RandomWoodenStonebrickHouse(212).setCreativeTab(IMSM.Structures);
	
	public static Block Live_Power_Windmill_East = new Live_Power_Windmill_East(200).setCreativeTab(IMSM.LiveStructures);
	public static Block LiveAirBalloon = new LiveAirBalloon(200).setCreativeTab(IMSM.LiveStructures);
	public static Block LiveAirplane = new LiveAirplane(201).setCreativeTab(IMSM.LiveStructures);
	public static Block LiveBoat = new LiveBoat(202).setCreativeTab(IMSM.LiveStructures);
	public static Block LiveFlyingShip2 = new LiveFlyingShip2(203).setCreativeTab(IMSM.LiveStructures);
	public static Block LiveFlyingShip = new LiveFlyingShip(204).setCreativeTab(IMSM.LiveStructures);
	public static Block LivePlane = new LivePlane().setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Helicopter = new Live_Helicopter(200).setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Fair_FreeFall = new Live_Fair_FreeFall(201).setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Mill = new Live_Mill(202).setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Cinema = new Live_Cinema(200).setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Flying_Helicopter = new Live_Flying_Helicopter(201).setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Bus = new Live_Bus(200).setCreativeTab(IMSM.LiveStructures);
	public static Block Live_Bus2 = new Live_Bus2(201).setCreativeTab(IMSM.LiveStructures);
	public static Block BlockFerrisWheel = new BlockFerrisWheel(200).setCreativeTab(IMSM.LiveStructures);
	
	public static Block ChristmasHouse = new ChristmasHouse(200).setCreativeTab(IMSM.Other);
	public static Block ChristmasHouse2 = new ChristmasHouse2(201).setCreativeTab(IMSM.Other);
	public static Block ChristmasHouse3 = new ChristmasHouse3(202).setCreativeTab(IMSM.Other);
	public static Block ChristmasSleigh = new ChristmasSleigh(203).setCreativeTab(IMSM.Other);
	public static Block ChristmasSleigh2 = new ChristmasSleigh2(204).setCreativeTab(IMSM.Other);
	public static Block ChristmasSnowman = new ChristmasSnowman(205).setCreativeTab(IMSM.Other);
	public static Block ChristmasTree = new ChristmasTree(206).setCreativeTab(IMSM.Other);
	public static Block ChristmasMarket = new ChristmasMarket(207).setCreativeTab(IMSM.Other);
	public static Block Live_WaterMill = new Live_WaterMill(200).setCreativeTab(IMSM.LiveStructures);

	public static Block BlockUnlimited = new BlockUnlimited(200).setCreativeTab(IMSM.User);

	public static ArrayList<Block> userBlocks = registerUserBlocks();
	
	public static int dialoge = 0;

	public static LiveStructure currentInput;


	public void reg(Block block) {
	    Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(Item.getItemFromBlock(block), new ModelResourceLocation(modid + ":" + block.getNameTextComponent().getUnformattedComponentText().substring(5), "inventory"));
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
		       blocks.add(new BlockUserStructure(line).setCreativeTab(IMSM.User));
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

@SubscribeEvent
	public void init(FMLClientSetupEvent event){
	eventHandler=new modid.imsm.core.EventHandler();
	MinecraftForge.EVENT_BUS.register(eventHandler);
	MinecraftForge.EVENT_BUS.register(new modid.imsm.core.ForgeEventHandler());
	regAll();
	/*
		regAll();
		System.out.println("IMSM has rendered sooooo many blocks");
*/
	
	}
	   
@SubscribeEvent
public void registerBlocks(RegistryEvent.Register<Block> event) {
		//TODO: Thing commented without reason?
		//LanguageRegistry.instance().addStringLocalization("itemGroup.Structures", "en_US", "Structures");
		/**
		GameRegistry.addRecipe(new ItemStack(House, 1),
				"OOO", "OSO", "OOO", 'S', House, 'O', Items.leather);

			*/

event.getRegistry().register(LiveStructureRemover);
		


event.getRegistry().register(LiveStructureRemover);

event.getRegistry().register(DecorationGrassNorthEastSouthWest);
event.getRegistry().register(DecorationParkEast);
event.getRegistry().register(DecorationParkingGarageEast);
event.getRegistry().register(DecorationParkingGarageNorth);
event.getRegistry().register(DecorationParkingGarageSouth);
event.getRegistry().register(DecorationParkingGarageWest);
event.getRegistry().register(DecorationParkingLotsEast);
event.getRegistry().register(DecorationParkingLotsNorth);
event.getRegistry().register(DecorationParkingLotsSouth);
event.getRegistry().register(DecorationParkingLotsWest);
event.getRegistry().register(DecorationParkNorth);
event.getRegistry().register(DecorationParkSouth);
event.getRegistry().register(DecorationParkWest);
event.getRegistry().register(DecorationPlazaFountainNorthEastSouthWest);
event.getRegistry().register(DecorationPlazaNorthEastSouthWest);
event.getRegistry().register(DecorationSoccerStadiumEastWest);
event.getRegistry().register(DecorationSoccerStadiumNorthSouth);
event.getRegistry().register(DecorationSquareNorthEastSouthWest);
event.getRegistry().register(DecorationSquareTreeEast);
event.getRegistry().register(DecorationSquareTreeNorth);
event.getRegistry().register(DecorationSquareTreeSouth);
event.getRegistry().register(DecorationSquareTreeWest);
event.getRegistry().register(FoodCarrotsEastWest);
event.getRegistry().register(FoodCarrotsNorthSouth);
event.getRegistry().register(FoodFarmEast);
event.getRegistry().register(FoodFarmNorth);
event.getRegistry().register(FoodFarmSouth);
event.getRegistry().register(FoodFarmWest);
event.getRegistry().register(FoodPotatoesNorthEastSouthWest);
event.getRegistry().register(FoodStableEastWest);
event.getRegistry().register(FoodStableNorthSouth);
event.getRegistry().register(FoodWheatNorthEastSouthWest);
event.getRegistry().register(IndustryHigh_DensityBlueEast);
event.getRegistry().register(IndustryHigh_DensityBlueNorth);
event.getRegistry().register(IndustryHigh_DensityBlueSouth);
event.getRegistry().register(IndustryHigh_DensityBlueWest);
event.getRegistry().register(IndustryHigh_DensityBrickEast);
event.getRegistry().register(IndustryHigh_DensityBrickNorth);
event.getRegistry().register(IndustryHigh_DensityBrickSouth);
event.getRegistry().register(IndustryHigh_DensityBrickWest);
event.getRegistry().register(IndustryHigh_DensityChimneyEast);
event.getRegistry().register(IndustryHigh_DensityChimneyNorth);
event.getRegistry().register(IndustryHigh_DensityChimneySouth);
event.getRegistry().register(IndustryHigh_DensityChimneyWest);
event.getRegistry().register(IndustryHigh_DensityComputerChipEast);
event.getRegistry().register(IndustryHigh_DensityComputerChipNorth);
event.getRegistry().register(IndustryHigh_DensityComputerChipSouth);
event.getRegistry().register(IndustryHigh_DensityComputerChipWest);
event.getRegistry().register(IndustryHigh_DensityGreenEast);
event.getRegistry().register(IndustryHigh_DensityGreenNorth);
event.getRegistry().register(IndustryHigh_DensityGreenSouth);
event.getRegistry().register(IndustryHigh_DensityGreenWest);
event.getRegistry().register(IndustryHigh_DensityLightBlueEast);
event.getRegistry().register(IndustryHigh_DensityLightBlueNorth);
event.getRegistry().register(IndustryHigh_DensityLightBlueSouth);
event.getRegistry().register(IndustryHigh_DensityLightBlueWest);
event.getRegistry().register(IndustryLow_Density3DPrintingEast);
event.getRegistry().register(IndustryLow_Density3DPrintingNorth);
event.getRegistry().register(IndustryLow_Density3DPrintingSouth);
event.getRegistry().register(IndustryLow_Density3DPrintingWest);
event.getRegistry().register(IndustryLow_DensityBlueEast);
event.getRegistry().register(IndustryLow_DensityBlueNorth);
event.getRegistry().register(IndustryLow_DensityBlueSouth);
event.getRegistry().register(IndustryLow_DensityBlueWest);
event.getRegistry().register(IndustryLow_DensityBrickEast);
event.getRegistry().register(IndustryLow_DensityBrickEastWest);
event.getRegistry().register(IndustryLow_DensityBrickNorth);
event.getRegistry().register(IndustryLow_DensityBrickNorthSouth);
event.getRegistry().register(IndustryLow_DensityBrickSouth);
event.getRegistry().register(IndustryLow_DensityBrickWest);
event.getRegistry().register(IndustryLow_DensityBrownEast2);
event.getRegistry().register(IndustryLow_DensityBrownEast);
event.getRegistry().register(IndustryLow_DensityBrownNorth2);
event.getRegistry().register(IndustryLow_DensityBrownNorth);
event.getRegistry().register(IndustryLow_DensityBrownSouth2);
event.getRegistry().register(IndustryLow_DensityBrownSouth);
event.getRegistry().register(IndustryLow_DensityBrownWest2);
event.getRegistry().register(IndustryLow_DensityBrownWest);
event.getRegistry().register(IndustryLow_DensityChimneyEast);
event.getRegistry().register(IndustryLow_DensityChimneyNorth);
event.getRegistry().register(IndustryLow_DensityChimneySouth);
event.getRegistry().register(IndustryLow_DensityChimneyWest);
event.getRegistry().register(IndustryLow_DensityGreenEast);
event.getRegistry().register(IndustryLow_DensityGreenNorth);
event.getRegistry().register(IndustryLow_DensityGreenSouth);
event.getRegistry().register(IndustryLow_DensityGreenWest);
event.getRegistry().register(IndustryLow_DensityIronEast);
event.getRegistry().register(IndustryLow_DensityIronNorth);
event.getRegistry().register(IndustryLow_DensityIronSouth);
event.getRegistry().register(IndustryLow_DensityIronWest);
event.getRegistry().register(IndustryLow_DensityParabolicAntennaEast);
event.getRegistry().register(IndustryLow_DensityParabolicAntennaNorth);
event.getRegistry().register(IndustryLow_DensityParabolicAntennaSouth);
event.getRegistry().register(IndustryLow_DensityParabolicAntennaWest);
event.getRegistry().register(IndustryLow_DensityTankNorthEastSouthWest);
event.getRegistry().register(IndustryLow_DensityTelescopeEast);
event.getRegistry().register(IndustryLow_DensityTelescopeNorth);
event.getRegistry().register(IndustryLow_DensityTelescopeSouth);
event.getRegistry().register(IndustryLow_DensityTelescopeWest);
event.getRegistry().register(IndustryMedium_DensityBlueEast);
event.getRegistry().register(IndustryMedium_DensityBlueNorth);
event.getRegistry().register(IndustryMedium_DensityBlueSouth);
event.getRegistry().register(IndustryMedium_DensityBlueWest);
event.getRegistry().register(IndustryMedium_DensityBrickEast);
event.getRegistry().register(IndustryMedium_DensityBrickNorth);
event.getRegistry().register(IndustryMedium_DensityBrickSouth);
event.getRegistry().register(IndustryMedium_DensityBrickWest);
event.getRegistry().register(IndustryMedium_DensityBrownEast);
event.getRegistry().register(IndustryMedium_DensityBrownNorth);
event.getRegistry().register(IndustryMedium_DensityBrownSouth);
event.getRegistry().register(IndustryMedium_DensityBrownWest);
event.getRegistry().register(IndustryMedium_DensityChemicalPressEastWest);
event.getRegistry().register(IndustryMedium_DensityChemicalPressNorthSouth);
event.getRegistry().register(IndustryMedium_DensityChimneyEast);
event.getRegistry().register(IndustryMedium_DensityChimneyNorth);
event.getRegistry().register(IndustryMedium_DensityChimneySouth);
event.getRegistry().register(IndustryMedium_DensityChimneyWest);
event.getRegistry().register(IndustryMedium_DensityGreenEast);
event.getRegistry().register(IndustryMedium_DensityGreenNorth);
event.getRegistry().register(IndustryMedium_DensityGreenSouth);
event.getRegistry().register(IndustryMedium_DensityGreenWest);
event.getRegistry().register(IndustryMedium_DensityIceEast);
event.getRegistry().register(IndustryMedium_DensityIceNorth);
event.getRegistry().register(IndustryMedium_DensityIceSouth);
event.getRegistry().register(IndustryMedium_DensityIceWest);
event.getRegistry().register(IndustryMedium_DensitySandstoneEast);
event.getRegistry().register(IndustryMedium_DensitySandstoneNorth);
event.getRegistry().register(IndustryMedium_DensitySandstoneSouth);
event.getRegistry().register(IndustryMedium_DensitySandstoneWest);
event.getRegistry().register(IndustryMedium_DensityTankEast);
event.getRegistry().register(IndustryMedium_DensityTankNorth);
event.getRegistry().register(IndustryMedium_DensityTankSouth);
event.getRegistry().register(IndustryMedium_DensityTankWest);
event.getRegistry().register(OfficeHigh_DensityBrickEastWest);
event.getRegistry().register(OfficeHigh_DensityBrickNorthSouth);
event.getRegistry().register(OfficeHigh_DensityCyanEast);
event.getRegistry().register(OfficeHigh_DensityCyanNorth);
event.getRegistry().register(OfficeHigh_DensityCyanSouth);
event.getRegistry().register(OfficeHigh_DensityCyanWest);
event.getRegistry().register(OfficeHigh_DensityHoleOnTopEast);
event.getRegistry().register(OfficeHigh_DensityHoleOnTopNorth);
event.getRegistry().register(OfficeHigh_DensityHoleOnTopSouth);
event.getRegistry().register(OfficeHigh_DensityHoleOnTopWest);
event.getRegistry().register(OfficeHigh_DensityLightBlueEastWest);
event.getRegistry().register(OfficeHigh_DensityLightBlueNorthSouth);
event.getRegistry().register(OfficeHigh_DensitySpirolBuildingEast);
event.getRegistry().register(OfficeHigh_DensitySpirolBuildingNorth);
event.getRegistry().register(OfficeHigh_DensitySpirolBuildingSouth);
event.getRegistry().register(OfficeHigh_DensitySpirolBuildingWest);
event.getRegistry().register(OfficeLow_DensityBlueEast);
event.getRegistry().register(OfficeLow_DensityBlueNorth);
event.getRegistry().register(OfficeLow_DensityBlueSouth);
event.getRegistry().register(OfficeLow_DensityBlueWest);
event.getRegistry().register(OfficeLow_DensityGreenEast);
event.getRegistry().register(OfficeLow_DensityGreenNorth);
event.getRegistry().register(OfficeLow_DensityGreenSouth);
event.getRegistry().register(OfficeLow_DensityGreenWest);
event.getRegistry().register(OfficeLow_DensityWhiteEast);
event.getRegistry().register(OfficeLow_DensityWhiteNorth);
event.getRegistry().register(OfficeLow_DensityWhiteSouth);
event.getRegistry().register(OfficeLow_DensityWhiteWest);
event.getRegistry().register(OfficeLow_DensityYellowEast);
event.getRegistry().register(OfficeLow_DensityYellowNorth);
event.getRegistry().register(OfficeLow_DensityYellowSouth);
event.getRegistry().register(OfficeLow_DensityYellowWest);
event.getRegistry().register(OfficeMedium_DensityCyanEast);
event.getRegistry().register(OfficeMedium_DensityCyanNorth);
event.getRegistry().register(OfficeMedium_DensityCyanSouth);
event.getRegistry().register(OfficeMedium_DensityCyanWest);
event.getRegistry().register(OfficeMedium_DensityLightBlueEast);
event.getRegistry().register(OfficeMedium_DensityLightBlueNorth);
event.getRegistry().register(OfficeMedium_DensityLightBlueSouth);
event.getRegistry().register(OfficeMedium_DensityLightBlueWest);
event.getRegistry().register(OfficeMedium_DensityPinkEast);
event.getRegistry().register(OfficeMedium_DensityPinkNorth);
event.getRegistry().register(OfficeMedium_DensityPinkSouth);
event.getRegistry().register(OfficeMedium_DensityPinkWest);
event.getRegistry().register(OfficeMedium_DensitySandstoneEast);
event.getRegistry().register(OfficeMedium_DensitySandstoneNorth);
event.getRegistry().register(OfficeMedium_DensitySandstoneSouth);
event.getRegistry().register(OfficeMedium_DensitySandstoneWest);
event.getRegistry().register(PublicFireServiceBigEast);
event.getRegistry().register(PublicFireServiceBigNorth);
event.getRegistry().register(PublicFireServiceBigSouth);
event.getRegistry().register(PublicFireServiceBigWest);
event.getRegistry().register(PublicFireServiceSmallEast);
event.getRegistry().register(PublicFireServiceSmallNorth);
event.getRegistry().register(PublicFireServiceSmallSouth);
event.getRegistry().register(PublicFireServiceSmallWest);
event.getRegistry().register(PublicHospitalBigEast);
event.getRegistry().register(PublicHospitalBigNorth);
event.getRegistry().register(PublicHospitalBigSouth);
event.getRegistry().register(PublicHospitalBigWest);
event.getRegistry().register(PublicHospitalSmallEast);
event.getRegistry().register(PublicHospitalSmallNorth);
event.getRegistry().register(PublicHospitalSmallSouth);
event.getRegistry().register(PublicHospitalSmallWest);
event.getRegistry().register(PublicLibraryEastWest);
event.getRegistry().register(PublicLibraryNorthSouth);
event.getRegistry().register(PublicPoliceBigEast);
event.getRegistry().register(PublicPoliceBigNorth);
event.getRegistry().register(PublicPoliceBigSouth);
event.getRegistry().register(PublicPoliceBigWest);
event.getRegistry().register(PublicPoliceSmallEast);
event.getRegistry().register(PublicPoliceSmallNorth);
event.getRegistry().register(PublicPoliceSmallSouth);
event.getRegistry().register(PublicPoliceSmallWest);
event.getRegistry().register(PublicSchoolBigNorthEast);
event.getRegistry().register(PublicSchoolBigNorthWest);
event.getRegistry().register(PublicSchoolBigSouthEast);
event.getRegistry().register(PublicSchoolBigSouthWest);
event.getRegistry().register(PublicSchoolSmallNorthEast);
event.getRegistry().register(PublicSchoolSmallNorthWest);
event.getRegistry().register(PublicSchoolSmallSouthEast);
event.getRegistry().register(PublicSchoolSmallSouthWest);
event.getRegistry().register(PublicTownhallBigEastWest);
event.getRegistry().register(PublicTownhallBigNorthSouth);
event.getRegistry().register(PublicTownhallSmallEast);
event.getRegistry().register(PublicTownhallSmallNorth);
event.getRegistry().register(PublicTownhallSmallSouth);
event.getRegistry().register(PublicTownhallSmallWest);
event.getRegistry().register(PublicUniversityEast);
event.getRegistry().register(PublicUniversityNorth);
event.getRegistry().register(PublicUniversitySouth);
event.getRegistry().register(PublicUniversityWest);
event.getRegistry().register(ResidentalEnormous_DensityBlockNorthEastSouthWest);
event.getRegistry().register(ResidentalEnormous_DensityBrickBigEast);
event.getRegistry().register(ResidentalEnormous_DensityBrickBigNorth);
event.getRegistry().register(ResidentalEnormous_DensityBrickBigSouth);
event.getRegistry().register(ResidentalEnormous_DensityBrickBigWest);
event.getRegistry().register(ResidentalEnormous_DensityBrickSmallNorthEastSouthWest);
event.getRegistry().register(ResidentalEnormous_DensityGreyEast);
event.getRegistry().register(ResidentalEnormous_DensityGreyNorth);
event.getRegistry().register(ResidentalEnormous_DensityGreySouth);
event.getRegistry().register(ResidentalEnormous_DensityGreyWest);
event.getRegistry().register(ResidentalEnormous_DensityModernEast);
event.getRegistry().register(ResidentalEnormous_DensityModernNorth);
event.getRegistry().register(ResidentalEnormous_DensityModernSouth);
event.getRegistry().register(ResidentalEnormous_DensityModernWest);
event.getRegistry().register(ResidentalEnormous_DensityRedEastWest);
event.getRegistry().register(ResidentalEnormous_DensityRedNorthSouth);
event.getRegistry().register(ResidentalEnormous_DensityRoundNorthEastSouthWest);
event.getRegistry().register(ResidentalEnormous_DensityStoneEast2);
event.getRegistry().register(ResidentalEnormous_DensityStoneEast);
event.getRegistry().register(ResidentalEnormous_DensityStoneNorth2);
event.getRegistry().register(ResidentalEnormous_DensityStoneNorth);
event.getRegistry().register(ResidentalEnormous_DensityStoneSouth2);
event.getRegistry().register(ResidentalEnormous_DensityStoneSouth);
event.getRegistry().register(ResidentalEnormous_DensityStoneWest2);
event.getRegistry().register(ResidentalEnormous_DensityStoneWest);
event.getRegistry().register(ResidentalEnormous_DensityYellowNorthEastSouthWest);
event.getRegistry().register(ResidentalHigh_DensityBlueEast);
event.getRegistry().register(ResidentalHigh_DensityBlueEastWest);
event.getRegistry().register(ResidentalHigh_DensityBlueNorth);
event.getRegistry().register(ResidentalHigh_DensityBlueNorthSouth);
event.getRegistry().register(ResidentalHigh_DensityBlueSouth);
event.getRegistry().register(ResidentalHigh_DensityBlueWest);
event.getRegistry().register(ResidentalHigh_DensityBrickEast);
event.getRegistry().register(ResidentalHigh_DensityBrickEastWest);
event.getRegistry().register(ResidentalHigh_DensityBrickNorth);
event.getRegistry().register(ResidentalHigh_DensityBrickNorthSouth);
event.getRegistry().register(ResidentalHigh_DensityBrickSouth);
event.getRegistry().register(ResidentalHigh_DensityBrickWest);
event.getRegistry().register(ResidentalHigh_DensityGreenGreyEast);
event.getRegistry().register(ResidentalHigh_DensityGreenGreyNorth);
event.getRegistry().register(ResidentalHigh_DensityGreenGreySouth);
event.getRegistry().register(ResidentalHigh_DensityGreenGreyWest);
event.getRegistry().register(ResidentalHigh_DensityRedCornerNorthEast);
event.getRegistry().register(ResidentalHigh_DensityRedCornerNorthWest);
event.getRegistry().register(ResidentalHigh_DensityRedCornerSouthEast);
event.getRegistry().register(ResidentalHigh_DensityRedCornerSouthWest);
event.getRegistry().register(ResidentalHigh_DensityRedYellowEast);
event.getRegistry().register(ResidentalHigh_DensityRedYellowNorth);
event.getRegistry().register(ResidentalHigh_DensityRedYellowSouth);
event.getRegistry().register(ResidentalHigh_DensityRedYellowWest);
event.getRegistry().register(ResidentalHigh_DensityStoneEast2);
event.getRegistry().register(ResidentalHigh_DensityStoneEast);
event.getRegistry().register(ResidentalHigh_DensityStoneNorth2);
event.getRegistry().register(ResidentalHigh_DensityStoneNorth);
event.getRegistry().register(ResidentalHigh_DensityStoneSouth2);
event.getRegistry().register(ResidentalHigh_DensityStoneSouth);
event.getRegistry().register(ResidentalHigh_DensityStoneWest2);
event.getRegistry().register(ResidentalHigh_DensityStoneWest);
event.getRegistry().register(ResidentalHigh_DensityYellowEast);
event.getRegistry().register(ResidentalHigh_DensityYellowNorth);
event.getRegistry().register(ResidentalHigh_DensityYellowSouth);
event.getRegistry().register(ResidentalHigh_DensityYellowWest);
event.getRegistry().register(ResidentalLow_DensityBeigeEast);
event.getRegistry().register(ResidentalLow_DensityBeigeNorth);
event.getRegistry().register(ResidentalLow_DensityBeigeSouth);
event.getRegistry().register(ResidentalLow_DensityBeigeWest);
event.getRegistry().register(ResidentalLow_DensityCyanEast);
event.getRegistry().register(ResidentalLow_DensityCyanNorth);
event.getRegistry().register(ResidentalLow_DensityCyanSouth);
event.getRegistry().register(ResidentalLow_DensityCyanWest);
event.getRegistry().register(ResidentalLow_DensityGreenEast2);
event.getRegistry().register(ResidentalLow_DensityGreenEast);
event.getRegistry().register(ResidentalLow_DensityGreenNorth2);
event.getRegistry().register(ResidentalLow_DensityGreenNorth);
event.getRegistry().register(ResidentalLow_DensityGreenSouth2);
event.getRegistry().register(ResidentalLow_DensityGreenSouth);
event.getRegistry().register(ResidentalLow_DensityGreenWest2);
event.getRegistry().register(ResidentalLow_DensityGreenWest);
event.getRegistry().register(ResidentalLow_DensityLightBlueEast2);
event.getRegistry().register(ResidentalLow_DensityLightBlueEast);
event.getRegistry().register(ResidentalLow_DensityLightBlueNorth2);
event.getRegistry().register(ResidentalLow_DensityLightBlueNorth);
event.getRegistry().register(ResidentalLow_DensityLightBlueSouth2);
event.getRegistry().register(ResidentalLow_DensityLightBlueSouth);
event.getRegistry().register(ResidentalLow_DensityLightBlueWest2);
event.getRegistry().register(ResidentalLow_DensityLightBlueWest);
event.getRegistry().register(ResidentalLow_DensityLightGreyEast);
event.getRegistry().register(ResidentalLow_DensityLightGreyNorth);
event.getRegistry().register(ResidentalLow_DensityLightGreySouth);
event.getRegistry().register(ResidentalLow_DensityLightGreyWest);
event.getRegistry().register(ResidentalLow_DensityModernEast);
event.getRegistry().register(ResidentalLow_DensityModernNorth);
event.getRegistry().register(ResidentalLow_DensityModernSouth);
event.getRegistry().register(ResidentalLow_DensityModernWest);
event.getRegistry().register(ResidentalLow_DensityOrangeEast);
event.getRegistry().register(ResidentalLow_DensityOrangeNorth);
event.getRegistry().register(ResidentalLow_DensityOrangeSouth);
event.getRegistry().register(ResidentalLow_DensityOrangeWest);
event.getRegistry().register(ResidentalLow_DensityRedEast);
event.getRegistry().register(ResidentalLow_DensityRedNorth);
event.getRegistry().register(ResidentalLow_DensityRedSouth);
event.getRegistry().register(ResidentalLow_DensityRedWest);
event.getRegistry().register(ResidentalLow_DensityStoneEast);
event.getRegistry().register(ResidentalLow_DensityStoneNorth);
event.getRegistry().register(ResidentalLow_DensityStoneSouth);
event.getRegistry().register(ResidentalLow_DensityStoneWest);
event.getRegistry().register(ResidentalLow_DensityWhiteEast);
event.getRegistry().register(ResidentalLow_DensityWhiteNorth);
event.getRegistry().register(ResidentalLow_DensityWhiteSouth);
event.getRegistry().register(ResidentalLow_DensityWhiteWest);
event.getRegistry().register(ResidentalLow_DensityWoodEast);
event.getRegistry().register(ResidentalLow_DensityWoodNorth);
event.getRegistry().register(ResidentalLow_DensityWoodSouth);
event.getRegistry().register(ResidentalLow_DensityWoodWest);
event.getRegistry().register(ResidentalLow_DensityYellowEast2);
event.getRegistry().register(ResidentalLow_DensityYellowEast);
event.getRegistry().register(ResidentalLow_DensityYellowNorth2);
event.getRegistry().register(ResidentalLow_DensityYellowNorth);
event.getRegistry().register(ResidentalLow_DensityYellowSouth2);
event.getRegistry().register(ResidentalLow_DensityYellowSouth);
event.getRegistry().register(ResidentalLow_DensityYellowWest2);
event.getRegistry().register(ResidentalLow_DensityYellowWest);
event.getRegistry().register(ResidentalMedium_DensityBlueGreenEast);
event.getRegistry().register(ResidentalMedium_DensityBlueGreenNorth);
event.getRegistry().register(ResidentalMedium_DensityBlueGreenSouth);
event.getRegistry().register(ResidentalMedium_DensityBlueGreenWest);
event.getRegistry().register(ResidentalMedium_DensityBlueRedEast);
event.getRegistry().register(ResidentalMedium_DensityBlueRedNorth);
event.getRegistry().register(ResidentalMedium_DensityBlueRedSouth);
event.getRegistry().register(ResidentalMedium_DensityBlueRedWest);
event.getRegistry().register(ResidentalMedium_DensityBrickEast);
event.getRegistry().register(ResidentalMedium_DensityBrickNorth);
event.getRegistry().register(ResidentalMedium_DensityBrickSouth);
event.getRegistry().register(ResidentalMedium_DensityBrickWest);
event.getRegistry().register(ResidentalMedium_DensityHorizontalEast);
event.getRegistry().register(ResidentalMedium_DensityHorizontalNorth);
event.getRegistry().register(ResidentalMedium_DensityHorizontalSouth);
event.getRegistry().register(ResidentalMedium_DensityHorizontalWest);
event.getRegistry().register(ResidentalMedium_DensityOrangeGreenEast);
event.getRegistry().register(ResidentalMedium_DensityOrangeGreenNorth);
event.getRegistry().register(ResidentalMedium_DensityOrangeGreenSouth);
event.getRegistry().register(ResidentalMedium_DensityOrangeGreenWest);
event.getRegistry().register(ResidentalMedium_DensityQuartzEast);
event.getRegistry().register(ResidentalMedium_DensityQuartzNorth);
event.getRegistry().register(ResidentalMedium_DensityQuartzSouth);
event.getRegistry().register(ResidentalMedium_DensityQuartzWest);
event.getRegistry().register(ResidentalMedium_DensityRedGreenEast);
event.getRegistry().register(ResidentalMedium_DensityRedGreenNorth);
event.getRegistry().register(ResidentalMedium_DensityRedGreenSouth);
event.getRegistry().register(ResidentalMedium_DensityRedGreenWest);
event.getRegistry().register(ResidentalMedium_DensityRoofEast);
event.getRegistry().register(ResidentalMedium_DensityRoofNorth);
event.getRegistry().register(ResidentalMedium_DensityRoofSouth);
event.getRegistry().register(ResidentalMedium_DensityRoofWest);
event.getRegistry().register(ResidentalMedium_DensityStone1EastWest);
event.getRegistry().register(ResidentalMedium_DensityStone1NorthSouth);
event.getRegistry().register(ResidentalMedium_DensityStone2EastWest);
event.getRegistry().register(ResidentalMedium_DensityStone2NorthSouth);
event.getRegistry().register(ResidentalMedium_DensityStoneCornerNorthEast);
event.getRegistry().register(ResidentalMedium_DensityStoneCornerNorthWest);
event.getRegistry().register(ResidentalMedium_DensityStoneCornerSouthEast);
event.getRegistry().register(ResidentalMedium_DensityStoneCornerSouthWest);
event.getRegistry().register(ResidentalMedium_DensityStoneEast);
event.getRegistry().register(ResidentalMedium_DensityStoneEndNorthEastWest);
event.getRegistry().register(ResidentalMedium_DensityStoneEndNorthSouthEast);
event.getRegistry().register(ResidentalMedium_DensityStoneEndNorthSouthWest);
event.getRegistry().register(ResidentalMedium_DensityStoneEndSouthEastWest);
event.getRegistry().register(ResidentalMedium_DensityStoneNorth);
event.getRegistry().register(ResidentalMedium_DensityStoneSouth);
event.getRegistry().register(ResidentalMedium_DensityStoneWest);
event.getRegistry().register(ResidentalMedium_DensityVerticalEast);
event.getRegistry().register(ResidentalMedium_DensityVerticalNorth);
event.getRegistry().register(ResidentalMedium_DensityVerticalSouth);
event.getRegistry().register(ResidentalMedium_DensityVerticalWest);
event.getRegistry().register(ResidentalMedium_DensityYellowRedEast);
event.getRegistry().register(ResidentalMedium_DensityYellowRedNorth);
event.getRegistry().register(ResidentalMedium_DensityYellowRedSouth);
event.getRegistry().register(ResidentalMedium_DensityYellowRedWest);
event.getRegistry().register(ShoppingHigh_DensityQuartzEastWest);
event.getRegistry().register(ShoppingHigh_DensityQuartzNorthSouth);
event.getRegistry().register(ShoppingLow_DensityBrickEast);
event.getRegistry().register(ShoppingLow_DensityBrickNorth);
event.getRegistry().register(ShoppingLow_DensityBrickSouth);
event.getRegistry().register(ShoppingLow_DensityBrickWest);
event.getRegistry().register(ShoppingLow_DensityGreenEast);
event.getRegistry().register(ShoppingLow_DensityGreenNorth);
event.getRegistry().register(ShoppingLow_DensityGreenSouth);
event.getRegistry().register(ShoppingLow_DensityGreenWest);
event.getRegistry().register(ShoppingLow_DensityOrangeEast);
event.getRegistry().register(ShoppingLow_DensityOrangeNorth);
event.getRegistry().register(ShoppingLow_DensityOrangeSouth);
event.getRegistry().register(ShoppingLow_DensityOrangeWest);
event.getRegistry().register(ShoppingLow_DensityPinkEast);
event.getRegistry().register(ShoppingLow_DensityPinkNorth);
event.getRegistry().register(ShoppingLow_DensityPinkSouth);
event.getRegistry().register(ShoppingLow_DensityPinkWest);
event.getRegistry().register(ShoppingMedium_DensityModernEast);
event.getRegistry().register(ShoppingMedium_DensityModernNorth);
event.getRegistry().register(ShoppingMedium_DensityModernSouth);
event.getRegistry().register(ShoppingMedium_DensityModernWest);
event.getRegistry().register(ShoppingMedium_DensityQuartzEast);
event.getRegistry().register(ShoppingMedium_DensityQuartzNorth);
event.getRegistry().register(ShoppingMedium_DensityQuartzSouth);
event.getRegistry().register(ShoppingMedium_DensityQuartzWest);
event.getRegistry().register(TransportAirportRunway_EastWestBuilding_North);
event.getRegistry().register(TransportAirportRunway_EastWestBuilding_South);
event.getRegistry().register(TransportAirportRunway_NorthSouthBuilding_East);
event.getRegistry().register(TransportAirportRunway_NorthSouthBuilding_West);
event.getRegistry().register(TransportAvenue1EastWest);
event.getRegistry().register(TransportAvenue1NorthSouth);
event.getRegistry().register(TransportAvenue2EastWest);
event.getRegistry().register(TransportAvenue2NorthSouth);
event.getRegistry().register(TransportAvenueEEast);
event.getRegistry().register(TransportAvenueENorth);
event.getRegistry().register(TransportAvenueESouth);
event.getRegistry().register(TransportAvenueEWest);
event.getRegistry().register(TransportAvenueLNorthEast);
event.getRegistry().register(TransportAvenueLNorthWest);
event.getRegistry().register(TransportAvenueLSouthEast);
event.getRegistry().register(TransportAvenueLSouthWest);
event.getRegistry().register(TransportAvenueTNorthEastWest);
event.getRegistry().register(TransportAvenueTNorthSouthEast);
event.getRegistry().register(TransportAvenueTNorthSouthWest);
event.getRegistry().register(TransportAvenueTSouthEastWest);
event.getRegistry().register(TransportAvenueXNorthSouthEastWest);
event.getRegistry().register(TransportBridgeAvenue1EastWest);
event.getRegistry().register(TransportBridgeAvenue1NorthSouth);
event.getRegistry().register(TransportBridgeAvenue2NorthSouth);
event.getRegistry().register(TransportBridgeAvenue2SouthWest);
event.getRegistry().register(TransportBridgeAvenue3EastWest);
event.getRegistry().register(TransportBridgeAvenue3NorthSouth);
event.getRegistry().register(TransportBridgeAvenue4EastWest);
event.getRegistry().register(TransportBridgeAvenue4NorthSouth);
event.getRegistry().register(TransportBridgeAvenueLNorthEast);
event.getRegistry().register(TransportBridgeAvenueLNorthWest);
event.getRegistry().register(TransportBridgeAvenueLSouthEast);
event.getRegistry().register(TransportBridgeAvenueLSouthWest);
event.getRegistry().register(TransportBridgeHighway1EastWest);
event.getRegistry().register(TransportBridgeHighway1NorthSouth);
event.getRegistry().register(TransportBridgeHighway2EastWest);
event.getRegistry().register(TransportBridgeHighway2NorthSouth);
event.getRegistry().register(TransportBridgeHighway3EastWest);
event.getRegistry().register(TransportBridgeHighway3NorthSouth);
event.getRegistry().register(TransportBridgeHighway4EastWest);
event.getRegistry().register(TransportBridgeHighway4NorthSouth);
event.getRegistry().register(TransportBridgeHighwayLNorthEast);
event.getRegistry().register(TransportBridgeHighwayLNorthWest);
event.getRegistry().register(TransportBridgeHighwayLSouthEast);
event.getRegistry().register(TransportBridgeHighwayLSouthWest);
event.getRegistry().register(TransportBridgeRoad1EastWest);
event.getRegistry().register(TransportBridgeRoad1NorthSouth);
event.getRegistry().register(TransportBridgeRoad2EastWest);
event.getRegistry().register(TransportBridgeRoad2NorthSouth);
event.getRegistry().register(TransportBridgeRoadLNorthEast);
event.getRegistry().register(TransportBridgeRoadLNorthWest);
event.getRegistry().register(TransportBridgeRoadLSouthEast);
event.getRegistry().register(TransportBridgeRoadLSouthWest);
event.getRegistry().register(TransportBridgeStreet1EastWest);
event.getRegistry().register(TransportBridgeStreet1NorthSouth);
event.getRegistry().register(TransportBridgeStreet2EastWest);
event.getRegistry().register(TransportBridgeStreet2NorthSouth);
event.getRegistry().register(TransportBridgeStreetLNorthEast);
event.getRegistry().register(TransportBridgeStreetLNorthWest);
event.getRegistry().register(TransportBridgeStreetLSouthEast);
event.getRegistry().register(TransportBridgeStreetLSouthWest);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_EastStreet_NorthWestside);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_EastStreet_SouthWestside);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthEastside);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_NorthStreet_SouthWestside);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthEastside);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_SouthStreet_NorthWestside);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_WestStreet_NorthEastside);
event.getRegistry().register(TransportConnectorAvenue_Street1Avenue_WestStreet_SouthEastside);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_EastStreet_North);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_EastStreet_South);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_NorthStreet_East);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_NorthStreet_West);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_SouthStreet_East);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_SouthStreet_West);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_WestStreet_North);
event.getRegistry().register(TransportConnectorAvenue_StreetLAvenue_WestStreet_South);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_EastStreet_NorthSouth);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_EastWestStreet_North);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_EastWestStreet_South);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_East);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_NorthSouthStreet_West);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_NorthStreet_EastWest);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_SouthStreet_EastWest);
event.getRegistry().register(TransportConnectorAvenue_StreetTAvenue_WestStreet_NorthSouth);
event.getRegistry().register(TransportConnectorAvenue_StreetXAvenue_EastWestStreet_NorthSouth);
event.getRegistry().register(TransportConnectorAvenue_StreetXAvenue_NorthSouthStreet_EastWest);
event.getRegistry().register(TransportConnectorBridge_AvenueBridge_EastAvenue_West);
event.getRegistry().register(TransportConnectorBridge_AvenueBridge_NorthAvenue_South);
event.getRegistry().register(TransportConnectorBridge_AvenueBridge_SouthAvenue_North);
event.getRegistry().register(TransportConnectorBridge_AvenueBridge_WestAvenue_East);
event.getRegistry().register(TransportConnectorBridge_RoadBridge_EastRoad_West);
event.getRegistry().register(TransportConnectorBridge_RoadBridge_NorthRoad_South);
event.getRegistry().register(TransportConnectorBridge_RoadBridge_SouthRoad_North);
event.getRegistry().register(TransportConnectorBridge_RoadBridge_WestRoad_East);
event.getRegistry().register(TransportConnectorBridge_StreetBridge_EastStreet_West);
event.getRegistry().register(TransportConnectorBridge_StreetBridge_NorthStreet_South);
event.getRegistry().register(TransportConnectorBridge_StreetBridge_SouthStreet_North);
event.getRegistry().register(TransportConnectorBridge_StreetBridge_WestStreet_East);
event.getRegistry().register(TransportConnectorHighway_AvenueHighway_EastAvenue_West);
event.getRegistry().register(TransportConnectorHighway_AvenueHighway_NorthAvenue_South);
event.getRegistry().register(TransportConnectorHighway_AvenueHighway_SouthAvenue_North);
event.getRegistry().register(TransportConnectorHighway_AvenueHighway_WestAvenue_East);
event.getRegistry().register(TransportConnectorHighwayFloor_AvenueHighwayFloor_EastAvenue_West);
event.getRegistry().register(TransportConnectorHighwayFloor_AvenueHighwayFloor_NorthAvenue_South);
event.getRegistry().register(TransportConnectorHighwayFloor_AvenueHighwayFloor_SouthAvenue_North);
event.getRegistry().register(TransportConnectorHighwayFloor_AvenueHighwayFloor_WestAvenueFloor_East);
event.getRegistry().register(TransportConnectorHighway_HighwayFloorHighway_EastHighwayFloor_West);
event.getRegistry().register(TransportConnectorHighway_HighwayFloorHighway_NorthHighwayFloor_South);
event.getRegistry().register(TransportConnectorHighway_HighwayFloorHighway_SouthHighwayFloor_North);
event.getRegistry().register(TransportConnectorHighway_HighwayFloorHighway_WestHighwayFloor_East);
event.getRegistry().register(TransportHarbourBigEast);
event.getRegistry().register(TransportHarbourBigNorth);
event.getRegistry().register(TransportHarbourBigSouth);
event.getRegistry().register(TransportHarbourBigWest);
event.getRegistry().register(TransportHarbourSide1CornerNorthEast);
event.getRegistry().register(TransportHarbourSide1CornerNorthWest);
event.getRegistry().register(TransportHarbourSide1CornerSouthEast);
event.getRegistry().register(TransportHarbourSide1CornerSouthWest);
event.getRegistry().register(TransportHarbourSide2CornerCraneEast);
event.getRegistry().register(TransportHarbourSide2CornerCraneNorth);
event.getRegistry().register(TransportHarbourSide2CornerCraneSouth);
event.getRegistry().register(TransportHarbourSide2CornerCraneWest);
event.getRegistry().register(TransportHarbourSide2CornerEast);
event.getRegistry().register(TransportHarbourSide2CornerNorth);
event.getRegistry().register(TransportHarbourSide2CornerSouth);
event.getRegistry().register(TransportHarbourSide2CornerWest);
event.getRegistry().register(TransportHarbourSide3CornerNorthEast_NorthWest_SouthEast);
event.getRegistry().register(TransportHarbourSide3CornerNorthEast_NorthWest_SouthWest);
event.getRegistry().register(TransportHarbourSide3CornerNorthEast_SouthEast_SouthWest);
event.getRegistry().register(TransportHarbourSide3CornerNorthWest_SouthEast_SouthWest);
event.getRegistry().register(TransportHarbourSmallEast);
event.getRegistry().register(TransportHarbourSmallNorth);
event.getRegistry().register(TransportHarbourSmallSouth);
event.getRegistry().register(TransportHarbourSmallWest);
event.getRegistry().register(TransportHighway05EastWestNorthside);
event.getRegistry().register(TransportHighway05EastWestSouthside);
event.getRegistry().register(TransportHighway05NorthSouthEastside);
event.getRegistry().register(TransportHighway05NorthSouthWestside);
event.getRegistry().register(TransportHighway1EastWest);
event.getRegistry().register(TransportHighway1NorthSouth);
event.getRegistry().register(TransportHighway2EastWest);
event.getRegistry().register(TransportHighway2NorthSouth);
event.getRegistry().register(TransportHighwayDrivewayEastWestEastside);
event.getRegistry().register(TransportHighwayDrivewayEastWestWestside);
event.getRegistry().register(TransportHighwayDrivewayExitEastWestEastside);
event.getRegistry().register(TransportHighwayDrivewayExitEastWestWestside);
event.getRegistry().register(TransportHighwayDrivewayExitNorthSouthNorthside);
event.getRegistry().register(TransportHighwayDrivewayExitNorthSouthSouthside);
event.getRegistry().register(TransportHighwayDrivewayNorthSouthNorthside);
event.getRegistry().register(TransportHighwayDrivewayNorthSouthSouthside);
event.getRegistry().register(TransportHighwayExitEastWestEastside);
event.getRegistry().register(TransportHighwayExitEastWestWestside);
event.getRegistry().register(TransportHighwayExitNorthSouthNorthside);
event.getRegistry().register(TransportHighwayExitNorthSouthSouthside);
event.getRegistry().register(TransportHighwayFloor05EastWestNorthside);
event.getRegistry().register(TransportHighwayFloor05EastWestSouthside);
event.getRegistry().register(TransportHighwayFloor05NorthSouthEastside);
event.getRegistry().register(TransportHighwayFloor05NorthSouthWestside);
event.getRegistry().register(TransportHighwayFloor1EastWest);
event.getRegistry().register(TransportHighwayFloor1NorthSouth);
event.getRegistry().register(TransportHighwayFloor2EastWest);
event.getRegistry().register(TransportHighwayFloor2NorthSouth);
event.getRegistry().register(TransportHighwayFloorDrivewayEastWestEastside);
event.getRegistry().register(TransportHighwayFloorDrivewayEastWestWestside);
event.getRegistry().register(TransportHighwayFloorDrivewayExitEastWestEastside);
event.getRegistry().register(TransportHighwayFloorDrivewayExitEastWestWestside);
event.getRegistry().register(TransportHighwayFloorDrivewayExitNorthSouthNorthside);
event.getRegistry().register(TransportHighwayFloorDrivewayExitNorthSouthSouthside);
event.getRegistry().register(TransportHighwayFloorDrivewayNorthSouthNorthside);
event.getRegistry().register(TransportHighwayFloorDrivewayNorthSouthSouthside);
event.getRegistry().register(TransportHighwayFloorExitEastWestEast);
event.getRegistry().register(TransportHighwayFloorExitEastWestWestside);
event.getRegistry().register(TransportHighwayFloorExitNorthSouthNorthside);
event.getRegistry().register(TransportHighwayFloorExitNorthSouthSouthside);
event.getRegistry().register(TransportHighwayFloorLNorthEast);
event.getRegistry().register(TransportHighwayFloorLNorthWest);
event.getRegistry().register(TransportHighwayFloorLSouthEast);
event.getRegistry().register(TransportHighwayFloorLSouthWest);
event.getRegistry().register(TransportHighwayFloorTNorthEastWest);
event.getRegistry().register(TransportHighwayFloorTNorthSouthEast);
event.getRegistry().register(TransportHighwayFloorTNorthSouthWest);
event.getRegistry().register(TransportHighwayFloorTSouthEastWest);
event.getRegistry().register(TransportHighwayFloorXNorthEastSouthWest);
event.getRegistry().register(TransportHighwayLNorthEast);
event.getRegistry().register(TransportHighwayLNorthWest);
event.getRegistry().register(TransportHighwayLSouthEast);
event.getRegistry().register(TransportHighwayLSouthWest);
event.getRegistry().register(TransportHighwayTNorthEastWest);
event.getRegistry().register(TransportHighwayTNorthSouthEast);
event.getRegistry().register(TransportHighwayTNorthSouthWest);
event.getRegistry().register(TransportHighwayTSouthEastWest);
event.getRegistry().register(TransportHighwayXNorthEastSouthWest);
event.getRegistry().register(TransportPublicConnectorHightram_TramHightram_EastTram_West);
event.getRegistry().register(TransportPublicConnectorHightram_TramHightram_NorthTram_South);
event.getRegistry().register(TransportPublicConnectorHightram_TramHightram_SouthTram_North);
event.getRegistry().register(TransportPublicConnectorHightram_TramHightram_WestTram_East);
event.getRegistry().register(TransportPublicHightram1EastWest);
event.getRegistry().register(TransportPublicHightram1NorthSouth);
event.getRegistry().register(TransportPublicHightramEEast);
event.getRegistry().register(TransportPublicHightramENorth);
event.getRegistry().register(TransportPublicHightramESouth);
event.getRegistry().register(TransportPublicHightramEWest);
event.getRegistry().register(TransportPublicHightramLNorthEast);
event.getRegistry().register(TransportPublicHightramLNorthWest);
event.getRegistry().register(TransportPublicHightramLSouthEast);
event.getRegistry().register(TransportPublicHightramLSouthWest);
event.getRegistry().register(TransportPublicHightramStationEastWest);
event.getRegistry().register(TransportPublicHightramStationNorthSouth);
event.getRegistry().register(TransportPublicHightramXNorthEastSouthWest);
event.getRegistry().register(TransportPublicTram1EastWest);
event.getRegistry().register(TransportPublicTram1NorthSouth);
event.getRegistry().register(TransportPublicTramEEast);
event.getRegistry().register(TransportPublicTramENorth);
event.getRegistry().register(TransportPublicTramESouth);
event.getRegistry().register(TransportPublicTramEWest);
event.getRegistry().register(TransportPublicTramLNorthEast);
event.getRegistry().register(TransportPublicTramLNorthWest);
event.getRegistry().register(TransportPublicTramLSouthEast);
event.getRegistry().register(TransportPublicTramLSouthWest);
event.getRegistry().register(TransportPublicTram_on_road1EastWest);
event.getRegistry().register(TransportPublicTram_on_road1NorthSouth);
event.getRegistry().register(TransportPublicTram_on_roadEEast);
event.getRegistry().register(TransportPublicTram_on_roadENorth);
event.getRegistry().register(TransportPublicTram_on_roadESouth);
event.getRegistry().register(TransportPublicTram_on_roadEWest);
event.getRegistry().register(TransportPublicTram_on_roadLNorthEast);
event.getRegistry().register(TransportPublicTram_on_roadLNorthWest);
event.getRegistry().register(TransportPublicTram_on_roadLSouthEast);
event.getRegistry().register(TransportPublicTram_on_roadLSouthWest);
event.getRegistry().register(TransportPublicTramStationEastWest);
event.getRegistry().register(TransportPublicTramStationNorthSouth);
event.getRegistry().register(TransportPublicTramXNorthEastSouthWest);
event.getRegistry().register(TransportRoad1EastWest);
event.getRegistry().register(TransportRoad1NorthSouth);
event.getRegistry().register(TransportRoadEEast);
event.getRegistry().register(TransportRoadENorth);
event.getRegistry().register(TransportRoadESouth);
event.getRegistry().register(TransportRoadEWest);
event.getRegistry().register(TransportRoadLNorthEast);
event.getRegistry().register(TransportRoadLNorthWest);
event.getRegistry().register(TransportRoadLSouthEast);
event.getRegistry().register(TransportRoadLSouthWest);
event.getRegistry().register(TransportRoadTNorthEastWest);
event.getRegistry().register(TransportRoadTNorthSouthEast);
event.getRegistry().register(TransportRoadTNorthSouthWest);
event.getRegistry().register(TransportRoadTSouthEastWest);
event.getRegistry().register(TransportRoadXNorthEastSouthWest);
event.getRegistry().register(TransportStreet1EastWest);
event.getRegistry().register(TransportStreet1NorthSouth);
event.getRegistry().register(TransportStreetEEast);
event.getRegistry().register(TransportStreetENorth);
event.getRegistry().register(TransportStreetESouth);
event.getRegistry().register(TransportStreetEWest);
event.getRegistry().register(TransportStreetLNorthEast);
event.getRegistry().register(TransportStreetLNorthWest);
event.getRegistry().register(TransportStreetLSouthEast);
event.getRegistry().register(TransportStreetLSouthWest);
event.getRegistry().register(TransportStreetRoundaboutNorthEastSouthWest);
event.getRegistry().register(TransportStreetTNorthEastWest);
event.getRegistry().register(TransportStreetTNorthSouthEast);
event.getRegistry().register(TransportStreetTNorthSouthWest);
event.getRegistry().register(TransportStreetTSouthEastWest);
event.getRegistry().register(TransportStreetXNorthEastSouthWest);
event.getRegistry().register(TransportWater1CornerNorthEast);
event.getRegistry().register(TransportWater1CornerNorthWest);
event.getRegistry().register(TransportWater1CornerSouthEast);
event.getRegistry().register(TransportWater1CornerSouthWest);
event.getRegistry().register(TransportWater2CornerEast);
event.getRegistry().register(TransportWater2CornerNorth);
event.getRegistry().register(TransportWater2CornerSouth);
event.getRegistry().register(TransportWater2CornerWest);
event.getRegistry().register(TransportWater3CornerNorthEast_NorthWest_SouthEast);
event.getRegistry().register(TransportWater3CornerNorthEast_NorthWest_SouthWest);
event.getRegistry().register(TransportWater3CornerSouthEast_SouthWest_NorthEast);
event.getRegistry().register(TransportWater3CornerSouthEast_SouthWest_NorthWest);
event.getRegistry().register(TransportWater4CornerNorthSouthEastWest);
event.getRegistry().register(UtilityPower_NuclearEast);
event.getRegistry().register(UtilityPower_NuclearNorth);
event.getRegistry().register(UtilityPower_NuclearSouth);
event.getRegistry().register(UtilityPower_NuclearWest);
event.getRegistry().register(UtilityPower_OilCoalEast);
event.getRegistry().register(UtilityPower_OilCoalNorth);
event.getRegistry().register(UtilityPower_OilCoalSouth);
event.getRegistry().register(UtilityPower_OilCoalWest);
event.getRegistry().register(UtilityPower_SunNorthEastSouthWest);
event.getRegistry().register(UtilityPower_WindEast);
event.getRegistry().register(UtilityPower_WindNorth);
event.getRegistry().register(UtilityPower_WindSouth);
event.getRegistry().register(UtilityPower_WindWest);
event.getRegistry().register(UtilityPumpjackEastWest);
event.getRegistry().register(UtilityPumpjackNorthSouth);
event.getRegistry().register(UtilityScrap_BurningEast);
event.getRegistry().register(UtilityScrap_BurningNorth);
event.getRegistry().register(UtilityScrap_BurningSouth);
event.getRegistry().register(UtilityScrap_BurningWest);
event.getRegistry().register(UtilityScrap_HeapEast);
event.getRegistry().register(UtilityScrap_HeapNorth);
event.getRegistry().register(UtilityScrap_HeapSouth);
event.getRegistry().register(UtilityScrap_HeapWest);
event.getRegistry().register(UtilityScrap_RecycleEast);
event.getRegistry().register(UtilityScrap_RecycleNorth);
event.getRegistry().register(UtilityScrap_RecycleSouth);
event.getRegistry().register(UtilityScrap_RecycleWest);
event.getRegistry().register(UtilityWater_PumpEast);
event.getRegistry().register(UtilityWater_PumpNorth);
event.getRegistry().register(UtilityWater_PumpSouth);
event.getRegistry().register(UtilityWater_PumpWest);
event.getRegistry().register(UtilityWater_TowerNorthEastSouthWest);
event.getRegistry().register(UtilityWater_TreatmentEast);
event.getRegistry().register(UtilityWater_TreatmentNorth);
event.getRegistry().register(UtilityWater_TreatmentSouth);
event.getRegistry().register(UtilityWater_TreatmentWest);
event.getRegistry().register(BlockAirBalloon);
event.getRegistry().register(BlockAirplane);
//event.getRegistry().register(BlockApplepie);
event.getRegistry().register(BlockArena1);
event.getRegistry().register(BlockArena2);
event.getRegistry().register(BlockBigPyramid);
event.getRegistry().register(BlockBoat);
event.getRegistry().register(BlockBunker);
//event.getRegistry().register(BlockCactus2);
//event.getRegistry().register(BlockCake2);
event.getRegistry().register(BlockCastleTower);
//event.getRegistry().register(BlockCave);
//event.getRegistry().register(BlockColumn);
event.getRegistry().register(BlockCosyHouse);
event.getRegistry().register(BlockDungeon);
event.getRegistry().register(BlockEnchantmentRoom);
event.getRegistry().register(BlockFarm2);
event.getRegistry().register(BlockFarm3);
event.getRegistry().register(BlockFarm4);
event.getRegistry().register(BlockFarm);
//event.getRegistry().register(BlockFloatingSphere);
event.getRegistry().register(BlockGiantTree);
//event.getRegistry().register(BlockGlassHouse);
event.getRegistry().register(BlockHountedHouse);
//event.getRegistry().register(BlockHouse2);
event.getRegistry().register(BlockHouse);
event.getRegistry().register(BlockHouseTrap1);
event.getRegistry().register(BlockHouseTrap2);
//event.getRegistry().register(BlockLeaves2);
event.getRegistry().register(BlockLighthouse);
event.getRegistry().register(BlockMegaHouse2);
event.getRegistry().register(BlockMegaHouse);
event.getRegistry().register(BlockMegaTower);
//event.getRegistry().register(BlockPenIron);
//event.getRegistry().register(BlockPenNether);
//event.getRegistry().register(BlockPenWood);
event.getRegistry().register(BlockPlane);
event.getRegistry().register(BlockPrison2);
event.getRegistry().register(BlockPrison);
event.getRegistry().register(BlockPyramid);
event.getRegistry().register(BlockRollerCoaster2);
event.getRegistry().register(BlockRollercoaster);
//event.getRegistry().register(BlockShelter);
//event.getRegistry().register(BlockSkyscraper2);
event.getRegistry().register(BlockSkyscraper);
//event.getRegistry().register(BlockStadium2);
event.getRegistry().register(BlockStadium);
//event.getRegistry().register(BlockStandardBrickHouse);
event.getRegistry().register(BlockStoreHouse);
//event.getRegistry().register(BlockStreet);
event.getRegistry().register(BlockTorch2);
event.getRegistry().register(BlockTower);
event.getRegistry().register(BlockWaterSlide);
//event.getRegistry().register(Remover16256);
//event.getRegistry().register(Remover1632);
//event.getRegistry().register(Remover168);
event.getRegistry().register(Remover16);
//event.getRegistry().register(Remover3216);
//event.getRegistry().register(Remover32256);
//event.getRegistry().register(Remover328);
event.getRegistry().register(Remover32);
//event.getRegistry().register(Remover64256);
event.getRegistry().register(Remover64);
//event.getRegistry().register(Remover816);
//event.getRegistry().register(Remover8256);
//event.getRegistry().register(Remover832);
event.getRegistry().register(Remover8);
event.getRegistry().register(RemoverLast);

event.getRegistry().register(OtherBrickHouse);
event.getRegistry().register(OtherGrandHouse);
event.getRegistry().register(OtherStable);
event.getRegistry().register(OtherSurvivorHouse2);
event.getRegistry().register(OtherSurvivorHouse3);
event.getRegistry().register(OtherSurvivorHouse4);
event.getRegistry().register(OtherSurvivorHouse5);
event.getRegistry().register(OtherSurvivorHouse6);
event.getRegistry().register(OtherSurvivorHouse7);
event.getRegistry().register(OtherSurvivorHouse8);
event.getRegistry().register(OtherSurvivorHouse);
event.getRegistry().register(OtherTemple);
event.getRegistry().register(SurvivalSmallBuilding);
event.getRegistry().register(SurvivalWoodenHouse);
event.getRegistry().register(WoodenHouse);
event.getRegistry().register(BlockCheckerboard);
//event.getRegistry().register(BlockAtlantis);
event.getRegistry().register(BlockBigWorld);

event.getRegistry().register(RandomAirballoon2);
event.getRegistry().register(RandomEntrance);
event.getRegistry().register(RandomFlyingShip);
event.getRegistry().register(RandomGreenTent);
event.getRegistry().register(RandomGreyTent);
event.getRegistry().register(RandomLightHouse);
event.getRegistry().register(RandomMinerTent);
event.getRegistry().register(RandomNetherEntranceSurvival);
event.getRegistry().register(RandomRandomBrickHouse);
event.getRegistry().register(RandomSurvivalHouse1);
event.getRegistry().register(RandomSurvivalHouseSandstone);
event.getRegistry().register(RandomTentCamp);
event.getRegistry().register(RandomWoodenHouse);
event.getRegistry().register(BlockCloud);

event.getRegistry().register(RandomBuildingComplex);
event.getRegistry().register(RandomImmense_Buildingcomplex);
event.getRegistry().register(RandomImmense_greenroof);
event.getRegistry().register(RandomImmense_White_House);
event.getRegistry().register(RandomImmense_WorkingBuilding);
event.getRegistry().register(RandomLittlePalace);
event.getRegistry().register(RandomLittleWoodenCabin);
event.getRegistry().register(RandomSandstoneBuilding);
event.getRegistry().register(RandomSandStoneChurch);
event.getRegistry().register(RandomSandstonewithFarm);
event.getRegistry().register(RandomSimpleSandstone);
event.getRegistry().register(RandomSpawnHouseProd);
event.getRegistry().register(RandomWoodenStonebrickHouse);
event.getRegistry().register(Live_Power_Windmill_East);

event.getRegistry().register(LiveAirBalloon);
event.getRegistry().register(LiveAirplane);
event.getRegistry().register(LiveBoat);
event.getRegistry().register(LiveFlyingShip2);
event.getRegistry().register(LiveFlyingShip);
event.getRegistry().register(LivePlane);
event.getRegistry().register(Live_Helicopter);
event.getRegistry().register(Live_Fair_FreeFall);
event.getRegistry().register(Live_Mill);
event.getRegistry().register(Live_Cinema);
event.getRegistry().register(Live_Flying_Helicopter);
event.getRegistry().register(Live_Bus);
event.getRegistry().register(Live_Bus2);
event.getRegistry().register(BlockFerrisWheel);

event.getRegistry().register(ChristmasHouse);
event.getRegistry().register(ChristmasHouse2);
event.getRegistry().register(ChristmasHouse3);
event.getRegistry().register(ChristmasSleigh);
event.getRegistry().register(ChristmasSleigh2);
event.getRegistry().register(ChristmasSnowman);
event.getRegistry().register(ChristmasTree);
event.getRegistry().register(ChristmasMarket);
event.getRegistry().register(Live_WaterMill);
event.getRegistry().register(BlockUnlimited);

		for(Block block : userBlocks){
			event.getRegistry().register(block);
		}
	 
		/*GameRegistry.addRecipe(new ItemStack(BlockPyramid, 1), new Object[] { 
			      " % ", " * ", "*&*",    Character.valueOf('&'), Blocks.GLOWSTONE, Character.valueOf('*'), Blocks.SANDSTONE, Character.valueOf('%'), Blocks.TORCH
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockTower, 1), new Object[] { 
			      "%&%", "%&%", "* *",    Character.valueOf('&'), Blocks.PLANKS, Character.valueOf('*'), Blocks.LOG, Character.valueOf('%'), Blocks.COBBLESTONE
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockEnchantmentRoom, 1), new Object[] { 
			      "***", "*&*", "***",    Character.valueOf('&'), Blocks.ENCHANTING_TABLE, Character.valueOf('*'), Blocks.BOOKSHELF
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockLighthouse, 1), new Object[] { 
			      "%&%", " * ", " * ",    Character.valueOf('&'), Blocks.GLOWSTONE, Character.valueOf('*'), Blocks.STONE, Character.valueOf('%'), Blocks.GLASS
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHouse, 1), new Object[] { 
			      " & ", "*%*", "*&*",    Character.valueOf('&'), Blocks.PLANKS, Character.valueOf('*'), Blocks.COBBLESTONE, Character.valueOf('%'), Blocks.GLASS
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockGiantTree, 1), new Object[] { 
			      " & ", "&&&", " * ",    Character.valueOf('&'), Blocks.LEAVES, Character.valueOf('*'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.WHEAT_SEEDS, Character.valueOf('&'), Blocks.DIRT
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockStoreHouse, 1), new Object[] { 
			      "%&%", "%&%", "***",    Character.valueOf('&'), Blocks.PLANKS, Character.valueOf('*'), Blocks.COBBLESTONE, Character.valueOf('%'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHouseTrap2, 1), new Object[] { 
			      "***", "*&*", "***",    Character.valueOf('&'), IMSM.BlockHouse, Character.valueOf('*'), Blocks.CACTUS, Character.valueOf('%'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHouseTrap1, 1), new Object[] { 
			      "***", "*&*", "***",    Character.valueOf('&'), IMSM.BlockHouse, Character.valueOf('*'), Items.LAVA_BUCKET, Character.valueOf('%'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockWaterSlide, 1), new Object[] { 
			      "&*&", "& &", "& &",    Character.valueOf('&'), Blocks.PLANKS, Character.valueOf('*'), Items.WATER_BUCKET, Character.valueOf('%'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockBunker, 1), new Object[] { 
			      "&&&", "   ", "&&&",    Character.valueOf('&'), Blocks.COBBLESTONE, Character.valueOf('*'), Items.WATER_BUCKET, Character.valueOf('%'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockArena1, 1), new Object[] { 
			      "&&&", "&%&", "&&&",    Character.valueOf('&'), Blocks.COBBLESTONE, Character.valueOf('*'), Blocks.SAND, Character.valueOf('%'), Blocks.SAND
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockArena2, 1), new Object[] { 
			      "&&&", "&%&", "&&&",    Character.valueOf('&'), Blocks.COBBLESTONE, Character.valueOf('*'), Items.LAVA_BUCKET, Character.valueOf('%'), Items.LAVA_BUCKET
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm2, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.REEDS, Character.valueOf('&'), Blocks.DIRT
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm3, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.MELON_SEEDS, Character.valueOf('&'), Blocks.DIRT
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockFarm4, 1), new Object[] { 
			      "***", "&&&",     Character.valueOf('*'), Items.PUMPKIN_SEEDS, Character.valueOf('&'), Blocks.DIRT
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPrison, 1), new Object[] { 
			      "&&&", "%%%", "&&&",    Character.valueOf('&'), Blocks.COBBLESTONE, Character.valueOf('*'), Items.WATER_BUCKET, Character.valueOf('%'), Blocks.IRON_BARS
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPrison2, 1), new Object[] { 
			      "&&&", "%%%", "&&&",    Character.valueOf('&'), Blocks.OBSIDIAN, Character.valueOf('*'), Items.WATER_BUCKET, Character.valueOf('%'), Blocks.IRON_BARS
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollercoaster, 1), new Object[] { 
			      "%%%", "&*&", "%%%",    Character.valueOf('&'), Blocks.RAIL, Character.valueOf('*'), Blocks.GOLDEN_RAIL, Character.valueOf('%'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockSkyscraper, 1), new Object[] { 
			      "&&&", "&%&", "&&&",    Character.valueOf('&'), Blocks.GLASS, Character.valueOf('*'), Blocks.GOLDEN_RAIL, Character.valueOf('%'), Blocks.STONE
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockTorch2, 1), new Object[] { 
			      "%%%", "%%%", "%%%",    Character.valueOf('&'), Blocks.GLASS, Character.valueOf('*'), Blocks.GOLDEN_RAIL, Character.valueOf('%'), Blocks.TORCH
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockAirplane, 1), new Object[] { 
			      "%*&", "**&", "***",    Character.valueOf('&'), Blocks.GLASS, Character.valueOf('*'), Blocks.STONEBRICK, Character.valueOf('%'), Blocks.GLOWSTONE
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockBoat, 1), new Object[] { 
			      "121", "343", "333",    Character.valueOf('1'), Blocks.IRON_BARS, Character.valueOf('2'), Blocks.GLASS_PANE, Character.valueOf('3'), Blocks.WOOL, Character.valueOf('4'), Blocks.LOG
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockAirBalloon, 1), new Object[] { 
			      "###", "###", " X ",    Character.valueOf('#'), Blocks.WOOL, Character.valueOf('X'), Blocks.PLANKS, Character.valueOf('%'), Blocks.GLOWSTONE
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockAirBalloon, 1), new Object[] { 
			      "###", " # ", "###",    Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('X'), Blocks.PLANKS, Character.valueOf('%'), Blocks.GLOWSTONE
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockBigPyramid, 1), new Object[] { 
			      " % ", "XYX", "%%%",    Character.valueOf('%'), Blocks.SANDSTONE, Character.valueOf('X'), Items.GOLD_INGOT, Character.valueOf('Y'), Items.DIAMOND
			      });
				  GameRegistry.addRecipe(new ItemStack(BlockCastleTower, 1), new Object[] { 
			      "PPP", "CCC", "CCC",    Character.valueOf('P'), Blocks.PLANKS, Character.valueOf('C'), Blocks.COBBLESTONE, Character.valueOf('Y'), Items.DIAMOND
			      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockDungeon, 1), new Object[] { 
				      "PPP", "PCP", "PPP",    Character.valueOf('P'), Blocks.COBBLESTONE, Character.valueOf('C'), Blocks.CHEST, Character.valueOf('Y'), Items.DIAMOND
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockMegaTower, 1), new Object[] { 
				      "PCP", "PCP", "PCP",    Character.valueOf('P'), Blocks.ICE, Character.valueOf('C'), Blocks.QUARTZ_BLOCK, Character.valueOf('Y'), Items.DIAMOND
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockHountedHouse, 1), new Object[] { 
				      "YPY", "PYP", "CCC",    Character.valueOf('P'), Blocks.VINE, Character.valueOf('C'), Blocks.STONEBRICK, Character.valueOf('Y'), Blocks.COBBLESTONE
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockPlane, 1), new Object[] { 
				      "PYP", "PCP", "PCP",    Character.valueOf('P'), Blocks.STONEBRICK, Character.valueOf('C'), Items.REDSTONE, Character.valueOf('Y'), Blocks.GLASS_PANE
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
				      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.OAK_FENCE, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.PLANKS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.SPRUCE_FENCE, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.PLANKS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.BIRCH_FENCE, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.PLANKS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.JUNGLE_FENCE, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.PLANKS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.ACACIA_FENCE, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.PLANKS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockRollerCoaster2, 1), new Object[] { 
					      "ABA", "CDC", "EFE",    Character.valueOf('A'), Blocks.DARK_OAK_FENCE, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.PLANKS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
					      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockStadium, 1), new Object[] { 
				      "AAA", "BBB", "CCC",    Character.valueOf('A'), Blocks.WOOL, Character.valueOf('B'), Blocks.GLOWSTONE, Character.valueOf('C'), Blocks.STONE, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
				      });
				  
				  GameRegistry.addRecipe(new ItemStack(BlockMegaHouse, 1), new Object[] { 
					      " A ", "AAA", "AAA",    Character.valueOf('A'), Blocks.PLANKS, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.GRASS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
					      });
					  
					  GameRegistry.addRecipe(new ItemStack(BlockMegaHouse2, 1), new Object[] { 
					      " A ", "AAA", "AAA",    Character.valueOf('A'), Blocks.LOG, Character.valueOf('B'), Items.MINECART, Character.valueOf('C'), Blocks.GRASS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
					      });
						GameRegistry.addRecipe(new ItemStack(OtherGrandHouse, 1), new Object[] { 
					      "BAB", "BCB", "BAB",    Character.valueOf('A'), Blocks.STONEBRICK, Character.valueOf('B'), Blocks.QUARTZ_BLOCK, Character.valueOf('C'), Blocks.YELLOW_FLOWER, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						});
						 GameRegistry.addRecipe(new ItemStack(OtherSurvivorHouse6, 1), new Object[] { 
					      " A ", "ABA", "ABA",    Character.valueOf('A'), Blocks.LOG, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.GRASS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						 });
							GameRegistry.addRecipe(new ItemStack(OtherSurvivorHouse7, 1), new Object[] { 
					      " B ", "ABA", "ABA",    Character.valueOf('A'), Blocks.LOG, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.GRASS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
							});
						  GameRegistry.addRecipe(new ItemStack(SurvivalSmallBuilding, 1), new Object[] { 
					      "BAB", "BAB", "BAB",    Character.valueOf('A'), Blocks.STONEBRICK, Character.valueOf('B'), Blocks.SANDSTONE, Character.valueOf('C'), Blocks.GRASS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						  GameRegistry.addRecipe(new ItemStack(SurvivalWoodenHouse, 1), new Object[] { 
					      " B ", "BAB", "BBB",    Character.valueOf('A'), Blocks.GLASS, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.GRASS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						  GameRegistry.addRecipe(new ItemStack(WoodenHouse, 1), new Object[] { 
					      "CBC", "ABA", "ABA",    Character.valueOf('A'), Blocks.LOG, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.OAK_STAIRS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomAirballoon2, 1), new Object[] { 
					      "ABA", " B ", "ABA",    Character.valueOf('A'), Blocks.WOOL, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.OAK_STAIRS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						   GameRegistry.addRecipe(new ItemStack(RandomFlyingShip, 1), new Object[] { 
					      "ABA", "AAA", "ABA",    Character.valueOf('A'), Blocks.WOOL, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.OAK_STAIRS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						   });
						  GameRegistry.addRecipe(new ItemStack(RandomGreenTent, 1), new Object[] { 
					      " A ", "AAA", "ABA",    Character.valueOf('A'), Blocks.WOOL, Character.valueOf('B'), Items.BED, Character.valueOf('C'), Blocks.OAK_STAIRS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomGreyTent, 1), new Object[] { 
					      " A ", "ABA", "AAA",    Character.valueOf('A'), Blocks.WOOL, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.OAK_STAIRS, Character.valueOf('D'), Blocks.GOLDEN_RAIL, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomLightHouse, 1), new Object[] { 
					      "CDC", "AAA", "ABA",    Character.valueOf('A'), Blocks.WOOL, Character.valueOf('B'), Blocks.QUARTZ_BLOCK, Character.valueOf('C'), Blocks.OAK_FENCE, Character.valueOf('D'), Blocks.REDSTONE_LAMP, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomLittleWoodenCabin, 1), new Object[] { 
					      " A ", "ACA", "ABA",    Character.valueOf('A'), Blocks.LOG, Character.valueOf('B'), Blocks.ENCHANTING_TABLE, Character.valueOf('C'), Blocks.IRON_BLOCK, Character.valueOf('D'), Blocks.REDSTONE_LAMP, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  });
						  GameRegistry.addRecipe(new ItemStack(RandomWoodenStonebrickHouse, 1), new Object[] { 
					      "CAC", "ABA", "ABA",    Character.valueOf('A'), Blocks.STONEBRICK, Character.valueOf('B'), Blocks.PLANKS, Character.valueOf('C'), Blocks.STONE_BRICK_STAIRS, Character.valueOf('D'), Blocks.REDSTONE_LAMP, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
						  
					      });
					  GameRegistry.addRecipe(new ItemStack(BlockCosyHouse, 1), new Object[] { 
						      "ABA", "BCB", "BDB",    Character.valueOf('A'), Blocks.SNOW, Character.valueOf('B'), Blocks.BRICK_BLOCK, Character.valueOf('C'), Blocks.GLASS, Character.valueOf('D'), Blocks.OAK_DOOR, Character.valueOf('E'), Blocks.REDSTONE_TORCH, Character.valueOf('F'), Blocks.STONE_SLAB
							  
						      });*/
	
	}
	   
	   @SubscribeEvent
	   public void serverLoad(FMLServerStartingEvent event)
	   {
	     //event.getCommandDispatcher().register(new MazeCommand());
	     //event.registerServerCommand(new LiveCommand());
	     //event.registerServerCommand(new RideCommand());
	     //event.registerServerCommand(new UndoCommand());
	   }
	  
}

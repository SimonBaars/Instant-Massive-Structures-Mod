package modid.imsm.core;

import modid.imsm.userstructures.PMCParser;
import modid.imsm.userstructures.PMCParserDelayedStarter;
import modid.imsm.userstructures.PMCParserIntent;
import modid.imsm.worldgeneration.MazeGenerator;
import modid.imsm.worldgeneration.MazeRunner;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
	public static int searchingPage = 1;
	boolean searchByTitle;
	
	@SubscribeEvent
	public void livingSpawnEvent(WorldEvent.Load event)
	{
		IMSM.updateChecked=false;
		IMSM.dialoge=0;
	}
		
	@SubscribeEvent
	public void playerChat(ServerChatEvent event){
		if(IMSM.dialoge!=0){
			if(IMSM.dialoge==1){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<61){
						IMSM.eventHandler.delayedPrints.add("You cannot fly a negative (or too low) distance. Please insert a positive number above 60.");
					} else {
						IMSM.eventHandler.delayedPrints.add("Thank you. Please hop aboard quickly, we are about to depart in 10 seconds.");
						IMSM.dialoge=0;
						IMSM.currentInput.animation[3][0]=inputNumber-60;
						IMSM.currentInput.waitTime=0;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			} else if (IMSM.dialoge==2){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<0){
						IMSM.eventHandler.delayedPrints.add("You cannot sail a negative distance. Please insert a positive number.");
					} else {
						IMSM.eventHandler.delayedPrints.add("Thank you. Please hop aboard quickly, we are about to depart in 10 seconds.");
						IMSM.dialoge=0;
						IMSM.currentInput.animation[2][0]=inputNumber;
						IMSM.currentInput.waitTime=0;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			} else if (IMSM.dialoge==3){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<0){
						IMSM.eventHandler.delayedPrints.add("You cannot ride a negative distance. Please insert a positive number.");
					} else {
						IMSM.eventHandler.delayedPrints.add("Thank you. Please hop aboard quickly, we are about to depart in 10 seconds.");
						IMSM.dialoge=0;
						IMSM.currentInput.animation[2][0]=inputNumber;
						IMSM.currentInput.waitTime=0;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			} else if (IMSM.dialoge==4){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<1 || inputNumber >2){
						IMSM.eventHandler.delayedPrints.add("Please enter 1 or 2.");
					} else {
						IMSM.eventHandler.serverCreators.add(new MazeGenerator((int)Minecraft.getMinecraft().thePlayer.posX,(int) Minecraft.getMinecraft().thePlayer.posY,(int) Minecraft.getMinecraft().thePlayer.posZ));
						if(inputNumber==1){
							getMazeGenerator().startGeneration();
							IMSM.dialoge=0;
						} else if (inputNumber==2){
							IMSM.eventHandler.delayedPrints.add("-- MAZE RUNNER - MANUAL SETUP STEP 1 OUT OF 6 --");
							IMSM.eventHandler.delayedPrints.add("Please choose a mapsize {8 - 1024} (Default 16):");
							IMSM.eventHandler.delayedPrints.add("Please type the number of your choice in the chat");
							  IMSM.dialoge++;
						}
					}
				} catch (Exception e){
					e.printStackTrace();
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			}else if (IMSM.dialoge==5){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<8 || inputNumber >256){
						IMSM.eventHandler.delayedPrints.add("Please enter a number between 8 and 1024.");
					} else {
						getMaze().numOfColumns=inputNumber*3;
						IMSM.eventHandler.delayedPrints.add("-- MAZE RUNNER - MANUAL SETUP STEP 2 OUT OF 6 --");
						IMSM.eventHandler.delayedPrints.add("Please choose the amount of chests {0 - "+((getMaze().numOfColumns*getMaze().numOfColumns)/5)+"} (Default "+((getMaze().numOfColumns*getMaze().numOfColumns)/200)+"):");
						IMSM.eventHandler.delayedPrints.add("Please type the number of your choice in the chat");
							  IMSM.dialoge++;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			}else if (IMSM.dialoge==6){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<0 || inputNumber >((getMaze().numOfColumns*getMaze().numOfColumns)/5)){
						IMSM.eventHandler.delayedPrints.add("Please enter a number between 0 and "+((getMaze().numOfColumns*getMaze().numOfColumns)/5)+".");
					} else {
						getMaze().amountOfChests=inputNumber;
						IMSM.eventHandler.delayedPrints.add("-- MAZE RUNNER - MANUAL SETUP STEP 3 OUT OF 6 --");
						IMSM.eventHandler.delayedPrints.add("Please choose the size of the walls {1-256} (Default 8)");
						IMSM.eventHandler.delayedPrints.add("Please type the number of your choice in the chat");
							  IMSM.dialoge++;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			} else if (IMSM.dialoge==7){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<1 || inputNumber >256){
						IMSM.eventHandler.delayedPrints.add("Please enter a number between 1 and 256.");
					} else {
						getMazeGenerator().blockSize=inputNumber;
						IMSM.eventHandler.delayedPrints.add("-- MAZE RUNNER - MANUAL SETUP STEP 4 OUT OF 6 --");
						IMSM.eventHandler.delayedPrints.add("Please choose the height of the walls {1-256} (Default 16)");
						IMSM.eventHandler.delayedPrints.add("Please type the number of your choice in the chat");
							  IMSM.dialoge++;
						
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			} else if (IMSM.dialoge==8){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<1 || inputNumber >256){
						IMSM.eventHandler.delayedPrints.add("Please enter a number between 1 and 256.");
					} else {
							getMazeGenerator().blockHeight=inputNumber;
							IMSM.eventHandler.delayedPrints.add("-- MAZE RUNNER - MANUAL SETUP STEP 5 OUT OF 6 --");
							IMSM.eventHandler.delayedPrints.add("Please choose the material of the walls (Default 1)");
							IMSM.eventHandler.delayedPrints.add("{1} Mossy Cobblestone");
							IMSM.eventHandler.delayedPrints.add("{2} Bedrock");
							IMSM.eventHandler.delayedPrints.add("{3} Obsidian");
							IMSM.eventHandler.delayedPrints.add("{4} Glass");
							IMSM.eventHandler.delayedPrints.add("Please type the number of your choice in the chat");
							  IMSM.dialoge++;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			} else if (IMSM.dialoge==9){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<1 || inputNumber >4){
						IMSM.eventHandler.delayedPrints.add("Please enter a number between 1 and 4.");
					} else {
						switch(inputNumber){
						case 1: getMazeGenerator().wallMaterial=Blocks.mossy_cobblestone; break;
						case 2: getMazeGenerator().wallMaterial=Blocks.bedrock; break;
						case 3: getMazeGenerator().wallMaterial=Blocks.obsidian; break;
						case 4: getMazeGenerator().wallMaterial=Blocks.glass; break;
						}
						IMSM.eventHandler.delayedPrints.add("-- MAZE RUNNER - MANUAL SETUP STEP 6 OUT OF 6 --");
						IMSM.eventHandler.delayedPrints.add("Do you want the middle of the maze to be populated with a house and farm (Default 1)");
						IMSM.eventHandler.delayedPrints.add("{1} Yes");
						IMSM.eventHandler.delayedPrints.add("{2} No");
						IMSM.eventHandler.delayedPrints.add("Please type the number of your choice in the chat");
							  IMSM.dialoge++;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			}  else if (IMSM.dialoge==10){
				try{
					int inputNumber = Integer.parseInt(event.getMessage());
					if(inputNumber<1 || inputNumber >2){
						IMSM.eventHandler.delayedPrints.add("Please enter 1 or 2.");
					} else {
						getMaze().populateGlade=inputNumber==1;
							getMazeGenerator().startGeneration();
							  IMSM.dialoge=0;
					}
				} catch (Exception e){
					//IMSM.eventHandler.delayedPrints.add("Please only use numbers");
				}
			} else if(IMSM.dialoge == 11){
				int inputNumber = Integer.parseInt(event.getMessage());
				if(inputNumber==1){
					IMSM.pmcParser = new PMCParser(PMCParserIntent.SEARCH_BY_POPULARITY, searchingPage);
					IMSM.dialoge = 13;
				}else if(inputNumber==2){
					IMSM.pmcParser = new PMCParser(PMCParserIntent.SEARCH_BY_RECENTLY_UPDATED, searchingPage);
					IMSM.dialoge=13;
				}else if(inputNumber==3){
					IMSM.pmcParser = new PMCParser(PMCParserIntent.SEARCH_BY_WHATS_HOT, searchingPage);
					IMSM.dialoge=13;
				}else if(inputNumber==4){
					IMSM.pmcParser = new PMCParser(PMCParserIntent.SEARCH_BY_LATEST, searchingPage);
					IMSM.dialoge=13;
				}else if(inputNumber==5){
					IMSM.pmcParser = new PMCParser(PMCParserIntent.SEARCH_BY_DOWNLOADS, searchingPage);
					IMSM.dialoge=13;
				}else if(inputNumber==6){
					IMSM.pmcParser = new PMCParser(PMCParserIntent.SEARCH_BY_VIEWS, searchingPage);
					IMSM.dialoge=13;
				}else if(inputNumber==7){
					IMSM.eventHandler.delayedPrints.add("----------------");
					  IMSM.eventHandler.delayedPrints.add(TextFormatting.UNDERLINE+"Search by title");
					  IMSM.eventHandler.delayedPrints.add("Please enter the title you want to search for...");
					  searchByTitle = true;
					IMSM.dialoge++;
				}else if(inputNumber==8){
					IMSM.eventHandler.delayedPrints.add("----------------");
					  IMSM.eventHandler.delayedPrints.add(TextFormatting.UNDERLINE+"Search by username");
					  IMSM.eventHandler.delayedPrints.add("Please enter the username whose structures you'd like to see...");
					  searchByTitle = false;
					IMSM.dialoge++;
				} else if(inputNumber==9){
					IMSM.pmcParser = new PMCParser(PMCParserIntent.RANDOM, searchingPage);
					IMSM.dialoge=13;
				}
			} else if(IMSM.dialoge==12){
				if(!event.getMessage().equals("")){
					if(searchByTitle){
						IMSM.pmcParser = new PMCParser(event.getMessage(), PMCParserIntent.SEARCH_BY_TITLE, searchingPage);
					} else {
						IMSM.pmcParser = new PMCParser(event.getMessage(), PMCParserIntent.SEARCH_BY_USERNAME);
					}
					IMSM.dialoge=13;
				}
			} else if(IMSM.dialoge == 13){				
				int spaceLocation = PMCParser.seekTill(event.getMessage(), 0, " ");
				String arg1 = event.getMessage().substring(0, spaceLocation);
				if(spaceLocation==0){
					arg1= event.getMessage();
				}
				System.out.println(arg1);
				try{
					int inputNumber = Integer.parseInt(arg1);
					if(inputNumber>0){
						if(IMSM.pmcParser!=null && IMSM.pmcParser.isAlive()){
							IMSM.pmcParser.stopThread();
						}
						//System.out.println(IMSM.pmcParser.currentResults.size()+"<="+((inputNumber-1)*2));
						if(inputNumber>0 && (inputNumber-1)*2<IMSM.pmcParser.currentResults.size()){
							IMSM.eventHandler.delayedPrints.add(TextFormatting.GREEN+"Downloading "+IMSM.pmcParser.currentResults.get((inputNumber-1)*2)+"...");
							new PMCParserDelayedStarter(new PMCParser(PMCParserIntent.DOWNLOAD, (inputNumber-1)*2, IMSM.pmcParser.currentResults));
							IMSM.dialoge=0;
						} else {
							IMSM.eventHandler.delayedPrints.add(TextFormatting.RED+"Please input a number in the range 1-"+(IMSM.pmcParser.currentResults.size()/2)+"...");
						}
					}
				} catch (Exception e){
					//e.printStackTrace();
				}
				if(arg1.equals("more")){
					if(IMSM.pmcParser!=null && IMSM.pmcParser.isAlive()){
						IMSM.eventHandler.delayedPrints.add(TextFormatting.RED+"Please wait for the current search to finish...");
					} else if(IMSM.pmcParser.getIntent() != PMCParserIntent.SEARCH_BY_USERNAME) {
						searchingPage++;
						IMSM.pmcParser = new PMCParser(IMSM.pmcParser.searchCriterium,IMSM.pmcParser.getIntent(), IMSM.pmcParser.currentResults, searchingPage);
					}
				} else if(arg1.equals("all")){
					new PMCParserDelayedStarter(new PMCParser(PMCParserIntent.DOWNLOAD_ALL, IMSM.pmcParser.currentResults));
					IMSM.dialoge=0;
				} else if(arg1.equals("quit")){
					if(IMSM.pmcParser!=null && IMSM.pmcParser.isAlive()){
						IMSM.pmcParser.stopThread();
					}
					IMSM.pmcParser = null;
					IMSM.dialoge=0;
				} 
			}
		}
	}
	
	MazeRunner getMaze(){
		for(int i = 0; i<IMSM.eventHandler.serverCreators.size(); i++){
			if(IMSM.eventHandler.serverCreators.get(i) instanceof MazeGenerator){
				return ((MazeGenerator)IMSM.eventHandler.serverCreators.get(i)).maze;
			}
		}
		return null;
	}
	
	MazeGenerator getMazeGenerator(){
		for(int i = 0; i<IMSM.eventHandler.serverCreators.size(); i++){
			if(IMSM.eventHandler.serverCreators.get(i) instanceof MazeGenerator){
				return (MazeGenerator)IMSM.eventHandler.serverCreators.get(i);
			}
		}
		return null;
	}
}

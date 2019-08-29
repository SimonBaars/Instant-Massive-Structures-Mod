package modid.imsm.userstructures;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

import modid.imsm.core.IMSM;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PMCParser extends Thread {
	PMCParserIntent intent;
	public ArrayList<String> currentResults = new ArrayList<String>();
	//ArrayList<File> schematics = new ArrayList<File>();
	final String savePath = "structures/";
	public String searchCriterium;
	private int intendedResult;
	private boolean stopThread=false;
	int searchingPage = 1;
	boolean thrownException = false;
	
	public PMCParser(PMCParserIntent intent, int page){
		this.intent=intent;
		this.searchingPage=page;
		start();
	}
	
	public PMCParser(String searchCriterium, PMCParserIntent intent){
		this.searchCriterium=searchCriterium;
		this.intent=intent;
		start();
	}
	
	public PMCParser(String searchCriterium, PMCParserIntent intent, int page){
		this.searchCriterium=searchCriterium;
		this.intent=intent;
		this.searchingPage=page;
		start();
	}
	
	public PMCParser(PMCParserIntent intent, ArrayList<String> currentResults){
		this.intent=intent;
		this.currentResults=currentResults;
		checkSaveFolderExistance();
		//start();
	}
	
	public PMCParser(PMCParserIntent intent, int result, ArrayList<String> currentResults){
		this.intent=intent;
		this.intendedResult = result;
		this.currentResults=currentResults;
		checkSaveFolderExistance();
		//start();
	}
	
	public PMCParser(String searchCriterium, PMCParserIntent intent, ArrayList<String> currentResults, int searchingPage) {
		this.intent=intent;
		this.currentResults=currentResults;
		this.searchingPage = searchingPage;
		this.searchCriterium=searchCriterium;
		start();
	}

	public void stopThread(){
		this.stopThread=true;
	}
	
	public PMCParserIntent getIntent(){
		return this.intent;
	}
	
	public void run(){
		//long millis = System.currentTimeMillis();
		if(intent == PMCParserIntent.SEARCH_BY_USERNAME){
			seekByUsername();
		} else if(intent == PMCParserIntent.SEARCH_BY_TITLE){
			seekByTitle();
		} else if(intent == PMCParserIntent.DOWNLOAD){
			downloadResult(this.intendedResult);
			p(TextFormatting.BLUE+"This structure will be available as a "+TextFormatting.UNDERLINE+"no texture block"+TextFormatting.RESET+TextFormatting.BLUE+" in the \"Your structures\" tab.");
			p(TextFormatting.BLUE+"Restart Minecraft to make it show up as a nice textured and modelled block.");
		} else if(intent == PMCParserIntent.DOWNLOAD_ALL){
			p("Downloading all the structures currently being searched for...");
			for(int i = 0; i<currentResults.size(); i+=2){
				downloadResult(i);
			}
			p(TextFormatting.BLUE+"This structure will be available as a "+TextFormatting.UNDERLINE+"no texture block"+TextFormatting.RESET+TextFormatting.BLUE+" in the \"Your structures\" tab.");
			p(TextFormatting.BLUE+"Restart Minecraft to make it show up as a nice textured and modelled block.");
		} else if(intent == PMCParserIntent.SEARCH_BY_DOWNLOADS){
			p("Searching by "+TextFormatting.ITALIC+"amount of downloads"+TextFormatting.RESET+"...");
			seekByFilter("order_downloads");
		} else if(intent == PMCParserIntent.SEARCH_BY_LATEST){
			p("Searching all "+TextFormatting.ITALIC+"the latest structures"+TextFormatting.RESET+"...");
			seekByFilter("order_latest");
		}else if(intent == PMCParserIntent.SEARCH_BY_POPULARITY){
			p("Searching all "+TextFormatting.ITALIC+"the most popular"+TextFormatting.RESET+" structures...");
			seekByFilter("order_popularity");
		}else if(intent == PMCParserIntent.SEARCH_BY_RECENTLY_UPDATED){
			p("Searching all "+TextFormatting.ITALIC+"the latest updated"+TextFormatting.RESET+" structures...");
			seekByFilter("order_updated");
		}else if(intent == PMCParserIntent.SEARCH_BY_VIEWS){
			p("Searching all structures with "+TextFormatting.ITALIC+"the most views"+TextFormatting.RESET+"...");
			seekByFilter("order_views");
		}else if(intent == PMCParserIntent.SEARCH_BY_WHATS_HOT){
			p("Searching all "+TextFormatting.ITALIC+"the hottest"+TextFormatting.RESET+" structures...");
			seekByFilter("order_hot");
		} else if(intent == PMCParserIntent.RANDOM){
			this.searchCriterium=readFile("http://www.wordgenerator.net/application/p.php?type=1&id=nouns&spaceflag=false");
			this.searchCriterium = this.searchCriterium.substring(0, seekTill(this.searchCriterium, 0, ","));
			seekByTitle();
		}
		/*else if(intent == PMCParserIntent.TRY_STRUCTURE){
			tryResult(this.intendedResult);
		}*/
		if(intent != PMCParserIntent.DOWNLOAD && intent != PMCParserIntent.DOWNLOAD_ALL){
			if(this.currentResults.size()==0){
				p(TextFormatting.RED+"No results found!!");
				IMSM.dialoge=0;
			} else if(!this.stopThread && this.currentResults.size()>0){
		p("Type "+TextFormatting.BOLD+TextFormatting.RED+"[a number]"+TextFormatting.RESET+" to add a structure to your structure blocks.");
		//p("Type "+TextFormatting.BOLD+TextFormatting.RED+"try [number]"+TextFormatting.RESET+" to view more structures.");
		p("Type "+TextFormatting.BOLD+TextFormatting.RED+"more"+TextFormatting.RESET+" to view more structures.");
		p("Type "+TextFormatting.BOLD+TextFormatting.RED+"all"+TextFormatting.RESET+" to download all above structures");
		p("Type "+TextFormatting.BOLD+TextFormatting.RED+"quit"+TextFormatting.RESET+" to quit adding structures");
		}}
		//p("It took "+(System.currentTimeMillis()-millis));
		//IMSM.pmcParser = null;
	}
	
	private void downloadResult(int intendedResult) {
		try {
			addFileToSchematics("http://www.planetminecraft.com"+currentResults.get(intendedResult+1)+"download/schematic/", "useradded"+countLines(savePath+"en_US.lang"), currentResults.get(intendedResult));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*private void tryResult(int intendedResult) {
		try {
			addFileToSchematics("http://www.planetminecraft.com"+currentResults.get(intendedResult+1)+"download/schematic/", "trystructure", currentResults.get(intendedResult), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	private void checkSaveFolderExistance() {
		new File(savePath).mkdirs();
	}

	private String getFinalURL(String url) throws MalformedURLException {
		HttpURLConnection con = null;
		try{
		URL urll = new URL(url);
		
	    con = (HttpURLConnection) urll.openConnection();
	    con.addRequestProperty("User-Agent", "Mozilla/4.76"); 
	    con.setInstanceFollowRedirects(false);
	    con.connect();
	    InputStream is = con.getInputStream();
	    //p("CONNINFO === "+con.getResponseCode()+", "+con.getHeaderField("Location"));
if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
	InputStreamReader isr = new InputStreamReader(is);
	int numCharsRead;
	char[] charArray = new char[1024];
	StringBuffer sb = new StringBuffer();
	while ((numCharsRead = isr.read(charArray)) > 0) {
		sb.append(charArray, 0, numCharsRead);
	}
	String result = sb.toString();
	return parseAdPage(result);
}
	    if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
	        String redirectUrl = con.getHeaderField("Location");
	        return getFinalURL(redirectUrl);
	    }
	    return null;
	    } catch(IOException e){
	    	return con.getURL().toString();
	    }
	    
	}
	
	private void seekByFilter(String filter) {
		String input = readFile("http://www.planetminecraft.com/resources/projects/any/?share=schematic&order="+filter+"&p="+searchingPage);
		if(!input.equals("")){
			String searchingFor="<div class=\"r-info\">\n<a href=\"";
			for(int i = 0; i<input.length()-searchingFor.length(); i++){
				if(stopThread){
					return;
				}
				if(input.substring(i, i+searchingFor.length()).equals(searchingFor)){
					String[] result = new String[2];
					result[1] = input.substring(i+searchingFor.length(), seekTill(input,i+searchingFor.length(),"\" title="));
					String searchingFor2=" class=\"r-title\">";
					for(int j = i+searchingFor.length()+result[1].length(); j<input.length()-searchingFor2.length(); j++){
						if(input.substring(j, j+searchingFor2.length()).equals(searchingFor2)){
						result[0] = input.substring(j+searchingFor2.length(), seekTill(input,j+searchingFor2.length(),"</a>"));
					i=j+searchingFor2.length();
						break;
					}
					}
					//boolean successfullyAdded;
					int contentSize = -1;
					try {
						//successfullyAdded = addFileToSchematics("http://www.planetminecraft.com"+result[1]+"download/schematic/", "/home/simon/tmp/"+result[0]+".schematic");
						contentSize = getFileSize("http://www.planetminecraft.com"+result[1]+"download/schematic/");
					} catch (IOException e) {
						//successfullyAdded = false;
						e.printStackTrace();
					}
					
					if(contentSize!=-1 && contentSize!=-2 && contentSize!=-3){
						//p("Schematic size: "+contentSize/1000+"kB");
						TextFormatting color = TextFormatting.GREEN;
						if(contentSize>100000){
							color = TextFormatting.RED;
						}
						p(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"["+((currentResults.size()/2)+1)+"] "+TextFormatting.RESET+result[0]+color+" ("+(contentSize/1000)+"kB)");
						currentResults.add(result[0]);
						currentResults.add(result[1]);
						/*try {
							addFileToSchematics("http://www.planetminecraft.com"+result[1]+"download/schematic/", result[0]+".structure");
						} catch (IOException e) {
							e.printStackTrace();
						}*/
					}else {
						//p("Skipped one that didn't qualify" + contentSize);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void seekByTitle() {
		p("Seeking for schematics with the following title: "+this.searchCriterium);
		String input = readFile("http://www.planetminecraft.com/resources/projects/any/?share=schematic&order=order_popularity&keywords="+URLEncoder.encode(searchCriterium)+"&p="+searchingPage);
		if(!input.equals("")){
			String searchingFor="<div class=\"r-info\">\n<a href=\"";
			for(int i = 0; i<input.length()-searchingFor.length(); i++){
				if(stopThread){
					return;
				}
				if(input.substring(i, i+searchingFor.length()).equals(searchingFor)){
					String[] result = new String[2];
					result[1] = input.substring(i+searchingFor.length(), seekTill(input,i+searchingFor.length(),"\" title="));
					String searchingFor2=" class=\"r-title\">";
					for(int j = i+searchingFor.length()+result[1].length(); j<input.length()-searchingFor2.length(); j++){
						if(input.substring(j, j+searchingFor2.length()).equals(searchingFor2)){
						result[0] = input.substring(j+searchingFor2.length(), seekTill(input,j+searchingFor2.length(),"</a>"));
					i=j+searchingFor2.length();
						break;
					}
					}
					//boolean successfullyAdded;
					int contentSize = -1;
					try {
						//successfullyAdded = addFileToSchematics("http://www.planetminecraft.com"+result[1]+"download/schematic/", "/home/simon/tmp/"+result[0]+".schematic");
						contentSize = getFileSize("http://www.planetminecraft.com"+result[1]+"download/schematic/");
					} catch (IOException e) {
						//successfullyAdded = false;
						e.printStackTrace();
					}
					
					if(contentSize!=-1 && contentSize!=-2 && contentSize!=-3){
						//p("Schematic size: "+contentSize/1000+"kB");
						TextFormatting color = TextFormatting.GREEN;
						if(contentSize>100000){
							color = TextFormatting.RED;
						}
						p(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"["+((currentResults.size()/2)+1)+"] "+TextFormatting.RESET+result[0]+color+" ("+(contentSize/1000)+"kB)");
						currentResults.add(result[0]);
						currentResults.add(result[1]);
						/*try {
							addFileToSchematics("http://www.planetminecraft.com"+result[1]+"download/schematic/", result[0]+".structure");
						} catch (IOException e) {
							e.printStackTrace();
						}*/
					}else {
						//p("Skipped one that didn't qualify" + contentSize);
					}
				}
			}
		}
	}
	
	private String removeSpaces(String webPage){
		return webPage.replace(" ", "%20");
	}
	
	/*private void parseFileAt(String filePath) {
		String input = readFile(filePath);
		//System.out.println("REDIRECT== "+input);
		/*String searchingFor="<div class=\"r-info\">\n<a href=\"";
		for(int i = 0; i<input.length()-searchingFor.length(); i++){
			if(input.substring(i, i+searchingFor.length()).equals(searchingFor)){
			}
		}
	}
*/
	public static int seekTill(String input, int i, String seekTill) {
		for(;i<input.length()-seekTill.length(); i++){
			if(input.substring(i,i+seekTill.length()).equals(seekTill)){
				return i;
			}
		}
		return 0;
	}
	
	private int getFileSize(String fileURL) throws IOException { 
		//p(fileURL);
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
		int responseCode = httpConn.getResponseCode();
		
		if(responseCode==HttpURLConnection.HTTP_VERSION){
			url = new URL(removeSpaces(getFinalURL(fileURL)));
			//p("URL HAS BECOME "+url.toString());
			if(url!=null){
				httpConn.disconnect();
				httpConn = (HttpURLConnection) url.openConnection();
				httpConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
				responseCode = httpConn.getResponseCode();
			}
		}
		if (responseCode == HttpURLConnection.HTTP_OK) {
			//String disposition = httpConn.getHeaderField("Content-Disposition");
		if(!httpConn.getContentType().equals("binary/octet-stream") && !httpConn.getContentType().equals("application/octet-stream")){
			if(httpConn.getContentType().equals("text/html")){
				
				return getFileSize(parseAdPage(getPageFromConn(httpConn)));
			}
			httpConn.disconnect();
			//p("Wrong content type: "+httpConn.getContentType());
			return -2; //Wrong content 
		}
		return httpConn.getContentLength();
		}
		//p("Invalid return code: "+responseCode);
		return -3;
	}

	private String getPageFromConn(HttpURLConnection httpConn) throws IOException {
		InputStream is = httpConn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		while ((numCharsRead = isr.read(charArray)) > 0) {
			sb.append(charArray, 0, numCharsRead);
		}
		return sb.toString();
	}

	private String parseAdPage(String input) {
		//p(input);
		if(!input.equals("")){
			String searchingFor="<h2>Download Ready!</h2>\n<a href=\"";
			for(int i = 0; i<input.length()-searchingFor.length(); i++){
				if(input.substring(i, i+searchingFor.length()).equals(searchingFor)){
					return input.substring(i+searchingFor.length(),seekTill(input,i+searchingFor.length(),"\""));
				}
			}
		}
		return "";
	}
	
	private boolean addFileToSchematics(String fileURL, String saveFilePath, String realFileName)throws IOException { 
		return addFileToSchematics(fileURL, saveFilePath, realFileName, true);
	}

	private boolean addFileToSchematics(String fileURL, String saveFilePath, String realFileName, boolean doRegister)throws IOException { 
		String origsaveFilePath = saveFilePath;
	URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
		int responseCode = httpConn.getResponseCode();
		
		if(responseCode==HttpURLConnection.HTTP_VERSION){
			url = new URL(removeSpaces(getFinalURL(fileURL)));
			//p("URL HAS BECOME "+url.toString());
			if(url!=null){
				httpConn.disconnect();
				httpConn = (HttpURLConnection) url.openConnection();
				httpConn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
				responseCode = httpConn.getResponseCode();
			}
		}
		if (responseCode == HttpURLConnection.HTTP_OK) {
		//String fileName = "";
		//String disposition = httpConn.getHeaderField("Content-Disposition");
		//String contentType = httpConn.getContentType();
		if(!httpConn.getContentType().equals("binary/octet-stream") && !httpConn.getContentType().equals("application/octet-stream")){
			if(httpConn.getContentType().equals("text/html")){
				return addFileToSchematics(parseAdPage(getPageFromConn(httpConn)), saveFilePath, realFileName);
			}
			httpConn.disconnect();
			//p("Wrong content type: "+httpConn.getContentType());
			return false; //Wrong content 
		}
		//int contentLength = httpConn.getContentLength();
		
		//if (disposition != null) {
		// extracts file name from header field
		//int index = disposition.indexOf("filename=");
		//if (index > 0) {
		//fileName = disposition.substring(index + 10,
		//disposition.length() - 1);
		//}
		//} else {
		// extracts file name from URL
		//fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
//fileURL.length());
//}
//System.out.println("Content-Type = " + contentType);
//System.out.println("Content-Disposition = " + disposition);
//System.out.println("Content-Length = " + contentLength);
//System.out.println("fileName = " + fileName);

// opens input stream from the HTTP connection
InputStream inputStream = httpConn.getInputStream();
//String saveFilePath = saveDir + "/" + fileName;

// opens an output stream to save into file
saveFilePath = savePath+saveFilePath.replace("=","");
FileOutputStream outputStream = new FileOutputStream(saveFilePath+".structure");

int bytesRead = -1;
byte[] buffer = new byte[4096];
while ((bytesRead = inputStream.read(buffer)) != -1) {
		                outputStream.write(buffer, 0, bytesRead);
		            }
//schematics.add(new File(saveFilePath));
		            outputStream.close();
		            inputStream.close();
		 
		            p(TextFormatting.GREEN+realFileName+" downloaded!");
		        } else {
		        	httpConn.disconnect();
		            //System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		            return false;
		        }
		        httpConn.disconnect();
		        if(doRegister){
		        registerDownloadedFile(origsaveFilePath, realFileName);
		        }
		        return true;
		    }


	private void registerDownloadedFile(String filename, String realFileName) throws IOException {
		addToFile(filename, "registry.txt");
		addToFile("tile."+filename+".name="+realFileName, "en_US.lang");
		PrintWriter writer = new PrintWriter(savePath+"last.lang", "UTF-8");
		writer.println("tile."+filename+".name="+realFileName);
		writer.close();
		addBlock(filename);
	}
	
	private void addBlock(final String realFileName) {
		Block block = new BlockUserStructure(realFileName).setCreativeTab(IMSM.User);
		  IMSM.userBlocks.add(block);
		  GameRegistry.registerBlock(block, realFileName);
		 // GameRegistry.registerWithItem(block);
		  Minecraft.getInstance().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(IMSM.modid + ":" + realFileName, "inventory"));
		  /*Minecraft.getInstance().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),  new ItemMeshDefinition() {
			  
			    @Override
			    public ModelResourceLocation getModelLocation(ItemStack stack) {
					return new ModelResourceLocation(IMSM.modid + ":" + realFileName, "inventory");
			    }

			});*/
		  try {
				InputStream languageFile = new FileInputStream(new File("structures/last.lang"));
				LanguageMap.inject(languageFile);
				languageFile.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	}

	private int countLines(String filename) throws IOException {
		File file = new File(filename);
		if(file.exists()){
	    InputStream is = new BufferedInputStream(new FileInputStream(file));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
		} 
		return 0;
	}


	private void addToFile(String lineName, String fileName) throws IOException{
		Path filePath = Paths.get(savePath+fileName);
			new FileOutputStream(filePath.toFile(), true).close();
			Scanner scanner = null;
			try{
				scanner = new Scanner(filePath);
				while(scanner.hasNextLine()){
					if(lineName.equals(scanner.nextLine().trim())){
				        return;
				     }
				 }
				scanner.close();
			} catch(Exception e){} finally { scanner.close();}
			lineName+="\n";
			Files.write(filePath, lineName.getBytes(), StandardOpenOption.APPEND);
	}

	@SuppressWarnings("deprecation")
	private void seekByUsername() {
		p("Seeking for schematics by the following user: "+this.searchCriterium);
		//int currentPage=1;
		//while(true){
		String input = readFile("http://www.planetminecraft.com/member/"+URLEncoder.encode(this.searchCriterium)+"/projects/any/?p="+searchingPage);
		if(input.contains("<br/>Nothing here.</p>")){
			p(TextFormatting.RED+"No (more) results have been found!");
			return;
		}
		if(!input.equals("")){
			String searchingFor="title=\"MCEdit Schematic\"";
			boolean hasFoundBefore = false;
			int origIAt = 0;
			for(int i = 0; i<input.length()-searchingFor.length(); i+=getLoopForwardOrBackward(hasFoundBefore)){
				if(stopThread){
					return;
				}
				if(input.substring(i, i+searchingFor.length()).equals(searchingFor)){
					//p(searchingFor + " at "+i+hasFoundBefore);
					if(!hasFoundBefore){
					 searchingFor="<div class=\"r-info\">\n<a href=\"";
					 origIAt = i;
					} else {
					String[] result = new String[2];
					result[1] = input.substring(i+searchingFor.length(), seekTill(input,i+searchingFor.length(),"\" title="));
					String searchingFor2=" class=\"r-title\">";
					for(int j = i+searchingFor.length()+result[1].length(); j<input.length()-searchingFor2.length(); j++){
						if(input.substring(j, j+searchingFor2.length()).equals(searchingFor2)){
						result[0] = input.substring(j+searchingFor2.length(), seekTill(input,j+searchingFor2.length(),"</a>"));
					i=j+searchingFor2.length();
						break;
					}
					}
					//p(result[1]);
					//boolean successfullyAdded;
					int contentSize = -1;
					try {
						//successfullyAdded = addFileToSchematics("http://www.planetminecraft.com"+result[1]+"download/schematic/", "/home/simon/tmp/"+result[0]+".schematic");
						contentSize = getFileSize("http://www.planetminecraft.com"+result[1]+"download/schematic/");
					} catch (IOException e) {
						//successfullyAdded = false;
						e.printStackTrace();
					}
					if(contentSize!=-1 && contentSize!=-2 && contentSize!=-3){
						//p("Schematic size: "+contentSize/1000+"kB");
						TextFormatting color = TextFormatting.GREEN;
						if(contentSize>100000){
							color = TextFormatting.RED;
						}
						p(TextFormatting.BOLD.toString()+TextFormatting.AQUA+"["+((currentResults.size()/2)+1)+"] "+TextFormatting.RESET+result[0]+color+" ("+(contentSize/1000)+"kB)");
						currentResults.add(result[0]);
						currentResults.add(result[1]);
					}else {
						//p("Skipped one that didn't qualify" + contentSize);
					}
					searchingFor="title=\"MCEdit Schematic\"";
					i=origIAt+1;
					}
					hasFoundBefore = !hasFoundBefore;
				}
			}
		}
		//currentPage++;
		//}
	}
	
	

	private int getLoopForwardOrBackward(boolean hasFoundBefore) {
		if(hasFoundBefore){
			return -1;
		}
		return 1;
	}


	private String readFile(String webPage) {
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(webPage);
			urlConnection = (HttpURLConnection) url.openConnection(); 
			urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76"); 
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			return result;
		} catch (Exception e) {
			if(!thrownException){
			e.printStackTrace();
			p(TextFormatting.RED+"You might not have an internet connection...");
			thrownException=true;
			}
		} finally {
			urlConnection.disconnect();
		}
			return "";
				}

	private void p(String result) {
		if(!this.stopThread){
		IMSM.eventHandler.delayedPrints.add(result);
		}
	}

	}

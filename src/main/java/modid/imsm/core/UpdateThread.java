package modid.imsm.core;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class UpdateThread extends Thread {
public void run(){
	if(readFile("http://minecraftcreations.com/updates/update05062016.txt").equals("1")){
		if(Minecraft.getInstance().player!=null){
		Minecraft.getInstance().player.sendChatMessage("There's a new update of Instant Massive Structures Mod available!! Download it now!!"));
		} else {
			IMSM.updateChecked=false;
			System.out.println("Could not check for a Instant Massive Structures Mod update. It's SimJoo's fault, not yours");
		}
		}
}

private String readFile(String path) {
	try {
		String webPage = path;
		URL url = new URL(webPage);
		URLConnection urlConnection = url.openConnection();
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
		
	}
		return "0";
			}

}

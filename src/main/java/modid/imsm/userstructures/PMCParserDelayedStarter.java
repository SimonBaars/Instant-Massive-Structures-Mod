package modid.imsm.userstructures;

import modid.imsm.core.IMSM;

public class PMCParserDelayedStarter extends Thread{
	PMCParser parser;
	public PMCParserDelayedStarter(PMCParser parser){
		if(IMSM.pmcParser.isAlive()){
			IMSM.pmcParser.stopThread();
		}
		this.parser=parser;
		start();
	}
	
	public void run(){
		while(IMSM.pmcParser!=null && IMSM.pmcParser.isAlive()){
		}
		parser.start();
		IMSM.pmcParser=parser;
	}
}

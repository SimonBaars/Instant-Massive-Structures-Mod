package modid.imsm.worldgeneration;

public class MazeRunner {

	public MazeRunner(){
	}
	byte[][] nodeRegister;
	public int numOfColumns=16*3;
	public int gladeSize = 9;	
	
	public int amountOfChests = (numOfColumns*numOfColumns)/100;
	
	public boolean populateGlade = true;
	
	public void setup(){
        
        initCollisions(true);
        
        for(int i = 0; i<nodeRegister.length; i++){
            for(int j = 0; j<nodeRegister[0].length; j++){
                  if(nodeRegister[i][j]==1){   
                	  System.out.print(" ");
                } else if(nodeRegister[i][j]==0){
                    System.out.print("▓");
                } else if (nodeRegister[i][j]==2){
                	System.out.print("O");
                } else if (nodeRegister[i][j]==3){
                	System.out.print("C");
                } else if (nodeRegister[i][j]==4){
                	System.out.print("N");
                } else {
                	System.out.print((char)(nodeRegister[i][j]-9+'0'));
                }
            }
            System.out.println();
        }
     }
	
	private byte[][][] initMap(float density) { //part of setup
		byte[][][] kleppen;
		  nodeRegister = new byte[numOfColumns+2][numOfColumns+2];
		  kleppen = new byte[numOfColumns][numOfColumns][4];
		  
		  for (int i = 0; i < nodeRegister.length; i++) {
			  for (int j = 0; j < nodeRegister[0].length; j++) {
				  nodeRegister[i][j]=1;
			  }
		  }
		  
			  for(int j = 0; j<kleppen.length; j++){
				  for(int k = 0; k<kleppen[0].length; k++){
					  int l = 0;
					  int randomInt = (int)(Math.random()*4);
					  for(int i = 0; i<4; i++){
					  if (Math.random()*density<1 || i==randomInt) {
			    	        kleppen[j][k][i]=(byte) (i+1);
			    	      }
					  }
					 
				  }
			  }
			  return kleppen;
			  
		  }
	
	public void populateGlade(){
		int x = (numOfColumns/2)-(gladeSize)+2;
		int y = (numOfColumns/2)-(gladeSize)+2;
		nodeRegister[x][y]=21;
		nodeRegister[x][y+1]=21;
		nodeRegister[x+1][y+1]=21;
		nodeRegister[x+1][y]=21;
		nodeRegister[x][y+2]=9;
		nodeRegister[x][y+3]=21;
		nodeRegister[x+1][y+3]=21;
		nodeRegister[x+1][y+2]=21;
		nodeRegister[x][y+5]=10;
		nodeRegister[x][y+6]=21;
		nodeRegister[x+1][y+6]=21;
		nodeRegister[x+1][y+5]=21;
		nodeRegister[x+(gladeSize*2)-4][y+11]=11;
		nodeRegister[x+(gladeSize*2)-4][y+12]=21;
		nodeRegister[x+(gladeSize*2)-3][y+12]=21;
		nodeRegister[x+(gladeSize*2)-3][y+11]=21;
		nodeRegister[x+(gladeSize*2)-4][y+13]=12;
		nodeRegister[x+(gladeSize*2)-4][y+14]=21;
		nodeRegister[x+(gladeSize*2)-3][y+14]=21;
		nodeRegister[x+(gladeSize*2)-3][y+13]=21;
	}
		  /*fillNodeRegister();
		  for (int a=1; a<numOfColumns; a++) {
		    lijnen = (Lijn[]) append(lijnen, new Lijn(a*tileSize, tileSize, tileSize, 0, true, false, true));
		    lijnen = (Lijn[]) append(lijnen, new Lijn(a*tileSize, numOfColumns*tileSize, tileSize, 0, true, false, true));
		    lijnen = (Lijn[]) append(lijnen, new Lijn(tileSize, a*tileSize, 0, tileSize, false, false, true));
		    lijnen = (Lijn[]) append(lijnen, new Lijn(numOfColumns*tileSize, a*tileSize, 0, tileSize, false, false, true));
		    for (int b=1; b<numOfColumns; b++) {
		      if (isCloseTo(a, gladeSize, numOfColumns/2) && isCloseTo(b, gladeSize, numOfColumns/2)) {
		        if (b==(numOfColumns/2)-gladeSize) {
		          lijnen = (Lijn[]) append(lijnen, new Lijn(a*tileSize, b*tileSize, tileSize, 0, true, a==numOfColumns/2, false));
		          lijnen = (Lijn[]) append(lijnen, new Lijn(a*tileSize, tileSize+(b+(2*gladeSize))*tileSize, tileSize, 0, true, a==numOfColumns/2, false));
		          lijnen = (Lijn[]) append(lijnen, new Lijn(b*tileSize, a*tileSize, 0, tileSize, false, a==numOfColumns/2, false));
		          lijnen = (Lijn[]) append(lijnen, new Lijn(tileSize+(b+(2*gladeSize))*tileSize, a*tileSize, 0, tileSize, false, a==numOfColumns/2, false));
		        }
		      } else {
		        //poorten = (Poort[]) append(poorten, new Poort(a*tileSize+(tileSize/2), b*tileSize+(tileSize/2)));
		    	  for (int a=0; a<4; a++) {
		    	      if ((n<3 && random(1.8)<1) || ( n==0 && a==3 )) {
		    	        n++; 
		    	        kleppen[a] = (Klep[]) append(kleppen, new Klep((a*90)));
		    	      }
		    	    }
		      }
		    }
		  }*/
	
	private void createOpening(int size, boolean horizontal, boolean left){
		int randomSpot = ((int)(Math.random()*numOfColumns))+1;
		byte number=5;
		for(int i = 0; i<size; i++){
			if(horizontal){
				if(left){
					nodeRegister[i][randomSpot] = number;
				} else {
					nodeRegister[nodeRegister.length-1-i][randomSpot] = (byte) (number+(1*booleanToInt(number==5)));
				}
			} else {
				if(left){
					nodeRegister[randomSpot][i] = (byte) (number+(2*booleanToInt(number==5)));
				} else {
					nodeRegister[randomSpot][nodeRegister[0].length-1-i] = (byte) (number+(3*booleanToInt(number==5)));
				}
			}
			if(i==0){
				number=1;
			}
		}
	}
	
	private void createOpeningsInGlade(int size){
		int randomSpot = nodeRegister.length/2;
		byte number = 5;
		for(int i = 0; i<size; i++){
			nodeRegister[randomSpot-i-gladeSize][randomSpot] = number;
			nodeRegister[numOfColumns-randomSpot+i+gladeSize+1][randomSpot] = (byte) (number+(1*booleanToInt(number==5)));
			nodeRegister[randomSpot][randomSpot-i-gladeSize] = (byte) (number+(2*booleanToInt(number==5)));
			nodeRegister[randomSpot][numOfColumns-randomSpot+i+gladeSize+1] = (byte) (number+(3*booleanToInt(number==5)));
			if(i==0){
				number=1;
			}
		}
	}
		
		private int booleanToInt(boolean b) {
		if(b){
			return 1;
		}
		return 0;
	}

		private void createOutline(int i, int j, int sizex, int sizey) {
			for(int a = i; a-i<sizex; a++){
				for(int b =j; b-j<sizey; b++){
					if(a==i || b==j || a-i==sizex-1 || b-j==sizey-1){
					nodeRegister[a][b]=0;
					}
				}
			}
	}
		
		private void fill(int i, int j, int sizex, int sizey) {
			for(int a = i; a-i<sizex; a++){
				for(int b =j; b-j<sizey; b++){
					nodeRegister[a][b]=4;
				}
			}
	}


		boolean isCloseTo(int number, int howCloseTo, int anotherNumber) {
			  if (anotherNumber<=number+howCloseTo && anotherNumber>=number-howCloseTo) {
			    return true;
			  }
			  return false;
			}
		
		byte[][] getNodeMap(int a, int b) {
			byte[][] nodeMap = {
			    {
			      nodeRegister[a][b],nodeRegister[a][b+1],nodeRegister[a][b+2]
			      }
			      , 
			    {
			    	  nodeRegister[a+1][b],nodeRegister[a+1][b+1],nodeRegister[a+1][b+2]
			      }
			      , 
			    {
			    	  nodeRegister[a+2][b],nodeRegister[a+2][b+1],nodeRegister[a+2][b+2]
			      }
			    };
			    //println(node+", "+ (node+1)+", "+ (node+2)    +createOutline", "+          (numOfColumns*3+node)+", "+ (numOfColumns*3+node+1)+", "+ (numOfColumns*3+node+2)    +", "+          ((2*(numOfColumns*3))+node)+", "+ ((2*(numOfColumns*3))+node+1)+", "+ ((2*(numOfColumns*3))+node+2));
			    return nodeMap;
			}

		void initCollisions(boolean doRemoveMiddle) { //part of setup
		 
		  writeRandomWalls(initMap(2.2F));
		  createOutsideWall();
		  if(doRemoveMiddle){
		  createOutline((numOfColumns/2)-(gladeSize)+1,(numOfColumns/2)-(gladeSize)+1,gladeSize*2,gladeSize*2);
		  createOpeningsInGlade(3);
		  if(populateGlade){
		  populateGlade();
		  }
		  } else {
			  fill((numOfColumns/2)-(gladeSize)+1,(numOfColumns/2)-(gladeSize)+1,gladeSize*2,gladeSize*2);
		  }
		  
		  createOpening(4, ((int)(Math.random()*2))==0, ((int)(Math.random()*2))==0);
	        generateChests(amountOfChests);
		}
		
		private void writeRandomWalls(byte[][][] kleppen) {
			int nodeCounter=0;
			  int lineCounter=0;
			  for (int a=1; a<numOfColumns; a+=3) {
			    for (int b=1; b<numOfColumns; b+=3) {
			      byte[][] temp = getNodeMap(a,b);
			      if (!(isCloseTo(a, gladeSize, numOfColumns/2) && isCloseTo(b, gladeSize, numOfColumns/2))) {
			        temp[1][1]=0;
			        checkContainingKleppen(temp, kleppen[a][b], a, b);
			        lineCounter++;
			      }
			      nodeCounter+=(numOfColumns*3)*3-9;
			      writeNodeMap(temp,a,b);
			    }
			    nodeCounter=a*3;
			  }
		}

		private void createOutsideWall() {
			for(int i = 0; i<nodeRegister.length; i++){
				  for(int j = 0; j<nodeRegister[0].length; j++){
					  if(j==0 || i==0){
						  nodeRegister[i][j]=0;
					  } else if(i==nodeRegister.length-1 || j==nodeRegister[0].length-1){
						  nodeRegister[i][j]=0;
					  }
				  }
			  }
		}

		private void generateChests(int amount){
			int spotx=0, spoty=0;
			for(int i = 0; i<amount; i++){
				while(nodeRegister[spotx][spoty]!=1 || (isCloseTo(spotx, gladeSize, numOfColumns/2) && isCloseTo(spoty, gladeSize, numOfColumns/2))){
					spotx=((int)(Math.random()*nodeRegister.length));
					spoty=((int)(Math.random()*nodeRegister[0].length));
				}
				nodeRegister[spotx][spoty]=3;
			}
		}
		
		void writeNodeMap(byte[][] nodeMap, int a, int b){
			
			      nodeRegister[a][b]=nodeMap[0][0];
			      nodeRegister[a][b+1]=nodeMap[0][1];
			      nodeRegister[a][b+2]=nodeMap[0][2];
			      nodeRegister[a+1][b]=nodeMap[1][0];
			      nodeRegister[a+1][b+1]=nodeMap[1][1];
			      nodeRegister[a+1][b+2]=nodeMap[1][2];
			    	  nodeRegister[a+2][b]=nodeMap[2][0];
			    	  nodeRegister[a+2][b+1]=nodeMap[2][1];
			    	  nodeRegister[a+2][b+2]=nodeMap[2][2];
		}

		void checkContainingKleppen(byte[][] nodeMap, byte[] klep, int a, int b) {
		  for (int i = 0; i<klep.length; i++) {
		    if (klep[i]==1) {
		      nodeMap[0][1]=0;
		    } else if (klep[i]==2) {
		      nodeMap[1][2]=0;
		    } else if (klep[i]==3) {
		      nodeMap[2][1]=0;
		    } else if (klep[i]==4) {
		      nodeMap[1][0]=0;
		    } 
		    //println("Klep gevonden op "+getDegrees(poort.kleppen[i].an+poort.an)+" graden in loop nr. "+a+", "+b);
		  }
		}

}

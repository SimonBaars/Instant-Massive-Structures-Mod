package modid.imsm.livestructures;

public class RideStructure {
	public int[][] animation;
	public int progress = -2;
	public byte number;
	
	public RideStructure(int number){
		this.number=(byte) number;
		animation = getAnimation(number);
	}
	
	int[][] getAnimation(int number){
		if(number==0){ //Ferris Wheel
			int[][] animation={{0,0 ,1 ,1  ,2  ,4  ,6  ,8  ,12 ,15 ,18 ,22 ,25 ,28 ,32 /*set 1*/,35 ,38 ,42 ,45 ,48 ,52 ,55 ,58 ,62 ,64 ,66 ,68 ,69,69,70/*set 2*/ ,70,70,69,69,68,66,64,62,58,55,52,48,45,42,38,35/*set 3*/,32,28,25,22,18,15,12,8 ,6 ,4 ,2 ,1 ,1,0, 0},
					           {0,-3,-7,-10,-13,-17,-20,-23,-27,-29,-31,-33,-34,-34,-35/*set 1*/,-35,-35,-34,-34,-33,-31,-29,-27,-23,-20,-17,-13,-10,-7,-3/*set 2*/,0 ,3 ,7 ,10,13,17,20,23,27,29,31,33,34,34,35,35/*set 3*/,35,34,34,33,31,29,27,23,20,17,13,10,7,3, 0}};
			return animation;
		} else if(number==1){ //Freefall
			int[][] animation={{0,4,11,20,32,47,65,79,87,92,87,80,63,47,30,11,5,3,1,0}};
			return animation;
		}
		return null;
	}
	
	public float getHeight(){
		if(number==0){
			return 1;
		} else if(number==1){
			return 2.5F;
		}
		return 0;
	}
}

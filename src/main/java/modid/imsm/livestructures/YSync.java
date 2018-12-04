package modid.imsm.livestructures;

import modid.imsm.core.LiveStructure;

public class YSync {
	private double y;
	public boolean tickFinal = false;
	public LiveStructure isVehicle = null;
	
	public YSync(double y){
		this.y=y;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}

package com.simonbaars.imsm.client;

import net.fabricmc.api.ClientModInitializer;

public class InstantMassiveStructuresClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AviationPlaytestShot.registerIfRequested();
		FerrisRidePlaytestShot.registerIfRequested();
		PlanePlaytestShot.registerIfRequested();
		PersistenceSmokeShot.registerIfRequested();
	}
}

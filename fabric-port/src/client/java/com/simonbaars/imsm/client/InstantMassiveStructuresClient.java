package com.simonbaars.imsm.client;

import net.fabricmc.api.ClientModInitializer;

public class InstantMassiveStructuresClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AviationPlaytestShot.registerIfRequested();
		FerrisRidePlaytestShot.registerIfRequested();
		FreeFallRidePlaytestShot.registerIfRequested();
		PlanePlaytestShot.registerIfRequested();
		BalloonPlaytestShot.registerIfRequested();
		ShipPlaytestShot.registerIfRequested();
		Bus2PlaytestShot.registerIfRequested();
		FlyingHeliPlaytestShot.registerIfRequested();
		FrameCyclerPlaytestShot.registerIfRequested();
		StaticHeldPlaytestShot.registerIfRequested();
		BoatBusPlaytestShot.registerIfRequested();
		PersistenceSmokeShot.registerIfRequested();
	}
}

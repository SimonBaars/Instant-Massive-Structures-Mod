package modid.imsm.core;

import net.fabricmc.api.ClientModInitializer;

/**
 * Client-side initialization for Instant Massive Structures Mod
 */
public class IMSMClientNew implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IMSMNew.LOGGER.info("Initializing IMSM client-side");
        // Client-side initialization code would go here
    }
}

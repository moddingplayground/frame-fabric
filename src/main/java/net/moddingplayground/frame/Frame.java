package net.moddingplayground.frame;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frame implements ModInitializer {
    public static final String MOD_ID   = "frame";
    public static final String MOD_NAME = "Frame";
    public static final Logger LOGGER   = LoggerFactory.getLogger(MOD_ID);

    @Override
	public void onInitialize() {
		LOGGER.info("Initializing {}", MOD_NAME);

        //

		LOGGER.info("Initialized {}", MOD_NAME);
	}
}

package net.moddingplayground.frame.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternsInternal;
import net.moddingplayground.frame.impl.registry.FrameRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("UnstableApiUsage")
public class Frame implements ModInitializer {
    public static final String MOD_ID   = "frame";
    public static final String MOD_NAME = "Frame";
    public static final Logger LOGGER   = LoggerFactory.getLogger(MOD_ID);

    @Override
	public void onInitialize() {
		LOGGER.info("Initializing {}", MOD_NAME);
        Reflection.initialize(FrameRegistries.class, FrameBannerPatternsInternal.class);
		LOGGER.info("Initialized {}", MOD_NAME);
	}
}

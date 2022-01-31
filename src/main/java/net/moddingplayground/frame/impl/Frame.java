package net.moddingplayground.frame.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.entrypoint.AfterInitializeFrameEntrypoint;
import net.moddingplayground.frame.api.registry.FrameRegistry;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternsInternal;
import net.moddingplayground.frame.impl.entity.FrameEntityType;
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

        Reflection.initialize(
            FrameRegistry.class,
            FrameBannerPatternsInternal.class,
            FrameEntityType.class
        );

        FabricLoader loader = FabricLoader.getInstance();
        loader.getEntrypoints(idString("after_initialize"), AfterInitializeFrameEntrypoint.class).forEach(AfterInitializeFrameEntrypoint::afterInitializeFrame);

		LOGGER.info("Initialized {}", MOD_NAME);
	}

    private static String idString(String path) {
        return new Identifier(MOD_ID, path).toString();
    }
}

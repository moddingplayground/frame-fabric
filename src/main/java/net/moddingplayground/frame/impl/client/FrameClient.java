package net.moddingplayground.frame.impl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.moddingplayground.frame.api.banner.BannerContext;
import net.moddingplayground.frame.api.banner.FrameBannerPattern;
import net.moddingplayground.frame.api.banner.FrameBannerPatterns;
import net.moddingplayground.frame.impl.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("%s-client".formatted(Frame.MOD_ID));

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing {}-client", Frame.MOD_NAME);
        for (BannerContext context : BannerContext.values()) registerBannerSprites(context);
        LOGGER.info("Initialized {}-client", Frame.MOD_NAME);
    }

    public void registerBannerSprites(BannerContext context) {
        ClientSpriteRegistryCallback.event(context.getAtlas()).register((texture, registry) -> {
            for (FrameBannerPattern pattern : FrameBannerPatterns.REGISTRY) {
                registry.register(pattern.getSpriteId(context));
            }
        });
    }
}

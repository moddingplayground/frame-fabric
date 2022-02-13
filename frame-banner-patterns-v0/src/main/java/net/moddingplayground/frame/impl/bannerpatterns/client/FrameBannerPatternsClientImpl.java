package net.moddingplayground.frame.impl.bannerpatterns.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.moddingplayground.frame.api.bannerpatterns.v0.BannerContext;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPatterns;

import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class FrameBannerPatternsClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Stream.of(BannerContext.values()).forEach(FrameBannerPatternsClientImpl::registerBannerSprites);
    }

    public static void registerBannerSprites(BannerContext context) {
        ClientSpriteRegistryCallback.event(context.getAtlas()).register((texture, registry) -> {
            for (FrameBannerPattern pattern : FrameBannerPatterns.REGISTRY) {
                registry.register(pattern.getSpriteId(context));
            }
        });
    }
}

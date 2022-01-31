package net.moddingplayground.frame.impl.client;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.moddingplayground.frame.api.banner.BannerContext;
import net.moddingplayground.frame.api.banner.FrameBannerPattern;
import net.moddingplayground.frame.api.banner.FrameBannerPatterns;
import net.moddingplayground.frame.api.block.wood.WoodBlockSet;
import net.moddingplayground.frame.api.registry.FrameRegistry;
import net.moddingplayground.frame.impl.Frame;
import net.moddingplayground.frame.impl.client.model.FrameEntityModelLayers;
import net.moddingplayground.frame.impl.client.render.entity.FrameBoatEntityRenderer;
import net.moddingplayground.frame.impl.entity.FrameEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class FrameClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("%s-client".formatted(Frame.MOD_ID));

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing {}-client", Frame.MOD_NAME);

        Reflection.initialize(FrameEntityModelLayers.class);
        EntityRendererRegistry.register(FrameEntityType.BOAT, FrameBoatEntityRenderer::new);

        Stream.of(BannerContext.values()).forEach(FrameClient::registerBannerSprites);
        FrameRegistry.WOOD.forEach(WoodBlockSet::onInitializeClient);

        LOGGER.info("Initialized {}-client", Frame.MOD_NAME);
    }

    public static void registerBannerSprites(BannerContext context) {
        ClientSpriteRegistryCallback.event(context.getAtlas()).register((texture, registry) -> {
            for (FrameBannerPattern pattern : FrameBannerPatterns.REGISTRY) {
                registry.register(pattern.getSpriteId(context));
            }
        });
    }
}

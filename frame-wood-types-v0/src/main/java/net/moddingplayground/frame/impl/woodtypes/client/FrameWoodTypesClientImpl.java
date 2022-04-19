package net.moddingplayground.frame.impl.woodtypes.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.moddingplayground.frame.api.woodtypes.v0.entity.FrameWoodEntityType;
import net.moddingplayground.frame.impl.woodtypes.client.render.entity.FrameBoatEntityRenderer;

@Environment(EnvType.CLIENT)
public class FrameWoodTypesClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FrameWoodEntityType.BOAT, FrameBoatEntityRenderer::new);
    }
}

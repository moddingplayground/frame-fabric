package net.moddingplayground.frame.impl.blocks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.moddingplayground.frame.api.blocks.v0.FrameBlocksEntityType;
import net.moddingplayground.frame.api.client.render.entity.NoRenderEmptyEntityRenderer;

@Environment(EnvType.CLIENT)
public class FrameBlocksClientImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FrameBlocksEntityType.SEAT, NoRenderEmptyEntityRenderer::new);
    }
}

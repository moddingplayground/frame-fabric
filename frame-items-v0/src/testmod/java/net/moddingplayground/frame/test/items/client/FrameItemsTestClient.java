package net.moddingplayground.frame.test.items.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.moddingplayground.frame.test.items.FrameItemsTest;

public class FrameItemsTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FrameItemsTest.TEST_SPAWN_EGG_ENTITY, PigEntityRenderer::new);
    }
}

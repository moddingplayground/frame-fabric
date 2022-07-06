package net.moddingplayground.frame.api.toymaker.v0.model;

import net.minecraft.data.client.TextureMap;

import static net.minecraft.data.client.TexturedModel.*;

public interface FrameTexturedModels {
    Factory TEMPLATE_WALL_PLANT_THIN = makeFactory(TextureMap::texture, FrameModels.TEMPLATE_WALL_PLANT_THIN);
}

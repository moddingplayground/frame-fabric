package net.moddingplayground.frame.api.rendering.v0;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

/**
 * An interface that adds custom textures to modded signs.
 */
@FunctionalInterface
public interface SignTextureProvider {
    Identifier getTexture(SignType type);

    @Environment(EnvType.CLIENT)
    default SpriteIdentifier getSpriteIdentifier(SignType type) {
        return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, this.getTexture(type));
    }

    @Environment(EnvType.CLIENT)
    default EntityModelLayer getEntityModelLayer(SignType type, String defaultLayer) {
        return new EntityModelLayer(this.getTexture(type), defaultLayer);
    }
}

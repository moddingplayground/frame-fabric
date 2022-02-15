package net.moddingplayground.frame.api.rendering.v0;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

/**
 * An interface that adds custom textures to modded chests.
 */
@FunctionalInterface
public interface ChestTextureProvider {
    Identifier getTexture(BlockEntity blockEntity, ChestType type, boolean christmas);

    @Environment(EnvType.CLIENT)
    default SpriteIdentifier getSpriteIdentifier(BlockEntity blockEntity, ChestType type, boolean christmas) {
        return new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, this.getTexture(blockEntity, type, christmas));
    }
}

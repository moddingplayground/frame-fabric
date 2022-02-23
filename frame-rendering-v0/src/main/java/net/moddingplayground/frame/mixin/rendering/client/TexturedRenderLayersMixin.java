package net.moddingplayground.frame.mixin.rendering.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.SignType;
import net.moddingplayground.frame.api.rendering.v0.ChestTextureProvider;
import net.moddingplayground.frame.api.rendering.v0.SignTextureProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "getChestTexture(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;", at = @At("HEAD"), cancellable = true)
    private static void onGetChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> cir) {
        BlockState state = blockEntity.getCachedState();
        Block block = state.getBlock();
        if (block instanceof ChestTextureProvider provider) cir.setReturnValue(provider.getSpriteIdentifier(blockEntity, type, christmas));
    }

    @Inject(method = "createSignTextureId", at = @At("HEAD"), cancellable = true)
    private static void onCreateSignTextureId(SignType type, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (type instanceof SignTextureProvider provider) cir.setReturnValue(provider.getSpriteIdentifier(type));
    }
}

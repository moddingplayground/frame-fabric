package net.moddingplayground.frame.mixin.block.wood;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.block.wood.WoodBlockSet;
import net.moddingplayground.frame.api.registry.FrameRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

import static net.moddingplayground.frame.api.block.wood.WoodBlock.*;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @Unique
    private static final Function<Block, Boolean> FRAME_SIGN_SUPPORTS = Util.memoize(block -> {
        for (WoodBlockSet set : FrameRegistry.WOOD) {
            if (block instanceof SignBlock && set.contains(SIGN)) return true;
            if (block instanceof WallSignBlock && set.contains(WALL_SIGN)) return true;
        }
        return false;
    });

    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void onSupports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        BlockEntityType<?> type = BlockEntityType.class.cast(this);
        if (type == BlockEntityType.SIGN && state.getBlock() instanceof AbstractSignBlock block) {
            if (FRAME_SIGN_SUPPORTS.apply(block)) cir.setReturnValue(true);
        }
    }
}

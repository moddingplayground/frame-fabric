package net.moddingplayground.frame.mixin.contentregistries;

import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

public class LadderStateRegistryMixins {
    @Mixin(DamageTracker.class)
    public static class DamageTrackerMixin {
        @Shadow @Final private LivingEntity entity;
        @Shadow @Nullable private String fallDeathSuffix;

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        @Inject(
            method = "setFallDeathSuffix",
            at = @At(
                value = "INVOKE",
                target = "Ljava/util/Optional;isPresent()Z",
                shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
        )
        private void fixFallDeathSuffix(CallbackInfo ci, Optional<BlockPos> pos) {
            pos.map(this.entity.world::getBlockState)
               .ifPresent(state -> {
                   if (StateRegistry.LADDERS_DEATH_MESSAGES.contains(state)) {
                       this.fallDeathSuffix = "ladder";
                       ci.cancel();
                   } else if (StateRegistry.SCAFFOLDING_DEATH_MESSAGES.contains(state)) {
                       this.fallDeathSuffix = "scaffolding";
                       ci.cancel();
                   }
               });
        }
    }

    @Mixin(LivingEntity.class)
    public static class LivingEntityMixin {
        @Inject(
            method = "canEnterTrapdoor",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
                shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
        )
        private void fixCanEnterTrapdoor(BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir, BlockState stated) {
            if (StateRegistry.LADDERS.contains(stated) && stated.contains(LadderBlock.FACING)) {
                if (stated.get(LadderBlock.FACING) == state.get(TrapdoorBlock.FACING)) cir.setReturnValue(true);
            }
        }
    }
}

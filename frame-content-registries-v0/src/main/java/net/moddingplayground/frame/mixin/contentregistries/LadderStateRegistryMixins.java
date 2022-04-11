package net.moddingplayground.frame.mixin.contentregistries;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.fluid.FlowableFluid;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

public class LadderStateRegistryMixins {
    @Mixin(DamageTracker.class)
    public static class DamageTrackerMixin {
        @Redirect(
            method = "setFallDeathSuffix",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
            ),
            slice = @Slice(
                to = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Blocks;LADDER:Lnet/minecraft/block/Block;",
                    shift = At.Shift.AFTER
                )
            )
        )
        private boolean redirectSetFallDeathSuffixIsOf(BlockState state, Block block) {
            if (block == Blocks.LADDER && StateRegistry.LADDERS_DEATH_MESSAGES.contains(state)) return true;
            if (block == Blocks.SCAFFOLDING && StateRegistry.SCAFFOLDING_DEATH_MESSAGES.contains(state)) return true;
            return state.isOf(block);
        }
    }

    @Mixin(FlowableFluid.class)
    public static class FlowableFluidMixin {
        @Redirect(
            method = "canFill",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
            ),
            slice = @Slice(
                to = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Blocks;LADDER:Lnet/minecraft/block/Block;",
                    shift = At.Shift.BY,
                    by = 3
                )
            )
        )
        private boolean redirectCanFillIsOf(BlockState state, Block block) {
            if (block == Blocks.LADDER && StateRegistry.LADDERS.contains(state)) return true;
            return state.isOf(block);
        }
    }

    @Mixin(LivingEntity.class)
    public static class LivingEntityMixin {
        @Redirect(
            method = "canEnterTrapdoor",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
            )
        )
        private boolean redirectCanEnterTrapdoorIsOf(BlockState state, Block block) {
            if (block == Blocks.LADDER && (StateRegistry.LADDERS.contains(state) && state.contains(LadderBlock.FACING))) return true;
            return state.isOf(block);
        }
    }
}

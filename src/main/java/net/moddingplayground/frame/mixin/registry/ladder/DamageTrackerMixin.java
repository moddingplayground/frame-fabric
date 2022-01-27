package net.moddingplayground.frame.mixin.registry.ladder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.damage.DamageTracker;
import net.moddingplayground.frame.api.registry.StateRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {
    @Redirect(method = "setFallDeathSuffix", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"), slice = @Slice(to = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;LADDER:Lnet/minecraft/block/Block;", shift = At.Shift.AFTER)))
    private boolean redirectSetFallDeathSuffixIsOf(BlockState state, Block block) {
        if (block == Blocks.LADDER && StateRegistry.LADDERS_DEATH_MESSAGES.contains(state)) return true;
        return state.isOf(block);
    }
}

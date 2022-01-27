package net.moddingplayground.frame.mixin.registry.ladder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.entity.LivingEntity;
import net.moddingplayground.frame.api.registry.StateRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Redirect(method = "canEnterTrapdoor", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean redirectCanEnterTrapdoorIsOf(BlockState state, Block block) {
        if (block == Blocks.LADDER && (StateRegistry.LADDERS.contains(state) && state.contains(LadderBlock.FACING))) return true;
        return state.isOf(block);
    }
}

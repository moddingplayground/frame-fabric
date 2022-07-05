package net.moddingplayground.frame.mixin.contentregistries.state;

import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    @Inject(method = "canAccessBookshelf", at = @At(value = "HEAD"), cancellable = true)
    private static void fixCanAccessBookshelf(World world, BlockPos pos, BlockPos offset, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.getBlockState(pos.add(offset));
        if (StateRegistry.BOOKSHELVES.contains(state)) {
            BlockPos offsetPos = pos.add(offset.getX() / 2, offset.getY(), offset.getZ() / 2);
            if (world.isAir(offsetPos)) cir.setReturnValue(true);
        }
    }
}

package net.moddingplayground.frame.mixin.contentregistries;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

public class BookshelfStateRegistryMixins {
    @Mixin(EnchantingTableBlock.class)
    public static class EnchantingTableBlockMixin {
        @Redirect(method = "canAccessBookshelf", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
        private static boolean redirectCanAccessBookshelf(BlockState state, Block block) {
            if (block == Blocks.BOOKSHELF && StateRegistry.BOOKSHELVES.contains(state)) return true;
            return state.isOf(block);
        }
    }
}

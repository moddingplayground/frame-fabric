package net.moddingplayground.frame.mixin.contentregistries;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

public class BookshelfStateRegistryMixins {
    @Mixin(EnchantingTableBlock.class)
    public static class EnchantingTableBlockMixin {
        @Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
        private boolean redirectRandomDisplayTickIsOf(BlockState state, Block block) {
            if (block == Blocks.BOOKSHELF && StateRegistry.BOOKSHELVES.contains(state)) return true;
            return state.isOf(block);
        }
    }

    @Mixin(EnchantmentScreenHandler.class)
    public static class EnchantmentScreenHandlerMixin {
        // method_17411 - lambda inside of onContentUpdate
        @Redirect(method = "method_17411", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
        private boolean redirectBookshelfIsOf(BlockState state, Block block) {
            if (block == Blocks.BOOKSHELF && StateRegistry.BOOKSHELVES.contains(state)) return true;
            return state.isOf(block);
        }
    }
}

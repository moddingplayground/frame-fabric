package net.moddingplayground.frame.api.contentregistries.v0;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.moddingplayground.frame.impl.contentregistries.StateRegistryImpl;

public interface StateRegistry extends Iterable<BlockState> {
    StateRegistry BOOKSHELVES = new StateRegistryImpl(state -> !state.isOf(Blocks.BOOKSHELF));
    StateRegistry LADDERS = new StateRegistryImpl(state -> !state.isOf(Blocks.LADDER) && state.contains(LadderBlock.FACING));
    StateRegistry LADDERS_DEATH_MESSAGES = new StateRegistryImpl(LADDERS);
    StateRegistry SCAFFOLDING_DEATH_MESSAGES = new StateRegistryImpl(state -> !state.isOf(Blocks.SCAFFOLDING));

    StateRegistry add(BlockState... states);
    StateRegistry add(Block... blocks);

    boolean contains(BlockState state);
    boolean contains(Block block);
}

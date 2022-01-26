package net.moddingplayground.frame.api.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.moddingplayground.frame.impl.registry.StateRegistryImpl;

public interface StateRegistry extends Iterable<BlockState> {
    StateRegistry BOOKSHELVES = new StateRegistryImpl(state -> !state.isOf(Blocks.BOOKSHELF));
    StateRegistry LADDERS = new StateRegistryImpl(state -> !state.isOf(Blocks.LADDER) && state.contains(LadderBlock.FACING));
    StateRegistry LADDERS_DEATH_MESSAGES = new StateRegistryImpl(LADDERS);

    StateRegistry add(BlockState... states);
    StateRegistry add(Block... blocks);

    boolean contains(BlockState state);
    boolean contains(Block block);
}

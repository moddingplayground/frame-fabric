package net.moddingplayground.frame.impl.registry;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.moddingplayground.frame.api.registry.StateRegistry;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import static net.moddingplayground.frame.impl.Frame.*;

/**
 * A stored list of states.
 */
public final class StateRegistryImpl implements StateRegistry {
    private final List<BlockState> states;
    private final Predicate<BlockState> predicate;

    public StateRegistryImpl(Predicate<BlockState> predicate) {
        this.states = Lists.newArrayList();
        this.predicate = predicate;
    }

    public StateRegistryImpl(StateRegistry registry) {
        this(((StateRegistryImpl) registry).predicate);
    }

    public StateRegistryImpl() {
        this(s -> true);
    }

    @Override
    public StateRegistry add(BlockState... states) {
        for (BlockState state : states) {
            if (!this.predicate.test(state)) LOGGER.warn("Registering {} to a block registry did not match its predicate! Expect unintended behavior or crashing", state);
            this.states.add(state);
        }
        return this;
    }

    @Override
    public StateRegistry add(Block... blocks) {
        for (Block block : blocks) {
            StateManager<Block, BlockState> manager = block.getStateManager();
            this.add(manager.getStates().toArray(BlockState[]::new));
        }
        return this;
    }

    @Override
    public boolean contains(BlockState state) {
        return this.states.contains(state);
    }

    @Override
    public boolean contains(Block block) {
        StateManager<Block, BlockState> manager = block.getStateManager();
        return manager.getStates().stream().anyMatch(this.states::contains);
    }

    @Override
    public Iterator<BlockState> iterator() {
        return this.states.iterator();
    }
}

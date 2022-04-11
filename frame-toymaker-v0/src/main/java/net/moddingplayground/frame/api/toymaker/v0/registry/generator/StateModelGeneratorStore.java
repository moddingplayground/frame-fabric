package net.moddingplayground.frame.api.toymaker.v0.registry.generator;

import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record StateModelGeneratorStore(Supplier<AbstractStateModelGenerator> factory) {
    private static final List<StateModelGeneratorStore> REGISTRY = new ArrayList<>();

    public static StateModelGeneratorStore register(Supplier<AbstractStateModelGenerator> factory) {
        StateModelGeneratorStore store = new StateModelGeneratorStore(factory);
        REGISTRY.add(store);
        return store;
    }

    public static List<Supplier<AbstractStateModelGenerator>> all() {
        List<Supplier<AbstractStateModelGenerator>> list = new ArrayList<>();
        for (StateModelGeneratorStore store : REGISTRY) list.add(store.factory());
        return list;
    }
}

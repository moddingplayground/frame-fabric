package net.moddingplayground.frame.api.toymaker.v0.registry.generator;

import net.moddingplayground.frame.api.toymaker.v0.generator.advancement.AbstractAdvancementGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record AdvancementGeneratorStore(Supplier<AbstractAdvancementGenerator> factory) {
    private static final List<AdvancementGeneratorStore> REGISTRY = new ArrayList<>();

    public static AdvancementGeneratorStore register(Supplier<AbstractAdvancementGenerator> factory) {
        AdvancementGeneratorStore store = new AdvancementGeneratorStore(factory);
        REGISTRY.add(store);
        return store;
    }

    public static List<Supplier<AbstractAdvancementGenerator>> all() {
        List<Supplier<AbstractAdvancementGenerator>> list = new ArrayList<>();
        for (AdvancementGeneratorStore store : REGISTRY) list.add(store.factory());
        return list;
    }
}

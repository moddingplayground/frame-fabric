package net.moddingplayground.frame.api.toymaker.v0.registry.generator;

import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record ItemModelGeneratorStore(Supplier<AbstractItemModelGenerator> factory) {
    private static final List<ItemModelGeneratorStore> REGISTRY = new ArrayList<>();

    public static ItemModelGeneratorStore register(Supplier<AbstractItemModelGenerator> factory) {
        ItemModelGeneratorStore store = new ItemModelGeneratorStore(factory);
        REGISTRY.add(store);
        return store;
    }

    public static List<Supplier<AbstractItemModelGenerator>> all() {
        List<Supplier<AbstractItemModelGenerator>> list = new ArrayList<>();
        for (ItemModelGeneratorStore store : REGISTRY) list.add(store.factory());
        return list;
    }
}

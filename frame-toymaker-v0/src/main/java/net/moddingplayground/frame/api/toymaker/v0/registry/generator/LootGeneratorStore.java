package net.moddingplayground.frame.api.toymaker.v0.registry.generator;

import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Pair;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractLootTableGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LootGeneratorStore<T extends AbstractLootTableGenerator<?>> {
    private static final List<LootGeneratorStore<?>> REGISTRY = new ArrayList<>();
    private final Supplier<T> factory;
    private final LootContextType context;

    public LootGeneratorStore(Supplier<T> factory, LootContextType context) {
        this.factory = factory;
        this.context = context;
    }

    public static LootGeneratorStore<?> register(Supplier<AbstractLootTableGenerator<?>> factory, LootContextType context) {
        LootGeneratorStore<?> store = new LootGeneratorStore<>(factory, context);
        REGISTRY.add(store);
        return store;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Pair<Supplier<AbstractLootTableGenerator<?>>, LootContextType>> all() {
        List<Pair<Supplier<AbstractLootTableGenerator<?>>, LootContextType>> list = new ArrayList<>();
        for (LootGeneratorStore<?> store : REGISTRY) list.add(new Pair(store.factory, store.context));
        return list;
    }
}

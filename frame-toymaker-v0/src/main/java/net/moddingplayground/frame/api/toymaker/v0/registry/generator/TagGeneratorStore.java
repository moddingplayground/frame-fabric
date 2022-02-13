package net.moddingplayground.frame.api.toymaker.v0.registry.generator;

import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TagGeneratorStore<T extends AbstractTagGenerator<?>> {
    private static final List<TagGeneratorStore<?>> REGISTRY = new ArrayList<>();
    private final Supplier<T> factory;

    public TagGeneratorStore(Supplier<T> factory) {
        this.factory = factory;
    }

    public static TagGeneratorStore<?> register(Supplier<AbstractTagGenerator<?>> factory) {
        TagGeneratorStore<?> store = new TagGeneratorStore<>(factory);
        REGISTRY.add(store);
        return store;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Supplier<AbstractTagGenerator<?>>> all() {
        List<Supplier<AbstractTagGenerator<?>>> list = new ArrayList<>();
        for (TagGeneratorStore store : REGISTRY) list.add(store.factory);
        return list;
    }
}

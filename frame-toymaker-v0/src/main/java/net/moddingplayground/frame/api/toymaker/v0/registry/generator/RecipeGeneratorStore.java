package net.moddingplayground.frame.api.toymaker.v0.registry.generator;

import net.moddingplayground.frame.api.toymaker.v0.generator.recipe.AbstractRecipeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RecipeGeneratorStore {
    private static final List<RecipeGeneratorStore> REGISTRY = new ArrayList<>();
    private final Supplier<AbstractRecipeGenerator> factory;

    public RecipeGeneratorStore(Supplier<AbstractRecipeGenerator> factory) {
        this.factory = factory;
    }

    public static RecipeGeneratorStore register(Supplier<AbstractRecipeGenerator> factory) {
        RecipeGeneratorStore store = new RecipeGeneratorStore(factory);
        REGISTRY.add(store);
        return store;
    }

    public static List<Supplier<AbstractRecipeGenerator>> all() {
        List<Supplier<AbstractRecipeGenerator>> list = new ArrayList<>();
        for (RecipeGeneratorStore store : REGISTRY) list.add(store.factory);
        return list;
    }
}

package net.moddingplayground.frame.impl.toymaker.provider;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.toymaker.v0.generator.recipe.AbstractRecipeGenerator;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.RecipeGeneratorStore;
import net.moddingplayground.frame.impl.toymaker.DataType;

import java.util.Map;
import java.util.function.Supplier;

public class RecipeProvider extends AbstractDataProvider<Supplier<AbstractRecipeGenerator>> {
    public RecipeProvider(DataGenerator root) {
        super(root);
    }

    @Override
    public String getName() {
        return "Recipe";
    }

    @Override
    public String getFolder() {
        return "recipes";
    }

    @Override
    public DataType getDataType() {
        return DataType.DATA;
    }

    @Override
    public Iterable<Supplier<AbstractRecipeGenerator>> getGenerators() {
        return RecipeGeneratorStore.all();
    }

    @Override
    public void run(DataCache cache) {
        super.run(cache);
        this.write(cache, this.createFileMapAdvancements(), (path, id) -> this.getOutput(path, id, "advancements/recipes"));
    }

    @Override
    public Map<Identifier, JsonElement> createFileMap() {
        Map<Identifier, JsonElement> map = Maps.newHashMap();
        for (Supplier<AbstractRecipeGenerator> generator : this.getGenerators()) {
            generator.get().accept(
                (id, factory) -> factory.offerTo(provider -> {
                    if (map.put(id, provider.toJson()) != null) {
                        throw new IllegalStateException("Duplicate recipe " + id);
                    }
                }, id)
            );
        }
        return map;
    }

    public Map<Identifier, JsonElement> createFileMapAdvancements() {
        Map<Identifier, JsonElement> map = Maps.newHashMap();
        for (Supplier<AbstractRecipeGenerator> generator : this.getGenerators()) {
            AbstractRecipeGenerator gen = generator.get();
            gen.accept(
                (id, factory) -> factory.offerTo(provider -> {
                    JsonObject json = provider.toAdvancementJson();
                    if (json != null && map.put(id, json) != null) throw new IllegalStateException("Duplicate recipe advancement " + id);
                }, id)
            );
        }
        return map;
    }
}

package net.moddingplayground.frame.impl.toymaker.provider;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.toymaker.v0.generator.advancement.AbstractAdvancementGenerator;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.AdvancementGeneratorStore;
import net.moddingplayground.frame.impl.toymaker.DataType;

import java.util.Map;
import java.util.function.Supplier;

public class AdvancementProvider extends AbstractDataProvider<Supplier<AbstractAdvancementGenerator>> {
    public AdvancementProvider(DataGenerator root) {
        super(root);
    }

    @Override
    public String getName() {
        return "Advancement";
    }

    @Override
    public String getFolder() {
        return "advancements";
    }

    @Override
    public DataType getDataType() {
        return DataType.DATA;
    }

    @Override
    public Iterable<Supplier<AbstractAdvancementGenerator>> getGenerators() {
        return AdvancementGeneratorStore.all();
    }

    @Override
    public Map<Identifier, JsonElement> createFileMap() {
        Map<Identifier, JsonElement> map = Maps.newHashMap();
        for (Supplier<AbstractAdvancementGenerator> generator : this.getGenerators()) {
            generator.get().accept((id, task) -> {
                if (map.put(id, task.toJson()) != null) {
                    throw new IllegalStateException("Duplicate advancement " + id);
                }
            });
        }
        return map;
    }
}

package net.moddingplayground.frame.impl.toymaker.provider;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.TagGeneratorStore;
import net.moddingplayground.frame.impl.toymaker.DataType;

import java.util.Map;
import java.util.function.Supplier;

public class TagProvider extends AbstractDataProvider<Supplier<AbstractTagGenerator<?>>> {
    public TagProvider(DataGenerator root) {
        super(root);
    }

    @Override
    public String getName() {
        return "Tag";
    }

    @Override
    public String getFolder() {
        return "tags";
    }

    @Override
    public DataType getDataType() {
        return DataType.DATA;
    }

    @Override
    public Iterable<Supplier<AbstractTagGenerator<?>>> getGenerators() {
        return TagGeneratorStore.all();
    }

    @Override
    public Map<Identifier, JsonElement> createFileMap() {
        Map<Identifier, JsonElement> map = Maps.newHashMap();
        for (Supplier<AbstractTagGenerator<?>> generator : this.getGenerators()) {
            generator.get().accept((id, factory) -> {
                if (map.put(id, factory.createJson()) != null) {
                    throw new IllegalStateException("Duplicate tag " + id);
                }
            });
        }
        return map;
    }
}

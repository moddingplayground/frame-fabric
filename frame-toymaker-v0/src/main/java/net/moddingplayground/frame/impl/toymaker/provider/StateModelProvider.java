package net.moddingplayground.frame.impl.toymaker.provider;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.block.AbstractStateModelGenerator;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.StateModelGeneratorStore;
import net.moddingplayground.frame.impl.toymaker.DataType;

import java.util.Map;
import java.util.function.Supplier;

public class StateModelProvider extends AbstractDataProvider<Supplier<AbstractStateModelGenerator>> {
    public StateModelProvider(DataGenerator root) {
        super(root);
    }

    @Override
    public String getName() {
        return "States and Block Models";
    }

    @Override
    public String getFolder() {
        return "blockstates";
    }

    @Override
    public DataType getDataType() {
        return DataType.ASSET;
    }

    @Override
    public Iterable<Supplier<AbstractStateModelGenerator>> getGenerators() {
        return StateModelGeneratorStore.all();
    }

    @Override
    public void run(DataWriter cache) {
        super.run(cache);
        this.write(cache, this.createFileMapModels(), (root, id) -> this.getOutput(root, id, "models"));
    }

    @Override
    public Map<Identifier, JsonElement> createFileMap() {
        Map<Identifier, JsonElement> map = Maps.newHashMap();
        for (Supplier<AbstractStateModelGenerator> generator : this.getGenerators()) {
            generator.get().accept((id, stateGen) -> {
                Block block = Registry.BLOCK.get(id);
                if (map.put(id, stateGen.makeJson(id, block)) != null) throw new IllegalStateException("Duplicate state " + id);
            });
        }
        return map;
    }

    public Map<Identifier, JsonElement> createFileMapModels() {
        Map<Identifier, JsonElement> map = Maps.newHashMap();
        for (Supplier<AbstractStateModelGenerator> generator : this.getGenerators()) {
            generator.get().accept((block, stateGen) -> stateGen.getModels((id, modelGen) -> map.put(id, modelGen.makeJson(id))));
        }
        return map;
    }
}

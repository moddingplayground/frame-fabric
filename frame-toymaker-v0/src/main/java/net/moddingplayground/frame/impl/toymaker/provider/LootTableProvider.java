package net.moddingplayground.frame.impl.toymaker.provider;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractLootTableGenerator;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.LootGeneratorStore;
import net.moddingplayground.frame.impl.toymaker.DataType;

import java.util.Map;
import java.util.function.Supplier;

public class LootTableProvider extends AbstractDataProvider<Pair<Supplier<AbstractLootTableGenerator<?>>, LootContextType>> {
    public LootTableProvider(DataGenerator root) {
        super(root);
    }

    @Override
    public String getName() {
        return "Loot Table";
    }

    @Override
    public String getFolder() {
        return "loot_tables";
    }

    @Override
    public DataType getDataType() {
        return DataType.DATA;
    }

    @Override
    public Iterable<Pair<Supplier<AbstractLootTableGenerator<?>>, LootContextType>> getGenerators() {
        return LootGeneratorStore.all();
    }

    @Override
    public Map<Identifier, JsonElement> createFileMap() {
        Map<Identifier, JsonElement> map = Maps.newHashMap();
        for (Pair<Supplier<AbstractLootTableGenerator<?>>, LootContextType> pair : this.getGenerators()) {
            pair.getLeft().get().accept((id, builder) -> {
                LootTable lootTable = builder.type(pair.getRight()).build();
                if (map.put(id, LootManager.toJson(lootTable)) != null) {
                    throw new IllegalStateException("Duplicate loot table " + id);
                }
            });
        }
        return map;
    }
}

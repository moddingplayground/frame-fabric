package net.moddingplayground.frame.impl.loottables;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.loottables.v0.LootTableAdditions;

import java.util.List;

public class LootTableAdditionsImpl implements LootTableAdditions {
    protected final Identifier lootTable;
    protected final List<Identifier> additions;

    public LootTableAdditionsImpl(Identifier lootTable) {
        this.lootTable = lootTable;
        this.additions = Lists.newArrayList();
    }

    @Override
    public Identifier getLootTable() {
        return this.lootTable;
    }

    @Override
    public LootTableAdditions add(List<Identifier> additions) {
        this.additions.addAll(additions);
        return this;
    }

    @Override
    public LootTableAdditions add(Identifier... additions) {
        return this.add(List.of(additions));
    }

    @Override
    public LootTableAdditions register() {
        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (id.equals(this.lootTable)) {
                List<Identifier> additions = this.additions;
                additions.forEach(table -> supplier.copyFrom(manager.getTable(table)));
            }
        });
        return this;
    }
}

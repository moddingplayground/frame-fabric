package net.moddingplayground.frame.impl.loottables;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.loottables.v0.LootTableAdditions;
import net.moddingplayground.frame.mixin.loottables.LootPoolAccessor;
import org.apache.commons.lang3.ArrayUtils;

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
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(this.lootTable)) {
                List<Identifier> additions = this.additions;
                additions.forEach(xid -> {
                    LootTable table = lootManager.getTable(xid);
                    for (LootPool pool : table.pools) {
                        ((LootPoolAccessor) pool).setFunctions(ArrayUtils.addAll(pool.functions, table.functions));
                        tableBuilder.pool(pool);
                    }
                });
            }
        });
        return this;
    }
}

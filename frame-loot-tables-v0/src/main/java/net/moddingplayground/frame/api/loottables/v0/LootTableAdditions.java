package net.moddingplayground.frame.api.loottables.v0;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.impl.loottables.LootTableAdditionsImpl;

import java.util.List;

/**
 * A utility to add loot tables to another loot table.
 */
public interface LootTableAdditions {
    static LootTableAdditions create(Identifier lootTable) { return new LootTableAdditionsImpl(lootTable); }
    static LootTableAdditions     of(Block block)          { return create(block.getLootTableId());        }
    static LootTableAdditions     of(EntityType<?> type)   { return create(type.getLootTableId());         }

    Identifier getLootTable();

    LootTableAdditions add(List<Identifier> additions);
    LootTableAdditions add(Identifier... additions);

    default LootTableAdditions defaulted(String modId) {
        return this.add(this.createDefaultLootTable(modId));
    }

    default Identifier createDefaultLootTable(String modId) {
        Identifier base = this.getLootTable();
        return new Identifier(base.getNamespace(), "frame/%s/%s".formatted(modId, base.getPath()));
    }

    LootTableAdditions register();
}

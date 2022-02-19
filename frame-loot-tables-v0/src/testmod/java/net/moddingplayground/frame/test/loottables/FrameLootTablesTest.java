package net.moddingplayground.frame.test.loottables;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.loottables.v0.LootTableAdditions;

public class FrameLootTablesTest implements ModInitializer {
    public static final String MOD_ID = "frame-loot-tables-test";

    @Override
    public void onInitialize() {
        LootTableAdditions.create(LootTables.SIMPLE_DUNGEON_CHEST).add(new Identifier(MOD_ID, "barrier")).register();
        LootTableAdditions.of(Blocks.STONE).defaulted(MOD_ID).register();
        LootTableAdditions.of(EntityType.PIG).defaulted(MOD_ID).register();
    }
}

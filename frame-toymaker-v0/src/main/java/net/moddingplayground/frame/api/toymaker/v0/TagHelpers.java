package net.moddingplayground.frame.api.toymaker.v0;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;

public interface TagHelpers {
    static void copyAll(FabricTagProvider.ItemTagProvider provider) {
        provider.copy(BlockTags.WOOL, ItemTags.WOOL);
        provider.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        provider.copy(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
        provider.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        provider.copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        provider.copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        provider.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        provider.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        provider.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        provider.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        provider.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        provider.copy(BlockTags.DOORS, ItemTags.DOORS);
        provider.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        provider.copy(BlockTags.OAK_LOGS, ItemTags.OAK_LOGS);
        provider.copy(BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS);
        provider.copy(BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS);
        provider.copy(BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS);
        provider.copy(BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS);
        provider.copy(BlockTags.MANGROVE_LOGS, ItemTags.MANGROVE_LOGS);
        provider.copy(BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS);
        provider.copy(BlockTags.CRIMSON_STEMS, ItemTags.CRIMSON_STEMS);
        provider.copy(BlockTags.WARPED_STEMS, ItemTags.WARPED_STEMS);
        provider.copy(BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS);
        provider.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        provider.copy(BlockTags.OVERWORLD_NATURAL_LOGS, ItemTags.OVERWORLD_NATURAL_LOGS);
        provider.copy(BlockTags.LOGS, ItemTags.LOGS);
        provider.copy(BlockTags.SAND, ItemTags.SAND);
        provider.copy(BlockTags.SLABS, ItemTags.SLABS);
        provider.copy(BlockTags.WALLS, ItemTags.WALLS);
        provider.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        provider.copy(BlockTags.ANVIL, ItemTags.ANVIL);
        provider.copy(BlockTags.RAILS, ItemTags.RAILS);
        provider.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        provider.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        provider.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        provider.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        provider.copy(BlockTags.BEDS, ItemTags.BEDS);
        provider.copy(BlockTags.FENCES, ItemTags.FENCES);
        provider.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        provider.copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
        provider.copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, ItemTags.SOUL_FIRE_BASE_BLOCKS);
        provider.copy(BlockTags.CANDLES, ItemTags.CANDLES);
        provider.copy(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
        provider.copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
        provider.copy(BlockTags.IRON_ORES, ItemTags.IRON_ORES);
        provider.copy(BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES);
        provider.copy(BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES);
        provider.copy(BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES);
        provider.copy(BlockTags.COAL_ORES, ItemTags.COAL_ORES);
        provider.copy(BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES);
        provider.copy(BlockTags.COPPER_ORES, ItemTags.COPPER_ORES);
        provider.copy(BlockTags.DIRT, ItemTags.DIRT);
        provider.copy(BlockTags.TERRACOTTA, ItemTags.TERRACOTTA);
        provider.copy(BlockTags.COMPLETES_FIND_TREE_TUTORIAL, ItemTags.COMPLETES_FIND_TREE_TUTORIAL);
        provider.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
    }
}

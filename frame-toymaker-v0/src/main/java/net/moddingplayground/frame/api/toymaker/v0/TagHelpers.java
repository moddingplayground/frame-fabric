package net.moddingplayground.frame.api.toymaker.v0;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagEntry;
import net.minecraft.tag.TagKey;
import net.moddingplayground.frame.mixin.toymaker.AbstractTagProviderInvoker;

import java.util.List;

public interface TagHelpers {
    static void copyAll(FabricTagProvider.BlockTagProvider blockTagProvider, FabricTagProvider.ItemTagProvider itemTagProvider) {
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOOL, ItemTags.WOOL);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.PLANKS, ItemTags.PLANKS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.BUTTONS, ItemTags.BUTTONS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.DOORS, ItemTags.DOORS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.OAK_LOGS, ItemTags.OAK_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.MANGROVE_LOGS, ItemTags.MANGROVE_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.CRIMSON_STEMS, ItemTags.CRIMSON_STEMS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WARPED_STEMS, ItemTags.WARPED_STEMS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.OVERWORLD_NATURAL_LOGS, ItemTags.OVERWORLD_NATURAL_LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.LOGS, ItemTags.LOGS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.SAND, ItemTags.SAND);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.SLABS, ItemTags.SLABS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WALLS, ItemTags.WALLS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.STAIRS, ItemTags.STAIRS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.ANVIL, ItemTags.ANVIL);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.RAILS, ItemTags.RAILS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.LEAVES, ItemTags.LEAVES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.BEDS, ItemTags.BEDS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.FENCES, ItemTags.FENCES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.FLOWERS, ItemTags.FLOWERS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.SOUL_FIRE_BASE_BLOCKS, ItemTags.SOUL_FIRE_BASE_BLOCKS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.CANDLES, ItemTags.CANDLES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.IRON_ORES, ItemTags.IRON_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.COAL_ORES, ItemTags.COAL_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.COPPER_ORES, ItemTags.COPPER_ORES);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.DIRT, ItemTags.DIRT);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.TERRACOTTA, ItemTags.TERRACOTTA);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.COMPLETES_FIND_TREE_TUTORIAL, ItemTags.COMPLETES_FIND_TREE_TUTORIAL);
        copyIfPresent(blockTagProvider, itemTagProvider, BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
    }

    @SuppressWarnings("unchecked")
    static void copyIfPresent(FabricTagProvider.BlockTagProvider blockTagProvider, FabricTagProvider.ItemTagProvider itemTagProvider, TagKey<Block> blockTag, TagKey<Item> itemTag) {
        List<TagEntry> list = ((AbstractTagProviderInvoker<Block>) blockTagProvider).invokeGetTagBuilder(blockTag).build();
        if (!list.isEmpty()) itemTagProvider.copy(blockTag, itemTag);
    }
}

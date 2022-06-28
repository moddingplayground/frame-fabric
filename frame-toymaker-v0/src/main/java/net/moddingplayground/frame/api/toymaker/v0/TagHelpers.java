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
    static void copyAll(FabricTagProvider.ItemTagProvider provider) {
        copyIfPresent(provider, BlockTags.WOOL, ItemTags.WOOL);
        copyIfPresent(provider, BlockTags.PLANKS, ItemTags.PLANKS);
        copyIfPresent(provider, BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
        copyIfPresent(provider, BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copyIfPresent(provider, BlockTags.BUTTONS, ItemTags.BUTTONS);
        copyIfPresent(provider, BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        copyIfPresent(provider, BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copyIfPresent(provider, BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copyIfPresent(provider, BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copyIfPresent(provider, BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copyIfPresent(provider, BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copyIfPresent(provider, BlockTags.DOORS, ItemTags.DOORS);
        copyIfPresent(provider, BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copyIfPresent(provider, BlockTags.OAK_LOGS, ItemTags.OAK_LOGS);
        copyIfPresent(provider, BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS);
        copyIfPresent(provider, BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS);
        copyIfPresent(provider, BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS);
        copyIfPresent(provider, BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS);
        copyIfPresent(provider, BlockTags.MANGROVE_LOGS, ItemTags.MANGROVE_LOGS);
        copyIfPresent(provider, BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS);
        copyIfPresent(provider, BlockTags.CRIMSON_STEMS, ItemTags.CRIMSON_STEMS);
        copyIfPresent(provider, BlockTags.WARPED_STEMS, ItemTags.WARPED_STEMS);
        copyIfPresent(provider, BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS);
        copyIfPresent(provider, BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copyIfPresent(provider, BlockTags.OVERWORLD_NATURAL_LOGS, ItemTags.OVERWORLD_NATURAL_LOGS);
        copyIfPresent(provider, BlockTags.LOGS, ItemTags.LOGS);
        copyIfPresent(provider, BlockTags.SAND, ItemTags.SAND);
        copyIfPresent(provider, BlockTags.SLABS, ItemTags.SLABS);
        copyIfPresent(provider, BlockTags.WALLS, ItemTags.WALLS);
        copyIfPresent(provider, BlockTags.STAIRS, ItemTags.STAIRS);
        copyIfPresent(provider, BlockTags.ANVIL, ItemTags.ANVIL);
        copyIfPresent(provider, BlockTags.RAILS, ItemTags.RAILS);
        copyIfPresent(provider, BlockTags.LEAVES, ItemTags.LEAVES);
        copyIfPresent(provider, BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copyIfPresent(provider, BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copyIfPresent(provider, BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copyIfPresent(provider, BlockTags.BEDS, ItemTags.BEDS);
        copyIfPresent(provider, BlockTags.FENCES, ItemTags.FENCES);
        copyIfPresent(provider, BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        copyIfPresent(provider, BlockTags.FLOWERS, ItemTags.FLOWERS);
        copyIfPresent(provider, BlockTags.SOUL_FIRE_BASE_BLOCKS, ItemTags.SOUL_FIRE_BASE_BLOCKS);
        copyIfPresent(provider, BlockTags.CANDLES, ItemTags.CANDLES);
        copyIfPresent(provider, BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
        copyIfPresent(provider, BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
        copyIfPresent(provider, BlockTags.IRON_ORES, ItemTags.IRON_ORES);
        copyIfPresent(provider, BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES);
        copyIfPresent(provider, BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES);
        copyIfPresent(provider, BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES);
        copyIfPresent(provider, BlockTags.COAL_ORES, ItemTags.COAL_ORES);
        copyIfPresent(provider, BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES);
        copyIfPresent(provider, BlockTags.COPPER_ORES, ItemTags.COPPER_ORES);
        copyIfPresent(provider, BlockTags.DIRT, ItemTags.DIRT);
        copyIfPresent(provider, BlockTags.TERRACOTTA, ItemTags.TERRACOTTA);
        copyIfPresent(provider, BlockTags.COMPLETES_FIND_TREE_TUTORIAL, ItemTags.COMPLETES_FIND_TREE_TUTORIAL);
        copyIfPresent(provider, BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
    }

    @SuppressWarnings("unchecked")
    static void copyIfPresent(FabricTagProvider.ItemTagProvider provider, TagKey<Block> blockTag, TagKey<Item> itemTag) {
        List<TagEntry> list = ((AbstractTagProviderInvoker<Item>) provider).invokeGetTagBuilder(itemTag).build();
        if (!list.isEmpty()) provider.copy(blockTag, itemTag);
    }
}

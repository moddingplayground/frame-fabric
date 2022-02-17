package net.moddingplayground.frame.api.tags.v0;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.impl.tags.CommonTagImpl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A collection of certain common tags, compiled from the
 * <a href="https://fabricmc.net/wiki/tutorial:tags#existing_common_tags">Fabric wiki</a>.
 */
public interface CommonTag {
    CommonTag BOOKSHELVES = new CommonTagImpl("bookshelf", "bookshelves");
    CommonTag LADDERS = new CommonTagImpl("ladder", "ladders");
    CommonTag PLANKS_THAT_BURN = new CommonTagImpl("planks_that_burn", "burning_planks", "burning_plank");
    CommonTag AMETHYST_BLOCKS = new CommonTagImpl("amethyst_block", "amethyst_blocks");

    /**
     * A tag for crafting table blocks.
     */
    CommonTag WORKBENCHES = new CommonTagImpl("workbench", "workbenches", "crafting_table", "crafting_tables");

    /**
     * A tag for blocks made out of their mineral.
     * For example, {@linkplain Blocks#DIAMOND_BLOCK} or {@linkplain Blocks#GOLD_BLOCK}.
     */
    CommonTag STORAGE_BLOCKS = new CommonTagImpl("storage_block", "storage_blocks");

    CommonTag DIRTS = new CommonTagImpl("dirt", "dirts");
    CommonTag STONES = new CommonTagImpl("stone", "stones");
    CommonTag COBBLESTONES = new CommonTagImpl("cobblestone", "cobblestones");
    CommonTag BASALTS = new CommonTagImpl("basalt", "basalts");
    CommonTag NETHER_STONES = new CommonTagImpl("nether_stone", "nether_stones");
    CommonTag NETHERRACKS = new CommonTagImpl("netherrack", "netherracks");

    CommonTag CHESTS = new CommonTagImpl("chest", "chests");
    CommonTag BARRELS = new CommonTagImpl("barrel", "barrels");

    CommonTag WOODEN_CHESTS = new CommonTagImpl("wooden_chest", "wooden_chests");
    CommonTag WOODEN_BARRELS = new CommonTagImpl("wooden_barrel", "wooden_barrels");

    CommonTag TRAPPED_CHESTS = new CommonTagImpl("trapped_chest", "trapped_chests");
    CommonTag TRAPPED_WOODEN_CHESTS = new CommonTagImpl("trapped_wooden_chest", "trapped_wooden_chests");

    CommonTag COPPER_BLOCKS = new CommonTagImpl("copper_block", "copper_blocks");
    CommonTag EXPOSED_COPPER_BLOCKS = new CommonTagImpl("exposed_copper_block", "exposed_copper_blocks");
    CommonTag WEATHERED_COPPER_BLOCKS = new CommonTagImpl("weathered_copper_block", "weathered_copper_blocks");
    CommonTag OXIDIZED_COPPER_BLOCKS = new CommonTagImpl("oxidized_copper_block", "oxidized_copper_blocks");

    CommonTag WOODEN_STICKS = new CommonTagImpl("wooden_stick", "wooden_sticks", "wood_stick", "wood_sticks");
    CommonTag WOODEN_RODS = new CommonTagImpl("wooden_rod", "wooden_rods", "wood_rod", "wood_rods");

    CommonTag DYES = new CommonTagImpl("dye", "dyes", "dye_any", "any_dye");
    Function<DyeColor, CommonTag> INDIVIDUAL_DYES = Util.memoize(dye -> {
        String name = dye.getName();
        return new CommonTagImpl(
            name + "_dye", name + "_dyes",
            name + "_dye_any", name + "_dyes_any",
            "any_" + name + "_dye", "any_" + name + "_dyes"
        );
    });

    List<Tag.Identified<Block>> blockTags();
    List<Tag.Identified<Item>> itemTags();

    void run(Consumer<Tag.Identified<Block>> block, Consumer<Tag.Identified<Item>> item);

    static Identifier id(String id) {
        return new Identifier("c", id);
    }

    static Tag.Identified<Item> item(String id) {
        return TagFactory.ITEM.create(id(id));
    }

    static Tag.Identified<Block> block(String id) {
        return TagFactory.BLOCK.create(id(id));
    }
}

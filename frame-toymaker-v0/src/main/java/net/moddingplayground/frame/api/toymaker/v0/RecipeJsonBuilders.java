package net.moddingplayground.frame.api.toymaker.v0;

import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static net.minecraft.data.server.RecipeProvider.*;

public interface RecipeJsonBuilders {
    static void offer(Consumer<RecipeJsonProvider> exporter, CraftingRecipeJsonBuilder recipe) {
        recipe.offerTo(exporter);
    }

    static ShapedRecipeJsonBuilder generic3x3(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern("###")
                                      .criterion("has_ingredient", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic2x2(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion("has_ingredient", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic2x1(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .criterion("has_ingredient", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic2x3(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion("has_ingredient", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic3x1(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .criterion("has_ingredient", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder sandwich(ItemConvertible outside, ItemConvertible inside, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', outside)
                                      .input('X', inside)
                                      .pattern("###")
                                      .pattern("XXX")
                                      .pattern("###")
                                      .criterion("has_outside", conditionsFromItem(outside))
                                      .criterion("has_inside", conditionsFromItem(inside));
    }

    static ShapedRecipeJsonBuilder chequer2x2(ItemConvertible one, ItemConvertible two, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', one)
                                      .input('X', two)
                                      .pattern("#X")
                                      .pattern("X#")
                                      .criterion("has_one", conditionsFromItem(one))
                                      .criterion("has_two", conditionsFromItem(two));
    }

    static ShapedRecipeJsonBuilder ring(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("# #")
                                      .pattern("###")
                                      .criterion("has_ingredient", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder ringSurrounding(ItemConvertible outside, ItemConvertible inside, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', outside)
                                      .input('X', inside)
                                      .pattern("###")
                                      .pattern("#X#")
                                      .pattern("###")
                                      .criterion("has_outside", conditionsFromItem(outside))
                                      .criterion("has_inside", conditionsFromItem(inside));
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible from, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(from)
                                         .criterion("has_ingredient", conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(one)
                                         .input(two)
                                         .criterion("has_one", conditionsFromItem(one))
                                         .criterion("has_two", conditionsFromItem(two));
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible three, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(one)
                                         .input(two)
                                         .input(three)
                                         .criterion("has_one", conditionsFromItem(one))
                                         .criterion("has_two", conditionsFromItem(two))
                                         .criterion("has_three", conditionsFromItem(three));
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible three, ItemConvertible four, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(one)
                                         .input(two)
                                         .input(three)
                                         .input(four)
                                         .criterion("has_one", conditionsFromItem(one))
                                         .criterion("has_two", conditionsFromItem(two))
                                         .criterion("has_three", conditionsFromItem(three))
                                         .criterion("has_four", conditionsFromItem(four));
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible output, int count, ItemConvertible... ingredients) {
        ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(output, count);
        Set<ItemConvertible> criteria = new HashSet<>();
        for (ItemConvertible item : ingredients) {
            builder.input(item);

            if (!criteria.contains(item)) {
                Identifier id = Registry.ITEM.getId(item.asItem());
                builder.criterion("has_%s".formatted(id.getPath()), conditionsFromItem(item));
                criteria.add(item);
            }
        }
        return builder;
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible[] from, ItemConvertible to, int count) {
        ShapelessRecipeJsonBuilder factory = ShapelessRecipeJsonBuilder.create(to, count)
                                                                       .input(Ingredient.ofItems(from));
        for (ItemConvertible itemC : from) {
            Item item = itemC.asItem();
            String itemId = Registry.ITEM.getId(item)
                                         .getPath();
            factory.criterion("has_" + itemId, conditionsFromItem(itemC));
        }

        return factory;
    }

    static SingleItemRecipeJsonBuilder stonecutting(ItemConvertible from, ItemConvertible to, int count) {
        return SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(from), to, count)
                                          .criterion("has_item", conditionsFromItem(from));
    }

    static SingleItemRecipeJsonBuilder stonecutting(ItemConvertible from, ItemConvertible to) {
        return stonecutting(from, to, 1);
    }

    static ShapelessRecipeJsonBuilder planks(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(from)
                                         .group("planks")
                                         .criterion("has_log", conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder planks(TagKey<Item> from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(from)
                                         .group("planks")
                                         .criterion("has_log", conditionsFromTag(from));
    }

    static ShapelessRecipeJsonBuilder planksLogs(TagKey<Item> from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(from)
                                         .group("planks")
                                         .criterion("has_logs", conditionsFromTag(from));
    }

    static ShapedRecipeJsonBuilder wood(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .group("bark")
                                      .criterion("has_log", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder boat(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .pattern("# #")
                                      .pattern("###")
                                      .group("boat")
                                      .criterion("in_water", requireEnteringFluid(Blocks.WATER));
    }

    static ShapelessRecipeJsonBuilder woodenButton(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(from)
                                         .group("wooden_button")
                                         .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenDoor(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .pattern("##")
                                      .group("wooden_door")
                                      .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenFence(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', Items.STICK)
                                      .input('W', from)
                                      .pattern("W#W")
                                      .pattern("W#W")
                                      .group("wooden_fence")
                                      .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenFenceGate(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', Items.STICK)
                                      .input('W', from)
                                      .pattern("#W#")
                                      .pattern("#W#")
                                      .group("wooden_fence_gate")
                                      .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenPressurePlate(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .pattern("##")
                                      .group("wooden_pressure_plate")
                                      .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenSlab(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 6)
                                      .input('#', from)
                                      .pattern("###")
                                      .group("wooden_slab")
                                      .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenStairs(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 4)
                                      .input('#', from)
                                      .pattern("#  ")
                                      .pattern("## ")
                                      .pattern("###")
                                      .group("wooden_stairs")
                                      .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenTrapdoor(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 2)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .group("wooden_trapdoor")
                                      .criterion("has_planks", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder sign(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .group("sign")
                                      .input('#', from)
                                      .input('X', Items.STICK)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern(" X ")
                                      .criterion("has_" + string, conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder wool(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(from)
                                         .input(Blocks.WHITE_WOOL)
                                         .group("wool")
                                         .criterion("has_white_wool", conditionsFromItem(Blocks.WHITE_WOOL));
    }

    static ShapedRecipeJsonBuilder carpet(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .group("carpet")
                                      .criterion("has_" + string, conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder dyedCarpet(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(to.asItem())
                                     .getPath();
        String string2 = Registry.ITEM.getId(from.asItem())
                                      .getPath();
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.WHITE_CARPET)
                                      .input('$', from)
                                      .pattern("###")
                                      .pattern("#$#")
                                      .pattern("###")
                                      .group("carpet")
                                      .criterion("has_white_carpet", conditionsFromItem(Blocks.WHITE_CARPET))
                                      .criterion("has_" + string2, conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder bed(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .input('X', ItemTags.PLANKS)
                                      .pattern("###")
                                      .pattern("XXX")
                                      .group("bed")
                                      .criterion("has_" + string, conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder dyedBed(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(to.asItem())
                                     .getPath();
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(Items.WHITE_BED)
                                         .input(from)
                                         .group("dyed_bed")
                                         .criterion("has_bed", conditionsFromItem(Items.WHITE_BED));
    }

    static ShapedRecipeJsonBuilder banner(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .input('|', Items.STICK)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern(" | ")
                                      .group("banner")
                                      .criterion("has_" + string, conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder stainedGlass(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.GLASS)
                                      .input('X', from)
                                      .pattern("###")
                                      .pattern("#X#")
                                      .pattern("###")
                                      .group("stained_glass")
                                      .criterion("has_glass", conditionsFromItem(Blocks.GLASS));
    }

    static ShapedRecipeJsonBuilder stainedGlassPaneGlass(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 16)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .group("stained_glass_pane")
                                      .criterion("has_glass", conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder stainedGlassPaneDye(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(to.asItem()).getPath();
        String string2 = Registry.ITEM.getId(from.asItem()).getPath();
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.GLASS_PANE)
                                      .input('$', from)
                                      .pattern("###")
                                      .pattern("#$#")
                                      .pattern("###")
                                      .group("stained_glass_pane")
                                      .criterion("has_glass_pane", conditionsFromItem(Blocks.GLASS_PANE))
                                      .criterion("has_" + string2, conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder stainedTerracotta(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.TERRACOTTA)
                                      .input('X', from)
                                      .pattern("###")
                                      .pattern("#X#")
                                      .pattern("###")
                                      .group("stained_terracotta")
                                      .criterion("has_terracotta", conditionsFromItem(Blocks.TERRACOTTA));
    }

    static ShapelessRecipeJsonBuilder concretePowder(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 8)
                                         .input(from)
                                         .input(Blocks.SAND, 4)
                                         .input(Blocks.GRAVEL, 4)
                                         .group("concrete_powder")
                                         .criterion("has_sand", conditionsFromItem(Blocks.SAND))
                                         .criterion("has_gravel", conditionsFromItem(Blocks.GRAVEL));
    }
}

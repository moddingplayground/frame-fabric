package net.moddingplayground.frame.api.toymaker.v0.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;

import static net.minecraft.data.server.RecipeProvider.*;

public interface RecipeJsonBuilders {
    static ShapedRecipeJsonBuilder generic3x3(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic2x2(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic2x1(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic1x2(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("#")
                                      .pattern("#")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic2x3(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic3x2(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic3x1(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder generic1x3(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("#")
                                      .pattern("#")
                                      .pattern("#")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder sandwich(ItemConvertible outside, ItemConvertible inside, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', outside)
                                      .input('X', inside)
                                      .pattern("###")
                                      .pattern("XXX")
                                      .pattern("###")
                                      .criterion(hasItem(outside), conditionsFromItem(outside))
                                      .criterion(hasItem(inside), conditionsFromItem(inside));
    }

    static ShapedRecipeJsonBuilder chequer2x2(ItemConvertible one, ItemConvertible two, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', one)
                                      .input('X', two)
                                      .pattern("#X")
                                      .pattern("X#")
                                      .criterion(hasItem(one), conditionsFromItem(one))
                                      .criterion(hasItem(two), conditionsFromItem(two));
    }

    static ShapedRecipeJsonBuilder chequer3x3(ItemConvertible one, ItemConvertible two, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', one)
                                      .input('X', two)
                                      .pattern("#X#")
                                      .pattern("X#X")
                                      .pattern("#X#")
                                      .criterion(hasItem(one), conditionsFromItem(one))
                                      .criterion(hasItem(two), conditionsFromItem(two));
    }

    static ShapedRecipeJsonBuilder ring(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("# #")
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder ringSurrounding(ItemConvertible outside, ItemConvertible inside, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', outside)
                                      .input('X', inside)
                                      .pattern("###")
                                      .pattern("#X#")
                                      .pattern("###")
                                      .criterion(hasItem(outside), conditionsFromItem(outside))
                                      .criterion(hasItem(inside), conditionsFromItem(inside));
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible from, ItemConvertible to, int count) {
        return ShapelessCriteriaRecipeJsonBuilder.create(to, count).input(from).build();
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible to, int count) {
        return ShapelessCriteriaRecipeJsonBuilder.create(to, count).input(one).input(two).build();
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible three, ItemConvertible to, int count) {
        return ShapelessCriteriaRecipeJsonBuilder.create(to, count).input(one).input(two).input(three).build();
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible three, ItemConvertible four, ItemConvertible to, int count) {
        return ShapelessCriteriaRecipeJsonBuilder.create(to, count).input(one).input(two).input(three).input(four).build();
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible output, int count, ItemConvertible... ingredients) {
        ShapelessCriteriaRecipeJsonBuilder builder = ShapelessCriteriaRecipeJsonBuilder.create(output, count);
        for (ItemConvertible item : ingredients) builder.input(item);
        return builder.build();
    }

    static ShapelessRecipeJsonBuilder shapeless(ItemConvertible[] from, ItemConvertible to, int count) {
        return shapeless(to, count, from);
    }

    static SingleItemRecipeJsonBuilder stonecutting(ItemConvertible from, ItemConvertible to, int count) {
        return SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(from), to, count).criterion(hasItem(from), conditionsFromItem(from));
    }

    static SingleItemRecipeJsonBuilder stonecutting(ItemConvertible from, ItemConvertible to) {
        return stonecutting(from, to, 1);
    }

    static ShapelessRecipeJsonBuilder planks(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(from)
                                         .group("planks")
                                         .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder planks(TagKey<Item> tag, ItemConvertible to, String criterion) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(tag)
                                         .group("planks")
                                         .criterion(criterion, conditionsFromTag(tag));
    }

    static ShapelessRecipeJsonBuilder planks(TagKey<Item> logs, ItemConvertible to) {
        return planks(logs, to, "has_logs");
    }

    static ShapedRecipeJsonBuilder wood(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .group("bark")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder boat(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .pattern("# #")
                                      .pattern("###")
                                      .group("boat")
                                      .criterion("in_water", requireEnteringFluid(Blocks.WATER));
    }

    static ShapelessRecipeJsonBuilder button(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(from)
                                         .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder woodenButton(ItemConvertible from, ItemConvertible to) {
        return button(from, to).group("wooden_button");
    }

    static ShapedRecipeJsonBuilder door(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenDoor(ItemConvertible from, ItemConvertible to) {
        return door(from, to).group("wooden_door");
    }

    static ShapedRecipeJsonBuilder fence(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', Items.STICK)
                                      .input('W', from)
                                      .pattern("W#W")
                                      .pattern("W#W")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenFence(ItemConvertible from, ItemConvertible to) {
        return door(from, to).group("wooden_fence");
    }

    static ShapedRecipeJsonBuilder fenceGate(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', Items.STICK)
                                      .input('W', from)
                                      .pattern("#W#")
                                      .pattern("#W#")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenFenceGate(ItemConvertible from, ItemConvertible to) {
        return fenceGate(from, to).group("wooden_fence_gate");
    }

    static ShapedRecipeJsonBuilder pressurePlate(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .pattern("##")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenPressurePlate(ItemConvertible from, ItemConvertible to) {
        return pressurePlate(from, to).group("wooden_pressure_plate");
    }

    static ShapedRecipeJsonBuilder slab(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 6)
                                      .input('#', from)
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenSlab(ItemConvertible from, ItemConvertible to) {
        return slab(from, to).group("wooden_slab");
    }

    static ShapedRecipeJsonBuilder stairs(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 4)
                                      .input('#', from)
                                      .pattern("#  ")
                                      .pattern("## ")
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenStairs(ItemConvertible from, ItemConvertible to) {
        return stairs(from, to).group("wooden_stairs");
    }

    static ShapedRecipeJsonBuilder trapdoor(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 2)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder woodenTrapdoor(ItemConvertible from, ItemConvertible to) {
        return trapdoor(from, to).group("wooden_trapdoor");
    }

    static ShapedRecipeJsonBuilder sign(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .group("sign")
                                      .input('#', from)
                                      .input('X', Items.STICK)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern(" X ")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder wool(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(from)
                                         .input(Blocks.WHITE_WOOL)
                                         .group("wool")
                                         .criterion("has_white_wool", conditionsFromItem(Blocks.WHITE_WOOL));
    }

    static ShapedRecipeJsonBuilder carpet(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .group("carpet")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder dyedCarpet(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.WHITE_CARPET)
                                      .input('$', from)
                                      .pattern("###")
                                      .pattern("#$#")
                                      .pattern("###")
                                      .group("carpet")
                                      .criterion("has_white_carpet", conditionsFromItem(Blocks.WHITE_CARPET))
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder bed(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .input('X', ItemTags.PLANKS)
                                      .pattern("###")
                                      .pattern("XXX")
                                      .group("bed")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapelessRecipeJsonBuilder dyedBed(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(Items.WHITE_BED)
                                         .input(from)
                                         .group("dyed_bed")
                                         .criterion("has_bed", conditionsFromItem(Items.WHITE_BED));
    }

    static ShapedRecipeJsonBuilder banner(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .input('|', Items.STICK)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern(" | ")
                                      .group("banner")
                                      .criterion(hasItem(from), conditionsFromItem(from));
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
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.GLASS_PANE)
                                      .input('$', from)
                                      .pattern("###")
                                      .pattern("#$#")
                                      .pattern("###")
                                      .group("stained_glass_pane")
                                      .criterion("has_glass_pane", conditionsFromItem(Blocks.GLASS_PANE))
                                      .criterion(hasItem(from), conditionsFromItem(from));
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

    static ShapedRecipeJsonBuilder condensing(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 4)
                                      .input('S', from)
                                      .pattern("SS")
                                      .pattern("SS")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder chiselCrafting(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .pattern("#")
                                      .pattern("#")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder cutCrafting(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 4)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static ShapedRecipeJsonBuilder wallCrafting(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 6)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .criterion(hasItem(from), conditionsFromItem(from));
    }

    static CookingRecipeJsonBuilder cracking(ItemConvertible from, ItemConvertible to) {
        return CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(from), to, 0.1F, 200)
                                       .criterion(hasItem(from), conditionsFromItem(from));
    }
}

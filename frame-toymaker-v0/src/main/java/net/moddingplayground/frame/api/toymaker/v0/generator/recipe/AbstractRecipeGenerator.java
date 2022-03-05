package net.moddingplayground.frame.api.toymaker.v0.generator.recipe;

import net.minecraft.advancement.criterion.EnterBlockCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.AbstractGenerator;
import net.moddingplayground.frame.mixin.toymaker.SingleItemRecipeJsonBuilderAccessor;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public abstract class AbstractRecipeGenerator extends AbstractGenerator<Identifier, CraftingRecipeJsonBuilder> {
    public AbstractRecipeGenerator(String modId) {
        super(modId);
    }

    public AbstractRecipeGenerator add(String id, CraftingRecipeJsonBuilder factory) {
        this.add(getId(id), factory);
        return this;
    }

    public SingleItemRecipeJsonBuilder copyFactory(SingleItemRecipeJsonBuilder factory, ItemConvertible newInput) {
        SingleItemRecipeJsonBuilderAccessor acco = (SingleItemRecipeJsonBuilderAccessor) factory;
        SingleItemRecipeJsonBuilder nu = SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(newInput), factory.getOutputItem(), acco.getCount())
                                                                    .group(acco.getGroup());
        ((SingleItemRecipeJsonBuilderAccessor) nu).setAdvancementBuilder(acco.getAdvancementBuilder());
        return nu;
    }

    protected static EnterBlockCriterion.Conditions inFluid(Block block) {
        return new EnterBlockCriterion.Conditions(EntityPredicate.Extended.EMPTY, block, StatePredicate.ANY);
    }

    public InventoryChangedCriterion.Conditions hasItem(ItemConvertible itemConvertible) {
        return this.hasItems(ItemPredicate.Builder.create().items(itemConvertible).build());
    }

    public InventoryChangedCriterion.Conditions hasItems(TagKey<Item> tag) {
        return this.hasItems(ItemPredicate.Builder.create().tag(tag).build());
    }

    public InventoryChangedCriterion.Conditions hasItems(ItemPredicate... predicates) {
        return new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, predicates);
    }

    public ShapedRecipeJsonBuilder generic3x3(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern("###")
                                      .criterion("has_ingredient", hasItem(from));
    }

    public ShapedRecipeJsonBuilder generic2x2(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion("has_ingredient", hasItem(from));
    }

    public ShapedRecipeJsonBuilder generic2x1(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .criterion("has_ingredient", hasItem(from));
    }

    public ShapedRecipeJsonBuilder generic2x3(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .pattern("##")
                                      .criterion("has_ingredient", hasItem(from));
    }

    public ShapedRecipeJsonBuilder generic3x1(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .criterion("has_ingredient", hasItem(from));
    }

    public ShapedRecipeJsonBuilder sandwich(ItemConvertible outside, ItemConvertible inside, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', outside)
                                      .input('X', inside)
                                      .pattern("###")
                                      .pattern("XXX")
                                      .pattern("###")
                                      .criterion("has_outside", hasItem(outside))
                                      .criterion("has_inside", hasItem(inside));
    }

    public ShapedRecipeJsonBuilder chequer2x2(ItemConvertible one, ItemConvertible two, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', one)
                                      .input('X', two)
                                      .pattern("#X")
                                      .pattern("X#")
                                      .criterion("has_one", hasItem(one))
                                      .criterion("has_two", hasItem(two));
    }

    public ShapedRecipeJsonBuilder ring(ItemConvertible from, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("# #")
                                      .pattern("###")
                                      .criterion("has_ingredient", hasItem(from));
    }

    public ShapedRecipeJsonBuilder ringSurrounding(ItemConvertible outside, ItemConvertible inside, ItemConvertible to, int count) {
        return ShapedRecipeJsonBuilder.create(to, count)
                                      .input('#', outside)
                                      .input('X', inside)
                                      .pattern("###")
                                      .pattern("#X#")
                                      .pattern("###")
                                      .criterion("has_outside", hasItem(outside))
                                      .criterion("has_inside", hasItem(inside));
    }

    public ShapelessRecipeJsonBuilder shapeless(ItemConvertible from, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(from)
                                         .criterion("has_ingredient", hasItem(from));
    }

    public ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(one)
                                         .input(two)
                                         .criterion("has_one", hasItem(one))
                                         .criterion("has_two", hasItem(two));
    }

    public ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible three, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(one)
                                         .input(two)
                                         .input(three)
                                         .criterion("has_one", hasItem(one))
                                         .criterion("has_two", hasItem(two))
                                         .criterion("has_three", hasItem(three));
    }

    public ShapelessRecipeJsonBuilder shapeless(ItemConvertible one, ItemConvertible two, ItemConvertible three, ItemConvertible four, ItemConvertible to, int count) {
        return ShapelessRecipeJsonBuilder.create(to, count)
                                         .input(one)
                                         .input(two)
                                         .input(three)
                                         .input(four)
                                         .criterion("has_one", hasItem(one))
                                         .criterion("has_two", hasItem(two))
                                         .criterion("has_three", hasItem(three))
                                         .criterion("has_four", hasItem(four));
    }

    public ShapelessRecipeJsonBuilder shapeless(ItemConvertible[] from, ItemConvertible to, int count) {
        ShapelessRecipeJsonBuilder factory = ShapelessRecipeJsonBuilder.create(to, count)
                                                                       .input(Ingredient.ofItems(from));
        for (ItemConvertible itemC : from) {
            Item item = itemC.asItem();
            String itemId = Registry.ITEM.getId(item)
                                         .getPath();
            factory.criterion("has_" + itemId, hasItem(itemC));
        }

        return factory;
    }

    public SingleItemRecipeJsonBuilder stonecutting(ItemConvertible from, ItemConvertible to, int count) {
        return SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(from), to, count)
                                          .criterion("has_item", hasItem(from));
    }

    public SingleItemRecipeJsonBuilder stonecutting(ItemConvertible from, ItemConvertible to) {
        return stonecutting(from, to, 1);
    }

    public ShapelessRecipeJsonBuilder planks(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(from)
                                         .group("planks")
                                         .criterion("has_log", hasItem(from));
    }

    public ShapelessRecipeJsonBuilder planks(TagKey<Item> from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(from)
                                         .group("planks")
                                         .criterion("has_log", hasItems(from));
    }

    public ShapelessRecipeJsonBuilder planksLogs(TagKey<Item> from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 4)
                                         .input(from)
                                         .group("planks")
                                         .criterion("has_logs", hasItems(from));
    }

    public ShapedRecipeJsonBuilder wood(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .group("bark")
                                      .criterion("has_log", hasItem(from));
    }

    public ShapedRecipeJsonBuilder boat(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .pattern("# #")
                                      .pattern("###")
                                      .group("boat")
                                      .criterion("in_water", inFluid(Blocks.WATER));
    }

    public ShapelessRecipeJsonBuilder woodenButton(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(from)
                                         .group("wooden_button")
                                         .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder woodenDoor(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .pattern("##")
                                      .pattern("##")
                                      .group("wooden_door")
                                      .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder woodenFence(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', Items.STICK)
                                      .input('W', from)
                                      .pattern("W#W")
                                      .pattern("W#W")
                                      .group("wooden_fence")
                                      .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder woodenFenceGate(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', Items.STICK)
                                      .input('W', from)
                                      .pattern("#W#")
                                      .pattern("#W#")
                                      .group("wooden_fence_gate")
                                      .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder woodenPressurePlate(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .pattern("##")
                                      .group("wooden_pressure_plate")
                                      .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder woodenSlab(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 6)
                                      .input('#', from)
                                      .pattern("###")
                                      .group("wooden_slab")
                                      .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder woodenStairs(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 4)
                                      .input('#', from)
                                      .pattern("#  ")
                                      .pattern("## ")
                                      .pattern("###")
                                      .group("wooden_stairs")
                                      .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder woodenTrapdoor(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 2)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .group("wooden_trapdoor")
                                      .criterion("has_planks", hasItem(from));
    }

    public ShapedRecipeJsonBuilder sign(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .group("sign")
                                      .input('#', from)
                                      .input('X', Items.STICK)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern(" X ")
                                      .criterion("has_" + string, hasItem(from));
    }

    public ShapelessRecipeJsonBuilder wool(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(from)
                                         .input(Blocks.WHITE_WOOL)
                                         .group("wool")
                                         .criterion("has_white_wool", hasItem(Blocks.WHITE_WOOL));
    }

    public ShapedRecipeJsonBuilder carpet(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to, 3)
                                      .input('#', from)
                                      .pattern("##")
                                      .group("carpet")
                                      .criterion("has_" + string, hasItem(from));
    }

    public ShapedRecipeJsonBuilder dyedCarpet(ItemConvertible from, ItemConvertible to) {
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
                                      .criterion("has_white_carpet", hasItem(Blocks.WHITE_CARPET))
                                      .criterion("has_" + string2, hasItem(from));
    }

    public ShapedRecipeJsonBuilder bed(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .input('X', ItemTags.PLANKS)
                                      .pattern("###")
                                      .pattern("XXX")
                                      .group("bed")
                                      .criterion("has_" + string, hasItem(from));
    }

    public ShapelessRecipeJsonBuilder dyedBed(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(to.asItem())
                                     .getPath();
        return ShapelessRecipeJsonBuilder.create(to)
                                         .input(Items.WHITE_BED)
                                         .input(from)
                                         .group("dyed_bed")
                                         .criterion("has_bed", hasItem(Items.WHITE_BED));
    }

    public ShapedRecipeJsonBuilder banner(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(from.asItem())
                                     .getPath();
        return ShapedRecipeJsonBuilder.create(to)
                                      .input('#', from)
                                      .input('|', Items.STICK)
                                      .pattern("###")
                                      .pattern("###")
                                      .pattern(" | ")
                                      .group("banner")
                                      .criterion("has_" + string, hasItem(from));
    }

    public ShapedRecipeJsonBuilder stainedGlass(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.GLASS)
                                      .input('X', from)
                                      .pattern("###")
                                      .pattern("#X#")
                                      .pattern("###")
                                      .group("stained_glass")
                                      .criterion("has_glass", hasItem(Blocks.GLASS));
    }

    public ShapedRecipeJsonBuilder stainedGlassPaneGlass(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 16)
                                      .input('#', from)
                                      .pattern("###")
                                      .pattern("###")
                                      .group("stained_glass_pane")
                                      .criterion("has_glass", hasItem(from));
    }

    public ShapedRecipeJsonBuilder stainedGlassPaneDye(ItemConvertible from, ItemConvertible to) {
        String string = Registry.ITEM.getId(to.asItem()).getPath();
        String string2 = Registry.ITEM.getId(from.asItem()).getPath();
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.GLASS_PANE)
                                      .input('$', from)
                                      .pattern("###")
                                      .pattern("#$#")
                                      .pattern("###")
                                      .group("stained_glass_pane")
                                      .criterion("has_glass_pane", hasItem(Blocks.GLASS_PANE))
                                      .criterion("has_" + string2, hasItem(from));
    }

    public ShapedRecipeJsonBuilder stainedTerracotta(ItemConvertible from, ItemConvertible to) {
        return ShapedRecipeJsonBuilder.create(to, 8)
                                      .input('#', Blocks.TERRACOTTA)
                                      .input('X', from)
                                      .pattern("###")
                                      .pattern("#X#")
                                      .pattern("###")
                                      .group("stained_terracotta")
                                      .criterion("has_terracotta", hasItem(Blocks.TERRACOTTA));
    }

    public ShapelessRecipeJsonBuilder concretePowder(ItemConvertible from, ItemConvertible to) {
        return ShapelessRecipeJsonBuilder.create(to, 8)
                                         .input(from)
                                         .input(Blocks.SAND, 4)
                                         .input(Blocks.GRAVEL, 4)
                                         .group("concrete_powder")
                                         .criterion("has_sand", hasItem(Blocks.SAND))
                                         .criterion("has_gravel", hasItem(Blocks.GRAVEL));
    }
}

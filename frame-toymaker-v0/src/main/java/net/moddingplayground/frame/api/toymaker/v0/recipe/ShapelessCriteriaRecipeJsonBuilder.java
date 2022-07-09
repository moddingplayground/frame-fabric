package net.moddingplayground.frame.api.toymaker.v0.recipe;

import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.TagKey;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.data.server.RecipeProvider.*;

/**
 * Builds a {@link ShapelessRecipeJsonBuilder} with inferred criteria.
 */
public class ShapelessCriteriaRecipeJsonBuilder {
    private final ShapelessRecipeJsonBuilder builder;
    private final List<String> criteria = new ArrayList<>();
    private int ingredients;

    public ShapelessCriteriaRecipeJsonBuilder(ItemConvertible output, int outputCount) {
        this.builder = ShapelessRecipeJsonBuilder.create(output, outputCount);
    }

    public ShapelessCriteriaRecipeJsonBuilder(ItemConvertible output) {
        this(output, 1);
    }

    public static ShapelessCriteriaRecipeJsonBuilder create(ItemConvertible output, int outputCount) {
        return new ShapelessCriteriaRecipeJsonBuilder(output, outputCount);
    }

    public static ShapelessCriteriaRecipeJsonBuilder create(ItemConvertible output) {
        return new ShapelessCriteriaRecipeJsonBuilder(output);
    }

    public ShapelessCriteriaRecipeJsonBuilder input(String criterion, TagKey<Item> tag) {
        this.builder.input(tag);
        this.incrementIngredientsAndAddCriterion(criterion, conditionsFromTag(tag));
        return this;
    }

    public ShapelessCriteriaRecipeJsonBuilder input(ItemConvertible item, int size) {
        this.builder.input(item, size);
        this.incrementIngredientsAndAddCriterion(hasItem(item), conditionsFromItem(item), size);
        return this;
    }

    public ShapelessCriteriaRecipeJsonBuilder input(ItemConvertible item) {
        return this.input(item, 1);
    }

    protected void incrementIngredientsAndAddCriterion(String id, InventoryChangedCriterion.Conditions conditions, int items) {
        this.ingredients += items;
        if (this.ingredients > 9) throw new IllegalStateException("A crafting recipe cannot have more than 9 ingredients!");

        if (!this.criteria.contains(id)) {
            this.builder.criterion(id, conditions);
            this.criteria.add(id);
        }
    }

    protected void incrementIngredientsAndAddCriterion(String id, InventoryChangedCriterion.Conditions conditions) {
        this.incrementIngredientsAndAddCriterion(id, conditions, 1);
    }

    public ShapelessRecipeJsonBuilder build() {
        return this.builder;
    }
}

package net.moddingplayground.frame.api.toymaker.v0.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.mixin.toymaker.FabricRecipeProviderAccessor;

import java.util.function.Consumer;

import static net.minecraft.data.server.RecipeProvider.*;

public class RecipeExporter {
    private final Consumer<RecipeJsonProvider> exporter;
    private final String modId;

    public RecipeExporter(Consumer<RecipeJsonProvider> exporter, String modId) {
        this.exporter = exporter;
        this.modId = modId;
    }

    public static RecipeExporter of(Consumer<RecipeJsonProvider> exporter, String modId) {
        return new RecipeExporter(exporter, modId);
    }

    public static RecipeExporter of(Consumer<RecipeJsonProvider> exporter, FabricDataGenerator dataGenerator) {
        return of(exporter, dataGenerator.getModId());
    }

    public static RecipeExporter of(Consumer<RecipeJsonProvider> exporter, FabricRecipeProvider provider) {
        return of(exporter, ((FabricRecipeProviderAccessor) provider).getDataGenerator());
    }

    public void export(CraftingRecipeJsonBuilder recipe, Identifier id) {
        recipe.offerTo(this.exporter, id);
    }

    public void exportExact(CraftingRecipeJsonBuilder recipe, String id) {
        this.export(recipe, new Identifier(this.modId, id));
    }

    public void export(CraftingRecipeJsonBuilder recipe, String id) {
        this.export(recipe, new Identifier(this.modId, recipe instanceof SingleItemRecipeJsonBuilder ? id + "_from_stonecutting" : id));
    }

    public void export(CraftingRecipeJsonBuilder recipe) {
        this.export(recipe, getItemPath(recipe.getOutputItem()));
    }

    public void export(ShapelessCriteriaRecipeJsonBuilder recipe, Identifier id) {
        this.export(recipe.build(), id);
    }

    public void export(ShapelessCriteriaRecipeJsonBuilder recipe, String id) {
        this.export(recipe.build(), id);
    }

    public void export(ShapelessCriteriaRecipeJsonBuilder recipe) {
        this.export(recipe.build());
    }
}

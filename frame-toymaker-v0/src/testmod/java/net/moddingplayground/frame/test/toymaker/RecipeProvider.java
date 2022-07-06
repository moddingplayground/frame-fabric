package net.moddingplayground.frame.test.toymaker;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.moddingplayground.frame.api.toymaker.v0.recipe.RecipeExporter;

import java.util.function.Consumer;

import static net.minecraft.item.Items.*;
import static net.moddingplayground.frame.api.toymaker.v0.recipe.RecipeJsonBuilders.*;

public final class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporterFunction) {
        RecipeExporter exporter = RecipeExporter.of(exporterFunction, this);
        exporter.export(ringSurrounding(STONE, DIAMOND, DIAMOND_ORE, 1), "stone_and_diamond_to_diamond_ore");
    }
}

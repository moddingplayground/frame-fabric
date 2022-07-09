package net.moddingplayground.frame.test.toymaker;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.tag.ItemTags;
import net.moddingplayground.frame.api.toymaker.v0.recipe.RecipeExporter;
import net.moddingplayground.frame.api.toymaker.v0.recipe.ShapelessCriteriaRecipeJsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static net.minecraft.item.Items.*;
import static net.moddingplayground.frame.api.toymaker.v0.recipe.RecipeJsonBuilders.*;

public final class RecipeProvider extends FabricRecipeProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger("frame-recipes-test");

    public RecipeProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporterFunction) {
        RecipeExporter exporter = RecipeExporter.of(exporterFunction, this);

        exporter.export(ringSurrounding(STONE, DIAMOND, DIAMOND_ORE, 1), "stone_and_diamond_to_diamond_ore");

        // generic shapeless recipe
        exporter.export(
            ShapelessCriteriaRecipeJsonBuilder.create(EMERALD_ORE)
                                              .input("has_planks", ItemTags.PLANKS)
                                              .input(NAME_TAG)
                                              .input(GLOWSTONE)
                                              .input(WHITE_BED)
        );

        // 9 manual inputs
        exporter.export(
            ShapelessCriteriaRecipeJsonBuilder.create(GOLD_ORE, 2)
                                              .input("has_planks", ItemTags.PLANKS)
                                              .input(NAME_TAG)
                                              .input(GLOWSTONE)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED) // 9
        );

        // 9 automatic inputs
        exporter.export(ShapelessCriteriaRecipeJsonBuilder.create(COAL_ORE, 5).input(WHITE_BED, 9));

        try {
            ShapelessCriteriaRecipeJsonBuilder.create(GOLD_ORE)
                                              .input("has_planks", ItemTags.PLANKS)
                                              .input(NAME_TAG)
                                              .input(GLOWSTONE)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED)
                                              .input(WHITE_BED); // 10
        } catch (IllegalStateException ignored) {
            LOGGER.info("Successfully thrown an exception when manually adding too many inputs (10)");
        }

        try {
            ShapelessCriteriaRecipeJsonBuilder.create(GOLD_ORE).input(WHITE_BED, 10);
        } catch (IllegalStateException ignored) {
            LOGGER.info("Successfully thrown an exception when automatically adding too many inputs (10)");
        }
    }
}

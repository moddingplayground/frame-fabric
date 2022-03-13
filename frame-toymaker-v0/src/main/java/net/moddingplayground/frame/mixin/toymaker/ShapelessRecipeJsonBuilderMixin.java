package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.impl.toymaker.DataMain;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ShapelessRecipeJsonBuilder.class)
public abstract class ShapelessRecipeJsonBuilderMixin {
    @Shadow @Final private Advancement.Builder advancementBuilder;
    @Shadow @Final private Item output;
    @Shadow @Final private int outputCount;
    @Shadow @Nullable private String group;
    @Shadow @Final private List<Ingredient> inputs;

    @Shadow protected abstract void validate(Identifier recipeId);

    @Inject(method = "offerTo", at = @At(value = "HEAD"), cancellable = true)
    private void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId, CallbackInfo ci) {
        Optional.ofNullable(DataMain.TARGET_MOD_ID).ifPresent(s -> {
            if (recipeId.getNamespace().equals(s)) {
                this.validate(recipeId);
                this.advancementBuilder.parent(new Identifier(s, "recipes/root"))
                                       .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                                       .rewards(AdvancementRewards.Builder.recipe(recipeId))
                                       .criteriaMerger(CriterionMerger.OR);

                exporter.accept(new ShapelessRecipeJsonBuilder.ShapelessRecipeJsonProvider(
                    recipeId, this.output, this.outputCount,
                    this.group == null
                        ? ""
                        : this.group,
                    this.inputs, this.advancementBuilder,
                    new Identifier(recipeId.getNamespace(), "recipes/" + recipeId.getPath())
                ));

                ci.cancel();
            }
        });
    }
}
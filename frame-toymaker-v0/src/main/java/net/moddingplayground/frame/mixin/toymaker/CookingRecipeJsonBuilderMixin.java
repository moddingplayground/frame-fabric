package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.recipe.CookingRecipeSerializer;
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

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(CookingRecipeJsonBuilder.class)
public abstract class CookingRecipeJsonBuilderMixin {
    @Shadow @Final private Advancement.Builder advancementBuilder;
    @Shadow @Nullable private String group;
    @Shadow @Final private Ingredient input;
    @Shadow @Final private Item output;
    @Shadow @Final private float experience;
    @Shadow @Final private int cookingTime;
    @Shadow @Final private CookingRecipeSerializer<?> serializer;

    @Shadow protected abstract void validate(Identifier recipeId);

    @Inject(method = "offerTo", at = @At("HEAD"), cancellable = true)
    private void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier id, CallbackInfo ci) {
        Optional.ofNullable(DataMain.TARGET_MOD_ID).ifPresent(s -> {
            if (id.getNamespace().equals(s)) {
                this.validate(id);
                this.advancementBuilder.parent(new Identifier(s, "recipes/root"))
                                       .criterion("has_the_recipe", RecipeUnlockedCriterion.create(id))
                                       .rewards(AdvancementRewards.Builder.recipe(id))
                                       .criteriaMerger(CriterionMerger.OR);

                exporter.accept(new CookingRecipeJsonBuilder.CookingRecipeJsonProvider(
                    id,
                    this.group == null
                        ? ""
                        : this.group,
                    this.input, this.output, this.experience, this.cookingTime, this.advancementBuilder,
                    new Identifier(id.getNamespace(), "recipes/" + id.getPath()), this.serializer
                ));

                ci.cancel();
            }
        });
    }
}

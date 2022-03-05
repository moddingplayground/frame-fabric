package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.advancement.Advancement;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingleItemRecipeJsonBuilder.class)
public interface SingleItemRecipeJsonBuilderAccessor {
    @Accessor Ingredient getInput();
    @Accessor String getGroup();
    @Accessor int getCount();

    @Accessor Advancement.Builder getAdvancementBuilder();
    @Mutable @Accessor void setAdvancementBuilder(Advancement.Builder builder);
}

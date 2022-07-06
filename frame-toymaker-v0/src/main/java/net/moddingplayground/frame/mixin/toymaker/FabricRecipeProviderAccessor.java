package net.moddingplayground.frame.mixin.toymaker;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FabricRecipeProvider.class)
public interface FabricRecipeProviderAccessor {
    @Accessor FabricDataGenerator getDataGenerator();
}

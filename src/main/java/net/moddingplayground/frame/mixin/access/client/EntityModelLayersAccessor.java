package net.moddingplayground.frame.mixin.access.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(EntityModelLayers.class)
public interface EntityModelLayersAccessor {
    @Accessor static String getMAIN() { throw new AssertionError(); }
}

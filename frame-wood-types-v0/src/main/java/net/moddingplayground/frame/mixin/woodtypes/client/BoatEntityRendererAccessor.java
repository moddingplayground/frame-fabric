package net.moddingplayground.frame.mixin.woodtypes.client;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(BoatEntityRenderer.class)
public interface BoatEntityRendererAccessor {
    @Accessor Map<BoatEntity.Type, Pair<Identifier, BoatEntityModel>> getTexturesAndModels();
}

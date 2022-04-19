package net.moddingplayground.frame.api.woodtypes.v0.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public interface FrameWoodTypesEntityModelLayers {
    Function<WoodType, EntityModelLayer> BOAT_LAYERS = Util.memoize(type -> {
        Identifier id = type.getId();
        String ns = id.getNamespace();
        String pa = id.getPath();

        EntityModelLayer layer = new EntityModelLayer(new Identifier(ns, "boat/" + pa), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, BoatEntityModel::getTexturedModelData);

        return layer;
    });
}

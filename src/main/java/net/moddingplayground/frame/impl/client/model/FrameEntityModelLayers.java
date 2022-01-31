package net.moddingplayground.frame.impl.client.model;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.block.wood.WoodBlockSet;
import net.moddingplayground.frame.api.registry.FrameRegistry;
import net.moddingplayground.frame.mixin.access.client.EntityModelLayersAccessor;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class FrameEntityModelLayers {
    public static final Map<WoodBlockSet, EntityModelLayer> BOAT_LAYERS = Util.make(ImmutableMap.<WoodBlockSet, EntityModelLayer>builder(), map -> {
        for (WoodBlockSet set : FrameRegistry.WOOD) {
            Identifier id = FrameRegistry.WOOD.getId(set);
            if (id == null) continue;

            Identifier identifier = new Identifier(id.getNamespace(), "boat/%s".formatted(id.getPath()));
            EntityModelLayer layer = new EntityModelLayer(identifier, EntityModelLayersAccessor.getMAIN());

            EntityModelLayerRegistry.registerModelLayer(layer, BoatEntityModel::getTexturedModelData);
            map.put(set, layer);
        }
    }).build();
}

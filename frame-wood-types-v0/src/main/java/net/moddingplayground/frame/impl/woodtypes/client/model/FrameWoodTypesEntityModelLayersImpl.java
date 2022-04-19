package net.moddingplayground.frame.impl.woodtypes.client.model;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.moddingplayground.frame.api.woodtypes.v0.FrameWoodTypes;
import net.moddingplayground.frame.api.woodtypes.v0.client.model.FrameWoodTypesEntityModelLayers;

@Environment(EnvType.CLIENT)
public class FrameWoodTypesEntityModelLayersImpl implements FrameWoodTypesEntityModelLayers, ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FrameWoodTypes.REGISTRY.forEach(BOAT_LAYERS::apply);
    }
}

package net.moddingplayground.frame.api.toymaker.v0.model;

import net.minecraft.data.client.TextureMap;
import net.minecraft.item.Item;

import static net.minecraft.data.client.ModelIds.*;
import static net.minecraft.data.client.TextureKey.*;
import static net.moddingplayground.frame.api.toymaker.v0.model.FrameTextureKeys.*;

public interface FrameTextureMaps {
    static TextureMap generatedOverlay(Item item) {
        return new TextureMap().put(LAYER0, getItemModelId(item)).put(LAYER1, getItemSubModelId(item, "_overlay"));
    }
}

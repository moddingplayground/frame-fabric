package net.moddingplayground.frame.api.toymaker.v0.model;

import net.minecraft.data.client.TextureKey;

public interface FrameTextureKeys {
    TextureKey LAYER1 = layer(1);

    static TextureKey layer(int i) {
        return TextureKey.of("layer" + i);
    }
}

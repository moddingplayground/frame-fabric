package net.moddingplayground.frame.api.toymaker.v0.model;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public interface FrameModels {
    Model GENERATED_OVERLAY = item(Identifier.DEFAULT_NAMESPACE, "generated", TextureKey.LAYER0, FrameTextureKeys.LAYER1);
    Model TEMPLATE_FLOOR_LAYER = block("template_floor_layer", TextureKey.TEXTURE);
    Model TEMPLATE_WALL_PLANT_THIN = block("template_wall_plant_thin", TextureKey.TEXTURE);

    static Model make(TextureKey... keys) {
        return new Model(Optional.empty(), Optional.empty(), keys);
    }

    static Model block(String parent, TextureKey... keys) {
        return new Model(Optional.of(new Identifier("frame", "block/" + parent)), Optional.empty(), keys);
    }

    static Model block(String parent, String variant, TextureKey... keys) {
        return new Model(Optional.of(new Identifier("frame", "block/" + parent)), Optional.of(variant), keys);
    }

    static Model item(String namespace, String parent, TextureKey... keys) {
        return new Model(Optional.of(new Identifier(namespace, "item/" + parent)), Optional.empty(), keys);
    }

    static Model item(String parent, TextureKey... keys) {
        return item("frame", parent, keys);
    }
}

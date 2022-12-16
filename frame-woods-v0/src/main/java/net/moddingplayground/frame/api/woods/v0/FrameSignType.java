package net.moddingplayground.frame.api.woods.v0;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.moddingplayground.frame.api.rendering.v0.SignTextureProvider;
import net.moddingplayground.frame.mixin.woods.SignTypeInvoker;

public class FrameSignType extends SignType implements SignTextureProvider {
    private final Identifier id;

    public FrameSignType(Identifier id) {
        super(id.toString());
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }

    @Override
    public Identifier getTexture(SignType type) {
        if (type instanceof FrameSignType frameType) {
            Identifier id = frameType.getId();
            return new Identifier(id.getNamespace(), "entity/signs/%s".formatted(id.getPath()));
        }

        throw new IllegalArgumentException("Type given must be of FrameSignType");
    }

    /**
     * Registers a {@link SignType} to the vanilla signs registry.
     */
    public static SignType register(SignType type) {
        return SignTypeInvoker.invokeRegister(type);
    }

    /**
     * Registers a {@link FrameSignType} to the vanilla registry
     * with the given {@link Identifier}.
     *
     * @apiNote this is probably the registry method that you
     *          want to use
     */
    public static FrameSignType register(Identifier id) {
        FrameSignType sign = (FrameSignType) register(new FrameSignType(id));
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            Identifier texture = new Identifier(id.getNamespace(), "entity/signs/%s".formatted(id.getPath()));
            TexturedRenderLayers.WOOD_TYPE_TEXTURES.put(
                sign, new SpriteIdentifier(
                    TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, texture
                )
            );
        };

        return sign;
    }
}

package net.moddingplayground.frame.api.banner;

import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.Identifier;

public enum BannerContext {
    BANNER(TexturedRenderLayers.BANNER_PATTERNS_ATLAS_TEXTURE),
    SHIELD(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE);

    /**
     * An ID for the context, for use in file paths.
     */
    private final String id;

    /**
     * The sprite atlas texture of the context.
     */
    private final Identifier atlas;

    BannerContext(Identifier atlas) {
        this.id = this.name().toLowerCase();
        this.atlas = atlas;
    }

    public String getId() {
        return this.id;
    }

    public Identifier getAtlas() {
        return this.atlas;
    }

    public static BannerContext from(boolean banner) {
        return banner ? BANNER : SHIELD;
    }
}

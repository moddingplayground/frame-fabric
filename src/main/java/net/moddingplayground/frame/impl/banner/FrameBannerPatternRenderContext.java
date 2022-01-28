package net.moddingplayground.frame.impl.banner;

import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;

import java.util.Collections;
import java.util.List;

public final class FrameBannerPatternRenderContext {
    private static List<FrameBannerPatternData> frameBannerPatterns = Collections.emptyList();

    private FrameBannerPatternRenderContext() {}

    /**
     * Set before {@link BannerBlockEntityRenderer#renderCanvas} is called.
     */
    public static void setFrameBannerPatterns(List<FrameBannerPatternData> patterns) {
        frameBannerPatterns = patterns;
    }

    public static List<FrameBannerPatternData> getFrameBannerPatterns() {
        return frameBannerPatterns;
    }
}

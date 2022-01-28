package net.moddingplayground.frame.impl.banner;

import net.minecraft.util.DyeColor;
import net.moddingplayground.frame.api.banner.FrameBannerPattern;

/**
 * Class to store frame banner pattern data in the banner - its pattern,
 * color, and the index (in the vanilla banner pattern list) that
 * this pattern appears before. This allows Frame banner patterns
 * to be used with vanilla banner patterns.
 */
public record FrameBannerPatternData(FrameBannerPattern pattern, DyeColor color, int index) {
    public FrameBannerPatternData {
        if (index < 0) {
            throw new IllegalArgumentException("index: " + index);
        }
    }
}

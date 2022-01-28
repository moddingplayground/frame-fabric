package net.moddingplayground.frame.api.banner;

import org.jetbrains.annotations.NotNull;

/**
 * Implement this on an Item to mark it as a pattern item.
 */
public interface FrameBannerPatternProvider {
    /**
     * @return The pattern associated with this item. Must not be null.
     */
    @NotNull FrameBannerPattern getPattern();
}

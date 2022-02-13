package net.moddingplayground.frame.api.bannerpatterns.v0;

/**
 * Implement this on an Item to mark it as a pattern item.
 */
public interface FrameBannerPatternProvider {
    /**
     * @return The pattern associated with this item. Must not be null.
     */
    FrameBannerPattern getPattern();
}

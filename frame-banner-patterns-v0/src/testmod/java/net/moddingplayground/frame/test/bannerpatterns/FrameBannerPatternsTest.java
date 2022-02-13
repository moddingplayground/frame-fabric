package net.moddingplayground.frame.test.bannerpatterns;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPatternItem;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPatterns;

public class FrameBannerPatternsTest implements ModInitializer {
    public static final FrameBannerPattern TEST_PATTERN = Registry.register(FrameBannerPatterns.REGISTRY, id("test_pattern"), new FrameBannerPattern());
    public static final Item TEST_PATTERN_ITEM = Registry.register(Registry.ITEM, id("test_pattern_item"), new FrameBannerPatternItem(TEST_PATTERN, new FabricItemSettings().fireproof()));

    @Override public void onInitialize() {}

    private static Identifier id(String id) {
        return new Identifier("frame-banner-patterns-test", id);
    }
}

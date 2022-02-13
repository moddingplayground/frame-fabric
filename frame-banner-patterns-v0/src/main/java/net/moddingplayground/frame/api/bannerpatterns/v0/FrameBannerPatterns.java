package net.moddingplayground.frame.api.bannerpatterns.v0;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.impl.bannerpatterns.FrameBannerPatternsImpl;

public final class FrameBannerPatterns {
    public static final SimpleRegistry<FrameBannerPattern> REGISTRY = FrameBannerPatternsImpl.BANNER_PATTERNS_REGISTRY;
    public static final RegistryKey<? extends Registry<FrameBannerPattern>> REGISTRY_KEY = REGISTRY.getKey();

    private FrameBannerPatterns() {}
}

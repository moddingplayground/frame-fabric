package net.moddingplayground.frame.api.banner;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.api.registry.FrameRegistries;

public final class FrameBannerPatterns {
    public static final SimpleRegistry<FrameBannerPattern> REGISTRY = FrameRegistries.BANNER_PATTERNS;
    public static final RegistryKey<? extends Registry<FrameBannerPattern>> REGISTRY_KEY = REGISTRY.getKey();

    private FrameBannerPatterns() {}
}

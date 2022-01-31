package net.moddingplayground.frame.api.banner;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.api.registry.FrameRegistry;

public final class FrameBannerPatterns {
    public static final SimpleRegistry<FrameBannerPattern> REGISTRY = FrameRegistry.BANNER_PATTERNS;
    public static final RegistryKey<? extends Registry<FrameBannerPattern>> REGISTRY_KEY = REGISTRY.getKey();

    private FrameBannerPatterns() {}
}

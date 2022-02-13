package net.moddingplayground.frame.impl.bannerpatterns;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;

public class FrameBannerPatternsImpl implements ModInitializer {
    public static final SimpleRegistry<FrameBannerPattern> BANNER_PATTERNS_REGISTRY =
        FabricRegistryBuilder.createSimple(FrameBannerPattern.class, new Identifier("frame", "banner_patterns"))
                             .attribute(RegistryAttribute.SYNCED)
                             .buildAndRegister();

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        Reflection.initialize(FrameBannerPatternsInternal.class);
    }
}

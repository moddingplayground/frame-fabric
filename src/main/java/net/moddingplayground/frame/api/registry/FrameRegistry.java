package net.moddingplayground.frame.api.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.api.banner.FrameBannerPattern;
import net.moddingplayground.frame.api.block.wood.WoodBlockSet;
import net.moddingplayground.frame.impl.Frame;

public final class FrameRegistry {
    public static final SimpleRegistry<FrameBannerPattern> BANNER_PATTERNS = FabricRegistryBuilder.createSimple(FrameBannerPattern.class, id("banner_patterns"))
                                                                                                  .attribute(RegistryAttribute.SYNCED)
                                                                                                  .buildAndRegister();

    public static final SimpleRegistry<WoodBlockSet> WOOD = FabricRegistryBuilder.createSimple(WoodBlockSet.class, id("wood"))
                                                                                 .attribute(RegistryAttribute.SYNCED)
                                                                                 .buildAndRegister();

    private FrameRegistry() {}

    private static Identifier id(String path) {
        return new Identifier(Frame.MOD_ID, path);
    }
}

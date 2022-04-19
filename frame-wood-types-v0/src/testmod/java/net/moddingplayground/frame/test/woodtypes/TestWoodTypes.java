package net.moddingplayground.frame.test.woodtypes;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.woodtypes.v0.FrameWoodTypes;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;

public interface TestWoodTypes {
    WoodType TEST = register("test_wood_type", WoodType.create()).register();

    private static WoodType register(String id, WoodType type) {
        return Registry.register(FrameWoodTypes.REGISTRY, new Identifier("frame-wood-types-test", id), type);
    }
}

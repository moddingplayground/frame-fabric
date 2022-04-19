package net.moddingplayground.frame.api.woodtypes.v0;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.impl.woodtypes.FrameWoodTypesImpl;

public interface FrameWoodTypes {
    SimpleRegistry<WoodType> REGISTRY = FrameWoodTypesImpl.WOOD_TYPES_REGISTRY;
    RegistryKey<? extends Registry<WoodType>> REGISTRY_KEY = REGISTRY.getKey();
}

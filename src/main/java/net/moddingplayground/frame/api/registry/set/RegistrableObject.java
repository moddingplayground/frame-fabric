package net.moddingplayground.frame.api.registry.set;

import net.minecraft.util.Identifier;

public interface RegistrableObject<T> {
    <R extends RegistrySet> T create(R set);
    <R extends RegistrySet> T register(Identifier id, Object object, R set);

    default String getId() { return ""; }
}

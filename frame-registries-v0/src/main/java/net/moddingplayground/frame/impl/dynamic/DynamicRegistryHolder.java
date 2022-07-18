package net.moddingplayground.frame.impl.dynamic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.Nullable;

/**
 * A nicer wrapper around {@link net.minecraft.util.registry.DynamicRegistryManager}.
 */
public record DynamicRegistryHolder<T>(SimpleRegistry<T> registry, BuiltinRegistries.Initializer<T> initializer, Codec<T> codec, @Nullable Codec<T> networkCodec) {
    public RegistryKey<? extends Registry<T>> getRegistryRef() {
        return this.registry.getKey();
    }

    public Lifecycle getLifecycle() {
        return this.registry.getLifecycle();
    }
}

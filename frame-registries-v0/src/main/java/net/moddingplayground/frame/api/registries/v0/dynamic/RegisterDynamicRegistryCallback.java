package net.moddingplayground.frame.api.registries.v0.dynamic;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.SimpleRegistry;

/**
 * Callback for registering dynamic registries.
 * <p>Is hooked inside the initialization of {@link DynamicRegistryManager#INFOS}, just before the map is built.</p>
 */
public interface RegisterDynamicRegistryCallback {
    Event<RegisterDynamicRegistryCallback> EVENT = EventFactory.createArrayBacked(RegisterDynamicRegistryCallback.class, listeners -> context -> {
        for (RegisterDynamicRegistryCallback event : listeners) event.registerDynamicRegistries(context);
    });

    void registerDynamicRegistries(Context context);

    interface Context {
        <T> void register(SimpleRegistry<T> registry, BuiltinRegistries.Initializer<T> initializer, Codec<T> codec, Codec<T> networkCodec);

        default <T> void register(SimpleRegistry<T> registry, BuiltinRegistries.Initializer<T> initializer, Codec<T> codec) {
            this.register(registry, initializer, codec, null);
        }
    }
}

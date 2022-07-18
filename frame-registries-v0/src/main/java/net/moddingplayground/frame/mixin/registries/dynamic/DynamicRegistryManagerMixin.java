package net.moddingplayground.frame.mixin.registries.dynamic;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.api.registries.v0.dynamic.EndDynamicRegistrySetupEntrypoint;
import net.moddingplayground.frame.api.registries.v0.dynamic.RegisterDynamicRegistryCallback;
import net.moddingplayground.frame.impl.dynamic.DynamicRegistryHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DynamicRegistryManager.class)
public interface DynamicRegistryManagerMixin {
    @Inject(
        method = "method_30531",
        at = @At(
            value = "INVOKE",
            target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;",
            shift = At.Shift.BEFORE
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void registerCustomDynamicRegistries(
        CallbackInfoReturnable<ImmutableMap<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>>> cir,
        ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder
    ) {
        // invoke entrypoints
        FabricLoader.getInstance()
                    .getEntrypoints(EndDynamicRegistrySetupEntrypoint.ENTRYPOINT_ID, EndDynamicRegistrySetupEntrypoint.class)
                    .forEach(EndDynamicRegistrySetupEntrypoint::afterDynamicRegistrySetup);

        // invoke events
        RegisterDynamicRegistryCallback.EVENT.invoker().registerDynamicRegistries(new RegisterDynamicRegistryCallback.Context() {
            @Override
            public <T> void register(SimpleRegistry<T> registry, BuiltinRegistries.Initializer<T> initializer, Codec<T> codec, Codec<T> networkCodec) {
                DynamicRegistryHolder<T> holder = new DynamicRegistryHolder<>(registry, initializer, codec, networkCodec);
                addCustomDynamicRegistry(holder);
                builder.put(holder.getRegistryRef(), createDynamicRegistryManagerInfo(holder));
            }
        });
    }

    @Unique
    private static <T> DynamicRegistryManager.Info<T> createDynamicRegistryManagerInfo(DynamicRegistryHolder<T> holder) {
        return new DynamicRegistryManager.Info<>(holder.getRegistryRef(), holder.codec(), holder.networkCodec());
    }

    @Unique
    private static <T> void addCustomDynamicRegistry(DynamicRegistryHolder<T> holder) {
        BuiltinRegistries.addRegistry(holder.getRegistryRef(), holder.registry(), holder.initializer(), holder.getLifecycle());
    }
}

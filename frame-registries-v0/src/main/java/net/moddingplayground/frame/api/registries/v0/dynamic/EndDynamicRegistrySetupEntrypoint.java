package net.moddingplayground.frame.api.registries.v0.dynamic;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;

/**
 * An entry point called upon the registry of vanilla's {@link BuiltinRegistries}
 * inside of {@link DynamicRegistryManager}.
 *
 * <p>In {@code fabric.mod.json}, the entrypoint is defined with {@value ENTRYPOINT_ID} key.</p>
 */
@FunctionalInterface
public interface EndDynamicRegistrySetupEntrypoint {
    String ENTRYPOINT_ID = "frame-registries:end_dynamic_registry_setup";
    void afterDynamicRegistrySetup();
}

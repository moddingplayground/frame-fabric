package net.moddingplayground.frame.test.registries;

import net.moddingplayground.frame.api.registries.v0.dynamic.EndDynamicRegistrySetupEntrypoint;
import net.moddingplayground.frame.api.registries.v0.dynamic.RegisterDynamicRegistryCallback;

public class FrameDynamicRegistriesTest implements EndDynamicRegistrySetupEntrypoint {
    @Override
    public void afterDynamicRegistrySetup() {
        RegisterDynamicRegistryCallback.EVENT.register(context -> {
            context.register(FrameRegistriesTest.TATER_REGISTRY, Tater::initAndGetDefault, Tater.CODEC, Tater.NETWORK_CODEC);
        });
    }
}

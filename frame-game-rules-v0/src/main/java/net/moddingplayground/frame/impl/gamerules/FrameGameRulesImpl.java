package net.moddingplayground.frame.impl.gamerules;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.moddingplayground.frame.api.gamerules.v0.SynchronizedBooleanGameRuleRegistry;

public final class FrameGameRulesImpl implements ModInitializer {
    @Override
    public void onInitialize() {
        // send game rule update on join
        SynchronizedBooleanGameRuleRegistryImpl impl = (SynchronizedBooleanGameRuleRegistryImpl) SynchronizedBooleanGameRuleRegistry.INSTANCE;
        ServerPlayConnectionEvents.INIT.register((handler, server) -> impl.values.keySet().forEach(key -> impl.synchronize(handler, server, key)));
    }
}

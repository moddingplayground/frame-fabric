package net.moddingplayground.frame.impl.gamerules;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.GameRules;
import net.moddingplayground.frame.api.gamerules.v0.SynchronizedBooleanGameRuleRegistry;

public final class FrameGameRulesImpl implements ModInitializer {
    @Override
    public void onInitialize() {
        SynchronizedBooleanGameRuleRegistryImpl impl = (SynchronizedBooleanGameRuleRegistryImpl) SynchronizedBooleanGameRuleRegistry.INSTANCE;

        // send game rule update on join
        ServerPlayConnectionEvents.INIT.register((handler, server) -> {
            GameRules rules = server.getGameRules();
            impl.values.keySet().forEach(key -> impl.synchronize(handler.player, rules.get(key)));
        });
    }
}

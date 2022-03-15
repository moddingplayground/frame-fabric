package net.moddingplayground.frame.impl.gamerules.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.moddingplayground.frame.api.gamerules.v0.SynchronizedBooleanGameRuleRegistry;
import net.moddingplayground.frame.impl.gamerules.SynchronizedBooleanGameRuleRegistryImpl;
import net.moddingplayground.frame.mixin.gamerules.GameRulesRuleAccessor;

import static net.minecraft.world.GameRules.*;

@Environment(EnvType.CLIENT)
public final class FrameGameRulesClientImpl implements ClientModInitializer {
    @SuppressWarnings("unchecked")
    @Override
    public void onInitializeClient() {
        SynchronizedBooleanGameRuleRegistryImpl impl = (SynchronizedBooleanGameRuleRegistryImpl) SynchronizedBooleanGameRuleRegistry.INSTANCE;

        // accept client game rule update
        ClientPlayNetworking.registerGlobalReceiver(SynchronizedBooleanGameRuleRegistryImpl.PACKET_ID, (client, handler, buf, sender) -> {
            String id = buf.readString();
            boolean value = buf.readBoolean();

            BooleanRule rule = impl.idCache.get().get(id);
            GameRulesRuleAccessor access = (GameRulesRuleAccessor) rule;
            Type<?> type = access.getType();
            impl.set((Key<BooleanRule>) SynchronizedBooleanGameRuleRegistryImpl.KEY_CACHE.apply(type), value);
        });

        // reset game rules on leave
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> impl.values.putAll(impl.defaults));
    }
}

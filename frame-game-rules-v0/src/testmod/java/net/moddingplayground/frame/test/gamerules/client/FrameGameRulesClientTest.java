package net.moddingplayground.frame.test.gamerules.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.moddingplayground.frame.api.gamerules.v0.SynchronizedBooleanGameRuleRegistry;
import net.moddingplayground.frame.test.gamerules.FrameGameRulesTest;

@Environment(EnvType.CLIENT)
public class FrameGameRulesClientTest implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.world == null) return;
            boolean val = SynchronizedBooleanGameRuleRegistry.INSTANCE.get(client.world, FrameGameRulesTest.TEST_GAME_RULE);
            client.player.sendMessage(Text.of("Client value: " + val), true);
        });
    }
}

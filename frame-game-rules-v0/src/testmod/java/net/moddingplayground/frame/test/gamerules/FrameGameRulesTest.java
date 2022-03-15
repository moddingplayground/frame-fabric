package net.moddingplayground.frame.test.gamerules;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameRules;
import net.moddingplayground.frame.api.gamerules.v0.SynchronizedBooleanGameRuleRegistry;

import java.util.function.BiConsumer;

import static net.minecraft.util.Identifier.*;
import static net.minecraft.world.GameRules.*;

public class FrameGameRulesTest implements ModInitializer {
    public static final Key<BooleanRule> TEST_GAME_RULE = synced("test_GAME_rule", false, (server, rule) -> {
        boolean val = rule.get();
        for (ServerPlayerEntity player : PlayerLookup.all(server)) player.sendMessage(new LiteralText("Server value updated: " + val), false);
    });

    @Override public void onInitialize() {}

    private static GameRules.Key<GameRules.BooleanRule> synced(String id, boolean defaultValue, BiConsumer<MinecraftServer, BooleanRule> onChanged) {
        GameRules.Key<GameRules.BooleanRule> key = register(id, SynchronizedBooleanGameRuleRegistry.createRule(defaultValue, onChanged));
        SynchronizedBooleanGameRuleRegistry.INSTANCE.register(key, defaultValue);
        return key;
    }

    private static <T extends Rule<T>> Key<T> register(String id, Type<T> type) {
        return GameRuleRegistry.register("frame-game-rules-test" + NAMESPACE_SEPARATOR + id, Category.MISC, type);
    }
}

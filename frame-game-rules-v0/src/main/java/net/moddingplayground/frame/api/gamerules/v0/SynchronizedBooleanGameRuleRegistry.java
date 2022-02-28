package net.moddingplayground.frame.api.gamerules.v0;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.moddingplayground.frame.impl.gamerules.SynchronizedBooleanGameRuleRegistryImpl;

import java.util.function.BiConsumer;

import static net.minecraft.world.GameRules.*;

public interface SynchronizedBooleanGameRuleRegistry {
    SynchronizedBooleanGameRuleRegistry INSTANCE = new SynchronizedBooleanGameRuleRegistryImpl();

    Key<BooleanRule> register(Key<BooleanRule> key, boolean defaultValue);
    boolean get(World world, Key<BooleanRule> key);

    /**
     * Creates a synchronized boolean rule type.
     *
     * @param defaultValue the default value of the game rule
     * @param changed a callback that is invoked when the value of a game rule has changed
     * @return a synchronized boolean rule type
     */
    static Type<BooleanRule> createRule(boolean defaultValue, BiConsumer<MinecraftServer, BooleanRule> changed) {
        return GameRuleFactory.createBooleanRule(defaultValue, (server, rule) -> {
            ((SynchronizedBooleanGameRuleRegistryImpl) INSTANCE).synchronize(server, rule);
            changed.accept(server, rule);
        });
    }

    /**
     * Creates a synchronized boolean rule type.
     *
     * @param defaultValue the default value of the game rule
     * @return a synchronized boolean rule type
     */
    static Type<BooleanRule> createRule(boolean defaultValue) {
        return createRule(defaultValue, (s, r) -> {});
    }
}

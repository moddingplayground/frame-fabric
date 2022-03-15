package net.moddingplayground.frame.impl.gamerules;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.moddingplayground.frame.api.gamerules.v0.SynchronizedBooleanGameRuleRegistry;
import net.moddingplayground.frame.mixin.gamerules.GameRulesAccessor;
import net.moddingplayground.frame.mixin.gamerules.GameRulesRuleAccessor;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.minecraft.world.GameRules.*;

public final class SynchronizedBooleanGameRuleRegistryImpl implements SynchronizedBooleanGameRuleRegistry {
    public static final Identifier PACKET_ID = new Identifier("frame", "game_rule_sync");
    public static final Function<Type<?>, Key<?>> KEY_CACHE = Util.memoize(SynchronizedBooleanGameRuleRegistryImpl::typeToKey);

    public final Map<Key<BooleanRule>, Boolean> defaults, values;
    public final Supplier<Map<String, BooleanRule>> idCache;

    public SynchronizedBooleanGameRuleRegistryImpl() {
        this.defaults = Maps.newHashMap();
        this.values = Maps.newHashMap();

        this.idCache = Suppliers.memoize(() -> Util.make(ImmutableMap.<String, BooleanRule>builder(), map -> {
            GameRules rules = new GameRules();
            this.values.keySet().forEach(key -> map.put(key.getName(), rules.get(key)));
        }).build());
    }

    @Override
    public Key<BooleanRule> register(Key<BooleanRule> key, boolean defaultValue) {
        this.defaults.put(key, defaultValue);
        this.values.put(key, defaultValue);
        return key;
    }

    @Override
    public boolean get(World world, Key<BooleanRule> key) {
        return world.isClient ? this.values.get(key) : world.getGameRules().getBoolean(key);
    }

    public void set(Key<BooleanRule> key, boolean value) {
        this.values.put(key, value);
    }

    public void synchronize(MinecraftServer server, BooleanRule rule) {
        GameRulesRuleAccessor access = (GameRulesRuleAccessor) rule;
        Key<?> key = KEY_CACHE.apply(access.getType());
        for (ServerPlayerEntity player : PlayerLookup.all(server)) this.synchronize(player, key.getName(), rule.get());
    }

    public void synchronize(ServerPlayNetworkHandler handler, MinecraftServer server, Key<BooleanRule> key) {
        GameRules rules = server.getGameRules();
        BooleanRule rule = rules.get(key);
        this.synchronize(handler.player, key.getName(), rule.get());
    }

    public void synchronize(ServerPlayerEntity player, String id, boolean value) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(id);
        buf.writeBoolean(value);
        ServerPlayNetworking.send(player, PACKET_ID, buf);
    }

    public static Key<?> typeToKey(Type<?> type) {
        Map<Key<?>, Type<?>> map = GameRulesAccessor.getRULE_TYPES();
        Set<Map.Entry<Key<?>, Type<?>>> entries = map.entrySet();
        Map<Type<?>, Key<?>> inverse = entries.stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        return inverse.get(type);
    }
}

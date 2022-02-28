package net.moddingplayground.frame.impl.gamerules;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.moddingplayground.frame.api.gamerules.v0.SynchronizedBooleanGameRuleRegistry;
import net.moddingplayground.frame.api.util.ReverseMemoizeFunction;
import net.moddingplayground.frame.mixin.gamerules.GameRulesAccessor;
import net.moddingplayground.frame.mixin.gamerules.GameRulesRuleAccessor;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.minecraft.world.GameRules.*;

public final class SynchronizedBooleanGameRuleRegistryImpl implements SynchronizedBooleanGameRuleRegistry {
    public static final Identifier PACKET_ID = new Identifier("frame", "game_rule_sync");

    public final Function<Type<BooleanRule>, Key<BooleanRule>> typeToKeyCache;
    public final ReverseMemoizeFunction<BooleanRule, Identifier> ruleToIdCache;
    public final Map<Key<BooleanRule>, Boolean> defaults, values;

    public SynchronizedBooleanGameRuleRegistryImpl() {
        this.typeToKeyCache = Util.memoize(this::typeToKey);
        this.ruleToIdCache = ReverseMemoizeFunction.create(this::ruleToId);

        this.defaults = Maps.newHashMap();
        this.values = Maps.newHashMap();
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
        Identifier id = this.ruleToIdCache.apply(rule);
        if (id == null) return;
        for (ServerPlayerEntity player : PlayerLookup.all(server)) this.synchronize(player, id, rule.get());
    }

    public void synchronize(ServerPlayerEntity player, BooleanRule rule) {
        Identifier id = this.ruleToIdCache.apply(rule);
        if (id == null) return;
        this.synchronize(player, id, rule.get());
    }

    public void synchronize(ServerPlayerEntity player, Identifier id, boolean value) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIdentifier(id);
        buf.writeBoolean(value);
        ServerPlayNetworking.send(player, PACKET_ID, buf);
    }

    @SuppressWarnings("unchecked")
    private Key<BooleanRule> typeToKey(Type<BooleanRule> type) {
        Map<Key<?>, Type<?>> map = GameRulesAccessor.getRULE_TYPES();
        Set<Map.Entry<Key<?>, Type<?>>> entries = map.entrySet();
        Map<Type<?>, Key<?>> inverse = entries.stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        return (Key<BooleanRule>) inverse.get(type);
    }

    public Identifier ruleToId(BooleanRule rule) {
        Key<BooleanRule> key = this.typeToKeyCache.apply(((GameRulesRuleAccessor) rule).getType());
        String name = key.getName();
        return Identifier.tryParse(name);
    }
}

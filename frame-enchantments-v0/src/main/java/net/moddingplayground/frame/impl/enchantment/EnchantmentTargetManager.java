package net.moddingplayground.frame.impl.enchantment;

import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.enchantment.v0.target.EnchantmentTargetInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentTargetManager {
    private final Map<Identifier, EnchantmentTargetInfo> registry = new HashMap<>();

    protected EnchantmentTargetManager() {}

    public EnchantmentTargetInfo register(Identifier id, String className) {
        EnchantmentTargetInfo info = new EnchantmentTargetInfo(id, className);
        this.registry.put(info.id(), info);
        return info;
    }

    public List<EnchantmentTargetInfo> values() {
        return new ArrayList<>(this.registry.values());
    }
}

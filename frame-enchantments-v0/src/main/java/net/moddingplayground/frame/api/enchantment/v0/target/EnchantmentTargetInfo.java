package net.moddingplayground.frame.api.enchantment.v0.target;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.util.Identifier;

import java.util.Locale;

public record EnchantmentTargetInfo(Identifier id, String className) {
    public String getEnumName() {
        return this.id.toUnderscoreSeparatedString().toUpperCase(Locale.ROOT);
    }

    public EnchantmentTarget getEnchantmentTarget() {
        return EnchantmentTarget.valueOf(this.getEnumName());
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}

package net.moddingplayground.frame.api.enchantment.v0.target;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.moddingplayground.frame.api.enchantment.v0.FrameEnchantmentTargetsEntrypoint;
import net.moddingplayground.frame.mixin.enchantment.EnchantmentTargetMixin;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;

/**
 * Wrapper class to extend {@link EnchantmentTarget}.
 * Not to be instantiated! Register through class name by {@link FrameEnchantmentTargetsEntrypoint}.
 */
@ApiStatus.OverrideOnly
public abstract class CustomEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        throw new NotImplementedException();
    }
}

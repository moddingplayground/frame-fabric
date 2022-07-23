package net.moddingplayground.frame.test.enchantments.target;

import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.enchantment.v0.FrameEnchantmentTargetsEntrypoint;
import net.moddingplayground.frame.api.enchantment.v0.target.EnchantmentTargetInfo;
import net.moddingplayground.frame.impl.enchantment.EnchantmentTargetManager;
import net.moddingplayground.frame.test.enchantments.FrameEnchantmentsTest;

public final class FrameEnchantmentsTestEnchantmentTargets implements FrameEnchantmentTargetsEntrypoint {
    public static EnchantmentTargetInfo TEST_ENCHANTMENT_TARGET;

    @Override
    public void registerEnchantmentTargets(EnchantmentTargetManager manager) {
        TEST_ENCHANTMENT_TARGET = manager.register(new Identifier(FrameEnchantmentsTest.MOD_ID, "test_enchantment_target"), "net.moddingplayground.frame.test.enchantments.target.FrameTestEnchantmentTarget");
    }
}

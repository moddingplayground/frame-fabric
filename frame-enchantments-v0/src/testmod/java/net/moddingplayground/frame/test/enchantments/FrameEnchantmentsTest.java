package net.moddingplayground.frame.test.enchantments;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.test.enchantments.target.FrameEnchantmentsTestTargets;

import java.util.Arrays;

public final class FrameEnchantmentsTest implements ModInitializer {
    public static final String MOD_ID = "frame-enchantments-test";

    public static final Enchantment TEST_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "test_enchantment"), new TestEnchantment(Enchantment.Rarity.COMMON, FrameEnchantmentsTestTargets.TEST_ENCHANTMENT_TARGET, new EquipmentSlot[]{ EquipmentSlot.CHEST }));

    @Override
    public void onInitialize() {
        System.out.println(Arrays.toString(EnchantmentTarget.values()));
    }
}

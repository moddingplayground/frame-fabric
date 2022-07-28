package net.moddingplayground.frame.test.enchantments.target;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.moddingplayground.frame.api.enchantment.v0.target.CustomEnchantmentTarget;

public class FrameTestEnchantmentTarget extends CustomEnchantmentTarget {
    @Override
    public boolean isCustomAcceptableItem(Item item) {
        return item == Items.SPONGE;
    }
}

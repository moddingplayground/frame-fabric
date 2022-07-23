package net.moddingplayground.frame.api.enchantment.v0;

import net.moddingplayground.frame.impl.enchantment.EnchantmentTargetManager;

/**
 * An entry point for registering {@code EnchantmentTarget}s.
 *
 * <p>In {@code fabric.mod.json}, the entrypoint is defined with {@value ENTRYPOINT_ID} key.</p>
 *
 * @apiNote You may not reference any {@link net.minecraft} classes within any called methods
 *          in this entry point!
 */
public interface FrameEnchantmentTargetsEntrypoint {
    String ENTRYPOINT_ID = "frame-enchantments:targets";
    default void registerEnchantmentTargets(EnchantmentTargetManager manager) {}
}

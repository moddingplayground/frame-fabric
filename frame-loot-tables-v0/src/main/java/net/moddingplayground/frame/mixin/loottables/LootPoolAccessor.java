package net.moddingplayground.frame.mixin.loottables;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.function.LootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.class)
public interface LootPoolAccessor {
    @Mutable @Accessor void setFunctions(LootFunction[] functions);
}

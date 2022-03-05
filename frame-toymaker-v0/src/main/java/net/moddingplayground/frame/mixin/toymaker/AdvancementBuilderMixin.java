package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(Advancement.Builder.class)
public class AdvancementBuilderMixin {
    @Redirect(method = "criterion(Ljava/lang/String;Lnet/minecraft/advancement/AdvancementCriterion;)Lnet/minecraft/advancement/Advancement$Builder;", at = @At(value = "INVOKE", target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z"))
    private boolean onContainsKey(Map<String, AdvancementCriterion> instance, Object key) {
        return false;
    }
}

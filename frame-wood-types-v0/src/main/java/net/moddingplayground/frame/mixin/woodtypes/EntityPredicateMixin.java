package net.moddingplayground.frame.mixin.woodtypes;

import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.moddingplayground.frame.impl.woodtypes.FrameWoodTypesImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Replaces checks for Frame boats with a check for vanilla boats.
 */
public class EntityPredicateMixin {
    @Mixin(EntityTypePredicate.Single.class)
    private abstract static class Single {
        @Inject(method = "matches", at = @At("RETURN"), cancellable = true)
        private void onMatches(EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
            EntityTypePredicate.Single that = (EntityTypePredicate.Single) (Object) this;
            FrameWoodTypesImpl.replaceBoatPredicate(that, type, cir);
        }
    }

    @Mixin(EntityTypePredicate.Tagged.class)
    private abstract static class Tagged {
        @Inject(method = "matches", at = @At("RETURN"), cancellable = true)
        private void onMatches(EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
            EntityTypePredicate.Tagged that = (EntityTypePredicate.Tagged) (Object) this;
            FrameWoodTypesImpl.replaceBoatPredicate(that, type, cir);
        }
    }
}

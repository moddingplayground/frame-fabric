package net.moddingplayground.frame.mixin.block.wood;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityPredicate;
import net.moddingplayground.frame.api.block.wood.FrameBoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPredicate.class)
public class EntityPredicateMixin {
    // replace predicates testing for Frame boat with vanilla boat (for things like advancements)
    @Redirect(method = "test(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/entity/Entity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getType()Lnet/minecraft/entity/EntityType;"))
    private EntityType<?> onTest(Entity entity) {
        return entity instanceof FrameBoatEntity ? EntityType.BOAT : entity.getType();
    }
}

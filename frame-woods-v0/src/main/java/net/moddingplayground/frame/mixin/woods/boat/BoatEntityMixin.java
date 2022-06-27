package net.moddingplayground.frame.mixin.woods.boat;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.moddingplayground.frame.impl.woods.boat.BoatEntityTypeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow public abstract BoatEntity.Type getBoatType();

    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    private void onAsItem(CallbackInfoReturnable<Item> cir) {
        BoatEntity.Type type = this.getBoatType();
        BoatEntityTypeAccess access = BoatEntityTypeAccess.class.cast(type);
        access.getFrameData().ifPresent(data -> cir.setReturnValue(data.getBoatItem()));
    }
}

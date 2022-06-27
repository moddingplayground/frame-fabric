package net.moddingplayground.frame.mixin.woods.boat.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.impl.woods.boat.BoatEntityTypeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BoatEntityRenderer.class)
public class BoatEntityRendererMixin {
    @Inject(
        method = "getTexture(Lnet/minecraft/entity/vehicle/BoatEntity$Type;Z)Ljava/lang/String;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void onGetTexture(BoatEntity.Type type, boolean chest, CallbackInfoReturnable<String> cir) {
        BoatEntityTypeAccess.class.cast(type).getFrameData().ifPresent(data -> {
            Identifier id = data.getId();
            String path = (chest ? "textures/entity/chest_boat/%s.png" : "textures/entity/boat/%s.png").formatted(id.getPath());
            cir.setReturnValue(new Identifier(id.getNamespace(), path).toString());
        });
    }
}

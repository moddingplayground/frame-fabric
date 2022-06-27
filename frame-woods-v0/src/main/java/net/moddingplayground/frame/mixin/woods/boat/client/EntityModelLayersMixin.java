package net.moddingplayground.frame.mixin.woods.boat.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.impl.woods.boat.BoatEntityTypeAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(EntityModelLayers.class)
public class EntityModelLayersMixin {
    @Shadow @Final private static String MAIN;

    @Inject(method = "createBoat", at = @At("HEAD"), cancellable = true)
    private static void onCreateBoat(BoatEntity.Type type, CallbackInfoReturnable<EntityModelLayer> cir) {
        frame_replaceBoat(type, "boat", cir);
    }

    @Inject(method = "createChestBoat", at = @At("HEAD"), cancellable = true)
    private static void onCreateChestBoat(BoatEntity.Type type, CallbackInfoReturnable<EntityModelLayer> cir) {
        frame_replaceBoat(type, "chest_boat", cir);
    }

    @Unique
    private static void frame_replaceBoat(BoatEntity.Type type, String path, CallbackInfoReturnable<EntityModelLayer> cir) {
        BoatEntityTypeAccess.class.cast(type).getFrameData().ifPresent(data -> {
            Identifier id = data.getId();
            Identifier layerId = new Identifier(id.getNamespace(), "%s/%s".formatted(path, id.getPath()));
            cir.setReturnValue(new EntityModelLayer(layerId, MAIN));
        });
    }
}

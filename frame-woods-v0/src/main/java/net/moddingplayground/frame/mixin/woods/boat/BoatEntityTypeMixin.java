package net.moddingplayground.frame.mixin.woods.boat;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.woods.v0.FrameWoodsEntrypoint;
import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeData;
import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeManager;
import net.moddingplayground.frame.impl.woods.boat.BoatEntityTypeAccess;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@SuppressWarnings({ "unused", "target" })
@Mixin(BoatEntity.Type.class)
public class BoatEntityTypeMixin implements BoatEntityTypeAccess {
    @Invoker("<init>") private static BoatEntity.Type invokeInit(String internalName, int internalId, Block baseBlock, String name) { throw new AssertionError(); }
    @Shadow @Final @Mutable private static BoatEntity.Type[] field_7724; // VALUES

    @Unique private FrameBoatTypeData boatTypeData = null;

    @Inject(
        method = "<clinit>",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/vehicle/BoatEntity$Type;field_7724:[Lnet/minecraft/entity/vehicle/BoatEntity$Type;",
            opcode = Opcodes.PUTSTATIC,
            shift = At.Shift.AFTER
        )
    )
    private static void onInitAddCustomTypes(CallbackInfo ci) {
        FrameBoatTypeManager manager = FrameBoatTypeManager.INSTANCE;
        FabricLoader loader = FabricLoader.getInstance();
        loader.getEntrypoints("frame-woods", FrameWoodsEntrypoint.class).forEach(e -> e.registerBoatTypes(manager));
        List<FrameBoatTypeData> newTypes = manager.values();
        if (!newTypes.isEmpty()) {
            List<BoatEntity.Type> values = new ArrayList<>(Arrays.asList(field_7724));
            int startIndex = values.size();

            for (int i = 0, l = newTypes.size(); i < l; i++) {
                FrameBoatTypeData data = newTypes.get(i);

                Identifier id = data.getId();
                BoatEntity.Type type = invokeInit(
                    id.toUnderscoreSeparatedString().toUpperCase(Locale.ROOT),
                    startIndex + i,
                    data.getBase(),
                    id.toString()
                );

                BoatEntityTypeAccess.class.cast(type).setFrameData(data);

                values.add(type);
                data.set(type);
            }


            field_7724 = values.toArray(BoatEntity.Type[]::new);
        }
    }

    @Unique
    @Override
    public Optional<FrameBoatTypeData> getFrameData() {
        return Optional.ofNullable(this.boatTypeData);
    }

    @Unique
    @Override
    public void setFrameData(FrameBoatTypeData data) {
        this.boatTypeData = data;
    }
}

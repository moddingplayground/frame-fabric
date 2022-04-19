package net.moddingplayground.frame.impl.woodtypes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import net.moddingplayground.frame.api.woodtypes.v0.FrameWoodTypesEntrypoint;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;
import net.moddingplayground.frame.api.woodtypes.v0.entity.FrameWoodEntityType;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class FrameWoodTypesImpl implements ModInitializer {
    public static final SimpleRegistry<WoodType> WOOD_TYPES_REGISTRY =
        FabricRegistryBuilder.createSimple(WoodType.class, new Identifier("frame", "wood_types"))
                             .attribute(RegistryAttribute.SYNCED)
                             .buildAndRegister();

    @Override
    public void onInitialize() {
        // register wood types for other mods
        FabricLoader.getInstance()
                    .getEntrypoints("frame-wood-types", FrameWoodTypesEntrypoint.class)
                    .forEach(FrameWoodTypesEntrypoint::onInitializeFrameWoodTypes);
    }

    public static void replaceBoatPredicate(EntityTypePredicate that, EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && type == FrameWoodEntityType.BOAT) {
            if (that.matches(EntityType.BOAT)) cir.setReturnValue(true);
        }
    }
}

package net.moddingplayground.frame.mixin.contentregistries.state;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    private final @Unique Function<BlockState, Boolean> frame_supports = Util.memoize(state -> {
        BlockEntityType<?> that = (BlockEntityType<?>) (Object) this;
        StateRegistry registry = StateRegistry.BLOCK_ENTITY_SUPPORTS.apply(that);
        return registry.contains(state);
    });

    @Inject(method = "supports", at = @At("RETURN"), cancellable = true)
    private void onSupports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && this.frame_supports.apply(state)) cir.setReturnValue(true);
    }
}

package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.server.dedicated.EulaReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EulaReader.class)
public class EulaReaderMixin {
    @Inject(method = "isEulaAgreedTo", at = @At("HEAD"))
    private void onIsEulaAgreedTo(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}

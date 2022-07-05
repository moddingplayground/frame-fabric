package net.moddingplayground.frame.mixin.contentregistries.client.splash;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.moddingplayground.frame.api.contentregistries.v0.client.SplashesRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    @Shadow @Final private List<String> splashTexts;
    @Shadow @Final private static Random RANDOM;

    @Inject(
        method = "get",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;get(I)Ljava/lang/Object;",
            shift = At.Shift.BEFORE
        ),
        cancellable = true
    )
    private void onGet(CallbackInfoReturnable<String> cir) {
        List<Supplier<Text>> extra = SplashesRegistry.INSTANCE.values();
        int vanilla = this.splashTexts.size();
        int index = RANDOM.nextInt(vanilla + extra.size());
        if (index > vanilla) cir.setReturnValue(null);
    }
}

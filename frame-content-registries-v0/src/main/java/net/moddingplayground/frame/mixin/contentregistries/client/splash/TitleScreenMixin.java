package net.moddingplayground.frame.mixin.contentregistries.client.splash;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import net.moddingplayground.frame.api.contentregistries.v0.client.SplashesRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow @Nullable private String splashText;

    @Unique private Text frameSplashText;

    private TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(
        method = "init",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/gui/screen/TitleScreen;splashText:Ljava/lang/String;",
            ordinal = 1,
            shift = At.Shift.AFTER
        )
    )
    private void onInitSplashText(CallbackInfo ci) {
        if (this.splashText == null) {
            List<Supplier<Text>> extra = SplashesRegistry.INSTANCE.values();
            if (!extra.isEmpty()) this.frameSplashText = extra.get(Random.create().nextInt(extra.size())).get();
        }
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(
        method = "render",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/gui/screen/TitleScreen;splashText:Ljava/lang/String;",
            ordinal = 0,
            shift = At.Shift.BEFORE
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onRenderSplashText(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci, float f, int i, int j, float g, int l) {
        if (this.splashText == null && this.frameSplashText != null) {
            matrices.push();

            matrices.translate(this.width / 2D + 90, 70.0, 0.0);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-20.0F));

            float scale = (1.8F - MathHelper.abs(
                MathHelper.sin(
                    (float) (Util.getMeasuringTimeMs() % 1000L) / 1000.0F * (float) (Math.PI * 2)
                ) * 0.1F
            )) * 100F / (float)(this.textRenderer.getWidth(this.frameSplashText) + 32);

            matrices.scale(scale, scale, scale);
            drawCenteredText(matrices, this.textRenderer, this.frameSplashText, 0, -8, 0xFFFF00 | l);

            matrices.pop();
        }
    }
}

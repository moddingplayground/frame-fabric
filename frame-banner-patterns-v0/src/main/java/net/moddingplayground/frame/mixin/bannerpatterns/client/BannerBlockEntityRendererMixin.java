package net.moddingplayground.frame.mixin.bannerpatterns.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.bannerpatterns.v0.BannerContext;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.impl.bannerpatterns.FrameBannerPatternAccess;
import net.moddingplayground.frame.impl.bannerpatterns.FrameBannerPatternData;
import net.moddingplayground.frame.impl.bannerpatterns.FrameBannerPatternRenderContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

@Environment(EnvType.CLIENT)
@Mixin(BannerBlockEntityRenderer.class)
public abstract class BannerBlockEntityRendererMixin {
    @Unique private static List<FrameBannerPatternData> frame_bannerPatterns;
    @Unique private static int frame_nextPatternIndex;

    /**
     * Saves Frame banner pattern in a field for rendering.
     */
    @Inject(method = "render(Lnet/minecraft/block/entity/BannerBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"))
    private void preFrameBannerPatternRender(BannerBlockEntity blockEntity, float tickDelta, MatrixStack stack, VertexConsumerProvider vertices, int light, int overlay, CallbackInfo ci) {
        FrameBannerPatternRenderContext.setFrameBannerPatterns(((FrameBannerPatternAccess) blockEntity).frame_getBannerPatterns());
    }

    @Inject(
        method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;Z)V",
        at = @At("HEAD")
    )
    private static void frameResetLocalCtx(CallbackInfo info) {
        frame_nextPatternIndex = 0;
        frame_bannerPatterns = FrameBannerPatternRenderContext.getFrameBannerPatterns();
    }

    /**
     * Renders Frame banner patterns in line with vanilla banner patterns.
     */
    @Inject(
        method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;get(I)Ljava/lang/Object;",
            ordinal = 0,
            remap = false
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void frameBannerPatternRenderInline(MatrixStack stack, VertexConsumerProvider vertices, int light, int overlay, ModelPart part, SpriteIdentifier sprite, boolean banner, List<Pair<BannerPattern, DyeColor>> patterns, boolean glint, CallbackInfo ci, int idx) {
        while (frame_nextPatternIndex < frame_bannerPatterns.size()) {
            FrameBannerPatternData data = frame_bannerPatterns.get(frame_nextPatternIndex);
            if (data.index() == idx - 1) {
                frame_renderBannerPattern(data, stack, vertices, part, light, overlay, banner);
                frame_nextPatternIndex++;
            } else break;
        }
    }

    /**
     * Renders Frame banner patterns that occur after all vanilla banner patterns.
     */
    @Inject(
        method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;Z)V",
        at = @At("RETURN")
    )
    private static void frameBannerPatternRenderPost(MatrixStack stack, VertexConsumerProvider vertices, int light, int overlay, ModelPart part, SpriteIdentifier sprite, boolean banner,
        List<Pair<BannerPattern, DyeColor>> patterns, boolean glint, CallbackInfo ci) {
        for (int i = frame_nextPatternIndex; i < frame_bannerPatterns.size(); i++) {
            frame_renderBannerPattern(frame_bannerPatterns.get(i), stack, vertices, part, light, overlay, banner);
        }
        frame_bannerPatterns = Collections.emptyList();
    }

    @Unique private static final BiFunction<Identifier, Identifier, SpriteIdentifier> FRAME_SPRITE_IDS = Util.memoize(SpriteIdentifier::new);

    @Unique
    private static void frame_renderBannerPattern(FrameBannerPatternData data, MatrixStack stack, VertexConsumerProvider vertices, ModelPart part, int light, int overlay, boolean banner) {
        BannerContext context = BannerContext.from(banner);

        FrameBannerPattern pattern = data.pattern();
        Identifier id = pattern.getSpriteId(context);
        SpriteIdentifier spriteId = FRAME_SPRITE_IDS.apply(context.getAtlas(), id);

        DyeColor color = data.color();
        float[] colors = color.getColorComponents();

        part.render(stack, spriteId.getVertexConsumer(vertices, RenderLayer::getEntityNoOutline), light, overlay, colors[0], colors[1], colors[2], 1.0f);
    }
}

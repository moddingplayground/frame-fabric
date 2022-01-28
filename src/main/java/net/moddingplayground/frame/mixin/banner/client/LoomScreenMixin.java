package net.moddingplayground.frame.mixin.banner.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.moddingplayground.frame.api.banner.FrameBannerPattern;
import net.moddingplayground.frame.api.banner.PatternLimitModifier;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternAccess;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternConversions;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternData;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternRenderContext;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternsInternal;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends HandledScreen<LoomScreenHandler> {
    @Shadow private boolean hasTooManyPatterns;
    @Shadow private List<?> bannerPatterns;

    @Unique private List<FrameBannerPatternData> frame_bannerPatterns = Collections.emptyList();

    private LoomScreenMixin(LoomScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    /**
     * Adds the number of rows corresponding to Frame banner patterns
     * to the loom GUI.
     */
    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/block/entity/BannerPattern;COUNT:I"
        )
    )
    private static int takeFrameBannerPatternIntoAccountForRowCount() {
        return BannerPattern.COUNT + FrameBannerPatternsInternal.dyePatternCount();
    }

    /**
     * Modifies the banner pattern count to include the number of
     * dye Frame banner patterns.
     */
    @Redirect(
        method = "drawBackground",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/block/entity/BannerPattern;COUNT:I"
        )
    )
    private int modifyDyePatternCount() {
        return BannerPattern.COUNT + FrameBannerPatternsInternal.dyePatternCount();
    }

    @Redirect(
        method = "drawBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/screen/LoomScreenHandler;getSelectedPattern()I",
            ordinal = 0
        )
    )
    private int negateFrameBannerPatternForCmp(LoomScreenHandler handler) {
        int res = handler.getSelectedPattern();
        if (res < 0) res = -res;
        return res;
    }

    @ModifyConstant(method = "onInventoryChanged", constant = @Constant(intValue = 6))
    private int disarmVanillaPatternLimitCheck(int limit) {
        return Integer.MAX_VALUE;
    }

    @Inject(
        method = "onInventoryChanged",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/gui/screen/ingame/LoomScreen;hasTooManyPatterns:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0
        )
    )
    private void addFrameBannerPatternsToFullCond(CallbackInfo ci) {
        if (this.client == null) return;
        ItemStack banner = (this.handler).getBannerSlot().getStack();
        int patternLimit = PatternLimitModifier.EVENT.invoker().computePatternLimit(6, this.client.player);
        this.hasTooManyPatterns |= BannerBlockEntity.getPatternCount(banner) >= patternLimit;
    }

    @Inject(method = "onInventoryChanged", at = @At("RETURN"))
    private void saveFrameBannerPatterns(CallbackInfo ci) {
        if (this.bannerPatterns != null) {
            ItemStack banner = (this.handler).getOutputSlot().getStack();
            NbtList ls = FrameBannerPatternConversions.getNbt(banner);
            frame_bannerPatterns = FrameBannerPatternConversions.makeData(ls);
        } else frame_bannerPatterns = Collections.emptyList();
    }

    @Unique private int frame_bannerPatternIndex;

    /**
     * Prevents an ArrayIndexOutOfBoundsException from occuring when the vanilla
     * code tries to index BannerPattern.values() with an index representing
     * a Frame banner pattern (which is negative).
     */
    @ModifyVariable(
        method = "drawBanner",
        at = @At(value = "LOAD", ordinal = 0),
        ordinal = 0, argsOnly = true
    )
    private int disarmFrameBannerPatternIndexForVanilla(int patternIndex) {
        frame_bannerPatternIndex = patternIndex;
        return Math.max(patternIndex, 0);
    }

    @Unique private static final List<FrameBannerPatternData> frame_singlePattern = new ArrayList<>();

    /**
     * If the pattern index indicates a Frame pattern, put the Frame
     * pattern in the item NBT instead of a vanilla pattern.
     */
    @Redirect(
        method = "drawBanner",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/NbtCompound;put(Ljava/lang/String;Lnet/minecraft/nbt/NbtElement;)Lnet/minecraft/nbt/NbtElement;",
            ordinal = 0
        )
    )
    private NbtElement proxyPutPatterns(NbtCompound nbt, String key, NbtElement patterns) {
        frame_singlePattern.clear();

        if (frame_bannerPatternIndex < 0) {
            int frameBannerPatternIdx = -frame_bannerPatternIndex - (1 + BannerPattern.LOOM_APPLICABLE_COUNT);
            FrameBannerPattern pattern = FrameBannerPatternsInternal.get(frameBannerPatternIdx);
            NbtList framePatterns = new NbtList();
            NbtCompound patternNbtElement = new NbtCompound();
            patternNbtElement.putString("Pattern", pattern.getId().toString());
            patternNbtElement.putInt("Color", 0);
            patternNbtElement.putInt("Index", 1);
            framePatterns.add(patternNbtElement);
            // pop dummy vanilla banner pattern
            NbtList vanillaPatterns = (NbtList) patterns;
            assert vanillaPatterns.size() == 2 : vanillaPatterns.size();
            vanillaPatterns.remove(1);
            nbt.put(FrameBannerPatternAccess.NBT_KEY, framePatterns);
            frame_singlePattern.add(new FrameBannerPatternData(pattern, DyeColor.WHITE, 1));
        }

        FrameBannerPatternRenderContext.setFrameBannerPatterns(frame_singlePattern);
        return nbt.put(key, patterns);
    }

    @Inject(
        method = "drawBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/block/entity/BannerBlockEntityRenderer;renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;)V"
        )
    )
    private void setEmptyFrameBannerPattern(CallbackInfo ci) {
        FrameBannerPatternRenderContext.setFrameBannerPatterns(frame_bannerPatterns);
    }

    /**
     * The dye pattern loop has positive indices, we negate the indices that
     * represent Frame banner patterns before passing them to method_22692.
     */
    @ModifyArg(
        method = "drawBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/ingame/LoomScreen;drawBanner(III)V",
            ordinal = 0
        ),
        index = 0
    )
    private int modifyFrameBannerPatternIdxArg(int patternIdx) {
        if (patternIdx > BannerPattern.LOOM_APPLICABLE_COUNT) {
            patternIdx = -patternIdx;
        }

        return patternIdx;
    }
}

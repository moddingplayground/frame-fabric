package net.moddingplayground.frame.mixin.banner;

import net.minecraft.block.Block;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.banner.FrameBannerPattern;
import net.moddingplayground.frame.api.banner.FrameBannerPatterns;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BannerItem.class)
public abstract class BannerItemMixin extends WallStandingBlockItem {
    @Unique private static NbtList frame_bannerPatterns;
    @Unique private static int frame_nextBannerPatternIndex;

    private BannerItemMixin(Block standingBlock, Block wallBlock, Settings settings) {
        super(standingBlock, wallBlock, settings);
    }

    /**
     * Reads in frame banner pattern data and resets current banner pattern index.
     */
    @Inject(method = "appendBannerTooltip", at = @At("HEAD"))
    private static void preAppendFramePatterns(ItemStack stack, List<Text> lines, CallbackInfo ci) {
        frame_nextBannerPatternIndex = 0;
        NbtCompound beTag = stack.getSubNbt("BlockEntityTag");
        if (beTag != null && beTag.contains(FrameBannerPatternAccess.NBT_KEY)) {
            frame_bannerPatterns = beTag.getList(FrameBannerPatternAccess.NBT_KEY, NbtElement.COMPOUND_TYPE);
        }
    }

    /**
     * Add frame banner patterns to the tooltip.
     */
    @Inject(
        method = "appendBannerTooltip",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/NbtList;getCompound(I)Lnet/minecraft/nbt/NbtCompound;",
            ordinal = 0
        )
    )
    private static void appendFramePatternsInline(ItemStack stack, List<Text> lines, CallbackInfo ci) {
        int nextIndex = lines.size() - 1;
        if (frame_bannerPatterns != null) {
            while (frame_nextBannerPatternIndex < frame_bannerPatterns.size()) {
                NbtCompound nbt = frame_bannerPatterns.getCompound(frame_nextBannerPatternIndex);
                if (nbt.getInt("Index") == nextIndex) {
                    frame_addBannerPatternLine(nbt, lines);
                    frame_nextBannerPatternIndex++;
                } else break;
            }
        }
    }

    /**
     * Add Frame banner patterns that occur after all regular banner patterns
     * in the tooltip (this also covers the case where no vanilla banner patterns
     * are present).
     */
    @Inject(method = "appendBannerTooltip", at = @At("RETURN"))
    private static void appendFramePatternsPost(ItemStack stack, List<Text> lines, CallbackInfo ci) {
        if (frame_bannerPatterns != null) {
            for (int i = frame_nextBannerPatternIndex; i < frame_bannerPatterns.size(); i++) {
                NbtCompound data = frame_bannerPatterns.getCompound(i);
                frame_addBannerPatternLine(data, lines);
            }

            // allow NBT tag to be garbage collected
            frame_bannerPatterns = null;
        }
    }

    @Unique
    private static void frame_addBannerPatternLine(NbtCompound nbt, List<Text> lines) {
        Identifier id = Identifier.tryParse(nbt.getString("Pattern"));
        DyeColor color = DyeColor.byId(nbt.getInt("Color"));
        if (id != null) {
            FrameBannerPattern pattern = FrameBannerPatterns.REGISTRY.get(id);
            if (pattern != null) pattern.addPatternLine(lines, color);
        }
    }
}

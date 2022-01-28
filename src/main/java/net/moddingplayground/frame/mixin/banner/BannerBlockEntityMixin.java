package net.moddingplayground.frame.mixin.banner;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.frame.api.banner.FrameBannerPatterns;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

import static java.util.Comparator.*;

/**
 * Adds Frame banner pattern data fields to the banner block entity.
 * The actual pattern parsing is done client side in the banner's
 * client-only methods.
 */
@Mixin(BannerBlockEntity.class)
public abstract class BannerBlockEntityMixin extends BlockEntity implements FrameBannerPatternAccess.Internal {
    @Unique private NbtList frame_bannerPatternsTag = new NbtList();

    private BannerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public NbtList frame_getBannerPatternNbt() {
        return this.frame_bannerPatternsTag;
    }

    @Override
    public void frame_setBannerPatternNbt(NbtList nbt) {
        frame_bannerPatternsTag = nbt;

        if (frame_bannerPatternsTag != null) {
            // validate NBT data, removing and/or resetting invalid data
            for (Iterator<NbtElement> itr = frame_bannerPatternsTag.iterator(); itr.hasNext(); ) {
                NbtCompound element = (NbtCompound) itr.next();
                Identifier id = Identifier.tryParse(element.getString("Pattern"));
                int colorId = element.getInt("Color");
                int index = element.getInt("Index");

                if (id == null || !FrameBannerPatterns.REGISTRY.getIds().contains(id)) {
                    itr.remove();
                } else {
                    int rtColorId = DyeColor.byId(colorId).getId();

                    if (rtColorId != colorId) {
                        element.putInt("Color", rtColorId);
                    }

                    if (index < 0) {
                        element.putInt("Index", 0);
                    }
                }
            }

            // the Java API requires that this sort be stable
            frame_bannerPatternsTag.sort(comparingInt(t -> ((NbtCompound) t).getInt("Index")));
        }
    }

    /**
     * Add Frame patterns to the pattern count.
     */
    @Inject(method = "getPatternCount", at = @At("RETURN"), cancellable = true)
    private static void modifyPatternCount(ItemStack stack, CallbackInfoReturnable<Integer> info) {
        NbtCompound beNbt = stack.getSubNbt("BlockEntityTag");
        if (beNbt != null && beNbt.contains(FrameBannerPatternAccess.NBT_KEY)) {
            int count = beNbt.getList(FrameBannerPatternAccess.NBT_KEY, 10).size();
            info.setReturnValue(info.getReturnValueI() + count);
        }
    }

    /**
     * Handles removing Frame banner patterns instead of vanilla banner patterns
     * when a banner is cleaned in a cauldron. Yes, this is an "inject-and-cancel"
     * callback. Let me know if there are incompatibilities.
     */
    @Inject(method = "loadFromItemStack", at = @At("HEAD"), cancellable = true)
    private static void cleanFrameBannerPattern(ItemStack stack, CallbackInfo info) {
        NbtCompound beTag = stack.getSubNbt("BlockEntityTag");

        if (beTag != null) {
            NbtList framePatterns = beTag.getList(FrameBannerPatternAccess.NBT_KEY, 10);
            NbtList patterns = beTag.getList("Patterns", 10);
            boolean cleaned = false;

            if (!framePatterns.isEmpty()) {
                // determine if the last banner pattern is the topmost
                int lastIndex = framePatterns.getCompound(framePatterns.size() - 1).getInt("Index");

                if (lastIndex >= patterns.size()) {
                    framePatterns.remove(framePatterns.size() - 1);
                    cleaned = true;
                }
            }

            if (!cleaned && !patterns.isEmpty()) {
                patterns.remove(patterns.size() - 1);
            }

            if (framePatterns.isEmpty()) {
                if (patterns.isEmpty()) {
                    stack.removeSubNbt("BlockEntityTag");
                } else {
                    beTag.remove(FrameBannerPatternAccess.NBT_KEY);
                }
            } else if (patterns.isEmpty()) {
                beTag.remove("Patterns");
            }
        }

        info.cancel();
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void addFrameBannerPatternData(NbtCompound nbt, CallbackInfo ci) {
        nbt.put(FrameBannerPatternAccess.NBT_KEY, frame_bannerPatternsTag);
    }

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readFrameBannerPatternData(NbtCompound nbt, CallbackInfo ci) {
        frame_setBannerPatternNbt(nbt.getList(FrameBannerPatternAccess.NBT_KEY, 10));
    }
}

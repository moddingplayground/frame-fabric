package net.moddingplayground.frame.mixin.banner.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternAccess;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternConversions;
import net.moddingplayground.frame.impl.banner.FrameBannerPatternData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(BannerBlockEntity.class)
public abstract class ClientBannerBlockEntityMixin extends BlockEntity implements FrameBannerPatternAccess {
    @Shadow private List<?> patterns;
    @Unique private List<FrameBannerPatternData> frame_bannerPatterns = Collections.emptyList();

    private ClientBannerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public List<FrameBannerPatternData> frame_getBannerPatterns() {
        if (this.patterns == null) {
            NbtList nbt = ((FrameBannerPatternAccess.Internal) this).frame_getBannerPatternNbt();
            frame_bannerPatterns = FrameBannerPatternConversions.makeData(nbt);
        }
        return Collections.unmodifiableList(frame_bannerPatterns);
    }

    /**
     * Reads Frame banner pattern data from an item stack.
     */
    @Inject(method = "readFrom(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DyeColor;)V", at = @At("RETURN"))
    private void frameReadPatternFromItemStack(ItemStack stack, DyeColor color, CallbackInfo info) {
        ((Internal) this).frame_setBannerPatternNbt(FrameBannerPatternConversions.getNbt(stack));
    }

    /**
     * Adds Frame banner pattern data to the pick block stack.
     */
    @Inject(method = "getPickStack", at = @At("RETURN"))
    private void putFrameBannerPatternsInPickStack(CallbackInfoReturnable<ItemStack> info) {
        ItemStack stack = info.getReturnValue();
        NbtList tag = ((Internal) this).frame_getBannerPatternNbt();

        if (tag != null) {
            stack.getOrCreateSubNbt("BlockEntityTag")
                 .put(NBT_KEY, tag);
        }
    }
}

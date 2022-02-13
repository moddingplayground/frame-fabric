package net.moddingplayground.frame.impl.bannerpatterns;

import net.minecraft.nbt.NbtList;

import java.util.List;

public interface FrameBannerPatternAccess {
    String NBT_KEY = "frame_BannerPatterns";
    List<FrameBannerPatternData> frame_getBannerPatterns();

    /**
     * Internal interface that allows the client mixin to communicate
     * with the common mixin.
     */
    interface Internal {
        NbtList frame_getBannerPatternNbt();
        void frame_setBannerPatternNbt(NbtList nbt);
    }
}

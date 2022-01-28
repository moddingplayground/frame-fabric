package net.moddingplayground.frame.impl.banner;

import net.minecraft.nbt.NbtList;
import net.moddingplayground.frame.impl.Frame;

import java.util.List;

public interface FrameBannerPatternAccess {
    String NBT_KEY = "%s_BannerPatterns".formatted(Frame.MOD_ID);
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

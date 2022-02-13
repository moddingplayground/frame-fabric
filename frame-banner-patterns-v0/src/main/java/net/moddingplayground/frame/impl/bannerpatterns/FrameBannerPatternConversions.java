package net.moddingplayground.frame.impl.bannerpatterns;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPatterns;

import java.util.ArrayList;
import java.util.List;

public final class FrameBannerPatternConversions {
    private FrameBannerPatternConversions() {}

    /**
     * Extracts the {@link FrameBannerPatternData} tag from the given ItemStack.
     *
     * @return the Frame banner pattern tag, or null if it is not present.
     */
    public static NbtList getNbt(ItemStack stack) {
        NbtCompound nbt = stack.getSubNbt("BlockEntityTag");
        return nbt != null && nbt.contains(FrameBannerPatternAccess.NBT_KEY, NbtElement.LIST_TYPE)
            ? nbt.getList(FrameBannerPatternAccess.NBT_KEY, NbtElement.COMPOUND_TYPE)
            : null;
    }

    /**
     * Parses the given NBT data into a list of {@link FrameBannerPatternData} objects.
     *
     * @param nbt a nullable {@link NbtList} with Frame banner pattern data
     */
    public static List<FrameBannerPatternData> makeData(NbtList nbt) {
        List<FrameBannerPatternData> res = new ArrayList<>();

        if (nbt != null) {
            for (NbtElement t : nbt) {
                NbtCompound patternNbt = (NbtCompound) t;
                FrameBannerPattern pattern = FrameBannerPatterns.REGISTRY.get(new Identifier(patternNbt.getString("Pattern")));

                if (pattern != null) {
                    DyeColor color = DyeColor.byId(patternNbt.getInt("Color"));
                    int index = patternNbt.getInt("Index");
                    res.add(new FrameBannerPatternData(pattern, color, index));
                }
            }
        }

        return res;
    }
}

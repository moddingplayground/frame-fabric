package net.moddingplayground.frame.test.item;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.moddingplayground.frame.api.banner.FrameBannerPattern;
import net.moddingplayground.frame.api.banner.FrameBannerPatternProvider;
import net.moddingplayground.frame.test.FrameTest;

public class SwordBannerItem extends SwordItem implements FrameBannerPatternProvider {
    public SwordBannerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public FrameBannerPattern getPattern() {
        return FrameTest.TEST_BANNER_PATTERN;
    }
}

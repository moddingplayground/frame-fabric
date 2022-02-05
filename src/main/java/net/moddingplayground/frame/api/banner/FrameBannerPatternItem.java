package net.moddingplayground.frame.api.banner;

import com.google.common.base.Suppliers;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class FrameBannerPatternItem extends Item implements FrameBannerPatternProvider {
    private final Supplier<FrameBannerPattern> pattern;

    public FrameBannerPatternItem(Supplier<FrameBannerPattern> pattern, Item.Settings settings) {
        super(settings);
        this.pattern = pattern;
    }

    public FrameBannerPatternItem(FrameBannerPattern pattern, Item.Settings settings) {
        this(Suppliers.memoize(() -> pattern), settings);
    }

    @Override
    public FrameBannerPattern getPattern() {
        return this.pattern.get();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(this.getDescription().formatted(Formatting.GRAY));
    }

    public MutableText getDescription() {
        return new TranslatableText(this.getTranslationKey() + ".desc");
    }
}

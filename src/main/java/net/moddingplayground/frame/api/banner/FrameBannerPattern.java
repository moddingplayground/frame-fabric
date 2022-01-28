package net.moddingplayground.frame.api.banner;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.impl.Frame;

import java.util.List;
import java.util.function.Function;

public class FrameBannerPattern {
    private final boolean hasItem;
    private final Function<BannerContext, Identifier> spriteId = Util.memoize(context -> {
        Identifier id = this.getId();
        return new Identifier(id.getNamespace(), "%s/banner_pattern/%s/%s".formatted(Frame.MOD_ID, context.getId(), id.getPath()));
    });

    private Identifier id;

    public FrameBannerPattern(boolean hasItem) {
        this.hasItem = hasItem;
    }

    public FrameBannerPattern() {
        this(false);
    }

    public final boolean hasItem() {
        return this.hasItem;
    }

    /**
     * @return the sprite ID for the pattern mask texture for this pattern.
     */
    public Identifier getSpriteId(BannerContext context) {
        return this.spriteId.apply(context);
    }

    /**
     * Adds a description of this {@link FrameBannerPattern}'s appearance to {@code lines}.
     * @param color The color this pattern has been dyed with.
     */
    public void addPatternLine(List<Text> lines, DyeColor color) {
        Identifier id = this.getId();
        lines.add(new TranslatableText("%s.pattern.%s.%s.%s".formatted(Frame.MOD_ID, id.getNamespace(), id.getPath(), color.getName())).formatted(Formatting.GRAY));
    }

    /**
     * Returns a cache of the registered id of this pattern.
     * @throws IllegalStateException if this {@link FrameBannerPattern} has not been registered.
     */
    public Identifier getId() {
        if (this.id == null) {
            Identifier id = FrameBannerPatterns.REGISTRY.getId(this);
            if (id == null) throw new IllegalStateException("Banner pattern was not registered!");
            this.id = id;
        }
        return this.id;
    }

    @Override
    public String toString() {
        return "FrameBannerPattern{" + "id=" + id + '}';
    }
}

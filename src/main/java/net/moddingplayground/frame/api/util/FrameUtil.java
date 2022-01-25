package net.moddingplayground.frame.api.util;

import net.minecraft.util.Identifier;
import net.moddingplayground.frame.Frame;
import net.moddingplayground.frame.api.gui.itemgroup.TabbedItemGroup;

public final class FrameUtil {
    public static final Identifier DEFAULT_TAB_BACKGROUND = new Identifier(Frame.MOD_ID, "textures/gui/item_group/tab_background");

    private FrameUtil() {}

    public static Identifier suffixId(Identifier id, String suffix) {
        return Identifier.tryParse("%s%s.png".formatted(id, suffix.isEmpty() ? "" : "_%s".formatted(suffix)));
    }

    public static Identifier suffixId(Identifier id) {
        return suffixId(id, "");
    }

    public static GUIIcon<Identifier> iconOf(Identifier id) {
        return GUIIcon.ofStatic(suffixId(id), suffixId(id, "hovered"), suffixId(id, "selected"));
    }

    public static GUIIcon<Identifier> iconOf(TabbedItemGroup group) {
        return GUIIcon.of(group::baseIconTex, group::hoverIconTex, group::selectedIconTex);
    }
}

package net.moddingplayground.frame.api.util;

import net.minecraft.util.Identifier;

public final class FrameUtil {
    private FrameUtil() {}

    public static Identifier suffixIdentifier(Identifier id, String suffix) {
        return Identifier.tryParse("%s%s.png".formatted(id, suffix.isEmpty() ? "" : "_%s".formatted(suffix)));
    }

    public static Identifier suffixIdentifier(Identifier id) {
        return suffixIdentifier(id, "");
    }

    public static GUIIcon<Identifier> iconOf(Identifier id) {
        return GUIIcon.ofStatic(suffixIdentifier(id), suffixIdentifier(id, "hovered"), suffixIdentifier(id, "selected"));
    }
}

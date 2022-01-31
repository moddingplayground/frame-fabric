package net.moddingplayground.frame.api.block.wood;

import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.moddingplayground.frame.mixin.access.SignTypeInvoker;

public class FrameSignType extends SignType {
    private final Identifier id;

    public FrameSignType(Identifier id) {
        super(id.toString());
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }

    public static SignType register(Identifier id) {
        return SignTypeInvoker.invokeRegister(new FrameSignType(id));
    }
}

package net.moddingplayground.frame.impl.blocks;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.moddingplayground.frame.api.blocks.v0.FrameBlocksEntityType;

public class FrameBlocksImpl implements ModInitializer {
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        Reflection.initialize(FrameBlocksEntityType.class);
    }
}

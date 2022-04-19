package net.moddingplayground.frame.test.woodtypes;

import net.fabricmc.api.ModInitializer;

public class FrameWoodTypesTest implements ModInitializer {
    @Override
    public void onInitialize() {
        TestWoodTypes.TEST.register();
    }
}

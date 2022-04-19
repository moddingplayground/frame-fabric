package net.moddingplayground.frame.test.woodtypes;

import com.google.common.reflect.Reflection;
import net.moddingplayground.frame.api.woodtypes.v0.FrameWoodTypesEntrypoint;

public class FrameWoodTypesTestEntrypoint implements FrameWoodTypesEntrypoint {
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeFrameWoodTypes() {
        Reflection.initialize(TestWoodTypes.class);
    }
}

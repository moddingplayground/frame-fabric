package net.moddingplayground.frame.api.woods.v0;

import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeManager;

public interface FrameWoodsEntrypoint {
    default void registerBoatTypes(FrameBoatTypeManager manager) {}
}

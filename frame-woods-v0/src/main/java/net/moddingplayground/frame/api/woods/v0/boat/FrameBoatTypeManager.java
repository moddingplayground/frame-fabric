package net.moddingplayground.frame.api.woods.v0.boat;

import net.minecraft.util.Identifier;
import net.moddingplayground.frame.impl.woods.boat.FrameBoatTypeManagerImpl;

import java.util.List;

public interface FrameBoatTypeManager {
    FrameBoatTypeManager INSTANCE = new FrameBoatTypeManagerImpl();

    FrameBoatTypeData register(FrameBoatTypeData data);
    List<FrameBoatTypeData> values();

    FrameBoatTypeData get(Identifier id);
}

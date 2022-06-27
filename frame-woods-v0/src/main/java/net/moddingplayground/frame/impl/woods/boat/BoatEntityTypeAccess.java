package net.moddingplayground.frame.impl.woods.boat;

import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeData;

import java.util.Optional;

public interface BoatEntityTypeAccess {
    Optional<FrameBoatTypeData> getFrameData();
    void setFrameData(FrameBoatTypeData data);
}

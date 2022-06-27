package net.moddingplayground.frame.impl.woods.boat;

import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeData;
import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FrameBoatTypeManagerImpl implements FrameBoatTypeManager {
    private final Map<Identifier, FrameBoatTypeData> idToData = new HashMap<>();

    public FrameBoatTypeManagerImpl() {}

    @Override
    public FrameBoatTypeData register(FrameBoatTypeData data) {
        this.idToData.put(data.getId(), data);
        return data;
    }

    @Override
    public List<FrameBoatTypeData> values() {
        return new ArrayList<>(this.idToData.values());
    }

    @Override
    public FrameBoatTypeData get(Identifier id) {
        return this.idToData.get(id);
    }
}

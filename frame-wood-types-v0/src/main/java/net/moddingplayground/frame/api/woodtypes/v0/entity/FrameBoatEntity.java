package net.moddingplayground.frame.api.woodtypes.v0.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.moddingplayground.frame.api.woodtypes.v0.FrameWoodTypes;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;
import net.moddingplayground.frame.api.woodtypes.v0.object.WoodObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FrameBoatEntity extends BoatEntity {
    public static final TrackedData<String> TYPE = DataTracker.registerData(FrameBoatEntity.class, TrackedDataHandlerRegistry.STRING);
    public static final String TYPE_KEY = "Type";

    public FrameBoatEntity(EntityType<? extends BoatEntity> type, World world) {
        super(type, world);
    }

    public FrameBoatEntity(World world, double x, double y, double z) {
        this(FrameWoodEntityType.BOAT, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    public Item asItem() {
        WoodType type = this.getWoodType();
        return Optional.ofNullable(type)
                       .flatMap(t -> t.get(WoodObject.BOAT))
                       .flatMap(r -> Optional.of(r.item()))
                       .orElse(Items.AIR);
    }

    /* Tracked Data */

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, "");
    }

    protected String getRawWoodType() {
        return this.dataTracker.get(TYPE);
    }

    protected void setRawWoodType(String type) {    // ⬇ get tracked wood type
        this.dataTracker.set(TYPE, type);
    }

    /* Utility Getters/Setters */

    public Identifier getWoodTypeId() {             // ⬇ convert tracked wood type to an identifier
        String type = this.getRawWoodType();
        return Identifier.tryParse(type);
    }

    public @Nullable WoodType getWoodType() {       // ⬅ convert identifier to wood type object
        Identifier type = this.getWoodTypeId();
        return FrameWoodTypes.REGISTRY.get(type);
    }

    /* NBT */

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString(TYPE_KEY, this.getRawWoodType());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains(TYPE_KEY, NbtElement.STRING_TYPE)) this.setRawWoodType(nbt.getString(TYPE_KEY));
    }

    /* Deprecate Vanilla Boat Type */

    @Deprecated @Override public void setBoatType(Type type) {}
    @Deprecated @Override public Type getBoatType() { return Type.OAK; }
}

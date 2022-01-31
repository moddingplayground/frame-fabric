package net.moddingplayground.frame.api.block.wood;

import com.google.common.base.Suppliers;
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
import net.moddingplayground.frame.api.registry.FrameRegistry;
import net.moddingplayground.frame.impl.Frame;
import net.moddingplayground.frame.impl.entity.FrameEntityType;

import java.util.Optional;
import java.util.function.Supplier;

public class FrameBoatEntity extends BoatEntity {
    public static final Supplier<String> FIRST_WOOD = Suppliers.memoize(() -> {
        WoodBlockSet set = FrameRegistry.WOOD.get(0);
        if (set == null) return null;
        Identifier id = FrameRegistry.WOOD.getId(set);
        return id == null ? null : id.toString();
    });

    public static final TrackedData<String> WOOD_TYPE = DataTracker.registerData(FrameBoatEntity.class, TrackedDataHandlerRegistry.STRING);

    public final Supplier<Item> item = Suppliers.memoize(() -> {
        WoodBlockSet wood = FrameRegistry.WOOD.get(this.getWoodTypeId());
        return wood != null ? wood.get(WoodBlock.BOAT, Item.class) : Items.AIR;
    });

    public FrameBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public FrameBoatEntity(World world, double x, double y, double z) {
        this(FrameEntityType.BOAT, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(WOOD_TYPE, Optional.ofNullable(FIRST_WOOD.get()).orElse(""));
    }

    public String getWoodType() {
        return this.dataTracker.get(WOOD_TYPE);
    }

    public Identifier getWoodTypeId() {
        String type = this.getWoodType();
        return Identifier.tryParse(type);
    }

    public boolean hasValidWoodType() {
        Identifier id = this.getWoodTypeId();
        return FrameRegistry.WOOD.containsId(id);
    }

    public void setWoodType(String type) {
        this.dataTracker.set(WOOD_TYPE, type);
    }

    public void setWoodType(WoodBlockSet wood) {
        Identifier id = FrameRegistry.WOOD.getId(wood);
        if (id == null) return;
        this.setWoodType(id.toString());
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("Type", this.getWoodType());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Type", NbtElement.STRING_TYPE)) this.setWoodType(nbt.getString("Type"));
    }

    @Override
    public Item asItem() {
        return this.item.get();
    }

    public static final String TYPE_ASSERTION_ERROR_MESSAGE = "Tried to set vanilla boat type on %s boat";

    @Override
    public void setBoatType(Type type) {
        throw new AssertionError(TYPE_ASSERTION_ERROR_MESSAGE.formatted(Frame.MOD_NAME));
    }

    @Override
    public Type getBoatType() {
        throw new AssertionError(TYPE_ASSERTION_ERROR_MESSAGE.formatted(Frame.MOD_NAME));
    }
}

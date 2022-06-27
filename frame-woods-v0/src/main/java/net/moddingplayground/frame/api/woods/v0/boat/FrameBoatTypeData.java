package net.moddingplayground.frame.api.woods.v0.boat;

import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FrameBoatTypeData {
    private final Identifier id;
    private final Block base;
    private final Supplier<ItemConvertible> boatItem, chestBoatItem;
    private final Consumer<BoatEntity.Type> setter;

    public FrameBoatTypeData(Identifier id, Block base, Supplier<ItemConvertible> boatItem, Supplier<ItemConvertible> chestBoatItem, Consumer<BoatEntity.Type> setter) {
        this.id = id;
        this.base = base;
        this.setter = setter;
        this.boatItem = boatItem;
        this.chestBoatItem = chestBoatItem;
    }

    public FrameBoatTypeData(Identifier id, Block base, Supplier<ItemConvertible> boatItem, Supplier<ItemConvertible> chestBoatItem) {
        this(id, base, boatItem, chestBoatItem, type -> {});
    }

    public Identifier getId() {
        return this.id;
    }

    public Block getBase() {
        return base;
    }

    public Item getBoatItem() {
        return getItemOrAir(this.boatItem);
    }

    public Item getChestBoatItem() {
        return getItemOrAir(this.chestBoatItem);
    }

    private static Item getItemOrAir(Supplier<ItemConvertible> item) {
        return Optional.ofNullable(item.get()).orElse(Items.AIR).asItem();
    }

    public void set(BoatEntity.Type type) {
        this.setter.accept(type);
    }
}

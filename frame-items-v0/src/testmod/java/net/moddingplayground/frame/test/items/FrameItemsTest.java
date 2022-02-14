package net.moddingplayground.frame.test.items;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.items.v0.SortedSpawnEggItem;

public class FrameItemsTest implements ModInitializer {
    public static final EntityType<PigEntity> TEST_SPAWN_EGG_ENTITY = Registry.register(Registry.ENTITY_TYPE, id("test_spawn_egg"), FabricEntityTypeBuilder.createMob().entityFactory(PigEntity::new).dimensions(EntityType.PIG.getDimensions()).defaultAttributes(PigEntity::createPigAttributes).build());
    public static final Item TEST_SPAWN_EGG = Registry.register(Registry.ITEM, id("test_spawn_egg"), new SortedSpawnEggItem(TEST_SPAWN_EGG_ENTITY, 0, 0, new FabricItemSettings()));

    @Override
    public void onInitialize() {}

    private static Identifier id(String id) {
        return new Identifier("frame-items-test", id);
    }
}

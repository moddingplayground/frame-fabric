package net.moddingplayground.frame.api.woodtypes.v0.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface FrameWoodEntityType {
    EntityType<FrameBoatEntity> BOAT = register("boat",
        FabricEntityTypeBuilder.<FrameBoatEntity>create()
                               .entityFactory(FrameBoatEntity::new).spawnGroup(SpawnGroup.MISC)
                               .dimensions(EntityDimensions.fixed(1.375f, 0.5625f))
                               .trackRangeChunks(10)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier("frame", id), entity.build());
    }
}

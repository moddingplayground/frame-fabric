package net.moddingplayground.frame.api.blocks.v0;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.blocks.v0.seat.SeatEntity;

public interface FrameBlocksEntityType {
    EntityType<SeatEntity> SEAT = register("seat",
        FabricEntityTypeBuilder.create()
                               .entityFactory(SeatEntity::new).spawnGroup(SpawnGroup.MISC)
                               .dimensions(EntityDimensions.fixed(0.0F, 0.0F))
                               .disableSummon()
                               .trackRangeChunks(10)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier("frame", id), entity.build());
    }
}

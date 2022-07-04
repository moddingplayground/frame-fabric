package net.moddingplayground.frame.test.woods;

import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.woods.v0.FrameWoodsEntrypoint;
import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeData;
import net.moddingplayground.frame.api.woods.v0.boat.FrameBoatTypeManager;

public class FrameWoodsTestWoods implements FrameWoodsEntrypoint {
    @Override
    public void registerBoatTypes(FrameBoatTypeManager manager) {
        manager.register(new FrameBoatTypeData(new Identifier(FrameWoodsTest.MOD_ID, "test_boat_type"), () -> Blocks.STONE, () -> Items.ACACIA_BOAT, () -> Items.ACACIA_CHEST_BOAT));
    }
}

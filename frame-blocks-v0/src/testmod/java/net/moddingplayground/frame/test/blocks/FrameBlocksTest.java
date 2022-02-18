package net.moddingplayground.frame.test.blocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.blocks.v0.seat.DefaultSeatBlock;

public class FrameBlocksTest implements ModInitializer {
    public static final Block TEST_SEAT_BLOCK = register("test_seat", new DefaultSeatBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));

    @Override public void onInitialize() {}

    private static Block register(String id, Block block) {
        Identifier identifier = new Identifier("frame-blocks-test", id);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        return Registry.register(Registry.BLOCK, identifier, block);
    }
}

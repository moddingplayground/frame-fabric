package net.moddingplayground.frame.test.woods;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;
import net.moddingplayground.frame.api.woods.v0.FrameSignType;

public final class FrameWoodsTest implements ModInitializer {
    public static final String MOD_ID = "frame-woods-test";

    public static final SignType TEST_SIGN_TYPE = FrameSignType.register(new Identifier(MOD_ID, "test_sign_type"));
    public static final Block TEST_SIGN = register("test_sign", new SignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN), TEST_SIGN_TYPE));
    public static final Block TEST_WALL_SIGN = register("test_wall_sign", new WallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN), TEST_SIGN_TYPE));
    public static final Item TEST_SIGN_ITEM = register("test_sign", new SignItem(new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS), TEST_SIGN, TEST_WALL_SIGN));

    @Override
    public void onInitialize() {
        StateRegistry.BLOCK_ENTITY_SUPPORTS.apply(BlockEntityType.SIGN).add(TEST_SIGN, TEST_WALL_SIGN);
    }

    private static Block register(String id, Block block) {
        return register(Registry.BLOCK, id, block);
    }

    private static Item register(String id, Item item) {
        return register(Registry.ITEM, id, item);
    }

    private static <T> T register(Registry<T> registry, String id, T obj) {
        return Registry.register(registry, new Identifier(MOD_ID, id), obj);
    }
}

package net.moddingplayground.frame.api.block.wood;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.moddingplayground.frame.api.block.vanilla.PublicDoorBlock;
import net.moddingplayground.frame.api.block.vanilla.PublicPressurePlateBlock;
import net.moddingplayground.frame.api.block.vanilla.PublicSaplingBlock;
import net.moddingplayground.frame.api.block.vanilla.PublicStairsBlock;
import net.moddingplayground.frame.api.block.vanilla.PublicTrapdoorBlock;
import net.moddingplayground.frame.api.block.vanilla.PublicWoodenButtonBlock;
import net.moddingplayground.frame.mixin.access.BlocksInvoker;

import static net.moddingplayground.frame.api.block.wood.WoodBlock.*;

public class WoodBlockDefaults {
    public static Block planks(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new Block(
            FabricBlockSettings.of(settings.material(), settings.mapColorLight())
                               .strength(2.0f, 3.0f).sounds(settings.soundGroup())
        );
    }

    public static Block sapling(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PublicSaplingBlock(
            settings.saplingGenerator(),
            FabricBlockSettings.of(settings.materialSapling())
                               .noCollision().ticksRandomly()
                               .breakInstantly().sounds(settings.soundGroupSapling())
        );
    }

    public static Block pottedSapling(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        Block sapling = set.getBlock(SAPLING);
        return new FlowerPotBlock(
            sapling,
            FabricBlockSettings.of(settings.materialDecoration())
                               .breakInstantly().nonOpaque()
        );
    }

    public static Block log(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PillarBlock(
            FabricBlockSettings.of(settings.material(), state -> logColor(state, settings, false))
                               .strength(2.0f).sounds(settings.soundGroup())
        );
    }

    public static Block strippedLog(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PillarBlock(
            FabricBlockSettings.of(settings.material(), state -> logColor(state, settings, true))
                               .strength(2.0f).sounds(settings.soundGroup())
        );
    }

    public static Block wood(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PillarBlock(
            FabricBlockSettings.of(settings.material(), settings.mapColorDark())
                               .strength(2.0f).sounds(settings.soundGroup())
        );
    }

    public static Block leaves(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new LeavesBlock(
            FabricBlockSettings.of(settings.materialLeaves())
                               .strength(0.2f).ticksRandomly()
                               .sounds(settings.soundGroupLeaves()).nonOpaque()
                               .allowsSpawning(BlocksInvoker::invokeCanSpawnOnLeaves)
                               .suffocates(WoodBlockDefaults::never)
                               .blockVision(WoodBlockDefaults::never)
        );
    }

    public static Block slab(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new SlabBlock(
            FabricBlockSettings.of(settings.material(), settings.mapColorLight())
                               .strength(2.0f, 3.0f).sounds(settings.soundGroup())
        );
    }

    public static Block fence(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new FenceBlock(
            FabricBlockSettings.of(settings.material(), settings.mapColorLight())
                               .strength(2.0f, 3.0f).sounds(settings.soundGroup())
        );
    }

    public static Block stairs(WoodBlockSet set) {
        Block planks = set.getBlock(PLANKS);
        return new PublicStairsBlock(planks.getDefaultState(), FabricBlockSettings.copyOf(planks));
    }

    public static Block button(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PublicWoodenButtonBlock(
            FabricBlockSettings.of(settings.materialDecoration())
                               .noCollision().strength(0.5f).sounds(settings.soundGroup())
        );
    }

    public static Block pressurePlate(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PublicPressurePlateBlock(
            settings.activationRule(),
            FabricBlockSettings.of(settings.material(), settings.mapColorLight())
                               .noCollision().strength(0.5f).sounds(settings.soundGroup())
        );
    }

    public static Block door(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PublicDoorBlock(
            FabricBlockSettings.of(settings.material(), settings.mapColorLight())
                               .strength(3.0f).sounds(settings.soundGroup())
                               .nonOpaque()
        );
    }

    public static Block trapdoor(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new PublicTrapdoorBlock(
            FabricBlockSettings.of(settings.material(), settings.mapColorLight())
                               .strength(3.0f).sounds(settings.soundGroup())
                               .nonOpaque().allowsSpawning(WoodBlockDefaults::never)
        );
    }

    public static Block fenceGate(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new FenceGateBlock(
            FabricBlockSettings.of(settings.material(), settings.mapColorLight())
                               .strength(2.0f, 3.0f).sounds(settings.soundGroup())
        );
    }

    public static Item signItem(WoodBlockSet set, Block block, Item.Settings settings) {
        Block sign = set.getBlock(SIGN);
        Block wallSign = set.getBlock(WALL_SIGN);
        return new SignItem(settings, sign, wallSign);
    }

    public static Block sign(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        return new SignBlock(
            FabricBlockSettings.of(settings.material())
                               .noCollision().strength(1.0f).sounds(settings.soundGroup()),
            set.getOrCreateSignType()
        );
    }

    public static Block wallSign(WoodBlockSet set) {
        Block sign = set.getBlock(SIGN);
        return new WallSignBlock(FabricBlockSettings.copyOf(sign), set.getOrCreateSignType());
    }

    public static MapColor logColor(BlockState state, WoodBlockSet.Settings settings, boolean stripped) {
        return !stripped && state.get(PillarBlock.AXIS) == Direction.Axis.Y ? settings.mapColorLight() : settings.mapColorDark();
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}

package net.moddingplayground.frame.api.woodtypes.v0.object;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.item.BoatItem;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;

import java.util.Collections;
import java.util.List;

public interface WoodObject<T extends RegisteredWoodObject> {
    WoodObject<BlockWoodObject.Registered> PLANKS = new BlockWoodObject("%s_planks", t -> new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    WoodObject<BlockWoodObject.Registered> SAPLING = new BlockWoodObject("%s_sapling", t -> new SaplingBlock(new OakSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));
    WoodObject<BlockWoodObject.Registered> LOG = new BlockWoodObject("%s_log", t -> new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));
    WoodObject<BlockWoodObject.Registered> STRIPPED_LOG = new BlockWoodObject("stripped_%s_log", t -> new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG)));
    WoodObject<BlockWoodObject.Registered> STRIPPED_WOOD = new BlockWoodObject("stripped_%s_wood", t -> new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));
    WoodObject<BlockWoodObject.Registered> WOOD = new BlockWoodObject("%s_wood", t -> new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));
    WoodObject<BlockWoodObject.Registered> LEAVES = new BlockWoodObject("%s_leaves", t -> new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));
    WoodObject<BlockWoodObject.Registered> SLAB = new BlockWoodObject("%s_slab", t -> new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)));
    WoodObject<BlockWoodObject.Registered> FENCE = new BlockWoodObject("%s_fence", t -> new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE)));
    WoodObject<BlockWoodObject.Registered> STAIRS = new BlockWoodObject("%s_stairs", t -> new StairsBlock(null, FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    WoodObject<BlockWoodObject.Registered> BUTTON = new BlockWoodObject("%s_button", t -> new WoodenButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON)));
    WoodObject<BlockWoodObject.Registered> PRESSURE_PLATE = new BlockWoodObject("%s_pressure_plate", t -> new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE)));
    WoodObject<BlockWoodObject.Registered> DOOR = new BlockWoodObject("%s_door", t -> new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR)));
    WoodObject<BlockWoodObject.Registered> TRAPDOOR = new BlockWoodObject("%s_trapdoor", t -> new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR)));
    WoodObject<BlockWoodObject.Registered> FENCE_GATE = new BlockWoodObject("%s_fence_gate", t -> new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE)));
    WoodObject<ItemWoodObject.Registered> BOAT = new ItemWoodObject("%s_boat", t -> new BoatItem(null, new FabricItemSettings()));
    WoodObject<SignWoodObject.Registered> SIGN = new SignWoodObject("%s_sign", "%s_wall_sign", t -> new SignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN), null), t -> new WallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN), null));

    List<WoodObject<?>> ALL = Collections.unmodifiableList(Lists.newArrayList(
        PLANKS, SAPLING, LOG, STRIPPED_LOG, STRIPPED_WOOD,
        WOOD, LEAVES, SLAB, FENCE, STAIRS, BUTTON,
        PRESSURE_PLATE, DOOR, TRAPDOOR, FENCE_GATE,
        BOAT, SIGN
    ));

    T register(WoodType type);
}

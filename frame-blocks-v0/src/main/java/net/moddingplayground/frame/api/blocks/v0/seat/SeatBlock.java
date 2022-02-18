package net.moddingplayground.frame.api.blocks.v0.seat;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.moddingplayground.frame.api.blocks.v0.FrameBlocksEntityType;

import java.util.List;

public interface SeatBlock {
    Vec3d DEFAULT_OFFSET = new Vec3d(0.5D, 0.6D, 0.5D);

    default Vec3d getSeatedOffset(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return DEFAULT_OFFSET;
    }

    default boolean canSit(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return true;
    }

    default boolean trySit(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!this.canSit(world, pos, state, player)) return false;

        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, new Box(pos), p -> p.getVehicle() instanceof SeatEntity);
        if (!players.isEmpty()) return false;

        SeatEntity seat = FrameBlocksEntityType.SEAT.create(world);
        if (seat != null) {
            seat.setPosition(Vec3d.of(pos).add(this.getSeatedOffset(world, pos, state, player)));
            if (player.startRiding(seat)) {
                world.spawnEntity(seat);
                return true;
            }
        }

        return false;
    }

    default ActionResult trySit(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty() && this.trySit(world, pos, state, player)) return ActionResult.success(world.isClient);
        return ActionResult.PASS;
    }
}

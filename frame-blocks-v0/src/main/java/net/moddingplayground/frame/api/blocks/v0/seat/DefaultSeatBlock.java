package net.moddingplayground.frame.api.blocks.v0.seat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class DefaultSeatBlock extends Block implements SeatBlock {
    public DefaultSeatBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ActionResult sit = this.trySit(state, world, pos, player, hand);
        return sit.isAccepted() ? sit : super.onUse(state, world, pos, player, hand, hit);
    }
}

package net.moddingplayground.frame.api.blocks.v0.seat;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SeatEntity extends Entity {
    public SeatEntity(EntityType<?> type, World world) {
        super(type, world);
        this.noClip = true;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isClient) {
            BlockPos pos = this.getBlockPos();
            BlockState state = this.world.getBlockState(pos);
            if (!this.hasPassengers()
                || !(state.getBlock() instanceof SeatBlock || this.world.getBlockEntity(pos) instanceof PistonBlockEntity)
            ) this.discard();
        }
    }

    @Override
    public void move(MovementType type, Vec3d movement) {
        if (type == MovementType.PISTON) {
            this.refreshPositionAfterTeleport(new Vec3d(
                this.getX() + (movement.x == 0 ? 0 : movement.x < 0 ? -1.0D : 1.0D),
                this.getY(),
                this.getZ() + (movement.z == 0 ? 0 : movement.z < 0 ? -1.0D : 1.0D)
            ));
            return;
        }
        super.move(type, movement);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d ret = super.updatePassengerForDismount(passenger);
        BlockPos pos = this.getBlockPos();
        BlockState state = this.world.getBlockState(pos.up());
        return state.isAir() ? new Vec3d(ret.x, pos.getY() + 1.0D, ret.z) : ret;
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.0D;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override protected void initDataTracker() {}
    @Override protected void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override protected void writeCustomDataToNbt(NbtCompound nbt) {}
}

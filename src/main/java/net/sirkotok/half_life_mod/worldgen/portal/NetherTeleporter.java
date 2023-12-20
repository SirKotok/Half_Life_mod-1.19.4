package net.sirkotok.half_life_mod.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.ITeleporter;
import net.tslat.smartbrainlib.object.SquareRadius;

import java.util.function.Function;

public class NetherTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public NetherTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        BlockPos destinationPos;
        if (!destinationWorld.isOutsideBuildHeight(entity.blockPosition())) destinationPos = entity.blockPosition();
        else destinationPos = new BlockPos(entity.getBlockX(), destinationWorld.getLogicalHeight()/2, entity.getBlockZ());
        SquareRadius radius = new SquareRadius(25, 60);
        Boolean done = false;
        for (BlockPos pos : BlockPos.betweenClosed(entity.blockPosition().subtract(radius.toVec3i()), entity.blockPosition().offset(radius.toVec3i()))) {
            BlockState state = destinationWorld.getBlockState(pos);
            BlockState statebelow = destinationWorld.getBlockState(pos.below());
            BlockState stateabove = destinationWorld.getBlockState(pos.above());
            if (state.isAir() && !statebelow.isAir() && stateabove.isAir() && !destinationWorld.isOutsideBuildHeight(pos)) {
                if (pos.getY() >= destinationPos.getY()) {
                    entity.moveTo(pos.getCenter());
                    done = true;
                    break;
                }
            }
        }
            if (!done) {
                for (BlockPos pos : BlockPos.betweenClosed(entity.blockPosition().subtract(radius.toVec3i()), entity.blockPosition().offset(radius.toVec3i()))) {
                    BlockState state = destinationWorld.getBlockState(pos);
                    BlockState statebelow = destinationWorld.getBlockState(pos.below());
                    BlockState stateabove = destinationWorld.getBlockState(pos.above());
                    if (state.isAir() && !statebelow.isAir() && stateabove.isAir() && !destinationWorld.isOutsideBuildHeight(pos)) {
                            entity.moveTo(pos.getCenter());
                        done = true;
                            break;
                        }
                    }
            }
            if (!done) entity.moveTo(entity.getBlockX(), destinationWorld.getLogicalHeight()-50, entity.getBlockZ());

        return entity;
    }



}

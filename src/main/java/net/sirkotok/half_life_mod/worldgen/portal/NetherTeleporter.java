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
        BlockPos destinationP;
        if (entity.blockPosition().getY() < 100 || entity.blockPosition().getY() > 20) destinationP = entity.blockPosition();
        else destinationP = new BlockPos(entity.getBlockX(), 74, entity.getBlockZ());

        double d = destinationWorld.dimensionType().coordinateScale();
        double c = currentWorld.dimensionType().coordinateScale();


        int x = (int) Math.round(destinationP.getX()*c/d);
        int y = (int) Math.round(destinationP.getY()*c/d);
        int z = (int) Math.round(destinationP.getZ()*c/d);


        BlockPos  destinationPos = new BlockPos( x, y, z);


        SquareRadius radius = new SquareRadius(25, 25);
        Boolean done = false;
        for (BlockPos pos : BlockPos.betweenClosed(destinationPos.subtract(radius.toVec3i()), destinationPos.offset(radius.toVec3i()))) {
            BlockState state = destinationWorld.getBlockState(pos);
            BlockState statebelow = destinationWorld.getBlockState(pos.below());
            BlockState stateabove = destinationWorld.getBlockState(pos.above());
            if (state.isAir() && !statebelow.isAir() && stateabove.isAir() && pos.getY() < 120) {
                if (pos.getY() >= destinationPos.getY()) {
                    entity.moveTo(pos.getCenter());
                    done = true;
                    break;
                }
            }
        }
            if (!done) {
                for (BlockPos pos : BlockPos.betweenClosed(destinationPos.subtract(radius.toVec3i()), destinationPos.offset(radius.toVec3i()))) {
                    BlockState state = destinationWorld.getBlockState(pos);
                    BlockState statebelow = destinationWorld.getBlockState(pos.below());
                    BlockState stateabove = destinationWorld.getBlockState(pos.above());
                    if (state.isAir() && !statebelow.isAir() && stateabove.isAir() && pos.getY() < 120) {
                            entity.moveTo(pos.getCenter());
                        done = true;
                            break;
                        }
                    }
            }
            if (!done) entity.moveTo(destinationPos.getX(), 78, destinationPos.getZ());

        return entity;
    }



}

package net.sirkotok.half_life_mod.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.tslat.smartbrainlib.object.SquareRadius;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class XenTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public XenTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }







    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        BlockPos destinationP = entity.blockPosition();


        double d = destinationWorld.dimensionType().coordinateScale();
        double c = currentWorld.dimensionType().coordinateScale();

        int x = (int) Math.round(destinationP.getX()*c/d);
        int y = destinationP.getY();
        int z = (int) Math.round(destinationP.getZ()*c/d);


        BlockPos  destinationPos = new BlockPos(x, y, z);


        SquareRadius radius = new SquareRadius(25, 60);
        Boolean done = false;
        for (BlockPos pos : BlockPos.betweenClosed(destinationPos.subtract(radius.toVec3i()), destinationPos.offset(radius.toVec3i()))) {
            BlockState state = destinationWorld.getBlockState(pos);
            BlockState statebelow = destinationWorld.getBlockState(pos.below());
            BlockState stateabove = destinationWorld.getBlockState(pos.above());
            if (state.isAir() && !statebelow.isAir() && stateabove.isAir()) {
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
                    if (state.isAir() && !statebelow.isAir() && stateabove.isAir()) {
                            entity.moveTo(pos.getCenter());
                            break;
                        }
                    }
            }

        return entity;

    }



}

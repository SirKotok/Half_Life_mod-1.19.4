package net.sirkotok.half_life_mod.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;


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
        BlockPos destinationPos = new BlockPos(thisPos.getX(), thisPos.getY(), thisPos.getZ());
        entity.setPos(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        return entity;
    }
}

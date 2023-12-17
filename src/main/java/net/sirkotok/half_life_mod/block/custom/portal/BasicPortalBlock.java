package net.sirkotok.half_life_mod.block.custom.portal;


import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.sirkotok.half_life_mod.worldgen.dimension.HalfLifeDimensions;
import net.sirkotok.half_life_mod.worldgen.portal.XenTeleporter;
import net.tslat.smartbrainlib.object.SquareRadius;

public class BasicPortalBlock extends Block {
    public BasicPortalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            handleHalfLifePortal(pPlayer, pPos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void handleHalfLifePortal(Entity player, BlockPos pPos) {
        if (player.level instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level.dimension() == HalfLifeDimensions.HALFLIFE_LEVEL_KEY ?
                    Level.OVERWORLD : HalfLifeDimensions.HALFLIFE_LEVEL_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == HalfLifeDimensions.HALFLIFE_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new XenTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new XenTeleporter(pPos, false));
                }
            }
        }
    }

}


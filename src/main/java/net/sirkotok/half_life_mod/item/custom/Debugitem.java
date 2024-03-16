package net.sirkotok.half_life_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.fallingblock.GravityGunFallingBlockEntity;
import net.sirkotok.half_life_mod.item.HalfLifeItems;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;

public class Debugitem extends Item {

    public Debugitem(Properties pProperties) {
        super(pProperties);
    }


}

/*
*    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide()) {
            BlockPos pBlockPos = pPlayer.blockPosition();
            int rad = 5;
            List<GravityGunFallingBlockEntity> blocks = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof GravityGunFallingBlockEntity block && block.getPlayerStringUUID().equals(pPlayer.getStringUUID()));
            if (!blocks.isEmpty()) {
                for (GravityGunFallingBlockEntity block : blocks) {
                    block.setnoplayer();
                }
            } else {
                BlockPos pPos = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE).getBlockPos();
                BlockState pBlockState = pLevel.getBlockState(pPos);
                if (pBlockState.isAir()) return InteractionResultHolder.fail(stack);
                GravityGunFallingBlockEntity fb = new GravityGunFallingBlockEntity(pLevel, (double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, pBlockState.hasProperty(BlockStateProperties.WATERLOGGED) ? pBlockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : pBlockState);
                pLevel.setBlock(pPos, pBlockState.getFluidState().createLegacyBlock(), 3);
                fb.moveTo(GravityGunFallingBlockEntity.findposstat(pPlayer));
                pLevel.addFreshEntity(fb);
                fb.setPlayerStringUuid(pPlayer.getStringUUID());
                fb.setNoGravity(true);
            }
        }
        return InteractionResultHolder.success(stack);
    }

* */



/* Debug for hl1 vort being scared behaviour

//Angles tested are from -180 to 180



 */
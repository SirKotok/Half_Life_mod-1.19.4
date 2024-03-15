package net.sirkotok.half_life_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.fallingblock.GravityGunFallingBlockEntity;

public class Debugitem extends Item {

    public Debugitem(Properties pProperties) {
        super(pProperties);
    }






    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        BlockPos pPos = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE).getBlockPos();
        BlockState pBlockState = pLevel.getBlockState(pPos);
        if (pBlockState.isAir()) return InteractionResultHolder.fail(stack);

        GravityGunFallingBlockEntity fb = new GravityGunFallingBlockEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pBlockState.hasProperty(BlockStateProperties.WATERLOGGED) ? pBlockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : pBlockState);
        pLevel.setBlock(pPos, pBlockState.getFluidState().createLegacyBlock(), 3);
        fb.moveTo(GravityGunFallingBlockEntity.findposstat(pPlayer));
        pLevel.addFreshEntity(fb);
        fb.setPlayerStringUuid(pPlayer.getStringUUID());
        fb.setNoGravity(true);

        return InteractionResultHolder.success(stack);
    }








}

/* Debug for hl1 vort being scared behaviour

//Angles tested are from -180 to 180

 public boolean anglecomapre(double ang1, double ang2, float margin) {
        boolean normalflag = Mth.abs((float)(ang1 - ang2)) < margin;
        boolean plus2Pi = Mth.abs((float)(ang1 - ang2+360)) < margin;
        boolean minus2Pi = Mth.abs((float)(ang1 - ang2-360)) < margin;

        return normalflag || plus2Pi || minus2Pi;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player entity, InteractionHand pHand) {
        ItemStack itemstack = entity.getItemInHand(pHand);
        if (!pLevel.isClientSide()) {
           BlockPos pBlockPos = entity.blockPosition();
           int rad = 16;
           List<LivingEntity> antlions = EntityRetrievalUtil.getEntities((Level) pLevel,
                   new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                           pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> obj instanceof LivingEntity && obj != entity);
           if (!antlions.isEmpty()) {
               LivingEntity vort = antlions.get(0);
               float yrot = entity.getYRot();
               float xrot = entity.getXRot();
               Vec3 vec3 = new Vec3(entity.getX() - vort.getX(), entity.getEyeY() - vort.getEyeY(), entity.getZ() - vort.getZ());
               Vec3 vec2 = new Vec3(vec3.x, 0, vec3.z);

               double L = vec3.length();
               double Lxz = vec2.length();
               double x = vec3.x;
               double z = vec3.z;
               double alpha = Math.acos(Lxz/L) * 180/Math.PI * Math.signum(entity.getEyeY() - vort.getEyeY());
               double beta = -Math.atan(x/z)* 180/Math.PI*Mth.sign(x);
               if (z > 0 && beta < 0) beta = 180+beta;
               else if (z < 0 && beta < 0) beta = -180 - beta;
               else if (z < 0 && beta > 0) beta = -beta;

               if ((z > 0 && x < 0) || (z < 0 && x > 0) && beta < 0) beta = -beta;



               boolean cansee = anglecomapre(alpha, xrot, 75) && anglecomapre(beta, yrot, 90);
               // boolean cansee2 = entity.getSensing().hasLineOfSight(this.target)

               entity.sendSystemMessage(Component.literal("yrot = " + yrot + "; xrot = " + xrot + "; alpha = " + alpha + "; beta = " + beta + "; can see = " + cansee));



           } else {
               entity.sendSystemMessage(Component.literal("No entities found"));
           }




       }

        return InteractionResultHolder.success(itemstack);
    }

 */
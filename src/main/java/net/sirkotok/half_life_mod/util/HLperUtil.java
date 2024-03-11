package net.sirkotok.half_life_mod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.effect.HalfLifeEffects;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Pitdrone;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Shockroach;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public final class HLperUtil {
    @Nullable
    public static UUID getMaxUUID(List<UUID> list) {
        if (list == null || list.isEmpty()) return null;
        UUID max = list.get(0);
        for (UUID id : list) {
            if (max.compareTo(id) < 0) max = id;
        }
      return max;
    }


    public static void rotateTTT(Entity currentone, LivingEntity to) {
        Vec3 vec31 =  to.position();
        Vec3 vec3 = currentone.position();
        Vec3 between = vec3.subtract(vec31);
        rotateToVec3(currentone, between);
        currentone.setYRot((float) HLperUtil.yanglefromvec3(between));
        currentone.setXRot((float) HLperUtil.yanglefromvec3(between));
    }


    public static void rotateToVec3(Entity entity, Vec3 vec3) {
        entity.setYRot((float) HLperUtil.yanglefromvec3(vec3));
        entity.setXRot((float) HLperUtil.yanglefromvec3(vec3));
    }

    public static void rotateToOppositeVec3(Entity entity, Vec3 vec31) {
        Vec3 vec3 = vec31.scale(-1);
        entity.setYRot((float) HLperUtil.yanglefromvec3(vec3));
        entity.setXRot((float) HLperUtil.yanglefromvec3(vec3));
    }

    public static void rotateToTarget(LivingEntity currentone) {
        LivingEntity to = BrainUtils.getTargetOfEntity(currentone);
        if (to == null) return;
        rotateTTT(currentone, to);
    }




    public static void slowEntityFor(LivingEntity entity, int ticks) {
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, ticks, 100, false, false, false));
    }

    public static LivingEntity TargetOrThis(LivingEntity me){
        return BrainUtils.getTargetOfEntity(me) != null ? BrainUtils.getTargetOfEntity(me) : me;
    }


    public static double xanglefromvec3(Vec3 vec3){
        Vec3 vec2 = new Vec3(vec3.x, 0, vec3.z);
        double L = vec3.length();
        double Lxz = vec2.length();
        double x = vec3.x;
        double z = vec3.z;
        return Math.acos(Lxz/L) * 180/Math.PI * Math.signum(vec3.y);
    }

    public static boolean anglecomapre(double ang1, double ang2, float margin) {
        boolean normalflag = Mth.abs((float)(ang1 - ang2)) < margin;
        boolean plus2Pi = Mth.abs((float)(ang1 - ang2+360)) < margin;
        boolean minus2Pi = Mth.abs((float)(ang1 - ang2-360)) < margin;
        return normalflag || plus2Pi || minus2Pi;
    }

    public static boolean cansee(LivingEntity vort, LivingEntity entity) {
        float yrot = entity.getYRot();
        float xrot = entity.getXRot();
        Vec3 vec3 = new Vec3(entity.getX() - vort.getX(), entity.getEyeY() - vort.getEyeY(), entity.getZ() - vort.getZ());
        double alpha = xanglefromvec3(vec3);
        double beta = yanglefromvec3(vec3);
        return anglecomapre(alpha, xrot, 75) && anglecomapre(beta, yrot, 90);
    }


    public static float RandomFloat(int upperbound){
        return RandomSource.create().nextFloat()*upperbound*(RandomSource.create().nextFloat() < 0.5 ? 1 : -1);
    }
    public static double yanglefromvec3(Vec3 vec3){
        Vec3 vec2 = new Vec3(vec3.x, 0, vec3.z);
        double L = vec3.length();
        double Lxz = vec2.length();
        double x = vec3.x;
        double z = vec3.z;
        double beta = -Math.atan(x/z)* 180/Math.PI* Mth.sign(x);
        if (z > 0 && beta < 0) beta = 180+beta;
        else if (z < 0 && beta < 0) beta = -180 - beta;
        else if (z < 0 && beta > 0) beta = -beta;
        if ((z > 0 && x < 0) || (z < 0 && x > 0) && beta < 0) beta = -beta;
        return beta;
    }



    public static boolean isWithinSpawnChunks(BlockPos pBlockPos, ServerLevelAccessor pLevel) {
        BlockPos spwanpos = pLevel.getLevel().getSharedSpawnPos();
        int x = Math.abs(spwanpos.getX()-pBlockPos.getX());
        int z = Math.abs(spwanpos.getZ()-pBlockPos.getZ());
        return x < 310 && z < 310;
    }



    private static void maybeDisableShieldFor(int ticks, Player pPlayer, ServerLevel level, float f, ItemStack pPlayerItemStack) {
        if (!pPlayerItemStack.isEmpty() && pPlayerItemStack.is(Items.SHIELD)) {
            if (RandomSource.create().nextFloat() < f) {
                pPlayer.getCooldowns().addCooldown(Items.SHIELD, ticks);
                level.broadcastEntityEvent(pPlayer, (byte)30);
                pPlayer.stopUsingItem();
            }
        }
    }

    public static void DisableShieldFor(Entity entity, float chance, int ticks, ServerLevel level) {
        if (entity instanceof Player && chance>0) {
            Player player = (Player)entity;
            maybeDisableShieldFor(ticks, player, level, chance, player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
        }
    }





    public static int getrandomof(int i, int j, int k, boolean a, boolean b) {
        int r = 1;
        int changer = j;
        if (a && b) {
            r = 3;
        } else if(a) {
            r = 2;
        } else if(b) {
            r = 2;
            changer = k;
        }

        switch(RandomSource.create().nextInt(1,r+1)) {
            case 2: return changer;
            case 3: return k;
        }
        return i;
    }



    public static int getMaxUUIDnumber(List<UUID> list) {
        if (list == null || list.isEmpty()) return -1;
        int i = -1;
        int currentmaxi = 0;
        UUID max = list.get(0);
        for (UUID id : list) {
            i++;
            if (max.compareTo(id) < 0) {
                max = id;
                currentmaxi = i;
            }
        }
        return currentmaxi;
    }




    public static int getMinUUIDnumber(List<UUID> list) {
        if (list == null || list.isEmpty()) return -1;
        int i = -1;
        int currentmini = 0;
        UUID min = list.get(0);
        for (UUID id : list) {
            i++;
            if (min.compareTo(id) > 0) {
                min = id;
                currentmini = i;
            }
        }
        return currentmini;
    }





    @Nullable
    public static UUID getMinUUID(List<UUID> list) {
        if (list == null || list.isEmpty()) return null;
        UUID min = list.get(0);
        for (UUID id : list) {
            if (min.compareTo(id) > 0) min = id;
        }
        return min;
    }




}

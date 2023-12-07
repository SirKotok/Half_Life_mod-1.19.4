package net.sirkotok.half_life_mod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
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



    public static LivingEntity TargetOrThis(LivingEntity me){
        return BrainUtils.getTargetOfEntity(me) != null ? BrainUtils.getTargetOfEntity(me) : me;
    }


    public static boolean issameteam(LivingEntity one, LivingEntity two){
        boolean combine = (one.getType().is(ModTags.EntityTypes.FACTION_COMBINE) && two.getType().is(ModTags.EntityTypes.FACTION_COMBINE));
        boolean science_team = (one.getType().is(ModTags.EntityTypes.FACTION_SCIENCE_TEAM) && two.getType().is(ModTags.EntityTypes.FACTION_SCIENCE_TEAM));
        boolean race_x = (one.getType().is(ModTags.EntityTypes.FACTION_RACE_X) && two.getType().is(ModTags.EntityTypes.FACTION_RACE_X));
        boolean xen = (one.getType().is(ModTags.EntityTypes.FACTION_XEN) && two.getType().is(ModTags.EntityTypes.FACTION_XEN));
        boolean pitdrone_unique = !((one instanceof Pitdrone && two instanceof Shockroach) || (one instanceof Shockroach && two instanceof Pitdrone));
        boolean headcrab = (one.getType().is(ModTags.EntityTypes.FACTION_HEADCRAB) && two.getType().is(ModTags.EntityTypes.FACTION_HEADCRAB));
        return (science_team || race_x || combine || headcrab || xen) && pitdrone_unique;
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

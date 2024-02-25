package net.sirkotok.half_life_mod.entity.modinterface;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public interface HasLeaderMob<E extends Mob> {
    LivingEntity getLeader();
    void setLeader(LivingEntity entity);
}
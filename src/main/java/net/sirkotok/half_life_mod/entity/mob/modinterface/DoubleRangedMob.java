package net.sirkotok.half_life_mod.entity.mob.modinterface;

import net.minecraft.world.entity.LivingEntity;

public interface DoubleRangedMob {
    void performSecondRangedAttack(LivingEntity pTarget, float pVelocity);
}
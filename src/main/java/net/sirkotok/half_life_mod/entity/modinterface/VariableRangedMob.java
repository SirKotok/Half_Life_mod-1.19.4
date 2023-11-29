package net.sirkotok.half_life_mod.entity.modinterface;

import net.minecraft.world.entity.LivingEntity;

public interface VariableRangedMob {
    void performVariableRangedAttack(LivingEntity pTarget, float pVelocity, float down);
}
package net.sirkotok.half_life_mod.entity.mob.modinterface;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public interface MultiMeleeEntity {
    boolean performMultiAttack(boolean after, Entity entity, float disablechance, float attack_modifier, float knockback_modifier, @Nullable MobEffect effect, int duration, boolean visible);
    boolean isinside();


}

package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;


import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;

import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;

public class ConfigurableAnimatableMeleeAttack<E extends HalfLifeMonster> extends AnimatableMeleeAttack<E> {

  protected int attackmod;
  protected int counter = 0;
    protected boolean growled = false;
    protected int knockbackmod;

    @Nullable
    protected MobEffect effect;
    @Nullable protected int effectduration;

    @Nullable
    protected SoundEvent onhit;


    public ConfigurableAnimatableMeleeAttack(int delayTicks, int modattack, int modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.effect = effect;
        this.effectduration = effectduration;

        this.onhit = onhit;
    }






    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
        if (this.target == null)
            return;

        if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
            if (this.onhit != null) {
            entity.playSound(this.onhit);}
            entity.ConfigurabledoHurtTarget(this.target, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
        }

}




}

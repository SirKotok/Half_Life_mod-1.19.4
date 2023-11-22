package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;

public class DoubleMeleeAttack<E extends HalfLifeMonster> extends AnimatableMeleeAttack<E> {

  protected float attackmod;
  protected int counter = 0;
    protected boolean growled = false;
    protected int firsthitdelay;
    protected float knockbackmod;
    protected int tick;
    protected float disableshield;
    @Nullable
    protected MobEffect effect;
    @Nullable protected int effectduration;

    @Nullable
    protected SoundEvent onhit;
    @Nullable
    protected SoundEvent startsound;
    protected boolean attacked;

    public DoubleMeleeAttack(int delayTicks, int firsthitdelay, float disablechance, float modattack, float modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.firsthitdelay = firsthitdelay;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.onhit = onhit;
        this.startsound = startsound;
    }


    @Override
    protected void start(E entity) {
        super.start(entity);
        this.attacked = false;
        this.tick = 0;
        if (this.startsound != null) {
            entity.playSound(this.startsound, 0.8f, entity.getVoicePitch());}
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        tick++;
        if (!this.attacked && this.tick > this.firsthitdelay) {
            this.attacked = true;
            if (this.target == null)
                return;
            if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
                if (this.onhit != null) {
                    entity.playSound(this.onhit, 0.8f, entity.getVoicePitch());}
                entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
            }
        }
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
        if (this.target == null)
            return;

        if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
            if (this.onhit != null) {
            entity.playSound(this.onhit, 0.8f, entity.getVoicePitch());}
            entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
        }

}




}

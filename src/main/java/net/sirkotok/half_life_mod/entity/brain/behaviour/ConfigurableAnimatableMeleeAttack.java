package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;


import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;

import net.sirkotok.half_life_mod.util.CommonSounds;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;

public class ConfigurableAnimatableMeleeAttack<E extends HalfLifeMonster> extends AnimatableMeleeAttack<E> {

  protected float attackmod;
  protected int counter = 0;
    protected boolean growled = false;
    protected float knockbackmod;

    protected float disableshield;
    @Nullable
    protected MobEffect effect;
    @Nullable protected int effectduration;

    @Nullable
    protected SoundEvent onhit;

    @Nullable
    protected SoundEvent miss;
    @Nullable
    protected SoundEvent zombielong;
    @Nullable
    protected SoundEvent zombieshort;
    @Nullable
    protected SoundEvent startsound;

    public ConfigurableAnimatableMeleeAttack(int delayTicks, float disablechance, float modattack, float modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.onhit = onhit;
        this.startsound = startsound;
        this.zombielong = null;
        this.zombieshort = null;
        this.miss = null;
    }


    public ConfigurableAnimatableMeleeAttack(int delayTicks, float disablechance, float modattack, float modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound, @Nullable SoundEvent miss, @Nullable SoundEvent zombielong, @Nullable SoundEvent zombieshort) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.onhit = onhit;
        this.startsound = startsound;
        this.zombielong = zombielong;
        this.zombieshort = zombieshort;
        this.miss = miss;
    }


    @Override
    protected void start(E entity) {
        super.start(entity);
        SoundEvent playsound = this.startsound;
        if (this.zombieshort != null && this.zombielong != null) {
            switch(RandomSource.create().nextInt(5)) {
                case 2: playsound = this.zombielong;
                break;
                case 3: playsound = this.zombieshort; break;
            }
        }

        if (playsound != null) {
            CommonSounds.PlaySoundAsOwn(entity, playsound);
        }
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
        if (this.target == null)
            return;

        if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
            if (this.onhit != null) {
                CommonSounds.PlaySoundAsOwn(entity, onhit); }
                entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
        }
             else {
                if (this.miss != null) CommonSounds.PlaySoundAsOwn(entity, miss);
            }

    }




}

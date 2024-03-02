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
import java.util.function.Function;

public class TripleMeleeAttack<E extends HalfLifeMonster> extends AnimatableMeleeAttack<E> {

  protected float attackmod;

    protected int firsthitdelay;
    protected int secondhitdelay;
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
    protected boolean attacked2;

    @Nullable
    protected SoundEvent miss;



    public TripleMeleeAttack(int delayTicks, int firsthitdelay, int seconddelay, float disablechance, float modattack, float modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound, @Nullable SoundEvent misssound) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.firsthitdelay = firsthitdelay;
        this.secondhitdelay = seconddelay;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.onhit = onhit;
        this.startsound = startsound;
        this.miss = misssound;

    }





    @Override
    protected void start(E entity) {
        super.start(entity);
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, 30+delayTime);
        this.attacked = false;
        this.attacked2 = false;
        this.tick = 0;
        HLperUtil.slowEntityFor(entity, delayTime+2);
        SoundEvent playsound = this.startsound;
        if (playsound != null) {
            CommonSounds.PlaySoundAsOwn(entity, playsound);
        }
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        tick++;
        if (!this.attacked2 && this.tick > this.secondhitdelay) {
            this.attacked2 = true;
            if (this.target == null)
                return;
            if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
                if (this.onhit != null) {
                    CommonSounds.PlaySoundAsOwn(entity, this.onhit);}
                entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
            } else {
                if (this.miss != null) CommonSounds.PlaySoundAsOwn(entity, miss);
            }
        }



        if (!this.attacked && this.tick > this.firsthitdelay) {
            this.attacked = true;
            if (this.target == null)
                return;
            if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
                    CommonSounds.PlaySoundAsOwn(entity, this.onhit);
                entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
            } else {
                CommonSounds.PlaySoundAsOwn(entity, miss);
            }
        }


    }




    @Override
    protected void doDelayedAction(E entity) {

         BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
        if (this.target == null)
            return;
        if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
            CommonSounds.PlaySoundAsOwn(entity, this.onhit);
            entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
        } else {
             CommonSounds.PlaySoundAsOwn(entity, miss);
        }

}




}

package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.function.Function;

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
    protected boolean attacked2;
    protected boolean attacked3;
    protected int adddelay;
    @Nullable
    protected SoundEvent zombielong;
    @Nullable
    protected SoundEvent zombieshort;
    @Nullable
    protected SoundEvent miss;

    @Nullable
    protected SoundEvent frenzy;
    protected boolean playedlong = false;

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
        this.zombielong = null;
        this.zombieshort = null;
        this.miss = null;
        this.adddelay = 0;
    }

    public DoubleMeleeAttack(int delayTicks, int firsthitdelay, float disablechance, float modattack, float modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound, @Nullable SoundEvent miss, @Nullable SoundEvent zombielong, @Nullable SoundEvent zombieshort) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.firsthitdelay = firsthitdelay;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.onhit = onhit;
        this.startsound = startsound;
        this.zombielong = zombielong;
        this.zombieshort = zombieshort;
        this.miss = miss;
        this.adddelay = 0;
    }

    public DoubleMeleeAttack(int delayTicks, int firsthitdelay, float disablechance, float modattack, float modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound, @Nullable SoundEvent miss, @Nullable SoundEvent zombielong, @Nullable SoundEvent zombieshort, int adddelay, @Nullable SoundEvent frenzy) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.firsthitdelay = firsthitdelay;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.onhit = onhit;
        this.startsound = startsound;
        this.zombielong = zombielong;
        this.zombieshort = zombieshort;
        this.miss = miss;
        this.adddelay = adddelay;
        this.frenzy = frenzy;
    }



    @Override
    protected void start(E entity) {
        super.start(entity);
        this.attacked = false;
        this.attacked2 = false;
        this.attacked3 = false;
        this.tick = 0;
        playedlong = false;
        SoundEvent playsound = this.startsound;
        if (this.zombieshort != null && this.zombielong != null) {
            switch(RandomSource.create().nextInt(5)) {
                case 2: {playsound = this.zombielong;
                    playedlong = true;
                    break;
                }
                case 3, 4: playsound = this.zombieshort; break;
            }
        }

        if (playsound != null) {
            CommonSounds.PlaySoundAsOwn(entity, playsound);
        }
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        tick++;


        if (!this.attacked2 && this.adddelay > 0 && this.tick > this.delayTime-this.adddelay) {
            this.attacked2 = true;
            HLperUtil.slowEntityFor(entity, adddelay);
            BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.fastzombieintervalsupplier.apply(entity));
            if (this.target == null)
                return;
            if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
                if (this.onhit != null) {
                    entity.playSound(this.onhit, 0.8f, entity.getVoicePitch());}
                entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
            } else {
                if (this.miss != null) CommonSounds.PlaySoundAsOwn(entity, miss);
            }
        }


        if (!this.attacked3 && this.adddelay > 0 && this.tick > this.delayTime - this.adddelay + 6) {
            this.attacked3 = true;
            if (this.frenzy != null) {
                entity.playSound(this.frenzy, 0.8f, entity.getVoicePitch());
            }
        }


        if (!this.attacked && this.tick > this.firsthitdelay) {
            this.attacked = true;
            if (this.target == null)
                return;
            if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
                if (this.onhit != null) {
                    entity.playSound(this.onhit, 0.8f, entity.getVoicePitch());}
                entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
            } else {
                if (this.miss != null) CommonSounds.PlaySoundAsOwn(entity, miss);
            }
        }

        if ( !this.playedlong && this.attacked && this.tick > this.firsthitdelay && this.zombieshort != null && this.zombielong != null) {
               SoundEvent playsound2 = null;
                switch(RandomSource.create().nextInt(5)) {
                    case 2: {playsound2 = this.zombielong;
                        break; }
                    case 3, 4: playsound2 = this.zombieshort;
            }
            if (playsound2 != null) {
                CommonSounds.PlaySoundAsOwn(entity, playsound2);
            }
            this.playedlong = true;
         }
    }
    protected Function<E, Integer> fastzombieintervalsupplier = entity -> 50;
    @Override
    protected void doDelayedAction(E entity) {
        if (this.adddelay == 0) {
         BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
        if (this.target == null)
            return;
        if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
            if (this.onhit != null) {
            entity.playSound(this.onhit, 0.8f, entity.getVoicePitch());}
            entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
        } else {
            if (this.miss != null) CommonSounds.PlaySoundAsOwn(entity, miss);
        }
        }
}




}

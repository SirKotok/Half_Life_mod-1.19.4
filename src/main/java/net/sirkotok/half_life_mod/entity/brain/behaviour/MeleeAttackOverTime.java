package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Controller;
import net.sirkotok.half_life_mod.entity.mob.modinterface.VariableRangedMob;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;

import javax.annotation.Nullable;

public class MeleeAttackOverTime<E extends HalfLifeMonster> extends AnimatableMeleeAttack<E> {
    protected float attackmod;
    public int ded;
    private boolean done;
    public int ticks;
    public int tickint;
    public int firstticks;
    public int secondticks;
    public int interval;
    protected float knockbackmod;
    protected float disableshield;
    @Nullable
    protected MobEffect effect;
    @Nullable protected int effectduration;

    @Nullable
    protected SoundEvent startsound;
    @Nullable
    protected SoundEvent prepsound;

    @Nullable
    protected SoundEvent hitsound;
    @Nullable
    protected SoundEvent misssound;
    public MeleeAttackOverTime(int fullanim, int addeddelay, int start, int stop, int interval, float disablechance, float modattack
            , float modknockback, @Nullable MobEffect effect, int effectduration
            , @Nullable SoundEvent startsound, @Nullable SoundEvent prepsound, @Nullable SoundEvent hitsound
            , @Nullable SoundEvent misssound) {
        super(fullanim);

        this.ded = addeddelay;
        this.interval = interval;
        this.firstticks = start;
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.secondticks = stop;
        this.startsound = startsound;
        this.prepsound = prepsound;
        this.hitsound = hitsound;
        this.misssound = misssound;
    }

    @Override
    protected void start(E entity) {
        ticks = 0;
        done = false;
        tickint = this.interval;
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, delayTime+3+this.ded, 100, false, false, false));
        if (this.target == null)
            return;
        BehaviorUtils.lookAtEntity(entity, this.target);
        if (this.prepsound != null) entity.playSound(this.prepsound, 0.6f, entity.getVoicePitch());
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        if (this.target == null)
            return;
        HLperUtil.rotateToTarget(entity);
        BehaviorUtils.lookAtEntity(entity, this.target);
        if (this.ticks > this.firstticks && this.ticks < this.secondticks ){
            if (this.startsound != null && !done) {
                entity.playSound(this.startsound, 0.6f, entity.getVoicePitch());
                done = true;
            }
            if (this.tickint == this.interval) {
                if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target)) {
                    CommonSounds.PlaySoundAsOwn(entity, this.hitsound);
                    entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
                } else {
                     CommonSounds.PlaySoundAsOwn(entity, misssound);
                }
            tickint = -1;
            }
            tickint++;
        }
        ticks++;
    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
    }


}

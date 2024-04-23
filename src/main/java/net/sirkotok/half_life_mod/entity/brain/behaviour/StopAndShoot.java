package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Controller;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.VortigauntHL1;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.VortigauntHL2;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;

public class StopAndShoot<E extends LivingEntity & RangedAttackMob> extends AnimatableRangedAttack<E> {
    public float vel;
    public int ded;
    public int count;
    public boolean delay;

    @Nullable
    public SoundEvent startsound;
    public StopAndShoot(int delayTicks, int seconddelay, float velocity) {
        super(delayTicks);
        this.vel = velocity;
        this.ded = seconddelay;
        this.startsound = null;
        this.delay = true;
    }

    public StopAndShoot(int delayTicks, int seconddelay, float velocity, @Nullable SoundEvent startsound) {
        super(delayTicks);
        this.vel = velocity;
        this.ded = seconddelay;
        this.startsound = startsound;
        this.delay = true;
    }

    public StopAndShoot(int delayTicks, int seconddelay, float velocity, @Nullable SoundEvent startsound, boolean delay) {
        super(delayTicks);
        this.vel = velocity;
        this.ded = seconddelay;
        this.startsound = startsound;
        this.delay = delay;
    }


    @Override
    protected void start(E entity) {
        count = 0;
        if (entity instanceof VortigauntHL1 vort) vort.setcharge(2);
        if (entity instanceof VortigauntHL2 vort) vort.setcharge(2);
        entity.swing(InteractionHand.MAIN_HAND);
        BehaviorUtils.lookAtEntity(entity, this.target);
        if (entity instanceof HalfLifeMonster monster) CommonSounds.PlaySoundAsOwn(monster, startsound);
        if (entity instanceof HalfLifeNeutral monster) CommonSounds.PlaySoundAsOwn(monster, startsound);
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.SNIFFER_HAPPY, true, delayTime+10);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, delayTime+3+this.ded, 100, false, false, false));
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);

        if (count > 4 && entity instanceof VortigauntHL1 vort && vort.getcharge() == 2 ) vort.setcharge(1);
        if (count > 4 && entity instanceof VortigauntHL2 vort && vort.getcharge() == 2 ) vort.setcharge(1);
        if (count > 5 && count < 10 && entity instanceof Controller vort && vort.getcharge() == 0 ) vort.setcharge(2);
        if (count > 10 && entity instanceof Controller vort && vort.getcharge() == 2 ) vort.setcharge(0);
        count++;
        BehaviorUtils.lookAtEntity(entity, this.target);
        HLperUtil.rotateToTarget(entity);
    }

    @Override
    protected void stop(E entity) {
        if (entity instanceof VortigauntHL1 vort) vort.setcharge(0);
        if (entity instanceof VortigauntHL2 vort) vort.setcharge(0);
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.target == null)
            return;
        if (this.delay) BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.ded);
        entity.performRangedAttack(this.target, this.vel);

    }


}

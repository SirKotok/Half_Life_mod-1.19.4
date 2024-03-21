package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.sirkotok.half_life_mod.entity.mob.modinterface.DoubleRangedMob;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;

import javax.annotation.Nullable;

public class StopAndSecondShootx3<E extends LivingEntity & DoubleRangedMob> extends AnimatableSecondRangedAttack<E> {
    public float vel;
    public int ded;
    public int ticks;

    public SoundEvent event;
    public int firstticks;
    public int secondticks;
    public boolean done1;
    public boolean done2;
    public StopAndSecondShootx3(int delayTicks, int adddelay, int first, int second, float velocity, @Nullable SoundEvent startsound) {
        super(delayTicks);
        this.vel = velocity;
        this.ded = adddelay;
        this.firstticks = first;
        this.secondticks = second;
        this.event = startsound;
    }
    @Override
    protected void start(E entity) {
        done1 = false;
        if (this.event != null) entity.playSound(this.event, 0.4f, entity.getVoicePitch());
        ticks = 0;
        done2 = false;
        entity.swing(InteractionHand.MAIN_HAND);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, delayTime+3+this.ded, 100, false, false, false));
        if (this.target == null)
            return;
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        HLperUtil.rotateToTarget(entity);
        if (this.target == null)
            return;
        BehaviorUtils.lookAtEntity(entity, this.target);
        if (this.ticks > this.firstticks && !this.done1){
            entity.performSecondRangedAttack(this.target, this.vel);
            done1 = true;
        }
        if (this.ticks > this.secondticks && !this.done2){
            entity.performSecondRangedAttack(this.target, this.vel);
            done2 = true;
        }
        ticks++;
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.target == null)
            return;
        entity.performSecondRangedAttack(this.target, this.vel);
    }


}

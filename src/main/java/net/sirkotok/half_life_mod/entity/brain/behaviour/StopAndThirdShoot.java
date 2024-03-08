package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.sirkotok.half_life_mod.entity.modinterface.DoubleRangedMob;
import net.sirkotok.half_life_mod.entity.modinterface.TripleRangedMob;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;

public class StopAndThirdShoot<E extends LivingEntity & TripleRangedMob> extends AnimatableThirdRangedAttack<E> {
    public float vel;
    public int ded;
    public SoundEvent event;
    public StopAndThirdShoot(int delayTicks, int seconddelay, float velocity, @Nullable SoundEvent startsound) {
        super(delayTicks);
        this.vel = velocity;
        this.ded = seconddelay;
        this.event = startsound;
    }
    @Override
    protected void start(E entity) {
        if (this.event != null) entity.playSound(this.event, 0.4f, entity.getVoicePitch());
        entity.swing(InteractionHand.MAIN_HAND);
        BehaviorUtils.lookAtEntity(entity, this.target);
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.SNIFFER_HAPPY, true, delayTime+10);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, delayTime+3+this.ded, 100, false, false, false));
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        BehaviorUtils.lookAtEntity(entity, this.target);
        HLperUtil.rotateToTarget(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.target == null)
            return;
        entity.performThirdRangedAttack(this.target, this.vel);
    }


}

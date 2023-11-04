package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

public class StopAndShoot<E extends LivingEntity & RangedAttackMob> extends AnimatableRangedAttack<E> {
    public float vel;
    public int ded;
    public StopAndShoot(int delayTicks, int seconddelay, float velocity) {
        super(delayTicks);
        this.vel = velocity;
        this.ded = seconddelay;
    }
    @Override
    protected void start(E entity) {
        entity.swing(InteractionHand.MAIN_HAND);
        BehaviorUtils.lookAtEntity(entity, this.target);
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.SNIFFER_HAPPY, true, delayTime+10);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, delayTime+3+this.ded, 100, false, false, false));
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.target == null)
            return;
        entity.performRangedAttack(this.target, this.vel);
    }


}

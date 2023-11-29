package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.sirkotok.half_life_mod.entity.modinterface.VariableRangedMob;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;

public class StopAndShootOverTime<E extends LivingEntity & VariableRangedMob & RangedAttackMob> extends AnimatableRangedAttack<E> {
    public float vel;
    public int ded;
    public int ticks;
    public int tickint;
    public int firstticks;
    public int secondticks;
    public int interval;
    public boolean down;
    public StopAndShootOverTime(int fullanim, int addeddelay, int start, int stop, int interval,  float velocity, boolean down) {
        super(fullanim);
        this.vel = velocity;
        this.ded = addeddelay;
        this.interval = interval;
        this.firstticks = start;
        this.down = down;
        this.secondticks = stop;
    }
    @Override
    protected void start(E entity) {
        ticks = 0;
        tickint = this.interval;
        entity.swing(InteractionHand.MAIN_HAND);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, delayTime+3+this.ded, 100, false, false, false));
        if (this.target == null)
            return;
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        if (this.target == null)
            return;
        BehaviorUtils.lookAtEntity(entity, this.target);
        if (this.ticks > this.firstticks && this.ticks < this.secondticks ){
            if (this.tickint == this.interval) {
            entity.performVariableRangedAttack(this.target, this.vel, this.down ? (((float)this.secondticks - (float)this.ticks)/((float)this.secondticks-(float)this.firstticks)) : 0);
            tickint = -1;
            }
            tickint++;
        }
        ticks++;

    }

    @Override
    protected void doDelayedAction(E entity) {
    }


}

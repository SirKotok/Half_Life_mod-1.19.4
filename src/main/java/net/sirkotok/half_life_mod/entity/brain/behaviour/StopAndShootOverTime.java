package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Controller;
import net.sirkotok.half_life_mod.entity.modinterface.VariableRangedMob;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;

import javax.annotation.Nullable;

public class StopAndShootOverTime<E extends LivingEntity & VariableRangedMob & RangedAttackMob> extends AnimatableRangedAttack<E> {
    public float vel;
    public int ded;
    private boolean done;
    public int ticks;
    public int tickint;
    public int firstticks;
    public int secondticks;
    public int interval;
    public boolean down;
    @Nullable
    protected SoundEvent startsound;
    @Nullable
    protected SoundEvent prepsound;
    public StopAndShootOverTime(int fullanim, int addeddelay, int start, int stop, int interval,  float velocity, boolean down, @Nullable SoundEvent startsound, @Nullable SoundEvent prepsound) {
        super(fullanim);
        this.vel = velocity;
        this.ded = addeddelay;
        this.interval = interval;
        this.firstticks = start;
        this.down = down;
        this.secondticks = stop;
        this.startsound = startsound;
        this.prepsound = prepsound;
    }
    @Override
    protected void start(E entity) {
        ticks = 0;
        done = false;
        tickint = this.interval;
        entity.swing(InteractionHand.MAIN_HAND);
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
            if (entity instanceof Controller controller) controller.setcharge(1);
            if (this.startsound != null && !done) {
                entity.playSound(this.startsound, 0.6f, entity.getVoicePitch());
                done = true;
            }
            if (this.tickint == this.interval) {
            entity.performVariableRangedAttack(this.target, this.vel, this.down ? (((float)this.secondticks - (float)this.ticks)/((float)this.secondticks-(float)this.firstticks)) : 0);
            tickint = -1;
            }
            tickint++;
        }
        if (this.ticks > this.secondticks && entity instanceof Controller controller) controller.setcharge(0);
        ticks++;

    }

    @Override
    protected void stop(E entity) {
        if (entity instanceof Controller controller) controller.setcharge(0);
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
    }


}

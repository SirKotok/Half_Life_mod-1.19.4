package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.sirkotok.half_life_mod.entity.modinterface.AmmoCountMob;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public class StopAndReload<E extends LivingEntity & AmmoCountMob & RangedAttackMob> extends AnimatableRangedAttack<E> {

    public int ded;

    @Nullable
    protected SoundEvent bark;



    public StopAndReload(int delayTicks, int seconddelay, @Nullable SoundEvent reloadsound) {
        super(delayTicks);
        this.bark = reloadsound;
        this.ded = seconddelay;
    }
    @Override
    protected void start(E entity) {
        super.start(entity);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, delayTime+3+this.ded, 100, false, false, false));
    }






    @Override
    protected void doDelayedAction(E entity) {
        if (this.bark != null) {
            entity.playSound(this.bark);
        }
        entity.performReloadAction();
    }
}

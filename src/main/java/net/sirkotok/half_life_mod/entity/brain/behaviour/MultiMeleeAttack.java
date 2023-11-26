package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.modinterface.MultiMeleeEntity;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class MultiMeleeAttack<E extends HalfLifeMonster & MultiMeleeEntity> extends AnimatableMeleeAttack<E> {

  protected float attackmod;
  protected int counter = 0;
    protected boolean growled = false;
    protected float knockbackmod;

    protected float disableshield;
    @Nullable
    protected MobEffect effect;
    @Nullable protected int effectduration;

    @Nullable
    protected SoundEvent onhit;
    @Nullable
    protected SoundEvent startsound;

    protected boolean doanyway;

    public MultiMeleeAttack(boolean doanyway, int delayTicks, float disablechance, float modattack, float modknockback, @Nullable MobEffect effect, int effectduration, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound) {
        super(delayTicks);
        this.attackmod = modattack;
        this.knockbackmod = modknockback;
        this.effect = effect;
        this.effectduration = effectduration;
        this.disableshield = disablechance;
        this.onhit = onhit;
        this.startsound = startsound;
        this.doanyway = doanyway;
    }


    @Override
    protected void start(E entity) {
        super.start(entity);
        if (this.startsound != null) {
            entity.playSound(this.startsound, 0.8f, entity.getVoicePitch());}
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
        if (this.target == null)
            return;

        if (entity.isinside()) {
            if (this.onhit != null) {
            entity.playSound(this.onhit, 0.8f, entity.getVoicePitch());}
            entity.performMultiAttack(true, this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
        } else  if (entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target) && doanyway) {
            if (this.onhit != null) {
            entity.playSound(this.onhit, 0.8f, entity.getVoicePitch());}
            entity.ConfigurabledoHurtTarget(this.target, this.disableshield, this.attackmod, this.knockbackmod, this.effect, this.effectduration, false);
        }

}





/*	private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

	protected Function<E, Integer> attackIntervalSupplier = entity -> 20;

	@Nullable
	protected LivingEntity target = null;

	public AnimatableMeleeAttack(int delayTicks) {
		super(delayTicks);
	}



public AnimatableMeleeAttack<E> attackInterval(Function<E, Integer> supplier) {
    this.attackIntervalSupplier = supplier;

    return this;
}

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);

        return entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target);
    }

    @Override
    protected void start(E entity) {
        entity.swing(InteractionHand.MAIN_HAND);
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void stop(E entity) {
        this.target = null;
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

        if (this.target == null)
            return;

        if (!entity.getSensing().hasLineOfSight(this.target) || !entity.isWithinMeleeAttackRange(this.target))
            return;

        entity.doHurtTarget(this.target);
    } */




}

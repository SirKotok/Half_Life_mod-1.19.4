package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeMemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class AnimatableMeleeHeal<E extends Mob> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(HalfLifeMemoryModuleType.NEAREST_HEALABLE_ALLY.get(), MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

    protected Function<E, Integer> attackIntervalSupplier = entity -> 20;

    @Nullable
    protected SoundEvent onhit;
    @Nullable
    protected SoundEvent startsound;

    @Nullable
    protected LivingEntity target = null;

    public AnimatableMeleeHeal(int delayTicks, @Nullable SoundEvent onhit, @Nullable SoundEvent startsound) {
        super(delayTicks);
        this.onhit = onhit;
        this.startsound = startsound;
    }

    /**
     * Set the time between attacks.
     * @param supplier The tick value provider
     * @return this
     */
    public AnimatableMeleeHeal<E> attackInterval(Function<E, Integer> supplier) {
        this.attackIntervalSupplier = supplier;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getMemory(entity, HalfLifeMemoryModuleType.NEAREST_HEALABLE_ALLY.get());
        if (target == null) return false;

        return entity.getSensing().hasLineOfSight(this.target) && entity.isWithinMeleeAttackRange(this.target);
    }

    @Override
    protected void start(E entity) {
        entity.swing(InteractionHand.MAIN_HAND);
        BehaviorUtils.lookAtEntity(entity, this.target);
        if (this.startsound != null) {
            entity.playSound(this.startsound, 0.8f, entity.getVoicePitch());}
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

        if (this.onhit != null) {
            entity.playSound(this.onhit, 0.5f, entity.getVoicePitch());}
        this.target.heal(4);
        BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        BrainUtils.clearMemory(entity, HalfLifeMemoryModuleType.NEAREST_HEALABLE_ALLY.get());
    }
}

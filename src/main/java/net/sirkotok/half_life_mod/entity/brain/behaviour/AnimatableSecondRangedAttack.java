package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sirkotok.half_life_mod.entity.mob.modinterface.DoubleRangedMob;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class AnimatableSecondRangedAttack<E extends LivingEntity & DoubleRangedMob> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

    protected Function<E, Integer> attackIntervalSupplier = entity -> entity.level.getDifficulty() == Difficulty.HARD ? 20 : 40;
    protected float attackRadius;

    @Nullable
    protected LivingEntity target = null;

    public AnimatableSecondRangedAttack(int delayTicks) {
        super(delayTicks);

        attackRadius(16);
    }

    /**
     * Set the time between attacks.
     * @param supplier The tick value provider
     * @return this
     */
    public AnimatableSecondRangedAttack<E> attackInterval(Function<E, Integer> supplier) {
        this.attackIntervalSupplier = supplier;

        return this;
    }

    /**
     * Set the radius in blocks that the entity should be able to fire on targets.
     * @param radius The radius, in blocks
     * @return this
     */
    public AnimatableSecondRangedAttack<E> attackRadius(float radius) {
        this.attackRadius = radius * radius;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);

        return BrainUtils.canSee(entity, this.target) && entity.distanceToSqr(this.target) <= this.attackRadius;
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
        if (this.target == null)
            return;

        if (!BrainUtils.canSee(entity, this.target) || entity.distanceToSqr(this.target) > this.attackRadius)
            return;

        entity.performSecondRangedAttack(this.target, 1);
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
    }
}


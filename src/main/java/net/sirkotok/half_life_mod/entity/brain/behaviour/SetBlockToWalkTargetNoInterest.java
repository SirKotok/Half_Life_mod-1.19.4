package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class SetBlockToWalkTargetNoInterest<E extends PathfinderMob> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(SBLMemoryTypes.NEARBY_BLOCKS.get(), MemoryStatus.VALUE_PRESENT));


    protected BiFunction<E, Vec3, Float> speedModifier = (entity, targetPos) -> 1f;
    protected Predicate<E> avoidWaterPredicate = entity -> true;
    protected BiPredicate<E, Vec3> positionPredicate = (entity, pos) -> true;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }




    /**
     * Set the movespeed modifier for the path when chosen.
     * @param modifier The movespeed modifier/multiplier
     * @return this
     */
    public SetBlockToWalkTargetNoInterest<E> speedModifier(float modifier) {
        return speedModifier((entity, targetPos) -> modifier);
    }

    /**
     * Set the movespeed modifier for the path when chosen.
     * @param function The movespeed modifier/multiplier function
     * @return this
     */
    public SetBlockToWalkTargetNoInterest<E> speedModifier(BiFunction<E, Vec3, Float> function) {
        this.speedModifier = function;

        return this;
    }

    /**
     * Sets a predicate to check whether the target movement position is valid or not
     * @param predicate The predicate
     * @return this
     */
    public SetBlockToWalkTargetNoInterest<E> walkTargetPredicate(BiPredicate<E, Vec3> predicate) {
        this.positionPredicate = predicate;

        return this;
    }

    /**
     * Sets the behaviour to allow finding of positions that might be in water. <br>
     * Useful for hybrid or water-based entities.
     * @return this
     */
    public SetBlockToWalkTargetNoInterest<E> dontAvoidWater() {
        return avoidWaterWhen(entity -> false);
    }

    /**
     * Set the predicate to determine when the entity should avoid water walk targets;
     * @param predicate The predicate
     * @return this
     */
    public SetBlockToWalkTargetNoInterest<E> avoidWaterWhen(Predicate<E> predicate) {
        this.avoidWaterPredicate = predicate;

        return this;
    }


    @Override
    protected void start(E entity) {
        Vec3 targetPos = getTargetPos(entity);

        if (!this.positionPredicate.test(entity, targetPos))
            targetPos = null;

        if (targetPos == null) {
            BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        }
        else {
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, this.speedModifier.apply(entity, targetPos), 0));
        }
    }

    @Nullable
    protected Vec3 getTargetPos(E entity) {
        List<Pair<BlockPos, BlockState>> list = BrainUtils.getMemory(entity, SBLMemoryTypes.NEARBY_BLOCKS.get());
        assert list != null;
        Vec3 vec3 = entity.blockPosition().getCenter();
        if (!list.isEmpty()) {
            Pair<BlockPos, BlockState> interest = list.get(RandomSource.create().nextInt(0, list.size()));
        BlockPos pos = interest.getFirst();
            vec3 = pos.getCenter();
        }
        return vec3;
    }


}



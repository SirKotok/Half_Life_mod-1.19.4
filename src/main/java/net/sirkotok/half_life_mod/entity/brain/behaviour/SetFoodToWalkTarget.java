package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class SetFoodToWalkTarget<E extends PathfinderMob> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(ModMemoryModuleType.FOOD_SMELL.get(), MemoryStatus.VALUE_PRESENT));


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
    public SetFoodToWalkTarget<E> speedModifier(float modifier) {
        return speedModifier((entity, targetPos) -> modifier);
    }

    /**
     * Set the movespeed modifier for the path when chosen.
     * @param function The movespeed modifier/multiplier function
     * @return this
     */
    public SetFoodToWalkTarget<E> speedModifier(BiFunction<E, Vec3, Float> function) {
        this.speedModifier = function;

        return this;
    }

    /**
     * Sets a predicate to check whether the target movement position is valid or not
     * @param predicate The predicate
     * @return this
     */
    public SetFoodToWalkTarget<E> walkTargetPredicate(BiPredicate<E, Vec3> predicate) {
        this.positionPredicate = predicate;

        return this;
    }

    /**
     * Sets the behaviour to allow finding of positions that might be in water. <br>
     * Useful for hybrid or water-based entities.
     * @return this
     */
    public SetFoodToWalkTarget<E> dontAvoidWater() {
        return avoidWaterWhen(entity -> false);
    }

    /**
     * Set the predicate to determine when the entity should avoid water walk targets;
     * @param predicate The predicate
     * @return this
     */
    public SetFoodToWalkTarget<E> avoidWaterWhen(Predicate<E> predicate) {
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
        List<Vec3> list = BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get());
        if (list != null && !list.isEmpty())
        {
            int i = -1;
            Vec3 Clostestlocation = list.get(0);
            for (Vec3 location : list) {
                i++;
                if (entity.distanceToSqr(Clostestlocation) > entity.distanceToSqr(location))
                    Clostestlocation = location;
            }
            BrainUtils.setMemory(entity, ModMemoryModuleType.FOOD_ID.get(), i);
            BrainUtils.setMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_1.get(), RandomSource.create().nextInt(1000));
            return Clostestlocation;
        }
        else {
            int index = RandomSource.create().nextInt(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get()).size());
        Vec3 targetpos = BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get()).get(index);
        BrainUtils.setMemory(entity, ModMemoryModuleType.FOOD_ID.get(), index);
        BrainUtils.setMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_1.get(), RandomSource.create().nextInt(1000));
        return targetpos;
    }}
}



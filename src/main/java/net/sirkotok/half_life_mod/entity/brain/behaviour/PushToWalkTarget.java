package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PushToWalkTarget<E extends PathfinderMob> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT));


    public PushToWalkTarget() {
        super(0);
        cooldownFor(entity -> entity.getRandom().nextInt(1, 20));
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }



    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        Brain<?> brain = entity.getBrain();
        WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);

        if (!hasReachedTarget(entity, walkTarget)) {
            return true;
        }

        BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
        BrainUtils.clearMemory(brain, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);

        return false;
    }



    protected boolean hasReachedTarget(E entity, WalkTarget target) {
        return target.getTarget().currentBlockPosition().distManhattan(entity.blockPosition()) <= target.getCloseEnoughDist();
    }


    @Override
    protected void doDelayedAction(E entity) {
        super.doDelayedAction(entity);
        if (BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET) != null) {
            Vec3 targetpos = BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET).getTarget().currentPosition();
            Vec3 pos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
           float f = BrainUtils.getTargetOfEntity(entity) != null ? BrainUtils.getTargetOfEntity(entity).getEyeHeight() : RandomSource.create().nextFloat()*2;
            Vec3 direction = new Vec3(targetpos.x() - pos.x(), targetpos.y() - pos.y() + f, targetpos.z() - pos.z());
            Vec3 dir = direction.normalize().multiply(0.4, 0.15, 0.4);
            entity.setDeltaMovement(entity.getDeltaMovement().add(dir));
        }
    }
}

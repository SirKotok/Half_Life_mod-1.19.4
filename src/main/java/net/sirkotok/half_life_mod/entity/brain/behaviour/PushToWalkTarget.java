package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Function;

public class PushToWalkTarget<E extends PathfinderMob> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT));
    protected Function<E, Float> SpeedXZModProvider = entity -> 1f;
    protected Function<E, Float> SpeedYModProvider = entity -> 1f;
    public boolean down;
    public PushToWalkTarget() {
        this(false);
    }

    public PushToWalkTarget(boolean k) {
        super(0);
        cooldownFor(entity -> entity.getRandom().nextInt(1, 20));
        this.down = k;
    }


    public PushToWalkTarget<E> setXZMoveSpeedMod(Function<E, Float> speed) {
        this.SpeedXZModProvider = speed;
        return this;
    }

    public PushToWalkTarget<E> setYMoveSpeedMod(Function<E, Float> speed) {
        this.SpeedYModProvider = speed;
        return this;
    }


    protected float getXZSpeedMod(E entity){
        return this.SpeedXZModProvider.apply(entity);
    }
    protected float getYSpeedMod(E entity){
        return this.SpeedYModProvider.apply(entity);
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
            if (this.down) f = 0;
            Vec3 direction = new Vec3(targetpos.x() - pos.x(), targetpos.y() - pos.y() + f, targetpos.z() - pos.z());
            float f1 = this.getXZSpeedMod(entity);
            float f2 = this.getYSpeedMod(entity);
            Vec3 dir = direction.normalize().multiply(0.4, 0.15, 0.4).multiply(f1,f2,f1);
            entity.setDeltaMovement(entity.getDeltaMovement().add(dir));
        }
    }
}

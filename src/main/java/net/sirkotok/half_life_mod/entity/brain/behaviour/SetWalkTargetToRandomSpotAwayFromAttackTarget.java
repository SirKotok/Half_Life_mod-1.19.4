package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SetWalkTargetToRandomSpotAwayFromAttackTarget<E extends PathfinderMob> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
    protected SquareRadius radius = new SquareRadius(8, 5);
    protected float speedModifier = 1;


    public SetWalkTargetToRandomSpotAwayFromAttackTarget<E> speedMod(float speedModifier) {
        this.speedModifier = speedModifier;

        return this;
    }

    public SetWalkTargetToRandomSpotAwayFromAttackTarget<E> radius(int xz, int y) {
        this.radius = new SquareRadius(xz, y);

        return this;
    }



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        Vec3 targetPos = getTargetPos(entity);
        Brain<?> brain = entity.getBrain();
        LivingEntity target = BrainUtils.getTargetOfEntity(entity);

        if (entity.getSensing().hasLineOfSight(target) && BehaviorUtils.isWithinAttackRange(entity, target, 1)) {
            BrainUtils.clearMemory(brain, MemoryModuleType.WALK_TARGET);
        }
        else {

            if (targetPos == null) {
                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
            }
            else {
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos,this.speedModifier, 0));
            }


        }
    }


    @Nullable
    protected Vec3 getTargetPos(E entity) {
            LivingEntity targetentity = BrainUtils.getTargetOfEntity(entity);
            Vec3 target = new Vec3 (targetentity.getX(), targetentity.getY(), targetentity.getZ());
            return DefaultRandomPos.getPosAway(entity, (int)this.radius.xzRadius(), (int)this.radius.yRadius(), target);
    }
}

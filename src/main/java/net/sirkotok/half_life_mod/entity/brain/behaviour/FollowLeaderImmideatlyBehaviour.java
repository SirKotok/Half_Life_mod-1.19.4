package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.sirkotok.half_life_mod.entity.mob.modinterface.HasLeaderMob;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class FollowLeaderImmideatlyBehaviour<E extends Mob & HasLeaderMob<E>> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED));


    protected float speedModifier = 1;

    /**
     * Set the movespeed modifier for the entity when moving to the target.
     * @param speedModifier The movespeed modifier/multiplier
     * @return this
     */
    public FollowLeaderImmideatlyBehaviour<E> speedMod(float speedModifier) {
        this.speedModifier = speedModifier;
        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        LivingEntity leader = entity.getLeader();
        if (leader == entity || leader == null) return;
        if (entity.getSensing().hasLineOfSight(leader) && entity.distanceTo(leader) < 4) {
                    BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                }
                else {
                    BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(leader, true));
                    BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(leader, false), this.speedModifier, 3));
                }

        }
    }


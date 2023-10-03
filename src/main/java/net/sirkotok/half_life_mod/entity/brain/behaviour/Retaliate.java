package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;


import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Predicate;

public class Retaliate<E extends Mob> extends ExtendedBehaviour<E> {
    // The generic type E here represents the minimum entity type of the entities that could use this behaviour. Other examples might be Mob, or PathfinderMob

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.HURT_BY_ENTITY, MemoryStatus.VALUE_PRESENT));


    protected Predicate<LivingEntity> targetPredicate = entity -> entity.isAlive() && (!(entity instanceof Player player) || !player.isCreative()); // Predicate that determines an applicable target
    protected Predicate<E> canTargetPredicate = entity -> true; // Predicate that determines whether our entity is ready to target or not


    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS; // Return our static memory requirements here. Can also be non-static if required, but that's less performant
    }

    @Override // Use our can target predicate here to see if we should run the behaviour
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return this.canTargetPredicate.test(entity);
    }

    @Override // Actually handle the function of the behaviour here
    protected void start(E entity) {
        LivingEntity target = BrainUtils.getMemory(entity, MemoryModuleType.HURT_BY_ENTITY); // put the entity that hurt us into target

        if (target != null && this.targetPredicate.test(target)) { // If we have a valid target then, Target found, set the target in memory, and reset the unreachable target timer
            BrainUtils.setMemory(entity, MemoryModuleType.ATTACK_TARGET, target);
            BrainUtils.clearMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        }
    }
}
package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;


import net.minecraft.world.entity.player.Player;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.ModTags;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class Retaliate<E extends Mob> extends ExtendedBehaviour<E> {
    // The generic type E here represents the minimum entity type of the entities that could use this behaviour. Other examples might be Mob, or PathfinderMob

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.HURT_BY_ENTITY, MemoryStatus.VALUE_PRESENT), Pair.of(ModMemoryModuleType.RETALIATECOOLDOWN.get(), MemoryStatus.VALUE_ABSENT));


    protected Predicate<LivingEntity> targetPredicate = entity -> entity.isAlive() && (!(entity instanceof Player player) || !player.isCreative()); // Predicate that determines an applicable target
    protected Predicate<E> canTargetPredicate = entity -> true; // Predicate that determines whether our entity is ready to target or not

    protected Function<E, Integer> cooldowntime = entity -> 40;



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS; // Return our static memory requirements here. Can also be non-static if required, but that's less performant
    }


    @Override // Use our can target predicate here to see if we should run the behaviour
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        LivingEntity target = BrainUtils.getMemory(entity, MemoryModuleType.HURT_BY_ENTITY);
        if (target == null) return false;
        boolean flag = HLperUtil.issameteam(entity, target);

        return this.canTargetPredicate.test(entity) && !flag;
    }

    public Retaliate<E> SetTime(Function<E, Integer> timeProvider) {
        this.cooldowntime = timeProvider;
        return this;
    }



    @Override // Actually handle the function of the behaviour here
    protected void start(E entity) {
        LivingEntity target = BrainUtils.getMemory(entity, MemoryModuleType.HURT_BY_ENTITY); // put the entity that hurt us into target




        // If we have a valid target then, Target found, set the target in memory, and reset the unreachable target timer
        if (target != null && this.targetPredicate.test(target)) {
            BrainUtils.setForgettableMemory(entity, ModMemoryModuleType.RETALIATECOOLDOWN.get(), true, this.cooldowntime.apply(entity));
            BrainUtils.setMemory(entity, MemoryModuleType.ATTACK_TARGET, target);
            BrainUtils.clearMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        }
    }
}
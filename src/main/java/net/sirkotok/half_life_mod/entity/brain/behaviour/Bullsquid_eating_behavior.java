package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob.custom.Bullsquid;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Consumer;

public class Bullsquid_eating_behavior<E extends Bullsquid> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(ModMemoryModuleType.FOOD_SMELL.get(), MemoryStatus.VALUE_PRESENT), Pair.of(ModMemoryModuleType.FOOD_ID.get(), MemoryStatus.VALUE_PRESENT));

    public Bullsquid_eating_behavior() {
        super(0);

    }

    private Consumer<E> callback;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    public Bullsquid_eating_behavior<E> callback(Consumer<E> callback) {
        this.callback = callback;
        return this;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        Vec3 location = BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get()).get(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_ID.get()));
        return entity.distanceToSqr(location) < 3f;
    }

    @Override
    protected void start(E entity) {
        BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);

    }


    protected void StopAiBasedOnHunger(E entity){
        if (BrainUtils.getMemory(entity, ModMemoryModuleType.HUNGRY.get()) == null) {
            entity.heal(entity.getMaxHealth());
            entity.stopAiFor(740 );
        } else {entity.stopAiFor(95);}

    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (BrainUtils.getMemory(entity, ModMemoryModuleType.HUNGRY.get()) == null) {
            entity.triggerAnim("onetime", "eatlong");
        }
        else entity.triggerAnim("onetime", "nothungry");
        this.callback.accept(entity);
        this.StopAiBasedOnHunger(entity);


        if (BrainUtils.getMemory(entity, ModMemoryModuleType.HUNGRY.get()) == null) {
            BrainUtils.setForgettableMemory(entity,    ModMemoryModuleType.HUNGRY.get(), false, 3000);
        }
        List<Vec3> food = BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get());
         food.remove(Math.min(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_ID.get()), food.size()));
        BrainUtils.setMemory(entity,  ModMemoryModuleType.FOOD_SMELL.get(), food);
        BrainUtils.clearMemory(entity, ModMemoryModuleType.FOOD_ID.get());
    }
}

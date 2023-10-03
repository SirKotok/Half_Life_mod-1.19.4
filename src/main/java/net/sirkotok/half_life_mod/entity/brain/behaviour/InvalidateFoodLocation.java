package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob.custom.Bullsquid;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvalidateFoodLocation<E extends LivingEntity> extends ExtendedBehaviour<E> {

    //TODO: this makes null pointer exeption, needs rewrite, dont use

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(ModMemoryModuleType.FOOD_SMELL.get(), MemoryStatus.VALUE_PRESENT), Pair.of(ModMemoryModuleType.FOOD_ID.get(), MemoryStatus.VALUE_PRESENT), Pair.of(ModMemoryModuleType.RNG_COMPARITOR_1.get(), MemoryStatus.VALUE_PRESENT));

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected void start(E entity) {

        if (BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_ID.get()) == null || (BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get()) == null)) {
            return;
        }

        if (BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get()).get((BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_ID.get()))) == null) {
            return;
        }

        int rng = 0;
        if (BrainUtils.getMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_1.get()) != null) rng = BrainUtils.getMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_1.get());
        if (BrainUtils.getMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_CHECK.get()) == null) {
            BrainUtils.setMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_CHECK.get(), rng);
        }
       if (rng == (BrainUtils.getMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_CHECK.get()))) {
           return;
       }

        BrainUtils.setMemory(entity, ModMemoryModuleType.RNG_COMPARITOR_CHECK.get(), rng);



       if (BrainUtils.getMemory(entity, ModMemoryModuleType.CHECKED_LOCATIONS.get()) == null) {
           BrainUtils.setMemory(entity, ModMemoryModuleType.CHECKED_LOCATIONS.get(), new ArrayList<Vec3>());
       }

       if (BrainUtils.getMemory(entity, ModMemoryModuleType.CHECKED_LOCATIONS.get()).contains(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get()).get(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_ID.get())))) {
           if (RandomSource.create().nextInt(2) == 0) {
               List<Vec3> food = BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get());
               food.remove(Math.min(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_ID.get()), food.size()));
               BrainUtils.setMemory(entity,  ModMemoryModuleType.FOOD_SMELL.get(), food);
               BrainUtils.clearMemory(entity, ModMemoryModuleType.FOOD_ID.get());
           }
            return;
       }

       List<Vec3> checked = BrainUtils.getMemory(entity, ModMemoryModuleType.CHECKED_LOCATIONS.get());
        assert checked != null;
        checked.add(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_SMELL.get()).get(BrainUtils.getMemory(entity, ModMemoryModuleType.FOOD_ID.get())));
        BrainUtils.setMemory(entity, ModMemoryModuleType.CHECKED_LOCATIONS.get(), checked);



    }
}

package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Houndeye;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class HoundeyeSleepingBehaviour<E extends Houndeye> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(ModMemoryModuleType.HUNGRY.get(), MemoryStatus.VALUE_ABSENT));

    public HoundeyeSleepingBehaviour() {
        super(0);
    }


    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }



    @Override
    protected void start(E entity) {
        List<Houndeye> dogs = entity.getDogs();
        for (Houndeye dog : dogs) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 100, false, false, false));
            BrainUtils.clearMemory(dog, MemoryModuleType.WALK_TARGET);
        }
    }


    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        List<Houndeye> dogs = entity.getDogs();
        boolean flag = true;
        for (Houndeye dog : dogs) {
            if (dog.getLastHurtByMob() != null) flag = false;
        }
        return super.checkExtraStartConditions(level, entity) && flag;
    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {

        BrainUtils.setForgettableMemory(entity, ModMemoryModuleType.HUNGRY.get(), false, 5000);

        List<Houndeye> dogs = entity.getDogs();
        for (Houndeye dog : dogs) {
            if (dog == entity) dog.triggerAnim("long", "guard");
            else dog.triggerAnim("long", "sleep");
            dog.stopAiFor(1220);
        }



    }
}

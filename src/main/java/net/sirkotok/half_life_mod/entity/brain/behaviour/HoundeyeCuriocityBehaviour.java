package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Houndeye;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class HoundeyeCuriocityBehaviour<E extends Houndeye> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(HalfLifeMemoryModuleType.LOCATION_OF_INTEREST.get(), MemoryStatus.VALUE_PRESENT));

    public HoundeyeCuriocityBehaviour() {
        super(0);

    }



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }



    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        Vec3 location = BrainUtils.getMemory(entity, HalfLifeMemoryModuleType.LOCATION_OF_INTEREST.get()).getFirst().getCenter();
        return entity.distanceToSqr(location) < 2f;
    }

    @Override
    protected void start(E entity) {
        BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);

    }




    @Override
    protected void stop(E entity) {
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
        entity.triggerAnim("long", "curious");
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 100, false, false, false));
        BrainUtils.clearMemory(entity, HalfLifeMemoryModuleType.LOCATION_OF_INTEREST.get());
    }
}

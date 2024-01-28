package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import com.mojang.util.UUIDTypeAdapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.UUID;

public class HL1FollowSpeedup<E extends HalfLifeNeutral> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(ModMemoryModuleType.FOLLOWING_PLAYER.get(), MemoryStatus.VALUE_PRESENT));


    protected float speedModifier = 1;


    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
       LivingEntity player = BrainUtils.getMemory(entity, ModMemoryModuleType.FOLLOWING_PLAYER.get());
       if (player != null && entity.distanceTo(player) > 7) {
           entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false, false));
       }
        if (player != null && entity.distanceTo(player) > 12) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 2, false, false, false));
        }
        if (player != null && entity.distanceTo(player) > 25) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 3, false, false, false));
        }
    }
}

package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.block.HalfLifeBlocks;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class VortigoreLayAnEggBehavior<E extends Voltigore> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(ModMemoryModuleType.LOCATION_OF_INTEREST.get(), MemoryStatus.VALUE_PRESENT), Pair.of(ModMemoryModuleType.NEARBY_BLOCKS_TWO.get(), MemoryStatus.REGISTERED));

    public VortigoreLayAnEggBehavior() {
        super(8);

    }



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }



    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        BlockPos pos = BrainUtils.getMemory(entity, ModMemoryModuleType.LOCATION_OF_INTEREST.get()).getFirst();
        BlockPos pos2 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        Vec3 location = pos2.getCenter();
        BlockState blockstate = level.getBlockState(pos2);

        if (BrainUtils.getMemory(entity, ModMemoryModuleType.NEARBY_BLOCKS_TWO.get()) != null) {
            if (BrainUtils.getMemory(entity, ModMemoryModuleType.NEARBY_BLOCKS_TWO.get()).size() > 10) return false;
        }

        return entity.distanceToSqr(location) < 1f && blockstate.isAir();
    }

    @Override
    protected void start(E entity) {
        BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 100, false, false, false));
        entity.triggerAnim("onetime", "egg");
    }




    @Override
    protected void stop(E entity) {
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
        BlockPos pos = BrainUtils.getMemory(entity, ModMemoryModuleType.LOCATION_OF_INTEREST.get()).getFirst();
        BlockPos pos2 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        BlockState blockstate = entity.level.getBlockState(pos2);

        if (blockstate.isAir()) {
        entity.level.setBlock(pos2, HalfLifeBlocks.VOLTIGORE_EGG.get().defaultBlockState(), 2);
        entity.playSound(SoundEvents.TURTLE_LAY_EGG);
        entity.setPersistenceRequired();
        }
        BrainUtils.clearMemory(entity, ModMemoryModuleType.LOCATION_OF_INTEREST.get());
    }
}

package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeMemoryModuleType;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToDoubleFunction;

public class SetJumpTargetToRandomSpotAwayFromAttackTarget<E extends PathfinderMob> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(HalfLifeMemoryModuleType.TARGET_LONGJUMP_LOCATION.get(), MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
    protected SquareRadius radius = new SquareRadius(5, 3);
    protected float speedModifier = 1;


    public SetJumpTargetToRandomSpotAwayFromAttackTarget() {
    }


    public SetJumpTargetToRandomSpotAwayFromAttackTarget<E> speedMod(float speedModifier) {
        this.speedModifier = speedModifier;

        return this;
    }





    public SetJumpTargetToRandomSpotAwayFromAttackTarget<E> radius(int xz, int y) {
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

            if (targetPos == null) {
                BrainUtils.clearMemory(entity, HalfLifeMemoryModuleType.TARGET_LONGJUMP_LOCATION.get());
            }
            else {
                BrainUtils.setForgettableMemory(entity, HalfLifeMemoryModuleType.TARGET_LONGJUMP_LOCATION.get(), targetPos, 50);
            }

    }


    @Nullable
    protected Vec3 getTargetPos(E entity) {
         return LandRandomPos.getPosAway(entity, (int)this.radius.xzRadius(), (int)this.radius.yRadius(), HLperUtil.TargetOrThis(entity).position());
    }





}

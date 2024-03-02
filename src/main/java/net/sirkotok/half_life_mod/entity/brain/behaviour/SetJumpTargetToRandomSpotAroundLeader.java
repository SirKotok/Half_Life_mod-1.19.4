package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.modinterface.HasLeaderMob;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToDoubleFunction;

public class SetJumpTargetToRandomSpotAroundLeader<E extends PathfinderMob & HasLeaderMob<E>> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(ModMemoryModuleType.TARGET_LONGJUMP_LOCATION.get(), MemoryStatus.REGISTERED));
    protected SquareRadius radius = new SquareRadius(5, 3);
    protected float speedModifier = 1;
    public final int self012;

    public SetJumpTargetToRandomSpotAroundLeader(int self012) {
        this.self012 = self012;
    }


    public SetJumpTargetToRandomSpotAroundLeader<E> speedMod(float speedModifier) {
        this.speedModifier = speedModifier;

        return this;
    }


    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        LivingEntity leader = entity.getLeader();
        return super.checkExtraStartConditions(level, entity) && !(leader == entity || leader == null);
    }

    public SetJumpTargetToRandomSpotAroundLeader<E> radius(int xz, int y) {
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
                BrainUtils.clearMemory(entity, ModMemoryModuleType.TARGET_LONGJUMP_LOCATION.get());
            }
            else {
                BrainUtils.setForgettableMemory(entity, ModMemoryModuleType.TARGET_LONGJUMP_LOCATION.get(), targetPos, 50);
            }

    }


    @Nullable
    protected Vec3 getTargetPos(E entity) {
            LivingEntity targetentity = entity.getLeader();
        if (self012 == 2 || (self012 == 1 && RandomSource.create().nextFloat() < 0.2f)) {targetentity = entity;}
         return this.getPos(targetentity, (int)this.radius.xzRadius(), (int)this.radius.yRadius());
    }


    public float getWalkTargetValue(BlockPos pPos) {
        return 0;
    }

    public Vec3 getPos(LivingEntity pMob, int pRadius, int pVerticalRange) {
        return getPos(pMob, pRadius, pVerticalRange, this::getWalkTargetValue);
    }


    public BlockPos movePosUpOutOfSolid(LivingEntity pMob, BlockPos pPos) {
        pPos = RandomPos.moveUpOutOfSolid(pPos, pMob.level.getMaxBuildHeight(), (p_148534_) -> {
            return pMob.level.getBlockState(p_148534_).getMaterial().isSolid();
        });
        return !(pMob.level.getFluidState(pPos).is(FluidTags.WATER) || pMob.level.getFluidState(pPos).is(FluidTags.LAVA)) ? pPos : null;
    }


    public Vec3 getPos(LivingEntity pMob, int pRadius, int pYRange, ToDoubleFunction<BlockPos> pToDoubleFunction) {
        return RandomPos.generateRandomPos(() -> {
            BlockPos blockpos = RandomPos.generateRandomDirection(pMob.getRandom(), pRadius, pYRange);
            BlockPos blockpos1 = generateRandomPosTowardDirection(pMob, pRadius, blockpos);
            return blockpos1 == null ? null : movePosUpOutOfSolid(pMob, blockpos1);
        }, pToDoubleFunction);
    }

    public BlockPos generateRandomPosTowardDirection(LivingEntity pMob, int pRadius, BlockPos pPos) {
        return generateRandomPosTowardDirection(pMob, pRadius, pMob.getRandom(), pPos);
    }

    /**
     * @return a random position within range, only if the mob is currently restricted
     */
    public BlockPos generateRandomPosTowardDirection(LivingEntity pMob, int pRange, RandomSource pRandom, BlockPos pPos) {
        int i = pPos.getX();
        int j = pPos.getZ();
        if (pRange > 1) {
            BlockPos blockpos = pMob.blockPosition();
            if (pMob.getX() > (double)blockpos.getX()) {
                i -= pRandom.nextInt(pRange / 2);
            } else {
                i += pRandom.nextInt(pRange / 2);
            }

            if (pMob.getZ() > (double)blockpos.getZ()) {
                j -= pRandom.nextInt(pRange / 2);
            } else {
                j += pRandom.nextInt(pRange / 2);
            }
        }

        return BlockPos.containing((double)i + pMob.getX(), (double)pPos.getY() + pMob.getY(), (double)j + pMob.getZ());
    }


}

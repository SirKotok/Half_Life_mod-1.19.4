package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class LongJumpToJumpTarget<E extends HalfLifeMonster> extends DelayedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(ModMemoryModuleType.JUMPING.get(), MemoryStatus.VALUE_ABSENT), Pair.of(ModMemoryModuleType.TARGET_LONGJUMP_LOCATION.get(), MemoryStatus.VALUE_PRESENT));
    private static final List<Integer> ALLOWED_ANGLES = Lists.newArrayList(65, 70, 75, 80);
    protected final float maxJumpVelocity;
    @Nullable
    public SoundEvent jumpsound;

    protected Vec3 optimal_jump_vector;
    protected Vec3 targetPos;
    protected int tick;

    public LongJumpToJumpTarget(int delay, float maxJumpVelocity, @Nullable SoundEvent jumpsound) {
        super(delay);
        this.maxJumpVelocity = maxJumpVelocity;
        this.jumpsound = jumpsound;

    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {

        this.targetPos = null;
        this.optimal_jump_vector = null;


        this.targetPos = BrainUtils.getMemory(entity, ModMemoryModuleType.TARGET_LONGJUMP_LOCATION.get());
        if (targetPos == null && BrainUtils.getTargetOfEntity(entity) != null) targetPos = BrainUtils.getTargetOfEntity(entity).position();
        if (targetPos == null) return false;
        this.optimal_jump_vector = this.calculateOptimalJumpVector(entity, targetPos);
        if (this.optimal_jump_vector == null) return false;

        return super.checkExtraStartConditions(level, entity);
    }

    @Override
    protected void start(E entity) {
        tick = 0;
       Vec3 vec31 = this.optimal_jump_vector;

        entity.setDiscardFriction(true);
        double d0 = vec31.length();
        double d1 = d0 + entity.getJumpBoostPower();
        entity.setDeltaMovement(vec31.scale(d1 / d0));

        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, delayTime+4);


        HLperUtil.slowEntityFor(entity, 20);
        CommonSounds.PlaySoundAsOwn(entity, jumpsound);




    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        if (this.tick > 8 && entity.isOnGround() && entity.shouldDiscardFriction()) {entity.setDiscardFriction(false);
        }
        else tick++;
    }

    @Override
    protected void doDelayedAction(E entity) {
        entity.setDiscardFriction(false);
        super.doDelayedAction(entity);
    }

    @javax.annotation.Nullable
    protected Vec3 calculateOptimalJumpVector(Mob pMob, Vec3 pTarget) {
        List<Integer> list = Lists.newArrayList(ALLOWED_ANGLES);
        Collections.shuffle(list);

        for(int i : list) {
            Vec3 vec3 = this.calculateJumpVectorForAngle(pMob, pTarget, i);
            if (vec3 != null) {
                return vec3;
            }
        }

        return null;
    }

    @javax.annotation.Nullable
    private Vec3 calculateJumpVectorForAngle(Mob pMob, Vec3 pTarget, int pAngle) {
        Vec3 vec3 = pMob.position();
        Vec3 vec31 = (new Vec3(pTarget.x - vec3.x, 0.0D, pTarget.z - vec3.z)).normalize().scale(0.5D);
        pTarget = pTarget.subtract(vec31);
        Vec3 vec32 = pTarget.subtract(vec3);
        float f = (float)pAngle * (float)Math.PI / 180.0F;
        double d0 = Math.atan2(vec32.z, vec32.x);
        double d1 = vec32.subtract(0.0D, vec32.y, 0.0D).lengthSqr();
        double d2 = Math.sqrt(d1);
        double d3 = vec32.y;
        double d4 = Math.sin((double)(2.0F * f));
        double d5 = 0.08D;
        double d6 = Math.pow(Math.cos((double)f), 2.0D);
        double d7 = Math.sin((double)f);
        double d8 = Math.cos((double)f);
        double d9 = Math.sin(d0);
        double d10 = Math.cos(d0);
        double d11 = d1 * 0.08D / (d2 * d4 - 2.0D * d3 * d6);
        if (d11 < 0.0D) {
            return null;
        } else {
            double d12 = Math.sqrt(d11);
            if (d12 > (double)this.maxJumpVelocity) {
                return null;
            } else {
                double d13 = d12 * d8;
                double d14 = d12 * d7;
                int i = Mth.ceil(d2 / d13) * 2;
                double d15 = 0.0D;
                Vec3 vec33 = null;
                EntityDimensions entitydimensions = pMob.getDimensions(Pose.LONG_JUMPING);

                for(int j = 0; j < i - 1; ++j) {
                    d15 += d2 / (double)i;
                    double d16 = d7 / d8 * d15 - Math.pow(d15, 2.0D) * 0.08D / (2.0D * d11 * Math.pow(d8, 2.0D));
                    double d17 = d15 * d10;
                    double d18 = d15 * d9;
                    Vec3 vec34 = new Vec3(vec3.x + d17, vec3.y + d16, vec3.z + d18);
                    if (vec33 != null && !this.isClearTransition(pMob, entitydimensions, vec33, vec34)) {
                        return null;
                    }

                    vec33 = vec34;
                }

                return (new Vec3(d13 * d10, d14, d13 * d9)).scale((double)0.95F);
            }
        }
    }


    protected boolean checkExtraStartConditions(ServerLevel pLevel, Mob pOwner) {
        return pOwner.isOnGround() && !pOwner.isInWater() && !pOwner.isInLava() && !pLevel.getBlockState(pOwner.blockPosition()).is(Blocks.HONEY_BLOCK);
    }


    private boolean isClearTransition(Mob pMob, EntityDimensions pDimensions, Vec3 pStart, Vec3 pEnd) {
        Vec3 vec3 = pEnd.subtract(pStart);
        double d0 = (double)Math.min(pDimensions.width, pDimensions.height);
        int i = Mth.ceil(vec3.length() / d0);
        Vec3 vec31 = vec3.normalize();
        Vec3 vec32 = pStart;

        for(int j = 0; j < i; ++j) {
            vec32 = j == i - 1 ? pEnd : vec32.add(vec31.scale(d0 * (double)0.9F));
            if (!pMob.level.noCollision(pMob, pDimensions.makeBoundingBox(vec32))) {
                return false;
            }
        }

        return true;
    }





}

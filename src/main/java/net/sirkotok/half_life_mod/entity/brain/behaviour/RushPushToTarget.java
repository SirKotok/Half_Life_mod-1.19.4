package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeMonster;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.modinterface.RushingMob;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.sirkotok.half_life_mod.util.InfightingUtil;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;

public class RushPushToTarget<E extends Mob & RushingMob> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), Pair.of(ModMemoryModuleType.RUSHING.get(), MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));

    protected BiFunction<E, Vec3, Float> speedModifier; // = (entity, targetPos) -> 1f;
    public int startticks;
    public int wallticks;
    private BlockPos targetPosition;
    public int bodyticks;
    public Vec3 pushvec3;
    public boolean stopifbodyhit;
    protected int ticks;
    @Nullable
    protected SoundEvent rushsound;
    public RushPushToTarget(int delayTicks, int startticks, int bodyticks, int wallticks, BiFunction<E, Vec3, Float> speedmod, boolean stop, @Nullable SoundEvent rushsound) {
        super(delayTicks);
        this.startticks = startticks;
        this.bodyticks = bodyticks;
        this.wallticks = wallticks;
        this.speedModifier = speedmod;
        this.stopifbodyhit = stop;
        this.rushsound = rushsound;
    }



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        this.pushvec3 = null;
        this.ticks = 0;
        if (this.rushsound != null) entity.playSound(rushsound, 0.6f, entity.getVoicePitch());
        BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
        BrainUtils.setForgettableMemory(entity, ModMemoryModuleType.RUSHING.get(), true, delayTime+3);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, startticks+1, 100, false, false, false));
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        Vec3 pPos = entity.position();
        if (this.pushvec3 != null) {
            this.setcorrectrotation(entity, entity.getDeltaMovement());
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 100, false, false, false));
        }
        LivingEntity entity1 = BrainUtils.getTargetOfEntity(entity);
        if (this.ticks % 4 == 0) {
            if (entity1 != null && this.ticks > startticks) {
                if (this.pushvec3 == null) this.pushvec3 =
                        new Vec3(entity1.getX() - entity.getX(), 0, entity1.getZ() - entity.getZ()).normalize();

                entity.setDeltaMovement(entity1.getDeltaMovement().add(pushvec3));
                List<Entity> list = ((ServerLevel) entity.level).getEntities(entity, entity.getrushingbox(), obj -> !(obj.equals(entity)) && obj instanceof LivingEntity living && !InfightingUtil.issameteam(entity, living));
                if (!list.isEmpty()) {
                    for (Entity entity2 : list) {
                        if (entity2 instanceof LivingEntity alive && ticks % 4 == 0) {
                            if (entity instanceof HalfLifeMonster) {
                                ((HalfLifeMonster) entity).ConfigurabledoHurtTarget(alive, 0.5f, 1f, 2f, null, 0, false);
                            } else entity.doHurtTarget(alive);
                            if (alive.equals(entity1) || alive.getBbWidth() > entity.getBbWidth()) {
                                entity.hitbody();
                                if (this.stopifbodyhit) {
                                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.bodyticks, 100, false, false, false));
                                this.delayFinishedAt = 0;
                                }
                            }
                        }
                    }
                }
            }

        //    Vec3 vec3 = this.collide(pPos, (ServerLevel) entity.getLevel(), entity);
        //    boolean flag2 = !Mth.equal(pPos.x, vec3.x);
        //    boolean flag = !Mth.equal(pPos.z, vec3.z);
            if (entity.horizontalCollision) { //flag || flag2 ||
                entity.hitwall();
                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.wallticks, 100, false, false, false));
                this.delayFinishedAt = 0;
            }
        }
        ticks++;
    }


    @Override
    protected void doDelayedAction(E entity) {
    }




    //TODO: fix rotaiton

   protected void setcorrectrotation(E entity, Vec3 vec3){
       double d0 = vec3.x;
       double d1 = vec3.z;
       float f9 = (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
       entity.setYRot(this.rotlerp(entity.getYRot(), f9, 90.0F));
       entity.yBodyRot = f9;
   }

    protected float rotlerp(float pSourceAngle, float pTargetAngle, float pMaximumChange) {
        float f = Mth.wrapDegrees(pTargetAngle - pSourceAngle);
        if (f > pMaximumChange) {
            f = pMaximumChange;
        }

        if (f < -pMaximumChange) {
            f = -pMaximumChange;
        }

        float f1 = pSourceAngle + f;
        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }

        return f1;
    }




}

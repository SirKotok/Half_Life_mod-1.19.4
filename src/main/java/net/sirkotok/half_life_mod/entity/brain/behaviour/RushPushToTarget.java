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
        LivingEntity entity1 = BrainUtils.getTargetOfEntity(entity);
        if (this.ticks % 4 == 0) {
            this.setcorrectrotation(entity);
            if (entity1 != null && this.ticks > startticks) {
                if (this.pushvec3 == null) this.pushvec3 =
                        new Vec3(entity1.getX() - entity.getX(), 0, entity1.getZ() - entity.getZ()).normalize();
                entity.setDeltaMovement(entity1.getDeltaMovement().add(pushvec3));
                entity.setYBodyRot((float)Math.atan2(pushvec3.x, pushvec3.z)*180/Mth.PI);
                List<Entity> list = ((ServerLevel) entity.level).getEntities(entity, entity.getrushingbox(), obj -> !(obj.equals(entity)) && obj instanceof LivingEntity living && !HLperUtil.issameteam(entity, living));
                if (!list.isEmpty()) {
                    for (Entity entity2 : list) {
                        if (entity2 instanceof LivingEntity alive && ticks % 4 == 0) {
                            if (entity instanceof HalfLifeMonster<?>) {
                                ((HalfLifeMonster<?>) entity).ConfigurabledoHurtTarget(alive, 0.5f, 1f, 2f, null, 0, false);
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

   protected void setcorrectrotation(E entity){
       if (this.targetPosition != null && (!entity.level.isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= entity.level.getMinBuildHeight())) {
           this.targetPosition = null;
       }

       if (this.targetPosition == null || RandomSource.create().nextInt(30) == 0 || this.targetPosition.closerToCenterThan(entity.position(), 2.0D)) {
           this.targetPosition = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());
       }

       double d2 = (double)this.targetPosition.getX() + 0.5D - entity.getX();
       double d0 = (double)this.targetPosition.getY() + 0.1D - entity.getY();
       double d1 = (double)this.targetPosition.getZ() + 0.5D - entity.getZ();
       Vec3 vec3 = entity.getDeltaMovement();
       Vec3 vec31 = vec3.add((Math.signum(d2) * 0.5D - vec3.x) * (double)0.1F, (Math.signum(d0) * (double)0.7F - vec3.y) * (double)0.1F, (Math.signum(d1) * 0.5D - vec3.z) * (double)0.1F);
       // this.setDeltaMovement(vec31);
       float f = (float)(Mth.atan2(vec3.z, vec3.x) * (double)(180F / (float)Math.PI)) - 90.0F;
       float f1 = Mth.wrapDegrees(f - entity.getYRot());
       entity.zza = 0.5F;
       entity.setYRot(entity.getYRot() + f1);
   }





}

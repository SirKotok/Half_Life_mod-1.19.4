package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sirkotok.half_life_mod.util.HLTags;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class BiteWhileJumpingBehavior<E extends LivingEntity> extends ConditionlessAttack<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
    protected final RandomSource random = RandomSource.create();
    protected SoundEvent bitesound;
    protected float sitchance;
    public BiteWhileJumpingBehavior(int delayticks, SoundEvent bitesound, float sitchance) {
        super(delayticks);
        this.bitesound = bitesound;
        this.sitchance = sitchance;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    public double getSquareDistanceToHead(E entity)
    {
        return entity.distanceToSqr(this.target.getX(), this.target.getY() + this.target.getEyeHeight(),this.target.getZ());
    }
    public boolean canSitOnHead() {
        return this.target.getType().is(HLTags.EntityTypes.HEAD_CRAB_TARGET) && (this.target.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) && this.target.getPassengers().isEmpty();
    }
    public boolean canBite(E entity)
    {
        return this.getSquareDistanceToHead(entity) <= 0.36F && !entity.isPassenger();
    }


    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);
        return (this.target != null) && (!entity.isOnGround()) && (!entity.isPassenger());
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        if (!entity.isOnGround() && BrainUtils.getMemory(entity, MemoryModuleType.ATTACK_TARGET) != null) {
            if(canBite(entity))
            {
                entity.playSound(this.bitesound, 0.3f, entity.getVoicePitch());
                entity.doHurtTarget(this.target);
               if (canSitOnHead() && (this.random.nextFloat()<this.sitchance)) {
                    entity.startRiding(this.target, true);
                   if(this.target instanceof ServerPlayer player) {
                       player.connection.send(new ClientboundSetPassengersPacket(player)); } // automatically done in 1.20.1 so no need to do that
                }


                doDelayedAction(entity);
                this.delayedCallback.accept(entity);


            }}
    }


    @Override
    protected void doDelayedAction(E entity) {
        super.doDelayedAction(entity);
    }
}







/*
@Override
    protected void tick(E entity) {
        super.tick(entity);
        LivingEntity bitetarget;
        if (BrainUtils.getMemory(entity, ModMemoryModuleType.JUMPING.get()) != null) {
        if ((!entity.isOnGround()) && !(BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_LIVING_ENTITIES).isEmpty()) && (BrainUtils.getMemory(entity, ModMemoryModuleType.JUMPING.get()))) {

            List<LivingEntity> entities = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_LIVING_ENTITIES);
            ListIterator<LivingEntity>
                    iterator = entities.listIterator();

            while (iterator.hasNext()) {
                bitetarget = iterator.next();


                if (entity.distanceTo(bitetarget) < 0.8) {
                    entity.doHurtTarget(bitetarget);
                    BrainUtils.setMemory(entity, ModMemoryModuleType.JUMPING.get(), false);

                }
            }
            }
        }
    }

 */
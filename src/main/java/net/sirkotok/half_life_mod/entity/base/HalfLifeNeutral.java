package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Barney;

import javax.annotation.Nullable;
import java.util.UUID;

public class HalfLifeNeutral extends PathfinderMob implements NeutralMob {

    protected HalfLifeNeutral(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }



    public static final EntityDataAccessor<String> STRING_UUID_FOLLOW = SynchedEntityData.defineId(Barney.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> TIME_STAMP = SynchedEntityData.defineId(Barney.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_STAMP_2 = SynchedEntityData.defineId(Barney.class, EntityDataSerializers.INT);


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STRING_UUID_FOLLOW, "");
        this.entityData.define(TIME_STAMP, 0);
        this.entityData.define(TIME_STAMP_2, 0);
    }


    public String getFollowUUIDString() {
       return this.entityData.get(STRING_UUID_FOLLOW);
    }

    public SoundEvent getStartFollowingSound() {
        return SoundEvents.PIG_AMBIENT;
    }
    public SoundEvent getStopFollowingSound() {
        return SoundEvents.PIG_DEATH;
    }


    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        String string = pPlayer.getStringUUID();
        String second = this.getFollowUUIDString();
        if (second.equals(string) && (this.tickCount - this.entityData.get(TIME_STAMP)>40) && this.tickCount-this.entityData.get(TIME_STAMP_2)>40) {
            this.playSound(getStopFollowingSound(), this.getSoundVolume(), 1f);
            this.entityData.set(STRING_UUID_FOLLOW, "");
            this.entityData.set(TIME_STAMP_2, this.tickCount);
            return super.interactAt(pPlayer, pVec, pHand);
        }
        if (this.tickCount-this.entityData.get(TIME_STAMP_2)>40 && (this.tickCount - this.entityData.get(TIME_STAMP)>40)) {
        this.playSound(getStartFollowingSound(), this.getSoundVolume(), 1f);
        this.entityData.set(TIME_STAMP, this.tickCount);
        this.entityData.set(STRING_UUID_FOLLOW, string); }
        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }



    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @org.jetbrains.annotations.Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@org.jetbrains.annotations.Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {
    }
}

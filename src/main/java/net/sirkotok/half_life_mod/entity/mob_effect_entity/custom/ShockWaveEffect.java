package net.sirkotok.half_life_mod.entity.mob_effect_entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShockWaveEffect extends Entity implements GeoEntity {

    //TODO: remake this piece of .... crap. This doesnt work at all

    public ShockWaveEffect(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noCulling = true;
        this.noPhysics = true;
    }
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final EntityDataAccessor<Integer> SQUAD_SIZE = SynchedEntityData.defineId(ShockWaveEffect.class, EntityDataSerializers.INT);


    @Override
    protected void defineSynchedData() {
        this.entityData.define(SQUAD_SIZE, 1);
    }
    public int getsquaidsize() {
        return this.entityData.get(SQUAD_SIZE);
    }
    public void setSquadSize(int size) {
        this.entityData.set(SQUAD_SIZE, size);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setSquadSize(pCompound.getInt("Squad_size") + 1);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Squad_size", this.getsquaidsize() - 1 );
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::noattackpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::attackpredicate));
     //   controllerRegistrar.add(new AnimationController<>(this, "attack", state -> PlayState.STOP)
    //            .triggerableAnim("attack", RawAnimation.begin().then("animation.houndeye.actuallyattack", Animation.LoopType.PLAY_ONCE)));
    }

    private <T extends GeoAnimatable> PlayState attackpredicate(AnimationState<T> tAnimationState) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.actuallyattack", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
    }


    public <T extends GeoAnimatable> PlayState noattackpredicate(AnimationState<T> tAnimationState) {
         tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 900) {
            this.discard();
        }
    }


}

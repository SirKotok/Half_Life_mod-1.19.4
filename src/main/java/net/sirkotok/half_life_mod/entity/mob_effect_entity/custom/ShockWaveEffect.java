package net.sirkotok.half_life_mod.entity.mob_effect_entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;

import net.minecraft.world.entity.PathfinderMob;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Houndeye;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;



public class ShockWaveEffect extends PathfinderMob implements GeoEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public ShockWaveEffect(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.noCulling = true;
    }
    public static final EntityDataAccessor<Integer> SQUAD_SIZE = SynchedEntityData.defineId(Houndeye.class, EntityDataSerializers.INT);


    @Override
    public boolean isPickable() {
        return false;
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public boolean canBeSeenByAnyone() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(0, 0, 0);
        this.setPos(this.getX(), this.getY(), this.getZ());
        if (this.tickCount > 60) this.discard();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SQUAD_SIZE, 1);

    }
    public int getsquaidsize() {
        return this.entityData.get(SQUAD_SIZE);
    }
    public void setSquadSize(int size) {
        this.entityData.set(SQUAD_SIZE, size);
    }
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 0f)
                .add(Attributes.ATTACK_SPEED, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noattack", 0, this::noattackpredicate));
        controllerRegistrar.add(new AnimationController<>(this, "attack", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("animation.houndeye.actuallyattack", Animation.LoopType.PLAY_ONCE))); }


    private <T extends GeoAnimatable> PlayState noattackpredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.houndeye.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }










}

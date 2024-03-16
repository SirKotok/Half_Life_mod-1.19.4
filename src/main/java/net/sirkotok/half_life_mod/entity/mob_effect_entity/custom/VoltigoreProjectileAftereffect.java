package net.sirkotok.half_life_mod.entity.mob_effect_entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.base.HalfLifeAnimatedEffect;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoltigoreProjectileAftereffect extends HalfLifeAnimatedEffect implements GeoEntity {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public VoltigoreProjectileAftereffect(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.noCulling = true;
    }


    public static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(VoltigoreProjectileAftereffect.class, EntityDataSerializers.INT);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TIME, 40);

    }

    @Override
    public int remainingticks() {
        return this.entityData.get(TIME) ;
    }
    public void setTime(int time) {
        this.entityData.set(TIME, time);
    }

    @Override
    public void tick() {
        super.tick();
        if (random.nextFloat() < 0.3 || this.tickCount % 4 == 0) {
            Vec3 startPos = this.position();
            this.level.addParticle(HalfLifeParticles.VOLT_LIGHTNING.get(), startPos.x, startPos.y, startPos.z, 1, 0, 0);
        }
        this.setDeltaMovement(0, 0, 0);
        this.setPos(this.getX(), this.getY(), this.getZ());
        if (this.tickCount > remainingticks()) this.discard();
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1D)
                .add(Attributes.ATTACK_DAMAGE, 0f)
                .add(Attributes.ATTACK_SPEED, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "idle", 0, this::idlepredicate));}


    private <T extends GeoAnimatable> PlayState idlepredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.voltigore_projectile.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }




}
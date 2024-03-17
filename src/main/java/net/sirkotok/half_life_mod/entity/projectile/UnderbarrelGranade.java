package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class UnderbarrelGranade extends ThrowableItemProjectile implements GeoEntity {
    public UnderbarrelGranade(EntityType<UnderbarrelGranade> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(Items.SNOWBALL.getDefaultInstance());
    }
    public UnderbarrelGranade(EntityType<UnderbarrelGranade> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public UnderbarrelGranade(EntityType<UnderbarrelGranade> pEntityType, LivingEntity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getY() + pShooter.getBbHeight(), pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
    }

    public UnderbarrelGranade(Level pLevel, LivingEntity pShooter) {
        this(HalfLifeEntities.UNDER_NADE.get(), pShooter, pLevel);
    }




    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(CommonSounds.getHL1Explosion(), 0.6f, 1f);
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3f, Level.ExplosionInteraction.MOB);
            this.discard();
        }

    }




    @Override
    protected float getGravity() {
        return 0.033F;
    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return false;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "allways", 0, this::spin));
    }

    private <T extends GeoAnimatable> PlayState spin(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.nade.spin", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

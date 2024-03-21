package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.SitThenBlowUpEffect;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AcidThrownBM extends ThrowableItemProjectile implements GeoEntity {
    public AcidThrownBM(EntityType<AcidThrownBM> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(Items.SNOWBALL.getDefaultInstance());
    }
    public AcidThrownBM(EntityType<AcidThrownBM> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public AcidThrownBM(EntityType<AcidThrownBM> pEntityType, LivingEntity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getY() + pShooter.getBbHeight(), pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
    }

    public AcidThrownBM(Level pLevel, LivingEntity pShooter) {
        this(HalfLifeEntities.ACID_THROWNBM.get(), pShooter, pLevel);
    }



    protected SoundEvent getAcidSound(){
            switch (this.random.nextInt(1,3)) {
                case 1:  return HalfLifeSounds.BULLSQUID_ACID_1.get();
                case 2:  return HalfLifeSounds.BULLSQUID_ACID_2.get();
            }
            return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getHitSound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return HalfLifeSounds.BULLSQUID_SPIT_1.get();
            case 2:  return HalfLifeSounds.BULLSQUID_SPIT_2.get();
            case 3:  return HalfLifeSounds.BULLSQUID_SPIT_3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_DIE_1.get();
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            if (entity.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB)) return;
            playSound(this.getAcidSound(), 0.3f, 1f);
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity bullsquid = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, bullsquid), 20f);
            }
        }
    }



    private void spawnEffect(ServerLevel serverlevel){
        SitThenBlowUpEffect wave = HalfLifeEntities.SITBLOWUP.get().create(serverlevel);
        if (wave != null) {
            wave.moveTo(this.getX(), this.getY(), this.getZ());
            wave.setYBodyRot(this.getYRot());
            wave.setOwn(this.getOwner() instanceof  LivingEntity living ? living : null);
            ForgeEventFactory.onFinalizeSpawn((Mob) wave, (ServerLevelAccessor) serverlevel, serverlevel.getCurrentDifficultyAt(wave.blockPosition()), MobSpawnType.TRIGGERED, null, null);
            serverlevel.addFreshEntity(wave);
        }
    }


    private void spawnLingeringCloud() {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        areaeffectcloud.setRadius(4F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setDuration(66);
        areaeffectcloud.setRadiusPerTick(areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.POISON));
        this.level.addFreshEntity(areaeffectcloud);
    }

    /*
    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        int direction = blockhit.getDirection().get3DDataValue();
        if (this.level.isClientSide){
            switch(direction){
                case 0:  getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 1: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 2: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                    break;
                case 3: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                    break;
                case 4: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 5: getLevel().addAlwaysVisibleParticle(ModParticles.SPIT_HIT.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
            }}
        this.discard();
    } */

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(this.getHitSound(), 0.3f, 1f);
            this.end();
        }

    }


    protected void end(){
        this.spawnEffect((ServerLevel) this.level);
        this.spawnLingeringCloud();
        this.discard();
    }


    @Override
    protected float getGravity() {
        return 0.03F;
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
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}

package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.mob.mob_effect_entity.custom.VoltigoreProjectileAftereffect;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Voltigore;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.misc.damagesource.HalfLifeDamageTypes;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class VoltigoreShock extends FireballNoTrail implements GeoEntity {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public VoltigoreShock(EntityType<VoltigoreShock> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(Items.SLIME_BALL.getDefaultInstance());
    }
    public VoltigoreShock(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.VOLTIGORE_SHOCK.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public VoltigoreShock(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.VOLTIGORE_SHOCK.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }




    @Override
    protected boolean shouldBurn() {
        return false;
    }


    protected SoundEvent getHitSound() {
        return HalfLifeSounds.SHOCK_IMPACT.get();
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            ServerLevel serverlevel = (ServerLevel) level;
            Entity entity = pResult.getEntity();
            Entity entity1 = this.getOwner();
            BlockPos pBlockPos = entity.blockPosition();
            if (entity1 instanceof LivingEntity) {
                LivingEntity bullsquid = (LivingEntity) entity1;

                entity.hurt(HalfLifeDamageTypes.electricdamage(level.registryAccess(), this, bullsquid), 15f);
                HLperUtil.DisableShieldFor(entity, 1f, 200, serverlevel);

                int rad = 3;
                List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                        new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                                pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> !(obj instanceof Voltigore) && obj instanceof LivingEntity);

                    for (LivingEntity entity2 : targets) {
                        if (entity2 != entity) {
                            HLperUtil.DisableShieldFor(entity2, 0.1f, 20, serverlevel);
                            entity2.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity1 ), 2f);
                        }
                    }

                VoltigoreProjectileAftereffect wave = HalfLifeEntities.VOLTIGOREPROJECTEFFECT.get().create(serverlevel);
                if (wave != null) {
                    wave.moveTo(this.getX(), this.getY(), this.getZ());
                    wave.setYBodyRot(this.getYRot());
                    wave.setTime(35);
                    ForgeEventFactory.onFinalizeSpawn((Mob) wave, (ServerLevelAccessor) serverlevel, serverlevel.getCurrentDifficultyAt(wave.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                    serverlevel.addFreshEntity(wave);
                }

            }
        }
    }


    @Override
    public void tick() {
        super.tick();
        if (random.nextFloat() < 0.3 || this.tickCount % 4 == 0) {
        Vec3 startPos = this.position();
        this.level.addParticle(HalfLifeParticles.VOLT_LIGHTNING.get(), startPos.x, startPos.y, startPos.z, 1, 0, 0);
        }

    }

    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        int direction = blockhit.getDirection().get3DDataValue();
        if (this.level.isClientSide){
            switch(direction){
                case 0:  getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 1: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 2: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                    break;
                case 3: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                    break;
                case 4: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 5: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
            }}
        BlockPos pBlockPos = blockhit.getBlockPos();
        Entity entity1 = this.getOwner();
        if (entity1 != null) {
        if (!this.level.isClientSide) {



                ServerLevel serverlevel = (ServerLevel) level;
                VoltigoreProjectileAftereffect wave = HalfLifeEntities.VOLTIGOREPROJECTEFFECT.get().create(serverlevel);
                if (wave != null) {
                    wave.moveTo(this.getX(), this.getY(), this.getZ());
                    wave.setYBodyRot(this.getYRot());
                    ForgeEventFactory.onFinalizeSpawn((Mob) wave, (ServerLevelAccessor) serverlevel, serverlevel.getCurrentDifficultyAt(wave.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                    serverlevel.addFreshEntity(wave);
                }





            int rad = 5;
            List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) serverlevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> !(obj instanceof Voltigore) && obj instanceof LivingEntity);
        if (entity1 instanceof LivingEntity) {
            for (LivingEntity entity : targets) {
            HLperUtil.DisableShieldFor(entity, 0.1f, 20, serverlevel);
                entity.hurt(HalfLifeDamageTypes.electricdamage(level.registryAccess(), this, (LivingEntity) entity1), 5); }
        }}
        }

        this.discard();
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            playSound(this.getHitSound(), 0.3f, 1f);
            this.discard();
        }

    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }





    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "main_loop", 0, this::predicate));

    }



    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.voltigore_projectile.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }





    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}

package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.GonarchBM;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;

public class AcidBall extends FireballNoTrail {
    public AcidBall(EntityType<AcidBall> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(Items.SLIME_BALL.getDefaultInstance());
    }
    public AcidBall(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.ACID_BALL.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        if (pShooter instanceof GonarchBM) this.setItem(Items.SNOWBALL.getDefaultInstance());
    }

    public AcidBall(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(HalfLifeEntities.ACID_BALL.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    @Override
    protected boolean shouldBurn() {
        return false;
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
            Entity entity1 = this.getOwner();
            if (entity1 instanceof GonarchBM && entity.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB)) return;
            playSound(this.getAcidSound(), 0.3f, 1f);
            if (entity1 instanceof LivingEntity) {
                LivingEntity bullsquid = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, bullsquid), 5f);
            }
        }
    }

    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        int direction = blockhit.getDirection().get3DDataValue();
        if (!(this.getOwner() instanceof GonarchBM)) {
        if (this.level.isClientSide){
            switch(direction){
                case 0:  getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 1: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 2: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                    break;
                case 3: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                    break;
                case 4: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 5: getLevel().addAlwaysVisibleParticle(HalfLifeParticles.SPIT_HIT.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
            }}}
        this.discard();
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (pResult instanceof EntityHitResult result) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof GonarchBM && entity.getType().is(HLTags.EntityTypes.FACTION_HEADCRAB)) return;
        }


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






}

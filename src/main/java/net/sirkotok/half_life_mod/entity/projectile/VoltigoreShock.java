package net.sirkotok.half_life_mod.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.base.FireballNoTrail;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Vortigore;
import net.sirkotok.half_life_mod.particle.ModParticles;
import net.sirkotok.half_life_mod.sound.ModSounds;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.List;

public class VoltigoreShock extends FireballNoTrail {

    public VoltigoreShock(EntityType<VoltigoreShock> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(Items.SLIME_BALL.getDefaultInstance());
    }
    public VoltigoreShock(Level pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(ModEntities.VOLTIGORE_SHOCK.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public VoltigoreShock(Level pLevel, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ) {
        super(ModEntities.VOLTIGORE_SHOCK.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }




    @Override
    protected boolean shouldBurn() {
        return false;
    }

    protected SoundEvent getAcidSound(){
        switch (this.random.nextInt(1,3)) {
            case 1:  return ModSounds.BULLSQUID_ACID_1.get();
            case 2:  return ModSounds.BULLSQUID_ACID_2.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    protected SoundEvent getHitSound(){
        switch (this.random.nextInt(1,4)) {
            case 1:  return ModSounds.BULLSQUID_SPIT_1.get();
            case 2:  return ModSounds.BULLSQUID_SPIT_2.get();
            case 3:  return ModSounds.BULLSQUID_SPIT_3.get();
        }
        return ModSounds.HEADCRAB_1_DIE_1.get();
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level.isClientSide) {
            Entity entity = pResult.getEntity();
            playSound(this.getAcidSound(), 0.3f, 1f);
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                LivingEntity bullsquid = (LivingEntity) entity1;
                entity.hurt(this.damageSources().mobProjectile(this, bullsquid), 15f);
            }
        }
    }

    protected void onHitBlock(BlockHitResult blockhit) {
        super.onHitBlock(blockhit);
        int direction = blockhit.getDirection().get3DDataValue();
        if (this.level.isClientSide){
            switch(direction){
                case 0:  getLevel().addAlwaysVisibleParticle(ModParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()-0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 1: getLevel().addAlwaysVisibleParticle(ModParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y()+0.01f, blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 2: getLevel().addAlwaysVisibleParticle(ModParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()-0.01f, direction, 0, 0);
                    break;
                case 3: getLevel().addAlwaysVisibleParticle(ModParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x(), blockhit.getLocation().y(), blockhit.getLocation().z()+0.01f, direction, 0, 0);
                    break;
                case 4: getLevel().addAlwaysVisibleParticle(ModParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x()-0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
                case 5: getLevel().addAlwaysVisibleParticle(ModParticles.SHOCK_IMPACT.get(), blockhit.getLocation().x()+0.01f, blockhit.getLocation().y(), blockhit.getLocation().z(), direction, 0, 0);
                    break;
            }}
        BlockPos pBlockPos = blockhit.getBlockPos();
        Entity entity1 = this.getOwner();
        if (entity1 != null) {
        if (!this.level.isClientSide) {
            int rad = 5;
            List<LivingEntity> targets = EntityRetrievalUtil.getEntities((Level) level,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> !(obj instanceof Vortigore) && obj instanceof LivingEntity);
        if (entity1 instanceof LivingEntity) {
            for (LivingEntity entity : targets) {
            entity.hurt(this.damageSources().mobProjectile(this, (LivingEntity) entity1 ), 5f); }
        }}}

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






}

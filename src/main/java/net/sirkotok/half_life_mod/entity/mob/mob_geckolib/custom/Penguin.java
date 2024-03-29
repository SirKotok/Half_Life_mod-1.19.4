package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;

public class Penguin extends Snark {
    public Penguin(EntityType type, Level level) {
        super(type, level);
    }


    @Override
    public void die(DamageSource pDamageSource) {

        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3f, Level.ExplosionInteraction.MOB);
            this.dead = true;
            this.discard();
        }
        super.die(pDamageSource);
    }

    @Override
    public void explode() {
        if (!this.level.isClientSide) {

            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3f, Level.ExplosionInteraction.MOB);
        }
        super.explode();
    }

    @Override
    public SoundEvent getExplosionSound() {
        return CommonSounds.getHL1Explosion();
    }
}
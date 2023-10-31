package net.sirkotok.half_life_mod.entity.mob_geckolib.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sirkotok.half_life_mod.sound.ModSounds;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;

public class Hoteye extends Houndeye{
    public Hoteye(EntityType type, Level level) {
        super(type, level);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1D)
                .add(Attributes.ATTACK_DAMAGE, 4f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.28f).build();
    }


    public void explode() {
        this.playSound(ModSounds.SNARK_BLAST.get(), 0.3f, 1); //TODO: change to correct sound
        if (!this.level.isClientSide) {
        ServerLevel pLevel = (ServerLevel) this.getLevel();
        BlockPos pBlockPos = this.blockPosition();
        pLevel.explode(this, this.getX(), this.getY(), this.getZ(), 2f, Level.ExplosionInteraction.MOB);
        this.dead = true;
        this.discard();

        int radius = 100;
        List<Houndeye> houndeyes = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                        pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof Houndeye);

        for (Houndeye dog : houndeyes) {
            dog.removeDogfromDogs(this);
        }
        }
    }

    @Override
    public void die(DamageSource pDamageSource) {
        this.explode();
        super.die(pDamageSource);
    }

    @Override
    public void performattack() {
        this.explode();
    }
}

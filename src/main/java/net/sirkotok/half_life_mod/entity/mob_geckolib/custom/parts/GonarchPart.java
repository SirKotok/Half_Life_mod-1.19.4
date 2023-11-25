package net.sirkotok.half_life_mod.entity.mob_geckolib.custom.parts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Gonarch;

public class GonarchPart extends net.minecraftforge.entity.PartEntity<Gonarch> {
    public final Gonarch parentMob;
    public final String name;
    private final EntityDimensions size;
    private final boolean issack;

    public GonarchPart(Gonarch pParentMob, String pName, float pWidth, float pHeight, boolean issack) {
        super(pParentMob);
        this.size = EntityDimensions.scalable(pWidth, pHeight);
        this.refreshDimensions();
        this.parentMob = pParentMob;
        this.name = pName;
        this.issack = issack;
    }


    public boolean canBeCollidedWith() {
        return false;
    }




    protected void defineSynchedData() {
    }




    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return true;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.issack) return false;
        return this.parentMob.hurt(pSource, pAmount);
    }





    /**
     * Returns {@code true} if Entity argument is equal to this Entity
     */
    public boolean is(Entity pEntity) {
        return this == pEntity || this.parentMob == pEntity;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    public EntityDimensions getDimensions(Pose pPose) {
        return this.size;
    }

    public boolean shouldBeSaved() {
        return false;
    }
}

package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.parts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.GonarchBM;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.sirkotok.half_life_mod.misc.util.HLTags;

public class GonarchBMPart extends net.minecraftforge.entity.PartEntity<GonarchBM> {
    public final GonarchBM parentMob;
    public final String name;
    private final EntityDimensions size;
    private final boolean issack;
    private final boolean ispick;
    private final boolean solid;
    public GonarchBMPart(GonarchBM pParentMob, String pName, float pWidth, float pHeight, boolean issack, boolean ispick, boolean solid) {
        super(pParentMob);
        this.size = EntityDimensions.scalable(pWidth, pHeight);
        this.refreshDimensions();
        this.parentMob = pParentMob;
        this.name = pName;
        this.issack = issack;
        this.solid = solid;
        this.ispick = ispick;
    }


    public boolean canBeCollidedWith() {
        return solid;
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
        return ispick;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.issack) {
            if (pSource.is(HLTags.DamageTypes.HEADCRAB_ARMOR_PROTECTS_FROM)) this.playSound(CommonSounds.getRicSound(), 0.8f,  this.parentMob.getVoicePitch());
            return false;
        }
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

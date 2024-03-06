package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class HomingFballNoTrail extends AbstractHomingProjectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(HomingFballNoTrail.class, EntityDataSerializers.ITEM_STACK);

    public HomingFballNoTrail(EntityType<? extends HomingFballNoTrail> pEntityType, Level pLevel, LivingEntity pTarget) {
        super(pEntityType, pLevel, pTarget);
    }

    public HomingFballNoTrail(EntityType<? extends HomingFballNoTrail> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, LivingEntity pTarget) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel, pTarget);
    }

    public HomingFballNoTrail(EntityType<? extends HomingFballNoTrail> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, LivingEntity pTarget) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel, pTarget);
    }

    public HomingFballNoTrail(EntityType<? extends HomingFballNoTrail> pEntityType, LivingEntity pShooter, Level pLevel, LivingEntity pTarget) {
        super(pEntityType, pShooter, pLevel, pTarget);
    }

    public ItemStack GetAllwaysItem() {
        return null;
    }


    public void setItem(ItemStack pStack) {

        if (GetAllwaysItem() != null) {
            this.getEntityData().set(DATA_ITEM_STACK, Util.make(GetAllwaysItem().copy(), (p_37015_) -> {
                p_37015_.setCount(1);
            }));
        } else {
            if (!pStack.is(Items.FIRE_CHARGE) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, Util.make(pStack.copy(), (p_37015_) -> {
                p_37015_.setCount(1);
            }));
        }}

    }

    protected ItemStack getItemRaw() {
        if (GetAllwaysItem() != null) return GetAllwaysItem();
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setItem(itemstack);
    }
}


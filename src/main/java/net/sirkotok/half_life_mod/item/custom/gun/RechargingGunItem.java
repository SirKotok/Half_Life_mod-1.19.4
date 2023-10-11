package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class RechargingGunItem extends GunItem {

    private static final String TICK = "Tick";




    public static int GetMaxAmmo() {
        return 8;
    }




    public static void SetTick(ItemStack gunstack, int tick) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("Tick", tick);

    }
    public static int GetTick(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("Tick");
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(getAmmooutofMax(pStack));
    }
    @Override
    public MutableComponent getAmmooutofMax(ItemStack pStack) {
        return Component.literal("Ammo: "+GetAmmo(pStack)+"\\"+GetMaxAmmo());
    }



    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (GetCooldow(pStack)>0) {
            int i = GetCooldow(pStack);
            SetCooldow(pStack, i-1);
            if (GetTick(pStack) % 20 == 0) {SetTick(pStack, GetTick(pStack)+1);}
        } else {
        SetTick(pStack, GetTick(pStack)+1);
        }

        if (GetAmmo(pStack) < GetMaxAmmo() && GetTick(pStack) % 20 == 0) {
            SetAmmo(pStack, GetAmmo(pStack)+1);
        }

    }


    public RechargingGunItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }


    @Override
    public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        return InteractionResultHolder.fail(itemstack);
    }




}




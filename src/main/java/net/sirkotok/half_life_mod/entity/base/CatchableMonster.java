package net.sirkotok.half_life_mod.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Chumtoad;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;

public class CatchableMonster extends HalfLifeMonster {


    protected CatchableMonster(EntityType type, Level level) {
        super(type, level);
    }

    // Override for correct item
    public Item getweopon(){
        return Items.BEDROCK;
    }
    public int getemount(){
        return 1;
    }


    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        if (this.tickCount < 40) return InteractionResult.FAIL;
        if (pPlayer.getItemInHand(pHand).isEmpty()) {
                pPlayer.setItemInHand(pHand, getweopon().getDefaultInstance());
                if (this.getemount() != 1) pPlayer.getItemInHand(pHand).grow(getemount()-1);
                if (this.hasCustomName()) pPlayer.getItemInHand(pHand).setHoverName(this.getCustomName());
                if (this instanceof Chumtoad toad) {
                    CompoundTag tag = toad.serializeNBT();
                    ChumtoadItem.addentitynbtdata(pPlayer.getItemInHand(pHand), tag);
                }
                this.discard();
                return InteractionResult.SUCCESS;
            }
        if (this instanceof Chumtoad) {
            return InteractionResult.FAIL;
        }
            ItemStack stack = pPlayer.getItemInHand(pHand);
            if (this.hasCustomName() && (!stack.getHoverName().equals(this.getCustomName()) || !stack.hasCustomHoverName())) return InteractionResult.PASS;
            if (!this.hasCustomName() && stack.hasCustomHoverName()) return InteractionResult.PASS;

            if  (stack.is(getweopon()) && stack.getCount()<stack.getMaxStackSize()) {
                if  (stack.getCount()+getemount() <= stack.getMaxStackSize()) {
                stack.grow(getemount());
                this.discard();
                return InteractionResult.SUCCESS;}

                stack.grow(stack.getMaxStackSize()-stack.getCount());
                this.discard();
                return InteractionResult.SUCCESS;

    }
        return InteractionResult.PASS;
    }
}

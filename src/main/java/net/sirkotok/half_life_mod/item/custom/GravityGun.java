package net.sirkotok.half_life_mod.item.custom;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.item.client.renderer.GravityGunRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.Pistol_1_ItemRenderer;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.UUID;
import java.util.function.Consumer;

public class GravityGun extends GunItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public ItemStack getammoitem(){
        return Items.IRON_INGOT.getDefaultInstance();
    }
    public GravityGun(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }


    public static void SetHolding(ItemStack gunstack, boolean holding, Entity block) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putBoolean("IsThisHolding", holding);
        compoundtag.putString("HeldBlock", block.getUUID().toString());
    }
    public static boolean IsHolding(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getBoolean("IsThisHolding");
        return false;
    }
    /*
    public static Entity GetHolding(Player player, ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        Level level1 = player.getLevel();
        if (compoundtag != null) {
            String id = compoundtag.getString("HeldBlock");

        }
        return "";
    } */


   @Override
    public int getRightClickCooldown(){
        return 20;
    } //4
    @Override
    public int getLeftClickCooldown(){
        return 20;
    } //6
    @Override
    public int getReloadCooldown(){
        return 20;
    }

    @Override
    public float getgundamage() {
        return 3.5f;
    }

    @Override public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild) {
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                return InteractionResultHolder.fail(itemstack);
            }
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PISTOL_SHOOT.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootright(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild) {
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                return InteractionResultHolder.fail(itemstack);
            }
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PISTOL_SHOOT.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootleft(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "swim", 0, this::idle));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.gravitygun.shootpassive", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload", RawAnimation.begin().then("animation.gravitygun.shootactive", Animation.LoopType.PLAY_ONCE)));
    }

 // animation.gravitygun.nothing
    // animation.gravitygun.normal
    // animation.gravitygun.hold
    // animation.gravitygun.shootactive
    // animation.gravitygun.shootpassive

    private <T extends GeoAnimatable> PlayState idle(AnimationState<T> tAnimationState) {

     //   if () {
  //          tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.archer.onland", Animation.LoopType.LOOP));
 //           return PlayState.CONTINUE;
  //      }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.gravitygun.hold", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;


    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }



    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GravityGunRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new GravityGunRenderer();
                }
                return this.renderer;
            }
        });
    }

}

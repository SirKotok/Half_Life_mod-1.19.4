package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.entity.projectile.UnderbarrelGranade;
import net.sirkotok.half_life_mod.item.client.renderer.SAW_ItemRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.SMG_1_ItemRenderer;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunAltFireItem;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;
import net.sirkotok.half_life_mod.misc.config.HalfLifeCommonConfigs;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class SAW_Item extends GunItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public ItemStack getammoitem(){
        return Items.IRON_INGOT.getDefaultInstance();
    }
    public SAW_Item(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static String getAmmoString(ItemStack stack){
       int i = GetAmmo(stack);
       if (i > 9 || GetReloadTimer(stack) > 0) return "";
       if (i == 0) return "_1";
       return "_"+i;
    }


    @Override
    public int getLeftClickCooldown(){
        return 2;
    }
    @Override
    public int getReloadCooldown(){
        return 65;
    }

    @Override
    public float getgundamage() {
        return 4.5f;
    }
    @Override public int GetMaxAmmo() {
        return 50;
    }

    @Override
    public InteractionResultHolder<ItemStack> Reload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
        if (slotwithitem == -1 && !pPlayer.getAbilities().instabuild) return InteractionResultHolder.fail(itemstack);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) != GetMaxAmmo() || pPlayer.getAbilities().instabuild) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
                actuallyreload(pLevel, pPlayer, pHand);
        }}
        return InteractionResultHolder.success(itemstack);
    }


    public void actuallyreload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        SetCooldow(pPlayer, getReloadCooldown()+1);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.SAW_RELOAD.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "reload");
        SetReloadTimer(itemstack, getReloadCooldown());
    }



    public SoundEvent getShootingSound(){
        switch (RandomSource.create().nextInt(1,4)) {
            case 1:  return HalfLifeSounds.SAW_FIRE1.get();
            case 2:  return HalfLifeSounds.SAW_FIRE2.get();
            case 3:  return HalfLifeSounds.SAW_FIRE3.get();
        }
        return HalfLifeSounds.HEADCRAB_1_ALERT_1.get();
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild) {
                int slotwithitem = findSlotMatchingItem(pPlayer, getammoitem());
                if (slotwithitem == -1  || !HalfLifeCommonConfigs.AUTO_RELOAD.get()) {
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DRYFIRE1.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                    SetCooldow(pPlayer, 10);
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (GetAmmo(itemstack) != GetMaxAmmo()) {
                        actuallyreload(pLevel, pPlayer, pHand);
                    }
                    return InteractionResultHolder.success(itemstack);
                }
            }
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), getShootingSound(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootleft(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }


    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getLeftClickCooldown());
            Bullet snowball = new Bullet(pLevel, pPlayer);
            snowball.setdamage(this.getgundamage());
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 4.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noshoot", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "noreload", 0, this::predicate2));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.m249.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("altfire", RawAnimation.begin().then("animation.smg.altfire", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload", RawAnimation.begin().then("animation.m249.reload", Animation.LoopType.PLAY_ONCE)));
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.m249.noattack", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState predicate2(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.m249.noreload", Animation.LoopType.LOOP));
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
            private SAW_ItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new SAW_ItemRenderer();
                }
                return this.renderer;
            }
        });
    }



}

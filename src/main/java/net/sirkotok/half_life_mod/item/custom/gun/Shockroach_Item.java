package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.entity.projectile.AcidBall;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.entity.projectile.ShockProjectile;
import net.sirkotok.half_life_mod.item.client.renderer.Pistol_1_ItemRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.Shockroach_ItemRenderer;
import net.sirkotok.half_life_mod.sound.ModSounds;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class Shockroach_Item extends RechargingGunItem implements GeoItem {


    private static final String ELECTRIC_TIMER = "Electric";

    public static void SetElectric(ItemStack gunstack, int time) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("Electric", time);
    }
    public static int GetElectric(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("Electric");
        return -1;
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (GetElectric(pStack)>0) {
            int i = GetElectric(pStack);
            SetElectric(pStack, i - 1);
        }
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public ItemStack getammoitem(){
        return Items.BEDROCK.getDefaultInstance();
    }
    public Shockroach_Item(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

   @Override
    public int getRightClickCooldown(){
        return 10;
    }
    @Override
    public int getLeftClickCooldown(){
        return 5;
    }






    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
        if (!pLevel.isClientSide) {
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.SHOCKROACH_DRAW.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "idle");
            SetCooldow(itemstack, getLeftClickCooldown());
            return InteractionResultHolder.fail(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetAmmo(itemstack) == 0 && !pPlayer.getAbilities().instabuild)
                return InteractionResultHolder.fail(itemstack);
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.SHOCK_FIRE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            shootleft(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.shockroach.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("idle", RawAnimation.begin().then("animation.shockroach.idleonce", Animation.LoopType.PLAY_ONCE)));
    }



    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.shockroach.hold", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }


    @Override
    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            SetElectric(itemstack, getLeftClickCooldown()-2);
            SetCooldow(itemstack, getLeftClickCooldown());
            ShockProjectile snowball = new ShockProjectile(pLevel, pPlayer);
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2F, 0.5F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
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
            private Shockroach_ItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new Shockroach_ItemRenderer();
                }
                return this.renderer;
            }
        });
    }



}

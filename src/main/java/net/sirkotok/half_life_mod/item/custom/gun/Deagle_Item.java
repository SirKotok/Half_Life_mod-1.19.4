package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.Minecraft;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.entity.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.entity.projectile.Bullet;
import net.sirkotok.half_life_mod.item.client.renderer.Deagle_ItemRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.Pistol_1_ItemRenderer;
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

public class Deagle_Item extends GunItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public ItemStack getammoitem(){
        return Items.IRON_INGOT.getDefaultInstance();
    }
    public Deagle_Item(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }


    public static void SwitchLazer(ItemStack gunstack) {
        if (gunstack.getItem() instanceof GunItem) {
            CompoundTag compoundtag = gunstack.getOrCreateTag();
            compoundtag.putBoolean("Lazer", !GetLazer(gunstack)); }
    }
    public static boolean GetLazer(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getBoolean("Lazer");
        return false;
    }


    public void actuallyreload(Level pLevel, Player pPlayer, InteractionHand pHand) {
        SetCooldow(pPlayer, getReloadCooldown()+1);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DESERT_EAGLE_RELOAD.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "reload");
        SetReloadTimer(itemstack, getReloadCooldown());
    }

    @Override
    public int GetMaxAmmo() {
        return 7;
    }

    @Override
    public int getRightClickCooldown(){
        return 10;
    } //4
    @Override
    public int getLeftClickCooldown(){
        return 15;
    } //6
    @Override
    public int getReloadCooldown(){
        return 18;
    }

    @Override
    public float getgundamage() {
        return 18f;
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

    /*
    public void shootright(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getRightClickCooldown());
            SporeShot snowball = new SporeShot(pLevel, pPlayer);
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.8F, 6.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    } */


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pIsSelected && GetLazer(pStack)) {

        Minecraft craft = Minecraft.getInstance();
        HitResult result = craft.hitResult;
        if (result != null) {
            Vec3 vec3 = result.getLocation();
            if (pLevel instanceof ServerLevel level) {
                level.sendParticles(HalfLifeParticles.STAT_GLOW.get(), vec3.x(),  vec3.y(),  vec3.z(), 0,0, 0, 0, 0);
            }
          //  pLevel.addParticle(HalfLifeParticles.STAT_GLOW.get(), vec3.x(),  vec3.y(),  vec3.z(), 0, 0, 0);
        }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (!pLevel.isClientSide) {
            SetCooldow(pPlayer, 5);
            SwitchLazer(itemstack);
            if (GetLazer(itemstack)) pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DESERT_EAGLE_SIGHT.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            else pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DESERT_EAGLE_SIGHT2.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        }

        return InteractionResultHolder.success(itemstack);
    }


    public void shootright(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getRightClickCooldown());
            Bullet snowball = new Bullet(pLevel, pPlayer);
            snowball.setdamage(this.getgundamage());
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 8.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    }

    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(pPlayer, getLeftClickCooldown());
            Bullet snowball = new Bullet(pLevel, pPlayer);
            snowball.setdamage(this.getgundamage());
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 4F, 1.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
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
            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.DESERT_EAGLE_FIRE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            if (GetLazer(itemstack)) shootleft(pLevel, pPlayer, pHand);
            else shootright(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "noshoot", 0, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.pistol.shoot", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("reload", RawAnimation.begin().then("animation.pistol.reload", Animation.LoopType.PLAY_ONCE)));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.pistol.noattack", Animation.LoopType.LOOP));
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
            private Deagle_ItemRenderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new Deagle_ItemRenderer();
                }
                return this.renderer;
            }
        });
    }

 /*
    @Override
    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            pPlayer.level.gameEvent(pPlayer, GameEvent.PROJECTILE_SHOOT, pPlayer.blockPosition());
            SetCooldow(itemstack, getLeftClickCooldown());
            SporeShot snowball = new SporeShot(pLevel, pPlayer);
            snowball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1F, 1.0F);
            pLevel.addFreshEntity(snowball);
        }
        award(pPlayer);
        if (!pPlayer.getAbilities().instabuild) SetAmmo(itemstack, GetAmmo(itemstack)-1);
    } */

}

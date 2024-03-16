package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.entity.fallingblock.GravityGunFallingBlockEntity;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Antlion;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.AntlionWorker;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Manhack;
import net.sirkotok.half_life_mod.item.client.renderer.GravityGunRenderer;
import net.sirkotok.half_life_mod.item.client.renderer.Pistol_1_ItemRenderer;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;
import net.sirkotok.half_life_mod.particle.HalfLifeParticles;
import net.sirkotok.half_life_mod.sound.HalfLifeSounds;
import net.sirkotok.half_life_mod.util.HLTags;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.List;
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

    public static int  GetHoldingCheck(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getInt("HoldingCheck");
        return 0;
    }

    public static void SetHoldingCheck(ItemStack gunstack, int check) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putInt("HoldingCheck", check);
    }

    public static void SetHolding(ItemStack gunstack, boolean holding) {
        CompoundTag compoundtag = gunstack.getOrCreateTag();
        compoundtag.putBoolean("IsThisHolding", holding);
    }
    public static boolean IsHolding(ItemStack gunstack) {
        CompoundTag compoundtag = gunstack.getTag();
        if (compoundtag != null) return compoundtag.getBoolean("IsThisHolding");
        return false;
    }



    public boolean isentityhittablewithggun(Entity pEntity){
        if (pEntity instanceof LivingEntity) {
            if (pEntity.getBbHeight()*pEntity.getBbHeight() < 0.8f) {
                return true;
            }
        }
        return false;
    }
    private boolean canSeeBlock(Vec3 from, Vec3 to, Level pLevel, float distance) {
        BlockHitResult result = pLevel.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));
        return Vec3.atCenterOf(result.getBlockPos()).distanceTo(to) < distance-1.5f;
    }

    public boolean entityHit(Player pPlayer) {
        Vec3 vec3 = pPlayer.getEyePosition(1.0f);
        Vec3 vec31 = pPlayer.getViewVector(1.0F);

        double d0 = 16;
        double d1 = 16;
        Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
        float f = 1.0F;
        AABB aabb = pPlayer.getBoundingBox().inflate(3d).expandTowards(vec31.scale(d0)).inflate(16.0D, 16.0D, 16.0D);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(pPlayer, vec3, vec32, aabb, (entity) -> {
            return (!entity.isPassenger()) && (isentityhittablewithggun(entity) ||entity instanceof Antlion || entity instanceof Manhack || entity instanceof AntlionWorker || entity.getType().is(HLTags.EntityTypes.HEADCRAB)) && entity.isPickable();
        }, d1);
        if (entityhitresult != null) {
            Entity entity1 = entityhitresult.getEntity();
            if (entity1 instanceof Antlion ant) {
                ant.flipover(0);
            }
            if (entity1 instanceof AntlionWorker ant) {
                ant.flipover(0);
            }
            Vec3 from = pPlayer.getEyePosition().subtract(0, 0.3, 0);
            Vec3 vec33 = new Vec3(entity1.getX(), entity1.getEyeY(), entity1.getZ());
            Vec3 to = vec33.subtract(from);
         //   pPlayer.level.addParticle(HalfLifeParticles.GRAV_GUN_ORANGE_LIGHTNING.get(), from.x, from.y, from.z, to.x, to.y, to.z);
            ((ServerLevel) pPlayer.level).sendParticles(HalfLifeParticles.GRAV_GUN_ORANGE_LIGHTNING.get(), from.x, from.y, from.z, 0, to.x, to.y, to.z, 1d);
            this.shootEntityFromRotation(entity1,pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2F, 1.0F);
            pPlayer.level.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), getLaunchSound(), SoundSource.NEUTRAL, 0.5F, 1F);
            entity1.hurt(pPlayer.damageSources().playerAttack(pPlayer), 4);
            return true;
        } else return false;
    }

  /*  public boolean entityCatch(Player pPlayer) {
        Vec3 vec3 = pPlayer.getEyePosition(1.0f);
        Vec3 vec31 = pPlayer.getViewVector(1.0F);
        double d0 = 16;
        double d1 = 16;
        Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
        float f = 1.0F;
        AABB aabb = pPlayer.getBoundingBox().inflate(3d).expandTowards(vec31.scale(d0)).inflate(16.0D, 16.0D, 16.0D);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(pPlayer, vec3, vec32, aabb, (entity) -> {
            return entity.isPickable();
        }, d1);
        if (entityhitresult != null) {
            Entity entity1 = entityhitresult.getEntity();
            if (entity1 instanceof Manhack fb) {
            boolean flag = canSeeBlock(vec3, vec32, entity1.level, pPlayer.distanceTo(entity1));
            if (flag) return false;
            pPlayer.level.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PHYSCANNON_PICKUP.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            fb.moveTo(GravityGunFallingBlockEntity.findposstat(pPlayer));
            fb.setPlayerStringUuid(pPlayer.getStringUUID());
            fb.setNoGravity(true);
            SetCooldow(pPlayer, getRightClickCooldown());
            return true;
        } else return false;
        } else return false;
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
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pEntity instanceof Player pPlayer && !pLevel.isClientSide() && pIsSelected) {
        int j = GetHoldingCheck(pStack);
        if (j < 12) SetHoldingCheck(pStack, j + 1);
        else {
             BlockPos pBlockPos  = pEntity.blockPosition();
             int rad = 5;
            List<Entity> blocks = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> (obj instanceof GravityGunFallingBlockEntity block && block.getPlayerStringUUID().equals(pPlayer.getStringUUID())));
            SetHolding(pStack, !blocks.isEmpty());
            SetHoldingCheck(pStack, 0);
    }
        if (IsHolding(pStack)) {
            if (j % 2 == 0) pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PHYSCANNON_HOLD_LOOP.get(), SoundSource.NEUTRAL, 0.2F, 1F);
            if (j % 4 == 0) triggerAnim(pPlayer, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel),"idle", "hold");
        } else if (j % 2 == 0) triggerAnim(pPlayer, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel),"idle", "normal");
        }
   }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            shootright(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void shootright(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) {
         //   boolean flag = entityCatch(pPlayer);
         //   if (flag) return;

            BlockPos pBlockPos = pPlayer.blockPosition();
            int rad = 5;
            List<Entity> blocks = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> (obj instanceof GravityGunFallingBlockEntity block && block.getPlayerStringUUID().equals(pPlayer.getStringUUID())));
            if (!blocks.isEmpty()) {
                for (Entity block : blocks) {
                    if (block instanceof GravityGunFallingBlockEntity gblock) gblock.setnoplayer();
                //    if (block instanceof Manhack gblock) gblock.setnoplayer();
                    SetCooldow(pPlayer, getRightClickCooldown());
                    SetHolding(stack, false);
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PHYSCANNON_DROP.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                }
            } else {
                BlockHitResult result = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);
                BlockPos pPos = result.getBlockPos();

                BlockState pBlockState = pLevel.getBlockState(pPos);
                if (pBlockState.isAir()) {
                    SetCooldow(pPlayer, 10);
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PHYSCANNON_DRYFIRE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                    return;
                }
                if (pBlockState.is(HLTags.Blocks.GRAVITY_GUN_BLACKLIST)) {
                    SetCooldow(pPlayer, 10);
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PHYSCANNON_TOOHEAVY.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                    return;
                }
                GravityGunFallingBlockEntity fb = new GravityGunFallingBlockEntity(pLevel, (double) pPos.getX() + 0.5D, (double) pPos.getY(), (double) pPos.getZ() + 0.5D, pBlockState.hasProperty(BlockStateProperties.WATERLOGGED) ? pBlockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : pBlockState);
                pLevel.setBlock(pPos, pBlockState.getFluidState().createLegacyBlock(), 3);
                fb.moveTo(GravityGunFallingBlockEntity.findposstat(pPlayer));
                pLevel.addFreshEntity(fb);
                fb.setPlayerStringUuid(pPlayer.getStringUUID());
                fb.setNoGravity(true);
                fb.setOwner(pPlayer);
                SetCooldow(pPlayer, getRightClickCooldown());
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PHYSCANNON_PICKUP.get(), SoundSource.NEUTRAL, 0.5F, 1F);

            }
        }
    }


    public void shoot(Entity projectile, double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize().add(RandomSource.create().triangle(0.0D, 0.0172275D * (double)pInaccuracy), RandomSource.create().triangle(0.0D, 0.0172275D * (double)pInaccuracy), RandomSource.create().triangle(0.0D, 0.0172275D * (double)pInaccuracy)).scale((double)pVelocity);
        projectile.addDeltaMovement(vec3);
        projectile.yRotO = projectile.getYRot();
        projectile.xRotO = projectile.getXRot();
    }

    public void shootEntityFromRotation(Entity projectile, Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        this.shoot(projectile, (double)f, (double)f1, (double)f2, pVelocity, pInaccuracy);
        Vec3 vec3 = pShooter.getDeltaMovement();
        projectile.setDeltaMovement(projectile.getDeltaMovement().add(vec3.x, pShooter.isOnGround() ? 0.0D : vec3.y, vec3.z));
    } //block.addDeltaMovement(pPlayer.getEyePosition().subtract(0, 0.4f, 0).subtract(block.position()).normalize().scale(-2));


    public SoundEvent getLaunchSound() {
       switch(RandomSource.create().nextInt(0, 4)) {
           case 0: return HalfLifeSounds.SUPERPHYS_LAUNCH1.get();
           case 1: return HalfLifeSounds.SUPERPHYS_LAUNCH2.get();
           case 2: return HalfLifeSounds.SUPERPHYS_LAUNCH3.get();
           case 3: return HalfLifeSounds.SUPERPHYS_LAUNCH4.get();
       }
       return SoundEvents.FROG_AMBIENT;
    }

    @Override
    public void shootleft(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide()) {
            BlockPos pBlockPos = pPlayer.blockPosition();
            int rad = 5;
            List<Entity> blocks = EntityRetrievalUtil.getEntities((Level) pLevel,
                    new AABB(pBlockPos.getX() - rad, pBlockPos.getY() - rad, pBlockPos.getZ() - rad,
                            pBlockPos.getX() + rad, pBlockPos.getY() + rad, pBlockPos.getZ() + rad), obj -> (obj instanceof GravityGunFallingBlockEntity block && block.getPlayerStringUUID().equals(pPlayer.getStringUUID())));
            if (!blocks.isEmpty()) {
                for (Entity block : blocks) {

                  if (block instanceof GravityGunFallingBlockEntity gblock) {
                    gblock.setnoplayer();
                    gblock.setLaunched(); }

               //     if (block instanceof Manhack gblock) {
              //          gblock.setnoplayer();
              //          gblock.hurt(gblock.damageSources().playerAttack(pPlayer), 3);
              //      }

                    SetCooldow(pPlayer, getLeftClickCooldown());
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), getLaunchSound(), SoundSource.NEUTRAL, 0.5F, 1F);
                    this.shootEntityFromRotation(block,pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2F, 1.0F);
                    SetHolding(stack, false);

                    Vec3 from = pPlayer.getEyePosition().subtract(0, 0.3, 0);
                    Vec3 vec33 = new Vec3(block.getX(), block.getY()+0.45f, block.getZ());
                   Vec3 to = vec33.subtract(from).multiply(2, 2, 2);
                   ((ServerLevel) pPlayer.level).sendParticles(HalfLifeParticles.GRAV_GUN_ORANGE_LIGHTNING.get(), from.x, from.y, from.z, 0, to.x, to.y, to.z, 1d);



                }
            } else {
                boolean flag = entityHit(pPlayer);
                if (!flag) {
                    SetCooldow(pPlayer, 10);
                    pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), HalfLifeSounds.PHYSCANNON_DRYFIRE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                } else  SetCooldow(pPlayer, 20);
            }
        }
   }

    @Override
    public InteractionResultHolder<ItemStack> leftuse(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (GetCooldow(itemstack) > 0) return InteractionResultHolder.fail(itemstack);
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pHand), (ServerLevel) pLevel),"onetime", "shoot");
            shootleft(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.success(itemstack);
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "idle", state -> PlayState.CONTINUE)
                .triggerableAnim("normal", RawAnimation.begin().then("animation.gravitygun.normal", Animation.LoopType.LOOP))
                .triggerableAnim("hold", RawAnimation.begin().then("animation.gravitygun.hold", Animation.LoopType.LOOP)));
        controllerRegistrar.add(new AnimationController<>(this, "onetime", state -> PlayState.STOP)
                .triggerableAnim("shoot", RawAnimation.begin().then("animation.gravitygun.shootpassive", Animation.LoopType.PLAY_ONCE))
                .triggerableAnim("shoot2", RawAnimation.begin().then("animation.gravitygun.shootactive", Animation.LoopType.PLAY_ONCE)));
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

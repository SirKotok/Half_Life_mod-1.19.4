package net.sirkotok.half_life_mod.block.blockentity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.blockentity.HalfLifeBlockEntities;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Voltigore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class VoltigoreEggBlockEntity extends BlockEntity implements GeoBlockEntity {


   protected int tick = 0;
    protected int remove = -1;
    protected boolean cracked = false;



    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        tick++;

        if (remove>0) remove--;
        if (remove == 0) pLevel.removeBlock(pPos, false);

        if (this.tick % 20 == 0 && this.tick > 200 && (pRandom.nextFloat() < 0.2f)) {
            float i = pRandom.nextFloat();
            if (i > 0.01f) {
                if (i > 0.6f) pLevel.playSound((Player)null, pPos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + pRandom.nextFloat() * 0.2F);
            } else {
                pLevel.playSound((Player)null, pPos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + pRandom.nextFloat() * 0.2F);
                if (!level.isClientSide)
                {
                    pLevel.levelEvent(2001, pPos, Block.getId(pState));
                    Voltigore turtle = HalfLifeEntities.VOLTIGORE.get().create(pLevel);
                    this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                    remove = 20;
                    this.cracked = true;
                    if (turtle != null) {
                        turtle.setthistobaby(true);
                        turtle.setPersistenceRequired();
                        turtle.moveTo((double)pPos.getX() + 0.3D + 0.2D, (double)pPos.getY(), (double)pPos.getZ() + 0.3D, 0.0F, 0.0F);
                        ForgeEventFactory.onFinalizeSpawn((Mob) turtle, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(turtle.blockPosition()), MobSpawnType.BREEDING, null, null);
                        pLevel.addFreshEntity(turtle);
                    }
                }
            }
        }

    }







    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);

        var moddata = new CompoundTag();
        moddata.putBoolean("Cracked", this.cracked);
        nbt.put(HalfLifeMod.MOD_ID, moddata);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);

        CompoundTag tutorialmodData = nbt.getCompound(HalfLifeMod.MOD_ID);
        this.cracked = tutorialmodData.getBoolean("Cracked");
    }


    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }




    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public VoltigoreEggBlockEntity(BlockPos pos, BlockState state) {
        super(HalfLifeBlockEntities.VOLTIGORE_EGG_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if (this.cracked) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.voltigore_egg.crack", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.voltigore_egg.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
}
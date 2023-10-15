package net.sirkotok.half_life_mod.entity.mob.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.sirkotok.half_life_mod.entity.base.CatchableCreature;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Retaliate;
import net.sirkotok.half_life_mod.entity.brain.behaviour.Spawnsnarks;
import net.sirkotok.half_life_mod.item.ModItems;
import net.sirkotok.half_life_mod.sound.ModSounds;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class Snarknest extends CatchableCreature implements GeoEntity, SmartBrainOwner<Snarknest> {

    public Item getweopon(){
        return ModItems.SNARK_THROWER.get();
    }
    @Override
    public int getemount(){
        return 5;
    }
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Snarknest(EntityType type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }




    public static boolean checkNestSpawnRules(EntityType<Snarknest> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pBlockPos, RandomSource pRandom) {
        if (pBlockPos.getY() >= pLevel.getSeaLevel()) {
            return false;
        }
        int radius = 70;
        List<Mob> entities = EntityRetrievalUtil.getEntities((Level) pLevel,
                new AABB(pBlockPos.getX() - radius, pBlockPos.getY() - radius, pBlockPos.getZ() - radius,
                pBlockPos.getX() + radius, pBlockPos.getY() + radius, pBlockPos.getZ() + radius), obj -> obj instanceof Snarknest);
        if (!entities.isEmpty()) return false;

        return pLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(pLevel, pBlockPos, pRandom) && checkMobSpawnRules(pType, pLevel, pSpawnType, pBlockPos, pRandom);
    }





    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.SNARK_DIE.get();
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return ModSounds.SNARK_DIE.get();
    }




    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5D)
                .add(Attributes.ATTACK_DAMAGE, 0f)
                .add(Attributes.ATTACK_SPEED, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate)); }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snarknest.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }






    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }



    @Override
    public List<ExtendedSensor<Snarknest>> getSensors() {
        return ObjectArrayList.of(
                new HurtBySensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<Snarknest>()
                                .setPredicate((target, entity) ->
                                        target instanceof Player || target instanceof IronGolem || target instanceof HalfLifeNeutral ||
                                                target instanceof AbstractVillager));
    }



    @Override
    public BrainActivityGroup<Snarknest> getCoreTasks() {
        return BrainActivityGroup.coreTasks();
    }

    @Override
    public BrainActivityGroup<Snarknest> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Snarknest>(
                        new TargetOrRetaliate<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))));
    }




    @Override
    public BrainActivityGroup<Snarknest> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new Spawnsnarks<>(0).cooldownFor(entity -> 200)
                );
    }

}

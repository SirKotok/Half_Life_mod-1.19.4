package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.behaviour.*;
import net.sirkotok.half_life_mod.misc.gamerules.HalfLifeGameRules;
import net.sirkotok.half_life_mod.misc.util.HLperUtil;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.AllApplicableBehaviours;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.util.BrainUtils;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class AquaticBullsquid extends Bullsquid{
    public AquaticBullsquid(EntityType type, Level level) {
        super(type, level);
    }


    public int getwateremountbelow() {
        int i = 0;
        for (int j = 1; j < 20; j++) {
            BlockPos pos = new BlockPos(this.blockPosition().getX(), this.blockPosition().getY() - j, this.blockPosition().getZ());
            BlockState blockstate = this.level.getBlockState(pos);
            if (!blockstate.is(Blocks.WATER)) return i;
            i++;
        }
        return i;
    }

    private int attackbehaviour;

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if (this.attackbehaviour == 1 && RandomSource.create().nextFloat() < 0.1f) {
            BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
            this.attackbehaviour = 0;
        }


        return super.hurt(pSource, pAmount);
    }


    private BlockPos targetPosition;
    public void setAttackbehaviour(int attackbehaviour) {
        this.attackbehaviour = attackbehaviour;
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.getGameRules().getRule(HalfLifeGameRules.HALF_LIFE_AI_IS_ON).get()) return;
        if (!this.isInWaterOrBubble())  {super.customServerAiStep(); return;}

        tickBrain(this);


        if (BrainUtils.getTargetOfEntity(this) != null && this.attackbehaviour == 0) {
            Vec3 vec31 =  BrainUtils.getTargetOfEntity(this).position();
            Vec3 vec3 = this.position();
            Vec3 between = vec3.subtract(vec31);
            this.setYRot((float) HLperUtil.yanglefromvec3(between));
            this.setXRot((float) HLperUtil.yanglefromvec3(between));
            return;
        }

        if (this.targetPosition != null && (!this.level.getBlockState(this.targetPosition).equals(Blocks.WATER.defaultBlockState()) || this.targetPosition.getY() <= this.level.getMinBuildHeight())) {
            this.targetPosition = null;
        }

        if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.position(), 2.0D)) {
            this.targetPosition = BlockPos.containing(this.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7), this.getY() + (double) this.random.nextInt(6) - 2.0D, this.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7));
        }

        double d2 = (double) this.targetPosition.getX() + 0.5D - this.getX();
        double d0 = (double) this.targetPosition.getY() + 0.1D - this.getY();
        double d1 = (double) this.targetPosition.getZ() + 0.5D - this.getZ();
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec31 = vec3.add((Math.signum(d2) * 0.5D - vec3.x) * (double) 0.1F, (Math.signum(d0) * (double) 0.7F - vec3.y) * (double) 0.1F, (Math.signum(d1) * 0.5D - vec3.z) * (double) 0.1F);
        float f = (float) (Mth.atan2(vec31.z, vec31.x) * (double) (180F / (float) Math.PI)) - 90.0F;
        float f1 = Mth.wrapDegrees(f - this.getYRot());
        this.zza = 0.5F;
        this.setYRot(this.getYRot() + f1);


    }

    @Override
    public BrainActivityGroup<Bullsquid> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>().startCondition(entity -> !this.isInWaterOrBubble()));
    }


    @Override
    public BrainActivityGroup<Bullsquid> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new AllApplicableBehaviours<>(
                new FirstApplicableBehaviour<>(
                        new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, HalfLifeMemoryModuleType.HUNGRY.get()))
                                .startCondition(entity -> ((this.getHealth() + 2) < this.getMaxHealth())).cooldownFor(entity -> 400),
                        new CustomBehaviour<>(entity -> this.entityData.set(IS_ANGRY, true))
                                .startCondition(entity -> this.getLastAttacker() != null)
                                .whenStarting(entity -> triggerAnim("onetime", "startled"))
                                .cooldownFor(entity -> 200),
                        new TargetOrRetaliate<>(),
                        new EatingBehavior<>().callback(entity -> this.entityData.set(IS_EATING, true))
                                .whenStopping(entity -> this.entityData.set(IS_EATING, false)),
                        new SetFoodToWalkTarget<>().whenStarting(entity -> this.Smellfor()).cooldownFor(entity -> 400),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().dontAvoidWater(),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 90))
                                .whenStarting(entity -> this.entityData.set(IS_ANGRY, false)))
                ).startCondition(entity -> !this.isInWaterOrBubble()),
                new AllApplicableBehaviours<>(
                        new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 150),
                        new CustomBehaviour<>(entity -> this.attackbehaviour = 0).startCondition(entity -> this.attackbehaviour != 0),
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>(),
                        new SetBlockToWalkTargetNoInterest<>(),
                        new FirstApplicableBehaviour<>(
                                new PushToWalkTarget<>(true)
                                        .setXZMoveSpeedMod(entity -> 0.3f)
                                        .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.getwateremountbelow() < 3)
                                        .cooldownFor(entity -> 1),
                                new PushToWalkTarget<>()
                                        .setXZMoveSpeedMod(entity -> 0.2f)
                                        .startCondition(entity -> this.isInWaterOrBubble())
                                        .cooldownFor(entity -> entity.getRandom().nextInt(1, 2)))
                ).startCondition(entity -> this.isInWaterOrBubble())
        );
    }

    @Override
    public BrainActivityGroup<Bullsquid> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new AllApplicableBehaviours<>(
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new SetWalkTargetToAttackTarget<>().speedMod(1.84f).whenStarting(entity -> this.entityData.set(IS_ANGRY, true)),
                new FirstApplicableBehaviour<>(
                        new ConfigurableAnimatableMeleeAttack<>(20, 1f, 2, 2, null, 0, null, null)
                                .whenStarting(entity -> triggerAnim("onetime", "spin"))
                                .cooldownFor(entity -> random.nextInt(40, 80))
                                .startCondition(entity -> HLperUtil.TargetOrThis(this).getHealth()<10.5f),
                        new ConfigurableAnimatableMeleeAttack<>(15, 0.3f, 1, 2, null, 0, this.getBiteSound(), this.getAttackGrowlSound())
                                .whenStarting(entity -> triggerAnim("onetime", "bite"))
                                .cooldownFor(entity -> random.nextInt(20, 30)),
                        new StopAndShoot<>(21, 0, 1f).cooldownFor(entity -> random.nextInt(20, 40))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot")))
                ).startCondition(entity -> !this.isInWaterOrBubble()),
                new AllApplicableBehaviours<>(
                new CustomBehaviour<>(entity -> this.setAttackbehaviour(random.nextInt(2))).startCondition(entity -> this.random.nextFloat() < 0.01f).cooldownFor(entity -> 400),
                new CustomBehaviour<>(entity -> this.setAttackbehaviour(0)).startCondition(entity -> this.attackbehaviour == 1 && (!HLperUtil.TargetOrThis(this).isInWaterOrBubble() || (entity.distanceTo(HLperUtil.TargetOrThis(this)) > 5f && entity.distanceTo(HLperUtil.TargetOrThis(this)) < 12f))),
                new CustomBehaviour<>(entity -> this.setAttackbehaviour(1)).startCondition(entity -> this.attackbehaviour == 0 && ((entity.distanceTo(HLperUtil.TargetOrThis(this)) < 2f || entity.distanceTo(HLperUtil.TargetOrThis(this)) > 18 ) && HLperUtil.TargetOrThis(this).isInWaterOrBubble())),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).cooldownFor(entity -> 200),
                new CustomBehaviour<>(entity -> BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET)).startCondition(entity -> this.attackbehaviour == 0).cooldownFor(entity -> 200),
                new InvalidateAttackTarget<>(),
                new Retaliate<>(),
                new FirstApplicableBehaviour<>(
                        new SetBlockToWalkTargetNoInterest<>().startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0),
                        new SetWalkTargetToAttackTarget<>().startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour != 0)
                ),
                new FirstApplicableBehaviour<>(
                        new PushToWalkTarget<>(true)
                                .setXZMoveSpeedMod(entity -> 0.3f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0 && this.getwateremountbelow() < 3)
                                .cooldownFor(entity -> 1),
                        new PushToWalkTarget<>()
                                .setXZMoveSpeedMod(entity -> 0.3f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour == 0 )
                                .cooldownFor(entity -> 1),
                        new PushToWalkTargetDontStop<>()
                                .setXZMoveSpeedMod(entity -> 0.33f)
                                .startCondition(entity -> (this.isInWaterOrBubble() || !this.isOnGround()) && this.attackbehaviour != 0)
                                .cooldownFor(entity -> 1)
                ),
                new FirstApplicableBehaviour<>(
                        new ConfigurableAnimatableMeleeAttack<>(15, 0.3f, 1, 2, null, 0, this.getBiteSound(), this.getAttackGrowlSound())
                                .whenStarting(entity -> triggerAnim("onetime", "bite"))
                                .cooldownFor(entity -> random.nextInt(20, 30)),
                        new StopAndShoot<>(21, 0, 1f).cooldownFor(entity -> random.nextInt(20, 40))
                                .whenStarting(entity -> triggerAnim("onetime", "shoot"))
                )
        ).startCondition(entity -> this.isInWaterOrBubble()));
    }



    @Override
    protected  <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {

        if (BrainUtils.getMemory(this, MemoryModuleType.SNIFFER_HAPPY) != null) {
            if (BrainUtils.getMemory(this, MemoryModuleType.SNIFFER_HAPPY).equals(true)) {return PlayState.CONTINUE;}}


        if (this.isInWaterOrBubble() && this.getwateremountbelow() == 0) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        if(tAnimationState.isMoving()) {
            if (!this.isangry()) {
                tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.walk", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;
            }
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }


        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.bullsquid.idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;

    }



}

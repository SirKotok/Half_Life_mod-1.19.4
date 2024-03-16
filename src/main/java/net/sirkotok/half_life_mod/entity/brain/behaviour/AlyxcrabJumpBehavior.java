package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_3;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Headcrab_Armored;
import net.sirkotok.half_life_mod.misc.util.HLTags;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class AlyxcrabJumpBehavior<E extends Headcrab_3>  extends ConditionlessAttack<E> {


    protected SoundEvent jumpsound;
    protected final RandomSource random = RandomSource.create();
    protected SoundEvent bitesound;
    protected float sitchance;
    protected int jumpdelayticks;
    protected int tickstillfalldown;
    protected int fullanimationticks;
    protected int tick = 0;
    protected boolean headcrabjumped = false;
    protected boolean animationed = false;
    protected boolean fell = false;
    protected boolean bit = false;


    public AlyxcrabJumpBehavior(int animationlengthbeforejump, int fullanimationlength, int tickstillfalldown, SoundEvent jumpsound, SoundEvent bitesound, float sitchance) {
        super(tickstillfalldown);
        this.jumpsound = jumpsound;
        this.fullanimationticks = fullanimationlength;
        this.bitesound = bitesound;
        this.sitchance = sitchance;
        this.jumpdelayticks = animationlengthbeforejump;
        this.tickstillfalldown = tickstillfalldown;

    }


    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));



    public int MinDistance = 0;
    public int MaxDistance = 7;



    public AlyxcrabJumpBehavior<E> SetMinDistance(int distance) {
        this.MinDistance = distance;
        return this;
    }
    public AlyxcrabJumpBehavior<E> SetMaxDistance(int distance) {
        this.MaxDistance = distance;
        return this;
    }




    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);
        return (!entity.isPassenger()) && (this.target != null) && (entity.isOnGround()) && (entity.distanceTo(this.target) >= this.MinDistance) && (entity.distanceTo(this.target) <= this.MaxDistance);
    }


    public void jump(E headcrab)
    {
        Vec3 position = new Vec3(headcrab.getX(), headcrab.getY(), headcrab.getZ());
        Vec3 motion = new Vec3(this.target.getX(), this.target.getY() + (double) this.target.getEyeHeight(), this.target.getZ()).subtract(position).normalize().scale(1.9D);
        headcrab.setDeltaMovement(motion);
    }



    public double getSquareDistanceToHead(E entity)
    {
        return entity.distanceToSqr(this.target.getX(), this.target.getY() + this.target.getEyeHeight(),this.target.getZ());
    }
    public boolean canSitOnHead() {
        return this.target.getType().is(HLTags.EntityTypes.HEAD_CRAB_TARGET) && (this.target.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) && this.target.getPassengers().isEmpty();
    }
    public boolean canBite(E entity)
    {
        return this.getSquareDistanceToHead(entity) <= 0.36F && !entity.isPassenger();
    }

    @Override
    protected void start(E entity) {
        super.start(entity);
        this.tick = 0;
        this.headcrabjumped = false;
        this.animationed = false;
        this.bit = false;
        this.fell = false;
    }

    @Override
    protected void stop(E entity) {
        if (entity instanceof Headcrab_Armored) {((Headcrab_Armored) entity).setIsVulnerable(false);}
        super.stop(entity);
    }

    @Override
    protected void tick(E entity) {
        super.tick(entity);
        this.tick++;
        if (this.tick > 0 && !this.animationed) {
            this.animationed = true;
            entity.triggerAnim("jump", "jump");
        }

        if (this.tick > (this.tickstillfalldown-10) && this.headcrabjumped && !this.fell) {
            entity.stopAiFor(this.fullanimationticks-this.tickstillfalldown);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.fullanimationticks-this.tickstillfalldown, 100, false, false, false));
            this.fell = true;
        }

        if (this.tick > this.jumpdelayticks && !this.headcrabjumped) {
            if (entity.isOnGround() && BrainUtils.getMemory(entity, MemoryModuleType.ATTACK_TARGET) != null) {
               if (entity instanceof Headcrab_Armored) {((Headcrab_Armored) entity).setIsVulnerable(true);}
                this.jump(entity);
                this.headcrabjumped = true;
                entity.playSound(this.jumpsound, 0.3f, 1);
            }
            BrainUtils.setForgettableMemory(entity, ModMemoryModuleType.JUMPING.get(), true, 50);
        }




        if (this.headcrabjumped && !this.fell && !this.bit) {
        if (!entity.isOnGround() && BrainUtils.getMemory(entity, MemoryModuleType.ATTACK_TARGET) != null) {
            if(canBite(entity))
            {
                this.bit = true;
                entity.playSound(this.bitesound, 0.3f, 1);
                entity.doHurtTarget(this.target);
                if (canSitOnHead() && (this.random.nextFloat()<this.sitchance)) {

                    entity.startRiding(this.target, true);
                    if(this.target instanceof ServerPlayer player) {
                        player.connection.send(new ClientboundSetPassengersPacket(player)); }

                    doDelayedAction(entity);
                    this.delayedCallback.accept(entity);
                }
            }
        }
        }
    }


    @Override
    protected void doDelayedAction(E entity) {
        super.doDelayedAction(entity);
    }

}

/*




    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);
        return (this.target != null) && (!entity.isOnGround()) && (!entity.isPassenger());
    }





}

 */




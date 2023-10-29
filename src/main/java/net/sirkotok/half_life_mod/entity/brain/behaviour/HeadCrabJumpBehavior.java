package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.Vec3;

import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.List;

public class HeadCrabJumpBehavior<E extends Monster>  extends ConditionlessAttack<E> {


    protected SoundEvent jumpsound;
    @Nullable
    protected SoundEvent scream;

    public HeadCrabJumpBehavior(int delayTicks, @Nullable SoundEvent jumpsound, @Nullable SoundEvent screamsound) {
        super(delayTicks);
        this.jumpsound = jumpsound;
        this.scream = screamsound;
    }


    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));



    public int MinDistance = 0;
    public int MaxDistance = 7;



    public HeadCrabJumpBehavior<E> SetMinDistance(int distance) {
        this.MinDistance = distance;
        return this;
    }
    public HeadCrabJumpBehavior<E> SetMaxDistance(int distance) {
        this.MaxDistance = distance;
        return this;
    }



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected void start(E entity) {
        super.start(entity);
        if (this.scream !=null) {entity.playSound(this.scream, 0.3f, 1);}
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


    @Override
    protected void doDelayedAction(E entity) {

        if (entity.isOnGround() && BrainUtils.getMemory(entity, MemoryModuleType.ATTACK_TARGET) != null) {
                BehaviorUtils.lookAtEntity(entity, this.target);
                this.jump(entity);
                if (this.jumpsound != null) {
                entity.playSound(this.jumpsound, 0.3f, 1);
                }
            }


        BrainUtils.setForgettableMemory(entity, ModMemoryModuleType.JUMPING.get(), true, 50);
    }




}




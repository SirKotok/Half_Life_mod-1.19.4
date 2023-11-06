package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class BarkAtTarget<E extends Mob> extends ConditionlessAttack<E> {

    protected SoundEvent bark;
    protected int tick = 0;
    protected boolean animationed = false;


    public BarkAtTarget(int delayTicks, SoundEvent bark) {
        super(delayTicks);
        this.bark = bark;
    }


    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));


    public float MaxDistance = 2.7f;



    public BarkAtTarget<E> SetMaxDistance(int distance) {
        this.MaxDistance = distance;
        return this;
    }



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected void start(E entity) {
        this.animationed = false;
        this.tick = 0;
        super.start(entity);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);
        return this.target != null && (entity.distanceTo(this.target) <= this.MaxDistance);
    }



    @Override
    protected void tick(E entity) {
        super.tick(entity);
        this.tick++;
        if (this.tick > 0 && this.tick<10 && !this.animationed) {
            this.animationed = true;
            entity.playSound(this.bark);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.delayTime, 100, false, false, false));
        }
    }



    @Override
    protected void doDelayedAction(E entity) {
    }

}

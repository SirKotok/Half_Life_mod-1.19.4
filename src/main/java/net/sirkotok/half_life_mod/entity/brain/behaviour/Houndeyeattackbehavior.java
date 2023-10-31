package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Houndeye;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.List;

public class Houndeyeattackbehavior<E extends Houndeye> extends ConditionlessAttack<E> {
    @Nullable
    protected SoundEvent blastsound;
    @Nullable
    protected SoundEvent buildup;
    protected int tick = 0;
    protected boolean animationed = false;
    protected boolean flag = false;

    public Houndeyeattackbehavior(int delayTicks, @Nullable SoundEvent blastsound, @Nullable SoundEvent buildup) {
        super(delayTicks);
        this.blastsound = blastsound;
        this.buildup = buildup;
    }


    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));


    public float MaxDistance = 2.7f;



    public Houndeyeattackbehavior<E> SetMaxDistance(int distance) {
        this.MaxDistance = distance;
        return this;
    }



    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected void start(E entity) {
        this.flag = false;
        this.tick = 0;
        this.animationed = false;
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
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 100, false, false, false));
            entity.triggerAnim("long", "attack");
            entity.level.gameEvent(entity, GameEvent.ENTITY_ROAR, entity.blockPosition());
            entity.playSound(buildup);
        }
        if (this.tick > 10 && this.tick < 16 ) {
            this.animationed = false;
        }

        if (this.tick<=19) {
        this.flag = (entity.tickCount - entity.getLastHurtByMobTimestamp() < this.tick); }

        if (this.tick > 19 && !this.flag && !this.animationed) {
            entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 17, 100, false, false, false));
            this.animationed = true;

            //TODO: make this work
            /*
             if (!(entity instanceof Hoteye)) {
                if (!entity.getLevel().isClientSide()) {
                    ServerLevel level = (ServerLevel) entity.level;
                    ShockWaveEffect wave = ModEntities.SHOCKWAVEEFFECT.get().create(level);
                    if (wave != null) {
                        wave.moveTo(entity.getX(), entity.getY(), entity.getZ());
                        wave.setSquadSize(entity.getsquaidsize()); //Determines the texture of the shockwave based on squad size
                        level.addFreshEntity(wave);
                        wave.triggerAnim("attack", "attack");
                    }
                }
            } */

           entity.triggerAnim("attack", "attack");
            if (this.blastsound != null) {
             entity.playSound(blastsound);
            }

        }



    }




    @Override
    protected void doDelayedAction(E entity) {
        if (!this.flag) entity.performattack();
    }
}

package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.HalfLifeEntities;
import net.sirkotok.half_life_mod.entity.mob_effect_entity.custom.VortShockWaveEffect;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.VortigauntHL2;
import net.sirkotok.half_life_mod.misc.util.CommonSounds;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.List;

public class VortWaveattackbehavior<E extends VortigauntHL2> extends ConditionlessAttack<E> {
    @Nullable
    protected SoundEvent blastsound;
    @Nullable
    protected SoundEvent buildup;
    protected int tick = 0;
    protected boolean animationed = false;

    public VortWaveattackbehavior(int delayTicks, @Nullable SoundEvent blastsound, @Nullable SoundEvent buildup) {
        super(delayTicks);
        this.blastsound = blastsound;
        this.buildup = buildup;
    }


    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));


    public float MaxDistance = 2.7f;



    public VortWaveattackbehavior<E> SetMaxDistance(int distance) {
        this.MaxDistance = distance;
        return this;
    }





    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }


    @Override
    protected void start(E entity) {
        entity.setcharge(2);
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
            entity.setcharge(1);
            this.animationed = true;
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 25, 100, false, false, false));
            entity.triggerAnim("onetime", "blast");
            entity.level.gameEvent(entity, GameEvent.ENTITY_ROAR, entity.blockPosition());
            CommonSounds.PlaySoundAsOwn(entity, buildup);
        }
        if (this.tick > 10 && this.tick < 16 ) {
            this.animationed = false;
        }


        if (this.tick > 19  && !this.animationed) {
            entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 17, 100, false, false, false));
            this.animationed = true;


                if (!entity.getLevel().isClientSide()) {
                    ServerLevel level = (ServerLevel) entity.level;
                    VortShockWaveEffect wave = HalfLifeEntities.VORTSHOCKWAVEEFFECT.get().create(level);
                    if (wave != null) {
                        wave.moveTo(entity.getX(), entity.getY(), entity.getZ());
                        wave.setYBodyRot(entity.yBodyRot);
                        ForgeEventFactory.onFinalizeSpawn((Mob) wave, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(wave.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                        level.addFreshEntity(wave);
                        wave.triggerAnim("attack", "attack");
                    }
            }


            CommonSounds.PlaySoundAsOwn(entity, blastsound);

        }



    }

    @Override
    protected void stop(E entity) {
        entity.setcharge(0);
        super.stop(entity);
    }

    @Override
    protected void doDelayedAction(E entity) {
         entity.performattack();
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, 20);
    }
}

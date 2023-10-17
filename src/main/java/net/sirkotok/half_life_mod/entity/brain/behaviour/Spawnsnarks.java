package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.ForgeEventFactory;
import net.sirkotok.half_life_mod.entity.ModEntities;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.Snark;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

public class Spawnsnarks<E extends LivingEntity> extends ConditionlessAttack<E> {
    public Spawnsnarks(int delayTicks) {
        super(delayTicks);
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (!entity.getLevel().isClientSide()) {
            ServerLevel level = (ServerLevel) entity.level;
            for (int i = 0; i < 5; i++) {
            Snark summon = ModEntities.SNARK.get().create(level);
            if (summon != null) {
            summon.moveTo(entity.position());
            ForgeEventFactory.onFinalizeSpawn((Mob) summon, (ServerLevelAccessor) level, level.getCurrentDifficultyAt(summon.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
            level.addFreshEntity(summon);
            BrainUtils.setMemory(summon, MemoryModuleType.ATTACK_TARGET, BrainUtils.getMemory(entity, MemoryModuleType.ATTACK_TARGET));
        }}}

    }
}

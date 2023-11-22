package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;

public class PushToWalkTargetDontStop<E extends PathfinderMob> extends PushToWalkTarget<E> {

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return true;
    }
}

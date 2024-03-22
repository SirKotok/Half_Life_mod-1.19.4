package net.sirkotok.half_life_mod.entity.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import com.mojang.util.UUIDTypeAdapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import net.sirkotok.half_life_mod.entity.base.HalfLifeNeutral;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeMemoryModuleType;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.UUID;

public class HalfLife1SetFollowToWalkTarget<E extends HalfLifeNeutral> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.NEAREST_PLAYERS, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED));


    protected float speedModifier = 1;

    /**
     * Set the movespeed modifier for the entity when moving to the target.
     * @param speedModifier The movespeed modifier/multiplier
     * @return this
     */
    public  HalfLife1SetFollowToWalkTarget<E> speedMod(float speedModifier) {
        this.speedModifier = speedModifier;
        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        String id = entity.getFollowUUIDString();
        if (id.equals("")) {
            if (Boolean.TRUE.equals(BrainUtils.getMemory(entity, HalfLifeMemoryModuleType.FOLLOWING_PLAYER.get()) != null)) {
                BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                BrainUtils.clearMemory(entity, HalfLifeMemoryModuleType.FOLLOWING_PLAYER.get());
            }
            return;
        }
        UUID uuid = UUIDTypeAdapter.fromString(id);
        List<Player> playerlist = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_PLAYERS);
        if (playerlist == null || playerlist.isEmpty()) return;
        for (Player target : playerlist) {
            if (target.getUUID().equals(uuid)) {
                if (entity.getSensing().hasLineOfSight(target) && entity.distanceTo(target) < 3) {
                    BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);
                }
                else {
                    BrainUtils.setMemory(entity, HalfLifeMemoryModuleType.FOLLOWING_PLAYER.get(), target);
                    BrainUtils.setMemory(entity, MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
                    BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(target, false), this.speedModifier, 0));
                }
            }
        }
    }
}

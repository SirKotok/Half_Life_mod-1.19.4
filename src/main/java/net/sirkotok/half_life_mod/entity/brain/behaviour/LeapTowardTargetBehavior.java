package net.sirkotok.half_life_mod.entity.brain.behaviour;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;

import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.util.BrainUtils;

public class LeapTowardTargetBehavior<E extends Mob>  extends ConditionlessAttack<E> {

    private final float yd;
    public LeapTowardTargetBehavior(int delayTicks, float yd) {
        super(delayTicks);
        this.yd = yd;
    }


    protected static int reducedTickDelay(int p_186074_) {
        return Mth.positiveCeilDiv(p_186074_, 2);
    }
    public boolean canUse(E entity) {
        if (entity.isVehicle()) {
            return false;
        } else {
            this.target = entity.getTarget();
            if (this.target == null) {
                return false;
            } else {
                double d0 = entity.distanceToSqr(this.target);
                if (!(d0<5) && !(d0 > 30.0D)) {
                    if (!entity.isOnGround()) {
                        return false;
                    } else {
                        return entity.getRandom().nextInt(reducedTickDelay(5)) == 0;
                    }
                } else {
                    return false;
                }
            }
        }
    }


    @Override
    protected void doDelayedAction(E entity) {
        if (!canUse(entity))
            return;

        Vec3 vec3 = entity.getDeltaMovement();
        Vec3 vec31 = new Vec3(this.target.getX() - entity.getX(), 0.0D, this.target.getZ() - entity.getZ());
        if (vec31.lengthSqr() > 1.0E-7D) {
            vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(0.2D));
        }

        entity.setDeltaMovement(vec31.x, (double)this.yd, vec31.z);

    }



}

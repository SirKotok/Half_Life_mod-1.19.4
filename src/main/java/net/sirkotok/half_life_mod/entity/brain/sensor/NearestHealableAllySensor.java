package net.sirkotok.half_life_mod.entity.brain.sensor;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeSensorType;
import net.sirkotok.half_life_mod.misc.util.InfightingUtil;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class NearestHealableAllySensor<E extends LivingEntity> extends PredicateSensor<LivingEntity, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(HalfLifeMemoryModuleType.NEAREST_HEALABLE_ALLY.get());

    @Nullable
    protected SquareRadius radius = null;

    public NearestHealableAllySensor() {
        super((target, entity) -> target != entity && target.isAlive());
    }

    /**
     * Set the radius for the sensor to scan.
     *
     * @param radius The coordinate radius, in blocks
     * @return this
     */
    public NearestHealableAllySensor<E> setRadius(double radius) {
        return setRadius(radius, radius);
    }

    /**
     * Set the radius for the sensor to scan.
     *
     * @param xz The X/Z coordinate radius, in blocks
     * @param y  The Y coordinate radius, in blocks
     * @return this
     */
    public NearestHealableAllySensor<E> setRadius(double xz, double y) {
        this.radius = new SquareRadius(xz, y);

        return this;
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return HalfLifeSensorType.NEAREST_HEALABLE_ALLY_SENSOR.get();
    }

    @Override
    protected void doTick(ServerLevel level, E entity) {
        SquareRadius radius = this.radius;
        if (radius == null) {
            double dist = entity.getAttributeValue(Attributes.FOLLOW_RANGE);

            radius = new SquareRadius(dist, dist);
        }
        List<LivingEntity> entities = EntityRetrievalUtil.getEntities(level, entity.getBoundingBox().inflate(radius.xzRadius(), radius.yRadius(), radius.xzRadius()), obj -> obj instanceof LivingEntity livingEntity && !(obj instanceof Player) && InfightingUtil.issameteam(livingEntity, entity) && predicate().test(livingEntity, entity));
        entities.sort(Comparator.comparingDouble(entity::distanceToSqr));
        if (!entities.isEmpty()) {
            LivingEntity entit = entities.get(0);
            BrainUtils.setMemory(entity, HalfLifeMemoryModuleType.NEAREST_HEALABLE_ALLY.get(), entit);
        }
    }
}

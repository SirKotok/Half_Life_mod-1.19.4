package net.sirkotok.half_life_mod.entity.brain.sensor;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;

import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.HalfLifeSensorType;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AlienGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Cockroach;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;

import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiPredicate;

public class SmellSensor<E extends LivingEntity> extends PredicateSensor<LivingEntity, E> {
    @Nullable
    protected SquareRadius radius = null;
    public SmellSensor<E> setRadius(double xz, double y) {
        this.radius = new SquareRadius(xz, y);

        return this;
    }








    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(HalfLifeMemoryModuleType.FOOD_SMELL.get());

    @Override
    public List<MemoryModuleType<?>> memoriesUsed()  {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return HalfLifeSensorType.SMELL_SENSOR.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, entity) -> target.isDeadOrDying() && !(target instanceof Cockroach) && (!(entity instanceof AlienGrunt) || target.getLastAttacker() == entity);
    }
    protected Vec3 getPosition(LivingEntity entity){
        return new Vec3(entity.getX(), entity.getY(), entity.getZ());
    }


    @Override
    protected void doTick(ServerLevel level, E entity) {


        SquareRadius radius = this.radius;
        if (radius == null) {
            double dist = entity.getAttributeValue(Attributes.FOLLOW_RANGE);
            radius = new SquareRadius(dist, dist);
        }
        List<Vec3> food;
        List<LivingEntity> entities = EntityRetrievalUtil.getEntities(level, entity.getBoundingBox().inflate(radius.xzRadius(), radius.yRadius(), radius.xzRadius()), obj -> obj instanceof LivingEntity livingEntity && predicate().test(livingEntity, entity));
        ListIterator<LivingEntity>
                iterator = entities.listIterator();

        while (iterator.hasNext()) {
            food = BrainUtils.getMemory(entity, HalfLifeMemoryModuleType.FOOD_SMELL.get());
            if (food == null) {food = new ArrayList<Vec3>();}
            LivingEntity foodsource = iterator.next();
            food.add(getPosition(foodsource));
            BrainUtils.setMemory(entity, HalfLifeMemoryModuleType.FOOD_SMELL.get(), food);
            }
        }

}

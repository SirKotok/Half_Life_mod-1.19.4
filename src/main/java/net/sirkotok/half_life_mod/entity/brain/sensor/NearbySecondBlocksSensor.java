package net.sirkotok.half_life_mod.entity.brain.sensor;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.level.block.state.BlockState;
import net.sirkotok.half_life_mod.entity.brain.ModMemoryModuleType;
import net.sirkotok.half_life_mod.entity.brain.ModSensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.registry.SBLSensors;

import java.util.List;

/**
 * Sensor for identifying and memorising nearby blocks using the {@link SBLMemoryTypes#NEARBY_BLOCKS} memory module. <br>
 * Defaults:
 * <ul>
 *     <li>1-block radius</li>
 *     <li>Ignores air blocks</li>
 * </ul>
 */
public class  NearbySecondBlocksSensor<E extends LivingEntity> extends PredicateSensor<BlockState, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(ModMemoryModuleType.NEARBY_BLOCKS_TWO.get());

    protected SquareRadius radius = new SquareRadius(1, 1);

    public NearbySecondBlocksSensor() {
        setPredicate((state, entity) -> !state.isAir());
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return ModSensorType.NEARBY_SECOND_BLOCKS_SENSOR.get();
    }

    /**
     * Set the radius for the sensor to scan
     * @param radius The coordinate radius, in blocks
     * @return this
     */
    public NearbySecondBlocksSensor<E> setRadius(double radius) {
        return setRadius(radius, radius);
    }

    /**
     * Set the radius for the sensor to scan.
     * @param xz The X/Z coordinate radius, in blocks
     * @param y The Y coordinate radius, in blocks
     * @return this
     */
    public NearbySecondBlocksSensor<E> setRadius(double xz, double y) {
        this.radius = new SquareRadius(xz, y);

        return this;
    }

    @Override
    protected void doTick(ServerLevel level, E entity) {
        List<Pair<BlockPos, BlockState>> blocks = new ObjectArrayList<>();

        for (BlockPos pos : BlockPos.betweenClosed(entity.blockPosition().subtract(this.radius.toVec3i()), entity.blockPosition().offset(this.radius.toVec3i()))) {
            BlockState state = level.getBlockState(pos);

            if (this.predicate().test(state, entity))
                blocks.add(Pair.of(pos.immutable(), state));
        }

        if (blocks.isEmpty()) {
            BrainUtils.clearMemory(entity, ModMemoryModuleType.NEARBY_BLOCKS_TWO.get());
        }
        else {
            BrainUtils.setMemory(entity, ModMemoryModuleType.NEARBY_BLOCKS_TWO.get(), blocks);
        }
    }
}

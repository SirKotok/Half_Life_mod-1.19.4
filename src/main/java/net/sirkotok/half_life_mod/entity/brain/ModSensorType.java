package net.sirkotok.half_life_mod.entity.brain;



import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;

import net.sirkotok.half_life_mod.entity.brain.sensor.SmellSensor;


public class ModSensorType {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
            DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, HalfLifeMod.MOD_ID);

    public static final RegistryObject<SensorType<SmellSensor<?>>> SMELL_SENSOR = SENSOR_TYPES.register("smellsensor", () -> new SensorType<>(SmellSensor::new));

    public static void register(IEventBus eventBus) {
        SENSOR_TYPES.register(eventBus);
    }
}

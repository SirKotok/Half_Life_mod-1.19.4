package net.sirkotok.half_life_mod.entity.brain;



import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;



import java.util.List;
import java.util.Optional;


public class ModMemoryModuleType {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES =
            DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, HalfLifeMod.MOD_ID);

    public static final RegistryObject<MemoryModuleType<Boolean>> JUMPING = MEMORY_MODULE_TYPES.register("jumping", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Integer>> FOOD_ID = MEMORY_MODULE_TYPES.register("food_id", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final RegistryObject<MemoryModuleType<Boolean>> HUNGRY = MEMORY_MODULE_TYPES.register("hungry", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<LivingEntity>> FOLLOWING_PLAYER = MEMORY_MODULE_TYPES.register("following_player", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<LivingEntity>> NEAREST_HEALABLE_ALLY = MEMORY_MODULE_TYPES.register("nearest_healable_ally", () -> new MemoryModuleType<>(Optional.empty()));

    public static final RegistryObject<MemoryModuleType<List<Vec3>>> FOOD_SMELL = MEMORY_MODULE_TYPES.register("food_smell", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<List<Pair<BlockPos, BlockState>>>> CHECKED_LOCATIONS = MEMORY_MODULE_TYPES.register("checked_locations", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<com.mojang.datafixers.util.Pair<BlockPos, BlockState>>> LOCATION_OF_INTEREST = MEMORY_MODULE_TYPES.register("location_of_interest", () -> new MemoryModuleType<>(Optional.empty()));

    public static final RegistryObject<MemoryModuleType<Integer>> RNG_COMPARITOR_1 = MEMORY_MODULE_TYPES.register("rng_1", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final RegistryObject<MemoryModuleType<Integer>> RNG_COMPARITOR_CHECK = MEMORY_MODULE_TYPES.register("rng_1_check", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

    public static final RegistryObject<MemoryModuleType<List<Pair<BlockPos, BlockState>>>> NEARBY_BLOCKS_TWO = MEMORY_MODULE_TYPES.register("nearby_blocks_two", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Boolean>> RETALIATECOOLDOWN = MEMORY_MODULE_TYPES.register("retaliate_cooldown", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Boolean>> RUSHING = MEMORY_MODULE_TYPES.register("rushing", () -> new MemoryModuleType<>(Optional.empty()));

    public static final RegistryObject<MemoryModuleType<Vec3>> TARGET_LONGJUMP_LOCATION = MEMORY_MODULE_TYPES.register("target_longjump_location", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Boolean>> NOANTLIONATTACK = MEMORY_MODULE_TYPES.register("noantlionattack", () -> new MemoryModuleType<>(Optional.empty()));




    public static void register(IEventBus eventBus) {
        MEMORY_MODULE_TYPES.register(eventBus);
    }
}

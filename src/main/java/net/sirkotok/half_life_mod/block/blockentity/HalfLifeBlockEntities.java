package net.sirkotok.half_life_mod.block.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.HalfLifeBlocks;
import net.sirkotok.half_life_mod.block.blockentity.custom.VoltigoreEggBlockEntity;


public class HalfLifeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, HalfLifeMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<VoltigoreEggBlockEntity>> VOLTIGORE_EGG_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("voltigore_egg_block_entity", () ->
                    BlockEntityType.Builder.of(VoltigoreEggBlockEntity::new,
                            HalfLifeBlocks.VOLTIGORE_EGG.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
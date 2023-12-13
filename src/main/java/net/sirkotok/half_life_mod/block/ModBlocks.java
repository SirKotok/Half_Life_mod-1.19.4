package net.sirkotok.half_life_mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.custom.VoltigoreEggBlock;
import net.sirkotok.half_life_mod.block.custom.portal.BasicPortalBlock;
import net.sirkotok.half_life_mod.item.ModItems;


import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, HalfLifeMod.MOD_ID);

    public static final RegistryObject<Block> HALF_LIFE_PORTAL = registerBlock("half_life_portal",
            () -> new BasicPortalBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).noLootTable()));


    public static final RegistryObject<Block> VOLTIGORE_NEST = registerBlock("voltigore_nest",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> VOLTIGORE_EGG = BLOCKS.register("voltigore_egg_block",
            () -> new VoltigoreEggBlock(BlockBehaviour.Properties.of(Material.EGG).noOcclusion().noParticlesOnBreak().sound(SoundType.METAL)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}


// REMEMBER TO REGISTERBLOCK not BLOCKS.REGISTER FOR NORMAL BLOCKS. IT TOOK ME 5 HOURS TO UNDERSTAND WHY THE GAME KEEPS CRASHING, I HATE MY LIFE
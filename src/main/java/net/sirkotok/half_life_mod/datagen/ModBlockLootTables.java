package net.sirkotok.half_life_mod.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.block.HalfLifeBlocks;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(HalfLifeBlocks.VOLTIGORE_NEST.get());
        dropWhenSilkTouch(HalfLifeBlocks.VOLTIGORE_EGG.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return HalfLifeBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
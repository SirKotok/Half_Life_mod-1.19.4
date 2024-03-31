package net.sirkotok.half_life_mod.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.misc.util.HLTags;

public class HLCarvers {
    public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE_XEN = createKey("cave_xen");
    public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE_EXTRA_UNDERGROUND_XEN = createKey("cave_extra_underground_xen");
    public static final ResourceKey<ConfiguredWorldCarver<?>> CANYON_XEN = createKey("canyon_xen");
    public static final ResourceKey<ConfiguredWorldCarver<?>> NETHER_CAVE_XEN = createKey("nether_cave_xen");

    private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String pName) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(HalfLifeMod.MOD_ID, pName));
    }

    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> pContext) {
        HolderGetter<Block> holdergetter = pContext.lookup(Registries.BLOCK);
        pContext.register(CAVE_XEN, WorldCarver.CAVE.configured(new CaveCarverConfiguration(0.15F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), holdergetter.getOrThrow(HLTags.Blocks.XEN_CARVER_REPLACABLE), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
        pContext.register(CAVE_EXTRA_UNDERGROUND_XEN, WorldCarver.CAVE.configured(new CaveCarverConfiguration(0.07F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(47)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.OAK_BUTTON.defaultBlockState()), holdergetter.getOrThrow(HLTags.Blocks.XEN_CARVER_REPLACABLE), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
        pContext.register(CANYON_XEN, WorldCarver.CANYON.configured(new CanyonCarverConfiguration(0.01F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(67)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), holdergetter.getOrThrow(HLTags.Blocks.XEN_CARVER_REPLACABLE), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
        pContext.register(NETHER_CAVE_XEN, WorldCarver.NETHER_CAVE.configured(new CaveCarverConfiguration(0.2F, UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.belowTop(1)), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(10), holdergetter.getOrThrow(HLTags.Blocks.XEN_CARVER_REPLACABLE), ConstantFloat.of(1.0F), ConstantFloat.of(1.0F), ConstantFloat.of(-0.7F))));
    }
}
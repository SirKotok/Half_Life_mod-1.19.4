package net.sirkotok.half_life_mod.worldgen.noise;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class HalfLifeNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> XEN_NS = createKey("xen_ns");

    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(HalfLifeMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
        context.register(XEN_NS, HalfLifeNoiseBuilders.XenNoiseSettings(densityFunctions, noise));
    }
}

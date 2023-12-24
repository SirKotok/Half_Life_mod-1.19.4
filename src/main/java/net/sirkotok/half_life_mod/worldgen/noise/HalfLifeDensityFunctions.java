package net.sirkotok.half_life_mod.worldgen.noise;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class HalfLifeDensityFunctions {
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE_HALFLIFE = createKey("base_3d_noise_halflife");

    public static final ResourceKey<DensityFunction> PIT_3D_NOISE = createKey("pit_3d_noise");

    public static final ResourceKey<DensityFunction> LARGE_NOISE = createKey("large_noise");

    public static final ResourceKey<DensityFunction> SECOND_NOISE = createKey("second_noise");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        context.register(BASE_3D_NOISE_HALFLIFE, BlendedNoise.createUnseeded(
                0.20, // xz scale
                0.20, // y scale
                77.0, // xz factor
                150.0, // y factor
                8)); // smear scale multiplier, capped at 8

        context.register(SECOND_NOISE, BlendedNoise.createUnseeded(
                0.20, // xz scale
                0.20, // y scale
                77.0, // xz factor
                150.0, // y factor
                8)); // smear scale multiplier, capped at 8


        context.register(LARGE_NOISE, BlendedNoise.createUnseeded(
                1, // xz scale
                0.20, // y scale
                77.0, // xz factor
                100, // y factor
                8)); // smear scale multiplier, capped at 8


            context.register(PIT_3D_NOISE, BlendedNoise.createUnseeded(
            0.31, // xz scale
            0.05, // y scale
            120.0, // xz factor
            180.0, // y factor
            7)); // smear scale multiplier, capped at 8
}


}
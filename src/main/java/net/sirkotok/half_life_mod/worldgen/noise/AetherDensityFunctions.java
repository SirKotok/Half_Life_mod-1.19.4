package net.sirkotok.half_life_mod.worldgen.noise;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class AetherDensityFunctions {
    public static final ResourceKey<DensityFunction> BASE_3D_NOISE_AETHER = createKey("base_3d_noise_aether");

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<DensityFunction> context) {
        context.register(BASE_3D_NOISE_AETHER, BlendedNoise.createUnseeded(
                0.27, // xz scale
                0.27, // y scale
                87.0, // xz factor
                166.0, // y factor
                8.0)); // smear scale multiplier, capped at 8
    }
}
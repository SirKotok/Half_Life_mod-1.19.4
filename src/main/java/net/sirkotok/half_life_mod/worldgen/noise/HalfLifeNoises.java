package net.sirkotok.half_life_mod.worldgen.noise;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.sirkotok.half_life_mod.HalfLifeMod;

public class HalfLifeNoises {

    //  public static final ResourceKey<NoiseParameters> TEMPERATURE = createKey("temperature");


    private static ResourceKey<NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(HalfLifeMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<NoiseParameters> context) {
        //      register(context, TEMPERATURE, -8, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0);

    }

    public static void register(BootstapContext<NoiseParameters> context, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        context.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}

package net.sirkotok.half_life_mod.worldgen.noise;


import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.HalfLifeBlocks;

import java.util.List;

public class HalfLifeNoiseBuilders {

    public static final float GLOBAL_OFFSET = -0.50375F;
    private static final float ORE_THICKNESS = 0.08F;
    private static final double VEININESS_FREQUENCY = 1.5D;
    private static final double NOODLE_SPACING_AND_STRAIGHTNESS = 1.5D;
    private static final double SURFACE_DENSITY_THRESHOLD = 1.5625D;
    private static final double CHEESE_NOISE_TARGET = -0.703125D;
    public static final int ISLAND_CHUNK_DISTANCE = 64;
    public static final long ISLAND_CHUNK_DISTANCE_SQR = 4096L;
    private static final DensityFunction BLENDING_FACTOR = DensityFunctions.constant(10.0D);
    private static final DensityFunction BLENDING_JAGGEDNESS = DensityFunctions.zero();
    private static final ResourceKey<DensityFunction> ZERO = createKey("zero");
    private static final ResourceKey<DensityFunction> Y = createKey("y");
    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_OVERWORLD = createKey("overworld/base_3d_noise");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_END = createKey("end/base_3d_noise");
    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("overworld/continents");
    public static final ResourceKey<DensityFunction> EROSION = createKey("overworld/erosion");
    public static final ResourceKey<DensityFunction> RIDGES = createKey("overworld/ridges");
    public static final ResourceKey<DensityFunction> RIDGES_FOLDED = createKey("overworld/ridges_folded");
    public static final ResourceKey<DensityFunction> OFFSET = createKey("overworld/offset");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("overworld/factor");
    public static final ResourceKey<DensityFunction> JAGGEDNESS = createKey("overworld/jaggedness");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("overworld/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
    public static final ResourceKey<DensityFunction> CONTINENTS_LARGE = createKey("overworld_large_biomes/continents");
    public static final ResourceKey<DensityFunction> EROSION_LARGE = createKey("overworld_large_biomes/erosion");
    private static final ResourceKey<DensityFunction> OFFSET_LARGE = createKey("overworld_large_biomes/offset");
    private static final ResourceKey<DensityFunction> FACTOR_LARGE = createKey("overworld_large_biomes/factor");
    private static final ResourceKey<DensityFunction> JAGGEDNESS_LARGE = createKey("overworld_large_biomes/jaggedness");
    private static final ResourceKey<DensityFunction> DEPTH_LARGE = createKey("overworld_large_biomes/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_LARGE = createKey("overworld_large_biomes/sloped_cheese");
    private static final ResourceKey<DensityFunction> OFFSET_AMPLIFIED = createKey("overworld_amplified/offset");
    private static final ResourceKey<DensityFunction> FACTOR_AMPLIFIED = createKey("overworld_amplified/factor");
    private static final ResourceKey<DensityFunction> JAGGEDNESS_AMPLIFIED = createKey("overworld_amplified/jaggedness");
    private static final ResourceKey<DensityFunction> DEPTH_AMPLIFIED = createKey("overworld_amplified/depth");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_AMPLIFIED = createKey("overworld_amplified/sloped_cheese");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE_END = createKey("end/sloped_cheese");
    private static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = createKey("overworld/caves/spaghetti_roughness_function");
    private static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
    private static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
    private static final ResourceKey<DensityFunction> PILLARS = createKey("overworld/caves/pillars");
    private static final ResourceKey<DensityFunction> SPAGHETTI_2D_THICKNESS_MODULATOR = createKey("overworld/caves/spaghetti_2d_thickness_modulator");
    private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");
    private static ResourceKey<DensityFunction> createKey(String pLocation) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(pLocation));
    }




    private static final SurfaceRules.RuleSource GRASS_BLOCK = SurfaceRules.state(HalfLifeBlocks.XEN_GROUND.get().defaultBlockState());
    private static final SurfaceRules.RuleSource DIRT = SurfaceRules.state(HalfLifeBlocks.XEN_ROCK.get().defaultBlockState());

    public static NoiseGeneratorSettings XenNoiseSettings(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        BlockState standartblock = HalfLifeBlocks.XEN_ROCK.get().defaultBlockState();
        return new NoiseGeneratorSettings(
                new NoiseSettings(16, 256, 2, 1), // noiseSettings
                standartblock, // defaultBlock
                Blocks.WATER.defaultBlockState(), // defaultFluid
                makeNoiseRouter(densityFunctions, noise), // noiseRouter
                XenSurfaceRules(), // surfaceRule
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                true, // aquifersEnabled
                true, // oreVeinsEnabled
                false  // useLegacyRandomSource
        );
    }

    public static SurfaceRules.RuleSource XenSurfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT);
        return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT));
    }

    private static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        return createNoiseRouter(densityFunctions, noise, buildFinalDensity(densityFunctions, noise));
    }

    private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        DensityFunction density = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID,"second_noise")));
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));
        density = slide(density, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.05));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density);
        DensityFunction densitylarge = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID,"large_noise")));
        densitylarge = DensityFunctions.cache2d(densitylarge);
        densitylarge = densitylarge.clamp(-0.01, 1);


        density = density.squeeze();
        densitylarge = densitylarge.squeeze();

        density = DensityFunctions.min(density, densitylarge);

        DensityFunction densitysub = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID,"pit_3d_noise")));

        densitysub = slide(densitysub, 0, 200, 72, 0, -0.2, 8, 40, -0.1);
        densitysub = DensityFunctions.add(densitysub, DensityFunctions.constant(-0.05));
        densitysub = DensityFunctions.blendDensity(densitysub);
        densitysub = DensityFunctions.interpolated(densitysub);

        DensityFunction shiftX = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
        DensityFunction shiftZ = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));

        DensityFunction function = DensityFunctions.shiftedNoise2d(shiftZ, shiftX, 0.3, noise.getOrThrow(Noises.EROSION));

        DensityFunction densityfunction4 = getFunction(densityFunctions, SHIFT_X);
        DensityFunction densityfunction5 = getFunction(densityFunctions, SHIFT_Z);
        DensityFunction continentalness = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, noise.getOrThrow(Noises.CONTINENTALNESS));

        DensityFunction densitybig = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID,"pit_3d_noise")));

        densitybig = DensityFunctions.min(continentalness, densitybig);

        densitybig = DensityFunctions.blendDensity(densitybig);
        densitybig = DensityFunctions.interpolated(densitybig);
        densitybig = densitybig.squeeze();

        DensityFunction noodle = getFunction(densityFunctions, NOODLE);


        density = density.squeeze();
        densitysub = densitysub.squeeze();

        DensityFunction sub2 = densitysub.halfNegative().cube().squeeze();
        sub2 = DensityFunctions.interpolated(sub2);

        density = DensityFunctions.add(density, sub2);

        function = DensityFunctions.add(function, densitybig);
        function = DensityFunctions.add(function, noodle);

        DensityFunction density2 = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID,"base_3d_noise_halflife")));

        density2 = DensityFunctions.add(density2, DensityFunctions.constant(-0.13));
        density2 = slide(density2, 0, 200, 72, 0, -0.2, 8, 40, -0.1);
        density2 = DensityFunctions.add(density2, DensityFunctions.constant(-0.05));
        density2 = DensityFunctions.blendDensity(density2);
        density2 = DensityFunctions.interpolated(density2);




        density2 = DensityFunctions.add(density2, densitysub);

        function = DensityFunctions.add(density, function);

        density = DensityFunctions.min(density2, function);



         DensityFunction densityfunction11 = getFunction(densityFunctions, SLOPED_CHEESE);
        DensityFunction densityfunction13 = underground(densityFunctions, noise, densityfunction11);
        DensityFunction densityfunction14 = DensityFunctions.min(densityfunction13, getFunction(densityFunctions, NOODLE));



        DensityFunction result = DensityFunctions.min(density, densityfunction14);


        return result;



    }



    private static DensityFunction underground(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, DensityFunction p_256658_) {
        DensityFunction densityfunction = getFunction(pDensityFunctions, SPAGHETTI_2D);
        DensityFunction densityfunction1 = getFunction(pDensityFunctions, SPAGHETTI_ROUGHNESS_FUNCTION);
        DensityFunction densityfunction2 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.CAVE_LAYER), 8.0D);
        DensityFunction densityfunction3 = DensityFunctions.mul(DensityFunctions.constant(4.0D), densityfunction2.square());
        DensityFunction densityfunction4 = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666D);
        DensityFunction densityfunction5 = DensityFunctions.add(DensityFunctions.add(DensityFunctions.constant(0.27D), densityfunction4).clamp(-1.0D, 1.0D), DensityFunctions.add(DensityFunctions.constant(1.5D), DensityFunctions.mul(DensityFunctions.constant(-0.64D), p_256658_)).clamp(0.0D, 0.5D));
        DensityFunction densityfunction6 = DensityFunctions.add(densityfunction3, densityfunction5);
        DensityFunction densityfunction7 = DensityFunctions.min(densityfunction6, DensityFunctions.add(densityfunction, densityfunction1));
       DensityFunction densityfunction8 = getFunction(pDensityFunctions, PILLARS);
       DensityFunction densityfunction9 = DensityFunctions.rangeChoice(densityfunction8, -1000000.0D, 0.03D, DensityFunctions.constant(-1000000.0D), densityfunction8);
        return   DensityFunctions.max(densityfunction7, densityfunction9);
    }




    private static DensityFunction slide(DensityFunction density, int minY, int maxY, int fromYTop, int toYTop, double offset1, int fromYBottom, int toYBottom, double offset2) {
        DensityFunction topSlide = DensityFunctions.yClampedGradient(minY + maxY - fromYTop, minY + maxY - toYTop, 1, 0);
        density = DensityFunctions.lerp(topSlide, offset1, density);
        DensityFunction bottomSlide = DensityFunctions.yClampedGradient(minY + fromYBottom, minY + toYBottom, 0, 1);
        return DensityFunctions.lerp(bottomSlide, offset2, density);
    }


    private static NoiseRouter createNoiseRouter(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, DensityFunction finalDensity) {


        DensityFunction densityfunction4 = getFunction(pDensityFunctions, SHIFT_X);
        DensityFunction densityfunction5 = getFunction(pDensityFunctions, SHIFT_Z);
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(Noises.VEGETATION));

        DensityFunction depth = getFunction(pDensityFunctions, DEPTH);
        DensityFunction erosion = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(Noises.EROSION));
        DensityFunction continentalness = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(Noises.CONTINENTALNESS));
        DensityFunction ridges = DensityFunctions.shiftedNoise2d(densityfunction4, densityfunction5, 0.25D, pNoiseParameters.getOrThrow(Noises.RIDGE));


        return new NoiseRouter(
                DensityFunctions.zero(), // barrier noise
                DensityFunctions.constant(1), // fluid level floodedness noise
                DensityFunctions.constant(1), // fluid level spread noise
                DensityFunctions.zero(), // lava noise
                temperature, // temperature
                vegetation, // vegetation
                continentalness, // continentalness noise
                erosion, // erosion noise
                depth, // depth
                ridges, // ridges
                DensityFunctions.zero(), // initial density without jaggedness, not sure if this is needed. Some vanilla dimensions use this while others don't.
                finalDensity, // finaldensity
                DensityFunctions.zero(), // veinToggle
                DensityFunctions.zero(), // veinRidged
                DensityFunctions.zero()); // veinGap
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }
}



/* Very good generation, but I will see if I can make it better

 private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> densityFunctions) {
        DensityFunction density = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID,"base_3d_noise_halflife")));
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));
        density = slide(density, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.05));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density);
        DensityFunction densitysub = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(HalfLifeMod.MOD_ID,"pit_3d_noise")));

        densitysub = slide(densitysub, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
        densitysub = DensityFunctions.add(densitysub, DensityFunctions.constant(-0.05));
        densitysub = DensityFunctions.blendDensity(densitysub);
        densitysub = DensityFunctions.interpolated(density);

        density = density.squeeze();
        densitysub = densitysub.squeeze();
        density = DensityFunctions.add(density, densitysub);

        return density;
    }


 */
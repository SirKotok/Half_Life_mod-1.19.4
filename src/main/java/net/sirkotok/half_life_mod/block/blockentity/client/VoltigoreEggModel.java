package net.sirkotok.half_life_mod.block.blockentity.client;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.blockentity.custom.VoltigoreEggBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class VoltigoreEggModel extends GeoModel<VoltigoreEggBlockEntity> {
    @Override
    public ResourceLocation getModelResource(VoltigoreEggBlockEntity animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/voltigore_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VoltigoreEggBlockEntity animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/block/voltigore_egg.png");
    }

    @Override
    public ResourceLocation getAnimationResource(VoltigoreEggBlockEntity animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/voltigore_egg.animation.json");
    }
}

package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.fake.FakeBulletItem;
import software.bernie.geckolib.model.GeoModel;

public class FakeBulletItemModel extends GeoModel<FakeBulletItem> {

    @Override
    public ResourceLocation getModelResource(FakeBulletItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/bullet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FakeBulletItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/bullet.png");
    //    return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/notexture.png");
    }


    @Override
    public ResourceLocation getAnimationResource(FakeBulletItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/chumtoad.animation.json");
    }
}

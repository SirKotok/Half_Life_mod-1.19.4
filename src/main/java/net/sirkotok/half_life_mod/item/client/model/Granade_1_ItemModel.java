package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.projectile.Granade;
import net.sirkotok.half_life_mod.item.custom.Granade_hl1;
import net.sirkotok.half_life_mod.item.custom.gun.Shotgun_1_Item;
import software.bernie.geckolib.model.GeoModel;

public class Granade_1_ItemModel extends GeoModel<Granade_hl1> {
    @Override
    public ResourceLocation getModelResource(Granade_hl1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/granade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Granade_hl1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/item/granade.png");
    }


    @Override
    public ResourceLocation getAnimationResource(Granade_hl1 animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/controller.animation.json");
    }


}



package net.sirkotok.half_life_mod.item.client.model;

import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import net.sirkotok.half_life_mod.item.custom.armor.SecurityGuardArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class Security_Guard_Armor_Model extends GeoModel<SecurityGuardArmorItem> {

    @Override
    public ResourceLocation getModelResource(SecurityGuardArmorItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "geo/security_guard_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SecurityGuardArmorItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/armor/security_guard_armor.png");
    }


    @Override
    public ResourceLocation getAnimationResource(SecurityGuardArmorItem animatable) {
        return new ResourceLocation(HalfLifeMod.MOD_ID, "animations/chumtoad.animation.json");
    }
}

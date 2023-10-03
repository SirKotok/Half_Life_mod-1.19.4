package net.sirkotok.half_life_mod.item.client.renderer;

import net.sirkotok.half_life_mod.item.client.model.ChumtoadItemModel;
import net.sirkotok.half_life_mod.item.client.model.Security_Guard_Armor_Model;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import net.sirkotok.half_life_mod.item.custom.armor.SecurityGuardArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Security_Guard_Armor_Renderer extends GeoArmorRenderer<SecurityGuardArmorItem> {
    public Security_Guard_Armor_Renderer() {
        super(new Security_Guard_Armor_Model());
    }
}

package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Pistol_1_ItemModel;
import net.sirkotok.half_life_mod.item.client.model.SMG_1_ItemModel;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import net.sirkotok.half_life_mod.item.custom.gun.SMG_1_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SMG_1_ItemRenderer extends GeoItemRenderer<SMG_1_Item> {
    public SMG_1_ItemRenderer() {
        super(new SMG_1_ItemModel());
    }
}

package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Deagle_ItemModel;
import net.sirkotok.half_life_mod.item.client.model.SAW_ItemModel;
import net.sirkotok.half_life_mod.item.custom.gun.Deagle_Item;
import net.sirkotok.half_life_mod.item.custom.gun.SAW_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Deagle_ItemRenderer extends GeoItemRenderer<Deagle_Item> {
    public Deagle_ItemRenderer() {
        super(new Deagle_ItemModel());
    }
}

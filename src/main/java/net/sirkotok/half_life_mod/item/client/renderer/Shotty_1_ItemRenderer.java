package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Pistol_1_ItemModel;
import net.sirkotok.half_life_mod.item.client.model.Shotty_1_ItemModel;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import net.sirkotok.half_life_mod.item.custom.gun.Shotgun_1_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Shotty_1_ItemRenderer extends GeoItemRenderer<Shotgun_1_Item> {
    public Shotty_1_ItemRenderer() {
        super(new Shotty_1_ItemModel());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

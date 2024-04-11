package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Granade_1_ItemModel;
import net.sirkotok.half_life_mod.item.client.model.Shotty_1_ItemModel;
import net.sirkotok.half_life_mod.item.custom.Granade_hl1;
import net.sirkotok.half_life_mod.item.custom.gun.Shotgun_1_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Granade_1_ItemRenderer extends GeoItemRenderer<Granade_hl1> {
    public Granade_1_ItemRenderer() {
        super(new Granade_1_ItemModel());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

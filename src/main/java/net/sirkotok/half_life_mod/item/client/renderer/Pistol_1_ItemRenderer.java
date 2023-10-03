package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Pistol_1_ItemModel;
import net.sirkotok.half_life_mod.item.custom.gun.Pistol_1_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Pistol_1_ItemRenderer extends GeoItemRenderer<Pistol_1_Item> {
    public Pistol_1_ItemRenderer() {
        super(new Pistol_1_ItemModel());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

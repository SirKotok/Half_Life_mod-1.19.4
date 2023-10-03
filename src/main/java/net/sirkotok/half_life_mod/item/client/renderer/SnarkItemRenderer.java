package net.sirkotok.half_life_mod.item.client.renderer;

import net.sirkotok.half_life_mod.item.client.model.SnarkItemmodel;

import net.sirkotok.half_life_mod.item.custom.SnarkItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SnarkItemRenderer extends GeoItemRenderer<SnarkItem> {
    public SnarkItemRenderer() {
        super(new SnarkItemmodel());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

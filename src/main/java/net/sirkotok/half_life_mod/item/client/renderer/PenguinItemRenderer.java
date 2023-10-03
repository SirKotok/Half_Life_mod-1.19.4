package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.PenguinItemmodel;
import net.sirkotok.half_life_mod.item.custom.PenguinItem;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PenguinItemRenderer extends GeoItemRenderer<PenguinItem> {
    public PenguinItemRenderer() {
        super(new PenguinItemmodel());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

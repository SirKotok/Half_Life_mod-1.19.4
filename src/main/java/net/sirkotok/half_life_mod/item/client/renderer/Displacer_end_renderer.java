package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Displacer_end_model;
import net.sirkotok.half_life_mod.item.client.model.Displacer_nether_model;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_end;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_nether;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Displacer_end_renderer extends GeoItemRenderer<Displacer_end> {
    public Displacer_end_renderer() {
        super(new Displacer_end_model());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

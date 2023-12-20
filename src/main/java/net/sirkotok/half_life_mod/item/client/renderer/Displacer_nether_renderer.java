package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Displacer_gun_model;
import net.sirkotok.half_life_mod.item.client.model.Displacer_nether_model;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_cannon;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_nether;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Displacer_nether_renderer extends GeoItemRenderer<Displacer_nether> {
    public Displacer_nether_renderer() {
        super(new Displacer_nether_model());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

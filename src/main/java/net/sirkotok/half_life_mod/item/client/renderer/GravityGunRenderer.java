package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.GravityGun_model;
import net.sirkotok.half_life_mod.item.custom.GravityGun;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GravityGunRenderer extends GeoItemRenderer<GravityGun> {
    public GravityGunRenderer() {
        super(new GravityGun_model());
       // addRenderLayer(new AutoGlowingGeoLayer<>(this)); //this makes the eye float around the snark so it doesnt work
    }
}

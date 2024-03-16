package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.GravityGun_model;
import net.sirkotok.half_life_mod.item.custom.gun.GravityGun;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GravityGunRenderer<T extends GravityGun> extends GeoItemRenderer<T> {
    public GravityGunRenderer() {
        super(new GravityGun_model());
    }
}

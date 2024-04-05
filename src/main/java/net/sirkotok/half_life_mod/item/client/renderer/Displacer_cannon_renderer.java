package net.sirkotok.half_life_mod.item.client.renderer;


import net.sirkotok.half_life_mod.item.client.model.Displacer_gun_model;
import net.sirkotok.half_life_mod.item.client.model.SMG_1_ItemModel;
import net.sirkotok.half_life_mod.item.custom.gun.Displacer_cannon;
import net.sirkotok.half_life_mod.item.custom.gun.SMG_1_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Displacer_cannon_renderer extends GeoItemRenderer<Displacer_cannon> {
    public Displacer_cannon_renderer() {
        super(new Displacer_gun_model());
    }
}

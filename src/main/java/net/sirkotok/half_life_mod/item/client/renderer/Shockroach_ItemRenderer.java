package net.sirkotok.half_life_mod.item.client.renderer;

import net.minecraft.world.item.SaddleItem;
import net.sirkotok.half_life_mod.item.client.model.ChumtoadItemModel;
import net.sirkotok.half_life_mod.item.client.model.Shockroach_Itemmodel;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import net.sirkotok.half_life_mod.item.custom.gun.Shockroach_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class Shockroach_ItemRenderer extends GeoItemRenderer<Shockroach_Item> {
    public Shockroach_ItemRenderer() {
        super(new Shockroach_Itemmodel());
    }
}

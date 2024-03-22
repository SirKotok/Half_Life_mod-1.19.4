package net.sirkotok.half_life_mod.item.client.renderer;

import net.sirkotok.half_life_mod.item.client.model.HiveHand_Itemmodel;
import net.sirkotok.half_life_mod.item.client.model.Shockroach_Itemmodel;
import net.sirkotok.half_life_mod.item.custom.gun.Hivehand_Item;
import net.sirkotok.half_life_mod.item.custom.gun.Shockroach_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class HivehandRenderer extends GeoItemRenderer<Hivehand_Item> {
    public HivehandRenderer() {
        super(new HiveHand_Itemmodel());
    }
}

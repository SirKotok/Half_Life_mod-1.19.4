package net.sirkotok.half_life_mod.item.client.renderer;

import net.sirkotok.half_life_mod.item.client.model.ChumtoadItemModel;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ChumtoadItemRenderer extends GeoItemRenderer<ChumtoadItem> {
    public ChumtoadItemRenderer() {
        super(new ChumtoadItemModel());
    }
}

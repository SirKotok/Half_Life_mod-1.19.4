package net.sirkotok.half_life_mod.item.client.renderer;

import net.sirkotok.half_life_mod.item.client.model.ChumtoadItemModel;
import net.sirkotok.half_life_mod.item.client.model.FakeBulletItemModel;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import net.sirkotok.half_life_mod.item.custom.FakeBulletItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FakeBulletItemRenderer extends GeoItemRenderer<FakeBulletItem> {
    public FakeBulletItemRenderer() {
        super(new FakeBulletItemModel());
    }
}

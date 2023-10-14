package net.sirkotok.half_life_mod.item.client.renderer;

import net.sirkotok.half_life_mod.item.client.model.FakeBulletItemModel;
import net.sirkotok.half_life_mod.item.custom.fake.FakeBulletItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FakeBulletItemRenderer extends GeoItemRenderer<FakeBulletItem> {
    public FakeBulletItemRenderer() {
        super(new FakeBulletItemModel());
    }
}

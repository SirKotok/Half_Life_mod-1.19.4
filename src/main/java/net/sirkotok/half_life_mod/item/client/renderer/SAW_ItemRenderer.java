package net.sirkotok.half_life_mod.item.client.renderer;


import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.client.model.SAW_ItemModel;
import net.sirkotok.half_life_mod.item.client.model.SMG_1_ItemModel;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import net.sirkotok.half_life_mod.item.custom.gun.SAW_Item;
import net.sirkotok.half_life_mod.item.custom.gun.SMG_1_Item;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SAW_ItemRenderer extends GeoItemRenderer<SAW_Item> {
    public SAW_ItemRenderer() {
        super(new SAW_ItemModel());
    }


    @Override
    public ResourceLocation getTextureLocation(SAW_Item animatable) {
        String s = SAW_Item.getAmmoString(currentItemStack);
        return new ResourceLocation(HalfLifeMod.MOD_ID,"textures/item/m249"+s+".png");
    }

}

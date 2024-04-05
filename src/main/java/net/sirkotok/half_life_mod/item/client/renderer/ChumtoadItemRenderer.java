package net.sirkotok.half_life_mod.item.client.renderer;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.client.model.Chumtoad6eyeItemModel;
import net.sirkotok.half_life_mod.item.client.model.ChumtoadItemModel;
import net.sirkotok.half_life_mod.item.custom.ChumtoadItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ChumtoadItemRenderer extends GeoItemRenderer<ChumtoadItem> {

    protected final GeoModel<ChumtoadItem> sixeyemodel;

    public ChumtoadItemRenderer() {
        super(new ChumtoadItemModel());
        this.sixeyemodel = new Chumtoad6eyeItemModel();
    }


    @Override
    public GeoModel<ChumtoadItem> getGeoModel() {
       if (currentItemStack.hasTag()) {
           CompoundTag tag = currentItemStack.getTag();
           boolean sixeye = tag.getBoolean("ChumtoadEye");
           if (sixeye) return sixeyemodel;
           else return model;
       } else return model;
    }

    @Override
    public ResourceLocation getTextureLocation(ChumtoadItem animatable) {
        if (currentItemStack.hasTag()) {
            CompoundTag tag = currentItemStack.getTag();
            boolean sixeye = tag.getBoolean("ChumtoadEye");
            int texture = tag.getInt("ChumtoadTexture");
            if (!sixeye) {switch (texture) {
                case 0: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadoriginal.png");
                case 1: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadpurple.png");
                case 2: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadblue.png");
                case 3: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadbrown.png");
                case 4: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadgreen.png");
                case 5: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoadconcept.png");
            }} else {
                switch (texture) {
                    case 0: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyeoriginal.png");
                    case 1: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyepurple.png");
                    case 2: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyeblue.png");
                    case 3: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyebrown.png");
                    case 4: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyegreen.png");
                    case 5: return new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/chumtoad6eyeconcept.png");
                }
            }
        }
        return super.getTextureLocation(animatable);
    }
}

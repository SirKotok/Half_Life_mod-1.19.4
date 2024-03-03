package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;



import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.VortHL1_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.VortHL2_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.VortigauntHL1;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.VortigauntHL2;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class VortHL2_renderer extends GeoEntityRenderer<VortigauntHL2> {
    public VortHL2_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VortHL2_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));

    }





    @Override
    public void render(VortigauntHL2 entityIn, float entityYaw,
                       float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLight) {
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLight);

        }




}


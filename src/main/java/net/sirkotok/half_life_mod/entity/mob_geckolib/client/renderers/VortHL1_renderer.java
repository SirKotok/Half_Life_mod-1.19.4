package net.sirkotok.half_life_mod.entity.mob_geckolib.client.renderers;



import com.github.alexthe666.citadel.client.render.LightningRender;
import com.github.alexthe666.citadel.client.render.LightningBoltData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.sirkotok.half_life_mod.entity.mob_geckolib.client.models.VortHL1_model;
import net.sirkotok.half_life_mod.entity.mob_geckolib.custom.VortigauntHL1;
import net.sirkotok.half_life_mod.util.HLperUtil;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.joml.Vector4f;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class VortHL1_renderer extends GeoEntityRenderer<VortigauntHL1> {
    public VortHL1_renderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VortHL1_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));

    }





    @Override
    public void render(VortigauntHL1 entityIn, float entityYaw,
                       float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferIn, int packedLight) {
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLight);

        }




}



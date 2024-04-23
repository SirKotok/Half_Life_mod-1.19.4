package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Archer_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Gonome_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Archer;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Gonome;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class Gonomerenderer extends GeoEntityRenderer<Gonome> {
    public Gonomerenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Gonome_model<>());
    }
}

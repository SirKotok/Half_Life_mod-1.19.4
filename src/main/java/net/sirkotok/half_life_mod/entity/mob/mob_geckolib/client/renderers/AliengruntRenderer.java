package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.AlientGrunt_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.models.Archer_model;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.AlienGrunt;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.Archer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;


public class AliengruntRenderer extends GeoEntityRenderer<AlienGrunt> {
    public AliengruntRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AlientGrunt_model());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}

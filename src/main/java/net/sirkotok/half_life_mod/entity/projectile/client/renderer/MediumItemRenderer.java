package net.sirkotok.half_life_mod.entity.projectile.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class MediumItemRenderer<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {
    public MediumItemRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, 2, true);
    }





}

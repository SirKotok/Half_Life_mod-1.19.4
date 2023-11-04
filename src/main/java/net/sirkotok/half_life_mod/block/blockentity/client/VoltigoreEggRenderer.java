package net.sirkotok.half_life_mod.block.blockentity.client;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.sirkotok.half_life_mod.block.blockentity.custom.VoltigoreEggBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class VoltigoreEggRenderer extends GeoBlockRenderer<VoltigoreEggBlockEntity> {
    public VoltigoreEggRenderer(BlockEntityRendererProvider.Context context) {
        super(new VoltigoreEggModel());
    }
}

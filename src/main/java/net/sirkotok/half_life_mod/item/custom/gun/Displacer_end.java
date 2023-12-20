package net.sirkotok.half_life_mod.item.custom.gun;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.sirkotok.half_life_mod.item.client.renderer.Displacer_cannon_renderer;
import net.sirkotok.half_life_mod.item.client.renderer.Displacer_end_renderer;
import net.sirkotok.half_life_mod.worldgen.portal.NetherTeleporter;
import net.sirkotok.half_life_mod.worldgen.portal.XenTeleporter;

import java.util.function.Consumer;

public class Displacer_end extends Displacer_cannon{
    public Displacer_end(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getDestination() {
        return 2;
    }
    @Override protected void handleHalfLifePortal(Entity player, BlockPos pPos) {
        if (player.level instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level.dimension() == Level.END ?
                    Level.OVERWORLD : Level.END;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == Level.END) {
                    player.changeDimension(portalDimension, new XenTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new XenTeleporter(pPos, false));
                }
            }
        }
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private Displacer_end_renderer renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    renderer = new Displacer_end_renderer();
                }
                return this.renderer;
            }
        });
    }


}

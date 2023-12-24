package net.sirkotok.half_life_mod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;
import net.sirkotok.half_life_mod.networking.HalfLifePackets;
import net.sirkotok.half_life_mod.networking.packet.GunLeftC2SPacket;
import net.sirkotok.half_life_mod.networking.packet.GunReloadC2SPacket;
import net.sirkotok.half_life_mod.networking.packet.GunRightC2SPacket;
import net.sirkotok.half_life_mod.util.KeyBinding;

@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {





    @SubscribeEvent
    public static void inputEvent(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isAttack() || event.isUseItem()) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null && localPlayer.getMainHandItem().getItem() instanceof GunItem) {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public static void onKeyinput(InputEvent.Key event ) {
        if (KeyBinding.RELOAD_KEY.consumeClick()) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null) {
                boolean holdingGun = localPlayer.getMainHandItem().getItem() instanceof GunItem;
                if (holdingGun) {
                    HalfLifePackets.sendToServer(new GunReloadC2SPacket());
                }
            }
        }
    }


    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null) {
                boolean leftMouse = Minecraft.getInstance().mouseHandler.isLeftPressed();
                boolean rightMouse = Minecraft.getInstance().mouseHandler.isRightPressed();
                boolean holdingGun = localPlayer.getMainHandItem().getItem() instanceof GunItem;
                if (holdingGun && leftMouse) {
                    HalfLifePackets.sendToServer(new GunLeftC2SPacket());
                }
                if (holdingGun && rightMouse) {
                    HalfLifePackets.sendToServer(new GunRightC2SPacket());
                }
            }
        }
    }

}
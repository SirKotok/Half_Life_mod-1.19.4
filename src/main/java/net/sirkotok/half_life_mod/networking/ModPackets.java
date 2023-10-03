package net.sirkotok.half_life_mod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.networking.packet.GunLeftC2SPacket;
import net.sirkotok.half_life_mod.networking.packet.GunReloadC2SPacket;
import net.sirkotok.half_life_mod.networking.packet.GunRightC2SPacket;

public class ModPackets     {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
        }


    public static void  register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(HalfLifeMod.MOD_ID, "messege"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;


        net.messageBuilder(GunLeftC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GunLeftC2SPacket::new)
                .encoder(GunLeftC2SPacket::toBytes)
                .consumerMainThread(GunLeftC2SPacket::handle)
                .add();
        net.messageBuilder(GunRightC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GunRightC2SPacket::new)
                .encoder(GunRightC2SPacket::toBytes)
                .consumerMainThread(GunRightC2SPacket::handle)
                .add();
        net.messageBuilder(GunReloadC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GunReloadC2SPacket::new)
                .encoder(GunReloadC2SPacket::toBytes)
                .consumerMainThread(GunReloadC2SPacket::handle)
                .add();
        }

        public static <MSG> void sendToServer(MSG messege) {
            INSTANCE.sendToServer(messege);
        }

        public static <MSG> void sendToPlayer(MSG messege, ServerPlayer player) {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), messege);
        }

}



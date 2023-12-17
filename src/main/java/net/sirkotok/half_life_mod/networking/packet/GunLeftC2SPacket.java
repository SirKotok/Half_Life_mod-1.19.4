package net.sirkotok.half_life_mod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.sirkotok.half_life_mod.item.custom.gun.base.GunItem;

import java.util.function.Supplier;

public class GunLeftC2SPacket {
    public GunLeftC2SPacket() {

    }

    public GunLeftC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            ItemStack item = player.getMainHandItem();
            if (item.getItem() instanceof GunItem) {
                ((GunItem) item.getItem()).leftuse(player.getLevel(), player, InteractionHand.MAIN_HAND);
            }
        });
        return true;
    }

}

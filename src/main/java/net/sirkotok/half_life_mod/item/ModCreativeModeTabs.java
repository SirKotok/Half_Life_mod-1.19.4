package net.sirkotok.half_life_mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;


@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab HALF_LIFE_SPAWN_EGGS;
    public static CreativeModeTab HALF_LIFE_WEOPONS_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
       HALF_LIFE_SPAWN_EGGS = event.registerCreativeModeTab(new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.SNARK_SPAWN_EGG.get()))
                       .title(Component.translatable("creativemodetab.half_life_tab")));

        HALF_LIFE_WEOPONS_TAB = event.registerCreativeModeTab(new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_weopon_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.PISTOL.get()))
                        .title(Component.translatable("creativemodetab.half_life_weopon_tab")));
    }

}

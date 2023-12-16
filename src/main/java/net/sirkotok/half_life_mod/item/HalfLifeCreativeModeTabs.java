package net.sirkotok.half_life_mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.HalfLifeBlocks;


@Mod.EventBusSubscriber(modid = HalfLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HalfLifeCreativeModeTabs {
    public static CreativeModeTab HALF_LIFE_SPAWN_EGGS;
    public static CreativeModeTab HALF_LIFE_WEOPONS_TAB;
    public static CreativeModeTab HALF_LIFE_BLOCKS_TAB;
    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
       HALF_LIFE_SPAWN_EGGS = event.registerCreativeModeTab(new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_tab"),
                builder -> builder.icon(() -> new ItemStack(HalfLifeItems.SNARK_SPAWN_EGG.get()))
                       .title(Component.translatable("creativemodetab.half_life_tab")));


        HALF_LIFE_BLOCKS_TAB = event.registerCreativeModeTab(new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_blocks_tab"),
                builder -> builder.icon(() -> new ItemStack(HalfLifeBlocks.VOLTIGORE_NEST.get().asItem()))
                        .title(Component.translatable("creativemodetab.half_life_blocks_tab")));

        HALF_LIFE_WEOPONS_TAB = event.registerCreativeModeTab(new ResourceLocation(HalfLifeMod.MOD_ID, "half_life_weopon_tab"),
                builder -> builder.icon(() -> new ItemStack(HalfLifeItems.PISTOL.get()))
                        .title(Component.translatable("creativemodetab.half_life_weopon_tab")));
    }

}

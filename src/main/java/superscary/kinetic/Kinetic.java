package superscary.kinetic;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.entity.KineticBlockEntities;
import superscary.kinetic.config.Config;
import superscary.kinetic.item.KineticItems;
import superscary.kinetic.network.ModMessages;
import superscary.kinetic.recipe.KineticRecipes;
import superscary.kinetic.gui.CompressorScreen;
import superscary.kinetic.gui.KineticMenus;
import superscary.kinetic.gui.SawmillScreen;

@Mod(Kinetic.MODID)
public class Kinetic
{
    public static final String MODID = "kinetic";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Kinetic ()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        KineticBlocks.BLOCKS.register(modEventBus);
        KineticItems.ITEMS.register(modEventBus);
        KineticBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        KineticTabs.TABS.register(modEventBus);
        KineticMenus.MENUS.register(modEventBus);
        KineticRecipes.SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup (final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModMessages::register);
    }

    @SubscribeEvent
    public void onServerStarting (ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber (modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup (FMLClientSetupEvent event)
        {
            MenuScreens.register(KineticMenus.COMPRESSOR_MENU.get(), CompressorScreen::new);
            MenuScreens.register(KineticMenus.SAWMILL_MENU.get(), SawmillScreen::new);
        }
    }
}

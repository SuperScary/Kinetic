package superscary.kinetic;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.cables.util.power.basic.BasicPowerCableModelLoader;
import superscary.kinetic.block.KineticBlockEntities;
import superscary.kinetic.block.cables.util.power.deluxe.DeluxePowerCableModelLoader;
import superscary.kinetic.block.cables.util.power.premium.PremiumPowerCableModelLoader;
import superscary.kinetic.block.cables.util.power.standard.StandardPowerCableModelLoader;
import superscary.kinetic.block.cables.util.power.ultimate.UltimatePowerCableModelLoader;
import superscary.kinetic.gui.KineticMenus;
import superscary.kinetic.gui.screen.*;
import superscary.kinetic.item.KineticItems;
import superscary.kinetic.network.ModMessages;
import superscary.kinetic.recipe.KineticRecipes;
import superscary.kinetic.util.FacadeBlockColor;

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
    }

    private void commonSetup (final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ModMessages::register);
    }

    @SubscribeEvent
    public void onServerStarting (ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup (FMLClientSetupEvent event)
        {
            MenuScreens.register(KineticMenus.COMPRESSOR_MENU.get(), CompressorScreen::new);
            MenuScreens.register(KineticMenus.SAWMILL_MENU.get(), SawmillScreen::new);
            MenuScreens.register(KineticMenus.COAL_GENERATOR_MENU.get(), CoalGeneratorScreen::new);
            MenuScreens.register(KineticMenus.CHARGER_MENU.get(), ChargerScreen::new);
            MenuScreens.register(KineticMenus.PRINTER_MENU.get(), PrinterScreen::new);
            MenuScreens.register(KineticMenus.INSCRIBER_MENU.get(), InscriberScreen::new);
        }

        @SubscribeEvent
        public static void modelInit (ModelEvent.RegisterGeometryLoaders event)
        {
            BasicPowerCableModelLoader.register(event);
            StandardPowerCableModelLoader.register(event);
            PremiumPowerCableModelLoader.register(event);
            DeluxePowerCableModelLoader.register(event);
            UltimatePowerCableModelLoader.register(event);
        }

        @SubscribeEvent
        public static void registerBlockColor (RegisterColorHandlersEvent.Block event)
        {
            event.register(new FacadeBlockColor(), KineticBlocks.FACADE_BLOCK.get());
        }

    }
}

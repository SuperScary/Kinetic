package superscary.kinetic.register;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import superscary.kinetic.block.cables.util.power.basic.BasicPowerCableModelLoader;
import superscary.kinetic.block.cables.util.power.deluxe.DeluxePowerCableModelLoader;
import superscary.kinetic.block.cables.util.power.premium.PremiumPowerCableModelLoader;
import superscary.kinetic.block.cables.util.power.standard.StandardPowerCableModelLoader;
import superscary.kinetic.block.cables.util.power.ultimate.UltimatePowerCableModelLoader;
import superscary.kinetic.gui.screen.*;
import superscary.kinetic.util.FacadeBlockColor;

import static superscary.kinetic.Kinetic.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistrar
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
        MenuScreens.register(KineticMenus.BASIC_BATTERY_MENU.get(), BasicBatteryScreen::new);
        MenuScreens.register(KineticMenus.DRAFTING_TABLE_MENU.get(), DraftingTableScreen::new);
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

package superscary.kinetic;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import superscary.kinetic.fluid.KineticFluidTypes;
import superscary.kinetic.fluid.KineticFluids;
import superscary.kinetic.register.*;
import superscary.kinetic.network.ModMessages;
import superscary.kinetic.register.KineticTrunkPlacerTypes;

import java.util.Random;

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
        KineticEntities.POI_TYPES.register(modEventBus);
        KineticEntities.VILLAGER_PROFESSIONS.register(modEventBus);
        KineticTrunkPlacerTypes.TRUNK_PLACERS.register(modEventBus);
        KineticFoliagePlacerTypes.FOLIAGE_PLACERS.register(modEventBus);
        KineticFluidTypes.FLUID_TYPES.register(modEventBus);
        KineticFluids.FLUIDS.register(modEventBus);

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

    public static ResourceLocation getResource (String name)
    {
        return new ResourceLocation(MODID, name);
    }
    public static ResourceLocation getMinecraftResource (String name)
    {
        return new ResourceLocation(name);
    }

    public static Random getRandom ()
    {
        return new Random();
    }

}

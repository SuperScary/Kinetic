package superscary.kinetic;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticBlockEntities;
import superscary.kinetic.register.KineticMenus;
import superscary.kinetic.register.KineticItems;
import superscary.kinetic.network.ModMessages;
import superscary.kinetic.register.KineticRecipes;

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

    public static ResourceLocation getResource (String name)
    {
        return new ResourceLocation(MODID, name);
    }

}

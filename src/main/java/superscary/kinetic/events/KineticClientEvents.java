package superscary.kinetic.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlockEntities;
import superscary.kinetic.renderer.DraftingTableRenderer;

@Mod.EventBusSubscriber(modid = Kinetic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KineticClientEvents
{

    @SubscribeEvent
    public static void registerRenderer (EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(KineticBlockEntities.DRAFTING_TABLE_BE.get(), DraftingTableRenderer::new);
    }

}

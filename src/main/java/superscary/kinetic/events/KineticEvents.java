package superscary.kinetic.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticEntities;
import superscary.kinetic.register.KineticItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = Kinetic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KineticEvents
{

    @SubscribeEvent
    public static void addCustomTrades (VillagerTradesEvent event)
    {
        if (event.getType() == KineticEntities.ENGINEER.get())
        {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.EMERALD, Kinetic.getRandom().nextInt(10,22)),
                    new ItemStack(KineticItems.BLUEPRINT.get(), 1), 10, 2, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.EMERALD, Kinetic.getRandom().nextInt(15,19)),
                    new ItemStack(KineticItems.SD_CARD.get(), 1), 10, 2, 0.02f));

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(KineticItems.BLUEPRINT.get(), 1),
                    new ItemStack(Items.EMERALD, Kinetic.getRandom().nextInt(1, 4)), 5, 2, 0.02f));

        }

        if (event.getType() == KineticEntities.TINKERER.get())
        {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.EMERALD, Kinetic.getRandom().nextInt(4,11)),
                    new ItemStack(KineticItems.WRENCH.get(), 1), 3, 2, 0.02f));
        }

    }

}

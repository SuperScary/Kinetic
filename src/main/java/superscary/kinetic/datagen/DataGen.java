package superscary.kinetic.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superscary.kinetic.Kinetic;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Kinetic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen
{

    @SubscribeEvent
    public static void gather (GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), KineticLootTableProvider.create(packOutput));
        generator.addProvider(event.includeClient(), new KineticPoiTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeClient(), new KineticBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeClient(), new KineticItemModelProvider(packOutput, existingFileHelper));
    }

}

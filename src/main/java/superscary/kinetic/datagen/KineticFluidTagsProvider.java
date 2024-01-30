package superscary.kinetic.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import superscary.kinetic.Kinetic;
import superscary.kinetic.fluid.KineticFluids;

import java.util.concurrent.CompletableFuture;

public class KineticFluidTagsProvider extends FluidTagsProvider
{

    public KineticFluidTagsProvider (PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, ExistingFileHelper existingFileHelper)
    {
        super(pOutput, pProvider, Kinetic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.Provider pProvider)
    {
        this.tag(FluidTags.WATER)
                .add(KineticFluids.OIL_SOURCE.get())
                .add(KineticFluids.OIL_FLOWING.get());
    }
}

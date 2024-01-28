package superscary.kinetic.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.Kinetic;

import java.util.concurrent.CompletableFuture;

public class KineticPoiTypeTagsProvider extends PoiTypeTagsProvider
{


    public KineticPoiTypeTagsProvider (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(packOutput, future, Kinetic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.Provider pProvider)
    {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).addOptional(Kinetic.getResource("engineer_poi"));
    }
}

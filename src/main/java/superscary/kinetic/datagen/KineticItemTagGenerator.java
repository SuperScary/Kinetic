package superscary.kinetic.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;

import java.util.concurrent.CompletableFuture;

public class KineticItemTagGenerator extends ItemTagsProvider
{


    public KineticItemTagGenerator (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(packOutput, future, completableFuture, Kinetic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.Provider provider)
    {
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(KineticBlocks.RUBBER_LOG.get().asItem())
                .add(KineticBlocks.RUBBER_WOOD.get().asItem())
                .add(KineticBlocks.STRIPPED_RUBBER_LOG.get().asItem())
                .add(KineticBlocks.STRIPPED_RUBBER_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(KineticBlocks.RUBBER_PLANKS.get().asItem());
    }
}

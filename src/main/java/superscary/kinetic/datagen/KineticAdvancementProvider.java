package superscary.kinetic.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import superscary.kinetic.Kinetic;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticItems;

import java.util.function.Consumer;

public class KineticAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator
{
    @Override
    public void generate (HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper)
    {
        Advancement root = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(KineticItems.WRENCH.get()),
                        Component.translatable("advancement.build_wrench"), Component.translatable("advancement.build_wrench.desc"),
                        Kinetic.getResource("textures/block/steel_block.png"),
                        FrameType.TASK, true, true, false))
                .addCriterion("craft_wrench", InventoryChangeTrigger.TriggerInstance.hasItems(KineticItems.WRENCH.get()))
                .save(consumer, Kinetic.getResource("with_friends_like_these"), existingFileHelper);

        Advancement buildCompressor = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(KineticBlocks.COMPRESSOR.get()),
                        Component.translatable("advancement.build_compressor"), Component.translatable("advancement.build_compressor.desc"),
                        null, FrameType.TASK, true, true, false))
                .parent(root)
                .addCriterion("has_compressor", InventoryChangeTrigger.TriggerInstance.hasItems(KineticBlocks.COMPRESSOR.get()))
                .save(consumer, Kinetic.getResource("build_compressor"), existingFileHelper);

        Advancement compressPlastic = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(KineticItems.PLASTIC.get()),
                        Component.translatable("advancement.compress_plastic"), Component.translatable("advancement.compress_plastic.desc"),
                        null, FrameType.TASK, true, true, false))
                .parent(buildCompressor)
                .addCriterion("compressed", InventoryChangeTrigger.TriggerInstance.hasItems(KineticItems.PLASTIC.get()))
                .save(consumer, Kinetic.getResource("compress_plastic"), existingFileHelper);

    }
}

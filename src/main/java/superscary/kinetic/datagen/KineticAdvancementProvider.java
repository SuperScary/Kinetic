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
import superscary.kinetic.register.KineticItems;

import java.util.function.Consumer;

public class KineticAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator
{
    @Override
    public void generate (HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper)
    {
        Advancement withFriendsLikeThese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(KineticItems.WRENCH.get()),
                        Component.literal("With Friends Like These.."), Component.literal("Your New Best Friend"),
                        Kinetic.getResource("textures/block/steel_block.png"),
                        FrameType.TASK, true, true, false))
                .addCriterion("craft_wrench", InventoryChangeTrigger.TriggerInstance.hasItems(KineticItems.WRENCH.get()))
                .save(consumer, Kinetic.getResource("withfriendslikethese"), existingFileHelper);
    }
}

package superscary.kinetic;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class KineticTabs
{

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Kinetic.MODID);

    public static final List<Supplier<? extends ItemLike>> TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("kinetic_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.kinetic"))
                    .icon(() -> new ItemStack(KineticItems.WRENCH.get()))
                    .displayItems((displayParams, output) -> {
                        TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get()));
                    })
                    .build()
    );

    public static <T extends Item> RegistryObject<T> addItemToTab (RegistryObject<T> itemLike)
    {
        TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    public static <T extends Block> RegistryObject<T> addBlockToTab (RegistryObject<T> itemLike)
    {
        TAB_ITEMS.add(itemLike);
        return itemLike;
    }

}

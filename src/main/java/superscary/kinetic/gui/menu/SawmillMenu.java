package superscary.kinetic.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.entity.CompressorBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.gui.KineticMenus;

public class SawmillMenu extends KineticContainerMenu
{

    public final CompressorBlockEntity blockEntity;

    public SawmillMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public SawmillMenu (int containerId, Inventory inventory, BlockEntity entity, ContainerData data)
    {
        super(KineticMenus.SAWMILL_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = ((CompressorBlockEntity) entity);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 11));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 80, 59));
        });
    }

    @Override
    public int getContainerSize ()
    {
        return 2;
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.SAWMILL.get();
    }

}

package superscary.kinetic.gui.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import superscary.kinetic.block.entity.DraftingTableBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.gui.UpgradeSlot;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.register.KineticMenus;

public class DraftingTableMenu extends KineticContainerMenu
{

    public final DraftingTableBlockEntity blockEntity;

    public DraftingTableMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public DraftingTableMenu (int containerId, Inventory inventory, BlockEntity entity)
    {
        super(KineticMenus.DRAFTING_TABLE_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = (DraftingTableBlockEntity) entity;

        if (getLevel().getBlockEntity(entity.getBlockPos()) instanceof DraftingTableBlockEntity generator)
        {
            this.addSlot(new SlotItemHandler(generator.getItems(), 0, 43, 33));
        }

    }

    public DraftingTableBlockEntity getBlockEntity ()
    {
        return this.blockEntity;
    }

    @Override
    public int getContainerSize ()
    {
        return 7;
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.DRAFTING_TABLE.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {

    }
}

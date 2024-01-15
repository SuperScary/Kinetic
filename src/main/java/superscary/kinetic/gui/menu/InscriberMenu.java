package superscary.kinetic.gui.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.entity.InscriberBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.gui.KineticMenus;
import superscary.kinetic.gui.UpgradeSlot;

public class InscriberMenu extends KineticContainerMenu
{

    public final InscriberBlockEntity blockEntity;

    public InscriberMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public InscriberMenu (int containerId, Inventory inventory, BlockEntity entity)
    {
        super(KineticMenus.INSCRIBER_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = (InscriberBlockEntity) entity;

        if (getLevel().getBlockEntity(entity.getBlockPos()) instanceof InscriberBlockEntity inscriber)
        {
            this.addSlot(new SlotItemHandler(inscriber.getItems(), 0, 43, 33));
            this.addSlot(new SlotItemHandler(inscriber.getItems(), 1, 80, 33));
            this.addSlot(new SlotItemHandler(inscriber.getItems(), 2, 117, 33));
            this.addSlot(new UpgradeSlot(inscriber.getItems(), 3, 182, 5));
            this.addSlot(new UpgradeSlot(inscriber.getItems(), 4, 182, 23));
            this.addSlot(new UpgradeSlot(inscriber.getItems(), 5, 182, 41));
            this.addSlot(new UpgradeSlot(inscriber.getItems(), 6, 182, 59));
        }

    }

    public InscriberBlockEntity getBlockEntity ()
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
        return KineticBlocks.INSCRIBER_BLOCK.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {
        if (player.level().getBlockEntity(pos) instanceof InscriberBlockEntity inscriber) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return inscriber.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    InscriberMenu.this.power = (InscriberMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (inscriber.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    InscriberMenu.this.power = (InscriberMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
    }
}

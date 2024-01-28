package superscary.kinetic.gui.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.block.entity.PrinterBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.register.KineticMenus;
import superscary.kinetic.gui.UpgradeSlot;

public class PrinterMenu extends KineticContainerMenu
{

    public final PrinterBlockEntity blockEntity;

    public PrinterMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public PrinterMenu (int containerId, Inventory inventory, BlockEntity entity)
    {
        super(KineticMenus.PRINTER_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = (PrinterBlockEntity) entity;

        if (getLevel().getBlockEntity(entity.getBlockPos()) instanceof PrinterBlockEntity generator)
        {
            this.addSlot(new SlotItemHandler(generator.getItems(), 0, 43, 33));
            this.addSlot(new SlotItemHandler(generator.getItems(), 1, 80, 33));
            this.addSlot(new SlotItemHandler(generator.getItems(), 2, 117, 33));
            this.addSlot(new UpgradeSlot(generator.getItems(), 3, 182, 5));
            this.addSlot(new UpgradeSlot(generator.getItems(), 4, 182, 23));
            this.addSlot(new UpgradeSlot(generator.getItems(), 5, 182, 41));
            this.addSlot(new UpgradeSlot(generator.getItems(), 6, 182, 59));
        }

    }

    public PrinterBlockEntity getBlockEntity ()
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
        return KineticBlocks.PRINTER_BLOCK.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {
        if (player.level().getBlockEntity(pos) instanceof PrinterBlockEntity printer) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return printer.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    PrinterMenu.this.power = (PrinterMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (printer.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    PrinterMenu.this.power = (PrinterMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
    }
}

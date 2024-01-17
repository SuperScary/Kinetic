package superscary.kinetic.gui.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.entity.BasicBatteryBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.gui.KineticMenus;

public class BasicBatteryMenu extends KineticContainerMenu
{

    public final BasicBatteryBlockEntity blockEntity;

    public BasicBatteryMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public BasicBatteryMenu (int containerId, Inventory inventory, BlockEntity entity)
    {
        super(KineticMenus.BASIC_BATTERY_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = (BasicBatteryBlockEntity) entity;
    }

    public BasicBatteryBlockEntity getBlockEntity ()
    {
        return this.blockEntity;
    }

    @Override
    public int getContainerSize ()
    {
        return 0;
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.BASIC_BATTERY.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {
        if (player.level().getBlockEntity(pos) instanceof BasicBatteryBlockEntity battery) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return battery.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    BasicBatteryMenu.this.power = (BasicBatteryMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (battery.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    BasicBatteryMenu.this.power = (BasicBatteryMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
    }


}

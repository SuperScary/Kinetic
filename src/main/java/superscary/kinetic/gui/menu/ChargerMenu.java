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
import superscary.kinetic.block.entity.ChargerBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.gui.KineticMenus;

public class ChargerMenu extends KineticContainerMenu
{

    public final ChargerBlockEntity blockEntity;

    public ChargerMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ChargerMenu (int containerId, Inventory inventory, BlockEntity entity)
    {
        super(KineticMenus.CHARGER_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = (ChargerBlockEntity) entity;

        if (getLevel().getBlockEntity(entity.getBlockPos()) instanceof ChargerBlockEntity generator)
        {
            this.addSlot(new SlotItemHandler(generator.getItems(), 0, 43, 33));
            this.addSlot(new SlotItemHandler(generator.getItems(), 1, 80, 33));
            this.addSlot(new SlotItemHandler(generator.getItems(), 2, 117, 33));
        }

    }

    public ChargerBlockEntity getBlockEntity ()
    {
        return this.blockEntity;
    }

    @Override
    public int getContainerSize ()
    {
        return 3;
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.CHARGER.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {
        if (player.level().getBlockEntity(pos) instanceof ChargerBlockEntity charger) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return charger.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargerMenu.this.power = (ChargerMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (charger.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargerMenu.this.power = (ChargerMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
    }
}

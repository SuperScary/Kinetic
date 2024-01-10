package superscary.kinetic.gui.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.entity.CompressorBlockEntity;
import superscary.kinetic.block.entity.SawmillBlockEntity;
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
        return blockEntity.getInventorySize();
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.SAWMILL.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {
        if (player.level().getBlockEntity(pos) instanceof SawmillBlockEntity sawmill) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return sawmill.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    SawmillMenu.this.power = (SawmillMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (sawmill.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    SawmillMenu.this.power = (SawmillMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
    }

}

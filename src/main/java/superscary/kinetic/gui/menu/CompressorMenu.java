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
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.gui.KineticMenus;

public class CompressorMenu extends KineticContainerMenu
{

    public final CompressorBlockEntity blockEntity;

    public CompressorMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
    }

    public CompressorMenu (int containerId, Inventory inventory, BlockEntity entity, ContainerData data)
    {
        super(KineticMenus.COMPRESSOR_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = ((CompressorBlockEntity) entity);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 56, 35));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 116, 35));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 21, 57));
        });

        addDataSlots(data);
    }

    @Override
    public int getContainerSize ()
    {
        return 3;
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.COMPRESSOR.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {
        if (player.level().getBlockEntity(pos) instanceof CompressorBlockEntity compressor) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return compressor.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    CompressorMenu.this.power = (CompressorMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (compressor.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    CompressorMenu.this.power = (CompressorMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
    }

    public CompressorBlockEntity getBlockEntity ()
    {
        return this.blockEntity;
    }

}

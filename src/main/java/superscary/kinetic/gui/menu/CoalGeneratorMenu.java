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
import superscary.kinetic.block.entity.CoalGeneratorBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.gui.KineticMenus;

public class CoalGeneratorMenu extends KineticContainerMenu
{

    public final CoalGeneratorBlockEntity blockEntity;

    public CoalGeneratorMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public CoalGeneratorMenu (int containerId, Inventory inventory, BlockEntity entity)
    {
        super(KineticMenus.COAL_GENERATOR_MENU.get(), containerId, inventory, entity.getBlockPos());
        blockEntity = (CoalGeneratorBlockEntity) entity;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 33));
        });

    }

    @Override
    public int getContainerSize ()
    {
        return 1;
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.COAL_GENERATOR.get();
    }

    public CoalGeneratorBlockEntity getBlockEntity ()
    {
        return this.blockEntity;
    }

}

package superscary.kinetic.gui.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.block.entity.CoalGeneratorBlockEntity;
import superscary.kinetic.gui.KineticContainerMenu;
import superscary.kinetic.register.KineticMenus;
import superscary.kinetic.gui.UpgradeSlot;

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
            this.addSlot(new UpgradeSlot(iItemHandler, 1, 182, 5));
            this.addSlot(new UpgradeSlot(iItemHandler, 2, 182, 23));
            this.addSlot(new UpgradeSlot(iItemHandler, 3, 182, 41));
            this.addSlot(new UpgradeSlot(iItemHandler, 4, 182, 59));
        });

    }

    @Override
    public int getContainerSize ()
    {
        return 5;
    }

    @Override
    public Block getBlock ()
    {
        return KineticBlocks.COAL_GENERATOR.get();
    }

    @Override
    public void addDataSlots (Player player, BlockPos pos)
    {
        if (player.level().getBlockEntity(pos) instanceof CoalGeneratorBlockEntity generator) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return generator.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    CoalGeneratorMenu.this.power = (CoalGeneratorMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (generator.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    CoalGeneratorMenu.this.power = (CoalGeneratorMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }
    }

    public CoalGeneratorBlockEntity getBlockEntity ()
    {
        return this.blockEntity;
    }

}

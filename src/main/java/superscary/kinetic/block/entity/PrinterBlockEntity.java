package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBlockEntities;
import superscary.kinetic.gui.menu.PrinterMenu;
import superscary.kinetic.item.UpgradeItem;
import superscary.kinetic.util.SizedInventory;
import superscary.kinetic.util.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.NBTKeys;

public class PrinterBlockEntity extends BlockEntity implements MenuProvider, SizedInventory
{

    public static final int MAX_TRANSFER = 512;
    public static final int CAPACITY = 20000;

    private final EnergyStorage energy = createEnergyStorage();
    private final ItemStackHandler itemHandler = new ItemStackHandler(getInventorySize())
    {
        @Override
        protected void onContentsChanged (int slot)
        {
            setChanged();
            if (!level.isClientSide())
            {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }

        }

        @Override
        public boolean isItemValid (int slot, @NotNull ItemStack stack)
        {
            return switch (slot)
            {
                case 0, 1, 2 -> stack.getCapability(ForgeCapabilities.ENERGY).isPresent();
                case 3, 4, 5, 6 -> stack.getItem() instanceof UpgradeItem;
                default -> false;
            };
        }
    };
    private final LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy) {
        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            setChanged();
            return super.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return true;
        }
    });
    private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);

    public PrinterBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.PRINTER_BE.get(), pos, state);
    }

    public void tickServer ()
    {
        boolean powered = energy.getEnergyStored() > 0;
        if (powered != getBlockState().getValue(BlockStateProperties.POWERED))
        {

            if (itemHandler.getStackInSlot(0) != ItemStack.EMPTY)
            {
                ItemStack s0 = itemHandler.getStackInSlot(0);
                s0.getCapability(ForgeCapabilities.ENERGY).ifPresent(handler -> {
                    if (handler.canReceive())
                    {
                        chargeItem(s0);
                    }
                });
            }

            if (itemHandler.getStackInSlot(1) != ItemStack.EMPTY)
            {
                ItemStack s1 = itemHandler.getStackInSlot(1);
                s1.getCapability(ForgeCapabilities.ENERGY).ifPresent(handler -> {
                    if (handler.canReceive())
                    {
                        chargeItem(s1);
                    }
                });
            }

            if (itemHandler.getStackInSlot(2) != ItemStack.EMPTY)
            {
                ItemStack s2 = itemHandler.getStackInSlot(2);
                s2.getCapability(ForgeCapabilities.ENERGY).ifPresent(handler -> {
                    if (handler.canReceive())
                    {
                        chargeItem(s2);
                    }
                });
            }

            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, powered));
        }
        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    private void chargeItem (ItemStack stack)
    {
        if (energy.getEnergyStored() <= 0) return;

        stack.getCapability(ForgeCapabilities.ENERGY).map(e -> {
            if (e.canReceive())
            {
                int received = e.receiveEnergy(Math.min(energy.getEnergyStored(), MAX_TRANSFER), false);
                energy.extractEnergy(received, false);
                setChanged();
                return received;
            }
            return 0;
        });
    }

    private EnergyStorage createEnergyStorage ()
    {
        return new EnergyStorage(CAPACITY, MAX_TRANSFER, MAX_TRANSFER);
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(NBTKeys.POWER, energy.serializeNBT());
        tag.put(NBTKeys.INVENTORY, itemHandler.serializeNBT());
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        if (tag.contains(NBTKeys.POWER)) energy.deserializeNBT(tag.get(NBTKeys.POWER));
        if (tag.contains(NBTKeys.INVENTORY)) itemHandler.deserializeNBT(tag.getCompound(NBTKeys.INVENTORY));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();
        else if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        return super.getCapability(cap, side);
    }

    public EnergyStorage getEnergyStorage ()
    {
        return energy;
    }

    public ItemStackHandler getItems ()
    {
        return itemHandler;
    }

    public void drops ()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu (int id, Inventory inventory, Player player)
    {
        return new PrinterMenu(id, inventory, this);
    }

    @Override
    public Component getDisplayName ()
    {
        return Component.translatable("block.kinetic.printer");
    }

    @Override
    public int getInventorySize ()
    {
        return 7;
    }

    public int getStoredPower ()
    {
        return energy.getEnergyStored();
    }
}

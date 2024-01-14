package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
import superscary.kinetic.gui.menu.SawmillMenu;
import superscary.kinetic.item.KineticItems;
import superscary.kinetic.util.SizedInventory;
import superscary.kinetic.util.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.NBTKeys;

public class SawmillBlockEntity extends BlockEntity implements MenuProvider, SizedInventory
{

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    protected final ContainerData data;
    private final int DEFAULT_MAX_PROGRESS = 78;
    private final int DEFAULT_ENERGY_AMOUNT = 128;
    private EnergyStorage energy = createEnergyStorage();
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
                case 0 -> true;
                case 1 -> false;
                case 2 -> stack.getItem() == KineticItems.CAPACITOR_BASIC.get()
                        || stack.getItem() == KineticItems.CAPACITOR_STANDARD.get()
                        || stack.getItem() == KineticItems.CAPACITOR_PREMIUM.get()
                        || stack.getItem() == KineticItems.CAPACITOR_DELUXE.get()
                        || stack.getItem() == KineticItems.CAPACITOR_ULTIMATE.get();
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy));
    private int progress = 0;
    private int maxProgress = 0;
    private int energyAmount = 0;

    public SawmillBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.SAWMILL_BE.get(), pos, state);
        this.data = new ContainerData()
        {
            @Override
            public int get (int index)
            {
                return switch (index)
                {
                    case 0 -> SawmillBlockEntity.this.progress;
                    case 1 -> SawmillBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set (int index, int value)
            {
                switch (index)
                {
                    case 0 -> SawmillBlockEntity.this.progress = value;
                    case 1 -> SawmillBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount ()
            {
                return 3;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();
        else if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        else return super.getCapability(cap, side);
    }

    private EnergyStorage createEnergyStorage ()
    {
        return new EnergyStorage(64000, 128, 128);
    }

    public IEnergyStorage getEnergyStorage ()
    {
        return energy;
    }

    @Override
    public void invalidateCaps ()
    {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
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

    @Override
    public Component getDisplayName ()
    {
        return Component.translatable("block.kinetic.sawmill");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu (int containerId, Inventory inventory, Player player)
    {
        return new SawmillMenu(containerId, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        tag.put(NBTKeys.INVENTORY, itemHandler.serializeNBT());
        tag.putInt(NBTKeys.SAWMILL_NBT_PROGRESS, progress);
        tag.putInt(NBTKeys.SAWMILL_NBT_MAX_PROGRESS, maxProgress);
        tag.putInt(NBTKeys.POWER, energyAmount);
        tag.putInt(NBTKeys.POWER, energy.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound(NBTKeys.INVENTORY));
        progress = tag.getInt(NBTKeys.SAWMILL_NBT_PROGRESS);
        maxProgress = tag.getInt(NBTKeys.SAWMILL_NBT_MAX_PROGRESS);
        energyAmount = tag.getInt(NBTKeys.POWER);
        energy.deserializeNBT(tag.get(NBTKeys.POWER));
    }

    public void tick (Level pLevel, BlockPos pPos, BlockState pState)
    {

    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket ()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag ()
    {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket (Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        super.onDataPacket(net, pkt);
    }

    @Override
    public int getInventorySize ()
    {
        return 3;
    }

    public int getStoredPower ()
    {
        return energy.getEnergyStored();
    }

}

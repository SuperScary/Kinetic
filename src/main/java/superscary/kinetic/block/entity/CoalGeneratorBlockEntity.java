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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
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
import superscary.kinetic.gui.menu.CoalGeneratorMenu;
import superscary.kinetic.util.SizedInventory;
import superscary.kinetic.util.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.NBTKeys;

public class CoalGeneratorBlockEntity extends BlockEntity implements MenuProvider, SizedInventory
{

    public static final int GENERATE = 50;
    public static final int MAX_TRANSFER = 1024;
    public static final int CAPACITY = 100000;

    public static int SLOT_COUNT = 1;
    public static int SLOT = 0;
    protected final ContainerData data;
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
    };
    private final EnergyStorage energy = createEnergyStorage();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy));
    private int burnTime;

    public CoalGeneratorBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.COAL_GENERATOR_BE.get(), pos, state);
        this.data = new ContainerData()
        {
            @Override
            public int get (int index)
            {
                return switch (index)
                {
                    case 0 -> CoalGeneratorBlockEntity.this.burnTime;
                    default -> 0;
                };
            }

            @Override
            public void set (int index, int value)
            {
                switch (index)
                {
                    case 0 -> CoalGeneratorBlockEntity.this.burnTime = value;
                }
            }

            @Override
            public int getCount ()
            {
                return SLOT_COUNT;
            }
        };
    }

    public void tickServer ()
    {
        generateEnergy();
        distributeEnergy();

        BlockState blockState = level.getBlockState(worldPosition);
        if (blockState.getValue(BlockStateProperties.POWERED) != burnTime > 0)
        {
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, burnTime > 0), Block.UPDATE_ALL);
        }

    }

    private void generateEnergy ()
    {
        if (energy.getEnergyStored() < energy.getMaxEnergyStored())
        {
            if (burnTime <= 0)
            {
                ItemStack fuel = itemHandler.getStackInSlot(SLOT);
                if (fuel.isEmpty()) return;

                setBurnTime(ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING));
                if (burnTime <= 0) return;

                itemHandler.extractItem(SLOT, 1, false);
            } else
            {
                setBurnTime(burnTime - 1);
                energy.receiveEnergy(GENERATE, false);
            }
            setChanged();
        }
    }

    private void setBurnTime (int bt)
    {
        if (bt == burnTime) return;
        burnTime = bt;
        if (getBlockState().getValue(BlockStateProperties.POWERED) != burnTime > 0)
        {
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, burnTime > 0));
        }

        setChanged();
    }

    private void distributeEnergy ()
    {
        for (Direction direction : Direction.values())
        {
            if (energy.getEnergyStored() <= 0) return;
            BlockEntity be = level.getBlockEntity(getBlockPos().relative(direction));
            if (be != null)
            {
                be.getCapability(ForgeCapabilities.ENERGY).map(e -> {
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
        }
    }

    public ItemStackHandler getItemHandler ()
    {
        return itemHandler;
    }

    public EnergyStorage getEnergyStorage ()
    {
        return energy;
    }

    private EnergyStorage createEnergyStorage ()
    {
        return new EnergyStorage(CAPACITY, MAX_TRANSFER, MAX_TRANSFER);
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
    public void onLoad ()
    {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energy);
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(NBTKeys.INVENTORY, itemHandler.serializeNBT());
        tag.put(NBTKeys.POWER, energy.serializeNBT());
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        if (tag.contains(NBTKeys.INVENTORY)) itemHandler.deserializeNBT(tag.getCompound(NBTKeys.INVENTORY));
        if (tag.contains(NBTKeys.POWER)) energy.deserializeNBT(tag.get(NBTKeys.POWER));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        else if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps ()
    {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    public Component getDisplayName ()
    {
        return Component.translatable("block.kinetic.coal_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu (int containerId, Inventory inventory, Player player)
    {
        return new CoalGeneratorMenu(containerId, inventory, this);
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
        return 1;
    }

    public int getStoredPower ()
    {
        return energy.getEnergyStored();
    }

}

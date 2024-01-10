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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import superscary.kinetic.block.blocks.CompressorBlock;
import superscary.kinetic.gui.menu.CompressorMenu;
import superscary.kinetic.item.CapacitorItem;
import superscary.kinetic.item.KineticItems;
import superscary.kinetic.recipe.CompressorRecipe;
import superscary.kinetic.util.SizedInventory;
import superscary.kinetic.util.energy.CapacitorModifiable;
import superscary.kinetic.util.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.InventoryHelper;
import superscary.kinetic.util.helpers.NBTKeys;

import java.util.Optional;

public class CompressorBlockEntity extends BlockEntity implements MenuProvider, SizedInventory, CapacitorModifiable
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

            if (slot == getCapacitorSlot())
            {
                setChanged();
                energy = createEnergyStorage();
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

    public CompressorBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.COMPRESSOR_BE.get(), pos, state);
        this.data = new ContainerData()
        {
            @Override
            public int get (int index)
            {
                return switch (index)
                {
                    case 0 -> CompressorBlockEntity.this.progress;
                    case 1 -> CompressorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set (int index, int value)
            {
                switch (index)
                {
                    case 0 -> CompressorBlockEntity.this.progress = value;
                    case 1 -> CompressorBlockEntity.this.maxProgress = value;
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
        if (capacitorSlotValid()) return new EnergyStorage(getCapacitor().getMaxCapacity(), getCapacitor().getMaxTransfer(), getCapacitor().getMaxTransfer());
        else return new EnergyStorage(0, 0, 0);
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
        return Component.translatable("block.kinetic.compressor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu (int containerId, Inventory inventory, Player player)
    {
        return new CompressorMenu(containerId, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        tag.put(NBTKeys.INVENTORY, itemHandler.serializeNBT());
        tag.putInt(NBTKeys.COMPRESSOR_NBT_PROGRESS, progress);
        tag.putInt(NBTKeys.COMPRESSOR_NBT_MAX_PROGRESS, maxProgress);
        tag.putInt(NBTKeys.COMPRESSOR_RF_AMOUNT, energyAmount);
        tag.putInt("energy", energy.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound(NBTKeys.INVENTORY));
        progress = tag.getInt(NBTKeys.COMPRESSOR_NBT_PROGRESS);
        maxProgress = tag.getInt(NBTKeys.COMPRESSOR_NBT_MAX_PROGRESS);
        energyAmount = tag.getInt(NBTKeys.COMPRESSOR_RF_AMOUNT);
        energy.deserializeNBT(tag.get(NBTKeys.POWER));
    }

    public void tick (Level pLevel, BlockPos pPos, BlockState pState)
    {
        CompressorBlock block = (CompressorBlock) pState.getBlock();
        if (hasRecipe() && capacitorSlotValid())
        {
            block.defaultBlockState().setValue(BlockStateProperties.POWERED, Boolean.TRUE);
            increaseCraftingProgress();
            energy.extractEnergy(energyAmount, false);
            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished())
            {
                craftItem();
                resetProgress();
                block.defaultBlockState().setValue(BlockStateProperties.POWERED, Boolean.FALSE);
            }

        } else
        {
            resetProgress();
            block.defaultBlockState().setValue(BlockStateProperties.POWERED, Boolean.FALSE);
        }

    }

    private void resetProgress ()
    {
        progress = 0;
    }

    private void craftItem ()
    {
        Optional<CompressorRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe ()
    {
        Optional<CompressorRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        energyAmount = recipe.get().getEnergyReq();
        maxProgress = recipe.get().getCraftTime();

        return canInsertAmount(result.getCount()) && canInsertItem(result.getItem()) && hasEnoughEnergy(energyAmount);
    }

    private boolean hasEnoughEnergy (int req)
    {
        boolean b = this.energy.getEnergyStored() >= req * maxProgress;
        if (b) this.energy.extractEnergy(req, false);
        return b;
    }

    private Optional<CompressorRecipe> getCurrentRecipe ()
    {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(CompressorRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItem (Item item)
    {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmount (int count)
    {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished ()
    {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress ()
    {
        progress++;
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

    @Override
    public int getCapacitorSlot ()
    {
        return 2;
    }

    @Override
    public boolean capacitorSlotValid ()
    {
        return InventoryHelper.capacitorIsValid(itemHandler, getCapacitorSlot());
    }

    @Override
    public CapacitorItem getCapacitor ()
    {
        return capacitorSlotValid() ? (CapacitorItem) itemHandler.getStackInSlot(getCapacitorSlot()).getItem() : null;
    }

    public int getStoredPower ()
    {
        return energy.getEnergyStored();
    }

}

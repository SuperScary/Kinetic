package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.CompressorBlock;
import superscary.kinetic.network.ModMessages;
import superscary.kinetic.network.packet.EnergySyncS2CPacket;
import superscary.kinetic.recipe.CompressorRecipe;
import superscary.kinetic.gui.CompressorMenu;
import superscary.kinetic.util.ModEnergyStorage;
import superscary.kinetic.util.NBTKeys;

import java.util.Optional;

public class CompressorBlockEntity extends BlockEntity implements MenuProvider
{

    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
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

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private static final int ENERGY_REQ = 64;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

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
                    default ->  0;
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
                return 2;
            }
        };
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256)
    {
        @Override
        public void onEnergyChanged ()
        {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad ()
    {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps ()
    {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    public IEnergyStorage getEnergyStorage ()
    {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel (int energyLevel)
    {
        this.ENERGY_STORAGE.setEnergy(energyLevel);
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
        tag.putInt(NBTKeys.POWER, ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound(NBTKeys.INVENTORY));
        ENERGY_STORAGE.setEnergy(tag.getInt(NBTKeys.POWER));
        progress = tag.getInt(NBTKeys.COMPRESSOR_NBT_PROGRESS);
    }

    public void tick (Level pLevel, BlockPos pPos, BlockState pState)
    {
        CompressorBlock block = (CompressorBlock) pState.getBlock();
        if (hasRecipe() && hasEnoughEnergy())
        {
            block.defaultBlockState().setValue(BlockStateProperties.POWERED, Boolean.TRUE);
            increaseCraftingProgress();
            extractEnergy();
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

    private void extractEnergy ()
    {
        this.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private boolean hasEnoughEnergy ()
    {
        return this.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * this.maxProgress;
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

        return canInsertAmount(result.getCount()) && canInsertItem(result.getItem());
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
}

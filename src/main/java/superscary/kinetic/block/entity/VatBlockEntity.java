package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.register.KineticBlockEntities;
import superscary.kinetic.util.helpers.NBTKeys;

public class VatBlockEntity extends BlockEntity
{

    private final ItemStackHandler itemHandler = new ItemStackHandler(2)
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
            return true;
        }
    };

    private final FluidTank fluidTank = createFluidTank();

    private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.of(() -> fluidTank);

    public VatBlockEntity (BlockPos pPos, BlockState pBlockState)
    {
        super(KineticBlockEntities.VAT_BE.get(), pPos, pBlockState);
    }

    public void tickServer ()
    {
        setChanged();
        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag = fluidTank.writeToNBT(tag);
        tag.put(NBTKeys.INVENTORY, itemHandler.serializeNBT());
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        if (tag.contains(NBTKeys.FLUID)) fluidTank.readFromNBT(tag.getCompound(NBTKeys.FLUID));
        if (tag.contains(NBTKeys.INVENTORY)) itemHandler.deserializeNBT(tag.getCompound(NBTKeys.INVENTORY));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        else if (cap == ForgeCapabilities.FLUID_HANDLER) return lazyFluidHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps ()
    {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    public ItemStackHandler getItems ()
    {
        return itemHandler;
    }
    public FluidTank getFluidTank ()
    {
        return fluidTank;
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

    private FluidTank createFluidTank ()
    {
        return new FluidTank(10000)
        {
            @Override
            protected void onContentsChanged ()
            {
                setChanged();
                if (!level.isClientSide())
                {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid (FluidStack stack)
            {
                return true;
            }

        };
    }

}

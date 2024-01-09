package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.TankBlock;

public class KineticTankBase extends BlockEntity
{

    private final int capacity;
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final FluidTank FLUID_TANK = createFluidTank();

    public KineticTankBase (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.BASIC_TANK_BE.get(), pos, state);
        this.capacity = ((TankBlock) state.getBlock()).getCapacity();
    }

    private FluidTank createFluidTank ()
    {
        return new FluidTank(capacity) {
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.FLUID_HANDLER) return lazyFluidHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad ()
    {
        super.onLoad();
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps ()
    {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        tag = FLUID_TANK.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        FLUID_TANK.readFromNBT(tag);
    }

    public FluidStack getRenderStack ()
    {
        return FLUID_TANK.isEmpty() ? FluidStack.EMPTY : FLUID_TANK.getFluid();
    }

}

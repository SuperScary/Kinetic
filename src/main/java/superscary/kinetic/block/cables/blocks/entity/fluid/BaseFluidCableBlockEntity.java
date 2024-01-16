package superscary.kinetic.block.cables.blocks.entity.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.util.helpers.NBTKeys;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class BaseFluidCableBlockEntity extends BlockEntity
{

    public static final String FLUID_TAG = NBTKeys.FLUID;
    private final int max_transfer;
    private final int capacity;
    private final FluidTank fluid = createFluidTank();
    private final LazyOptional<IFluidTank> fluidHandler = LazyOptional.of(() -> fluid);
    // Cached outputs
    private Set<BlockPos> outputs = null;

    public BaseFluidCableBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState state, FluidCableType fluidCableType)
    {
        super(type, pos, state);
        this.capacity = fluidCableType.getCapacity();
        this.max_transfer = fluidCableType.getMaxTransfer();
    }

    private void checkOutputs ()
    {
        if (outputs == null)
        {
            outputs = new HashSet<>();
            traverse(worldPosition, cable -> {
                for (Direction direction : Direction.values())
                {
                    BlockPos p = cable.getBlockPos().relative(direction);
                    BlockEntity te = level.getBlockEntity(p);
                    if (te != null && !(te instanceof BaseFluidCableBlockEntity))
                    {
                        te.getCapability(ForgeCapabilities.ENERGY).ifPresent(handler -> {
                            if (handler.canReceive())
                            {
                                outputs.add(p);
                            }
                        });
                    }
                }
            });
        }
    }

    public void markDirty ()
    {
        traverse(worldPosition, cable -> cable.outputs = null);
    }

    private void traverse (BlockPos pos, Consumer<BaseFluidCableBlockEntity> consumer)
    {
        Set<BlockPos> traversed = new HashSet<>();
        traversed.add(pos);
        consumer.accept(this);
        traverse(pos, traversed, consumer);
    }

    public abstract void traverse (BlockPos pos, Set<BlockPos> traversed, Consumer<BaseFluidCableBlockEntity> consumer);

    public void tickServer ()
    {
        if (fluid.getFluidAmount() > 0)
        {
            checkOutputs();
            if (!outputs.isEmpty())
            {
                int amount = fluid.getFluidAmount() / outputs.size();
                for (BlockPos p : outputs)
                {
                    BlockEntity te = level.getBlockEntity(p);
                    if (te != null)
                    {
                        te.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
                            if (handler.isFluidValid(0, fluid.getFluid()))
                            {
                                int received = handler.fill(new FluidStack(fluid.getFluid(), amount), IFluidHandler.FluidAction.EXECUTE);
                                fluid.drain(received, IFluidHandler.FluidAction.EXECUTE);
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag = fluid.writeToNBT(tag);
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        if (tag.contains(FLUID_TAG))
        {
            fluid.readFromNBT(tag);
        }
    }

    @Nonnull
    private FluidTank createFluidTank ()
    {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged ()
            {
                setChanged();
                if (!level.isClientSide()) level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }

            @Override
            public boolean isFluidValid (FluidStack stack)
            {
                return true;
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.FLUID_HANDLER)
        {
            return fluidHandler.cast();
        } else
        {
            return super.getCapability(cap, side);
        }
    }

    public enum FluidCableType
    {
        BASIC(8192, 1024),
        STANDARD(16384, 2048),
        PREMIUM(32768, 2096),
        DELUXE(65536, 8192),
        ULTIMATE(131072, 16384),
        FACADE(0, 0);

        int cap, max_receive;
        FluidCableType (int cap, int max_receive)
        {
            this.cap = cap;
            this.max_receive = max_receive;
        }

        public int getCapacity ()
        {
            return cap;
        }

        public int getMaxTransfer ()
        {
            return max_receive;
        }

    }

}

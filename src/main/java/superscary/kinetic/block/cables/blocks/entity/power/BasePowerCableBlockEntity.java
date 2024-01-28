package superscary.kinetic.block.cables.blocks.entity.power;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.api.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.NBTKeys;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class BasePowerCableBlockEntity extends BlockEntity
{

    public static final String ENERGY_TAG = NBTKeys.POWER;
    private final int max_transfer;
    private final int capacity;
    private final EnergyStorage energy = createEnergyStorage();
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy) {
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
    // Cached outputs
    private Set<BlockPos> outputs = null;

    public BasePowerCableBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState state, PowerCableType powerCableType)
    {
        super(type, pos, state);
        this.capacity = powerCableType.getCapacity();
        this.max_transfer = powerCableType.getMaxTransfer();
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
                    if (te != null && !(te instanceof BasePowerCableBlockEntity))
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

    private void traverse (BlockPos pos, Consumer<BasePowerCableBlockEntity> consumer)
    {
        Set<BlockPos> traversed = new HashSet<>();
        traversed.add(pos);
        consumer.accept(this);
        traverse(pos, traversed, consumer);
    }

    public abstract void traverse (BlockPos pos, Set<BlockPos> traversed, Consumer<BasePowerCableBlockEntity> consumer);

    public void tickServer ()
    {
        if (energy.getEnergyStored() > 0)
        {
            checkOutputs();
            if (!outputs.isEmpty())
            {
                int amount = energy.getEnergyStored() / outputs.size();
                for (BlockPos p : outputs)
                {
                    BlockEntity te = level.getBlockEntity(p);
                    if (te != null)
                    {
                        te.getCapability(ForgeCapabilities.ENERGY).ifPresent(handler -> {
                            if (handler.canReceive())
                            {
                                int received = handler.receiveEnergy(amount, false);
                                energy.extractEnergy(received, false);
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
        tag.put(ENERGY_TAG, energy.serializeNBT());
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        if (tag.contains(ENERGY_TAG))
        {
            energy.deserializeNBT(tag.get(ENERGY_TAG));
        }
    }

    @Nonnull
    private EnergyStorage createEnergyStorage ()
    {
        return new EnergyStorage(capacity, max_transfer, max_transfer);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY)
        {
            return energyHandler.cast();
        } else
        {
            return super.getCapability(cap, side);
        }
    }

    public enum PowerCableType
    {
        BASIC(8192, 1024),
        STANDARD(16384, 2048),
        PREMIUM(32768, 2096),
        DELUXE(65536, 8192),
        ULTIMATE(131072, 16384),
        FACADE(0, 0);

        int cap, max_receive;
        PowerCableType (int cap, int max_receive)
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

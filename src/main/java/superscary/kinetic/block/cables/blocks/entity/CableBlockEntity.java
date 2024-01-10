package superscary.kinetic.block.cables.blocks.entity;

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
import superscary.kinetic.block.entity.KineticBlockEntities;
import superscary.kinetic.util.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.NBTKeys;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CableBlockEntity extends BlockEntity
{

    public static final String ENERGY_TAG = NBTKeys.POWER;
    public static final int MAX_TRANSFER = 100;
    public static final int CAPACITY = 1000;

    private final EnergyStorage energy = createEnergyStorage();
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy));
    // Cached outputs
    private Set<BlockPos> outputs = null;

    protected CableBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public CableBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.CABLE_BE.get(), pos, state);
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
                    if (te != null && !(te instanceof CableBlockEntity))
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

    private void traverse (BlockPos pos, Consumer<CableBlockEntity> consumer)
    {
        Set<BlockPos> traversed = new HashSet<>();
        traversed.add(pos);
        consumer.accept(this);
        traverse(pos, traversed, consumer);
    }

    private void traverse (BlockPos pos, Set<BlockPos> traversed, Consumer<CableBlockEntity> consumer)
    {
        for (Direction direction : Direction.values())
        {
            BlockPos p = pos.relative(direction);
            if (!traversed.contains(p))
            {
                traversed.add(p);
                if (level.getBlockEntity(p) instanceof CableBlockEntity cable)
                {
                    consumer.accept(cable);
                    cable.traverse(p, traversed, consumer);
                }
            }
        }
    }

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
        return new EnergyStorage(CAPACITY, MAX_TRANSFER, MAX_TRANSFER);
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
}
package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.api.energy.KineticEnergyStorage;
import superscary.kinetic.register.KineticBlockEntities;
import superscary.kinetic.util.helpers.NBTKeys;

public class ElectricMotorBlockEntity extends BlockEntity
{

    private static final int USE = 52;
    private boolean canRun;

    private final EnergyStorage energy = createEnergyStorage();

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy)
    {

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            setChanged();
            return super.receiveEnergy(maxReceive, simulate);
        }

    });

    public ElectricMotorBlockEntity (BlockPos pPos, BlockState pBlockState)
    {
        super(KineticBlockEntities.ELECTRIC_MOTOR_BE.get(), pPos, pBlockState);
        this.canRun = false;
    }

    public void tickServer ()
    {
        if (getEnergyStorage().getEnergyStored() >= USE)
        {
            this.getBlockState().setValue(BlockStateProperties.POWERED, true);
        }
        else this.getBlockState().setValue(BlockStateProperties.POWERED, false);

        runMotor();
        setChanged();
        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    private void runMotor ()
    {
        if (this.getBlockState().getValue(BlockStateProperties.POWERED))
        {
            getEnergyStorage().extractEnergy(USE, false);
            this.canRun = true;
        } else this.canRun = false;
    }

    @Override
    public void onLoad ()
    {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energy);
    }

    @Override
    public void invalidateCaps ()
    {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(NBTKeys.POWER, energy.serializeNBT());
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        if (tag.contains(NBTKeys.POWER)) energy.deserializeNBT(tag.get(NBTKeys.POWER));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();
        return super.getCapability(cap, side);
    }

    public EnergyStorage getEnergyStorage ()
    {
        return energy;
    }

    private EnergyStorage createEnergyStorage ()
    {
        return new EnergyStorage(1024, 1024, 1024);
    }

    public boolean getCanRun ()
    {
        return this.canRun;
    }

}

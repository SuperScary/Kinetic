package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.util.ModEnergyStorage;
import superscary.kinetic.util.NBTKeys;

public class ChargerBlockEntity extends BlockEntity
{

    public static final int MAX_TRANSFER = 128;
    public static final int CAPACITY = 100000;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public ChargerBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.CHARGER_BE.get(), pos, state);
    }

    public void tickServer ()
    {
        boolean powered = ENERGY_STORAGE.getEnergyStored() > 0;
        if (powered != getBlockState().getValue(BlockStateProperties.POWERED))
        {
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, powered));
        }
    }

    private ModEnergyStorage createEnergyStorage ()
    {
        return new ModEnergyStorage(CAPACITY, MAX_TRANSFER)
        {
            @Override
            public void onEnergyChanged ()
            {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }

            @Override
            public int receiveEnergy (int max, boolean simulate)
            {
                setChanged();
                return super.receiveEnergy(max, simulate);
            }

            @Override
            public boolean canReceive ()
            {
                return true;
            }
        };
    }

    @Override
    public void onLoad ()
    {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.put(NBTKeys.POWER, ENERGY_STORAGE.serializeNBT());
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        if (tag.contains(NBTKeys.POWER)) ENERGY_STORAGE.deserializeNBT(tag.get(NBTKeys.POWER));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();
        return super.getCapability(cap, side);
    }
}

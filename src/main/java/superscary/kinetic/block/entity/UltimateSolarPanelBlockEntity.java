package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
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
import superscary.kinetic.register.KineticBlockEntities;
import superscary.kinetic.util.BlockUtils;
import superscary.kinetic.api.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.NBTKeys;

public class UltimateSolarPanelBlockEntity extends BlockEntity
{

    public static final int GENERATE_DAY = 256;
    public static final int GENERATE_NIGHT = 16;
    public static final int MAX_TRANSFER = 16384;
    public static final int CAPACITY = 300_000;
    private final EnergyStorage energy = createEnergyStorage();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy)
    {
        @Override
        public boolean canReceive ()
        {
            return false;
        }
    });

    public UltimateSolarPanelBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.ULTIMATE_SOLAR_PANEL_BE.get(), pos, state);
    }

    public void tickServer ()
    {
        generateEnergy();
        distributeEnergy();

        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);

    }

    private void generateEnergy ()
    {
        if (energy.getEnergyStored() < energy.getMaxEnergyStored())
        {
            BlockState blockState = level.getBlockState(worldPosition);
            if (BlockUtils.solarPanelPlacementValid(level, getBlockPos()))
            {
                energy.receiveEnergy(GENERATE_DAY, false);
                level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, true), Block.UPDATE_ALL);
            }
            else
            {
                energy.receiveEnergy(GENERATE_NIGHT, false);
                level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, false), Block.UPDATE_ALL);
            }
            setChanged();
        }
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

    public EnergyStorage getEnergyStorage ()
    {
        return energy;
    }

    private EnergyStorage createEnergyStorage ()
    {
        return new EnergyStorage(CAPACITY, MAX_TRANSFER, MAX_TRANSFER);
    }

    @Override
    public void onLoad ()
    {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energy);
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

    @Override
    public void invalidateCaps ()
    {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
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

    public int getStoredPower ()
    {
        return energy.getEnergyStored();
    }

}

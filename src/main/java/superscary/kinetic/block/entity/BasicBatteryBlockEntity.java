package superscary.kinetic.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.register.KineticBlockEntities;
import superscary.kinetic.gui.menu.BasicBatteryMenu;
import superscary.kinetic.api.energy.KineticEnergyStorage;
import superscary.kinetic.util.helpers.NBTKeys;

public class BasicBatteryBlockEntity extends BlockEntity implements MenuProvider
{

    public static final int MAX_TRANSFER = 64;
    public static final int CAPACITY = 1_000_000;
    private final EnergyStorage energy = createEnergyStorage();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> new KineticEnergyStorage(energy)
    {

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            setChanged();
            return super.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy (int maxExtract, boolean simulate)
        {
            setChanged();
            return super.extractEnergy(maxExtract, simulate);
        }
    });

    public BasicBatteryBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.BASIC_BATTERY_BE.get(), pos, state);
    }

    public void tickServer ()
    {
        distributeEnergy();

        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);

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
        return new EnergyStorage(CAPACITY, MAX_TRANSFER, MAX_TRANSFER, 0);
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

    @Override
    public Component getDisplayName ()
    {
        return Component.translatable("block.kinetic.basic_battery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu (int i, Inventory inventory, Player player)
    {
        return new BasicBatteryMenu(i, inventory, this);
    }
}

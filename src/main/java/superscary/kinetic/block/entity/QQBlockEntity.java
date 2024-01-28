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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.register.KineticBlockEntities;
import superscary.kinetic.block.blocks.QuantumQuarryBlock;
import superscary.kinetic.util.helpers.NBTKeys;

public class QQBlockEntity extends BlockEntity implements MenuProvider
{

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    protected final ContainerData data;
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
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private int progress = 0;
    private int maxProgress = 78;

    public QQBlockEntity (BlockPos pos, BlockState state)
    {
        super(KineticBlockEntities.QQ_BE.get(), pos, state);
        this.data = new ContainerData()
        {
            @Override
            public int get (int index)
            {
                return switch (index)
                {
                    case 0 -> QQBlockEntity.this.progress;
                    case 1 -> QQBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set (int index, int value)
            {
                switch (index)
                {
                    case 0 -> QQBlockEntity.this.progress = value;
                    case 1 -> QQBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount ()
            {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side)
    {
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
        return null;
    }

    @Override
    protected void saveAdditional (CompoundTag tag)
    {
        tag.put(NBTKeys.INVENTORY, itemHandler.serializeNBT());
        tag.putInt(NBTKeys.QUARRY_NBT_PROGRESS, progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load (CompoundTag tag)
    {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound(NBTKeys.INVENTORY));
        progress = tag.getInt(NBTKeys.QUARRY_NBT_PROGRESS);
    }

    public void tick (Level pLevel, BlockPos pPos, BlockState pState)
    {
        QuantumQuarryBlock block = (QuantumQuarryBlock) pState.getBlock();


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

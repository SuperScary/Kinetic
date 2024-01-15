package superscary.kinetic.block.cables.blocks.power;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.cables.blocks.KineticBaseCable;
import superscary.kinetic.block.cables.blocks.entity.power.BasicPowerCableBlockEntity;

import javax.annotation.Nonnull;

public class BasicPowerCableBlock extends KineticBaseCable
{

    public BasicPowerCableBlock ()
    {
        super(BlockBehaviour.Properties.of()
                .strength(1.0f)
                .sound(SoundType.METAL)
                .noOcclusion()
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos blockPos, BlockState blockState)
    {
        return new BasicPowerCableBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type)
    {
        if (level.isClientSide)
        {
            return null;
        } else
        {
            return (lvl, pos, st, be) -> {
                if (be instanceof BasicPowerCableBlockEntity cable)
                {
                    cable.tickServer();
                }
            };
        }
    }

    @Override
    public void neighborChanged (BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof BasicPowerCableBlockEntity cable)
        {
            cable.markDirty();
        }
    }

    @Override
    public void setPlacedBy (@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack)
    {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof BasicPowerCableBlockEntity cable)
        {
            cable.markDirty();
        }
        BlockState blockState = calculateState(level, pos, state);
        if (state != blockState)
        {
            level.setBlockAndUpdate(pos, blockState);
        }
    }

}



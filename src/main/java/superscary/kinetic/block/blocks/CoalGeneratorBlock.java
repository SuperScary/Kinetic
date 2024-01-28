package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;
import superscary.kinetic.block.entity.CoalGeneratorBlockEntity;

import static superscary.kinetic.register.KineticBlocks.MACHINE_BASE_BASIC;

public class CoalGeneratorBlock extends KineticBaseEntityBlock
{

    public CoalGeneratorBlock ()
    {
        super(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get()).noOcclusion().strength(3.5f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos pos, BlockState state)
    {
        return new CoalGeneratorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type)
    {
        if (level.isClientSide())
        {
            return null;
        } else return (lvl, pos, st, be) -> {
            if (be instanceof CoalGeneratorBlockEntity generator) generator.tickServer();
        };
    }

    @Override
    public void onRemove (BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {

        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity block = level.getBlockEntity(pos);
            if (block instanceof CoalGeneratorBlockEntity)
            {
                ((CoalGeneratorBlockEntity) block).drops();
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use (BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!level.isClientSide())
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof CoalGeneratorBlockEntity)
            {
                NetworkHooks.openScreen(((ServerPlayer) player), (CoalGeneratorBlockEntity) entity, pos);
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else throw new IllegalStateException("Container provider missing.");
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void animateTick (BlockState state, Level level, BlockPos pos, RandomSource source)
    {
        super.animateTick(state, level, pos, source);
        if (state.getValue(BlockStateProperties.POWERED))
        {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (source.nextDouble() < 0.1D)
            {
                level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(BlockStateProperties.FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = source.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
            double d6 = source.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);

        }
    }
}

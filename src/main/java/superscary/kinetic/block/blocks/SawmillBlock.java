package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
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
import superscary.kinetic.block.entity.CompressorBlockEntity;
import superscary.kinetic.block.entity.KineticBlockEntities;

import static superscary.kinetic.block.KineticBlocks.MACHINE_BASE_BASIC;

public class SawmillBlock extends KineticBaseEntityBlock
{

    public SawmillBlock ()
    {
        super(BlockBehaviour.Properties.copy(MACHINE_BASE_BASIC.get()));
    }

    @Override
    public void onRemove (BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {

        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity block = level.getBlockEntity(pos);
            if (block instanceof CompressorBlockEntity)
            {
                ((CompressorBlockEntity) block).drops();
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
            if (entity instanceof CompressorBlockEntity)
            {
                NetworkHooks.openScreen(((ServerPlayer) player), (CompressorBlockEntity) entity, pos);
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else
            {
                throw new IllegalStateException("Container provider missing.");
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos pos, BlockState state)
    {
        return new CompressorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        if (pLevel.isClientSide()) return null;
        return createTickerHelper(pBlockEntityType, KineticBlockEntities.COMPRESSOR_BE.get(), (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public void animateTick (BlockState state, Level level, BlockPos pos, RandomSource randomSource)
    {
        if (state.getValue(BlockStateProperties.POWERED))
        {
            level.playLocalSound(pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1f, 1f, false);
        }
        super.animateTick(state, level, pos, randomSource);
    }

}

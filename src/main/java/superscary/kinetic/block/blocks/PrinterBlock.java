package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.block.entity.PrinterBlockEntity;

public class PrinterBlock extends KineticBaseEntityBlock
{

    public PrinterBlock ()
    {
        super(BlockBehaviour.Properties.copy(KineticBlocks.MACHINE_BASE_BASIC.get()).strength(3.5f).requiresCorrectToolForDrops().noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos blockPos, BlockState blockState)
    {
        return new PrinterBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type)
    {
        if (level.isClientSide()) return null;
        else
        {
            return (lvl, pos, st, be) -> {
                if (be instanceof PrinterBlockEntity charger)
                {
                    charger.tickServer();
                }
            };
        }
    }

    @Override
    public InteractionResult use (BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!level.isClientSide())
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof PrinterBlockEntity)
            {
                NetworkHooks.openScreen(((ServerPlayer) player), (PrinterBlockEntity) entity, pos);
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else throw new IllegalStateException("Container provider missing.");
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void onRemove (BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {

        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity block = level.getBlockEntity(pos);
            if (block instanceof PrinterBlockEntity)
            {
                ((PrinterBlockEntity) block).drops();
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

}

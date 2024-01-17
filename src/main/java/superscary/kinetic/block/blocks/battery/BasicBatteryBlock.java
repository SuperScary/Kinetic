package superscary.kinetic.block.blocks.battery;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;
import superscary.kinetic.block.entity.BasicBatteryBlockEntity;

import static superscary.kinetic.block.KineticBlocks.MACHINE_BASE_BASIC;

public class BasicBatteryBlock extends KineticBaseEntityBlock
{

    public BasicBatteryBlock ()
    {
        super(Properties.copy(MACHINE_BASE_BASIC.get()).noOcclusion().strength(3.5f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos pos, BlockState state)
    {
        return new BasicBatteryBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type)
    {
        if (level.isClientSide())
        {
            return null;
        } else return (lvl, pos, st, be) -> {
            if (be instanceof BasicBatteryBlockEntity generator) generator.tickServer();
        };
    }

    @Override
    public InteractionResult use (BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!level.isClientSide())
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof BasicBatteryBlockEntity)
            {
                NetworkHooks.openScreen(((ServerPlayer) player), (BasicBatteryBlockEntity) entity, pos);
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else throw new IllegalStateException("Container provider missing.");
        }

        return super.use(state, level, pos, player, hand, hit);
    }

}

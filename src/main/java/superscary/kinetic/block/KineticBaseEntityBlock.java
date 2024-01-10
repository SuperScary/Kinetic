package superscary.kinetic.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public abstract class KineticBaseEntityBlock extends BaseEntityBlock
{

    protected KineticBaseEntityBlock (Properties properties)
    {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(BlockStateProperties.POWERED, false)
                .setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.FACING);
    }

    @Override
    public RenderShape getRenderShape (BlockState state)
    {
        return RenderShape.MODEL;
    }

}

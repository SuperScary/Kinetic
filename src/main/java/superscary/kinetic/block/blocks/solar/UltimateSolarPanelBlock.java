package superscary.kinetic.block.blocks.solar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;
import superscary.kinetic.block.entity.UltimateSolarPanelBlockEntity;

import static superscary.kinetic.register.KineticBlocks.MACHINE_FRAME;

public class UltimateSolarPanelBlock extends KineticBaseEntityBlock
{

    public UltimateSolarPanelBlock ()
    {
        super(Properties.copy(MACHINE_FRAME.get()).noOcclusion().strength(3.5f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos pos, BlockState state)
    {
        return new UltimateSolarPanelBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type)
    {
        if (level.isClientSide())
        {
            return null;
        } else return (lvl, pos, st, be) -> {
            if (be instanceof UltimateSolarPanelBlockEntity generator) generator.tickServer();
        };
    }

}

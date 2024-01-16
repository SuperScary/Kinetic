package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;
import superscary.kinetic.block.entity.PremiumSolarPanelBlockEntity;
import superscary.kinetic.block.entity.StandardSolarPanelBlockEntity;

import static superscary.kinetic.block.KineticBlocks.MACHINE_BASE_BASIC;

public class PremiumSolarPanelBlock extends KineticBaseEntityBlock
{

    public PremiumSolarPanelBlock ()
    {
        super(Properties.copy(MACHINE_BASE_BASIC.get()).noOcclusion().strength(3.5f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos pos, BlockState state)
    {
        return new PremiumSolarPanelBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type)
    {
        if (level.isClientSide())
        {
            return null;
        } else return (lvl, pos, st, be) -> {
            if (be instanceof PremiumSolarPanelBlockEntity generator) generator.tickServer();
        };
    }

}

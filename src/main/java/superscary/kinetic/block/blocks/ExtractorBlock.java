package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;

import static superscary.kinetic.register.KineticBlocks.MACHINE_FRAME;

public class ExtractorBlock extends KineticBaseEntityBlock
{

    public ExtractorBlock ()
    {
        super(BlockBehaviour.Properties.copy(MACHINE_FRAME.get()));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos p_153215_, BlockState p_153216_)
    {
        return null;
    }
}

package superscary.kinetic.block.cables.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBlocks;
import superscary.kinetic.block.cables.blocks.entity.FacadeBlockEntity;
import superscary.kinetic.block.cables.blocks.power.BasicPowerCableBlock;

import javax.annotation.Nonnull;

public class FacadeBlock extends BasicPowerCableBlock implements EntityBlock
{

    public FacadeBlock ()
    {
        super();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (@NotNull BlockPos pos, @NotNull BlockState state)
    {
        return new FacadeBlockEntity(pos, state);
    }

    @NotNull
    @Override
    public VoxelShape getShape (@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        if (world.getBlockEntity(pos) instanceof FacadeBlockEntity facade)
        {
            BlockState mimicBlock = facade.getMimicBlock();
            if (mimicBlock != null)
            {
                return mimicBlock.getShape(world, pos, context);
            }
        }
        return super.getShape(state, world, pos, context);
    }

    // This function is called when the facade block is succesfully harvested by the player
    // When the player destroys the facade we need to drop the facade block item with the correct mimiced block
    @Override
    public void playerDestroy (@Nonnull Level level, @Nonnull Player player, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable BlockEntity te, @Nonnull ItemStack stack)
    {
        ItemStack item = new ItemStack(KineticBlocks.FACADE_BLOCK.get());
        BlockState mimicBlock;
        if (te instanceof FacadeBlockEntity)
        {
            mimicBlock = ((FacadeBlockEntity) te).getMimicBlock();
        } else
        {
            mimicBlock = Blocks.COBBLESTONE.defaultBlockState();
        }
        FacadeBlockItem.setMimicBlock(item, mimicBlock);
        popResource(level, pos, item);
    }

    // When the player destroys the facade we need to restore the cable block
    @Override
    public boolean onDestroyedByPlayer (BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        BlockState defaultState = state.getBlock().defaultBlockState();
        BlockState newState = KineticBaseCable.calculateState(world, pos, defaultState);
        return ((LevelAccessor) world).setBlock(pos, newState, ((LevelAccessor) world).isClientSide()
                ? Block.UPDATE_ALL + Block.UPDATE_IMMEDIATE
                : Block.UPDATE_ALL);
    }

}

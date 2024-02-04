package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;
import superscary.kinetic.block.KineticBaseEntityBlock;
import superscary.kinetic.block.entity.VatBlockEntity;
import superscary.kinetic.register.KineticBlocks;
import superscary.kinetic.util.helpers.FluidUtils;

public class VatBlock extends KineticBaseEntityBlock
{

    public VatBlock ()
    {
        super(BlockBehaviour.Properties.copy(KineticBlocks.MACHINE_FRAME.get()).strength(3.5f).requiresCorrectToolForDrops().noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos blockPos, BlockState blockState)
    {
        return null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type)
    {
        if (level.isClientSide()) return null;
        else
        {
            return (lvl, pos, st, be) -> {
                if (be instanceof VatBlockEntity vat)
                {
                    vat.tickServer();
                }
            };
        }
    }

    @Override
    public InteractionResult use (BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (level.isClientSide())
        {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof VatBlockEntity)
            {
                VatBlockEntity vat = (VatBlockEntity) entity;
                if (player.getItemInHand(hand).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent())
                {
                    player.getItemInHand(hand).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem ->
                    {
                        int drainAmount = Math.min(vat.getFluidTank().getSpace(), 1000);
                        FluidStack stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                        if (stack.isFluidEqual(vat.getFluidTank().getFluid()))
                        {
                            stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                            FluidUtils.fillTank(vat.getFluidTank(), stack, iFluidHandlerItem.getContainer(), player);
                        }
                    });
                }
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void onRemove (BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {

        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity block = level.getBlockEntity(pos);
            if (block instanceof VatBlockEntity)
            {
                ((VatBlockEntity) block).drops();
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

}

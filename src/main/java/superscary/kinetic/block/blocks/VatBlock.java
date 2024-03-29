package superscary.kinetic.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
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
        return new VatBlockEntity(blockPos, blockState);
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
    public @NotNull InteractionResult use (BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof VatBlockEntity)
        {
            if (player.getItemInHand(hand).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent())
            {

                return fillTank(entity, player, hand);
            }
            else
            {
                return putItemInVat((VatBlockEntity) entity, player, hand);
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    /**
     * TODO: Advanced block hits to get items out on hit
     * Puts/Removes item(s) in Vat
     * @param entity
     * @param player
     * @param hand
     * @return
     */
    private InteractionResult putItemInVat (VatBlockEntity entity, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isCrouching())
        {
            if (entity.getItems().getStackInSlot(0).isEmpty() || entity.getItems().getStackInSlot(0) == stack)
            {
                handleItemInput(entity, player, stack, 0);
                return InteractionResult.SUCCESS;
            }
            else if (entity.getItems().getStackInSlot(1).isEmpty() || entity.getItems().getStackInSlot(1) == stack)
            {
                handleItemInput(entity, player, stack, 1);
                return InteractionResult.SUCCESS;
            }
            else return InteractionResult.FAIL;
        }
        else
        {
            entity.getItems().extractItem(0, entity.getItems().getStackInSlot(0).getCount(), false);
            entity.getItems().extractItem(1, entity.getItems().getStackInSlot(1).getCount(), false);
            return InteractionResult.SUCCESS;
        }
    }

    private void handleItemInput (VatBlockEntity entity, Player player, ItemStack stack, int slot)
    {
        ItemStack s0 = entity.getItems().getStackInSlot(slot);

        int amount = 0;
        int remainder = 0;
        int math = maxAdd(s0, stack, s0.getMaxStackSize());
        if (math <= -1)
        {
            amount = 64;
            remainder = math * -1;
            player.getInventory().setItem(player.getInventory().selected, new ItemStack(stack.getItem(), remainder));
        }

        if (math == 0)
        {
            amount = 64;
        }

        if (math >= 1)
        {
            amount = math;
        }

        entity.getItems().setStackInSlot(slot, new ItemStack(stack.getItem(), amount));
        if (!player.isCreative()) player.getInventory().setItem(player.getInventory().selected, ItemStack.EMPTY);
    }

    /**
     * Returns either the combined size of the two stacks, or the difference from maxCount.
     * @param s0 first stack
     * @param s1 second stack
     * @param maxCount max stack count
     * @return
     */
    private int maxAdd(ItemStack s0, ItemStack s1, int maxCount)
    {
        int v = s0.getCount();
        int h = s1.getCount();
        if (h + v <= maxCount) return v + h;
        else
        {
            return maxCount - (h + v);
        }
    }

    // TODO: Fill fluidhandler if player is sneaking and holding a valid fluid handler
    private InteractionResult fillTank (BlockEntity entity, Player player, InteractionHand hand)
    {
        VatBlockEntity vat = (VatBlockEntity) entity;
        player.getItemInHand(hand).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem ->
        {
            if (!player.isCrouching())
            {
                int drainAmount = Math.min(vat.getFluidTank().getSpace(), 1000);
                FluidStack stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
                if ((stack.isFluidEqual(vat.getFluidTank().getFluid()) || vat.getFluidTank().getFluid().isEmpty()))
                {
                    stack = iFluidHandlerItem.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                    FluidUtils.fillTank(vat.getFluidTank(), stack, iFluidHandlerItem.getContainer(), player);
                }
            }
            else
            {
                int drainAmount = 1000;
                if (vat.getFluidTank().getFluidAmount() >= drainAmount)
                {
                    FluidStack stack = vat.getFluidTank().drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                    Item item = stack.getFluid().getBucket();
                    player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
                    player.getInventory().add(new ItemStack(item, 1));
                }
            }
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove (BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {

        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity block = level.getBlockEntity(pos);
            if (block instanceof VatBlockEntity)
            {
                VatBlockEntity entity = (VatBlockEntity) block;
                entity.drops();
                if (entity.getFluidTank().getFluidAmount() == entity.getFluidTank().getCapacity())
                {
                    Block block1 = entity.getFluidTank().getFluid().getFluid().defaultFluidState().createLegacyBlock().getBlock();
                    level.setBlock(pos, block1.defaultBlockState(), 0);
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

}

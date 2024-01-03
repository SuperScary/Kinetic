package superscary.kinetic.network.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidSyncS2CPacket
{
    private final FluidStack inputFluidStack;
    private final BlockPos pos;

    public FluidSyncS2CPacket (FluidStack fluidStack, BlockPos pos)
    {
        this.inputFluidStack = fluidStack;
        this.pos = pos;
    }

    public FluidSyncS2CPacket (FriendlyByteBuf buf)
    {
        this.inputFluidStack = buf.readFluidStack();
        this.pos = buf.readBlockPos();
    }

    public void toBytes (FriendlyByteBuf buf)
    {
        buf.writeFluidStack(inputFluidStack);
        buf.writeBlockPos(pos);
    }

    public boolean handle (Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {

        });
        return true;
    }
}
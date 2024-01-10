package superscary.kinetic.network.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MultiFluidSyncS2CPacket
{
    private final List<FluidStack> fluidStacks;
    private final BlockPos pos;

    public MultiFluidSyncS2CPacket (List<FluidStack> fluidStacks, BlockPos pos)
    {
        this.fluidStacks = fluidStacks;
        this.pos = pos;
    }

    public MultiFluidSyncS2CPacket (FriendlyByteBuf buf)
    {
        this.fluidStacks = buf.readCollection(ArrayList::new, FriendlyByteBuf::readFluidStack);
        this.pos = buf.readBlockPos();
    }

    public void toBytes (FriendlyByteBuf buf)
    {
        buf.writeCollection(fluidStacks, FriendlyByteBuf::writeFluidStack);
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

package superscary.kinetic.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import superscary.kinetic.block.SawmillBlock;
import superscary.kinetic.block.entity.CompressorBlockEntity;
import superscary.kinetic.gui.CompressorMenu;
import superscary.kinetic.gui.SawmillMenu;

import java.util.function.Supplier;

public class EnergySyncS2CPacket
{

    private final int energy;
    private final BlockPos pos;

    public EnergySyncS2CPacket (int energy, BlockPos pos)
    {
        this.energy = energy;
        this.pos = pos;
    }

    public EnergySyncS2CPacket (FriendlyByteBuf buf)
    {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes (FriendlyByteBuf buf)
    {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle (Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {


            /*if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof SawmillBlock blockEntity)
            {
                blockEntity.setEnergyLevel(energy);

                if (Minecraft.getInstance().player.containerMenu instanceof SawmillMenu menu && menu.blockEntity.getBlockPos().equals(pos))
                {
                    blockEntity.setEnergyLevel(energy);
                }

            }*/

        });

        return true;
    }

}

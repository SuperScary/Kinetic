package superscary.kinetic.util;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage
{

    public ModEnergyStorage (int capacity, int maxTransfer)
    {
        super(capacity, maxTransfer);
    }

    @Override
    public int extractEnergy (int max, boolean simulate)
    {
        int extracted = super.extractEnergy(max, simulate);
        if (extracted != 0)
        {
            onEnergyChanged();
        }

        return extracted;
    }

    @Override
    public int receiveEnergy (int max, boolean simulate)
    {
        int receive = super.receiveEnergy(max, simulate);
        if (receive != 0)
        {
            onEnergyChanged();
        }

        return receive;
    }

    public int setEnergy (int energy)
    {
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();

}

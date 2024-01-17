package superscary.kinetic.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class CapacitorItem extends Item
{

    private final int maxCap;
    private final int maxTrans;
    private final int rarity;

    private final IEnergyStorage energy = createEnergyStorage();
    private final LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.of(() -> energy);

    public CapacitorItem (Properties properties, int maxCap, int maxTrans, int rarity)
    {
        super(properties.durability(maxCap).defaultDurability(0));
        this.maxCap = maxCap;
        this.maxTrans = maxTrans;
        this.rarity = rarity;
    }

    public CapacitorItem (int maxCap, int maxTrans, int rarity)
    {
        this (new Item.Properties().stacksTo(1), maxCap, maxTrans, rarity);
    }

    @Override
    public Rarity getRarity (ItemStack pStack)
    {
        return switch (rarity)
        {
            default -> Rarity.COMMON;
            case 2 -> Rarity.UNCOMMON;
            case 3 -> Rarity.RARE;
            case 4 -> Rarity.EPIC;
        };
    }

    private IEnergyStorage createEnergyStorage ()
    {
        return new EnergyStorage(getMaxCapacity(), getMaxTransfer(), getMaxTransfer());
    }

    public int getMaxCapacity ()
    {
        return maxCap;
    }

    public int getMaxTransfer ()
    {
        return maxTrans;
    }

}

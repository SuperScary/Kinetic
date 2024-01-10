package superscary.kinetic.util.energy;

import superscary.kinetic.item.CapacitorItem;

public interface CapacitorModifiable
{

    /**
     * The slot ID for the capacitor
     * @return
     */
    int getCapacitorSlot ();

    /**
     * If there is a valid capacitor in the assigned inventory slot
     * @return true if capacitor is in the capacitor slot
     */
    boolean capacitorSlotValid ();

    CapacitorItem getCapacitor ();

}

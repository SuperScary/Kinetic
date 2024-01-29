package superscary.kinetic.item.properties;

import net.minecraft.world.food.FoodProperties;

public class FoodProp
{

    public static final FoodProperties HARD_BOILED_EGG = new FoodProperties.Builder().nutrition(3).saturationMod(2.5f).build();
    public static final FoodProperties HONEY_BUN = new FoodProperties.Builder().nutrition(4).saturationMod(3).alwaysEat().build();

}

package hacks.epstein.peanut.modules;

import hacks.epstein.peanut.PeanutClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Comparator;

public class AutoEat extends Hack{
    private int oldSlot = 0;
    private boolean eating;
    public void toggle(){
        super.toggle();
    }
    public void tick(){
        if(this.enabled){
            var player = MinecraftClient.getInstance().player;
            if(player != null){
                var hunger = player.getHungerManager().getFoodLevel();
                if(hunger < 15){
                    var bestIndex = getBestFood();
                    oldSlot = player.getInventory().selectedSlot;
                    player.getInventory().selectedSlot = bestIndex;
                    MinecraftClient.getInstance().options.useKey.setPressed(true);
                    eating = true;
                }else{
                    if(eating){
                        stopEating();
                        eating = false;
                    }
                }
            }
        }
    }
    private void stopEating(){
        MinecraftClient.getInstance().options.useKey.setPressed(false);
        MinecraftClient.getInstance().player.getInventory().selectedSlot = oldSlot;
    }
    private int getBestFood(){
        var mainInv = MinecraftClient.getInstance().player.getInventory().main;
        var bestIndex = 0;
        FoodComponent bestFood = null;
        Comparator<FoodComponent> comparator =
                Comparator.comparingDouble(FoodComponent::saturation);

        for(var i = 0; i < 9; i++){
            ItemStack stack = mainInv.get(i);
            if(mainInv.get(i).contains(DataComponentTypes.FOOD)) {
                FoodComponent food = stack.get(DataComponentTypes.FOOD);
                if(bestFood == null || comparator.compare(food, bestFood) > 0){
                    bestFood = food;
                    bestIndex = i;
                }
            }
        }
        return bestIndex;
    }
}

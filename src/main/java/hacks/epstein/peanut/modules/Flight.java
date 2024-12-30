package hacks.epstein.peanut.modules;
import net.minecraft.client.MinecraftClient;

public class Flight extends Hack{
    public void toggle(){
        super.toggle();
        var player = MinecraftClient.getInstance().player;
        if(player == null){
            return;
        }
        player.getAbilities().allowFlying = this.enabled;
    }
    @Override
    public void tick(){
        var player = MinecraftClient.getInstance().player;
        if(player != null){
            if(!player.isCreative()){
                player.getAbilities().allowFlying = this.enabled;
            }
        }

    }
}
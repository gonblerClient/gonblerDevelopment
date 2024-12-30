package hacks.epstein.peanut.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;

public class AutoLog extends Hack{
    public void toggle(){
        super.toggle();
    }

    @Override
    public void tick(){
        var instance = MinecraftClient.getInstance();
        var player = instance.player;
        if(player != null && this.enabled){
            var health = player.getHealth();
            if(health < 10){
                player.networkHandler.sendPacket(new DisconnectS2CPacket(Text.of("AutoLog was triggered!")));
            }
        }
    }
}

package hacks.epstein.peanut.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Hack{
    private boolean fast(ClientPlayerEntity player){
        return player.getVelocity().y < -0.5;
    }
    public void toggle(){
        super.toggle();
    }
    @Override
    public void tick(){
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(this.isEnabled() && player != null){
            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, player.horizontalCollision));
        }
    }


}

package hacks.epstein.peanut.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Hack{
    private boolean fast(ClientPlayerEntity player){
        return player.getVelocity().y < -0.5;
    }
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    @Override
    public void tick(){
        if(this.isEnabled() && player != null){
            if(player.isCreative()){
                return;
            }

            if(player.isGliding()){
                return;
            }

            if(player.isGliding() && player.isSneaking() && !fast(player)){
                return;
            }

            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, player.horizontalCollision));
        }
    }


}

package hacks.epstein.peanut.modules;

import java.util.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.report.log.ChatLog;
import net.minecraft.client.session.report.log.ChatLogEntry;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.c2s.play.ChatCommandSignedC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.Text;

public class AntiAFK extends Hack{
    Random rand = new Random();
    public void toggle(){
        super.toggle();
    }
    @Override
    public void tick(){
        var player = MinecraftClient.getInstance().player;
        var mc = MinecraftClient.getInstance();
        var action = rand.nextInt(9);
        var messageChance = rand.nextInt(99);
        if(super.isEnabled() && player != null) {
            if (action == 1) {
                mc.options.jumpKey.setPressed(!mc.options.jumpKey.isPressed());
            } else if (action == 2) {
                mc.options.leftKey.setPressed(!mc.options.leftKey.isPressed());
            } else if (action == 3) {
                mc.options.rightKey.setPressed(!mc.options.rightKey.isPressed());
            } else if (action == 4) {
                mc.options.forwardKey.setPressed(!mc.options.forwardKey.isPressed());
            } else if (action == 5) {
                mc.options.backKey.setPressed(!mc.options.backKey.isPressed());
            } else if (action == 6) {
                mc.options.sneakKey.setPressed(!mc.options.sneakKey.isPressed());
            } else if (action == 7) {
                mc.options.attackKey.setPressed(!mc.options.attackKey.isPressed());
            } else if (action == 8 && messageChance == 7){
                mc.inGameHud.getChatHud().addMessage(Text.of("Gonbler client is based"));
                mc.player.networkHandler.sendChatMessage("Gonbler client is based");
            }
        }
    }
}
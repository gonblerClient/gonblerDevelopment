package hacks.epstein.peanut;

import hacks.epstein.peanut.modules.AntiAFK;
import hacks.epstein.peanut.modules.Flight;
import hacks.epstein.peanut.modules.Hack;
import hacks.epstein.peanut.modules.NoFall;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.awt.*;
import java.util.*;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeanutClient implements ModInitializer{
	public static final String MOD_ID = "peanut-client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final List<Hack> HACKS = new ArrayList<>();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		HACKS.add(new Flight());
		HACKS.add(new NoFall());
		HACKS.add(new AntiAFK());
		HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
			var textRenderer = MinecraftClient.getInstance().textRenderer;
			drawContext.drawText(textRenderer, "gonbler Client", 5, 5, 0xffffffff, true);
			var instance = MinecraftClient.getInstance();
			var window = instance.getWindow();
			var player = instance.player;
			var playerX = player.getX();
			var playerY = player.getY();
			var playerZ = player.getZ();
			drawContext.drawText(textRenderer, "X: " + Math.round(playerX) + " Y: " + Math.round(playerY) + " Z: "+ Math.round(playerZ), 5, 17, 0xffffffff, true);
			showEnabled(drawContext, textRenderer);
		});
		ClientTickEvents.START_CLIENT_TICK.register(client ->
				HACKS.forEach(hack -> {
							hack.tick();
						}
				)
		);
	}
	public static void showEnabled(DrawContext context, TextRenderer renderer){
		var offset = 29;
		for(var hack : getHacks()){
			if(hack.isEnabled()){
				context.drawText(renderer, hack.getClass().getSimpleName(), 5, offset, 0xffff00ff, true);
				offset+=12;
			}
		}
	}

	private static <T extends Hack> T getHack(Class<T> hackClass) {
		for (var hack : HACKS) {
			if (hack.getClass() == hackClass) {
				return hackClass.cast(hack);
			}
		}
		return null;
	}
	public static boolean isEnabled(Class<? extends Hack> hackClass) {
		var hack = getHack(hackClass);
		return hack != null && hack.isEnabled();
	}
	public static void toggle(Class<? extends Hack> hackClass) {
		var hack = getHack(hackClass);
		if (hack != null) {
			hack.toggle();
		}
	}
	public static List<Hack> getHacks() {
		LOGGER.info(HACKS.get(0).getClass().getName());
		return HACKS;
	}
}
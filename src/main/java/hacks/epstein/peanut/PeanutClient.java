package hacks.epstein.peanut;


import hacks.epstein.peanut.modules.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeanutClient implements ModInitializer{
	public static final String MOD_ID = "peanut-client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final List<Hack> HACKS = new ArrayList<>();
	private KeyBinding menuButton;


	private static void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
		var textRenderer = MinecraftClient.getInstance().textRenderer;
		drawContext.drawText(textRenderer, "gonbler Client", 5, 5, 0xffffffff, true);
		var instance = MinecraftClient.getInstance();
		var player = instance.player;
		var playerX = player.getX();
		var playerY = player.getY();
		var playerZ = player.getZ();
		drawContext.drawText(textRenderer, "X: " + Math.round(playerX) + " Y: " + Math.round(playerY) + " Z: " + Math.round(playerZ), 5, 17, 0xffffffff, true);
		showEnabled(drawContext, textRenderer);
		if (getHack(Dexter.class).isEnabled()) {
			// Identifier dexterTexture = Identifier.of("epstein","dexter.png");
			var dW = 284;
			var dH = 909;
			var dSizeDown = 2;
			Identifier texture = Identifier.of("peanut-client", "dexter.png");
			drawContext.drawTexture(RenderLayer::getGuiTextured, texture, 0, 0, 0, 0, dW/dSizeDown, dH/dSizeDown, dW/dSizeDown, dH/dSizeDown);
		}
	}

	@Override
	public void onInitialize() {
		HACKS.add(new Flight());
		HACKS.add(new NoFall());
		HACKS.add(new AntiAFK());
		HACKS.add(new Dexter());
		HACKS.add(new AutoEat());
		HACKS.add(new AutoLog());
		menuButton = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"rightShift.epstein.gonblerClient",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_RIGHT_SHIFT,
				"menu.epstein.gonblerClient"
		));
		HudRenderCallback.EVENT.register(PeanutClient::onHudRender);
		ClientTickEvents.START_CLIENT_TICK.register(client ->
				{
					HACKS.forEach(hack -> {
								hack.tick();
							}
					);
					if(menuButton.wasPressed()){
						if(MinecraftClient.getInstance() != null){
							MinecraftClient.getInstance().setScreen(new HackScreen(null));
						}
					}
				}
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
		return HACKS;
	}
}
package hacks.epstein.peanut.mixin;

import hacks.epstein.peanut.PeanutClient;
import hacks.epstein.peanut.modules.HackScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.AbstractTextWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }
    @Inject(method = "initWidgets", at=@At("HEAD"))
    void initWidgets(CallbackInfo ci){
        var buttonWidget = ButtonWidget.builder(
                Text.of("Gonbler"),
                button -> {
                    if(MinecraftClient.getInstance() != null){
                        MinecraftClient.getInstance().setScreen(new HackScreen(this));
                    }
                }
        ).position(5,5).build();
        this.addDrawableChild(buttonWidget);
    }
}
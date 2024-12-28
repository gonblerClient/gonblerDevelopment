package hacks.epstein.peanut.modules;

import hacks.epstein.peanut.PeanutClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import java.util.Map;
import java.util.HashMap;

public class HackScreen extends Screen {
    private final Screen parent;
    private final Map<ButtonWidget, Class<? extends Hack>> hackButtons = new HashMap<>();
    public HackScreen(Screen parent){
        super(Text.translatable("options.title"));
        this.parent = parent;
    }

    @Override
    protected void init(){
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(2);

        adder.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.client.setScreen(this.parent)).width(200).build(), 2, adder.copyPositioner().marginTop(6));
        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.3f, 0.0f);
        for (var hack : PeanutClient.getHacks()) {
            this.hackButtons.put(adder.add(ButtonWidget.builder(
                            Text.of("Test"),
                            button -> PeanutClient.toggle(hack.getClass())
                    ).build()),
                    hack.getClass()
            );
        }
        gridWidget.refreshPositions();
        gridWidget.forEachChild(this::addDrawableChild);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Change the text on the buttons depending on if the hacks are enabled
        for (var entry : this.hackButtons.entrySet()) {
            var button = entry.getKey();
            var clazz = entry.getValue();

            button.setMessage(Text.of(enabledText(clazz)));
        }
        super.render(context, mouseX, mouseY, delta);
    }
    /**
     * A simple utility function to generate the enabled text of a hack
     * @param clazz the clazz to get the enabled text for
     * @return      the generated text
     */
    private Text enabledText(Class<? extends Hack> clazz) {
        var text = clazz.getSimpleName() + " is ";
        text += PeanutClient.isEnabled(clazz) ? "enabled" : "disabled";
        return Text.of(text);
    }
}
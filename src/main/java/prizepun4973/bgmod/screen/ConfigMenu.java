package prizepun4973.bgmod.screen;

import prizepun4973.bgmod.BGMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ConfigMenu extends Screen {

    public TextFieldWidget scroll;

    public ConfigMenu() {
        super(Text.of("idk"));
    }

    @Override
    public void init(){
        super.init();
        scroll = new TextFieldWidget(textRenderer, (width / 2) - (50 / 2) - 3, height - 22, 50, 20, Text.of(""));
        scroll.setText(String.valueOf(BGMod.scroll));
        addDrawableChild(scroll);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, "FPS: ", width / 2, height - 30, 16777215);
    }

    @Override
    public void close() {
        try { BGMod.scroll = Integer.parseInt(scroll.getText()); }
        catch (NumberFormatException ignored) {}
        BGMod.cfg();
        MinecraftClient.getInstance().setScreen(new MenuScreen());
    }
}

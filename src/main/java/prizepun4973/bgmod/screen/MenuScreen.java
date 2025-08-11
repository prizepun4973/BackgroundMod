package prizepun4973.bgmod.screen;

import prizepun4973.bgmod.widget.ReButton;
import com.terraformersmc.modmenu.gui.ModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends Screen {

    private final int start = -100;
    private final int end = 20;

    private double x;

    private int target;

    private List<Drawable> drawables = new ArrayList<>();

    public MenuScreen(){
        super(Text.literal("Menu"));
    }

    @Override
    public void init(){

        x = start;
        target = end;

        addDrawableChild(new ReButton(start, height / 2 - 44, 91, 20, Text.translatable("menu.singleplayer"), 255,
                () -> MinecraftClient.getInstance().setScreen(new SelectWorldScreen(this))));

        addDrawableChild(new ReButton(start, height / 2 - 22, 91, 20, Text.translatable("menu.multiplayer"), 255,
                () -> MinecraftClient.getInstance().setScreen(new MultiplayerScreen(this))));

        addDrawableChild(new ReButton(start, height / 2, 91, 20, Text.literal("Realms"), 255,
                () -> MinecraftClient.getInstance().setScreen(new RealmsMainScreen(this))));

        addDrawableChild(new ReButton(start, height / 2 + 22, 91, 20, Text.translatable("menu.options"), 255,
                () -> MinecraftClient.getInstance().setScreen(new OptionsScreen(this, MinecraftClient.getInstance().options))));

        addDrawableChild(new ReButton(start, height / 2 + 44, 91, 20, Text.translatable("modmenu.title"), 255,
                () -> MinecraftClient.getInstance().setScreen(new ModsScreen(this))));

        addDrawableChild(new ReButton(start, height / 2 + 66, 91, 20, Text.translatable("wallpaper.cfgmenu"), 255,
                () -> MinecraftClient.getInstance().setScreen(new ConfigMenu())));

        addDrawableChild(new ReButton(start, height / 2 + 88, 91, 20, Text.translatable("menu.quit"), 255,
                client::scheduleStop));
    }

    @Override
    protected <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
        super.addDrawableChild(drawableElement);
        drawables.add(drawableElement);
        return addSelectableChild(drawableElement);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            if (target == end) target = start;
            else if (target == start) target = end;
            return true;
        }
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        super.render(context, mouseX, mouseY, delta);

        int scale = client.options.getGuiScale().getValue();
        if (scale == 0) scale = client.getWindow().calculateScaleFactor(0, client.forcesUnicodeFont());
        x += (target - x) * scale / 50 / client.getWindow().calculateScaleFactor(0, client.forcesUnicodeFont());

        for (Drawable i : drawables) {
            if (i instanceof ReButton) {
                ((ReButton) i).setX((int) x);
            }
        }
    }
}

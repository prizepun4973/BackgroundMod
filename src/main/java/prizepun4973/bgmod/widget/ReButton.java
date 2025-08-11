package prizepun4973.bgmod.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ReButton extends ClickableWidget {

    private int change = 0;
    private final int OUTLINE_WIDTH = 1;

    private Runnable callback = () -> {};

    public int alpha = 255;
    public double alphaFactor = 0.5;

//    public ReButton(int x, int y, int width, int height, Text message) {
//        super(x, y, width, height, message);
//    }
//
//    public ReButton(int x, int y, int width, int height, Text message, int alpha) {
//        super(x, y, width, height, message);
//        this.alpha = alpha;
//    }
//
//    public ReButton(int x, int y, int width, int height, Text message, @Nullable Runnable callback) {
//        super(x, y, width, height, message);
//        this.callback = callback;
//    }

    public ReButton(int x, int y, int width, int height, Text message, int alpha, @Nullable Runnable callback) {
        super(x, y, width, height, message);
        this.alpha = alpha;
        this.callback = callback;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (hovered && change < 255) change += 17;
        if (!hovered && change > 0)  change -= 17;

        context.fill(getX(), getY(), getRight(), getBottom(), new Color(255, 255, 255, (int) (alpha * alphaFactor)).hashCode());
        context.fill(getX() + OUTLINE_WIDTH, getY() + OUTLINE_WIDTH, getRight() - OUTLINE_WIDTH, getBottom() - OUTLINE_WIDTH, new Color(change, change, change, (int) (alpha * alphaFactor)).hashCode());
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, getMessage(), getX() + getWidth() / 2, getY() + getHeight() / 4 + OUTLINE_WIDTH, new Color(255, 255, 255, alpha).hashCode());
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        callback.run();
    }
}

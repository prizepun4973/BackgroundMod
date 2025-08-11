package prizepun4973.bgmod;

import prizepun4973.bgmod.screen.ConfigMenu;
import prizepun4973.bgmod.screen.MenuScreen;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import org.slf4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class BGMod implements ClientModInitializer {
    public static Logger LOG = LogUtils.getLogger();
    public static String namespace = "bgmod";

    public static Path config = FabricLoader.getInstance().getConfigDir().resolve(namespace + ".json");

    public static ArrayList<Identifier> imagePool = new ArrayList<>();
    public static ArrayList<Integer> imageWidths = new ArrayList<>();
    public static ArrayList<Integer> imageHeights = new ArrayList<>();

    public static double curFrame = 0;

    public static int x , y;
    public static int scroll = 30;

    public static int width, height;

    @Override
    public void onInitializeClient() {
        LOG.info("loading BackgroundMod");
    }

    public static void render(DrawContext context){
        width = MinecraftClient.getInstance().currentScreen.width;
        height = MinecraftClient.getInstance().currentScreen.height;

        if (curFrame >= (imagePool.size())) curFrame = 0;

        if (!imagePool.isEmpty()) {

            int i = MathHelper.floor(curFrame);

            // 自适应
            int w = imageWidths.get(i);
            int h = imageHeights.get(i);

            if (((double) width / height) >= ((double) w / h)) {
                h = (int) (width / ((double) w / h));
                w = width;
            } else {
                w = (int) (height * ((double) w / h));
                h = height;
            }

            context.drawTexture(imagePool.get(i), x, y, 0, 0, w, h, w, h); // 1.21.1

            if (scroll < MinecraftClient.getInstance().getCurrentFps()) curFrame += ((double) scroll / MinecraftClient.getInstance().getCurrentFps());
            else curFrame += ((double) MinecraftClient.getInstance().getCurrentFps() / scroll);
        }

        if (!(MinecraftClient.getInstance().currentScreen instanceof TitleScreen) &&
            !(MinecraftClient.getInstance().currentScreen instanceof MenuScreen) &&
            !(MinecraftClient.getInstance().currentScreen instanceof ConfigMenu))
            context.fill(0, 0, width, height, new Color(0, 0, 0, 50).hashCode());
    }

    public static void cfg(){
        JsonObject cfg = new JsonObject();
        cfg.addProperty("scroll", scroll);
        try {
            Files.createDirectories(config.getParent());
            Files.writeString(config, JsonHelper.toSortedString(cfg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

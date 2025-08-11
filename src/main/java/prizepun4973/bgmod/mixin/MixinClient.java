package prizepun4973.bgmod.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import prizepun4973.bgmod.BGMod;
import prizepun4973.bgmod.screen.MenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.NoSuchElementException;
import java.util.Optional;

@Mixin(MinecraftClient.class)
public class MixinClient {

    @Inject(method = "onFinishedLoading", at = @At("TAIL"))
    private void postInit(MinecraftClient.LoadingContext loadingContext, CallbackInfo ci){
        if (!Files.exists(BGMod.config)) {
            BGMod.cfg();
            return;
        }
        else try (BufferedReader reader = Files.newBufferedReader(BGMod.config)) { BGMod.scroll = JsonHelper.getInt(JsonHelper.deserialize(reader), "scroll", 30); } catch (Exception e) { BGMod.cfg(); }

        BGMod.imagePool.clear();
        BGMod.imageWidths.clear();
        BGMod.imageHeights.clear();

        Optional<Resource> meta = MinecraftClient.getInstance().getResourceManager().getResource(Identifier.of(BGMod.namespace, "config.json"));
        String prefix = "";
        try (BufferedReader metaInput = meta.get().getReader()) {
            prefix = JsonHelper.getString(JsonHelper.deserialize(metaInput), "prefix", "");
            BGMod.LOG.info(prefix);
        }
        catch (Exception ignored){}

        int i = 0;
        while (true) {
            BGMod.imagePool.add(Identifier.of(BGMod.namespace,  "img/" + prefix + (i + 1) + ".png"));
            try {

                BGMod.LOG.info((i + 1) + " w:" + getImage(BGMod.imagePool.get(i)).getWidth() + " h:" + getImage(BGMod.imagePool.get(i)).getHeight());
                BGMod.imageWidths.add(getImage(BGMod.imagePool.get(i)).getWidth());
                BGMod.imageHeights.add(getImage(BGMod.imagePool.get(i)).getHeight());
            }
            catch (NoSuchElementException e){
                BGMod.LOG.info("load complete");
                BGMod.imagePool.removeLast();
                break;
            }
            i++;
        }

        BGMod.curFrame = 0;
    }

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void noTitleScreen(Screen screen, CallbackInfo ci){
        if (screen instanceof TitleScreen) {
            MinecraftClient.getInstance().setScreen(new MenuScreen());
            ci.cancel();
        }
    }
    
    @Unique
    private static BufferedImage getImage(Identifier identifier) { try { return ImageIO.read(MinecraftClient.getInstance().getResourceManager().getResource(identifier).get().getInputStream()); } catch (IOException e) { return null; } }
}

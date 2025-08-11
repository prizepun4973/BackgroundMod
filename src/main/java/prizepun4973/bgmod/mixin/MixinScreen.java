package prizepun4973.bgmod.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import prizepun4973.bgmod.BGMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class MixinScreen {
    @Inject(method = "renderDarkening(Lnet/minecraft/client/gui/DrawContext;)V", at = @At("TAIL"))
    private void bg(DrawContext context, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null ||
                client.currentScreen instanceof MessageScreen ||
                client.currentScreen instanceof DownloadingTerrainScreen ||
                client.currentScreen instanceof CreateWorldScreen) {
            BGMod.render(context);
        }
    }
}

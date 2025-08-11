package prizepun4973.bgmod.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class MixinCreateWorldScreen {
    @Inject(method = "renderDarkening(Lnet/minecraft/client/gui/DrawContext;)V", at = @At("HEAD"), cancellable = true)
    private void bg(DrawContext context, CallbackInfo ci) { ci.cancel(); }
}

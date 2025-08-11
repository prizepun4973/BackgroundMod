package prizepun4973.bgmod.mixin;

import prizepun4973.bgmod.BGMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RotatingCubeMapRenderer.class)
public class MixinPanorama {

    @Inject(method = "render", at = @At("TAIL"))
    private void bg(DrawContext context, int width, int height, float alpha, float tickDelta, CallbackInfo ci){
        BGMod.width = width;
        BGMod.height = height;
        BGMod.render(context);
    }
}

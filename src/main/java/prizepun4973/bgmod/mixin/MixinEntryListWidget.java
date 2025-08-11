package prizepun4973.bgmod.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(EntryListWidget.class)
public abstract class MixinEntryListWidget extends ContainerWidget {
    public MixinEntryListWidget(int i, int j, int k, int l, Text text) {
        super(i, j, k, l, text);
    }

    @Inject(method = "drawMenuListBackground", at = @At("HEAD"), cancellable = true)
    private void draw(DrawContext context, CallbackInfo ci){
        context.fill(this.getX(), this.getY(), getRight(), getBottom(), new Color(0, 0, 0, 114).hashCode());
        ci.cancel();
    }
}

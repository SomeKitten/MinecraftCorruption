package space.cutekitten.corruption.mixin;

import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.cutekitten.corruption.client.ClientDB;
import space.cutekitten.corruption.screen.CorruptionOptionsScreen;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin {
    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        OptionsScreenAccessor thisAccessor = (OptionsScreenAccessor) this;
        thisAccessor.invokeAddDrawableChild(new ButtonWidget(
                thisAccessor.getWidth() / 2 - 100,
                thisAccessor.getHeight() / 6 + 144 - 6,
                200,
                20,
                Text.translatable("options.corruption.title"),
                button -> {
                    ClientDB.client.setScreen(new CorruptionOptionsScreen((OptionsScreen) (Object) this, ClientDB.client.options));
                }
        ));
    }
}

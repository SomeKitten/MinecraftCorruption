package space.cutekitten.corruption.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface OptionsScreenAccessor {
    @Invoker("addDrawableChild")
    public <T extends Element & Drawable & Selectable> T invokeAddDrawableChild(T drawableElement);

    @Accessor("width")
    public int getWidth();

    @Accessor("height")
    public int getHeight();
}

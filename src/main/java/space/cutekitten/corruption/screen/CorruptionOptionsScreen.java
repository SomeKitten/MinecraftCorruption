package space.cutekitten.corruption.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import space.cutekitten.corruption.client.ClientDB;

public class CorruptionOptionsScreen extends SimpleOptionsScreen {
    private static final SimpleOption<Integer> RANDOMIZE_BLOCKS = new SimpleOption<>(
            "options.corruption.randomizeblocks",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> {
                if (value == 0) {
                    return GameOptions.getGenericValueText(optionText, Text.translatable("options.randomizeblocks.off"));
                } else if (value < 50) {
                    return GameOptions.getGenericValueText(optionText, Text.translatable("options.randomizeblocks.normal").append(" | " + value));
                } else if (value < 100) {
                    return GameOptions.getGenericValueText(optionText, Text.translatable("options.randomizeblocks.high").append(" | " + value));
                }
                return GameOptions.getGenericValueText(optionText, Text.translatable("options.randomizeblocks.max"));
            },
            new SimpleOption.ValidatingIntSliderCallbacks(0, 100),
            5,
            value -> ClientDB.randomizeBlocks = ClientDB.sliderConversion(value)
    );

    private static final SimpleOption<Integer> CONVERT_ENTITIES = new SimpleOption<>(
            "options.corruption.convertentities",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> {
                if (value == 0) {
                    return GameOptions.getGenericValueText(optionText, Text.translatable("options.convertentities.off"));
                } else if (value < 50) {
                    return GameOptions.getGenericValueText(optionText, Text.translatable("options.convertentities.normal").append(" | " + value));
                } else if (value < 100) {
                    return GameOptions.getGenericValueText(optionText, Text.translatable("options.convertentities.high").append(" | " + value));
                }
                return GameOptions.getGenericValueText(optionText, Text.translatable("options.convertentities.max"));
            },
            new SimpleOption.ValidatingIntSliderCallbacks(0, 100),
            5,
            value -> ClientDB.convertEntities = ClientDB.sliderConversion(value)
    );

    private static SimpleOption<?>[] getOptions() {
        return new SimpleOption[]{
                CorruptionOptionsScreen.RANDOMIZE_BLOCKS, CorruptionOptionsScreen.CONVERT_ENTITIES
        };
    }

    public CorruptionOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions, Text.translatable("options.corruption.title"), CorruptionOptionsScreen.getOptions());
    }
}

package space.cutekitten.corruption.client;

import net.minecraft.client.MinecraftClient;

public class ClientDB {
    public static MinecraftClient client = MinecraftClient.getInstance();

    public static int randomizeBlocks = 25;
    public static int convertEntities = 25;
    public static int sliderConversion(int value) {
//        magic values to get the slider to FEEL good
        return (int) (9 * (Math.pow(1.4, ((double) value) / 3) - 1));
    }
    public static int sliderMax = sliderConversion(100);
}

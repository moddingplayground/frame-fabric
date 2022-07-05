package net.moddingplayground.frame.test.contentregistries.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.moddingplayground.frame.api.contentregistries.v0.client.SplashesRegistry;

@Environment(EnvType.CLIENT)
public class FrameContentRegistriesTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SplashesRegistry splashes = SplashesRegistry.INSTANCE;
        splashes.register(Text.literal("A custom splash text, from Frame!").setStyle(Style.EMPTY.withBold(true)));
        splashes.register(Text.translatable("text.frame-content-registries-test.splash.translatable"));
        splashes.register(() -> Text.literal("Top tip! Press ").append(Text.keybind("key.drop")).append(" to drop an item!"));
        splashes.register("Basic splash text");
        splashes.register("Oooooh! Custom colour codes!", 0xFF0000);
        splashes.register("MOAR formatting :)", Formatting.UNDERLINE);
    }
}

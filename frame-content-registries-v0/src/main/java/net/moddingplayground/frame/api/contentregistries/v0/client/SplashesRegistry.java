package net.moddingplayground.frame.api.contentregistries.v0.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.moddingplayground.frame.impl.contentregistries.client.SplashesRegistryImpl;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public interface SplashesRegistry {
    SplashesRegistry INSTANCE = new SplashesRegistryImpl();

    void register(Supplier<Text> text);
    void register(Text text);
    void register(String text);
    void register(String text, int color);
    void register(String text, Formatting... formattings);

    List<Supplier<Text>> values();
}

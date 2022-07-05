package net.moddingplayground.frame.impl.contentregistries.client;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.moddingplayground.frame.api.contentregistries.v0.client.SplashesRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public final class SplashesRegistryImpl implements SplashesRegistry {
    private final List<Supplier<Text>> values = new ArrayList<>();

    public SplashesRegistryImpl() {}

    @Override
    public void register(Supplier<Text> text) {
        this.values.add(text);
    }

    @Override
    public void register(Text text) {
        this.register(() -> text);
    }

    @Override
    public void register(String text) {
        this.register(Text.of(text));
    }

    @Override
    public void register(String text, int color) {
        this.register(Text.literal(text).setStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public void register(String text, Formatting... formattings) {
        this.register(Text.literal(text).formatted(formattings));
    }

    @Override
    public List<Supplier<Text>> values() {
        return ImmutableList.copyOf(this.values);
    }
}

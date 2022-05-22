package net.moddingplayground.frame.api.config.v0.option;

import com.google.gson.JsonElement;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class Option<T> {
    private final T defaultValue;
    private T value;

    public Option(T defaultValue) {
        this.defaultValue = defaultValue;
        this.value = this.defaultValue;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean is(T other) {
        return this.value.equals(other);
    }

    public abstract JsonElement toJson();
    public abstract void fromJson(JsonElement json);

    protected void invalidConfig(JsonElement json) {
        System.err.printf("Loading default value for config %s: %s%n", this, json);
    }

    @Environment(EnvType.CLIENT)
    public abstract void addConfigEntries(ConfigCategory category, Identifier id, ConfigEntryBuilder builder);

    @Environment(EnvType.CLIENT)
    public String getTranslationKey(Identifier id) {
        return "config.%s.%s".formatted(id.getNamespace(), id.getPath());
    }

    @Environment(EnvType.CLIENT)
    public Text getTitle(Identifier id) {
        return Text.translatable(this.getTranslationKey(id));
    }

    @Environment(EnvType.CLIENT)
    public Collection<Text> getTooltip(Identifier id) {
        String key = "%s.tooltip".formatted(this.getTranslationKey(id));
        return I18n.hasTranslation(key) ? List.of(Text.translatable(key)) : Collections.emptyList();
    }

    @Environment(EnvType.CLIENT)
    public Optional<Text[]> getTooltipArray(Identifier id) {
        Collection<Text> texts = this.getTooltip(id);
        return texts.isEmpty() ? Optional.empty() : Optional.of(texts.toArray(Text[]::new));
    }

    @Override
    public String toString() {
        return "%s{value=%s}".formatted(this.getClass().getSimpleName(), this.value);
    }
}

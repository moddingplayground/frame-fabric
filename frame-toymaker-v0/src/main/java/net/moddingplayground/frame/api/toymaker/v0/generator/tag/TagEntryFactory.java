package net.moddingplayground.frame.api.toymaker.v0.generator.tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

public class TagEntryFactory<T> {
    private boolean replace;
    private final Function<T, Identifier> idGetter;
    private final Set<String> entries = new LinkedHashSet<>();

    public TagEntryFactory(Function<T, Identifier> idGetter) {
        this.idGetter = idGetter;
    }

    public final TagEntryFactory<T> add(String... ids) {
        this.entries.addAll(Arrays.asList(ids));
        return this;
    }

    @SafeVarargs
    public final TagEntryFactory<T> add(T... objects) {
        for (T obj : objects) {
            this.entries.add(this.idGetter.apply(obj).toString());
        }
        return this;
    }

    @SafeVarargs
    public final TagEntryFactory<T> add(Tag<T>... tags) {
        for (Tag<T> tag : tags) {
            if (tag instanceof Tag.Identified identified) {
                this.entries.add(String.format("#%s", identified.getId()));
            } else {
                throw new RuntimeException("Cannot identify tag " + tag);
            }
        }
        return this;
    }

    public final void copyTo(TagEntryFactory<?> factory) {
        this.entries.forEach(factory::add);
    }

    public final TagEntryFactory<T> replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public final boolean isReplace() {
        return this.replace;
    }

    public final Set<String> getEntries() {
        return this.entries;
    }

    public JsonObject createJson() {
        JsonObject root = new JsonObject();
        root.addProperty("replace", replace);
        JsonArray values = new JsonArray();
        this.entries.forEach(values::add);
        root.add("values", values);
        return root;
    }
}

package net.moddingplayground.frame.api.toymaker.v0.generator.tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.tag.TagKey;
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
    public final TagEntryFactory<T> add(TagKey<T>... tags) {
        for (TagKey<T> tag : tags) this.entries.add(String.format("#%s", tag.id()));
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
        this.entries.stream()
                    .sorted((o1, o2) -> {
                        if (o1.equals(o2)) return 0;
                        if (o1.startsWith("#") && o2.startsWith("#")) return 0;
                        return o2.startsWith("#") ? 1 : -1;
                    })
                    .forEach(values::add);
        root.add("values", values);
        return root;
    }
}

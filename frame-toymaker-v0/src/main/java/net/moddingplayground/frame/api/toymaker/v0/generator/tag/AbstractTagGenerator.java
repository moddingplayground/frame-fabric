package net.moddingplayground.frame.api.toymaker.v0.generator.tag;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.moddingplayground.frame.api.toymaker.v0.generator.AbstractGenerator;
import net.moddingplayground.frame.mixin.toymaker.TagManagerLoaderAccessor;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public abstract class AbstractTagGenerator<T> extends AbstractGenerator<Identifier, TagEntryFactory<T>> {
    protected final Registry<T> registry;
    private final NameType nameType;

    public AbstractTagGenerator(String modId, Registry<T> registry, NameType nameType) {
        super(modId);
        this.registry = registry;
        this.nameType = nameType;
    }

    public AbstractTagGenerator(String modId, Registry<T> registry) {
        this(modId, registry, NameType.infer(registry));
    }

    @SafeVarargs
    public final TagEntryFactory<T> add(TagKey<T> tag, T... objects) {
        return this.getOrCreateFactory(tag).add(objects);
    }

    @SafeVarargs
    public final TagEntryFactory<T> add(TagKey<T> tag, RegistryKey<T>... objects) {
        TagEntryFactory<T> factory = this.getOrCreateFactory(tag);
        for (RegistryKey<T> object : objects) factory.add(this.registry.get(object));
        return factory;
    }

    @SafeVarargs
    public final TagEntryFactory<T> add(TagKey<T> tag, TagKey<T>... tags) {
        return this.getOrCreateFactory(tag).add(tags);
    }

    public TagEntryFactory<T> getOrCreateFactory(TagKey<T> tag) {
        return this.getOrCreateFactory(tag.id());
    }

    public TagEntryFactory<T> getOrCreateFactory(Identifier id) {
        return this.map.computeIfAbsent(this.getId(id), i -> new TagEntryFactory<>(this.registry::getId));
    }

    public Identifier getId(Identifier id) {
        String format = this.getFormat();
        RegistryKey<? extends Registry<T>> key = this.registry.getKey();
        Identifier reg = key.getValue();
        return new Identifier(id.getNamespace(), format.formatted(reg.getPath(), id.getPath()));
    }

    public String getFormat() {
        return this.nameType.format;
    }

    public enum NameType {
        SINGULAR("%s/%s"), PLURAL("%ss/%s");

        public static final Set<RegistryKey<? extends Registry<?>>> INFER_PLURAL = TagManagerLoaderAccessor.getDIRECTORIES().keySet();

        public final String format;

        NameType(String format) {
            this.format = format;
        }

        public static <T> NameType infer(Registry<T> reg) {
            return INFER_PLURAL.contains(reg.getKey()) ? PLURAL : SINGULAR;
        }
    }
}

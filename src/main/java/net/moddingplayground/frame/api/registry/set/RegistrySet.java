package net.moddingplayground.frame.api.registry.set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.impl.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RegistrySet {
    private static final Logger LOGGER = LoggerFactory.getLogger(Frame.MOD_ID);

    protected final Map<RegistrableObject<?>, FormattableIdentifier> objects;
    protected final Map<RegistrableObject<?>, Object> entries;
    private boolean registered;

    public RegistrySet() {
        this.objects = Maps.newLinkedHashMap();
        this.entries = Maps.newLinkedHashMap();
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public List<Object> getArgs(RegistrableObject<?> object) {
        return Lists.newArrayList(object.getId());
    }

    public <T> T get(RegistrableObject<T> object, Class<T> clazz) {
        Object entry = this.entries.get(object);
        if (clazz.isInstance(entry)) return clazz.cast(entry);
        return null;
    }

    @SuppressWarnings("unchecked")
    protected <T, R extends RegistrySet> R add(FormattableIdentifier id, RegistrableObject<T> object) {
        if (this.objects.put(object, id) != null) LOGGER.warn("Replaced a {} object: {}", this.getClass().getSimpleName(), id);
        return (R) this;
    }

    public boolean contains(RegistrableObject<?>... objects) {
        return this.entries.keySet().containsAll(List.of(objects));
    }

    public boolean containsOrThrow(RegistrableObject<?>... objects) {
        if (!this.contains(objects)) throw new IllegalArgumentException("Objects " + Arrays.toString(objects) + "are not present in " + this);
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrySet> void requireTo(Consumer<T> action, RegistrableObject<?>... required) {
        if (this.contains(required)) action.accept((T) this);
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrySet> T register() {
        if (this.registered) {
            LOGGER.warn("Tried to register a {} more than once!", this.getClass().getSimpleName());
            return (T) this;
        }

        this.initializeRegisterables();

        for (Map.Entry<RegistrableObject<?>, FormattableIdentifier> entry : this.objects.entrySet()) {
            RegistrableObject<?> registerable = entry.getKey();
            Object object = registerable.create(this);
            this.entries.put(registerable, object);
        }

        for (Map.Entry<RegistrableObject<?>, Object> entry : this.entries.entrySet()) {
            RegistrableObject<?> registerable = entry.getKey();
            Object object = entry.getValue();
            FormattableIdentifier id = this.objects.get(registerable);

            Identifier identifier = id.format(this.getArgs(registerable));
            registerable.register(identifier, object, this);
        }

        this.registered = true;
        return (T) this;
    }

    protected void initializeRegisterables() {}
}

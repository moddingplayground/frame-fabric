package net.moddingplayground.frame.impl.woodtypes;

import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.woodtypes.v0.FrameWoodTypes;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;
import net.moddingplayground.frame.api.woodtypes.v0.object.RegisteredWoodObject;
import net.moddingplayground.frame.api.woodtypes.v0.object.WoodObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WoodTypeImpl implements WoodType {
    private final Map<WoodObject<?>, RegisteredWoodObject> objects;

    public WoodTypeImpl(Collection<WoodObject<?>> exclude, Collection<WoodObject<?>> extra) {
        List<WoodObject<?>> includes = new ArrayList<>(WoodObject.ALL);
        exclude.forEach(includes::remove);
        includes.addAll(extra);
        this.objects = includes.stream().collect(Collectors.toMap(Function.identity(), o -> null));
    }

    @Override
    public <T extends RegisteredWoodObject> Optional<T> get(WoodObject<T> key) {
        //noinspection unchecked
        return Optional.ofNullable((T) this.objects.get(key));
    }

    @Override
    public WoodType register() {
        Set<WoodObject<?>> keys = this.objects.keySet();
        for (WoodObject<?> object : keys) this.objects.put(object, object.register(this));
        return this;
    }

    @Override
    public Identifier getId() {
        return FrameWoodTypes.REGISTRY.getId(this);
    }

    @Override
    public String toString() {
        Identifier id = this.getId();
        return Optional.ofNullable(id).map(Identifier::toString).orElse("Unidentified Frame Wood Type");
    }
}

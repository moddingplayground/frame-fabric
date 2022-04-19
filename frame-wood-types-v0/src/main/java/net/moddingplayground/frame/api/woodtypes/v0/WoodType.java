package net.moddingplayground.frame.api.woodtypes.v0;

import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.woodtypes.v0.object.RegisteredWoodObject;
import net.moddingplayground.frame.api.woodtypes.v0.object.WoodObject;
import net.moddingplayground.frame.impl.woodtypes.WoodTypeImpl;
import org.apache.commons.compress.utils.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface WoodType {
    <T extends RegisteredWoodObject> Optional<T> get(WoodObject<T> key);

    WoodType register();

    Identifier getId();

    static WoodType.Builder builder() {
        return new Builder();
    }

    static WoodType create() {
        return builder().build();
    }

    class Builder {
        private final List<WoodObject<?>> exclude = Lists.newArrayList();
        private final List<WoodObject<?>> extra = Lists.newArrayList();

        Builder() {}

        public Builder exclude(WoodObject<?>... objects) {
            Collections.addAll(this.exclude, objects);
            return this;
        }

        public Builder extra(WoodObject<?>... objects) {
            Collections.addAll(this.extra, objects);
            return this;
        }

        public WoodType build() {
            return new WoodTypeImpl(this.exclude, this.extra);
        }
    }
}

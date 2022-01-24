package net.moddingplayground.frame.api.util;

import java.util.Optional;
import java.util.function.Supplier;

public class GUIIcon<T> {
    private final Factory<T> factory;

    protected GUIIcon(Factory<T> factory) {
        this.factory = factory;
    }

    public static <T> GUIIcon<T> of(Supplier<T> base, Supplier<T> hovered, Supplier<T> selected) {
        return new GUIIcon<>((h, s) -> {
            if (s) return selected.get();
            if (h) return hovered.get();
            return base.get();
        });
    }

    public static <T> GUIIcon<T> of(Supplier<T> base, Supplier<T> hovered, boolean delegateSelected) {
        return of(base, hovered, delegateSelected ? hovered : base);
    }

    public static <T> GUIIcon<T> of(Supplier<T> base, Supplier<T> hovered) {
        return of(base, hovered, true);
    }

    public static <T> GUIIcon<T> of(Supplier<T> base) {
        return of(base, base);
    }

    public T getIcon(boolean hovered, boolean selected) {
        return this.factory.create(hovered, selected);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> optional(GUIIcon<?> icon, boolean hovered, boolean selected, Class<T> clazz) {
        Object ico = icon.getIcon(hovered, selected);
        return ico.getClass() == clazz ? Optional.of((T) ico) : Optional.empty();
    }

    @FunctionalInterface interface Factory<T> { T create(boolean hovered, boolean selected); }
}

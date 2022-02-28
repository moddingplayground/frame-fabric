package net.moddingplayground.frame.api.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A cached function that can be applied in either direction.
 */
public class ReverseMemoizeFunction<T, R> implements Function<T, R> {
    private final Function<T, R> function;
    private final Map<T, R> cache;
    private final Map<R, T> reversed;

    public ReverseMemoizeFunction(Function<T, R> function) {
        this.function = function;
        this.cache = Maps.newHashMap();
        this.reversed = Maps.newHashMap();
    }

    public static <T, R> ReverseMemoizeFunction<T, R> create(Function<T, R> function) {
        return new ReverseMemoizeFunction<>(function);
    }

    public R apply(T object) {
        return this.cache.computeIfAbsent(object, function);
    }

    public T reverse(R object) {
        return this.reversed.computeIfAbsent(object, r -> {
            Set<Map.Entry<T, R>> entries = this.cache.entrySet();
            return entries.stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)).get(object);
        });
    }

    public String toString() {
        return "memoize/1[function=" + function + ", size=" + this.cache.size() + ", size_reversed=" + this.reversed.size() + "]";
    }
}

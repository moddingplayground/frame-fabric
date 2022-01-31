package net.moddingplayground.frame.api.registry.set;

import net.minecraft.util.Identifier;

import java.util.List;

public class FormattableIdentifier {
    public static final String DEFAULT_PATH = "%s_%s";
    private final String namespace, path;

    public FormattableIdentifier(String namespace, String path) {
        this.namespace = namespace;
        this.path = path == null ? DEFAULT_PATH : path;
    }

    public static FormattableIdentifier ofNamespace(String namespace) {
        return new FormattableIdentifier(namespace, DEFAULT_PATH);
    }

    public static FormattableIdentifier ofPath(String path) {
        return new FormattableIdentifier(Identifier.DEFAULT_NAMESPACE, path);
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getPath() {
        return this.path;
    }

    public Identifier format(List<?> args) {
        return this.format(args.toArray(Object[]::new));
    }

    public Identifier format(Object... args) {
        return new Identifier(this.toString().formatted(args));
    }

    public Identifier toId() {
        return new Identifier(this.toString());
    }

    @Override
    public String toString() {
        return this.getNamespace() + Identifier.NAMESPACE_SEPARATOR + this.getPath();
    }
}

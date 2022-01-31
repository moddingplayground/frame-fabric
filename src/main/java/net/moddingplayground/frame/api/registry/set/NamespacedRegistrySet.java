package net.moddingplayground.frame.api.registry.set;

public class NamespacedRegistrySet extends RegistrySet {
    private final String namespace;

    public NamespacedRegistrySet(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return this.namespace;
    }

    protected <T, R extends RegistrySet> R add(String format, RegistrableObject<T> object) {
        FormattableIdentifier id = new FormattableIdentifier(this.getNamespace(), format);
        return super.add(id, object);
    }

    @Override
    public String toString() {
        return "NamespacedRegistrySet{" + "namespace='" + namespace + '\'' + "} ";
    }
}

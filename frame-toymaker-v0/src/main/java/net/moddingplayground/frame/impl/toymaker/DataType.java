package net.moddingplayground.frame.impl.toymaker;

public enum DataType {
    ASSET("assets"),
    DATA("data");

    private final String id;

    DataType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

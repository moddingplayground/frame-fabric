package net.moddingplayground.frame.api.woodtypes.v0.object;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;

import java.util.function.Function;

public record ItemWoodObject(String format, Function<WoodType, Item> factory) implements WoodObject<ItemWoodObject.Registered> {
    public ItemWoodObject(String format, Function<WoodType, Item> factory) {
        this.format = format;
        this.factory = Util.memoize(factory);
    }

    @Override
    public Registered register(WoodType type) {
        Identifier typeId = type.getId();
        String ns = typeId.getNamespace();
        String format = this.format().formatted(typeId.getPath(), ns);
        if (!format.contains(Identifier.DEFAULT_NAMESPACE)) format = ns + Identifier.DEFAULT_NAMESPACE + format;

        Identifier id = Identifier.tryParse(format);
        Item item = this.factory().apply(type);
        Registry.register(Registry.ITEM, id, item);

        return new Registered(item);
    }

    public record Registered(Item item) implements RegisteredWoodObject {}
}

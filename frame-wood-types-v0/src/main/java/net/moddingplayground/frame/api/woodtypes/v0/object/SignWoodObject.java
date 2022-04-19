package net.moddingplayground.frame.api.woodtypes.v0.object;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;

import java.util.function.Function;

public record SignWoodObject(String format, String wallFormat, Function<WoodType, Block> factory, Function<WoodType, Block> wallFactory) implements WoodObject<SignWoodObject.Registered> {
    public SignWoodObject(String format, String wallFormat, Function<WoodType, Block> factory, Function<WoodType, Block> wallFactory) {
        this.format = format;
        this.wallFormat = wallFormat;
        this.factory = Util.memoize(factory);
        this.wallFactory = Util.memoize(wallFactory);
    }

    @Override
    public Registered register(WoodType type) {
        Identifier typeId = type.getId();
        String ns = typeId.getNamespace();
        String format = this.format().formatted(typeId.getPath(), ns);
        if (!format.contains(Identifier.DEFAULT_NAMESPACE)) format = ns + Identifier.DEFAULT_NAMESPACE + format;

        Identifier id = Identifier.tryParse(format);
        Block block = this.factory().apply(type);
        Block blockWall = this.wallFactory().apply(type);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.BLOCK, id, blockWall);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new FabricItemSettings())); // TODO

        return new Registered(block);
    }

    public record Registered(Block block) implements RegisteredWoodObject {}
}

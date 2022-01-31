package net.moddingplayground.frame.api.block.wood;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.registry.set.FormattableIdentifier;
import net.moddingplayground.frame.api.registry.set.RegistrableObject;
import net.moddingplayground.frame.api.registry.set.RegistrySet;

public enum WoodBlock implements RegistrableObject<Block> {
    PLANKS(WoodBlockDefaults::planks),
    SAPLING(WoodBlockDefaults::sapling),
    POTTED_SAPLING("potted_%s_sapling", WoodBlockDefaults::pottedSapling, BlockItemFactory.NONE),
    LOG(WoodBlockDefaults::log),
    STRIPPED_LOG("stripped_%s_log", WoodBlockDefaults::strippedLog),
    WOOD(WoodBlockDefaults::wood),
    STRIPPED_WOOD("stripped_%s_wood", WoodBlockDefaults::wood),
    LEAVES(WoodBlockDefaults::leaves),
    SLAB(WoodBlockDefaults::slab),
    FENCE(WoodBlockDefaults::fence),
    STAIRS(WoodBlockDefaults::stairs),
    BUTTON(WoodBlockDefaults::button),
    PRESSURE_PLATE(WoodBlockDefaults::pressurePlate),
    DOOR(WoodBlockDefaults::door),
    TRAPDOOR(WoodBlockDefaults::trapdoor),
    FENCE_GATE(WoodBlockDefaults::fenceGate),
    SIGN(WoodBlockDefaults::sign, WoodBlockDefaults::signItem),
    WALL_SIGN(WoodBlockDefaults::wallSign, BlockItemFactory.NONE);

    public static final RegistrableObject<Item> BOAT = new RegistrableObject<>() {
        @Override
        public String getId() {
            return "boat";
        }

        @Override
        public <R extends RegistrySet> Item create(R set) {
            WoodBlockSet woodSet = (WoodBlockSet) set;
            WoodBlockSet.Settings settings = woodSet.getSettings();
            return new FrameBoatItem(woodSet, new FabricItemSettings().maxCount(1).group(settings.itemGroup()));
        }

        @Override
        public <R extends RegistrySet> Item register(Identifier id, Object object, R set) {
            return Registry.register(Registry.ITEM, id, (Item) object);
        }
    };

    private final String id;
    private final String format;
    private final BlockFactory block;
    private final BlockItemFactory item;

    WoodBlock(String format, BlockFactory block, BlockItemFactory item) {
        this.id = this.name().toLowerCase();
        this.format = format;
        this.block = block;
        this.item = item;
    }

    WoodBlock(String format, BlockFactory block) {
        this(format, block, (set, b, settings) -> new BlockItem(b, settings));
    }

    WoodBlock(BlockFactory block, BlockItemFactory item) {
        this(null, block, item);
    }

    WoodBlock(BlockFactory block) {
        this(block, BlockItemFactory.DEFAULT);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getFormat() {
        return this.format;
    }

    public Block createBlock(WoodBlockSet set) {
        WoodBlockSet.Settings settings = set.getSettings();
        if (settings.vanilla()) {
            FormattableIdentifier id = FormattableIdentifier.ofPath(this.format);
            Identifier identifier = id.format(set.getId(), this.getId());
            return Registry.BLOCK.get(identifier);
        }
        return this.block.create(set);
    }

    public Item createItem(WoodBlockSet set, Block block) {
        WoodBlockSet.Settings settings = set.getSettings();
        return this.item.create(set, block, new FabricItemSettings().group(settings.itemGroup()));
    }

    @Override
    public <R extends RegistrySet> Block create(R set) {
        return this.createBlock((WoodBlockSet) set);
    }

    @Override
    public <R extends RegistrySet> Block register(Identifier id, Object object, R set) {
        if (!(object instanceof Block block) || Registry.BLOCK.containsId(id)) return null;

        WoodBlockSet woodSet = (WoodBlockSet) set;
        Item item = this.createItem(woodSet, block);
        if (item != null) Registry.register(Registry.ITEM, id, item);

        return Registry.register(Registry.BLOCK, id, block);
    }

    @FunctionalInterface
    public interface BlockFactory {
        Block create(WoodBlockSet set);
    }

    @FunctionalInterface
    public interface BlockItemFactory {
        BlockItemFactory NONE = (ctx, block, settings) -> null;
        BlockItemFactory DEFAULT = (ctx, block, settings) -> new BlockItem(block, settings);
        Item create(WoodBlockSet set, Block block, Item.Settings settings);
    }
}

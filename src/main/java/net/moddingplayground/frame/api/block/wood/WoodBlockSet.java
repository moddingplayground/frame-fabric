package net.moddingplayground.frame.api.block.wood;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.registry.FrameRegistry;
import net.moddingplayground.frame.api.registry.set.BlockSet;
import net.moddingplayground.frame.api.registry.set.FormattableIdentifier;
import net.moddingplayground.frame.api.registry.set.RegistrableObject;
import net.moddingplayground.frame.api.registry.set.RegistrySet;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

import static net.moddingplayground.frame.api.block.wood.WoodBlock.*;

public class WoodBlockSet extends BlockSet {
    protected final String id;
    protected final Settings settings;
    protected final EnumSet<WoodBlock> include;
    protected SignType signType;

    public WoodBlockSet(String modId, String id, Settings settings, EnumSet<WoodBlock> include) {
        super(modId);
        this.id = id;
        this.settings = settings;
        this.include = include;
    }

    public WoodBlockSet(String modId, String id, Settings settings) {
        this(modId, id, settings, EnumSet.allOf(WoodBlock.class));
    }

    public String getId() {
        return this.id;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public SignType getOrCreateSignType() {
        if (this.signType == null) this.signType = FrameSignType.register(new Identifier(this.getNamespace(), this.getId()));
        return this.signType;
    }

    public void requireWood(Consumer<WoodBlockSet> action, WoodBlock... required) {
        this.requireTo(action, required);
    }

    @Override
    public List<Object> getArgs(RegistrableObject<?> object) {
        List<Object> list = super.getArgs(object);
        list.add(0, this.getId());
        return list;
    }

    @Override
    protected void initializeRegisterables() {
        for (WoodBlock wood : WoodBlock.values()) {
            if (this.include.contains(wood)) this.add(wood.getFormat(), wood);
        }
    }

    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        if (!this.getSettings().vanilla()) {
            BlockRenderLayerMap layers = BlockRenderLayerMap.INSTANCE;
            requireWood(s -> layers.putBlock(s.getBlock(DOOR), RenderLayer.getCutout()), DOOR);
            requireWood(s -> layers.putBlock(s.getBlock(TRAPDOOR), RenderLayer.getCutout()), TRAPDOOR);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RegistrySet> T register() {
        boolean registered = this.isRegistered();
        super.register();

        if (!registered) {
            String namespace = this.getNamespace();
            String id = this.getId();
            Settings settings = this.getSettings();

            if (settings.boat()) {
                FormattableIdentifier formatId = FormattableIdentifier.ofNamespace(namespace);
                Item item = BOAT.register(formatId.format(this.getArgs(BOAT)), BOAT.create(this), this);
                this.entries.put(BOAT, item);
            }

            if (settings.flammable()) {
                FlammableBlockRegistry flamReg = FlammableBlockRegistry.getDefaultInstance();
                FuelRegistry fuelReg = FuelRegistry.INSTANCE;
                requireWood(s -> flamReg.add(s.getBlock(PLANKS), 5, 20), PLANKS);
                requireWood(s -> flamReg.add(s.getBlock(SLAB), 5, 20), SLAB);
                requireWood(s -> flamReg.add(s.getBlock(STAIRS), 5, 20), STAIRS);
                requireWood(s -> flamReg.add(s.getBlock(LOG), 5, 5), LOG);
                requireWood(s -> flamReg.add(s.getBlock(STRIPPED_LOG), 5, 5), STRIPPED_LOG);
                requireWood(s -> flamReg.add(s.getBlock(STRIPPED_WOOD), 5, 5), STRIPPED_WOOD);
                requireWood(s -> flamReg.add(s.getBlock(WOOD), 5, 5), WOOD);
                requireWood(s -> flamReg.add(s.getBlock(LEAVES), 30, 60), LEAVES);

                requireWood(s -> {
                    Block block = s.getBlock(FENCE);
                    flamReg.add(block, 5, 20);
                    fuelReg.add(block, 300);
                }, FENCE);

                requireWood(s -> {
                    Block block = s.getBlock(FENCE_GATE);
                    flamReg.add(block, 5, 20);
                    fuelReg.add(block, 300);
                }, FENCE_GATE);

                requireWood(s -> {
                    Block block = s.getBlock(SIGN);
                    Item item = block.asItem();
                    fuelReg.add(item, 200);
                }, SIGN);
            }

            Registry.register(FrameRegistry.WOOD, new Identifier(namespace, id), this);
        }

        return (T) this;
    }

    @Override
    public String toString() {
        return new Identifier(this.getNamespace(), this.getId()).toString();
    }

    public record Settings(
        Material material, Material materialDecoration, Material materialLeaves, Material materialSapling,
        MapColor mapColorLight, MapColor mapColorDark,
        BlockSoundGroup soundGroup, BlockSoundGroup soundGroupLeaves, BlockSoundGroup soundGroupSapling,
        SaplingGenerator saplingGenerator, PressurePlateBlock.ActivationRule activationRule,
        boolean vanilla, boolean flammable, boolean boat, ItemGroup itemGroup
    ) {
        public static final Settings DEFAULT = builder().build();

        public static Builder builder() {
            return new Builder();
        }

        public static Builder builder(Settings settings) {
            return new Builder(settings);
        }

        public static class Builder {
            private Material material = Material.WOOD;
            private Material materialDecoration = Material.DECORATION;
            private Material materialLeaves = Material.LEAVES;
            private Material materialSapling = Material.PLANT;
            private MapColor mapColorLight = MapColor.OAK_TAN;
            private MapColor mapColorDark = MapColor.SPRUCE_BROWN;
            private BlockSoundGroup soundGroup = BlockSoundGroup.WOOD;
            private BlockSoundGroup soundGroupLeaves = BlockSoundGroup.GRASS;
            private BlockSoundGroup soundGroupSapling = BlockSoundGroup.GRASS;
            private SaplingGenerator saplingGenerator = new OakSaplingGenerator();
            private PressurePlateBlock.ActivationRule activationRule = PressurePlateBlock.ActivationRule.EVERYTHING;
            private boolean vanilla = false;
            private boolean flammable, boat = true;
            private ItemGroup itemGroup = null;

            public Builder() {}

            public Builder(Settings settings) {
                this.material = settings.material();
                this.materialDecoration = settings.materialDecoration();
                this.materialLeaves = settings.materialLeaves();
                this.materialSapling = settings.materialSapling;
                this.mapColorLight = settings.mapColorLight();
                this.mapColorDark = settings.mapColorDark();
                this.soundGroup = settings.soundGroup();
                this.soundGroupLeaves = settings.soundGroupLeaves();
                this.soundGroupSapling = settings.soundGroupSapling();
                this.activationRule = settings.activationRule();
                this.vanilla = settings.vanilla();
                this.flammable = settings.flammable();
                this.boat = settings.boat();
                this.itemGroup = settings.itemGroup();
            }

            public Builder material(Material material) {
                this.material = material;
                return this;
            }

            public Builder materialDecoration(Material material) {
                this.materialDecoration = material;
                return this;
            }

            public Builder materialLeaves(Material material) {
                this.materialLeaves = material;
                return this;
            }

            public Builder materialSapling(Material material) {
                this.materialSapling = material;
                return this;
            }

            public Builder mapColorLight(MapColor mapColor) {
                this.mapColorLight = mapColor;
                return this;
            }

            public Builder mapColorDark(MapColor mapColor) {
                this.mapColorDark = mapColor;
                return this;
            }

            public Builder soundGroup(BlockSoundGroup soundGroup) {
                this.soundGroup = soundGroup;
                return this;
            }

            public Builder soundGroupLeaves(BlockSoundGroup soundGroup) {
                this.soundGroupLeaves = soundGroup;
                return this;
            }

            public Builder soundGroupSapling(BlockSoundGroup soundGroup) {
                this.soundGroupSapling = soundGroup;
                return this;
            }

            public Builder saplingGenerator(SaplingGenerator generator) {
                this.saplingGenerator = generator;
                return this;
            }

            public Builder activationRule(PressurePlateBlock.ActivationRule rule) {
                this.activationRule = rule;
                return this;
            }

            public Builder vanilla(boolean vanilla) {
                this.vanilla = vanilla;
                return this;
            }

            public Builder vanilla() {
                return this.vanilla(true);
            }

            public Builder flammable(boolean flammable) {
                this.flammable = flammable;
                return this;
            }

            public Builder boat(boolean boat) {
                this.boat = boat;
                return this;
            }

            public Builder itemGroup(ItemGroup group) {
                this.itemGroup = group;
                return this;
            }

            public Settings build() {
                return new Settings(
                    this.material, this.materialDecoration, this.materialLeaves, this.materialSapling,
                    this.mapColorLight, this.mapColorDark,
                    this.soundGroup, this.soundGroupLeaves, this.soundGroupSapling,
                    this.saplingGenerator, this.activationRule,
                    this.vanilla, this.flammable, this.boat, this.itemGroup
                );
            }
        }
    }
}

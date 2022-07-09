package net.moddingplayground.frame.api.toymaker.v0.model.uploader;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.MultipartBlockStateSupplier;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.data.client.When;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.toymaker.v0.model.FrameModels;
import net.moddingplayground.frame.api.toymaker.v0.model.FrameTextureMaps;
import net.moddingplayground.frame.api.toymaker.v0.model.FrameTexturedModels;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.minecraft.data.client.BlockStateModelGenerator.*;
import static net.moddingplayground.frame.api.toymaker.v0.model.ModelHelpers.*;

public class BlockStateModelUploader {
    private final BlockStateModelGenerator generator;
    private final BiConsumer<Identifier, Supplier<JsonElement>> modelCollector;

    public BlockStateModelUploader(BlockStateModelGenerator generator) {
        this.generator = generator;
        this.modelCollector = this.generator.modelCollector;
    }

    public static BlockStateModelUploader of(BlockStateModelGenerator blockStateModelGenerator) {
        return new BlockStateModelUploader(blockStateModelGenerator);
    }

    public void registerGeneratedOverlay(Item... items) {
        for (Item item : items) upload(FrameModels.GENERATED_OVERLAY, ModelIds.getItemModelId(item), FrameTextureMaps.generatedOverlay(item));
    }

    public void register(TexturedModel.Factory model, Block... blocks) {
        for (Block block : blocks) this.generator.registerSingleton(block, model);
    }

    public void register(Model model, TextureMap textureMap, Block... blocks) {
        for (Block block : blocks) this.generator.registerSingleton(block, textureMap, model);
    }

    public void register(Model model, Function<Block, TextureMap> textureMapFunction, Block... blocks) {
        for (Block block : blocks) this.generator.registerSingleton(block, textureMapFunction.apply(block), model);
    }

    public void accept(BlockStateSupplier state) {
        this.generator.blockStateCollector.accept(state);
    }

    public Identifier upload(Model model, Block block, TextureMap textureMap) {
        return model.upload(block, textureMap, this.modelCollector);
    }

    public Identifier upload(Model model, Block block, String suffix, TextureMap textureMap) {
        return model.upload(block, suffix, textureMap, this.modelCollector);
    }

    public Identifier upload(Model model, Identifier id, TextureMap textureMap) {
        return model.upload(id, textureMap, this.modelCollector);
    }

    public Identifier upload(TexturedModel.Factory model, Block block) {
        return model.upload(block, this.modelCollector);
    }

    public Identifier upload(TexturedModel.Factory model, Block block, String suffix) {
        return model.upload(block, suffix, this.modelCollector);
    }

    public void registerGeneratedItemModel(Block... blocks) {
        for (Block block : blocks) this.generator.registerItemModel(block.asItem());
    }

    public void registerIfPresent(BlockFamily family, BlockFamily.Variant variant, Predicate<Block> predicate, Consumer<Block> action) {
        Optional.ofNullable(family.getVariant(variant)).filter(predicate).ifPresent(action);
    }

    public void registerIfPresent(BlockFamily family, BlockFamily.Variant variant, Consumer<Block> action) {
        registerIfPresent(family, variant, block -> true, action);
    }

    public void registerAxisRotated(TexturedModel.Factory model, Block... blocks) {
        for (Block block : blocks) this.generator.registerAxisRotated(block, model);
    }

    public void registerAxisRotatedColumn(Block... blocks) {
        for (Block block : blocks) this.accept(this.axisRotatedColumn(block));
    }

    public BlockStateSupplier axisRotatedColumn(Block block) {
        Identifier model = this.upload(TexturedModel.CUBE_COLUMN, block);
        return BlockStateModelGenerator.createAxisRotatedBlockState(block, model);
    }

    public void registerRotatingIntProperty(IntProperty property, Block... blocks) {
        for (Block block : blocks) {
            this.accept(this.rotatingIntProperty(block, property));
            this.registerGeneratedItemModel(block);
        }
    }

    public VariantsBlockStateSupplier rotatingIntProperty(Block block, IntProperty property) {
        return VariantsBlockStateSupplier.create(block)
                                         .coordinate(
                                             BlockStateVariantMap.create(property)
                                                                 .registerVariants(layer -> {
                                                                     layer -= 1;
                                                                     String suffix = layer == 0 ? "" : "" + layer;
                                                                     return rotateAll(ModelIds.getBlockSubModelId(block, suffix));
                                                                 })
                                         );
    }

    public void registerVaryingFloorLayers(int variants, Block... blocks) {
        for (Block block : blocks) {
            this.accept(this.varyingFloorLayers(block, variants));
            this.registerGeneratedItemModel(block);
        }
    }

    public VariantsBlockStateSupplier varyingFloorLayers(Block block, int variants) {
        return createVariants(block, Util.make(new ArrayList<>(), list -> {
            for (int i = 0; i <= variants; i++) {
                String suffix = i == 0 ? "" : "" + i;
                TextureMap textureMap = TextureMap.texture(TextureMap.getSubId(block, suffix));
                Identifier model = this.upload(FrameModels.TEMPLATE_FLOOR_LAYER, block, suffix, textureMap);
                rotateAll(list, model);
            }
        }));
    }

    public void registerFlowerPot(Block plant, Block flowerPot, TintType tintType) {
        this.accept(this.flowerPot(plant, flowerPot, tintType));
    }

    public VariantsBlockStateSupplier flowerPot(Block plant, Block flowerPot, TintType tintType) {
        TextureMap textureMap = TextureMap.plant(plant);
        Identifier model = this.upload(tintType.getFlowerPotCrossModel(), flowerPot, textureMap);
        return BlockStateModelGenerator.createSingletonBlockState(flowerPot, model);
    }

    public void registerFlowerPot(TintType tintType, Block... blocks) {
        for (Block block : blocks) this.registerFlowerPot(block, block, tintType);
    }

    public void registerWallPlantThin(Block... blocks) {
        for (Block block : blocks) {
            this.accept(this.wallPlantThin(block));
            this.registerGeneratedItemModel(block);
        }
    }

    public MultipartBlockStateSupplier wallPlantThin(Block block) {
        Identifier model = this.upload(FrameTexturedModels.TEMPLATE_WALL_PLANT_THIN, block);
        MultipartBlockStateSupplier multipart = MultipartBlockStateSupplier.create(block);
        BlockState state = block.getDefaultState();
        When.PropertyCondition when = Util.make(When.create(),
            condition -> CONNECTION_VARIANT_FUNCTIONS.stream()
                                                     .map(Pair::getFirst)
                                                     .filter(state::contains)
                                                     .forEach(property -> condition.set(property, false))
        );
        for (Pair<BooleanProperty, Function<Identifier, BlockStateVariant>> pair : CONNECTION_VARIANT_FUNCTIONS) {
            BooleanProperty property = pair.getFirst();
            Function<Identifier, BlockStateVariant> function = pair.getSecond();
            if (!state.contains(property)) continue;
            multipart.with(When.create().set(property, true), function.apply(model));
            multipart.with(when, function.apply(model));
        }
        return multipart;
    }

    public void registerStairs(Function<Block, TextureMap> textureMapFunction, Block block, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier inner = this.upload(Models.INNER_STAIRS, block, textureMap);
        Identifier regular = this.upload(Models.STAIRS, block, textureMap);
        Identifier outer = this.upload(Models.OUTER_STAIRS, block, textureMap);

        this.accept(BlockStateModelGenerator.createStairsBlockState(block, inner, regular, outer));
        this.generator.registerParentedItemModel(block, regular);
    }

    public void registerSlab(Function<Block, TextureMap> textureMapFunction, Block block, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier bottom = this.upload(Models.SLAB, block, textureMap);
        Identifier top = this.upload(Models.SLAB_TOP, block, textureMap);
        this.accept(BlockStateModelGenerator.createSlabBlockState(block, bottom, top, ModelIds.getBlockModelId(base)));
        this.generator.registerParentedItemModel(block, bottom);
    }

    public void registerWall(Function<Block, TextureMap> textureMapFunction, Block block, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier post = this.upload(Models.TEMPLATE_WALL_POST, block, textureMap);
        Identifier side = this.upload(Models.TEMPLATE_WALL_SIDE, block, textureMap);
        Identifier sideTall = this.upload(Models.TEMPLATE_WALL_SIDE_TALL, block, textureMap);
        this.accept(BlockStateModelGenerator.createWallBlockState(block, post, side, sideTall));
        Identifier inventory = this.upload(Models.WALL_INVENTORY, block, textureMap);
        this.generator.registerParentedItemModel(block, inventory);
    }

    public void registerFence(Function<Block, TextureMap> textureMapFunction, Block block, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier post = this.upload(Models.FENCE_POST, block, textureMap);
        Identifier side = this.upload(Models.FENCE_SIDE, block, textureMap);
        this.accept(BlockStateModelGenerator.createFenceBlockState(block, post, side));
        Identifier inventory = this.upload(Models.FENCE_INVENTORY, block, textureMap);
        this.generator.registerParentedItemModel(block, inventory);
    }

    public void registerFenceGate(Function<Block, TextureMap> textureMapFunction, Block block, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier open = this.upload(Models.TEMPLATE_FENCE_GATE_OPEN, block, textureMap);
        Identifier closed = this.upload(Models.TEMPLATE_FENCE_GATE, block, textureMap);
        Identifier openWall = this.upload(Models.TEMPLATE_FENCE_GATE_WALL_OPEN, block, textureMap);
        Identifier closedWall = this.upload(Models.TEMPLATE_FENCE_GATE_WALL, block, textureMap);
        this.accept(BlockStateModelGenerator.createFenceGateBlockState(block, open, closed, openWall, closedWall));
    }

    public void registerButton(Function<Block, TextureMap> textureMapFunction, Block block, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier regular = this.upload(Models.BUTTON, block, textureMap);
        Identifier pressed = this.upload(Models.BUTTON_PRESSED, block, textureMap);
        this.accept(BlockStateModelGenerator.createButtonBlockState(block, regular, pressed));
        Identifier inventory = this.upload(Models.BUTTON_INVENTORY, block, textureMap);
        this.generator.registerParentedItemModel(block, inventory);
    }

    public void registerPressurePlate(Function<Block, TextureMap> textureMapFunction, Block block, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier up = this.upload(Models.PRESSURE_PLATE_UP, block, textureMap);
        Identifier down = this.upload(Models.PRESSURE_PLATE_DOWN, block, textureMap);
        this.accept(BlockStateModelGenerator.createPressurePlateBlockState(block, up, down));
    }

    public void registerSign(Function<Block, TextureMap> textureMapFunction, Block block, Block wall, Block base) {
        TextureMap textureMap = textureMapFunction.apply(base);
        Identifier model = this.upload(Models.PARTICLE, block, textureMap);
        this.accept(BlockStateModelGenerator.createSingletonBlockState(block, model));
        this.accept(BlockStateModelGenerator.createSingletonBlockState(wall, model));
        this.registerGeneratedItemModel(block);
        this.generator.excludeFromSimpleItemModelGeneration(wall);
    }
}

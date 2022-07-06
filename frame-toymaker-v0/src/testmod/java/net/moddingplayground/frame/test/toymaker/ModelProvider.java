package net.moddingplayground.frame.test.toymaker;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.Items;
import net.moddingplayground.frame.api.toymaker.v0.model.uploader.BlockStateModelUploader;
import net.moddingplayground.frame.api.toymaker.v0.model.uploader.ItemModelUploader;

public final class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        BlockStateModelUploader uploader = BlockStateModelUploader.of(gen);
        uploader.registerGeneratedOverlay(Items.STONE);
        uploader.register(Models.CARPET, TextureMap::wool, Blocks.SAND, Blocks.RED_SAND);
        uploader.registerVaryingFloorLayers(5, Blocks.TERRACOTTA, Blocks.GLASS);
        uploader.registerVaryingFloorLayers(2, Blocks.CLAY, Blocks.TINTED_GLASS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        ItemModelUploader uploader = ItemModelUploader.of(gen);
        uploader.register(Models.GENERATED, Items.ALLAY_SPAWN_EGG, Items.TRIDENT);
    }
}

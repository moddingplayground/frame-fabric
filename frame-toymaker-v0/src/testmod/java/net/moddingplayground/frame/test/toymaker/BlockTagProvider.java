package net.moddingplayground.frame.test.toymaker;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.BlockTags;

import static net.minecraft.block.Blocks.*;

public final class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateTags() {
        this.getOrCreateTagBuilder(BlockTags.WOOL).add(STONE, OAK_PLANKS, SPRUCE_PLANKS);
    }
}

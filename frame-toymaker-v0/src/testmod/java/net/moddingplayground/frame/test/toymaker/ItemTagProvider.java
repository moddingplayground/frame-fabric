package net.moddingplayground.frame.test.toymaker;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.ItemTags;
import net.moddingplayground.frame.api.toymaker.v0.TagHelpers;

import static net.minecraft.item.Items.*;

public final class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    private final BlockTagProvider blockTagProvider;

    public ItemTagProvider(FabricDataGenerator gen, BlockTagProvider blockTagProvider) {
        super(gen, blockTagProvider);
        this.blockTagProvider = blockTagProvider;
    }

    @Override
    protected void generateTags() {
        TagHelpers.copyAll(this.blockTagProvider, this);
        this.getOrCreateTagBuilder(ItemTags.AXOLOTL_TEMPT_ITEMS).add(WHEAT, BEETROOT);
    }
}

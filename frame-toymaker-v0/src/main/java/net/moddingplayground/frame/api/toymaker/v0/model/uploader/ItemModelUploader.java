package net.moddingplayground.frame.api.toymaker.v0.model.uploader;

import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.item.Item;

public class ItemModelUploader {
    private final ItemModelGenerator itemModelGenerator;

    public ItemModelUploader(ItemModelGenerator itemModelGenerator) {
        this.itemModelGenerator = itemModelGenerator;
    }

    public static ItemModelUploader of(ItemModelGenerator itemModelGenerator) {
        return new ItemModelUploader(itemModelGenerator);
    }

    public void register(Model model, Item... items) {
        for (Item item : items) itemModelGenerator.register(item, model);
    }
}
